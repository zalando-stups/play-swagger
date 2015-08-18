package de.zalando.play.compiler

import java.io.{FileOutputStream, File}

import de.zalando.apifirst.Domain
import de.zalando.swagger.{Swagger2Ast, YamlParser}
import org.scalatest.{FunSpec, MustMatchers}

import scala.language.implicitConversions

class ModelGeneratorTest extends FunSpec with MustMatchers {

  def removeNoise(s: String) =
    s.split("\n").filterNot(_.trim.isEmpty).filterNot(_.contains("@since")).mkString("\n")

  describe("Model Generator standalone") {
    it("should convert empty model") {
      val expected = removeNoise(ModelGenerator.template.
        replace("#IMPORT#", "").
        replace("#NAMESPACE#", "definitions").
        replace("#PACKAGE#", "package pkg").
        replace("#TRAITS#", "").
        replace("#CLASSES#", "")
      )
      implicit val emptyModel = Map.empty[String, Domain.Type]
      val result = removeNoise(ModelGenerator.generate(Some("pkg")))
      result mustBe expected
    }
/*
    it ("should escape package if it contains reserved words") {
      fail("Not implemented yet") TODO
    }
*/
  }

  val standardDefinitions = Map(
  "uber.api.yaml" ->
    """package pkg
      |import scala.collection.Seq
      |object definitions {
      |  case class Profile(
      |    // First name of the Uber user.
      |    first_name: String,
      |    // Email address of the Uber user
      |    email: String,
      |    // Promo code of the Uber user.
      |    promo_code: String,
      |    // Last name of the Uber user.
      |    last_name: String,
      |    // Image URL of the Uber user.
      |    picture: String
      |  )
      |  case class Activity(
      |    // Unique identifier for the activity
      |    uuid: String
      |  )
      |  case class Error(
      |    code: Int,
      |    message: String,
      |    fields: String
      |  )
      |  case class PriceEstimate(
      |    // Lower bound of the estimated price.
      |    low_estimate: Double,
      |    // Display name of product.
      |    display_name: String,
      |    // Formatted string of estimate in local currency of the start location. Estimate could be a range, a single number (flat rate) or "Metered" for TAXI.
      |    estimate: String,
      |    // Upper bound of the estimated price.
      |    high_estimate: Double,
      |    // Unique identifier representing a specific product for a given latitude & longitude. For example, uberX in San Francisco will have a different product_id than uberX in Los Angeles
      |    product_id: String,
      |    // [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217) currency code.
      |    currency_code: String,
      |    // Expected surge multiplier. Surge is active if surge_multiplier is greater than 1. Price estimate already factors in the surge multiplier.
      |    surge_multiplier: Double
      |  )
      |  case class Activities(
      |    // Position in pagination.
      |    offset: Int,
      |    // Number of items to retrieve (100 max).
      |    limit: Int,
      |    // Total number of items available.
      |    count: Int,
      |    history: Seq[Activity]
      |  )
      |  case class Product(
      |    // Image URL representing the product.
      |    image: String,
      |    // Description of product.
      |    description: String,
      |    // Display name of product.
      |    display_name: String,
      |    // Unique identifier representing a specific product for a given latitude & longitude. For example, uberX in San Francisco will have a different product_id than uberX in Los Angeles.
      |    product_id: String,
      |    // Capacity of product. For example, 4 people.
      |    capacity: String
      |  )
      |}""".stripMargin
  ,
  "simple.petstore.api.yaml" ->
    """package pkg
      |object definitions {
      |  case class pet(
      |    id: Long,
      |    name: String,
      |    tag: String
      |  )
      |  case class newPet(
      |    id: Long,
      |    name: String,
      |    tag: String
      |  )
      |  case class errorModel(
      |    code: Int,
      |    message: String
      |  )
      |}""".stripMargin
  ,
  "security.api.yaml" ->
    """package pkg
      |object definitions {
      |  case class Pet(
      |    name: String,
      |    tag: String
      |  )
      |  case class ErrorModel(
      |    code: Int,
      |    message: String
      |  )
      |}""".stripMargin
  ,
  "instagram.api.yaml" ->
    """package pkg
      |import scala.collection.Seq
      |object definitions {
      |  case class Media(
      |    location: Location,
      |    // Epoc time (ms)
      |    created_time: Int,
      |    comments:: comments:,
      |    tags: Seq[Tag],
      |    users_in_photo: Seq[MiniProfile],
      |    filter: String,
      |    likes: likes,
      |    id: Int,
      |    videos: videos,
      |    type: String,
      |    images: images,
      |    user: MiniProfile
      |  )
      |  case class Image(
      |    width: Int,
      |    height: Int,
      |    url: String
      |  )
      |  case class Like(
      |    first_name: String,
      |    id: String,
      |    last_name: String,
      |    type: String,
      |    user_name: String
      |  )
      |  case class Location(
      |    id: String,
      |    name: String,
      |    latitude: Double,
      |    longitude: Double
      |  )
      |  case class Comment(
      |    id: String,
      |    created_time: String,
      |    text: String,
      |    from: MiniProfile
      |  )
      |  case class User(
      |    website: String,
      |    profile_picture: String,
      |    username: String,
      |    full_name: String,
      |    bio: String,
      |    id: Int,
      |    counts: counts
      |  )
      |  // A shorter version of User for likes array
      |  case class MiniProfile(
      |    user_name: String,
      |    full_name: String,
      |    id: Int,
      |    profile_picture: String
      |  )
      |  case class Tag(
      |    media_count: Int,
      |    name: String
      |  )
      |}""".stripMargin
  ,
  "heroku.petstore.api.yaml" ->
    """package pkg
      |object definitions {
      |  case class Pet(
      |    name: String,
      |    birthday: Int
      |  )
      |}""".stripMargin
  ,
  "full.petstore.api.yaml" ->
    """package pkg
      |import scala.collection.Seq
      |object definitions {
      |  case class Category(
      |    id: Long,
      |    name: String
      |  )
      |  case class Tag(
      |    id: Long,
      |    name: String
      |  )
      |  case class Pet(
      |    name: String,
      |    tags: Seq[Tag],
      |    photoUrls: Seq[String],
      |    id: Long,
      |    // pet status in the store
      |    status: String,
      |    category: Category
      |  )
      |  case class Order(
      |    shipDate: java.util.Date,
      |    quantity: Int,
      |    petId: Long,
      |    id: Long,
      |    complete: Boolean,
      |    // Order Status
      |    status: String
      |  )
      |  case class User(
      |    email: String,
      |    username: String,
      |    // User Status
      |    userStatus: Int,
      |    lastName: String,
      |    firstName: String,
      |    id: Long,
      |    phone: String,
      |    password: String
      |  )
      |}""".stripMargin
  ,
    "basic_extension.yaml" ->
      """package pkg
        |object definitions {
        |  // Basic error model
        |  trait ErrorModelDef {
        |    // The text of the error message
        |    def message: String
        |    // The error code
        |    def code: Int
        |  }
        |  // Basic error model
        |  case class ErrorModel(
        |    // The text of the error message
        |    message: String,
        |    // The error code
        |    code: Int
        |  ) extends ErrorModelDef
        |  // Extended error model
        |  case class ExtendedErrorModel(
        |    rootCause: String,
        |    // The text of the error message
        |    message: String,
        |    // The error code
        |    code: Int
        |  ) extends ErrorModelDef
        |}""".stripMargin
  ,
    "basic_polymorphism.yaml" ->
      """package pkg
        |object definitions {
        |  trait PetDef {
        |    def name: String
        |    def petType: String
        |  }
        |  // A representation of a dog
        |  trait DogDef extends PetDef {
        |    // the size of the pack the dog is from
        |    def packSize: Int
        |  }
        |  case class Pet(
        |    name: String,
        |    petType: String
        |  ) extends PetDef
        |  // A representation of a cat
        |  case class Cat(
        |    // The measured skill for hunting
        |    huntingSkill: String,
        |    name: String,
        |    petType: String
        |  ) extends PetDef
        |  // A representation of a dog
        |  case class Dog(
        |    // the size of the pack the dog is from
        |    packSize: Int,
        |    name: String,
        |    petType: String
        |  ) extends PetDef with DogDef
        |  // A concrete implementation of a dog
        |  case class Labrador(
        |    // the cuteness of the animal in percent
        |    cuteness: Int,
        |    // the size of the pack the dog is from
        |    packSize: Int,
        |    name: String,
        |    petType: String
        |  ) extends DogDef
        |}""".stripMargin

  ).withDefaultValue(
      """package pkg
        |object definitions {
        |}""".stripMargin)

  val specialDefinitions = Map(
    "nested_arrays.yaml" ->
      """package pkg
        |import scala.Option
        |import scala.collection.Seq
        |object definitions {
        |  case class Example(
        |    // The text of the error message
        |    messages: Option[Seq[Seq[Activity]]],
        |    nestedArrays: Option[Seq[Seq[Seq[Seq[String]]]]]
        |  )
        |}""".stripMargin
  ,
    "options.yaml" ->
      """package pkg
        |import scala.collection.Seq
        |import scala.Option
        |object definitions {
        |  case class Basic(
        |    id: Long,
        |    required: Seq[String],
        |    optional: Option[Seq[String]]
        |  )
        |}""".stripMargin
  ).withDefaultValue(
      """package pkg
        |object definitions {
        |}""".stripMargin)

/*
  describe("Model Generator standard tests") {
    val fixtures = new File("compiler/src/test/resources/examples").listFiles
    testFixture(fixtures, standardDefinitions)
  }
*/

  describe("Model Generator special cases") {
    val fixtures = new File("compiler/src/test/resources/model").listFiles
    testFixture(fixtures, specialDefinitions)
  }

  def testFixture(fixtures: Array[File], expectedDefinitions: Map[String, String]): Unit = {
    fixtures.filter(_.getName.endsWith(".yaml")).foreach { file =>
        it(s"should parse the yaml swagger file ${file.getName} with expected definitions result") {
        val swaggerModel = YamlParser.parse(file)
        implicit val definitions = Swagger2Ast.convertDefinitions(swaggerModel)
        val fullResult = ModelGenerator.generate(Some("pkg"))
        val result = removeNoise(fullResult)
        val newFile = new File("./src/test/scala/pkg/" + file.getName + ".scala")
        newFile.getParentFile.mkdirs()
        newFile.delete()
        newFile.createNewFile()
        val out = new FileOutputStream(newFile)
        out.write(fullResult.getBytes)
        out.close
        println(newFile.getAbsolutePath)
        result mustBe expectedDefinitions(file.getName)
      }
    }
  }
}
