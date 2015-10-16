package de.zalando.swagger

import java.util.Map.Entry

import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.node.{NullNode, BaseJsonNode}
import com.fasterxml.jackson.databind._
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import de.zalando.swagger.strictModel._
import com.fasterxml.jackson.core.{JsonParser => JParser, _}

/**
 * @author  slasch 
 * @since   09.10.2015.
 */
object deserializers {

  /*
    TODO: the error location is pointing to the wrong line and column because we are not working with the original file,
    but with "prepared" version (needed due jackson's luck of support for some YAML features)
 */

  /**
   * Deserializer class for SecurityDefinitions
   * Creates right instance based on the data included in the specification
   *
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

  /**
   * Deserializer class for ParametersListItem
   *
   * Creates right instance based on the data included in the specification for one of following parameter list items:
   *
   * - JsonRef($ref)
   * - BodyParameter(in: body)
   * - PathParameter(in: path)
   * - FormDataParameter(in: formData)
   * - QueryParameter(in: query)
   * - HeaderParameter(in: header)
   *
   */
  private class ParametersListItemDeserializer[ParamType <: ParametersListItem] extends StdDeserializer[ParamType](classOf[ParametersListItem]) {

    import scala.collection.JavaConversions._

    @Override
    def deserialize(jp: JParser, ctxt: DeserializationContext): ParamType = {
      val mapper = jp.getCodec.asInstanceOf[ObjectMapper]
      val root = mapper.readTree(jp).asInstanceOf[BaseJsonNode]

      val instance = if (root == null || root == NullNode.instance) null
      else {
        val fields = root.fields.toSeq
        val refField = getFieldValue(fields, "$ref")
        lazy val parser = root.traverse
        refField match {
          case someRef if someRef != null && someRef.trim.nonEmpty =>
            mapper.convertValue(root, classOf[JsonReference])
          case otherRef =>
            val in = getFieldValue(fields, "in")
            if (in == null || in.trim.isEmpty)
              throw new JsonParseException("Parameter should have '$ref' or 'in' defined, but neither was found", jp.getTokenLocation)
            in.toLowerCase match {
              case "header" =>
                checkTypeIsNotFile(fields, parser)
                mapper.convertValue(root, classOf[HeaderParameter[_]])
              case "path" =>
                checkTypeIsNotFile(fields, parser)
                if ("true" != getFieldValue(fields, "required"))
                  throw new JsonParseException("Path parameter MUST be required", jp.getTokenLocation)
                mapper.convertValue(root, classOf[PathParameter[_]])
              case "formdata" =>
                mapper.convertValue(root, classOf[FormDataParameter[_]])
              case "query" =>
                checkTypeIsNotFile(fields, parser)
                mapper.convertValue(root, classOf[QueryParameter[_]])
              case "body" =>
                checkTypeIsNotFile(fields, parser)
                mapper.convertValue(root, classOf[BodyParameter[_]])
            }
        }
      }
      instance.asInstanceOf[ParamType]
    }
  }

  private class SchemaOrFileSchemaDeserializer extends StdDeserializer[SchemaOrFileSchema](classOf[SchemaOrFileSchema]) {

    import scala.collection.JavaConversions._

    @Override
    def deserialize(jp: JParser, ctxt: DeserializationContext): SchemaOrFileSchema = {
      val mapper = jp.getCodec.asInstanceOf[ObjectMapper]
      val root = mapper.readTree(jp).asInstanceOf[BaseJsonNode]

      if (root == null || root == NullNode.instance) null
      else {
        val fields = root.fields.toSeq
        val typeField = getFieldValue(fields, "type")
        typeField match {
          case "file" =>
            mapper.convertValue(root, classOf[FileSchema[_]])
          case other =>
            val ref = getFieldValue(fields, "$ref")
            if (ref == null || ref.trim.isEmpty)
              mapper.convertValue(root, classOf[Schema[_]])
            else
              mapper.convertValue(root, classOf[JsonReference])
        }
      }
    }
  }

  private class SchemaOrSchemaArrayDeserializer extends StdDeserializer[SchemaOrSchemaArray](classOf[SchemaOrSchemaArray]) {

    @Override
    def deserialize(jp: JParser, ctxt: DeserializationContext): SchemaOrSchemaArray = {
      val mapper = jp.getCodec.asInstanceOf[ObjectMapper]
      val root = mapper.readTree(jp).asInstanceOf[BaseJsonNode]

      if (root == null || root == NullNode.instance) null
      else if (root.isArray) mapper.convertValue(root, classOf[Many[SchemaOrReference]]).asInstanceOf[SchemaOrSchemaArray]
      else mapper.convertValue(root, classOf[SchemaOrFileSchema]).asInstanceOf[SchemaOrSchemaArray]
    }
  }

  private class JsonSchemaTypeDeserializer extends StdDeserializer[JsonSchemaType](classOf[JsonSchemaType]) {

    @Override
    def deserialize(jp: JParser, ctxt: DeserializationContext): JsonSchemaType = {
      val mapper = jp.getCodec.asInstanceOf[ObjectMapper]
      val root = mapper.readTree(jp).asInstanceOf[BaseJsonNode]

      if (root == null || root == NullNode.instance) null
      else if (root.isTextual) PrimitiveType.withName(root.asText()).asInstanceOf[JsonSchemaType]
      else if (root.isArray) {
        val array = mapper.convertValue(root, classOf[Array[String]])
        if (array.distinct.length == array.length) array.toSet.asInstanceOf[JsonSchemaType]
        else throw new JsonParseException(s"'JsonSchemaType array must contain unique values", jp.getTokenLocation)
      } else
        throw new JsonParseException(s"'JsonSchemaType must be array or primitive type", jp.getTokenLocation)
    }
  }

  private def checkTypeIsNotFile(fields: Seq[Entry[SimpleTag, JsonNode]], jp: JParser): Unit =
    if ("File".equalsIgnoreCase(getFieldValue(fields, "type")))
      throw new JsonParseException(s"'File' type is not allowed here", jp.getTokenLocation)

  private def getFieldValue(fields: Seq[Entry[String, JsonNode]], name: String, default: String = ""): String =
    fields find (_.getKey == name) map (_.getValue.asText) getOrElse default

  private val securityDeserializer = new SecurityDefinitionDeserializer
  val securityModule = new SimpleModule("PolymorphicSecurityDefinitionDeserializerModule", new Version(1, 0, 0, null, "", ""))
  securityModule.addDeserializer(classOf[SecurityDefinition], securityDeserializer)

  val parametersListItemModule = new SimpleModule("PolymorphicParametersListItemDeserializerModule", new Version(1, 0, 0, null, "", ""))

  private val paramListItemDeserializer = new ParametersListItemDeserializer[ParametersListItem]
  parametersListItemModule.addDeserializer(classOf[ParametersListItem], paramListItemDeserializer)

  private val parameterDeserializer = new ParametersListItemDeserializer[Parameter[_]]
  parametersListItemModule.addDeserializer(classOf[Parameter[_]], parameterDeserializer)

  private val arrayJsonSchemaDeserializer = new JsonSchemaTypeDeserializer
  parametersListItemModule.addDeserializer(classOf[JsonSchemaType], arrayJsonSchemaDeserializer)

  private val schemaOrSchemaArrayDeserializer = new SchemaOrSchemaArrayDeserializer
  parametersListItemModule.addDeserializer(classOf[SchemaOrSchemaArray], schemaOrSchemaArrayDeserializer)

  private val schemaOrFileSchemaDeserializer = new SchemaOrFileSchemaDeserializer
  parametersListItemModule.addDeserializer(classOf[SchemaOrReference], schemaOrFileSchemaDeserializer)
  parametersListItemModule.addDeserializer(classOf[SchemaOrFileSchema], schemaOrFileSchemaDeserializer)

}
