package de.zalando.apifirst.generators

import java.io.File

import de.zalando.ExpectedResults
import de.zalando.apifirst.{ScalaName, TypeNormaliser}
import de.zalando.swagger.{ModelConverter, StrictYamlParser}
import org.scalatest.{FunSpec, MustMatchers}

class ScalaFormParserGeneratorIntegrationTest extends FunSpec with MustMatchers with ExpectedResults {

  override val expectationsFolder = "/expected_results/form_parsers/"

  val exampleFixtures = new File("compiler/src/test/resources/examples").listFiles

  def toTest: File => Boolean = f => f.getName.endsWith(".yaml")

  describe("ScalaPlayControllerGenerator should generate controller and base controller files") {
    exampleFixtures.filter(toTest).foreach { file =>
      testScalaFormParserGenerator(file)
    }
  }

  def testScalaFormParserGenerator(file: File): Unit = {
    it(s"should generate from the yaml swagger file ${file.getName} a form parser") {
      val packageName = ScalaName.scalaPackageName(file.getName)
      val (base, model) = StrictYamlParser.parse(file)
      val ast         = ModelConverter.fromModel(base, model, Option(file))
      val flatAst     = TypeNormaliser.flatten(ast)
      val processor   = new ScalaGenerator(flatAst)
      val formParser  = processor.playScalaFormParsers(file.getName, packageName)
      val expected    = asInFile(file, "scala")
      if (expected.isEmpty) dump(formParser, file, "scala")
      clean(formParser) mustBe clean(expected)
    }
  }

  def clean(str: String): String = str.split("\n").map(_.trim).filter(_.nonEmpty).mkString("\n")
}
