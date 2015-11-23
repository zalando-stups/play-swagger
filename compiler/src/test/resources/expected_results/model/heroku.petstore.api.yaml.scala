package heroku.petstore.api.yaml
object definitions {
    type PetName = Option[String]
    type PetBirthday = Option[Int]
    case class Pet(name: PetName, birthday: PetBirthday) 
    }
object paths {
    import definitions.Pet
    type PetIGetPetId = String
    type GetResponses200Opt = scala.collection.Seq[Pet]
    type PostResponses200 = Null
    type GetResponses200 = Option[GetResponses200Opt]
    }
