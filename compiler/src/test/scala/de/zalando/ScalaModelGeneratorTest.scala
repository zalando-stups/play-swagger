package de.zalando

import de.zalando.apifirst.Domain._
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

    it("should generate single type alias for an option") {
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

    it("should generate single type alias for an array") {
      val model = Map(
        "definitions" / "Int" -> Arr(Intgr(None), None),
        "definitions" / "Dbl" -> Arr(Dbl(None), None),
        "definitions" / "Flt" -> Arr(Flt(None), None)
      )
      new ScalaModelGenerator(model)("test") mustBe
        """package test
          |package definitions {
          |
          |type Int = scala.collection.Seq[Int]
          |type Dbl = scala.collection.Seq[Double]
          |type Flt = scala.collection.Seq[Float]
          |}
          |""".stripMargin
    }

    it("should generate single type alias for catchAll") {
      val model = Map(
        "parameters" / "all" -> CatchAll(Bool(None), None)
      )
      new ScalaModelGenerator(model)("test") mustBe
        """package test
          |package parameters {
          |
          |type All = scala.collection.immutable.Map[String, Boolean]
          |}
          |""".stripMargin
    }
  }
}
