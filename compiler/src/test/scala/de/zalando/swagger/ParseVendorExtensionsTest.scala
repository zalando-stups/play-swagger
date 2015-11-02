package de.zalando.swagger

import java.io.File

import com.fasterxml.jackson.databind.JsonMappingException
import org.scalatest.{FunSpec, MustMatchers}

class ParseVendorExtensionsTest extends FunSpec with MustMatchers {

  val ok = new File("compiler/src/test/resources/extensions.ok.yaml")
  val nok = new File("compiler/src/test/resources/extensions.nok.yaml")

  describe("The swagger parser") {
    it("should read valid vendor extensions") {
      implicit val swagger = StrictYamlParser.parse(ok)
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
  }
}
