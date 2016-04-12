package instagram.api.yaml

import play.api.mvc.{Action, Controller, Results}
import play.api.http._
import Results.Status

import de.zalando.play.controllers.{PlayBodyParsing, ParsingError, ResultWrapper, ResponseWriters}
import PlayBodyParsing._
import scala.util._
import scala.math.BigInt
import scala.math.BigDecimal

import de.zalando.play.controllers.PlayPathBindables



trait InstagramApiYamlBase extends Controller with PlayBodyParsing  with InstagramApiYamlSecurity {
    sealed trait getmediaByMedia_idLikesType[ResultT] extends ResultWrapper[ResultT]
    case class getmediaByMedia_idLikes200(result: MediaMedia_idLikesGetResponses200)(implicit val writer: String => Option[Writeable[MediaMedia_idLikesGetResponses200]]) extends getmediaByMedia_idLikesType[MediaMedia_idLikesGetResponses200] { val statusCode = 200 }
    

    private type getmediaByMedia_idLikesActionRequestType       = (BigInt)
    private type getmediaByMedia_idLikesActionType[T]            = getmediaByMedia_idLikesActionRequestType => getmediaByMedia_idLikesType[T]


    val getmediaByMedia_idLikesActionConstructor  = new getmediaByMedia_idLikesSecureAction("basic", "comments", "relationships", "likes")
    def getmediaByMedia_idLikesAction[T] = (f: getmediaByMedia_idLikesActionType[T]) => (media_id: BigInt) => getmediaByMedia_idLikesActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getmediaByMedia_idLikesResponseMimeType =>

            
            

                val result =
                        new MediaMedia_idLikesGetValidator(media_id).errors match {
                            case e if e.isEmpty => processValidgetmediaByMedia_idLikesRequest(f)((media_id))(getmediaByMedia_idLikesResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getmediaByMedia_idLikesResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetmediaByMedia_idLikesRequest[T](f: getmediaByMedia_idLikesActionType[T])(request: getmediaByMedia_idLikesActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait postmediaByMedia_idLikesType[ResultT] extends ResultWrapper[ResultT]
    case class postmediaByMedia_idLikes200(result: MediaMedia_idCommentsDeleteResponses200)(implicit val writer: String => Option[Writeable[MediaMedia_idCommentsDeleteResponses200]]) extends postmediaByMedia_idLikesType[MediaMedia_idCommentsDeleteResponses200] { val statusCode = 200 }
    

    private type postmediaByMedia_idLikesActionRequestType       = (BigInt)
    private type postmediaByMedia_idLikesActionType[T]            = postmediaByMedia_idLikesActionRequestType => postmediaByMedia_idLikesType[T]


    val postmediaByMedia_idLikesActionConstructor  = new postmediaByMedia_idLikesSecureAction("comments")
    def postmediaByMedia_idLikesAction[T] = (f: postmediaByMedia_idLikesActionType[T]) => (media_id: BigInt) => postmediaByMedia_idLikesActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { postmediaByMedia_idLikesResponseMimeType =>

            
            

                val result =
                        new MediaMedia_idLikesPostValidator(media_id).errors match {
                            case e if e.isEmpty => processValidpostmediaByMedia_idLikesRequest(f)((media_id))(postmediaByMedia_idLikesResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(postmediaByMedia_idLikesResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidpostmediaByMedia_idLikesRequest[T](f: postmediaByMedia_idLikesActionType[T])(request: postmediaByMedia_idLikesActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait deletemediaByMedia_idLikesType[ResultT] extends ResultWrapper[ResultT]
    case class deletemediaByMedia_idLikes200(result: MediaMedia_idCommentsDeleteResponses200)(implicit val writer: String => Option[Writeable[MediaMedia_idCommentsDeleteResponses200]]) extends deletemediaByMedia_idLikesType[MediaMedia_idCommentsDeleteResponses200] { val statusCode = 200 }
    

    private type deletemediaByMedia_idLikesActionRequestType       = (BigInt)
    private type deletemediaByMedia_idLikesActionType[T]            = deletemediaByMedia_idLikesActionRequestType => deletemediaByMedia_idLikesType[T]


    val deletemediaByMedia_idLikesActionConstructor  = new deletemediaByMedia_idLikesSecureAction("basic", "comments", "relationships", "likes")
    def deletemediaByMedia_idLikesAction[T] = (f: deletemediaByMedia_idLikesActionType[T]) => (media_id: BigInt) => deletemediaByMedia_idLikesActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { deletemediaByMedia_idLikesResponseMimeType =>

            
            

                val result =
                        new MediaMedia_idLikesDeleteValidator(media_id).errors match {
                            case e if e.isEmpty => processValiddeletemediaByMedia_idLikesRequest(f)((media_id))(deletemediaByMedia_idLikesResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(deletemediaByMedia_idLikesResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValiddeletemediaByMedia_idLikesRequest[T](f: deletemediaByMedia_idLikesActionType[T])(request: deletemediaByMedia_idLikesActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait getusersByUser_idFollowsType[ResultT] extends ResultWrapper[ResultT]
    case class getusersByUser_idFollows200(result: UsersUser_idFollowsGetResponses200)(implicit val writer: String => Option[Writeable[UsersUser_idFollowsGetResponses200]]) extends getusersByUser_idFollowsType[UsersUser_idFollowsGetResponses200] { val statusCode = 200 }
    

    private type getusersByUser_idFollowsActionRequestType       = (BigDecimal)
    private type getusersByUser_idFollowsActionType[T]            = getusersByUser_idFollowsActionRequestType => getusersByUser_idFollowsType[T]


    val getusersByUser_idFollowsActionConstructor  = new getusersByUser_idFollowsSecureAction("basic", "comments", "relationships", "likes")
    def getusersByUser_idFollowsAction[T] = (f: getusersByUser_idFollowsActionType[T]) => (user_id: BigDecimal) => getusersByUser_idFollowsActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getusersByUser_idFollowsResponseMimeType =>

            
            

                val result =
                        new UsersUser_idFollowsGetValidator(user_id).errors match {
                            case e if e.isEmpty => processValidgetusersByUser_idFollowsRequest(f)((user_id))(getusersByUser_idFollowsResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getusersByUser_idFollowsResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetusersByUser_idFollowsRequest[T](f: getusersByUser_idFollowsActionType[T])(request: getusersByUser_idFollowsActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait getlocationsByLocation_idType[ResultT] extends ResultWrapper[ResultT]
    case class getlocationsByLocation_id200(result: LocationsLocation_idGetResponses200)(implicit val writer: String => Option[Writeable[LocationsLocation_idGetResponses200]]) extends getlocationsByLocation_idType[LocationsLocation_idGetResponses200] { val statusCode = 200 }
    

    private type getlocationsByLocation_idActionRequestType       = (BigInt)
    private type getlocationsByLocation_idActionType[T]            = getlocationsByLocation_idActionRequestType => getlocationsByLocation_idType[T]


    val getlocationsByLocation_idActionConstructor  = new getlocationsByLocation_idSecureAction("basic", "comments", "relationships", "likes")
    def getlocationsByLocation_idAction[T] = (f: getlocationsByLocation_idActionType[T]) => (location_id: BigInt) => getlocationsByLocation_idActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getlocationsByLocation_idResponseMimeType =>

            
            

                val result =
                        new LocationsLocation_idGetValidator(location_id).errors match {
                            case e if e.isEmpty => processValidgetlocationsByLocation_idRequest(f)((location_id))(getlocationsByLocation_idResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getlocationsByLocation_idResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetlocationsByLocation_idRequest[T](f: getlocationsByLocation_idActionType[T])(request: getlocationsByLocation_idActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait getusersSearchType[ResultT] extends ResultWrapper[ResultT]
    case class getusersSearch200(result: UsersUser_idFollowsGetResponses200)(implicit val writer: String => Option[Writeable[UsersUser_idFollowsGetResponses200]]) extends getusersSearchType[UsersUser_idFollowsGetResponses200] { val statusCode = 200 }
    

    private type getusersSearchActionRequestType       = (String, MediaFilter)
    private type getusersSearchActionType[T]            = getusersSearchActionRequestType => getusersSearchType[T]


    val getusersSearchActionConstructor  = new getusersSearchSecureAction("basic", "comments", "relationships", "likes")
    def getusersSearchAction[T] = (f: getusersSearchActionType[T]) => (q: String, count: MediaFilter) => getusersSearchActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getusersSearchResponseMimeType =>

            
            

                val result =
                        new UsersSearchGetValidator(q, count).errors match {
                            case e if e.isEmpty => processValidgetusersSearchRequest(f)((q, count))(getusersSearchResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getusersSearchResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetusersSearchRequest[T](f: getusersSearchActionType[T])(request: getusersSearchActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait getusersSelfMediaLikedType[ResultT] extends ResultWrapper[ResultT]
    case class getusersSelfMediaLiked200(result: UsersSelfFeedGetResponses200)(implicit val writer: String => Option[Writeable[UsersSelfFeedGetResponses200]]) extends getusersSelfMediaLikedType[UsersSelfFeedGetResponses200] { val statusCode = 200 }
    

    private type getusersSelfMediaLikedActionRequestType       = (MediaId, MediaId)
    private type getusersSelfMediaLikedActionType[T]            = getusersSelfMediaLikedActionRequestType => getusersSelfMediaLikedType[T]


    val getusersSelfMediaLikedActionConstructor  = new getusersSelfMediaLikedSecureAction("basic", "comments", "relationships", "likes")
    def getusersSelfMediaLikedAction[T] = (f: getusersSelfMediaLikedActionType[T]) => (count: MediaId, max_like_id: MediaId) => getusersSelfMediaLikedActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getusersSelfMediaLikedResponseMimeType =>

            
            

                val result =
                        new UsersSelfMediaLikedGetValidator(count, max_like_id).errors match {
                            case e if e.isEmpty => processValidgetusersSelfMediaLikedRequest(f)((count, max_like_id))(getusersSelfMediaLikedResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getusersSelfMediaLikedResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetusersSelfMediaLikedRequest[T](f: getusersSelfMediaLikedActionType[T])(request: getusersSelfMediaLikedActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait gettagsByTag_nameType[ResultT] extends ResultWrapper[ResultT]
    case class gettagsByTag_name200(result: Tag)(implicit val writer: String => Option[Writeable[Tag]]) extends gettagsByTag_nameType[Tag] { val statusCode = 200 }
    

    private type gettagsByTag_nameActionRequestType       = (String)
    private type gettagsByTag_nameActionType[T]            = gettagsByTag_nameActionRequestType => gettagsByTag_nameType[T]


    val gettagsByTag_nameActionConstructor  = new gettagsByTag_nameSecureAction("basic", "comments", "relationships", "likes")
    def gettagsByTag_nameAction[T] = (f: gettagsByTag_nameActionType[T]) => (tag_name: String) => gettagsByTag_nameActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { gettagsByTag_nameResponseMimeType =>

            
            

                val result =
                        new TagsTag_nameGetValidator(tag_name).errors match {
                            case e if e.isEmpty => processValidgettagsByTag_nameRequest(f)((tag_name))(gettagsByTag_nameResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(gettagsByTag_nameResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgettagsByTag_nameRequest[T](f: gettagsByTag_nameActionType[T])(request: gettagsByTag_nameActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait gettagsSearchType[ResultT] extends ResultWrapper[ResultT]
    case class gettagsSearch200(result: TagsSearchGetResponses200)(implicit val writer: String => Option[Writeable[TagsSearchGetResponses200]]) extends gettagsSearchType[TagsSearchGetResponses200] { val statusCode = 200 }
    

    private type gettagsSearchActionRequestType       = (MediaFilter)
    private type gettagsSearchActionType[T]            = gettagsSearchActionRequestType => gettagsSearchType[T]


    val gettagsSearchActionConstructor  = new gettagsSearchSecureAction("basic", "comments", "relationships", "likes")
    def gettagsSearchAction[T] = (f: gettagsSearchActionType[T]) => (q: MediaFilter) => gettagsSearchActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { gettagsSearchResponseMimeType =>

            
            

                val result =
                        new TagsSearchGetValidator(q).errors match {
                            case e if e.isEmpty => processValidgettagsSearchRequest(f)((q))(gettagsSearchResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(gettagsSearchResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgettagsSearchRequest[T](f: gettagsSearchActionType[T])(request: gettagsSearchActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait getusersByUser_idFollowed_byType[ResultT] extends ResultWrapper[ResultT]
    case class getusersByUser_idFollowed_by200(result: UsersUser_idFollowsGetResponses200)(implicit val writer: String => Option[Writeable[UsersUser_idFollowsGetResponses200]]) extends getusersByUser_idFollowed_byType[UsersUser_idFollowsGetResponses200] { val statusCode = 200 }
    

    private type getusersByUser_idFollowed_byActionRequestType       = (BigDecimal)
    private type getusersByUser_idFollowed_byActionType[T]            = getusersByUser_idFollowed_byActionRequestType => getusersByUser_idFollowed_byType[T]


    val getusersByUser_idFollowed_byActionConstructor  = new getusersByUser_idFollowed_bySecureAction("basic", "comments", "relationships", "likes")
    def getusersByUser_idFollowed_byAction[T] = (f: getusersByUser_idFollowed_byActionType[T]) => (user_id: BigDecimal) => getusersByUser_idFollowed_byActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getusersByUser_idFollowed_byResponseMimeType =>

            
            

                val result =
                        new UsersUser_idFollowed_byGetValidator(user_id).errors match {
                            case e if e.isEmpty => processValidgetusersByUser_idFollowed_byRequest(f)((user_id))(getusersByUser_idFollowed_byResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getusersByUser_idFollowed_byResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetusersByUser_idFollowed_byRequest[T](f: getusersByUser_idFollowed_byActionType[T])(request: getusersByUser_idFollowed_byActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait getmediaByMedia_idCommentsType[ResultT] extends ResultWrapper[ResultT]
    case class getmediaByMedia_idComments200(result: MediaMedia_idCommentsGetResponses200)(implicit val writer: String => Option[Writeable[MediaMedia_idCommentsGetResponses200]]) extends getmediaByMedia_idCommentsType[MediaMedia_idCommentsGetResponses200] { val statusCode = 200 }
    

    private type getmediaByMedia_idCommentsActionRequestType       = (BigInt)
    private type getmediaByMedia_idCommentsActionType[T]            = getmediaByMedia_idCommentsActionRequestType => getmediaByMedia_idCommentsType[T]


    val getmediaByMedia_idCommentsActionConstructor  = new getmediaByMedia_idCommentsSecureAction("basic", "comments", "relationships", "likes")
    def getmediaByMedia_idCommentsAction[T] = (f: getmediaByMedia_idCommentsActionType[T]) => (media_id: BigInt) => getmediaByMedia_idCommentsActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getmediaByMedia_idCommentsResponseMimeType =>

            
            

                val result =
                        new MediaMedia_idCommentsGetValidator(media_id).errors match {
                            case e if e.isEmpty => processValidgetmediaByMedia_idCommentsRequest(f)((media_id))(getmediaByMedia_idCommentsResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getmediaByMedia_idCommentsResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetmediaByMedia_idCommentsRequest[T](f: getmediaByMedia_idCommentsActionType[T])(request: getmediaByMedia_idCommentsActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait postmediaByMedia_idCommentsType[ResultT] extends ResultWrapper[ResultT]
    case class postmediaByMedia_idComments200(result: MediaMedia_idCommentsDeleteResponses200)(implicit val writer: String => Option[Writeable[MediaMedia_idCommentsDeleteResponses200]]) extends postmediaByMedia_idCommentsType[MediaMedia_idCommentsDeleteResponses200] { val statusCode = 200 }
    

    private type postmediaByMedia_idCommentsActionRequestType       = (BigInt, LocationLatitude)
    private type postmediaByMedia_idCommentsActionType[T]            = postmediaByMedia_idCommentsActionRequestType => postmediaByMedia_idCommentsType[T]

        private def postmediaByMedia_idCommentsParser(acceptedTypes: Seq[String], maxLength: Int = parse.DefaultMaxTextLength) = {
            def bodyMimeType: Option[MediaType] => String = mediaType => {
                val requestType = mediaType.toSeq.map {
                    case m: MediaRange => m
                    case MediaType(a,b,c) => new MediaRange(a,b,c,None,Nil)
                }
                negotiateContent(requestType, acceptedTypes).orElse(acceptedTypes.headOption).getOrElse("application/json")
            }
            
            import de.zalando.play.controllers.WrappedBodyParsers
            
            val customParsers = WrappedBodyParsers.optionParser[BigDecimal]
            optionParser[BigDecimal](bodyMimeType, customParsers, "Invalid LocationLatitude", maxLength)
        }

    val postmediaByMedia_idCommentsActionConstructor  = new postmediaByMedia_idCommentsSecureAction("comments")
    def postmediaByMedia_idCommentsAction[T] = (f: postmediaByMedia_idCommentsActionType[T]) => (media_id: BigInt) => postmediaByMedia_idCommentsActionConstructor(postmediaByMedia_idCommentsParser(Seq[String]("application/json"))) { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { postmediaByMedia_idCommentsResponseMimeType =>

            val tEXT = request.body
            
            

                val result =
                        new MediaMedia_idCommentsPostValidator(media_id, tEXT).errors match {
                            case e if e.isEmpty => processValidpostmediaByMedia_idCommentsRequest(f)((media_id, tEXT))(postmediaByMedia_idCommentsResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(postmediaByMedia_idCommentsResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidpostmediaByMedia_idCommentsRequest[T](f: postmediaByMedia_idCommentsActionType[T])(request: postmediaByMedia_idCommentsActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait deletemediaByMedia_idCommentsType[ResultT] extends ResultWrapper[ResultT]
    case class deletemediaByMedia_idComments200(result: MediaMedia_idCommentsDeleteResponses200)(implicit val writer: String => Option[Writeable[MediaMedia_idCommentsDeleteResponses200]]) extends deletemediaByMedia_idCommentsType[MediaMedia_idCommentsDeleteResponses200] { val statusCode = 200 }
    

    private type deletemediaByMedia_idCommentsActionRequestType       = (BigInt)
    private type deletemediaByMedia_idCommentsActionType[T]            = deletemediaByMedia_idCommentsActionRequestType => deletemediaByMedia_idCommentsType[T]


    val deletemediaByMedia_idCommentsActionConstructor  = new deletemediaByMedia_idCommentsSecureAction("basic", "comments", "relationships", "likes")
    def deletemediaByMedia_idCommentsAction[T] = (f: deletemediaByMedia_idCommentsActionType[T]) => (media_id: BigInt) => deletemediaByMedia_idCommentsActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { deletemediaByMedia_idCommentsResponseMimeType =>

            
            

                val result =
                        new MediaMedia_idCommentsDeleteValidator(media_id).errors match {
                            case e if e.isEmpty => processValiddeletemediaByMedia_idCommentsRequest(f)((media_id))(deletemediaByMedia_idCommentsResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(deletemediaByMedia_idCommentsResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValiddeletemediaByMedia_idCommentsRequest[T](f: deletemediaByMedia_idCommentsActionType[T])(request: deletemediaByMedia_idCommentsActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait gettagsByTag_nameMediaRecentType[ResultT] extends ResultWrapper[ResultT]
    case class gettagsByTag_nameMediaRecent200(result: TagsTag_nameMediaRecentGetResponses200)(implicit val writer: String => Option[Writeable[TagsTag_nameMediaRecentGetResponses200]]) extends gettagsByTag_nameMediaRecentType[TagsTag_nameMediaRecentGetResponses200] { val statusCode = 200 }
    

    private type gettagsByTag_nameMediaRecentActionRequestType       = (String)
    private type gettagsByTag_nameMediaRecentActionType[T]            = gettagsByTag_nameMediaRecentActionRequestType => gettagsByTag_nameMediaRecentType[T]


    val gettagsByTag_nameMediaRecentActionConstructor  = new gettagsByTag_nameMediaRecentSecureAction("basic", "comments", "relationships", "likes")
    def gettagsByTag_nameMediaRecentAction[T] = (f: gettagsByTag_nameMediaRecentActionType[T]) => (tag_name: String) => gettagsByTag_nameMediaRecentActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { gettagsByTag_nameMediaRecentResponseMimeType =>

            
            

                val result =
                        new TagsTag_nameMediaRecentGetValidator(tag_name).errors match {
                            case e if e.isEmpty => processValidgettagsByTag_nameMediaRecentRequest(f)((tag_name))(gettagsByTag_nameMediaRecentResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(gettagsByTag_nameMediaRecentResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgettagsByTag_nameMediaRecentRequest[T](f: gettagsByTag_nameMediaRecentActionType[T])(request: gettagsByTag_nameMediaRecentActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait postusersByUser_idRelationshipType[ResultT] extends ResultWrapper[ResultT]
    case class postusersByUser_idRelationship200(result: UsersUser_idFollowsGetResponses200)(implicit val writer: String => Option[Writeable[UsersUser_idFollowsGetResponses200]]) extends postusersByUser_idRelationshipType[UsersUser_idFollowsGetResponses200] { val statusCode = 200 }
    

    private type postusersByUser_idRelationshipActionRequestType       = (BigDecimal, UsersUser_idRelationshipPostAction)
    private type postusersByUser_idRelationshipActionType[T]            = postusersByUser_idRelationshipActionRequestType => postusersByUser_idRelationshipType[T]

        private def postusersByUser_idRelationshipParser(acceptedTypes: Seq[String], maxLength: Int = parse.DefaultMaxTextLength) = {
            def bodyMimeType: Option[MediaType] => String = mediaType => {
                val requestType = mediaType.toSeq.map {
                    case m: MediaRange => m
                    case MediaType(a,b,c) => new MediaRange(a,b,c,None,Nil)
                }
                negotiateContent(requestType, acceptedTypes).orElse(acceptedTypes.headOption).getOrElse("application/json")
            }
            
            import de.zalando.play.controllers.WrappedBodyParsers
            
            val customParsers = WrappedBodyParsers.optionParser[String]
            optionParser[String](bodyMimeType, customParsers, "Invalid UsersUser_idRelationshipPostAction", maxLength)
        }

    val postusersByUser_idRelationshipActionConstructor  = new postusersByUser_idRelationshipSecureAction("relationships")
    def postusersByUser_idRelationshipAction[T] = (f: postusersByUser_idRelationshipActionType[T]) => (user_id: BigDecimal) => postusersByUser_idRelationshipActionConstructor(postusersByUser_idRelationshipParser(Seq[String]("application/json"))) { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { postusersByUser_idRelationshipResponseMimeType =>

            val action = request.body
            
            

                val result =
                        new UsersUser_idRelationshipPostValidator(user_id, action).errors match {
                            case e if e.isEmpty => processValidpostusersByUser_idRelationshipRequest(f)((user_id, action))(postusersByUser_idRelationshipResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(postusersByUser_idRelationshipResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidpostusersByUser_idRelationshipRequest[T](f: postusersByUser_idRelationshipActionType[T])(request: postusersByUser_idRelationshipActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait getusersSelfFeedType[ResultT] extends ResultWrapper[ResultT]
    case class getusersSelfFeed200(result: UsersSelfFeedGetResponses200)(implicit val writer: String => Option[Writeable[UsersSelfFeedGetResponses200]]) extends getusersSelfFeedType[UsersSelfFeedGetResponses200] { val statusCode = 200 }
    

    private type getusersSelfFeedActionRequestType       = (MediaId, MediaId, MediaId)
    private type getusersSelfFeedActionType[T]            = getusersSelfFeedActionRequestType => getusersSelfFeedType[T]


    val getusersSelfFeedActionConstructor  = new getusersSelfFeedSecureAction("basic", "comments", "relationships", "likes")
    def getusersSelfFeedAction[T] = (f: getusersSelfFeedActionType[T]) => (count: MediaId, max_id: MediaId, min_id: MediaId) => getusersSelfFeedActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getusersSelfFeedResponseMimeType =>

            
            

                val result =
                        new UsersSelfFeedGetValidator(count, max_id, min_id).errors match {
                            case e if e.isEmpty => processValidgetusersSelfFeedRequest(f)((count, max_id, min_id))(getusersSelfFeedResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getusersSelfFeedResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetusersSelfFeedRequest[T](f: getusersSelfFeedActionType[T])(request: getusersSelfFeedActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait getusersByUser_idType[ResultT] extends ResultWrapper[ResultT]
    case class getusersByUser_id200(result: UsersUser_idGetResponses200)(implicit val writer: String => Option[Writeable[UsersUser_idGetResponses200]]) extends getusersByUser_idType[UsersUser_idGetResponses200] { val statusCode = 200 }
    

    private type getusersByUser_idActionRequestType       = (BigDecimal)
    private type getusersByUser_idActionType[T]            = getusersByUser_idActionRequestType => getusersByUser_idType[T]


    val getusersByUser_idActionConstructor  = new getusersByUser_idSecureAction("basic")
    def getusersByUser_idAction[T] = (f: getusersByUser_idActionType[T]) => (user_id: BigDecimal) => getusersByUser_idActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getusersByUser_idResponseMimeType =>

            
            

                val result =
                        new UsersUser_idGetValidator(user_id).errors match {
                            case e if e.isEmpty => processValidgetusersByUser_idRequest(f)((user_id))(getusersByUser_idResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getusersByUser_idResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetusersByUser_idRequest[T](f: getusersByUser_idActionType[T])(request: getusersByUser_idActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait getmediaSearchType[ResultT] extends ResultWrapper[ResultT]
    case class getmediaSearch200(result: MediaSearchGetResponses200)(implicit val writer: String => Option[Writeable[MediaSearchGetResponses200]]) extends getmediaSearchType[MediaSearchGetResponses200] { val statusCode = 200 }
    

    private type getmediaSearchActionRequestType       = (MediaId, BigInt, LocationLatitude, MediaId, LocationLatitude)
    private type getmediaSearchActionType[T]            = getmediaSearchActionRequestType => getmediaSearchType[T]


    val getmediaSearchActionConstructor  = new getmediaSearchSecureAction("basic", "comments", "relationships", "likes")
    def getmediaSearchAction[T] = (f: getmediaSearchActionType[T]) => (mAX_TIMESTAMP: MediaId, dISTANCE: BigInt, lNG: LocationLatitude, mIN_TIMESTAMP: MediaId, lAT: LocationLatitude) => getmediaSearchActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getmediaSearchResponseMimeType =>

            
            

                val result =
                        new MediaSearchGetValidator(mAX_TIMESTAMP, dISTANCE, lNG, mIN_TIMESTAMP, lAT).errors match {
                            case e if e.isEmpty => processValidgetmediaSearchRequest(f)((mAX_TIMESTAMP, dISTANCE, lNG, mIN_TIMESTAMP, lAT))(getmediaSearchResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getmediaSearchResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetmediaSearchRequest[T](f: getmediaSearchActionType[T])(request: getmediaSearchActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait getgeographiesByGeo_idMediaRecentType[ResultT] extends ResultWrapper[ResultT]
    case class getgeographiesByGeo_idMediaRecent200(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends getgeographiesByGeo_idMediaRecentType[Null] { val statusCode = 200 }
    

    private type getgeographiesByGeo_idMediaRecentActionRequestType       = (BigInt, MediaId, MediaId)
    private type getgeographiesByGeo_idMediaRecentActionType[T]            = getgeographiesByGeo_idMediaRecentActionRequestType => getgeographiesByGeo_idMediaRecentType[T]


    val getgeographiesByGeo_idMediaRecentActionConstructor  = new getgeographiesByGeo_idMediaRecentSecureAction("basic", "comments", "relationships", "likes")
    def getgeographiesByGeo_idMediaRecentAction[T] = (f: getgeographiesByGeo_idMediaRecentActionType[T]) => (geo_id: BigInt, count: MediaId, min_id: MediaId) => getgeographiesByGeo_idMediaRecentActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getgeographiesByGeo_idMediaRecentResponseMimeType =>

            
            

                val result =
                        new GeographiesGeo_idMediaRecentGetValidator(geo_id, count, min_id).errors match {
                            case e if e.isEmpty => processValidgetgeographiesByGeo_idMediaRecentRequest(f)((geo_id, count, min_id))(getgeographiesByGeo_idMediaRecentResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getgeographiesByGeo_idMediaRecentResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetgeographiesByGeo_idMediaRecentRequest[T](f: getgeographiesByGeo_idMediaRecentActionType[T])(request: getgeographiesByGeo_idMediaRecentActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait getmediaByShortcodeType[ResultT] extends ResultWrapper[ResultT]
    case class getmediaByShortcode200(result: Media)(implicit val writer: String => Option[Writeable[Media]]) extends getmediaByShortcodeType[Media] { val statusCode = 200 }
    

    private type getmediaByShortcodeActionRequestType       = (String)
    private type getmediaByShortcodeActionType[T]            = getmediaByShortcodeActionRequestType => getmediaByShortcodeType[T]


    val getmediaByShortcodeActionConstructor  = new getmediaByShortcodeSecureAction("basic", "comments", "relationships", "likes")
    def getmediaByShortcodeAction[T] = (f: getmediaByShortcodeActionType[T]) => (shortcode: String) => getmediaByShortcodeActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getmediaByShortcodeResponseMimeType =>

            
            

                val result =
                        new MediaShortcodeGetValidator(shortcode).errors match {
                            case e if e.isEmpty => processValidgetmediaByShortcodeRequest(f)((shortcode))(getmediaByShortcodeResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getmediaByShortcodeResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetmediaByShortcodeRequest[T](f: getmediaByShortcodeActionType[T])(request: getmediaByShortcodeActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait getlocationsSearchType[ResultT] extends ResultWrapper[ResultT]
    case class getlocationsSearch200(result: LocationsSearchGetResponses200)(implicit val writer: String => Option[Writeable[LocationsSearchGetResponses200]]) extends getlocationsSearchType[LocationsSearchGetResponses200] { val statusCode = 200 }
    

    private type getlocationsSearchActionRequestType       = (MediaId, MediaId, MediaId, LocationLatitude, MediaId, LocationLatitude)
    private type getlocationsSearchActionType[T]            = getlocationsSearchActionRequestType => getlocationsSearchType[T]


    val getlocationsSearchActionConstructor  = new getlocationsSearchSecureAction("basic", "comments", "relationships", "likes")
    def getlocationsSearchAction[T] = (f: getlocationsSearchActionType[T]) => (foursquare_v2_id: MediaId, facebook_places_id: MediaId, distance: MediaId, lat: LocationLatitude, foursquare_id: MediaId, lng: LocationLatitude) => getlocationsSearchActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getlocationsSearchResponseMimeType =>

            
            

                val result =
                        new LocationsSearchGetValidator(foursquare_v2_id, facebook_places_id, distance, lat, foursquare_id, lng).errors match {
                            case e if e.isEmpty => processValidgetlocationsSearchRequest(f)((foursquare_v2_id, facebook_places_id, distance, lat, foursquare_id, lng))(getlocationsSearchResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getlocationsSearchResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetlocationsSearchRequest[T](f: getlocationsSearchActionType[T])(request: getlocationsSearchActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait getusersSelfRequested_byType[ResultT] extends ResultWrapper[ResultT]
    case class getusersSelfRequested_by200(result: UsersSelfRequested_byGetResponses200)(implicit val writer: String => Option[Writeable[UsersSelfRequested_byGetResponses200]]) extends getusersSelfRequested_byType[UsersSelfRequested_byGetResponses200] { val statusCode = 200 }
    

    private type getusersSelfRequested_byActionRequestType       = (Unit)
    private type getusersSelfRequested_byActionType[T]            = getusersSelfRequested_byActionRequestType => getusersSelfRequested_byType[T]


    val getusersSelfRequested_byActionConstructor  = new getusersSelfRequested_bySecureAction("basic", "comments", "relationships", "likes")
    def getusersSelfRequested_byAction[T] = (f: getusersSelfRequested_byActionType[T]) => getusersSelfRequested_byActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getusersSelfRequested_byResponseMimeType =>

            
            

                val result = processValidgetusersSelfRequested_byRequest(f)()(getusersSelfRequested_byResponseMimeType)
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetusersSelfRequested_byRequest[T](f: getusersSelfRequested_byActionType[T])(request: getusersSelfRequested_byActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait getmediaByMedia_idType[ResultT] extends ResultWrapper[ResultT]
    case class getmediaByMedia_id200(result: Media)(implicit val writer: String => Option[Writeable[Media]]) extends getmediaByMedia_idType[Media] { val statusCode = 200 }
    

    private type getmediaByMedia_idActionRequestType       = (BigInt)
    private type getmediaByMedia_idActionType[T]            = getmediaByMedia_idActionRequestType => getmediaByMedia_idType[T]


    val getmediaByMedia_idActionConstructor  = new getmediaByMedia_idSecureAction("basic", "comments", "relationships", "likes")
    def getmediaByMedia_idAction[T] = (f: getmediaByMedia_idActionType[T]) => (media_id: BigInt) => getmediaByMedia_idActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getmediaByMedia_idResponseMimeType =>

            
            

                val result =
                        new MediaMedia_idGetValidator(media_id).errors match {
                            case e if e.isEmpty => processValidgetmediaByMedia_idRequest(f)((media_id))(getmediaByMedia_idResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getmediaByMedia_idResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetmediaByMedia_idRequest[T](f: getmediaByMedia_idActionType[T])(request: getmediaByMedia_idActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait getlocationsByLocation_idMediaRecentType[ResultT] extends ResultWrapper[ResultT]
    case class getlocationsByLocation_idMediaRecent200(result: UsersSelfFeedGetResponses200)(implicit val writer: String => Option[Writeable[UsersSelfFeedGetResponses200]]) extends getlocationsByLocation_idMediaRecentType[UsersSelfFeedGetResponses200] { val statusCode = 200 }
    

    private type getlocationsByLocation_idMediaRecentActionRequestType       = (BigInt, MediaId, MediaId, MediaFilter, MediaFilter)
    private type getlocationsByLocation_idMediaRecentActionType[T]            = getlocationsByLocation_idMediaRecentActionRequestType => getlocationsByLocation_idMediaRecentType[T]


    val getlocationsByLocation_idMediaRecentActionConstructor  = new getlocationsByLocation_idMediaRecentSecureAction("basic", "comments", "relationships", "likes")
    def getlocationsByLocation_idMediaRecentAction[T] = (f: getlocationsByLocation_idMediaRecentActionType[T]) => (location_id: BigInt, max_timestamp: MediaId, min_timestamp: MediaId, min_id: MediaFilter, max_id: MediaFilter) => getlocationsByLocation_idMediaRecentActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getlocationsByLocation_idMediaRecentResponseMimeType =>

            
            

                val result =
                        new LocationsLocation_idMediaRecentGetValidator(location_id, max_timestamp, min_timestamp, min_id, max_id).errors match {
                            case e if e.isEmpty => processValidgetlocationsByLocation_idMediaRecentRequest(f)((location_id, max_timestamp, min_timestamp, min_id, max_id))(getlocationsByLocation_idMediaRecentResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getlocationsByLocation_idMediaRecentResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetlocationsByLocation_idMediaRecentRequest[T](f: getlocationsByLocation_idMediaRecentActionType[T])(request: getlocationsByLocation_idMediaRecentActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait getusersByUser_idMediaRecentType[ResultT] extends ResultWrapper[ResultT]
    case class getusersByUser_idMediaRecent200(result: UsersSelfFeedGetResponses200)(implicit val writer: String => Option[Writeable[UsersSelfFeedGetResponses200]]) extends getusersByUser_idMediaRecentType[UsersSelfFeedGetResponses200] { val statusCode = 200 }
    

    private type getusersByUser_idMediaRecentActionRequestType       = (BigDecimal, MediaId, MediaFilter, MediaId, MediaFilter, MediaId)
    private type getusersByUser_idMediaRecentActionType[T]            = getusersByUser_idMediaRecentActionRequestType => getusersByUser_idMediaRecentType[T]


    val getusersByUser_idMediaRecentActionConstructor  = new getusersByUser_idMediaRecentSecureAction("basic", "comments", "relationships", "likes")
    def getusersByUser_idMediaRecentAction[T] = (f: getusersByUser_idMediaRecentActionType[T]) => (user_id: BigDecimal, max_timestamp: MediaId, min_id: MediaFilter, min_timestamp: MediaId, max_id: MediaFilter, count: MediaId) => getusersByUser_idMediaRecentActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getusersByUser_idMediaRecentResponseMimeType =>

            
            

                val result =
                        new UsersUser_idMediaRecentGetValidator(user_id, max_timestamp, min_id, min_timestamp, max_id, count).errors match {
                            case e if e.isEmpty => processValidgetusersByUser_idMediaRecentRequest(f)((user_id, max_timestamp, min_id, min_timestamp, max_id, count))(getusersByUser_idMediaRecentResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getusersByUser_idMediaRecentResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetusersByUser_idMediaRecentRequest[T](f: getusersByUser_idMediaRecentActionType[T])(request: getusersByUser_idMediaRecentActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait getmediaPopularType[ResultT] extends ResultWrapper[ResultT]
    case class getmediaPopular200(result: UsersSelfFeedGetResponses200)(implicit val writer: String => Option[Writeable[UsersSelfFeedGetResponses200]]) extends getmediaPopularType[UsersSelfFeedGetResponses200] { val statusCode = 200 }
    

    private type getmediaPopularActionRequestType       = (Unit)
    private type getmediaPopularActionType[T]            = getmediaPopularActionRequestType => getmediaPopularType[T]


    val getmediaPopularActionConstructor  = new getmediaPopularSecureAction("basic", "comments", "relationships", "likes")
    def getmediaPopularAction[T] = (f: getmediaPopularActionType[T]) => getmediaPopularActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getmediaPopularResponseMimeType =>

            
            

                val result = processValidgetmediaPopularRequest(f)()(getmediaPopularResponseMimeType)
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetmediaPopularRequest[T](f: getmediaPopularActionType[T])(request: getmediaPopularActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    case object EmptyReturn extends ResultWrapper[Results.EmptyContent]                                                                                { val statusCode = 204; val result = Results.EmptyContent(); val writer = (x: String) => Some(new DefaultWriteables{}.writeableOf_EmptyContent); override def toResult(mimeType: String): Option[play.api.mvc.Result] = Some(Results.NoContent) }
    case object NotImplementedYet extends ResultWrapper[Results.EmptyContent]  with getmediaByMedia_idLikesType[Results.EmptyContent] with postmediaByMedia_idLikesType[Results.EmptyContent] with deletemediaByMedia_idLikesType[Results.EmptyContent] with getusersByUser_idFollowsType[Results.EmptyContent] with getlocationsByLocation_idType[Results.EmptyContent] with getusersSearchType[Results.EmptyContent] with getusersSelfMediaLikedType[Results.EmptyContent] with gettagsByTag_nameType[Results.EmptyContent] with gettagsSearchType[Results.EmptyContent] with getusersByUser_idFollowed_byType[Results.EmptyContent] with getmediaByMedia_idCommentsType[Results.EmptyContent] with postmediaByMedia_idCommentsType[Results.EmptyContent] with deletemediaByMedia_idCommentsType[Results.EmptyContent] with gettagsByTag_nameMediaRecentType[Results.EmptyContent] with postusersByUser_idRelationshipType[Results.EmptyContent] with getusersSelfFeedType[Results.EmptyContent] with getusersByUser_idType[Results.EmptyContent] with getmediaSearchType[Results.EmptyContent] with getgeographiesByGeo_idMediaRecentType[Results.EmptyContent] with getmediaByShortcodeType[Results.EmptyContent] with getlocationsSearchType[Results.EmptyContent] with getusersSelfRequested_byType[Results.EmptyContent] with getmediaByMedia_idType[Results.EmptyContent] with getlocationsByLocation_idMediaRecentType[Results.EmptyContent] with getusersByUser_idMediaRecentType[Results.EmptyContent] with getmediaPopularType[Results.EmptyContent] { val statusCode = 501; val result = Results.EmptyContent(); val writer = (x: String) => Some(new DefaultWriteables{}.writeableOf_EmptyContent); override def toResult(mimeType: String): Option[play.api.mvc.Result] = Some(Results.NotImplemented) }
}
