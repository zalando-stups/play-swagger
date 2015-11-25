package minimal.api.yaml
import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._

object pathsAction {
    import paths.GetResponses200
    class Dashboard extends DashboardBase {
        val index = indexAction {
            ???
        }
        }
    }
