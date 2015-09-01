package heroku.petstore.api.yaml
import scala.Option
import org.scalacheck.Gen
import org.scalacheck.Arbitrary._
object generatorDefinitions {
  import definitions.Pet
  def genPet = _generate(PetGenerator)
  // test data generator for /definitions/Pet
  val PetGenerator =
    for {
      name <- Gen.option(arbitrary[String])
      birthday <- Gen.option(arbitrary[Int])
    } yield Pet(name, birthday)
  def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
}