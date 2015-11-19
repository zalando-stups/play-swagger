package nested_options.yaml
import org.scalacheck.Gen
import org.scalacheck.Arbitrary._

object definitionsGenerator {
    import definitions._
    def createBasicOptionalGenerator = _generate(BasicOptionalGenerator)
    def createBasicOptionalNested_optionalGenerator = _generate(BasicOptionalNested_optionalGenerator)
    val BasicOptionalGenerator = Gen.option(BasicOptionalOptGenerator)
    val BasicOptionalNested_optionalGenerator = Gen.option(arbitrary[String])
    def createBasicGenerator = _generate(BasicGenerator)
    def createBasicOptionalOptGenerator = _generate(BasicOptionalOptGenerator)
    val BasicGenerator =
        for {
        optional <- BasicOptionalGenerator
        } yield Basic(optional)
    
    val BasicOptionalOptGenerator =
        for {
        nested_optional <- BasicOptionalNested_optionalGenerator
        } yield BasicOptionalOpt(nested_optional)
    
    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample

    def _genMap[K,V](keyGen: Gen[K], valGen: Gen[V]): Gen[Map[K,V]] = for {
        keys <- Gen.containerOf[List,K](keyGen)
        values <- Gen.containerOfN[List,V](keys.size, valGen)
    } yield keys.zip(values).toMap
}
