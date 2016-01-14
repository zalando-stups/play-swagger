package simple.petstore.api.yaml
import play.api.mvc.{Action, Controller, Results}
import play.api.http.Writeable
import Results.Status
import de.zalando.play.controllers.{PlayBodyParsing, ParsingError}
import PlayBodyParsing._
import scala.util._
import de.zalando.play.controllers.ArrayWrapper
import de.zalando.play.controllers.PlayPathBindables
trait SimplePetstoreApiYamlBase extends Controller with PlayBodyParsing {
    private val addPetResponseMimeType    = "application/json"
    private val addPetActionSuccessStatus = Status(200)

    private type addPetActionRequestType       = (NewPet)
    private type addPetActionResultType        = ErrorModel
    private type addPetActionType              = addPetActionRequestType => Try[addPetActionResultType]

    private val errorToStatusaddPet: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    


    private def addPetParser(maxLength: Int = parse.DefaultMaxTextLength) = anyParser[NewPet]("application/json", "Invalid NewPet", maxLength)
    


    def addPetAction = (f: addPetActionType) => Action(addPetParser()) { request =>

        val pet = request.body
        val result = 

            new PetsPostValidator(pet).errors match {
                    case e if e.isEmpty => processValidaddPetRequest(f)((pet))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(addPetResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValidaddPetRequest(f: addPetActionType)(request: addPetActionRequestType) = {
        implicit val addPetWritableJson = anyToWritable[addPetActionResultType](addPetResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusaddPet orElse defaultErrorMapping)(error)
            case Success(result) => addPetActionSuccessStatus(result)
        }
        status
    }
    }
trait DashboardBase extends Controller with PlayBodyParsing {
    private val methodLevelResponseMimeType    = "application/json"
    private val methodLevelActionSuccessStatus = Status(200)

    private type methodLevelActionRequestType       = (PetsGetTags, PetsGetLimit)
    private type methodLevelActionResultType        = Seq[Pet]
    private type methodLevelActionType              = methodLevelActionRequestType => Try[methodLevelActionResultType]

    private val errorToStatusmethodLevel: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    


    


    def methodLevelAction = (f: methodLevelActionType) => (tags: PetsGetTags, limit: PetsGetLimit) => Action { 

        val result = 

            new PetsGetValidator(tags, limit).errors match {
                    case e if e.isEmpty => processValidmethodLevelRequest(f)((tags, limit))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(methodLevelResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValidmethodLevelRequest(f: methodLevelActionType)(request: methodLevelActionRequestType) = {
        implicit val methodLevelWritableJson = anyToWritable[methodLevelActionResultType](methodLevelResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusmethodLevel orElse defaultErrorMapping)(error)
            case Success(result) => methodLevelActionSuccessStatus(result)
        }
        status
    }
    private val pathLevelGetResponseMimeType    = "application/json"
    private val pathLevelGetActionSuccessStatus = Status(200)

    private type pathLevelGetActionRequestType       = (Long)
    private type pathLevelGetActionResultType        = ErrorModel
    private type pathLevelGetActionType              = pathLevelGetActionRequestType => Try[pathLevelGetActionResultType]

    private val errorToStatuspathLevelGet: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    


    


    def pathLevelGetAction = (f: pathLevelGetActionType) => (id: Long) => Action { 

        val result = 

            new PetsIdGetValidator(id).errors match {
                    case e if e.isEmpty => processValidpathLevelGetRequest(f)((id))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(pathLevelGetResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValidpathLevelGetRequest(f: pathLevelGetActionType)(request: pathLevelGetActionRequestType) = {
        implicit val pathLevelGetWritableJson = anyToWritable[pathLevelGetActionResultType](pathLevelGetResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatuspathLevelGet orElse defaultErrorMapping)(error)
            case Success(result) => pathLevelGetActionSuccessStatus(result)
        }
        status
    }
    private val pathLevelDeleteResponseMimeType    = "application/json"
    private val pathLevelDeleteActionSuccessStatus = Status(200)

    private type pathLevelDeleteActionRequestType       = (Long)
    private type pathLevelDeleteActionResultType        = Null
    private type pathLevelDeleteActionType              = pathLevelDeleteActionRequestType => Try[pathLevelDeleteActionResultType]

    private val errorToStatuspathLevelDelete: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    


    


    def pathLevelDeleteAction = (f: pathLevelDeleteActionType) => (id: Long) => Action { 

        val result = 

            new PetsIdDeleteValidator(id).errors match {
                    case e if e.isEmpty => processValidpathLevelDeleteRequest(f)((id))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(pathLevelDeleteResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValidpathLevelDeleteRequest(f: pathLevelDeleteActionType)(request: pathLevelDeleteActionRequestType) = {
        implicit val pathLevelDeleteWritableJson = anyToWritable[pathLevelDeleteActionResultType](pathLevelDeleteResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatuspathLevelDelete orElse defaultErrorMapping)(error)
            case Success(result) => pathLevelDeleteActionSuccessStatus(result)
        }
        status
    }
    }
