package additional_properties
package object yaml {
import java.util.Date
import java.io.File


    type KeyedArraysAdditionalProperties = scala.collection.immutable.Map[String, KeyedArraysAdditionalPropertiesCatchAll]

    type KeyedArraysAdditionalPropertiesCatchAll = scala.collection.Seq[Int]

    case class KeyedArrays(additionalProperties: KeyedArraysAdditionalProperties
) 

    

}
