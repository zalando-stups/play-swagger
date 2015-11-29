package expanded_polymorphism.yaml
import org.scalacheck.Gen
import org.scalacheck.Arbitrary._

object definitionsGenerator {
    import definitions._
    def createNewPetTagGenerator = _generate(NewPetTagGenerator)
    val NewPetTagGenerator = Gen.option(arbitrary[String])
    def createNewPetGenerator = _generate(NewPetGenerator)
    def createPetGenerator = _generate(PetGenerator)
    def createErrorGenerator = _generate(ErrorGenerator)
    val NewPetGenerator =
        for {
        name <- arbitrary[String]
        tag <- NewPetTagGenerator
        } yield NewPet(name, tag)
    
    val PetGenerator =
        for {
        name <- arbitrary[String]
        tag <- NewPetTagGenerator
        id <- arbitrary[Long]
        } yield Pet(name, tag, id)
    
    val ErrorGenerator =
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
    import definitions.{Error, Pet}
    import paths._
    import definitionsGenerator.{ErrorGenerator, PetGenerator}
    def createPetsIdDeleteResponsesDefaultGenerator = _generate(PetsIdDeleteResponsesDefaultGenerator)
    def createPetsIdDeleteResponses204Generator = _generate(PetsIdDeleteResponses204Generator)
    def createPetsIdDeleteIdGenerator = _generate(PetsIdDeleteIdGenerator)
    def createPetsGetLimitGenerator = _generate(PetsGetLimitGenerator)
    def createPetsGetTagsOptGenerator = _generate(PetsGetTagsOptGenerator)
    def createPetsGetResponses200OptGenerator = _generate(PetsGetResponses200OptGenerator)
    def createPetsGetResponses200Generator = _generate(PetsGetResponses200Generator)
    def createPetsGetTagsGenerator = _generate(PetsGetTagsGenerator)
    val PetsIdDeleteResponsesDefaultGenerator = Gen.option(ErrorGenerator)
    val PetsIdDeleteResponses204Generator = arbitrary[Null]
    val PetsIdDeleteIdGenerator = arbitrary[Long]
    val PetsGetLimitGenerator = Gen.option(arbitrary[Int])
    val PetsGetTagsOptGenerator = Gen.containerOf[List,String](arbitrary[String])
    val PetsGetResponses200OptGenerator = Gen.containerOf[List,Pet](PetGenerator)
    val PetsGetResponses200Generator = Gen.option(PetsGetResponses200OptGenerator)
    val PetsGetTagsGenerator = Gen.option(PetsGetTagsOptGenerator)
    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample

    def _genMap[K,V](keyGen: Gen[K], valGen: Gen[V]): Gen[Map[K,V]] = for {
        keys <- Gen.containerOf[List,K](keyGen)
        values <- Gen.containerOfN[List,V](keys.size, valGen)
    } yield keys.zip(values).toMap
}
