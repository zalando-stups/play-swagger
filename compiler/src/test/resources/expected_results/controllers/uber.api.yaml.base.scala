package uber.api.yaml
import play.api.mvc.{Action, Controller, Results}
import play.api.http.Writeable
import Results.Status
import de.zalando.play.controllers.{PlayBodyParsing, ParsingError}
import PlayBodyParsing._
import scala.util._
import de.zalando.play.controllers.PlayPathBindables
trait UberApiYamlBase extends Controller with PlayBodyParsing {
        private val getmeResponseMimeType    = "application/json"
        private val getmeActionSuccessStatus = Status(200)

        private type getmeActionRequestType       = (Unit)
        private type getmeActionResultType        = Option[Profile]
        private type getmeActionType              = getmeActionRequestType => Try[getmeActionResultType]

        private val errorToStatusgetme: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

        


        


        def getmeAction = (f: getmeActionType) => Action { 

            val result = processValidgetmeRequest(f)()

                result
        }

        private def processValidgetmeRequest(f: getmeActionType)(request: getmeActionRequestType) = {
            implicit val getmeWritableJson = anyToWritable[getmeActionResultType](getmeResponseMimeType)
            val callerResult = f(request)
            val status = callerResult match {
                case Failure(error) => (errorToStatusgetme orElse defaultErrorMapping)(error)
                case Success(result) => getmeActionSuccessStatus(result)
            }
            status
        }
        private val getproductsResponseMimeType    = "application/json"
        private val getproductsActionSuccessStatus = Status(200)

        private type getproductsActionRequestType       = (Double, Double)
        private type getproductsActionResultType        = Option[ProductsGetResponses200Opt]
        private type getproductsActionType              = getproductsActionRequestType => Try[getproductsActionResultType]

        private val errorToStatusgetproducts: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

        


        


        def getproductsAction = (f: getproductsActionType) => (latitude: Double, longitude: Double) => Action { 

            val result = 

                new ProductsGetValidator(latitude, longitude).errors match {
                        case e if e.isEmpty => processValidgetproductsRequest(f)((latitude, longitude))
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getproductsResponseMimeType)
                            BadRequest(l)
                    }

                result
        }

        private def processValidgetproductsRequest(f: getproductsActionType)(request: getproductsActionRequestType) = {
            implicit val getproductsWritableJson = anyToWritable[getproductsActionResultType](getproductsResponseMimeType)
            val callerResult = f(request)
            val status = callerResult match {
                case Failure(error) => (errorToStatusgetproducts orElse defaultErrorMapping)(error)
                case Success(result) => getproductsActionSuccessStatus(result)
            }
            status
        }
        private val getestimatesTimeResponseMimeType    = "application/json"
        private val getestimatesTimeActionSuccessStatus = Status(200)

        private type getestimatesTimeActionRequestType       = (Double, Double, ProfileLast_name, ProfileLast_name)
        private type getestimatesTimeActionResultType        = Option[ProductsGetResponses200Opt]
        private type getestimatesTimeActionType              = getestimatesTimeActionRequestType => Try[getestimatesTimeActionResultType]

        private val errorToStatusgetestimatesTime: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

        


        


        def getestimatesTimeAction = (f: getestimatesTimeActionType) => (start_latitude: Double, start_longitude: Double, customer_uuid: ProfileLast_name, product_id: ProfileLast_name) => Action { 

            val result = 

                new EstimatesTimeGetValidator(start_latitude, start_longitude, customer_uuid, product_id).errors match {
                        case e if e.isEmpty => processValidgetestimatesTimeRequest(f)((start_latitude, start_longitude, customer_uuid, product_id))
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getestimatesTimeResponseMimeType)
                            BadRequest(l)
                    }

                result
        }

        private def processValidgetestimatesTimeRequest(f: getestimatesTimeActionType)(request: getestimatesTimeActionRequestType) = {
            implicit val getestimatesTimeWritableJson = anyToWritable[getestimatesTimeActionResultType](getestimatesTimeResponseMimeType)
            val callerResult = f(request)
            val status = callerResult match {
                case Failure(error) => (errorToStatusgetestimatesTime orElse defaultErrorMapping)(error)
                case Success(result) => getestimatesTimeActionSuccessStatus(result)
            }
            status
        }
        private val getestimatesPriceResponseMimeType    = "application/json"
        private val getestimatesPriceActionSuccessStatus = Status(200)

        private type getestimatesPriceActionRequestType       = (Double, Double, Double, Double)
        private type getestimatesPriceActionResultType        = Option[EstimatesPriceGetResponses200Opt]
        private type getestimatesPriceActionType              = getestimatesPriceActionRequestType => Try[getestimatesPriceActionResultType]

        private val errorToStatusgetestimatesPrice: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

        


        


        def getestimatesPriceAction = (f: getestimatesPriceActionType) => (start_latitude: Double, start_longitude: Double, end_latitude: Double, end_longitude: Double) => Action { 

            val result = 

                new EstimatesPriceGetValidator(start_latitude, start_longitude, end_latitude, end_longitude).errors match {
                        case e if e.isEmpty => processValidgetestimatesPriceRequest(f)((start_latitude, start_longitude, end_latitude, end_longitude))
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getestimatesPriceResponseMimeType)
                            BadRequest(l)
                    }

                result
        }

        private def processValidgetestimatesPriceRequest(f: getestimatesPriceActionType)(request: getestimatesPriceActionRequestType) = {
            implicit val getestimatesPriceWritableJson = anyToWritable[getestimatesPriceActionResultType](getestimatesPriceResponseMimeType)
            val callerResult = f(request)
            val status = callerResult match {
                case Failure(error) => (errorToStatusgetestimatesPrice orElse defaultErrorMapping)(error)
                case Success(result) => getestimatesPriceActionSuccessStatus(result)
            }
            status
        }
        private val gethistoryResponseMimeType    = "application/json"
        private val gethistoryActionSuccessStatus = Status(200)

        private type gethistoryActionRequestType       = (ActivitiesLimit, ActivitiesLimit)
        private type gethistoryActionResultType        = Option[Activities]
        private type gethistoryActionType              = gethistoryActionRequestType => Try[gethistoryActionResultType]

        private val errorToStatusgethistory: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

        


        


        def gethistoryAction = (f: gethistoryActionType) => (offset: ActivitiesLimit, limit: ActivitiesLimit) => Action { 

            val result = 

                new HistoryGetValidator(offset, limit).errors match {
                        case e if e.isEmpty => processValidgethistoryRequest(f)((offset, limit))
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(gethistoryResponseMimeType)
                            BadRequest(l)
                    }

                result
        }

        private def processValidgethistoryRequest(f: gethistoryActionType)(request: gethistoryActionRequestType) = {
            implicit val gethistoryWritableJson = anyToWritable[gethistoryActionResultType](gethistoryResponseMimeType)
            val callerResult = f(request)
            val status = callerResult match {
                case Failure(error) => (errorToStatusgethistory orElse defaultErrorMapping)(error)
                case Success(result) => gethistoryActionSuccessStatus(result)
            }
            status
        }
        }


    