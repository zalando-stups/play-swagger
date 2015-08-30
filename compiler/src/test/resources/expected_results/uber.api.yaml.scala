package uber.api.yaml
import scala.Option
import scala.collection.Seq
object definitions {
  case class PriceEstimate(
    // Lower bound of the estimated price.
    low_estimate: Option[Double],
    // Display name of product.
    display_name: Option[String],
    // Formatted string of estimate in local currency of the start location. Estimate could be a range, a single number (flat rate) or "Metered" for TAXI.
    estimate: Option[String],
    // Upper bound of the estimated price.
    high_estimate: Option[Double],
    // Unique identifier representing a specific product for a given latitude & longitude. For example, uberX in San Francisco will have a different product_id than uberX in Los Angeles
    product_id: Option[String],
    // [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217) currency code.
    currency_code: Option[String],
    // Expected surge multiplier. Surge is active if surge_multiplier is greater than 1. Price estimate already factors in the surge multiplier.
    surge_multiplier: Option[Double]
  )
  case class Product(
    // Image URL representing the product.
    image: Option[String],
    // Description of product.
    description: Option[String],
    // Display name of product.
    display_name: Option[String],
    // Unique identifier representing a specific product for a given latitude & longitude. For example, uberX in San Francisco will have a different product_id than uberX in Los Angeles.
    product_id: Option[String],
    // Capacity of product. For example, 4 people.
    capacity: Option[String]
  )
  case class Activity(
    // Unique identifier for the activity
    uuid: Option[String]
  )
  case class Activities(
    // Position in pagination.
    offset: Option[Int],
    // Number of items to retrieve (100 max).
    limit: Option[Int],
    // Total number of items available.
    count: Option[Int],
    history: Option[Seq[Activity]]
  )
  case class Profile(
    // First name of the Uber user.
    first_name: Option[String],
    // Email address of the Uber user
    email: Option[String],
    // Promo code of the Uber user.
    promo_code: Option[String],
    // Last name of the Uber user.
    last_name: Option[String],
    // Image URL of the Uber user.
    picture: Option[String]
  )
  case class Error(
    code: Option[Int],
    message: Option[String],
    fields: Option[String]
  )
}