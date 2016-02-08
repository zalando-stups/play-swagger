package de.zalando.swagger

import java.io.File
import java.net.URI

import de.zalando.swagger.strictModel.SwaggerModel
import org.scalatest.{FunSpec, MustMatchers}

class StrictParseExamplesTest extends FunSpec with MustMatchers {

  val fixtures = new File("compiler/src/test/resources/examples").listFiles ++
    new File("compiler/src/test/resources/schema_examples").listFiles

  // TODO enable these tests after jackson-json-reference is fixed
  val disabledTests = Seq("body_to_ref_recurse.api.yaml")

  describe("Strict Swagger Parser") {
    fixtures.filter(f => f.getName.endsWith(".yaml") && !disabledTests.contains(f.getName)).foreach { file =>
      it(s"should parse the yaml swagger file ${file.getName} as specification") {
        val result = StrictYamlParser.parse(file)
        result._1 mustBe a [URI]
        result._2 mustBe a [SwaggerModel]
      }
    }
  }
}
