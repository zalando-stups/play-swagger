package de.zalando.swagger

import java.io.File

import de.zalando.apifirst.Domain
import Domain._
import org.scalatest.{FunSpec, MustMatchers}

class DefinitionsConverterTest extends FunSpec with MustMatchers {

  val fixtures = new File("compiler/src/test/resources/examples").listFiles

  val expectedDefinitions = Map(

    "full.petstore.api.yaml" -> Map(
      "/definitions/Order" ->
        TypeDef("Order", List(
          Field("shipDate", Opt(Field("shipDate", DateTime(TypeMeta(Some("date-time"))), TypeMeta(None)), TypeMeta(None)), TypeMeta(None)),
          Field("quantity", Opt(Field("quantity", Int(TypeMeta(Some("int32"))), TypeMeta(None)), TypeMeta(None)), TypeMeta(None)),
          Field("petId", Opt(Field("petId", Lng(TypeMeta(Some("int64"))), TypeMeta(None)), TypeMeta(None)), TypeMeta(None)),
          Field("id", Opt(Field("id", Lng(TypeMeta(Some("int64"))), TypeMeta(None)), TypeMeta(None)), TypeMeta(None)),
          Field("complete", Opt(Field("complete", Bool(TypeMeta(None)), TypeMeta(None)), TypeMeta(None)), TypeMeta(None)),
          Field("status", Opt(Field("status", Str(None, TypeMeta(None)), TypeMeta(Some("Order Status"))), TypeMeta(Some("Order Status"))), TypeMeta(Some("Order Status")))), List(), TypeMeta(None))
      , "/definitions/User" ->
        TypeDef("User", List(
          Field("email", Opt(Field("email", Str(None, TypeMeta(None)), TypeMeta(None)), TypeMeta(None)), TypeMeta(None)),
          Field("username", Opt(Field("username", Str(None, TypeMeta(None)), TypeMeta(None)), TypeMeta(None)), TypeMeta(None)),
          Field("userStatus", Opt(Field("userStatus", Int(TypeMeta(Some("int32"))), TypeMeta(Some("User Status"))), TypeMeta(Some("User Status"))), TypeMeta(Some("User Status"))),
          Field("lastName", Opt(Field("lastName", Str(None, TypeMeta(None)), TypeMeta(None)), TypeMeta(None)), TypeMeta(None)),
          Field("firstName", Opt(Field("firstName", Str(None, TypeMeta(None)), TypeMeta(None)), TypeMeta(None)), TypeMeta(None)),
          Field("id", Opt(Field("id", Lng(TypeMeta(Some("int64"))), TypeMeta(None)), TypeMeta(None)), TypeMeta(None)),
          Field("phone", Opt(Field("phone", Str(None, TypeMeta(None)), TypeMeta(None)), TypeMeta(None)), TypeMeta(None)),
          Field("password", Opt(Field("password", Str(None, TypeMeta(None)), TypeMeta(None)), TypeMeta(None)), TypeMeta(None))), List(), TypeMeta(None))
      , "/definitions/Tag" ->
        TypeDef("Tag", List(
          Field("id", Opt(Field("id", Lng(TypeMeta(Some("int64"))), TypeMeta(None)), TypeMeta(None)), TypeMeta(None)),
          Field("name", Opt(Field("name", Str(None, TypeMeta(None)), TypeMeta(None)), TypeMeta(None)), TypeMeta(None))), List(), TypeMeta(None))
      , "/definitions/Pet" ->
        TypeDef("Pet", List(
          Field("name", Str(None, TypeMeta(None)), TypeMeta(None)),
          Field("tags", Opt(Field("tags", Arr(Field("tags", ReferenceObject("/definitions/Tag", TypeMeta(None)), TypeMeta(None)), TypeMeta(None)), TypeMeta(None)), TypeMeta(None)), TypeMeta(None)),
          Field("photoUrls", Arr(Field("photoUrls", Str(None, TypeMeta(None)), TypeMeta(None)), TypeMeta(None)), TypeMeta(None)),
          Field("id", Opt(Field("id", Lng(TypeMeta(Some("int64"))), TypeMeta(None)), TypeMeta(None)), TypeMeta(None)),
          Field("status", Opt(Field("status", Str(None, TypeMeta(None)), TypeMeta(Some("pet status in the store"))), TypeMeta(Some("pet status in the store"))), TypeMeta(Some("pet status in the store"))),
          Field("category", Opt(Field("category", ReferenceObject("/definitions/Category", TypeMeta(None)), TypeMeta(None)), TypeMeta(None)), TypeMeta(None))), List(), TypeMeta(None))
      , "/definitions/Category" ->
        TypeDef("Category", List(
          Field("id", Opt(Field("id", Lng(TypeMeta(Some("int64"))), TypeMeta(None)), TypeMeta(None)), TypeMeta(None)),
          Field("name", Opt(Field("name", Str(None, TypeMeta(None)), TypeMeta(None)), TypeMeta(None)), TypeMeta(None))), List(), TypeMeta(None))
    ),

    "heroku.petstore.api.yaml" -> Map(
      "/definitions/Pet" ->
        TypeDef("Pet", List(
          Field("name", Opt(Field("name", Str(None, TypeMeta(None)), TypeMeta(None)), TypeMeta(None)), TypeMeta(None)),
          Field("birthday", Opt(Field("birthday", Int(TypeMeta(Some("int32"))), TypeMeta(None)), TypeMeta(None)), TypeMeta(None))), List(), TypeMeta(None))
    ),

    "security.api.yaml" -> Map(
      "/definitions/Pet" ->
        TypeDef("Pet", List(
          Field("name", Str(None, TypeMeta(None)), TypeMeta(None)),
          Field("tag", Opt(Field("tag", Str(None, TypeMeta(None)), TypeMeta(None)), TypeMeta(None)), TypeMeta(None))), List(), TypeMeta(None))
      , "/definitions/ErrorModel" ->
        TypeDef("ErrorModel", List(
          Field("code", Int(TypeMeta(Some("int32"))), TypeMeta(None)),
          Field("message", Str(None, TypeMeta(None)), TypeMeta(None))), List(), TypeMeta(None))
    ),

    "simple.petstore.api.yaml" -> Map(
      "/definitions/pet" ->
        TypeDef("pet", List(
          Field("id", Lng(TypeMeta(Some("int64"))), TypeMeta(None)),
          Field("name", Str(None, TypeMeta(None)), TypeMeta(None)),
          Field("tag", Opt(Field("tag", Str(None, TypeMeta(None)), TypeMeta(None)), TypeMeta(None)), TypeMeta(None))), List(), TypeMeta(None))
      , "/definitions/newPet" ->
        TypeDef("newPet", List(
          Field("id", Opt(Field("id", Lng(TypeMeta(Some("int64"))), TypeMeta(None)), TypeMeta(None)), TypeMeta(None)),
          Field("name", Str(None, TypeMeta(None)), TypeMeta(None)),
          Field("tag", Opt(Field("tag", Str(None, TypeMeta(None)), TypeMeta(None)), TypeMeta(None)), TypeMeta(None))), List(), TypeMeta(None))
      , "/definitions/errorModel" ->
        TypeDef("errorModel", List(
          Field("code", Int(TypeMeta(Some("int32"))), TypeMeta(None)),
          Field("message", Str(None, TypeMeta(None)), TypeMeta(None))), List(), TypeMeta(None))
    ),

    "uber.api.yaml" -> Map(
      "/definitions/Activity" ->
        TypeDef("Activity", List(
          Field("uuid", Opt(Field("uuid", Str(None, TypeMeta(None)), TypeMeta(Some("Unique identifier for the activity"))), TypeMeta(Some("Unique identifier for the activity"))), TypeMeta(Some("Unique identifier for the activity")))), List(), TypeMeta(None))
      , "/definitions/Activities" ->
        TypeDef("Activities", List(
          Field("offset", Opt(Field("offset", Int(TypeMeta(Some("int32"))), TypeMeta(Some("Position in pagination."))), TypeMeta(Some("Position in pagination."))), TypeMeta(Some("Position in pagination."))),
          Field("limit", Opt(Field("limit", Int(TypeMeta(Some("int32"))), TypeMeta(Some("Number of items to retrieve (100 max)."))), TypeMeta(Some("Number of items to retrieve (100 max)."))), TypeMeta(Some("Number of items to retrieve (100 max)."))),
          Field("count", Opt(Field("count", Int(TypeMeta(Some("int32"))), TypeMeta(Some("Total number of items available."))), TypeMeta(Some("Total number of items available."))), TypeMeta(Some("Total number of items available."))),
          Field("history", Opt(Field("history", Arr(Field("history", ReferenceObject("/definitions/Activity", TypeMeta(None)), TypeMeta(None)), TypeMeta(None)), TypeMeta(None)), TypeMeta(None)), TypeMeta(None))), List(), TypeMeta(None))
      , "/definitions/Product" ->
        TypeDef("Product", List(
          Field("image", Opt(Field("image", Str(None, TypeMeta(None)), TypeMeta(Some("Image URL representing the product."))), TypeMeta(Some("Image URL representing the product."))), TypeMeta(Some("Image URL representing the product."))),
          Field("description", Opt(Field("description", Str(None, TypeMeta(None)), TypeMeta(Some("Description of product."))), TypeMeta(Some("Description of product."))), TypeMeta(Some("Description of product."))),
          Field("display_name", Opt(Field("display_name", Str(None, TypeMeta(None)), TypeMeta(Some("Display name of product."))), TypeMeta(Some("Display name of product."))), TypeMeta(Some("Display name of product."))),
          Field("product_id", Opt(Field("product_id", Str(None, TypeMeta(None)), TypeMeta(Some("Unique identifier representing a specific product for a given latitude & longitude. For example, uberX in San Francisco will have a different product_id than uberX in Los Angeles."))), TypeMeta(Some("Unique identifier representing a specific product for a given latitude & longitude. For example, uberX in San Francisco will have a different product_id than uberX in Los Angeles."))), TypeMeta(Some("Unique identifier representing a specific product for a given latitude & longitude. For example, uberX in San Francisco will have a different product_id than uberX in Los Angeles."))),
          Field("capacity", Opt(Field("capacity", Str(None, TypeMeta(None)), TypeMeta(Some("Capacity of product. For example, 4 people."))), TypeMeta(Some("Capacity of product. For example, 4 people."))), TypeMeta(Some("Capacity of product. For example, 4 people.")))), List(), TypeMeta(None))
      , "/definitions/Profile" ->
        TypeDef("Profile", List(
          Field("first_name", Opt(Field("first_name", Str(None, TypeMeta(None)), TypeMeta(Some("First name of the Uber user."))), TypeMeta(Some("First name of the Uber user."))), TypeMeta(Some("First name of the Uber user."))),
          Field("email", Opt(Field("email", Str(None, TypeMeta(None)), TypeMeta(Some("Email address of the Uber user"))), TypeMeta(Some("Email address of the Uber user"))), TypeMeta(Some("Email address of the Uber user"))),
          Field("promo_code", Opt(Field("promo_code", Str(None, TypeMeta(None)), TypeMeta(Some("Promo code of the Uber user."))), TypeMeta(Some("Promo code of the Uber user."))), TypeMeta(Some("Promo code of the Uber user."))),
          Field("last_name", Opt(Field("last_name", Str(None, TypeMeta(None)), TypeMeta(Some("Last name of the Uber user."))), TypeMeta(Some("Last name of the Uber user."))), TypeMeta(Some("Last name of the Uber user."))),
          Field("picture", Opt(Field("picture", Str(None, TypeMeta(None)), TypeMeta(Some("Image URL of the Uber user."))), TypeMeta(Some("Image URL of the Uber user."))), TypeMeta(Some("Image URL of the Uber user.")))), List(), TypeMeta(None))
      , "/definitions/PriceEstimate" ->
        TypeDef("PriceEstimate", List(
          Field("low_estimate", Opt(Field("low_estimate", Dbl(TypeMeta(None)), TypeMeta(Some("Lower bound of the estimated price."))), TypeMeta(Some("Lower bound of the estimated price."))), TypeMeta(Some("Lower bound of the estimated price."))),
          Field("display_name", Opt(Field("display_name", Str(None, TypeMeta(None)), TypeMeta(Some("Display name of product."))), TypeMeta(Some("Display name of product."))), TypeMeta(Some("Display name of product."))),
          Field("estimate", Opt(Field("estimate", Str(None, TypeMeta(None)), TypeMeta(Some("Formatted string of estimate in local currency of the start location. Estimate could be a range, a single number (flat rate) or \"Metered\" for TAXI."))), TypeMeta(Some("Formatted string of estimate in local currency of the start location. Estimate could be a range, a single number (flat rate) or \"Metered\" for TAXI."))), TypeMeta(Some("Formatted string of estimate in local currency of the start location. Estimate could be a range, a single number (flat rate) or \"Metered\" for TAXI."))),
          Field("high_estimate", Opt(Field("high_estimate", Dbl(TypeMeta(None)), TypeMeta(Some("Upper bound of the estimated price."))), TypeMeta(Some("Upper bound of the estimated price."))), TypeMeta(Some("Upper bound of the estimated price."))),
          Field("product_id", Opt(Field("product_id", Str(None, TypeMeta(None)), TypeMeta(Some("Unique identifier representing a specific product for a given latitude & longitude. For example, uberX in San Francisco will have a different product_id than uberX in Los Angeles"))), TypeMeta(Some("Unique identifier representing a specific product for a given latitude & longitude. For example, uberX in San Francisco will have a different product_id than uberX in Los Angeles"))), TypeMeta(Some("Unique identifier representing a specific product for a given latitude & longitude. For example, uberX in San Francisco will have a different product_id than uberX in Los Angeles"))),
          Field("currency_code", Opt(Field("currency_code", Str(None, TypeMeta(None)), TypeMeta(Some("[ISO 4217](http://en.wikipedia.org/wiki/ISO_4217) currency code."))), TypeMeta(Some("[ISO 4217](http://en.wikipedia.org/wiki/ISO_4217) currency code."))), TypeMeta(Some("[ISO 4217](http://en.wikipedia.org/wiki/ISO_4217) currency code."))),
          Field("surge_multiplier", Opt(Field("surge_multiplier", Dbl(TypeMeta(None)), TypeMeta(Some("Expected surge multiplier. Surge is active if surge_multiplier is greater than 1. Price estimate already factors in the surge multiplier."))), TypeMeta(Some("Expected surge multiplier. Surge is active if surge_multiplier is greater than 1. Price estimate already factors in the surge multiplier."))), TypeMeta(Some("Expected surge multiplier. Surge is active if surge_multiplier is greater than 1. Price estimate already factors in the surge multiplier.")))), List(), TypeMeta(None))
      , "/definitions/Error" ->
        TypeDef("Error", List(
          Field("code", Opt(Field("code", Int(TypeMeta(Some("int32"))), TypeMeta(None)), TypeMeta(None)), TypeMeta(None)),
          Field("message", Opt(Field("message", Str(None, TypeMeta(None)), TypeMeta(None)), TypeMeta(None)), TypeMeta(None)),
          Field("fields", Opt(Field("fields", Str(None, TypeMeta(None)), TypeMeta(None)), TypeMeta(None)), TypeMeta(None))), List(), TypeMeta(None))
    ),
    "instagram.api.yaml" -> Map(
      "/definitions/Like" ->
        TypeDef("Like", List(
          Field("first_name", Opt(Field("first_name", Str(None, TypeMeta(None)), TypeMeta(None)), TypeMeta(None)), TypeMeta(None)),
          Field("id", Opt(Field("id", Str(None, TypeMeta(None)), TypeMeta(None)), TypeMeta(None)), TypeMeta(None)),
          Field("last_name", Opt(Field("last_name", Str(None, TypeMeta(None)), TypeMeta(None)), TypeMeta(None)), TypeMeta(None)),
          Field("type", Opt(Field("type", Str(None, TypeMeta(None)), TypeMeta(None)), TypeMeta(None)), TypeMeta(None)),
          Field("user_name", Opt(Field("user_name", Str(None, TypeMeta(None)), TypeMeta(None)), TypeMeta(None)), TypeMeta(None))), List(), TypeMeta(None))
      , "/definitions/User" ->
        TypeDef("User", List(
          Field("website", Opt(Field("website", Str(None,TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None)),
          Field("profile_picture", Opt(Field("profile_picture", Str(None,TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None)),
          Field("username", Opt(Field("username", Str(None,TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None)),
          Field("full_name", Opt(Field("full_name", Str(None,TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None)),
          Field("bio", Opt(Field("bio", Str(None,TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None)),
          Field("id", Opt(Field("id", Int(TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None)),
          Field("counts", Opt(Field("counts",
            TypeDef("counts", List(
              Field("media", Opt(Field("media", Int(TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None)),
              Field("follows", Opt(Field("follows", Int(TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None)),
              Field("follwed_by", Opt(Field("follwed_by", Int(TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None))), List(), TypeMeta(None))
            , TypeMeta(None)),TypeMeta(None)), TypeMeta(None))), List(), TypeMeta(None))
      , "/definitions/Location" ->
        TypeDef("Location", List(
          Field("id", Opt(Field("id", Str(None,TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None)),
          Field("name", Opt(Field("name", Str(None,TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None)),
          Field("latitude", Opt(Field("latitude", Dbl(TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None)),
          Field("longitude", Opt(Field("longitude", Dbl(TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None))), List(), TypeMeta(None))
      , "/definitions/Media" ->
        TypeDef("Media", List(
          Field("location", Opt(Field("location", ReferenceObject("/definitions/Location", TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None)),
          Field("created_time", Opt(Field("created_time", Int(TypeMeta(None)), TypeMeta(Some("Epoc time (ms)"))),TypeMeta(Some("Epoc time (ms)"))), TypeMeta(Some("Epoc time (ms)"))),
          Field("comments:", Opt(Field("comments:",
            TypeDef("comments:", List(
              Field("count", Opt(Field("count", Int(TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None)),
              Field("data", Opt(Field("data", Arr(Field("data", ReferenceObject("/definitions/Comment", TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None))), List(), TypeMeta(None))
            , TypeMeta(None)),TypeMeta(None)), TypeMeta(None)),
          Field("tags", Opt(Field("tags", Arr(Field("tags", ReferenceObject("/definitions/Tag", TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None)),
          Field("users_in_photo", Opt(Field("users_in_photo", Arr(Field("users_in_photo", ReferenceObject("/definitions/MiniProfile", TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None)),
          Field("filter", Opt(Field("filter", Str(None,TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None)),
          Field("likes", Opt(Field("likes",
            TypeDef("likes", List(
              Field("count", Opt(Field("count", Int(TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None)),
              Field("data", Opt(Field("data", Arr(Field("data", ReferenceObject("/definitions/MiniProfile", TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None))), List(), TypeMeta(None))
            , TypeMeta(None)),TypeMeta(None)), TypeMeta(None)),
          Field("id", Opt(Field("id", Int(TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None)),
          Field("videos", Opt(Field("videos",
            TypeDef("videos", List(
              Field("low_resolution", Opt(Field("low_resolution", ReferenceObject("/definitions/Image", TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None)),
              Field("standard_resolution", Opt(Field("standard_resolution", ReferenceObject("/definitions/Image", TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None))), List(), TypeMeta(None))
            , TypeMeta(None)),TypeMeta(None)), TypeMeta(None)),
          Field("type", Opt(Field("type", Str(None,TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None)),
          Field("images", Opt(Field("images",
            TypeDef("images", List(
              Field("low_resolution", Opt(Field("low_resolution", ReferenceObject("/definitions/Image", TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None)),
              Field("thumbnail", Opt(Field("thumbnail", ReferenceObject("/definitions/Image", TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None)),
              Field("standard_resolution", Opt(Field("standard_resolution", ReferenceObject("/definitions/Image", TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None))), List(), TypeMeta(None))
            , TypeMeta(None)),TypeMeta(None)), TypeMeta(None)),
          Field("user", Opt(Field("user", ReferenceObject("/definitions/MiniProfile", TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None))), List(), TypeMeta(None))
      , "/definitions/Tag" ->
        TypeDef("Tag", List(
          Field("media_count", Opt(Field("media_count", Int(TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None)),
          Field("name", Opt(Field("name", Str(None,TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None))), List(), TypeMeta(None))
      , "/definitions/MiniProfile" ->
        TypeDef("MiniProfile", List(
          Field("user_name", Opt(Field("user_name", Str(None,TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None)),
          Field("full_name", Opt(Field("full_name", Str(None,TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None)),
          Field("id", Opt(Field("id", Int(TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None)),
          Field("profile_picture", Opt(Field("profile_picture", Str(None,TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None))), List(), TypeMeta(Some("A shorter version of User for likes array")))
      , "/definitions/Image" ->
        TypeDef("Image", List(
          Field("width", Opt(Field("width", Int(TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None)),
          Field("height", Opt(Field("height", Int(TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None)),
          Field("url", Opt(Field("url", Str(None,TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None))), List(), TypeMeta(None))
      , "/definitions/Comment" ->
        TypeDef("Comment", List(
          Field("id", Opt(Field("id", Str(None,TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None)),
          Field("created_time", Opt(Field("created_time", Str(None,TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None)),
          Field("text", Opt(Field("text", Str(None,TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None)),
          Field("from", Opt(Field("from", ReferenceObject("/definitions/MiniProfile", TypeMeta(None)), TypeMeta(None)),TypeMeta(None)), TypeMeta(None))), List(), TypeMeta(None))
    ),
    "basic_extension.yaml" -> Map(
      "/definitions/ErrorModel" ->
        TypeDef("ErrorModel", List(
          Field("message", Str(None, TypeMeta(None)), TypeMeta(Some("The text of the error message"))),
          Field("code", Int(TypeMeta(None)), TypeMeta(Some("The error code")))), List(), TypeMeta(Some("Basic error model")))
      ,
      "/definitions/ExtendedErrorModel" ->
        TypeDef("ExtendedErrorModel", List(
          Field("rootCause", Opt(Field("rootCause", Str(None, TypeMeta(None)), TypeMeta(None)), TypeMeta(None)), TypeMeta(None))), List(ReferenceObject("/definitions/ErrorModel", TypeMeta(None))), TypeMeta(Some("Extended error model")))
    ),
    "basic_polymorphism.yaml" -> Map(
      "/definitions/Pet" ->
        TypeDef("Pet", List(
          Field("name", Str(None, TypeMeta(None)), TypeMeta(None)),
          Field("petType", Str(None, TypeMeta(None)), TypeMeta(None))), List(), TypeMeta(None))
      , "/definitions/Cat" ->
        TypeDef("Cat", List(
          Field("huntingSkill", Opt(Field("huntingSkill", Str(None, TypeMeta(None)), TypeMeta(Some("The measured skill for hunting"))), TypeMeta(Some("The measured skill for hunting"))), TypeMeta(Some("The measured skill for hunting")))), List(ReferenceObject("/definitions/Pet", TypeMeta(None))), TypeMeta(Some("A representation of a cat")))
      , "/definitions/Dog" ->
        TypeDef("Dog", List(
          Field("packSize", Opt(Field("packSize", Int(TypeMeta(Some("int32"))), TypeMeta(Some("the size of the pack the dog is from"))), TypeMeta(Some("the size of the pack the dog is from"))), TypeMeta(Some("the size of the pack the dog is from")))), List(ReferenceObject("/definitions/Pet", TypeMeta(None))), TypeMeta(Some("A representation of a dog")))
      , "/definitions/Labrador" ->
        TypeDef("Labrador", List(
          Field("cuteness", Opt(Field("cuteness", Int(TypeMeta(Some("int32"))), TypeMeta(Some("the cuteness of the animal in percent"))), TypeMeta(Some("the cuteness of the animal in percent"))), TypeMeta(Some("the cuteness of the animal in percent")))), List(ReferenceObject("/definitions/Dog", TypeMeta(None))), TypeMeta(Some("A concrete implementation of a dog")))
    )
  ).withDefaultValue(Map.empty[String, Domain.Type])

  describe("Definitions Converter Test") {
    fixtures.filter(_.getName.endsWith(".yaml")).foreach { file =>
      it(s"should parse the yaml swagger file ${file.getName} with expected definitions result") {
        val swaggerModel = YamlParser.parse(file)
        Swagger2Ast.convertDefinitions(swaggerModel) mustBe expectedDefinitions(file.getName)
      }
    }
  }
}
