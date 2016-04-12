package echo

import play.api.mvc.{Action, Controller, Results}
import play.api.http._
import Results.Status

import de.zalando.play.controllers.{PlayBodyParsing, ParsingError, ResultWrapper, ResponseWriters}
import PlayBodyParsing._
import scala.util._

import de.zalando.play.controllers.PlayPathBindables



trait EchoHandlerBase extends Controller with PlayBodyParsing {
    sealed trait methodType[ResultT] extends ResultWrapper[ResultT]
    case class method200(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends methodType[Null] { val statusCode = 200 }
    

    private type methodActionRequestType       = (Unit)
    private type methodActionType[T]            = methodActionRequestType => methodType[T]


    val methodActionConstructor  = Action
    def methodAction[T] = (f: methodActionType[T]) => methodActionConstructor { request =>
        val providedTypes = Seq[String]()

        negotiateContent(request.acceptedTypes, providedTypes).map { methodResponseMimeType =>

            
            

                val result = processValidmethodRequest(f)()(methodResponseMimeType)
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidmethodRequest[T](f: methodActionType[T])(request: methodActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    case object EmptyReturn extends ResultWrapper[Results.EmptyContent]     { val statusCode = 204; val result = Results.EmptyContent(); val writer = (x: String) => Some(new DefaultWriteables{}.writeableOf_EmptyContent); override def toResult(mimeType: String): Option[play.api.mvc.Result] = Some(Results.NoContent) }
    case object NotImplementedYet extends ResultWrapper[Results.EmptyContent]  with methodType[Results.EmptyContent] { val statusCode = 501; val result = Results.EmptyContent(); val writer = (x: String) => Some(new DefaultWriteables{}.writeableOf_EmptyContent); override def toResult(mimeType: String): Option[play.api.mvc.Result] = Some(Results.NotImplemented) }
}
trait EchoApiYamlBase extends Controller with PlayBodyParsing {
    sealed trait postType[ResultT] extends ResultWrapper[ResultT]
    case class post200(result: PostResponses200)(implicit val writer: String => Option[Writeable[PostResponses200]]) extends postType[PostResponses200] { val statusCode = 200 }
    

    private type postActionRequestType       = (PostName, PostName)
    private type postActionType[T]            = postActionRequestType => postType[T]


    val postActionConstructor  = Action
    def postAction[T] = (f: postActionType[T]) => postActionConstructor { request =>
        val providedTypes = Seq[String]()

        negotiateContent(request.acceptedTypes, providedTypes).map { postResponseMimeType =>

            
            val eitherFormParameters = FormDataParser.postParseForm(request)
            eitherFormParameters match {
                case Left(problem: Seq[String]) =>
                    val msg = problem.mkString("\n")
                    BadRequest(msg)

                case Right((name, year)) =>
            

                val result =
                        new PostValidator(name, year).errors match {
                            case e if e.isEmpty => processValidpostRequest(f)((name, year))(postResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(postResponseMimeType)
                                BadRequest(l)
                        }
                result
            
            }
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidpostRequest[T](f: postActionType[T])(request: postActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait gettest_pathByIdType[ResultT] extends ResultWrapper[ResultT]
    case class gettest_pathById200(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends gettest_pathByIdType[Null] { val statusCode = 200 }
    

    private type gettest_pathByIdActionRequestType       = (String)
    private type gettest_pathByIdActionType[T]            = gettest_pathByIdActionRequestType => gettest_pathByIdType[T]


    val gettest_pathByIdActionConstructor  = Action
    def gettest_pathByIdAction[T] = (f: gettest_pathByIdActionType[T]) => (id: String) => gettest_pathByIdActionConstructor { request =>
        val providedTypes = Seq[String]()

        negotiateContent(request.acceptedTypes, providedTypes).map { gettest_pathByIdResponseMimeType =>

            
            

                val result =
                        new Test_pathIdGetValidator(id).errors match {
                            case e if e.isEmpty => processValidgettest_pathByIdRequest(f)((id))(gettest_pathByIdResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(gettest_pathByIdResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgettest_pathByIdRequest[T](f: gettest_pathByIdActionType[T])(request: gettest_pathByIdActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    case object EmptyReturn extends ResultWrapper[Results.EmptyContent]        { val statusCode = 204; val result = Results.EmptyContent(); val writer = (x: String) => Some(new DefaultWriteables{}.writeableOf_EmptyContent); override def toResult(mimeType: String): Option[play.api.mvc.Result] = Some(Results.NoContent) }
    case object NotImplementedYet extends ResultWrapper[Results.EmptyContent]  with postType[Results.EmptyContent] with gettest_pathByIdType[Results.EmptyContent] { val statusCode = 501; val result = Results.EmptyContent(); val writer = (x: String) => Some(new DefaultWriteables{}.writeableOf_EmptyContent); override def toResult(mimeType: String): Option[play.api.mvc.Result] = Some(Results.NotImplemented) }
}
