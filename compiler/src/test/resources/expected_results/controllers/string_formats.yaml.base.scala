package string_formats.yaml

import play.api.mvc.{Action, Controller, Results}
import play.api.http._
import Results.Status

import de.zalando.play.controllers.{PlayBodyParsing, ParsingError, ResultWrapper, ResponseWriters}
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
    sealed trait getType[ResultT] extends ResultWrapper[ResultT]
    case class get200(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends getType[Null] { val statusCode = 200 }
    

    private type getActionRequestType       = (BinaryString, GetBase64, GetDate, GetDate_time)
    private type getActionType[T]            = getActionRequestType => getType[T]

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
    def getAction[T] = (f: getActionType[T]) => (base64: GetBase64, date: GetDate, date_time: GetDate_time) => getActionConstructor(getParser(Seq[String]())) { request =>
        val providedTypes = Seq[String]("application/json", "application/yaml")

        negotiateContent(request.acceptedTypes, providedTypes).map { getResponseMimeType =>

            val petId = request.body
            
            

                val result =
                        new GetValidator(petId, base64, date, date_time).errors match {
                            case e if e.isEmpty => processValidgetRequest(f)((petId, base64, date, date_time))(getResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetRequest[T](f: getActionType[T])(request: getActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    case object EmptyReturn extends ResultWrapper[Results.EmptyContent]     { val statusCode = 204; val result = Results.EmptyContent(); val writer = (x: String) => Some(new DefaultWriteables{}.writeableOf_EmptyContent); override def toResult(mimeType: String): Option[play.api.mvc.Result] = Some(Results.NoContent) }
    case object NotImplementedYet extends ResultWrapper[Results.EmptyContent]  with getType[Results.EmptyContent] { val statusCode = 501; val result = Results.EmptyContent(); val writer = (x: String) => Some(new DefaultWriteables{}.writeableOf_EmptyContent); override def toResult(mimeType: String): Option[play.api.mvc.Result] = Some(Results.NotImplemented) }
}
