package simple.petstore.api.yaml
import org.scalacheck.Gen
import org.scalacheck.Arbitrary._

object definitionsGenerator {
    import definitions._
    def createNewPetTagGenerator = _generate(NewPetTagGenerator)
    def createNewPetIdGenerator = _generate(NewPetIdGenerator)
    val NewPetTagGenerator = Gen.option(arbitrary[String])
    val NewPetIdGenerator = Gen.option(arbitrary[Long])
    def createErrorModelGenerator = _generate(ErrorModelGenerator)
    def createPetGenerator = _generate(PetGenerator)
    def createNewPetGenerator = _generate(NewPetGenerator)
    val ErrorModelGenerator =
        for {
        code <- arbitrary[Int]
        message <- arbitrary[String]
        } yield ErrorModel(code, message)
    
    val PetGenerator =
        for {
        id <- arbitrary[Long]
        name <- arbitrary[String]
        tag <- NewPetTagGenerator
        } yield Pet(id, name, tag)
    
    val NewPetGenerator =
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
    import definitions._
    import paths._
    import definitionsGenerator._
    def createPetsIDeleteResponsesDefaultGenerator = _generate(PetsIDeleteResponsesDefaultGenerator)
    def createPetsPostResponses200Generator = _generate(PetsPostResponses200Generator)
    def createPetsIDeleteResponses204Generator = _generate(PetsIDeleteResponses204Generator)
    def createPetsIDeleteIdGenerator = _generate(PetsIDeleteIdGenerator)
    def createPetsGetLimitGenerator = _generate(PetsGetLimitGenerator)
    def createPetsGetResponses200Generator = _generate(PetsGetResponses200Generator)
    def createPetsGetTagsGenerator = _generate(PetsGetTagsGenerator)
    val PetsIDeleteResponsesDefaultGenerator = Gen.option(ErrorModelGenerator)
    val PetsPostResponses200Generator = Gen.option(PetGenerator)
    val PetsIDeleteResponses204Generator = arbitrary[Null]
    val PetsIDeleteIdGenerator = arbitrary[Long]
    val PetsGetLimitGenerator = Gen.option(arbitrary[Int])
    val PetsGetResponses200Generator = Gen.option(PetsPostResponses200Generator)
    val PetsGetTagsGenerator = Gen.option(NewPetTagGenerator)
    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample

    def _genMap[K,V](keyGen: Gen[K], valGen: Gen[V]): Gen[Map[K,V]] = for {
        keys <- Gen.containerOf[List,K](keyGen)
        values <- Gen.containerOfN[List,V](keys.size, valGen)
    } yield keys.zip(values).toMap
}
