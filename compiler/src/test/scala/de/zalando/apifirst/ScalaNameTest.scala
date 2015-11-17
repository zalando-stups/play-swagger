package de.zalando.apifirst

import de.zalando.apifirst.ScalaName._
import de.zalando.apifirst.new_naming.dsl._
import org.scalatest.{FunSpec, MustMatchers}
/**
  * @author  slasch 
  * @since   16.11.2015.
  */
class ScalaNameTest extends FunSpec with MustMatchers {

  it("must correctly capitalize names") {
    ("one" / "two" / "three").names mustBe ("one", "Two", Some("three"))
    ("ONE" / "TWO" / "THREE").names mustBe ("one", "TWO", Some("tHREE"))
    ("OnE" / "TwO" / "ThReE").names mustBe ("one", "TwO", Some("thReE"))
  }

  it("must correctly recognize short names") {
    ("one" / "two").names mustBe ("one", "Two", None)
  }

  it("must correctly escape scala names") {
    ("catch" / "if" / "match").names mustBe ("`catch`","If",Some("`match`"))
  }

  it("must correctly concat names") {
    ("definitions"/"Example"/"nestedArrays"/"Opt"/"Arr:").names mustBe ("definitions", "Example", Some("`nestedArrays_Opt_Arr:`"))
  }

}
