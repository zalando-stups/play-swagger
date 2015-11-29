package expanded_polymorphism.yaml
import play.api.mvc.{Action, Controller, Results}
import play.api.http.Writeable
import Results.Status
import de.zalando.play.controllers.{PlayBodyParsing, ParsingError}
import PlayBodyParsing._
import scala.util._

object definitionsBase {
    import definitions._
    import definitionsValidator._
    }
object pathsBase {
    import definitions.{Error, NewPet, Pet}
    import paths._
    import pathsValidator._
    trait Expanded_polymorphismYamlBase extends Controller with PlayBodyParsing {
        private val findPetsResponseMimeType    = "application/json"
        private val findPetsActionSuccessStatus = Status(200)

        private type findPetsActionRequestType       = (PetsGetTags, PetsGetLimit)
        private type findPetsActionResultType        = Option[PetsGetResponses200Opt]
        private type findPetsActionType              = findPetsActionRequestType => Try[findPetsActionResultType]

        private val errorToStatusfindPets: PartialFunction[Throwable, Status] = 
        { case _:  => Status()
        } val findPetsAction = (f: findPetsActionType) => (tags: PetsGetTags, limit: PetsGetLimit) => Action { 
            val result = 
                new PetsGetValidator(tags, limit).errors match {
                        case e if e.isEmpty => processValidfindPetsRequest(f)((tags, limit))
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(findPetsResponseMimeType)
                            BadRequest(l)
                    }
                result
        }

        private def processValidfindPetsRequest(f: findPetsActionType)(request: findPetsActionRequestType) = {
            val callerResult = f(request)
            val status = callerResult match {
                case Failure(error) => (errorToStatusfindPets orElse defaultErrorMapping)(error)
                case Success(result) => findPetsActionSuccessStatus
            }
            implicit val findPetsWritableJson = anyToWritable[findPetsActionResultType](findPetsResponseMimeType)
            status
        }
        private val addPetResponseMimeType    = "application/json"
        private val addPetActionSuccessStatus = Status(200)

        private type addPetActionRequestType       = (NewPet)
        private type addPetActionResultType        = Pet
        private type addPetActionType              = addPetActionRequestType => Try[addPetActionResultType]

        private val errorToStatusaddPet: PartialFunction[Throwable, Status] = 
        { case _:  => Status()
        } private def addPetParser(maxLength: Int = parse.DefaultMaxTextLength) = anyParser[addPetActionRequestType]("addPet", "Invalid addPet", maxLength)
        val addPetAction = (f: addPetActionType) => Action (addPetParser()){ request =>
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
            val callerResult = f(request)
            val status = callerResult match {
                case Failure(error) => (errorToStatusaddPet orElse defaultErrorMapping)(error)
                case Success(result) => addPetActionSuccessStatus
            }
            implicit val addPetWritableJson = anyToWritable[addPetActionResultType](addPetResponseMimeType)
            status
        }
        private val findPetByIdResponseMimeType    = "application/json"
        private val findPetByIdActionSuccessStatus = Status(200)

        private type findPetByIdActionRequestType       = (Long)
        private type findPetByIdActionResultType        = Pet
        private type findPetByIdActionType              = findPetByIdActionRequestType => Try[findPetByIdActionResultType]

        private val errorToStatusfindPetById: PartialFunction[Throwable, Status] = 
        { case _:  => Status()
        } val findPetByIdAction = (f: findPetByIdActionType) => (id: Long) => Action { 
            val result = 
                new PetsIdGetValidator(id).errors match {
                        case e if e.isEmpty => processValidfindPetByIdRequest(f)((id))
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(findPetByIdResponseMimeType)
                            BadRequest(l)
                    }
                result
        }

        private def processValidfindPetByIdRequest(f: findPetByIdActionType)(request: findPetByIdActionRequestType) = {
            val callerResult = f(request)
            val status = callerResult match {
                case Failure(error) => (errorToStatusfindPetById orElse defaultErrorMapping)(error)
                case Success(result) => findPetByIdActionSuccessStatus
            }
            implicit val findPetByIdWritableJson = anyToWritable[findPetByIdActionResultType](findPetByIdResponseMimeType)
            status
        }
        private val deletePetResponseMimeType    = "application/json"
        private val deletePetActionSuccessStatus = Status(200)

        private type deletePetActionRequestType       = (Long)
        private type deletePetActionResultType        = Null
        private type deletePetActionType              = deletePetActionRequestType => Try[deletePetActionResultType]

        private val errorToStatusdeletePet: PartialFunction[Throwable, Status] = 
        { case _:  => Status()
        } val deletePetAction = (f: deletePetActionType) => (id: Long) => Action { 
            val result = 
                new PetsIdDeleteValidator(id).errors match {
                        case e if e.isEmpty => processValiddeletePetRequest(f)((id))
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(deletePetResponseMimeType)
                            BadRequest(l)
                    }
                result
        }

        private def processValiddeletePetRequest(f: deletePetActionType)(request: deletePetActionRequestType) = {
            val callerResult = f(request)
            val status = callerResult match {
                case Failure(error) => (errorToStatusdeletePet orElse defaultErrorMapping)(error)
                case Success(result) => deletePetActionSuccessStatus
            }
            implicit val deletePetWritableJson = anyToWritable[deletePetActionResultType](deletePetResponseMimeType)
            status
        }
        }

    }
