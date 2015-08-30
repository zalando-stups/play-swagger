package de.zalando.play.compiler

import java.io.File

import de.zalando.ExpectedResults
import de.zalando.apifirst.Domain
import de.zalando.swagger.{Swagger2Ast, YamlParser}
import org.scalatest.{FunSpec, MustMatchers}

import scala.language.implicitConversions

class ModelGeneratorTest extends FunSpec with MustMatchers with ExpectedResults {

  def removeNoise(s: String) =
    s.split("\n").filterNot(_.trim.isEmpty).filterNot(_.contains("@since")).mkString("\n")

  describe("Model Generator standalone") {
    it("should convert empty model") {
      val template = ModelGenerator.mainTemplate + ModelGenerator.namespaceTemplate
      val expected = ""
      implicit val emptyModel = Seq.empty[Domain.Type]
      val result = removeNoise(ModelGenerator.generate("pkg").head._2)
      result mustBe expected
    }
    /*
        it ("should escape package name if it contains reserved words") {
          fail("Not implemented yet") TODO
        }
    */
  }

  describe("Model Generator standard tests") {
    val fixtures = new File("compiler/src/test/resources/examples").listFiles
    testFixture(fixtures)
  }

  describe("Model Generator special cases") {
    val fixtures = new File("compiler/src/test/resources/model").listFiles
    testFixture(fixtures)
  }

  def testFixture(fixtures: Array[File]): Unit = {
    fixtures.filter(_.getName.endsWith(".yaml")) foreach { file =>
      it(s"should parse the yaml swagger file ${file.getName} with expected definitions result") {
        val swaggerModel = YamlParser.parse(file)
        implicit val definitions = Swagger2Ast.convertDefinitions(swaggerModel)
        val fullResult = ModelGenerator.generate(file.getName)
        val result = removeNoise(fullResult.head._2)
        result mustBe  asInFile(file, "scala")
      }
    }
  }


}
