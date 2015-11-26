package echo.api.yaml
import org.scalacheck.Gen
import org.scalacheck.Arbitrary._

object pathsGenerator {
    import paths.{PostName, `Test-pathIdGetId`, `Test-pathIdGetResponses200`}
    def `createTest-pathIdGetResponses200Generator` = _generate(`Test-pathIdGetResponses200Generator`)
    def createPostNameGenerator = _generate(PostNameGenerator)
    def `createTest-pathIdGetIdGenerator` = _generate(`Test-pathIdGetIdGenerator`)
    val `Test-pathIdGetResponses200Generator` = arbitrary[Null]
    val PostNameGenerator = Gen.option(arbitrary[String])
    val `Test-pathIdGetIdGenerator` = arbitrary[String]
    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample

    def _genMap[K,V](keyGen: Gen[K], valGen: Gen[V]): Gen[Map[K,V]] = for {
        keys <- Gen.containerOf[List,K](keyGen)
        values <- Gen.containerOfN[List,V](keys.size, valGen)
    } yield keys.zip(values).toMap
}
