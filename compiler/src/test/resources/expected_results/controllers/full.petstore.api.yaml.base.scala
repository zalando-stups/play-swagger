package full.petstore.api.yaml
import play.api.mvc.{Action, Controller, Results}
import play.api.http.Writeable
import Results.Status
import de.zalando.play.controllers.{PlayBodyParsing, ParsingError}
import PlayBodyParsing._
import scala.util._
import de.zalando.play.controllers.ArrayWrapper
import org.joda.time.DateTime
import de.zalando.play.controllers.PlayPathBindables
trait FullPetstoreApiYamlBase extends Controller with PlayBodyParsing {
    private val findPetsByTagsResponseMimeType    = "application/json"
    private val findPetsByTagsActionSuccessStatus = Status(200)

    private type findPetsByTagsActionRequestType       = (PetsFindByStatusGetStatus)
    private type findPetsByTagsActionResultType        = Seq[PetsPostBodyOpt]
    private type findPetsByTagsActionType              = findPetsByTagsActionRequestType => Try[findPetsByTagsActionResultType]

    private val errorToStatusfindPetsByTags: PartialFunction[Throwable, Status] = 

    { { case _: java.lang.IllegalArgumentException => Status(405)

    case _: java.lang.IndexOutOfBoundsException => Status(405)

    } } 


    


    def findPetsByTagsAction = (f: findPetsByTagsActionType) => (tags: PetsFindByStatusGetStatus) => Action { 

        val result = 

            new PetsFindByTagsGetValidator(tags).errors match {
                    case e if e.isEmpty => processValidfindPetsByTagsRequest(f)((tags))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(findPetsByTagsResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValidfindPetsByTagsRequest(f: findPetsByTagsActionType)(request: findPetsByTagsActionRequestType) = {
        implicit val findPetsByTagsWritableJson = anyToWritable[findPetsByTagsActionResultType](findPetsByTagsResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusfindPetsByTags orElse defaultErrorMapping)(error)
            case Success(result) => findPetsByTagsActionSuccessStatus(result)
        }
        status
    }
    private val placeOrderResponseMimeType    = "application/json"
    private val placeOrderActionSuccessStatus = Status(200)

    private type placeOrderActionRequestType       = (StoresOrderPostBody)
    private type placeOrderActionResultType        = Order
    private type placeOrderActionType              = placeOrderActionRequestType => Try[placeOrderActionResultType]

    private val errorToStatusplaceOrder: PartialFunction[Throwable, Status] = 

    { { case _: java.lang.IllegalArgumentException => Status(405)

    case _: java.lang.IndexOutOfBoundsException => Status(405)

    } } 


    private def placeOrderParser(maxLength: Int = parse.DefaultMaxTextLength) = anyParser[StoresOrderPostBody]("application/json", "Invalid StoresOrderPostBody", maxLength)
    


    def placeOrderAction = (f: placeOrderActionType) => Action (placeOrderParser()){ request =>

        val body = request.body
        val result = 

            new StoresOrderPostValidator(body).errors match {
                    case e if e.isEmpty => processValidplaceOrderRequest(f)((body))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(placeOrderResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValidplaceOrderRequest(f: placeOrderActionType)(request: placeOrderActionRequestType) = {
        implicit val placeOrderWritableJson = anyToWritable[placeOrderActionResultType](placeOrderResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusplaceOrder orElse defaultErrorMapping)(error)
            case Success(result) => placeOrderActionSuccessStatus(result)
        }
        status
    }
    private val createUserResponseMimeType    = "application/json"
    private val createUserActionSuccessStatus = Status(200)

    private type createUserActionRequestType       = (UsersUsernamePutBody)
    private type createUserActionResultType        = Null
    private type createUserActionType              = createUserActionRequestType => Try[createUserActionResultType]

    private val errorToStatuscreateUser: PartialFunction[Throwable, Status] = 

    { { case _: java.lang.IllegalArgumentException => Status(405)

    case _: java.lang.IndexOutOfBoundsException => Status(405)

    } } 


    private def createUserParser(maxLength: Int = parse.DefaultMaxTextLength) = anyParser[UsersUsernamePutBody]("application/json", "Invalid UsersUsernamePutBody", maxLength)
    


    def createUserAction = (f: createUserActionType) => Action (createUserParser()){ request =>

        val body = request.body
        val result = 

            new UsersPostValidator(body).errors match {
                    case e if e.isEmpty => processValidcreateUserRequest(f)((body))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(createUserResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValidcreateUserRequest(f: createUserActionType)(request: createUserActionRequestType) = {
        implicit val createUserWritableJson = anyToWritable[createUserActionResultType](createUserResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatuscreateUser orElse defaultErrorMapping)(error)
            case Success(result) => createUserActionSuccessStatus(result)
        }
        status
    }
    private val createUsersWithListInputResponseMimeType    = "application/json"
    private val createUsersWithListInputActionSuccessStatus = Status(200)

    private type createUsersWithListInputActionRequestType       = (UsersCreateWithListPostBody)
    private type createUsersWithListInputActionResultType        = Null
    private type createUsersWithListInputActionType              = createUsersWithListInputActionRequestType => Try[createUsersWithListInputActionResultType]

    private val errorToStatuscreateUsersWithListInput: PartialFunction[Throwable, Status] = 

    { { case _: java.lang.IllegalArgumentException => Status(405)

    case _: java.lang.IndexOutOfBoundsException => Status(405)

    } } 


    private def createUsersWithListInputParser(maxLength: Int = parse.DefaultMaxTextLength) = anyParser[UsersCreateWithListPostBody]("application/json", "Invalid UsersCreateWithListPostBody", maxLength)
    


    def createUsersWithListInputAction = (f: createUsersWithListInputActionType) => Action (createUsersWithListInputParser()){ request =>

        val body = request.body
        val result = 

            new UsersCreateWithListPostValidator(body).errors match {
                    case e if e.isEmpty => processValidcreateUsersWithListInputRequest(f)((body))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(createUsersWithListInputResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValidcreateUsersWithListInputRequest(f: createUsersWithListInputActionType)(request: createUsersWithListInputActionRequestType) = {
        implicit val createUsersWithListInputWritableJson = anyToWritable[createUsersWithListInputActionResultType](createUsersWithListInputResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatuscreateUsersWithListInput orElse defaultErrorMapping)(error)
            case Success(result) => createUsersWithListInputActionSuccessStatus(result)
        }
        status
    }
    private val getUserByNameResponseMimeType    = "application/json"
    private val getUserByNameActionSuccessStatus = Status(200)

    private type getUserByNameActionRequestType       = (String)
    private type getUserByNameActionResultType        = User
    private type getUserByNameActionType              = getUserByNameActionRequestType => Try[getUserByNameActionResultType]

    private val errorToStatusgetUserByName: PartialFunction[Throwable, Status] = 

    { { case _: java.lang.IllegalArgumentException => Status(405)

    case _: java.lang.IndexOutOfBoundsException => Status(405)

    } } 


    


    def getUserByNameAction = (f: getUserByNameActionType) => (username: String) => Action { 

        val result = 

            new UsersUsernameGetValidator(username).errors match {
                    case e if e.isEmpty => processValidgetUserByNameRequest(f)((username))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getUserByNameResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValidgetUserByNameRequest(f: getUserByNameActionType)(request: getUserByNameActionRequestType) = {
        implicit val getUserByNameWritableJson = anyToWritable[getUserByNameActionResultType](getUserByNameResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetUserByName orElse defaultErrorMapping)(error)
            case Success(result) => getUserByNameActionSuccessStatus(result)
        }
        status
    }
    private val updateUserResponseMimeType    = "application/json"
    private val updateUserActionSuccessStatus = Status(200)

    private type updateUserActionRequestType       = (String, UsersUsernamePutBody)
    private type updateUserActionResultType        = Null
    private type updateUserActionType              = updateUserActionRequestType => Try[updateUserActionResultType]

    private val errorToStatusupdateUser: PartialFunction[Throwable, Status] = 

    { { case _: java.lang.IllegalArgumentException => Status(405)

    case _: java.lang.IndexOutOfBoundsException => Status(405)

    } } 


    private def updateUserParser(maxLength: Int = parse.DefaultMaxTextLength) = anyParser[UsersUsernamePutBody]("application/json", "Invalid UsersUsernamePutBody", maxLength)
    


    def updateUserAction = (f: updateUserActionType) => (username: String) => Action (updateUserParser()){ request =>

        val body = request.body
        val result = 

            new UsersUsernamePutValidator(username, body).errors match {
                    case e if e.isEmpty => processValidupdateUserRequest(f)((username, body))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(updateUserResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValidupdateUserRequest(f: updateUserActionType)(request: updateUserActionRequestType) = {
        implicit val updateUserWritableJson = anyToWritable[updateUserActionResultType](updateUserResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusupdateUser orElse defaultErrorMapping)(error)
            case Success(result) => updateUserActionSuccessStatus(result)
        }
        status
    }
    private val deleteUserResponseMimeType    = "application/json"
    private val deleteUserActionSuccessStatus = Status(200)

    private type deleteUserActionRequestType       = (String)
    private type deleteUserActionResultType        = Null
    private type deleteUserActionType              = deleteUserActionRequestType => Try[deleteUserActionResultType]

    private val errorToStatusdeleteUser: PartialFunction[Throwable, Status] = 

    { { case _: java.lang.IllegalArgumentException => Status(405)

    case _: java.lang.IndexOutOfBoundsException => Status(405)

    } } 


    


    def deleteUserAction = (f: deleteUserActionType) => (username: String) => Action { 

        val result = 

            new UsersUsernameDeleteValidator(username).errors match {
                    case e if e.isEmpty => processValiddeleteUserRequest(f)((username))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(deleteUserResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValiddeleteUserRequest(f: deleteUserActionType)(request: deleteUserActionRequestType) = {
        implicit val deleteUserWritableJson = anyToWritable[deleteUserActionResultType](deleteUserResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusdeleteUser orElse defaultErrorMapping)(error)
            case Success(result) => deleteUserActionSuccessStatus(result)
        }
        status
    }
    private val updatePetResponseMimeType    = "application/json"
    private val updatePetActionSuccessStatus = Status(200)

    private type updatePetActionRequestType       = (PetsPostBody)
    private type updatePetActionResultType        = Null
    private type updatePetActionType              = updatePetActionRequestType => Try[updatePetActionResultType]

    private val errorToStatusupdatePet: PartialFunction[Throwable, Status] = 

    { { case _: java.lang.IllegalArgumentException => Status(405)

    case _: java.lang.IndexOutOfBoundsException => Status(405)

    } } 


    private def updatePetParser(maxLength: Int = parse.DefaultMaxTextLength) = anyParser[PetsPostBody]("application/json", "Invalid PetsPostBody", maxLength)
    


    def updatePetAction = (f: updatePetActionType) => Action (updatePetParser()){ request =>

        val body = request.body
        val result = 

            new PetsPutValidator(body).errors match {
                    case e if e.isEmpty => processValidupdatePetRequest(f)((body))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(updatePetResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValidupdatePetRequest(f: updatePetActionType)(request: updatePetActionRequestType) = {
        implicit val updatePetWritableJson = anyToWritable[updatePetActionResultType](updatePetResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusupdatePet orElse defaultErrorMapping)(error)
            case Success(result) => updatePetActionSuccessStatus(result)
        }
        status
    }
    private val addPetResponseMimeType    = "application/json"
    private val addPetActionSuccessStatus = Status(200)

    private type addPetActionRequestType       = (PetsPostBody)
    private type addPetActionResultType        = Null
    private type addPetActionType              = addPetActionRequestType => Try[addPetActionResultType]

    private val errorToStatusaddPet: PartialFunction[Throwable, Status] = 

    { { case _: java.lang.IllegalArgumentException => Status(405)

    case _: java.lang.IndexOutOfBoundsException => Status(405)

    } } 


    private def addPetParser(maxLength: Int = parse.DefaultMaxTextLength) = anyParser[PetsPostBody]("application/json", "Invalid PetsPostBody", maxLength)
    


    def addPetAction = (f: addPetActionType) => Action (addPetParser()){ request =>

        val body = request.body
        val result = 

            new PetsPostValidator(body).errors match {
                    case e if e.isEmpty => processValidaddPetRequest(f)((body))
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
    private val createUsersWithArrayInputResponseMimeType    = "application/json"
    private val createUsersWithArrayInputActionSuccessStatus = Status(200)

    private type createUsersWithArrayInputActionRequestType       = (UsersCreateWithListPostBody)
    private type createUsersWithArrayInputActionResultType        = Null
    private type createUsersWithArrayInputActionType              = createUsersWithArrayInputActionRequestType => Try[createUsersWithArrayInputActionResultType]

    private val errorToStatuscreateUsersWithArrayInput: PartialFunction[Throwable, Status] = 

    { { case _: java.lang.IllegalArgumentException => Status(405)

    case _: java.lang.IndexOutOfBoundsException => Status(405)

    } } 


    private def createUsersWithArrayInputParser(maxLength: Int = parse.DefaultMaxTextLength) = anyParser[UsersCreateWithListPostBody]("application/json", "Invalid UsersCreateWithListPostBody", maxLength)
    


    def createUsersWithArrayInputAction = (f: createUsersWithArrayInputActionType) => Action (createUsersWithArrayInputParser()){ request =>

        val body = request.body
        val result = 

            new UsersCreateWithArrayPostValidator(body).errors match {
                    case e if e.isEmpty => processValidcreateUsersWithArrayInputRequest(f)((body))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(createUsersWithArrayInputResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValidcreateUsersWithArrayInputRequest(f: createUsersWithArrayInputActionType)(request: createUsersWithArrayInputActionRequestType) = {
        implicit val createUsersWithArrayInputWritableJson = anyToWritable[createUsersWithArrayInputActionResultType](createUsersWithArrayInputResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatuscreateUsersWithArrayInput orElse defaultErrorMapping)(error)
            case Success(result) => createUsersWithArrayInputActionSuccessStatus(result)
        }
        status
    }
    private val getOrderByIdResponseMimeType    = "application/json"
    private val getOrderByIdActionSuccessStatus = Status(200)

    private type getOrderByIdActionRequestType       = (String)
    private type getOrderByIdActionResultType        = Order
    private type getOrderByIdActionType              = getOrderByIdActionRequestType => Try[getOrderByIdActionResultType]

    private val errorToStatusgetOrderById: PartialFunction[Throwable, Status] = 

    { { case _: java.lang.IllegalArgumentException => Status(405)

    case _: java.lang.IndexOutOfBoundsException => Status(405)

    } } 


    


    def getOrderByIdAction = (f: getOrderByIdActionType) => (orderId: String) => Action { 

        val result = 

            new StoresOrderOrderIdGetValidator(orderId).errors match {
                    case e if e.isEmpty => processValidgetOrderByIdRequest(f)((orderId))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getOrderByIdResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValidgetOrderByIdRequest(f: getOrderByIdActionType)(request: getOrderByIdActionRequestType) = {
        implicit val getOrderByIdWritableJson = anyToWritable[getOrderByIdActionResultType](getOrderByIdResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetOrderById orElse defaultErrorMapping)(error)
            case Success(result) => getOrderByIdActionSuccessStatus(result)
        }
        status
    }
    private val deleteOrderResponseMimeType    = "application/json"
    private val deleteOrderActionSuccessStatus = Status(200)

    private type deleteOrderActionRequestType       = (String)
    private type deleteOrderActionResultType        = Null
    private type deleteOrderActionType              = deleteOrderActionRequestType => Try[deleteOrderActionResultType]

    private val errorToStatusdeleteOrder: PartialFunction[Throwable, Status] = 

    { { case _: java.lang.IllegalArgumentException => Status(405)

    case _: java.lang.IndexOutOfBoundsException => Status(405)

    } } 


    


    def deleteOrderAction = (f: deleteOrderActionType) => (orderId: String) => Action { 

        val result = 

            new StoresOrderOrderIdDeleteValidator(orderId).errors match {
                    case e if e.isEmpty => processValiddeleteOrderRequest(f)((orderId))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(deleteOrderResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValiddeleteOrderRequest(f: deleteOrderActionType)(request: deleteOrderActionRequestType) = {
        implicit val deleteOrderWritableJson = anyToWritable[deleteOrderActionResultType](deleteOrderResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusdeleteOrder orElse defaultErrorMapping)(error)
            case Success(result) => deleteOrderActionSuccessStatus(result)
        }
        status
    }
    private val logoutUserResponseMimeType    = "application/json"
    private val logoutUserActionSuccessStatus = Status(200)

    private type logoutUserActionRequestType       = (Unit)
    private type logoutUserActionResultType        = Null
    private type logoutUserActionType              = logoutUserActionRequestType => Try[logoutUserActionResultType]

    private val errorToStatuslogoutUser: PartialFunction[Throwable, Status] = 

    { { case _: java.lang.IllegalArgumentException => Status(405)

    case _: java.lang.IndexOutOfBoundsException => Status(405)

    } } 


    


    def logoutUserAction = (f: logoutUserActionType) => Action { 

        val result = processValidlogoutUserRequest(f)()

            result
    }

    private def processValidlogoutUserRequest(f: logoutUserActionType)(request: logoutUserActionRequestType) = {
        implicit val logoutUserWritableJson = anyToWritable[logoutUserActionResultType](logoutUserResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatuslogoutUser orElse defaultErrorMapping)(error)
            case Success(result) => logoutUserActionSuccessStatus(result)
        }
        status
    }
    private val getPetByIdResponseMimeType    = "application/json"
    private val getPetByIdActionSuccessStatus = Status(200)

    private type getPetByIdActionRequestType       = (Long)
    private type getPetByIdActionResultType        = /definitions/Pet
    private type getPetByIdActionType              = getPetByIdActionRequestType => Try[getPetByIdActionResultType]

    private val errorToStatusgetPetById: PartialFunction[Throwable, Status] = 

    { { case _: java.lang.IllegalArgumentException => Status(405)

    case _: java.lang.IndexOutOfBoundsException => Status(405)

    } } 


    


    def getPetByIdAction = (f: getPetByIdActionType) => (petId: Long) => Action { 

        val result = 

            new PetsPetIdGetValidator(petId).errors match {
                    case e if e.isEmpty => processValidgetPetByIdRequest(f)((petId))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getPetByIdResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValidgetPetByIdRequest(f: getPetByIdActionType)(request: getPetByIdActionRequestType) = {
        implicit val getPetByIdWritableJson = anyToWritable[getPetByIdActionResultType](getPetByIdResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetPetById orElse defaultErrorMapping)(error)
            case Success(result) => getPetByIdActionSuccessStatus(result)
        }
        status
    }
    private val updatePetWithFormResponseMimeType    = "application/json"
    private val updatePetWithFormActionSuccessStatus = Status(200)

    private type updatePetWithFormActionRequestType       = (String, String, String)
    private type updatePetWithFormActionResultType        = Null
    private type updatePetWithFormActionType              = updatePetWithFormActionRequestType => Try[updatePetWithFormActionResultType]

    private val errorToStatusupdatePetWithForm: PartialFunction[Throwable, Status] = 

    { { case _: java.lang.IllegalArgumentException => Status(405)

    case _: java.lang.IndexOutOfBoundsException => Status(405)

    } } 


    


    def updatePetWithFormAction = (f: updatePetWithFormActionType) => (petId: String, name: String, status: String) => Action { 

        val result = 

            new PetsPetIdPostValidator(petId, name, status).errors match {
                    case e if e.isEmpty => processValidupdatePetWithFormRequest(f)((petId, name, status))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(updatePetWithFormResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValidupdatePetWithFormRequest(f: updatePetWithFormActionType)(request: updatePetWithFormActionRequestType) = {
        implicit val updatePetWithFormWritableJson = anyToWritable[updatePetWithFormActionResultType](updatePetWithFormResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusupdatePetWithForm orElse defaultErrorMapping)(error)
            case Success(result) => updatePetWithFormActionSuccessStatus(result)
        }
        status
    }
    private val deletePetResponseMimeType    = "application/json"
    private val deletePetActionSuccessStatus = Status(200)

    private type deletePetActionRequestType       = (String, Long)
    private type deletePetActionResultType        = Null
    private type deletePetActionType              = deletePetActionRequestType => Try[deletePetActionResultType]

    private val errorToStatusdeletePet: PartialFunction[Throwable, Status] = 

    { { case _: java.lang.IllegalArgumentException => Status(405)

    case _: java.lang.IndexOutOfBoundsException => Status(405)

    } } 


    


    def deletePetAction = (f: deletePetActionType) => (petId: Long) => Action { request =>

        val api_key = request.headers.get("api_key")
        val result = 

            new PetsPetIdDeleteValidator(api_key, petId).errors match {
                    case e if e.isEmpty => processValiddeletePetRequest(f)((api_key, petId))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(deletePetResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValiddeletePetRequest(f: deletePetActionType)(request: deletePetActionRequestType) = {
        implicit val deletePetWritableJson = anyToWritable[deletePetActionResultType](deletePetResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusdeletePet orElse defaultErrorMapping)(error)
            case Success(result) => deletePetActionSuccessStatus(result)
        }
        status
    }
    private val findPetsByStatusResponseMimeType    = "application/json"
    private val findPetsByStatusActionSuccessStatus = Status(200)

    private type findPetsByStatusActionRequestType       = (PetsFindByStatusGetStatus)
    private type findPetsByStatusActionResultType        = Seq[PetsPostBodyOpt]
    private type findPetsByStatusActionType              = findPetsByStatusActionRequestType => Try[findPetsByStatusActionResultType]

    private val errorToStatusfindPetsByStatus: PartialFunction[Throwable, Status] = 

    { { case _: java.lang.IllegalArgumentException => Status(405)

    case _: java.lang.IndexOutOfBoundsException => Status(405)

    } } 


    


    def findPetsByStatusAction = (f: findPetsByStatusActionType) => (status: PetsFindByStatusGetStatus) => Action { 

        val result = 

            new PetsFindByStatusGetValidator(status).errors match {
                    case e if e.isEmpty => processValidfindPetsByStatusRequest(f)((status))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(findPetsByStatusResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValidfindPetsByStatusRequest(f: findPetsByStatusActionType)(request: findPetsByStatusActionRequestType) = {
        implicit val findPetsByStatusWritableJson = anyToWritable[findPetsByStatusActionResultType](findPetsByStatusResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusfindPetsByStatus orElse defaultErrorMapping)(error)
            case Success(result) => findPetsByStatusActionSuccessStatus(result)
        }
        status
    }
    private val loginUserResponseMimeType    = "application/json"
    private val loginUserActionSuccessStatus = Status(200)

    private type loginUserActionRequestType       = (OrderStatus, OrderStatus)
    private type loginUserActionResultType        = Null
    private type loginUserActionType              = loginUserActionRequestType => Try[loginUserActionResultType]

    private val errorToStatusloginUser: PartialFunction[Throwable, Status] = 

    { { case _: java.lang.IllegalArgumentException => Status(405)

    case _: java.lang.IndexOutOfBoundsException => Status(405)

    } } 


    


    def loginUserAction = (f: loginUserActionType) => (username: OrderStatus, password: OrderStatus) => Action { 

        val result = 

            new UsersLoginGetValidator(username, password).errors match {
                    case e if e.isEmpty => processValidloginUserRequest(f)((username, password))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(loginUserResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValidloginUserRequest(f: loginUserActionType)(request: loginUserActionRequestType) = {
        implicit val loginUserWritableJson = anyToWritable[loginUserActionResultType](loginUserResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusloginUser orElse defaultErrorMapping)(error)
            case Success(result) => loginUserActionSuccessStatus(result)
        }
        status
    }
    }
