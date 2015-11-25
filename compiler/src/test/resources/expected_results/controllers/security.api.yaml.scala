package security.api.yaml
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
    class SecurityApiYaml extends SecurityApiYamlBase {
        val getPetsById = getPetsByIdAction {
            (id: PetTag) =>
            ???
        }
        }
    }
