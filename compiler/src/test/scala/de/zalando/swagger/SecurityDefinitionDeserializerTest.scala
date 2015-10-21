package de.zalando.swagger

import java.io.File

import de.zalando.swagger.strictModel._
import org.scalatest.{MustMatchers, FunSpec}

/**
 * @author  slasch 
 * @since   09.10.2015.
 */
class SecurityDefinitionDeserializerTest extends FunSpec with MustMatchers {

  val file = new File("compiler/src/test/resources/examples/todo/security.api.yaml")

  describe("SecurityDefinitionDeserializer") {
      it(s"should parse security definitions in the ${file.getName}") {
        val result = YamlParser.parse(file).securityDefinitions
        result.size mustBe 3
        result("githubAccessCode") mustBe a [Oauth2AccessCodeSecurity]
        result("petstoreImplicit") mustBe a [Oauth2ImplicitSecurity]
        result("internalApiKey") mustBe a [ApiKeySecurity]
      }
  }
}
