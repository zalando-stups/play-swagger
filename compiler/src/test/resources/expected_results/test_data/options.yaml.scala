package options.yaml
import org.scalacheck.Gen
import org.scalacheck.Arbitrary._
import java.util.Date
import java.io.File

object Generators {
def createBasicRequiredGenerator = _generate(BasicRequiredGenerator)

    def createBasicOptionalGenerator = _generate(BasicOptionalGenerator)

    def BasicRequiredGenerator = Gen.containerOf[List,String](arbitrary[String])

    def BasicOptionalGenerator = Gen.option(BasicRequiredGenerator)

    def createBasicGenerator = _generate(BasicGenerator)

    def BasicGenerator = for {
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
