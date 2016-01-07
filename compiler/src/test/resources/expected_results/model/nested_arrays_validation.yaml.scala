package nested_arrays_validation
package object yaml {
import de.zalando.play.controllers.ArrayWrapper
type ExampleNestedArraysOpt = ArrayWrapper[ExampleNestedArraysOptArr]

    type ActivityActions = Option[String]

    type ExampleNestedArraysOptArrArr = ArrayWrapper[ExampleNestedArraysOptArrArrArr]

    type ExampleMessagesOptArr = ArrayWrapper[Activity]

    type ExampleNestedArraysOptArrArrArr = ArrayWrapper[String]

    type GetExample = Option[Example]

    type ExampleMessages = Option[ExampleMessagesOpt]

    type ExampleMessagesOpt = ArrayWrapper[ExampleMessagesOptArr]

    type ExampleNestedArrays = Option[ExampleNestedArraysOpt]

    type ExampleNestedArraysOptArr = ArrayWrapper[ExampleNestedArraysOptArrArr]

    type GetResponses200 = Null

    case class Activity(actions: ActivityActions) 

    case class Example(messages: ExampleMessages, nestedArrays: ExampleNestedArrays) 

    


    
    
    }
