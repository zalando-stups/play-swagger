package de.zalando.apifirst.generators

import java.io.File

import de.zalando.ExpectedResults
import de.zalando.apifirst._
import de.zalando.swagger.{ModelConverter, StrictYamlParser}
import org.scalatest.{FunSpec, MustMatchers}

class ScalaMarshallersGeneratorIntegrationTest extends FunSpec with MustMatchers with ExpectedResults {

  override val expectationsFolder = "/expected_results/marshallers/"

  val exampleFixtures = new File("compiler/src/test/resources/examples").listFiles

  def toTest: File => Boolean = f => f.getName.endsWith(".yaml")

  describe("ScalaMarshallersGenerator should generate model files") {
    exampleFixtures.filter(toTest).foreach { file =>
      testScalaMarshallersGenerator(file)
    }
  }

  def testScalaMarshallersGenerator(file: File): Unit = {
    it(s"should parse the yaml swagger file ${file.getName} as specification") {
      val (base, model) = StrictYamlParser.parse(file)
      val packageName = ScalaName.scalaPackageName(file.getName)
      val ast         = ModelConverter.fromModel(base, model, Option(file))
      val flatAst     = TypeNormaliser.flatten(ast)
      val scalaModel  = new ScalaGenerator(flatAst).playScalaMarshallers(file.getName, packageName)
      val expected    = asInFile(file, "scala")
      if (expected.isEmpty)
        dump(scalaModel, file, "scala")
      clean(scalaModel) mustBe clean(expected)
    }
  }

  def clean(str: String) = str.split("\n").map(_.trim).filterNot(_.isEmpty).mkString("\n")
}
