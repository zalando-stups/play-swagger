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
    private type addPetActionRequestType       = (NewPet)
    private type addPetActionType              = addPetActionRequestType => Try[(Int, Any)]

    private val errorToStatusaddPet: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]
        private def addPetParser(maxLength: Int = parse.DefaultMaxTextLength) = anyParser[NewPet]("application/json", "Invalid NewPet", maxLength)

    def addPetAction = (f: addPetActionType) => Action(addPetParser()) { request =>        val addPetResponseMimeType    = "application/json"

        val possibleWriters = Map(
                200 -> anyToWritable[Pet]
        ).withDefaultValue(anyToWritable[ErrorModel])        
        val pet = request.body
        
            val result =                
                    new PetsPostValidator(pet).errors match {
                        case e if e.isEmpty => processValidaddPetRequest(f)((pet), possibleWriters, addPetResponseMimeType)
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(addPetResponseMimeType)
                            BadRequest(l)
                    }
                
            result
    }

    private def processValidaddPetRequest[T <: Any](f: addPetActionType)(request: addPetActionRequestType, writers: Map[Int, String => Writeable[T]], mimeType: String) = {
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
}


trait DashboardBase extends Controller with PlayBodyParsing {
    private type methodLevelActionRequestType       = (PetsGetTags, PetsGetLimit)
    private type methodLevelActionType              = methodLevelActionRequestType => Try[(Int, Any)]

    private val errorToStatusmethodLevel: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def methodLevelAction = (f: methodLevelActionType) => (tags: PetsGetTags, limit: PetsGetLimit) => Action {        val methodLevelResponseMimeType    = "application/json"

        val possibleWriters = Map(
                200 -> anyToWritable[Seq[Pet]]
        ).withDefaultValue(anyToWritable[ErrorModel])        
            val result =                
                    new PetsGetValidator(tags, limit).errors match {
                        case e if e.isEmpty => processValidmethodLevelRequest(f)((tags, limit), possibleWriters, methodLevelResponseMimeType)
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(methodLevelResponseMimeType)
                            BadRequest(l)
                    }
                
            result
    }

    private def processValidmethodLevelRequest[T <: Any](f: methodLevelActionType)(request: methodLevelActionRequestType, writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusmethodLevel orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val methodLevelWritableJson = writer(mimeType)
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
    private type pathLevelGetActionRequestType       = (Long)
    private type pathLevelGetActionType              = pathLevelGetActionRequestType => Try[(Int, Any)]

    private val errorToStatuspathLevelGet: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def pathLevelGetAction = (f: pathLevelGetActionType) => (id: Long) => Action {        val pathLevelGetResponseMimeType    = "application/json"

        val possibleWriters = Map(
                200 -> anyToWritable[Pet]
        ).withDefaultValue(anyToWritable[ErrorModel])        
            val result =                
                    new PetsIdGetValidator(id).errors match {
                        case e if e.isEmpty => processValidpathLevelGetRequest(f)((id), possibleWriters, pathLevelGetResponseMimeType)
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(pathLevelGetResponseMimeType)
                            BadRequest(l)
                    }
                
            result
    }

    private def processValidpathLevelGetRequest[T <: Any](f: pathLevelGetActionType)(request: pathLevelGetActionRequestType, writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatuspathLevelGet orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val pathLevelGetWritableJson = writer(mimeType)
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
    private type pathLevelDeleteActionRequestType       = (Long)
    private type pathLevelDeleteActionType              = pathLevelDeleteActionRequestType => Try[(Int, Any)]

    private val errorToStatuspathLevelDelete: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def pathLevelDeleteAction = (f: pathLevelDeleteActionType) => (id: Long) => Action {        val pathLevelDeleteResponseMimeType    = "application/json"

        val possibleWriters = Map(
                204 -> anyToWritable[Null]
        ).withDefaultValue(anyToWritable[ErrorModel])        
            val result =                
                    new PetsIdDeleteValidator(id).errors match {
                        case e if e.isEmpty => processValidpathLevelDeleteRequest(f)((id), possibleWriters, pathLevelDeleteResponseMimeType)
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(pathLevelDeleteResponseMimeType)
                            BadRequest(l)
                    }
                
            result
    }

    private def processValidpathLevelDeleteRequest[T <: Any](f: pathLevelDeleteActionType)(request: pathLevelDeleteActionRequestType, writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatuspathLevelDelete orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val pathLevelDeleteWritableJson = writer(mimeType)
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
