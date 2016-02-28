package echo

import play.api.mvc.{Action, Controller, Results}
import play.api.http.Writeable
import Results.Status
import de.zalando.play.controllers.{PlayBodyParsing, ParsingError}
import PlayBodyParsing._
import scala.util._




trait HandlerBase extends Controller with PlayBodyParsing {
    private type methodActionRequestType       = (Unit)
    private type methodActionType              = methodActionRequestType => Try[(Int, Any)]

    private val errorToStatusmethod: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    def methodAction = (f: methodActionType) => Action { request =>
        val providedTypes = Seq[String]()

        negotiateContent(request.acceptedTypes, providedTypes).map { methodResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[Null]
            )
            

                val result = processValidmethodRequest(f)()(possibleWriters, methodResponseMimeType)
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidmethodRequest[T <: Any](f: methodActionType)(request: methodActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusmethod orElse defaultErrorMapping)(error)
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

trait EchoApiYamlBase extends Controller with PlayBodyParsing {
    private type postActionRequestType       = (PostName, PostName)
    private type postActionType              = postActionRequestType => Try[(Int, Any)]

    private val errorToStatuspost: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    def postAction = (f: postActionType) => (name: PostName, year: PostName) => Action { request =>
        val providedTypes = Seq[String]()

        negotiateContent(request.acceptedTypes, providedTypes).map { postResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[PostResponses200]
            )
            

                val result =
                        new PostValidator(name, year).errors match {
                            case e if e.isEmpty => processValidpostRequest(f)((name, year))(possibleWriters, postResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(postResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidpostRequest[T <: Any](f: postActionType)(request: postActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
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
    private type gettest_pathByIdActionRequestType       = (String)
    private type gettest_pathByIdActionType              = gettest_pathByIdActionRequestType => Try[(Int, Any)]

    private val errorToStatusgettest_pathById: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    def gettest_pathByIdAction = (f: gettest_pathByIdActionType) => (id: String) => Action { request =>
        val providedTypes = Seq[String]()

        negotiateContent(request.acceptedTypes, providedTypes).map { gettest_pathByIdResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[Null]
            )
            

                val result =
                        new Test_pathIdGetValidator(id).errors match {
                            case e if e.isEmpty => processValidgettest_pathByIdRequest(f)((id))(possibleWriters, gettest_pathByIdResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(gettest_pathByIdResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgettest_pathByIdRequest[T <: Any](f: gettest_pathByIdActionType)(request: gettest_pathByIdActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgettest_pathById orElse defaultErrorMapping)(error)
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
