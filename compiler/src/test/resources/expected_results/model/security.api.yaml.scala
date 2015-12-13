package security.api
package object yaml {
import java.util.Date
import java.io.File


    type PetTag = Option[String]

    case class ErrorModel(code: Int, 
message: String
) 

    case class Pet(name: String, 
tag: PetTag
) 

    


    type PetsIdGetId = scala.collection.Seq[String]

    type PetsIdGetResponsesDefault = Option[ErrorModel]

    type PetsIdGetResponses200 = Option[PetsIdGetResponses200Opt]

    type PetsIdGetResponses200Opt = scala.collection.Seq[Pet]

    

}
