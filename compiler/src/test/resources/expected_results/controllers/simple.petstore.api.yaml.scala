package simple.petstore.api.yaml
import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._

object definitionsAction {
    import definitions._
    }
object pathsAction {
    import definitions._
    import paths._
    import definitionsAction._
    class SimplePetstoreApiYaml extends SimplePetstoreApiYamlBase {
        val addPet = addPetAction {
            input: (NewPet) =>
            val (pet) = input
            ???
        }
        }
    class Dashboard extends DashboardBase {
        val methodLevel = methodLevelAction {
            input: (PetsGetTags, PetsGetLimit) =>
            val (tags, limit) = input
            ???
        }
        val pathLevel = pathLevelAction {
            input: (Long) =>
            val (id) = input
            ???
        }
        val pathLevel = pathLevelAction {
            input: (Long) =>
            val (id) = input
            ???
        }
        }
    }
