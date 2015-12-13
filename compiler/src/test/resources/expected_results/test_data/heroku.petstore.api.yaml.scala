package heroku.petstore.api.yaml
import org.scalacheck.Gen
import org.scalacheck.Arbitrary._
import java.util.Date
import java.io.File

object definitionsGenerator {
    def createPetNameGenerator = _generate(PetNameGenerator)

    def createPetBirthdayGenerator = _generate(PetBirthdayGenerator)

    def PetNameGenerator = Gen.option(arbitrary[String])

    def PetBirthdayGenerator = Gen.option(arbitrary[Int])

    def createPetGenerator = _generate(PetGenerator)

    def PetGenerator =

        for {

        name <- PetNameGenerator

        birthday <- PetBirthdayGenerator

        } yield Pet(name, birthday)

    

    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample

    def _genMap[K,V](keyGen: Gen[K], valGen: Gen[V]): Gen[Map[K,V]] = for {

        keys <- Gen.containerOf[List,K](keyGen)

        values <- Gen.containerOfN[List,V](keys.size, valGen)

    } yield keys.zip(values).toMap

}
object pathsGenerator {
    import definitionsGenerator.{PetBirthdayGenerator, PetGenerator}
    def createPetIdGetPetIdGenerator = _generate(PetIdGetPetIdGenerator)

    def createGetResponses200OptGenerator = _generate(GetResponses200OptGenerator)

    def createPostResponses200Generator = _generate(PostResponses200Generator)

    def createGetResponses200Generator = _generate(GetResponses200Generator)

    def PetIdGetPetIdGenerator = arbitrary[String]

    def GetResponses200OptGenerator = Gen.containerOf[List,Pet](PetGenerator)

    def PostResponses200Generator = arbitrary[Null]

    def GetResponses200Generator = Gen.option(GetResponses200OptGenerator)

    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample

    def _genMap[K,V](keyGen: Gen[K], valGen: Gen[V]): Gen[Map[K,V]] = for {

        keys <- Gen.containerOf[List,K](keyGen)

        values <- Gen.containerOfN[List,V](keys.size, valGen)

    } yield keys.zip(values).toMap

}
