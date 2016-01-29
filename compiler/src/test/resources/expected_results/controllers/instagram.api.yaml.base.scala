package instagram.api.yaml

import play.api.mvc.{Action, Controller, Results}
import play.api.http.Writeable
import Results.Status
import de.zalando.play.controllers.{PlayBodyParsing, ParsingError}
import PlayBodyParsing._
import scala.util._
import de.zalando.play.controllers.PlayPathBindables




trait InstagramApiYamlBase extends Controller with PlayBodyParsing {
    private type getusersSearchActionRequestType       = (String, MediaFilter)
    private type getusersSearchActionType              = getusersSearchActionRequestType => Try[Any]

    private val errorToStatusgetusersSearch: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def getusersSearchAction = (f: getusersSearchActionType) => (q: String, count: MediaFilter) => Action {        val getusersSearchResponseMimeType    = "application/json"

        val possibleWriters = Map(
                200 -> anyToWritable[`UsersUser-idFollowsGetResponses200`]
        )        
            val result =                
                    new UsersSearchGetValidator(q, count).errors match {
                        case e if e.isEmpty => processValidgetusersSearchRequest(f)((q, count), possibleWriters, getusersSearchResponseMimeType)
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getusersSearchResponseMimeType)
                            BadRequest(l)
                    }
                
            result
    }

    private def processValidgetusersSearchRequest[T <: Any](f: getusersSearchActionType)(request: getusersSearchActionRequestType, writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetusersSearch orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val getusersSearchWritableJson = writer(mimeType)
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        }
        status
    }
    private type getusersSelfMediaLikedActionRequestType       = (MediaId, MediaId)
    private type getusersSelfMediaLikedActionType              = getusersSelfMediaLikedActionRequestType => Try[Any]

    private val errorToStatusgetusersSelfMediaLiked: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def getusersSelfMediaLikedAction = (f: getusersSelfMediaLikedActionType) => (count: MediaId, max_like_id: MediaId) => Action {        val getusersSelfMediaLikedResponseMimeType    = "application/json"

        val possibleWriters = Map(
                200 -> anyToWritable[UsersSelfFeedGetResponses200]
        )        
            val result =                
                    new UsersSelfMediaLikedGetValidator(count, max_like_id).errors match {
                        case e if e.isEmpty => processValidgetusersSelfMediaLikedRequest(f)((count, max_like_id), possibleWriters, getusersSelfMediaLikedResponseMimeType)
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getusersSelfMediaLikedResponseMimeType)
                            BadRequest(l)
                    }
                
            result
    }

    private def processValidgetusersSelfMediaLikedRequest[T <: Any](f: getusersSelfMediaLikedActionType)(request: getusersSelfMediaLikedActionRequestType, writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetusersSelfMediaLiked orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val getusersSelfMediaLikedWritableJson = writer(mimeType)
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        }
        status
    }
    private type gettagsSearchActionRequestType       = (MediaFilter)
    private type gettagsSearchActionType              = gettagsSearchActionRequestType => Try[Any]

    private val errorToStatusgettagsSearch: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def gettagsSearchAction = (f: gettagsSearchActionType) => (q: MediaFilter) => Action {        val gettagsSearchResponseMimeType    = "application/json"

        val possibleWriters = Map(
                200 -> anyToWritable[TagsSearchGetResponses200]
        )        
            val result =                
                    new TagsSearchGetValidator(q).errors match {
                        case e if e.isEmpty => processValidgettagsSearchRequest(f)((q), possibleWriters, gettagsSearchResponseMimeType)
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(gettagsSearchResponseMimeType)
                            BadRequest(l)
                    }
                
            result
    }

    private def processValidgettagsSearchRequest[T <: Any](f: gettagsSearchActionType)(request: gettagsSearchActionRequestType, writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgettagsSearch orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val gettagsSearchWritableJson = writer(mimeType)
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        }
        status
    }
    private type getusersSelfFeedActionRequestType       = (MediaId, MediaId, MediaId)
    private type getusersSelfFeedActionType              = getusersSelfFeedActionRequestType => Try[Any]

    private val errorToStatusgetusersSelfFeed: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def getusersSelfFeedAction = (f: getusersSelfFeedActionType) => (count: MediaId, max_id: MediaId, min_id: MediaId) => Action {        val getusersSelfFeedResponseMimeType    = "application/json"

        val possibleWriters = Map(
                200 -> anyToWritable[UsersSelfFeedGetResponses200]
        )        
            val result =                
                    new UsersSelfFeedGetValidator(count, max_id, min_id).errors match {
                        case e if e.isEmpty => processValidgetusersSelfFeedRequest(f)((count, max_id, min_id), possibleWriters, getusersSelfFeedResponseMimeType)
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getusersSelfFeedResponseMimeType)
                            BadRequest(l)
                    }
                
            result
    }

    private def processValidgetusersSelfFeedRequest[T <: Any](f: getusersSelfFeedActionType)(request: getusersSelfFeedActionRequestType, writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetusersSelfFeed orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val getusersSelfFeedWritableJson = writer(mimeType)
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        }
        status
    }
    private type getmediaSearchActionRequestType       = (MediaId, Int, LocationLatitude, MediaId, LocationLatitude)
    private type getmediaSearchActionType              = getmediaSearchActionRequestType => Try[Any]

    private val errorToStatusgetmediaSearch: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def getmediaSearchAction = (f: getmediaSearchActionType) => (mAX_TIMESTAMP: MediaId, dISTANCE: Int, lNG: LocationLatitude, mIN_TIMESTAMP: MediaId, lAT: LocationLatitude) => Action {        val getmediaSearchResponseMimeType    = "application/json"

        val possibleWriters = Map(
                200 -> anyToWritable[MediaSearchGetResponses200]
        )        
            val result =                
                    new MediaSearchGetValidator(mAX_TIMESTAMP, dISTANCE, lNG, mIN_TIMESTAMP, lAT).errors match {
                        case e if e.isEmpty => processValidgetmediaSearchRequest(f)((mAX_TIMESTAMP, dISTANCE, lNG, mIN_TIMESTAMP, lAT), possibleWriters, getmediaSearchResponseMimeType)
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getmediaSearchResponseMimeType)
                            BadRequest(l)
                    }
                
            result
    }

    private def processValidgetmediaSearchRequest[T <: Any](f: getmediaSearchActionType)(request: getmediaSearchActionRequestType, writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetmediaSearch orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val getmediaSearchWritableJson = writer(mimeType)
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        }
        status
    }
    private type getmediaByShortcodeActionRequestType       = (String)
    private type getmediaByShortcodeActionType              = getmediaByShortcodeActionRequestType => Try[Any]

    private val errorToStatusgetmediaByShortcode: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def getmediaByShortcodeAction = (f: getmediaByShortcodeActionType) => (shortcode: String) => Action {        val getmediaByShortcodeResponseMimeType    = "application/json"

        val possibleWriters = Map(
                200 -> anyToWritable[Media]
        )        
            val result =                
                    new MediaShortcodeGetValidator(shortcode).errors match {
                        case e if e.isEmpty => processValidgetmediaByShortcodeRequest(f)((shortcode), possibleWriters, getmediaByShortcodeResponseMimeType)
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getmediaByShortcodeResponseMimeType)
                            BadRequest(l)
                    }
                
            result
    }

    private def processValidgetmediaByShortcodeRequest[T <: Any](f: getmediaByShortcodeActionType)(request: getmediaByShortcodeActionRequestType, writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetmediaByShortcode orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val getmediaByShortcodeWritableJson = writer(mimeType)
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        }
        status
    }
    private type getlocationsSearchActionRequestType       = (MediaId, MediaId, MediaId, LocationLatitude, MediaId, LocationLatitude)
    private type getlocationsSearchActionType              = getlocationsSearchActionRequestType => Try[Any]

    private val errorToStatusgetlocationsSearch: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def getlocationsSearchAction = (f: getlocationsSearchActionType) => (foursquare_v2_id: MediaId, facebook_places_id: MediaId, distance: MediaId, lat: LocationLatitude, foursquare_id: MediaId, lng: LocationLatitude) => Action {        val getlocationsSearchResponseMimeType    = "application/json"

        val possibleWriters = Map(
                200 -> anyToWritable[LocationsSearchGetResponses200]
        )        
            val result =                
                    new LocationsSearchGetValidator(foursquare_v2_id, facebook_places_id, distance, lat, foursquare_id, lng).errors match {
                        case e if e.isEmpty => processValidgetlocationsSearchRequest(f)((foursquare_v2_id, facebook_places_id, distance, lat, foursquare_id, lng), possibleWriters, getlocationsSearchResponseMimeType)
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getlocationsSearchResponseMimeType)
                            BadRequest(l)
                    }
                
            result
    }

    private def processValidgetlocationsSearchRequest[T <: Any](f: getlocationsSearchActionType)(request: getlocationsSearchActionRequestType, writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetlocationsSearch orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val getlocationsSearchWritableJson = writer(mimeType)
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        }
        status
    }
    private type getusersSelfRequested_byActionRequestType       = (Unit)
    private type getusersSelfRequested_byActionType              = getusersSelfRequested_byActionRequestType => Try[Any]

    private val errorToStatusgetusersSelfRequested_by: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def getusersSelfRequested_byAction = (f: getusersSelfRequested_byActionType) => Action {        val getusersSelfRequested_byResponseMimeType    = "application/json"

        val possibleWriters = Map(
                200 -> anyToWritable[`UsersSelfRequested-byGetResponses200`]
        )        
            val result = processValidgetusersSelfRequested_byRequest(f)()                
            result
    }

    private def processValidgetusersSelfRequested_byRequest[T <: Any](f: getusersSelfRequested_byActionType)(request: getusersSelfRequested_byActionRequestType, writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetusersSelfRequested_by orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val getusersSelfRequested_byWritableJson = writer(mimeType)
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        }
        status
    }
    private type getmediaPopularActionRequestType       = (Unit)
    private type getmediaPopularActionType              = getmediaPopularActionRequestType => Try[Any]

    private val errorToStatusgetmediaPopular: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def getmediaPopularAction = (f: getmediaPopularActionType) => Action {        val getmediaPopularResponseMimeType    = "application/json"

        val possibleWriters = Map(
                200 -> anyToWritable[UsersSelfFeedGetResponses200]
        )        
            val result = processValidgetmediaPopularRequest(f)()                
            result
    }

    private def processValidgetmediaPopularRequest[T <: Any](f: getmediaPopularActionType)(request: getmediaPopularActionRequestType, writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetmediaPopular orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val getmediaPopularWritableJson = writer(mimeType)
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        }
        status
    }
}
