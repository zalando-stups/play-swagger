package de.zalando.apifirst

import de.zalando.swagger.model._

import scala.language.implicitConversions
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

  def string2verb(name: String): Option[Verb] = verbs find { _.name == name.trim.toUpperCase }

  abstract class MimeType(name: String) extends Expr
  case object ApplicationJson extends MimeType(name = "application/json")

  case class Body(value: Option[String]) extends Expr

}

object Hypermedia {
  // TODO relation should be another API call, not an url
  case class Relation(tpe: String, url: String)
}

object Domain {
  // First comments, then everything else
  case class TypeMeta(comment: Option[String]) {
    override def toString = s"""TypeMeta(${comment match {
      case None => "None"
      case Some(s) => "Some(\"" + s.replaceAll("\"", "\\\"") + "\")"
    }})"""
  }
  implicit def option2TypeMeta(o:Option[String]):TypeMeta = TypeMeta(o)
  implicit def schema2TypeMeta(s: Schema): TypeMeta = Option(s.description)
  implicit def typeInfo2TypeMeta(s: TypeInfo): TypeMeta = Option(s.format)
  implicit def paramOrRefInfo2TypeMeta(s: ParameterOrReference): TypeMeta = s match {
    case s: Parameter => Option(s.description)
    case s: ParameterReference => Option(s.$ref)
  }

  abstract class Type(val name: String, val meta: TypeMeta) extends Expr
  case class Int(override val meta: TypeMeta) extends Type("Int", meta)
  case class Lng(override val meta: TypeMeta) extends Type("Long", meta)
  case class Flt(override val meta: TypeMeta) extends Type("Float", meta)
  case class Dbl(override val meta: TypeMeta) extends Type("Double", meta)

  case class Byt(override val meta: TypeMeta) extends Type("Byte", meta)

  case class Str(format: Option[String] = None, override val meta: TypeMeta) extends Type("String", meta)
  case class Bool(override val meta: TypeMeta) extends Type("Boolean", meta)
  case class Date(override val meta: TypeMeta) extends Type("java.util.Date", meta)
  case class File(override val meta: TypeMeta) extends Type("java.io.File", meta)
  case class DateTime(override val meta: TypeMeta) extends Type("java.util.Date", meta)
  case class Password(override val meta: TypeMeta) extends Type("Password", meta)

  case class Null(override val meta: TypeMeta) extends Type("null", meta)

  case class Arr(underlying: Type, override val meta: TypeMeta) extends Type(s"Seq[${ underlying.name }]", meta)
  case class Opt(underlying: Type, override val meta: TypeMeta) extends Type(s"Option[${ underlying.name }]", meta)

  abstract class Reference(override val name: String, override val meta: TypeMeta) extends Type(name, meta)
  case class ReferenceObject(url: String, override val meta: TypeMeta) extends Reference(url, meta) {
    override def toString = s"""ReferenceObject("$url", $meta)"""
  }
  case class RelativeSchemaFile(file: String, override val meta: TypeMeta) extends Reference(file, meta)
  case class EmbeddedSchema(file: String, ref: Reference, override val meta: TypeMeta) extends Reference(file, meta)

  case class Unknown(override val meta: TypeMeta) extends Type("Unknown", meta)

  abstract class Entity(override val name: String, override val meta: TypeMeta) extends Type(name, meta)
  case class Field(override val name: String, kind: Type, override val meta: TypeMeta = None) extends Type(name, meta) {
    override def toString = s"""Field("$name", $kind, $meta)"""
  }
  case class TypeDef(override val name: String,
                     fields: Seq[Field],
                     extend: Seq[Type] = Nil,
                     override val meta: TypeMeta = None) extends Entity(name, meta) {
    override def toString = s"""\n\tTypeDef("$name", List(${fields.mkString("\n\t\t", ",\n\t\t", "")}), $extend, $meta)\n"""
  }
  case class CatchAll(kind: Type, override val meta: TypeMeta) extends Entity("additionalProperties", meta)

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
    val encode     = true
    val constraint = """[^/]+"""
  }
  
  case class FullPath(value: Seq[PathElem]) extends Expr {
    def isAbsolute = value match {
      case Root :: segments => true
      case _                => false
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
        case seg if seg.nonEmpty                             =>
          Some(Segment(seg))
        case seg                                             =>
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

  case class Model(calls: Seq[ApiCall], definitions: Map[String, Domain.Type])

}


