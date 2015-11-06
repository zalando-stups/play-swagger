package de.zalando.swagger

import de.zalando.apifirst.new_naming._
import org.scalatest.{FunSpec, MustMatchers}

class ParseRefsTest extends FunSpec with MustMatchers {

  describe("Refs Parser") {
    it("should produce ReferenceObject for normal refs") {
      val ref = "#/definitions/Pet"
      val result = Reference(ref)
      result mustBe ReferenceObject(Pointer(ref))
      result.toString mustBe ref
    }
  }
}
