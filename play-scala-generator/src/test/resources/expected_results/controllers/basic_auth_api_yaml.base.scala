package basic.auth.api.yaml

import scala.language.existentials
import play.api.mvc._
import play.api.http._
import de.zalando.play.controllers._
import Results.Status
import PlayBodyParsing._

import scala.util._



import de.zalando.play.controllers.ResponseWriters




trait BasicAuthApiYamlBase extends Controller with PlayBodyParsing  with BasicAuthApiYamlSecurity {
    sealed trait GetType[T] extends ResultWrapper[T]
    
    case object Get200 extends EmptyReturn(200)
    

    private type getActionRequestType       = (Unit)
    private type getActionType[T]            = getActionRequestType => GetType[T] forSome { type T }


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
    abstract class EmptyReturn(override val statusCode: Int = 204) extends ResultWrapper[Results.EmptyContent]  with GetType[Results.EmptyContent] { val result = Results.EmptyContent(); val writer = (x: String) => Some(new DefaultWriteables{}.writeableOf_EmptyContent); override def toResult(mimeType: String): Option[play.api.mvc.Result] = Some(Results.NoContent) }
    case object NotImplementedYet extends ResultWrapper[Results.EmptyContent]  with GetType[Results.EmptyContent] { val statusCode = 501; val result = Results.EmptyContent(); val writer = (x: String) => Some(new DefaultWriteables{}.writeableOf_EmptyContent); override def toResult(mimeType: String): Option[play.api.mvc.Result] = Some(Results.NotImplemented) }
}
