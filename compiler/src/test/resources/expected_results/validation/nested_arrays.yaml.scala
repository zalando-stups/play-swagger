package nested_arrays.yaml
import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._

object definitionsValidator {
    import definitions._
    class ExampleNestedArraysOptArrValidator(override val instance: ExampleNestedArraysOptArr) extends RecursiveValidator[ExampleNestedArraysOptArr] {
        override val validators = Seq(
            )
        }
    class ActivityValidator(override val instance: Activity) extends RecursiveValidator[Activity] {
        override val validators = Seq(
            new ActivityActionsValidator(instance.actions)
            )
        }
    class ExampleNestedArraysOptValidator(override val instance: ExampleNestedArraysOpt) extends RecursiveValidator[ExampleNestedArraysOpt] {
        override val validators = Seq(
            )
        }
    class ExampleMessagesOptValidator(override val instance: ExampleMessagesOpt) extends RecursiveValidator[ExampleMessagesOpt] {
        override val validators = Seq(
            )
        }
    class ActivityActionsValidator(override val instance: ActivityActions) extends RecursiveValidator[ActivityActions] {
        override val validators = Seq(
            )
        }
    class ExampleMessagesValidator(override val instance: ExampleMessages) extends RecursiveValidator[ExampleMessages] {
        override val validators = Seq(
            )
        }
    class ExampleMessagesOptArrValidator(override val instance: ExampleMessagesOptArr) extends RecursiveValidator[ExampleMessagesOptArr] {
        override val validators = Seq(
            )
        }
    class ExampleValidator(override val instance: Example) extends RecursiveValidator[Example] {
        override val validators = Seq(
            new ExampleMessagesValidator(instance.messages), 
            new ExampleNestedArraysValidator(instance.nestedArrays)
            )
        }
    class ExampleNestedArraysOptArrArrValidator(override val instance: ExampleNestedArraysOptArrArr) extends RecursiveValidator[ExampleNestedArraysOptArrArr] {
        override val validators = Seq(
            )
        }
    class ExampleNestedArraysValidator(override val instance: ExampleNestedArrays) extends RecursiveValidator[ExampleNestedArrays] {
        override val validators = Seq(
            )
        }
    }
