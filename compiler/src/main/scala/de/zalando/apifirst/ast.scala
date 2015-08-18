package de.zalando.apifirst

import de.zalando.swagger.model._

import scala.language.{postfixOps, implicitConversions}
import scala.util.parsing.input.Positional

sealed trait Expr

object Http {

  abstract class Verb(val name: String) extends Expr

  case object GET extends Verb("GET")

  case object POST extends Verb("POST")

  case object PUT extends Verb("PUT")

  case object DELETE extends Verb("DELETE")

  case object HEAD extends Verb("HEAD")

  case object OPTIONS extends Verb("OPTIONS")

  case object TRACE extends Verb("TRACE")

  case object PATCH extends Verb("PATCH")

  private val verbs = GET :: POST :: PUT :: DELETE :: HEAD :: OPTIONS :: TRACE :: PATCH :: Nil

  def string2verb(name: String): Option[Verb] = verbs find {
    _.name == name.trim.toUpperCase
  }

  abstract class MimeType(name: String) extends Expr

  case object ApplicationJson extends MimeType(name = "application/json")

  case class Body(value: Option[String]) extends Expr

}

object Hypermedia {

  // TODO relation should be another API call, not an url
  case class Relation(tpe: String, url: String)

}

object Domain {

  type ModelDefinition = Iterable[Domain.Type]

  case class TypeName(fullName: String) {
    import TypeName.SL
    val simpleName = fullName.trim.takeRight(fullName.length - fullName.lastIndexOf(SL) - 1)
    val asSimpleType = camelize(simpleName)
    val namespace = fullName.trim.take(fullName.lastIndexOf(SL)).toLowerCase
    val oneUp = {
      val space = Option(namespace).flatMap(_.split(SL).filter(_.nonEmpty).drop(1).lastOption).getOrElse("")
      (if (space.nonEmpty) space + "." else "") + asSimpleType
    }

    def nestedIn(t: TypeName) = namespace.nonEmpty && t.fullName.toLowerCase.startsWith(namespace)
    def relativeTo(t: TypeName) = {
      val newSpace = namespace.replace(t.namespace,"").dropWhile(_ == SL).replace(SL, '.')
      if (newSpace.nonEmpty) newSpace + '.' + asSimpleType else asSimpleType
    }
    def nest(name: String) = TypeName(fullName + SL + name)
    private def camelize(s: String) = if (s.isEmpty) s else s.head.toUpper + s.tail
  }
  object TypeName {
    val SL = '/'
    def apply(namespace: String, simpleName: String):TypeName = TypeName(namespace + SL + simpleName)
    def escapeName(name: String) = {
      val innerType =
        if (name.lastIndexOf('[')>0 && name.indexOf(']') > name.lastIndexOf('['))
          name.substring(name.lastIndexOf('[')+1, name.indexOf(']'))
        else
          name
      val escapedType = innerType.split('.').map(escape).toSeq.mkString(".")
      name.replace(innerType, escapedType)
    }

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


  // First comments, then everything else
  case class TypeMeta(comment: Option[String]) {
    override def toString = s"""TypeMeta(${
      comment match {
        case None => "None"
        case Some(s) => "Some(\"" + s.replaceAll("\"", "\\\"") + "\")"
      }
    })"""
  }

  implicit def option2TypeMeta(o: Option[String]): TypeMeta = TypeMeta(o)

  implicit def schema2TypeMeta(s: Schema): TypeMeta = Option(s.description)

  implicit def typeInfo2TypeMeta(s: TypeInfo): TypeMeta = Option(s.format)

  implicit def paramOrRefInfo2TypeMeta(s: ParameterOrReference): TypeMeta = s match {
    case s: Parameter => Option(s.description)
    case s: ParameterReference => Option(s.$ref)
  }

  abstract class Type(val name: TypeName, val meta: TypeMeta) extends Expr {
    def nestedTypes: Seq[Type] = Nil
    def imports: Set[String] = Set.empty
  }

  implicit def string2TypeName(s: String): TypeName = TypeName(s)

  case class Int(override val meta: TypeMeta) extends Type("Int", meta)

  case class Lng(override val meta: TypeMeta) extends Type("Long", meta)

  case class Flt(override val meta: TypeMeta) extends Type("Float", meta)

  case class Dbl(override val meta: TypeMeta) extends Type("Double", meta)

  case class Byt(override val meta: TypeMeta) extends Type("Byte", meta)

  case class Str(format: Option[String] = None, override val meta: TypeMeta) extends Type("String", meta)

  case class Bool(override val meta: TypeMeta) extends Type("Boolean", meta)

  case class Date(override val meta: TypeMeta) extends Type("Date", meta) {
    override val imports = Set("java.util.Date")
  }

  case class File(override val meta: TypeMeta) extends Type("File", meta) {
    override val imports = Set("java.io.File")
  }

  case class DateTime(override val meta: TypeMeta) extends Type("Date", meta) {
    override val imports = Set("java.util.Date")
  }

  case class Password(override val meta: TypeMeta) extends Type("Password", meta)

  case class Null(override val meta: TypeMeta) extends Type("null", meta)

  abstract class Container(override val name: TypeName, val field: Field, override val meta: TypeMeta, override val imports: Set[String])
    extends Type(name, meta) {
    def allImports: Set[String] = imports ++ field.imports
    override def nestedTypes = field.kind.nestedTypes :+ field.kind
  }

  case class Arr(override val field: Field, override val meta: TypeMeta)
    extends Container(s"Seq[${field.kind.name.oneUp}]", field, meta, Set("scala.collection.Seq"))

  case class Opt(override val field: Field, override val meta: TypeMeta)
    extends Container(s"Option[${field.kind.name.oneUp}]", field, meta, Set("scala.Option"))

  case class CatchAll(override val field: Field, override val meta: TypeMeta)
    extends Container(s"Map[String, ${field.kind.name.oneUp}]", field, meta, Set("scala.collection.immutable.Map"))

  abstract class Entity(override val name: TypeName, override val meta: TypeMeta) extends Type(name, meta)

  case class Field(override val name: TypeName, kind: Type, override val meta: TypeMeta) extends Type(name, meta) {
    override def toString = s"""Field("$name", $kind, $meta)"""

    def asCode(prefix: String = "") = s"$name: ${kind.name}"

    override def imports = kind match {
      case c: Container => c.allImports
      case o => o.imports
    }

    override def nestedTypes = kind.nestedTypes :+ kind
  }

  case class TypeDef(override val name: TypeName,
                     fields: Seq[Field],
                     extend: Seq[Reference] = Nil,
                     override val meta: TypeMeta) extends Entity(name, meta) {
    override def toString = s"""\n\tTypeDef("$name", List(${fields.mkString("\n\t\t", ",\n\t\t", "")}), $extend, $meta)\n"""

    def imports(implicit model: ModelDefinition): Set[String] = {
      val fromFields = fields.flatMap(_.imports)
      val transient = extend.flatMap(_.resolve(model).toSeq.flatMap(_.imports))
      (fromFields ++ transient).filter(_.trim.nonEmpty).toSet
    }

    def allFields(implicit model: ModelDefinition): Seq[Field] =
      fields ++ extend.flatMap(_.resolve.toSeq.flatMap(_.allFields))

    def allExtends(implicit model: ModelDefinition): Seq[Reference] =
      extend ++ extend.flatMap(_.resolve.toSeq.flatMap(_.allExtends))

    override def nestedTypes = fields flatMap (_.nestedTypes) filter { _.name.nestedIn(name) } distinct
  }

  abstract class Reference(override val name: TypeName, override val meta: TypeMeta) extends Type(name, meta) {
    def resolve(implicit model: ModelDefinition): Option[TypeDef] = ???
  }

  case class ReferenceObject(override val name: TypeName, override val meta: TypeMeta) extends Reference(name, meta) {
    override def toString = s"""ReferenceObject("$name", $meta)"""
    override def resolve(implicit model: ModelDefinition): Option[TypeDef] = model.find(_.name == name) match {
      case Some(t: TypeDef) => Some(t)
      case _ => None
    }
  }

  case class RelativeSchemaFile(file: String, override val meta: TypeMeta) extends Reference(file, meta)

  case class EmbeddedSchema(file: String, ref: Reference, override val meta: TypeMeta) extends Reference(file, meta)

  object Reference {
    def apply(url: String, meta: TypeMeta = None): Reference = url.indexOf('#') match {
      case 0 => ReferenceObject(url.tail, meta)
      case i if i < 0 => RelativeSchemaFile(url, meta)
      case i if i > 0 =>
        val (filePart, urlPart) = url.splitAt(i)
        EmbeddedSchema(filePart, apply(urlPart, meta), meta)
    }
  }

}

object Path {

  import scala.language.{implicitConversions, postfixOps}

  abstract class PathElem(val value: String) extends Expr

  case object Root extends PathElem(value = "/")

  case class Segment(override val value: String) extends PathElem(value)

  // swagger in version 2.0 only supports Play's singleComponentPathPart - should be encoded for constraint,
  case class InPathParameter(override val value: String, constraint: String, encode: Boolean = true) extends PathElem(value)

  object InPathParameter {
    val encode = true
    val constraint = """[^/]+"""
  }

  case class FullPath(value: Seq[PathElem]) extends Expr {
    def isAbsolute = value match {
      case Root :: segments => true
      case _ => false
    }
  }

  object FullPath {
    def is(elements: PathElem*): FullPath = FullPath(elements.toList)
  }

  def path2path(path: String, parameters: Seq[Application.Parameter]): FullPath = {
    def path2segments(path: String, parameters: Seq[Application.Parameter]) = {
      path.trim split Root.value map {
        case seg if seg.startsWith("{") && seg.endsWith("}") =>
          val name = seg.tail.init
          parameters.find(_.name == name) map { p => InPathParameter(name, p.constraint, p.encode) }
        case seg if seg.nonEmpty =>
          Some(Segment(seg))
        case seg =>
          None
      }
    }
    val segments = path2segments(path, parameters).toList.flatten
    val elements = if (path.startsWith("/")) Root :: segments else segments
    FullPath(elements)
  }

}

object Query {

  case class QueryParam(name: String, value: String) extends Expr

  case class FullQuery(values: QueryParam*) extends Expr

}

object Application {

  // Play definition
  case class Parameter(name: String, typeName: Domain.Type,
                       fixed: Option[String], default: Option[String],
                       constraint: String, encode: Boolean) extends Expr with Positional

  // Play definition
  case class HandlerCall(packageName: String, controller: String, instantiate: Boolean,
                         method: String, parameters: Seq[Parameter])

  case class ApiCall(
                      verb: Http.Verb,
                      path: Path.FullPath,
                      handler: HandlerCall
                      )

  /*
    query: Query.FullQuery,
    body: Http.Body,
    mimeIn: MimeType,
    mimeOut: MimeType
  */

  case class Model(calls: Seq[ApiCall], definitions: Iterable[Domain.Type])

}


