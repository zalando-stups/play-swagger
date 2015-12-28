package nested_arrays_validation
package object yaml {
import java.util.Date
import java.io.File

type ExampleNestedArraysOpt = scala.collection.Seq[ExampleNestedArraysOptArr]

    type ActivityActions = Option[String]

    type ExampleNestedArraysOptArrArr = scala.collection.Seq[ExampleNestedArraysOptArrArrArr]

    type ExampleMessagesOptArr = scala.collection.Seq[Activity]

    type ExampleNestedArraysOptArrArrArr = scala.collection.Seq[String]

    type GetExample = Option[Example]

    type ExampleMessages = Option[ExampleMessagesOpt]

    type ExampleMessagesOpt = scala.collection.Seq[ExampleMessagesOptArr]

    type ExampleNestedArrays = Option[ExampleNestedArraysOpt]

    type ExampleNestedArraysOptArr = scala.collection.Seq[ExampleNestedArraysOptArrArr]

    case class Activity(actions: ActivityActions) 

    case class Example(messages: ExampleMessages, nestedArrays: ExampleNestedArrays) 

    

}
