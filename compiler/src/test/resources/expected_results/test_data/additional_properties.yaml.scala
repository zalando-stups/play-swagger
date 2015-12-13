package additional_properties.yaml
import org.scalacheck.Gen
import org.scalacheck.Arbitrary._
import java.util.Date
import java.io.File

object definitionsGenerator {
    def createKeyedArraysAdditionalPropertiesGenerator = _generate(KeyedArraysAdditionalPropertiesGenerator)

    def createKeyedArraysAdditionalPropertiesCatchAllGenerator = _generate(KeyedArraysAdditionalPropertiesCatchAllGenerator)

    def KeyedArraysAdditionalPropertiesGenerator = _genMap[String,KeyedArraysAdditionalPropertiesCatchAll](arbitrary[String], KeyedArraysAdditionalPropertiesCatchAllGenerator)

    def KeyedArraysAdditionalPropertiesCatchAllGenerator = Gen.containerOf[List,Int](arbitrary[Int])

    def createKeyedArraysGenerator = _generate(KeyedArraysGenerator)

    def KeyedArraysGenerator =

        for {

        additionalProperties <- KeyedArraysAdditionalPropertiesGenerator

        } yield KeyedArrays(additionalProperties)

    

    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample

    def _genMap[K,V](keyGen: Gen[K], valGen: Gen[V]): Gen[Map[K,V]] = for {

        keys <- Gen.containerOf[List,K](keyGen)

        values <- Gen.containerOfN[List,V](keys.size, valGen)

    } yield keys.zip(values).toMap

}
