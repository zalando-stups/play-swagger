package de.zalando.apifirst

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
  abstract class Type(val name: String) extends Expr
  case object Int extends Type("Int")
  case object Lng extends Type("Long")
  case object Flt extends Type("Float")
  case object Dbl extends Type("Double")

  case object Byt extends Type("Byte")

  case class Str(format: Option[String] = None) extends Type("String")
  case object Bool extends Type("Boolean")
  case object Date extends Type("java.util.Date")
  case object File extends Type("java.io.File")
  case object DateTime extends Type("java.util.Date")
  case object Password extends Type("Password")

  case object Null extends Type("null")

  case class Arr(underlying: Type) extends Type(s"Seq[${ underlying.name }]")
  case class Opt(underlying: Type) extends Type(s"Option[${ underlying.name }]")

  abstract class Reference(override val name: String) extends Type(name)
  case class ReferenceObject(url: String) extends Reference(url)
  case class RelativeSchemaFile(file: String) extends Reference(file)
  case class EmbeddedSchema(file: String, ref: Reference) extends Reference(file)

  case object Unknown extends Type("Unknown")

  abstract class Entity(override val name: String) extends Type(name)
  case class Field(override val name: String, kind: Type) extends Type(name)
  case class TypeDef(override val name: String, fields: Seq[Field]) extends Entity(name)

  object Reference {
    def apply(url: String): Reference = url.indexOf('#') match {
      case 0 => ReferenceObject(url.tail)
      case i if i < 0 => RelativeSchemaFile(url)
      case i if i > 0 =>
        val (filePart, urlPart) = url.splitAt(i)
        EmbeddedSchema(filePart, apply(urlPart))
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


