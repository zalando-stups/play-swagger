package heroku.petstore.api.yaml
import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._

object definitionsValidator {
    import definitions._
    class PetNameValidator(override val instance: PetName) extends RecursiveValidator[PetName] {
        override val validators = Seq(
            )
        }
    class PetBirthdayValidator(override val instance: PetBirthday) extends RecursiveValidator[PetBirthday] {
        override val validators = Seq(
            )
        }
    class PetValidator(override val instance: Pet) extends RecursiveValidator[Pet] {
        override val validators = Seq(
            new PetNameValidator(instance.name), 
            new PetBirthdayValidator(instance.birthday)
            )
        }
    }
object pathsValidator {
    import definitions.Pet
    import paths._
    import definitionsValidator.PetValidator
    class PetIGetPetIdConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class PetIGetPetIdValidator(override val instance: String) extends RecursiveValidator[String] {
      override val validators = Seq(new PetIGetPetIdConstraints(instance))
    }
    class PostResponses200Constraints(override val instance: Null) extends ValidationBase[Null] {
        override def constraints: Seq[Constraint[Null]] =
        Seq()
    }
    class PostResponses200Validator(override val instance: Null) extends RecursiveValidator[Null] {
      override val validators = Seq(new PostResponses200Constraints(instance))
    }
    class GetResponses200OptValidator(override val instance: GetResponses200Opt) extends RecursiveValidator[GetResponses200Opt] {
        override val validators = Seq(
            )
        }
    class GetResponses200Validator(override val instance: GetResponses200) extends RecursiveValidator[GetResponses200] {
        override val validators = Seq(
            )
        }
    }
