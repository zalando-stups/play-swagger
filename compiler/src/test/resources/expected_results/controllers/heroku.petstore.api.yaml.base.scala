package heroku.petstore.api.yaml

import play.api.mvc.{Action, Controller, Results}
import play.api.http._
import Results.Status

import de.zalando.play.controllers.{PlayBodyParsing, ParsingError, ResultWrapper, ResponseWriters}
import PlayBodyParsing._
import scala.util._
import scala.math.BigInt

import de.zalando.play.controllers.PlayPathBindables



trait HerokuPetstoreApiYamlBase extends Controller with PlayBodyParsing {
    sealed trait getType[ResultT] extends ResultWrapper[ResultT]
    case class get200(result: Seq[Pet])(implicit val writer: String => Option[Writeable[Seq[Pet]]]) extends getType[Seq[Pet]] { val statusCode = 200 }
    

    private type getActionRequestType       = (BigInt)
    private type getActionType[T]            = getActionRequestType => getType[T]


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
    sealed trait putType[ResultT] extends ResultWrapper[ResultT]
    case class put200(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends putType[Null] { val statusCode = 200 }
    

    private type putActionRequestType       = (PutPet)
    private type putActionType[T]            = putActionRequestType => putType[T]

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
    sealed trait postType[ResultT] extends ResultWrapper[ResultT]
    case class post200(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends postType[Null] { val statusCode = 200 }
    

    private type postActionRequestType       = (Pet)
    private type postActionType[T]            = postActionRequestType => postType[T]

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
    sealed trait getbyPetIdType[ResultT] extends ResultWrapper[ResultT]
    case class getbyPetId200(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends getbyPetIdType[Null] { val statusCode = 200 }
    

    private type getbyPetIdActionRequestType       = (String)
    private type getbyPetIdActionType[T]            = getbyPetIdActionRequestType => getbyPetIdType[T]


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
    case object EmptyReturn extends ResultWrapper[Results.EmptyContent]              { val statusCode = 204; val result = Results.EmptyContent(); val writer = (x: String) => Some(new DefaultWriteables{}.writeableOf_EmptyContent); override def toResult(mimeType: String): Option[play.api.mvc.Result] = Some(Results.NoContent) }
    case object NotImplementedYet extends ResultWrapper[Results.EmptyContent]  with getType[Results.EmptyContent] with putType[Results.EmptyContent] with postType[Results.EmptyContent] with getbyPetIdType[Results.EmptyContent] { val statusCode = 501; val result = Results.EmptyContent(); val writer = (x: String) => Some(new DefaultWriteables{}.writeableOf_EmptyContent); override def toResult(mimeType: String): Option[play.api.mvc.Result] = Some(Results.NotImplemented) }
}
