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
    class NestedObjectsPlainSimpleValidator(instance: String) extends RecursiveValidator {
      override val validators = Seq(new NestedObjectsPlainSimpleConstraints(instance))
    }
    class NestedObjectsNestedValidator(instance: NestedObjectsNested) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class NestedObjectsNestedOptValidator(instance: NestedObjectsNestedOpt) extends RecursiveValidator {
        override val validators = Seq(
            new NestedObjectsNestedNested2Validator(instance.nested2)
            )
        }
    class NestedObjectsNestedNested2Nested3BottomValidator(instance: NestedObjectsNestedNested2Nested3Bottom) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class NestedObjectsNestedNested2Nested3Validator(instance: NestedObjectsNestedNested2Nested3) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class NestedObjectsNestedNested2Nested3OptValidator(instance: NestedObjectsNestedNested2Nested3Opt) extends RecursiveValidator {
        override val validators = Seq(
            new NestedObjectsNestedNested2Nested3BottomValidator(instance.bottom)
            )
        }
    class NestedObjectsValidator(instance: NestedObjects) extends RecursiveValidator {
        override val validators = Seq(
            new NestedObjectsPlainValidator(instance.plain), 
            new NestedObjectsNestedValidator(instance.nested)
            )
        }
    class NestedObjectsPlainValidator(instance: NestedObjectsPlain) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class NestedObjectsPlainOptValidator(instance: NestedObjectsPlainOpt) extends RecursiveValidator {
        override val validators = Seq(
            new NestedObjectsPlainSimpleValidator(instance.simple)
            )
        }
    class NestedObjectsNestedNested2Validator(instance: NestedObjectsNestedNested2) extends RecursiveValidator {
        override val validators = Seq(
            new NestedObjectsNestedNested2Nested3Validator(instance.nested3)
            )
        }
    }
