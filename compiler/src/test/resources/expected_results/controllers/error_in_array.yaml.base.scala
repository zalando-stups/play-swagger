package error_in_array.yaml
import play.api.mvc.{Action, Controller, Results}
import play.api.http.Writeable
import Results.Status
import de.zalando.play.controllers.{PlayBodyParsing, ParsingError}
import PlayBodyParsing._
import scala.util._
import de.zalando.play.controllers.ArrayWrapper
trait Error_in_arrayYamlBase extends Controller with PlayBodyParsing {
    private val getschemaModelResponseMimeType    = "application/json"
    private val getschemaModelActionSuccessStatus = Status(200)

    private type getschemaModelActionRequestType       = (ModelSchemaRoot)
    private type getschemaModelActionResultType        = ModelSchemaRoot
    private type getschemaModelActionType              = getschemaModelActionRequestType => Try[getschemaModelActionResultType]

    private val errorToStatusgetschemaModel: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    


    private def getschemaModelParser(maxLength: Int = parse.DefaultMaxTextLength) = anyParser[ModelSchemaRoot]("application/json", "Invalid ModelSchemaRoot", maxLength)
    


    def getschemaModelAction = (f: getschemaModelActionType) => Action (getschemaModelParser()){ request =>

        val root = request.body
        val result = 

            new SchemaModelGetValidator(root).errors match {
                    case e if e.isEmpty => processValidgetschemaModelRequest(f)((root))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getschemaModelResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValidgetschemaModelRequest(f: getschemaModelActionType)(request: getschemaModelActionRequestType) = {
        implicit val getschemaModelWritableJson = anyToWritable[getschemaModelActionResultType](getschemaModelResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetschemaModel orElse defaultErrorMapping)(error)
            case Success(result) => getschemaModelActionSuccessStatus(result)
        }
        status
    }
    }
