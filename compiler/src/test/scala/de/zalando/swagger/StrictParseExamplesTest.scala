package de.zalando.swagger

import java.io.File

import de.zalando.ExpectedResults
import de.zalando.swagger.strictModel.SwaggerModel
import org.scalatest.{FunSpec, MustMatchers}

class StrictParseExamplesTest extends FunSpec with MustMatchers with ExpectedResults {

  override val expectationsFolder = "/"

  val modelFixtures = new File("compiler/src/test/resources/model/todo").listFiles

  val callsFixtures = new File("compiler/src/test/resources/examples/todo").listFiles

  describe("Strict Swagger Parser model") {
    modelFixtures.filter(_.getName.endsWith(".yaml")).foreach { file =>
      it(s"should parse the yaml swagger file ${file.getName} as specification") {
        val model = StrictYamlParser.parse(file)
        model mustBe a[SwaggerModel]
        val typeDefs = ModelConverter.fromModel(model).typeDefs
        val typesStr = typeDefs map { case (k,v) => k -> ("\n\t" + v.toShortString("\t\t"))} mkString "\n"
        // dump(typesStr, file, "types")
        typesStr.trim mustBe asInFile(file, "types")
      }
    }
  }

  describe("Strict Swagger Parser examples") {
    callsFixtures.filter(_.getName.endsWith(".yaml")).foreach { file =>
      it(s"should parse the yaml swagger file ${file.getName} as specification") {
        val model = StrictYamlParser.parse(file)
        model mustBe a[SwaggerModel]
        val result = ModelConverter.fromModel(model)
        println(result.params.mkString("\n"))
      }
    }
  }

}
