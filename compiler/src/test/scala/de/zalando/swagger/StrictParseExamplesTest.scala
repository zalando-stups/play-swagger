package de.zalando.swagger

import java.io.File

import de.zalando.swagger.strictModel.SwaggerModel
import org.scalatest.{FunSpec, MustMatchers}

class StrictParseExamplesTest extends FunSpec with MustMatchers {

  val fixtures = new File("compiler/src/test/resources/examples").listFiles

  describe("Strict Swagger Parser") {
    fixtures.filter(_.getName.endsWith(".yaml")).foreach { file =>
      it(s"should parse the yaml swagger file ${file.getName} as specification") {
        val model = StrictYamlParser.parse(file)
        model mustBe a [SwaggerModel]
        // println(typeConverter.fromModel(model).mkString("\n\n"))
      }
    }
  }
}
