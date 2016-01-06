package security.api.yaml
import play.api.mvc.{Action, Controller, Results}
import play.api.http.Writeable
import Results.Status
import de.zalando.play.controllers.{PlayBodyParsing, ParsingError}
import PlayBodyParsing._
import scala.util._
import de.zalando.play.controllers.PlayPathBindables
trait SecurityApiYamlBase extends Controller with PlayBodyParsing {
        private val getPetsByIdResponseMimeType    = "application/json"
        private val getPetsByIdActionSuccessStatus = Status(200)

        private type getPetsByIdActionRequestType       = (PetsIdGetId)
        private type getPetsByIdActionResultType        = Option[PetsIdGetResponses200Opt]
        private type getPetsByIdActionType              = getPetsByIdActionRequestType => Try[getPetsByIdActionResultType]

        private val errorToStatusgetPetsById: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

        


        


        def getPetsByIdAction = (f: getPetsByIdActionType) => (id: PetsIdGetId) => Action { 

            val result = 

                new PetsIdGetValidator(id).errors match {
                        case e if e.isEmpty => processValidgetPetsByIdRequest(f)((id))
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getPetsByIdResponseMimeType)
                            BadRequest(l)
                    }

                result
        }

        private def processValidgetPetsByIdRequest(f: getPetsByIdActionType)(request: getPetsByIdActionRequestType) = {
            implicit val getPetsByIdWritableJson = anyToWritable[getPetsByIdActionResultType](getPetsByIdResponseMimeType)
            val callerResult = f(request)
            val status = callerResult match {
                case Failure(error) => (errorToStatusgetPetsById orElse defaultErrorMapping)(error)
                case Success(result) => getPetsByIdActionSuccessStatus(result)
            }
            status
        }
        }


    