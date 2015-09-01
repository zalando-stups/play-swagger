package additional_properties.yaml
import scala.collection.immutable.Map
import org.scalacheck.Gen
import org.scalacheck.Arbitrary._
object generatorDefinitions {
  import definitions.KeyedArrays
  def genKeyedArrays = _generate(KeyedArraysGenerator)
  // test data generator for /definitions/KeyedArrays
  val KeyedArraysGenerator =
    for {
      additionalProperties <- Gen.const(Map.empty[String, Int])
    } yield KeyedArrays(additionalProperties)
  def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
}