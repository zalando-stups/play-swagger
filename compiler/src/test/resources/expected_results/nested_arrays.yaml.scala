package nested_arrays.yaml
import scala.Option
import scala.collection.Seq
object definitions {
  case class Activity(
    // The text of the error message
    actions: Option[String]
  )
  case class Example(
    // The text of the error message
    messages: Option[Seq[Seq[Activity]]],
    nestedArrays: Option[Seq[Seq[Seq[Seq[String]]]]]
  )
}