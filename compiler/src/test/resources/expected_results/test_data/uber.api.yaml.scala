package uber.api.yaml

import org.scalacheck.Gen
import org.scalacheck.Arbitrary
import Arbitrary._

import de.zalando.play.controllers.ArrayWrapper
object Generators {
def createDoubleGenerator = _generate(DoubleGenerator)

    def createProfileLast_nameGenerator = _generate(ProfileLast_nameGenerator)

    def createProductsGetResponses200OptGenerator = _generate(ProductsGetResponses200OptGenerator)

    def createEstimatesPriceGetResponses200OptGenerator = _generate(EstimatesPriceGetResponses200OptGenerator)

    def createActivitiesHistoryGenerator = _generate(ActivitiesHistoryGenerator)

    def createPriceEstimateLow_estimateGenerator = _generate(PriceEstimateLow_estimateGenerator)

    def createHistoryGetResponsesDefaultGenerator = _generate(HistoryGetResponsesDefaultGenerator)

    def createHistoryGetResponses200Generator = _generate(HistoryGetResponses200Generator)

    def createProductsGetResponses200Generator = _generate(ProductsGetResponses200Generator)

    def createMeGetResponses200Generator = _generate(MeGetResponses200Generator)

    def createActivitiesHistoryOptGenerator = _generate(ActivitiesHistoryOptGenerator)

    def createEstimatesPriceGetResponses200Generator = _generate(EstimatesPriceGetResponses200Generator)

    def createActivitiesLimitGenerator = _generate(ActivitiesLimitGenerator)

    def DoubleGenerator = arbitrary[Double]

    def ProfileLast_nameGenerator = Gen.option(arbitrary[String])

    def ProductsGetResponses200OptGenerator = _genList(ProductGenerator, "csv")

    def EstimatesPriceGetResponses200OptGenerator = _genList(PriceEstimateGenerator, "csv")

    def ActivitiesHistoryGenerator = Gen.option(ActivitiesHistoryOptGenerator)

    def PriceEstimateLow_estimateGenerator = Gen.option(arbitrary[Double])

    def HistoryGetResponsesDefaultGenerator = Gen.option(ErrorGenerator)

    def HistoryGetResponses200Generator = Gen.option(ActivitiesGenerator)

    def ProductsGetResponses200Generator = Gen.option(ProductsGetResponses200OptGenerator)

    def MeGetResponses200Generator = Gen.option(ProfileGenerator)

    def ActivitiesHistoryOptGenerator = _genList(ActivityGenerator, "csv")

    def EstimatesPriceGetResponses200Generator = Gen.option(EstimatesPriceGetResponses200OptGenerator)

    def ActivitiesLimitGenerator = Gen.option(arbitrary[Int])

    def createActivityGenerator = _generate(ActivityGenerator)

    def createPriceEstimateGenerator = _generate(PriceEstimateGenerator)

    def createProductGenerator = _generate(ProductGenerator)

    def createProfileGenerator = _generate(ProfileGenerator)

    def createActivitiesGenerator = _generate(ActivitiesGenerator)

    def createErrorGenerator = _generate(ErrorGenerator)

    def ActivityGenerator = for {
        uuid <- ProfileLast_nameGenerator
        } yield Activity(uuid)

    def PriceEstimateGenerator = for {
        low_estimate <- PriceEstimateLow_estimateGenerator
        display_name <- ProfileLast_nameGenerator
        estimate <- ProfileLast_nameGenerator
        high_estimate <- PriceEstimateLow_estimateGenerator
        product_id <- ProfileLast_nameGenerator
        currency_code <- ProfileLast_nameGenerator
        surge_multiplier <- PriceEstimateLow_estimateGenerator
        } yield PriceEstimate(low_estimate, display_name, estimate, high_estimate, product_id, currency_code, surge_multiplier)

    def ProductGenerator = for {
        image <- ProfileLast_nameGenerator
        description <- ProfileLast_nameGenerator
        display_name <- ProfileLast_nameGenerator
        product_id <- ProfileLast_nameGenerator
        capacity <- ProfileLast_nameGenerator
        } yield Product(image, description, display_name, product_id, capacity)

    def ProfileGenerator = for {
        first_name <- ProfileLast_nameGenerator
        email <- ProfileLast_nameGenerator
        promo_code <- ProfileLast_nameGenerator
        last_name <- ProfileLast_nameGenerator
        picture <- ProfileLast_nameGenerator
        } yield Profile(first_name, email, promo_code, last_name, picture)

    def ActivitiesGenerator = for {
        offset <- ActivitiesLimitGenerator
        limit <- ActivitiesLimitGenerator
        count <- ActivitiesLimitGenerator
        history <- ActivitiesHistoryGenerator
        } yield Activities(offset, limit, count, history)

    def ErrorGenerator = for {
        code <- ActivitiesLimitGenerator
        message <- ProfileLast_nameGenerator
        fields <- ProfileLast_nameGenerator
        } yield Error(code, message, fields)

    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
    def _genList[T](gen: Gen[T], format: String): Gen[ArrayWrapper[T]] = for {
        items <- Gen.containerOf[List,T](gen)
    } yield ArrayWrapper(format)(items)
}
