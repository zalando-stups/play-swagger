package de.zalando.play.compiler

import java.io.File

import de.zalando.ExpectedResults
import de.zalando.apifirst.Application.Model
import de.zalando.swagger.{Swagger2Ast, YamlParser}
import org.scalatest.{FunSpec, MustMatchers}

import scala.language.implicitConversions

class ControllerGeneratorTest extends FunSpec with MustMatchers with ExpectedResults {

  def removeNoise(s: String) =
    s.split("\n").filterNot(_.trim.isEmpty).filterNot(_.contains("@since")).mkString("\n")

  describe("ControllersGenerator standalone") {
    it("should convert empty model") {
      implicit val emptyModel = Model(Nil, Nil)
      val result = ControllersGenerator.generate("pkg")
      result mustBe empty
    }
  }

  describe("ControllersGenerator standard tests should be empty because no handlers are defined") {
    val xApiEnabled = "minimal.api.yaml" :: "simple.petstore.api.yaml" :: Nil
    val fixtures = new File("compiler/src/test/resources/examples").listFiles
    testFixture(fixtures) { (file, result) =>
      result.nonEmpty mustBe xApiEnabled.contains(file.getName)
    }
  }

  describe("ControllersGenerator standard tests should not be empty for normal specs") {
    val fixtures = new File("compiler/src/test/resources/controllers").listFiles
    testFixture(fixtures) { (file, fullResult) =>
      val result = fullResult.head._2
      result mustBe asInFile(file, "controllers.scala")
    }
  }

  def testFixture(fixtures: Array[File])(test: (File, Iterable[(Set[String], String)]) => Unit): Unit = {
    fixtures.filter(_.getName.endsWith(".yaml")) foreach { file =>
      it(s"should parse the yaml swagger file ${file.getName} with empty result") {
        implicit val swaggerModel = YamlParser.parse(file)
        implicit val model = Swagger2Ast.convert("x-api-first")(swaggerModel)
        val fullResult = ControllersGenerator.generate(file.getName)
        test(file, fullResult)
      }
    }
  }
}
