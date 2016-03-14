package instagram.api.yaml

import play.api.mvc.{Action, Controller, Results}
import play.api.http._
import Results.Status
import de.zalando.play.controllers.{PlayBodyParsing, ParsingError}
import PlayBodyParsing._
import scala.util._

import de.zalando.play.controllers.PlayPathBindables




trait InstagramApiYamlBase extends Controller with PlayBodyParsing  with InstagramApiYamlSecurity {
    private type getmediaByMedia_idLikesActionRequestType       = (Int)
    private type getmediaByMedia_idLikesActionType              = getmediaByMedia_idLikesActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetmediaByMedia_idLikes: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val getmediaByMedia_idLikesActionConstructor  = new getmediaByMedia_idLikesSecureAction("basic", "comments", "relationships", "likes")
    def getmediaByMedia_idLikesAction = (f: getmediaByMedia_idLikesActionType) => (media_id: Int) => getmediaByMedia_idLikesActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getmediaByMedia_idLikesResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[MediaMedia_idLikesGetResponses200]
            )
            

                val result =
                        new MediaMedia_idLikesGetValidator(media_id).errors match {
                            case e if e.isEmpty => processValidgetmediaByMedia_idLikesRequest(f)((media_id))(possibleWriters, getmediaByMedia_idLikesResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getmediaByMedia_idLikesResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetmediaByMedia_idLikesRequest[T <: Any](f: getmediaByMedia_idLikesActionType)(request: getmediaByMedia_idLikesActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetmediaByMedia_idLikes orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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
    private type postmediaByMedia_idLikesActionRequestType       = (Int)
    private type postmediaByMedia_idLikesActionType              = postmediaByMedia_idLikesActionRequestType => Try[(Int, Any)]

    private val errorToStatuspostmediaByMedia_idLikes: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val postmediaByMedia_idLikesActionConstructor  = new postmediaByMedia_idLikesSecureAction("comments")
    def postmediaByMedia_idLikesAction = (f: postmediaByMedia_idLikesActionType) => (media_id: Int) => postmediaByMedia_idLikesActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { postmediaByMedia_idLikesResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[MediaMedia_idCommentsDeleteResponses200]
            )
            

                val result =
                        new MediaMedia_idLikesPostValidator(media_id).errors match {
                            case e if e.isEmpty => processValidpostmediaByMedia_idLikesRequest(f)((media_id))(possibleWriters, postmediaByMedia_idLikesResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(postmediaByMedia_idLikesResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidpostmediaByMedia_idLikesRequest[T <: Any](f: postmediaByMedia_idLikesActionType)(request: postmediaByMedia_idLikesActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatuspostmediaByMedia_idLikes orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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
    private type deletemediaByMedia_idLikesActionRequestType       = (Int)
    private type deletemediaByMedia_idLikesActionType              = deletemediaByMedia_idLikesActionRequestType => Try[(Int, Any)]

    private val errorToStatusdeletemediaByMedia_idLikes: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val deletemediaByMedia_idLikesActionConstructor  = new deletemediaByMedia_idLikesSecureAction("basic", "comments", "relationships", "likes")
    def deletemediaByMedia_idLikesAction = (f: deletemediaByMedia_idLikesActionType) => (media_id: Int) => deletemediaByMedia_idLikesActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { deletemediaByMedia_idLikesResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[MediaMedia_idCommentsDeleteResponses200]
            )
            

                val result =
                        new MediaMedia_idLikesDeleteValidator(media_id).errors match {
                            case e if e.isEmpty => processValiddeletemediaByMedia_idLikesRequest(f)((media_id))(possibleWriters, deletemediaByMedia_idLikesResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(deletemediaByMedia_idLikesResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValiddeletemediaByMedia_idLikesRequest[T <: Any](f: deletemediaByMedia_idLikesActionType)(request: deletemediaByMedia_idLikesActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusdeletemediaByMedia_idLikes orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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
    private type getusersByUser_idFollowsActionRequestType       = (Double)
    private type getusersByUser_idFollowsActionType              = getusersByUser_idFollowsActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetusersByUser_idFollows: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val getusersByUser_idFollowsActionConstructor  = new getusersByUser_idFollowsSecureAction("basic", "comments", "relationships", "likes")
    def getusersByUser_idFollowsAction = (f: getusersByUser_idFollowsActionType) => (user_id: Double) => getusersByUser_idFollowsActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getusersByUser_idFollowsResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[UsersUser_idFollowsGetResponses200]
            )
            

                val result =
                        new UsersUser_idFollowsGetValidator(user_id).errors match {
                            case e if e.isEmpty => processValidgetusersByUser_idFollowsRequest(f)((user_id))(possibleWriters, getusersByUser_idFollowsResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getusersByUser_idFollowsResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetusersByUser_idFollowsRequest[T <: Any](f: getusersByUser_idFollowsActionType)(request: getusersByUser_idFollowsActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetusersByUser_idFollows orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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
    private type getlocationsByLocation_idActionRequestType       = (Int)
    private type getlocationsByLocation_idActionType              = getlocationsByLocation_idActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetlocationsByLocation_id: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val getlocationsByLocation_idActionConstructor  = new getlocationsByLocation_idSecureAction("basic", "comments", "relationships", "likes")
    def getlocationsByLocation_idAction = (f: getlocationsByLocation_idActionType) => (location_id: Int) => getlocationsByLocation_idActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getlocationsByLocation_idResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[LocationsLocation_idGetResponses200]
            )
            

                val result =
                        new LocationsLocation_idGetValidator(location_id).errors match {
                            case e if e.isEmpty => processValidgetlocationsByLocation_idRequest(f)((location_id))(possibleWriters, getlocationsByLocation_idResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getlocationsByLocation_idResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetlocationsByLocation_idRequest[T <: Any](f: getlocationsByLocation_idActionType)(request: getlocationsByLocation_idActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetlocationsByLocation_id orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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
    private type getusersSearchActionRequestType       = (String, MediaFilter)
    private type getusersSearchActionType              = getusersSearchActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetusersSearch: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val getusersSearchActionConstructor  = new getusersSearchSecureAction("basic", "comments", "relationships", "likes")
    def getusersSearchAction = (f: getusersSearchActionType) => (q: String, count: MediaFilter) => getusersSearchActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getusersSearchResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[UsersUser_idFollowsGetResponses200]
            )
            

                val result =
                        new UsersSearchGetValidator(q, count).errors match {
                            case e if e.isEmpty => processValidgetusersSearchRequest(f)((q, count))(possibleWriters, getusersSearchResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getusersSearchResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetusersSearchRequest[T <: Any](f: getusersSearchActionType)(request: getusersSearchActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetusersSearch orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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
    private type getusersSelfMediaLikedActionRequestType       = (MediaId, MediaId)
    private type getusersSelfMediaLikedActionType              = getusersSelfMediaLikedActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetusersSelfMediaLiked: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val getusersSelfMediaLikedActionConstructor  = new getusersSelfMediaLikedSecureAction("basic", "comments", "relationships", "likes")
    def getusersSelfMediaLikedAction = (f: getusersSelfMediaLikedActionType) => (count: MediaId, max_like_id: MediaId) => getusersSelfMediaLikedActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getusersSelfMediaLikedResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[UsersSelfFeedGetResponses200]
            )
            

                val result =
                        new UsersSelfMediaLikedGetValidator(count, max_like_id).errors match {
                            case e if e.isEmpty => processValidgetusersSelfMediaLikedRequest(f)((count, max_like_id))(possibleWriters, getusersSelfMediaLikedResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getusersSelfMediaLikedResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetusersSelfMediaLikedRequest[T <: Any](f: getusersSelfMediaLikedActionType)(request: getusersSelfMediaLikedActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetusersSelfMediaLiked orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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
    private type gettagsByTag_nameActionRequestType       = (String)
    private type gettagsByTag_nameActionType              = gettagsByTag_nameActionRequestType => Try[(Int, Any)]

    private val errorToStatusgettagsByTag_name: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val gettagsByTag_nameActionConstructor  = new gettagsByTag_nameSecureAction("basic", "comments", "relationships", "likes")
    def gettagsByTag_nameAction = (f: gettagsByTag_nameActionType) => (tag_name: String) => gettagsByTag_nameActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { gettagsByTag_nameResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[Tag]
            )
            

                val result =
                        new TagsTag_nameGetValidator(tag_name).errors match {
                            case e if e.isEmpty => processValidgettagsByTag_nameRequest(f)((tag_name))(possibleWriters, gettagsByTag_nameResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(gettagsByTag_nameResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgettagsByTag_nameRequest[T <: Any](f: gettagsByTag_nameActionType)(request: gettagsByTag_nameActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgettagsByTag_name orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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
    private type gettagsSearchActionRequestType       = (MediaFilter)
    private type gettagsSearchActionType              = gettagsSearchActionRequestType => Try[(Int, Any)]

    private val errorToStatusgettagsSearch: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val gettagsSearchActionConstructor  = new gettagsSearchSecureAction("basic", "comments", "relationships", "likes")
    def gettagsSearchAction = (f: gettagsSearchActionType) => (q: MediaFilter) => gettagsSearchActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { gettagsSearchResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[TagsSearchGetResponses200]
            )
            

                val result =
                        new TagsSearchGetValidator(q).errors match {
                            case e if e.isEmpty => processValidgettagsSearchRequest(f)((q))(possibleWriters, gettagsSearchResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(gettagsSearchResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgettagsSearchRequest[T <: Any](f: gettagsSearchActionType)(request: gettagsSearchActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgettagsSearch orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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
    private type getusersByUser_idFollowed_byActionRequestType       = (Double)
    private type getusersByUser_idFollowed_byActionType              = getusersByUser_idFollowed_byActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetusersByUser_idFollowed_by: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val getusersByUser_idFollowed_byActionConstructor  = new getusersByUser_idFollowed_bySecureAction("basic", "comments", "relationships", "likes")
    def getusersByUser_idFollowed_byAction = (f: getusersByUser_idFollowed_byActionType) => (user_id: Double) => getusersByUser_idFollowed_byActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getusersByUser_idFollowed_byResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[UsersUser_idFollowsGetResponses200]
            )
            

                val result =
                        new UsersUser_idFollowed_byGetValidator(user_id).errors match {
                            case e if e.isEmpty => processValidgetusersByUser_idFollowed_byRequest(f)((user_id))(possibleWriters, getusersByUser_idFollowed_byResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getusersByUser_idFollowed_byResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetusersByUser_idFollowed_byRequest[T <: Any](f: getusersByUser_idFollowed_byActionType)(request: getusersByUser_idFollowed_byActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetusersByUser_idFollowed_by orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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
    private type getmediaByMedia_idCommentsActionRequestType       = (Int)
    private type getmediaByMedia_idCommentsActionType              = getmediaByMedia_idCommentsActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetmediaByMedia_idComments: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val getmediaByMedia_idCommentsActionConstructor  = new getmediaByMedia_idCommentsSecureAction("basic", "comments", "relationships", "likes")
    def getmediaByMedia_idCommentsAction = (f: getmediaByMedia_idCommentsActionType) => (media_id: Int) => getmediaByMedia_idCommentsActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getmediaByMedia_idCommentsResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[MediaMedia_idCommentsGetResponses200]
            )
            

                val result =
                        new MediaMedia_idCommentsGetValidator(media_id).errors match {
                            case e if e.isEmpty => processValidgetmediaByMedia_idCommentsRequest(f)((media_id))(possibleWriters, getmediaByMedia_idCommentsResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getmediaByMedia_idCommentsResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetmediaByMedia_idCommentsRequest[T <: Any](f: getmediaByMedia_idCommentsActionType)(request: getmediaByMedia_idCommentsActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetmediaByMedia_idComments orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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
    private type postmediaByMedia_idCommentsActionRequestType       = (Int, LocationLatitude)
    private type postmediaByMedia_idCommentsActionType              = postmediaByMedia_idCommentsActionRequestType => Try[(Int, Any)]

    private val errorToStatuspostmediaByMedia_idComments: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

        private def postmediaByMedia_idCommentsParser(acceptedTypes: Seq[String], maxLength: Int = parse.DefaultMaxTextLength) = {
            def bodyMimeType: Option[MediaType] => String = mediaType => {
                val requestType = mediaType.toSeq.map {
                    case m: MediaRange => m
                    case MediaType(a,b,c) => new MediaRange(a,b,c,None,Nil)
                }
                negotiateContent(requestType, acceptedTypes).orElse(acceptedTypes.headOption).getOrElse("application/json")
            }
            
            import de.zalando.play.controllers.WrappedBodyParsers
            
            val customParsers = WrappedBodyParsers.optionParser[Double]
            optionParser[Double](bodyMimeType, customParsers, "Invalid LocationLatitude", maxLength)
        }

    val postmediaByMedia_idCommentsActionConstructor  = new postmediaByMedia_idCommentsSecureAction("comments")
    def postmediaByMedia_idCommentsAction = (f: postmediaByMedia_idCommentsActionType) => (media_id: Int) => postmediaByMedia_idCommentsActionConstructor(postmediaByMedia_idCommentsParser(Seq[String]("application/json"))) { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { postmediaByMedia_idCommentsResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[MediaMedia_idCommentsDeleteResponses200]
            )
            val tEXT = request.body
            

                val result =
                        new MediaMedia_idCommentsPostValidator(media_id, tEXT).errors match {
                            case e if e.isEmpty => processValidpostmediaByMedia_idCommentsRequest(f)((media_id, tEXT))(possibleWriters, postmediaByMedia_idCommentsResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(postmediaByMedia_idCommentsResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidpostmediaByMedia_idCommentsRequest[T <: Any](f: postmediaByMedia_idCommentsActionType)(request: postmediaByMedia_idCommentsActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatuspostmediaByMedia_idComments orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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
    private type deletemediaByMedia_idCommentsActionRequestType       = (Int)
    private type deletemediaByMedia_idCommentsActionType              = deletemediaByMedia_idCommentsActionRequestType => Try[(Int, Any)]

    private val errorToStatusdeletemediaByMedia_idComments: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val deletemediaByMedia_idCommentsActionConstructor  = new deletemediaByMedia_idCommentsSecureAction("basic", "comments", "relationships", "likes")
    def deletemediaByMedia_idCommentsAction = (f: deletemediaByMedia_idCommentsActionType) => (media_id: Int) => deletemediaByMedia_idCommentsActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { deletemediaByMedia_idCommentsResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[MediaMedia_idCommentsDeleteResponses200]
            )
            

                val result =
                        new MediaMedia_idCommentsDeleteValidator(media_id).errors match {
                            case e if e.isEmpty => processValiddeletemediaByMedia_idCommentsRequest(f)((media_id))(possibleWriters, deletemediaByMedia_idCommentsResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(deletemediaByMedia_idCommentsResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValiddeletemediaByMedia_idCommentsRequest[T <: Any](f: deletemediaByMedia_idCommentsActionType)(request: deletemediaByMedia_idCommentsActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusdeletemediaByMedia_idComments orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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
    private type gettagsByTag_nameMediaRecentActionRequestType       = (String)
    private type gettagsByTag_nameMediaRecentActionType              = gettagsByTag_nameMediaRecentActionRequestType => Try[(Int, Any)]

    private val errorToStatusgettagsByTag_nameMediaRecent: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val gettagsByTag_nameMediaRecentActionConstructor  = new gettagsByTag_nameMediaRecentSecureAction("basic", "comments", "relationships", "likes")
    def gettagsByTag_nameMediaRecentAction = (f: gettagsByTag_nameMediaRecentActionType) => (tag_name: String) => gettagsByTag_nameMediaRecentActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { gettagsByTag_nameMediaRecentResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[TagsTag_nameMediaRecentGetResponses200]
            )
            

                val result =
                        new TagsTag_nameMediaRecentGetValidator(tag_name).errors match {
                            case e if e.isEmpty => processValidgettagsByTag_nameMediaRecentRequest(f)((tag_name))(possibleWriters, gettagsByTag_nameMediaRecentResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(gettagsByTag_nameMediaRecentResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgettagsByTag_nameMediaRecentRequest[T <: Any](f: gettagsByTag_nameMediaRecentActionType)(request: gettagsByTag_nameMediaRecentActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgettagsByTag_nameMediaRecent orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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
    private type postusersByUser_idRelationshipActionRequestType       = (Double, UsersUser_idRelationshipPostAction)
    private type postusersByUser_idRelationshipActionType              = postusersByUser_idRelationshipActionRequestType => Try[(Int, Any)]

    private val errorToStatuspostusersByUser_idRelationship: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

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
    def postusersByUser_idRelationshipAction = (f: postusersByUser_idRelationshipActionType) => (user_id: Double) => postusersByUser_idRelationshipActionConstructor(postusersByUser_idRelationshipParser(Seq[String]("application/json"))) { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { postusersByUser_idRelationshipResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[UsersUser_idFollowsGetResponses200]
            )
            val action = request.body
            

                val result =
                        new UsersUser_idRelationshipPostValidator(user_id, action).errors match {
                            case e if e.isEmpty => processValidpostusersByUser_idRelationshipRequest(f)((user_id, action))(possibleWriters, postusersByUser_idRelationshipResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(postusersByUser_idRelationshipResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidpostusersByUser_idRelationshipRequest[T <: Any](f: postusersByUser_idRelationshipActionType)(request: postusersByUser_idRelationshipActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatuspostusersByUser_idRelationship orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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
    private type getusersSelfFeedActionRequestType       = (MediaId, MediaId, MediaId)
    private type getusersSelfFeedActionType              = getusersSelfFeedActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetusersSelfFeed: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val getusersSelfFeedActionConstructor  = new getusersSelfFeedSecureAction("basic", "comments", "relationships", "likes")
    def getusersSelfFeedAction = (f: getusersSelfFeedActionType) => (count: MediaId, max_id: MediaId, min_id: MediaId) => getusersSelfFeedActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getusersSelfFeedResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[UsersSelfFeedGetResponses200]
            )
            

                val result =
                        new UsersSelfFeedGetValidator(count, max_id, min_id).errors match {
                            case e if e.isEmpty => processValidgetusersSelfFeedRequest(f)((count, max_id, min_id))(possibleWriters, getusersSelfFeedResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getusersSelfFeedResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetusersSelfFeedRequest[T <: Any](f: getusersSelfFeedActionType)(request: getusersSelfFeedActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetusersSelfFeed orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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
    private type getusersByUser_idActionRequestType       = (Double)
    private type getusersByUser_idActionType              = getusersByUser_idActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetusersByUser_id: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val getusersByUser_idActionConstructor  = new getusersByUser_idSecureAction("basic")
    def getusersByUser_idAction = (f: getusersByUser_idActionType) => (user_id: Double) => getusersByUser_idActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getusersByUser_idResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[UsersUser_idGetResponses200]
            )
            

                val result =
                        new UsersUser_idGetValidator(user_id).errors match {
                            case e if e.isEmpty => processValidgetusersByUser_idRequest(f)((user_id))(possibleWriters, getusersByUser_idResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getusersByUser_idResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetusersByUser_idRequest[T <: Any](f: getusersByUser_idActionType)(request: getusersByUser_idActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetusersByUser_id orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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
    private type getmediaSearchActionRequestType       = (MediaId, Int, LocationLatitude, MediaId, LocationLatitude)
    private type getmediaSearchActionType              = getmediaSearchActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetmediaSearch: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val getmediaSearchActionConstructor  = new getmediaSearchSecureAction("basic", "comments", "relationships", "likes")
    def getmediaSearchAction = (f: getmediaSearchActionType) => (mAX_TIMESTAMP: MediaId, dISTANCE: Int, lNG: LocationLatitude, mIN_TIMESTAMP: MediaId, lAT: LocationLatitude) => getmediaSearchActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getmediaSearchResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[MediaSearchGetResponses200]
            )
            

                val result =
                        new MediaSearchGetValidator(mAX_TIMESTAMP, dISTANCE, lNG, mIN_TIMESTAMP, lAT).errors match {
                            case e if e.isEmpty => processValidgetmediaSearchRequest(f)((mAX_TIMESTAMP, dISTANCE, lNG, mIN_TIMESTAMP, lAT))(possibleWriters, getmediaSearchResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getmediaSearchResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetmediaSearchRequest[T <: Any](f: getmediaSearchActionType)(request: getmediaSearchActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetmediaSearch orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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
    private type getgeographiesByGeo_idMediaRecentActionRequestType       = (Int, MediaId, MediaId)
    private type getgeographiesByGeo_idMediaRecentActionType              = getgeographiesByGeo_idMediaRecentActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetgeographiesByGeo_idMediaRecent: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val getgeographiesByGeo_idMediaRecentActionConstructor  = new getgeographiesByGeo_idMediaRecentSecureAction("basic", "comments", "relationships", "likes")
    def getgeographiesByGeo_idMediaRecentAction = (f: getgeographiesByGeo_idMediaRecentActionType) => (geo_id: Int, count: MediaId, min_id: MediaId) => getgeographiesByGeo_idMediaRecentActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getgeographiesByGeo_idMediaRecentResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[Null]
            )
            

                val result =
                        new GeographiesGeo_idMediaRecentGetValidator(geo_id, count, min_id).errors match {
                            case e if e.isEmpty => processValidgetgeographiesByGeo_idMediaRecentRequest(f)((geo_id, count, min_id))(possibleWriters, getgeographiesByGeo_idMediaRecentResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getgeographiesByGeo_idMediaRecentResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetgeographiesByGeo_idMediaRecentRequest[T <: Any](f: getgeographiesByGeo_idMediaRecentActionType)(request: getgeographiesByGeo_idMediaRecentActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetgeographiesByGeo_idMediaRecent orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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
    private type getmediaByShortcodeActionRequestType       = (String)
    private type getmediaByShortcodeActionType              = getmediaByShortcodeActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetmediaByShortcode: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val getmediaByShortcodeActionConstructor  = new getmediaByShortcodeSecureAction("basic", "comments", "relationships", "likes")
    def getmediaByShortcodeAction = (f: getmediaByShortcodeActionType) => (shortcode: String) => getmediaByShortcodeActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getmediaByShortcodeResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[Media]
            )
            

                val result =
                        new MediaShortcodeGetValidator(shortcode).errors match {
                            case e if e.isEmpty => processValidgetmediaByShortcodeRequest(f)((shortcode))(possibleWriters, getmediaByShortcodeResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getmediaByShortcodeResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetmediaByShortcodeRequest[T <: Any](f: getmediaByShortcodeActionType)(request: getmediaByShortcodeActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetmediaByShortcode orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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
    private type getlocationsSearchActionRequestType       = (MediaId, MediaId, MediaId, LocationLatitude, MediaId, LocationLatitude)
    private type getlocationsSearchActionType              = getlocationsSearchActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetlocationsSearch: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val getlocationsSearchActionConstructor  = new getlocationsSearchSecureAction("basic", "comments", "relationships", "likes")
    def getlocationsSearchAction = (f: getlocationsSearchActionType) => (foursquare_v2_id: MediaId, facebook_places_id: MediaId, distance: MediaId, lat: LocationLatitude, foursquare_id: MediaId, lng: LocationLatitude) => getlocationsSearchActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getlocationsSearchResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[LocationsSearchGetResponses200]
            )
            

                val result =
                        new LocationsSearchGetValidator(foursquare_v2_id, facebook_places_id, distance, lat, foursquare_id, lng).errors match {
                            case e if e.isEmpty => processValidgetlocationsSearchRequest(f)((foursquare_v2_id, facebook_places_id, distance, lat, foursquare_id, lng))(possibleWriters, getlocationsSearchResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getlocationsSearchResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetlocationsSearchRequest[T <: Any](f: getlocationsSearchActionType)(request: getlocationsSearchActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetlocationsSearch orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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
    private type getusersSelfRequested_byActionRequestType       = (Unit)
    private type getusersSelfRequested_byActionType              = getusersSelfRequested_byActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetusersSelfRequested_by: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val getusersSelfRequested_byActionConstructor  = new getusersSelfRequested_bySecureAction("basic", "comments", "relationships", "likes")
    def getusersSelfRequested_byAction = (f: getusersSelfRequested_byActionType) => getusersSelfRequested_byActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getusersSelfRequested_byResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[UsersSelfRequested_byGetResponses200]
            )
            

                val result = processValidgetusersSelfRequested_byRequest(f)()(possibleWriters, getusersSelfRequested_byResponseMimeType)
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetusersSelfRequested_byRequest[T <: Any](f: getusersSelfRequested_byActionType)(request: getusersSelfRequested_byActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetusersSelfRequested_by orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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
    private type getmediaByMedia_idActionRequestType       = (Int)
    private type getmediaByMedia_idActionType              = getmediaByMedia_idActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetmediaByMedia_id: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val getmediaByMedia_idActionConstructor  = new getmediaByMedia_idSecureAction("basic", "comments", "relationships", "likes")
    def getmediaByMedia_idAction = (f: getmediaByMedia_idActionType) => (media_id: Int) => getmediaByMedia_idActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getmediaByMedia_idResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[Media]
            )
            

                val result =
                        new MediaMedia_idGetValidator(media_id).errors match {
                            case e if e.isEmpty => processValidgetmediaByMedia_idRequest(f)((media_id))(possibleWriters, getmediaByMedia_idResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getmediaByMedia_idResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetmediaByMedia_idRequest[T <: Any](f: getmediaByMedia_idActionType)(request: getmediaByMedia_idActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetmediaByMedia_id orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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
    private type getlocationsByLocation_idMediaRecentActionRequestType       = (Int, MediaId, MediaId, MediaFilter, MediaFilter)
    private type getlocationsByLocation_idMediaRecentActionType              = getlocationsByLocation_idMediaRecentActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetlocationsByLocation_idMediaRecent: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val getlocationsByLocation_idMediaRecentActionConstructor  = new getlocationsByLocation_idMediaRecentSecureAction("basic", "comments", "relationships", "likes")
    def getlocationsByLocation_idMediaRecentAction = (f: getlocationsByLocation_idMediaRecentActionType) => (location_id: Int, max_timestamp: MediaId, min_timestamp: MediaId, min_id: MediaFilter, max_id: MediaFilter) => getlocationsByLocation_idMediaRecentActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getlocationsByLocation_idMediaRecentResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[UsersSelfFeedGetResponses200]
            )
            

                val result =
                        new LocationsLocation_idMediaRecentGetValidator(location_id, max_timestamp, min_timestamp, min_id, max_id).errors match {
                            case e if e.isEmpty => processValidgetlocationsByLocation_idMediaRecentRequest(f)((location_id, max_timestamp, min_timestamp, min_id, max_id))(possibleWriters, getlocationsByLocation_idMediaRecentResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getlocationsByLocation_idMediaRecentResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetlocationsByLocation_idMediaRecentRequest[T <: Any](f: getlocationsByLocation_idMediaRecentActionType)(request: getlocationsByLocation_idMediaRecentActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetlocationsByLocation_idMediaRecent orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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
    private type getusersByUser_idMediaRecentActionRequestType       = (Double, MediaId, MediaFilter, MediaId, MediaFilter, MediaId)
    private type getusersByUser_idMediaRecentActionType              = getusersByUser_idMediaRecentActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetusersByUser_idMediaRecent: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val getusersByUser_idMediaRecentActionConstructor  = new getusersByUser_idMediaRecentSecureAction("basic", "comments", "relationships", "likes")
    def getusersByUser_idMediaRecentAction = (f: getusersByUser_idMediaRecentActionType) => (user_id: Double, max_timestamp: MediaId, min_id: MediaFilter, min_timestamp: MediaId, max_id: MediaFilter, count: MediaId) => getusersByUser_idMediaRecentActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getusersByUser_idMediaRecentResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[UsersSelfFeedGetResponses200]
            )
            

                val result =
                        new UsersUser_idMediaRecentGetValidator(user_id, max_timestamp, min_id, min_timestamp, max_id, count).errors match {
                            case e if e.isEmpty => processValidgetusersByUser_idMediaRecentRequest(f)((user_id, max_timestamp, min_id, min_timestamp, max_id, count))(possibleWriters, getusersByUser_idMediaRecentResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getusersByUser_idMediaRecentResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetusersByUser_idMediaRecentRequest[T <: Any](f: getusersByUser_idMediaRecentActionType)(request: getusersByUser_idMediaRecentActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetusersByUser_idMediaRecent orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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
    private type getmediaPopularActionRequestType       = (Unit)
    private type getmediaPopularActionType              = getmediaPopularActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetmediaPopular: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val getmediaPopularActionConstructor  = new getmediaPopularSecureAction("basic", "comments", "relationships", "likes")
    def getmediaPopularAction = (f: getmediaPopularActionType) => getmediaPopularActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getmediaPopularResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[UsersSelfFeedGetResponses200]
            )
            

                val result = processValidgetmediaPopularRequest(f)()(possibleWriters, getmediaPopularResponseMimeType)
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetmediaPopularRequest[T <: Any](f: getmediaPopularActionType)(request: getmediaPopularActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetmediaPopular orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
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
