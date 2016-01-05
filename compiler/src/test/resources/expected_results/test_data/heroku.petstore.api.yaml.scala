package heroku.petstore.api.yaml

import org.scalacheck.Gen
import org.scalacheck.Arbitrary._

import de.zalando.play.controllers.ArrayWrapper
object Generators {
def createPetNameGenerator = _generate(PetNameGenerator)

    def createStringGenerator = _generate(StringGenerator)

    def createGetResponses200OptGenerator = _generate(GetResponses200OptGenerator)

    def createNullGenerator = _generate(NullGenerator)

    def createPetBirthdayGenerator = _generate(PetBirthdayGenerator)

    def createGetResponses200Generator = _generate(GetResponses200Generator)

    def PetNameGenerator = Gen.option(arbitrary[String])

    def StringGenerator = arbitrary[String]

    def GetResponses200OptGenerator = _genList(PetGenerator, "csv")

    def NullGenerator = arbitrary[Null]

    def PetBirthdayGenerator = Gen.option(arbitrary[Int])

    def GetResponses200Generator = Gen.option(GetResponses200OptGenerator)

    def createPetGenerator = _generate(PetGenerator)

    def PetGenerator = for {
        name <- PetNameGenerator
        birthday <- PetBirthdayGenerator
        } yield Pet(name, birthday)

    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
    def _genList[T](gen: Gen[T], format: String): Gen[ArrayWrapper[T]] = for {
        items <- Gen.containerOf[List,T](gen)
    } yield ArrayWrapper(format)(items)
}
