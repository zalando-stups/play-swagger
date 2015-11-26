package options.yaml
import org.scalacheck.Gen
import org.scalacheck.Arbitrary._

object definitionsGenerator {
    import definitions.{Basic, BasicOptional, BasicRequired}
    def createBasicRequiredGenerator = _generate(BasicRequiredGenerator)
    def createBasicOptionalGenerator = _generate(BasicOptionalGenerator)
    val BasicRequiredGenerator = Gen.containerOf[List,String](arbitrary[String])
    val BasicOptionalGenerator = Gen.option(BasicRequiredGenerator)
    def createBasicGenerator = _generate(BasicGenerator)
    val BasicGenerator =
        for {
        id <- arbitrary[Long]
        required <- BasicRequiredGenerator
        optional <- BasicOptionalGenerator
        } yield Basic(id, required, optional)
    
    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample

    def _genMap[K,V](keyGen: Gen[K], valGen: Gen[V]): Gen[Map[K,V]] = for {
        keys <- Gen.containerOf[List,K](keyGen)
        values <- Gen.containerOfN[List,V](keys.size, valGen)
    } yield keys.zip(values).toMap
}
