package heroku.petstore.api
package object yaml {
import java.util.Date
import java.io.File

type PetName = Option[String]

    type PetIdGetPetId = String

    type GetResponses200Opt = scala.collection.Seq[Pet]

    type PostResponses200 = Null

    type PetBirthday = Option[Int]

    type GetResponses200 = Option[GetResponses200Opt]

    case class Pet(name: PetName, birthday: PetBirthday) 

    

}
