package nested_options.yaml
import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._

object definitionsValidator {
    import definitions._
    class BasicValidator(override val instance: Basic) extends RecursiveValidator[Basic] {
        override val validators = Seq(
            new BasicOptionalValidator(instance.optional)
            )
        }
    class BasicOptionalValidator(override val instance: BasicOptional) extends RecursiveValidator[BasicOptional] {
        override val validators = Seq(
            )
        }
    class BasicOptionalOptValidator(override val instance: BasicOptionalOpt) extends RecursiveValidator[BasicOptionalOpt] {
        override val validators = Seq(
            new BasicOptionalNested_optionalValidator(instance.nested_optional)
            )
        }
    class BasicOptionalNested_optionalValidator(override val instance: BasicOptionalNested_optional) extends RecursiveValidator[BasicOptionalNested_optional] {
        override val validators = Seq(
            )
        }
    }
