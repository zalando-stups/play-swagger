package full.petstore.api.yaml

import org.scalacheck.Gen
import org.scalacheck.Arbitrary
import Arbitrary._

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

    def UsersCreateWithListPostBodyOptGenerator = _genList(UserGenerator, "csv")

    def OrderPetIdGenerator = Gen.option(arbitrary[Long])

    def PetsFindByStatusGetResponses200Generator = Gen.option(PetsFindByStatusGetResponses200OptGenerator)

    def PetsPostBodyGenerator = Gen.option(PetGenerator)

    def UsersUsernamePutBodyGenerator = Gen.option(UserGenerator)

    def StoresOrderPostBodyGenerator = Gen.option(OrderGenerator)

    def OrderStatusGenerator = Gen.option(arbitrary[String])

    def PetTagsGenerator = Gen.option(PetTagsOptGenerator)

    def OrderCompleteGenerator = Gen.option(arbitrary[Boolean])

    def LongGenerator = arbitrary[Long]

    def PetTagsOptGenerator = _genList(TagGenerator, "csv")

    def PetsFindByStatusGetResponses200OptGenerator = _genList(PetGenerator, "csv")

    def PetCategoryGenerator = Gen.option(TagGenerator)

    def OrderShipDateGenerator = Gen.option(arbitrary[DateTime])

    def UsersCreateWithListPostBodyGenerator = Gen.option(UsersCreateWithListPostBodyOptGenerator)

    def PetsFindByStatusGetStatusGenerator = Gen.option(PetPhotoUrlsGenerator)

    def PetPhotoUrlsGenerator = _genList(arbitrary[String], "csv")

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
    def _genList[T](gen: Gen[T], format: String): Gen[ArrayWrapper[T]] = for {
        items <- Gen.containerOf[List,T](gen)
    } yield ArrayWrapper(format)(items)
    implicit lazy val arbDateTime: Arbitrary[DateTime] = Arbitrary(for {
        l <- arbitrary[Long]
    } yield new DateTime(System.currentTimeMillis + l))
}
