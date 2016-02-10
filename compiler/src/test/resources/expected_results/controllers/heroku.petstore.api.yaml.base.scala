package heroku.petstore.api.yaml

import play.api.mvc.{Action, Controller, Results}
import play.api.http.Writeable
import Results.Status
import de.zalando.play.controllers.{PlayBodyParsing, ParsingError}
import PlayBodyParsing._
import scala.util._




trait HerokuPetstoreApiYamlBase extends Controller with PlayBodyParsing {
    private type getActionRequestType       = (Int)
    private type getActionType              = getActionRequestType => Try[(Int, Any)]

    private val errorToStatusget: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def getAction = (f: getActionType) => (limit: Int) => Action {
        val getResponseMimeType    = "application/json"
        val possibleWriters = Map(
            200 -> anyToWritable[Seq[Pet]]
        )        
        val result =
            new GetValidator(limit).errors match {
                case e if e.isEmpty => processValidgetRequest(f)((limit))(possibleWriters, getResponseMimeType)
                case l =>
                    implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getResponseMimeType)
                    BadRequest(l)
            }
        result
    }

    private def processValidgetRequest[T <: Any](f: getActionType)(request: getActionRequestType)(writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusget orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val getWritableJson = writer(mimeType)
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
    private type putActionRequestType       = (PutPet)
    private type putActionType              = putActionRequestType => Try[(Int, Any)]

    private val errorToStatusput: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]
        private def putParser(maxLength: Int = parse.DefaultMaxTextLength) = optionParser[Pet]("application/json", "Invalid PutPet", maxLength)

    def putAction = (f: putActionType) => Action(putParser()) { request =>
        val putResponseMimeType    = "application/json"
        val possibleWriters = Map(
            200 -> anyToWritable[Null]
        )        
        val pet = request.body
        val result =
            new PutValidator(pet).errors match {
                case e if e.isEmpty => processValidputRequest(f)((pet))(possibleWriters, putResponseMimeType)
                case l =>
                    implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(putResponseMimeType)
                    BadRequest(l)
            }
        result
    }

    private def processValidputRequest[T <: Any](f: putActionType)(request: putActionRequestType)(writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusput orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val putWritableJson = writer(mimeType)
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
    private type postActionRequestType       = (Pet)
    private type postActionType              = postActionRequestType => Try[(Int, Any)]

    private val errorToStatuspost: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]
        private def postParser(maxLength: Int = parse.DefaultMaxTextLength) = anyParser[Pet]("application/json", "Invalid Pet", maxLength)

    def postAction = (f: postActionType) => Action(postParser()) { request =>
        val postResponseMimeType    = "application/json"
        val possibleWriters = Map(
            200 -> anyToWritable[Null]
        )        
        val pet = request.body
        val result =
            new PostValidator(pet).errors match {
                case e if e.isEmpty => processValidpostRequest(f)((pet))(possibleWriters, postResponseMimeType)
                case l =>
                    implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(postResponseMimeType)
                    BadRequest(l)
            }
        result
    }

    private def processValidpostRequest[T <: Any](f: postActionType)(request: postActionRequestType)(writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatuspost orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val postWritableJson = writer(mimeType)
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
    private type getbyPetIdActionRequestType       = (String)
    private type getbyPetIdActionType              = getbyPetIdActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetbyPetId: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def getbyPetIdAction = (f: getbyPetIdActionType) => (petId: String) => Action {
        val getbyPetIdResponseMimeType    = "application/json"
        val possibleWriters = Map(
            200 -> anyToWritable[Null]
        )        
        val result =
            new PetIdGetValidator(petId).errors match {
                case e if e.isEmpty => processValidgetbyPetIdRequest(f)((petId))(possibleWriters, getbyPetIdResponseMimeType)
                case l =>
                    implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getbyPetIdResponseMimeType)
                    BadRequest(l)
            }
        result
    }

    private def processValidgetbyPetIdRequest[T <: Any](f: getbyPetIdActionType)(request: getbyPetIdActionRequestType)(writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetbyPetId orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val getbyPetIdWritableJson = writer(mimeType)
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
