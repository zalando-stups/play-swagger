package de.zalando.play.controllers

import org.specs2.mutable._

/**
  * @author slasch 
  * @since 07.01.2016.
  */
class PlayPathBindablesTest extends Specification {

  "createMapper" should {
    "should create csv mapper" in {
      val wrapper = PipesArrayWrapper.apply(Nil)
      val mapper = PlayPathBindables.createMapper
      val reader = PlayPathBindables.createReader(mapper, wrapper)
      val result = PlayPathBindables.readArray(reader)("a|b|c|d")
      result must have size(4)
    }

  }
}
