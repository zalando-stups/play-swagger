package error_in_array.yaml

import play.api.mvc.{Action, Controller, Results}
import play.api.http.Writeable
import Results.Status
import de.zalando.play.controllers.{PlayBodyParsing, ParsingError}
import PlayBodyParsing._
import scala.util._
import de.zalando.play.controllers.ArrayWrapper




trait Error_in_arrayYamlBase extends Controller with PlayBodyParsing {
    private type getschemaModelActionRequestType       = (ModelSchemaRoot)
    private type getschemaModelActionType              = getschemaModelActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetschemaModel: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]
        private def getschemaModelParser(maxLength: Int = parse.DefaultMaxTextLength) = anyParser[ModelSchemaRoot]("application/json", "Invalid ModelSchemaRoot", maxLength)

    def getschemaModelAction = (f: getschemaModelActionType) => Action(getschemaModelParser()) { request =>        val getschemaModelResponseMimeType    = "application/json"

        val possibleWriters = Map(
                200 -> anyToWritable[ModelSchemaRoot]
        )        
        val root = request.body
        
            val result =                
                    new SchemaModelGetValidator(root).errors match {
                        case e if e.isEmpty => processValidgetschemaModelRequest(f)((root), possibleWriters, getschemaModelResponseMimeType)
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getschemaModelResponseMimeType)
                            BadRequest(l)
                    }
                
            result
    }

    private def processValidgetschemaModelRequest[T <: Any](f: getschemaModelActionType)(request: getschemaModelActionRequestType, writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetschemaModel orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val getschemaModelWritableJson = writer(mimeType)
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
