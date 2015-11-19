package heroku.petstore.api.yaml
import org.scalacheck.Gen
import org.scalacheck.Arbitrary._

object definitionsGenerator {
    import definitions.Pet
    def createPetNameGenerator = _generate(PetNameGenerator)
    def createPetBirthdayGenerator = _generate(PetBirthdayGenerator)
    val PetNameGenerator = Gen.option(arbitrary[String])
    val PetBirthdayGenerator = Gen.option(arbitrary[Int])
    def createPetGenerator = _generate(PetGenerator)
    val PetGenerator =
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
    import definitions.Pet
    import paths.GetResponses200Opt
    import definitionsGenerator.PetGenerator
    def createGetResponses200OptGenerator = _generate(GetResponses200OptGenerator)
    def createGetResponses200Generator = _generate(GetResponses200Generator)
    val GetResponses200OptGenerator = Gen.containerOf[List,Pet](PetGenerator)
    val GetResponses200Generator = Gen.option(GetResponses200OptGenerator)
    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample

    def _genMap[K,V](keyGen: Gen[K], valGen: Gen[V]): Gen[Map[K,V]] = for {
        keys <- Gen.containerOf[List,K](keyGen)
        values <- Gen.containerOfN[List,V](keys.size, valGen)
    } yield keys.zip(values).toMap
}
