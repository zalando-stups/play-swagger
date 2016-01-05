package full.petstore.api.yaml

import org.scalacheck.Gen
import org.scalacheck.Arbitrary._

import de.zalando.play.controllers.ArrayWrapper
import org.joda.time.DateTime
object Generators {
def createOrderQuantityGenerator = _generate(OrderQuantityGenerator)

    def createStringGenerator = _generate(StringGenerator)

    def createNullGenerator = _generate(NullGenerator)

    def createUsersCreateWithListPostBodyOptGenerator = _generate(UsersCreateWithListPostBodyOptGenerator)

    def createOrderPetIdGenerator = _generate(OrderPetIdGenerator)

    def createPetsFindByStatusGetResponses200Generator = _generate(PetsFindByStatusGetResponses200Generator)

    def createPetsPostBodyGenerator = _generate(PetsPostBodyGenerator)

    def createUsersUsernamePutBodyGenerator = _generate(UsersUsernamePutBodyGenerator)

    def createStoresOrderPostBodyGenerator = _generate(StoresOrderPostBodyGenerator)

    def createOrderStatusGenerator = _generate(OrderStatusGenerator)

    def createPetTagsGenerator = _generate(PetTagsGenerator)

    def createOrderCompleteGenerator = _generate(OrderCompleteGenerator)

    def createLongGenerator = _generate(LongGenerator)

    def createPetTagsOptGenerator = _generate(PetTagsOptGenerator)

    def createPetsFindByStatusGetResponses200OptGenerator = _generate(PetsFindByStatusGetResponses200OptGenerator)

    def createPetCategoryGenerator = _generate(PetCategoryGenerator)

    def createOrderShipDateGenerator = _generate(OrderShipDateGenerator)

    def createUsersCreateWithListPostBodyGenerator = _generate(UsersCreateWithListPostBodyGenerator)

    def createPetsFindByStatusGetStatusGenerator = _generate(PetsFindByStatusGetStatusGenerator)

    def createPetPhotoUrlsGenerator = _generate(PetPhotoUrlsGenerator)

    def OrderQuantityGenerator = Gen.option(arbitrary[Int])

    def StringGenerator = arbitrary[String]

    def NullGenerator = arbitrary[Null]

    def UsersCreateWithListPostBodyOptGenerator = Gen.containerOf[List,User](UserGenerator)

    def OrderPetIdGenerator = Gen.option(arbitrary[Long])

    def PetsFindByStatusGetResponses200Generator = Gen.option(PetsFindByStatusGetResponses200OptGenerator)

    def PetsPostBodyGenerator = Gen.option(PetGenerator)

    def UsersUsernamePutBodyGenerator = Gen.option(UserGenerator)

    def StoresOrderPostBodyGenerator = Gen.option(OrderGenerator)

    def OrderStatusGenerator = Gen.option(arbitrary[String])

    def PetTagsGenerator = Gen.option(PetTagsOptGenerator)

    def OrderCompleteGenerator = Gen.option(arbitrary[Boolean])

    def LongGenerator = arbitrary[Long]

    def PetTagsOptGenerator = Gen.containerOf[List,Tag](TagGenerator)

    def PetsFindByStatusGetResponses200OptGenerator = Gen.containerOf[List,Pet](PetGenerator)

    def PetCategoryGenerator = Gen.option(TagGenerator)

    def OrderShipDateGenerator = Gen.option(arbitrary[DateTime])

    def UsersCreateWithListPostBodyGenerator = Gen.option(UsersCreateWithListPostBodyOptGenerator)

    def PetsFindByStatusGetStatusGenerator = Gen.option(PetPhotoUrlsGenerator)

    def PetPhotoUrlsGenerator = Gen.containerOf[List,String](arbitrary[String])

    def createUserGenerator = _generate(UserGenerator)

    def createOrderGenerator = _generate(OrderGenerator)

    def createTagGenerator = _generate(TagGenerator)

    def createPetGenerator = _generate(PetGenerator)

    def UserGenerator = for {
        email <- OrderStatusGenerator
        username <- OrderStatusGenerator
        userStatus <- OrderQuantityGenerator
        lastName <- OrderStatusGenerator
        firstName <- OrderStatusGenerator
        id <- OrderPetIdGenerator
        phone <- OrderStatusGenerator
        password <- OrderStatusGenerator
        } yield User(email, username, userStatus, lastName, firstName, id, phone, password)

    def OrderGenerator = for {
        shipDate <- OrderShipDateGenerator
        quantity <- OrderQuantityGenerator
        petId <- OrderPetIdGenerator
        id <- OrderPetIdGenerator
        complete <- OrderCompleteGenerator
        status <- OrderStatusGenerator
        } yield Order(shipDate, quantity, petId, id, complete, status)

    def TagGenerator = for {
        id <- OrderPetIdGenerator
        name <- OrderStatusGenerator
        } yield Tag(id, name)

    def PetGenerator = for {
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
