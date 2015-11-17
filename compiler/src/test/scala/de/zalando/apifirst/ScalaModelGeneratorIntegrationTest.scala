package de.zalando.apifirst

import java.io.File

import de.zalando.{ScalaModelGenerator, ExpectedResults}
import de.zalando.swagger.{ModelConverter, StrictYamlParser}
import org.scalatest.{FunSpec, MustMatchers}

class ScalaModelGeneratorIntegrationTest extends FunSpec with MustMatchers with ExpectedResults {

  override val expectationsFolder = "/expected_results/model/"

  val modelFixtures = new File("compiler/src/test/resources/model").listFiles

  val exampleFixtures = new File("compiler/src/test/resources/examples").listFiles

  def toTest: File => Boolean = f => {
    f.getName.endsWith(".yaml") && f.getName == "basic_polymorphism.yaml"
  }

  describe("ScalaModelGenerator should generate model files") {
    (modelFixtures ++ exampleFixtures ).filter(toTest).foreach { file =>
      testTypeFlattener(file)
    }
  }

  def testTypeFlattener(file: File): Unit = {
    it(s"should parse the yaml swagger file ${file.getName} as specification") {
      val (base, model) = StrictYamlParser.parse(file)
      val ast         = ModelConverter.fromModel(base, model)
      val flatAst     = (ParameterDereferencer.apply _ andThen TypeFlattener.apply andThen TypeDeduplicator.apply) (ast)
      val scalaModel  = new ScalaModelGenerator(flatAst.typeDefs, flatAst.discriminators).apply(file.getName)
      val expected    = asInFile(file, "scala")
      if (expected.isEmpty) dump(scalaModel, file, "scala")
      clean(scalaModel) mustBe clean(expected)
    }
  }

  def clean(str: String) = str.split("\n").map(_.trim).mkString("\n")
}
