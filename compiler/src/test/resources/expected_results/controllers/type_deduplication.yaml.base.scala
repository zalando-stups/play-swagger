package type_deduplication.yaml

import play.api.mvc.{Action, Controller, Results}
import play.api.http._
import Results.Status
import de.zalando.play.controllers.{PlayBodyParsing, ParsingError}
import PlayBodyParsing._
import scala.util._

import de.zalando.play.controllers.PlayPathBindables




trait Type_deduplicationYamlBase extends Controller with PlayBodyParsing {
    private type getplantsByPlant_idWateringsByWatering_idActionRequestType       = (String, String)
    private type getplantsByPlant_idWateringsByWatering_idActionType              = getplantsByPlant_idWateringsByWatering_idActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetplantsByPlant_idWateringsByWatering_id: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val getplantsByPlant_idWateringsByWatering_idActionConstructor  = Action
    def getplantsByPlant_idWateringsByWatering_idAction = (f: getplantsByPlant_idWateringsByWatering_idActionType) => (plant_id: String, watering_id: String) => getplantsByPlant_idWateringsByWatering_idActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getplantsByPlant_idWateringsByWatering_idResponseMimeType =>
                val possibleWriters = Map(
                    404 -> anyToWritable[Error], 
                    200 -> anyToWritable[Watering]
            ).withDefaultValue(anyToWritable[Error])
            

                val result =
                        new PlantsPlant_idWateringsWatering_idGetValidator(plant_id, watering_id).errors match {
                            case e if e.isEmpty => processValidgetplantsByPlant_idWateringsByWatering_idRequest(f)((plant_id, watering_id))(possibleWriters, getplantsByPlant_idWateringsByWatering_idResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getplantsByPlant_idWateringsByWatering_idResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetplantsByPlant_idWateringsByWatering_idRequest[T <: Any](f: getplantsByPlant_idWateringsByWatering_idActionType)(request: getplantsByPlant_idWateringsByWatering_idActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetplantsByPlant_idWateringsByWatering_id orElse defaultErrorMapping)(error)
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
    private type putplantsByPlant_idWateringsByWatering_idActionRequestType       = (String, String)
    private type putplantsByPlant_idWateringsByWatering_idActionType              = putplantsByPlant_idWateringsByWatering_idActionRequestType => Try[(Int, Any)]

    private val errorToStatusputplantsByPlant_idWateringsByWatering_id: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val putplantsByPlant_idWateringsByWatering_idActionConstructor  = Action
    def putplantsByPlant_idWateringsByWatering_idAction = (f: putplantsByPlant_idWateringsByWatering_idActionType) => (plant_id: String, watering_id: String) => putplantsByPlant_idWateringsByWatering_idActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { putplantsByPlant_idWateringsByWatering_idResponseMimeType =>
                val possibleWriters = Map(
                    404 -> anyToWritable[Error], 
                    200 -> anyToWritable[Null], 
                    201 -> anyToWritable[Null]
            ).withDefaultValue(anyToWritable[Error])
            

                val result =
                        new PlantsPlant_idWateringsWatering_idPutValidator(plant_id, watering_id).errors match {
                            case e if e.isEmpty => processValidputplantsByPlant_idWateringsByWatering_idRequest(f)((plant_id, watering_id))(possibleWriters, putplantsByPlant_idWateringsByWatering_idResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(putplantsByPlant_idWateringsByWatering_idResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidputplantsByPlant_idWateringsByWatering_idRequest[T <: Any](f: putplantsByPlant_idWateringsByWatering_idActionType)(request: putplantsByPlant_idWateringsByWatering_idActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusputplantsByPlant_idWateringsByWatering_id orElse defaultErrorMapping)(error)
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
    private type getusersMeActionRequestType       = (Unit)
    private type getusersMeActionType              = getusersMeActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetusersMe: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val getusersMeActionConstructor  = Action
    def getusersMeAction = (f: getusersMeActionType) => getusersMeActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getusersMeResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[User]
            ).withDefaultValue(anyToWritable[Error])
            

                val result = processValidgetusersMeRequest(f)()(possibleWriters, getusersMeResponseMimeType)
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetusersMeRequest[T <: Any](f: getusersMeActionType)(request: getusersMeActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetusersMe orElse defaultErrorMapping)(error)
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
    private type getplantsByPlant_idSunlight_needsActionRequestType       = (String)
    private type getplantsByPlant_idSunlight_needsActionType              = getplantsByPlant_idSunlight_needsActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetplantsByPlant_idSunlight_needs: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val getplantsByPlant_idSunlight_needsActionConstructor  = Action
    def getplantsByPlant_idSunlight_needsAction = (f: getplantsByPlant_idSunlight_needsActionType) => (plant_id: String) => getplantsByPlant_idSunlight_needsActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getplantsByPlant_idSunlight_needsResponseMimeType =>
                val possibleWriters = Map(
                    404 -> anyToWritable[Null], 
                    200 -> anyToWritable[SunlightNeeds]
            ).withDefaultValue(anyToWritable[Error])
            

                val result =
                        new PlantsPlant_idSunlight_needsGetValidator(plant_id).errors match {
                            case e if e.isEmpty => processValidgetplantsByPlant_idSunlight_needsRequest(f)((plant_id))(possibleWriters, getplantsByPlant_idSunlight_needsResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getplantsByPlant_idSunlight_needsResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetplantsByPlant_idSunlight_needsRequest[T <: Any](f: getplantsByPlant_idSunlight_needsActionType)(request: getplantsByPlant_idSunlight_needsActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetplantsByPlant_idSunlight_needs orElse defaultErrorMapping)(error)
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
    private type putplantsByPlant_idSunlight_needsActionRequestType       = (String, SunlightNeeds)
    private type putplantsByPlant_idSunlight_needsActionType              = putplantsByPlant_idSunlight_needsActionRequestType => Try[(Int, Any)]

    private val errorToStatusputplantsByPlant_idSunlight_needs: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

        private def putplantsByPlant_idSunlight_needsParser(acceptedTypes: Seq[String], maxLength: Int = parse.DefaultMaxTextLength) = {
            def bodyMimeType: Option[MediaType] => String = mediaType => {
                val requestType = mediaType.toSeq.map {
                    case m: MediaRange => m
                    case MediaType(a,b,c) => new MediaRange(a,b,c,None,Nil)
                }
                negotiateContent(requestType, acceptedTypes).orElse(acceptedTypes.headOption).getOrElse("application/json")
            }
            
            import de.zalando.play.controllers.WrappedBodyParsers
            
            val customParsers = WrappedBodyParsers.anyParser[SunlightNeeds]
            anyParser[SunlightNeeds](bodyMimeType, customParsers, "Invalid SunlightNeeds", maxLength)
        }

    val putplantsByPlant_idSunlight_needsActionConstructor  = Action
    def putplantsByPlant_idSunlight_needsAction = (f: putplantsByPlant_idSunlight_needsActionType) => (plant_id: String) => putplantsByPlant_idSunlight_needsActionConstructor(putplantsByPlant_idSunlight_needsParser(Seq[String]())) { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { putplantsByPlant_idSunlight_needsResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[Null], 
                    404 -> anyToWritable[Null]
            ).withDefaultValue(anyToWritable[Error])
            val sunlight_needs = request.body
            

                val result =
                        new PlantsPlant_idSunlight_needsPutValidator(plant_id, sunlight_needs).errors match {
                            case e if e.isEmpty => processValidputplantsByPlant_idSunlight_needsRequest(f)((plant_id, sunlight_needs))(possibleWriters, putplantsByPlant_idSunlight_needsResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(putplantsByPlant_idSunlight_needsResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidputplantsByPlant_idSunlight_needsRequest[T <: Any](f: putplantsByPlant_idSunlight_needsActionType)(request: putplantsByPlant_idSunlight_needsActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusputplantsByPlant_idSunlight_needs orElse defaultErrorMapping)(error)
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
    private type getusersActionRequestType       = (ErrorCode, ErrorCode)
    private type getusersActionType              = getusersActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetusers: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val getusersActionConstructor  = Action
    def getusersAction = (f: getusersActionType) => (limit: ErrorCode, offset: ErrorCode) => getusersActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getusersResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[Seq[User]]
            ).withDefaultValue(anyToWritable[Error])
            

                val result =
                        new UsersGetValidator(limit, offset).errors match {
                            case e if e.isEmpty => processValidgetusersRequest(f)((limit, offset))(possibleWriters, getusersResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getusersResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetusersRequest[T <: Any](f: getusersActionType)(request: getusersActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetusers orElse defaultErrorMapping)(error)
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
    private type postusersActionRequestType       = (SigninData)
    private type postusersActionType              = postusersActionRequestType => Try[(Int, Any)]

    private val errorToStatuspostusers: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

        private def postusersParser(acceptedTypes: Seq[String], maxLength: Int = parse.DefaultMaxTextLength) = {
            def bodyMimeType: Option[MediaType] => String = mediaType => {
                val requestType = mediaType.toSeq.map {
                    case m: MediaRange => m
                    case MediaType(a,b,c) => new MediaRange(a,b,c,None,Nil)
                }
                negotiateContent(requestType, acceptedTypes).orElse(acceptedTypes.headOption).getOrElse("application/json")
            }
            
            import de.zalando.play.controllers.WrappedBodyParsers
            
            val customParsers = WrappedBodyParsers.anyParser[SigninData]
            anyParser[SigninData](bodyMimeType, customParsers, "Invalid SigninData", maxLength)
        }

    val postusersActionConstructor  = Action
    def postusersAction = (f: postusersActionType) => postusersActionConstructor(postusersParser(Seq[String]())) { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { postusersResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[Null]
            ).withDefaultValue(anyToWritable[Error])
            val signin_data = request.body
            

                val result =
                        new UsersPostValidator(signin_data).errors match {
                            case e if e.isEmpty => processValidpostusersRequest(f)((signin_data))(possibleWriters, postusersResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(postusersResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidpostusersRequest[T <: Any](f: postusersActionType)(request: postusersActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatuspostusers orElse defaultErrorMapping)(error)
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
    private type getareasByArea_idActionRequestType       = (String)
    private type getareasByArea_idActionType              = getareasByArea_idActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetareasByArea_id: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val getareasByArea_idActionConstructor  = Action
    def getareasByArea_idAction = (f: getareasByArea_idActionType) => (area_id: String) => getareasByArea_idActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getareasByArea_idResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[Area]
            ).withDefaultValue(anyToWritable[Error])
            

                val result =
                        new AreasArea_idGetValidator(area_id).errors match {
                            case e if e.isEmpty => processValidgetareasByArea_idRequest(f)((area_id))(possibleWriters, getareasByArea_idResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getareasByArea_idResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetareasByArea_idRequest[T <: Any](f: getareasByArea_idActionType)(request: getareasByArea_idActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetareasByArea_id orElse defaultErrorMapping)(error)
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
    private type putareasByArea_idActionRequestType       = (String)
    private type putareasByArea_idActionType              = putareasByArea_idActionRequestType => Try[(Int, Any)]

    private val errorToStatusputareasByArea_id: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val putareasByArea_idActionConstructor  = Action
    def putareasByArea_idAction = (f: putareasByArea_idActionType) => (area_id: String) => putareasByArea_idActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { putareasByArea_idResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[Null], 
                    201 -> anyToWritable[Null]
            ).withDefaultValue(anyToWritable[Error])
            

                val result =
                        new AreasArea_idPutValidator(area_id).errors match {
                            case e if e.isEmpty => processValidputareasByArea_idRequest(f)((area_id))(possibleWriters, putareasByArea_idResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(putareasByArea_idResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidputareasByArea_idRequest[T <: Any](f: putareasByArea_idActionType)(request: putareasByArea_idActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusputareasByArea_id orElse defaultErrorMapping)(error)
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
    private type deleteareasByArea_idActionRequestType       = (String)
    private type deleteareasByArea_idActionType              = deleteareasByArea_idActionRequestType => Try[(Int, Any)]

    private val errorToStatusdeleteareasByArea_id: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val deleteareasByArea_idActionConstructor  = Action
    def deleteareasByArea_idAction = (f: deleteareasByArea_idActionType) => (area_id: String) => deleteareasByArea_idActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { deleteareasByArea_idResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[Null]
            ).withDefaultValue(anyToWritable[Error])
            

                val result =
                        new AreasArea_idDeleteValidator(area_id).errors match {
                            case e if e.isEmpty => processValiddeleteareasByArea_idRequest(f)((area_id))(possibleWriters, deleteareasByArea_idResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(deleteareasByArea_idResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValiddeleteareasByArea_idRequest[T <: Any](f: deleteareasByArea_idActionType)(request: deleteareasByArea_idActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusdeleteareasByArea_id orElse defaultErrorMapping)(error)
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
    private type getplantsActionRequestType       = (PlantsGetLimit, PlantsGetOffset)
    private type getplantsActionType              = getplantsActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetplants: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val getplantsActionConstructor  = Action
    def getplantsAction = (f: getplantsActionType) => (limit: PlantsGetLimit, offset: PlantsGetOffset) => getplantsActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getplantsResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[Seq[Plant]]
            ).withDefaultValue(anyToWritable[Error])
            

                val result =
                        new PlantsGetValidator(limit, offset).errors match {
                            case e if e.isEmpty => processValidgetplantsRequest(f)((limit, offset))(possibleWriters, getplantsResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getplantsResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetplantsRequest[T <: Any](f: getplantsActionType)(request: getplantsActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetplants orElse defaultErrorMapping)(error)
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
    private type getuserByUser_idPlantsActionRequestType       = (String, ErrorCode, ErrorCode)
    private type getuserByUser_idPlantsActionType              = getuserByUser_idPlantsActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetuserByUser_idPlants: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val getuserByUser_idPlantsActionConstructor  = Action
    def getuserByUser_idPlantsAction = (f: getuserByUser_idPlantsActionType) => (user_id: String, limit: ErrorCode, offset: ErrorCode) => getuserByUser_idPlantsActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getuserByUser_idPlantsResponseMimeType =>
                val possibleWriters = Map(
                    404 -> anyToWritable[Null], 
                    200 -> anyToWritable[Seq[Plant]]
            ).withDefaultValue(anyToWritable[Error])
            

                val result =
                        new UserUser_idPlantsGetValidator(user_id, limit, offset).errors match {
                            case e if e.isEmpty => processValidgetuserByUser_idPlantsRequest(f)((user_id, limit, offset))(possibleWriters, getuserByUser_idPlantsResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getuserByUser_idPlantsResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetuserByUser_idPlantsRequest[T <: Any](f: getuserByUser_idPlantsActionType)(request: getuserByUser_idPlantsActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetuserByUser_idPlants orElse defaultErrorMapping)(error)
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
    private type getusersByUser_idActionRequestType       = (String)
    private type getusersByUser_idActionType              = getusersByUser_idActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetusersByUser_id: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val getusersByUser_idActionConstructor  = Action
    def getusersByUser_idAction = (f: getusersByUser_idActionType) => (user_id: String) => getusersByUser_idActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getusersByUser_idResponseMimeType =>
                val possibleWriters = Map(
                    404 -> anyToWritable[Error], 
                    200 -> anyToWritable[User]
            ).withDefaultValue(anyToWritable[Error])
            

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
    private type putusersByUser_idActionRequestType       = (String, User)
    private type putusersByUser_idActionType              = putusersByUser_idActionRequestType => Try[(Int, Any)]

    private val errorToStatusputusersByUser_id: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

        private def putusersByUser_idParser(acceptedTypes: Seq[String], maxLength: Int = parse.DefaultMaxTextLength) = {
            def bodyMimeType: Option[MediaType] => String = mediaType => {
                val requestType = mediaType.toSeq.map {
                    case m: MediaRange => m
                    case MediaType(a,b,c) => new MediaRange(a,b,c,None,Nil)
                }
                negotiateContent(requestType, acceptedTypes).orElse(acceptedTypes.headOption).getOrElse("application/json")
            }
            
            import de.zalando.play.controllers.WrappedBodyParsers
            
            val customParsers = WrappedBodyParsers.anyParser[User]
            anyParser[User](bodyMimeType, customParsers, "Invalid User", maxLength)
        }

    val putusersByUser_idActionConstructor  = Action
    def putusersByUser_idAction = (f: putusersByUser_idActionType) => (user_id: String) => putusersByUser_idActionConstructor(putusersByUser_idParser(Seq[String]())) { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { putusersByUser_idResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[Null], 
                    201 -> anyToWritable[Null]
            ).withDefaultValue(anyToWritable[Error])
            val user = request.body
            

                val result =
                        new UsersUser_idPutValidator(user_id, user).errors match {
                            case e if e.isEmpty => processValidputusersByUser_idRequest(f)((user_id, user))(possibleWriters, putusersByUser_idResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(putusersByUser_idResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidputusersByUser_idRequest[T <: Any](f: putusersByUser_idActionType)(request: putusersByUser_idActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusputusersByUser_id orElse defaultErrorMapping)(error)
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
    private type deleteusersByUser_idActionRequestType       = (String, User)
    private type deleteusersByUser_idActionType              = deleteusersByUser_idActionRequestType => Try[(Int, Any)]

    private val errorToStatusdeleteusersByUser_id: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

        private def deleteusersByUser_idParser(acceptedTypes: Seq[String], maxLength: Int = parse.DefaultMaxTextLength) = {
            def bodyMimeType: Option[MediaType] => String = mediaType => {
                val requestType = mediaType.toSeq.map {
                    case m: MediaRange => m
                    case MediaType(a,b,c) => new MediaRange(a,b,c,None,Nil)
                }
                negotiateContent(requestType, acceptedTypes).orElse(acceptedTypes.headOption).getOrElse("application/json")
            }
            
            import de.zalando.play.controllers.WrappedBodyParsers
            
            val customParsers = WrappedBodyParsers.anyParser[User]
            anyParser[User](bodyMimeType, customParsers, "Invalid User", maxLength)
        }

    val deleteusersByUser_idActionConstructor  = Action
    def deleteusersByUser_idAction = (f: deleteusersByUser_idActionType) => (user_id: String) => deleteusersByUser_idActionConstructor(deleteusersByUser_idParser(Seq[String]())) { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { deleteusersByUser_idResponseMimeType =>
                val possibleWriters = Map(
                    404 -> anyToWritable[Error], 
                    200 -> anyToWritable[Null]
            ).withDefaultValue(anyToWritable[Error])
            val user = request.body
            

                val result =
                        new UsersUser_idDeleteValidator(user_id, user).errors match {
                            case e if e.isEmpty => processValiddeleteusersByUser_idRequest(f)((user_id, user))(possibleWriters, deleteusersByUser_idResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(deleteusersByUser_idResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValiddeleteusersByUser_idRequest[T <: Any](f: deleteusersByUser_idActionType)(request: deleteusersByUser_idActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusdeleteusersByUser_id orElse defaultErrorMapping)(error)
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
    private type getareasActionRequestType       = (ErrorCode, ErrorCode)
    private type getareasActionType              = getareasActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetareas: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val getareasActionConstructor  = Action
    def getareasAction = (f: getareasActionType) => (limit: ErrorCode, offset: ErrorCode) => getareasActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getareasResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[Seq[Area]]
            ).withDefaultValue(anyToWritable[Error])
            

                val result =
                        new AreasGetValidator(limit, offset).errors match {
                            case e if e.isEmpty => processValidgetareasRequest(f)((limit, offset))(possibleWriters, getareasResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getareasResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetareasRequest[T <: Any](f: getareasActionType)(request: getareasActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetareas orElse defaultErrorMapping)(error)
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
    private type getplantsByPlant_idLocationActionRequestType       = (String)
    private type getplantsByPlant_idLocationActionType              = getplantsByPlant_idLocationActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetplantsByPlant_idLocation: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val getplantsByPlant_idLocationActionConstructor  = Action
    def getplantsByPlant_idLocationAction = (f: getplantsByPlant_idLocationActionType) => (plant_id: String) => getplantsByPlant_idLocationActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getplantsByPlant_idLocationResponseMimeType =>
                val possibleWriters = Map(
                    404 -> anyToWritable[Null], 
                    200 -> anyToWritable[Location]
            ).withDefaultValue(anyToWritable[Error])
            

                val result =
                        new PlantsPlant_idLocationGetValidator(plant_id).errors match {
                            case e if e.isEmpty => processValidgetplantsByPlant_idLocationRequest(f)((plant_id))(possibleWriters, getplantsByPlant_idLocationResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getplantsByPlant_idLocationResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetplantsByPlant_idLocationRequest[T <: Any](f: getplantsByPlant_idLocationActionType)(request: getplantsByPlant_idLocationActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetplantsByPlant_idLocation orElse defaultErrorMapping)(error)
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
    private type putplantsByPlant_idLocationActionRequestType       = (String, Location)
    private type putplantsByPlant_idLocationActionType              = putplantsByPlant_idLocationActionRequestType => Try[(Int, Any)]

    private val errorToStatusputplantsByPlant_idLocation: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

        private def putplantsByPlant_idLocationParser(acceptedTypes: Seq[String], maxLength: Int = parse.DefaultMaxTextLength) = {
            def bodyMimeType: Option[MediaType] => String = mediaType => {
                val requestType = mediaType.toSeq.map {
                    case m: MediaRange => m
                    case MediaType(a,b,c) => new MediaRange(a,b,c,None,Nil)
                }
                negotiateContent(requestType, acceptedTypes).orElse(acceptedTypes.headOption).getOrElse("application/json")
            }
            
            import de.zalando.play.controllers.WrappedBodyParsers
            
            val customParsers = WrappedBodyParsers.anyParser[Location]
            anyParser[Location](bodyMimeType, customParsers, "Invalid Location", maxLength)
        }

    val putplantsByPlant_idLocationActionConstructor  = Action
    def putplantsByPlant_idLocationAction = (f: putplantsByPlant_idLocationActionType) => (plant_id: String) => putplantsByPlant_idLocationActionConstructor(putplantsByPlant_idLocationParser(Seq[String]())) { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { putplantsByPlant_idLocationResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[Null], 
                    404 -> anyToWritable[Null]
            ).withDefaultValue(anyToWritable[Error])
            val location = request.body
            

                val result =
                        new PlantsPlant_idLocationPutValidator(plant_id, location).errors match {
                            case e if e.isEmpty => processValidputplantsByPlant_idLocationRequest(f)((plant_id, location))(possibleWriters, putplantsByPlant_idLocationResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(putplantsByPlant_idLocationResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidputplantsByPlant_idLocationRequest[T <: Any](f: putplantsByPlant_idLocationActionType)(request: putplantsByPlant_idLocationActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusputplantsByPlant_idLocation orElse defaultErrorMapping)(error)
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
    private type deleteplantsByPlant_idLocationActionRequestType       = (String)
    private type deleteplantsByPlant_idLocationActionType              = deleteplantsByPlant_idLocationActionRequestType => Try[(Int, Any)]

    private val errorToStatusdeleteplantsByPlant_idLocation: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val deleteplantsByPlant_idLocationActionConstructor  = Action
    def deleteplantsByPlant_idLocationAction = (f: deleteplantsByPlant_idLocationActionType) => (plant_id: String) => deleteplantsByPlant_idLocationActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { deleteplantsByPlant_idLocationResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[Null], 
                    404 -> anyToWritable[Null]
            ).withDefaultValue(anyToWritable[Error])
            

                val result =
                        new PlantsPlant_idLocationDeleteValidator(plant_id).errors match {
                            case e if e.isEmpty => processValiddeleteplantsByPlant_idLocationRequest(f)((plant_id))(possibleWriters, deleteplantsByPlant_idLocationResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(deleteplantsByPlant_idLocationResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValiddeleteplantsByPlant_idLocationRequest[T <: Any](f: deleteplantsByPlant_idLocationActionType)(request: deleteplantsByPlant_idLocationActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusdeleteplantsByPlant_idLocation orElse defaultErrorMapping)(error)
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
    private type getusersByUser_idPictureActionRequestType       = (String)
    private type getusersByUser_idPictureActionType              = getusersByUser_idPictureActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetusersByUser_idPicture: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val getusersByUser_idPictureActionConstructor  = Action
    def getusersByUser_idPictureAction = (f: getusersByUser_idPictureActionType) => (user_id: String) => getusersByUser_idPictureActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getusersByUser_idPictureResponseMimeType =>
                val possibleWriters = Map(
                    404 -> anyToWritable[Error], 
                    200 -> anyToWritable[Null]
            ).withDefaultValue(anyToWritable[Error])
            

                val result =
                        new UsersUser_idPictureGetValidator(user_id).errors match {
                            case e if e.isEmpty => processValidgetusersByUser_idPictureRequest(f)((user_id))(possibleWriters, getusersByUser_idPictureResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getusersByUser_idPictureResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetusersByUser_idPictureRequest[T <: Any](f: getusersByUser_idPictureActionType)(request: getusersByUser_idPictureActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetusersByUser_idPicture orElse defaultErrorMapping)(error)
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
    private type putusersByUser_idPictureActionRequestType       = (String)
    private type putusersByUser_idPictureActionType              = putusersByUser_idPictureActionRequestType => Try[(Int, Any)]

    private val errorToStatusputusersByUser_idPicture: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val putusersByUser_idPictureActionConstructor  = Action
    def putusersByUser_idPictureAction = (f: putusersByUser_idPictureActionType) => (user_id: String) => putusersByUser_idPictureActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { putusersByUser_idPictureResponseMimeType =>
                val possibleWriters = Map(
                    404 -> anyToWritable[Error], 
                    200 -> anyToWritable[Null], 
                    201 -> anyToWritable[Null]
            ).withDefaultValue(anyToWritable[Error])
            

                val result =
                        new UsersUser_idPicturePutValidator(user_id).errors match {
                            case e if e.isEmpty => processValidputusersByUser_idPictureRequest(f)((user_id))(possibleWriters, putusersByUser_idPictureResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(putusersByUser_idPictureResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidputusersByUser_idPictureRequest[T <: Any](f: putusersByUser_idPictureActionType)(request: putusersByUser_idPictureActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusputusersByUser_idPicture orElse defaultErrorMapping)(error)
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
    private type deleteusersByUser_idPictureActionRequestType       = (String)
    private type deleteusersByUser_idPictureActionType              = deleteusersByUser_idPictureActionRequestType => Try[(Int, Any)]

    private val errorToStatusdeleteusersByUser_idPicture: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val deleteusersByUser_idPictureActionConstructor  = Action
    def deleteusersByUser_idPictureAction = (f: deleteusersByUser_idPictureActionType) => (user_id: String) => deleteusersByUser_idPictureActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { deleteusersByUser_idPictureResponseMimeType =>
                val possibleWriters = Map(
                    404 -> anyToWritable[Error], 
                    200 -> anyToWritable[Null]
            ).withDefaultValue(anyToWritable[Error])
            

                val result =
                        new UsersUser_idPictureDeleteValidator(user_id).errors match {
                            case e if e.isEmpty => processValiddeleteusersByUser_idPictureRequest(f)((user_id))(possibleWriters, deleteusersByUser_idPictureResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(deleteusersByUser_idPictureResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValiddeleteusersByUser_idPictureRequest[T <: Any](f: deleteusersByUser_idPictureActionType)(request: deleteusersByUser_idPictureActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusdeleteusersByUser_idPicture orElse defaultErrorMapping)(error)
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
    private type getplantsByPlant_idPicturesActionRequestType       = (String, ErrorCode, ErrorCode)
    private type getplantsByPlant_idPicturesActionType              = getplantsByPlant_idPicturesActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetplantsByPlant_idPictures: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val getplantsByPlant_idPicturesActionConstructor  = Action
    def getplantsByPlant_idPicturesAction = (f: getplantsByPlant_idPicturesActionType) => (plant_id: String, limit: ErrorCode, offset: ErrorCode) => getplantsByPlant_idPicturesActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getplantsByPlant_idPicturesResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[Seq[String]], 
                    404 -> anyToWritable[Null]
            ).withDefaultValue(anyToWritable[Error])
            

                val result =
                        new PlantsPlant_idPicturesGetValidator(plant_id, limit, offset).errors match {
                            case e if e.isEmpty => processValidgetplantsByPlant_idPicturesRequest(f)((plant_id, limit, offset))(possibleWriters, getplantsByPlant_idPicturesResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getplantsByPlant_idPicturesResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetplantsByPlant_idPicturesRequest[T <: Any](f: getplantsByPlant_idPicturesActionType)(request: getplantsByPlant_idPicturesActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetplantsByPlant_idPictures orElse defaultErrorMapping)(error)
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
    private type getplantsByPlant_idActionRequestType       = (String)
    private type getplantsByPlant_idActionType              = getplantsByPlant_idActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetplantsByPlant_id: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val getplantsByPlant_idActionConstructor  = Action
    def getplantsByPlant_idAction = (f: getplantsByPlant_idActionType) => (plant_id: String) => getplantsByPlant_idActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getplantsByPlant_idResponseMimeType =>
                val possibleWriters = Map(
                    404 -> anyToWritable[Error], 
                    200 -> anyToWritable[Plant]
            ).withDefaultValue(anyToWritable[Error])
            

                val result =
                        new PlantsPlant_idGetValidator(plant_id).errors match {
                            case e if e.isEmpty => processValidgetplantsByPlant_idRequest(f)((plant_id))(possibleWriters, getplantsByPlant_idResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getplantsByPlant_idResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetplantsByPlant_idRequest[T <: Any](f: getplantsByPlant_idActionType)(request: getplantsByPlant_idActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetplantsByPlant_id orElse defaultErrorMapping)(error)
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
    private type putplantsByPlant_idActionRequestType       = (String, Plant)
    private type putplantsByPlant_idActionType              = putplantsByPlant_idActionRequestType => Try[(Int, Any)]

    private val errorToStatusputplantsByPlant_id: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

        private def putplantsByPlant_idParser(acceptedTypes: Seq[String], maxLength: Int = parse.DefaultMaxTextLength) = {
            def bodyMimeType: Option[MediaType] => String = mediaType => {
                val requestType = mediaType.toSeq.map {
                    case m: MediaRange => m
                    case MediaType(a,b,c) => new MediaRange(a,b,c,None,Nil)
                }
                negotiateContent(requestType, acceptedTypes).orElse(acceptedTypes.headOption).getOrElse("application/json")
            }
            
            import de.zalando.play.controllers.WrappedBodyParsers
            
            val customParsers = WrappedBodyParsers.anyParser[Plant]
            anyParser[Plant](bodyMimeType, customParsers, "Invalid Plant", maxLength)
        }

    val putplantsByPlant_idActionConstructor  = Action
    def putplantsByPlant_idAction = (f: putplantsByPlant_idActionType) => (plant_id: String) => putplantsByPlant_idActionConstructor(putplantsByPlant_idParser(Seq[String]())) { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { putplantsByPlant_idResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[Null], 
                    201 -> anyToWritable[Null], 
                    404 -> anyToWritable[Null]
            ).withDefaultValue(anyToWritable[Error])
            val plant = request.body
            

                val result =
                        new PlantsPlant_idPutValidator(plant_id, plant).errors match {
                            case e if e.isEmpty => processValidputplantsByPlant_idRequest(f)((plant_id, plant))(possibleWriters, putplantsByPlant_idResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(putplantsByPlant_idResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidputplantsByPlant_idRequest[T <: Any](f: putplantsByPlant_idActionType)(request: putplantsByPlant_idActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusputplantsByPlant_id orElse defaultErrorMapping)(error)
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
    private type deleteplantsByPlant_idActionRequestType       = (String)
    private type deleteplantsByPlant_idActionType              = deleteplantsByPlant_idActionRequestType => Try[(Int, Any)]

    private val errorToStatusdeleteplantsByPlant_id: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val deleteplantsByPlant_idActionConstructor  = Action
    def deleteplantsByPlant_idAction = (f: deleteplantsByPlant_idActionType) => (plant_id: String) => deleteplantsByPlant_idActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { deleteplantsByPlant_idResponseMimeType =>
                val possibleWriters = Map(
                    404 -> anyToWritable[Error], 
                    200 -> anyToWritable[Null]
            ).withDefaultValue(anyToWritable[Error])
            

                val result =
                        new PlantsPlant_idDeleteValidator(plant_id).errors match {
                            case e if e.isEmpty => processValiddeleteplantsByPlant_idRequest(f)((plant_id))(possibleWriters, deleteplantsByPlant_idResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(deleteplantsByPlant_idResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValiddeleteplantsByPlant_idRequest[T <: Any](f: deleteplantsByPlant_idActionType)(request: deleteplantsByPlant_idActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusdeleteplantsByPlant_id orElse defaultErrorMapping)(error)
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
    private type getplantsByPlant_idWateringsActionRequestType       = (String, ErrorCode, ErrorCode)
    private type getplantsByPlant_idWateringsActionType              = getplantsByPlant_idWateringsActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetplantsByPlant_idWaterings: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val getplantsByPlant_idWateringsActionConstructor  = Action
    def getplantsByPlant_idWateringsAction = (f: getplantsByPlant_idWateringsActionType) => (plant_id: String, limit: ErrorCode, offset: ErrorCode) => getplantsByPlant_idWateringsActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getplantsByPlant_idWateringsResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[Seq[Watering]], 
                    404 -> anyToWritable[Null]
            ).withDefaultValue(anyToWritable[Error])
            

                val result =
                        new PlantsPlant_idWateringsGetValidator(plant_id, limit, offset).errors match {
                            case e if e.isEmpty => processValidgetplantsByPlant_idWateringsRequest(f)((plant_id, limit, offset))(possibleWriters, getplantsByPlant_idWateringsResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getplantsByPlant_idWateringsResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetplantsByPlant_idWateringsRequest[T <: Any](f: getplantsByPlant_idWateringsActionType)(request: getplantsByPlant_idWateringsActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetplantsByPlant_idWaterings orElse defaultErrorMapping)(error)
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
    private type getplantsByPlant_idPicturesByPicture_idActionRequestType       = (String, String)
    private type getplantsByPlant_idPicturesByPicture_idActionType              = getplantsByPlant_idPicturesByPicture_idActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetplantsByPlant_idPicturesByPicture_id: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val getplantsByPlant_idPicturesByPicture_idActionConstructor  = Action
    def getplantsByPlant_idPicturesByPicture_idAction = (f: getplantsByPlant_idPicturesByPicture_idActionType) => (plant_id: String, picture_id: String) => getplantsByPlant_idPicturesByPicture_idActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getplantsByPlant_idPicturesByPicture_idResponseMimeType =>
                val possibleWriters = Map(
                    404 -> anyToWritable[Error], 
                    200 -> anyToWritable[Null]
            ).withDefaultValue(anyToWritable[Error])
            

                val result =
                        new PlantsPlant_idPicturesPicture_idGetValidator(plant_id, picture_id).errors match {
                            case e if e.isEmpty => processValidgetplantsByPlant_idPicturesByPicture_idRequest(f)((plant_id, picture_id))(possibleWriters, getplantsByPlant_idPicturesByPicture_idResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getplantsByPlant_idPicturesByPicture_idResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetplantsByPlant_idPicturesByPicture_idRequest[T <: Any](f: getplantsByPlant_idPicturesByPicture_idActionType)(request: getplantsByPlant_idPicturesByPicture_idActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetplantsByPlant_idPicturesByPicture_id orElse defaultErrorMapping)(error)
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
    private type putplantsByPlant_idPicturesByPicture_idActionRequestType       = (String, String)
    private type putplantsByPlant_idPicturesByPicture_idActionType              = putplantsByPlant_idPicturesByPicture_idActionRequestType => Try[(Int, Any)]

    private val errorToStatusputplantsByPlant_idPicturesByPicture_id: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val putplantsByPlant_idPicturesByPicture_idActionConstructor  = Action
    def putplantsByPlant_idPicturesByPicture_idAction = (f: putplantsByPlant_idPicturesByPicture_idActionType) => (plant_id: String, picture_id: String) => putplantsByPlant_idPicturesByPicture_idActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { putplantsByPlant_idPicturesByPicture_idResponseMimeType =>
                val possibleWriters = Map(
                    404 -> anyToWritable[Null], 
                    200 -> anyToWritable[Null], 
                    201 -> anyToWritable[Null]
            ).withDefaultValue(anyToWritable[Error])
            

                val result =
                        new PlantsPlant_idPicturesPicture_idPutValidator(plant_id, picture_id).errors match {
                            case e if e.isEmpty => processValidputplantsByPlant_idPicturesByPicture_idRequest(f)((plant_id, picture_id))(possibleWriters, putplantsByPlant_idPicturesByPicture_idResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(putplantsByPlant_idPicturesByPicture_idResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidputplantsByPlant_idPicturesByPicture_idRequest[T <: Any](f: putplantsByPlant_idPicturesByPicture_idActionType)(request: putplantsByPlant_idPicturesByPicture_idActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusputplantsByPlant_idPicturesByPicture_id orElse defaultErrorMapping)(error)
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
    private type deleteplantsByPlant_idPicturesByPicture_idActionRequestType       = (String, String)
    private type deleteplantsByPlant_idPicturesByPicture_idActionType              = deleteplantsByPlant_idPicturesByPicture_idActionRequestType => Try[(Int, Any)]

    private val errorToStatusdeleteplantsByPlant_idPicturesByPicture_id: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val deleteplantsByPlant_idPicturesByPicture_idActionConstructor  = Action
    def deleteplantsByPlant_idPicturesByPicture_idAction = (f: deleteplantsByPlant_idPicturesByPicture_idActionType) => (plant_id: String, picture_id: String) => deleteplantsByPlant_idPicturesByPicture_idActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { deleteplantsByPlant_idPicturesByPicture_idResponseMimeType =>
                val possibleWriters = Map(
                    404 -> anyToWritable[Error], 
                    200 -> anyToWritable[Null]
            ).withDefaultValue(anyToWritable[Error])
            

                val result =
                        new PlantsPlant_idPicturesPicture_idDeleteValidator(plant_id, picture_id).errors match {
                            case e if e.isEmpty => processValiddeleteplantsByPlant_idPicturesByPicture_idRequest(f)((plant_id, picture_id))(possibleWriters, deleteplantsByPlant_idPicturesByPicture_idResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(deleteplantsByPlant_idPicturesByPicture_idResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValiddeleteplantsByPlant_idPicturesByPicture_idRequest[T <: Any](f: deleteplantsByPlant_idPicturesByPicture_idActionType)(request: deleteplantsByPlant_idPicturesByPicture_idActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusdeleteplantsByPlant_idPicturesByPicture_id orElse defaultErrorMapping)(error)
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
    private type getplantsByPlant_idWater_needsActionRequestType       = (String)
    private type getplantsByPlant_idWater_needsActionType              = getplantsByPlant_idWater_needsActionRequestType => Try[(Int, Any)]

    private val errorToStatusgetplantsByPlant_idWater_needs: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val getplantsByPlant_idWater_needsActionConstructor  = Action
    def getplantsByPlant_idWater_needsAction = (f: getplantsByPlant_idWater_needsActionType) => (plant_id: String) => getplantsByPlant_idWater_needsActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getplantsByPlant_idWater_needsResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[WaterNeeds]
            ).withDefaultValue(anyToWritable[Error])
            

                val result =
                        new PlantsPlant_idWater_needsGetValidator(plant_id).errors match {
                            case e if e.isEmpty => processValidgetplantsByPlant_idWater_needsRequest(f)((plant_id))(possibleWriters, getplantsByPlant_idWater_needsResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getplantsByPlant_idWater_needsResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetplantsByPlant_idWater_needsRequest[T <: Any](f: getplantsByPlant_idWater_needsActionType)(request: getplantsByPlant_idWater_needsActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusgetplantsByPlant_idWater_needs orElse defaultErrorMapping)(error)
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
    private type putplantsByPlant_idWater_needsActionRequestType       = (String, WaterNeeds)
    private type putplantsByPlant_idWater_needsActionType              = putplantsByPlant_idWater_needsActionRequestType => Try[(Int, Any)]

    private val errorToStatusputplantsByPlant_idWater_needs: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

        private def putplantsByPlant_idWater_needsParser(acceptedTypes: Seq[String], maxLength: Int = parse.DefaultMaxTextLength) = {
            def bodyMimeType: Option[MediaType] => String = mediaType => {
                val requestType = mediaType.toSeq.map {
                    case m: MediaRange => m
                    case MediaType(a,b,c) => new MediaRange(a,b,c,None,Nil)
                }
                negotiateContent(requestType, acceptedTypes).orElse(acceptedTypes.headOption).getOrElse("application/json")
            }
            
            import de.zalando.play.controllers.WrappedBodyParsers
            
            val customParsers = WrappedBodyParsers.anyParser[WaterNeeds]
            anyParser[WaterNeeds](bodyMimeType, customParsers, "Invalid WaterNeeds", maxLength)
        }

    val putplantsByPlant_idWater_needsActionConstructor  = Action
    def putplantsByPlant_idWater_needsAction = (f: putplantsByPlant_idWater_needsActionType) => (plant_id: String) => putplantsByPlant_idWater_needsActionConstructor(putplantsByPlant_idWater_needsParser(Seq[String]())) { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { putplantsByPlant_idWater_needsResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[Null], 
                    404 -> anyToWritable[Null]
            ).withDefaultValue(anyToWritable[Error])
            val water_needs = request.body
            

                val result =
                        new PlantsPlant_idWater_needsPutValidator(plant_id, water_needs).errors match {
                            case e if e.isEmpty => processValidputplantsByPlant_idWater_needsRequest(f)((plant_id, water_needs))(possibleWriters, putplantsByPlant_idWater_needsResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(putplantsByPlant_idWater_needsResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidputplantsByPlant_idWater_needsRequest[T <: Any](f: putplantsByPlant_idWater_needsActionType)(request: putplantsByPlant_idWater_needsActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusputplantsByPlant_idWater_needs orElse defaultErrorMapping)(error)
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
