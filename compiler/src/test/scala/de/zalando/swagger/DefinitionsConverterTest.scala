package de.zalando.swagger

import java.io.File

import de.zalando.apifirst.Domain
import Domain._
import org.scalatest.{FunSpec, MustMatchers}

class DefinitionsConverterTest extends FunSpec with MustMatchers {

  val fixtures = new File("compiler/src/test/resources/examples").listFiles

  val expectedDefinitions = Map(
    "full.petstore.api.yaml" -> Map(
      "/definitions/Order" -> TypeDef("Order",List(Field("shipDate",DateTime), Field("quantity",Int), Field("petId",Lng), Field("id",Lng), Field("complete",Bool), Field("status",Str(None)))),
      "/definitions/User" -> TypeDef("User",List(Field("email",Str(None)), Field("username",Str(None)), Field("userStatus",Int), Field("lastName",Str(None)), Field("firstName",Str(None)), Field("id",Lng), Field("phone",Str(None)), Field("password",Str(None)))),
      "/definitions/Tag" -> TypeDef("Tag",List(Field("id",Lng), Field("name",Str(None)))),
      "/definitions/Pet" -> TypeDef("Pet",List(Field("name",Str(None)), Field("tags",Arr(ReferenceObject("/definitions/Tag"))), Field("photoUrls",Arr(Str(None))), Field("id",Lng), Field("status",Str(None)), Field("category",ReferenceObject("/definitions/Category")))),
      "/definitions/Category" -> TypeDef("Category",List(Field("id",Lng), Field("name",Str(None))))
    ),

    "heroku.petstore.api.yaml" -> Map(
      "/definitions/Pet" -> TypeDef("Pet",List(Field("name",Str(None)), Field("birthday",Int)))
    ),

    "security.api.yaml" -> Map(
      "/definitions/Pet" -> TypeDef("Pet",List(Field("name",Str(None)), Field("tag",Str(None)))),
      "/definitions/ErrorModel" -> TypeDef("ErrorModel",List(Field("code",Int), Field("message",Str(None))))
    ),

    "simple.petstore.api.yaml" -> Map(
      "/definitions/pet" -> TypeDef("pet",List(Field("id",Lng), Field("name",Str(None)), Field("tag",Str(None)))),
      "/definitions/newPet" -> TypeDef("newPet",List(Field("id",Lng), Field("name",Str(None)), Field("tag",Str(None)))),
      "/definitions/errorModel" -> TypeDef("errorModel",List(Field("code",Int), Field("message",Str(None))))
    ),

    "uber.api.yaml" -> Map(
      "/definitions/Activity" -> TypeDef("Activity",List(Field("uuid",Str(None)))),
      "/definitions/Activities" -> TypeDef("Activities",List(Field("offset",Int), Field("limit",Int), Field("count",Int), Field("history",Arr(ReferenceObject("/definitions/Activity"))))),
      "/definitions/Product" -> TypeDef("Product",List(Field("image",Str(None)), Field("description",Str(None)), Field("display_name",Str(None)), Field("product_id",Str(None)), Field("capacity",Str(None)))),
      "/definitions/Profile" -> TypeDef("Profile",List(Field("first_name",Str(None)), Field("email",Str(None)), Field("promo_code",Str(None)), Field("last_name",Str(None)), Field("picture",Str(None)))),
      "/definitions/PriceEstimate" -> TypeDef("PriceEstimate",List(Field("low_estimate",Dbl), Field("display_name",Str(None)), Field("estimate",Str(None)), Field("high_estimate",Dbl), Field("product_id",Str(None)), Field("currency_code",Str(None)), Field("surge_multiplier",Dbl))),
      "/definitions/Error" -> TypeDef("Error",List(Field("code",Int), Field("message",Str(None)), Field("fields",Str(None))))
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
