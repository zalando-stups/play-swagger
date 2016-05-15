package de.zalando.play.controllers

import java.io.File

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import de.zalando.play.controllers.WrappedBodyParsers.Parser
import play.api.Logger
import play.api.http.Status._
import play.api.http._
import play.api.libs.Files.TemporaryFile
import play.api.libs.iteratee._
import play.api.mvc.MultipartFormData.FilePart
import play.api.mvc.Results.Status
import play.api.mvc._

import scala.concurrent.Future
import scala.language.implicitConversions
import scala.reflect.ClassTag
import scala.util.{Either, Left, Right}
import scala.util.control.NonFatal

/**
 * @since 02.09.2015
 */
object WriterFactories {
  private val jsonFactory = new JsonFactory()

  /**
    * Contains proper Jackson Factories for different mime types
    * JsonFactory is a default
    */
  val factories: Map[String, JsonFactory] = Map(
    "application/json" -> jsonFactory,
    "text/x-yaml" -> new YAMLFactory() // TODO implement workaround for bug in yaml parser
  ).withDefaultValue(jsonFactory)
}
object PlayBodyParsing extends PlayBodyParsing {

  import play.api.libs.iteratee.Execution.Implicits.trampoline

  /**
   * Returns proper jackson mapper for given mime type
    *
    * @param mimeType  the mimeType of the required mapper
   * @return
   */
  def jacksonMapper(mimeType: String): ObjectMapper = {
    assert(mimeType != null)
    val factory = WriterFactories.factories(mimeType)
    val mapper = new ObjectMapper(factory)
    mapper.registerModule(DefaultScalaModule)
    mapper
  }

  /**
    * Parser factory for optional bodies
    *
    * @param mimeType name of the parser
    * @param errorMsg error message to return if an input cannot be parsed
    * @param maxLength the maximal length of the content
    * @param tag the ClassTag to use at runtime
    * @tparam T the type of the input the parser should be created for
    * @return BodyParser for the type Option[T]
    */
  def optionParser[T](mimeType: Option[MediaType] => String,
                      customParsers: Seq[(String, Parser[Option[T]])],
                      errorMsg: String, maxLength: Int = parse.DefaultMaxTextLength)
                  (implicit oTag: ClassTag[Option[T]], tag: ClassTag[T]): BodyParser[Option[T]] =
    tolerantBodyParser[Option[T]](maxLength.toLong, errorMsg) { (requestHeader, bytes) =>
      if (bytes.nonEmpty) {
        parserCore(mimeType, customParsers, requestHeader, bytes)
      } else
        None
    }

  /**
   * Parser factory for any type
   *
   * @param mimeType name of the parser
   * @param errorMsg error message to return if an input cannot be parsed
   * @param maxLength the maximal length of the content
   * @param tag the ClassTag to use at runtime
   * @tparam T the type of the input the parser should be created for
   * @return BodyParser for the type T
   */
  def anyParser[T](mimeType: Option[MediaType] => String,
                   customParsers: Seq[(String, Parser[T])],
                   errorMsg: String, maxLength: Int = parse.DefaultMaxTextLength)
                  (implicit tag: ClassTag[T]): BodyParser[T] =
    tolerantBodyParser[T](maxLength.toLong, errorMsg) { (requestHeader, bytes) =>
      parserCore(mimeType, customParsers, requestHeader, bytes)
    }

  private def parserCore[T](mimeType: (Option[MediaType]) => String,
                            customParsers: Seq[(String, Parser[T])],
                            requestHeader: RequestHeader,
                            bytes: Array[Byte])(implicit tag: ClassTag[T]): T = {
    val mimeTypeName = mimeType(requestHeader.mediaType)
    val jacksonParser: (RequestHeader, Array[Byte]) => T =
      (_, bytes) => jacksonMapper(mimeTypeName).readValue(bytes, tag.runtimeClass.asInstanceOf[Class[T]])
    // TODO default play parsers could be used here as well
    val parser = customParsers.find(_._1 == mimeTypeName).map(_._2).getOrElse {
      jacksonParser
    }
    parser(requestHeader, bytes)
  }

  /**
   * Converts parsing errors to Writeable
   */
  def parsingErrors2Writable(mimeType: String): Writeable[Seq[ParsingError]] =
    Writeable(parsingErrors2Bytes(mimeType), Some(mimeType))


  def anyToWritable[T <: Any]: String => Writeable[T] = mimeType =>
    Writeable(jacksonMapper(mimeType).writeValueAsBytes, Some(mimeType))

  /**
   * Converts anything of type Either[Throwable, T] to Writeable
   */
  def eitherToWritable[T](mimeType: String): Writeable[Either[Throwable, T]] =
    Writeable(eitherToT(mimeType), Some(mimeType))

  private def eitherToT[T](mimeType: String): (Either[Throwable, T]) => Array[Byte] =
    (t: Either[Throwable, T]) => {
      val result = t match {
        case Right(rt) => rt
        case Left(throwable) => throwable.getLocalizedMessage
      }
      jacksonMapper(mimeType).writeValueAsBytes(result)
    }

  private def parsingErrors2Bytes(mimeType: String): Seq[ParsingError] => Array[Byte] = errors =>
    jacksonMapper(mimeType).writeValueAsBytes(errors)

  implicit def writers[T]: String => Option[Writeable[T]] =
    mimeType => util.Try(Some(PlayBodyParsing.anyToWritable[T](mimeType))).recover {
      case _: java.util.NoSuchElementException => None
    }.get
}


trait PlayBodyParsing extends BodyParsers {
  val logger = Logger.logger

  type ContentMap = Map[Int, PartialFunction[String, Writeable[Any]]]

  def merge(m1: ContentMap, m2: ContentMap): ContentMap = {
    val onlyFirst = m1.filterKeys(!m2.keySet.contains(_))
    val onlySecond = m2.filterKeys(!m1.keySet.contains(_))
    val both = m1.filterKeys(m2.keySet.contains)
    val merged = both map { case (code, f) =>
        code -> f.orElse(m2(code))
    }
    onlyFirst ++ onlySecond ++ merged
  }

  def negotiateContent(acceptedTypes: Seq[MediaRange], providedTypes: Seq[String]): Option[String] =
    acceptedTypes.sorted collectFirst {
      case mr: MediaRange if providedTypes.exists(mr.accepts) => providedTypes.find(mr.accepts).get
    }

  def defaultErrorMapping: PartialFunction[Throwable, Status] = {
    case _: IllegalArgumentException => Status(BAD_REQUEST)
    case _: IndexOutOfBoundsException => Status(NOT_FOUND)
    case _ => Status(INTERNAL_SERVER_ERROR)
  }

  /**
    * Helper method to parse parameters sent as Headers
    */
  def fromParameters[T](place: String)(key: String, headers: Map[String, Seq[String]], default: Option[T] = None)(implicit binder: QueryStringBindable[T]): Either[String,T] =
    binder.bind(key, headers).getOrElse {
      default.map(d => Right(d)).getOrElse(Left(s"Missing $place parameter(s) for '$key'"))
    }

  /**
    * Helper methods to parse files
    */
  def fromFileOptional[T <: Option[File]](name: String, file: Option[FilePart[TemporaryFile]]) = Right(file.map(_.ref.file))

  def fromFileRequired[T <: File](name: String, file: Option[FilePart[TemporaryFile]]) = file match {
    case Some(filePart) => Right(filePart.ref.file)
    case None => Left(s"Missing file parameter for '$name'")
  }

  /**
   * This is private in play codebase. Copy-pasted it.
   */
  def tolerantBodyParser[A](maxLength: Long, errorMessage: String)(parser: (RequestHeader, Array[Byte]) => A): BodyParser[A] =
    BodyParser(errorMessage + ", maxLength=" + maxLength) { request =>
      import play.api.libs.iteratee.Execution.Implicits.trampoline

      import scala.util.control.Exception._

      val bodyParser: Iteratee[Array[Byte], Either[Result, Either[Future[Result], A]]] =
        Traversable.takeUpTo[Array[Byte]](maxLength).transform(
          Iteratee.consume[Array[Byte]]().map { bytes =>
            allCatch[A].either {
              parser(request, bytes)
            }.left.map {
              case NonFatal(e) =>
                // logger.debug(errorMessage, e)
                createBadResult(errorMessage + ": " + e.getMessage)(request)
              case t => throw t
            }
          }
        ).flatMap(checkForEof(request))

      bodyParser.mapM {
        case Left(tooLarge) => Future.successful(Left(tooLarge))
        case Right(Left(badResult)) => badResult.map(Left.apply)
        case Right(Right(body)) => Future.successful(Right(body))
      }
    }

  /**
   *
   * This is private in play codebase. Copy-pasted it.
   *
   * Check that the input is finished. If it is finished, the iteratee returns `eofValue`.
   * If the input is not finished then it returns a REQUEST_ENTITY_TOO_LARGE result.
   */
  private def checkForEof[A](request: RequestHeader): A => Iteratee[Array[Byte], Either[Result, A]] = { eofValue: A =>
    import play.api.libs.iteratee.Execution.Implicits.trampoline
    def cont: Iteratee[Array[Byte], Either[Result, A]] = Cont {
      case in@Input.El(e) =>
        val badResult: Future[Result] = createBadResult("Request Entity Too Large", REQUEST_ENTITY_TOO_LARGE)(request)
        Iteratee.flatten(badResult.map(r => Done(Left(r), in)))
      case in@Input.EOF =>
        Done(Right(eofValue), in)
      case Input.Empty =>
        cont
    }
    cont
  }

  /**
   * This is private in play codebase. Copy-pasted it.
   */
  private def createBadResult(msg: String, statusCode: Int = BAD_REQUEST): RequestHeader => Future[Result] = { request =>
    LazyHttpErrorHandler.onClientError(request, statusCode, msg)
  }

}
