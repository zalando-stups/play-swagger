package instagram.api.yaml

import play.api.mvc.{Action, Controller, Results}
import play.api.http.Writeable
import Results.Status
import de.zalando.play.controllers.{PlayBodyParsing, ParsingError}
import PlayBodyParsing._
import scala.util._
import de.zalando.play.controllers.PlayPathBindables




trait InstagramApiYamlBase extends Controller with PlayBodyParsing {
    private val getusersSearchResponseMimeType    = "application/json"
    private val getusersSearchActionSuccessStatus = Status(200)

    private type getusersSearchActionRequestType       = (String, MediaFilter)
    private type getusersSearchActionResultType        = `UsersUser-idFollowsGetResponses200`
    private type getusersSearchActionType              = getusersSearchActionRequestType => Try[getusersSearchActionResultType]

    private val errorToStatusgetusersSearch: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def getusersSearchAction = (f: getusersSearchActionType) => (q: String, count: MediaFilter) => Action {        
            val result =                
                    new UsersSearchGetValidator(q, count).errors match {
                        case e if e.isEmpty => processValidgetusersSearchRequest(f)((q, count))
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getusersSearchResponseMimeType)
                            BadRequest(l)
                    }
                
            result
    }

    private def processValidgetusersSearchRequest(f: getusersSearchActionType)(request: getusersSearchActionRequestType) = {
        implicit val getusersSearchWritableJson = anyToWritable[getusersSearchActionResultType](getusersSearchResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetusersSearch orElse defaultErrorMapping)(error)
            case Success(result) => getusersSearchActionSuccessStatus(result)
        }
        status
    }
    private val getusersSelfMediaLikedResponseMimeType    = "application/json"
    private val getusersSelfMediaLikedActionSuccessStatus = Status(200)

    private type getusersSelfMediaLikedActionRequestType       = (MediaId, MediaId)
    private type getusersSelfMediaLikedActionResultType        = UsersSelfFeedGetResponses200
    private type getusersSelfMediaLikedActionType              = getusersSelfMediaLikedActionRequestType => Try[getusersSelfMediaLikedActionResultType]

    private val errorToStatusgetusersSelfMediaLiked: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def getusersSelfMediaLikedAction = (f: getusersSelfMediaLikedActionType) => (count: MediaId, max_like_id: MediaId) => Action {        
            val result =                
                    new UsersSelfMediaLikedGetValidator(count, max_like_id).errors match {
                        case e if e.isEmpty => processValidgetusersSelfMediaLikedRequest(f)((count, max_like_id))
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getusersSelfMediaLikedResponseMimeType)
                            BadRequest(l)
                    }
                
            result
    }

    private def processValidgetusersSelfMediaLikedRequest(f: getusersSelfMediaLikedActionType)(request: getusersSelfMediaLikedActionRequestType) = {
        implicit val getusersSelfMediaLikedWritableJson = anyToWritable[getusersSelfMediaLikedActionResultType](getusersSelfMediaLikedResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetusersSelfMediaLiked orElse defaultErrorMapping)(error)
            case Success(result) => getusersSelfMediaLikedActionSuccessStatus(result)
        }
        status
    }
    private val gettagsSearchResponseMimeType    = "application/json"
    private val gettagsSearchActionSuccessStatus = Status(200)

    private type gettagsSearchActionRequestType       = (MediaFilter)
    private type gettagsSearchActionResultType        = TagsSearchGetResponses200
    private type gettagsSearchActionType              = gettagsSearchActionRequestType => Try[gettagsSearchActionResultType]

    private val errorToStatusgettagsSearch: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def gettagsSearchAction = (f: gettagsSearchActionType) => (q: MediaFilter) => Action {        
            val result =                
                    new TagsSearchGetValidator(q).errors match {
                        case e if e.isEmpty => processValidgettagsSearchRequest(f)((q))
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(gettagsSearchResponseMimeType)
                            BadRequest(l)
                    }
                
            result
    }

    private def processValidgettagsSearchRequest(f: gettagsSearchActionType)(request: gettagsSearchActionRequestType) = {
        implicit val gettagsSearchWritableJson = anyToWritable[gettagsSearchActionResultType](gettagsSearchResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgettagsSearch orElse defaultErrorMapping)(error)
            case Success(result) => gettagsSearchActionSuccessStatus(result)
        }
        status
    }
    private val getusersSelfFeedResponseMimeType    = "application/json"
    private val getusersSelfFeedActionSuccessStatus = Status(200)

    private type getusersSelfFeedActionRequestType       = (MediaId, MediaId, MediaId)
    private type getusersSelfFeedActionResultType        = UsersSelfFeedGetResponses200
    private type getusersSelfFeedActionType              = getusersSelfFeedActionRequestType => Try[getusersSelfFeedActionResultType]

    private val errorToStatusgetusersSelfFeed: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def getusersSelfFeedAction = (f: getusersSelfFeedActionType) => (count: MediaId, max_id: MediaId, min_id: MediaId) => Action {        
            val result =                
                    new UsersSelfFeedGetValidator(count, max_id, min_id).errors match {
                        case e if e.isEmpty => processValidgetusersSelfFeedRequest(f)((count, max_id, min_id))
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getusersSelfFeedResponseMimeType)
                            BadRequest(l)
                    }
                
            result
    }

    private def processValidgetusersSelfFeedRequest(f: getusersSelfFeedActionType)(request: getusersSelfFeedActionRequestType) = {
        implicit val getusersSelfFeedWritableJson = anyToWritable[getusersSelfFeedActionResultType](getusersSelfFeedResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetusersSelfFeed orElse defaultErrorMapping)(error)
            case Success(result) => getusersSelfFeedActionSuccessStatus(result)
        }
        status
    }
    private val getmediaSearchResponseMimeType    = "application/json"
    private val getmediaSearchActionSuccessStatus = Status(200)

    private type getmediaSearchActionRequestType       = (MediaId, MediaId, LocationLatitude, MediaId, LocationLatitude)
    private type getmediaSearchActionResultType        = MediaSearchGetResponses200
    private type getmediaSearchActionType              = getmediaSearchActionRequestType => Try[getmediaSearchActionResultType]

    private val errorToStatusgetmediaSearch: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def getmediaSearchAction = (f: getmediaSearchActionType) => (mAX_TIMESTAMP: MediaId, dISTANCE: MediaId, lNG: LocationLatitude, mIN_TIMESTAMP: MediaId, lAT: LocationLatitude) => Action {        
            val result =                
                    new MediaSearchGetValidator(mAX_TIMESTAMP, dISTANCE, lNG, mIN_TIMESTAMP, lAT).errors match {
                        case e if e.isEmpty => processValidgetmediaSearchRequest(f)((mAX_TIMESTAMP, dISTANCE, lNG, mIN_TIMESTAMP, lAT))
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getmediaSearchResponseMimeType)
                            BadRequest(l)
                    }
                
            result
    }

    private def processValidgetmediaSearchRequest(f: getmediaSearchActionType)(request: getmediaSearchActionRequestType) = {
        implicit val getmediaSearchWritableJson = anyToWritable[getmediaSearchActionResultType](getmediaSearchResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetmediaSearch orElse defaultErrorMapping)(error)
            case Success(result) => getmediaSearchActionSuccessStatus(result)
        }
        status
    }
    private val getmediaByShortcodeResponseMimeType    = "application/json"
    private val getmediaByShortcodeActionSuccessStatus = Status(200)

    private type getmediaByShortcodeActionRequestType       = (String)
    private type getmediaByShortcodeActionResultType        = Media
    private type getmediaByShortcodeActionType              = getmediaByShortcodeActionRequestType => Try[getmediaByShortcodeActionResultType]

    private val errorToStatusgetmediaByShortcode: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def getmediaByShortcodeAction = (f: getmediaByShortcodeActionType) => (shortcode: String) => Action {        
            val result =                
                    new MediaShortcodeGetValidator(shortcode).errors match {
                        case e if e.isEmpty => processValidgetmediaByShortcodeRequest(f)((shortcode))
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getmediaByShortcodeResponseMimeType)
                            BadRequest(l)
                    }
                
            result
    }

    private def processValidgetmediaByShortcodeRequest(f: getmediaByShortcodeActionType)(request: getmediaByShortcodeActionRequestType) = {
        implicit val getmediaByShortcodeWritableJson = anyToWritable[getmediaByShortcodeActionResultType](getmediaByShortcodeResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetmediaByShortcode orElse defaultErrorMapping)(error)
            case Success(result) => getmediaByShortcodeActionSuccessStatus(result)
        }
        status
    }
    private val getlocationsSearchResponseMimeType    = "application/json"
    private val getlocationsSearchActionSuccessStatus = Status(200)

    private type getlocationsSearchActionRequestType       = (MediaId, MediaId, MediaId, LocationLatitude, MediaId, LocationLatitude)
    private type getlocationsSearchActionResultType        = LocationsSearchGetResponses200
    private type getlocationsSearchActionType              = getlocationsSearchActionRequestType => Try[getlocationsSearchActionResultType]

    private val errorToStatusgetlocationsSearch: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def getlocationsSearchAction = (f: getlocationsSearchActionType) => (foursquare_v2_id: MediaId, facebook_places_id: MediaId, distance: MediaId, lat: LocationLatitude, foursquare_id: MediaId, lng: LocationLatitude) => Action {        
            val result =                
                    new LocationsSearchGetValidator(foursquare_v2_id, facebook_places_id, distance, lat, foursquare_id, lng).errors match {
                        case e if e.isEmpty => processValidgetlocationsSearchRequest(f)((foursquare_v2_id, facebook_places_id, distance, lat, foursquare_id, lng))
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getlocationsSearchResponseMimeType)
                            BadRequest(l)
                    }
                
            result
    }

    private def processValidgetlocationsSearchRequest(f: getlocationsSearchActionType)(request: getlocationsSearchActionRequestType) = {
        implicit val getlocationsSearchWritableJson = anyToWritable[getlocationsSearchActionResultType](getlocationsSearchResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetlocationsSearch orElse defaultErrorMapping)(error)
            case Success(result) => getlocationsSearchActionSuccessStatus(result)
        }
        status
    }
    private val getusersSelfRequested_byResponseMimeType    = "application/json"
    private val getusersSelfRequested_byActionSuccessStatus = Status(200)

    private type getusersSelfRequested_byActionRequestType       = (Unit)
    private type getusersSelfRequested_byActionResultType        = `UsersSelfRequested-byGetResponses200`
    private type getusersSelfRequested_byActionType              = getusersSelfRequested_byActionRequestType => Try[getusersSelfRequested_byActionResultType]

    private val errorToStatusgetusersSelfRequested_by: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def getusersSelfRequested_byAction = (f: getusersSelfRequested_byActionType) => Action {        
            val result = processValidgetusersSelfRequested_byRequest(f)()                
            result
    }

    private def processValidgetusersSelfRequested_byRequest(f: getusersSelfRequested_byActionType)(request: getusersSelfRequested_byActionRequestType) = {
        implicit val getusersSelfRequested_byWritableJson = anyToWritable[getusersSelfRequested_byActionResultType](getusersSelfRequested_byResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetusersSelfRequested_by orElse defaultErrorMapping)(error)
            case Success(result) => getusersSelfRequested_byActionSuccessStatus(result)
        }
        status
    }
    private val getmediaPopularResponseMimeType    = "application/json"
    private val getmediaPopularActionSuccessStatus = Status(200)

    private type getmediaPopularActionRequestType       = (Unit)
    private type getmediaPopularActionResultType        = UsersSelfFeedGetResponses200
    private type getmediaPopularActionType              = getmediaPopularActionRequestType => Try[getmediaPopularActionResultType]

    private val errorToStatusgetmediaPopular: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def getmediaPopularAction = (f: getmediaPopularActionType) => Action {        
            val result = processValidgetmediaPopularRequest(f)()                
            result
    }

    private def processValidgetmediaPopularRequest(f: getmediaPopularActionType)(request: getmediaPopularActionRequestType) = {
        implicit val getmediaPopularWritableJson = anyToWritable[getmediaPopularActionResultType](getmediaPopularResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetmediaPopular orElse defaultErrorMapping)(error)
            case Success(result) => getmediaPopularActionSuccessStatus(result)
        }
        status
    }
}
