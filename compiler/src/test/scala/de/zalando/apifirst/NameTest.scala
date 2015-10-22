package de.zalando.apifirst

import org.scalatest.{FunSpec, MustMatchers}

class NameTest extends FunSpec with MustMatchers {
  describe("Name") {

    import Domain.naming._
    import Domain.naming.dsl._

    val n1 = Name("foo") / "bar"
    val n2 = Name("baz")
    val n3 = n1 / n2 / "qux"

    it("should correctly compose") {
      n1.toString mustBe "/foo/bar"
      n2.toString mustBe "/baz"
      n3.toString mustBe "/foo/bar/baz/qux"

      (n1 / n2).toString mustBe "/foo/bar/baz"
      (n2 / n1).toString mustBe "/baz/foo/bar"
      ((n1 / n2) / (n2 / n1)).toString mustBe "/foo/bar/baz/baz/foo/bar"
    }

    it("should have correct simple name representations") {
      Name().simple mustBe "/"
      Name("/foo").simple mustBe "foo"
      Name("/foo/bar").simple mustBe "bar"
      Name("/foo/bar/baz").simple mustBe "baz"
      Name("/foo/bar/baz/qux").simple mustBe "qux"
    }

    it("should have correct qualified name representations") {
      n1.qualified mustBe "/foo/bar"
      n2.qualified mustBe "/baz"
      n3.qualified mustBe "/foo/bar/baz/qux"
    }

    it("should have correct namespaces") {
      Name("/foo").namespace mustBe Name("/")
      Name("/foo/bar").namespace mustBe Name("/foo")
      Name("/foo/bar/baz").namespace mustBe Name("/foo/bar")
    }

    it("always starts with root") {
      Name().toString mustBe "/"
      Name("/foo").toString mustBe "/foo"
      Name("/foo/bar").toString mustBe "/foo/bar"
    }

    it("is always absolute") {
      Name("foo").toString mustBe "/foo"
      Name("foo/bar").toString mustBe "/foo/bar"
    }

    it("has a normalized root") {
      Name("/").toString mustBe "/"
      Name("//").toString mustBe "/"
      Name("//foo").toString mustBe "/foo"
    }

    it("does not allow empty names") {
      intercept[IllegalArgumentException] {
        Name("/foo//bar")
      } .getMessage mustBe "requirement failed: empty names are not allowed: [foo,,bar]"
      intercept[IllegalArgumentException] {
        Name("/foo/bar//")
      } .getMessage mustBe "requirement failed: empty names are not allowed: [foo,bar,,]"
    }

    it("maintains a correct internal representation") {
      Name(List.empty).toString mustBe "/"
      Name(List("foo")).toString mustBe "/foo"
      Name(List("foo", "bar")).toString mustBe "/foo/bar"
    }
  }
}
