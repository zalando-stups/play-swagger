package minimal.api.yaml
import play.api.mvc.{Action, Controller, Results}
import play.api.http.Writeable
import Results.Status
import de.zalando.play.controllers.{PlayBodyParsing, ParsingError}
import PlayBodyParsing._
import scala.util._

trait DashboardBase extends Controller with PlayBodyParsing {
        private val indexResponseMimeType    = "application/json"
        private val indexActionSuccessStatus = Status(200)

        private type indexActionRequestType       = (Unit)
        private type indexActionResultType        = Null
        private type indexActionType              = indexActionRequestType => Try[indexActionResultType]

        private val errorToStatusindex: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

        


        


        def indexAction = (f: indexActionType) => Action { 

            val result = processValidindexRequest(f)()

                result
        }

        private def processValidindexRequest(f: indexActionType)(request: indexActionRequestType) = {
            implicit val indexWritableJson = anyToWritable[indexActionResultType](indexResponseMimeType)
            val callerResult = f(request)
            val status = callerResult match {
                case Failure(error) => (errorToStatusindex orElse defaultErrorMapping)(error)
                case Success(result) => indexActionSuccessStatus(result)
            }
            status
        }
        }


    