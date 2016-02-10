package expanded

import play.api.mvc.{Action, Controller, Results}
import play.api.http.Writeable
import Results.Status
import de.zalando.play.controllers.{PlayBodyParsing, ParsingError}
import PlayBodyParsing._
import scala.util._
import de.zalando.play.controllers.ArrayWrapper
import de.zalando.play.controllers.PlayPathBindables




trait Expanded_polymorphismYamlBase extends Controller with PlayBodyParsing {
    private type findPetsActionRequestType       = (PetsGetTags, PetsGetLimit)
    private type findPetsActionType              = findPetsActionRequestType => Try[(Int, Any)]

    private val errorToStatusfindPets: PartialFunction[Throwable, Status] = {
        case _: java.util.NoSuchElementException => Status(404)
     } 

    def findPetsAction = (f: findPetsActionType) => (tags: PetsGetTags, limit: PetsGetLimit) => Action {
        val findPetsResponseMimeType    = "application/json"
        val possibleWriters = Map(
            200 -> anyToWritable[Seq[Pet]]
        ).withDefaultValue(anyToWritable[Error])        
            val result =                
                    new PetsGetValidator(tags, limit).errors match {
                        case e if e.isEmpty => processValidfindPetsRequest(f)((tags, limit))(possibleWriters, findPetsResponseMimeType)
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(findPetsResponseMimeType)
                            BadRequest(l)
                    }
                
            result
    }

    private def processValidfindPetsRequest[T <: Any](f: findPetsActionType)(request: findPetsActionRequestType)(writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusfindPets orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val findPetsWritableJson = writer(mimeType)
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
    private type addPetActionRequestType       = (NewPet)
    private type addPetActionType              = addPetActionRequestType => Try[(Int, Any)]

    private val errorToStatusaddPet: PartialFunction[Throwable, Status] = {
        case _: java.util.NoSuchElementException => Status(404)
     } 
        private def addPetParser(maxLength: Int = parse.DefaultMaxTextLength) = anyParser[NewPet]("application/json", "Invalid NewPet", maxLength)

    def addPetAction = (f: addPetActionType) => Action(addPetParser()) { request =>
        val addPetResponseMimeType    = "application/json"
        val possibleWriters = Map(
            200 -> anyToWritable[Pet]
        ).withDefaultValue(anyToWritable[Error])        
        val pet = request.body
        
            val result =                
                    new PetsPostValidator(pet).errors match {
                        case e if e.isEmpty => processValidaddPetRequest(f)((pet))(possibleWriters, addPetResponseMimeType)
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
    private type deletePetActionRequestType       = (Long)
    private type deletePetActionType              = deletePetActionRequestType => Try[(Int, Any)]

    private val errorToStatusdeletePet: PartialFunction[Throwable, Status] = {
        case _: java.util.NoSuchElementException => Status(404)
     } 

    def deletePetAction = (f: deletePetActionType) => (id: Long) => Action {
        val deletePetResponseMimeType    = "application/json"
        val possibleWriters = Map(
            204 -> anyToWritable[Null]
        ).withDefaultValue(anyToWritable[Error])        
            val result =                
                    new PetsIdDeleteValidator(id).errors match {
                        case e if e.isEmpty => processValiddeletePetRequest(f)((id))(possibleWriters, deletePetResponseMimeType)
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(deletePetResponseMimeType)
                            BadRequest(l)
                    }
                
            result
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
}
