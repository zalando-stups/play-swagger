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
    private type findPetsByTagsActionRequestType       = (PetsFindByStatusGetStatus)
    private type findPetsByTagsActionType              = findPetsByTagsActionRequestType => Try[(Int, Any)]

    private val errorToStatusfindPetsByTags: PartialFunction[Throwable, Status] = { 
        case _: java.lang.IllegalArgumentException => Status(405)
    
        case _: java.lang.IndexOutOfBoundsException => Status(405)
     } 

    def findPetsByTagsAction = (f: findPetsByTagsActionType) => (tags: PetsFindByStatusGetStatus) => Action {
        val findPetsByTagsResponseMimeType    = "application/json"
        val possibleWriters = Map(
            400 -> anyToWritable[Null],
            200 -> anyToWritable[Seq[Pet]]
        )        
        val result =
            new PetsFindByTagsGetValidator(tags).errors match {
                case e if e.isEmpty => processValidfindPetsByTagsRequest(f)((tags))(possibleWriters, findPetsByTagsResponseMimeType)
                case l =>
                    implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(findPetsByTagsResponseMimeType)
                    BadRequest(l)
            }

        result
    }

    private def processValidfindPetsByTagsRequest[T <: Any](f: findPetsByTagsActionType)(request: findPetsByTagsActionRequestType)(writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusfindPetsByTags orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val findPetsByTagsWritableJson = writer(mimeType)
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        case Success(other) =>
            implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
            Status(500)(new IllegalStateException(s"Expected pair (responseCode, response) from the controller, but was: other"))
        }
        status
    }
    private type placeOrderActionRequestType       = (StoresOrderPostBody)
    private type placeOrderActionType              = placeOrderActionRequestType => Try[(Int, Any)]

    private val errorToStatusplaceOrder: PartialFunction[Throwable, Status] = { 
        case _: java.lang.IllegalArgumentException => Status(405)
        case _: java.lang.IndexOutOfBoundsException => Status(405)
     } 
        private def placeOrderParser(maxLength: Int = parse.DefaultMaxTextLength) = optionParser[Order]("application/json", "Invalid StoresOrderPostBody", maxLength)

    def placeOrderAction = (f: placeOrderActionType) => Action(placeOrderParser()) { request =>
        val placeOrderResponseMimeType    = "application/json"
        val possibleWriters = Map(
            400 -> anyToWritable[Null],
            200 -> anyToWritable[Order]
        )        
        val body = request.body
        val result =
            new StoresOrderPostValidator(body).errors match {
                case e if e.isEmpty => processValidplaceOrderRequest(f)((body))(possibleWriters, placeOrderResponseMimeType)
                case l =>
                    implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(placeOrderResponseMimeType)
                    BadRequest(l)
            }
        result
    }

    private def processValidplaceOrderRequest[T <: Any](f: placeOrderActionType)(request: placeOrderActionRequestType)(writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusplaceOrder orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val placeOrderWritableJson = writer(mimeType)
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        case Success(other) =>
            implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
            Status(500)(new IllegalStateException(s"Expected pair (responseCode, response) from the controller, but was: other"))
        }
        status
    }
    private type createUserActionRequestType       = (UsersUsernamePutBody)
    private type createUserActionType              = createUserActionRequestType => Try[(Int, Any)]

    private val errorToStatuscreateUser: PartialFunction[Throwable, Status] = { 
        case _: java.lang.IllegalArgumentException => Status(405)
    
        case _: java.lang.IndexOutOfBoundsException => Status(405)
     } 
        private def createUserParser(maxLength: Int = parse.DefaultMaxTextLength) = optionParser[User]("application/json", "Invalid UsersUsernamePutBody", maxLength)

    def createUserAction = (f: createUserActionType) => Action(createUserParser()) { request =>
        val createUserResponseMimeType    = "application/json"
        val possibleWriters = Map.empty[Int,String => Writeable[Any]].withDefaultValue(anyToWritable[Null])
        val body = request.body
        val result =
            new UsersPostValidator(body).errors match {
                case e if e.isEmpty => processValidcreateUserRequest(f)((body))(possibleWriters, createUserResponseMimeType)
                case l =>
                    implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(createUserResponseMimeType)
                    BadRequest(l)
            }
        result
    }

    private def processValidcreateUserRequest[T <: Any](f: createUserActionType)(request: createUserActionRequestType)(writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatuscreateUser orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val createUserWritableJson = writer(mimeType)
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        case Success(other) =>
            implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
            Status(500)(new IllegalStateException(s"Expected pair (responseCode, response) from the controller, but was: other"))
        }
        status
    }
    private type createUsersWithListInputActionRequestType       = (UsersCreateWithListPostBody)
    private type createUsersWithListInputActionType              = createUsersWithListInputActionRequestType => Try[(Int, Any)]

    private val errorToStatuscreateUsersWithListInput: PartialFunction[Throwable, Status] = { 
        case _: java.lang.IllegalArgumentException => Status(405)
    
        case _: java.lang.IndexOutOfBoundsException => Status(405)
     } 
        private def createUsersWithListInputParser(maxLength: Int = parse.DefaultMaxTextLength) = optionParser[UsersCreateWithListPostBodyOpt]("application/json", "Invalid UsersCreateWithListPostBody", maxLength)

    def createUsersWithListInputAction = (f: createUsersWithListInputActionType) => Action(createUsersWithListInputParser()) { request =>
        val createUsersWithListInputResponseMimeType    = "application/json"
        val possibleWriters = Map.empty[Int,String => Writeable[Any]].withDefaultValue(anyToWritable[Null])
        val body = request.body
        val result =
            new UsersCreateWithListPostValidator(body).errors match {
                case e if e.isEmpty => processValidcreateUsersWithListInputRequest(f)((body))(possibleWriters, createUsersWithListInputResponseMimeType)
                case l =>
                    implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(createUsersWithListInputResponseMimeType)
                    BadRequest(l)
            }
        result
    }

    private def processValidcreateUsersWithListInputRequest[T <: Any](f: createUsersWithListInputActionType)(request: createUsersWithListInputActionRequestType)(writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatuscreateUsersWithListInput orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val createUsersWithListInputWritableJson = writer(mimeType)
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        case Success(other) =>
            implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
            Status(500)(new IllegalStateException(s"Expected pair (responseCode, response) from the controller, but was: other"))
        }
        status
    }
    private type getUserByNameActionRequestType       = (String)
    private type getUserByNameActionType              = getUserByNameActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetUserByName: PartialFunction[Throwable, Status] = { 
        case _: java.lang.IllegalArgumentException => Status(405)
    
        case _: java.lang.IndexOutOfBoundsException => Status(405)
     } 

    def getUserByNameAction = (f: getUserByNameActionType) => (username: String) => Action {
        val getUserByNameResponseMimeType    = "application/json"
        val possibleWriters = Map(
            200 -> anyToWritable[User],
            404 -> anyToWritable[Null],
            400 -> anyToWritable[Null]
        )        
        val result =
            new UsersUsernameGetValidator(username).errors match {
                case e if e.isEmpty => processValidgetUserByNameRequest(f)((username))(possibleWriters, getUserByNameResponseMimeType)
                case l =>
                    implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getUserByNameResponseMimeType)
                    BadRequest(l)
            }
        result
    }

    private def processValidgetUserByNameRequest[T <: Any](f: getUserByNameActionType)(request: getUserByNameActionRequestType)(writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetUserByName orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val getUserByNameWritableJson = writer(mimeType)
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        case Success(other) =>
            implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
            Status(500)(new IllegalStateException(s"Expected pair (responseCode, response) from the controller, but was: other"))
        }
        status
    }
    private type updateUserActionRequestType       = (String, UsersUsernamePutBody)
    private type updateUserActionType              = updateUserActionRequestType => Try[(Int, Any)]

    private val errorToStatusupdateUser: PartialFunction[Throwable, Status] = { 
        case _: java.lang.IllegalArgumentException => Status(405)
    
        case _: java.lang.IndexOutOfBoundsException => Status(405)
     } 
        private def updateUserParser(maxLength: Int = parse.DefaultMaxTextLength) = optionParser[User]("application/json", "Invalid UsersUsernamePutBody", maxLength)

    def updateUserAction = (f: updateUserActionType) => (username: String) => Action(updateUserParser()) { request =>
        val updateUserResponseMimeType    = "application/json"
        val possibleWriters = Map(
            404 -> anyToWritable[Null],
            400 -> anyToWritable[Null]
        )        
        val body = request.body
        val result =
            new UsersUsernamePutValidator(username, body).errors match {
                case e if e.isEmpty => processValidupdateUserRequest(f)((username, body))(possibleWriters, updateUserResponseMimeType)
                case l =>
                    implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(updateUserResponseMimeType)
                    BadRequest(l)
            }
        result
    }

    private def processValidupdateUserRequest[T <: Any](f: updateUserActionType)(request: updateUserActionRequestType)(writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusupdateUser orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val updateUserWritableJson = writer(mimeType)
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        case Success(other) =>
            implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
            Status(500)(new IllegalStateException(s"Expected pair (responseCode, response) from the controller, but was: other"))
        }
        status
    }
    private type deleteUserActionRequestType       = (String)
    private type deleteUserActionType              = deleteUserActionRequestType => Try[(Int, Any)]

    private val errorToStatusdeleteUser: PartialFunction[Throwable, Status] = { 
        case _: java.lang.IllegalArgumentException => Status(405)
    
        case _: java.lang.IndexOutOfBoundsException => Status(405)
     } 

    def deleteUserAction = (f: deleteUserActionType) => (username: String) => Action {
        val deleteUserResponseMimeType    = "application/json"
        val possibleWriters = Map(
            404 -> anyToWritable[Null],
            400 -> anyToWritable[Null]
        )        
        val result =
            new UsersUsernameDeleteValidator(username).errors match {
                case e if e.isEmpty => processValiddeleteUserRequest(f)((username))(possibleWriters, deleteUserResponseMimeType)
                case l =>
                    implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(deleteUserResponseMimeType)
                    BadRequest(l)
            }
        result
    }

    private def processValiddeleteUserRequest[T <: Any](f: deleteUserActionType)(request: deleteUserActionRequestType)(writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusdeleteUser orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val deleteUserWritableJson = writer(mimeType)
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        case Success(other) =>
            implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
            Status(500)(new IllegalStateException(s"Expected pair (responseCode, response) from the controller, but was: other"))
        }
        status
    }
    private type updatePetActionRequestType       = (PetsPostBody)
    private type updatePetActionType              = updatePetActionRequestType => Try[(Int, Any)]

    private val errorToStatusupdatePet: PartialFunction[Throwable, Status] = { 
        case _: java.lang.IllegalArgumentException => Status(405)
        case _: java.lang.IndexOutOfBoundsException => Status(405)
     } 
    private def updatePetParser(maxLength: Int = parse.DefaultMaxTextLength) = optionParser[Pet]("application/json", "Invalid PetsPostBody", maxLength)

    def updatePetAction = (f: updatePetActionType) => Action(updatePetParser()) { request =>
        val updatePetResponseMimeType    = "application/json"
        val possibleWriters = Map(
            405 -> anyToWritable[Null],
            404 -> anyToWritable[Null],
            400 -> anyToWritable[Null]
        )        
        val body = request.body
        val result =
            new PetsPutValidator(body).errors match {
                case e if e.isEmpty => processValidupdatePetRequest(f)((body))(possibleWriters, updatePetResponseMimeType)
                case l =>
                    implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(updatePetResponseMimeType)
                    BadRequest(l)
            }
        result
    }

    private def processValidupdatePetRequest[T <: Any](f: updatePetActionType)(request: updatePetActionRequestType)(writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusupdatePet orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val updatePetWritableJson = writer(mimeType)
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        case Success(other) =>
            implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
            Status(500)(new IllegalStateException(s"Expected pair (responseCode, response) from the controller, but was: other"))
        }
        status
    }
    private type addPetActionRequestType       = (PetsPostBody)
    private type addPetActionType              = addPetActionRequestType => Try[(Int, Any)]

    private val errorToStatusaddPet: PartialFunction[Throwable, Status] = { 
        case _: java.lang.IllegalArgumentException => Status(405)
        case _: java.lang.IndexOutOfBoundsException => Status(405)
     } 
    private def addPetParser(maxLength: Int = parse.DefaultMaxTextLength) = optionParser[Pet]("application/json", "Invalid PetsPostBody", maxLength)

    def addPetAction = (f: addPetActionType) => Action(addPetParser()) { request =>
        val addPetResponseMimeType    = "application/json"
        val possibleWriters = Map(
            405 -> anyToWritable[Null]
        )        
        val body = request.body
        val result =
            new PetsPostValidator(body).errors match {
                case e if e.isEmpty => processValidaddPetRequest(f)((body))(possibleWriters, addPetResponseMimeType)
                case l =>
                    implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(addPetResponseMimeType)
                    BadRequest(l)
            }
        result
    }

    private def processValidaddPetRequest[T <: Any](f: addPetActionType)(request: addPetActionRequestType)(writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusaddPet orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val addPetWritableJson = writer(mimeType)
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        case Success(other) =>
            implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
            Status(500)(new IllegalStateException(s"Expected pair (responseCode, response) from the controller, but was: other"))
        }
        status
    }
    private type createUsersWithArrayInputActionRequestType       = (UsersCreateWithListPostBody)
    private type createUsersWithArrayInputActionType              = createUsersWithArrayInputActionRequestType => Try[(Int, Any)]

    private val errorToStatuscreateUsersWithArrayInput: PartialFunction[Throwable, Status] = { 
        case _: java.lang.IllegalArgumentException => Status(405)
    
        case _: java.lang.IndexOutOfBoundsException => Status(405)
     } 
        private def createUsersWithArrayInputParser(maxLength: Int = parse.DefaultMaxTextLength) = optionParser[UsersCreateWithListPostBodyOpt]("application/json", "Invalid UsersCreateWithListPostBody", maxLength)

    def createUsersWithArrayInputAction = (f: createUsersWithArrayInputActionType) => Action(createUsersWithArrayInputParser()) { request =>
        val createUsersWithArrayInputResponseMimeType    = "application/json"
        val possibleWriters = Map.empty[Int,String => Writeable[Any]].withDefaultValue(anyToWritable[Null])
        val body = request.body
        val result =
            new UsersCreateWithArrayPostValidator(body).errors match {
                case e if e.isEmpty => processValidcreateUsersWithArrayInputRequest(f)((body))(possibleWriters, createUsersWithArrayInputResponseMimeType)
                case l =>
                    implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(createUsersWithArrayInputResponseMimeType)
                    BadRequest(l)
            }
        result
    }

    private def processValidcreateUsersWithArrayInputRequest[T <: Any](f: createUsersWithArrayInputActionType)(request: createUsersWithArrayInputActionRequestType)(writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatuscreateUsersWithArrayInput orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val createUsersWithArrayInputWritableJson = writer(mimeType)
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        case Success(other) =>
            implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
            Status(500)(new IllegalStateException(s"Expected pair (responseCode, response) from the controller, but was: other"))
        }
        status
    }
    private type getOrderByIdActionRequestType       = (String)
    private type getOrderByIdActionType              = getOrderByIdActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetOrderById: PartialFunction[Throwable, Status] = { 
        case _: java.lang.IllegalArgumentException => Status(405)
    
        case _: java.lang.IndexOutOfBoundsException => Status(405)
     } 

    def getOrderByIdAction = (f: getOrderByIdActionType) => (orderId: String) => Action {
        val getOrderByIdResponseMimeType    = "application/json"
        val possibleWriters = Map(
            404 -> anyToWritable[Null],
            400 -> anyToWritable[Null],
            200 -> anyToWritable[Order]
        )        
        val result =
            new StoresOrderOrderIdGetValidator(orderId).errors match {
                case e if e.isEmpty => processValidgetOrderByIdRequest(f)((orderId))(possibleWriters, getOrderByIdResponseMimeType)
                case l =>
                    implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getOrderByIdResponseMimeType)
                    BadRequest(l)
            }
        result
    }

    private def processValidgetOrderByIdRequest[T <: Any](f: getOrderByIdActionType)(request: getOrderByIdActionRequestType)(writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetOrderById orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val getOrderByIdWritableJson = writer(mimeType)
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        case Success(other) =>
            implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
            Status(500)(new IllegalStateException(s"Expected pair (responseCode, response) from the controller, but was: other"))
        }
        status
    }
    private type deleteOrderActionRequestType       = (String)
    private type deleteOrderActionType              = deleteOrderActionRequestType => Try[(Int, Any)]

    private val errorToStatusdeleteOrder: PartialFunction[Throwable, Status] = { 
        case _: java.lang.IllegalArgumentException => Status(405)
    
        case _: java.lang.IndexOutOfBoundsException => Status(405)
     } 

    def deleteOrderAction = (f: deleteOrderActionType) => (orderId: String) => Action {
        val deleteOrderResponseMimeType    = "application/json"
        val possibleWriters = Map(
            404 -> anyToWritable[Null],
            400 -> anyToWritable[Null]
        )        
        val result =
            new StoresOrderOrderIdDeleteValidator(orderId).errors match {
                case e if e.isEmpty => processValiddeleteOrderRequest(f)((orderId))(possibleWriters, deleteOrderResponseMimeType)
                case l =>
                    implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(deleteOrderResponseMimeType)
                    BadRequest(l)
            }
        result
    }

    private def processValiddeleteOrderRequest[T <: Any](f: deleteOrderActionType)(request: deleteOrderActionRequestType)(writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusdeleteOrder orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val deleteOrderWritableJson = writer(mimeType)
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        case Success(other) =>
            implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
            Status(500)(new IllegalStateException(s"Expected pair (responseCode, response) from the controller, but was: other"))
        }
        status
    }
    private type logoutUserActionRequestType       = (Unit)
    private type logoutUserActionType              = logoutUserActionRequestType => Try[(Int, Any)]

    private val errorToStatuslogoutUser: PartialFunction[Throwable, Status] = { 
        case _: java.lang.IllegalArgumentException => Status(405)
        case _: java.lang.IndexOutOfBoundsException => Status(405)
     } 

    def logoutUserAction = (f: logoutUserActionType) => Action {
        val logoutUserResponseMimeType    = "application/json"
        val possibleWriters = Map.empty[Int,String => Writeable[Any]].withDefaultValue(anyToWritable[Null])
        val result = processValidlogoutUserRequest(f)()(possibleWriters, logoutUserResponseMimeType)
        result
    }

    private def processValidlogoutUserRequest[T <: Any](f: logoutUserActionType)(request: logoutUserActionRequestType)(writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatuslogoutUser orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val logoutUserWritableJson = writer(mimeType)
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        case Success(other) =>
            implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
            Status(500)(new IllegalStateException(s"Expected pair (responseCode, response) from the controller, but was: other"))
        }
        status
    }
    private type getPetByIdActionRequestType       = (Long)
    private type getPetByIdActionType              = getPetByIdActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetPetById: PartialFunction[Throwable, Status] = { 
        case _: java.lang.IllegalArgumentException => Status(405)
        case _: java.lang.IndexOutOfBoundsException => Status(405)
     } 

    def getPetByIdAction = (f: getPetByIdActionType) => (petId: Long) => Action {
        val getPetByIdResponseMimeType    = "application/json"
        val possibleWriters = Map(
            404 -> anyToWritable[Null],
            400 -> anyToWritable[Null],
            200 -> anyToWritable[Pet]
        )        
        val result =
            new PetsPetIdGetValidator(petId).errors match {
                case e if e.isEmpty => processValidgetPetByIdRequest(f)((petId))(possibleWriters, getPetByIdResponseMimeType)
                case l =>
                    implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getPetByIdResponseMimeType)
                    BadRequest(l)
            }
        result
    }

    private def processValidgetPetByIdRequest[T <: Any](f: getPetByIdActionType)(request: getPetByIdActionRequestType)(writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetPetById orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val getPetByIdWritableJson = writer(mimeType)
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        case Success(other) =>
            implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
            Status(500)(new IllegalStateException(s"Expected pair (responseCode, response) from the controller, but was: other"))
        }
        status
    }
    private type updatePetWithFormActionRequestType       = (String, String, String)
    private type updatePetWithFormActionType              = updatePetWithFormActionRequestType => Try[(Int, Any)]

    private val errorToStatusupdatePetWithForm: PartialFunction[Throwable, Status] = { 
        case _: java.lang.IllegalArgumentException => Status(405)
        case _: java.lang.IndexOutOfBoundsException => Status(405)
     } 

    def updatePetWithFormAction = (f: updatePetWithFormActionType) => (petId: String, name: String, status: String) => Action {
        val updatePetWithFormResponseMimeType    = "application/json"
        val possibleWriters = Map(
            405 -> anyToWritable[Null]
        )        
        val result =
            new PetsPetIdPostValidator(petId, name, status).errors match {
                case e if e.isEmpty => processValidupdatePetWithFormRequest(f)((petId, name, status))(possibleWriters, updatePetWithFormResponseMimeType)
                case l =>
                    implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(updatePetWithFormResponseMimeType)
                    BadRequest(l)
            }
        result
    }

    private def processValidupdatePetWithFormRequest[T <: Any](f: updatePetWithFormActionType)(request: updatePetWithFormActionRequestType)(writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusupdatePetWithForm orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val updatePetWithFormWritableJson = writer(mimeType)
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        case Success(other) =>
            implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
            Status(500)(new IllegalStateException(s"Expected pair (responseCode, response) from the controller, but was: other"))
        }
        status
    }
    private type deletePetActionRequestType       = (String, Long)
    private type deletePetActionType              = deletePetActionRequestType => Try[(Int, Any)]

    private val errorToStatusdeletePet: PartialFunction[Throwable, Status] = { 
        case _: java.lang.IllegalArgumentException => Status(405)
    
        case _: java.lang.IndexOutOfBoundsException => Status(405)
     } 

    def deletePetAction = (f: deletePetActionType) => (petId: Long) => Action { request =>
        val deletePetResponseMimeType    = "application/json"
        val possibleWriters = Map(
            400 -> anyToWritable[Null]
        )        
        val api_key =
            fromHeaders[String]("api_key", request.headers.toMap)
            (api_key) match {
                case (Right(api_key)) =>
        
                val result =
                    new PetsPetIdDeleteValidator(api_key, petId).errors match {
                        case e if e.isEmpty => processValiddeletePetRequest(f)((api_key, petId))(possibleWriters, deletePetResponseMimeType)
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(deletePetResponseMimeType)
                            BadRequest(l)
                    }
                result
            case (_) =>
                val msg = Seq(api_key).filter{_.isLeft}.map(_.left.get).mkString("\n")
                BadRequest(msg)
        }
        
    }

    private def processValiddeletePetRequest[T <: Any](f: deletePetActionType)(request: deletePetActionRequestType)(writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusdeletePet orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val deletePetWritableJson = writer(mimeType)
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        case Success(other) =>
            implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
            Status(500)(new IllegalStateException(s"Expected pair (responseCode, response) from the controller, but was: other"))
        }
        status
    }
    private type findPetsByStatusActionRequestType       = (PetsFindByStatusGetStatus)
    private type findPetsByStatusActionType              = findPetsByStatusActionRequestType => Try[(Int, Any)]

    private val errorToStatusfindPetsByStatus: PartialFunction[Throwable, Status] = { 
        case _: java.lang.IllegalArgumentException => Status(405)
        case _: java.lang.IndexOutOfBoundsException => Status(405)
     } 

    def findPetsByStatusAction = (f: findPetsByStatusActionType) => (status: PetsFindByStatusGetStatus) => Action {
        val findPetsByStatusResponseMimeType    = "application/json"
        val possibleWriters = Map(
            200 -> anyToWritable[Seq[Pet]],
            400 -> anyToWritable[Null]
        )        
        val result =
            new PetsFindByStatusGetValidator(status).errors match {
                case e if e.isEmpty => processValidfindPetsByStatusRequest(f)((status))(possibleWriters, findPetsByStatusResponseMimeType)
                case l =>
                    implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(findPetsByStatusResponseMimeType)
                    BadRequest(l)
            }
        result
    }

    private def processValidfindPetsByStatusRequest[T <: Any](f: findPetsByStatusActionType)(request: findPetsByStatusActionRequestType)(writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusfindPetsByStatus orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val findPetsByStatusWritableJson = writer(mimeType)
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        case Success(other) =>
            implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
            Status(500)(new IllegalStateException(s"Expected pair (responseCode, response) from the controller, but was: other"))
        }
        status
    }
    private type loginUserActionRequestType       = (OrderStatus, OrderStatus)
    private type loginUserActionType              = loginUserActionRequestType => Try[(Int, Any)]

    private val errorToStatusloginUser: PartialFunction[Throwable, Status] = { 
        case _: java.lang.IllegalArgumentException => Status(405)
        case _: java.lang.IndexOutOfBoundsException => Status(405)
     } 

    def loginUserAction = (f: loginUserActionType) => (username: OrderStatus, password: OrderStatus) => Action {
        val loginUserResponseMimeType    = "application/json"
        val possibleWriters = Map(
            200 -> anyToWritable[String],
            400 -> anyToWritable[Null]
        )        
        val result =
            new UsersLoginGetValidator(username, password).errors match {
                case e if e.isEmpty => processValidloginUserRequest(f)((username, password))(possibleWriters, loginUserResponseMimeType)
                case l =>
                    implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(loginUserResponseMimeType)
                    BadRequest(l)
            }
        result
    }

    private def processValidloginUserRequest[T <: Any](f: loginUserActionType)(request: loginUserActionRequestType)(writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusloginUser orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val loginUserWritableJson = writer(mimeType)
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        case Success(other) =>
            implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
            Status(500)(new IllegalStateException(s"Expected pair (responseCode, response) from the controller, but was: other"))
        }
        status
    }
}
