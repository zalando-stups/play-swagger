package expanded_polymorphism.yaml
import org.scalacheck.Gen
import org.scalacheck.Arbitrary._
import java.util.Date
import java.io.File

object definitionsGenerator {
    def createNewPetTagGenerator = _generate(NewPetTagGenerator)

    def NewPetTagGenerator = Gen.option(arbitrary[String])

    def createNewPetGenerator = _generate(NewPetGenerator)

    def createPetGenerator = _generate(PetGenerator)

    def createErrorGenerator = _generate(ErrorGenerator)

    def NewPetGenerator =

        for {

        name <- arbitrary[String]

        tag <- NewPetTagGenerator

        } yield NewPet(name, tag)

    

    def PetGenerator =

        for {

        name <- arbitrary[String]

        tag <- NewPetTagGenerator

        id <- arbitrary[Long]

        } yield Pet(name, tag, id)

    

    def ErrorGenerator =

        for {

        code <- arbitrary[Int]

        message <- arbitrary[String]

        } yield Error(code, message)

    

    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample

    def _genMap[K,V](keyGen: Gen[K], valGen: Gen[V]): Gen[Map[K,V]] = for {

        keys <- Gen.containerOf[List,K](keyGen)

        values <- Gen.containerOfN[List,V](keys.size, valGen)

    } yield keys.zip(values).toMap

}
object pathsGenerator {
    import definitionsGenerator.{ErrorGenerator, NewPetGenerator, PetGenerator}
    def createPetsIdDeleteResponsesDefaultGenerator = _generate(PetsIdDeleteResponsesDefaultGenerator)

    def createPetsIdDeleteResponses204Generator = _generate(PetsIdDeleteResponses204Generator)

    def createPetsIdDeleteIdGenerator = _generate(PetsIdDeleteIdGenerator)

    def createPetsGetLimitGenerator = _generate(PetsGetLimitGenerator)

    def createPetsGetTagsOptGenerator = _generate(PetsGetTagsOptGenerator)

    def createPetsGetResponses200OptGenerator = _generate(PetsGetResponses200OptGenerator)

    def createPetsGetResponses200Generator = _generate(PetsGetResponses200Generator)

    def createPetsGetTagsGenerator = _generate(PetsGetTagsGenerator)

    def PetsIdDeleteResponsesDefaultGenerator = Gen.option(ErrorGenerator)

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
