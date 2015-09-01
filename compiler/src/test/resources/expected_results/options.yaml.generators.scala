package options.yaml
import scala.collection.Seq
import scala.Option
import org.scalacheck.Gen
import org.scalacheck.Arbitrary._
object generatorDefinitions {
  import definitions.Basic
  def genBasic = _generate(BasicGenerator)
  // test data generator for /definitions/Basic
  val BasicGenerator =
    for {
      id <- arbitrary[Long]
      required <- Gen.containerOf[List,String](arbitrary[String])
      optional <- Gen.option(Gen.containerOf[List,String](arbitrary[String]))
    } yield Basic(id, required, optional)
  def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
}