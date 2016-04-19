package error_in_array.yaml

import play.api.mvc.{Action, Controller, Results}
import play.api.http._
import Results.Status
import de.zalando.play.controllers.{PlayBodyParsing, ParsingError, ResultWrapper}
import PlayBodyParsing._
import scala.util._
// import PickAvailableWriteable._ // if you want play to pick the first available writable
import de.zalando.play.controllers.ArrayWrapper



trait Error_in_arrayYamlBase extends Controller with PlayBodyParsing {
    sealed trait getschemaModelType[Result] extends ResultWrapper[Result]
    case class getschemaModel200(result: ModelSchemaRoot)(implicit val writers: List[Writeable[ModelSchemaRoot]]) extends getschemaModelType[ModelSchemaRoot] { val statusCode = 200 }
    

    private type getschemaModelActionRequestType       = (ModelSchemaRoot)
    private type getschemaModelActionType[T]            = getschemaModelActionRequestType => getschemaModelType[T]

        private def getschemaModelParser(acceptedTypes: Seq[String], maxLength: Int = parse.DefaultMaxTextLength) = {
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

    val getschemaModelActionConstructor  = Action
    def getschemaModelAction[T] = (f: getschemaModelActionType[T]) => getschemaModelActionConstructor(getschemaModelParser(Seq[String]())) { request =>
        val providedTypes = Seq[String]()

        negotiateContent(request.acceptedTypes, providedTypes).map { getschemaModelResponseMimeType =>

            val root = request.body
            

                val result =
                        new SchemaModelGetValidator(root).errors match {
                            case e if e.isEmpty => processValidgetschemaModelRequest(f)((root))(getschemaModelResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getschemaModelResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetschemaModelRequest[T](f: getschemaModelActionType[T])(request: getschemaModelActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    case object EmptyReturn extends ResultWrapper[Results.EmptyContent]     { val statusCode = 204; val result = Results.EmptyContent(); val writers = List(new DefaultWriteables{}.writeableOf_EmptyContent); override def toResult(mimeType: String): Option[play.api.mvc.Result] = Some(Results.NoContent) }
    case object NotImplementedYet extends ResultWrapper[Results.EmptyContent]  with getschemaModelType[Results.EmptyContent] { val statusCode = 501; val result = Results.EmptyContent(); val writers = List(new DefaultWriteables{}.writeableOf_EmptyContent); override def toResult(mimeType: String): Option[play.api.mvc.Result] = Some(Results.NotImplemented) }
}
