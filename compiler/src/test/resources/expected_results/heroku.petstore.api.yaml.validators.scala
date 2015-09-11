package heroku.petstore.api.yaml
import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._
import scala.Option
/**
*/
  import definitions.Pet
  package controllers {
   class ValidationForApiYamlallPets(limit: Option[Int]) {
  val limitConstraints = new ValidationBase[Int] {
      override def constraints: Seq[Constraint[Int]] = Seq(
       max(10000, false),
  min(11, false)
      )
    }
     val normalValidations = Seq.empty[scala.Either[scala.Seq[ParsingError], String]]
     val containerValidations = Seq(limit map limitConstraints.applyConstraints).flatten
     val rightResult = Right((limit))
     val allValidations = normalValidations ++ containerValidations
     val result = {
      val errors = allValidations.filter(_.isLeft).flatMap(_.left.get)
      if (errors.nonEmpty) Left(errors) else rightResult
     }
    }
   class ValidationForApiYamlupdatePet(pet: Pet) {
     val normalValidations = Seq(new PetValidation(pet).result)
     val containerValidations = Seq.empty[scala.Either[scala.Seq[ParsingError], String]]
     val rightResult = Right((pet))
     val allValidations = normalValidations ++ containerValidations
     val result = {
      val errors = allValidations.filter(_.isLeft).flatMap(_.left.get)
      if (errors.nonEmpty) Left(errors) else rightResult
     }
    }
   class ValidationForApiYamlcreatePet(pet: Pet) {
     val normalValidations = Seq(new PetValidation(pet).result)
     val containerValidations = Seq.empty[scala.Either[scala.Seq[ParsingError], String]]
     val rightResult = Right((pet))
     val allValidations = normalValidations ++ containerValidations
     val result = {
      val errors = allValidations.filter(_.isLeft).flatMap(_.left.get)
      if (errors.nonEmpty) Left(errors) else rightResult
     }
    }
   class ValidationForApiYamlgetPet(petId: String) {
  val petIdConstraints = new ValidationBase[String] {
      override def constraints: Seq[Constraint[String]] = Seq(
      )
    }
     val normalValidations = Seq(petIdConstraints.applyConstraints(petId))
     val containerValidations = Seq.empty[scala.Either[scala.Seq[ParsingError], String]]
     val rightResult = Right((petId))
     val allValidations = normalValidations ++ containerValidations
     val result = {
      val errors = allValidations.filter(_.isLeft).flatMap(_.left.get)
      if (errors.nonEmpty) Left(errors) else rightResult
     }
    }
  }
  class PetValidation(instance: Pet) {
    import de.zalando.play.controllers.PlayValidations._
    val allValidations = Seq.empty[scala.Either[scala.Seq[ParsingError], String]]
    val result = {
      val errors = allValidations.filter(_.isLeft).flatMap(_.left.get)
      if (errors.nonEmpty) Left(errors) else Right(instance)
    }
  }