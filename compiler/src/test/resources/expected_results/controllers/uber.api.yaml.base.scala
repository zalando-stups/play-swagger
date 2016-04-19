package uber.api.yaml

import play.api.mvc.{Action, Controller, Results}
import play.api.http._
import Results.Status
import de.zalando.play.controllers.{PlayBodyParsing, ParsingError, ResultWrapper}
import PlayBodyParsing._
import scala.util._
// import PickAvailableWriteable._ // if you want play to pick the first available writable
import de.zalando.play.controllers.ArrayWrapper

import de.zalando.play.controllers.PlayPathBindables



trait UberApiYamlBase extends Controller with PlayBodyParsing {
    sealed trait getmeType[Result] extends ResultWrapper[Result]
    case class getme200(result: Profile)(implicit val writers: List[Writeable[Profile]]) extends getmeType[Profile] { val statusCode = 200 }
    

    private type getmeActionRequestType       = (Unit)
    private type getmeActionType[T]            = getmeActionRequestType => getmeType[T]


    val getmeActionConstructor  = Action
    def getmeAction[T] = (f: getmeActionType[T]) => getmeActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getmeResponseMimeType =>

            

                val result = processValidgetmeRequest(f)()(getmeResponseMimeType)
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetmeRequest[T](f: getmeActionType[T])(request: getmeActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait getproductsType[Result] extends ResultWrapper[Result]
    case class getproducts200(result: Seq[Product])(implicit val writers: List[Writeable[Seq[Product]]]) extends getproductsType[Seq[Product]] { val statusCode = 200 }
    

    private type getproductsActionRequestType       = (Double, Double)
    private type getproductsActionType[T]            = getproductsActionRequestType => getproductsType[T]


    val getproductsActionConstructor  = Action
    def getproductsAction[T] = (f: getproductsActionType[T]) => (latitude: Double, longitude: Double) => getproductsActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getproductsResponseMimeType =>

            

                val result =
                        new ProductsGetValidator(latitude, longitude).errors match {
                            case e if e.isEmpty => processValidgetproductsRequest(f)((latitude, longitude))(getproductsResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getproductsResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetproductsRequest[T](f: getproductsActionType[T])(request: getproductsActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait getestimatesTimeType[Result] extends ResultWrapper[Result]
    case class getestimatesTime200(result: Seq[Product])(implicit val writers: List[Writeable[Seq[Product]]]) extends getestimatesTimeType[Seq[Product]] { val statusCode = 200 }
    

    private type getestimatesTimeActionRequestType       = (Double, Double, ProfilePicture, ProfilePicture)
    private type getestimatesTimeActionType[T]            = getestimatesTimeActionRequestType => getestimatesTimeType[T]


    val getestimatesTimeActionConstructor  = Action
    def getestimatesTimeAction[T] = (f: getestimatesTimeActionType[T]) => (start_latitude: Double, start_longitude: Double, customer_uuid: ProfilePicture, product_id: ProfilePicture) => getestimatesTimeActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getestimatesTimeResponseMimeType =>

            

                val result =
                        new EstimatesTimeGetValidator(start_latitude, start_longitude, customer_uuid, product_id).errors match {
                            case e if e.isEmpty => processValidgetestimatesTimeRequest(f)((start_latitude, start_longitude, customer_uuid, product_id))(getestimatesTimeResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getestimatesTimeResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetestimatesTimeRequest[T](f: getestimatesTimeActionType[T])(request: getestimatesTimeActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait getestimatesPriceType[Result] extends ResultWrapper[Result]
    case class getestimatesPrice200(result: Seq[PriceEstimate])(implicit val writers: List[Writeable[Seq[PriceEstimate]]]) extends getestimatesPriceType[Seq[PriceEstimate]] { val statusCode = 200 }
    

    private type getestimatesPriceActionRequestType       = (Double, Double, Double, Double)
    private type getestimatesPriceActionType[T]            = getestimatesPriceActionRequestType => getestimatesPriceType[T]


    val getestimatesPriceActionConstructor  = Action
    def getestimatesPriceAction[T] = (f: getestimatesPriceActionType[T]) => (start_latitude: Double, start_longitude: Double, end_latitude: Double, end_longitude: Double) => getestimatesPriceActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getestimatesPriceResponseMimeType =>

            

                val result =
                        new EstimatesPriceGetValidator(start_latitude, start_longitude, end_latitude, end_longitude).errors match {
                            case e if e.isEmpty => processValidgetestimatesPriceRequest(f)((start_latitude, start_longitude, end_latitude, end_longitude))(getestimatesPriceResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getestimatesPriceResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetestimatesPriceRequest[T](f: getestimatesPriceActionType[T])(request: getestimatesPriceActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait gethistoryType[Result] extends ResultWrapper[Result]
    case class gethistory200(result: Activities)(implicit val writers: List[Writeable[Activities]]) extends gethistoryType[Activities] { val statusCode = 200 }
    

    private type gethistoryActionRequestType       = (ErrorCode, ErrorCode)
    private type gethistoryActionType[T]            = gethistoryActionRequestType => gethistoryType[T]


    val gethistoryActionConstructor  = Action
    def gethistoryAction[T] = (f: gethistoryActionType[T]) => (offset: ErrorCode, limit: ErrorCode) => gethistoryActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { gethistoryResponseMimeType =>

            

                val result =
                        new HistoryGetValidator(offset, limit).errors match {
                            case e if e.isEmpty => processValidgethistoryRequest(f)((offset, limit))(gethistoryResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(gethistoryResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgethistoryRequest[T](f: gethistoryActionType[T])(request: gethistoryActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    case object EmptyReturn extends ResultWrapper[Results.EmptyContent]                 { val statusCode = 204; val result = Results.EmptyContent(); val writers = List(new DefaultWriteables{}.writeableOf_EmptyContent); override def toResult(mimeType: String): Option[play.api.mvc.Result] = Some(Results.NoContent) }
    case object NotImplementedYet extends ResultWrapper[Results.EmptyContent]  with getmeType[Results.EmptyContent] with getproductsType[Results.EmptyContent] with getestimatesTimeType[Results.EmptyContent] with getestimatesPriceType[Results.EmptyContent] with gethistoryType[Results.EmptyContent] { val statusCode = 501; val result = Results.EmptyContent(); val writers = List(new DefaultWriteables{}.writeableOf_EmptyContent); override def toResult(mimeType: String): Option[play.api.mvc.Result] = Some(Results.NotImplemented) }
}
