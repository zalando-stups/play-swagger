package type_deduplication.yaml

import play.api.mvc.{Action, Controller, Results}
import play.api.http._
import Results.Status

import de.zalando.play.controllers.{PlayBodyParsing, ParsingError, ResultWrapper, ResponseWriters}
import PlayBodyParsing._
import scala.util._
import scala.math.BigInt

import de.zalando.play.controllers.PlayPathBindables



trait Type_deduplicationYamlBase extends Controller with PlayBodyParsing {
    sealed trait getplantsByPlant_idWateringsByWatering_idType[ResultT] extends ResultWrapper[ResultT]
    case class getplantsByPlant_idWateringsByWatering_id404(result: Error)(implicit val writer: String => Option[Writeable[Error]]) extends getplantsByPlant_idWateringsByWatering_idType[Error] { val statusCode = 404 }
    case class getplantsByPlant_idWateringsByWatering_id200(result: Watering)(implicit val writer: String => Option[Writeable[Watering]]) extends getplantsByPlant_idWateringsByWatering_idType[Watering] { val statusCode = 200 }
    

    private type getplantsByPlant_idWateringsByWatering_idActionRequestType       = (String, String)
    private type getplantsByPlant_idWateringsByWatering_idActionType[T]            = getplantsByPlant_idWateringsByWatering_idActionRequestType => getplantsByPlant_idWateringsByWatering_idType[T]


    val getplantsByPlant_idWateringsByWatering_idActionConstructor  = Action
    def getplantsByPlant_idWateringsByWatering_idAction[T] = (f: getplantsByPlant_idWateringsByWatering_idActionType[T]) => (plant_id: String, watering_id: String) => getplantsByPlant_idWateringsByWatering_idActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getplantsByPlant_idWateringsByWatering_idResponseMimeType =>

            
            

                val result =
                        new PlantsPlant_idWateringsWatering_idGetValidator(plant_id, watering_id).errors match {
                            case e if e.isEmpty => processValidgetplantsByPlant_idWateringsByWatering_idRequest(f)((plant_id, watering_id))(getplantsByPlant_idWateringsByWatering_idResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getplantsByPlant_idWateringsByWatering_idResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetplantsByPlant_idWateringsByWatering_idRequest[T](f: getplantsByPlant_idWateringsByWatering_idActionType[T])(request: getplantsByPlant_idWateringsByWatering_idActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait putplantsByPlant_idWateringsByWatering_idType[ResultT] extends ResultWrapper[ResultT]
    case class putplantsByPlant_idWateringsByWatering_id404(result: Error)(implicit val writer: String => Option[Writeable[Error]]) extends putplantsByPlant_idWateringsByWatering_idType[Error] { val statusCode = 404 }
    case class putplantsByPlant_idWateringsByWatering_id200(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends putplantsByPlant_idWateringsByWatering_idType[Null] { val statusCode = 200 }
    case class putplantsByPlant_idWateringsByWatering_id201(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends putplantsByPlant_idWateringsByWatering_idType[Null] { val statusCode = 201 }
    

    private type putplantsByPlant_idWateringsByWatering_idActionRequestType       = (String, String)
    private type putplantsByPlant_idWateringsByWatering_idActionType[T]            = putplantsByPlant_idWateringsByWatering_idActionRequestType => putplantsByPlant_idWateringsByWatering_idType[T]


    val putplantsByPlant_idWateringsByWatering_idActionConstructor  = Action
    def putplantsByPlant_idWateringsByWatering_idAction[T] = (f: putplantsByPlant_idWateringsByWatering_idActionType[T]) => (plant_id: String, watering_id: String) => putplantsByPlant_idWateringsByWatering_idActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { putplantsByPlant_idWateringsByWatering_idResponseMimeType =>

            
            

                val result =
                        new PlantsPlant_idWateringsWatering_idPutValidator(plant_id, watering_id).errors match {
                            case e if e.isEmpty => processValidputplantsByPlant_idWateringsByWatering_idRequest(f)((plant_id, watering_id))(putplantsByPlant_idWateringsByWatering_idResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(putplantsByPlant_idWateringsByWatering_idResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidputplantsByPlant_idWateringsByWatering_idRequest[T](f: putplantsByPlant_idWateringsByWatering_idActionType[T])(request: putplantsByPlant_idWateringsByWatering_idActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait getusersMeType[ResultT] extends ResultWrapper[ResultT]
    case class getusersMe200(result: User)(implicit val writer: String => Option[Writeable[User]]) extends getusersMeType[User] { val statusCode = 200 }
    

    private type getusersMeActionRequestType       = (Unit)
    private type getusersMeActionType[T]            = getusersMeActionRequestType => getusersMeType[T]


    val getusersMeActionConstructor  = Action
    def getusersMeAction[T] = (f: getusersMeActionType[T]) => getusersMeActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getusersMeResponseMimeType =>

            
            

                val result = processValidgetusersMeRequest(f)()(getusersMeResponseMimeType)
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetusersMeRequest[T](f: getusersMeActionType[T])(request: getusersMeActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait getplantsByPlant_idSunlight_needsType[ResultT] extends ResultWrapper[ResultT]
    case class getplantsByPlant_idSunlight_needs404(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends getplantsByPlant_idSunlight_needsType[Null] { val statusCode = 404 }
    case class getplantsByPlant_idSunlight_needs200(result: SunlightNeeds)(implicit val writer: String => Option[Writeable[SunlightNeeds]]) extends getplantsByPlant_idSunlight_needsType[SunlightNeeds] { val statusCode = 200 }
    

    private type getplantsByPlant_idSunlight_needsActionRequestType       = (String)
    private type getplantsByPlant_idSunlight_needsActionType[T]            = getplantsByPlant_idSunlight_needsActionRequestType => getplantsByPlant_idSunlight_needsType[T]


    val getplantsByPlant_idSunlight_needsActionConstructor  = Action
    def getplantsByPlant_idSunlight_needsAction[T] = (f: getplantsByPlant_idSunlight_needsActionType[T]) => (plant_id: String) => getplantsByPlant_idSunlight_needsActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getplantsByPlant_idSunlight_needsResponseMimeType =>

            
            

                val result =
                        new PlantsPlant_idSunlight_needsGetValidator(plant_id).errors match {
                            case e if e.isEmpty => processValidgetplantsByPlant_idSunlight_needsRequest(f)((plant_id))(getplantsByPlant_idSunlight_needsResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getplantsByPlant_idSunlight_needsResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetplantsByPlant_idSunlight_needsRequest[T](f: getplantsByPlant_idSunlight_needsActionType[T])(request: getplantsByPlant_idSunlight_needsActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait putplantsByPlant_idSunlight_needsType[ResultT] extends ResultWrapper[ResultT]
    case class putplantsByPlant_idSunlight_needs200(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends putplantsByPlant_idSunlight_needsType[Null] { val statusCode = 200 }
    case class putplantsByPlant_idSunlight_needs404(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends putplantsByPlant_idSunlight_needsType[Null] { val statusCode = 404 }
    

    private type putplantsByPlant_idSunlight_needsActionRequestType       = (String, SunlightNeeds)
    private type putplantsByPlant_idSunlight_needsActionType[T]            = putplantsByPlant_idSunlight_needsActionRequestType => putplantsByPlant_idSunlight_needsType[T]

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
    def putplantsByPlant_idSunlight_needsAction[T] = (f: putplantsByPlant_idSunlight_needsActionType[T]) => (plant_id: String) => putplantsByPlant_idSunlight_needsActionConstructor(putplantsByPlant_idSunlight_needsParser(Seq[String]())) { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { putplantsByPlant_idSunlight_needsResponseMimeType =>

            val sunlight_needs = request.body
            
            

                val result =
                        new PlantsPlant_idSunlight_needsPutValidator(plant_id, sunlight_needs).errors match {
                            case e if e.isEmpty => processValidputplantsByPlant_idSunlight_needsRequest(f)((plant_id, sunlight_needs))(putplantsByPlant_idSunlight_needsResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(putplantsByPlant_idSunlight_needsResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidputplantsByPlant_idSunlight_needsRequest[T](f: putplantsByPlant_idSunlight_needsActionType[T])(request: putplantsByPlant_idSunlight_needsActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait getusersType[ResultT] extends ResultWrapper[ResultT]
    case class getusers200(result: Seq[User])(implicit val writer: String => Option[Writeable[Seq[User]]]) extends getusersType[Seq[User]] { val statusCode = 200 }
    

    private type getusersActionRequestType       = (UsersGetLimit, UsersGetLimit)
    private type getusersActionType[T]            = getusersActionRequestType => getusersType[T]


    val getusersActionConstructor  = Action
    def getusersAction[T] = (f: getusersActionType[T]) => (limit: UsersGetLimit, offset: UsersGetLimit) => getusersActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getusersResponseMimeType =>

            
            

                val result =
                        new UsersGetValidator(limit, offset).errors match {
                            case e if e.isEmpty => processValidgetusersRequest(f)((limit, offset))(getusersResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getusersResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetusersRequest[T](f: getusersActionType[T])(request: getusersActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait postusersType[ResultT] extends ResultWrapper[ResultT]
    case class postusers200(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends postusersType[Null] { val statusCode = 200 }
    

    private type postusersActionRequestType       = (SigninData)
    private type postusersActionType[T]            = postusersActionRequestType => postusersType[T]

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
    def postusersAction[T] = (f: postusersActionType[T]) => postusersActionConstructor(postusersParser(Seq[String]())) { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { postusersResponseMimeType =>

            val signin_data = request.body
            
            

                val result =
                        new UsersPostValidator(signin_data).errors match {
                            case e if e.isEmpty => processValidpostusersRequest(f)((signin_data))(postusersResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(postusersResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidpostusersRequest[T](f: postusersActionType[T])(request: postusersActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait getareasByArea_idType[ResultT] extends ResultWrapper[ResultT]
    case class getareasByArea_id200(result: Area)(implicit val writer: String => Option[Writeable[Area]]) extends getareasByArea_idType[Area] { val statusCode = 200 }
    

    private type getareasByArea_idActionRequestType       = (String)
    private type getareasByArea_idActionType[T]            = getareasByArea_idActionRequestType => getareasByArea_idType[T]


    val getareasByArea_idActionConstructor  = Action
    def getareasByArea_idAction[T] = (f: getareasByArea_idActionType[T]) => (area_id: String) => getareasByArea_idActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getareasByArea_idResponseMimeType =>

            
            

                val result =
                        new AreasArea_idGetValidator(area_id).errors match {
                            case e if e.isEmpty => processValidgetareasByArea_idRequest(f)((area_id))(getareasByArea_idResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getareasByArea_idResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetareasByArea_idRequest[T](f: getareasByArea_idActionType[T])(request: getareasByArea_idActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait putareasByArea_idType[ResultT] extends ResultWrapper[ResultT]
    case class putareasByArea_id200(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends putareasByArea_idType[Null] { val statusCode = 200 }
    case class putareasByArea_id201(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends putareasByArea_idType[Null] { val statusCode = 201 }
    

    private type putareasByArea_idActionRequestType       = (String)
    private type putareasByArea_idActionType[T]            = putareasByArea_idActionRequestType => putareasByArea_idType[T]


    val putareasByArea_idActionConstructor  = Action
    def putareasByArea_idAction[T] = (f: putareasByArea_idActionType[T]) => (area_id: String) => putareasByArea_idActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { putareasByArea_idResponseMimeType =>

            
            

                val result =
                        new AreasArea_idPutValidator(area_id).errors match {
                            case e if e.isEmpty => processValidputareasByArea_idRequest(f)((area_id))(putareasByArea_idResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(putareasByArea_idResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidputareasByArea_idRequest[T](f: putareasByArea_idActionType[T])(request: putareasByArea_idActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait deleteareasByArea_idType[ResultT] extends ResultWrapper[ResultT]
    case class deleteareasByArea_id200(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends deleteareasByArea_idType[Null] { val statusCode = 200 }
    

    private type deleteareasByArea_idActionRequestType       = (String)
    private type deleteareasByArea_idActionType[T]            = deleteareasByArea_idActionRequestType => deleteareasByArea_idType[T]


    val deleteareasByArea_idActionConstructor  = Action
    def deleteareasByArea_idAction[T] = (f: deleteareasByArea_idActionType[T]) => (area_id: String) => deleteareasByArea_idActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { deleteareasByArea_idResponseMimeType =>

            
            

                val result =
                        new AreasArea_idDeleteValidator(area_id).errors match {
                            case e if e.isEmpty => processValiddeleteareasByArea_idRequest(f)((area_id))(deleteareasByArea_idResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(deleteareasByArea_idResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValiddeleteareasByArea_idRequest[T](f: deleteareasByArea_idActionType[T])(request: deleteareasByArea_idActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait getplantsType[ResultT] extends ResultWrapper[ResultT]
    case class getplants200(result: Seq[Plant])(implicit val writer: String => Option[Writeable[Seq[Plant]]]) extends getplantsType[Seq[Plant]] { val statusCode = 200 }
    

    private type getplantsActionRequestType       = (PlantsGetLimit, PlantsGetOffset)
    private type getplantsActionType[T]            = getplantsActionRequestType => getplantsType[T]


    val getplantsActionConstructor  = Action
    def getplantsAction[T] = (f: getplantsActionType[T]) => (limit: PlantsGetLimit, offset: PlantsGetOffset) => getplantsActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getplantsResponseMimeType =>

            
            

                val result =
                        new PlantsGetValidator(limit, offset).errors match {
                            case e if e.isEmpty => processValidgetplantsRequest(f)((limit, offset))(getplantsResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getplantsResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetplantsRequest[T](f: getplantsActionType[T])(request: getplantsActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait getuserByUser_idPlantsType[ResultT] extends ResultWrapper[ResultT]
    case class getuserByUser_idPlants404(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends getuserByUser_idPlantsType[Null] { val statusCode = 404 }
    case class getuserByUser_idPlants200(result: Seq[Plant])(implicit val writer: String => Option[Writeable[Seq[Plant]]]) extends getuserByUser_idPlantsType[Seq[Plant]] { val statusCode = 200 }
    

    private type getuserByUser_idPlantsActionRequestType       = (String, UsersGetLimit, UsersGetLimit)
    private type getuserByUser_idPlantsActionType[T]            = getuserByUser_idPlantsActionRequestType => getuserByUser_idPlantsType[T]


    val getuserByUser_idPlantsActionConstructor  = Action
    def getuserByUser_idPlantsAction[T] = (f: getuserByUser_idPlantsActionType[T]) => (user_id: String, limit: UsersGetLimit, offset: UsersGetLimit) => getuserByUser_idPlantsActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getuserByUser_idPlantsResponseMimeType =>

            
            

                val result =
                        new UserUser_idPlantsGetValidator(user_id, limit, offset).errors match {
                            case e if e.isEmpty => processValidgetuserByUser_idPlantsRequest(f)((user_id, limit, offset))(getuserByUser_idPlantsResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getuserByUser_idPlantsResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetuserByUser_idPlantsRequest[T](f: getuserByUser_idPlantsActionType[T])(request: getuserByUser_idPlantsActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait getusersByUser_idType[ResultT] extends ResultWrapper[ResultT]
    case class getusersByUser_id404(result: Error)(implicit val writer: String => Option[Writeable[Error]]) extends getusersByUser_idType[Error] { val statusCode = 404 }
    case class getusersByUser_id200(result: User)(implicit val writer: String => Option[Writeable[User]]) extends getusersByUser_idType[User] { val statusCode = 200 }
    

    private type getusersByUser_idActionRequestType       = (String)
    private type getusersByUser_idActionType[T]            = getusersByUser_idActionRequestType => getusersByUser_idType[T]


    val getusersByUser_idActionConstructor  = Action
    def getusersByUser_idAction[T] = (f: getusersByUser_idActionType[T]) => (user_id: String) => getusersByUser_idActionConstructor { request =>
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
    sealed trait putusersByUser_idType[ResultT] extends ResultWrapper[ResultT]
    case class putusersByUser_id200(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends putusersByUser_idType[Null] { val statusCode = 200 }
    case class putusersByUser_id201(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends putusersByUser_idType[Null] { val statusCode = 201 }
    

    private type putusersByUser_idActionRequestType       = (String, User)
    private type putusersByUser_idActionType[T]            = putusersByUser_idActionRequestType => putusersByUser_idType[T]

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
    def putusersByUser_idAction[T] = (f: putusersByUser_idActionType[T]) => (user_id: String) => putusersByUser_idActionConstructor(putusersByUser_idParser(Seq[String]())) { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { putusersByUser_idResponseMimeType =>

            val user = request.body
            
            

                val result =
                        new UsersUser_idPutValidator(user_id, user).errors match {
                            case e if e.isEmpty => processValidputusersByUser_idRequest(f)((user_id, user))(putusersByUser_idResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(putusersByUser_idResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidputusersByUser_idRequest[T](f: putusersByUser_idActionType[T])(request: putusersByUser_idActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait deleteusersByUser_idType[ResultT] extends ResultWrapper[ResultT]
    case class deleteusersByUser_id404(result: Error)(implicit val writer: String => Option[Writeable[Error]]) extends deleteusersByUser_idType[Error] { val statusCode = 404 }
    case class deleteusersByUser_id200(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends deleteusersByUser_idType[Null] { val statusCode = 200 }
    

    private type deleteusersByUser_idActionRequestType       = (String, User)
    private type deleteusersByUser_idActionType[T]            = deleteusersByUser_idActionRequestType => deleteusersByUser_idType[T]

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
    def deleteusersByUser_idAction[T] = (f: deleteusersByUser_idActionType[T]) => (user_id: String) => deleteusersByUser_idActionConstructor(deleteusersByUser_idParser(Seq[String]())) { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { deleteusersByUser_idResponseMimeType =>

            val user = request.body
            
            

                val result =
                        new UsersUser_idDeleteValidator(user_id, user).errors match {
                            case e if e.isEmpty => processValiddeleteusersByUser_idRequest(f)((user_id, user))(deleteusersByUser_idResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(deleteusersByUser_idResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValiddeleteusersByUser_idRequest[T](f: deleteusersByUser_idActionType[T])(request: deleteusersByUser_idActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait getareasType[ResultT] extends ResultWrapper[ResultT]
    case class getareas200(result: Seq[Area])(implicit val writer: String => Option[Writeable[Seq[Area]]]) extends getareasType[Seq[Area]] { val statusCode = 200 }
    

    private type getareasActionRequestType       = (UsersGetLimit, UsersGetLimit)
    private type getareasActionType[T]            = getareasActionRequestType => getareasType[T]


    val getareasActionConstructor  = Action
    def getareasAction[T] = (f: getareasActionType[T]) => (limit: UsersGetLimit, offset: UsersGetLimit) => getareasActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getareasResponseMimeType =>

            
            

                val result =
                        new AreasGetValidator(limit, offset).errors match {
                            case e if e.isEmpty => processValidgetareasRequest(f)((limit, offset))(getareasResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getareasResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetareasRequest[T](f: getareasActionType[T])(request: getareasActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait getplantsByPlant_idLocationType[ResultT] extends ResultWrapper[ResultT]
    case class getplantsByPlant_idLocation404(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends getplantsByPlant_idLocationType[Null] { val statusCode = 404 }
    case class getplantsByPlant_idLocation200(result: Location)(implicit val writer: String => Option[Writeable[Location]]) extends getplantsByPlant_idLocationType[Location] { val statusCode = 200 }
    

    private type getplantsByPlant_idLocationActionRequestType       = (String)
    private type getplantsByPlant_idLocationActionType[T]            = getplantsByPlant_idLocationActionRequestType => getplantsByPlant_idLocationType[T]


    val getplantsByPlant_idLocationActionConstructor  = Action
    def getplantsByPlant_idLocationAction[T] = (f: getplantsByPlant_idLocationActionType[T]) => (plant_id: String) => getplantsByPlant_idLocationActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getplantsByPlant_idLocationResponseMimeType =>

            
            

                val result =
                        new PlantsPlant_idLocationGetValidator(plant_id).errors match {
                            case e if e.isEmpty => processValidgetplantsByPlant_idLocationRequest(f)((plant_id))(getplantsByPlant_idLocationResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getplantsByPlant_idLocationResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetplantsByPlant_idLocationRequest[T](f: getplantsByPlant_idLocationActionType[T])(request: getplantsByPlant_idLocationActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait putplantsByPlant_idLocationType[ResultT] extends ResultWrapper[ResultT]
    case class putplantsByPlant_idLocation200(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends putplantsByPlant_idLocationType[Null] { val statusCode = 200 }
    case class putplantsByPlant_idLocation404(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends putplantsByPlant_idLocationType[Null] { val statusCode = 404 }
    

    private type putplantsByPlant_idLocationActionRequestType       = (String, Location)
    private type putplantsByPlant_idLocationActionType[T]            = putplantsByPlant_idLocationActionRequestType => putplantsByPlant_idLocationType[T]

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
    def putplantsByPlant_idLocationAction[T] = (f: putplantsByPlant_idLocationActionType[T]) => (plant_id: String) => putplantsByPlant_idLocationActionConstructor(putplantsByPlant_idLocationParser(Seq[String]())) { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { putplantsByPlant_idLocationResponseMimeType =>

            val location = request.body
            
            

                val result =
                        new PlantsPlant_idLocationPutValidator(plant_id, location).errors match {
                            case e if e.isEmpty => processValidputplantsByPlant_idLocationRequest(f)((plant_id, location))(putplantsByPlant_idLocationResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(putplantsByPlant_idLocationResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidputplantsByPlant_idLocationRequest[T](f: putplantsByPlant_idLocationActionType[T])(request: putplantsByPlant_idLocationActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait deleteplantsByPlant_idLocationType[ResultT] extends ResultWrapper[ResultT]
    case class deleteplantsByPlant_idLocation200(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends deleteplantsByPlant_idLocationType[Null] { val statusCode = 200 }
    case class deleteplantsByPlant_idLocation404(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends deleteplantsByPlant_idLocationType[Null] { val statusCode = 404 }
    

    private type deleteplantsByPlant_idLocationActionRequestType       = (String)
    private type deleteplantsByPlant_idLocationActionType[T]            = deleteplantsByPlant_idLocationActionRequestType => deleteplantsByPlant_idLocationType[T]


    val deleteplantsByPlant_idLocationActionConstructor  = Action
    def deleteplantsByPlant_idLocationAction[T] = (f: deleteplantsByPlant_idLocationActionType[T]) => (plant_id: String) => deleteplantsByPlant_idLocationActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { deleteplantsByPlant_idLocationResponseMimeType =>

            
            

                val result =
                        new PlantsPlant_idLocationDeleteValidator(plant_id).errors match {
                            case e if e.isEmpty => processValiddeleteplantsByPlant_idLocationRequest(f)((plant_id))(deleteplantsByPlant_idLocationResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(deleteplantsByPlant_idLocationResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValiddeleteplantsByPlant_idLocationRequest[T](f: deleteplantsByPlant_idLocationActionType[T])(request: deleteplantsByPlant_idLocationActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait getusersByUser_idPictureType[ResultT] extends ResultWrapper[ResultT]
    case class getusersByUser_idPicture404(result: Error)(implicit val writer: String => Option[Writeable[Error]]) extends getusersByUser_idPictureType[Error] { val statusCode = 404 }
    case class getusersByUser_idPicture200(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends getusersByUser_idPictureType[Null] { val statusCode = 200 }
    

    private type getusersByUser_idPictureActionRequestType       = (String)
    private type getusersByUser_idPictureActionType[T]            = getusersByUser_idPictureActionRequestType => getusersByUser_idPictureType[T]


    val getusersByUser_idPictureActionConstructor  = Action
    def getusersByUser_idPictureAction[T] = (f: getusersByUser_idPictureActionType[T]) => (user_id: String) => getusersByUser_idPictureActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getusersByUser_idPictureResponseMimeType =>

            
            

                val result =
                        new UsersUser_idPictureGetValidator(user_id).errors match {
                            case e if e.isEmpty => processValidgetusersByUser_idPictureRequest(f)((user_id))(getusersByUser_idPictureResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getusersByUser_idPictureResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetusersByUser_idPictureRequest[T](f: getusersByUser_idPictureActionType[T])(request: getusersByUser_idPictureActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait putusersByUser_idPictureType[ResultT] extends ResultWrapper[ResultT]
    case class putusersByUser_idPicture404(result: Error)(implicit val writer: String => Option[Writeable[Error]]) extends putusersByUser_idPictureType[Error] { val statusCode = 404 }
    case class putusersByUser_idPicture200(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends putusersByUser_idPictureType[Null] { val statusCode = 200 }
    case class putusersByUser_idPicture201(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends putusersByUser_idPictureType[Null] { val statusCode = 201 }
    

    private type putusersByUser_idPictureActionRequestType       = (String)
    private type putusersByUser_idPictureActionType[T]            = putusersByUser_idPictureActionRequestType => putusersByUser_idPictureType[T]


    val putusersByUser_idPictureActionConstructor  = Action
    def putusersByUser_idPictureAction[T] = (f: putusersByUser_idPictureActionType[T]) => (user_id: String) => putusersByUser_idPictureActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { putusersByUser_idPictureResponseMimeType =>

            
            

                val result =
                        new UsersUser_idPicturePutValidator(user_id).errors match {
                            case e if e.isEmpty => processValidputusersByUser_idPictureRequest(f)((user_id))(putusersByUser_idPictureResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(putusersByUser_idPictureResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidputusersByUser_idPictureRequest[T](f: putusersByUser_idPictureActionType[T])(request: putusersByUser_idPictureActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait deleteusersByUser_idPictureType[ResultT] extends ResultWrapper[ResultT]
    case class deleteusersByUser_idPicture404(result: Error)(implicit val writer: String => Option[Writeable[Error]]) extends deleteusersByUser_idPictureType[Error] { val statusCode = 404 }
    case class deleteusersByUser_idPicture200(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends deleteusersByUser_idPictureType[Null] { val statusCode = 200 }
    

    private type deleteusersByUser_idPictureActionRequestType       = (String)
    private type deleteusersByUser_idPictureActionType[T]            = deleteusersByUser_idPictureActionRequestType => deleteusersByUser_idPictureType[T]


    val deleteusersByUser_idPictureActionConstructor  = Action
    def deleteusersByUser_idPictureAction[T] = (f: deleteusersByUser_idPictureActionType[T]) => (user_id: String) => deleteusersByUser_idPictureActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { deleteusersByUser_idPictureResponseMimeType =>

            
            

                val result =
                        new UsersUser_idPictureDeleteValidator(user_id).errors match {
                            case e if e.isEmpty => processValiddeleteusersByUser_idPictureRequest(f)((user_id))(deleteusersByUser_idPictureResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(deleteusersByUser_idPictureResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValiddeleteusersByUser_idPictureRequest[T](f: deleteusersByUser_idPictureActionType[T])(request: deleteusersByUser_idPictureActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait getplantsByPlant_idPicturesType[ResultT] extends ResultWrapper[ResultT]
    case class getplantsByPlant_idPictures200(result: Seq[String])(implicit val writer: String => Option[Writeable[Seq[String]]]) extends getplantsByPlant_idPicturesType[Seq[String]] { val statusCode = 200 }
    case class getplantsByPlant_idPictures404(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends getplantsByPlant_idPicturesType[Null] { val statusCode = 404 }
    

    private type getplantsByPlant_idPicturesActionRequestType       = (String, UsersGetLimit, UsersGetLimit)
    private type getplantsByPlant_idPicturesActionType[T]            = getplantsByPlant_idPicturesActionRequestType => getplantsByPlant_idPicturesType[T]


    val getplantsByPlant_idPicturesActionConstructor  = Action
    def getplantsByPlant_idPicturesAction[T] = (f: getplantsByPlant_idPicturesActionType[T]) => (plant_id: String, limit: UsersGetLimit, offset: UsersGetLimit) => getplantsByPlant_idPicturesActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getplantsByPlant_idPicturesResponseMimeType =>

            
            

                val result =
                        new PlantsPlant_idPicturesGetValidator(plant_id, limit, offset).errors match {
                            case e if e.isEmpty => processValidgetplantsByPlant_idPicturesRequest(f)((plant_id, limit, offset))(getplantsByPlant_idPicturesResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getplantsByPlant_idPicturesResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetplantsByPlant_idPicturesRequest[T](f: getplantsByPlant_idPicturesActionType[T])(request: getplantsByPlant_idPicturesActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait getplantsByPlant_idType[ResultT] extends ResultWrapper[ResultT]
    case class getplantsByPlant_id404(result: Error)(implicit val writer: String => Option[Writeable[Error]]) extends getplantsByPlant_idType[Error] { val statusCode = 404 }
    case class getplantsByPlant_id200(result: Plant)(implicit val writer: String => Option[Writeable[Plant]]) extends getplantsByPlant_idType[Plant] { val statusCode = 200 }
    

    private type getplantsByPlant_idActionRequestType       = (String)
    private type getplantsByPlant_idActionType[T]            = getplantsByPlant_idActionRequestType => getplantsByPlant_idType[T]


    val getplantsByPlant_idActionConstructor  = Action
    def getplantsByPlant_idAction[T] = (f: getplantsByPlant_idActionType[T]) => (plant_id: String) => getplantsByPlant_idActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getplantsByPlant_idResponseMimeType =>

            
            

                val result =
                        new PlantsPlant_idGetValidator(plant_id).errors match {
                            case e if e.isEmpty => processValidgetplantsByPlant_idRequest(f)((plant_id))(getplantsByPlant_idResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getplantsByPlant_idResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetplantsByPlant_idRequest[T](f: getplantsByPlant_idActionType[T])(request: getplantsByPlant_idActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait putplantsByPlant_idType[ResultT] extends ResultWrapper[ResultT]
    case class putplantsByPlant_id200(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends putplantsByPlant_idType[Null] { val statusCode = 200 }
    case class putplantsByPlant_id201(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends putplantsByPlant_idType[Null] { val statusCode = 201 }
    case class putplantsByPlant_id404(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends putplantsByPlant_idType[Null] { val statusCode = 404 }
    

    private type putplantsByPlant_idActionRequestType       = (String, Plant)
    private type putplantsByPlant_idActionType[T]            = putplantsByPlant_idActionRequestType => putplantsByPlant_idType[T]

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
    def putplantsByPlant_idAction[T] = (f: putplantsByPlant_idActionType[T]) => (plant_id: String) => putplantsByPlant_idActionConstructor(putplantsByPlant_idParser(Seq[String]())) { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { putplantsByPlant_idResponseMimeType =>

            val plant = request.body
            
            

                val result =
                        new PlantsPlant_idPutValidator(plant_id, plant).errors match {
                            case e if e.isEmpty => processValidputplantsByPlant_idRequest(f)((plant_id, plant))(putplantsByPlant_idResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(putplantsByPlant_idResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidputplantsByPlant_idRequest[T](f: putplantsByPlant_idActionType[T])(request: putplantsByPlant_idActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait deleteplantsByPlant_idType[ResultT] extends ResultWrapper[ResultT]
    case class deleteplantsByPlant_id404(result: Error)(implicit val writer: String => Option[Writeable[Error]]) extends deleteplantsByPlant_idType[Error] { val statusCode = 404 }
    case class deleteplantsByPlant_id200(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends deleteplantsByPlant_idType[Null] { val statusCode = 200 }
    

    private type deleteplantsByPlant_idActionRequestType       = (String)
    private type deleteplantsByPlant_idActionType[T]            = deleteplantsByPlant_idActionRequestType => deleteplantsByPlant_idType[T]


    val deleteplantsByPlant_idActionConstructor  = Action
    def deleteplantsByPlant_idAction[T] = (f: deleteplantsByPlant_idActionType[T]) => (plant_id: String) => deleteplantsByPlant_idActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { deleteplantsByPlant_idResponseMimeType =>

            
            

                val result =
                        new PlantsPlant_idDeleteValidator(plant_id).errors match {
                            case e if e.isEmpty => processValiddeleteplantsByPlant_idRequest(f)((plant_id))(deleteplantsByPlant_idResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(deleteplantsByPlant_idResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValiddeleteplantsByPlant_idRequest[T](f: deleteplantsByPlant_idActionType[T])(request: deleteplantsByPlant_idActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait getplantsByPlant_idWateringsType[ResultT] extends ResultWrapper[ResultT]
    case class getplantsByPlant_idWaterings200(result: Seq[Watering])(implicit val writer: String => Option[Writeable[Seq[Watering]]]) extends getplantsByPlant_idWateringsType[Seq[Watering]] { val statusCode = 200 }
    case class getplantsByPlant_idWaterings404(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends getplantsByPlant_idWateringsType[Null] { val statusCode = 404 }
    

    private type getplantsByPlant_idWateringsActionRequestType       = (String, UsersGetLimit, UsersGetLimit)
    private type getplantsByPlant_idWateringsActionType[T]            = getplantsByPlant_idWateringsActionRequestType => getplantsByPlant_idWateringsType[T]


    val getplantsByPlant_idWateringsActionConstructor  = Action
    def getplantsByPlant_idWateringsAction[T] = (f: getplantsByPlant_idWateringsActionType[T]) => (plant_id: String, limit: UsersGetLimit, offset: UsersGetLimit) => getplantsByPlant_idWateringsActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getplantsByPlant_idWateringsResponseMimeType =>

            
            

                val result =
                        new PlantsPlant_idWateringsGetValidator(plant_id, limit, offset).errors match {
                            case e if e.isEmpty => processValidgetplantsByPlant_idWateringsRequest(f)((plant_id, limit, offset))(getplantsByPlant_idWateringsResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getplantsByPlant_idWateringsResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetplantsByPlant_idWateringsRequest[T](f: getplantsByPlant_idWateringsActionType[T])(request: getplantsByPlant_idWateringsActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait getplantsByPlant_idPicturesByPicture_idType[ResultT] extends ResultWrapper[ResultT]
    case class getplantsByPlant_idPicturesByPicture_id404(result: Error)(implicit val writer: String => Option[Writeable[Error]]) extends getplantsByPlant_idPicturesByPicture_idType[Error] { val statusCode = 404 }
    case class getplantsByPlant_idPicturesByPicture_id200(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends getplantsByPlant_idPicturesByPicture_idType[Null] { val statusCode = 200 }
    

    private type getplantsByPlant_idPicturesByPicture_idActionRequestType       = (String, String)
    private type getplantsByPlant_idPicturesByPicture_idActionType[T]            = getplantsByPlant_idPicturesByPicture_idActionRequestType => getplantsByPlant_idPicturesByPicture_idType[T]


    val getplantsByPlant_idPicturesByPicture_idActionConstructor  = Action
    def getplantsByPlant_idPicturesByPicture_idAction[T] = (f: getplantsByPlant_idPicturesByPicture_idActionType[T]) => (plant_id: String, picture_id: String) => getplantsByPlant_idPicturesByPicture_idActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getplantsByPlant_idPicturesByPicture_idResponseMimeType =>

            
            

                val result =
                        new PlantsPlant_idPicturesPicture_idGetValidator(plant_id, picture_id).errors match {
                            case e if e.isEmpty => processValidgetplantsByPlant_idPicturesByPicture_idRequest(f)((plant_id, picture_id))(getplantsByPlant_idPicturesByPicture_idResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getplantsByPlant_idPicturesByPicture_idResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetplantsByPlant_idPicturesByPicture_idRequest[T](f: getplantsByPlant_idPicturesByPicture_idActionType[T])(request: getplantsByPlant_idPicturesByPicture_idActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait putplantsByPlant_idPicturesByPicture_idType[ResultT] extends ResultWrapper[ResultT]
    case class putplantsByPlant_idPicturesByPicture_id404(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends putplantsByPlant_idPicturesByPicture_idType[Null] { val statusCode = 404 }
    case class putplantsByPlant_idPicturesByPicture_id200(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends putplantsByPlant_idPicturesByPicture_idType[Null] { val statusCode = 200 }
    case class putplantsByPlant_idPicturesByPicture_id201(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends putplantsByPlant_idPicturesByPicture_idType[Null] { val statusCode = 201 }
    

    private type putplantsByPlant_idPicturesByPicture_idActionRequestType       = (String, String)
    private type putplantsByPlant_idPicturesByPicture_idActionType[T]            = putplantsByPlant_idPicturesByPicture_idActionRequestType => putplantsByPlant_idPicturesByPicture_idType[T]


    val putplantsByPlant_idPicturesByPicture_idActionConstructor  = Action
    def putplantsByPlant_idPicturesByPicture_idAction[T] = (f: putplantsByPlant_idPicturesByPicture_idActionType[T]) => (plant_id: String, picture_id: String) => putplantsByPlant_idPicturesByPicture_idActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { putplantsByPlant_idPicturesByPicture_idResponseMimeType =>

            
            

                val result =
                        new PlantsPlant_idPicturesPicture_idPutValidator(plant_id, picture_id).errors match {
                            case e if e.isEmpty => processValidputplantsByPlant_idPicturesByPicture_idRequest(f)((plant_id, picture_id))(putplantsByPlant_idPicturesByPicture_idResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(putplantsByPlant_idPicturesByPicture_idResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidputplantsByPlant_idPicturesByPicture_idRequest[T](f: putplantsByPlant_idPicturesByPicture_idActionType[T])(request: putplantsByPlant_idPicturesByPicture_idActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait deleteplantsByPlant_idPicturesByPicture_idType[ResultT] extends ResultWrapper[ResultT]
    case class deleteplantsByPlant_idPicturesByPicture_id404(result: Error)(implicit val writer: String => Option[Writeable[Error]]) extends deleteplantsByPlant_idPicturesByPicture_idType[Error] { val statusCode = 404 }
    case class deleteplantsByPlant_idPicturesByPicture_id200(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends deleteplantsByPlant_idPicturesByPicture_idType[Null] { val statusCode = 200 }
    

    private type deleteplantsByPlant_idPicturesByPicture_idActionRequestType       = (String, String)
    private type deleteplantsByPlant_idPicturesByPicture_idActionType[T]            = deleteplantsByPlant_idPicturesByPicture_idActionRequestType => deleteplantsByPlant_idPicturesByPicture_idType[T]


    val deleteplantsByPlant_idPicturesByPicture_idActionConstructor  = Action
    def deleteplantsByPlant_idPicturesByPicture_idAction[T] = (f: deleteplantsByPlant_idPicturesByPicture_idActionType[T]) => (plant_id: String, picture_id: String) => deleteplantsByPlant_idPicturesByPicture_idActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { deleteplantsByPlant_idPicturesByPicture_idResponseMimeType =>

            
            

                val result =
                        new PlantsPlant_idPicturesPicture_idDeleteValidator(plant_id, picture_id).errors match {
                            case e if e.isEmpty => processValiddeleteplantsByPlant_idPicturesByPicture_idRequest(f)((plant_id, picture_id))(deleteplantsByPlant_idPicturesByPicture_idResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(deleteplantsByPlant_idPicturesByPicture_idResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValiddeleteplantsByPlant_idPicturesByPicture_idRequest[T](f: deleteplantsByPlant_idPicturesByPicture_idActionType[T])(request: deleteplantsByPlant_idPicturesByPicture_idActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait getplantsByPlant_idWater_needsType[ResultT] extends ResultWrapper[ResultT]
    case class getplantsByPlant_idWater_needs200(result: WaterNeeds)(implicit val writer: String => Option[Writeable[WaterNeeds]]) extends getplantsByPlant_idWater_needsType[WaterNeeds] { val statusCode = 200 }
    

    private type getplantsByPlant_idWater_needsActionRequestType       = (String)
    private type getplantsByPlant_idWater_needsActionType[T]            = getplantsByPlant_idWater_needsActionRequestType => getplantsByPlant_idWater_needsType[T]


    val getplantsByPlant_idWater_needsActionConstructor  = Action
    def getplantsByPlant_idWater_needsAction[T] = (f: getplantsByPlant_idWater_needsActionType[T]) => (plant_id: String) => getplantsByPlant_idWater_needsActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { getplantsByPlant_idWater_needsResponseMimeType =>

            
            

                val result =
                        new PlantsPlant_idWater_needsGetValidator(plant_id).errors match {
                            case e if e.isEmpty => processValidgetplantsByPlant_idWater_needsRequest(f)((plant_id))(getplantsByPlant_idWater_needsResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(getplantsByPlant_idWater_needsResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidgetplantsByPlant_idWater_needsRequest[T](f: getplantsByPlant_idWater_needsActionType[T])(request: getplantsByPlant_idWater_needsActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait putplantsByPlant_idWater_needsType[ResultT] extends ResultWrapper[ResultT]
    case class putplantsByPlant_idWater_needs200(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends putplantsByPlant_idWater_needsType[Null] { val statusCode = 200 }
    case class putplantsByPlant_idWater_needs404(result: Null)(implicit val writer: String => Option[Writeable[Null]]) extends putplantsByPlant_idWater_needsType[Null] { val statusCode = 404 }
    

    private type putplantsByPlant_idWater_needsActionRequestType       = (String, WaterNeeds)
    private type putplantsByPlant_idWater_needsActionType[T]            = putplantsByPlant_idWater_needsActionRequestType => putplantsByPlant_idWater_needsType[T]

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
    def putplantsByPlant_idWater_needsAction[T] = (f: putplantsByPlant_idWater_needsActionType[T]) => (plant_id: String) => putplantsByPlant_idWater_needsActionConstructor(putplantsByPlant_idWater_needsParser(Seq[String]())) { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { putplantsByPlant_idWater_needsResponseMimeType =>

            val water_needs = request.body
            
            

                val result =
                        new PlantsPlant_idWater_needsPutValidator(plant_id, water_needs).errors match {
                            case e if e.isEmpty => processValidputplantsByPlant_idWater_needsRequest(f)((plant_id, water_needs))(putplantsByPlant_idWater_needsResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(putplantsByPlant_idWater_needsResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidputplantsByPlant_idWater_needsRequest[T](f: putplantsByPlant_idWater_needsActionType[T])(request: putplantsByPlant_idWater_needsActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    case object EmptyReturn extends ResultWrapper[Results.EmptyContent]                                                                                                  { val statusCode = 204; val result = Results.EmptyContent(); val writer = (x: String) => Some(new DefaultWriteables{}.writeableOf_EmptyContent); override def toResult(mimeType: String): Option[play.api.mvc.Result] = Some(Results.NoContent) }
    case object NotImplementedYet extends ResultWrapper[Results.EmptyContent]  with getplantsByPlant_idWateringsByWatering_idType[Results.EmptyContent] with putplantsByPlant_idWateringsByWatering_idType[Results.EmptyContent] with getusersMeType[Results.EmptyContent] with getplantsByPlant_idSunlight_needsType[Results.EmptyContent] with putplantsByPlant_idSunlight_needsType[Results.EmptyContent] with getusersType[Results.EmptyContent] with postusersType[Results.EmptyContent] with getareasByArea_idType[Results.EmptyContent] with putareasByArea_idType[Results.EmptyContent] with deleteareasByArea_idType[Results.EmptyContent] with getplantsType[Results.EmptyContent] with getuserByUser_idPlantsType[Results.EmptyContent] with getusersByUser_idType[Results.EmptyContent] with putusersByUser_idType[Results.EmptyContent] with deleteusersByUser_idType[Results.EmptyContent] with getareasType[Results.EmptyContent] with getplantsByPlant_idLocationType[Results.EmptyContent] with putplantsByPlant_idLocationType[Results.EmptyContent] with deleteplantsByPlant_idLocationType[Results.EmptyContent] with getusersByUser_idPictureType[Results.EmptyContent] with putusersByUser_idPictureType[Results.EmptyContent] with deleteusersByUser_idPictureType[Results.EmptyContent] with getplantsByPlant_idPicturesType[Results.EmptyContent] with getplantsByPlant_idType[Results.EmptyContent] with putplantsByPlant_idType[Results.EmptyContent] with deleteplantsByPlant_idType[Results.EmptyContent] with getplantsByPlant_idWateringsType[Results.EmptyContent] with getplantsByPlant_idPicturesByPicture_idType[Results.EmptyContent] with putplantsByPlant_idPicturesByPicture_idType[Results.EmptyContent] with deleteplantsByPlant_idPicturesByPicture_idType[Results.EmptyContent] with getplantsByPlant_idWater_needsType[Results.EmptyContent] with putplantsByPlant_idWater_needsType[Results.EmptyContent] { val statusCode = 501; val result = Results.EmptyContent(); val writer = (x: String) => Some(new DefaultWriteables{}.writeableOf_EmptyContent); override def toResult(mimeType: String): Option[play.api.mvc.Result] = Some(Results.NotImplemented) }
}
