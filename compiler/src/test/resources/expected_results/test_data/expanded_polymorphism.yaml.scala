package expanded_polymorphism.yaml

import org.scalacheck.Gen
import org.scalacheck.Arbitrary
import Arbitrary._

import de.zalando.play.controllers.ArrayWrapper
object Generators {
def createPetsIdDeleteResponsesDefaultGenerator = _generate(PetsIdDeleteResponsesDefaultGenerator)

    def createNullGenerator = _generate(NullGenerator)

    def createLongGenerator = _generate(LongGenerator)

    def createPetsGetLimitGenerator = _generate(PetsGetLimitGenerator)

    def createNewPetTagGenerator = _generate(NewPetTagGenerator)

    def createPetsGetTagsOptGenerator = _generate(PetsGetTagsOptGenerator)

    def createPetsGetResponses200OptGenerator = _generate(PetsGetResponses200OptGenerator)

    def createPetsGetResponses200Generator = _generate(PetsGetResponses200Generator)

    def createPetsGetTagsGenerator = _generate(PetsGetTagsGenerator)

    def PetsIdDeleteResponsesDefaultGenerator = Gen.option(ErrorGenerator)

    def NullGenerator = arbitrary[Null]

    def LongGenerator = arbitrary[Long]

    def PetsGetLimitGenerator = Gen.option(arbitrary[Int])

    def NewPetTagGenerator = Gen.option(arbitrary[String])

    def PetsGetTagsOptGenerator = _genList(arbitrary[String], "csv")

    def PetsGetResponses200OptGenerator = _genList(PetGenerator, "csv")

    def PetsGetResponses200Generator = Gen.option(PetsGetResponses200OptGenerator)

    def PetsGetTagsGenerator = Gen.option(PetsGetTagsOptGenerator)

    def createNewPetGenerator = _generate(NewPetGenerator)

    def createPetGenerator = _generate(PetGenerator)

    def createErrorGenerator = _generate(ErrorGenerator)

    def NewPetGenerator = for {
        name <- arbitrary[String]
        tag <- NewPetTagGenerator
        } yield NewPet(name, tag)

    def PetGenerator = for {
        name <- arbitrary[String]
        tag <- NewPetTagGenerator
        id <- arbitrary[Long]
        } yield Pet(name, tag, id)

    def ErrorGenerator = for {
        code <- arbitrary[Int]
        message <- arbitrary[String]
        } yield Error(code, message)

    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
    def _genList[T](gen: Gen[T], format: String): Gen[ArrayWrapper[T]] = for {
        items <- Gen.containerOf[List,T](gen)
    } yield ArrayWrapper(format)(items)
}
