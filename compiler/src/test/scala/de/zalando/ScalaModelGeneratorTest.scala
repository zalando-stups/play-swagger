package de.zalando

import de.zalando.apifirst.Domain.{Str, Lng, Opt}
import de.zalando.apifirst.new_naming
import org.scalatest.{MustMatchers, FunSpec}
import new_naming.dsl._
/**
  * @author  slasch 
  * @since   16.11.2015.
  */
class ScalaModelGeneratorTest extends FunSpec with MustMatchers {

  describe("ScalaModelGeneratorTest") {
    it("should generate nothing for empty model") {
      new ScalaModelGenerator(Map.empty)("test") mustBe ""
    }
    it("should generate single type alias for option") {
      val model = Map(
        "definitions" / "Opti" -> Opt(Lng(None), None),
        "definitions" / "Stri" -> Opt(Str(None, None), None)
      )
      new ScalaModelGenerator(model)("test") mustBe
        """package test
          |package definitions {
          |
          |type Opti = scala.Option[Long]
          |type Stri = scala.Option[String]
          |}
          |""".stripMargin
    }
    // TODO something should be done with these names
    it("should deal with overriden type definitions") {
      val model = Map(
        "definitions" / "Option" -> Opt(Lng(None), None),
        "definitions" / "String" -> Opt(Str(None, None), None)
      )
      new ScalaModelGenerator(model)("overloaded") mustBe
        """package overloaded
          |package definitions {
          |
          |type Option = scala.Option[Long]
          |type String = scala.Option[String]
          |}
          |""".stripMargin
    }
  }
}
