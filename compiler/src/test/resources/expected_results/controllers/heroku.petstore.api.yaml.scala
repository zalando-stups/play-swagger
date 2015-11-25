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
            (limit: PetBirthday) =>
            ???
        }
        val put = putAction {
            (pet: Pet) =>
            ???
        }
        val post = postAction {
            (pet: Pet) =>
            ???
        }
        val getByPetId = getByPetIdAction {
            (petId: String) =>
            ???
        }
        }
    }
