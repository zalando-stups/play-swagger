package security.api
package object yaml {
import de.zalando.play.controllers.ArrayWrapper
import de.zalando.play.controllers.PlayPathBindables
    type PetsIdGetId = ArrayWrapper[String]

    type PetsIdGetResponsesDefault = Option[ErrorModel]

    type PetTag = Option[String]

    type PetsIdGetResponses200 = Option[PetsIdGetResponses200Opt]

    type PetsIdGetResponses200Opt = ArrayWrapper[Pet]

    case class ErrorModel(code: Int, message: String) 

    case class Pet(name: String, tag: PetTag)

    implicit val bindable_ArrayWrapperStringPath = PlayPathBindables.createArrayWrapperPathBindable[String]("csv")

}
