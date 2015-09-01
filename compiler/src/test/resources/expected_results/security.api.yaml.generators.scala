package security.api.yaml
import scala.Option
import org.scalacheck.Gen
import org.scalacheck.Arbitrary._
object generatorDefinitions {
  import definitions.Pet
  import definitions.ErrorModel
  def createPet = _generate(PetGenerator)
  def createErrorModel = _generate(ErrorModelGenerator)
  // test data generator for /definitions/Pet
  val PetGenerator =
    for {
      name <- arbitrary[String]
      tag <- Gen.option(arbitrary[String])
    } yield Pet(name, tag)
  // test data generator for /definitions/ErrorModel
  val ErrorModelGenerator =
    for {
      code <- arbitrary[Int]
      message <- arbitrary[String]
    } yield ErrorModel(code, message)
  def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
}