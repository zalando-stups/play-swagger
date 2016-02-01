package uber.api.yaml

import play.api.mvc.{Action, Controller, Results}
import play.api.http.Writeable
import Results.Status
import de.zalando.play.controllers.{PlayBodyParsing, ParsingError}
import PlayBodyParsing._
import scala.util._
import de.zalando.play.controllers.ArrayWrapper
import de.zalando.play.controllers.PlayPathBindables




trait UberApiYamlBase extends Controller with PlayBodyParsing {
    private type getmeActionRequestType       = (Unit)
    private type getmeActionType              = getmeActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetme: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def getmeAction = (f: getmeActionType) => Action {        val getmeResponseMimeType    = "application/json"

        val possibleWriters = Map(
                200 -> anyToWritable[Profile]
        ).withDefaultValue(anyToWritable[Error])        
            val result = processValidgetmeRequest(f)()(possibleWriters, getmeResponseMimeType)                
            result
    }

    private def processValidgetmeRequest[T <: Any](f: getmeActionType)(request: getmeActionRequestType)(writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetme orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val getmeWritableJson = writer(mimeType)
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
    private type getproductsActionRequestType       = (Double, Double)
    private type getproductsActionType              = getproductsActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetproducts: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def getproductsAction = (f: getproductsActionType) => (latitude: Double, longitude: Double) => Action {        val getproductsResponseMimeType    = "application/json"

        val possibleWriters = Map(
                200 -> anyToWritable[Seq[Product]]
        ).withDefaultValue(anyToWritable[Error])        
            val result =                
                    new ProductsGetValidator(latitude, longitude).errors match {
                        case e if e.isEmpty => processValidgetproductsRequest(f)((latitude, longitude))(possibleWriters, getproductsResponseMimeType)
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getproductsResponseMimeType)
                            BadRequest(l)
                    }
                
            result
    }

    private def processValidgetproductsRequest[T <: Any](f: getproductsActionType)(request: getproductsActionRequestType)(writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetproducts orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val getproductsWritableJson = writer(mimeType)
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
    private type getestimatesTimeActionRequestType       = (Double, Double, ProfilePicture, ProfilePicture)
    private type getestimatesTimeActionType              = getestimatesTimeActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetestimatesTime: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def getestimatesTimeAction = (f: getestimatesTimeActionType) => (start_latitude: Double, start_longitude: Double, customer_uuid: ProfilePicture, product_id: ProfilePicture) => Action {        val getestimatesTimeResponseMimeType    = "application/json"

        val possibleWriters = Map(
                200 -> anyToWritable[Seq[Product]]
        ).withDefaultValue(anyToWritable[Error])        
            val result =                
                    new EstimatesTimeGetValidator(start_latitude, start_longitude, customer_uuid, product_id).errors match {
                        case e if e.isEmpty => processValidgetestimatesTimeRequest(f)((start_latitude, start_longitude, customer_uuid, product_id))(possibleWriters, getestimatesTimeResponseMimeType)
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getestimatesTimeResponseMimeType)
                            BadRequest(l)
                    }
                
            result
    }

    private def processValidgetestimatesTimeRequest[T <: Any](f: getestimatesTimeActionType)(request: getestimatesTimeActionRequestType)(writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetestimatesTime orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val getestimatesTimeWritableJson = writer(mimeType)
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
    private type getestimatesPriceActionRequestType       = (Double, Double, Double, Double)
    private type getestimatesPriceActionType              = getestimatesPriceActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetestimatesPrice: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def getestimatesPriceAction = (f: getestimatesPriceActionType) => (start_latitude: Double, start_longitude: Double, end_latitude: Double, end_longitude: Double) => Action {        val getestimatesPriceResponseMimeType    = "application/json"

        val possibleWriters = Map(
                200 -> anyToWritable[Seq[PriceEstimate]]
        ).withDefaultValue(anyToWritable[Error])        
            val result =                
                    new EstimatesPriceGetValidator(start_latitude, start_longitude, end_latitude, end_longitude).errors match {
                        case e if e.isEmpty => processValidgetestimatesPriceRequest(f)((start_latitude, start_longitude, end_latitude, end_longitude))(possibleWriters, getestimatesPriceResponseMimeType)
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getestimatesPriceResponseMimeType)
                            BadRequest(l)
                    }
                
            result
    }

    private def processValidgetestimatesPriceRequest[T <: Any](f: getestimatesPriceActionType)(request: getestimatesPriceActionRequestType)(writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetestimatesPrice orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val getestimatesPriceWritableJson = writer(mimeType)
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
    private type gethistoryActionRequestType       = (ErrorCode, ErrorCode)
    private type gethistoryActionType              = gethistoryActionRequestType => Try[(Int, Any)]

    private val errorToStatusgethistory: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def gethistoryAction = (f: gethistoryActionType) => (offset: ErrorCode, limit: ErrorCode) => Action {        val gethistoryResponseMimeType    = "application/json"

        val possibleWriters = Map(
                200 -> anyToWritable[Activities]
        ).withDefaultValue(anyToWritable[Error])        
            val result =                
                    new HistoryGetValidator(offset, limit).errors match {
                        case e if e.isEmpty => processValidgethistoryRequest(f)((offset, limit))(possibleWriters, gethistoryResponseMimeType)
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(gethistoryResponseMimeType)
                            BadRequest(l)
                    }
                
            result
    }

    private def processValidgethistoryRequest[T <: Any](f: gethistoryActionType)(request: gethistoryActionRequestType)(writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgethistory orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val gethistoryWritableJson = writer(mimeType)
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
