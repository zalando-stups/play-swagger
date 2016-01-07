package heroku.petstore.api.yaml
import org.scalacheck.Gen
import org.scalacheck.Arbitrary._
import java.util.Date
import java.io.File

object Generators {
def createPetNameGenerator = _generate(PetNameGenerator)

    def createStringGenerator = _generate(StringGenerator)

    def createGetResponses200OptGenerator = _generate(GetResponses200OptGenerator)

    def createNullGenerator = _generate(NullGenerator)

    def createPetBirthdayGenerator = _generate(PetBirthdayGenerator)

    def createGetResponses200Generator = _generate(GetResponses200Generator)

    def PetNameGenerator = Gen.option(arbitrary[String])

    def StringGenerator = arbitrary[String]

    def GetResponses200OptGenerator = Gen.containerOf[List,Pet](PetGenerator)

    def NullGenerator = arbitrary[Null]

    def PetBirthdayGenerator = Gen.option(arbitrary[Int])

    def GetResponses200Generator = Gen.option(GetResponses200OptGenerator)

    def createPetGenerator = _generate(PetGenerator)

    def PetGenerator = for {
        name <- PetNameGenerator
        birthday <- PetBirthdayGenerator
        } yield Pet(name, birthday)

    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample

    def _genMap[K,V](keyGen: Gen[K], valGen: Gen[V]): Gen[Map[K,V]] = for {

        keys <- Gen.containerOf[List,K](keyGen)

        values <- Gen.containerOfN[List,V](keys.size, valGen)

    } yield keys.zip(values).toMap

}
