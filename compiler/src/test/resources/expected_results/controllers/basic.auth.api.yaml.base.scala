package basic.auth.api.yaml

import play.api.mvc.{Action, Controller, Results}
import play.api.http._
import Results.Status
import de.zalando.play.controllers.{PlayBodyParsing, ParsingError, ResultWrapper}
import PlayBodyParsing._
import scala.util._
// import PickAvailableWriteable._ // if you want play to pick the first available writable



trait BasicAuthApiYamlBase extends Controller with PlayBodyParsing  with BasicAuthApiYamlSecurity {
    sealed trait getType[Result] extends ResultWrapper[Result]
    case class get200(result: Null)(implicit val writers: List[Writeable[Null]]) extends getType[Null] { val statusCode = 200 }
    

    private type getActionRequestType       = (Unit)
    private type getActionType[T]            = getActionRequestType => getType[T]


    val getActionConstructor  = getSecureAction
    def getAction[T] = (f: getActionType[T]) => getActionConstructor { request =>
        val providedTypes = Seq[String]()

        negotiateContent(request.acceptedTypes, providedTypes).map { getResponseMimeType =>

            

                val result = processValidgetRequest(f)()(getResponseMimeType)
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetRequest[T](f: getActionType[T])(request: getActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    case object EmptyReturn extends ResultWrapper[Results.EmptyContent]     { val statusCode = 204; val result = Results.EmptyContent(); val writers = List(new DefaultWriteables{}.writeableOf_EmptyContent); override def toResult(mimeType: String): Option[play.api.mvc.Result] = Some(Results.NoContent) }
    case object NotImplementedYet extends ResultWrapper[Results.EmptyContent]  with getType[Results.EmptyContent] { val statusCode = 501; val result = Results.EmptyContent(); val writers = List(new DefaultWriteables{}.writeableOf_EmptyContent); override def toResult(mimeType: String): Option[play.api.mvc.Result] = Some(Results.NotImplemented) }
}
