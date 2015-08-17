package de.zalando.play.compiler

import java.io.File

import de.zalando.apifirst.Domain
import de.zalando.swagger.{Swagger2Ast, YamlParser}
import org.scalatest.{FunSpec, MustMatchers}

import scala.language.implicitConversions

class ModelGeneratorTest extends FunSpec with MustMatchers {

  def removeNoise(s: String) =
    s.split("\n").filterNot(_.trim.isEmpty).filterNot(_.contains("@since")).mkString("\n")

  describe("Model Generator standalone") {
    it("should convert empty model") {
      val expected = removeNoise(ModelGenerator.template.
        replace("#IMPORT#", "").
        replace("#NAMESPACE#", "definitions").
        replace("#PACKAGE#", "package pkg").
        replace("#TRAITS#", "").
        replace("#CLASSES#", "")
      )
      implicit val emptyModel = Map.empty[String, Domain.Type]
      val result = removeNoise(ModelGenerator.generate(Some("pkg")))
      result mustBe expected
    }
/*
    it ("should escape package if it contains reserved words") {
      fail("Not implemented yet") TODO
    }
*/
  }

  val expectedDefinitions = Map(
    "basic_extension.yaml" ->
      """package pkg
        |object definitions {
        |  // Basic error model
        |  trait ErrorModel {
        |    // The text of the error message
        |    def message: String
        |    // The error code
        |    def code: Int
        |  }
        |}""".stripMargin
  ,
    "basic_polymorphism.yaml" ->
      """package pkg
        |object definitions {
        |  trait Pet {
        |    def name: String
        |    def petType: String
        |  }
        |  // A representation of a dog
        |  trait Dog {
        |    // the size of the pack the dog is from
        |    def packSize: Int
        |  }
        |}""".stripMargin

  ).withDefaultValue("""package pkg
    |object definitions {
    |}""".stripMargin)

  describe("Model Generator integration test") {
    val fixtures = new File("compiler/src/test/resources/examples").listFiles

    fixtures.filter(_.getName.endsWith(".yaml")).foreach { file =>
      it(s"should parse the yaml swagger file ${file.getName} with expected definitions result") {
        val swaggerModel = YamlParser.parse(file)
        implicit val definitions = Swagger2Ast.convertDefinitions(swaggerModel)
        val result = removeNoise(ModelGenerator.generate(Some("pkg")))
        // println(result)
        result mustBe expectedDefinitions(file.getName)
      }
    }
  }
}
