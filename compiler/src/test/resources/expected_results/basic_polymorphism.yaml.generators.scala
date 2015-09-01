package basic_polymorphism.yaml
import org.scalacheck.Gen
import org.scalacheck.Arbitrary._
import scala.Option
object generatorDefinitions {
  import definitions.Pet
  import definitions.Cat
  import definitions.Dog
  import definitions.Labrador
  def createPet = _generate(PetGenerator)
  def createCat = _generate(CatGenerator)
  def createDog = _generate(DogGenerator)
  def createLabrador = _generate(LabradorGenerator)
  // test data generator for /definitions/Pet
  val PetGenerator =
    for {
      name <- arbitrary[String]
      petType <- arbitrary[String]
    } yield Pet(name, petType)
  // test data generator for /definitions/Cat
  val CatGenerator =
    for {
      huntingSkill <- Gen.option(arbitrary[String])
      name <- arbitrary[String]
      petType <- arbitrary[String]
    } yield Cat(huntingSkill, name, petType)
  // test data generator for /definitions/Dog
  val DogGenerator =
    for {
      packSize <- Gen.option(arbitrary[Int])
      name <- arbitrary[String]
      petType <- arbitrary[String]
    } yield Dog(packSize, name, petType)
  // test data generator for /definitions/Labrador
  val LabradorGenerator =
    for {
      cuteness <- Gen.option(arbitrary[Int])
      packSize <- Gen.option(arbitrary[Int])
      name <- arbitrary[String]
      petType <- arbitrary[String]
    } yield Labrador(cuteness, packSize, name, petType)
  def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
}