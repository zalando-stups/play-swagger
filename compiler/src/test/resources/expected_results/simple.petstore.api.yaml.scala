package simple.petstore.api.yaml
import scala.Option
object definitions {
  case class Pet(
    id: Long,
    name: String,
    tag: Option[String]
  )
  case class NewPet(
    id: Option[Long],
    name: String,
    tag: Option[String]
  )
  case class ErrorModel(
    code: Int,
    message: String
  )
}