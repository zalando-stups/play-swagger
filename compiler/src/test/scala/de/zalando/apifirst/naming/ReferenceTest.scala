package de.zalando.apifirst.naming

import de.zalando.apifirst.new_naming._
import org.scalatest.{FunSpec, MustMatchers}

class ReferenceTest extends FunSpec with MustMatchers {
   describe("Reference") {

     it("can be created from absolute URI strings, optionally containing pointer fragments") {
       Reference("file:/swagger.yaml")          mustBe Reference("file:/swagger.yaml")
       Reference("http://goo.gl/swagger.yaml")  mustBe Reference("http://goo.gl/swagger.yaml")
       Reference("file:/swagger.yaml#/foo/bar") mustBe Reference("file:/swagger.yaml#/foo/bar")
     }

     it("can be created containing pointer fragments identifying a path segment") {
       Reference("file:/swagger.yaml#/{foo}/{bar}").parent mustBe Reference("file:/swagger.yaml#/{foo}")
     }

     it("must be able to append pointer tokens") {
       val base = Reference("file:/swagger.yaml")
       base / "foo"         mustBe Reference("file:/swagger.yaml#/foo")
       base / "foo" / "bar" mustBe Reference("file:/swagger.yaml#/foo/bar")
     }

     it("must be able to append pointers") {
       val base = Reference("file:/swagger.yaml")
       val foo = Pointer("/foo")
       val bar = Pointer("/bar")
       base / foo       mustBe Reference("file:/swagger.yaml#/foo")
       base / foo / bar mustBe Reference("file:/swagger.yaml#/foo/bar")
       base / foo / "" / bar mustBe Reference("file:/swagger.yaml#/foo//bar")
     }

     it("must be able to prepend pointer tokens") {
       val reference = Reference("file:/swagger.yaml#/bar")
       reference.prepend("foo") mustBe Reference("file:/swagger.yaml#/foo/bar")
     }

     it("must return a pointers parent reference or itself if no parent pointer reference exists") {
       val base = Reference("file:/swagger.yaml")
       (base / "foo" / "bar").parent mustBe Reference("file:/swagger.yaml#/foo")
       (base / "foo").parent         mustBe Reference("file:/swagger.yaml#")
       (base).parent                 mustBe Reference("file:/swagger.yaml#")
     }
   }
 }
