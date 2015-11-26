package heroku.petstore.api.yaml
import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._

object definitionsValidator {
    import definitions.{Pet, PetBirthday, PetName}
    import pathsValidator.PetIdGetPetIdValidator
    class PetIdGetPetIdConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class PetIdGetPetIdValidator(instance: String) extends RecursiveValidator {
      override val validators = Seq(new PetIdGetPetIdConstraints(instance))
    }
    }
object pathsValidator {
    import definitions.{Pet, PetBirthday}
    import paths.GetResponses200Opt
    import definitionsValidator.{PetBirthdayValidator, PetValidator}
    class PetIdGetPetIdConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class PetIdGetPetIdValidator(instance: String) extends RecursiveValidator {
      override val validators = Seq(new PetIdGetPetIdConstraints(instance))
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
