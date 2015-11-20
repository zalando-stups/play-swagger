package heroku.petstore.api.yaml
import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._

object definitionsValidator {
    import definitions.Pet
    val PetNameConstraints = new ValidationBase[String] {
            override def constraints: Seq[Constraint[String]] =
                Seq(maxLength(100), minLength(3))
        }
        
    val PetBirthdayConstraints = new ValidationBase[Int] {
            override def constraints: Seq[Constraint[Int]] =
                Seq(max(100, false), min(1, false))
        }
        
    class PetValidator(instance: Pet) {

            val topValidations = Seq(
            instance.name map PetNameConstraints.applyConstraints, 
            instance.birthday map PetBirthdayConstraints.applyConstraints
            )
            val flatValidations = Seq[scala.Either[scala.Seq[ParsingError], Any]](
                )
            val allValidations = topValidations.flatten ++ flatValidations
            val result = {
                val errors = allValidations.filter(_.isLeft).flatMap(_.left.get)
                if (errors.nonEmpty) Left(errors) else Right(instance)
            }
        }
    }
object pathsValidator {
    import definitions.Pet
    import paths.GetResponses200Opt
    import definitionsValidator.PetValidator
    }
