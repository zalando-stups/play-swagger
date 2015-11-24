package options.yaml
import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._

object definitionsValidator {
    import definitions._
    class BasicIdConstraints(override val instance: Long) extends ValidationBase[Long] {
        override def constraints: Seq[Constraint[Long]] =
        Seq()
    }
    class BasicIdValidator(instance: Long) extends RecursiveValidator {
      override val validators = Seq(new BasicIdConstraints(instance))
    }
    class BasicValidator(instance: Basic) extends RecursiveValidator {
        override val validators = Seq(
            new BasicIdValidator(instance.id), 
            new BasicRequiredValidator(instance.required), 
            new BasicOptionalValidator(instance.optional)
            )
        }
    class BasicRequiredValidator(instance: BasicRequired) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class BasicOptionalValidator(instance: BasicOptional) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    }
