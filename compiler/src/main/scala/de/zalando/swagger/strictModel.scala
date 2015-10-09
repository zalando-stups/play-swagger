package de.zalando.swagger

import com.fasterxml.jackson.annotation.{JsonProperty, JsonAnySetter}
import com.fasterxml.jackson.core.`type`.TypeReference
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException
import com.fasterxml.jackson.module.scala.JsonScalaEnumeration

import scala.collection.mutable

/**
 * @author  slasch
 * @since   09.10.2015.
 */
object strictModel {

  sealed trait API

  // format: OFF
  /**
   * The main container of swagger information
   *
   * @param swagger
   * @param info
   * @param paths               Relative paths to the individual endpoints. They must be relative to the 'basePath'.
   * @param schemes             The transfer protocol of the API.
   * @param host                The host (name or ip) of the API. Example: 'swagger.io'
   * @param basePath            The base path to the API. Example: '/api'.
   * @param consumes            A list of MIME types accepted by the API.
   * @param produces            A list of MIME types the API can produce.
   * @param definitions         One or more JSON objects describing the schemas being consumed and produced by the API.
   * @param parameters          One or more JSON representations for parameters
   * @param responses           One or more JSON representations for responses
   * @param securityDefinitions
   * @param security
   * @param tags
   */
  case class SwaggerModel(
    @JsonScalaEnumeration(classOf[SwaggerEnumType]) @JsonProperty(required = true) swagger: SwaggerEnum.Value,
    @JsonProperty(required = true) info:  Info,
    @JsonProperty(required = true) paths: Paths,

    @JsonScalaEnumeration(classOf[SchemeType]) schemes: SchemesList,
    host:                 Host,
    basePath:             BasePath,
    consumes:             MediaTypeList,
    produces:             MediaTypeList,
    definitions:          Definitions,
    parameters:           ParameterDefinitions,
    responses:            ResponseDefinitions,
    securityDefinitions:  SecurityDefinitions,
    security:             Security,
    tags:                 Tags
  ) extends VendorExtensions with API

  private[swagger] class SchemeType extends TypeReference[Scheme.type]

  private[swagger] class SwaggerEnumType extends TypeReference[SwaggerEnum.type]

  /**
   * The transfer protocol of the API.
   */
  case object Scheme extends Enumeration {
    type Scheme = Value
    val HTTP  = Value("http")
    val HTTPS = Value("https")
    val WS    = Value("ws")
    val WSS   = Value("wss")
  }

  case object CollectionFormat extends Enumeration {
    type CollectionFormat = Value
    val CSV     = Value("csv")
    val SSV     = Value("ssv")
    val TSV     = Value("tsv")
    val PIPES   = Value("pipes")
  }
  case object CollectionFormatWithMulti extends Enumeration {
    type CollectionFormat = Value
    val CSV     = Value("csv")
    val SSV     = Value("ssv")
    val TSV     = Value("tsv")
    val PIPES   = Value("pipes")
    val MULTI   = Value("multi")
  }

  /**
   * Single value available for swagger version 2.0
   */
  case object SwaggerEnum extends Enumeration {
    type SwaggerEnum = Value
    val v2_0  = Value("2.0")
  }

  /**
   *
   *
   * @param name
   * @param description
   * @param externalDocs
   */
  case class Tag(
    @JsonProperty(required = true) name: String,
    description: String,
    externalDocs: ExternalDocs
  ) extends VendorExtensions with API

  /**
   * Information about external documentation
   *
   * @param url
   * @param description
   */
  case class ExternalDocs(
    @JsonProperty(required = true) url: URI,
    description: String
  ) extends VendorExtensions with API


  /**
   *
   * General information about the API.
   *
   * @param title           A unique and precise title of the API.
   * @param version         A semantic version number of the API.
   * @param description     A longer description of the API. Should be different from the title.  GitHub Flavored Markdown is allowed.
   * @param termsOfService  The terms of service for the API.
   * @param contact
   * @param license
   */
  case class Info(
    @JsonProperty(required = true) title: String,
    @JsonProperty(required = true) version: String,
    description:          String,
    termsOfService:       String,
    contact:              Contact,
    license:              License
  ) extends VendorExtensions with API

  /**
   * Contact information for the owners of the API.
   *
   * @param name         The identifying name of the contact person/organization.
   * @param url          The URL pointing to the contact information.
   * @param email        The email address of the contact person/organization.
   */
  case class Contact(
    name:                 String,
    url:                  URI,
    email:                Email
  ) extends VendorExtensions with API

  /**
   *
   * @param name          The name of the license type. It's encouraged to use an OSI compatible license.
   * @param url           The URL pointing to the license.
   */
  case class License(
    name:                 String,
    url:                  URI
  ) extends API

  /**
   *
   * @param get
   * @param put
   * @param post
   * @param delete
   * @param options
   * @param head
   * @param patch
   * @param parameters      The parameters needed to send a valid API call.
   * @param $ref
   */
  case class PathItem(
    get:                  Operation,
    put:                  Operation,
    post:                 Operation,
    delete:               Operation,
    options:              Operation,
    head:                 Operation,
    patch:                Operation,
    parameters:           ParametersList,
    @JsonProperty("$ref") $ref: Ref
  ) extends VendorExtensions with API


  sealed trait ParametersListItem

  case class JsonReference(
    @JsonProperty(value = "$ref", required = true) $ref: Ref
  ) extends ParametersListItem with ResponseValue

  sealed trait Parameter extends ParametersListItem

  abstract class NonBodyParameter(
    // TODO required: name, in, type
  ) extends Parameter

  /**
   *
   * @param name          The name of the parameter.
   * @param in            Determines the location of the parameter.
   * @param schema
   * @param description   A brief description of the parameter. This could contain examples of use.  GitHub Flavored Markdown is allowed.
   * @param required      Determines whether or not this parameter is required or optional.
   */
  case class BodyParameter(
    @JsonProperty(required = true) name: String,
    @JsonProperty(required = true) in: String, // TODO enum: Body
    @JsonProperty(required = true) schema: Schema,
    description: String,
    required: Boolean = false
  ) extends Parameter with VendorExtensions

  /**
   *
   * @param required          Determines whether or not this parameter is required or optional.
   * @param name              The name of the parameter.
   * @param in                Determines the location of the parameter.
   * @param description       A brief description of the parameter. This could contain examples of use.  GitHub Flavored Markdown is allowed.
   * @param `type`
   * @param format
   * @param items
   * @param collectionFormat
   * @param default
   * @param maximum
   * @param exclusiveMaximum
   * @param minimum
   * @param exclusiveMinimum
   * @param maxLength
   * @param minLength
   * @param pattern
   * @param maxItems
   * @param minItems
   * @param uniqueItems
   * @param enum
   * @param multipleOf
   */
  case class PathParameter(
    @JsonProperty(required = true) required: Boolean = true, // TODO enum, can only be true
    name:                   String,
    in:                     String, // TODO enum: Body
    description:            String,
    `type`:                 String, // TODO "enum": [  "string", "number",  "boolean",  "integer", "array" ]
    format:                 String,
    items:                  PrimitivesItems,
    collectionFormat:       CollectionFormat.Value,
    default:                Default[_],
    maximum:                Maximum,
    exclusiveMaximum:       ExclusiveMaximum,
    minimum:                Minimum,
    exclusiveMinimum:       ExclusiveMinimum,
    maxLength:              MaxLength,
    minLength:              MinLength,
    pattern:                Pattern,
    maxItems:               MaxItems,
    minItems:               MinItems,
    uniqueItems:            UniqueItems,
    enum:                   Enum[_],
    multipleOf:             MultipleOf
  ) extends NonBodyParameter with VendorExtensions

  /**
   *
   * @param required          Determines whether or not this parameter is required or optional.
   * @param name              The name of the parameter.
   * @param in                Determines the location of the parameter.
   * @param description       A brief description of the parameter. This could contain examples of use.  GitHub Flavored Markdown is allowed.
   * @param `type`
   * @param format
   * @param items
   * @param collectionFormat
   * @param default
   * @param maximum
   * @param exclusiveMaximum
   * @param minimum
   * @param exclusiveMinimum
   * @param maxLength
   * @param minLength
   * @param pattern
   * @param maxItems
   * @param minItems
   * @param uniqueItems
   * @param enum
   * @param multipleOf
   * @param allowEmptyValue allows sending a parameter by name only or with an empty value.
   */
  case class FormDataParameter(
    required:               Boolean = false,
    name:                   String,
    in:                     String, // TODO enum: formData
    description:            String,
    `type`:                 String, // TODO "enum": [  "string", "number",  "boolean",  "integer", "array" ] + "file" - for form
    format:                 String,
    items:                  PrimitivesItems,
    collectionFormat:       CollectionFormat.Value,
    default:                Default[_],
    maximum:                Maximum,
    exclusiveMaximum:       ExclusiveMaximum,
    minimum:                Minimum,
    exclusiveMinimum:       ExclusiveMinimum,
    maxLength:              MaxLength,
    minLength:              MinLength,
    pattern:                Pattern,
    maxItems:               MaxItems,
    minItems:               MinItems,
    uniqueItems:            UniqueItems,
    enum:                   Enum[_],
    multipleOf:             MultipleOf,
    allowEmptyValue:        Boolean = false // unique for form
  ) extends NonBodyParameter with VendorExtensions

  /**
   *
   * @param required          Determines whether or not this parameter is required or optional.
   * @param name              The name of the parameter.
   * @param in                Determines the location of the parameter.
   * @param description       A brief description of the parameter. This could contain examples of use.  GitHub Flavored Markdown is allowed.
   * @param `type`
   * @param format
   * @param items
   * @param collectionFormat
   * @param default
   * @param maximum
   * @param exclusiveMaximum
   * @param minimum
   * @param exclusiveMinimum
   * @param maxLength
   * @param minLength
   * @param pattern
   * @param maxItems
   * @param minItems
   * @param uniqueItems
   * @param enum
   * @param multipleOf
   * @param allowEmptyValue allows sending a parameter by name only or with an empty value.
   */
  case class QueryParameter(
    required:               Boolean = false,
    name:                   String,
    in:                     String, // TODO enum: query
    description:            String,
    `type`:                 String, // TODO "enum": [  "string", "number",  "boolean",  "integer", "array" ]
    format:                 String,
    items:                  PrimitivesItems,
    collectionFormat:       CollectionFormatWithMulti.Value,
    default:                Default[_],
    maximum:                Maximum,
    exclusiveMaximum:       ExclusiveMaximum,
    minimum:                Minimum,
    exclusiveMinimum:       ExclusiveMinimum,
    maxLength:              MaxLength,
    minLength:              MinLength,
    pattern:                Pattern,
    maxItems:               MaxItems,
    minItems:               MinItems,
    uniqueItems:            UniqueItems,
    enum:                   Enum[_],
    multipleOf:             MultipleOf,
    allowEmptyValue:        Boolean = false // unique for query and form
  ) extends NonBodyParameter with VendorExtensions

  /**
   *
   * @param required          Determines whether or not this parameter is required or optional.
   * @param name              The name of the parameter.
   * @param in                Determines the location of the parameter.
   * @param description       A brief description of the parameter. This could contain examples of use.  GitHub Flavored Markdown is allowed.
   * @param `type`
   * @param format
   * @param items
   * @param collectionFormat
   * @param default
   * @param maximum
   * @param exclusiveMaximum
   * @param minimum
   * @param exclusiveMinimum
   * @param maxLength
   * @param minLength
   * @param pattern
   * @param maxItems
   * @param minItems
   * @param uniqueItems
   * @param enum
   * @param multipleOf
   */
  case class HeaderParameter(
    required:               Boolean = false,
    name:                   String,
    in:                     String, // TODO enum: header
    description:            String,
    `type`:                 String, // TODO "enum": [  "string", "number",  "boolean",  "integer", "array" ]
    format:                 String,
    items:                  PrimitivesItems,
    collectionFormat:       CollectionFormat.Value,
    default:                Default[_],
    maximum:                Maximum,
    exclusiveMaximum:       ExclusiveMaximum,
    minimum:                Minimum,
    exclusiveMinimum:       ExclusiveMinimum,
    maxLength:              MaxLength,
    minLength:              MinLength,
    pattern:                Pattern,
    maxItems:               MaxItems,
    minItems:               MinItems,
    uniqueItems:            UniqueItems,
    enum:                   Enum[_],
    multipleOf:             MultipleOf
  ) extends NonBodyParameter with VendorExtensions


  sealed trait ResponseValue

  case class Response(
    @JsonProperty(required = true) description: String,
    schema: SchemaOrFileSchema,
    headers: Headers,
    examples: Examples
  ) extends ResponseValue with VendorExtensions


  sealed trait SchemaOrFileSchema


  /**
   *
   * @param responses     Response objects names can either be any valid HTTP status code or 'default'.
   * @param tags
   * @param summary       A brief summary of the operation.
   * @param description   A longer description of the operation, GitHub Flavored Markdown is allowed.
   * @param externalDocs
   * @param operationId   A unique identifier of the operation.
   * @param consumes      A list of MIME types the API can consume.
   * @param produces      A list of MIME types the API can produce.
   * @param parameters
   * @param schemes
   * @param deprecated
   * @param security
   */
  case class Operation(
    @JsonProperty(required = true) responses: Responses,
    tags:                 SimpleTags,
    summary:              String,
    description:          String,
    externalDocs:         ExternalDocs,
    operationId:          String,
    consumes:             MediaTypeList,
    produces:             MediaTypeList,
    parameters:           ParametersList,
    @JsonScalaEnumeration(classOf[SchemeType]) schemes: SchemesList,
    deprecated:           Boolean = false,
    security:             Security
  ) extends VendorExtensions with API


  /**
   *
   * @param required          Determines whether or not this parameter is required or optional.
   * @param name              The name of the parameter.
   * @param in                Determines the location of the parameter.
   * @param description       A brief description of the parameter. This could contain examples of use.  GitHub Flavored Markdown is allowed.
   * @param `type`
   * @param format
   * @param items
   * @param collectionFormat
   * @param default
   * @param maximum
   * @param exclusiveMaximum
   * @param minimum
   * @param exclusiveMinimum
   * @param maxLength
   * @param minLength
   * @param pattern
   * @param maxItems
   * @param minItems
   * @param uniqueItems
   * @param enum
   * @param multipleOf
   */
  //noinspection ScalaDocUnknownParameter
  case class Header(
    @JsonProperty(required = true) `type`: String, // TODO "enum": [  "string", "number",  "boolean",  "integer", "array" ]

    description:            String,

    format:                 String,
    items:                  PrimitivesItems,
    collectionFormat:       CollectionFormat.Value,
    default:                Default[_],
    maximum:                Maximum,
    exclusiveMaximum:       ExclusiveMaximum,
    minimum:                Minimum,
    exclusiveMinimum:       ExclusiveMinimum,
    maxLength:              MaxLength,
    minLength:              MinLength,
    pattern:                Pattern,
    maxItems:               MaxItems,
    minItems:               MinItems,
    uniqueItems:            UniqueItems,
    enum:                   Enum[_],
    multipleOf:             MultipleOf
  ) extends VendorExtensions

  /**
   * A deterministic version of a JSON Schema object.
   *
   * @param `type`
   * @param format
   * @param title
   * @param description
   * @param default
   * @param required
   * @param readOnly
   * @param externalDocs
   * @param example
   */
  case class FileSchema(
    @JsonProperty(required = true) `type`: String, // TODO enum: file
    format:                 String,
    title:                  Title,
    description:            Description,
    default:                Default[_],
    required:               StringArray,
    readOnly:               Boolean = false,
    externalDocs:           ExternalDocs,
    example:                Example[_]
  ) extends VendorExtensions with SchemaOrFileSchema


  class Schema(
    @JsonProperty("$ref") $ref: String, // WTF this is String in the spec!!!
    format:                 String,
    title:                  Title,
    description:            Description,
    default:                Default[_],
    maximum:                Maximum,
    exclusiveMaximum:       ExclusiveMaximum,
    minimum:                Minimum,
    exclusiveMinimum:       ExclusiveMinimum,
    maxLength:              MaxLength,
    minLength:              MinLength,
    pattern:                Pattern,
    maxItems:               MaxItems,
    minItems:               MinItems,
    uniqueItems:            UniqueItems,
    enum:                   Enum[_],
    multipleOf:             MultipleOf,
    maxProperties:          MaxProperties,
    minProperties:          MinProperties,
    required:               StringArray,
    additionalProperties:   SchemaOrBoolean,
    @JsonProperty(required = true) `type`: JsonSchemaType,
    properties:             SchemaProperties,
    allOf:                  SchemaArray,
    items:                  SchemaOrSchemaArray,
    discriminator:          String,
    xml:                    Xml,
    readOnly:               Boolean = false,
    externalDocs:           ExternalDocs,
    example:                Example[_]
  ) extends VendorExtensions with SchemaOrFileSchema

  /**
   *
   * @param `type`
   * @param format
   * @param items
   * @param collectionFormat
   * @param default
   * @param maximum
   * @param exclusiveMaximum
   * @param minimum
   * @param exclusiveMinimum
   * @param maxLength
   * @param minLength
   * @param pattern
   * @param maxItems
   * @param minItems
   * @param uniqueItems
   * @param enum
   * @param multipleOf
   */
  case class PrimitivesItems(
    @JsonProperty(required = true) `type`: String, // TODO "enum": [  "string", "number",  "boolean",  "integer", "array" ]
    format:                 String,
    items:                  PrimitivesItems,
    collectionFormat:       CollectionFormat.Value,
    default:                Default[_],
    maximum:                Maximum,
    exclusiveMaximum:       ExclusiveMaximum,
    minimum:                Minimum,
    exclusiveMinimum:       ExclusiveMinimum,
    maxLength:              MaxLength,
    minLength:              MinLength,
    pattern:                Pattern,
    maxItems:               MaxItems,
    minItems:               MinItems,
    uniqueItems:            UniqueItems,
    enum:                   Enum[_],
    multipleOf:             MultipleOf
  ) extends VendorExtensions

  /**
   *
   * @param name
   * @param namespace
   * @param prefix
   * @param attribute
   * @param wrapped
   */
  case class Xml(
    name:                   String,
    namespace:              String,
    prefix:                 String,
    attribute:              Boolean = false,
    wrapped:                Boolean = false
  ) extends API with VendorExtensions

  /********* security definitions *********/
  sealed trait SecurityDefinition extends VendorExtensions

  case class BasicAuthenticationSecurity(
    @JsonProperty(required = true) `type`: String, // "enum": basic
    description: Description
  ) extends SecurityDefinition

  case class ApiKeySecurity(
    @JsonProperty(required = true) `type`: String,  // "enum": apiKey
    @JsonProperty(required = true) name: String,
    @JsonProperty(required = true) in: String,      // "enum": header, query
    description: Description
  ) extends SecurityDefinition

  case class Oauth2ImplicitSecurity(
    @JsonProperty(required = true) `type`: String,  // "enum": oauth2
    @JsonProperty(required = true) flow: String,    // "enum": implicit
    @JsonProperty(required = true) authorizationUrl: URI,
    scopes: Oauth2Scopes,
    description: Description
  ) extends SecurityDefinition

  case class Oauth2PasswordSecurity(
    @JsonProperty(required = true) `type`: String,  // "enum": oauth2
    @JsonProperty(required = true) flow: String,    // "enum": password
    @JsonProperty(required = true) tokenUrl: URI,
    scopes: Oauth2Scopes,
    description: Description
  ) extends SecurityDefinition

  case class Oauth2ApplicationSecurity(
    @JsonProperty(required = true) `type`: String,  // "enum": oauth2
    @JsonProperty(required = true) flow: String,    // "enum": application
    @JsonProperty(required = true) tokenUrl: URI,
    scopes: Oauth2Scopes,
    description: Description
  ) extends SecurityDefinition

  case class Oauth2AccessCodeSecurity(
    @JsonProperty(required = true) `type`: String,  // TODO "enum": oauth2
    @JsonProperty(required = true) flow: String,    // TODO "enum": accessCode
    @JsonProperty(required = true) authorizationUrl: URI,
    @JsonProperty(required = true) tokenUrl: URI,
    scopes: Oauth2Scopes,
    description: Description
  ) extends SecurityDefinition


  trait VendorExtensions { self =>
    private[this] val extensions = new mutable.HashMap[String, String]
    private[this] val errorMappings = new mutable.HashMap[String, Seq[Class[Exception]]]

    @JsonAnySetter
    def handleUnknown(key: String, value: Any) {
      value match {
        case str: String if key.startsWith("x-") =>
          extensions += key -> str
        case mapping: Map[_, _] =>
          import scala.util.control.Exception._
          handling(classOf[ClassNotFoundException]) by { e =>
            throw new IllegalArgumentException(s"Could not find exception class $e for error code")
          } apply {
            val errors = mapping filter {_._2.isInstanceOf[Array[_]] } map { case (k, l) =>
              k.toString -> l.asInstanceOf[Array[_]].map { e =>
                Class.forName(e.toString).asInstanceOf[Class[Exception]]
              }.toSeq
            }
            errorMappings ++= errors
          }
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
    lazy val vendorErrorMappings = errorMappings.toMap
  }

  type Many[T]                = List[T]
  type ManyUnique[T]          = Set[T]
  type AdditionalProperties[T]= Map[String, T]
  type Default[T]             = T
  type Example[T]             = T

  type Host                   = String  // "pattern": "^[^{}/ :\\\\]+(?::\\d+)?$", TODO
  type BasePath               = String  // "pattern": "^/", TODO
  type Format                 = String
  type SimpleTag              = String
  type URI                    = String  // TODO check pattern
  type Email                  = String  // TODO check pattern
  type Ref                    = String
  type MimeType               = String // The MIME type of the HTTP message.
  type Title                  = String
  type Description            = String

  type SchemesList            = ManyUnique[Scheme.Value]
  type MediaTypeList          = ManyUnique[MimeType]
  type Tags                   = ManyUnique[Tag]
  type Security               = ManyUnique[SecurityRequirement]
  type ParametersList         = ManyUnique[ParametersListItem]
  type SimpleTags             = ManyUnique[SimpleTag]
  type StringArray            = Many[String]
  type SchemaArray            = Many[Schema] // TODO minItems: 1


  type ParameterDefinitions   = AdditionalProperties[Parameter]
  type ResponseDefinitions    = AdditionalProperties[Response]
  type Definitions            = AdditionalProperties[Schema]
  type SecurityRequirement    = AdditionalProperties[ManyUnique[String]]
  type SecurityDefinitions    = AdditionalProperties[SecurityDefinition]
  type Headers                = AdditionalProperties[Header]
  type Responses              = AdditionalProperties[ResponseValue] // TODO name pattern: "^([0-9]{3})$|^(default)$", minProperties: 1
  type Examples               = AdditionalProperties[Any]
  type SchemaProperties       = AdditionalProperties[Schema]

  // -------------------------- Validations --------------------------

  // validations for Int, Long, Double, Float
  type MultipleOf             = Double  // TODO must be > 0
  type Maximum                = Double  // TODO must be present if ExclusiveMaximum is present
  type ExclusiveMaximum       = Boolean
  type Minimum                = Double  // TODO must be present if ExclusiveMinimum is present
  type ExclusiveMinimum       = Boolean

  // validations for String
  type MaxLength              = Int // length <= // TODO must be >= 0
  type MinLength              = Int // length >= // TODO must be >= 0
  type Pattern                = String // TODO must be valid regexp

  // validations for arrays
  type MaxItems               = Int // TODO must be >= 0
  type MinItems               = Int // TODO must be >= 0
  type UniqueItems            = Boolean
  type Enum[T]                = ManyUnique[T]

  // validations for objects
  type MaxProperties          = Int // TODO must be >=0
  type MinProperties          = Int // TODO must be >=0

  // validations WTF
  type JsonSchemaType         = String // TODO The value of this keyword MUST be either a string or an array. If it is an array, elements of the array MUST be strings and MUST be unique. String values MUST be one of the seven primitive types defined by the core specification.

  type Paths                  = AdditionalProperties[PathItem]  // TODO add VendorExtensions and pathItem for props starting with "/"
  type Oauth2Scopes           = AdditionalProperties[String]


  // TODO how to implement these ???
  type SchemaOrBoolean      = Any
  type SchemaOrSchemaArray  = Any
}
