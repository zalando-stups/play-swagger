package split.petstore.api.yaml

import play.api.mvc.{Action, Controller, Results}
import play.api.http._
import Results.Status

import de.zalando.play.controllers.{PlayBodyParsing, ParsingError, ResultWrapper, ResponseWriters}
import PlayBodyParsing._
import scala.util._
import de.zalando.play.controllers.ArrayWrapper
import org.joda.time.DateTime

import de.zalando.play.controllers.PlayPathBindables



trait SplitPetstoreApiYamlBase extends Controller with PlayBodyParsing  with SplitPetstoreApiYamlSecurity {
    sealed trait findPetsByTagsType[ResultT] extends ResultWrapper[ResultT]
    case class findPetsByTags400(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends findPetsByTagsType[Null] { val statusCode = 400 }
    case class findPetsByTags200(result: Seq[Pet])(implicit val writer: String => Option[Writeable[Seq[Pet]]]) extends findPetsByTagsType[Seq[Pet]] { val statusCode = 200 }
    
    case class findPetsByTagsIllegalArgumentException(result: java.lang.IllegalArgumentException)(implicit val writer: String => Option[Writeable[java.lang.Exception]])  extends findPetsByTagsType[java.lang.IllegalArgumentException] { val statusCode = 405 }
    
    case class findPetsByTagsIndexOutOfBoundsException(result: java.lang.IndexOutOfBoundsException)(implicit val writer: String => Option[Writeable[java.lang.Exception]])  extends findPetsByTagsType[java.lang.IndexOutOfBoundsException] { val statusCode = 405 }
    

    private type findPetsByTagsActionRequestType       = (PetsFindByStatusGetStatus)
    private type findPetsByTagsActionType[T]            = findPetsByTagsActionRequestType => findPetsByTagsType[T]


    val findPetsByTagsActionConstructor  = new findPetsByTagsSecureAction("write_pets", "read_pets")
    def findPetsByTagsAction[T] = (f: findPetsByTagsActionType[T]) => (tags: PetsFindByStatusGetStatus) => findPetsByTagsActionConstructor { request =>
        val providedTypes = Seq[String]("application/json", "application/xml")

        negotiateContent(request.acceptedTypes, providedTypes).map { findPetsByTagsResponseMimeType =>

            
            

                val result =
                        new PetsFindByTagsGetValidator(tags).errors match {
                            case e if e.isEmpty => processValidfindPetsByTagsRequest(f)((tags))(findPetsByTagsResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(findPetsByTagsResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidfindPetsByTagsRequest[T](f: findPetsByTagsActionType[T])(request: findPetsByTagsActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait placeOrderType[ResultT] extends ResultWrapper[ResultT]
    case class placeOrder400(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends placeOrderType[Null] { val statusCode = 400 }
    case class placeOrder200(result: Order)(implicit val writer: String => Option[Writeable[Order]]) extends placeOrderType[Order] { val statusCode = 200 }
    
    case class placeOrderIllegalArgumentException(result: java.lang.IllegalArgumentException)(implicit val writer: String => Option[Writeable[java.lang.Exception]])  extends placeOrderType[java.lang.IllegalArgumentException] { val statusCode = 405 }
    
    case class placeOrderIndexOutOfBoundsException(result: java.lang.IndexOutOfBoundsException)(implicit val writer: String => Option[Writeable[java.lang.Exception]])  extends placeOrderType[java.lang.IndexOutOfBoundsException] { val statusCode = 405 }
    

    private type placeOrderActionRequestType       = (StoresOrderPostBody)
    private type placeOrderActionType[T]            = placeOrderActionRequestType => placeOrderType[T]

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
    def placeOrderAction[T] = (f: placeOrderActionType[T]) => placeOrderActionConstructor(placeOrderParser(Seq[String]())) { request =>
        val providedTypes = Seq[String]("application/json", "application/xml")

        negotiateContent(request.acceptedTypes, providedTypes).map { placeOrderResponseMimeType =>

            val body = request.body
            
            

                val result =
                        new StoresOrderPostValidator(body).errors match {
                            case e if e.isEmpty => processValidplaceOrderRequest(f)((body))(placeOrderResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(placeOrderResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidplaceOrderRequest[T](f: placeOrderActionType[T])(request: placeOrderActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait createUserType[ResultT] extends ResultWrapper[ResultT]
    
    case class createUserIllegalArgumentException(result: java.lang.IllegalArgumentException)(implicit val writer: String => Option[Writeable[java.lang.Exception]])  extends createUserType[java.lang.IllegalArgumentException] { val statusCode = 405 }
    
    case class createUserIndexOutOfBoundsException(result: java.lang.IndexOutOfBoundsException)(implicit val writer: String => Option[Writeable[java.lang.Exception]])  extends createUserType[java.lang.IndexOutOfBoundsException] { val statusCode = 405 }
    

    private type createUserActionRequestType       = (UsersUsernamePutBody)
    private type createUserActionType[T]            = createUserActionRequestType => createUserType[T]

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
    def createUserAction[T] = (f: createUserActionType[T]) => createUserActionConstructor(createUserParser(Seq[String]())) { request =>
        val providedTypes = Seq[String]("application/json", "application/xml")

        negotiateContent(request.acceptedTypes, providedTypes).map { createUserResponseMimeType =>

            val body = request.body
            
            

                val result =
                        new UsersPostValidator(body).errors match {
                            case e if e.isEmpty => processValidcreateUserRequest(f)((body))(createUserResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(createUserResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidcreateUserRequest[T](f: createUserActionType[T])(request: createUserActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait createUsersWithListInputType[ResultT] extends ResultWrapper[ResultT]
    
    case class createUsersWithListInputIllegalArgumentException(result: java.lang.IllegalArgumentException)(implicit val writer: String => Option[Writeable[java.lang.Exception]])  extends createUsersWithListInputType[java.lang.IllegalArgumentException] { val statusCode = 405 }
    
    case class createUsersWithListInputIndexOutOfBoundsException(result: java.lang.IndexOutOfBoundsException)(implicit val writer: String => Option[Writeable[java.lang.Exception]])  extends createUsersWithListInputType[java.lang.IndexOutOfBoundsException] { val statusCode = 405 }
    

    private type createUsersWithListInputActionRequestType       = (UsersCreateWithListPostBody)
    private type createUsersWithListInputActionType[T]            = createUsersWithListInputActionRequestType => createUsersWithListInputType[T]

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
    def createUsersWithListInputAction[T] = (f: createUsersWithListInputActionType[T]) => createUsersWithListInputActionConstructor(createUsersWithListInputParser(Seq[String]())) { request =>
        val providedTypes = Seq[String]("application/json", "application/xml")

        negotiateContent(request.acceptedTypes, providedTypes).map { createUsersWithListInputResponseMimeType =>

            val body = request.body
            
            

                val result =
                        new UsersCreateWithListPostValidator(body).errors match {
                            case e if e.isEmpty => processValidcreateUsersWithListInputRequest(f)((body))(createUsersWithListInputResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(createUsersWithListInputResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidcreateUsersWithListInputRequest[T](f: createUsersWithListInputActionType[T])(request: createUsersWithListInputActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait getUserByNameType[ResultT] extends ResultWrapper[ResultT]
    case class getUserByName404(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends getUserByNameType[Null] { val statusCode = 404 }
    case class getUserByName400(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends getUserByNameType[Null] { val statusCode = 400 }
    case class getUserByName200(result: User)(implicit val writer: String => Option[Writeable[User]]) extends getUserByNameType[User] { val statusCode = 200 }
    
    case class getUserByNameIllegalArgumentException(result: java.lang.IllegalArgumentException)(implicit val writer: String => Option[Writeable[java.lang.Exception]])  extends getUserByNameType[java.lang.IllegalArgumentException] { val statusCode = 405 }
    
    case class getUserByNameIndexOutOfBoundsException(result: java.lang.IndexOutOfBoundsException)(implicit val writer: String => Option[Writeable[java.lang.Exception]])  extends getUserByNameType[java.lang.IndexOutOfBoundsException] { val statusCode = 405 }
    

    private type getUserByNameActionRequestType       = (String)
    private type getUserByNameActionType[T]            = getUserByNameActionRequestType => getUserByNameType[T]


    val getUserByNameActionConstructor  = Action
    def getUserByNameAction[T] = (f: getUserByNameActionType[T]) => (username: String) => getUserByNameActionConstructor { request =>
        val providedTypes = Seq[String]("application/json", "application/xml")

        negotiateContent(request.acceptedTypes, providedTypes).map { getUserByNameResponseMimeType =>

            
            

                val result =
                        new UsersUsernameGetValidator(username).errors match {
                            case e if e.isEmpty => processValidgetUserByNameRequest(f)((username))(getUserByNameResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getUserByNameResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetUserByNameRequest[T](f: getUserByNameActionType[T])(request: getUserByNameActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait updateUserType[ResultT] extends ResultWrapper[ResultT]
    case class updateUser404(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends updateUserType[Null] { val statusCode = 404 }
    case class updateUser400(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends updateUserType[Null] { val statusCode = 400 }
    
    case class updateUserIllegalArgumentException(result: java.lang.IllegalArgumentException)(implicit val writer: String => Option[Writeable[java.lang.Exception]])  extends updateUserType[java.lang.IllegalArgumentException] { val statusCode = 405 }
    
    case class updateUserIndexOutOfBoundsException(result: java.lang.IndexOutOfBoundsException)(implicit val writer: String => Option[Writeable[java.lang.Exception]])  extends updateUserType[java.lang.IndexOutOfBoundsException] { val statusCode = 405 }
    

    private type updateUserActionRequestType       = (String, UsersUsernamePutBody)
    private type updateUserActionType[T]            = updateUserActionRequestType => updateUserType[T]

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
    def updateUserAction[T] = (f: updateUserActionType[T]) => (username: String) => updateUserActionConstructor(updateUserParser(Seq[String]())) { request =>
        val providedTypes = Seq[String]("application/json", "application/xml")

        negotiateContent(request.acceptedTypes, providedTypes).map { updateUserResponseMimeType =>

            val body = request.body
            
            

                val result =
                        new UsersUsernamePutValidator(username, body).errors match {
                            case e if e.isEmpty => processValidupdateUserRequest(f)((username, body))(updateUserResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(updateUserResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidupdateUserRequest[T](f: updateUserActionType[T])(request: updateUserActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait deleteUserType[ResultT] extends ResultWrapper[ResultT]
    case class deleteUser404(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends deleteUserType[Null] { val statusCode = 404 }
    case class deleteUser400(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends deleteUserType[Null] { val statusCode = 400 }
    
    case class deleteUserIllegalArgumentException(result: java.lang.IllegalArgumentException)(implicit val writer: String => Option[Writeable[java.lang.Exception]])  extends deleteUserType[java.lang.IllegalArgumentException] { val statusCode = 405 }
    
    case class deleteUserIndexOutOfBoundsException(result: java.lang.IndexOutOfBoundsException)(implicit val writer: String => Option[Writeable[java.lang.Exception]])  extends deleteUserType[java.lang.IndexOutOfBoundsException] { val statusCode = 405 }
    

    private type deleteUserActionRequestType       = (String)
    private type deleteUserActionType[T]            = deleteUserActionRequestType => deleteUserType[T]


    val deleteUserActionConstructor  = Action
    def deleteUserAction[T] = (f: deleteUserActionType[T]) => (username: String) => deleteUserActionConstructor { request =>
        val providedTypes = Seq[String]("application/json", "application/xml")

        negotiateContent(request.acceptedTypes, providedTypes).map { deleteUserResponseMimeType =>

            
            

                val result =
                        new UsersUsernameDeleteValidator(username).errors match {
                            case e if e.isEmpty => processValiddeleteUserRequest(f)((username))(deleteUserResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(deleteUserResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValiddeleteUserRequest[T](f: deleteUserActionType[T])(request: deleteUserActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait updatePetType[ResultT] extends ResultWrapper[ResultT]
    case class updatePet405(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends updatePetType[Null] { val statusCode = 405 }
    case class updatePet404(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends updatePetType[Null] { val statusCode = 404 }
    case class updatePet400(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends updatePetType[Null] { val statusCode = 400 }
    
    case class updatePetIllegalArgumentException(result: java.lang.IllegalArgumentException)(implicit val writer: String => Option[Writeable[java.lang.Exception]])  extends updatePetType[java.lang.IllegalArgumentException] { val statusCode = 405 }
    
    case class updatePetIndexOutOfBoundsException(result: java.lang.IndexOutOfBoundsException)(implicit val writer: String => Option[Writeable[java.lang.Exception]])  extends updatePetType[java.lang.IndexOutOfBoundsException] { val statusCode = 405 }
    

    private type updatePetActionRequestType       = (PetsPostBody)
    private type updatePetActionType[T]            = updatePetActionRequestType => updatePetType[T]

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
    def updatePetAction[T] = (f: updatePetActionType[T]) => updatePetActionConstructor(updatePetParser(Seq[String]("application/json", "application/xml"))) { request =>
        val providedTypes = Seq[String]("application/json", "application/xml")

        negotiateContent(request.acceptedTypes, providedTypes).map { updatePetResponseMimeType =>

            val body = request.body
            
            

                val result =
                        new PetsPutValidator(body).errors match {
                            case e if e.isEmpty => processValidupdatePetRequest(f)((body))(updatePetResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(updatePetResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidupdatePetRequest[T](f: updatePetActionType[T])(request: updatePetActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait addPetType[ResultT] extends ResultWrapper[ResultT]
    case class addPet405(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends addPetType[Null] { val statusCode = 405 }
    
    case class addPetIllegalArgumentException(result: java.lang.IllegalArgumentException)(implicit val writer: String => Option[Writeable[java.lang.Exception]])  extends addPetType[java.lang.IllegalArgumentException] { val statusCode = 405 }
    
    case class addPetIndexOutOfBoundsException(result: java.lang.IndexOutOfBoundsException)(implicit val writer: String => Option[Writeable[java.lang.Exception]])  extends addPetType[java.lang.IndexOutOfBoundsException] { val statusCode = 405 }
    

    private type addPetActionRequestType       = (PetsPostBody)
    private type addPetActionType[T]            = addPetActionRequestType => addPetType[T]

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
    def addPetAction[T] = (f: addPetActionType[T]) => addPetActionConstructor(addPetParser(Seq[String]("application/json", "application/xml"))) { request =>
        val providedTypes = Seq[String]("application/json", "application/xml")

        negotiateContent(request.acceptedTypes, providedTypes).map { addPetResponseMimeType =>

            val body = request.body
            
            

                val result =
                        new PetsPostValidator(body).errors match {
                            case e if e.isEmpty => processValidaddPetRequest(f)((body))(addPetResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(addPetResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidaddPetRequest[T](f: addPetActionType[T])(request: addPetActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait createUsersWithArrayInputType[ResultT] extends ResultWrapper[ResultT]
    
    case class createUsersWithArrayInputIllegalArgumentException(result: java.lang.IllegalArgumentException)(implicit val writer: String => Option[Writeable[java.lang.Exception]])  extends createUsersWithArrayInputType[java.lang.IllegalArgumentException] { val statusCode = 405 }
    
    case class createUsersWithArrayInputIndexOutOfBoundsException(result: java.lang.IndexOutOfBoundsException)(implicit val writer: String => Option[Writeable[java.lang.Exception]])  extends createUsersWithArrayInputType[java.lang.IndexOutOfBoundsException] { val statusCode = 405 }
    

    private type createUsersWithArrayInputActionRequestType       = (UsersCreateWithListPostBody)
    private type createUsersWithArrayInputActionType[T]            = createUsersWithArrayInputActionRequestType => createUsersWithArrayInputType[T]

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
    def createUsersWithArrayInputAction[T] = (f: createUsersWithArrayInputActionType[T]) => createUsersWithArrayInputActionConstructor(createUsersWithArrayInputParser(Seq[String]())) { request =>
        val providedTypes = Seq[String]("application/json", "application/xml")

        negotiateContent(request.acceptedTypes, providedTypes).map { createUsersWithArrayInputResponseMimeType =>

            val body = request.body
            
            

                val result =
                        new UsersCreateWithArrayPostValidator(body).errors match {
                            case e if e.isEmpty => processValidcreateUsersWithArrayInputRequest(f)((body))(createUsersWithArrayInputResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(createUsersWithArrayInputResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidcreateUsersWithArrayInputRequest[T](f: createUsersWithArrayInputActionType[T])(request: createUsersWithArrayInputActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait getOrderByIdType[ResultT] extends ResultWrapper[ResultT]
    case class getOrderById404(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends getOrderByIdType[Null] { val statusCode = 404 }
    case class getOrderById400(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends getOrderByIdType[Null] { val statusCode = 400 }
    case class getOrderById200(result: Order)(implicit val writer: String => Option[Writeable[Order]]) extends getOrderByIdType[Order] { val statusCode = 200 }
    
    case class getOrderByIdIllegalArgumentException(result: java.lang.IllegalArgumentException)(implicit val writer: String => Option[Writeable[java.lang.Exception]])  extends getOrderByIdType[java.lang.IllegalArgumentException] { val statusCode = 405 }
    
    case class getOrderByIdIndexOutOfBoundsException(result: java.lang.IndexOutOfBoundsException)(implicit val writer: String => Option[Writeable[java.lang.Exception]])  extends getOrderByIdType[java.lang.IndexOutOfBoundsException] { val statusCode = 405 }
    

    private type getOrderByIdActionRequestType       = (String)
    private type getOrderByIdActionType[T]            = getOrderByIdActionRequestType => getOrderByIdType[T]


    val getOrderByIdActionConstructor  = Action
    def getOrderByIdAction[T] = (f: getOrderByIdActionType[T]) => (orderId: String) => getOrderByIdActionConstructor { request =>
        val providedTypes = Seq[String]("application/json", "application/xml")

        negotiateContent(request.acceptedTypes, providedTypes).map { getOrderByIdResponseMimeType =>

            
            

                val result =
                        new StoresOrderOrderIdGetValidator(orderId).errors match {
                            case e if e.isEmpty => processValidgetOrderByIdRequest(f)((orderId))(getOrderByIdResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getOrderByIdResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetOrderByIdRequest[T](f: getOrderByIdActionType[T])(request: getOrderByIdActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait deleteOrderType[ResultT] extends ResultWrapper[ResultT]
    case class deleteOrder404(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends deleteOrderType[Null] { val statusCode = 404 }
    case class deleteOrder400(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends deleteOrderType[Null] { val statusCode = 400 }
    
    case class deleteOrderIllegalArgumentException(result: java.lang.IllegalArgumentException)(implicit val writer: String => Option[Writeable[java.lang.Exception]])  extends deleteOrderType[java.lang.IllegalArgumentException] { val statusCode = 405 }
    
    case class deleteOrderIndexOutOfBoundsException(result: java.lang.IndexOutOfBoundsException)(implicit val writer: String => Option[Writeable[java.lang.Exception]])  extends deleteOrderType[java.lang.IndexOutOfBoundsException] { val statusCode = 405 }
    

    private type deleteOrderActionRequestType       = (String)
    private type deleteOrderActionType[T]            = deleteOrderActionRequestType => deleteOrderType[T]


    val deleteOrderActionConstructor  = Action
    def deleteOrderAction[T] = (f: deleteOrderActionType[T]) => (orderId: String) => deleteOrderActionConstructor { request =>
        val providedTypes = Seq[String]("application/json", "application/xml")

        negotiateContent(request.acceptedTypes, providedTypes).map { deleteOrderResponseMimeType =>

            
            

                val result =
                        new StoresOrderOrderIdDeleteValidator(orderId).errors match {
                            case e if e.isEmpty => processValiddeleteOrderRequest(f)((orderId))(deleteOrderResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(deleteOrderResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValiddeleteOrderRequest[T](f: deleteOrderActionType[T])(request: deleteOrderActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait logoutUserType[ResultT] extends ResultWrapper[ResultT]
    
    case class logoutUserIllegalArgumentException(result: java.lang.IllegalArgumentException)(implicit val writer: String => Option[Writeable[java.lang.Exception]])  extends logoutUserType[java.lang.IllegalArgumentException] { val statusCode = 405 }
    
    case class logoutUserIndexOutOfBoundsException(result: java.lang.IndexOutOfBoundsException)(implicit val writer: String => Option[Writeable[java.lang.Exception]])  extends logoutUserType[java.lang.IndexOutOfBoundsException] { val statusCode = 405 }
    

    private type logoutUserActionRequestType       = (Unit)
    private type logoutUserActionType[T]            = logoutUserActionRequestType => logoutUserType[T]


    val logoutUserActionConstructor  = Action
    def logoutUserAction[T] = (f: logoutUserActionType[T]) => logoutUserActionConstructor { request =>
        val providedTypes = Seq[String]("application/json", "application/xml")

        negotiateContent(request.acceptedTypes, providedTypes).map { logoutUserResponseMimeType =>

            
            

                val result = processValidlogoutUserRequest(f)()(logoutUserResponseMimeType)
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidlogoutUserRequest[T](f: logoutUserActionType[T])(request: logoutUserActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait getPetByIdType[ResultT] extends ResultWrapper[ResultT]
    case class getPetById404(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends getPetByIdType[Null] { val statusCode = 404 }
    case class getPetById400(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends getPetByIdType[Null] { val statusCode = 400 }
    case class getPetById200(result: Pet)(implicit val writer: String => Option[Writeable[Pet]]) extends getPetByIdType[Pet] { val statusCode = 200 }
    
    case class getPetByIdIllegalArgumentException(result: java.lang.IllegalArgumentException)(implicit val writer: String => Option[Writeable[java.lang.Exception]])  extends getPetByIdType[java.lang.IllegalArgumentException] { val statusCode = 405 }
    
    case class getPetByIdIndexOutOfBoundsException(result: java.lang.IndexOutOfBoundsException)(implicit val writer: String => Option[Writeable[java.lang.Exception]])  extends getPetByIdType[java.lang.IndexOutOfBoundsException] { val statusCode = 405 }
    

    private type getPetByIdActionRequestType       = (Long)
    private type getPetByIdActionType[T]            = getPetByIdActionRequestType => getPetByIdType[T]


    val getPetByIdActionConstructor  = new getPetByIdSecureAction("write_pets", "read_pets")
    def getPetByIdAction[T] = (f: getPetByIdActionType[T]) => (petId: Long) => getPetByIdActionConstructor { request =>
        val providedTypes = Seq[String]("application/json", "application/xml")

        negotiateContent(request.acceptedTypes, providedTypes).map { getPetByIdResponseMimeType =>

            
            

                val result =
                        new PetsPetIdGetValidator(petId).errors match {
                            case e if e.isEmpty => processValidgetPetByIdRequest(f)((petId))(getPetByIdResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getPetByIdResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetPetByIdRequest[T](f: getPetByIdActionType[T])(request: getPetByIdActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait updatePetWithFormType[ResultT] extends ResultWrapper[ResultT]
    case class updatePetWithForm405(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends updatePetWithFormType[Null] { val statusCode = 405 }
    
    case class updatePetWithFormIllegalArgumentException(result: java.lang.IllegalArgumentException)(implicit val writer: String => Option[Writeable[java.lang.Exception]])  extends updatePetWithFormType[java.lang.IllegalArgumentException] { val statusCode = 405 }
    
    case class updatePetWithFormIndexOutOfBoundsException(result: java.lang.IndexOutOfBoundsException)(implicit val writer: String => Option[Writeable[java.lang.Exception]])  extends updatePetWithFormType[java.lang.IndexOutOfBoundsException] { val statusCode = 405 }
    

    private type updatePetWithFormActionRequestType       = (String, String, String)
    private type updatePetWithFormActionType[T]            = updatePetWithFormActionRequestType => updatePetWithFormType[T]


    val updatePetWithFormActionConstructor  = new updatePetWithFormSecureAction("write_pets", "read_pets")
    def updatePetWithFormAction[T] = (f: updatePetWithFormActionType[T]) => (petId: String) => updatePetWithFormActionConstructor { request =>
        val providedTypes = Seq[String]("application/json", "application/xml")

        negotiateContent(request.acceptedTypes, providedTypes).map { updatePetWithFormResponseMimeType =>

            
            val eitherFormParameters = FormDataParser.updatePetWithFormParseForm(request)
            eitherFormParameters match {
                case Left(problem: Seq[String]) =>
                    val msg = problem.mkString("\n")
                    BadRequest(msg)

                case Right((name, status)) =>
            

                val result =
                        new PetsPetIdPostValidator(petId, name, status).errors match {
                            case e if e.isEmpty => processValidupdatePetWithFormRequest(f)((petId, name, status))(updatePetWithFormResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(updatePetWithFormResponseMimeType)
                                BadRequest(l)
                        }
                result
            
            }
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidupdatePetWithFormRequest[T](f: updatePetWithFormActionType[T])(request: updatePetWithFormActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait deletePetType[ResultT] extends ResultWrapper[ResultT]
    case class deletePet400(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends deletePetType[Null] { val statusCode = 400 }
    
    case class deletePetIllegalArgumentException(result: java.lang.IllegalArgumentException)(implicit val writer: String => Option[Writeable[java.lang.Exception]])  extends deletePetType[java.lang.IllegalArgumentException] { val statusCode = 405 }
    
    case class deletePetIndexOutOfBoundsException(result: java.lang.IndexOutOfBoundsException)(implicit val writer: String => Option[Writeable[java.lang.Exception]])  extends deletePetType[java.lang.IndexOutOfBoundsException] { val statusCode = 405 }
    

    private type deletePetActionRequestType       = (String, Long)
    private type deletePetActionType[T]            = deletePetActionRequestType => deletePetType[T]


    val deletePetActionConstructor  = new deletePetSecureAction("write_pets", "read_pets")
    def deletePetAction[T] = (f: deletePetActionType[T]) => (petId: Long) => deletePetActionConstructor { request =>
        val providedTypes = Seq[String]("application/json", "application/xml")

        negotiateContent(request.acceptedTypes, providedTypes).map { deletePetResponseMimeType =>

            
            val api_key: Either[String,String] =
                fromParameters[String]("header")("api_key", request.headers.toMap)
            
            
                (api_key) match {
                    case (Right(api_key)) =>
            

                val result =
                        new PetsPetIdDeleteValidator(api_key, petId).errors match {
                            case e if e.isEmpty => processValiddeletePetRequest(f)((api_key, petId))(deletePetResponseMimeType)
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

    private def processValiddeletePetRequest[T](f: deletePetActionType[T])(request: deletePetActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait findPetsByStatusType[ResultT] extends ResultWrapper[ResultT]
    case class findPetsByStatus200(result: Seq[Pet])(implicit val writer: String => Option[Writeable[Seq[Pet]]]) extends findPetsByStatusType[Seq[Pet]] { val statusCode = 200 }
    case class findPetsByStatus400(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends findPetsByStatusType[Null] { val statusCode = 400 }
    
    case class findPetsByStatusIllegalArgumentException(result: java.lang.IllegalArgumentException)(implicit val writer: String => Option[Writeable[java.lang.Exception]])  extends findPetsByStatusType[java.lang.IllegalArgumentException] { val statusCode = 405 }
    
    case class findPetsByStatusIndexOutOfBoundsException(result: java.lang.IndexOutOfBoundsException)(implicit val writer: String => Option[Writeable[java.lang.Exception]])  extends findPetsByStatusType[java.lang.IndexOutOfBoundsException] { val statusCode = 405 }
    

    private type findPetsByStatusActionRequestType       = (PetsFindByStatusGetStatus)
    private type findPetsByStatusActionType[T]            = findPetsByStatusActionRequestType => findPetsByStatusType[T]


    val findPetsByStatusActionConstructor  = new findPetsByStatusSecureAction("write_pets", "read_pets")
    def findPetsByStatusAction[T] = (f: findPetsByStatusActionType[T]) => (status: PetsFindByStatusGetStatus) => findPetsByStatusActionConstructor { request =>
        val providedTypes = Seq[String]("application/json", "application/xml")

        negotiateContent(request.acceptedTypes, providedTypes).map { findPetsByStatusResponseMimeType =>

            
            

                val result =
                        new PetsFindByStatusGetValidator(status).errors match {
                            case e if e.isEmpty => processValidfindPetsByStatusRequest(f)((status))(findPetsByStatusResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(findPetsByStatusResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidfindPetsByStatusRequest[T](f: findPetsByStatusActionType[T])(request: findPetsByStatusActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait loginUserType[ResultT] extends ResultWrapper[ResultT]
    case class loginUser200(result: String)(implicit val writer: String => Option[Writeable[String]]) extends loginUserType[String] { val statusCode = 200 }
    case class loginUser400(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends loginUserType[Null] { val statusCode = 400 }
    
    case class loginUserIllegalArgumentException(result: java.lang.IllegalArgumentException)(implicit val writer: String => Option[Writeable[java.lang.Exception]])  extends loginUserType[java.lang.IllegalArgumentException] { val statusCode = 405 }
    
    case class loginUserIndexOutOfBoundsException(result: java.lang.IndexOutOfBoundsException)(implicit val writer: String => Option[Writeable[java.lang.Exception]])  extends loginUserType[java.lang.IndexOutOfBoundsException] { val statusCode = 405 }
    

    private type loginUserActionRequestType       = (OrderStatus, OrderStatus)
    private type loginUserActionType[T]            = loginUserActionRequestType => loginUserType[T]


    val loginUserActionConstructor  = Action
    def loginUserAction[T] = (f: loginUserActionType[T]) => (username: OrderStatus, password: OrderStatus) => loginUserActionConstructor { request =>
        val providedTypes = Seq[String]("application/json", "application/xml")

        negotiateContent(request.acceptedTypes, providedTypes).map { loginUserResponseMimeType =>

            
            

                val result =
                        new UsersLoginGetValidator(username, password).errors match {
                            case e if e.isEmpty => processValidloginUserRequest(f)((username, password))(loginUserResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(loginUserResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidloginUserRequest[T](f: loginUserActionType[T])(request: loginUserActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    case object EmptyReturn extends ResultWrapper[Results.EmptyContent]         with createUserType[Results.EmptyContent]    with createUsersWithListInputType[Results.EmptyContent]                   with createUsersWithArrayInputType[Results.EmptyContent]          with logoutUserType[Results.EmptyContent]                  { val statusCode = 204; val result = Results.EmptyContent(); val writer = (x: String) => Some(new DefaultWriteables{}.writeableOf_EmptyContent); override def toResult(mimeType: String): Option[play.api.mvc.Result] = Some(Results.NoContent) }
    case object NotImplementedYet extends ResultWrapper[Results.EmptyContent]  with findPetsByTagsType[Results.EmptyContent] with placeOrderType[Results.EmptyContent] with createUserType[Results.EmptyContent] with createUsersWithListInputType[Results.EmptyContent] with getUserByNameType[Results.EmptyContent] with updateUserType[Results.EmptyContent] with deleteUserType[Results.EmptyContent] with updatePetType[Results.EmptyContent] with addPetType[Results.EmptyContent] with createUsersWithArrayInputType[Results.EmptyContent] with getOrderByIdType[Results.EmptyContent] with deleteOrderType[Results.EmptyContent] with logoutUserType[Results.EmptyContent] with getPetByIdType[Results.EmptyContent] with updatePetWithFormType[Results.EmptyContent] with deletePetType[Results.EmptyContent] with findPetsByStatusType[Results.EmptyContent] with loginUserType[Results.EmptyContent] { val statusCode = 501; val result = Results.EmptyContent(); val writer = (x: String) => Some(new DefaultWriteables{}.writeableOf_EmptyContent); override def toResult(mimeType: String): Option[play.api.mvc.Result] = Some(Results.NotImplemented) }
}
