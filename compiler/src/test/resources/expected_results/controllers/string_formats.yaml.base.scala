package string_formats.yaml

import play.api.mvc.{Action, Controller, Results}
import play.api.http.Writeable
import Results.Status
import de.zalando.play.controllers.{PlayBodyParsing, ParsingError}
import PlayBodyParsing._
import scala.util._
import de.zalando.play.controllers.Base64String
import Base64String._
import de.zalando.play.controllers.BinaryString
import BinaryString._
import org.joda.time.DateTime
import org.joda.time.DateMidnight

import de.zalando.play.controllers.PlayPathBindables

import PlayPathBindables.queryBindableBase64String

import PlayPathBindables.queryBindableDateTime

import PlayPathBindables.queryBindableDateMidnight




trait String_formatsYamlBase extends Controller with PlayBodyParsing {
    private type getActionRequestType       = (BinaryString, GetBase64, GetDate, GetDate_time)
    private type getActionType              = getActionRequestType => Try[(Int, Any)]

    private val errorToStatusget: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

        private def getParser(maxLength: Int = parse.DefaultMaxTextLength) = anyParser[BinaryString]("application/json", "Invalid BinaryString", maxLength)

    def getAction = (f: getActionType) => (base64: GetBase64, date: GetDate, date_time: GetDate_time) => Action(getParser()) { request =>
        val getResponseMimeType    = "application/json"

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

    }

    private def processValidgetRequest[T <: Any](f: getActionType)(request: getActionRequestType)(writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusget orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val getWritableJson = writer(mimeType)
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
