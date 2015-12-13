package nested_arrays
package object yaml {
import java.util.Date
import java.io.File


    type ExampleNestedArraysOptArr = scala.collection.Seq[ExampleNestedArraysOptArrArr]

    type ExampleNestedArraysOpt = scala.collection.Seq[ExampleNestedArraysOptArr]

    type ExampleMessagesOpt = scala.collection.Seq[ExampleMessagesOptArr]

    type ActivityActions = Option[String]

    type ExampleMessages = Option[ExampleMessagesOpt]

    type ExampleMessagesOptArr = scala.collection.Seq[Activity]

    type ExampleNestedArraysOptArrArr = scala.collection.Seq[ExampleNestedArraysOptArrArrArr]

    type ExampleNestedArrays = Option[ExampleNestedArraysOpt]

    type ExampleNestedArraysOptArrArrArr = scala.collection.Seq[String]

    case class Activity(actions: ActivityActions
) 

    case class Example(messages: ExampleMessages, 
nestedArrays: ExampleNestedArrays
) 

    

}
