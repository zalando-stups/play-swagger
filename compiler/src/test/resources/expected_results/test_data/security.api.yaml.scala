package security.api.yaml
import org.scalacheck.Gen
import org.scalacheck.Arbitrary._

object definitionsGenerator {
    import definitions._
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
    import definitions.{ErrorModel, Pet}
    import paths.{PetsIdGetResponses200, PetsIdGetResponses200Opt, PetsIdGetResponsesDefault}
    import definitionsGenerator.{ErrorModelGenerator, PetGenerator}
    def createPetsIdGetResponsesDefaultGenerator = _generate(PetsIdGetResponsesDefaultGenerator)
    def createPetsIdGetResponses200Generator = _generate(PetsIdGetResponses200Generator)
    def createPetsIdGetResponses200OptGenerator = _generate(PetsIdGetResponses200OptGenerator)
    val PetsIdGetResponsesDefaultGenerator = Gen.option(ErrorModelGenerator)
    val PetsIdGetResponses200Generator = Gen.option(PetsIdGetResponses200OptGenerator)
    val PetsIdGetResponses200OptGenerator = Gen.containerOf[List,Pet](PetGenerator)
    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample

    def _genMap[K,V](keyGen: Gen[K], valGen: Gen[V]): Gen[Map[K,V]] = for {
        keys <- Gen.containerOf[List,K](keyGen)
        values <- Gen.containerOfN[List,V](keys.size, valGen)
    } yield keys.zip(values).toMap
}
