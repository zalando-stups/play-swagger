package full.petstore.api
package object yaml {
import de.zalando.play.controllers.ArrayWrapper
import org.joda.time.DateTime
import de.zalando.play.controllers.PlayPathBindables
type OrderQuantity = Option[Int]

    type UsersUsernameGetUsername = String

    type UsersCreateWithListPostResponsesDefault = Null

    type UsersCreateWithListPostBodyOpt = ArrayWrapper[User]

    type OrderPetId = Option[Long]

    type PetsFindByStatusGetResponses200 = ArrayWrapper[Pet]

    type PetsPostBody = Option[Pet]

    type UsersUsernamePutBody = Option[User]

    type StoresOrderPostBody = Option[Order]

    type OrderStatus = Option[String]

    type PetTags = Option[PetTagsOpt]

    type OrderComplete = Option[Boolean]

    type PetsPetIdDeletePetId = Long

    type PetTagsOpt = ArrayWrapper[Tag]

    type PetCategory = Option[Tag]

    type OrderShipDate = Option[DateTime]

    type UsersCreateWithListPostBody = Option[UsersCreateWithListPostBodyOpt]

    type PetsFindByStatusGetStatus = Option[PetPhotoUrls]

    type PetPhotoUrls = ArrayWrapper[String]

    case class User(email: OrderStatus, username: OrderStatus, userStatus: OrderQuantity, lastName: OrderStatus, firstName: OrderStatus, id: OrderPetId, phone: OrderStatus, password: OrderStatus) 

    case class Order(shipDate: OrderShipDate, quantity: OrderQuantity, petId: OrderPetId, id: OrderPetId, complete: OrderComplete, status: OrderStatus) 

    case class Tag(id: OrderPetId, name: OrderStatus) 

    case class Pet(name: String, tags: PetTags, photoUrls: PetPhotoUrls, id: OrderPetId, status: OrderStatus, category: PetCategory) 

    


    
    
    import PlayPathBindables.queryBindableDateTime
    implicit val bindable_OptionStringQuery = PlayPathBindables.createOptionQueryBindable[String]
    implicit val bindable_OptionPetPhotoUrlsQuery = PlayPathBindables.createOptionQueryBindable[PetPhotoUrls]
    implicit val bindable_ArrayWrapperStringQuery = PlayPathBindables.createArrayWrapperQueryBindable[String]("csv")
    }
