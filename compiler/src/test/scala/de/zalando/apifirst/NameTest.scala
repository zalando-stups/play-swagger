package de.zalando.apifirst

import de.zalando.apifirst.naming.newnaming._
import org.scalatest.{FunSpec, MustMatchers}

class NameTest extends FunSpec with MustMatchers {
  describe("Name") {

    it("should have a default apply method for path names") {
      PathName("/") mustBe PathName("/")
      PathName("/foo") mustBe PathName("/foo")
    }

    it("should have default apply method for verb names") {
      VerbName("get", PathName("/foo")) mustBe VerbName("get", PathName("/foo"))
    }

    it("should have overwritten apply methods for different parameter parents") {
      ParmName("limit", PathName("/")) mustBe ParmName("limit", PathName("/"))
      ParmName("limit", VerbName("get", PathName("/foo"))) mustBe ParmName("limit", VerbName("get", PathName("/foo")))
    }

    it("should have overwritten apply methods for type names") {
      DomainName("Car") mustBe DomainName("Car", None)
      DomainName("Car", DomainName("Wheel")) mustBe DomainName("Car", Some(DomainName("Wheel", None)))
    }

    it("should represent paths as a strings correctly") {
      PathName("/").qualified mustBe "/"
      PathName("/foo").qualified mustBe "/foo"
      PathName("/foo/bar").qualified mustBe "/foo/bar"
    }

    it("should represent verbs as strings correctly") {
      VerbName("get", PathName("/")).qualified mustBe "/|get"
      VerbName("get", PathName("/foo")).qualified mustBe "/foo|get"
    }

    it("should represent parameters as strings correctly") {
      ParmName("limit", PathName("/foo")).qualified mustBe "/foo?limit"
      ParmName("limit", VerbName("get", PathName("/foo"))).qualified mustBe "/foo|get?limit"
    }

    it("should represent types as strings correctly") {
      DomainName("Car").qualified mustBe "Car"
      DomainName("Wheel", DomainName("Car")).qualified mustBe "Car#Wheel"
    }

    it("should compose on path names") {
      import dsl._
      val verbNameOnPathName = PathName("/foo/bar") =|= "get"
      verbNameOnPathName.qualified mustBe "/foo/bar|get"

      val parmNameOnPathName = PathName("/foo/bar") =?= "limit"
      parmNameOnPathName.qualified mustBe "/foo/bar?limit"
    }

    it("should compose on verb names") {
      import dsl._
      val parmNameOnVerbNameOnPathName = PathName("/foo/bar") =|= "get" =?= "limit"
      parmNameOnVerbNameOnPathName.qualified mustBe "/foo/bar|get?limit"
    }

    it("should compose on type names") {
      import dsl._
      val DomainNameOnDomainName = DomainName("Car") =#= "Wheel" =#= "Tire"
      DomainNameOnDomainName.qualified mustBe "Car#Wheel#Tire"
    }

    it("should have a name typed toString representation") {
      import dsl._
      val pathName = PathName("/foo/bar")
      val verbName = pathName =|= "GET"
      val parmName = verbName =?= "limit"
      val domainName = DomainName("Car") =#= "Wheel"

      pathName.toString mustBe "PathName(/foo/bar)"
      verbName.toString mustBe "VerbName(/foo/bar|GET)"
      parmName.toString mustBe "ParmName(/foo/bar|GET?limit)"
      domainName.toString mustBe "DomainName(Car#Wheel)"
    }
  }
}
