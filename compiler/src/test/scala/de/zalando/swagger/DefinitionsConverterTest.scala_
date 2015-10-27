package de.zalando.swagger

import java.io.File

import de.zalando.ExpectedResults
import de.zalando.apifirst.Domain
import Domain._
import org.scalatest.{FunSpec, MustMatchers}

class DefinitionsConverterTest extends FunSpec with MustMatchers with ExpectedResults {

  val fixtures = new File("compiler/src/test/resources/examples").listFiles

  describe("Definitions Converter Test") {
    fixtures.filter(_.getName.endsWith(".yaml")).foreach { file =>
      it(s"should parse the yaml swagger file ${file.getName} with expected definitions result") {
        val swaggerModel = YamlParser.parse(file)
        val result = Swagger2Ast.convertDefinitions(swaggerModel).toString()
        result mustBe asInFile(file, "def")
      }
    }
  }
}
