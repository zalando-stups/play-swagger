package expanded

import play.api.mvc.{Action, Controller, Results}
import play.api.http._
import Results.Status
import de.zalando.play.controllers.{PlayBodyParsing, ParsingError, ResultWrapper}
import PlayBodyParsing._
import scala.util._
// import PickAvailableWriteable._ // if you want play to pick the first available writable
import de.zalando.play.controllers.ArrayWrapper

import de.zalando.play.controllers.PlayPathBindables



trait Expanded_polymorphismYamlBase extends Controller with PlayBodyParsing {
    sealed trait findPetsType[Result] extends ResultWrapper[Result]
    case class findPets200(result: Seq[Pet])(implicit val writers: List[Writeable[Seq[Pet]]]) extends findPetsType[Seq[Pet]] { val statusCode = 200 }
    
    case class findPetsjava.util.NoSuchElementException(result: java.util.NoSuchElementException) extends findPetsType[java.util.NoSuchElementException] { val statusCode = 404 }
    

    private type findPetsActionRequestType       = (PetsGetTags, PetsGetLimit)
    private type findPetsActionType[T]            = findPetsActionRequestType => findPetsType[T]


    val findPetsActionConstructor  = Action
    def findPetsAction[T] = (f: findPetsActionType[T]) => (tags: PetsGetTags, limit: PetsGetLimit) => findPetsActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { findPetsResponseMimeType =>

            

                val result =
                        new PetsGetValidator(tags, limit).errors match {
                            case e if e.isEmpty => processValidfindPetsRequest(f)((tags, limit))(findPetsResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(findPetsResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidfindPetsRequest[T](f: findPetsActionType[T])(request: findPetsActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait addPetType[Result] extends ResultWrapper[Result]
    case class addPet200(result: Pet)(implicit val writers: List[Writeable[Pet]]) extends addPetType[Pet] { val statusCode = 200 }
    
    case class addPetjava.util.NoSuchElementException(result: java.util.NoSuchElementException) extends addPetType[java.util.NoSuchElementException] { val statusCode = 404 }
    

    private type addPetActionRequestType       = (NewPet)
    private type addPetActionType[T]            = addPetActionRequestType => addPetType[T]

        private def addPetParser(acceptedTypes: Seq[String], maxLength: Int = parse.DefaultMaxTextLength) = {
            def bodyMimeType: Option[MediaType] => String = mediaType => {
                val requestType = mediaType.toSeq.map {
                    case m: MediaRange => m
                    case MediaType(a,b,c) => new MediaRange(a,b,c,None,Nil)
                }
                negotiateContent(requestType, acceptedTypes).orElse(acceptedTypes.headOption).getOrElse("application/json")
            }
            
            import de.zalando.play.controllers.WrappedBodyParsers
            
            val customParsers = WrappedBodyParsers.anyParser[NewPet]
            anyParser[NewPet](bodyMimeType, customParsers, "Invalid NewPet", maxLength)
        }

    val addPetActionConstructor  = Action
    def addPetAction[T] = (f: addPetActionType[T]) => addPetActionConstructor(addPetParser(Seq[String]("application/json"))) { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { addPetResponseMimeType =>

            val pet = request.body
            

                val result =
                        new PetsPostValidator(pet).errors match {
                            case e if e.isEmpty => processValidaddPetRequest(f)((pet))(addPetResponseMimeType)
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
    sealed trait deletePetType[Result] extends ResultWrapper[Result]
    case class deletePet204(result: Null)(implicit val writers: List[Writeable[Null]]) extends deletePetType[Null] { val statusCode = 204 }
    
    case class deletePetjava.util.NoSuchElementException(result: java.util.NoSuchElementException) extends deletePetType[java.util.NoSuchElementException] { val statusCode = 404 }
    

    private type deletePetActionRequestType       = (Long)
    private type deletePetActionType[T]            = deletePetActionRequestType => deletePetType[T]


    val deletePetActionConstructor  = Action
    def deletePetAction[T] = (f: deletePetActionType[T]) => (id: Long) => deletePetActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { deletePetResponseMimeType =>

            

                val result =
                        new PetsIdDeleteValidator(id).errors match {
                            case e if e.isEmpty => processValiddeletePetRequest(f)((id))(deletePetResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(deletePetResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValiddeletePetRequest[T](f: deletePetActionType[T])(request: deletePetActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    case object EmptyReturn extends ResultWrapper[Results.EmptyContent]           { val statusCode = 204; val result = Results.EmptyContent(); val writers = List(new DefaultWriteables{}.writeableOf_EmptyContent); override def toResult(mimeType: String): Option[play.api.mvc.Result] = Some(Results.NoContent) }
    case object NotImplementedYet extends ResultWrapper[Results.EmptyContent]  with findPetsType[Results.EmptyContent] with addPetType[Results.EmptyContent] with deletePetType[Results.EmptyContent] { val statusCode = 501; val result = Results.EmptyContent(); val writers = List(new DefaultWriteables{}.writeableOf_EmptyContent); override def toResult(mimeType: String): Option[play.api.mvc.Result] = Some(Results.NotImplemented) }
}
