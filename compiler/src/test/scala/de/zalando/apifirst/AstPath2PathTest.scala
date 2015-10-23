package de.zalando.apifirst

import de.zalando.apifirst.Application.Parameter
import de.zalando.apifirst.Domain.naming.Name
import de.zalando.apifirst.Path._
import org.scalatest.{FunSpec, MustMatchers}

class AstPath2PathTest extends FunSpec with MustMatchers {

  describe("Path path2path") {

    val root = Path.path2path("/", List())
    val aasb = Path.path2path("/a", List())
    val rasb = Path.path2path("a", List())
    val nasb = Path.path2path("a/b", List())
    val parm = Path.path2path("/{a}", List(Parameter(Name("a"), null, null, null, "[0-9]+", true, ParameterPlace.PATH)))
    val ignr = Path.path2path("/a", List(Parameter(Name("a"), null, null, null, null, true, ParameterPlace.QUERY)))
    val pabm = Path.path2path("/a/{a}", List(Parameter(Name("a"), null, null, null, "[0-9]+", true, ParameterPlace.PATH)))

    val rend = Path.path2path("/a/b/c/", List())

    it("should convert the root path") {
      root mustBe FullPath.is(Root)
    }
    it("should convert absolute segments") {
      aasb mustBe FullPath.is(Root, Segment("a"))
    }
    it("should convert relative segments") {
      rasb mustBe FullPath.is(Segment("a"))
    }
    it("should convert nested path segments") {
      nasb mustBe FullPath.is(Segment("a"), Segment("b"))
    }
    it("should convert in-path parameters") {
      parm mustBe FullPath.is(Root, InPathParameter("a", "[0-9]+", true))
    }
    it("should convert in-path parameters after static parts") {
      pabm mustBe FullPath.is(Root, Segment("a"), InPathParameter("a", "[0-9]+", true))
    }
    it("should ignore non-in-path parameters") {
      ignr mustBe FullPath.is(Root, Segment("a"))
    }
    it("should answer its absolute property") {
      aasb.isAbsolute mustBe true
      rasb.isAbsolute mustBe false
    }
    it("should convert the root end path") {
      rend mustBe FullPath.is(Root, Segment("a"), Segment("b"), Segment("c"), Root)
    }

  }
}
