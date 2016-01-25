



import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._
import scala.util._
package minimal.api.yaml { 

  class Dashboard extends DashboardBase {
    val index = indexAction { _ =>
      Failure(???)
    } //////// EOF ////////  indexAction
  }
}
