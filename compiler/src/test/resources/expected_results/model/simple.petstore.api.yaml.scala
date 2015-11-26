package simple.petstore.api.yaml
object definitions {
    type NewPetTag = Option[String]
    type NewPetId = Option[Long]
    case class ErrorModel(code: Int, message: String) 
    case class Pet(id: Long, name: String, tag: NewPetTag) 
    case class NewPet(name: String, id: NewPetId, tag: NewPetTag) 
    }
object paths {
    import definitions.{ErrorModel, Pet}
    type PetsIdDeleteResponsesDefault = Option[ErrorModel]
    type PetsPostResponses200 = Option[Pet]
    type PetsIdDeleteResponses204 = Null
    type PetsIdDeleteId = Long
    type PetsGetLimit = Option[Int]
    type PetsGetTagsOpt = scala.collection.Seq[String]
    type PetsGetResponses200Opt = scala.collection.Seq[Pet]
    type PetsGetResponses200 = Option[PetsGetResponses200Opt]
    type PetsGetTags = Option[PetsGetTagsOpt]
    }
