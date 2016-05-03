package heroku.petstore.api.yaml

import play.api.mvc.{Action, Controller, Results}
import play.api.http._
import Results.Status
import de.zalando.play.controllers.{PlayBodyParsing, ParsingError}
import PlayBodyParsing._
import scala.util._




trait HerokuPetstoreApiYamlBase extends Controller with PlayBodyParsing {
    private type getActionRequestType       = (Int)
    private type getActionType              = getActionRequestType => Try[(Int, Any)]

    private val errorToStatusget: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val getActionConstructor  = Action
    def getAction = (f: getActionType) => (limit: Int) => getActionConstructor { request =>
        val providedTypes = Seq[String]("application/json", "text/html")

        negotiateContent(request.acceptedTypes, providedTypes).map { getResponseMimeType =>
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
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetRequest[T <: Any](f: getActionType)(request: getActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusget orElse defaultErrorMapping)(error)
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
    private type putActionRequestType       = (PutPet)
    private type putActionType              = putActionRequestType => Try[(Int, Any)]

    private val errorToStatusput: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

        private def putParser(acceptedTypes: Seq[String], maxLength: Int = parse.DefaultMaxTextLength) = {
            def bodyMimeType: Option[MediaType] => String = mediaType => {
                val requestType = mediaType.toSeq.map {
                    case m: MediaRange => m
                    case MediaType(a,b,c) => new MediaRange(a,b,c,None,Nil)
                }
                negotiateContent(requestType, acceptedTypes).orElse(acceptedTypes.headOption).getOrElse("application/json")
            }
            
            
            val customParsers = WrappedBodyParsers.optionParser[Pet]
            optionParser[Pet](bodyMimeType, customParsers, "Invalid PutPet", maxLength)
        }

    val putActionConstructor  = Action
    def putAction = (f: putActionType) => putActionConstructor(putParser(Seq[String]("application/json", "text/xml"))) { request =>
        val providedTypes = Seq[String]("application/json", "text/html")

        negotiateContent(request.acceptedTypes, providedTypes).map { putResponseMimeType =>
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
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidputRequest[T <: Any](f: putActionType)(request: putActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusput orElse defaultErrorMapping)(error)
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
    private type postActionRequestType       = (Pet)
    private type postActionType              = postActionRequestType => Try[(Int, Any)]

    private val errorToStatuspost: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

        private def postParser(acceptedTypes: Seq[String], maxLength: Int = parse.DefaultMaxTextLength) = {
            def bodyMimeType: Option[MediaType] => String = mediaType => {
                val requestType = mediaType.toSeq.map {
                    case m: MediaRange => m
                    case MediaType(a,b,c) => new MediaRange(a,b,c,None,Nil)
                }
                negotiateContent(requestType, acceptedTypes).orElse(acceptedTypes.headOption).getOrElse("application/json")
            }
            
            
            val customParsers = WrappedBodyParsers.anyParser[Pet]
            anyParser[Pet](bodyMimeType, customParsers, "Invalid Pet", maxLength)
        }

    val postActionConstructor  = Action
    def postAction = (f: postActionType) => postActionConstructor(postParser(Seq[String]("application/json", "text/xml"))) { request =>
        val providedTypes = Seq[String]("application/json", "text/html")

        negotiateContent(request.acceptedTypes, providedTypes).map { postResponseMimeType =>
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
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidpostRequest[T <: Any](f: postActionType)(request: postActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatuspost orElse defaultErrorMapping)(error)
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
    private type getbyPetIdActionRequestType       = (String)
    private type getbyPetIdActionType              = getbyPetIdActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetbyPetId: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val getbyPetIdActionConstructor  = Action
    def getbyPetIdAction = (f: getbyPetIdActionType) => (petId: String) => getbyPetIdActionConstructor { request =>
        val providedTypes = Seq[String]("application/json", "text/html")

        negotiateContent(request.acceptedTypes, providedTypes).map { getbyPetIdResponseMimeType =>
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
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetbyPetIdRequest[T <: Any](f: getbyPetIdActionType)(request: getbyPetIdActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetbyPetId orElse defaultErrorMapping)(error)
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
