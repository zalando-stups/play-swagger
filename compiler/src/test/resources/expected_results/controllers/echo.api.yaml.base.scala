package echo.api.yaml
import play.api.mvc.{Action, Controller, Results}
import play.api.http.Writeable
import Results.Status
import de.zalando.play.controllers.{PlayBodyParsing, ParsingError}
import PlayBodyParsing._
import scala.util._
trait EchoApiYamlBase extends Controller with PlayBodyParsing {
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
    private val postResponseMimeType    = "application/json"
    private val postActionSuccessStatus = Status(200)

    private type postActionRequestType       = (PostName, PostName)
    private type postActionResultType        = PostResponses200
    private type postActionType              = postActionRequestType => Try[postActionResultType]

    private val errorToStatuspost: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    


    


    def postAction = (f: postActionType) => (name: PostName, year: PostName) => Action { 

        val result = 

            new PostValidator(name, year).errors match {
                    case e if e.isEmpty => processValidpostRequest(f)((name, year))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(postResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValidpostRequest(f: postActionType)(request: postActionRequestType) = {
        implicit val postWritableJson = anyToWritable[postActionResultType](postResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatuspost orElse defaultErrorMapping)(error)
            case Success(result) => postActionSuccessStatus(result)
        }
        status
    }
    private val gettest_pathByIdResponseMimeType    = "application/json"
    private val gettest_pathByIdActionSuccessStatus = Status(200)

    private type gettest_pathByIdActionRequestType       = (String)
    private type gettest_pathByIdActionResultType        = Null
    private type gettest_pathByIdActionType              = gettest_pathByIdActionRequestType => Try[gettest_pathByIdActionResultType]

    private val errorToStatusgettest_pathById: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    


    


    def gettest_pathByIdAction = (f: gettest_pathByIdActionType) => (id: String) => Action { 

        val result = 

            new `Test-pathIdGetValidator`(id).errors match {
                    case e if e.isEmpty => processValidgettest_pathByIdRequest(f)((id))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(gettest_pathByIdResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValidgettest_pathByIdRequest(f: gettest_pathByIdActionType)(request: gettest_pathByIdActionRequestType) = {
        implicit val gettest_pathByIdWritableJson = anyToWritable[gettest_pathByIdActionResultType](gettest_pathByIdResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgettest_pathById orElse defaultErrorMapping)(error)
            case Success(result) => gettest_pathByIdActionSuccessStatus(result)
        }
        status
    }
    }
