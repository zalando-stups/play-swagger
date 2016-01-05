package nested_options.yaml

import org.scalacheck.Gen
import org.scalacheck.Arbitrary._

object Generators {
def createBasicOptionalGenerator = _generate(BasicOptionalGenerator)

    def createBasicOptionalNested_optionalGenerator = _generate(BasicOptionalNested_optionalGenerator)

    def BasicOptionalGenerator = Gen.option(BasicOptionalOptGenerator)

    def BasicOptionalNested_optionalGenerator = Gen.option(arbitrary[String])

    def createBasicGenerator = _generate(BasicGenerator)

    def createBasicOptionalOptGenerator = _generate(BasicOptionalOptGenerator)

    def BasicGenerator = for {
        optional <- BasicOptionalGenerator
        } yield Basic(optional)

    def BasicOptionalOptGenerator = for {
        nested_optional <- BasicOptionalNested_optionalGenerator
        } yield BasicOptionalOpt(nested_optional)

    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
}
