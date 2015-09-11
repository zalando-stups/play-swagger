package heroku.petstore.api.yaml
import play.api.mvc.{Action, Controller}
import de.zalando.play.controllers.PlayBodyParsing
import PlayBodyParsing._
import scala.Option
/**
 */
  import definitions.Pet
  package controllers {
  trait ApiYamlBase extends Controller with PlayBodyParsing {
    private val allPetsResponseMimeType = "application/json"
    private val allPetsActionSuccessStatus = Status(200)
    private type allPetsActionRequestType = (Option[Int])
    private type allPetsActionResultType = Seq[Pet]
    private type allPetsActionType = allPetsActionRequestType => Either[Throwable, allPetsActionResultType]
    private def errorToStatusallPets: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]
    def allPetsAction = (f: allPetsActionType) => (limit: Option[Int]) => Action {
      val result = new ValidationForApiYamlallPets(limit).result.right.map {
        processValidallPetsRequest(f)
      }
      implicit val marshaller = parsingErrors2Writable(allPetsResponseMimeType)
      val response = result.left.map { BadRequest(_) }
      response.fold(♀ => ♀, ♂ => ♂)
    }
    private def processValidallPetsRequest(f: allPetsActionType)(request: allPetsActionRequestType) = {
      val callerResult = f(request)
      val status = callerResult match {
        case Left(error) => (errorToStatusallPets orElse defaultErrorMapping)(error)
        case Right(result) => allPetsActionSuccessStatus
      }
      implicit val allPetsWritableJson = anyToWritable[allPetsActionResultType](allPetsResponseMimeType)
      status(callerResult)
    }
    private val updatePetResponseMimeType = "application/json"
    private val updatePetActionSuccessStatus = Status(200)
    private type updatePetActionRequestType = (Pet)
    private type updatePetActionResultType = Any
    private type updatePetActionType = updatePetActionRequestType => Either[Throwable, updatePetActionResultType]
    private def errorToStatusupdatePet: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]
    private def updatePetParser(maxLength: Int = parse.DefaultMaxTextLength) = anyParser[updatePetActionRequestType]("(Pet)", "Invalid (Pet)", maxLength)
    def updatePetAction = (f: updatePetActionType) => Action(updatePetParser()) { request =>
    val pet = request.body
      val result = new ValidationForApiYamlupdatePet(pet).result.right.map {
        processValidupdatePetRequest(f)
      }
      implicit val marshaller = parsingErrors2Writable(updatePetResponseMimeType)
      val response = result.left.map { BadRequest(_) }
      response.fold(♀ => ♀, ♂ => ♂)
    }
    private def processValidupdatePetRequest(f: updatePetActionType)(request: updatePetActionRequestType) = {
      val callerResult = f(request)
      val status = callerResult match {
        case Left(error) => (errorToStatusupdatePet orElse defaultErrorMapping)(error)
        case Right(result) => updatePetActionSuccessStatus
      }
      implicit val updatePetWritableJson = anyToWritable[updatePetActionResultType](updatePetResponseMimeType)
      status(callerResult)
    }
    private val createPetResponseMimeType = "application/json"
    private val createPetActionSuccessStatus = Status(200)
    private type createPetActionRequestType = (Pet)
    private type createPetActionResultType = Any
    private type createPetActionType = createPetActionRequestType => Either[Throwable, createPetActionResultType]
    private def errorToStatuscreatePet: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]
    private def createPetParser(maxLength: Int = parse.DefaultMaxTextLength) = anyParser[createPetActionRequestType]("(Pet)", "Invalid (Pet)", maxLength)
    def createPetAction = (f: createPetActionType) => Action(createPetParser()) { request =>
    val pet = request.body
      val result = new ValidationForApiYamlcreatePet(pet).result.right.map {
        processValidcreatePetRequest(f)
      }
      implicit val marshaller = parsingErrors2Writable(createPetResponseMimeType)
      val response = result.left.map { BadRequest(_) }
      response.fold(♀ => ♀, ♂ => ♂)
    }
    private def processValidcreatePetRequest(f: createPetActionType)(request: createPetActionRequestType) = {
      val callerResult = f(request)
      val status = callerResult match {
        case Left(error) => (errorToStatuscreatePet orElse defaultErrorMapping)(error)
        case Right(result) => createPetActionSuccessStatus
      }
      implicit val createPetWritableJson = anyToWritable[createPetActionResultType](createPetResponseMimeType)
      status(callerResult)
    }
    private val getPetResponseMimeType = "application/json"
    private val getPetActionSuccessStatus = Status(200)
    private type getPetActionRequestType = (String)
    private type getPetActionResultType = Any
    private type getPetActionType = getPetActionRequestType => Either[Throwable, getPetActionResultType]
    private def errorToStatusgetPet: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]
    def getPetAction = (f: getPetActionType) => (petId: String) => Action {
      val result = new ValidationForApiYamlgetPet(petId).result.right.map {
        processValidgetPetRequest(f)
      }
      implicit val marshaller = parsingErrors2Writable(getPetResponseMimeType)
      val response = result.left.map { BadRequest(_) }
      response.fold(♀ => ♀, ♂ => ♂)
    }
    private def processValidgetPetRequest(f: getPetActionType)(request: getPetActionRequestType) = {
      val callerResult = f(request)
      val status = callerResult match {
        case Left(error) => (errorToStatusgetPet orElse defaultErrorMapping)(error)
        case Right(result) => getPetActionSuccessStatus
      }
      implicit val getPetWritableJson = anyToWritable[getPetActionResultType](getPetResponseMimeType)
      status(callerResult)
    }
  }
  }