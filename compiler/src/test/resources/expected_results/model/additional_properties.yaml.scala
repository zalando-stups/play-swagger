package additional_properties
package object yaml {
import java.util.Date
import java.io.File
import de.zalando.play.controllers.ArrayWrapper

type KeyedArraysAdditionalProperties = scala.collection.immutable.Map[String, KeyedArraysAdditionalPropertiesCatchAll]

    type KeyedArraysAdditionalPropertiesCatchAll = ArrayWrapper[Int]

    case class KeyedArrays(additionalProperties: KeyedArraysAdditionalProperties) 

    

}
