package de.zalando.swagger

import java.io.File

import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.dataformat.yaml.snakeyaml.parser.ParserException
import org.scalatest.{FunSpec, MustMatchers}

class ParseVendorExtensionsTest extends FunSpec with MustMatchers {

  val ok = new File("compiler/src/test/resources/extensions/extensions.ok.yaml")
  val nok = new File("compiler/src/test/resources/extensions/extensions.nok.yaml")
  val hypermediaOk = new File("compiler/src/test/resources/extensions/hypermedia.ok.yaml")
  val hypermediaNOk1 = new File("compiler/src/test/resources/extensions/hypermedia.nok1.yaml")
  val hypermediaNOk2 = new File("compiler/src/test/resources/extensions/hypermedia.nok2.yaml")

  describe("The swagger parser") {
    it("should read valid vendor extensions") {
      implicit val (uri, swagger) = StrictYamlParser.parse(ok)
      swagger.info.vendorExtensions contains "x-info-extension"
      swagger.paths("/").vendorExtensions contains "x-path-extension"
      swagger.paths("/").get.vendorExtensions contains "x-operation-extension"
      swagger.paths("/").get.responses("200").vendorExtensions contains "x-response-extension"
      swagger.tags.head.vendorExtensions contains "x-tag-extension"
      swagger.securityDefinitions("internalApiKey").vendorExtensions contains "x-security-extension"
    }
    it("should reject invalid vendor extensions") {
      intercept[JsonMappingException] {
        StrictYamlParser.parse(nok)
      }
    }
    it("should read hypermedia definitions") {
      implicit val (uri, swagger) = StrictYamlParser.parse(hypermediaOk)
      val expected = Map("resource created" ->
        Map("resource updated" -> Map("condition" -> "some rule to show the transition"), "subresource added" -> null),
        "resource updated" -> Map("subresource added" -> Map("condition" -> ""),
          "self" -> Map("condition" -> "non-empty rule")), "resource deleted" -> Map("self" -> null),
        "subresource added" -> Map("resource updated" -> null, "self" -> null, "resource deleted" -> null))
      swagger.transitions.nonEmpty mustBe true
      swagger.transitions mustEqual expected
      swagger.paths("/").get.responses("200").targetState mustEqual Some("resource created")
      swagger.paths("/").get.responses("default").targetState mustEqual None
    }
    it("should reject hypermedia definitions without well-formed definition") {
      val exception = intercept[JsonMappingException] {
        StrictYamlParser.parse(hypermediaNOk1)
      }
      exception.getMessage mustEqual "Malformed transition definitions"
    }
    it("should reject hypermedia definitions with incorrect initial state") {
      intercept[ParserException] {
        StrictYamlParser.parse(hypermediaNOk2)
      }
    }
  }
}
