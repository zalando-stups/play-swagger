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

    def addPetAction = (f: addPetActionType) => Action(addPetParser()) { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { addPetResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[Pet]
            ).withDefaultValue(anyToWritable[ErrorModel])
            val pet = request.body
            

                val result =
                        new PetsPostValidator(pet).errors match {
                            case e if e.isEmpty => processValidaddPetRequest(f)((pet))(possibleWriters, addPetResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(addPetResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidaddPetRequest[T <: Any](f: addPetActionType)(request: addPetActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
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
}

trait DashboardBase extends Controller with PlayBodyParsing {
    private type methodLevelActionRequestType       = (PetsGetTags, PetsGetLimit)
    private type methodLevelActionType              = methodLevelActionRequestType => Try[(Int, Any)]

    private val errorToStatusmethodLevel: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    def methodLevelAction = (f: methodLevelActionType) => (tags: PetsGetTags, limit: PetsGetLimit) => Action { request =>
        val providedTypes = Seq[String]("application/json", "application/xml", "text/xml", "text/html")

        negotiateContent(request.acceptedTypes, providedTypes).map { methodLevelResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[Seq[Pet]]
            ).withDefaultValue(anyToWritable[ErrorModel])
            

                val result =
                        new PetsGetValidator(tags, limit).errors match {
                            case e if e.isEmpty => processValidmethodLevelRequest(f)((tags, limit))(possibleWriters, methodLevelResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(methodLevelResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidmethodLevelRequest[T <: Any](f: methodLevelActionType)(request: methodLevelActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusmethodLevel orElse defaultErrorMapping)(error)
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
    private type pathLevelGetActionRequestType       = (Long)
    private type pathLevelGetActionType              = pathLevelGetActionRequestType => Try[(Int, Any)]

    private val errorToStatuspathLevelGet: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    def pathLevelGetAction = (f: pathLevelGetActionType) => (id: Long) => Action { request =>
        val providedTypes = Seq[String]("application/json", "application/xml", "text/xml", "text/html")

        negotiateContent(request.acceptedTypes, providedTypes).map { pathLevelGetResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[Pet]
            ).withDefaultValue(anyToWritable[ErrorModel])
            

                val result =
                        new PetsIdGetValidator(id).errors match {
                            case e if e.isEmpty => processValidpathLevelGetRequest(f)((id))(possibleWriters, pathLevelGetResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(pathLevelGetResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidpathLevelGetRequest[T <: Any](f: pathLevelGetActionType)(request: pathLevelGetActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatuspathLevelGet orElse defaultErrorMapping)(error)
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
    private type pathLevelDeleteActionRequestType       = (Long)
    private type pathLevelDeleteActionType              = pathLevelDeleteActionRequestType => Try[(Int, Any)]

    private val errorToStatuspathLevelDelete: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    def pathLevelDeleteAction = (f: pathLevelDeleteActionType) => (id: Long) => Action { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { pathLevelDeleteResponseMimeType =>
                val possibleWriters = Map(
                    204 -> anyToWritable[Null]
            ).withDefaultValue(anyToWritable[ErrorModel])
            

                val result =
                        new PetsIdDeleteValidator(id).errors match {
                            case e if e.isEmpty => processValidpathLevelDeleteRequest(f)((id))(possibleWriters, pathLevelDeleteResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(pathLevelDeleteResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidpathLevelDeleteRequest[T <: Any](f: pathLevelDeleteActionType)(request: pathLevelDeleteActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatuspathLevelDelete orElse defaultErrorMapping)(error)
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
