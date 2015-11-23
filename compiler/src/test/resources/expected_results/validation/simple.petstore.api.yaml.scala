package simple.petstore.api.yaml
import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._

object definitionsValidator {
    import definitions._
    class ErrorModelCodeConstraints(override val instance: Int) extends ValidationBase[Int] {
        override def constraints: Seq[Constraint[Int]] =
        Seq()
    }
    class ErrorModelCodeValidator(override val instance: Int) extends RecursiveValidator[Int] {
      override val validators = Seq(new ErrorModelCodeConstraints(instance))
    }
    class ErrorModelMessageConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class ErrorModelMessageValidator(override val instance: String) extends RecursiveValidator[String] {
      override val validators = Seq(new ErrorModelMessageConstraints(instance))
    }
    class PetIdConstraints(override val instance: Long) extends ValidationBase[Long] {
        override def constraints: Seq[Constraint[Long]] =
        Seq()
    }
    class PetIdValidator(override val instance: Long) extends RecursiveValidator[Long] {
      override val validators = Seq(new PetIdConstraints(instance))
    }
    class PetNameConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class PetNameValidator(override val instance: String) extends RecursiveValidator[String] {
      override val validators = Seq(new PetNameConstraints(instance))
    }
    class NewPetNameConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class NewPetNameValidator(override val instance: String) extends RecursiveValidator[String] {
      override val validators = Seq(new NewPetNameConstraints(instance))
    }
    class NewPetTagValidator(override val instance: NewPetTag) extends RecursiveValidator[NewPetTag] {
        override val validators = Seq(
            )
        }
    class ErrorModelValidator(override val instance: ErrorModel) extends RecursiveValidator[ErrorModel] {
        override val validators = Seq(
            new ErrorModelCodeValidator(instance.code), 
            new ErrorModelMessageValidator(instance.message)
            )
        }
    class PetValidator(override val instance: Pet) extends RecursiveValidator[Pet] {
        override val validators = Seq(
            new PetIdValidator(instance.id), 
            new PetNameValidator(instance.name), 
            new NewPetTagValidator(instance.tag)
            )
        }
    class NewPetIdValidator(override val instance: NewPetId) extends RecursiveValidator[NewPetId] {
        override val validators = Seq(
            )
        }
    class NewPetValidator(override val instance: NewPet) extends RecursiveValidator[NewPet] {
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
    import definitionsValidator._
    class PetsIDeleteResponses204Constraints(override val instance: Null) extends ValidationBase[Null] {
        override def constraints: Seq[Constraint[Null]] =
        Seq()
    }
    class PetsIDeleteResponses204Validator(override val instance: Null) extends RecursiveValidator[Null] {
      override val validators = Seq(new PetsIDeleteResponses204Constraints(instance))
    }
    class PetsIDeleteIdConstraints(override val instance: Long) extends ValidationBase[Long] {
        override def constraints: Seq[Constraint[Long]] =
        Seq()
    }
    class PetsIDeleteIdValidator(override val instance: Long) extends RecursiveValidator[Long] {
      override val validators = Seq(new PetsIDeleteIdConstraints(instance))
    }
    class PetsIDeleteResponsesDefaultValidator(override val instance: PetsIDeleteResponsesDefault) extends RecursiveValidator[PetsIDeleteResponsesDefault] {
        override val validators = Seq(
            )
        }
    class PetsPostResponses200Validator(override val instance: PetsPostResponses200) extends RecursiveValidator[PetsPostResponses200] {
        override val validators = Seq(
            )
        }
    class PetsGetLimitValidator(override val instance: PetsGetLimit) extends RecursiveValidator[PetsGetLimit] {
        override val validators = Seq(
            )
        }
    class PetsGetResponses200Validator(override val instance: PetsGetResponses200) extends RecursiveValidator[PetsGetResponses200] {
        override val validators = Seq(
            )
        }
    class PetsGetTagsValidator(override val instance: PetsGetTags) extends RecursiveValidator[PetsGetTags] {
        override val validators = Seq(
            )
        }
    }
