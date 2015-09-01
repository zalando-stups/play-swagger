package full.petstore.api.yaml
import java.util.Date
import org.scalacheck.Arbitrary._
import org.scalacheck.Gen
import scala.collection.Seq
import scala.Option
object generatorDefinitions {
  import definitions.Pet
  import definitions.Category
  import definitions.Tag
  import definitions.User
  import definitions.Order
  def genUser = _generate(UserGenerator)
  def genTag = _generate(TagGenerator)
  def genCategory = _generate(CategoryGenerator)
  def genPet = _generate(PetGenerator)
  def genOrder = _generate(OrderGenerator)
  // test data generator for /definitions/Order
  val OrderGenerator =
    for {
      shipDate <- Gen.option(arbitrary[Date])
      quantity <- Gen.option(arbitrary[Int])
      petId <- Gen.option(arbitrary[Long])
      id <- Gen.option(arbitrary[Long])
      complete <- Gen.option(arbitrary[Boolean])
      status <- Gen.option(arbitrary[String])
    } yield Order(shipDate, quantity, petId, id, complete, status)
  // test data generator for /definitions/Pet
  val PetGenerator =
    for {
      name <- arbitrary[String]
      tags <- Gen.option(Gen.containerOf[List,Tag](generatorDefinitions.TagGenerator))
      photoUrls <- Gen.containerOf[List,String](arbitrary[String])
      id <- Gen.option(arbitrary[Long])
      status <- Gen.option(arbitrary[String])
      category <- Gen.option(generatorDefinitions.CategoryGenerator)
    } yield Pet(name, tags, photoUrls, id, status, category)
  // test data generator for /definitions/User
  val UserGenerator =
    for {
      email <- Gen.option(arbitrary[String])
      username <- Gen.option(arbitrary[String])
      userStatus <- Gen.option(arbitrary[Int])
      lastName <- Gen.option(arbitrary[String])
      firstName <- Gen.option(arbitrary[String])
      id <- Gen.option(arbitrary[Long])
      phone <- Gen.option(arbitrary[String])
      password <- Gen.option(arbitrary[String])
    } yield User(email, username, userStatus, lastName, firstName, id, phone, password)
  // test data generator for /definitions/Tag
  val TagGenerator =
    for {
      id <- Gen.option(arbitrary[Long])
      name <- Gen.option(arbitrary[String])
    } yield Tag(id, name)
  // test data generator for /definitions/Category
  val CategoryGenerator =
    for {
      id <- Gen.option(arbitrary[Long])
      name <- Gen.option(arbitrary[String])
    } yield Category(id, name)
  def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
}