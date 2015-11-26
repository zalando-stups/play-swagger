package nested_arrays.yaml
import org.scalacheck.Gen
import org.scalacheck.Arbitrary._

object definitionsGenerator {
    import definitions._
    def createExampleNestedArraysOptArrGenerator = _generate(ExampleNestedArraysOptArrGenerator)
    def createExampleNestedArraysOptGenerator = _generate(ExampleNestedArraysOptGenerator)
    def createExampleMessagesOptGenerator = _generate(ExampleMessagesOptGenerator)
    def createActivityActionsGenerator = _generate(ActivityActionsGenerator)
    def createExampleMessagesGenerator = _generate(ExampleMessagesGenerator)
    def createExampleMessagesOptArrGenerator = _generate(ExampleMessagesOptArrGenerator)
    def createExampleNestedArraysOptArrArrGenerator = _generate(ExampleNestedArraysOptArrArrGenerator)
    def createExampleNestedArraysGenerator = _generate(ExampleNestedArraysGenerator)
    def createExampleNestedArraysOptArrArrArrGenerator = _generate(ExampleNestedArraysOptArrArrArrGenerator)
    val ExampleNestedArraysOptArrGenerator = Gen.containerOf[List,ExampleNestedArraysOptArrArr](ExampleNestedArraysOptArrArrGenerator)
    val ExampleNestedArraysOptGenerator = Gen.containerOf[List,ExampleNestedArraysOptArr](ExampleNestedArraysOptArrGenerator)
    val ExampleMessagesOptGenerator = Gen.containerOf[List,ExampleMessagesOptArr](ExampleMessagesOptArrGenerator)
    val ActivityActionsGenerator = Gen.option(arbitrary[String])
    val ExampleMessagesGenerator = Gen.option(ExampleMessagesOptGenerator)
    val ExampleMessagesOptArrGenerator = Gen.containerOf[List,Activity](ActivityGenerator)
    val ExampleNestedArraysOptArrArrGenerator = Gen.containerOf[List,ExampleNestedArraysOptArrArrArr](ExampleNestedArraysOptArrArrArrGenerator)
    val ExampleNestedArraysGenerator = Gen.option(ExampleNestedArraysOptGenerator)
    val ExampleNestedArraysOptArrArrArrGenerator = Gen.containerOf[List,String](arbitrary[String])
    def createActivityGenerator = _generate(ActivityGenerator)
    def createExampleGenerator = _generate(ExampleGenerator)
    val ActivityGenerator =
        for {
        actions <- ActivityActionsGenerator
        } yield Activity(actions)
    
    val ExampleGenerator =
        for {
        messages <- ExampleMessagesGenerator
        nestedArrays <- ExampleNestedArraysGenerator
        } yield Example(messages, nestedArrays)
    
    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample

    def _genMap[K,V](keyGen: Gen[K], valGen: Gen[V]): Gen[Map[K,V]] = for {
        keys <- Gen.containerOf[List,K](keyGen)
        values <- Gen.containerOfN[List,V](keys.size, valGen)
    } yield keys.zip(values).toMap
}
