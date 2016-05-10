package string_formats.yaml

import play.api.mvc.{Action, Controller, Results}
import play.api.http._
import Results.Status
import de.zalando.play.controllers.{PlayBodyParsing, ParsingError, ResponseWriters}
import PlayBodyParsing._
import scala.util._
import de.zalando.play.controllers.Base64String
import Base64String._
import de.zalando.play.controllers.BinaryString
import BinaryString._
import org.joda.time.DateTime
import org.joda.time.LocalDate

import de.zalando.play.controllers.PlayPathBindables




trait String_formatsYamlBase extends Controller with PlayBodyParsing {
    private type getActionRequestType       = (BinaryString, GetBase64, GetDate, GetDate_time)
    private type getActionType              = getActionRequestType => Try[(Int, Any)]

    private val errorToStatusget: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

        private def getParser(acceptedTypes: Seq[String], maxLength: Int = parse.DefaultMaxTextLength) = {
            def bodyMimeType: Option[MediaType] => String = mediaType => {
                val requestType = mediaType.toSeq.map {
                    case m: MediaRange => m
                    case MediaType(a,b,c) => new MediaRange(a,b,c,None,Nil)
                }
                negotiateContent(requestType, acceptedTypes).orElse(acceptedTypes.headOption).getOrElse("application/json")
            }
            
            import de.zalando.play.controllers.WrappedBodyParsers
            
            val customParsers = WrappedBodyParsers.anyParser[BinaryString]
            anyParser[BinaryString](bodyMimeType, customParsers, "Invalid BinaryString", maxLength)
        }

    val getActionConstructor  = Action
    def getAction = (f: getActionType) => (base64: GetBase64, date: GetDate, date_time: GetDate_time) => getActionConstructor(getParser(Seq[String]())) { request =>
        val providedTypes = Seq[String]("application/json", "application/yaml")

        negotiateContent(request.acceptedTypes, providedTypes).map { getResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[Null]
            )
            val petId = request.body
            
            

                val result =
                        new GetValidator(petId, base64, date, date_time).errors match {
                            case e if e.isEmpty => processValidgetRequest(f)((petId, base64, date, date_time))(possibleWriters, getResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetRequest[T <: Any](f: getActionType)(request: getActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusget orElse defaultErrorMapping)(error)
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
