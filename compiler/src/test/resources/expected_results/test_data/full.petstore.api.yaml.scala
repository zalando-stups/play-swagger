package full.petstore.api.yaml
import org.scalacheck.Gen
import org.scalacheck.Arbitrary._

object definitionsGenerator {
    import definitions._
    import java.util.Date
    def createOrderQuantityGenerator = _generate(OrderQuantityGenerator)
    def createOrderPetIdGenerator = _generate(OrderPetIdGenerator)
    def createOrderStatusGenerator = _generate(OrderStatusGenerator)
    def createPetTagsGenerator = _generate(PetTagsGenerator)
    def createOrderCompleteGenerator = _generate(OrderCompleteGenerator)
    def createPetCategoryGenerator = _generate(PetCategoryGenerator)
    def createOrderShipDateGenerator = _generate(OrderShipDateGenerator)
    val OrderQuantityGenerator = Gen.option(arbitrary[Int])
    val OrderPetIdGenerator = Gen.option(arbitrary[Long])
    val OrderStatusGenerator = Gen.option(arbitrary[String])
    val PetTagsGenerator = Gen.option(PetCategoryGenerator)
    val OrderCompleteGenerator = Gen.option(arbitrary[Boolean])
    val PetCategoryGenerator = Gen.option(TagGenerator)
    val OrderShipDateGenerator = Gen.option(arbitrary[Date])
    def createUserGenerator = _generate(UserGenerator)
    def createOrderGenerator = _generate(OrderGenerator)
    def createTagGenerator = _generate(TagGenerator)
    def createPetGenerator = _generate(PetGenerator)
    val UserGenerator =
        for {
        email <- OrderStatusGenerator
        username <- OrderStatusGenerator
        userStatus <- OrderQuantityGenerator
        lastName <- OrderStatusGenerator
        firstName <- OrderStatusGenerator
        id <- OrderPetIdGenerator
        phone <- OrderStatusGenerator
        password <- OrderStatusGenerator
        } yield User(email, username, userStatus, lastName, firstName, id, phone, password)
    
    val OrderGenerator =
        for {
        shipDate <- OrderShipDateGenerator
        quantity <- OrderQuantityGenerator
        petId <- OrderPetIdGenerator
        id <- OrderPetIdGenerator
        complete <- OrderCompleteGenerator
        status <- OrderStatusGenerator
        } yield Order(shipDate, quantity, petId, id, complete, status)
    
    val TagGenerator =
        for {
        id <- OrderPetIdGenerator
        name <- OrderStatusGenerator
        } yield Tag(id, name)
    
    val PetGenerator =
        for {
        name <- arbitrary[String]
        tags <- PetTagsGenerator
        photoUrls <- OrderStatusGenerator
        id <- OrderPetIdGenerator
        status <- OrderStatusGenerator
        category <- PetCategoryGenerator
        } yield Pet(name, tags, photoUrls, id, status, category)
    
    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample

    def _genMap[K,V](keyGen: Gen[K], valGen: Gen[V]): Gen[Map[K,V]] = for {
        keys <- Gen.containerOf[List,K](keyGen)
        values <- Gen.containerOfN[List,V](keys.size, valGen)
    } yield keys.zip(values).toMap
}
object pathsGenerator {
    import definitions._
    import paths._
    import definitionsGenerator._
    def createUsersUsernameGetUsernameGenerator = _generate(UsersUsernameGetUsernameGenerator)
    def createUsersCreateWithListPostResponsesDefaultGenerator = _generate(UsersCreateWithListPostResponsesDefaultGenerator)
    def createPetsFindByStatusGetResponses200Generator = _generate(PetsFindByStatusGetResponses200Generator)
    def createPetsPostBodyGenerator = _generate(PetsPostBodyGenerator)
    def createUsersUsernamePutBodyGenerator = _generate(UsersUsernamePutBodyGenerator)
    def createStoresOrderPostBodyGenerator = _generate(StoresOrderPostBodyGenerator)
    def createPetsPetIdDeletePetIdGenerator = _generate(PetsPetIdDeletePetIdGenerator)
    def createUsersCreateWithListPostBodyGenerator = _generate(UsersCreateWithListPostBodyGenerator)
    def createPetsFindByStatusGetStatusGenerator = _generate(PetsFindByStatusGetStatusGenerator)
    val UsersUsernameGetUsernameGenerator = arbitrary[String]
    val UsersCreateWithListPostResponsesDefaultGenerator = arbitrary[Null]
    val PetsFindByStatusGetResponses200Generator = Gen.option(PetsPostBodyGenerator)
    val PetsPostBodyGenerator = Gen.option(PetGenerator)
    val UsersUsernamePutBodyGenerator = Gen.option(UserGenerator)
    val StoresOrderPostBodyGenerator = Gen.option(OrderGenerator)
    val PetsPetIdDeletePetIdGenerator = arbitrary[Long]
    val UsersCreateWithListPostBodyGenerator = Gen.option(UsersUsernamePutBodyGenerator)
    val PetsFindByStatusGetStatusGenerator = Gen.option(OrderStatusGenerator)
    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample

    def _genMap[K,V](keyGen: Gen[K], valGen: Gen[V]): Gen[Map[K,V]] = for {
        keys <- Gen.containerOf[List,K](keyGen)
        values <- Gen.containerOfN[List,V](keys.size, valGen)
    } yield keys.zip(values).toMap
}
