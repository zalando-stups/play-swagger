package de.zalando.swagger

import java.util.Map.Entry

import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.node.{NullNode, BaseJsonNode}
import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper, DeserializationContext}
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import de.zalando.swagger.strictModel._
import com.fasterxml.jackson.core.{JsonParser => JParser, _}

/**
 * @author  slasch 
 * @since   09.10.2015.
 */
object deserializers {

  /**
   * Deserializer class for SecurityDefinitions
   * Creates right instance based on the data included in the specification
   *
   * TODO: the error location is pointing to the wrong line and column because we are not working with the original file,
   * but with "prepared" version (needed due jackson's luck of support for some YAML features)
   */
  private class SecurityDefinitionDeserializer extends StdDeserializer[SecurityDefinition](classOf[SecurityDefinition]) {

    import scala.collection.JavaConversions._

    @Override
    def deserialize(jp: JParser, ctxt: DeserializationContext): SecurityDefinition = {
      val mapper = jp.getCodec.asInstanceOf[ObjectMapper]
      val root = mapper.readTree(jp).asInstanceOf[BaseJsonNode]

      if (root == null || root == NullNode.instance) null
      else {
        val fields = root.fields.toSeq
        val typeField = getFieldValue(fields, "type")
        lazy val parser = root.traverse
        val instance = typeField match {
          case "basic" =>
            mapper.readValue(parser, classOf[BasicAuthenticationSecurity])
          case "apiKey" =>
            val in = getFieldValue(fields, "in")
            in match {
              case a if a == "header" || a == "query" =>
                val name = getFieldValue(fields, "name")
                if (name != null && name.nonEmpty)
                  mapper.readValue(parser, classOf[ApiKeySecurity])
                else
                  throw new JsonParseException(s"Name should not be empty for apiKey", jp.getTokenLocation)
              case other =>
                throw new JsonParseException(s"Wrong in value '$other', expected one of [header, query]", jp.getTokenLocation)
            }
          case "oauth2" =>
            val flow = getFieldValue(fields, "flow")
            lazy val tokenUrl = getFieldValue(fields, "tokenUrl")
            flow match {
              case "implicit" =>
                val authorizationUrl = getFieldValue(fields, "authorizationUrl")
                if (authorizationUrl != null && authorizationUrl.nonEmpty)
                  mapper.readValue(parser, classOf[Oauth2ImplicitSecurity])
                else
                  throw new JsonParseException(s"authorizationUrl should not be empty for implicit oauth2", jp.getTokenLocation)

              case "password" =>
                if (tokenUrl != null && tokenUrl.nonEmpty)
                  mapper.readValue(parser, classOf[Oauth2PasswordSecurity])
                else
                  throw new JsonParseException(s"tokenUrl should not be empty for password oauth2", jp.getTokenLocation)

              case "application" =>
                if (tokenUrl != null && tokenUrl.nonEmpty)
                  mapper.readValue(parser, classOf[Oauth2ApplicationSecurity])
                else
                  throw new JsonParseException(s"tokenUrl should not be empty for application oauth2", jp.getTokenLocation)

              case "accessCode" =>
                if (tokenUrl != null && tokenUrl.nonEmpty)
                  mapper.readValue(parser, classOf[Oauth2AccessCodeSecurity])
                else
                  throw new JsonParseException(s"tokenUrl should not be empty for accessCode oauth2", jp.getTokenLocation)

              case other =>
                throw new JsonParseException(s"Wrong oauth2 value '$other', expected one of [implicit, password, application, accessCode]", jp.getTokenLocation)
            }
          case other =>
            throw new JsonParseException(s"Wrong type value '$other', expected one of [basic, apiKey, oauth2]", jp.getTokenLocation)
        }
        instance.asInstanceOf[SecurityDefinition]
      }
    }
  }

  def getFieldValue(fields: Seq[Entry[String, JsonNode]], name: String, default: String = ""): String =
    fields find (_.getKey == name) map (_.getValue.asText) getOrElse default

  private val securityDeserializer = new deserializers.SecurityDefinitionDeserializer
  val securityModule = new SimpleModule("PolymorphicSecurityDefinitionDeserializerModule", new Version(1, 0, 0, null, "", ""))
  securityModule.addDeserializer(classOf[strictModel.SecurityDefinition], securityDeserializer)

}
