package heroku.petstore.api
package object yaml {

    type PetName = Option[String]

    type PetIdGetPetId = String

    type PetBirthday = Option[Int]

    type PostResponses200 = Null

    type GetLimit = Int

    type PutPet = Option[Pet]

    type GetResponses200 = Seq[Pet]

    case class Pet(name: PetName, birthday: PetBirthday)
}
