package split.petstore.api.yaml

import play.api.mvc.{Action, Controller, Results}
import play.api.http._
import Results.Status
import de.zalando.play.controllers.{PlayBodyParsing, ParsingError}
import PlayBodyParsing._
import scala.util._
import de.zalando.play.controllers.ArrayWrapper
import org.joda.time.DateTime

import de.zalando.play.controllers.PlayPathBindables




trait SplitPetstoreApiYamlBase extends Controller with PlayBodyParsing  with SplitPetstoreApiYamlSecurity {
    private type findPetsByTagsActionRequestType       = (PetsFindByStatusGetStatus)
    private type findPetsByTagsActionType              = findPetsByTagsActionRequestType => Try[(Int, Any)]

    private val errorToStatusfindPetsByTags: PartialFunction[Throwable, Status] = { 
        case _: java.lang.IllegalArgumentException => Status(405)
    
        case _: java.lang.IndexOutOfBoundsException => Status(405)
     } 


    val findPetsByTagsActionConstructor  = new findPetsByTagsSecureAction("write_pets", "read_pets")
    def findPetsByTagsAction = (f: findPetsByTagsActionType) => (tags: PetsFindByStatusGetStatus) => findPetsByTagsActionConstructor { request =>
        val providedTypes = Seq[String]("application/json", "application/xml")

        negotiateContent(request.acceptedTypes, providedTypes).map { findPetsByTagsResponseMimeType =>
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
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidfindPetsByTagsRequest[T <: Any](f: findPetsByTagsActionType)(request: findPetsByTagsActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusfindPetsByTags orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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

        private def placeOrderParser(acceptedTypes: Seq[String], maxLength: Int = parse.DefaultMaxTextLength) = {
            def bodyMimeType: Option[MediaType] => String = mediaType => {
                val requestType = mediaType.toSeq.map {
                    case m: MediaRange => m
                    case MediaType(a,b,c) => new MediaRange(a,b,c,None,Nil)
                }
                negotiateContent(requestType, acceptedTypes).orElse(acceptedTypes.headOption).getOrElse("application/json")
            }
            
            import de.zalando.play.controllers.WrappedBodyParsers
            
            val customParsers = WrappedBodyParsers.optionParser[Order]
            optionParser[Order](bodyMimeType, customParsers, "Invalid StoresOrderPostBody", maxLength)
        }

    val placeOrderActionConstructor  = Action
    def placeOrderAction = (f: placeOrderActionType) => placeOrderActionConstructor(placeOrderParser(Seq[String]())) { request =>
        val providedTypes = Seq[String]("application/json", "application/xml")

        negotiateContent(request.acceptedTypes, providedTypes).map { placeOrderResponseMimeType =>
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
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidplaceOrderRequest[T <: Any](f: placeOrderActionType)(request: placeOrderActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusplaceOrder orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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

        private def createUserParser(acceptedTypes: Seq[String], maxLength: Int = parse.DefaultMaxTextLength) = {
            def bodyMimeType: Option[MediaType] => String = mediaType => {
                val requestType = mediaType.toSeq.map {
                    case m: MediaRange => m
                    case MediaType(a,b,c) => new MediaRange(a,b,c,None,Nil)
                }
                negotiateContent(requestType, acceptedTypes).orElse(acceptedTypes.headOption).getOrElse("application/json")
            }
            
            import de.zalando.play.controllers.WrappedBodyParsers
            
            val customParsers = WrappedBodyParsers.optionParser[User]
            optionParser[User](bodyMimeType, customParsers, "Invalid UsersUsernamePutBody", maxLength)
        }

    val createUserActionConstructor  = Action
    def createUserAction = (f: createUserActionType) => createUserActionConstructor(createUserParser(Seq[String]())) { request =>
        val providedTypes = Seq[String]("application/json", "application/xml")

        negotiateContent(request.acceptedTypes, providedTypes).map { createUserResponseMimeType =>
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
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidcreateUserRequest[T <: Any](f: createUserActionType)(request: createUserActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatuscreateUser orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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

        private def createUsersWithListInputParser(acceptedTypes: Seq[String], maxLength: Int = parse.DefaultMaxTextLength) = {
            def bodyMimeType: Option[MediaType] => String = mediaType => {
                val requestType = mediaType.toSeq.map {
                    case m: MediaRange => m
                    case MediaType(a,b,c) => new MediaRange(a,b,c,None,Nil)
                }
                negotiateContent(requestType, acceptedTypes).orElse(acceptedTypes.headOption).getOrElse("application/json")
            }
            
            import de.zalando.play.controllers.WrappedBodyParsers
            
            val customParsers = WrappedBodyParsers.optionParser[UsersCreateWithListPostBodyOpt]
            optionParser[UsersCreateWithListPostBodyOpt](bodyMimeType, customParsers, "Invalid UsersCreateWithListPostBody", maxLength)
        }

    val createUsersWithListInputActionConstructor  = Action
    def createUsersWithListInputAction = (f: createUsersWithListInputActionType) => createUsersWithListInputActionConstructor(createUsersWithListInputParser(Seq[String]())) { request =>
        val providedTypes = Seq[String]("application/json", "application/xml")

        negotiateContent(request.acceptedTypes, providedTypes).map { createUsersWithListInputResponseMimeType =>
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
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidcreateUsersWithListInputRequest[T <: Any](f: createUsersWithListInputActionType)(request: createUsersWithListInputActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatuscreateUsersWithListInput orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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


    val getUserByNameActionConstructor  = Action
    def getUserByNameAction = (f: getUserByNameActionType) => (username: String) => getUserByNameActionConstructor { request =>
        val providedTypes = Seq[String]("application/json", "application/xml")

        negotiateContent(request.acceptedTypes, providedTypes).map { getUserByNameResponseMimeType =>
                val possibleWriters = Map(
                    404 -> anyToWritable[Null], 
                    400 -> anyToWritable[Null], 
                    200 -> anyToWritable[User]
            )
            
            

                val result =
                        new UsersUsernameGetValidator(username).errors match {
                            case e if e.isEmpty => processValidgetUserByNameRequest(f)((username))(possibleWriters, getUserByNameResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getUserByNameResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetUserByNameRequest[T <: Any](f: getUserByNameActionType)(request: getUserByNameActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetUserByName orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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

        private def updateUserParser(acceptedTypes: Seq[String], maxLength: Int = parse.DefaultMaxTextLength) = {
            def bodyMimeType: Option[MediaType] => String = mediaType => {
                val requestType = mediaType.toSeq.map {
                    case m: MediaRange => m
                    case MediaType(a,b,c) => new MediaRange(a,b,c,None,Nil)
                }
                negotiateContent(requestType, acceptedTypes).orElse(acceptedTypes.headOption).getOrElse("application/json")
            }
            
            import de.zalando.play.controllers.WrappedBodyParsers
            
            val customParsers = WrappedBodyParsers.optionParser[User]
            optionParser[User](bodyMimeType, customParsers, "Invalid UsersUsernamePutBody", maxLength)
        }

    val updateUserActionConstructor  = Action
    def updateUserAction = (f: updateUserActionType) => (username: String) => updateUserActionConstructor(updateUserParser(Seq[String]())) { request =>
        val providedTypes = Seq[String]("application/json", "application/xml")

        negotiateContent(request.acceptedTypes, providedTypes).map { updateUserResponseMimeType =>
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
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidupdateUserRequest[T <: Any](f: updateUserActionType)(request: updateUserActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusupdateUser orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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


    val deleteUserActionConstructor  = Action
    def deleteUserAction = (f: deleteUserActionType) => (username: String) => deleteUserActionConstructor { request =>
        val providedTypes = Seq[String]("application/json", "application/xml")

        negotiateContent(request.acceptedTypes, providedTypes).map { deleteUserResponseMimeType =>
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
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValiddeleteUserRequest[T <: Any](f: deleteUserActionType)(request: deleteUserActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusdeleteUser orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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

        private def updatePetParser(acceptedTypes: Seq[String], maxLength: Int = parse.DefaultMaxTextLength) = {
            def bodyMimeType: Option[MediaType] => String = mediaType => {
                val requestType = mediaType.toSeq.map {
                    case m: MediaRange => m
                    case MediaType(a,b,c) => new MediaRange(a,b,c,None,Nil)
                }
                negotiateContent(requestType, acceptedTypes).orElse(acceptedTypes.headOption).getOrElse("application/json")
            }
            
            
            val customParsers = WrappedBodyParsers.optionParser[Pet]
            optionParser[Pet](bodyMimeType, customParsers, "Invalid PetsPostBody", maxLength)
        }

    val updatePetActionConstructor  = new updatePetSecureAction("write_pets", "read_pets")
    def updatePetAction = (f: updatePetActionType) => updatePetActionConstructor(updatePetParser(Seq[String]("application/json", "application/xml"))) { request =>
        val providedTypes = Seq[String]("application/json", "application/xml")

        negotiateContent(request.acceptedTypes, providedTypes).map { updatePetResponseMimeType =>
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
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidupdatePetRequest[T <: Any](f: updatePetActionType)(request: updatePetActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusupdatePet orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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

        private def addPetParser(acceptedTypes: Seq[String], maxLength: Int = parse.DefaultMaxTextLength) = {
            def bodyMimeType: Option[MediaType] => String = mediaType => {
                val requestType = mediaType.toSeq.map {
                    case m: MediaRange => m
                    case MediaType(a,b,c) => new MediaRange(a,b,c,None,Nil)
                }
                negotiateContent(requestType, acceptedTypes).orElse(acceptedTypes.headOption).getOrElse("application/json")
            }
            
            
            val customParsers = WrappedBodyParsers.optionParser[Pet]
            optionParser[Pet](bodyMimeType, customParsers, "Invalid PetsPostBody", maxLength)
        }

    val addPetActionConstructor  = new addPetSecureAction("write_pets", "read_pets")
    def addPetAction = (f: addPetActionType) => addPetActionConstructor(addPetParser(Seq[String]("application/json", "application/xml"))) { request =>
        val providedTypes = Seq[String]("application/json", "application/xml")

        negotiateContent(request.acceptedTypes, providedTypes).map { addPetResponseMimeType =>
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
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidaddPetRequest[T <: Any](f: addPetActionType)(request: addPetActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusaddPet orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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

        private def createUsersWithArrayInputParser(acceptedTypes: Seq[String], maxLength: Int = parse.DefaultMaxTextLength) = {
            def bodyMimeType: Option[MediaType] => String = mediaType => {
                val requestType = mediaType.toSeq.map {
                    case m: MediaRange => m
                    case MediaType(a,b,c) => new MediaRange(a,b,c,None,Nil)
                }
                negotiateContent(requestType, acceptedTypes).orElse(acceptedTypes.headOption).getOrElse("application/json")
            }
            
            import de.zalando.play.controllers.WrappedBodyParsers
            
            val customParsers = WrappedBodyParsers.optionParser[UsersCreateWithListPostBodyOpt]
            optionParser[UsersCreateWithListPostBodyOpt](bodyMimeType, customParsers, "Invalid UsersCreateWithListPostBody", maxLength)
        }

    val createUsersWithArrayInputActionConstructor  = Action
    def createUsersWithArrayInputAction = (f: createUsersWithArrayInputActionType) => createUsersWithArrayInputActionConstructor(createUsersWithArrayInputParser(Seq[String]())) { request =>
        val providedTypes = Seq[String]("application/json", "application/xml")

        negotiateContent(request.acceptedTypes, providedTypes).map { createUsersWithArrayInputResponseMimeType =>
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
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidcreateUsersWithArrayInputRequest[T <: Any](f: createUsersWithArrayInputActionType)(request: createUsersWithArrayInputActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatuscreateUsersWithArrayInput orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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


    val getOrderByIdActionConstructor  = Action
    def getOrderByIdAction = (f: getOrderByIdActionType) => (orderId: String) => getOrderByIdActionConstructor { request =>
        val providedTypes = Seq[String]("application/json", "application/xml")

        negotiateContent(request.acceptedTypes, providedTypes).map { getOrderByIdResponseMimeType =>
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
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetOrderByIdRequest[T <: Any](f: getOrderByIdActionType)(request: getOrderByIdActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetOrderById orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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


    val deleteOrderActionConstructor  = Action
    def deleteOrderAction = (f: deleteOrderActionType) => (orderId: String) => deleteOrderActionConstructor { request =>
        val providedTypes = Seq[String]("application/json", "application/xml")

        negotiateContent(request.acceptedTypes, providedTypes).map { deleteOrderResponseMimeType =>
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
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValiddeleteOrderRequest[T <: Any](f: deleteOrderActionType)(request: deleteOrderActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusdeleteOrder orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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


    val logoutUserActionConstructor  = Action
    def logoutUserAction = (f: logoutUserActionType) => logoutUserActionConstructor { request =>
        val providedTypes = Seq[String]("application/json", "application/xml")

        negotiateContent(request.acceptedTypes, providedTypes).map { logoutUserResponseMimeType =>
                val possibleWriters = Map.empty[Int,String => Writeable[Any]].withDefaultValue(anyToWritable[Null])
            
            

                val result = processValidlogoutUserRequest(f)()(possibleWriters, logoutUserResponseMimeType)
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidlogoutUserRequest[T <: Any](f: logoutUserActionType)(request: logoutUserActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatuslogoutUser orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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


    val getPetByIdActionConstructor  = new getPetByIdSecureAction("write_pets", "read_pets")
    def getPetByIdAction = (f: getPetByIdActionType) => (petId: Long) => getPetByIdActionConstructor { request =>
        val providedTypes = Seq[String]("application/json", "application/xml")

        negotiateContent(request.acceptedTypes, providedTypes).map { getPetByIdResponseMimeType =>
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
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetPetByIdRequest[T <: Any](f: getPetByIdActionType)(request: getPetByIdActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetPetById orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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


    val updatePetWithFormActionConstructor  = new updatePetWithFormSecureAction("write_pets", "read_pets")
    def updatePetWithFormAction = (f: updatePetWithFormActionType) => (petId: String) => updatePetWithFormActionConstructor { request =>
        val providedTypes = Seq[String]("application/json", "application/xml")

        negotiateContent(request.acceptedTypes, providedTypes).map { updatePetWithFormResponseMimeType =>
                val possibleWriters = Map(
                    405 -> anyToWritable[Null]
            )
            
            val eitherFormParameters = FormDataParser.updatePetWithFormParseForm(request)
            eitherFormParameters match {
                case Left(problem: Seq[String]) =>
                    val msg = problem.mkString("\n")
                    BadRequest(msg)

                case Right((name, status)) =>
            

                val result =
                        new PetsPetIdPostValidator(petId, name, status).errors match {
                            case e if e.isEmpty => processValidupdatePetWithFormRequest(f)((petId, name, status))(possibleWriters, updatePetWithFormResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(updatePetWithFormResponseMimeType)
                                BadRequest(l)
                        }
                result
            
            }
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidupdatePetWithFormRequest[T <: Any](f: updatePetWithFormActionType)(request: updatePetWithFormActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusupdatePetWithForm orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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


    val deletePetActionConstructor  = new deletePetSecureAction("write_pets", "read_pets")
    def deletePetAction = (f: deletePetActionType) => (petId: Long) => deletePetActionConstructor { request =>
        val providedTypes = Seq[String]("application/json", "application/xml")

        negotiateContent(request.acceptedTypes, providedTypes).map { deletePetResponseMimeType =>
                val possibleWriters = Map(
                    400 -> anyToWritable[Null]
            )
            
            val api_key: Either[String,String] =
                fromParameters[String]("header")("api_key", request.headers.toMap)
            
            
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
                    val problem: Seq[String] = Seq(api_key).filter{_.isLeft}.map(_.left.get)
                    val msg = problem.mkString("\n")
                    BadRequest(msg)
                }
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValiddeletePetRequest[T <: Any](f: deletePetActionType)(request: deletePetActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusdeletePet orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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


    val findPetsByStatusActionConstructor  = new findPetsByStatusSecureAction("write_pets", "read_pets")
    def findPetsByStatusAction = (f: findPetsByStatusActionType) => (status: PetsFindByStatusGetStatus) => findPetsByStatusActionConstructor { request =>
        val providedTypes = Seq[String]("application/json", "application/xml")

        negotiateContent(request.acceptedTypes, providedTypes).map { findPetsByStatusResponseMimeType =>
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
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidfindPetsByStatusRequest[T <: Any](f: findPetsByStatusActionType)(request: findPetsByStatusActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusfindPetsByStatus orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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


    val loginUserActionConstructor  = Action
    def loginUserAction = (f: loginUserActionType) => (username: OrderStatus, password: OrderStatus) => loginUserActionConstructor { request =>
        val providedTypes = Seq[String]("application/json", "application/xml")

        negotiateContent(request.acceptedTypes, providedTypes).map { loginUserResponseMimeType =>
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
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidloginUserRequest[T <: Any](f: loginUserActionType)(request: loginUserActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusloginUser orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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
