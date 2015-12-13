package simple.petstore.api.yaml
import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._

class NewPetTagConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class NewPetTagValidator(instance: String) extends RecursiveValidator {
      override val validators = Seq(new NewPetTagConstraints(instance))

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
    class NewPetIdConstraints(override val instance: Long) extends ValidationBase[Long] {
        override def constraints: Seq[Constraint[Long]] =
        Seq()
    }
    class NewPetIdValidator(instance: Long) extends RecursiveValidator {
      override val validators = Seq(new NewPetIdConstraints(instance))

    }
    class NewPetNameConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class NewPetNameValidator(instance: String) extends RecursiveValidator {
      override val validators = Seq(new NewPetNameConstraints(instance))

    }
    class ErrorModelValidator(instance: ErrorModel) extends RecursiveValidator {
        override val validators = Seq(
            new ErrorModelCodeValidator(instance.code), 

            new ErrorModelMessageValidator(instance.message)

            ) ++ Seq(
            ).flatten
        }
    class PetValidator(instance: Pet) extends RecursiveValidator {
        override val validators = Seq(
            new PetIdValidator(instance.id), 

            new PetNameValidator(instance.name), 

            ) ++ Seq(
            instance.tag.toSeq map { new NewPetTagValidator(_)}, ).flatten
        }
    class NewPetValidator(instance: NewPet) extends RecursiveValidator {
        override val validators = Seq(
            new NewPetNameValidator(instance.name), 

            ) ++ Seq(
            instance.id.toSeq map { new NewPetIdValidator(_)}, instance.tag.toSeq map { new NewPetTagValidator(_)}, ).flatten
        }
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
    class PetsGetLimitConstraints(override val instance: Int) extends ValidationBase[Int] {
        override def constraints: Seq[Constraint[Int]] =
        Seq(max(200.toInt, false), min(1.toInt, false))
    }
    class PetsGetLimitValidator(instance: Int) extends RecursiveValidator {
      override val validators = Seq(new PetsGetLimitConstraints(instance))

    }
    class PetsIdGetIdConstraints(override val instance: Long) extends ValidationBase[Long] {
        override def constraints: Seq[Constraint[Long]] =
        Seq()
    }
    class PetsIdGetIdValidator(instance: Long) extends RecursiveValidator {
      override val validators = Seq(new PetsIdGetIdConstraints(instance))

    }
    class PetsGetTagsOptConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class PetsGetTagsOptValidator(instance: String) extends RecursiveValidator {
      override val validators = Seq(new PetsGetTagsOptConstraints(instance))

    }
    class PetsGetValidator(tags: PetsGetTags, limit: PetsGetLimit) extends RecursiveValidator {
    override val validators = Seq(
    )  ++ Seq(
        tags.toSeq map { new PetsGetTagsValidator(_)}, limit.toSeq map { new PetsGetLimitValidator(_)}).flatten
    }
    class PetsPostValidator(pet: NewPet) extends RecursiveValidator {
    override val validators = Seq(
    new NewPetValidator(pet)

    )  ++ Seq(
        ).flatten
    }
    class PetsIdGetValidator(id: Long) extends RecursiveValidator {
    override val validators = Seq(
    new PetsIdGetIdValidator(id)

    )  ++ Seq(
        ).flatten
    }
    class PetsIdDeleteValidator(id: Long) extends RecursiveValidator {
    override val validators = Seq(
    new PetsIdDeleteIdValidator(id)

    )  ++ Seq(
        ).flatten
    }
    