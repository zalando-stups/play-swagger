package heroku.petstore.api.yaml
import play.api.mvc.{Action, Controller, Results}
import play.api.http.Writeable
import Results.Status
import de.zalando.play.controllers.{PlayBodyParsing, ParsingError}
import PlayBodyParsing._
import scala.util._
import de.zalando.play.controllers.PlayPathBindables
trait HerokuPetstoreApiYamlBase extends Controller with PlayBodyParsing {
    private val getResponseMimeType    = "application/json"
    private val getActionSuccessStatus = Status(200)

    private type getActionRequestType       = (PetBirthday)
    private type getActionResultType        = Seq[Pet]
    private type getActionType              = getActionRequestType => Try[getActionResultType]

    private val errorToStatusget: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    


    


    def getAction = (f: getActionType) => (limit: PetBirthday) => Action { 

        val result = 

            new GetValidator(limit).errors match {
                    case e if e.isEmpty => processValidgetRequest(f)((limit))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getResponseMimeType)
                        BadRequest(l)
                }

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
    private val putResponseMimeType    = "application/json"
    private val putActionSuccessStatus = Status(200)

    private type putActionRequestType       = (Pet)
    private type putActionResultType        = Null
    private type putActionType              = putActionRequestType => Try[putActionResultType]

    private val errorToStatusput: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    


    private def putParser(maxLength: Int = parse.DefaultMaxTextLength) = anyParser[Pet]("application/json", "Invalid Pet", maxLength)
    


    def putAction = (f: putActionType) => Action (putParser()){ request =>

        val pet = request.body
        val result = 

            new PutValidator(pet).errors match {
                    case e if e.isEmpty => processValidputRequest(f)((pet))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(putResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValidputRequest(f: putActionType)(request: putActionRequestType) = {
        implicit val putWritableJson = anyToWritable[putActionResultType](putResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusput orElse defaultErrorMapping)(error)
            case Success(result) => putActionSuccessStatus(result)
        }
        status
    }
    private val postResponseMimeType    = "application/json"
    private val postActionSuccessStatus = Status(200)

    private type postActionRequestType       = (Pet)
    private type postActionResultType        = Null
    private type postActionType              = postActionRequestType => Try[postActionResultType]

    private val errorToStatuspost: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    


    private def postParser(maxLength: Int = parse.DefaultMaxTextLength) = anyParser[Pet]("application/json", "Invalid Pet", maxLength)
    


    def postAction = (f: postActionType) => Action (postParser()){ request =>

        val pet = request.body
        val result = 

            new PostValidator(pet).errors match {
                    case e if e.isEmpty => processValidpostRequest(f)((pet))
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
    private val getbyPetIdResponseMimeType    = "application/json"
    private val getbyPetIdActionSuccessStatus = Status(200)

    private type getbyPetIdActionRequestType       = (String)
    private type getbyPetIdActionResultType        = Null
    private type getbyPetIdActionType              = getbyPetIdActionRequestType => Try[getbyPetIdActionResultType]

    private val errorToStatusgetbyPetId: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    


    


    def getbyPetIdAction = (f: getbyPetIdActionType) => (petId: String) => Action { 

        val result = 

            new PetIdGetValidator(petId).errors match {
                    case e if e.isEmpty => processValidgetbyPetIdRequest(f)((petId))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getbyPetIdResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValidgetbyPetIdRequest(f: getbyPetIdActionType)(request: getbyPetIdActionRequestType) = {
        implicit val getbyPetIdWritableJson = anyToWritable[getbyPetIdActionResultType](getbyPetIdResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetbyPetId orElse defaultErrorMapping)(error)
            case Success(result) => getbyPetIdActionSuccessStatus(result)
        }
        status
    }
    }
