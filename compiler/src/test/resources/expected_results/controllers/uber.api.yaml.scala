
import play.api.mvc.{Action, Controller}

import play.api.data.validation.Constraint

import de.zalando.play.controllers._

import PlayBodyParsing._

import PlayValidations._

import scala.util._

import de.zalando.play.controllers.ArrayWrapper


package uber.api.yaml {

    class UberApiYaml extends UberApiYamlBase {
        val getme = getmeAction { _ =>
            
            

            Failure(???)

            

        } //////// EOF ////////  getmeAction
        val getproducts = getproductsAction { input: (Double, Double) =>
            val (latitude, longitude) = input
            

            Failure(???)

            

        } //////// EOF ////////  getproductsAction
        val getestimatesTime = getestimatesTimeAction { input: (Double, Double, ProfilePicture, ProfilePicture) =>
            val (start_latitude, start_longitude, customer_uuid, product_id) = input
            

            Failure(???)

            

        } //////// EOF ////////  getestimatesTimeAction
        val getestimatesPrice = getestimatesPriceAction { input: (Double, Double, Double, Double) =>
            val (start_latitude, start_longitude, end_latitude, end_longitude) = input
            

            Failure(???)

            

        } //////// EOF ////////  getestimatesPriceAction
        val gethistory = gethistoryAction { input: (ErrorCode, ErrorCode) =>
            val (offset, limit) = input
            

            Failure(???)

            

        } //////// EOF ////////  gethistoryAction
    }
}
