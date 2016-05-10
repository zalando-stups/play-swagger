package de.zalando.swagger

import java.io.File

import de.zalando.ExpectedResults
import org.scalatest.{FunSpec, MustMatchers}

/**
  * @since 05.03.2016
  */
class SecurityConstraintsIntegrationTest extends FunSpec with MustMatchers with ExpectedResults {

  override val expectationsFolder = "/expected_results/security_constraints/"

  val fixtures = new File("compiler/src/test/resources/examples").listFiles

  describe("Swagger ApiCall Converter with security constraints") {
    fixtures.filter(_.getName.endsWith(".yaml")).foreach { file =>
      testSecurityConverter(file)
    }
  }

  def testSecurityConverter(file: File): Unit = {
    it(s"should convert security constraints in ${file.getName}") {
      val (base, model) = StrictYamlParser.parse(file)
      val ast         = ModelConverter.fromModel(base, model, Option(file))
      val fullResult  = ast.calls.filter(_.security.nonEmpty).flatMap(_.security).distinct.mkString("\n")
      val expected    = asInFile(file, "types")
      if (expected.isEmpty && fullResult.trim.nonEmpty)
        dump(fullResult, file, "types")
      clean(fullResult) mustBe clean(expected)
    }
  }

  def clean(str: String) = str.split("\n").map(_.trim).mkString("\n")
}
