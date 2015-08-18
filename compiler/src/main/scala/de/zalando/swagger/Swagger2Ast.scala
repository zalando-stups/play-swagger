package de.zalando.swagger

import de.zalando.apifirst
import de.zalando.apifirst.Application.{ApiCall, Model}
import de.zalando.apifirst.Domain.Field
import de.zalando.apifirst.Path.InPathParameter
import de.zalando.apifirst.{Application, Domain, Http}
import de.zalando.swagger.model.PrimitiveType._
import de.zalando.swagger.model.{PrimitiveType, _}

import scala.language.{implicitConversions, postfixOps, reflectiveCalls}

/**
 * @since 17.07.2015
 */
object Swagger2Ast extends HandlerParser {

  implicit def convert(keyPrefix: String)(implicit swaggerModel: SwaggerModel): Model =
    Model(convertCalls(keyPrefix), convertDefinitions)

  def convertCalls(keyPrefix: String)(implicit swaggerModel: SwaggerModel) =
    swaggerModel.paths.flatMap(toCall(keyPrefix) _).toList

  def convertDefinitions(implicit swaggerModel: SwaggerModel) =
    Option(swaggerModel.definitions) map { d =>
      val definitions = d map toDefinition("/definitions/")
      definitions.toMap
    } getOrElse Map.empty[String, Domain.Type]

  import Http.string2verb

  def toDefinition(namespace: String)(definition: (String, model.Schema))(implicit swaggerModel: SwaggerModel): (String, Domain.Type) =
    (namespace + definition._1) -> SchemaConverter.schema2Type(definition._2, definition._1, namespace)

  def toCall(keyPrefix: String)(path: (String, model.Path))(implicit swaggerModel: SwaggerModel): Option[ApiCall] = {
    val call = operation(path) flatMap { case (operation: Operation, signature: String) =>
      for {
        verb <- string2verb(signature)
        pathParams = pathParameters(operation)
        queryParams = queryParameters(operation)
        allParams = pathParams ++ queryParams
        astPath = apifirst.Path.path2path(path._1, pathParams ++ queryParams)
        handlerText <- operation.vendorExtensions.get(s"$keyPrefix-handler")
        parseResult = parse(handlerText)
        handler <- if (parseResult.successful) Some(parseResult.get.copy(parameters = allParams)) else None
      } yield Some(ApiCall(verb, astPath, handler))
    case _ =>
      None
    }
    call.flatten
  }

  import model.parameterOrReference2Parameter

  private def pathParameters(operation: model.Operation)(implicit swaggerModel: SwaggerModel): Seq[Application.Parameter] =
    Option(operation.parameters).map(_.filter(_.pathParameter) map pathParameter).toSeq.flatten

  private def queryParameters(operation: model.Operation)(implicit swaggerModel: SwaggerModel): Seq[Application.Parameter] =
    Option(operation.parameters).map(_.filter(_.queryParameter) map queryParameter).toSeq.flatten

  private def pathParameter(p: model.ParameterOrReference)(implicit swaggerModel: SwaggerModel): Application.Parameter = {
    val fixed = None
    val default = None
    val typeName = SchemaConverter.swaggerType2Type(p)
    Application.Parameter(p.name, typeName, fixed, default, InPathParameter.constraint, InPathParameter.encode)
  }

  private def queryParameter(p: model.ParameterOrReference)(implicit swaggerModel: SwaggerModel): Application.Parameter = {
    val fixed = None // There is no way to define fixed parameters in swagger spec
    val default = if (p.required && p.default != null) Some(p.default) else None
    val typeName = SchemaConverter.swaggerType2Type(p)
    import Domain.paramOrRefInfo2TypeMeta
    val fullTypeName = if (p.required) typeName else Domain.Opt(Field(typeName.name, typeName), p)
    // TODO don't use InPathParameter in the next line
    Application.Parameter(p.name, fullTypeName, fixed, default, InPathParameter.constraint, InPathParameter.encode)
  }

  private def operation(path: (String, Path)) = {
    import scala.reflect.runtime.universe._

    val signatures = typeOf[Path].members.filter(_.typeSignature =:= typeOf[Operation]).iterator.toSeq.reverseIterator
    val ops = path._2.productIterator.zip(signatures.map(_.name.decoded)).filter(_._1 != null).toList
    ops.headOption
  }

}

object SchemaConverter {

  import Domain._

  def wrap[T <: Type](s: Schema, t: T): Type = t match {
    case td @ TypeDef(name, fields, extend, meta) =>
      val wrapped = fields map { f =>
        if (s.required != null && s.required.contains(f.name)) f
        else f.copy(kind = Domain.Opt(f, f.meta))
      }
      td.copy(fields = wrapped)
    case _ => t
  }

  def schema2Type(p: model.TypeInfo, name: String, namespace: String): Domain.Type = p match {
    case s: model.Schema if propsPartialFunction.isDefinedAt(p.`type`, p.format) =>
      wrap(s, propsPartialFunction(p.`type`, p.format)(p))

    case _ if propsPartialFunction.isDefinedAt(p.`type`, p.format) =>
      propsPartialFunction(p.`type`, p.format)(p)

    case s: model.Schema if s.additionalProperties != null =>
      val field = Field(name, schema2Type(s.additionalProperties, removeNamespace(namespace)(name), namespace))
      wrap(s, Domain.CatchAll(field, s))

    case s: model.Schema if s.allOf != null =>
      val (toExtend, types) = s.allOf.partition(_.isRef)
      val fields = types flatMap extractFields(namespace)
      val extensions = toExtend map { s => Domain.Reference(s.$ref, s) }
      wrap(s, Domain.TypeDef(removeNamespace(namespace)(name), fields, extensions, s))

    case s: model.Schema if (s.`type` == OBJECT || s.`type` == null) && s.properties != null =>
      wrap(s, fieldsToTypeDef(removeNamespace(namespace)(name), s, namespace))

    case s: model.Schema if s.`type` == ARRAY =>
      val field = Field(name, schema2Type(s.items, removeNamespace(namespace)(name), namespace))
      wrap(s, Domain.Arr(field, s))

    case i: Items if i.`type` == ARRAY =>
      val field = Field(name, schema2Type(i.items, removeNamespace(namespace)(name), namespace))
      Domain.Arr(field, i)

    case s: Schema if s.$ref != null =>
      wrap(s, Domain.Reference(s.$ref, s))

    case s if s.$ref != null =>
      Domain.Reference(s.$ref, s)

    case other =>
      ???
  }

  def fieldsToTypeDef(name: String, s: {def properties: Properties; def description: String}, namespace: String): TypeDef = {
    val fields = extractFields(namespace)(s)
    TypeDef(name, fields.toSeq, Nil, TypeMeta(Option(s.description)))
  }

  def extractFields(namespace: String)(s: {def properties: Properties}) = {
    val fields = s.properties map { prop =>
      val name = prop._1
      val pr = prop._2
      Field(name, schema2Type(pr, name, namespace), prop._2)
    }
    fields
  }

  import PrimitiveType._

  // format: OFF

  protected[swagger] def swaggerType2Type(p: model.Parameter): Domain.Type = propsPartialFunction(p.`type`, p.format)(p)

  protected def propsPartialFunction: PartialFunction[(PrimitiveType.Value, String), TypeMeta => Domain.Type] = {
    case (INTEGER, "int64")     => Domain.Lng
    case (INTEGER, "int32")     => Domain.Int
    case (INTEGER, _)           => Domain.Int

    case (NUMBER, "float")      => Domain.Flt
    case (NUMBER, "double")     => Domain.Dbl
    case (NUMBER, _)            => Domain.Dbl

    case (BOOLEAN, _)           => Domain.Bool

    case (STRING, "byte")       => Domain.Byt
    case (STRING, "date")       => Domain.Date
    case (STRING, "date-time")  => Domain.DateTime
    case (STRING, "password")   => Domain.Password
    case (STRING, format)       => Domain.Str.curried(Option(format))

    case (FILE, _)              => Domain.File

//    case other                      => println(other); Domain.Unknown // TODO custom types ? check specs
  }

  // format: ON

  def removeNamespace(namespace: String)(ref: String) = ref.replace(s"""/$namespace/""", "")

  def escape(name: String) = {
    import de.zalando.swagger.ScalaReserved._
    if (
      names.contains(name) ||
        startNames.exists(name.startsWith) ||
        partNames.exists(name.contains)
    )
      "`" + name + "`"
    else
      name
  }

}

