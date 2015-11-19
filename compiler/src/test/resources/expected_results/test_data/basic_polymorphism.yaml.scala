package basic_polymorphism.yaml
import org.scalacheck.Gen
import org.scalacheck.Arbitrary._

object definitionsGenerator {
    import definitions._
    def createCatGenerator = _generate(CatGenerator)
    def createDogGenerator = _generate(DogGenerator)
    def createCatNDogGenerator = _generate(CatNDogGenerator)
    def createPetGenerator = _generate(PetGenerator)
    def createLabradorGenerator = _generate(LabradorGenerator)
    val CatGenerator =
        for {
        name <- arbitrary[String]
        petType <- arbitrary[String]
        huntingSkill <- arbitrary[String]
        } yield Cat(name, petType, huntingSkill)
    
    val DogGenerator =
        for {
        name <- arbitrary[String]
        petType <- arbitrary[String]
        packSize <- arbitrary[Int]
        } yield Dog(name, petType, packSize)
    
    val CatNDogGenerator =
        for {
        name <- arbitrary[String]
        petType <- arbitrary[String]
        packSize <- arbitrary[Int]
        huntingSkill <- arbitrary[String]
        } yield CatNDog(name, petType, packSize, huntingSkill)
    
    val PetGenerator =
        for {
        name <- arbitrary[String]
        petType <- arbitrary[String]
        } yield Pet(name, petType)
    
    val LabradorGenerator =
        for {
        name <- arbitrary[String]
        petType <- arbitrary[String]
        packSize <- arbitrary[Int]
        cuteness <- arbitrary[Int]
        } yield Labrador(name, petType, packSize, cuteness)
    
    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample

    def _genMap[K,V](keyGen: Gen[K], valGen: Gen[V]): Gen[Map[K,V]] = for {
        keys <- Gen.containerOf[List,K](keyGen)
        values <- Gen.containerOfN[List,V](keys.size, valGen)
    } yield keys.zip(values).toMap
}
