package de.zalando.swagger

import de.zalando.apifirst
import de.zalando.apifirst.Application.{ApiCall, Model}
import de.zalando.apifirst.Domain._
import de.zalando.apifirst.Http.{MimeType, Verb}
import de.zalando.apifirst.Path.{FullPath, InPathParameter}
import de.zalando.apifirst.{ParameterPlace, Application, Domain, Http}
import de.zalando.swagger.model.PrimitiveType._
import de.zalando.swagger.model.{PrimitiveType, _}

import scala.language.{implicitConversions, postfixOps, reflectiveCalls}

/**
 * @since 17.07.2015
 */
object Swagger2Ast extends HandlerParser {

  implicit def convert(keyPrefix: String, definitionFile: java.io.File)(implicit swaggerModel: SwaggerModel): Model =
    Model(convertCalls(keyPrefix, definitionFile.getName), convertDefinitions)

  def convertCalls(keyPrefix: String, fileName: String)(implicit swaggerModel: SwaggerModel) =
    Option(swaggerModel.paths).map { paths: Paths =>
      paths.flatMap(toCall(keyPrefix, fileName))
    }.toSeq.flatten

  def convertDefinitions(implicit swaggerModel: SwaggerModel) =
    Option(swaggerModel.definitions).map { d =>
      d map toDefinition("/definitions/")
    }.toSeq.flatten

  import Http.string2verb

  def toDefinition(namespace: String)(definition: (String, model.Schema))(implicit swaggerModel: SwaggerModel) =
    SchemaConverter.schema2Type(definition._2, namespace + definition._1)

  def toCall(keyPrefix: String, fileName: String)(path: (String, model.Path))(implicit swaggerModel: SwaggerModel): Seq[ApiCall] = {
    import model.parameterOrReference2Parameter

    def defaultValue = { p: ParameterOrReference =>
      if (p.required && p.default != null) Some(p.default) else None
    }

    val queryParameters = nonBodyParameters(defaultValue, _.queryParameter) _
    val pathParameters = nonBodyParameters(_ => None,  _.pathParameter) _
    val bodyParameters = nonBodyParameters(_ => None,  _.bodyParameter) _

    val call = operations(path) flatMap { case (operation: Operation, signature: String) =>
      for {
        verb <- string2verb(signature)
        pathParams = pathParameters(operation, verb, path._1)
        queryParams = queryParameters(operation, verb, path._1)
        bodyParams = bodyParameters(operation, verb, path._1)
        astPath = apifirst.Path.path2path(path._1, pathParams ++ queryParams)
        handlerText <- getOrGenerateHandlerLine(operation, keyPrefix, signature, astPath, fileName)
        parseResult = parse(handlerText)
        handler <- if (parseResult.successful) Some(parseResult.get.copy(parameters = pathParams ++ queryParams ++ bodyParams)) else None
        (mimeIn, mimeOut) = mimeTypes(operation)
        errorMapping = swaggerModel.vendorErrorMappings // FIXME should be possible to override on path and method level
        (resultType, successStatus) = responseInfo(operation, verb, path._1)
      } yield Some(ApiCall(verb, astPath, handler, Set(mimeIn), Set(mimeOut), errorMapping, Map(successStatus -> resultType)))
    case _ =>
      None
    }
    call.toSeq.flatten
  }

  def getOrGenerateHandlerLine(operation: Operation, keyPrefix: String, verb: String, path: FullPath, fileName: String)
      (implicit model: SwaggerModel): Option[String] =
    operation.vendorExtensions.get(s"$keyPrefix-handler") orElse
      model.vendorExtensions.get(s"$keyPrefix-package").map { pkg =>
        val method = Option(operation.operationId).map(camelize(" ", _)).getOrElse(verb.toLowerCase + capitalize("/",path.string("by/" + _.value)))
        val controller = capitalize("\\.", fileName)
        s"$pkg.$controller.$method"
      }

  def capitalize(separator: String, str: String) = {
    assert(str != null)
    str.split(separator).map { p => if (p.nonEmpty) p.head.toUpper +: p.tail else p }.mkString("")
  }
  def camelize(separator: String, str: String) = capitalize(separator, str) match {
    case p if p.isEmpty => ""
    case p => p.head.toLower +: p.tail
  }

  // FIXME
  def mimeTypes(operation: Operation) = {
    val mimeIn = new MimeType(Option(operation.consumes).flatMap(_.headOption).getOrElse("application/json"))
    val mimeOut = new MimeType(Option(operation.produces).flatMap(_.headOption).getOrElse("application/json"))
    (mimeIn, mimeOut)
  }
  // FIXME
  def responseInfo(operation: Operation, verb: Http.Verb, path: String) =
    operation.responses.filter(r => r._1.forall(_.isDigit) && r._1.toInt < 400).map { r =>
      val status = r._1.toInt
      val typeName = TypeName("What is this name all about?")
      val name = Option(r._2.schema).map { schema =>
        SchemaConverter.schema2Type(schema, typeName)
      }.getOrElse(Domain.Any(TypeMeta(Option(r._2.description))))
      (name, status)
    }.headOption.getOrElse((Domain.Null(TypeMeta(None)), 200))

  // FIXME: these "inline" definitions (except reference) should be added to the type map
  private def nameForInlineParameter(pr: ParameterOrReference, op: Operation, verb: Verb, path: String): TypeName = pr match {
    case r: Reference => r.name
    case p: Parameter if p.name != null && p.name.trim.nonEmpty => path + "_" + verb.name + "_" + p.name
    case p: Parameter => path + "_" + verb.name + "_" + "inline" + p.hashCode()
  }

  private def nonBodyParameters(default: ParameterOrReference => Option[String],
    filter: ParameterOrReference => Boolean)(operation: model.Operation, verb: Verb, path: String)(implicit swaggerModel: SwaggerModel): Seq[Application.Parameter] =
    Option(operation.parameters).map { _.filter(filter).map { p =>
      nonBodyParameter(default)(nameForInlineParameter(p, operation, verb, path))(p)
    }}.toSeq.flatten

  private def nonBodyParameter(default: ParameterOrReference => Option[String])
      (typeName: TypeName)
      (p: model.ParameterOrReference)
      (implicit swaggerModel: SwaggerModel): Application.Parameter = {
    val fixed = None // There is no way to define fixed parameters in swagger spec
    val default = if (p.required) Option(p.default) else None
    val name = SchemaConverter.parameter2Type(p, typeName)
    import Domain.paramOrRefInfo2TypeMeta
    val fullTypeName = if (p.required) name else Domain.Opt(Field(name.name.simpleName, name, TypeMeta(Option(p.description))), p)
    val (constraint, encode) = Constraints(p.in.toString)
    Application.Parameter(p.name, fullTypeName, fixed, default, constraint, encode, ParameterPlace.BODY)
  }

  /**
   * Converts a pair of method name and path definition to the operation
   * @param path
   * @return
   */
  private def operations(path: (String, Path)) = {
    val fields = path._2.getClass.getMethods filter {
      _.getReturnType == classOf[Operation]
    } filterNot {
      _.getName.contains("$")
    }
    val pairs = fields flatMap { f =>
      Option(f.invoke(path._2)) map { _.asInstanceOf[Operation] -> f.getName }
    }
    pairs
  }

}

object SchemaConverter {
  val ADDITIONAL_PROPS = "additionalProperties"
  val requiredProperties = Seq(ADDITIONAL_PROPS)

  import Domain._

  def wrap[T <: Type](s: Schema, t: T): Type = t match {
    case td @ TypeDef(name, fields, extend, meta, _) =>
      val wrapped = fields map { f =>
        if (s.required != null && s.required.contains(f.name.simpleName) ||
          requiredProperties.contains(f.name.simpleName)) f
        else f.copy(kind = Domain.Opt(f, f.meta))
      }
      td.copy(fields = wrapped)
    case _ => t
  }

  def parameter2Type(p: model.ParameterOrReference, name: TypeName): Domain.Type = p match {
    case p: Parameter if propsPartialFunction.isDefinedAt(p.`type`, p.format, p.items) =>
      propsPartialFunction(p.`type`, p.format, null)(p) // TODO maybe wrap the parameter as well?
    case p: Parameter if p.`type` == ARRAY =>
      val field = Field(name.simpleName, schema2Type(p.items, name), TypeMeta(Option(p.description)))
      Domain.Arr(field, p) // TODO maybe {{{ wrap(p, Domain.Arr(field, p)) }}} ?
    case r: ParameterReference =>
      Domain.Reference(r.$ref, r)
    case s: Parameter if s.schema != null =>
      schema2Type(s.schema, name)
    case other =>
      println("ignoring!!!!: " + other)
      // this one is a complex type. later we could support them using URLEncode
      Domain.Null(TypeMeta(Some("URL Encoded complex types are not supported yet")))
  }

  def schema2Type(p: model.TypeInfo, name: TypeName): Domain.Type = p match {
    case s: model.Schema if propsPartialFunction.isDefinedAt(p.`type`, p.format, null) =>
      wrap(s, propsPartialFunction(p.`type`, p.format, null)(p))

    case _ if p != null && propsPartialFunction.isDefinedAt(p.`type`, p.format, null) =>
      propsPartialFunction(p.`type`, p.format, null)(p)

    case s: model.Schema if s.allOf != null =>
      val (toExtend, types) = s.allOf.partition(_.isRef)
      val fields = types flatMap extractFields(name)
      // TODO extract fields to private types
      // TODO use s.properties instead of fields
      val extensions = toExtend map { s => Domain.Reference(s.$ref, s) }
      wrap(s, Domain.TypeDef(name, fields, extensions, s))

    case s: model.Schema if s.`type` == ARRAY =>
      val field = Field(name.simpleName, schema2Type(s.items, name), TypeMeta(Option(s.description)))
      wrap(s, Domain.Arr(field, s))

    case i: Items if i.`type` == ARRAY || i.items != null =>
      val field = Field(name.simpleName, schema2Type(i.items, name), TypeMeta(Option(i.format)))
      Domain.Arr(field, i)

    case s: Items if s.allOf != null =>
      val toExtend = s.allOf.filter(_.isInstanceOf[ParameterReference]).map(_.asInstanceOf[ParameterReference])
      // TODO extract properties
      val fields = Seq.empty[Field] //
      val extensions = toExtend map { s => Domain.Reference(s.asInstanceOf[ParameterReference].$ref, s) }
      val field = Field(name.simpleName, Domain.TypeDef(name, fields, extensions, s), TypeMeta(Option(s.format)))
      Domain.Arr(field, s)

    case s: Schema if s.$ref != null =>
      wrap(s, Domain.Reference(s.$ref, s))

    case s: model.Schema if s.`type` == OBJECT || s.`type` == null =>
      wrap(s, fieldsToTypeDef(name, s))

    case s if s != null && s.$ref != null =>
      Domain.Reference(s.$ref, s)

    case p: Property if p != null && p.items != null =>
      schema2Type(p.items, name)

    case o if o == null =>
      Domain.Null(TypeMeta(Some("Got null to convert to the type")))

    case other =>
      println("Don't know what to do with " + other)
      ???
  }

  def fieldsToTypeDef(name: TypeName,  s: Schema): TypeDef = {
    val fields = if (s.properties != null) extractFields(name)(s) else Nil
    val catchAll = if (s.additionalProperties != null) {
      val fieldName = name.nest(ADDITIONAL_PROPS)
      val field = Field(fieldName, schema2Type(s.additionalProperties, fieldName), s)
      val mainField =
        Field(fieldName, Domain.CatchAll(field, s), TypeMeta(Some("Catch-All property")))
      Seq(mainField)
    } else Nil
    TypeDef(name, catchAll ++ fields, Nil, s)
  }

  def extractFields(name: TypeName)(s: Schema) = {
    val fields = s.properties map { prop =>
      val fieldName = name.nest(prop._1)
      Field(fieldName, schema2Type(prop._2, fieldName), TypeMeta(Option(prop._2.description)))
    }
    fields
  }

  import PrimitiveType._

  // format: OFF

  protected[swagger] def swaggerType2Type(p: model.Parameter): Domain.Type = propsPartialFunction(p.`type`, p.format, p.items)(p)

  protected def propsPartialFunction: PartialFunction[(PrimitiveType.Value, String, Items), TypeMeta => Domain.Type] = {
    case (INTEGER, "int64", _)     => Domain.Lng
    case (INTEGER, "int32", _)     => Domain.Int
    case (INTEGER, _, _)           => Domain.Int

    case (NUMBER, "float", _)      => Domain.Flt
    case (NUMBER, "double", _)     => Domain.Dbl
    case (NUMBER, _, _)            => Domain.Dbl

    case (BOOLEAN, _, _)           => Domain.Bool

    case (STRING, "byte", _)       => Domain.Byt
    case (STRING, "date", _)       => Domain.Date
    case (STRING, "date-time", _)  => Domain.DateTime
    case (STRING, "password", _)   => Domain.Password
    case (STRING, format, _)       => Domain.Str.curried(Option(format))

    case (FILE, _, _)              => Domain.File

//    case other                      => println(other); Domain.Unknown // TODO custom types ? check specs
  }

  // format: ON
}
