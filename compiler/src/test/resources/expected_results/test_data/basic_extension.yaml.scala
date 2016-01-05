package basic_extension.yaml

import org.scalacheck.Gen
import org.scalacheck.Arbitrary._

object Generators {
def createErrorModelGenerator = _generate(ErrorModelGenerator)

    def createExtendedErrorModelGenerator = _generate(ExtendedErrorModelGenerator)

    def ErrorModelGenerator = for {
        message <- arbitrary[String]
        code <- arbitrary[Int]
        } yield ErrorModel(message, code)

    def ExtendedErrorModelGenerator = for {
        message <- arbitrary[String]
        code <- arbitrary[Int]
        rootCause <- arbitrary[String]
        } yield ExtendedErrorModel(message, code, rootCause)

    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
}
