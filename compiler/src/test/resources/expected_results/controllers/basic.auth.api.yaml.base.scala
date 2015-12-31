package basic.auth.api.yaml
import play.api.mvc.{Action, Controller, Results}
import play.api.http.Writeable
import Results.Status
import de.zalando.play.controllers.{PlayBodyParsing, ParsingError}
import PlayBodyParsing._
import scala.util._

trait BasicAuthApiYamlBase extends Controller with PlayBodyParsing {
        private val getResponseMimeType    = "application/json"
        private val getActionSuccessStatus = Status(200)

        private type getActionRequestType       = (Unit)
        private type getActionResultType        = Null
        private type getActionType              = getActionRequestType => Try[getActionResultType]

        private val errorToStatusget: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

        


        


        def getAction = (f: getActionType) => Action { 

            val result = processValidgetRequest(f)()

                result
        }

        private def processValidgetRequest(f: getActionType)(request: getActionRequestType) = {
            implicit val getWritableJson = anyToWritable[getActionResultType](getResponseMimeType)
            val callerResult = f(request)
            val status = callerResult match {
                case Failure(error) => (errorToStatusget orElse defaultErrorMapping)(error)
                case Success(result) => getActionSuccessStatus(result)
            }
            status
        }
        }


    