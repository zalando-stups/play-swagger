package de.zalando.swagger

import java.io.File

import com.fasterxml.jackson.databind.JsonMappingException
import org.scalatest.{FunSpec, MustMatchers}

class JSONParserTest extends FunSpec with MustMatchers {

  val file = "compiler/src/test/resources/examples/uber.api."
  val json = new File(file + "json")

  describe("The swagger parser") {
    it("should parse json specification in the same way as yaml specification") {
      val jsonModel = StrictJsonParser.parse(json)
      jsonModel mustNot be(null)
    }
  }
}
