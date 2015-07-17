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

  case class SwaggerModel(
    swagger: Version = "2.0",
    info: Info,
    host: Host,
    basePath: BasePath,
    @JsonScalaEnumeration(classOf[SchemeType]) schemes: Schemes,
    consumes: Consumes,
    produces: Produces,
    paths: Paths,
    definitions: Definitions,
    parameters: Map[String, Parameter],
    responses: Responses,
    securityDefinitions: SecurityDefinitions,
    security: SecurityRequirements,
    tags: Tags
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
    title: String,
    description: String,
    termsOfService: String,
    contact: Contact,
    license: License,
    version: Version
    ) extends VendorExtensions with API

  case class Contact(
    name: String,
    url: String,
    email: String
    ) extends API

  case class License(
    name: String,
    url: URL
    ) extends API

  case class Path(
    get: Operation,
    put: Operation,
    post: Operation,
    delete: Operation,
    options: Operation,
    head: Operation,
    patch: Operation,
    parameters: List[Parameter],
    @JsonProperty("$ref") ref: Ref
    ) extends VendorExtensions with API

  case class Operation(
    tags: SimpleTags,
    summary: String,
    description: String,
    externalDocs: ExternalDocumentation,
    operationId: String,
    consumes: Consumes,
    produces: Produces,
    parameters: Parameters, // TODO should be ParameterOrReference
    responses: Responses, // TODO should be OperationResponses
    @JsonScalaEnumeration(classOf[SchemeType]) schemes: Schemes,
    deprecated: Boolean,
    security: SecurityRequirements
    ) extends VendorExtensions with API

  trait ParameterOrReference extends API

  case class Parameter(
    name: String,
    in: String,
    description: String,
    required: Boolean,
    // if in is "body"
    schema: Schema,
    // if in is any other value than body
    @JsonProperty("type") kind: String,
    format: String,
    allowEmptyValue: Boolean,
    items: Items,
    collectionFormat: String,
    default: String,
    maximum: String,
    exclusiveMaximum: Boolean,
    minimum: String,
    exclusiveMinimum: Boolean,
    maxLength: Int,
    minLength: Int,
    pattern: String,
    maxItems: Int,
    minItems: Int,
    uniqueItems: Boolean,
    enum: Enum
    // multipleOf: Int // TODO Scala 2.10.5 limits case classes to 22 attributes
    ) extends VendorExtensions with ParameterOrReference {
    lazy val bodyParameter = in.toLowerCase == "body"
    lazy val queryParameter = in.toLowerCase == "query"
  }

  case class Reference(
    @JsonProperty("$ref") ref: Ref
    ) extends ParameterOrReference

  case class Response(
    description: String,
    schema: Schema,
    headers: Headers,
    examples: Examples
    ) extends VendorExtensions with API

  case class Schema(
    discriminator: String,
    readOnly: Boolean,
    xml: Xml,
    externalDocs: ExternalDocumentation,
    example: Any,
    @JsonProperty("type") kind: String,
    @JsonProperty("$ref") ref: String,
    title: String,
    format: String,
    description: String,
    required: RequiredFields,
    items: Items,
    properties: Properties,
    enum: Enum
    ) extends API

  case class Header(
    description: String,
    @JsonScalaEnumeration(classOf[HeaderTypeReference]) @JsonProperty("type") headerType: HeaderType.Value,
    format: String,
    items: Items,
    collectionFormat: String,
    default: String,
    maximum: Int,
    exclusiveMaximum: Boolean,
    minimum: Int,
    exclusiveMinimum: Boolean,
    maxLength: Int,
    minLength: Int,
    pattern: String,
    maxItems: Int,
    minItems: Int,
    uniqueItems: Boolean,
    enum: Enum,
    multipleOf: Int
    ) extends API

  private[swagger] class HeaderTypeReference extends TypeReference[HeaderType.type]

  case object HeaderType extends Enumeration {
    type HeaderType = Value
    val STRING  = Value("string")
    val NUMBER  = Value("number")
    val INTEGER = Value("integer")
    val BOOLEAN = Value("boolean")
    val ARRAY   = Value("array")
  }

  case class Items(
    @JsonProperty("type") kind: String, // TODO should be enum
    format: String,
    items: Items,
    @JsonProperty("$ref") ref: String,
    collectionFormat: String,
    default: String,
    maximum: Int,
    exclusiveMaximum: Int,
    minimum: Int,
    exclusiveMinimum: Int,
    maxLength: Int,
    minLength: Int,
    pattern: String,
    maxItems: Int,
    minItems: Int,
    uniqueItems: Boolean,
    enum: List[String],
    multipleOf: Int
    ) extends API

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

  class VendorExtensions { self =>
    private[this] val extensions = new mutable.HashMap[String, String]

    @JsonAnySetter
    private[this] def handleUnknown(key: String, value: Any) {
      if (key.startsWith("x-") && value.isInstanceOf[String]) {
        extensions += key -> value.asInstanceOf[String]
      }
      else throw new UnrecognizedPropertyException(
        s"Unknown property: $key",
        null,
        self.getClass,
        key,
        null
      )
    }
    lazy val vendorExtensions = extensions.toMap
  }

  // format: OFF
  type Version    = String
  type Host       = String
  type BasePath   = String
  type Consume    = String
  type Produce    = String
  type Format     = String
  type SimpleTag  = String
  type URL        = String
  type Ref        = String

  type Many[T]    = List[T]

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
