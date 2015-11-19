package uber.api.yaml
object definitions {
    type ProfileLast_name = Option[String]
    type ActivitiesHistory = Option[ActivitiesHistoryOpt]
    type PriceEstimateLow_estimate = Option[Double]
    type ActivitiesHistoryOpt = scala.collection.Seq[Activity]
    type ActivitiesLimit = Option[Int]
    case class Activity(uuid: ProfileLast_name) 
    case class PriceEstimate(low_estimate: PriceEstimateLow_estimate, display_name: ProfileLast_name, estimate: ProfileLast_name, high_estimate: PriceEstimateLow_estimate, product_id: ProfileLast_name, currency_code: ProfileLast_name, surge_multiplier: PriceEstimateLow_estimate) 
    case class Product(image: ProfileLast_name, description: ProfileLast_name, display_name: ProfileLast_name, product_id: ProfileLast_name, capacity: ProfileLast_name) 
    case class Profile(first_name: ProfileLast_name, email: ProfileLast_name, promo_code: ProfileLast_name, last_name: ProfileLast_name, picture: ProfileLast_name) 
    case class Activities(offset: ActivitiesLimit, limit: ActivitiesLimit, count: ActivitiesLimit, history: ActivitiesHistory) 
    case class Error(code: ActivitiesLimit, message: ProfileLast_name, fields: ProfileLast_name) 
    }
object paths {
    import definitions._
    type ProductsGetResponses200Opt = scala.collection.Seq[Product]
    type EstimatesPriceGetResponses200Opt = scala.collection.Seq[PriceEstimate]
    type HistoryGetResponsesDefault = Option[Error]
    type HistoryGetResponses200 = Option[Activities]
    type ProductsGetResponses200 = Option[ProductsGetResponses200Opt]
    type MeGetResponses200 = Option[Profile]
    type EstimatesPriceGetResponses200 = Option[EstimatesPriceGetResponses200Opt]
    }
