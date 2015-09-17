package de.zalando.play.controllers

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import play.api.Logger
import play.api.http.Status._
import play.api.http.{LazyHttpErrorHandler, Writeable}
import play.api.libs.iteratee._
import play.api.mvc.Results.Status
import play.api.mvc.{BodyParser, BodyParsers, RequestHeader, Result}

import scala.concurrent.Future
import scala.language.implicitConversions
import scala.reflect.ClassTag
import scala.util.control.NonFatal

/**
 * @since 02.09.2015
 */
object PlayBodyParsing extends PlayBodyParsing {

  import play.api.libs.iteratee.Execution.Implicits.trampoline

  private val jsonFactory = new JsonFactory()

  /**
   * Contains proper Jackson Factories for different mime types
   * JsonFactory is a default
   */
  lazy val factories = Map(
    "application/json" -> jsonFactory,
    "text/x-yaml" -> new YAMLFactory() // TODO implement workaround for bug in yaml parser
  ).withDefaultValue(jsonFactory)

  /**
   * Returns proper jackson mapper for given mime type
   * @param mimeType  the mimeType of the required mapper
   * @return
   */
  def jacksonMapper(mimeType: String) = {
    assert(mimeType != null)
    val factory = factories(mimeType)
    val mapper = new ObjectMapper(factory)
    mapper.registerModule(DefaultScalaModule)
    mapper
  }

  /**
   * Parser factory for any type
   * @param mimeType name of the parser
   * @param errorMsg error message to return if an input cannot be parsed
   * @param maxLength the maximal length of the content
   * @param tag the ClassTag to use at runtime
   * @tparam T the type of the input the parser should be created for
   * @return BodyParser for the type T
   */
  def anyParser[T](mimeType: String, errorMsg: String, maxLength: Int = parse.DefaultMaxTextLength)
                  (implicit tag: ClassTag[T]): BodyParser[T] =
    tolerantBodyParser[T](mimeType, maxLength, errorMsg) { (request, bytes) =>
      jacksonMapper(mimeType).readValue(bytes, tag.runtimeClass.asInstanceOf[Class[T]])
    }

  /**
   * Converts parsing errors to Writeable
   */
  def parsingErrors2Writable(mimeType: String): Writeable[Seq[ParsingError]] =
    Writeable(parsingErrors2Bytes(mimeType), Some(mimeType))

  /**
   * Converts anything of type Either[Throwable, T] to Writeable
   */
  def anyToWritable[T](mimeType: String): Writeable[Either[Throwable, T]] =
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
}


trait PlayBodyParsing extends BodyParsers {
  val logger = Logger.logger

  def defaultErrorMapping: PartialFunction[Throwable, Status] = {
    case _: IllegalArgumentException => Status(BAD_REQUEST)
    case _: IndexOutOfBoundsException => Status(NOT_FOUND)
    case _ => Status(INTERNAL_SERVER_ERROR)
  }
  /**
   * This is private in play codebase. Copy-pasted it.
   */
  def tolerantBodyParser[A](name: String, maxLength: Long, errorMessage: String)(parser: (RequestHeader, Array[Byte]) => A): BodyParser[A] =
    BodyParser(name + ", maxLength=" + maxLength) { request =>
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
