package echo.api.yaml
import org.scalacheck.Gen
import org.scalacheck.Arbitrary._
import java.util.Date
import java.io.File

object Generators {
def createNullGenerator = _generate(NullGenerator)

    def createPostNameGenerator = _generate(PostNameGenerator)

    def createStringGenerator = _generate(StringGenerator)

    def NullGenerator = arbitrary[Null]

    def PostNameGenerator = Gen.option(arbitrary[String])

    def StringGenerator = arbitrary[String]

    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample

    def _genMap[K,V](keyGen: Gen[K], valGen: Gen[V]): Gen[Map[K,V]] = for {

        keys <- Gen.containerOf[List,K](keyGen)

        values <- Gen.containerOfN[List,V](keys.size, valGen)

    } yield keys.zip(values).toMap

}
