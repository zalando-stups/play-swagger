package echo.api.yaml
import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._

object pathsValidator {
    import paths._
    class `Test-pathIGetResponses200Constraints`(override val instance: Null) extends ValidationBase[Null] {
        override def constraints: Seq[Constraint[Null]] =
        Seq()
    }
    class `Test-pathIGetResponses200Validator`(override val instance: Null) extends RecursiveValidator[Null] {
      override val validators = Seq(new `Test-pathIGetResponses200Constraints`(instance))
    }
    class `Test-pathIGetIdConstraints`(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class `Test-pathIGetIdValidator`(override val instance: String) extends RecursiveValidator[String] {
      override val validators = Seq(new `Test-pathIGetIdConstraints`(instance))
    }
    class PostNameValidator(override val instance: PostName) extends RecursiveValidator[PostName] {
        override val validators = Seq(
            )
        }
    }
