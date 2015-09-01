package nested_objects.yaml
import scala.Option
object definitions {
  object _nestedobjects {
    object _nested {
      object _nested2 {
        case class Nested3(
          bottom: Option[String]
        )
      }
      case class Nested2(
        nested3: Option[_nested2.Nested3]
      )
    }
    case class Plain(
      simple: String
    )
    case class Nested(
      nested2: _nested.Nested2
    )
  }
  // Nested Objects Model
  case class NestedObjects(
    plain: Option[_nestedobjects.Plain],
    nested: Option[_nestedobjects.Nested]
  )
}