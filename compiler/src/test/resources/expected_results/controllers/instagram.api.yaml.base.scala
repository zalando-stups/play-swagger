package instagram.api.yaml
import play.api.mvc.{Action, Controller, Results}
import play.api.http.Writeable
import Results.Status
import de.zalando.play.controllers.{PlayBodyParsing, ParsingError}
import PlayBodyParsing._
import scala.util._
import de.zalando.play.controllers.ArrayWrapper
import de.zalando.play.controllers.PlayPathBindables
trait InstagramApiYamlBase extends Controller with PlayBodyParsing {
    private val getmediaByMedia_idLikesResponseMimeType    = "application/json"
    private val getmediaByMedia_idLikesActionSuccessStatus = Status(200)

    private type getmediaByMedia_idLikesActionRequestType       = (Int)
    private type getmediaByMedia_idLikesActionResultType        = Media
    private type getmediaByMedia_idLikesActionType              = getmediaByMedia_idLikesActionRequestType => Try[getmediaByMedia_idLikesActionResultType]

    private val errorToStatusgetmediaByMedia_idLikes: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    


    


    def getmediaByMedia_idLikesAction = (f: getmediaByMedia_idLikesActionType) => (`media-id`: Int) => Action { 

        val result = 

            new `MediaMedia-idLikesGetValidator`(`media-id`).errors match {
                    case e if e.isEmpty => processValidgetmediaByMedia_idLikesRequest(f)((`media-id`))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getmediaByMedia_idLikesResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValidgetmediaByMedia_idLikesRequest(f: getmediaByMedia_idLikesActionType)(request: getmediaByMedia_idLikesActionRequestType) = {
        implicit val getmediaByMedia_idLikesWritableJson = anyToWritable[getmediaByMedia_idLikesActionResultType](getmediaByMedia_idLikesResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetmediaByMedia_idLikes orElse defaultErrorMapping)(error)
            case Success(result) => getmediaByMedia_idLikesActionSuccessStatus(result)
        }
        status
    }
    private val postmediaByMedia_idLikesResponseMimeType    = "application/json"
    private val postmediaByMedia_idLikesActionSuccessStatus = Status(200)

    private type postmediaByMedia_idLikesActionRequestType       = (Int)
    private type postmediaByMedia_idLikesActionResultType        = Media
    private type postmediaByMedia_idLikesActionType              = postmediaByMedia_idLikesActionRequestType => Try[postmediaByMedia_idLikesActionResultType]

    private val errorToStatuspostmediaByMedia_idLikes: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    


    


    def postmediaByMedia_idLikesAction = (f: postmediaByMedia_idLikesActionType) => (`media-id`: Int) => Action { 

        val result = 

            new `MediaMedia-idLikesPostValidator`(`media-id`).errors match {
                    case e if e.isEmpty => processValidpostmediaByMedia_idLikesRequest(f)((`media-id`))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(postmediaByMedia_idLikesResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValidpostmediaByMedia_idLikesRequest(f: postmediaByMedia_idLikesActionType)(request: postmediaByMedia_idLikesActionRequestType) = {
        implicit val postmediaByMedia_idLikesWritableJson = anyToWritable[postmediaByMedia_idLikesActionResultType](postmediaByMedia_idLikesResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatuspostmediaByMedia_idLikes orElse defaultErrorMapping)(error)
            case Success(result) => postmediaByMedia_idLikesActionSuccessStatus(result)
        }
        status
    }
    private val deletemediaByMedia_idLikesResponseMimeType    = "application/json"
    private val deletemediaByMedia_idLikesActionSuccessStatus = Status(200)

    private type deletemediaByMedia_idLikesActionRequestType       = (Int)
    private type deletemediaByMedia_idLikesActionResultType        = Media
    private type deletemediaByMedia_idLikesActionType              = deletemediaByMedia_idLikesActionRequestType => Try[deletemediaByMedia_idLikesActionResultType]

    private val errorToStatusdeletemediaByMedia_idLikes: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    


    


    def deletemediaByMedia_idLikesAction = (f: deletemediaByMedia_idLikesActionType) => (`media-id`: Int) => Action { 

        val result = 

            new `MediaMedia-idLikesDeleteValidator`(`media-id`).errors match {
                    case e if e.isEmpty => processValiddeletemediaByMedia_idLikesRequest(f)((`media-id`))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(deletemediaByMedia_idLikesResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValiddeletemediaByMedia_idLikesRequest(f: deletemediaByMedia_idLikesActionType)(request: deletemediaByMedia_idLikesActionRequestType) = {
        implicit val deletemediaByMedia_idLikesWritableJson = anyToWritable[deletemediaByMedia_idLikesActionResultType](deletemediaByMedia_idLikesResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusdeletemediaByMedia_idLikes orElse defaultErrorMapping)(error)
            case Success(result) => deletemediaByMedia_idLikesActionSuccessStatus(result)
        }
        status
    }
    private val getusersByUser_idFollowsResponseMimeType    = "application/json"
    private val getusersByUser_idFollowsActionSuccessStatus = Status(200)

    private type getusersByUser_idFollowsActionRequestType       = (Double)
    private type getusersByUser_idFollowsActionResultType        = Users
    private type getusersByUser_idFollowsActionType              = getusersByUser_idFollowsActionRequestType => Try[getusersByUser_idFollowsActionResultType]

    private val errorToStatusgetusersByUser_idFollows: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    


    


    def getusersByUser_idFollowsAction = (f: getusersByUser_idFollowsActionType) => (`user-id`: Double) => Action { 

        val result = 

            new `UsersUser-idFollowsGetValidator`(`user-id`).errors match {
                    case e if e.isEmpty => processValidgetusersByUser_idFollowsRequest(f)((`user-id`))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getusersByUser_idFollowsResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValidgetusersByUser_idFollowsRequest(f: getusersByUser_idFollowsActionType)(request: getusersByUser_idFollowsActionRequestType) = {
        implicit val getusersByUser_idFollowsWritableJson = anyToWritable[getusersByUser_idFollowsActionResultType](getusersByUser_idFollowsResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetusersByUser_idFollows orElse defaultErrorMapping)(error)
            case Success(result) => getusersByUser_idFollowsActionSuccessStatus(result)
        }
        status
    }
    private val getlocationsByLocation_idResponseMimeType    = "application/json"
    private val getlocationsByLocation_idActionSuccessStatus = Status(200)

    private type getlocationsByLocation_idActionRequestType       = (Int)
    private type getlocationsByLocation_idActionResultType        = Locations
    private type getlocationsByLocation_idActionType              = getlocationsByLocation_idActionRequestType => Try[getlocationsByLocation_idActionResultType]

    private val errorToStatusgetlocationsByLocation_id: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    


    


    def getlocationsByLocation_idAction = (f: getlocationsByLocation_idActionType) => (`location-id`: Int) => Action { 

        val result = 

            new `LocationsLocation-idGetValidator`(`location-id`).errors match {
                    case e if e.isEmpty => processValidgetlocationsByLocation_idRequest(f)((`location-id`))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getlocationsByLocation_idResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValidgetlocationsByLocation_idRequest(f: getlocationsByLocation_idActionType)(request: getlocationsByLocation_idActionRequestType) = {
        implicit val getlocationsByLocation_idWritableJson = anyToWritable[getlocationsByLocation_idActionResultType](getlocationsByLocation_idResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetlocationsByLocation_id orElse defaultErrorMapping)(error)
            case Success(result) => getlocationsByLocation_idActionSuccessStatus(result)
        }
        status
    }
    private val getusersSearchResponseMimeType    = "application/json"
    private val getusersSearchActionSuccessStatus = Status(200)

    private type getusersSearchActionRequestType       = (String, LikeUser_name)
    private type getusersSearchActionResultType        = Users
    private type getusersSearchActionType              = getusersSearchActionRequestType => Try[getusersSearchActionResultType]

    private val errorToStatusgetusersSearch: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    


    


    def getusersSearchAction = (f: getusersSearchActionType) => (q: String, count: LikeUser_name) => Action { 

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

    private type getusersSelfMediaLikedActionRequestType       = (MediaCreated_time, MediaCreated_time)
    private type getusersSelfMediaLikedActionResultType        = Users
    private type getusersSelfMediaLikedActionType              = getusersSelfMediaLikedActionRequestType => Try[getusersSelfMediaLikedActionResultType]

    private val errorToStatusgetusersSelfMediaLiked: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    


    


    def getusersSelfMediaLikedAction = (f: getusersSelfMediaLikedActionType) => (count: MediaCreated_time, max_like_id: MediaCreated_time) => Action { 

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
    private val gettagsByTag_nameResponseMimeType    = "application/json"
    private val gettagsByTag_nameActionSuccessStatus = Status(200)

    private type gettagsByTag_nameActionRequestType       = (String)
    private type gettagsByTag_nameActionResultType        = Tag
    private type gettagsByTag_nameActionType              = gettagsByTag_nameActionRequestType => Try[gettagsByTag_nameActionResultType]

    private val errorToStatusgettagsByTag_name: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    


    


    def gettagsByTag_nameAction = (f: gettagsByTag_nameActionType) => (`tag-name`: String) => Action { 

        val result = 

            new `TagsTag-nameGetValidator`(`tag-name`).errors match {
                    case e if e.isEmpty => processValidgettagsByTag_nameRequest(f)((`tag-name`))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(gettagsByTag_nameResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValidgettagsByTag_nameRequest(f: gettagsByTag_nameActionType)(request: gettagsByTag_nameActionRequestType) = {
        implicit val gettagsByTag_nameWritableJson = anyToWritable[gettagsByTag_nameActionResultType](gettagsByTag_nameResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgettagsByTag_name orElse defaultErrorMapping)(error)
            case Success(result) => gettagsByTag_nameActionSuccessStatus(result)
        }
        status
    }
    private val gettagsSearchResponseMimeType    = "application/json"
    private val gettagsSearchActionSuccessStatus = Status(200)

    private type gettagsSearchActionRequestType       = (LikeUser_name)
    private type gettagsSearchActionResultType        = Tags
    private type gettagsSearchActionType              = gettagsSearchActionRequestType => Try[gettagsSearchActionResultType]

    private val errorToStatusgettagsSearch: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    


    


    def gettagsSearchAction = (f: gettagsSearchActionType) => (q: LikeUser_name) => Action { 

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
    private val getusersByUser_idFollowed_byResponseMimeType    = "application/json"
    private val getusersByUser_idFollowed_byActionSuccessStatus = Status(200)

    private type getusersByUser_idFollowed_byActionRequestType       = (Double)
    private type getusersByUser_idFollowed_byActionResultType        = Users
    private type getusersByUser_idFollowed_byActionType              = getusersByUser_idFollowed_byActionRequestType => Try[getusersByUser_idFollowed_byActionResultType]

    private val errorToStatusgetusersByUser_idFollowed_by: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    


    


    def getusersByUser_idFollowed_byAction = (f: getusersByUser_idFollowed_byActionType) => (`user-id`: Double) => Action { 

        val result = 

            new `UsersUser-idFollowed-byGetValidator`(`user-id`).errors match {
                    case e if e.isEmpty => processValidgetusersByUser_idFollowed_byRequest(f)((`user-id`))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getusersByUser_idFollowed_byResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValidgetusersByUser_idFollowed_byRequest(f: getusersByUser_idFollowed_byActionType)(request: getusersByUser_idFollowed_byActionRequestType) = {
        implicit val getusersByUser_idFollowed_byWritableJson = anyToWritable[getusersByUser_idFollowed_byActionResultType](getusersByUser_idFollowed_byResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetusersByUser_idFollowed_by orElse defaultErrorMapping)(error)
            case Success(result) => getusersByUser_idFollowed_byActionSuccessStatus(result)
        }
        status
    }
    private val getmediaByMedia_idCommentsResponseMimeType    = "application/json"
    private val getmediaByMedia_idCommentsActionSuccessStatus = Status(200)

    private type getmediaByMedia_idCommentsActionRequestType       = (Int)
    private type getmediaByMedia_idCommentsActionResultType        = Media
    private type getmediaByMedia_idCommentsActionType              = getmediaByMedia_idCommentsActionRequestType => Try[getmediaByMedia_idCommentsActionResultType]

    private val errorToStatusgetmediaByMedia_idComments: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    


    


    def getmediaByMedia_idCommentsAction = (f: getmediaByMedia_idCommentsActionType) => (`media-id`: Int) => Action { 

        val result = 

            new `MediaMedia-idCommentsGetValidator`(`media-id`).errors match {
                    case e if e.isEmpty => processValidgetmediaByMedia_idCommentsRequest(f)((`media-id`))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getmediaByMedia_idCommentsResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValidgetmediaByMedia_idCommentsRequest(f: getmediaByMedia_idCommentsActionType)(request: getmediaByMedia_idCommentsActionRequestType) = {
        implicit val getmediaByMedia_idCommentsWritableJson = anyToWritable[getmediaByMedia_idCommentsActionResultType](getmediaByMedia_idCommentsResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetmediaByMedia_idComments orElse defaultErrorMapping)(error)
            case Success(result) => getmediaByMedia_idCommentsActionSuccessStatus(result)
        }
        status
    }
    private val postmediaByMedia_idCommentsResponseMimeType    = "application/json"
    private val postmediaByMedia_idCommentsActionSuccessStatus = Status(200)

    private type postmediaByMedia_idCommentsActionRequestType       = (Int, LocationLongitude)
    private type postmediaByMedia_idCommentsActionResultType        = Media
    private type postmediaByMedia_idCommentsActionType              = postmediaByMedia_idCommentsActionRequestType => Try[postmediaByMedia_idCommentsActionResultType]

    private val errorToStatuspostmediaByMedia_idComments: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    


    private def postmediaByMedia_idCommentsParser(maxLength: Int = parse.DefaultMaxTextLength) = anyParser[LocationLongitude]("application/json", "Invalid LocationLongitude", maxLength)
    


    def postmediaByMedia_idCommentsAction = (f: postmediaByMedia_idCommentsActionType) => (`media-id`: Int) => Action (postmediaByMedia_idCommentsParser()){ request =>

        val tEXT = request.body
        val result = 

            new `MediaMedia-idCommentsPostValidator`(`media-id`, tEXT).errors match {
                    case e if e.isEmpty => processValidpostmediaByMedia_idCommentsRequest(f)((`media-id`, tEXT))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(postmediaByMedia_idCommentsResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValidpostmediaByMedia_idCommentsRequest(f: postmediaByMedia_idCommentsActionType)(request: postmediaByMedia_idCommentsActionRequestType) = {
        implicit val postmediaByMedia_idCommentsWritableJson = anyToWritable[postmediaByMedia_idCommentsActionResultType](postmediaByMedia_idCommentsResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatuspostmediaByMedia_idComments orElse defaultErrorMapping)(error)
            case Success(result) => postmediaByMedia_idCommentsActionSuccessStatus(result)
        }
        status
    }
    private val deletemediaByMedia_idCommentsResponseMimeType    = "application/json"
    private val deletemediaByMedia_idCommentsActionSuccessStatus = Status(200)

    private type deletemediaByMedia_idCommentsActionRequestType       = (Int)
    private type deletemediaByMedia_idCommentsActionResultType        = Media
    private type deletemediaByMedia_idCommentsActionType              = deletemediaByMedia_idCommentsActionRequestType => Try[deletemediaByMedia_idCommentsActionResultType]

    private val errorToStatusdeletemediaByMedia_idComments: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    


    


    def deletemediaByMedia_idCommentsAction = (f: deletemediaByMedia_idCommentsActionType) => (`media-id`: Int) => Action { 

        val result = 

            new `MediaMedia-idCommentsDeleteValidator`(`media-id`).errors match {
                    case e if e.isEmpty => processValiddeletemediaByMedia_idCommentsRequest(f)((`media-id`))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(deletemediaByMedia_idCommentsResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValiddeletemediaByMedia_idCommentsRequest(f: deletemediaByMedia_idCommentsActionType)(request: deletemediaByMedia_idCommentsActionRequestType) = {
        implicit val deletemediaByMedia_idCommentsWritableJson = anyToWritable[deletemediaByMedia_idCommentsActionResultType](deletemediaByMedia_idCommentsResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusdeletemediaByMedia_idComments orElse defaultErrorMapping)(error)
            case Success(result) => deletemediaByMedia_idCommentsActionSuccessStatus(result)
        }
        status
    }
    private val gettagsByTag_nameMediaRecentResponseMimeType    = "application/json"
    private val gettagsByTag_nameMediaRecentActionSuccessStatus = Status(200)

    private type gettagsByTag_nameMediaRecentActionRequestType       = (String)
    private type gettagsByTag_nameMediaRecentActionResultType        = Tags
    private type gettagsByTag_nameMediaRecentActionType              = gettagsByTag_nameMediaRecentActionRequestType => Try[gettagsByTag_nameMediaRecentActionResultType]

    private val errorToStatusgettagsByTag_nameMediaRecent: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    


    


    def gettagsByTag_nameMediaRecentAction = (f: gettagsByTag_nameMediaRecentActionType) => (`tag-name`: String) => Action { 

        val result = 

            new `TagsTag-nameMediaRecentGetValidator`(`tag-name`).errors match {
                    case e if e.isEmpty => processValidgettagsByTag_nameMediaRecentRequest(f)((`tag-name`))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(gettagsByTag_nameMediaRecentResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValidgettagsByTag_nameMediaRecentRequest(f: gettagsByTag_nameMediaRecentActionType)(request: gettagsByTag_nameMediaRecentActionRequestType) = {
        implicit val gettagsByTag_nameMediaRecentWritableJson = anyToWritable[gettagsByTag_nameMediaRecentActionResultType](gettagsByTag_nameMediaRecentResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgettagsByTag_nameMediaRecent orElse defaultErrorMapping)(error)
            case Success(result) => gettagsByTag_nameMediaRecentActionSuccessStatus(result)
        }
        status
    }
    private val postusersByUser_idRelationshipResponseMimeType    = "application/json"
    private val postusersByUser_idRelationshipActionSuccessStatus = Status(200)

    private type postusersByUser_idRelationshipActionRequestType       = (Double, LikeUser_name)
    private type postusersByUser_idRelationshipActionResultType        = Users
    private type postusersByUser_idRelationshipActionType              = postusersByUser_idRelationshipActionRequestType => Try[postusersByUser_idRelationshipActionResultType]

    private val errorToStatuspostusersByUser_idRelationship: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    


    private def postusersByUser_idRelationshipParser(maxLength: Int = parse.DefaultMaxTextLength) = anyParser[LikeUser_name]("application/json", "Invalid LikeUser_name", maxLength)
    


    def postusersByUser_idRelationshipAction = (f: postusersByUser_idRelationshipActionType) => (`user-id`: Double) => Action (postusersByUser_idRelationshipParser()){ request =>

        val action = request.body
        val result = 

            new `UsersUser-idRelationshipPostValidator`(`user-id`, action).errors match {
                    case e if e.isEmpty => processValidpostusersByUser_idRelationshipRequest(f)((`user-id`, action))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(postusersByUser_idRelationshipResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValidpostusersByUser_idRelationshipRequest(f: postusersByUser_idRelationshipActionType)(request: postusersByUser_idRelationshipActionRequestType) = {
        implicit val postusersByUser_idRelationshipWritableJson = anyToWritable[postusersByUser_idRelationshipActionResultType](postusersByUser_idRelationshipResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatuspostusersByUser_idRelationship orElse defaultErrorMapping)(error)
            case Success(result) => postusersByUser_idRelationshipActionSuccessStatus(result)
        }
        status
    }
    private val getusersSelfFeedResponseMimeType    = "application/json"
    private val getusersSelfFeedActionSuccessStatus = Status(200)

    private type getusersSelfFeedActionRequestType       = (MediaCreated_time, MediaCreated_time, MediaCreated_time)
    private type getusersSelfFeedActionResultType        = Users
    private type getusersSelfFeedActionType              = getusersSelfFeedActionRequestType => Try[getusersSelfFeedActionResultType]

    private val errorToStatusgetusersSelfFeed: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    


    


    def getusersSelfFeedAction = (f: getusersSelfFeedActionType) => (count: MediaCreated_time, max_id: MediaCreated_time, min_id: MediaCreated_time) => Action { 

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
    private val getusersByUser_idResponseMimeType    = "application/json"
    private val getusersByUser_idActionSuccessStatus = Status(200)

    private type getusersByUser_idActionRequestType       = (Double)
    private type getusersByUser_idActionResultType        = Users
    private type getusersByUser_idActionType              = getusersByUser_idActionRequestType => Try[getusersByUser_idActionResultType]

    private val errorToStatusgetusersByUser_id: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    


    


    def getusersByUser_idAction = (f: getusersByUser_idActionType) => (`user-id`: Double) => Action { 

        val result = 

            new `UsersUser-idGetValidator`(`user-id`).errors match {
                    case e if e.isEmpty => processValidgetusersByUser_idRequest(f)((`user-id`))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getusersByUser_idResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValidgetusersByUser_idRequest(f: getusersByUser_idActionType)(request: getusersByUser_idActionRequestType) = {
        implicit val getusersByUser_idWritableJson = anyToWritable[getusersByUser_idActionResultType](getusersByUser_idResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetusersByUser_id orElse defaultErrorMapping)(error)
            case Success(result) => getusersByUser_idActionSuccessStatus(result)
        }
        status
    }
    private val getmediaSearchResponseMimeType    = "application/json"
    private val getmediaSearchActionSuccessStatus = Status(200)

    private type getmediaSearchActionRequestType       = (MediaCreated_time, MediaCreated_time, LocationLongitude, MediaCreated_time, LocationLongitude)
    private type getmediaSearchActionResultType        = Media
    private type getmediaSearchActionType              = getmediaSearchActionRequestType => Try[getmediaSearchActionResultType]

    private val errorToStatusgetmediaSearch: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    


    


    def getmediaSearchAction = (f: getmediaSearchActionType) => (mAX_TIMESTAMP: MediaCreated_time, dISTANCE: MediaCreated_time, lNG: LocationLongitude, mIN_TIMESTAMP: MediaCreated_time, lAT: LocationLongitude) => Action { 

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
    private val getgeographiesByGeo_idMediaRecentResponseMimeType    = "application/json"
    private val getgeographiesByGeo_idMediaRecentActionSuccessStatus = Status(200)

    private type getgeographiesByGeo_idMediaRecentActionRequestType       = (Int, MediaCreated_time, MediaCreated_time)
    private type getgeographiesByGeo_idMediaRecentActionResultType        = Null
    private type getgeographiesByGeo_idMediaRecentActionType              = getgeographiesByGeo_idMediaRecentActionRequestType => Try[getgeographiesByGeo_idMediaRecentActionResultType]

    private val errorToStatusgetgeographiesByGeo_idMediaRecent: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    


    


    def getgeographiesByGeo_idMediaRecentAction = (f: getgeographiesByGeo_idMediaRecentActionType) => (`geo-id`: Int, count: MediaCreated_time, min_id: MediaCreated_time) => Action { 

        val result = 

            new `GeographiesGeo-idMediaRecentGetValidator`(`geo-id`, count, min_id).errors match {
                    case e if e.isEmpty => processValidgetgeographiesByGeo_idMediaRecentRequest(f)((`geo-id`, count, min_id))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getgeographiesByGeo_idMediaRecentResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValidgetgeographiesByGeo_idMediaRecentRequest(f: getgeographiesByGeo_idMediaRecentActionType)(request: getgeographiesByGeo_idMediaRecentActionRequestType) = {
        implicit val getgeographiesByGeo_idMediaRecentWritableJson = anyToWritable[getgeographiesByGeo_idMediaRecentActionResultType](getgeographiesByGeo_idMediaRecentResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetgeographiesByGeo_idMediaRecent orElse defaultErrorMapping)(error)
            case Success(result) => getgeographiesByGeo_idMediaRecentActionSuccessStatus(result)
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

    private type getlocationsSearchActionRequestType       = (MediaCreated_time, MediaCreated_time, MediaCreated_time, LocationLongitude, MediaCreated_time, LocationLongitude)
    private type getlocationsSearchActionResultType        = Locations
    private type getlocationsSearchActionType              = getlocationsSearchActionRequestType => Try[getlocationsSearchActionResultType]

    private val errorToStatusgetlocationsSearch: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    


    


    def getlocationsSearchAction = (f: getlocationsSearchActionType) => (foursquare_v2_id: MediaCreated_time, facebook_places_id: MediaCreated_time, distance: MediaCreated_time, lat: LocationLongitude, foursquare_id: MediaCreated_time, lng: LocationLongitude) => Action { 

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
    private type getusersSelfRequested_byActionResultType        = Users
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
    private val getmediaByMedia_idResponseMimeType    = "application/json"
    private val getmediaByMedia_idActionSuccessStatus = Status(200)

    private type getmediaByMedia_idActionRequestType       = (Int)
    private type getmediaByMedia_idActionResultType        = Media
    private type getmediaByMedia_idActionType              = getmediaByMedia_idActionRequestType => Try[getmediaByMedia_idActionResultType]

    private val errorToStatusgetmediaByMedia_id: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    


    


    def getmediaByMedia_idAction = (f: getmediaByMedia_idActionType) => (`media-id`: Int) => Action { 

        val result = 

            new `MediaMedia-idGetValidator`(`media-id`).errors match {
                    case e if e.isEmpty => processValidgetmediaByMedia_idRequest(f)((`media-id`))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getmediaByMedia_idResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValidgetmediaByMedia_idRequest(f: getmediaByMedia_idActionType)(request: getmediaByMedia_idActionRequestType) = {
        implicit val getmediaByMedia_idWritableJson = anyToWritable[getmediaByMedia_idActionResultType](getmediaByMedia_idResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetmediaByMedia_id orElse defaultErrorMapping)(error)
            case Success(result) => getmediaByMedia_idActionSuccessStatus(result)
        }
        status
    }
    private val getlocationsByLocation_idMediaRecentResponseMimeType    = "application/json"
    private val getlocationsByLocation_idMediaRecentActionSuccessStatus = Status(200)

    private type getlocationsByLocation_idMediaRecentActionRequestType       = (Int, MediaCreated_time, MediaCreated_time, LikeUser_name, LikeUser_name)
    private type getlocationsByLocation_idMediaRecentActionResultType        = Users
    private type getlocationsByLocation_idMediaRecentActionType              = getlocationsByLocation_idMediaRecentActionRequestType => Try[getlocationsByLocation_idMediaRecentActionResultType]

    private val errorToStatusgetlocationsByLocation_idMediaRecent: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    


    


    def getlocationsByLocation_idMediaRecentAction = (f: getlocationsByLocation_idMediaRecentActionType) => (`location-id`: Int, max_timestamp: MediaCreated_time, min_timestamp: MediaCreated_time, min_id: LikeUser_name, max_id: LikeUser_name) => Action { 

        val result = 

            new `LocationsLocation-idMediaRecentGetValidator`(`location-id`, max_timestamp, min_timestamp, min_id, max_id).errors match {
                    case e if e.isEmpty => processValidgetlocationsByLocation_idMediaRecentRequest(f)((`location-id`, max_timestamp, min_timestamp, min_id, max_id))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getlocationsByLocation_idMediaRecentResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValidgetlocationsByLocation_idMediaRecentRequest(f: getlocationsByLocation_idMediaRecentActionType)(request: getlocationsByLocation_idMediaRecentActionRequestType) = {
        implicit val getlocationsByLocation_idMediaRecentWritableJson = anyToWritable[getlocationsByLocation_idMediaRecentActionResultType](getlocationsByLocation_idMediaRecentResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetlocationsByLocation_idMediaRecent orElse defaultErrorMapping)(error)
            case Success(result) => getlocationsByLocation_idMediaRecentActionSuccessStatus(result)
        }
        status
    }
    private val getusersByUser_idMediaRecentResponseMimeType    = "application/json"
    private val getusersByUser_idMediaRecentActionSuccessStatus = Status(200)

    private type getusersByUser_idMediaRecentActionRequestType       = (Double, MediaCreated_time, LikeUser_name, MediaCreated_time, LikeUser_name, MediaCreated_time)
    private type getusersByUser_idMediaRecentActionResultType        = Users
    private type getusersByUser_idMediaRecentActionType              = getusersByUser_idMediaRecentActionRequestType => Try[getusersByUser_idMediaRecentActionResultType]

    private val errorToStatusgetusersByUser_idMediaRecent: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    


    


    def getusersByUser_idMediaRecentAction = (f: getusersByUser_idMediaRecentActionType) => (`user-id`: Double, max_timestamp: MediaCreated_time, min_id: LikeUser_name, min_timestamp: MediaCreated_time, max_id: LikeUser_name, count: MediaCreated_time) => Action { 

        val result = 

            new `UsersUser-idMediaRecentGetValidator`(`user-id`, max_timestamp, min_id, min_timestamp, max_id, count).errors match {
                    case e if e.isEmpty => processValidgetusersByUser_idMediaRecentRequest(f)((`user-id`, max_timestamp, min_id, min_timestamp, max_id, count))
                    case l =>
                        implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getusersByUser_idMediaRecentResponseMimeType)
                        BadRequest(l)
                }

            result
    }

    private def processValidgetusersByUser_idMediaRecentRequest(f: getusersByUser_idMediaRecentActionType)(request: getusersByUser_idMediaRecentActionRequestType) = {
        implicit val getusersByUser_idMediaRecentWritableJson = anyToWritable[getusersByUser_idMediaRecentActionResultType](getusersByUser_idMediaRecentResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetusersByUser_idMediaRecent orElse defaultErrorMapping)(error)
            case Success(result) => getusersByUser_idMediaRecentActionSuccessStatus(result)
        }
        status
    }
    private val getmediaPopularResponseMimeType    = "application/json"
    private val getmediaPopularActionSuccessStatus = Status(200)

    private type getmediaPopularActionRequestType       = (Unit)
    private type getmediaPopularActionResultType        = Users
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
