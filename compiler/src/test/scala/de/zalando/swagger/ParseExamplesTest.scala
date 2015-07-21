package de.zalando.swagger

import java.io.File

import de.zalando.swagger.model.SwaggerModel
import org.scalatest.{FunSpec, MustMatchers}

class ParseExamplesTest extends FunSpec with MustMatchers {

  val fixtures = new File("compiler/src/test/resources/examples").listFiles

  describe("Swagger Parser") {
    fixtures.filter(_.getName.endsWith(".json")).foreach { file =>
      it(s"should parse the json swagger file ${file.getName} as specification") {
        JsonParser.parse(file) mustBe a [SwaggerModel]
      }
    }
    fixtures.filter(_.getName.endsWith(".yaml")).foreach { file =>
      it(s"should parse the yaml swagger file ${file.getName} as specification") {
        YamlParser.parse(file) mustBe a [SwaggerModel]
      }
    }
  }
}
