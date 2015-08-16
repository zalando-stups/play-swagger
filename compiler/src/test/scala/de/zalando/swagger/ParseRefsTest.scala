package de.zalando.swagger

import de.zalando.apifirst.Domain
import Domain._
import org.scalatest.{FunSpec, MustMatchers}

class ParseRefsTest extends FunSpec with MustMatchers {

  describe("Refs Parser") {
    it("should produce ReferenceObject for normal refs") {
      Domain.Reference("#/definitions/Pet") mustBe ReferenceObject("/definitions/Pet", None)
    }
    it("should produce EmbeddedSchema for embedded refs") {
      Domain.Reference("definitions.yaml#/Pet") mustBe EmbeddedSchema("definitions.yaml", ReferenceObject("/Pet", None), None)
    }
    it("should produce RelativeSchemaFile for file refs") {
      Domain.Reference("Pet.json") mustBe RelativeSchemaFile("Pet.json", None)
    }


  }
}
