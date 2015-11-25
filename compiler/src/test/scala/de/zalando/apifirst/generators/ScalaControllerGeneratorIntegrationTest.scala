package de.zalando.apifirst.generators

import java.io.File

import de.zalando.ExpectedResults
import de.zalando.apifirst.{ParameterDereferencer, TypeDeduplicator, TypeFlattener}
import de.zalando.swagger.{ModelConverter, StrictYamlParser}
import org.scalatest.{FunSpec, MustMatchers}

class ScalaControllerGeneratorIntegrationTest extends FunSpec with MustMatchers with ExpectedResults {

  override val expectationsFolder = "/expected_results/controllers/"

  val modelFixtures = new File("compiler/src/test/resources/model").listFiles

  val exampleFixtures = new File("compiler/src/test/resources/examples").listFiles

  def toTest: File => Boolean = f => {
    f.getName.endsWith(".yaml")
  }

  describe("ScalaPlayGenerator should generate model files") {
    (modelFixtures ++ exampleFixtures ).filter(toTest).foreach { file =>
      testScalaModelGenerator(file)
    }
  }

  def testScalaModelGenerator(file: File): Unit = {
    it(s"should parse the yaml swagger file ${file.getName} as specification") {
      val (base, model) = StrictYamlParser.parse(file)
      val ast         = ModelConverter.fromModel(base, model, Option(file))
      val flatAst     = (ParameterDereferencer.apply _ andThen TypeFlattener.apply andThen TypeDeduplicator.apply) (ast)
      val controllers = new ScalaGenerator(flatAst).playScalaControllers(file.getName)
      val expected    = asInFile(file, "scala")
      // if (expected.isEmpty)
        dump(controllers, file, "scala")
      clean(controllers) mustBe clean(expected)
    }
  }

  def clean(str: String) = str.split("\n").map(_.trim).mkString("\n")
}
