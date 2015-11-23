package echo.api.yaml
import org.scalacheck.Gen
import org.scalacheck.Arbitrary._

object pathsGenerator {
    import paths._
    def `createTest-pathIGetResponses200Generator` = _generate(`Test-pathIGetResponses200Generator`)
    def createPostNameGenerator = _generate(PostNameGenerator)
    def `createTest-pathIGetIdGenerator` = _generate(`Test-pathIGetIdGenerator`)
    val `Test-pathIGetResponses200Generator` = arbitrary[Null]
    val PostNameGenerator = Gen.option(arbitrary[String])
    val `Test-pathIGetIdGenerator` = arbitrary[String]
    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample

    def _genMap[K,V](keyGen: Gen[K], valGen: Gen[V]): Gen[Map[K,V]] = for {
        keys <- Gen.containerOf[List,K](keyGen)
        values <- Gen.containerOfN[List,V](keys.size, valGen)
    } yield keys.zip(values).toMap
}
