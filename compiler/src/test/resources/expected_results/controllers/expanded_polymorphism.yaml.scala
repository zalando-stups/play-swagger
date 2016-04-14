
import play.api.mvc.{Action, Controller}

import play.api.data.validation.Constraint

import de.zalando.play.controllers._

import PlayBodyParsing._

import PlayValidations._

import scala.util._



package expanded {

    class Expanded_polymorphismYaml extends Expanded_polymorphismYamlBase {
        val findPets = findPetsAction { input: (PetsGetTags, PetsGetLimit) =>
            val (tags, limit) = input
            
            // Response: Success((200, Seq[Pet]))
            

            Failure(???)

            

        } //////// EOF ////////  findPetsAction
        val addPet = addPetAction { (pet: NewPet) =>
            
            // Response: Success((200, Pet))
            

            Failure(???)

            

        } //////// EOF ////////  addPetAction
        val deletePet = deletePetAction { (id: Long) =>
            
            // Response: Success((204, Null))
            

            Failure(???)

            

        } //////// EOF ////////  deletePetAction
    }
}
