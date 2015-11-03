package de.zalando.swagger

import de.zalando.apifirst.Domain
import Domain._
import de.zalando.apifirst.new_naming.{Reference, RelativeSchemaFile, EmbeddedSchema, ReferenceObject}
import org.scalatest.{FunSpec, MustMatchers}

class ParseRefsTest extends FunSpec with MustMatchers {

  describe("Refs Parser") {
    it("should produce ReferenceObject for normal refs") {
      val ref = "#/definitions/Pet"
      val result = Reference(ref)
      result mustBe ReferenceObject(ref)
      result.toString mustBe ref
    }
    it("should produce EmbeddedSchema for embedded refs") {
      val ref = "http://petstore.yaml#/definitions/Pet"
      val result = Reference(ref)
      result mustBe EmbeddedSchema("http://petstore.yaml", "#/definitions/Pet")
      result.toString mustBe ref
    }
    it("should produce RelativeSchemaFile for file refs") {
      val ref = "file://Pet.json"
      val result = Reference(ref)
      result mustBe RelativeSchemaFile(ref)
      result.toString mustBe ref
    }


  }
}
