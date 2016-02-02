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

    def methodAction = (f: methodActionType) => Action {        val methodResponseMimeType    = "application/json"

        val possibleWriters = Map(
                200 -> anyToWritable[Null]
        )        
            val result = processValidmethodRequest(f)()(possibleWriters, methodResponseMimeType)                
            result
    }

    private def processValidmethodRequest[T <: Any](f: methodActionType)(request: methodActionRequestType)(writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusmethod orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val methodWritableJson = writer(mimeType)
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

    def postAction = (f: postActionType) => (name: PostName, year: PostName) => Action {        val postResponseMimeType    = "application/json"

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
    private type gettest_pathByIdActionRequestType       = (String)
    private type gettest_pathByIdActionType              = gettest_pathByIdActionRequestType => Try[(Int, Any)]

    private val errorToStatusgettest_pathById: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def gettest_pathByIdAction = (f: gettest_pathByIdActionType) => (id: String) => Action {        val gettest_pathByIdResponseMimeType    = "application/json"

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
    }

    private def processValidgettest_pathByIdRequest[T <: Any](f: gettest_pathByIdActionType)(request: gettest_pathByIdActionRequestType)(writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgettest_pathById orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val gettest_pathByIdWritableJson = writer(mimeType)
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
