package simple.petstore.api.yaml
import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._

object definitionsValidator {
    import definitions._
    import pathsValidator.{PetsIdDeleteIdValidator, PetsIdGetIdValidator}
    class PetsIdDeleteIdConstraints(override val instance: Long) extends ValidationBase[Long] {
        override def constraints: Seq[Constraint[Long]] =
        Seq()
    }
    class PetsIdDeleteIdValidator(instance: Long) extends RecursiveValidator {
      override val validators = Seq(new PetsIdDeleteIdConstraints(instance))
    }
    class PetsIdGetIdConstraints(override val instance: Long) extends ValidationBase[Long] {
        override def constraints: Seq[Constraint[Long]] =
        Seq()
    }
    class PetsIdGetIdValidator(instance: Long) extends RecursiveValidator {
      override val validators = Seq(new PetsIdGetIdConstraints(instance))
    }
    class ErrorModelCodeConstraints(override val instance: Int) extends ValidationBase[Int] {
        override def constraints: Seq[Constraint[Int]] =
        Seq()
    }
    class ErrorModelCodeValidator(instance: Int) extends RecursiveValidator {
      override val validators = Seq(new ErrorModelCodeConstraints(instance))
    }
    class ErrorModelMessageConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class ErrorModelMessageValidator(instance: String) extends RecursiveValidator {
      override val validators = Seq(new ErrorModelMessageConstraints(instance))
    }
    class PetIdConstraints(override val instance: Long) extends ValidationBase[Long] {
        override def constraints: Seq[Constraint[Long]] =
        Seq()
    }
    class PetIdValidator(instance: Long) extends RecursiveValidator {
      override val validators = Seq(new PetIdConstraints(instance))
    }
    class PetNameConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class PetNameValidator(instance: String) extends RecursiveValidator {
      override val validators = Seq(new PetNameConstraints(instance))
    }
    class NewPetNameConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class NewPetNameValidator(instance: String) extends RecursiveValidator {
      override val validators = Seq(new NewPetNameConstraints(instance))
    }
    class NewPetTagValidator(instance: NewPetTag) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class ErrorModelValidator(instance: ErrorModel) extends RecursiveValidator {
        override val validators = Seq(
            new ErrorModelCodeValidator(instance.code), 
            new ErrorModelMessageValidator(instance.message)
            )
        }
    class PetValidator(instance: Pet) extends RecursiveValidator {
        override val validators = Seq(
            new PetIdValidator(instance.id), 
            new PetNameValidator(instance.name), 
            new NewPetTagValidator(instance.tag)
            )
        }
    class NewPetIdValidator(instance: NewPetId) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class NewPetValidator(instance: NewPet) extends RecursiveValidator {
        override val validators = Seq(
            new NewPetNameValidator(instance.name), 
            new NewPetIdValidator(instance.id), 
            new NewPetTagValidator(instance.tag)
            )
        }
    }
object pathsValidator {
    import definitions._
    import paths._
    import definitionsValidator.NewPetValidator
    class PetsIdDeleteResponses204Constraints(override val instance: Null) extends ValidationBase[Null] {
        override def constraints: Seq[Constraint[Null]] =
        Seq()
    }
    class PetsIdDeleteResponses204Validator(instance: Null) extends RecursiveValidator {
      override val validators = Seq(new PetsIdDeleteResponses204Constraints(instance))
    }
    class PetsIdDeleteIdConstraints(override val instance: Long) extends ValidationBase[Long] {
        override def constraints: Seq[Constraint[Long]] =
        Seq()
    }
    class PetsIdDeleteIdValidator(instance: Long) extends RecursiveValidator {
      override val validators = Seq(new PetsIdDeleteIdConstraints(instance))
    }
    class PetsIdGetIdConstraints(override val instance: Long) extends ValidationBase[Long] {
        override def constraints: Seq[Constraint[Long]] =
        Seq()
    }
    class PetsIdGetIdValidator(instance: Long) extends RecursiveValidator {
      override val validators = Seq(new PetsIdGetIdConstraints(instance))
    }
    class PetsIdDeleteResponsesDefaultValidator(instance: PetsIdDeleteResponsesDefault) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class PetsPostResponses200Validator(instance: PetsPostResponses200) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class PetsGetLimitValidator(instance: PetsGetLimit) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class PetsGetResponses200Validator(instance: PetsGetResponses200) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class PetsGetTagsValidator(instance: PetsGetTags) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class PetsGetValidator(tags: PetsGetTags, limit: PetsGetLimit) extends RecursiveValidator {
    override val validators = Seq(
    new PetsGetTagsValidator(tags), 
    new PetsGetLimitValidator(limit)
    )
    }
    class PetsPostValidator(pet: NewPet) extends RecursiveValidator {
    override val validators = Seq(
    new NewPetValidator(pet)
    )
    }
    class PetsIdGetValidator(id: Long) extends RecursiveValidator {
    override val validators = Seq(
    new PetsIdGetIdValidator(id)
    )
    }
    class PetsIdDeleteValidator(id: Long) extends RecursiveValidator {
    override val validators = Seq(
    new PetsIdDeleteIdValidator(id)
    )
    }
    }
