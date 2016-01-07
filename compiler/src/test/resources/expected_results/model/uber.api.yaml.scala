package uber.api
package object yaml {
import de.zalando.play.controllers.ArrayWrapper
import de.zalando.play.controllers.PlayPathBindables
type EstimatesPriceGetEnd_latitude = Double

    type ProductDescription = Option[String]

    type ActivitiesHistory = Option[ActivitiesHistoryOptNameClash]

    type ActivitiesHistoryNameClash = Option[ActivitiesHistoryOpt]

    type PriceEstimateLow_estimate = Option[Double]

    type ProductsGetResponses200 = Seq[Product]

    type ActivitiesHistoryOpt = Seq[Activity]

    type EstimatesPriceGetResponses200 = Seq[PriceEstimate]

    type ActivitiesHistoryOptNameClash = ArrayWrapper[Activity]

    type ActivitiesLimit = Option[Int]

    case class Activity(uuid: ProductDescription) 

    case class PriceEstimate(low_estimate: PriceEstimateLow_estimate, display_name: ProductDescription, estimate: ProductDescription, high_estimate: PriceEstimateLow_estimate, product_id: ProductDescription, currency_code: ProductDescription, surge_multiplier: PriceEstimateLow_estimate) 

    case class Product(image: ProductDescription, description: ProductDescription, display_name: ProductDescription, product_id: ProductDescription, capacity: ProductDescription) 

    case class HistoryGetResponses200(offset: ActivitiesLimit, limit: ActivitiesLimit, count: ActivitiesLimit, history: ActivitiesHistoryNameClash) 

    case class Profile(first_name: ProductDescription, email: ProductDescription, promo_code: ProductDescription, last_name: ProductDescription, picture: ProductDescription) 

    case class Activities(offset: ActivitiesLimit, limit: ActivitiesLimit, count: ActivitiesLimit, history: ActivitiesHistory) 

    case class Error(code: ActivitiesLimit, message: ProductDescription, fields: ProductDescription) 

    


    
    
    implicit val bindable_OptionIntQuery = PlayPathBindables.createOptionQueryBindable[Int]
    implicit val bindable_OptionStringQuery = PlayPathBindables.createOptionQueryBindable[String]
    }
