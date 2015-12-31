package de.zalando.apifirst.generators

import java.io.File

import de.zalando.ExpectedResults
import de.zalando.apifirst.TypeNormaliser
import de.zalando.swagger.{ModelConverter, StrictYamlParser}
import org.scalatest.{FunSpec, MustMatchers}

class ScalaControllerGeneratorIntegrationTest extends FunSpec with MustMatchers with ExpectedResults {

  override val expectationsFolder = "/expected_results/controllers/"

  val modelFixtures = new File("compiler/src/test/resources/model").listFiles

  val exampleFixtures = new File("compiler/src/test/resources/examples").listFiles

  def toTest: File => Boolean = f => f.getName.endsWith(".yaml")

  describe("ScalaPlayControllerGenerator should generate controller and base controller files") {
    (modelFixtures ++ exampleFixtures ).filter(toTest).foreach { file =>
      testScalaControllerGenerator(file)
    }
  }

  def testScalaControllerGenerator(file: File): Unit = {
    it(s"should parse the yaml swagger file ${file.getName} as specification") {
      val expectedC   = asInFile(file, "scala")

      val (base, model) = StrictYamlParser.parse(file)
      val ast         = ModelConverter.fromModel(base, model, Option(file))
      val flatAst     = TypeNormaliser.flatten(ast)
      val processor   = new ScalaGenerator(flatAst)
      val controllers = processor.playScalaControllers(file.getName, expectedC)
      val bases       = processor.playScalaControllerBases(file.getName)
      if (expectedC.isEmpty)
        dump(controllers, file, "scala")
      val expectedB   = asInFile(file, "base.scala")
      if (expectedB.isEmpty) dump(bases, file, "base.scala")
      clean(bases) mustBe clean(expectedB)
      clean(controllers) mustBe clean(expectedC)
    }
  }

  def clean(str: String) = str.split("\n").map(_.trim).filter(_.nonEmpty).mkString("\n")
}
