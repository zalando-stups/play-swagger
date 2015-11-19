package security.api.yaml
import org.scalacheck.Gen
import org.scalacheck.Arbitrary._

object definitionsGenerator {
    import definitions.ErrorModel
    import definitions.Pet
    def createPetTagGenerator = _generate(PetTagGenerator)
    val PetTagGenerator = Gen.option(arbitrary[String])
    def createErrorModelGenerator = _generate(ErrorModelGenerator)
    def createPetGenerator = _generate(PetGenerator)
    val ErrorModelGenerator =
        for {
        code <- arbitrary[Int]
        message <- arbitrary[String]
        } yield ErrorModel(code, message)
    
    val PetGenerator =
        for {
        name <- arbitrary[String]
        tag <- PetTagGenerator
        } yield Pet(name, tag)
    
    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample

    def _genMap[K,V](keyGen: Gen[K], valGen: Gen[V]): Gen[Map[K,V]] = for {
        keys <- Gen.containerOf[List,K](keyGen)
        values <- Gen.containerOfN[List,V](keys.size, valGen)
    } yield keys.zip(values).toMap
}
object pathsGenerator {
    import definitions.ErrorModel
    import definitions.Pet
    import paths.PetsIGetResponses200Opt
    import definitionsGenerator.ErrorModelGenerator
    import definitionsGenerator.PetGenerator
    def createPetsIGetResponsesDefaultGenerator = _generate(PetsIGetResponsesDefaultGenerator)
    def createPetsIGetResponses200Generator = _generate(PetsIGetResponses200Generator)
    def createPetsIGetResponses200OptGenerator = _generate(PetsIGetResponses200OptGenerator)
    val PetsIGetResponsesDefaultGenerator = Gen.option(ErrorModelGenerator)
    val PetsIGetResponses200Generator = Gen.option(PetsIGetResponses200OptGenerator)
    val PetsIGetResponses200OptGenerator = Gen.containerOf[List,Pet](PetGenerator)
    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample

    def _genMap[K,V](keyGen: Gen[K], valGen: Gen[V]): Gen[Map[K,V]] = for {
        keys <- Gen.containerOf[List,K](keyGen)
        values <- Gen.containerOfN[List,V](keys.size, valGen)
    } yield keys.zip(values).toMap
}
