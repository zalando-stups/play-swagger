package security.api.yaml
import de.zalando.play.controllers._
import play.api.data.validation.Constraint

object definitionsValidator {
    import definitions._
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
    class PetNameConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class PetNameValidator(instance: String) extends RecursiveValidator {
      override val validators = Seq(new PetNameConstraints(instance))
    }
    class ErrorModelValidator(instance: ErrorModel) extends RecursiveValidator {
        override val validators = Seq(
            new ErrorModelCodeValidator(instance.code), 
            new ErrorModelMessageValidator(instance.message)
            )
        }
    class PetTagValidator(instance: PetTag) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class PetValidator(instance: Pet) extends RecursiveValidator {
        override val validators = Seq(
            new PetNameValidator(instance.name), 
            new PetTagValidator(instance.tag)
            )
        }
    }
object pathsValidator {
    import definitions._
    import definitionsValidator._
    import paths._
    class PetsIdGetResponsesDefaultValidator(instance: PetsIdGetResponsesDefault) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class PetsIdGetResponses200Validator(instance: PetsIdGetResponses200) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class PetsIdGetResponses200OptValidator(instance: PetsIdGetResponses200Opt) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class PetsIdGetValidator(id: PetTag) extends RecursiveValidator {
    override val validators = Seq(
    new PetTagValidator(id)
    )
    }
    }
