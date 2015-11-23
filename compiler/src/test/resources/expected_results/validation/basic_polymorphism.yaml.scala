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
    class CatHuntingSkillValidator(override val instance: String) extends RecursiveValidator[String] {
      override val validators = Seq(new CatHuntingSkillConstraints(instance))
    }
    class DogPackSizeConstraints(override val instance: Int) extends ValidationBase[Int] {
        override def constraints: Seq[Constraint[Int]] =
        Seq(min(0, false))
    }
    class DogPackSizeValidator(override val instance: Int) extends RecursiveValidator[Int] {
      override val validators = Seq(new DogPackSizeConstraints(instance))
    }
    class PetNameConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class PetNameValidator(override val instance: String) extends RecursiveValidator[String] {
      override val validators = Seq(new PetNameConstraints(instance))
    }
    class PetPetTypeConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class PetPetTypeValidator(override val instance: String) extends RecursiveValidator[String] {
      override val validators = Seq(new PetPetTypeConstraints(instance))
    }
    class LabradorCutenessConstraints(override val instance: Int) extends ValidationBase[Int] {
        override def constraints: Seq[Constraint[Int]] =
        Seq(min(0, false))
    }
    class LabradorCutenessValidator(override val instance: Int) extends RecursiveValidator[Int] {
      override val validators = Seq(new LabradorCutenessConstraints(instance))
    }
    class CatValidator(override val instance: Cat) extends RecursiveValidator[Cat] {
        override val validators = Seq(
            )
        }
    class DogValidator(override val instance: Dog) extends RecursiveValidator[Dog] {
        override val validators = Seq(
            )
        }
    class CatNDogValidator(override val instance: CatNDog) extends RecursiveValidator[CatNDog] {
        override val validators = Seq(
            )
        }
    class PetValidator(override val instance: Pet) extends RecursiveValidator[Pet] {
        override val validators = Seq(
            new PetNameValidator(instance.name), 
            new PetPetTypeValidator(instance.petType)
            )
        }
    class LabradorValidator(override val instance: Labrador) extends RecursiveValidator[Labrador] {
        override val validators = Seq(
            )
        }
    }
