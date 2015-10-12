package de.zalando.swagger

import com.fasterxml.jackson.annotation.{JsonProperty, JsonAnySetter}
import com.fasterxml.jackson.core.`type`.TypeReference
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException
import com.fasterxml.jackson.module.scala.JsonScalaEnumeration

import scala.collection.mutable
import scala.util.Try

/**
 * @author  slasch
 * @since   09.10.2015.
 */
object strictModel {

  import NumberValidation._
  import StringValidation._
  import ArrayValidation._
  import ObjectValidation._

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

  private[swagger] class CollectionFormatReference extends TypeReference[CollectionFormat.type]
  case object CollectionFormat extends Enumeration {
    type CollectionFormat = Value
    val CSV     = Value("csv")
    val SSV     = Value("ssv")
    val TSV     = Value("tsv")
    val PIPES   = Value("pipes")
  }

  private[swagger] class CollectionFormatWithMultiReference extends TypeReference[CollectionFormatWithMulti.type]
  case object CollectionFormatWithMulti extends Enumeration {
    type CollectionFormatWithMulti = Value
    val CSV     = Value("csv")
    val SSV     = Value("ssv")
    val TSV     = Value("tsv")
    val PIPES   = Value("pipes")
    val MULTI   = Value("multi")
  }

  private[swagger] class PrimitiveTypeReference extends TypeReference[PrimitiveType.type]
  case object PrimitiveType extends Enumeration {
    type PrimitiveType = Value
    val ARRAY   = Value("array")
    val BOOLEAN = Value("boolean")
    val INTEGER = Value("integer")
    val NUMBER  = Value("number")
    val STRING  = Value("string")
  }
  private[swagger] class ParameterTypeReference extends TypeReference[ParameterType.type]
  case object ParameterType extends Enumeration {
    type ParameterType = Value
    val ARRAY   = Value("array")
    val BOOLEAN = Value("boolean")
    val INTEGER = Value("integer")
    val NUMBER  = Value("number")
    val STRING  = Value("string")
    val FILE    = Value("file") // only for formData
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
  ) extends ParametersListItem with ResponseValue with SchemaOrFileSchema

  sealed trait Parameter[T] extends ParametersListItem

  abstract class NonBodyParameter[T] extends Parameter[T] {
    @JsonProperty(required = true) def name:    String
    @JsonProperty(required = true) def in:      String
    @JsonScalaEnumeration(classOf[ParameterTypeReference]) @JsonProperty(required = true) def `type`: ParameterType.Value
  }

  /**
   *
   * @param name          The name of the parameter.
   * @param in            Determines the location of the parameter.
   * @param schema
   * @param description   A brief description of the parameter. This could contain examples of use.  GitHub Flavored Markdown is allowed.
   * @param required      Determines whether or not this parameter is required or optional.
   */
  case class BodyParameter[T](
    @JsonProperty(required = true) name:    String,
    @JsonProperty(required = true) in:      String,
    @JsonProperty(required = true) schema:  SchemaOrReference,
    description:                            String,
    required:                               Boolean = false
  ) extends Parameter[T] with VendorExtensions

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
  case class PathParameter[T](
    @JsonProperty(required = true) required: Boolean = true,
    name:                   String,
    in:                     String,
    description:            String,
    @JsonScalaEnumeration(classOf[ParameterTypeReference]) `type`: ParameterType.Value,
    format:                 String,
    items:                  PrimitivesItems[T],
    @JsonScalaEnumeration(classOf[CollectionFormatReference]) collectionFormat: CollectionFormat.Value,
    default:                Default[T],
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
    enum:                   Enum[T],
    multipleOf:             MultipleOf
  ) extends NonBodyParameter[T] with VendorExtensions with AllValidations[T]

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
  case class FormDataParameter[T](
    required:               Boolean = false,
    name:                   String,
    in:                     String,
    description:            String,
    @JsonScalaEnumeration(classOf[ParameterTypeReference]) `type`: ParameterType.Value,
    format:                 String,
    items:                  PrimitivesItems[T],
    @JsonScalaEnumeration(classOf[CollectionFormatReference]) collectionFormat: CollectionFormat.Value,
    default:                Default[T],
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
    enum:                   Enum[T],
    multipleOf:             MultipleOf,
    allowEmptyValue:        Boolean = false // unique for form
  ) extends NonBodyParameter[T] with VendorExtensions with AllValidations[T]

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
  case class QueryParameter[T](
    required:               Boolean = false,
    name:                   String,
    in:                     String,
    description:            String,
    @JsonScalaEnumeration(classOf[ParameterTypeReference]) `type`: ParameterType.Value,
    format:                 String,
    items:                  PrimitivesItems[T],
    @JsonScalaEnumeration(classOf[CollectionFormatWithMultiReference]) collectionFormat: CollectionFormatWithMulti.Value,
    default:                Default[T],
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
    enum:                   Enum[T],
    multipleOf:             MultipleOf,
    allowEmptyValue:        Boolean = false // unique for query and form
  ) extends NonBodyParameter[T] with VendorExtensions with AllValidations[T]

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
  case class HeaderParameter[T](
    required:               Boolean = false,
    name:                   String,
    in:                     String,
    description:            String,
    @JsonScalaEnumeration(classOf[ParameterTypeReference]) `type`: ParameterType.Value,
    format:                 String,
    items:                  PrimitivesItems[T],
    @JsonScalaEnumeration(classOf[CollectionFormatReference]) collectionFormat: CollectionFormat.Value,
    default:                Default[T],
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
    enum:                   Enum[T],
    multipleOf:             MultipleOf
  ) extends NonBodyParameter[T] with VendorExtensions with AllValidations[T]


  sealed trait ResponseValue

  case class Response[T](
    @JsonProperty(required = true) description: String,
    schema: SchemaOrFileSchema,
    headers: Headers,
    examples: Examples
  ) extends ResponseValue with VendorExtensions


  sealed trait SchemaOrReference

  sealed trait SchemaOrFileSchema extends SchemaOrReference


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
  case class Header[T](
    @JsonProperty(required = true) @JsonScalaEnumeration(classOf[PrimitiveTypeReference]) `type`: PrimitiveType.Value,
    description:            String,
    format:                 String,
    items:                  PrimitivesItems[T],
    @JsonScalaEnumeration(classOf[CollectionFormatReference]) collectionFormat: CollectionFormat.Value,
    default:                Default[T],
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
    enum:                   Enum[T],
    multipleOf:             MultipleOf
  ) extends VendorExtensions with AllValidations[T]

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
  case class FileSchema[T](
    @JsonProperty(required = true) `type`: String,
    format:                 String,
    title:                  Title,
    description:            Description,
    default:                Default[T],
    required:               StringArray,
    readOnly:               Boolean = false,
    externalDocs:           ExternalDocs,
    example:                Example[T]
  ) extends VendorExtensions with SchemaOrFileSchema


  class Schema[T](
    val format:                 String,
    val title:                  Title,
    val description:            Description,
    val default:                Default[T],
    val maximum:                Maximum,
    val exclusiveMaximum:       ExclusiveMaximum,
    val minimum:                Minimum,
    val exclusiveMinimum:       ExclusiveMinimum,
    val maxLength:              MaxLength,
    val minLength:              MinLength,
    val pattern:                Pattern,
    val maxItems:               MaxItems,
    val minItems:               MinItems,
    val uniqueItems:            UniqueItems,
    val enum:                   Enum[T],
    val multipleOf:             MultipleOf,
    val maxProperties:          MaxProperties,
    val minProperties:          MinProperties,
    val required:               StringArray,
    val additionalProperties:   SchemaOrBoolean,
    val `type`:                 JsonSchemaType,
    val properties:             SchemaProperties,
    val allOf:                  SchemaArray,
    val items:                  SchemaOrSchemaArray,
    val discriminator:          String,
    val xml:                    Xml,
    val readOnly:               Boolean = false,
    val externalDocs:           ExternalDocs,
    val example:                Example[T]
  ) extends VendorExtensions with SchemaOrFileSchema with AllValidations[T] with ObjectValidation

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
  case class PrimitivesItems[T](
    @JsonProperty(required = true) @JsonScalaEnumeration(classOf[PrimitiveTypeReference]) `type`: PrimitiveType.Value,
    format:                 String,
    items:                  PrimitivesItems[T],
    collectionFormat:       CollectionFormat.Value,
    default:                Default[T],
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
    enum:                   Enum[T],
    multipleOf:             MultipleOf
  ) extends VendorExtensions with AllValidations[T]

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
    @JsonProperty(required = true) `type`: String,  // "enum": oauth2
    @JsonProperty(required = true) flow: String,    // "enum": accessCode
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
  type URI                    = String // TODO check pattern
  type Email                  = String // TODO check pattern
  type Ref                    = String // TODO may be check pattern?
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
  type SchemaArray            = Many[SchemaOrReference] // TODO minItems: 1

  type ParameterDefinitions   = AdditionalProperties[Parameter[_]]
  type ResponseDefinitions    = AdditionalProperties[Response[_]]
  type Definitions            = AdditionalProperties[Schema[_]]
  type SecurityRequirement    = AdditionalProperties[ManyUnique[String]]
  type SecurityDefinitions    = AdditionalProperties[SecurityDefinition]
  type Headers                = AdditionalProperties[Header[_]]
  type Responses              = AdditionalProperties[Response[_]] // TODO name pattern: "^([0-9]{3})$|^(default)$", minProperties: 1
  type Examples               = AdditionalProperties[Any]
  type SchemaProperties       = AdditionalProperties[SchemaOrReference]
  type Paths                  = AdditionalProperties[PathItem]  // TODO add VendorExtensions and pathItem for props starting with "/"
  type Oauth2Scopes           = AdditionalProperties[String]

  // -------------------------- Validations --------------------------

  /**
   * validations for Int, Long, Double, Float
   */
  object NumberValidation {
    type MultipleOf             = Option[Double]
    type Maximum                = Option[Double]
    type ExclusiveMaximum       = Option[Boolean]
    type Minimum                = Option[Double]
    type ExclusiveMinimum       = Option[Boolean]
  }
  trait NumberValidation {
    import NumberValidation._
    def multipleOf:             MultipleOf
    def maximum:                Maximum
    def exclusiveMaximum:       ExclusiveMaximum
    def minimum:                Minimum
    def exclusiveMinimum:       ExclusiveMinimum
    require(multipleOf.forall(_ > 0))
    require(exclusiveMaximum.isEmpty || maximum.isDefined)
    require(exclusiveMinimum.isEmpty || minimum.isDefined)
  }
  object StringValidation {
    type MaxLength              = Option[Int] // length <=
    type MinLength              = Option[Int] // length >=
    type Pattern                = Option[String]
  }
  trait StringValidation {
    import StringValidation._
    def maxLength: MaxLength
    def minLength: MinLength
    def pattern: Pattern
    require(maxLength.forall(_>=0))
    require(minLength.forall(_>=0))
    require(pattern.forall(p => Try(p.r).isSuccess))
  }

  object ArrayValidation {
    type MaxItems               = Option[Int]
    type MinItems               = Option[Int]
    type UniqueItems            = Option[Boolean]
    type Enum[T]                = Option[ManyUnique[T]]
  }
  trait ArrayValidation[T] {
    import ArrayValidation._
    def maxItems:               MaxItems
    def minItems:               MinItems
    def uniqueItems:            UniqueItems
    def enum:                   Enum[T]
    require(maxItems.forall(_>=0))
    require(minItems.forall(_>=0))
  }
  object ObjectValidation {
    type MaxProperties          = Option[Int]
    type MinProperties          = Option[Int]
  }
  trait ObjectValidation {
    import ObjectValidation._
    def maxProperties:          MaxProperties
    def minProperties:          MinProperties
    require(maxProperties.forall(_>=0))
    require(minProperties.forall(_>=0))
  }
  trait AllValidations[T] extends NumberValidation with StringValidation with ArrayValidation[T]


  // Validations TODO

  // The value of this keyword MUST be either a string or an array.
  // If it is an array, elements of the array MUST be strings and MUST be unique.
  // String values MUST be one of the seven primitive types defined by the core specification.
  type JsonSchemaType         = String

  // how to implement these ???
  type SchemaOrBoolean      = Any
  type SchemaOrSchemaArray  = Any
}
