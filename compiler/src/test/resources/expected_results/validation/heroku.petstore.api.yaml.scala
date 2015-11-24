package heroku.petstore.api.yaml
import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._

object definitionsValidator {
    import definitions._
    class PetIdGetPetIdConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class PetIdGetPetIdValidator(instance: String) extends RecursiveValidator {
      override val validators = Seq(new PetIdGetPetIdConstraints(instance))
    }
    class PetNameValidator(instance: PetName) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class PetBirthdayValidator(instance: PetBirthday) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class PetValidator(instance: Pet) extends RecursiveValidator {
        override val validators = Seq(
            new PetNameValidator(instance.name), 
            new PetBirthdayValidator(instance.birthday)
            )
        }
    }
object pathsValidator {
    import definitions.Pet
    import definitions.PetBirthday
    import paths._
    import definitionsValidator.PetValidator
    import definitionsValidator.PetBirthdayValidator
    class PetIdGetPetIdConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class PetIdGetPetIdValidator(instance: String) extends RecursiveValidator {
      override val validators = Seq(new PetIdGetPetIdConstraints(instance))
    }
    class PostResponses200Constraints(override val instance: Null) extends ValidationBase[Null] {
        override def constraints: Seq[Constraint[Null]] =
        Seq()
    }
    class PostResponses200Validator(instance: Null) extends RecursiveValidator {
      override val validators = Seq(new PostResponses200Constraints(instance))
    }
    class GetResponses200OptValidator(instance: GetResponses200Opt) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class GetResponses200Validator(instance: GetResponses200) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class GetValidator(limit: PetBirthday) extends RecursiveValidator {
    override val validators = Seq(
    new PetBirthdayValidator(limit)
    )
    }
    class PutValidator(pet: Pet) extends RecursiveValidator {
    override val validators = Seq(
    new PetValidator(pet)
    )
    }
    class PostValidator(pet: Pet) extends RecursiveValidator {
    override val validators = Seq(
    new PetValidator(pet)
    )
    }
    class PetIdGetValidator(petId: String) extends RecursiveValidator {
    override val validators = Seq(
    new PetIdGetPetIdValidator(petId)
    )
    }
    }
