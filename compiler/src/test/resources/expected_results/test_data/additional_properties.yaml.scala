package additional_properties.yaml
import org.scalacheck.Gen
import org.scalacheck.Arbitrary._

object definitionsGenerator {
    import definitions.{KeyedArrays, KeyedArraysAdditionalProperties, KeyedArraysAdditionalPropertiesCatchAll}
    def createKeyedArraysAdditionalPropertiesGenerator = _generate(KeyedArraysAdditionalPropertiesGenerator)
    def createKeyedArraysAdditionalPropertiesCatchAllGenerator = _generate(KeyedArraysAdditionalPropertiesCatchAllGenerator)
    val KeyedArraysAdditionalPropertiesGenerator = _genMap[String,KeyedArraysAdditionalPropertiesCatchAll](arbitrary[String], KeyedArraysAdditionalPropertiesCatchAllGenerator)
    val KeyedArraysAdditionalPropertiesCatchAllGenerator = Gen.containerOf[List,Int](arbitrary[Int])
    def createKeyedArraysGenerator = _generate(KeyedArraysGenerator)
    val KeyedArraysGenerator =
        for {
        additionalProperties <- KeyedArraysAdditionalPropertiesGenerator
        } yield KeyedArrays(additionalProperties)
    
    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample

    def _genMap[K,V](keyGen: Gen[K], valGen: Gen[V]): Gen[Map[K,V]] = for {
        keys <- Gen.containerOf[List,K](keyGen)
        values <- Gen.containerOfN[List,V](keys.size, valGen)
    } yield keys.zip(values).toMap
}
