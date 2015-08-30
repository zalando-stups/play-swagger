package full.petstore.api.yaml
import java.util.Date
import scala.Option
import scala.collection.Seq
object definitions {
  case class Pet(
    name: String,
    tags: Option[Seq[Tag]],
    photoUrls: Seq[String],
    id: Option[Long],
    // pet status in the store
    status: Option[String],
    category: Option[Category]
  )
  case class Order(
    shipDate: Option[Date],
    quantity: Option[Int],
    petId: Option[Long],
    id: Option[Long],
    complete: Option[Boolean],
    // Order Status
    status: Option[String]
  )
  case class User(
    email: Option[String],
    username: Option[String],
    // User Status
    userStatus: Option[Int],
    lastName: Option[String],
    firstName: Option[String],
    id: Option[Long],
    phone: Option[String],
    password: Option[String]
  )
  case class Category(
    id: Option[Long],
    name: Option[String]
  )
  case class Tag(
    id: Option[Long],
    name: Option[String]
  )
}