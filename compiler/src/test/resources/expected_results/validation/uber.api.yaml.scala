package uber.api.yaml
import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._

object definitionsValidator {
    import definitions._
    class ProfileLast_nameValidator(override val instance: ProfileLast_name) extends RecursiveValidator[ProfileLast_name] {
        override val validators = Seq(
            )
        }
    class ActivityValidator(override val instance: Activity) extends RecursiveValidator[Activity] {
        override val validators = Seq(
            new ProfileLast_nameValidator(instance.uuid)
            )
        }
    class ActivitiesHistoryValidator(override val instance: ActivitiesHistory) extends RecursiveValidator[ActivitiesHistory] {
        override val validators = Seq(
            )
        }
    class PriceEstimateLow_estimateValidator(override val instance: PriceEstimateLow_estimate) extends RecursiveValidator[PriceEstimateLow_estimate] {
        override val validators = Seq(
            )
        }
    class PriceEstimateValidator(override val instance: PriceEstimate) extends RecursiveValidator[PriceEstimate] {
        override val validators = Seq(
            new PriceEstimateLow_estimateValidator(instance.low_estimate), 
            new ProfileLast_nameValidator(instance.display_name), 
            new ProfileLast_nameValidator(instance.estimate), 
            new PriceEstimateLow_estimateValidator(instance.high_estimate), 
            new ProfileLast_nameValidator(instance.product_id), 
            new ProfileLast_nameValidator(instance.currency_code), 
            new PriceEstimateLow_estimateValidator(instance.surge_multiplier)
            )
        }
    class ProductValidator(override val instance: Product) extends RecursiveValidator[Product] {
        override val validators = Seq(
            new ProfileLast_nameValidator(instance.image), 
            new ProfileLast_nameValidator(instance.description), 
            new ProfileLast_nameValidator(instance.display_name), 
            new ProfileLast_nameValidator(instance.product_id), 
            new ProfileLast_nameValidator(instance.capacity)
            )
        }
    class ProfileValidator(override val instance: Profile) extends RecursiveValidator[Profile] {
        override val validators = Seq(
            new ProfileLast_nameValidator(instance.first_name), 
            new ProfileLast_nameValidator(instance.email), 
            new ProfileLast_nameValidator(instance.promo_code), 
            new ProfileLast_nameValidator(instance.last_name), 
            new ProfileLast_nameValidator(instance.picture)
            )
        }
    class ActivitiesHistoryOptValidator(override val instance: ActivitiesHistoryOpt) extends RecursiveValidator[ActivitiesHistoryOpt] {
        override val validators = Seq(
            )
        }
    class ActivitiesValidator(override val instance: Activities) extends RecursiveValidator[Activities] {
        override val validators = Seq(
            new ActivitiesLimitValidator(instance.offset), 
            new ActivitiesLimitValidator(instance.limit), 
            new ActivitiesLimitValidator(instance.count), 
            new ActivitiesHistoryValidator(instance.history)
            )
        }
    class ErrorValidator(override val instance: Error) extends RecursiveValidator[Error] {
        override val validators = Seq(
            new ActivitiesLimitValidator(instance.code), 
            new ProfileLast_nameValidator(instance.message), 
            new ProfileLast_nameValidator(instance.fields)
            )
        }
    class ActivitiesLimitValidator(override val instance: ActivitiesLimit) extends RecursiveValidator[ActivitiesLimit] {
        override val validators = Seq(
            )
        }
    }
object pathsValidator {
    import definitions._
    import paths._
    import definitionsValidator._
    class EstimatesPriceGetEnd_latitudeConstraints(override val instance: Double) extends ValidationBase[Double] {
        override def constraints: Seq[Constraint[Double]] =
        Seq()
    }
    class EstimatesPriceGetEnd_latitudeValidator(override val instance: Double) extends RecursiveValidator[Double] {
      override val validators = Seq(new EstimatesPriceGetEnd_latitudeConstraints(instance))
    }
    class ProductsGetResponses200OptValidator(override val instance: ProductsGetResponses200Opt) extends RecursiveValidator[ProductsGetResponses200Opt] {
        override val validators = Seq(
            )
        }
    class EstimatesPriceGetResponses200OptValidator(override val instance: EstimatesPriceGetResponses200Opt) extends RecursiveValidator[EstimatesPriceGetResponses200Opt] {
        override val validators = Seq(
            )
        }
    class HistoryGetResponsesDefaultValidator(override val instance: HistoryGetResponsesDefault) extends RecursiveValidator[HistoryGetResponsesDefault] {
        override val validators = Seq(
            )
        }
    class HistoryGetResponses200Validator(override val instance: HistoryGetResponses200) extends RecursiveValidator[HistoryGetResponses200] {
        override val validators = Seq(
            )
        }
    class ProductsGetResponses200Validator(override val instance: ProductsGetResponses200) extends RecursiveValidator[ProductsGetResponses200] {
        override val validators = Seq(
            )
        }
    class MeGetResponses200Validator(override val instance: MeGetResponses200) extends RecursiveValidator[MeGetResponses200] {
        override val validators = Seq(
            )
        }
    class EstimatesPriceGetResponses200Validator(override val instance: EstimatesPriceGetResponses200) extends RecursiveValidator[EstimatesPriceGetResponses200] {
        override val validators = Seq(
            )
        }
    }
