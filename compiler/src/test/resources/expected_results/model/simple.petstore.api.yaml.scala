package simple.petstore.api
package object yaml {
import java.util.Date
import java.io.File
import de.zalando.play.controllers.ArrayWrapper

    type PetsIdDeleteResponsesDefault = Option[ErrorModel]

    type PetsPostResponses200 = Option[Pet]

    type PetsIdDeleteResponses204 = Null

    type PetsIdDeleteId = Long

    type PetsGetLimit = Option[Int]

    type NewPetId = Option[Long]

    type PetsGetTagsOpt = ArrayWrapper[String]

    type PetsGetResponses200Opt = ArrayWrapper[Pet]

    type PetTag = Option[String]

    type PetsGetResponses200 = Option[PetsGetResponses200Opt]

    type PetsGetTags = Option[PetsGetTagsOpt]

    case class ErrorModel(code: Int, message: String) 

    case class Pet(id: Long, name: String, tag: PetTag) 

    case class NewPet(name: String, id: NewPetId, tag: PetTag) 

    

}
