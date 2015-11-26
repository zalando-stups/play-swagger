package echo.api.yaml
import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._

object pathsValidator {
    import paths.PostName
    class `Test-pathIdGetResponses200Constraints`(override val instance: Null) extends ValidationBase[Null] {
        override def constraints: Seq[Constraint[Null]] =
        Seq()
    }
    class `Test-pathIdGetResponses200Validator`(instance: Null) extends RecursiveValidator {
      override val validators = Seq(new `Test-pathIdGetResponses200Constraints`(instance))
    }
    class `Test-pathIdGetIdConstraints`(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class `Test-pathIdGetIdValidator`(instance: String) extends RecursiveValidator {
      override val validators = Seq(new `Test-pathIdGetIdConstraints`(instance))
    }
    class PostNameValidator(instance: PostName) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class PostValidator(name: PostName, year: PostName) extends RecursiveValidator {
    override val validators = Seq(
    new PostNameValidator(name), 
    new PostNameValidator(year)
    )
    }
    }
