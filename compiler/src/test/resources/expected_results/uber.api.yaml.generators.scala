package uber.api.yaml
import org.scalacheck.Arbitrary._
import org.scalacheck.Gen
import scala.Option
import scala.collection.Seq
object generatorDefinitions {
  import definitions.Error
  import definitions.PriceEstimate
  import definitions.Activity
  import definitions.Profile
  import definitions.Activities
  import definitions.Product
  def genActivities = _generate(ActivitiesGenerator)
  def genPriceEstimate = _generate(PriceEstimateGenerator)
  def genActivity = _generate(ActivityGenerator)
  def genProduct = _generate(ProductGenerator)
  def genProfile = _generate(ProfileGenerator)
  def genError = _generate(ErrorGenerator)
  // test data generator for /definitions/Error
  val ErrorGenerator =
    for {
      code <- Gen.option(arbitrary[Int])
      message <- Gen.option(arbitrary[String])
      fields <- Gen.option(arbitrary[String])
    } yield Error(code, message, fields)
  // test data generator for /definitions/PriceEstimate
  val PriceEstimateGenerator =
    for {
      low_estimate <- Gen.option(arbitrary[Double])
      display_name <- Gen.option(arbitrary[String])
      estimate <- Gen.option(arbitrary[String])
      high_estimate <- Gen.option(arbitrary[Double])
      product_id <- Gen.option(arbitrary[String])
      currency_code <- Gen.option(arbitrary[String])
      surge_multiplier <- Gen.option(arbitrary[Double])
    } yield PriceEstimate(low_estimate, display_name, estimate, high_estimate, product_id, currency_code, surge_multiplier)
  // test data generator for /definitions/Activities
  val ActivitiesGenerator =
    for {
      offset <- Gen.option(arbitrary[Int])
      limit <- Gen.option(arbitrary[Int])
      count <- Gen.option(arbitrary[Int])
      history <- Gen.option(Gen.containerOf[List,Activity](generatorDefinitions.ActivityGenerator))
    } yield Activities(offset, limit, count, history)
  // test data generator for /definitions/Activity
  val ActivityGenerator =
    for {
      uuid <- Gen.option(arbitrary[String])
    } yield Activity(uuid)
  // test data generator for /definitions/Product
  val ProductGenerator =
    for {
      image <- Gen.option(arbitrary[String])
      description <- Gen.option(arbitrary[String])
      display_name <- Gen.option(arbitrary[String])
      product_id <- Gen.option(arbitrary[String])
      capacity <- Gen.option(arbitrary[String])
    } yield Product(image, description, display_name, product_id, capacity)
  // test data generator for /definitions/Profile
  val ProfileGenerator =
    for {
      first_name <- Gen.option(arbitrary[String])
      email <- Gen.option(arbitrary[String])
      promo_code <- Gen.option(arbitrary[String])
      last_name <- Gen.option(arbitrary[String])
      picture <- Gen.option(arbitrary[String])
    } yield Profile(first_name, email, promo_code, last_name, picture)
  def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
}