package heroku.petstore.api
package object yaml {
import de.zalando.play.controllers.PlayPathBindables
type PetName = Option[String]

    type PetIdGetPetId = String

    type PetBirthday = Option[Int]

    type PostResponses200 = Null

    type GetResponses200 = Seq[Pet]

    case class Pet(name: PetName, birthday: PetBirthday) 

    


    
    
    implicit val bindable_OptionIntQuery = PlayPathBindables.createOptionQueryBindable[Int]
    }
