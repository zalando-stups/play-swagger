package de.zalando.swagger

import org.scalatest.{FunSpec, MustMatchers}

import scala.io.Source

/**
 * @since 14.07.2015
 */
class ParserTest extends FunSpec with MustMatchers {

  def checkFile(name: String) = {
    val file = getClass.getClassLoader.getResource(name)
    val json = Source.fromURL(file).getLines().mkString("\n")
    val result = parser.readJson(json)
    result.host must be("petstore.swagger.io")
  }

  describe("Swagger Parser") {

    it("should parse a simple json file") {
      checkFile("petstore-simple.json")
    }

    it("should parse a minimal json file") {
      checkFile("petstore-minimal.json")
    }

    it("should parse an expanded json file") {
      checkFile("petstore-expanded.json")
    }

    it("should parse a default json file") {
      checkFile("petstore.json")
    }

  }


}
