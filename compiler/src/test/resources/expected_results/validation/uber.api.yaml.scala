package uber.api.yaml
import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._

object definitionsValidator {
    import definitions._
    import pathsValidator._
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
    }
object pathsValidator {
    import definitions._
    import paths.{EstimatesPriceGetResponses200Opt, ProductsGetResponses200Opt}
    import definitionsValidator.{ActivitiesLimitValidator, ProfileLast_nameValidator}
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
