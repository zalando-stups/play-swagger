package heroku.petstore.api.yaml
object definitions {
    type PetName = Option[String]
    type PetBirthday = Option[Int]
    case class Pet(name: PetName, birthday: PetBirthday) 
    }
object paths {
    import definitions.Pet
    type GetResponses200Opt = scala.collection.Seq[Pet]
    type GetResponses200 = Option[GetResponses200Opt]
    }
