package nested_objects.yaml
import scala.Option
import org.scalacheck.Gen
import org.scalacheck.Arbitrary._
object generatorDefinitions {
  import definitions.NestedObjects
  object _nestedobjects {
    import definitions._nestedobjects.Plain
    import definitions._nestedobjects.Nested
    object _nested {
      import definitions._nestedobjects._nested.Nested2
      object _nested2 {
        import definitions._nestedobjects._nested._nested2.Nested3
        def genNested3 = _generate(Nested3Generator)
        // test data generator for /definitions/NestedObjects/nested/nested2/nested3
        val Nested3Generator =
          for {
            bottom <- Gen.option(arbitrary[String])
          } yield Nested3(bottom)
        def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
      }
      def genNested2 = _generate(Nested2Generator)
      // test data generator for /definitions/NestedObjects/nested/nested2
      val Nested2Generator =
        for {
          nested3 <- Gen.option(generatorDefinitions._nestedobjects._nested._nested2.Nested3Generator)
        } yield Nested2(nested3)
      def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
    }
    def genPlain = _generate(PlainGenerator)
    def genNested = _generate(NestedGenerator)
    // test data generator for /definitions/NestedObjects/plain
    val PlainGenerator =
      for {
        simple <- arbitrary[String]
      } yield Plain(simple)
    // test data generator for /definitions/NestedObjects/nested
    val NestedGenerator =
      for {
        nested2 <- _nested.Nested2Generator
      } yield Nested(nested2)
    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
  }
  def genNestedObjects = _generate(NestedObjectsGenerator)
  // test data generator for /definitions/NestedObjects
  val NestedObjectsGenerator =
    for {
      plain <- Gen.option(generatorDefinitions._nestedobjects.PlainGenerator)
      nested <- Gen.option(generatorDefinitions._nestedobjects.NestedGenerator)
    } yield NestedObjects(plain, nested)
  def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
}