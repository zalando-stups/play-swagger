



import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._

package echo.yaml {

import scala.util.Success


class EchoYaml extends EchoYamlBase {
        val get = getAction {
            _ => Success(Some(""))
        }
        val post = postAction {
            input: (PostName, PostName) =>
            val (name, year) = input
            Success(Some(PostResponses200Opt(name, year)))
        }
        val getTest_pathById = getTest_pathByIdAction {
            (id: String) =>
            Success(Some(id))
        }
        }
}

