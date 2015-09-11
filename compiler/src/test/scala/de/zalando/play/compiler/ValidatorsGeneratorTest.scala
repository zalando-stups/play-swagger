package de.zalando.play.compiler

import java.io.File

import de.zalando.ExpectedResults
import de.zalando.apifirst.Application.Model
import de.zalando.swagger.{Swagger2Ast, YamlParser}
import org.scalatest.{FunSpec, MustMatchers}

import scala.language.implicitConversions

class ValidatorsGeneratorTest extends FunSpec with MustMatchers with ExpectedResults {

  def removeNoise(s: String) =
    s.split("\n").filterNot(_.trim.isEmpty).filterNot(_.contains("@since")).mkString("\n")

  describe("ValidatorsGenerator standard tests should not be empty for normal specs") {
    val fixtures = new File("compiler/src/test/resources/controllers").listFiles
    testFixture(fixtures) { (file, fullResult) =>
      val result = fullResult.head._2
      removeNoise(result) mustBe asInFile(file, "validators.scala")
    }
  }

  def testFixture(fixtures: Array[File])(test: (File, Iterable[(Set[String], String)]) => Unit): Unit = {
    fixtures.filter(_.getName.endsWith(".yaml")) foreach { file =>
      it(s"should parse the yaml swagger file ${file.getName} with empty result") {
        implicit val swaggerModel = YamlParser.parse(file)
        implicit val model = Swagger2Ast.convert("x-api-first")(swaggerModel)
        val fullResult = ValidatorsGenerator.generate(file.getName)
        test(file, fullResult)
      }
    }
  }
}
