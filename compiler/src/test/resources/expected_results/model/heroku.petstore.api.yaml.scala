package heroku.petstore.api
package object yaml {
import de.zalando.play.controllers.ArrayWrapper
    type PetName = Option[String]

    type PetIdGetPetId = String

    type GetResponses200Opt = ArrayWrapper[Pet]

    type PostResponses200 = Null

    type PetBirthday = Option[Int]

    type GetResponses200 = Option[GetResponses200Opt]

    case class Pet(name: PetName, birthday: PetBirthday) 

    

}
