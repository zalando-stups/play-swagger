package expanded_polymorphism
package object yaml {
import java.util.Date
import java.io.File

type PetsIdDeleteResponsesDefault = Option[Error]

    type PetsIdDeleteResponses204 = Null

    type PetsIdDeleteId = Long

    type PetsGetLimit = Option[Int]

    type NewPetTag = Option[String]

    type PetsGetTagsOpt = scala.collection.Seq[String]

    type PetsGetResponses200Opt = scala.collection.Seq[Pet]

    type PetsGetResponses200 = Option[PetsGetResponses200Opt]

    type PetsGetTags = Option[PetsGetTagsOpt]

    case class NewPet(name: String, tag: NewPetTag) 

    case class Pet(name: String, tag: NewPetTag, id: Long) 

    case class Error(code: Int, message: String) 

    

}
