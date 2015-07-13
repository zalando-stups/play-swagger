package de.zalando.swagger

/**
 * @since 13.07.2015
 */
object model {

  case class Swagger(
                      info: Info,
                      host: Host, basePath: BasePath,
                      tags: Tags, schemes: Schemes,
                      consumes: Consumes, produces: Produces,
                      securityRequirements: SecurityRequirements, securityDefinitions: SecurityDefinitions,
                      paths: Paths, definitions: Definitions, parameters: Parameters, externalDocs: ExternalDocs
                      ) {
    val swagger = "2.0"
  }

  case class Contact(name: String, url: String, email: String)

  case class License(name: String, url: String)

  case class Info(title: String, description: Option[String], termsOfService: Option[String],
                  contact: Contact, license: License, version: Option[String])

  case class Tag(name: String, description: String, externalDocs: ExternalDocs)

  case class ExternalDocumentation(description: String, url: String)

  case class Parameter(name: String, in: String, description: String, required: Boolean)

  case class SchemaObject(`$ref`: String, format: Format,
                          title: String,
                          description: String,
                          default: String,
                          multipleOf: String,
                          maximum: String,
                          exclusiveMaximum: String,
                          minimum: String,
                          exclusiveMinimum: String,
                          maxLength: String,
                          minLength: String,
                          pattern: String,
                          maxItems: String,
                          minItems: String,
                          uniqueItems: String,
                          maxProperties: String,
                          minProperties: String,
                          required: Boolean,
                          enum: String,
                          `type`: String
                           )

  case class Response(description: String, schema: SchemaObject, headers: Headers, examples: Examples)

  case class Item(
                   `type`: String,
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
                   enum: String,
                   multipleOf: Int)

  case class Header(description: String,
                    `type`: String,
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
                    enum: AnyRef,
                    multipleOf: Int
                     )

  case class Operation(tags: Tags,
                       summary: String, description: String, externalDocs: ExternalDocs, operationId: String,
                       consumes: Consumes, produces: Produces, parameters: Parameters, responses: Responses,
                       schemes: Schemes,
                       deprecated: Boolean, security: SecurityRequirement

                        )


  case class PathItem(
                       `$ref`: String,
                       get: Operation,
                       put: Operation,
                       post: Operation,
                       delete: Operation,
                       options: Operation,
                       head: Operation,
                       patch: Operation,
                       parameters: Parameters
                       )


  // format: OFF
  type Host       = String
  type BasePath   = String
  type Consume    = String
  type Produce    = String
  type Scheme     = String
  type Format     = String

  type Many[T]    = Iterable[T]

  type Consumes             = Many[Consume]
  type Produces             = Many[Produce]
  type Schemes              = Many[Scheme]
  type Tags                 = Many[Tag]
  type Examples             = Many[Example]
  type Items                = Many[Item]
  type SecurityRequirements = Many[SecurityRequirement]
  type SecurityDefinitions  = Many[SecurityDefinition]
  type ExternalDocs         = Many[ExternalDocumentation]
  type Parameters           = Many[Parameter]

  type Responses            = Map[Int, Response]
  type Headers              = Map[String, Header]
  type Paths                = Map[String, PathItem]
  type SecurityRequirement  = Map[String, String]
  type SecurityDefinition   = Map[String, SecurityRequirement]
  type Definitions          = Map[String, SchemaObject]
  type Example              = Map[String, AnyRef]
  // format: ON


}
