
import play.api.mvc.{Action, Controller}

import play.api.data.validation.Constraint

import de.zalando.play.controllers._

import PlayBodyParsing._

import PlayValidations._

import scala.util._

import de.zalando.play.controllers.ArrayWrapper


package simple.petstore.api.yaml {

    class SimplePetstoreApiYaml extends SimplePetstoreApiYamlBase {
        val addPet = addPetAction { (pet: NewPet) =>
            
            

            Failure(???)

            

        } //////// EOF ////////  addPetAction
    }
}
package simple.petstore.api.yaml {

    class Dashboard extends DashboardBase {
        val methodLevel = methodLevelAction { input: (PetsGetTags, PetsGetLimit) =>
            val (tags, limit) = input
            

            Failure(???)

            

        } //////// EOF ////////  methodLevelAction
        val pathLevelGet = pathLevelGetAction { (id: Long) =>
            
            

            Failure(???)

            

        } //////// EOF ////////  pathLevelGetAction
        val pathLevelDelete = pathLevelDeleteAction { (id: Long) =>
            
            

            Failure(???)

            

        } //////// EOF ////////  pathLevelDeleteAction
    }
}
