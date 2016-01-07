package uber.api.yaml

import org.scalacheck.Gen
import org.scalacheck.Arbitrary
import Arbitrary._

import de.zalando.play.controllers.ArrayWrapper
object Generators {
    def createDoubleGenerator = _generate(DoubleGenerator)

    def createProductDescriptionGenerator = _generate(ProductDescriptionGenerator)

    def createActivitiesHistoryGenerator = _generate(ActivitiesHistoryGenerator)

    def createActivitiesHistoryNameClashGenerator = _generate(ActivitiesHistoryNameClashGenerator)

    def createPriceEstimateLow_estimateGenerator = _generate(PriceEstimateLow_estimateGenerator)

    def createProductsGetResponses200Generator = _generate(ProductsGetResponses200Generator)

    def createActivitiesHistoryOptGenerator = _generate(ActivitiesHistoryOptGenerator)

    def createEstimatesPriceGetResponses200Generator = _generate(EstimatesPriceGetResponses200Generator)

    def createActivitiesHistoryOptNameClashGenerator = _generate(ActivitiesHistoryOptNameClashGenerator)

    def createActivitiesLimitGenerator = _generate(ActivitiesLimitGenerator)

    def DoubleGenerator = arbitrary[Double]

    def ProductDescriptionGenerator = Gen.option(arbitrary[String])

    def ActivitiesHistoryGenerator = Gen.option(ActivitiesHistoryOptNameClashGenerator)

    def ActivitiesHistoryNameClashGenerator = Gen.option(ActivitiesHistoryOptGenerator)

    def PriceEstimateLow_estimateGenerator = Gen.option(arbitrary[Double])

    def ProductsGetResponses200Generator = Gen.containerOf[List,Product](ProductGenerator)

    def ActivitiesHistoryOptGenerator = Gen.containerOf[List,Activity](ActivityGenerator)

    def EstimatesPriceGetResponses200Generator = Gen.containerOf[List,PriceEstimate](PriceEstimateGenerator)

    def ActivitiesHistoryOptNameClashGenerator = _genList(ActivityGenerator, "csv")

    def ActivitiesLimitGenerator = Gen.option(arbitrary[Int])

    def createActivityGenerator = _generate(ActivityGenerator)

    def createPriceEstimateGenerator = _generate(PriceEstimateGenerator)

    def createProductGenerator = _generate(ProductGenerator)

    def createHistoryGetResponses200Generator = _generate(HistoryGetResponses200Generator)

    def createProfileGenerator = _generate(ProfileGenerator)

    def createActivitiesGenerator = _generate(ActivitiesGenerator)

    def createErrorGenerator = _generate(ErrorGenerator)

    def ActivityGenerator = for {
        uuid <- ProductDescriptionGenerator
        } yield Activity(uuid)

    def PriceEstimateGenerator = for {
        low_estimate <- PriceEstimateLow_estimateGenerator
        display_name <- ProductDescriptionGenerator
        estimate <- ProductDescriptionGenerator
        high_estimate <- PriceEstimateLow_estimateGenerator
        product_id <- ProductDescriptionGenerator
        currency_code <- ProductDescriptionGenerator
        surge_multiplier <- PriceEstimateLow_estimateGenerator
        } yield PriceEstimate(low_estimate, display_name, estimate, high_estimate, product_id, currency_code, surge_multiplier)

    def ProductGenerator = for {
        image <- ProductDescriptionGenerator
        description <- ProductDescriptionGenerator
        display_name <- ProductDescriptionGenerator
        product_id <- ProductDescriptionGenerator
        capacity <- ProductDescriptionGenerator
        } yield Product(image, description, display_name, product_id, capacity)

    def HistoryGetResponses200Generator = for {
        offset <- ActivitiesLimitGenerator
        limit <- ActivitiesLimitGenerator
        count <- ActivitiesLimitGenerator
        history <- ActivitiesHistoryNameClashGenerator
        } yield HistoryGetResponses200(offset, limit, count, history)

    def ProfileGenerator = for {
        first_name <- ProductDescriptionGenerator
        email <- ProductDescriptionGenerator
        promo_code <- ProductDescriptionGenerator
        last_name <- ProductDescriptionGenerator
        picture <- ProductDescriptionGenerator
        } yield Profile(first_name, email, promo_code, last_name, picture)

    def ActivitiesGenerator = for {
        offset <- ActivitiesLimitGenerator
        limit <- ActivitiesLimitGenerator
        count <- ActivitiesLimitGenerator
        history <- ActivitiesHistoryGenerator
        } yield Activities(offset, limit, count, history)

    def ErrorGenerator = for {
        code <- ActivitiesLimitGenerator
        message <- ProductDescriptionGenerator
        fields <- ProductDescriptionGenerator
        } yield Error(code, message, fields)

    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
    def _genList[T](gen: Gen[T], format: String): Gen[ArrayWrapper[T]] = for {
        items <- Gen.containerOf[List,T](gen)
    } yield ArrayWrapper(format)(items)
    }