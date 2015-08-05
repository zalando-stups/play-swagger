package de.zalando.swagger

import com.fasterxml.jackson.annotation.{JsonAnySetter, JsonProperty}
import com.fasterxml.jackson.core.`type`.TypeReference
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException
import com.fasterxml.jackson.module.scala.JsonScalaEnumeration

import scala.collection.mutable

/**
 * @since 13.07.2015
 */
object model {

  sealed trait API

  // Header, Items, Parameter, Schema

  // format: OFF
  abstract class CommonProperties (
    val format:           String,
    val default:          String,
    val multipleOf:       Int,
    val maximum:          Double,
    val exclusiveMaximum: Boolean,
    val minimum:          Double,
    val exclusiveMinimum: Boolean,
    val maxLength:        Long,
    val minLength:        Long,
    val pattern:          String,
    val maxItems:         Long,
    val minItems:         Long,
    val uniqueItems:      Boolean,
    val enum:             Enum,
    val items:            Items,
    val `type`:           PrimitiveType.Value
  )

  case class SwaggerModel(
    @JsonScalaEnumeration(classOf[SchemeType]) schemes: Schemes,
    swagger:              Version = "2.0",
    info:                 Info,
    host:                 Host,
    basePath:             BasePath,
    consumes:             Consumes,
    produces:             Produces,
    paths:                Paths,
    definitions:          Definitions,
    parameters:           Map[String, Parameter],
    responses:            Responses,
    securityDefinitions:  SecurityDefinitions,
    security:             SecurityRequirements,
    tags:                 Tags
    ) extends API

  private[swagger] class SchemeType extends TypeReference[Scheme.type]

  case object Scheme extends Enumeration {
    type Scheme = Value
    val HTTP  = Value("http")
    val HTTPS = Value("https")
    val WS    = Value("ws")
    val WSS   = Value("wss")
  }

  case class Info(
    title:                String,
    description:          String,
    termsOfService:       String,
    contact:              Contact,
    license:              License,
    version:              Version
  ) extends VendorExtensions with API

  case class Contact(
    name:                 String,
    url:                  String,
    email:                String
  ) extends API

  case class License(
    name:                 String,
    url:                  URL
  ) extends API

  case class Path(
    get:                  Operation,
    put:                  Operation,
    post:                 Operation,
    delete:               Operation,
    options:              Operation,
    head:                 Operation,
    patch:                Operation,
    parameters:           List[Parameter],
    @JsonProperty("$ref") ref: Ref
  ) extends VendorExtensions with API

  case class Operation(
    tags:                 SimpleTags,
    summary:              String,
    description:          String,
    externalDocs:         ExternalDocumentation,
    operationId:          String,
    consumes:             Consumes,
    produces:             Produces,
    parameters:           Parameters, // TODO should be ParameterOrReference
    responses:            Responses,  // TODO should be OperationResponses
    deprecated:           Boolean,
    security:             SecurityRequirements,
    @JsonScalaEnumeration(classOf[SchemeType]) schemes: Schemes
  ) extends VendorExtensions with API

  trait ParameterOrReference extends API

  case class Parameter(
    override val format:           String,
    override val default:          String,
    override val multipleOf:       Int,
    override val maximum:          Double,
    override val exclusiveMaximum: Boolean,
    override val minimum:          Double,
    override val exclusiveMinimum: Boolean,
    override val maxLength:        Long,
    override val minLength:        Long,
    override val pattern:          String,
    override val maxItems:         Long,
    override val minItems:         Long,
    override val uniqueItems:      Boolean,
    override val enum:             Enum,
    override val items:            Items,
    @JsonScalaEnumeration(classOf[PrimitiveTypeReference]) override val `type`: PrimitiveType.Value,

    name:                           String,
    in:                             String,
    required:                       Boolean,
    schema:                         Schema,   // if in is "body"

    collectionFormat:               String,
    description:                    String
    // Scala 2.10.5 limits case classes to 22 attributes
    // multipleOf: Int
    // allowEmptyValue:                Boolean,  // if in is any other value than body
  ) extends CommonProperties(
      format, default, multipleOf, maximum, exclusiveMaximum, minimum, exclusiveMinimum,
      maxLength, minLength, pattern, maxItems, minItems, uniqueItems, enum, items, `type`
    ) with VendorExtensions with ParameterOrReference {
    lazy val bodyParameter      = in.toLowerCase == "body"
    lazy val queryParameter     = in.toLowerCase == "query"
    lazy val pathParameter      = in.toLowerCase == "path"
    lazy val headerParameter    = in.toLowerCase == "header"
    lazy val formDataParameter  = in.toLowerCase == "formData"
  }

  case class Reference(
    @JsonProperty("$ref") ref: Ref
  ) extends ParameterOrReference

  case class Response(
    description:                    String,
    schema:                         Schema,
    headers:                        Headers,
    examples:                       Examples
  ) extends VendorExtensions with API

  case class Schema(
    override val format:            String,
    override val default:           String,
    override val multipleOf:        Int,
    override val maximum:           Double,
    override val exclusiveMaximum:  Boolean,
    override val minimum:           Double,
    override val exclusiveMinimum:  Boolean,
    override val maxLength:         Long,
    override val minLength:         Long,
    override val pattern:           String,
    override val maxItems:          Long,
    override val minItems:          Long,
    override val uniqueItems:       Boolean,
    override val enum:              Enum,
    override val items:             Items,
    @JsonScalaEnumeration(classOf[PrimitiveTypeReference]) override val `type`: PrimitiveType.Value,

    required:                       Boolean,
    example:                        Any,
    description:                    String,
    title:                          String,
    @JsonProperty("$ref") ref:      String
// Scala 2.10.5 limits case classes to 22 attributes
//    properties:                     Properties,
//    additionalProperties:           Properties,
//    discriminator:                  String,   // polymorphism support
//    readOnly:                       Boolean,  // properties only
//    xml:                            Xml,      // properties only ?
//    externalDocs:                   ExternalDocumentation,
//    allOf:                          Many[Any], // TODO check JSON Schema
//    maxProperties:                  Int, // TODO no description in swagger spec, check JSON Schema
//    minProperties:                  Int, // TODO no description in swagger spec, check JSON Schema
  ) extends CommonProperties(
      format, default, multipleOf, maximum, exclusiveMaximum, minimum, exclusiveMinimum,
    maxLength, minLength, pattern, maxItems, minItems, uniqueItems, enum, items, `type`
  ) with API

  case class Header(
    override val format:            String,
    override val default:           String,
    override val multipleOf:        Int,
    override val maximum:           Double,
    override val exclusiveMaximum:  Boolean,
    override val minimum:           Double,
    override val exclusiveMinimum:  Boolean,
    override val maxLength:         Long,
    override val minLength:         Long,
    override val pattern:           String,
    override val maxItems:          Long,
    override val minItems:          Long,
    override val uniqueItems:       Boolean,
    override val enum:              Enum,
    override val items:             Items,
    @JsonScalaEnumeration(classOf[PrimitiveTypeReference]) override val `type`: PrimitiveType.Value, // except NULL and OBJECT

    description:                    String,
    collectionFormat:               String
   ) extends CommonProperties (
    format, default, multipleOf, maximum, exclusiveMaximum, minimum, exclusiveMinimum,
    maxLength, minLength, pattern, maxItems, minItems, uniqueItems, enum, items, `type`
  ) with API

  private[swagger] class PrimitiveTypeReference extends TypeReference[PrimitiveType.type]

  case object PrimitiveType extends Enumeration {
    type PrimitiveType = Value
    val ARRAY   = Value("array")
    val BOOLEAN = Value("boolean")
    val INTEGER = Value("integer")
    val NUMBER  = Value("number")
    val NULL    = Value("null")
    val OBJECT  = Value("object")
    val STRING  = Value("string")
  }

  case class Items(
    override val format:           String,
    override val default:          String,
    override val multipleOf:       Int,
    override val maximum:          Double,
    override val exclusiveMaximum: Boolean,
    override val minimum:          Double,
    override val exclusiveMinimum: Boolean,
    override val maxLength:        Long,
    override val minLength:        Long,
    override val pattern:          String,
    override val maxItems:         Long,
    override val minItems:         Long,
    override val uniqueItems:      Boolean,
    override val enum:             Enum,
    override val items:            Items,
    @JsonScalaEnumeration(classOf[PrimitiveTypeReference]) override val `type`: PrimitiveType.Value,

    collectionFormat:              String,
    @JsonProperty("$ref") ref:     String
  ) extends CommonProperties(
    format, default, multipleOf, maximum, exclusiveMaximum, minimum, exclusiveMinimum,
    maxLength, minLength, pattern, maxItems, minItems, uniqueItems, enum, items, `type`
  ) with API

  case class Definition(
    properties: Properties,
    required: RequiredFields
    ) extends API

  case class Property(
    @JsonProperty("type") kind: String,
    @JsonProperty("$ref") ref: String,
    description: String,
    format: String,
    items: Items,
    example: String,
    properties: Properties
    ) extends API

  case class SecurityDefinition(
    @JsonProperty("type") kind: String,
    description: String,
    name: String,
    in: String,
    flow: String,
    authorizationUrl: String,
    tokenUrl: String,
    scopes: Scopes
    ) extends VendorExtensions with API

  case class Tag(
    name: String,
    description: String,
    externalDocs: ExternalDocumentation
    ) extends VendorExtensions with API

  case class ExternalDocumentation(
    description: String,
    url: String
    ) extends API

  case class Xml(
    name: String,
    namespace: String,
    prefix: String,
    attribute: Boolean,
    wrapped: Boolean
    ) extends API

  trait VendorExtensions { self =>
    private[this] val extensions = new mutable.HashMap[String, String]

    @JsonAnySetter
    def handleUnknown(key: String, value: Any) {
      value match {
        case str: String if key.startsWith("x-") =>
          extensions += key -> str
        case _ =>
          throw new UnrecognizedPropertyException(
            s"Unknown property: $key",
            null,
            self.getClass,
            key,
            null
          )
      }
    }
    lazy val vendorExtensions = extensions.toMap
  }

  type Version              = String
  type Host                 = String
  type BasePath             = String
  type Consume              = String
  type Produce              = String
  type Format               = String
  type SimpleTag            = String
  type URL                  = String
  type Ref                  = String

  type Many[T]              = List[T]

  type Consumes             = Many[Consume]
  type Produces             = Many[Produce]
  type Schemes              = Many[Scheme.Value]
  type Tags                 = Many[Tag]
  type SimpleTags           = Many[SimpleTag]
  type Examples             = Many[Example]
  type ExternalDocs         = Many[ExternalDocumentation]
  type Parameters           = Many[Parameter]
  type SecurityRequirements = Many[SecurityRequirement]
  type Enum                 = Many[String]
  type RequiredFields       = Many[String]

  type Responses            = Map[String, Response]
  type Headers              = Map[String, Header]
  type Paths                = Map[String, Path]  // TODO add VendorExtensions
  type SecurityDefinitions  = Map[String, SecurityDefinition]
  type SecurityRequirement  = Map[String, List[String]]
  type Definitions          = Map[String, Definition]
  type Example              = Map[String, AnyRef]
  type Properties           = Map[String, Property]
  type Scopes               = Map[String, String]
  // format: ON
}
