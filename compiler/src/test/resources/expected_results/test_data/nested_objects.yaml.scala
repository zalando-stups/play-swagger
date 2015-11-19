package nested_objects.yaml
import org.scalacheck.Gen
import org.scalacheck.Arbitrary._

object definitionsGenerator {
    import definitions._
    def createNestedObjectsNestedGenerator = _generate(NestedObjectsNestedGenerator)
    def createNestedObjectsNestedNested2Nested3BottomGenerator = _generate(NestedObjectsNestedNested2Nested3BottomGenerator)
    def createNestedObjectsNestedNested2Nested3Generator = _generate(NestedObjectsNestedNested2Nested3Generator)
    def createNestedObjectsPlainGenerator = _generate(NestedObjectsPlainGenerator)
    val NestedObjectsNestedGenerator = Gen.option(NestedObjectsNestedOptGenerator)
    val NestedObjectsNestedNested2Nested3BottomGenerator = Gen.option(arbitrary[String])
    val NestedObjectsNestedNested2Nested3Generator = Gen.option(NestedObjectsNestedNested2Nested3OptGenerator)
    val NestedObjectsPlainGenerator = Gen.option(NestedObjectsPlainOptGenerator)
    def createNestedObjectsNestedOptGenerator = _generate(NestedObjectsNestedOptGenerator)
    def createNestedObjectsNestedNested2Nested3OptGenerator = _generate(NestedObjectsNestedNested2Nested3OptGenerator)
    def createNestedObjectsGenerator = _generate(NestedObjectsGenerator)
    def createNestedObjectsPlainOptGenerator = _generate(NestedObjectsPlainOptGenerator)
    def createNestedObjectsNestedNested2Generator = _generate(NestedObjectsNestedNested2Generator)
    val NestedObjectsNestedOptGenerator =
        for {
        nested2 <- NestedObjectsNestedNested2Generator
        } yield NestedObjectsNestedOpt(nested2)
    
    val NestedObjectsNestedNested2Nested3OptGenerator =
        for {
        bottom <- NestedObjectsNestedNested2Nested3BottomGenerator
        } yield NestedObjectsNestedNested2Nested3Opt(bottom)
    
    val NestedObjectsGenerator =
        for {
        plain <- NestedObjectsPlainGenerator
        nested <- NestedObjectsNestedGenerator
        } yield NestedObjects(plain, nested)
    
    val NestedObjectsPlainOptGenerator =
        for {
        simple <- arbitrary[String]
        } yield NestedObjectsPlainOpt(simple)
    
    val NestedObjectsNestedNested2Generator =
        for {
        nested3 <- NestedObjectsNestedNested2Nested3Generator
        } yield NestedObjectsNestedNested2(nested3)
    
    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample

    def _genMap[K,V](keyGen: Gen[K], valGen: Gen[V]): Gen[Map[K,V]] = for {
        keys <- Gen.containerOf[List,K](keyGen)
        values <- Gen.containerOfN[List,V](keys.size, valGen)
    } yield keys.zip(values).toMap
}
