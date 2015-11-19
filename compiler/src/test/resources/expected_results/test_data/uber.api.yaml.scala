package uber.api.yaml
import org.scalacheck.Gen
import org.scalacheck.Arbitrary._

object definitionsGenerator {
    import definitions._
    def createProfileLast_nameGenerator = _generate(ProfileLast_nameGenerator)
    def createActivitiesHistoryGenerator = _generate(ActivitiesHistoryGenerator)
    def createPriceEstimateLow_estimateGenerator = _generate(PriceEstimateLow_estimateGenerator)
    def createActivitiesHistoryOptGenerator = _generate(ActivitiesHistoryOptGenerator)
    def createActivitiesLimitGenerator = _generate(ActivitiesLimitGenerator)
    val ProfileLast_nameGenerator = Gen.option(arbitrary[String])
    val ActivitiesHistoryGenerator = Gen.option(ActivitiesHistoryOptGenerator)
    val PriceEstimateLow_estimateGenerator = Gen.option(arbitrary[Double])
    val ActivitiesHistoryOptGenerator = Gen.containerOf[List,Activity](ActivityGenerator)
    val ActivitiesLimitGenerator = Gen.option(arbitrary[Int])
    def createActivityGenerator = _generate(ActivityGenerator)
    def createPriceEstimateGenerator = _generate(PriceEstimateGenerator)
    def createProductGenerator = _generate(ProductGenerator)
    def createProfileGenerator = _generate(ProfileGenerator)
    def createActivitiesGenerator = _generate(ActivitiesGenerator)
    def createErrorGenerator = _generate(ErrorGenerator)
    val ActivityGenerator =
        for {
        uuid <- ProfileLast_nameGenerator
        } yield Activity(uuid)
    
    val PriceEstimateGenerator =
        for {
        low_estimate <- PriceEstimateLow_estimateGenerator
        display_name <- ProfileLast_nameGenerator
        estimate <- ProfileLast_nameGenerator
        high_estimate <- PriceEstimateLow_estimateGenerator
        product_id <- ProfileLast_nameGenerator
        currency_code <- ProfileLast_nameGenerator
        surge_multiplier <- PriceEstimateLow_estimateGenerator
        } yield PriceEstimate(low_estimate, display_name, estimate, high_estimate, product_id, currency_code, surge_multiplier)
    
    val ProductGenerator =
        for {
        image <- ProfileLast_nameGenerator
        description <- ProfileLast_nameGenerator
        display_name <- ProfileLast_nameGenerator
        product_id <- ProfileLast_nameGenerator
        capacity <- ProfileLast_nameGenerator
        } yield Product(image, description, display_name, product_id, capacity)
    
    val ProfileGenerator =
        for {
        first_name <- ProfileLast_nameGenerator
        email <- ProfileLast_nameGenerator
        promo_code <- ProfileLast_nameGenerator
        last_name <- ProfileLast_nameGenerator
        picture <- ProfileLast_nameGenerator
        } yield Profile(first_name, email, promo_code, last_name, picture)
    
    val ActivitiesGenerator =
        for {
        offset <- ActivitiesLimitGenerator
        limit <- ActivitiesLimitGenerator
        count <- ActivitiesLimitGenerator
        history <- ActivitiesHistoryGenerator
        } yield Activities(offset, limit, count, history)
    
    val ErrorGenerator =
        for {
        code <- ActivitiesLimitGenerator
        message <- ProfileLast_nameGenerator
        fields <- ProfileLast_nameGenerator
        } yield Error(code, message, fields)
    
    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample

    def _genMap[K,V](keyGen: Gen[K], valGen: Gen[V]): Gen[Map[K,V]] = for {
        keys <- Gen.containerOf[List,K](keyGen)
        values <- Gen.containerOfN[List,V](keys.size, valGen)
    } yield keys.zip(values).toMap
}
object pathsGenerator {
    import definitions._
    import paths.ProductsGetResponses200Opt
    import paths.EstimatesPriceGetResponses200Opt
    import definitionsGenerator._
    def createProductsGetResponses200OptGenerator = _generate(ProductsGetResponses200OptGenerator)
    def createEstimatesPriceGetResponses200OptGenerator = _generate(EstimatesPriceGetResponses200OptGenerator)
    def createHistoryGetResponsesDefaultGenerator = _generate(HistoryGetResponsesDefaultGenerator)
    def createHistoryGetResponses200Generator = _generate(HistoryGetResponses200Generator)
    def createProductsGetResponses200Generator = _generate(ProductsGetResponses200Generator)
    def createMeGetResponses200Generator = _generate(MeGetResponses200Generator)
    def createEstimatesPriceGetResponses200Generator = _generate(EstimatesPriceGetResponses200Generator)
    val ProductsGetResponses200OptGenerator = Gen.containerOf[List,Product](ProductGenerator)
    val EstimatesPriceGetResponses200OptGenerator = Gen.containerOf[List,PriceEstimate](PriceEstimateGenerator)
    val HistoryGetResponsesDefaultGenerator = Gen.option(ErrorGenerator)
    val HistoryGetResponses200Generator = Gen.option(ActivitiesGenerator)
    val ProductsGetResponses200Generator = Gen.option(ProductsGetResponses200OptGenerator)
    val MeGetResponses200Generator = Gen.option(ProfileGenerator)
    val EstimatesPriceGetResponses200Generator = Gen.option(EstimatesPriceGetResponses200OptGenerator)
    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample

    def _genMap[K,V](keyGen: Gen[K], valGen: Gen[V]): Gen[Map[K,V]] = for {
        keys <- Gen.containerOf[List,K](keyGen)
        values <- Gen.containerOfN[List,V](keys.size, valGen)
    } yield keys.zip(values).toMap
}
