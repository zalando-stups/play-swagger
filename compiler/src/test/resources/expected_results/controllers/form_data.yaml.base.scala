package form_data.yaml

import play.api.mvc.{Action, Controller, Results}
import play.api.http._
import Results.Status
import de.zalando.play.controllers.{PlayBodyParsing, ParsingError, ResponseWriters}
import PlayBodyParsing._
import scala.util._
import java.io.File
import scala.math.BigInt

import de.zalando.play.controllers.PlayPathBindables




trait Form_dataYamlBase extends Controller with PlayBodyParsing {
    private type postmultipartActionRequestType       = (String, BothPostYear, MultipartPostAvatar)
    private type postmultipartActionType              = postmultipartActionRequestType => Try[(Int, Any)]

    private val errorToStatuspostmultipart: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val postmultipartActionConstructor  = Action
    def postmultipartAction = (f: postmultipartActionType) => postmultipartActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { postmultipartResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[MultipartPostResponses200]
            )
            
            val eitherFormParameters = FormDataParser.postmultipartParseForm(request)
            eitherFormParameters match {
                case Left(problem: Seq[String]) =>
                    val msg = problem.mkString("\n")
                    BadRequest(msg)

                case Right((name, year, avatar)) =>
            

                val result =
                        new MultipartPostValidator(name, year, avatar).errors match {
                            case e if e.isEmpty => processValidpostmultipartRequest(f)((name, year, avatar))(possibleWriters, postmultipartResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(postmultipartResponseMimeType)
                                BadRequest(l)
                        }
                result
            
            }
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidpostmultipartRequest[T <: Any](f: postmultipartActionType)(request: postmultipartActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatuspostmultipart orElse defaultErrorMapping)(error)(error.getMessage)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        case Success(other) =>
            implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
            Status(500)(new IllegalStateException(s"Expected pair (responseCode, response) from the controller, but was: other"))
        }
        status
    }
    private type posturl_encodedActionRequestType       = (String, BothPostYear, File)
    private type posturl_encodedActionType              = posturl_encodedActionRequestType => Try[(Int, Any)]

    private val errorToStatusposturl_encoded: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val posturl_encodedActionConstructor  = Action
    def posturl_encodedAction = (f: posturl_encodedActionType) => posturl_encodedActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { posturl_encodedResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[MultipartPostResponses200]
            )
            
            val eitherFormParameters = FormDataParser.posturl_encodedParseForm(request)
            eitherFormParameters match {
                case Left(problem: Seq[String]) =>
                    val msg = problem.mkString("\n")
                    BadRequest(msg)

                case Right((name, year, avatar)) =>
            

                val result =
                        new Url_encodedPostValidator(name, year, avatar).errors match {
                            case e if e.isEmpty => processValidposturl_encodedRequest(f)((name, year, avatar))(possibleWriters, posturl_encodedResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(posturl_encodedResponseMimeType)
                                BadRequest(l)
                        }
                result
            
            }
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidposturl_encodedRequest[T <: Any](f: posturl_encodedActionType)(request: posturl_encodedActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusposturl_encoded orElse defaultErrorMapping)(error)(error.getMessage)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        case Success(other) =>
            implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
            Status(500)(new IllegalStateException(s"Expected pair (responseCode, response) from the controller, but was: other"))
        }
        status
    }
    private type postbothActionRequestType       = (String, BothPostYear, MultipartPostAvatar, File)
    private type postbothActionType              = postbothActionRequestType => Try[(Int, Any)]

    private val errorToStatuspostboth: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val postbothActionConstructor  = Action
    def postbothAction = (f: postbothActionType) => postbothActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { postbothResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[BothPostResponses200]
            )
            
            val eitherFormParameters = FormDataParser.postbothParseForm(request)
            eitherFormParameters match {
                case Left(problem: Seq[String]) =>
                    val msg = problem.mkString("\n")
                    BadRequest(msg)

                case Right((name, year, avatar, ringtone)) =>
            

                val result =
                        new BothPostValidator(name, year, avatar, ringtone).errors match {
                            case e if e.isEmpty => processValidpostbothRequest(f)((name, year, avatar, ringtone))(possibleWriters, postbothResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(postbothResponseMimeType)
                                BadRequest(l)
                        }
                result
            
            }
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidpostbothRequest[T <: Any](f: postbothActionType)(request: postbothActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatuspostboth orElse defaultErrorMapping)(error)(error.getMessage)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        case Success(other) =>
            implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
            Status(500)(new IllegalStateException(s"Expected pair (responseCode, response) from the controller, but was: other"))
        }
        status
    }
}
