package echo
import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._
// ----- constraints and wrapper validations -----
class PostNameOptConstraints(override val instance: String) extends ValidationBase[String] {
    override def constraints: Seq[Constraint[String]] =
        Seq()
}
class PostNameOptValidator(instance: String) extends RecursiveValidator {
    override val validators = Seq(new PostNameOptConstraints(instance))

}
class `Test-pathIdGetIdConstraints`(override val instance: String) extends ValidationBase[String] {
    override def constraints: Seq[Constraint[String]] =
        Seq()
}
class `Test-pathIdGetIdValidator`(instance: String) extends RecursiveValidator {
    override val validators = Seq(new `Test-pathIdGetIdConstraints`(instance))

}
// ----- complex type validators -----
// ----- option delegating validators -----
class PostNameValidator(instance: PostName) extends RecursiveValidator {
    override val validators = instance.toSeq.map { new PostNameOptValidator(_) }
}
// ----- array delegating validators -----
// ----- catch all simple validators -----
// ----- call validations -----
class `Test-pathIdGetValidator`(id: String) extends RecursiveValidator {
    override val validators = Seq(
        new `Test-pathIdGetIdValidator`(id)
    )
}
class PostValidator(name: PostName, year: PostName) extends RecursiveValidator {
    override val validators = Seq(
        new PostNameValidator(name),
        new PostNameValidator(year)
    )
}
