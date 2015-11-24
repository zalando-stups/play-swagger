package nested_arrays.yaml
import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._

object definitionsValidator {
    import definitions._
    class ExampleNestedArraysOptArrValidator(instance: ExampleNestedArraysOptArr) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class ActivityValidator(instance: Activity) extends RecursiveValidator {
        override val validators = Seq(
            new ActivityActionsValidator(instance.actions)
            )
        }
    class ExampleNestedArraysOptValidator(instance: ExampleNestedArraysOpt) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class ExampleMessagesOptValidator(instance: ExampleMessagesOpt) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class ActivityActionsValidator(instance: ActivityActions) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class ExampleMessagesValidator(instance: ExampleMessages) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class ExampleMessagesOptArrValidator(instance: ExampleMessagesOptArr) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class ExampleValidator(instance: Example) extends RecursiveValidator {
        override val validators = Seq(
            new ExampleMessagesValidator(instance.messages), 
            new ExampleNestedArraysValidator(instance.nestedArrays)
            )
        }
    class ExampleNestedArraysOptArrArrValidator(instance: ExampleNestedArraysOptArrArr) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class ExampleNestedArraysValidator(instance: ExampleNestedArrays) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    }
