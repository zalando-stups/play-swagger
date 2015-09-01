package nested_arrays.yaml
import scala.Option
import org.scalacheck.Gen
import org.scalacheck.Arbitrary._
import scala.collection.Seq
object generatorDefinitions {
  import definitions.Activity
  import definitions.Example
  def genActivity = _generate(ActivityGenerator)
  def genExample = _generate(ExampleGenerator)
  // test data generator for /definitions/Activity
  val ActivityGenerator =
    for {
      actions <- Gen.option(arbitrary[String])
    } yield Activity(actions)
  // test data generator for /definitions/Example
  val ExampleGenerator =
    for {
      messages <- Gen.option(Gen.containerOf[List,Seq[Activity]](Gen.containerOf[List,Activity](generatorDefinitions.ActivityGenerator)))
      nestedArrays <- Gen.option(Gen.containerOf[List,Seq[Seq[Seq[String]]]](Gen.containerOf[List,Seq[Seq[String]]](Gen.containerOf[List,Seq[String]](Gen.containerOf[List,String](arbitrary[String])))))
    } yield Example(messages, nestedArrays)
  def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
}