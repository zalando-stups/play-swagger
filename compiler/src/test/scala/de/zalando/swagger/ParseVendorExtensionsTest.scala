package de.zalando.swagger

import java.io.File

import com.fasterxml.jackson.databind.JsonMappingException
import org.scalatest.{FunSpec, MustMatchers}

class ParseVendorExtensionsTest extends FunSpec with MustMatchers {

  val ok = new File("compiler/src/test/resources/extensions/extensions.ok.yaml")
  val nok = new File("compiler/src/test/resources/extensions/extensions.nok.yaml")

  describe("The swagger parser") {
    it("should read valid vendor extensions") {
      implicit val (uri, swagger) = StrictYamlParser.parse(ok)
      swagger.info.vendorExtensions contains "x-info-extension" mustBe true
      swagger.paths("/").vendorExtensions contains "x-path-extension" mustBe true
      swagger.paths("/").get.vendorExtensions contains "x-operation-extension" mustBe true
      swagger.paths("/").get.responses("200").vendorExtensions contains "x-response-extension" mustBe true
      swagger.tags.head.vendorExtensions contains "x-tag-extension" mustBe true
      swagger.securityDefinitions("internalApiKey").vendorExtensions contains "x-security-extension" mustBe true
    }
    it("should reject invalid vendor extensions") {
      intercept[JsonMappingException] {
        StrictYamlParser.parse(nok)
      }.getClass mustBe classOf[JsonMappingException]
    }
  }
}
