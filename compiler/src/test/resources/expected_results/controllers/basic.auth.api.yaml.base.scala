package basic.auth.api.yaml

import play.api.mvc.{Action, Controller, Results}
import play.api.http.Writeable
import Results.Status
import de.zalando.play.controllers.{PlayBodyParsing, ParsingError}
import PlayBodyParsing._
import scala.util._




trait BasicAuthApiYamlBase extends Controller with PlayBodyParsing {
    private type getActionRequestType       = (Unit)
    private type getActionType              = getActionRequestType => Try[(Int, Any)]

    private val errorToStatusget: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def getAction = (f: getActionType) => Action {
        val getResponseMimeType    = "application/json"
        val possibleWriters = Map(
            200 -> anyToWritable[Null]
        )        
        val result = processValidgetRequest(f)()(possibleWriters, getResponseMimeType)
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
}
