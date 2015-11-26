package echo.api.yaml
import play.api.mvc.{Action, Controller, Results}
import Results.Status
import de.zalando.play.controllers.PlayBodyParsing
import PlayBodyParsing._
import scala.util._

object pathsBase {
    import paths.PostName
    trait EchoApiYamlBase extends Controller with PlayBodyParsing {
        private val getResponseMimeType    = "application/json"
        private val getActionSuccessStatus = Status(200)

        private type getActionRequestType       = ()
        private type getActionResultType        = Null
        private type getActionType              = getActionRequestType => Try[getActionResultType]

        private val errorToStatusget: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]
        def getAction = (f: getActionType) => Action { 
            implicit val marshaller = parsingErrors2Writable(getResponseMimeType)
            val result = processValidgetRequest(f)
                result
        }

        private def processValidgetRequest(f: getActionType)(request: getActionRequestType) = {
            val callerResult = f(request)
            val status = callerResult match {
                case Failure(error) => (errorToStatusget orElse defaultErrorMapping)(error)
                case Success(result) => getActionSuccessStatus
            }
            implicit val getWritableJson = anyToWritable[getActionResultType](getResponseMimeType)
            status(callerResult)
        }
        private val postResponseMimeType    = "application/json"
        private val postActionSuccessStatus = Status(200)

        private type postActionRequestType       = (PostName, PostName)
        private type postActionResultType        = Null
        private type postActionType              = postActionRequestType => Try[postActionResultType]

        private val errorToStatuspost: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]
        def postAction = (f: postActionType) => (name: PostName, year: PostName) => Action { 
            implicit val marshaller = parsingErrors2Writable(postResponseMimeType)
            val result = 
                new PostValidator(name, year).errors match {
                        case e if e.isEmpty => processValidpostRequest(f)
                        case l => l.map(BadRequest(_))
                    }
                result
        }

        private def processValidpostRequest(f: postActionType)(request: postActionRequestType) = {
            val callerResult = f(request)
            val status = callerResult match {
                case Failure(error) => (errorToStatuspost orElse defaultErrorMapping)(error)
                case Success(result) => postActionSuccessStatus
            }
            implicit val postWritableJson = anyToWritable[postActionResultType](postResponseMimeType)
            status(callerResult)
        }
        }

    }
