package de.zalando.apifirst.generators

import java.io.File

import de.zalando.ExpectedResults
import de.zalando.apifirst.{TypeNormaliser, ParameterDereferencer, TypeDeduplicator, TypeFlattener}
import de.zalando.swagger.{ModelConverter, StrictYamlParser}
import org.scalatest.{FunSpec, MustMatchers}

class ScalaPlayTestsGeneratorIntegrationTest extends FunSpec with MustMatchers with ExpectedResults {

  override val expectationsFolder = "/expected_results/tests/"

  val modelFixtures = new File("compiler/src/test/resources/model").listFiles

  val exampleFixtures = new File("compiler/src/test/resources/examples").listFiles

  def toTest: File => Boolean = f => {
    f.getName.endsWith(".yaml") && f.getName.startsWith("simple")
  }

  describe("ScalaModelGenerator should generate model files") {
    (modelFixtures ++ exampleFixtures ).filter(toTest).foreach { file =>
      testScalaTestsGenerator(file)
    }
  }

  def testScalaTestsGenerator(file: File): Unit = {
    it(s"should parse the yaml swagger file ${file.getName} as specification") {
      val (base, model) = StrictYamlParser.parse(file)
      val ast         = ModelConverter.fromModel(base, model, Option(file))
      val flatAst     = TypeNormaliser.flatten(ast)
      val scalaTests  = new ScalaGenerator(flatAst).tests(file.getName)
      val expected    = asInFile(file, "scala")
      if (expected.isEmpty)
        dump(scalaTests, file, "scala")
      clean(scalaTests) mustBe clean(expected)
    }
  }

  def clean(str: String) = str.split("\n").map(_.trim).mkString("\n")
}
