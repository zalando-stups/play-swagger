package additional_properties.yaml
import scala.collection.immutable.Map
object definitions {
  // An object, all keys of which represent and ID
  case class KeyedArrays(
    // Catch-All property
    additionalProperties: Map[String, Int]
  )
}