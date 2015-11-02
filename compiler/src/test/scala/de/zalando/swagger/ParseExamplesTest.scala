package de.zalando.swagger

import java.io.File

import de.zalando.swagger.strictModel.SwaggerModel
import org.scalatest.{FunSpec, MustMatchers}

class ParseExamplesTest extends FunSpec with MustMatchers {

  val fixtures = new File("compiler/src/test/resources/examples").listFiles

  describe("Swagger Parser") {
    fixtures.filter(_.getName.endsWith(".json")).foreach { file =>
      it(s"should parse the json swagger file ${file.getName} as specification") {
        StrictJsonParser.parse(file).getClass mustBe classOf[SwaggerModel]
        StrictJsonParser.parse(file) mustBe a [SwaggerModel]
      }
    }
    fixtures.filter(_.getName.endsWith(".yaml")).foreach { file =>
      it(s"should parse the yaml swagger file ${file.getName} as specification") {
        StrictYamlParser.parse(file) mustBe a [SwaggerModel]
      }
    }
  }
}
