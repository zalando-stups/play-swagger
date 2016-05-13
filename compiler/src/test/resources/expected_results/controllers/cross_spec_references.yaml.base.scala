package cross_spec_references.yaml

import play.api.mvc.{Action, Controller, Results}
import play.api.http._
import Results.Status
import de.zalando.play.controllers.{PlayBodyParsing, ParsingError, ResponseWriters}
import PlayBodyParsing._
import scala.util._
import de.zalando.play.controllers.ArrayWrapper
import scala.math.BigInt




trait Cross_spec_referencesYamlBase extends Controller with PlayBodyParsing {
    private type postActionRequestType       = (ModelSchemaRoot)
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
            
            import de.zalando.play.controllers.WrappedBodyParsers
            
            val customParsers = WrappedBodyParsers.anyParser[ModelSchemaRoot]
            anyParser[ModelSchemaRoot](bodyMimeType, customParsers, "Invalid ModelSchemaRoot", maxLength)
        }

    val postActionConstructor  = Action
    def postAction = (f: postActionType) => postActionConstructor(postParser(Seq[String]())) { request =>
        val providedTypes = Seq[String]()

        negotiateContent(request.acceptedTypes, providedTypes).map { postResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[Pet]
            )
            val root = request.body
            
            

                val result =
                        new PostValidator(root).errors match {
                            case e if e.isEmpty => processValidpostRequest(f)((root))(possibleWriters, postResponseMimeType)
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
            case Failure(error) => (errorToStatuspost orElse defaultErrorMapping)(error)(error.getMessage)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
                    if (code / 100 == 3) Redirect(result.toString, code) else Status(code)(result)
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
