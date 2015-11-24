package uber.api.yaml
import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._

object definitionsValidator {
    import definitions._
    class EstimatesPriceGetEnd_latitudeConstraints(override val instance: Double) extends ValidationBase[Double] {
        override def constraints: Seq[Constraint[Double]] =
        Seq()
    }
    class EstimatesPriceGetEnd_latitudeValidator(instance: Double) extends RecursiveValidator {
      override val validators = Seq(new EstimatesPriceGetEnd_latitudeConstraints(instance))
    }
    class EstimatesTimeGetStart_latitudeConstraints(override val instance: Double) extends ValidationBase[Double] {
        override def constraints: Seq[Constraint[Double]] =
        Seq()
    }
    class EstimatesTimeGetStart_latitudeValidator(instance: Double) extends RecursiveValidator {
      override val validators = Seq(new EstimatesTimeGetStart_latitudeConstraints(instance))
    }
    class ProductsGetLongitudeConstraints(override val instance: Double) extends ValidationBase[Double] {
        override def constraints: Seq[Constraint[Double]] =
        Seq()
    }
    class ProductsGetLongitudeValidator(instance: Double) extends RecursiveValidator {
      override val validators = Seq(new ProductsGetLongitudeConstraints(instance))
    }
    class EstimatesTimeGetStart_longitudeConstraints(override val instance: Double) extends ValidationBase[Double] {
        override def constraints: Seq[Constraint[Double]] =
        Seq()
    }
    class EstimatesTimeGetStart_longitudeValidator(instance: Double) extends RecursiveValidator {
      override val validators = Seq(new EstimatesTimeGetStart_longitudeConstraints(instance))
    }
    class ProductsGetLatitudeConstraints(override val instance: Double) extends ValidationBase[Double] {
        override def constraints: Seq[Constraint[Double]] =
        Seq()
    }
    class ProductsGetLatitudeValidator(instance: Double) extends RecursiveValidator {
      override val validators = Seq(new ProductsGetLatitudeConstraints(instance))
    }
    class EstimatesPriceGetStart_latitudeConstraints(override val instance: Double) extends ValidationBase[Double] {
        override def constraints: Seq[Constraint[Double]] =
        Seq()
    }
    class EstimatesPriceGetStart_latitudeValidator(instance: Double) extends RecursiveValidator {
      override val validators = Seq(new EstimatesPriceGetStart_latitudeConstraints(instance))
    }
    class EstimatesPriceGetEnd_longitudeConstraints(override val instance: Double) extends ValidationBase[Double] {
        override def constraints: Seq[Constraint[Double]] =
        Seq()
    }
    class EstimatesPriceGetEnd_longitudeValidator(instance: Double) extends RecursiveValidator {
      override val validators = Seq(new EstimatesPriceGetEnd_longitudeConstraints(instance))
    }
    class EstimatesPriceGetStart_longitudeConstraints(override val instance: Double) extends ValidationBase[Double] {
        override def constraints: Seq[Constraint[Double]] =
        Seq()
    }
    class EstimatesPriceGetStart_longitudeValidator(instance: Double) extends RecursiveValidator {
      override val validators = Seq(new EstimatesPriceGetStart_longitudeConstraints(instance))
    }
    class ProfileLast_nameValidator(instance: ProfileLast_name) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class ActivityValidator(instance: Activity) extends RecursiveValidator {
        override val validators = Seq(
            new ProfileLast_nameValidator(instance.uuid)
            )
        }
    class ActivitiesHistoryValidator(instance: ActivitiesHistory) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class PriceEstimateLow_estimateValidator(instance: PriceEstimateLow_estimate) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class PriceEstimateValidator(instance: PriceEstimate) extends RecursiveValidator {
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
    class ProductValidator(instance: Product) extends RecursiveValidator {
        override val validators = Seq(
            new ProfileLast_nameValidator(instance.image), 
            new ProfileLast_nameValidator(instance.description), 
            new ProfileLast_nameValidator(instance.display_name), 
            new ProfileLast_nameValidator(instance.product_id), 
            new ProfileLast_nameValidator(instance.capacity)
            )
        }
    class ProfileValidator(instance: Profile) extends RecursiveValidator {
        override val validators = Seq(
            new ProfileLast_nameValidator(instance.first_name), 
            new ProfileLast_nameValidator(instance.email), 
            new ProfileLast_nameValidator(instance.promo_code), 
            new ProfileLast_nameValidator(instance.last_name), 
            new ProfileLast_nameValidator(instance.picture)
            )
        }
    class ActivitiesHistoryOptValidator(instance: ActivitiesHistoryOpt) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class ActivitiesValidator(instance: Activities) extends RecursiveValidator {
        override val validators = Seq(
            new ActivitiesLimitValidator(instance.offset), 
            new ActivitiesLimitValidator(instance.limit), 
            new ActivitiesLimitValidator(instance.count), 
            new ActivitiesHistoryValidator(instance.history)
            )
        }
    class ErrorValidator(instance: Error) extends RecursiveValidator {
        override val validators = Seq(
            new ActivitiesLimitValidator(instance.code), 
            new ProfileLast_nameValidator(instance.message), 
            new ProfileLast_nameValidator(instance.fields)
            )
        }
    class ActivitiesLimitValidator(instance: ActivitiesLimit) extends RecursiveValidator {
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
    class EstimatesPriceGetEnd_latitudeValidator(instance: Double) extends RecursiveValidator {
      override val validators = Seq(new EstimatesPriceGetEnd_latitudeConstraints(instance))
    }
    class EstimatesTimeGetStart_latitudeConstraints(override val instance: Double) extends ValidationBase[Double] {
        override def constraints: Seq[Constraint[Double]] =
        Seq()
    }
    class EstimatesTimeGetStart_latitudeValidator(instance: Double) extends RecursiveValidator {
      override val validators = Seq(new EstimatesTimeGetStart_latitudeConstraints(instance))
    }
    class ProductsGetLongitudeConstraints(override val instance: Double) extends ValidationBase[Double] {
        override def constraints: Seq[Constraint[Double]] =
        Seq()
    }
    class ProductsGetLongitudeValidator(instance: Double) extends RecursiveValidator {
      override val validators = Seq(new ProductsGetLongitudeConstraints(instance))
    }
    class EstimatesTimeGetStart_longitudeConstraints(override val instance: Double) extends ValidationBase[Double] {
        override def constraints: Seq[Constraint[Double]] =
        Seq()
    }
    class EstimatesTimeGetStart_longitudeValidator(instance: Double) extends RecursiveValidator {
      override val validators = Seq(new EstimatesTimeGetStart_longitudeConstraints(instance))
    }
    class ProductsGetLatitudeConstraints(override val instance: Double) extends ValidationBase[Double] {
        override def constraints: Seq[Constraint[Double]] =
        Seq()
    }
    class ProductsGetLatitudeValidator(instance: Double) extends RecursiveValidator {
      override val validators = Seq(new ProductsGetLatitudeConstraints(instance))
    }
    class EstimatesPriceGetStart_latitudeConstraints(override val instance: Double) extends ValidationBase[Double] {
        override def constraints: Seq[Constraint[Double]] =
        Seq()
    }
    class EstimatesPriceGetStart_latitudeValidator(instance: Double) extends RecursiveValidator {
      override val validators = Seq(new EstimatesPriceGetStart_latitudeConstraints(instance))
    }
    class EstimatesPriceGetEnd_longitudeConstraints(override val instance: Double) extends ValidationBase[Double] {
        override def constraints: Seq[Constraint[Double]] =
        Seq()
    }
    class EstimatesPriceGetEnd_longitudeValidator(instance: Double) extends RecursiveValidator {
      override val validators = Seq(new EstimatesPriceGetEnd_longitudeConstraints(instance))
    }
    class EstimatesPriceGetStart_longitudeConstraints(override val instance: Double) extends ValidationBase[Double] {
        override def constraints: Seq[Constraint[Double]] =
        Seq()
    }
    class EstimatesPriceGetStart_longitudeValidator(instance: Double) extends RecursiveValidator {
      override val validators = Seq(new EstimatesPriceGetStart_longitudeConstraints(instance))
    }
    class ProductsGetResponses200OptValidator(instance: ProductsGetResponses200Opt) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class EstimatesPriceGetResponses200OptValidator(instance: EstimatesPriceGetResponses200Opt) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class HistoryGetResponsesDefaultValidator(instance: HistoryGetResponsesDefault) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class HistoryGetResponses200Validator(instance: HistoryGetResponses200) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class ProductsGetResponses200Validator(instance: ProductsGetResponses200) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class MeGetResponses200Validator(instance: MeGetResponses200) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class EstimatesPriceGetResponses200Validator(instance: EstimatesPriceGetResponses200) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class ProductsGetValidator(latitude: Double, longitude: Double) extends RecursiveValidator {
    override val validators = Seq(
    new ProductsGetLatitudeValidator(latitude), 
    new ProductsGetLongitudeValidator(longitude)
    )
    }
    class EstimatesTimeGetValidator(start_latitude: Double, start_longitude: Double, customer_uuid: ProfileLast_name, product_id: ProfileLast_name) extends RecursiveValidator {
    override val validators = Seq(
    new EstimatesTimeGetStart_latitudeValidator(start_latitude), 
    new EstimatesTimeGetStart_longitudeValidator(start_longitude), 
    new ProfileLast_nameValidator(customer_uuid), 
    new ProfileLast_nameValidator(product_id)
    )
    }
    class EstimatesPriceGetValidator(start_latitude: Double, start_longitude: Double, end_latitude: Double, end_longitude: Double) extends RecursiveValidator {
    override val validators = Seq(
    new EstimatesPriceGetStart_latitudeValidator(start_latitude), 
    new EstimatesPriceGetStart_longitudeValidator(start_longitude), 
    new EstimatesPriceGetEnd_latitudeValidator(end_latitude), 
    new EstimatesPriceGetEnd_longitudeValidator(end_longitude)
    )
    }
    class HistoryGetValidator(offset: ActivitiesLimit, limit: ActivitiesLimit) extends RecursiveValidator {
    override val validators = Seq(
    new ActivitiesLimitValidator(offset), 
    new ActivitiesLimitValidator(limit)
    )
    }
    }
