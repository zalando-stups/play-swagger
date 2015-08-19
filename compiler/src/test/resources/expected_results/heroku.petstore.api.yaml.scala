package heroku.petstore.api.yaml
import scala.Option
object definitions {
  case class Pet(
    name: Option[String],
    birthday: Option[Int]
  )
}