package full.petstore.api
package object yaml {
import java.util.Date
import java.io.File


    type OrderQuantity = Option[Int]

    type OrderPetId = Option[Long]

    type OrderStatus = Option[String]

    type PetTags = Option[PetTagsOpt]

    type OrderComplete = Option[Boolean]

    type PetTagsOpt = scala.collection.Seq[Tag]

    type PetCategory = Option[Tag]

    type OrderShipDate = Option[Date]

    type PetPhotoUrls = scala.collection.Seq[String]

    case class User(email: OrderStatus, 
username: OrderStatus, 
userStatus: OrderQuantity, 
lastName: OrderStatus, 
firstName: OrderStatus, 
id: OrderPetId, 
phone: OrderStatus, 
password: OrderStatus
) 

    case class Order(shipDate: OrderShipDate, 
quantity: OrderQuantity, 
petId: OrderPetId, 
id: OrderPetId, 
complete: OrderComplete, 
status: OrderStatus
) 

    case class Tag(id: OrderPetId, 
name: OrderStatus
) 

    case class Pet(name: String, 
tags: PetTags, 
photoUrls: PetPhotoUrls, 
id: OrderPetId, 
status: OrderStatus, 
category: PetCategory
) 

    


    type UsersUsernameGetUsername = String

    type UsersCreateWithListPostResponsesDefault = Null

    type UsersCreateWithListPostBodyOpt = scala.collection.Seq[User]

    type PetsFindByStatusGetResponses200 = Option[PetsFindByTagsGetResponses200Opt]

    type PetsPostBody = Option[Pet]

    type UsersUsernamePutBody = Option[User]

    type StoresOrderPostBody = Option[Order]

    type PetsFindByTagsGetResponses200Opt = scala.collection.Seq[Pet]

    type PetsPetIdDeletePetId = Long

    type UsersCreateWithListPostBody = Option[UsersCreateWithListPostBodyOpt]

    type PetsFindByStatusGetStatus = Option[PetPhotoUrls]

    

}
