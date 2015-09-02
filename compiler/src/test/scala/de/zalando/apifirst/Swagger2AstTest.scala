package de.zalando.apifirst

import java.io.File

import de.zalando.apifirst.Application.Parameter
import de.zalando.apifirst.Domain.{Field, Opt, Null, TypeMeta}
import de.zalando.apifirst.Http.GET
import de.zalando.apifirst.Path.{Segment, FullPath, Root}
import de.zalando.swagger.{Swagger2Ast, YamlParser}
import org.scalatest.{FunSpec, MustMatchers}

/**
 * @since 17.07.2015
 */
class Swagger2AstTest extends FunSpec with MustMatchers {

  describe("Swagger2Ast Converter") {

    val simple = YamlParser.parse(new File("compiler/src/test/resources/examples/simple.petstore.api.yaml"))
    val model = Swagger2Ast.convert("x-api-first")(simple)

    // | TODO | this whole test should break after we implement handler
    // | TODO | inheritance inside of swagger spec from path to the method
    it("coverts single-call spec to single-call model") {
      model.calls.size mustBe 1
    }

    model.calls foreach { call =>

      it("has correct path") {
        call.path mustBe FullPath(List(Root, Segment("pets")))
      }

      it("has correct method") {
        call.verb mustBe GET
      }

      it("has correct handler") {
        val expectedParams =
          List(
            Parameter("tags", Opt(Field("Seq[String]", Domain.Arr(Field("findPets", Domain.Str(None,TypeMeta(None)), TypeMeta(Some("tags to filter by"))),TypeMeta(Some("tags to filter by"))), TypeMeta(Some("tags to filter by"))),TypeMeta(Some("tags to filter by"))),None,None,"[^/]+",true),
            Parameter("limit",Opt(Field("Int", Domain.Int(TypeMeta(Some("maximum number of results to return"))), TypeMeta(Some("maximum number of results to return"))),TypeMeta(Some("maximum number of results to return"))),None,None,"[^/]+",true)
          )
        call.handler.packageName mustBe "admin"
        call.handler.controller mustBe "Dashboard"
        call.handler.instantiate mustBe false
        call.handler.method mustBe "methodLevel"
        call.handler.parameters mustBe expectedParams
      }
    }
  }

}
