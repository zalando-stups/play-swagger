package full.petstore.api.yaml
object definitions {
    import java.util.Date
    type OrderQuantity = Option[Int]
    type OrderPetId = Option[Long]
    type OrderStatus = Option[String]
    type PetTags = Option[PetCategory]
    type OrderComplete = Option[Boolean]
    type PetCategory = Option[Tag]
    type OrderShipDate = Option[Date]
    case class User(email: OrderStatus, username: OrderStatus, userStatus: OrderQuantity, lastName: OrderStatus, firstName: OrderStatus, id: OrderPetId, phone: OrderStatus, password: OrderStatus) 
    case class Order(shipDate: OrderShipDate, quantity: OrderQuantity, petId: OrderPetId, id: OrderPetId, complete: OrderComplete, status: OrderStatus) 
    case class Tag(id: OrderPetId, name: OrderStatus) 
    case class Pet(name: String, tags: PetTags, photoUrls: OrderStatus, id: OrderPetId, status: OrderStatus, category: PetCategory) 
    }
object paths {
    import definitions._
    type PetsFindByStatusGetResponses200 = Option[PetsPostBody]
    type PetsPostBody = Option[Pet]
    type UsersUsernamPutBody = Option[User]
    type StoresOrderPostBody = Option[Order]
    type UsersCreateWithListPostBody = Option[UsersUsernamPutBody]
    type PetsFindByStatusGetStatus = Option[OrderStatus]
    }
