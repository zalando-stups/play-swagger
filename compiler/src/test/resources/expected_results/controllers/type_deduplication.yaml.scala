
import play.api.mvc.{Action, Controller}

import play.api.data.validation.Constraint

import de.zalando.play.controllers._

import PlayBodyParsing._

import PlayValidations._

import scala.util._



package type_deduplication.yaml {

    class Type_deduplicationYaml extends Type_deduplicationYamlBase {
        val getplantsByPlant_idWateringsByWatering_id = getplantsByPlant_idWateringsByWatering_idAction { input: (String, String) =>
            val (plant_id, watering_id) = input
            

            Failure(???)

            

        } //////// EOF ////////  getplantsByPlant_idWateringsByWatering_idAction
        val putplantsByPlant_idWateringsByWatering_id = putplantsByPlant_idWateringsByWatering_idAction { input: (String, String) =>
            val (plant_id, watering_id) = input
            

            Failure(???)

            

        } //////// EOF ////////  putplantsByPlant_idWateringsByWatering_idAction
        val getusersMe = getusersMeAction { _ =>
            
            

            Failure(???)

            

        } //////// EOF ////////  getusersMeAction
        val getplantsByPlant_idSunlight_needs = getplantsByPlant_idSunlight_needsAction { (plant_id: String) =>
            
            

            Failure(???)

            

        } //////// EOF ////////  getplantsByPlant_idSunlight_needsAction
        val putplantsByPlant_idSunlight_needs = putplantsByPlant_idSunlight_needsAction { input: (String, SunlightNeeds) =>
            val (plant_id, sunlight_needs) = input
            

            Failure(???)

            

        } //////// EOF ////////  putplantsByPlant_idSunlight_needsAction
        val getusers = getusersAction { input: (ErrorCode, ErrorCode) =>
            val (limit, offset) = input
            

            Failure(???)

            

        } //////// EOF ////////  getusersAction
        val postusers = postusersAction { (signin_data: SigninData) =>
            
            

            Failure(???)

            

        } //////// EOF ////////  postusersAction
        val getareasByArea_id = getareasByArea_idAction { (area_id: String) =>
            
            

            Failure(???)

            

        } //////// EOF ////////  getareasByArea_idAction
        val putareasByArea_id = putareasByArea_idAction { (area_id: String) =>
            
            

            Failure(???)

            

        } //////// EOF ////////  putareasByArea_idAction
        val deleteareasByArea_id = deleteareasByArea_idAction { (area_id: String) =>
            
            

            Failure(???)

            

        } //////// EOF ////////  deleteareasByArea_idAction
        val getplants = getplantsAction { input: (PlantsGetLimit, PlantsGetOffset) =>
            val (limit, offset) = input
            

            Failure(???)

            

        } //////// EOF ////////  getplantsAction
        val getuserByUser_idPlants = getuserByUser_idPlantsAction { input: (String, ErrorCode, ErrorCode) =>
            val (user_id, limit, offset) = input
            

            Failure(???)

            

        } //////// EOF ////////  getuserByUser_idPlantsAction
        val getusersByUser_id = getusersByUser_idAction { (user_id: String) =>
            
            

            Failure(???)

            

        } //////// EOF ////////  getusersByUser_idAction
        val putusersByUser_id = putusersByUser_idAction { input: (String, User) =>
            val (user_id, user) = input
            

            Failure(???)

            

        } //////// EOF ////////  putusersByUser_idAction
        val deleteusersByUser_id = deleteusersByUser_idAction { input: (String, User) =>
            val (user_id, user) = input
            

            Failure(???)

            

        } //////// EOF ////////  deleteusersByUser_idAction
        val getareas = getareasAction { input: (ErrorCode, ErrorCode) =>
            val (limit, offset) = input
            

            Failure(???)

            

        } //////// EOF ////////  getareasAction
        val getplantsByPlant_idLocation = getplantsByPlant_idLocationAction { (plant_id: String) =>
            
            

            Failure(???)

            

        } //////// EOF ////////  getplantsByPlant_idLocationAction
        val putplantsByPlant_idLocation = putplantsByPlant_idLocationAction { input: (String, Location) =>
            val (plant_id, location) = input
            

            Failure(???)

            

        } //////// EOF ////////  putplantsByPlant_idLocationAction
        val deleteplantsByPlant_idLocation = deleteplantsByPlant_idLocationAction { (plant_id: String) =>
            
            

            Failure(???)

            

        } //////// EOF ////////  deleteplantsByPlant_idLocationAction
        val getusersByUser_idPicture = getusersByUser_idPictureAction { (user_id: String) =>
            
            

            Failure(???)

            

        } //////// EOF ////////  getusersByUser_idPictureAction
        val putusersByUser_idPicture = putusersByUser_idPictureAction { (user_id: String) =>
            
            

            Failure(???)

            

        } //////// EOF ////////  putusersByUser_idPictureAction
        val deleteusersByUser_idPicture = deleteusersByUser_idPictureAction { (user_id: String) =>
            
            

            Failure(???)

            

        } //////// EOF ////////  deleteusersByUser_idPictureAction
        val getplantsByPlant_idPictures = getplantsByPlant_idPicturesAction { input: (String, ErrorCode, ErrorCode) =>
            val (plant_id, limit, offset) = input
            

            Failure(???)

            

        } //////// EOF ////////  getplantsByPlant_idPicturesAction
        val getplantsByPlant_id = getplantsByPlant_idAction { (plant_id: String) =>
            
            

            Failure(???)

            

        } //////// EOF ////////  getplantsByPlant_idAction
        val putplantsByPlant_id = putplantsByPlant_idAction { input: (String, Plant) =>
            val (plant_id, plant) = input
            

            Failure(???)

            

        } //////// EOF ////////  putplantsByPlant_idAction
        val deleteplantsByPlant_id = deleteplantsByPlant_idAction { (plant_id: String) =>
            
            

            Failure(???)

            

        } //////// EOF ////////  deleteplantsByPlant_idAction
        val getplantsByPlant_idWaterings = getplantsByPlant_idWateringsAction { input: (String, ErrorCode, ErrorCode) =>
            val (plant_id, limit, offset) = input
            

            Failure(???)

            

        } //////// EOF ////////  getplantsByPlant_idWateringsAction
        val getplantsByPlant_idPicturesByPicture_id = getplantsByPlant_idPicturesByPicture_idAction { input: (String, String) =>
            val (plant_id, picture_id) = input
            

            Failure(???)

            

        } //////// EOF ////////  getplantsByPlant_idPicturesByPicture_idAction
        val putplantsByPlant_idPicturesByPicture_id = putplantsByPlant_idPicturesByPicture_idAction { input: (String, String) =>
            val (plant_id, picture_id) = input
            

            Failure(???)

            

        } //////// EOF ////////  putplantsByPlant_idPicturesByPicture_idAction
        val deleteplantsByPlant_idPicturesByPicture_id = deleteplantsByPlant_idPicturesByPicture_idAction { input: (String, String) =>
            val (plant_id, picture_id) = input
            

            Failure(???)

            

        } //////// EOF ////////  deleteplantsByPlant_idPicturesByPicture_idAction
        val getplantsByPlant_idWater_needs = getplantsByPlant_idWater_needsAction { (plant_id: String) =>
            
            

            Failure(???)

            

        } //////// EOF ////////  getplantsByPlant_idWater_needsAction
        val putplantsByPlant_idWater_needs = putplantsByPlant_idWater_needsAction { input: (String, WaterNeeds) =>
            val (plant_id, water_needs) = input
            

            Failure(???)

            

        } //////// EOF ////////  putplantsByPlant_idWater_needsAction
    }
}
