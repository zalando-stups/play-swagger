package string_formats.yaml
import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._
import de.zalando.play.controllers.Base64String
import Base64String._
import de.zalando.play.controllers.BinaryString
import BinaryString._
import org.joda.time.DateTime
import org.joda.time.DateMidnight
// ----- constraints and wrapper validations -----
class GetBase64OptConstraints(override val instance: Base64String) extends ValidationBase[Base64String] {
    override def constraints: Seq[Constraint[Base64String]] =
        Seq()
}
class GetBase64OptValidator(instance: Base64String) extends RecursiveValidator {
    override val validators = Seq(new GetBase64OptConstraints(instance))
}
class GetPetIdConstraints(override val instance: BinaryString) extends ValidationBase[BinaryString] {
    override def constraints: Seq[Constraint[BinaryString]] =
        Seq()
}
class GetPetIdValidator(instance: BinaryString) extends RecursiveValidator {
    override val validators = Seq(new GetPetIdConstraints(instance))
}
class GetDate_timeOptConstraints(override val instance: DateTime) extends ValidationBase[DateTime] {
    override def constraints: Seq[Constraint[DateTime]] =
        Seq()
}
class GetDate_timeOptValidator(instance: DateTime) extends RecursiveValidator {
    override val validators = Seq(new GetDate_timeOptConstraints(instance))
}
class GetDateOptConstraints(override val instance: DateMidnight) extends ValidationBase[DateMidnight] {
    override def constraints: Seq[Constraint[DateMidnight]] =
        Seq()
}
class GetDateOptValidator(instance: DateMidnight) extends RecursiveValidator {
    override val validators = Seq(new GetDateOptConstraints(instance))
}
// ----- complex type validators -----
// ----- option delegating validators -----
class GetBase64Validator(instance: GetBase64) extends RecursiveValidator {
    override val validators = instance.toSeq.map { new GetBase64OptValidator(_) }
}
class GetDate_timeValidator(instance: GetDate_time) extends RecursiveValidator {
    override val validators = instance.toSeq.map { new GetDate_timeOptValidator(_) }
}
class GetDateValidator(instance: GetDate) extends RecursiveValidator {
    override val validators = instance.toSeq.map { new GetDateOptValidator(_) }
}
// ----- array delegating validators -----
// ----- catch all simple validators -----
// ----- call validations -----
class GetValidator(petId: BinaryString, base64: GetBase64, date: GetDate, date_time: GetDate_time) extends RecursiveValidator {
    override val validators = Seq(
        new GetPetIdValidator(petId), 
    
        new GetBase64Validator(base64), 
    
        new GetDateValidator(date), 
    
        new GetDate_timeValidator(date_time)
    
    )
}
