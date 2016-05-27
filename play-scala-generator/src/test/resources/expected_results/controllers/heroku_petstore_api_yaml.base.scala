package heroku.petstore.api.yaml

import scala.language.existentials

import play.api.mvc.{Action, Controller, Results}
import play.api.http._
import Results.Status

import de.zalando.play.controllers.{PlayBodyParsing, ParsingError, ResultWrapper}
import PlayBodyParsing._
import scala.util._
import scala.math.BigInt

import de.zalando.play.controllers.PlayPathBindables





trait HerokuPetstoreApiYamlBase extends Controller with PlayBodyParsing {
    sealed trait GetType[T] extends ResultWrapper[T]
    case class Get200(result: Seq[Pet])(implicit val writer: String => Option[Writeable[Seq[Pet]]]) extends GetType[Seq[Pet]] { val statusCode = 200 }
    

    private type getActionRequestType       = (BigInt)
    private type getActionType[T]            = getActionRequestType => GetType[T] forSome { type T }


    val getActionConstructor  = Action
    def getAction[T] = (f: getActionType[T]) => (limit: BigInt) => getActionConstructor { request =>
        val providedTypes = Seq[String]("application/json", "text/html")

        negotiateContent(request.acceptedTypes, providedTypes).map { getResponseMimeType =>

            
            

                val result =
                        new GetValidator(limit).errors match {
                            case e if e.isEmpty => processValidgetRequest(f)((limit))(getResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetRequest[T](f: getActionType[T])(request: getActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait PutType[T] extends ResultWrapper[T]
    
    case object Put200 extends EmptyReturn(200)
    

    private type putActionRequestType       = (PutPet)
    private type putActionType[T]            = putActionRequestType => PutType[T] forSome { type T }

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
    def putAction[T] = (f: putActionType[T]) => putActionConstructor(putParser(Seq[String]("application/json", "text/xml"))) { request =>
        val providedTypes = Seq[String]("application/json", "text/html")

        negotiateContent(request.acceptedTypes, providedTypes).map { putResponseMimeType =>

            val pet = request.body
            
            

                val result =
                        new PutValidator(pet).errors match {
                            case e if e.isEmpty => processValidputRequest(f)((pet))(putResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(putResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidputRequest[T](f: putActionType[T])(request: putActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait PostType[T] extends ResultWrapper[T]
    
    case object Post200 extends EmptyReturn(200)
    

    private type postActionRequestType       = (Pet)
    private type postActionType[T]            = postActionRequestType => PostType[T] forSome { type T }

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
    def postAction[T] = (f: postActionType[T]) => postActionConstructor(postParser(Seq[String]("application/json", "text/xml"))) { request =>
        val providedTypes = Seq[String]("application/json", "text/html")

        negotiateContent(request.acceptedTypes, providedTypes).map { postResponseMimeType =>

            val pet = request.body
            
            

                val result =
                        new PostValidator(pet).errors match {
                            case e if e.isEmpty => processValidpostRequest(f)((pet))(postResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(postResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidpostRequest[T](f: postActionType[T])(request: postActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait GetbyPetIdType[T] extends ResultWrapper[T]
    
    case object GetbyPetId200 extends EmptyReturn(200)
    

    private type getbyPetIdActionRequestType       = (String)
    private type getbyPetIdActionType[T]            = getbyPetIdActionRequestType => GetbyPetIdType[T] forSome { type T }


    val getbyPetIdActionConstructor  = Action
    def getbyPetIdAction[T] = (f: getbyPetIdActionType[T]) => (petId: String) => getbyPetIdActionConstructor { request =>
        val providedTypes = Seq[String]("application/json", "text/html")

        negotiateContent(request.acceptedTypes, providedTypes).map { getbyPetIdResponseMimeType =>

            
            

                val result =
                        new PetIdGetValidator(petId).errors match {
                            case e if e.isEmpty => processValidgetbyPetIdRequest(f)((petId))(getbyPetIdResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getbyPetIdResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetbyPetIdRequest[T](f: getbyPetIdActionType[T])(request: getbyPetIdActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    abstract class EmptyReturn(override val statusCode: Int = 204) extends ResultWrapper[Results.EmptyContent]  with GetType[Results.EmptyContent] with PutType[Results.EmptyContent] with PostType[Results.EmptyContent] with GetbyPetIdType[Results.EmptyContent] { val result = Results.EmptyContent(); val writer = (x: String) => Some(new DefaultWriteables{}.writeableOf_EmptyContent); override def toResult(mimeType: String): Option[play.api.mvc.Result] = Some(Results.NoContent) }
    case object NotImplementedYet extends ResultWrapper[Results.EmptyContent]  with GetType[Results.EmptyContent] with PutType[Results.EmptyContent] with PostType[Results.EmptyContent] with GetbyPetIdType[Results.EmptyContent] { val statusCode = 501; val result = Results.EmptyContent(); val writer = (x: String) => Some(new DefaultWriteables{}.writeableOf_EmptyContent); override def toResult(mimeType: String): Option[play.api.mvc.Result] = Some(Results.NotImplemented) }
}
