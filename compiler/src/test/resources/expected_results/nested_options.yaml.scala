package nested_options.yaml
import scala.Option
object definitions {
  object _basic {
    case class Optional(
      nested_optional: Option[String]
    )
  }
  case class Basic(
    optional: Option[_basic.Optional]
  )
}