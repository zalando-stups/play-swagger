package simple.petstore.api
package object yaml {
import de.zalando.play.controllers.ArrayWrapper
import de.zalando.play.controllers.PlayPathBindables
type PetsIdDeleteResponses204 = Null

    type PetsIdDeleteId = Long

    type PetsGetLimit = Option[Int]

    type NewPetId = Option[Long]

    type PetsGetTagsOpt = ArrayWrapper[String]

    type PetTag = Option[String]

    type PetsGetResponses200 = Seq[Pet]

    type PetsGetTags = Option[PetsGetTagsOpt]

    case class ErrorModel(code: Int, message: String) 

    case class Pet(id: Long, name: String, tag: PetTag) 

    case class NewPet(name: String, id: NewPetId, tag: PetTag) 

    


    
    
    implicit val bindable_OptionIntQuery = PlayPathBindables.createOptionQueryBindable[Int]
    implicit val bindable_OptionPetsGetTagsOptQuery = PlayPathBindables.createOptionQueryBindable[PetsGetTagsOpt]
    implicit val bindable_ArrayWrapperStringQuery = PlayPathBindables.createArrayWrapperQueryBindable[String]("csv")
    }
