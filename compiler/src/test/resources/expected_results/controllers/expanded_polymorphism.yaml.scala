



import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._
import scala.util._
package expanded_polymorphism.yaml { 

  class Expanded_polymorphismYaml extends Expanded_polymorphismYamlBase {
    val findPets = findPetsAction { input: (PetsGetTags, PetsGetLimit) =>
      val (tags, limit) = input
      ???
    } //////// EOF ////////  findPetsAction
    val addPet = addPetAction { (pet: NewPet) =>
      ???
    } //////// EOF ////////  addPetAction
    val findPetById = findPetByIdAction { (id: Long) =>
      ???
    } //////// EOF ////////  findPetByIdAction
    val deletePet = deletePetAction { (id: Long) =>
      ???
    } //////// EOF ////////  deletePetAction
  }
}
