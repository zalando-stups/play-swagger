package echo.api.yaml
import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._

object pathsValidator {
    import paths.PostName
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
    class `Test-pathIdGetValidator`(id: String) extends RecursiveValidator {
    override val validators = Seq(
    new `Test-pathIdGetIdValidator`(id)
    )
    }
    }
