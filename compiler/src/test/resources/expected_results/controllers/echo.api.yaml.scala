package echo.api.yaml
import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._

object pathsAction {
    import paths.PostName
    class EchoApiYaml extends EchoApiYamlBase {
        val get = getAction {
            
            ???
        }
        val post = postAction {
            input: (PostName, PostName) =>
            val (name, year) = input
            
            ???
        }
        }
    }
