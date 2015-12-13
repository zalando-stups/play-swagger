package simple.petstore.api.yaml
import org.scalacheck.Gen
import org.scalacheck.Arbitrary._
import java.util.Date
import java.io.File

object definitionsGenerator {
    def createNewPetTagGenerator = _generate(NewPetTagGenerator)

    def createNewPetIdGenerator = _generate(NewPetIdGenerator)

    def NewPetTagGenerator = Gen.option(arbitrary[String])

    def NewPetIdGenerator = Gen.option(arbitrary[Long])

    def createErrorModelGenerator = _generate(ErrorModelGenerator)

    def createPetGenerator = _generate(PetGenerator)

    def createNewPetGenerator = _generate(NewPetGenerator)

    def ErrorModelGenerator =

        for {

        code <- arbitrary[Int]

        message <- arbitrary[String]

        } yield ErrorModel(code, message)

    

    def PetGenerator =

        for {

        id <- arbitrary[Long]

        name <- arbitrary[String]

        tag <- NewPetTagGenerator

        } yield Pet(id, name, tag)

    

    def NewPetGenerator =

        for {

        name <- arbitrary[String]

        id <- NewPetIdGenerator

        tag <- NewPetTagGenerator

        } yield NewPet(name, id, tag)

    

    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample

    def _genMap[K,V](keyGen: Gen[K], valGen: Gen[V]): Gen[Map[K,V]] = for {

        keys <- Gen.containerOf[List,K](keyGen)

        values <- Gen.containerOfN[List,V](keys.size, valGen)

    } yield keys.zip(values).toMap

}
object pathsGenerator {
    import definitionsGenerator.{ErrorModelGenerator, NewPetGenerator, PetGenerator}
    def createPetsIdDeleteResponsesDefaultGenerator = _generate(PetsIdDeleteResponsesDefaultGenerator)

    def createPetsPostResponses200Generator = _generate(PetsPostResponses200Generator)

    def createPetsIdDeleteResponses204Generator = _generate(PetsIdDeleteResponses204Generator)

    def createPetsIdDeleteIdGenerator = _generate(PetsIdDeleteIdGenerator)

    def createPetsGetLimitGenerator = _generate(PetsGetLimitGenerator)

    def createPetsGetTagsOptGenerator = _generate(PetsGetTagsOptGenerator)

    def createPetsGetResponses200OptGenerator = _generate(PetsGetResponses200OptGenerator)

    def createPetsGetResponses200Generator = _generate(PetsGetResponses200Generator)

    def createPetsGetTagsGenerator = _generate(PetsGetTagsGenerator)

    def PetsIdDeleteResponsesDefaultGenerator = Gen.option(ErrorModelGenerator)

    def PetsPostResponses200Generator = Gen.option(PetGenerator)

    def PetsIdDeleteResponses204Generator = arbitrary[Null]

    def PetsIdDeleteIdGenerator = arbitrary[Long]

    def PetsGetLimitGenerator = Gen.option(arbitrary[Int])

    def PetsGetTagsOptGenerator = Gen.containerOf[List,String](arbitrary[String])

    def PetsGetResponses200OptGenerator = Gen.containerOf[List,Pet](PetGenerator)

    def PetsGetResponses200Generator = Gen.option(PetsGetResponses200OptGenerator)

    def PetsGetTagsGenerator = Gen.option(PetsGetTagsOptGenerator)

    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample

    def _genMap[K,V](keyGen: Gen[K], valGen: Gen[V]): Gen[Map[K,V]] = for {

        keys <- Gen.containerOf[List,K](keyGen)

        values <- Gen.containerOfN[List,V](keys.size, valGen)

    } yield keys.zip(values).toMap

}
