package security.api.yaml

import org.scalacheck.Gen
import org.scalacheck.Arbitrary._

import de.zalando.play.controllers.ArrayWrapper
object Generators {
def createPetsIdGetIdGenerator = _generate(PetsIdGetIdGenerator)

    def createPetsIdGetResponsesDefaultGenerator = _generate(PetsIdGetResponsesDefaultGenerator)

    def createPetTagGenerator = _generate(PetTagGenerator)

    def createPetsIdGetResponses200Generator = _generate(PetsIdGetResponses200Generator)

    def createPetsIdGetResponses200OptGenerator = _generate(PetsIdGetResponses200OptGenerator)

    def PetsIdGetIdGenerator = _genList(arbitrary[String], "csv")

    def PetsIdGetResponsesDefaultGenerator = Gen.option(ErrorModelGenerator)

    def PetTagGenerator = Gen.option(arbitrary[String])

    def PetsIdGetResponses200Generator = Gen.option(PetsIdGetResponses200OptGenerator)

    def PetsIdGetResponses200OptGenerator = _genList(PetGenerator, "csv")

    def createErrorModelGenerator = _generate(ErrorModelGenerator)

    def createPetGenerator = _generate(PetGenerator)

    def ErrorModelGenerator = for {
        code <- arbitrary[Int]
        message <- arbitrary[String]
        } yield ErrorModel(code, message)

    def PetGenerator = for {
        name <- arbitrary[String]
        tag <- PetTagGenerator
        } yield Pet(name, tag)

    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
    def _genList[T](gen: Gen[T], format: String): Gen[ArrayWrapper[T]] = for {
        items <- Gen.containerOf[List,T](gen)
    } yield ArrayWrapper(format)(items)
}
