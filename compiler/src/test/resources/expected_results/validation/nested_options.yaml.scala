package nested_options.yaml
import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._

object definitionsValidator {
    import definitions._
    class BasicValidator(instance: Basic) extends RecursiveValidator {
        override val validators = Seq(
            new BasicOptionalValidator(instance.optional)
            )
        }
    class BasicOptionalValidator(instance: BasicOptional) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class BasicOptionalOptValidator(instance: BasicOptionalOpt) extends RecursiveValidator {
        override val validators = Seq(
            new BasicOptionalNested_optionalValidator(instance.nested_optional)
            )
        }
    class BasicOptionalNested_optionalValidator(instance: BasicOptionalNested_optional) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    }
