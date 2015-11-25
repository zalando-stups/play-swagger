package uber.api.yaml
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
    class UberApiYaml extends UberApiYamlBase {
        val getMe = getMeAction {
            
            ???
        }
        val getProducts = getProductsAction {
            input: (Double, Double) =>
            val (latitude, longitude) = input
            
            ???
        }
        val getEstimatesTime = getEstimatesTimeAction {
            input: (Double, Double, ProfileLast_name, ProfileLast_name) =>
            val (start_latitude, start_longitude, customer_uuid, product_id) = input
            
            ???
        }
        val getEstimatesPrice = getEstimatesPriceAction {
            input: (Double, Double, Double, Double) =>
            val (start_latitude, start_longitude, end_latitude, end_longitude) = input
            
            ???
        }
        val getHistory = getHistoryAction {
            input: (ActivitiesLimit, ActivitiesLimit) =>
            val (offset, limit) = input
            
            ???
        }
        }
    }
