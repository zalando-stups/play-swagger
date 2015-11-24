package basic_polymorphism.yaml
import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._

object definitionsValidator {
    import definitions._
    class CatHuntingSkillConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq(enum("clueless,lazy,adventurous,aggressive"))
    }
    class CatHuntingSkillValidator(instance: String) extends RecursiveValidator {
      override val validators = Seq(new CatHuntingSkillConstraints(instance))
    }
    class DogPackSizeConstraints(override val instance: Int) extends ValidationBase[Int] {
        override def constraints: Seq[Constraint[Int]] =
        Seq(min(0, false))
    }
    class DogPackSizeValidator(instance: Int) extends RecursiveValidator {
      override val validators = Seq(new DogPackSizeConstraints(instance))
    }
    class PetNameConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class PetNameValidator(instance: String) extends RecursiveValidator {
      override val validators = Seq(new PetNameConstraints(instance))
    }
    class PetPetTypeConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class PetPetTypeValidator(instance: String) extends RecursiveValidator {
      override val validators = Seq(new PetPetTypeConstraints(instance))
    }
    class LabradorCutenessConstraints(override val instance: Int) extends ValidationBase[Int] {
        override def constraints: Seq[Constraint[Int]] =
        Seq(min(0, false))
    }
    class LabradorCutenessValidator(instance: Int) extends RecursiveValidator {
      override val validators = Seq(new LabradorCutenessConstraints(instance))
    }
    class CatValidator(instance: Cat) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class DogValidator(instance: Dog) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class CatNDogValidator(instance: CatNDog) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class PetValidator(instance: Pet) extends RecursiveValidator {
        override val validators = Seq(
            new PetNameValidator(instance.name), 
            new PetPetTypeValidator(instance.petType)
            )
        }
    class LabradorValidator(instance: Labrador) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    }
