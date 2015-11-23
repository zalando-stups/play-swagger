package basic_extension.yaml
import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._

object definitionsValidator {
    import definitions.ErrorModel
    import definitions.ExtendedErrorModel
    class ErrorModelMessageConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class ErrorModelMessageValidator(override val instance: String) extends RecursiveValidator[String] {
      override val validators = Seq(new ErrorModelMessageConstraints(instance))
    }
    class ErrorModelCodeConstraints(override val instance: Int) extends ValidationBase[Int] {
        override def constraints: Seq[Constraint[Int]] =
        Seq(max(600, false), min(100, false))
    }
    class ErrorModelCodeValidator(override val instance: Int) extends RecursiveValidator[Int] {
      override val validators = Seq(new ErrorModelCodeConstraints(instance))
    }
    class ExtendedErrorModelRootCauseConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class ExtendedErrorModelRootCauseValidator(override val instance: String) extends RecursiveValidator[String] {
      override val validators = Seq(new ExtendedErrorModelRootCauseConstraints(instance))
    }
    class ErrorModelValidator(override val instance: ErrorModel) extends RecursiveValidator[ErrorModel] {
        override val validators = Seq(
            new ErrorModelMessageValidator(instance.message), 
            new ErrorModelCodeValidator(instance.code)
            )
        }
    class ExtendedErrorModelValidator(override val instance: ExtendedErrorModel) extends RecursiveValidator[ExtendedErrorModel] {
        override val validators = Seq(
            )
        }
    }
