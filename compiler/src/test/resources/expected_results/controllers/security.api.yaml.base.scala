package security.api.yaml

import play.api.mvc.{Action, Controller, Results}
import play.api.http._
import Results.Status
import de.zalando.play.controllers.{PlayBodyParsing, ParsingError, ResultWrapper}
import PlayBodyParsing._
import scala.util._
// import PickAvailableWriteable._ // if you want play to pick the first available writable
import de.zalando.play.controllers.ArrayWrapper

import de.zalando.play.controllers.PlayPathBindables



trait SecurityApiYamlBase extends Controller with PlayBodyParsing  with SecurityApiYamlSecurity {
    sealed trait getPetsByIdType[Result] extends ResultWrapper[Result]
    case class getPetsById200(result: Seq[Pet])(implicit val writers: List[Writeable[Seq[Pet]]]) extends getPetsByIdType[Seq[Pet]] { val statusCode = 200 }
    

    private type getPetsByIdActionRequestType       = (PetsIdGetId)
    private type getPetsByIdActionType[T]            = getPetsByIdActionRequestType => getPetsByIdType[T]


    val getPetsByIdActionConstructor  = new getPetsByIdSecureAction("user")
    def getPetsByIdAction[T] = (f: getPetsByIdActionType[T]) => (id: PetsIdGetId) => getPetsByIdActionConstructor { request =>
        val providedTypes = Seq[String]("application/json", "text/html")

        negotiateContent(request.acceptedTypes, providedTypes).map { getPetsByIdResponseMimeType =>

            

                val result =
                        new PetsIdGetValidator(id).errors match {
                            case e if e.isEmpty => processValidgetPetsByIdRequest(f)((id))(getPetsByIdResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getPetsByIdResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetPetsByIdRequest[T](f: getPetsByIdActionType[T])(request: getPetsByIdActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    case object EmptyReturn extends ResultWrapper[Results.EmptyContent]     { val statusCode = 204; val result = Results.EmptyContent(); val writers = List(new DefaultWriteables{}.writeableOf_EmptyContent); override def toResult(mimeType: String): Option[play.api.mvc.Result] = Some(Results.NoContent) }
    case object NotImplementedYet extends ResultWrapper[Results.EmptyContent]  with getPetsByIdType[Results.EmptyContent] { val statusCode = 501; val result = Results.EmptyContent(); val writers = List(new DefaultWriteables{}.writeableOf_EmptyContent); override def toResult(mimeType: String): Option[play.api.mvc.Result] = Some(Results.NotImplemented) }
}
