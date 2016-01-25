import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._
import scala.util._
package echo.api.yaml { 
  class EchoApiYaml extends EchoApiYamlBase {
    val get = getAction { _ =>
      Failure(???)
    } //////// EOF ////////  getAction
    val post = postAction { input: (PostName, PostName) =>
      val (name, year) = input
      Failure(???)
    } //////// EOF ////////  postAction
    val gettest_pathById = gettest_pathByIdAction { (id: String) =>
      Failure(???)
    } //////// EOF ////////  gettest_pathByIdAction
  }
}
