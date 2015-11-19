package nested_arrays.yaml
object definitions {
    type ExampleNestedArraysOptArr = scala.collection.Seq[ExampleNestedArraysOptArrArr]
    type ExampleNestedArraysOpt = scala.collection.Seq[ExampleNestedArraysOptArr]
    type ExampleMessagesOpt = scala.collection.Seq[ExampleMessagesOptArr]
    type ActivityActions = Option[String]
    type ExampleMessages = Option[ExampleMessagesOpt]
    type ExampleMessagesOptArr = scala.collection.Seq[Activity]
    type ExampleNestedArraysOptArrArr = scala.collection.Seq[ActivityActions]
    type ExampleNestedArrays = Option[ExampleNestedArraysOpt]
    case class Activity(actions: ActivityActions) 
    case class Example(messages: ExampleMessages, nestedArrays: ExampleNestedArrays) 
    }
