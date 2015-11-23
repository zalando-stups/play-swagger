package security.api.yaml
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
    class PetNameConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class PetNameValidator(override val instance: String) extends RecursiveValidator[String] {
      override val validators = Seq(new PetNameConstraints(instance))
    }
    class ErrorModelValidator(override val instance: ErrorModel) extends RecursiveValidator[ErrorModel] {
        override val validators = Seq(
            new ErrorModelCodeValidator(instance.code), 
            new ErrorModelMessageValidator(instance.message)
            )
        }
    class PetTagValidator(override val instance: PetTag) extends RecursiveValidator[PetTag] {
        override val validators = Seq(
            )
        }
    class PetValidator(override val instance: Pet) extends RecursiveValidator[Pet] {
        override val validators = Seq(
            new PetNameValidator(instance.name), 
            new PetTagValidator(instance.tag)
            )
        }
    }
object pathsValidator {
    import definitions.ErrorModel
    import definitions.Pet
    import paths._
    import definitionsValidator.ErrorModelValidator
    import definitionsValidator.PetValidator
    class PetsIGetResponsesDefaultValidator(override val instance: PetsIGetResponsesDefault) extends RecursiveValidator[PetsIGetResponsesDefault] {
        override val validators = Seq(
            )
        }
    class PetsIGetResponses200Validator(override val instance: PetsIGetResponses200) extends RecursiveValidator[PetsIGetResponses200] {
        override val validators = Seq(
            )
        }
    class PetsIGetResponses200OptValidator(override val instance: PetsIGetResponses200Opt) extends RecursiveValidator[PetsIGetResponses200Opt] {
        override val validators = Seq(
            )
        }
    }
