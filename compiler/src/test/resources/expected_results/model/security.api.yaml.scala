package security.api.yaml
object definitions {
    type PetTag = Option[String]
    case class ErrorModel(code: Int, message: String) 
    case class Pet(name: String, tag: PetTag) 
    }
object paths {
    import definitions.ErrorModel
    import definitions.Pet
    type PetsIGetResponsesDefault = Option[ErrorModel]
    type PetsIGetResponses200 = Option[PetsIGetResponses200Opt]
    type PetsIGetResponses200Opt = scala.collection.Seq[Pet]
    }
