package simple.petstore.api.yaml
import scala.Option
import org.scalacheck.Gen
import org.scalacheck.Arbitrary._
object generatorDefinitions {
  import definitions.Pet
  import definitions.NewPet
  import definitions.ErrorModel
  def genPet = _generate(PetGenerator)
  def genNewPet = _generate(NewPetGenerator)
  def genErrorModel = _generate(ErrorModelGenerator)
  // test data generator for /definitions/pet
  val PetGenerator =
    for {
      id <- arbitrary[Long]
      name <- arbitrary[String]
      tag <- Gen.option(arbitrary[String])
    } yield Pet(id, name, tag)
  // test data generator for /definitions/newPet
  val NewPetGenerator =
    for {
      id <- Gen.option(arbitrary[Long])
      name <- arbitrary[String]
      tag <- Gen.option(arbitrary[String])
    } yield NewPet(id, name, tag)
  // test data generator for /definitions/errorModel
  val ErrorModelGenerator =
    for {
      code <- arbitrary[Int]
      message <- arbitrary[String]
    } yield ErrorModel(code, message)
  def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
}