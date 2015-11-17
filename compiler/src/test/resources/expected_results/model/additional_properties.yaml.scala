package additional_properties.yaml
object definitions {
    type KeyedArraysAdditionalProperties = scala.collection.immutable.Map[String, KeyedArraysAdditionalPropertiesCatchAll]
    type KeyedArraysAdditionalPropertiesCatchAll = scala.collection.Seq[Int]
    case class KeyedArrays(additionalProperties: KeyedArraysAdditionalProperties) 
    }
