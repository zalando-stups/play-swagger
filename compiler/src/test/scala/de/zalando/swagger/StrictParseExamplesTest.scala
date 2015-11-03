package de.zalando.swagger

import java.io.File

import de.zalando.swagger.strictModel.SwaggerModel
import org.scalatest.{FunSpec, MustMatchers}

class StrictParseExamplesTest extends FunSpec with MustMatchers {

  val modelFixtures = new File("compiler/src/test/resources/model/todo").listFiles

  val callsFixtures = new File("compiler/src/test/resources/examples/todo").listFiles

  describe("Strict Swagger Parser model") {
    modelFixtures.filter(_.getName.endsWith(".yaml")).foreach { file =>
      it(s"should parse the yaml swagger file ${file.getName} as specification") {
        val model = StrictYamlParser.parse(file)
        model mustBe a [SwaggerModel]
        println(ModelConverter.fromModel(model).typeDefs.mkString("\n\n"))
      }
    }
  }

  describe("Strict Swagger Parser calls") {
    callsFixtures.filter(_.getName.endsWith(".yaml")).foreach { file =>
      it(s"should parse the yaml swagger file ${file.getName} as specification") {
        val model = StrictYamlParser.parse(file)
        model mustBe a [SwaggerModel]
        val result = ModelConverter.fromModel(model)
        // println(result.definitions.mkString("\n"))
        // println(result.calls.mkString("\n\n"))
        println(result.params.mkString("\n\n"))
      }
    }
  }

}
