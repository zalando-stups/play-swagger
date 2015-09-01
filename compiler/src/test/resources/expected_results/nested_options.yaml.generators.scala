package nested_options.yaml
import scala.Option
import org.scalacheck.Gen
import org.scalacheck.Arbitrary._
object generatorDefinitions {
  import definitions.Basic
  object _basic {
    import definitions._basic.Optional
    def genOptional = _generate(OptionalGenerator)
    // test data generator for /definitions/Basic/optional
    val OptionalGenerator =
      for {
        nested_optional <- Gen.option(arbitrary[String])
      } yield Optional(nested_optional)
    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
  }
  def genBasic = _generate(BasicGenerator)
  // test data generator for /definitions/Basic
  val BasicGenerator =
    for {
      optional <- Gen.option(generatorDefinitions._basic.OptionalGenerator)
    } yield Basic(optional)
  def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
}