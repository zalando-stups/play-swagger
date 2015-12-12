package expanded_polymorphism.yaml
import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._

import pathsBase._

import pathsBase._

object expanded {

import definitions.{Error, NewPet, Pet}
import paths._
import pathsValidator._
class Expanded_polymorphismYaml extends Expanded_polymorphismYamlBase {
        val findPets = findPetsAction {
            input: (PetsGetTags, PetsGetLimit) =>
            val (tags, limit) = input
            
            ???
        }
        val addPet = addPetAction {
            (pet: NewPet) =>
            ???
        }
        val findPetById = findPetByIdAction {
            (id: Long) =>
            ???
        }
        val deletePet = deletePetAction {
            (id: Long) =>
            ???
        }
        }
}

