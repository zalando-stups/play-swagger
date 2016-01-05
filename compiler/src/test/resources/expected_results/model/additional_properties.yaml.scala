package additional_properties
package object yaml {
import scala.collection.immutable.Map
import de.zalando.play.controllers.ArrayWrapper
    type KeyedArraysAdditionalProperties = Map[String, KeyedArraysAdditionalPropertiesCatchAll]

    type KeyedArraysAdditionalPropertiesCatchAll = ArrayWrapper[Int]

    case class KeyedArrays(additionalProperties: KeyedArraysAdditionalProperties) 

    

}
