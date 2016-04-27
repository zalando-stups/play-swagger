
import play.api.mvc.{Action, Controller}

import play.api.data.validation.Constraint

import de.zalando.play.controllers._

import PlayBodyParsing._

import PlayValidations._

import scala.util._



package simple.petstore.api.yaml {

    class SimplePetstoreApiYaml extends SimplePetstoreApiYamlBase {
        val addPet = addPetAction { (pet: NewPet) =>
            

            // Response: Success((200, Pet))
            Failure(???)

            

        } //////// EOF ////////  addPetAction
    }
}
package simple.petstore.api.yaml {

    class Dashboard extends DashboardBase {
        val methodLevel = methodLevelAction { input: (PetsGetTags, PetsGetLimit) =>
            val (tags, limit) = input
            

            // Response: Success((200, Seq[Pet]))
            Failure(???)

            

        } //////// EOF ////////  methodLevelAction
        val pathLevelGet = pathLevelGetAction { (id: Long) =>
            

            // Response: Success((200, Pet))
            Failure(???)

            

        } //////// EOF ////////  pathLevelGetAction
        val pathLevelDelete = pathLevelDeleteAction { (id: Long) =>
            

            // Response: Success((204, Null))
            Failure(???)

            

        } //////// EOF ////////  pathLevelDeleteAction
    }
}
