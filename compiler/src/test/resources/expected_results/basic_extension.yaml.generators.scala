package basic_extension.yaml
import org.scalacheck.Gen
import org.scalacheck.Arbitrary._
import scala.Option
object generatorDefinitions {
  import definitions.ErrorModel
  import definitions.ExtendedErrorModel
  def createErrorModel = _generate(ErrorModelGenerator)
  def createExtendedErrorModel = _generate(ExtendedErrorModelGenerator)
  // test data generator for /definitions/ErrorModel
  val ErrorModelGenerator =
    for {
      message <- arbitrary[String]
      code <- arbitrary[Int]
    } yield ErrorModel(message, code)
  // test data generator for /definitions/ExtendedErrorModel
  val ExtendedErrorModelGenerator =
    for {
      rootCause <- Gen.option(arbitrary[String])
      message <- arbitrary[String]
      code <- arbitrary[Int]
    } yield ExtendedErrorModel(rootCause, message, code)
  def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
}