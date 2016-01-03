package nested_arrays
package object yaml {
import java.util.Date
import java.io.File
import de.zalando.play.controllers.ArrayWrapper

    type ExampleNestedArraysOptArr = ArrayWrapper[ExampleNestedArraysOptArrArr]

    type ExampleNestedArraysOpt = ArrayWrapper[ExampleNestedArraysOptArr]

    type ExampleMessagesOpt = ArrayWrapper[ExampleMessagesOptArr]

    type ActivityActions = Option[String]

    type ExampleMessages = Option[ExampleMessagesOpt]

    type ExampleMessagesOptArr = ArrayWrapper[Activity]

    type ExampleNestedArraysOptArrArr = ArrayWrapper[ExampleNestedArraysOptArrArrArr]

    type ExampleNestedArrays = Option[ExampleNestedArraysOpt]

    type ExampleNestedArraysOptArrArrArr = ArrayWrapper[String]

    case class Activity(actions: ActivityActions) 

    case class Example(messages: ExampleMessages, nestedArrays: ExampleNestedArrays) 

    

}
