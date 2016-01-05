package minimal.api.yaml

import org.scalacheck.Gen
import org.scalacheck.Arbitrary._

object Generators {
def createNullGenerator = _generate(NullGenerator)

    def NullGenerator = arbitrary[Null]

    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
}
