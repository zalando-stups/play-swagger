package simple.petstore.api.yaml

import org.scalacheck.Gen
import org.scalacheck.Arbitrary
import Arbitrary._

import de.zalando.play.controllers.ArrayWrapper
object Generators {
def createPetsIdDeleteResponsesDefaultGenerator = _generate(PetsIdDeleteResponsesDefaultGenerator)

    def createPetsPostResponses200Generator = _generate(PetsPostResponses200Generator)

    def createNullGenerator = _generate(NullGenerator)

    def createLongGenerator = _generate(LongGenerator)

    def createPetsGetLimitGenerator = _generate(PetsGetLimitGenerator)

    def createNewPetIdGenerator = _generate(NewPetIdGenerator)

    def createPetsGetTagsOptGenerator = _generate(PetsGetTagsOptGenerator)

    def createPetsGetResponses200OptGenerator = _generate(PetsGetResponses200OptGenerator)

    def createPetTagGenerator = _generate(PetTagGenerator)

    def createPetsGetResponses200Generator = _generate(PetsGetResponses200Generator)

    def createPetsGetTagsGenerator = _generate(PetsGetTagsGenerator)

    def PetsIdDeleteResponsesDefaultGenerator = Gen.option(ErrorModelGenerator)

    def PetsPostResponses200Generator = Gen.option(PetGenerator)

    def NullGenerator = arbitrary[Null]

    def LongGenerator = arbitrary[Long]

    def PetsGetLimitGenerator = Gen.option(arbitrary[Int])

    def NewPetIdGenerator = Gen.option(arbitrary[Long])

    def PetsGetTagsOptGenerator = _genList(arbitrary[String], "csv")

    def PetsGetResponses200OptGenerator = _genList(PetGenerator, "csv")

    def PetTagGenerator = Gen.option(arbitrary[String])

    def PetsGetResponses200Generator = Gen.option(PetsGetResponses200OptGenerator)

    def PetsGetTagsGenerator = Gen.option(PetsGetTagsOptGenerator)

    def createErrorModelGenerator = _generate(ErrorModelGenerator)

    def createPetGenerator = _generate(PetGenerator)

    def createNewPetGenerator = _generate(NewPetGenerator)

    def ErrorModelGenerator = for {
        code <- arbitrary[Int]
        message <- arbitrary[String]
        } yield ErrorModel(code, message)

    def PetGenerator = for {
        id <- arbitrary[Long]
        name <- arbitrary[String]
        tag <- PetTagGenerator
        } yield Pet(id, name, tag)

    def NewPetGenerator = for {
        name <- arbitrary[String]
        id <- NewPetIdGenerator
        tag <- PetTagGenerator
        } yield NewPet(name, id, tag)

    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
    def _genList[T](gen: Gen[T], format: String): Gen[ArrayWrapper[T]] = for {
        items <- Gen.containerOf[List,T](gen)
    } yield ArrayWrapper(format)(items)
}
