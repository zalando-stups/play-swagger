package nested_arrays.yaml

import org.scalacheck.Gen
import org.scalacheck.Arbitrary._

import de.zalando.play.controllers.ArrayWrapper
object Generators {
def createExampleNestedArraysOptArrGenerator = _generate(ExampleNestedArraysOptArrGenerator)

    def createExampleNestedArraysOptGenerator = _generate(ExampleNestedArraysOptGenerator)

    def createExampleMessagesOptGenerator = _generate(ExampleMessagesOptGenerator)

    def createActivityActionsGenerator = _generate(ActivityActionsGenerator)

    def createExampleMessagesGenerator = _generate(ExampleMessagesGenerator)

    def createExampleMessagesOptArrGenerator = _generate(ExampleMessagesOptArrGenerator)

    def createExampleNestedArraysOptArrArrGenerator = _generate(ExampleNestedArraysOptArrArrGenerator)

    def createExampleNestedArraysGenerator = _generate(ExampleNestedArraysGenerator)

    def createExampleNestedArraysOptArrArrArrGenerator = _generate(ExampleNestedArraysOptArrArrArrGenerator)

    def ExampleNestedArraysOptArrGenerator = Gen.containerOf[List,ExampleNestedArraysOptArrArr](ExampleNestedArraysOptArrArrGenerator)

    def ExampleNestedArraysOptGenerator = Gen.containerOf[List,ExampleNestedArraysOptArr](ExampleNestedArraysOptArrGenerator)

    def ExampleMessagesOptGenerator = Gen.containerOf[List,ExampleMessagesOptArr](ExampleMessagesOptArrGenerator)

    def ActivityActionsGenerator = Gen.option(arbitrary[String])

    def ExampleMessagesGenerator = Gen.option(ExampleMessagesOptGenerator)

    def ExampleMessagesOptArrGenerator = Gen.containerOf[List,Activity](ActivityGenerator)

    def ExampleNestedArraysOptArrArrGenerator = Gen.containerOf[List,ExampleNestedArraysOptArrArrArr](ExampleNestedArraysOptArrArrArrGenerator)

    def ExampleNestedArraysGenerator = Gen.option(ExampleNestedArraysOptGenerator)

    def ExampleNestedArraysOptArrArrArrGenerator = Gen.containerOf[List,String](arbitrary[String])

    def createActivityGenerator = _generate(ActivityGenerator)

    def createExampleGenerator = _generate(ExampleGenerator)

    def ActivityGenerator = for {
        actions <- ActivityActionsGenerator
        } yield Activity(actions)

    def ExampleGenerator = for {
        messages <- ExampleMessagesGenerator
        nestedArrays <- ExampleNestedArraysGenerator
        } yield Example(messages, nestedArrays)

    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample

    def _genMap[K,V](keyGen: Gen[K], valGen: Gen[V]): Gen[Map[K,V]] = for {

        keys <- Gen.containerOf[List,K](keyGen)

        values <- Gen.containerOfN[List,V](keys.size, valGen)

    } yield keys.zip(values).toMap

}
