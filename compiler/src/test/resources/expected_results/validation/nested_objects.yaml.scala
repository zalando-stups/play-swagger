package nested_objects.yaml
import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._

object definitionsValidator {
    import definitions._
    class NestedObjectsPlainSimpleConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq(pattern("the pattern".r))
    }
    class NestedObjectsPlainSimpleValidator(override val instance: String) extends RecursiveValidator[String] {
      override val validators = Seq(new NestedObjectsPlainSimpleConstraints(instance))
    }
    class NestedObjectsNestedValidator(override val instance: NestedObjectsNested) extends RecursiveValidator[NestedObjectsNested] {
        override val validators = Seq(
            )
        }
    class NestedObjectsNestedOptValidator(override val instance: NestedObjectsNestedOpt) extends RecursiveValidator[NestedObjectsNestedOpt] {
        override val validators = Seq(
            new NestedObjectsNestedNested2Validator(instance.nested2)
            )
        }
    class NestedObjectsNestedNested2Nested3BottomValidator(override val instance: NestedObjectsNestedNested2Nested3Bottom) extends RecursiveValidator[NestedObjectsNestedNested2Nested3Bottom] {
        override val validators = Seq(
            )
        }
    class NestedObjectsNestedNested2Nested3Validator(override val instance: NestedObjectsNestedNested2Nested3) extends RecursiveValidator[NestedObjectsNestedNested2Nested3] {
        override val validators = Seq(
            )
        }
    class NestedObjectsNestedNested2Nested3OptValidator(override val instance: NestedObjectsNestedNested2Nested3Opt) extends RecursiveValidator[NestedObjectsNestedNested2Nested3Opt] {
        override val validators = Seq(
            new NestedObjectsNestedNested2Nested3BottomValidator(instance.bottom)
            )
        }
    class NestedObjectsValidator(override val instance: NestedObjects) extends RecursiveValidator[NestedObjects] {
        override val validators = Seq(
            new NestedObjectsPlainValidator(instance.plain), 
            new NestedObjectsNestedValidator(instance.nested)
            )
        }
    class NestedObjectsPlainValidator(override val instance: NestedObjectsPlain) extends RecursiveValidator[NestedObjectsPlain] {
        override val validators = Seq(
            )
        }
    class NestedObjectsPlainOptValidator(override val instance: NestedObjectsPlainOpt) extends RecursiveValidator[NestedObjectsPlainOpt] {
        override val validators = Seq(
            new NestedObjectsPlainSimpleValidator(instance.simple)
            )
        }
    class NestedObjectsNestedNested2Validator(override val instance: NestedObjectsNestedNested2) extends RecursiveValidator[NestedObjectsNestedNested2] {
        override val validators = Seq(
            new NestedObjectsNestedNested2Nested3Validator(instance.nested3)
            )
        }
    }
