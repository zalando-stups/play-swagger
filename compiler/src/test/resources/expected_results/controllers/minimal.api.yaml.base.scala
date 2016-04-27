package admin

import play.api.mvc.{Action, Controller, Results}
import play.api.http._
import Results.Status
import de.zalando.play.controllers.{PlayBodyParsing, ParsingError, ResultWrapper}
import PlayBodyParsing._
import scala.util._
// import PickAvailableWriteable._ // if you want play to pick the first available writable



trait DashboardBase extends Controller with PlayBodyParsing {
    sealed trait indexType[Result] extends ResultWrapper[Result]
    case class index200(result: Null)(implicit val writers: List[Writeable[Null]]) extends indexType[Null] { val statusCode = 200 }
    

    private type indexActionRequestType       = (Unit)
    private type indexActionType[T]            = indexActionRequestType => indexType[T]


    val indexActionConstructor  = Action
    def indexAction[T] = (f: indexActionType[T]) => indexActionConstructor { request =>
        val providedTypes = Seq[String]()

        negotiateContent(request.acceptedTypes, providedTypes).map { indexResponseMimeType =>

            

                val result = processValidindexRequest(f)()(indexResponseMimeType)
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidindexRequest[T](f: indexActionType[T])(request: indexActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    case object EmptyReturn extends ResultWrapper[Results.EmptyContent]     { val statusCode = 204; val result = Results.EmptyContent(); val writers = List(new DefaultWriteables{}.writeableOf_EmptyContent); override def toResult(mimeType: String): Option[play.api.mvc.Result] = Some(Results.NoContent) }
    case object NotImplementedYet extends ResultWrapper[Results.EmptyContent]  with indexType[Results.EmptyContent] { val statusCode = 501; val result = Results.EmptyContent(); val writers = List(new DefaultWriteables{}.writeableOf_EmptyContent); override def toResult(mimeType: String): Option[play.api.mvc.Result] = Some(Results.NotImplemented) }
}
