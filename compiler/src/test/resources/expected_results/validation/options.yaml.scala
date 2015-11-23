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
    class BasicIdValidator(override val instance: Long) extends RecursiveValidator[Long] {
      override val validators = Seq(new BasicIdConstraints(instance))
    }
    class BasicValidator(override val instance: Basic) extends RecursiveValidator[Basic] {
        override val validators = Seq(
            new BasicIdValidator(instance.id), 
            new BasicRequiredValidator(instance.required), 
            new BasicOptionalValidator(instance.optional)
            )
        }
    class BasicRequiredValidator(override val instance: BasicRequired) extends RecursiveValidator[BasicRequired] {
        override val validators = Seq(
            )
        }
    class BasicOptionalValidator(override val instance: BasicOptional) extends RecursiveValidator[BasicOptional] {
        override val validators = Seq(
            )
        }
    }
