package heroku.petstore.api.yaml
import play.api.mvc.{Action, Controller}
import de.zalando.play.controllers.PlayBodyParsing
import PlayBodyParsing._
import scala.Option
/**
 */
  import definitions.Pet
  package controllers {
   class ValidationForApiYamlallPets(limit: Option[Int]) {
     val result = Right((limit))
    }
   class ValidationForApiYamlupdatePet(pet: Pet) {
     val result = Right((pet))
    }
   class ValidationForApiYamlcreatePet(pet: Pet) {
     val result = Right((pet))
    }
   class ValidationForApiYamlgetPet(petId: String) {
     val result = Right((petId))
    }
  }