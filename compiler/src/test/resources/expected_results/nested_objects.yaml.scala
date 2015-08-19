package nested_objects.yaml
import scala.Option
object definitions {  
  object nestedobjects {  
    object nested {  
      object nested2 {
        case class Nested3(
          bottom: Option[String]
        )
      }
      case class Nested2(
        nested3: Option[nested2.Nested3]
      )
    }
    case class Plain(
      simple: String
    )
    case class Nested(
      nested2: nested.Nested2
    )
  }
  // Nested Objects Model
  case class NestedObjects(
    plain: Option[nestedobjects.Plain],
    nested: Option[nestedobjects.Nested]
  )
}