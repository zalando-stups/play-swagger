package de.zalando.swagger

import com.fasterxml.jackson.annotation.{JsonProperty, JsonAnySetter}
import com.fasterxml.jackson.core.`type`.TypeReference
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException
import com.fasterxml.jackson.module.scala.JsonScalaEnumeration

import scala.language.implicitConversions
import scala.collection.mutable

/**
 * @since 13.07.2015
 */
object model {

  sealed trait API

  // format: OFF

  trait TypeInfo extends API {
    def `type`:       PrimitiveType.Value
    def $ref:         String
    def format:       String
  }

  // Header, Items, Parameter, Schema

  class CommonProperties (
    val format:           String,
    val default:          String,
    val multipleOf:       Int,
    val maximum:          Double,
    val exclusiveMaximum: Boolean,
    val minimum:          Double,
    val exclusiveMinimum: Boolean,
    val maxLength:        Int,
    val minLength:        Int,
    val pattern:          String,
    val maxItems:         Long,
    val minItems:         Long,
    val uniqueItems:      Boolean,
    val enum:             Enum,
    val items:            Items,
    val `type`:           PrimitiveType.Value
  )

  case class Property(
    @JsonScalaEnumeration(classOf[PrimitiveTypeReference]) `type`: PrimitiveType.Value,
    @JsonProperty("$ref") $ref: String,
    description:          String,
    format:               String,
    items:                Items,
    example:              String,
    properties:           Properties
  ) extends TypeInfo with VendorExtensions

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
    ) extends VendorExtensions with API

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
    parameters:           Many[ParameterOrReference],
    @JsonProperty("$ref") $ref: Ref
  ) extends VendorExtensions with API

  case class Operation(
    tags:                 SimpleTags,
    summary:              String,
    description:          String,
    externalDocs:         ExternalDocumentation,
    operationId:          String,
    consumes:             Consumes,
    produces:             Produces,
    parameters:           ParametersOrReferences,
    responses:            Responses,  // TODO should be OperationResponses
    deprecated:           Boolean,
    security:             SecurityRequirements,
    @JsonScalaEnumeration(classOf[SchemeType]) schemes: Schemes
  ) extends VendorExtensions with API

  sealed trait ParameterOrReference extends API

  class Parameter(
    override val format:                String,
    override val default:               String,
    override val multipleOf:            Int,
    override val maximum:               Double,
    override val exclusiveMaximum:      Boolean,
    override val minimum:               Double,
    override val exclusiveMinimum:      Boolean,
    override val maxLength:             Int,
    override val minLength:             Int,
    override val pattern:               String,
    override val maxItems:              Long,
    override val minItems:              Long,
    override val uniqueItems:           Boolean,
    override val enum:                  Enum,
    override val items:                 Items,
    @JsonScalaEnumeration(classOf[PrimitiveTypeReference]) override val `type`: PrimitiveType.Value,

    val name:                           String,
    @JsonScalaEnumeration(classOf[ParameterInTypeReference]) val in: ParameterIn.Value,
    val required:                       Boolean,
    val schema:                         Schema,   // if in is "body"

    val collectionFormat:               String,
    val description:                    String,
    val allowEmptyValue:                Boolean  // if in is any other value than body
  ) extends CommonProperties(
      format, default, multipleOf, maximum, exclusiveMaximum, minimum, exclusiveMinimum,
      maxLength, minLength, pattern, maxItems, minItems, uniqueItems, enum, items, `type`
    ) with VendorExtensions with ParameterOrReference with API {
    lazy val bodyParameter      = in == ParameterIn.BODY
    lazy val queryParameter     = in == ParameterIn.QUERY
    lazy val pathParameter      = in == ParameterIn.PATH
    lazy val headerParameter    = in == ParameterIn.HEADER
    lazy val formDataParameter  = in == ParameterIn.FORM_DATA
  }

  case class ParameterReference(
    @JsonProperty("$ref") $ref: Ref
  ) extends ParameterOrReference

  implicit def parameterOrReference2Parameter(pr: ParameterOrReference)(implicit model: SwaggerModel): Parameter =
    pr match {
      case p: Parameter => p
      case pr: ParameterReference => model.parameters(pr.$ref)
    }


  case class Response(
    description:                    String,
    schema:                         Schema,
    headers:                        Headers,
    examples:                       Examples
  ) extends VendorExtensions with API

  class Schema(
    override val format:            String,
    override val default:           String,
    override val multipleOf:        Int,
    override val maximum:           Double,
    override val exclusiveMaximum:  Boolean,
    override val minimum:           Double,
    override val exclusiveMinimum:  Boolean,
    override val maxLength:         Int,
    override val minLength:         Int,
    override val pattern:           String,
    override val maxItems:          Long,
    override val minItems:          Long,
    override val uniqueItems:       Boolean,
    override val enum:              Enum,
    override val items:             Items,
    @JsonScalaEnumeration(classOf[PrimitiveTypeReference]) override val `type`: PrimitiveType.Value,

    val required:                       Many[String],
    val example:                        Any,
    val description:                    String,
    val title:                          String,
    @JsonProperty("$ref") val $ref:     String,
    val properties:                     Properties,
    val additionalProperties:           Property,
    val discriminator:                  String,   // polymorphism support
    val readOnly:                       Boolean,  // properties only
    val xml:                            Xml,      // properties only ?
    val externalDocs:                   ExternalDocumentation,
    val allOf:                          Many[Schema],
    val maxProperties:                  Int, // TODO no description in swagger spec, check JSON Schema
    val minProperties:                  Int // TODO no description in swagger spec, check JSON Schema
  ) extends CommonProperties(
      format, default, multipleOf, maximum, exclusiveMaximum, minimum, exclusiveMinimum,
    maxLength, minLength, pattern, maxItems, minItems, uniqueItems, enum, items, `type`
  ) with TypeInfo {
    val isRef = $ref != null && $ref.trim.nonEmpty

    override def toString =
      s"""Schema($isRef, $format, $default, $multipleOf, $maximum, $exclusiveMaximum, $minimum,
         |$exclusiveMinimum, $maxLength, $minLength, $pattern, $maxItems, $minItems, $uniqueItems,
         |$enum, $items, ${`type`}, $required, $example, $description, $title, ${$ref}, $properties,
         |$additionalProperties, $discriminator, $readOnly, $xml, $externalDocs, $allOf, $maxProperties,
         |$minProperties)
         |""".stripMargin

  }

  case class Header(
    override val format:            String,
    override val default:           String,
    override val multipleOf:        Int,
    override val maximum:           Double,
    override val exclusiveMaximum:  Boolean,
    override val minimum:           Double,
    override val exclusiveMinimum:  Boolean,
    override val maxLength:         Int,
    override val minLength:         Int,
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
    val FILE    = Value("file")
  }

  private[swagger] class ParameterInTypeReference extends TypeReference[ParameterIn.type]

  case object ParameterIn extends Enumeration {
    type ParameterIn = Value
    val QUERY       = Value("query")
    val HEADER      = Value("header")
    val PATH        = Value("path")
    val FORM_DATA   = Value("formData")
    val BODY        = Value("body")
  }


  case class Items(
    override val format:           String,
    override val default:          String,
    override val multipleOf:       Int,
    override val maximum:          Double,
    override val exclusiveMaximum: Boolean,
    override val minimum:          Double,
    override val exclusiveMinimum: Boolean,
    override val maxLength:        Int,
    override val minLength:        Int,
    override val pattern:          String,
    override val maxItems:         Long,
    override val minItems:         Long,
    override val uniqueItems:      Boolean,
    override val enum:             Enum,
    override val items:            Items,
    @JsonScalaEnumeration(classOf[PrimitiveTypeReference]) override val `type`: PrimitiveType.Value,

    collectionFormat:              String,
    @JsonProperty("$ref") $ref:    String,
    allOf:                         Many[ParameterOrReference]
  ) extends CommonProperties(
    format, default, multipleOf, maximum, exclusiveMaximum, minimum, exclusiveMinimum,
    maxLength, minLength, pattern, maxItems, minItems, uniqueItems, enum, items, `type`
  ) with TypeInfo

  case class SecurityDefinition(
    `type`: String,
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

  type Version                = String
  type Host                   = String
  type BasePath               = String
  type Consume                = String
  type Produce                = String
  type Format                 = String
  type SimpleTag              = String
  type URL                    = String
  type Ref                    = String

  type Many[T]                = List[T]

  type Consumes               = Many[Consume]
  type Produces               = Many[Produce]
  type Schemes                = Many[Scheme.Value]
  type Tags                   = Many[Tag]
  type SimpleTags             = Many[SimpleTag]
  type Examples               = Many[Example]
  type ExternalDocs           = Many[ExternalDocumentation]
  type Parameters             = Many[Parameter]
  type ParametersOrReferences = Many[ParameterOrReference]
  type SecurityRequirements   = Many[SecurityRequirement]
  type Enum                   = Many[String]
  type RequiredFields         = Many[String]

  type Responses              = Map[String, Response]
  type Headers                = Map[String, Header]
  type Paths                  = Map[String, Path]  // TODO add VendorExtensions
  type SecurityDefinitions    = Map[String, strictModel.SecurityDefinition]
  type SecurityRequirement    = Map[String, List[String]]
  type Definitions            = Map[String, Schema]
  type Example                = Map[String, AnyRef]
  type Properties             = Map[String, Schema]
  type Scopes                 = Map[String, String]
  // format: ON
}
