package full.petstore.api.yaml
import org.scalacheck.Gen
import org.scalacheck.Arbitrary._
import java.util.Date
import java.io.File

object definitionsGenerator {
    def createOrderQuantityGenerator = _generate(OrderQuantityGenerator)

    def createOrderPetIdGenerator = _generate(OrderPetIdGenerator)

    def createOrderStatusGenerator = _generate(OrderStatusGenerator)

    def createPetTagsGenerator = _generate(PetTagsGenerator)

    def createOrderCompleteGenerator = _generate(OrderCompleteGenerator)

    def createPetTagsOptGenerator = _generate(PetTagsOptGenerator)

    def createPetCategoryGenerator = _generate(PetCategoryGenerator)

    def createOrderShipDateGenerator = _generate(OrderShipDateGenerator)

    def createPetPhotoUrlsGenerator = _generate(PetPhotoUrlsGenerator)

    def OrderQuantityGenerator = Gen.option(arbitrary[Int])

    def OrderPetIdGenerator = Gen.option(arbitrary[Long])

    def OrderStatusGenerator = Gen.option(arbitrary[String])

    def PetTagsGenerator = Gen.option(PetTagsOptGenerator)

    def OrderCompleteGenerator = Gen.option(arbitrary[Boolean])

    def PetTagsOptGenerator = Gen.containerOf[List,Tag](TagGenerator)

    def PetCategoryGenerator = Gen.option(TagGenerator)

    def OrderShipDateGenerator = Gen.option(arbitrary[Date])

    def PetPhotoUrlsGenerator = Gen.containerOf[List,String](arbitrary[String])

    def createUserGenerator = _generate(UserGenerator)

    def createOrderGenerator = _generate(OrderGenerator)

    def createTagGenerator = _generate(TagGenerator)

    def createPetGenerator = _generate(PetGenerator)

    def UserGenerator =

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

    

    def OrderGenerator =

        for {

        shipDate <- OrderShipDateGenerator

        quantity <- OrderQuantityGenerator

        petId <- OrderPetIdGenerator

        id <- OrderPetIdGenerator

        complete <- OrderCompleteGenerator

        status <- OrderStatusGenerator

        } yield Order(shipDate, quantity, petId, id, complete, status)

    

    def TagGenerator =

        for {

        id <- OrderPetIdGenerator

        name <- OrderStatusGenerator

        } yield Tag(id, name)

    

    def PetGenerator =

        for {

        name <- arbitrary[String]

        tags <- PetTagsGenerator

        photoUrls <- PetPhotoUrlsGenerator

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
    import definitionsGenerator._
    def createUsersUsernameGetUsernameGenerator = _generate(UsersUsernameGetUsernameGenerator)

    def createUsersCreateWithListPostResponsesDefaultGenerator = _generate(UsersCreateWithListPostResponsesDefaultGenerator)

    def createUsersCreateWithListPostBodyOptGenerator = _generate(UsersCreateWithListPostBodyOptGenerator)

    def createPetsFindByStatusGetResponses200Generator = _generate(PetsFindByStatusGetResponses200Generator)

    def createPetsPostBodyGenerator = _generate(PetsPostBodyGenerator)

    def createUsersUsernamePutBodyGenerator = _generate(UsersUsernamePutBodyGenerator)

    def createStoresOrderPostBodyGenerator = _generate(StoresOrderPostBodyGenerator)

    def createPetsFindByTagsGetResponses200OptGenerator = _generate(PetsFindByTagsGetResponses200OptGenerator)

    def createPetsPetIdDeletePetIdGenerator = _generate(PetsPetIdDeletePetIdGenerator)

    def createUsersCreateWithListPostBodyGenerator = _generate(UsersCreateWithListPostBodyGenerator)

    def createPetsFindByStatusGetStatusGenerator = _generate(PetsFindByStatusGetStatusGenerator)

    def UsersUsernameGetUsernameGenerator = arbitrary[String]

    def UsersCreateWithListPostResponsesDefaultGenerator = arbitrary[Null]

    def UsersCreateWithListPostBodyOptGenerator = Gen.containerOf[List,User](UserGenerator)

    def PetsFindByStatusGetResponses200Generator = Gen.option(PetsFindByTagsGetResponses200OptGenerator)

    def PetsPostBodyGenerator = Gen.option(PetGenerator)

    def UsersUsernamePutBodyGenerator = Gen.option(UserGenerator)

    def StoresOrderPostBodyGenerator = Gen.option(OrderGenerator)

    def PetsFindByTagsGetResponses200OptGenerator = Gen.containerOf[List,Pet](PetGenerator)

    def PetsPetIdDeletePetIdGenerator = arbitrary[Long]

    def UsersCreateWithListPostBodyGenerator = Gen.option(UsersCreateWithListPostBodyOptGenerator)

    def PetsFindByStatusGetStatusGenerator = Gen.option(PetPhotoUrlsGenerator)

    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample

    def _genMap[K,V](keyGen: Gen[K], valGen: Gen[V]): Gen[Map[K,V]] = for {

        keys <- Gen.containerOf[List,K](keyGen)

        values <- Gen.containerOfN[List,V](keys.size, valGen)

    } yield keys.zip(values).toMap

}
