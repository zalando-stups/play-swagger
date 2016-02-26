package security.api.yaml

import play.api.mvc.{Action, Controller, Results}
import play.api.http.Writeable
import Results.Status
import de.zalando.play.controllers.{PlayBodyParsing, ParsingError}
import PlayBodyParsing._
import scala.util._
import de.zalando.play.controllers.ArrayWrapper

import de.zalando.play.controllers.PlayPathBindables




trait SecurityApiYamlBase extends Controller with PlayBodyParsing {
    private type getPetsByIdActionRequestType       = (PetsIdGetId)
    private type getPetsByIdActionType              = getPetsByIdActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetPetsById: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    def getPetsByIdAction = (f: getPetsByIdActionType) => (id: PetsIdGetId) => Action { request =>
        val providedTypes = Seq[String]("application/json", "text/html")
        negotiateContent(request.acceptedTypes, providedTypes).map { getPetsByIdResponseMimeType =>
            val possibleWriters = Map(
                    200 -> anyToWritable[Seq[Pet]]
            ).withDefaultValue(anyToWritable[ErrorModel])
            

                val result =
                        new PetsIdGetValidator(id).errors match {
                            case e if e.isEmpty => processValidgetPetsByIdRequest(f)((id))(possibleWriters, getPetsByIdResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getPetsByIdResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(BadRequest("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetPetsByIdRequest[T <: Any](f: getPetsByIdActionType)(request: getPetsByIdActionRequestType)(writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetPetsById orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val getPetsByIdWritableJson = writer(mimeType)
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
