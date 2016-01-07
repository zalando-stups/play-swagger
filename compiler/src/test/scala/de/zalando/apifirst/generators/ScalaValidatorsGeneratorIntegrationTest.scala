package de.zalando.apifirst.generators

import java.io.File

import de.zalando.ExpectedResults
import de.zalando.apifirst.{TypeNormaliser, ParameterDereferencer, TypeDeduplicator, TypeFlattener}
import de.zalando.swagger.{ModelConverter, StrictYamlParser}
import org.scalatest.{FunSpec, MustMatchers}

class ScalaValidatorsGeneratorIntegrationTest extends FunSpec with MustMatchers with ExpectedResults {

  override val expectationsFolder = "/expected_results/validation/"

  val validationFixtures = new File("compiler/src/test/resources/validations").listFiles

  val exampleFixtures = new File("compiler/src/test/resources/examples").listFiles

  val modelFixtures = new File("compiler/src/test/resources/model").listFiles

  def toTest: File => Boolean = f => f.getName.endsWith(".yaml")

  describe("ScalaValidatorsGenerator should generate model files") {
    (validationFixtures ++ exampleFixtures ++ modelFixtures).filter(toTest).foreach { file =>
      testScalaValidatorGenerator(file)
    }
  }

  def testScalaValidatorGenerator(file: File): Unit = {
    it(s"should parse the yaml swagger file ${file.getName} as specification") {
      val (base, model) = StrictYamlParser.parse(file)
      val ast         = ModelConverter.fromModel(base, model, Option(file))
      val flatAst     = TypeNormaliser.flatten(ast)
      val scalaModel  = new ScalaGenerator(flatAst).playValidators(file.getName)
      val expected    = asInFile(file, "scala")
      if (expected.isEmpty)
        dump(scalaModel, file, "scala")
      clean(scalaModel) mustBe clean(expected)
    }
  }

  def clean(str: String) = str.split("\n").map(_.trim).mkString("\n")
}
