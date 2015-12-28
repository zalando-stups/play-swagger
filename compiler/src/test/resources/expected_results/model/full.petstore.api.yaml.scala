package full.petstore.api
package object yaml {
import java.util.Date
import java.io.File

type OrderQuantity = Option[Int]

    type UsersCreateWithListPostBodyOpt = scala.collection.Seq[User]

    type OrderPetId = Option[Long]

    type PetsFindByStatusGetResponses200 = Option[PetsFindByStatusGetResponses200Opt]

    type PetsPostBody = Option[Pet]

    type UsersUsernamePutBody = Option[User]

    type StoresOrderPostBody = Option[Order]

    type OrderStatus = Option[String]

    type PetTags = Option[PetTagsOpt]

    type OrderComplete = Option[Boolean]

    type PetTagsOpt = scala.collection.Seq[Tag]

    type PetsFindByStatusGetResponses200Opt = scala.collection.Seq[Pet]

    type PetCategory = Option[Tag]

    type OrderShipDate = Option[Date]

    type UsersCreateWithListPostBody = Option[UsersCreateWithListPostBodyOpt]

    type PetsFindByStatusGetStatus = Option[PetPhotoUrls]

    type PetPhotoUrls = scala.collection.Seq[String]

    case class User(email: OrderStatus, username: OrderStatus, userStatus: OrderQuantity, lastName: OrderStatus, firstName: OrderStatus, id: OrderPetId, phone: OrderStatus, password: OrderStatus) 

    case class Order(shipDate: OrderShipDate, quantity: OrderQuantity, petId: OrderPetId, id: OrderPetId, complete: OrderComplete, status: OrderStatus) 

    case class Tag(id: OrderPetId, name: OrderStatus) 

    case class Pet(name: String, tags: PetTags, photoUrls: PetPhotoUrls, id: OrderPetId, status: OrderStatus, category: PetCategory) 

    

}
