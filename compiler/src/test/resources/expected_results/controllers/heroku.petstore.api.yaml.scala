package heroku.petstore.api.yaml
import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._

object definitionsAction {
    import definitions._
    }
object pathsAction {
    import definitions.Pet
    import definitions.PetBirthday
    import paths._
    import definitionsAction.PetAction
    import definitionsAction.PetBirthdayAction
    class HerokuPetstoreApiYaml extends HerokuPetstoreApiYamlBase {
        val get = getAction {
            input: (PetBirthday) =>
            val (limit) = input
            ???
        }
        val put = putAction {
            input: (Pet) =>
            val (pet) = input
            ???
        }
        val post = postAction {
            input: (Pet) =>
            val (pet) = input
            ???
        }
        val getByPetId = getByPetIdAction {
            input: (String) =>
            val (petId) = input
            ???
        }
        }
    }
