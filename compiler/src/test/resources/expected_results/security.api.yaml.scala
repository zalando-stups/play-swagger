package security.api.yaml
import scala.Option
object definitions {
  case class Pet(
    name: String,
    tag: Option[String]
  )
  case class ErrorModel(
    code: Int,
    message: String
  )
}