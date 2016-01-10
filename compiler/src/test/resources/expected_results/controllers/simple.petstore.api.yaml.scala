



import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._
package simple.petstore.api.yaml { 

    class SimplePetstoreApiYaml extends SimplePetstoreApiYamlBase {
    val addPet = addPetAction {
            (pet: NewPet) =>
???
            
        } //////// EOF ////////  addPetAction
    }
}
package simple.petstore.api.yaml { 

    class Dashboard extends DashboardBase {
    val methodLevel = methodLevelAction {
            input: (PetsGetTags, PetsGetLimit) =>
            val (tags, limit) = input

            ???
            
        } //////// EOF ////////  methodLevelAction
    val pathLevelGet = pathLevelGetAction {
            (id: Long) =>
???
            
        } //////// EOF ////////  pathLevelGetAction
    val pathLevelDelete = pathLevelDeleteAction {
            (id: Long) =>
???
            
        } //////// EOF ////////  pathLevelDeleteAction
    }
}
