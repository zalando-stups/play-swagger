package de.zalando.swagger

import java.io.File

import com.fasterxml.jackson.databind.JsonMappingException
import org.scalatest.junit.JUnitRunner
import org.scalatest.{MustMatchers, FunSpec, FlatSpec, Matchers}

class ParseVendorExtensionsTest extends FunSpec with MustMatchers {

  val ok = new File("compiler/src/test/resources/extensions.ok.yaml")
  val nok = new File("compiler/src/test/resources/extensions.nok.yaml")

  describe("The swagger parser") {
    it("should read valid vendor extensions") {
      val swagger = YamlParser.parse(ok)
      swagger.info.vendorExtensions contains ("x-info-extension")
      swagger.paths("/").vendorExtensions contains ("x-path-extension")
      swagger.paths("/").get.vendorExtensions contains ("x-operation-extension")
      swagger.paths("/").get.parameters(0).vendorExtensions contains ("x-parameter-extension")
      swagger.paths("/").get.responses("200").vendorExtensions contains ("x-response-extension")
      swagger.tags(0).vendorExtensions contains ("x-tag-extension")
      swagger.securityDefinitions("security").vendorExtensions contains ("x-security-extension")
    }
    it("should reject invalid vendor extensions") {
      intercept[JsonMappingException] {
        val swagger = YamlParser.parse(nok)
      }
    }
  }
}
