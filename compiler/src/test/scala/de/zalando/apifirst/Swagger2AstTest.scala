package de.zalando.apifirst

import java.io.File

import de.zalando.apifirst.Http.PATCH
import de.zalando.apifirst.Path.{FullPath, Root}
import de.zalando.swagger.{Swagger2Ast, YamlParser}
import org.scalatest.{FunSpec, MustMatchers}

/**
 * @since 17.07.2015
 */
class Swagger2AstTest extends FunSpec with MustMatchers {

  describe("Swagger2Ast Converter") {

    val minimal = YamlParser.parse(new File("compiler/src/test/resources/examples/minimal.api.yaml"))
    val model = Swagger2Ast.convert(minimal)

    it("coverts single-call spec to single-call model") {
      model.calls.size mustBe 1
    }

    val call = model.calls.head

    it("has correct path") {
      call.path mustBe FullPath(Root)
    }

    it("has correct method") {
      call.verb mustBe PATCH
    }

    it("has correct handler") {
      call.handler.packageName mustBe "admin"
      call.handler.controller mustBe "Dashboard"
      call.handler.instantiate mustBe false
      call.handler.method mustBe "index"
      call.handler.parameters mustBe None
    }
  }

}
