



import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._
package uber.api.yaml { 

    class UberApiYaml extends UberApiYamlBase {
    val getme = getmeAction {
            ???
            
        } //////// EOF ////////  getmeAction
    val getproducts = getproductsAction {
            input: (Double, Double) =>
            val (latitude, longitude) = input

            ???
            
        } //////// EOF ////////  getproductsAction
    val getestimatesTime = getestimatesTimeAction {
            input: (Double, Double, ProductDescription, ProductDescription) =>
            val (start_latitude, start_longitude, customer_uuid, product_id) = input

            ???
            
        } //////// EOF ////////  getestimatesTimeAction
    val getestimatesPrice = getestimatesPriceAction {
            input: (Double, Double, Double, Double) =>
            val (start_latitude, start_longitude, end_latitude, end_longitude) = input

            ???
            
        } //////// EOF ////////  getestimatesPriceAction
    val gethistory = gethistoryAction {
            input: (ActivitiesLimit, ActivitiesLimit) =>
            val (offset, limit) = input

            ???
            
        } //////// EOF ////////  gethistoryAction
    }
}
