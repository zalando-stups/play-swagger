package de.zalando.apifirst

import de.zalando.apifirst.Http.MimeType

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

  def string2verb(name: String): Option[Verb] = verbs find  { _.name == name.trim.toUpperCase }

  abstract class MimeType(name: String) extends Expr
  case object ApplicationJson extends MimeType(name = "application/json")

  case class Body(value: Option[String]) extends Expr

}

object Hypermedia {
  case class Relation(tpe: String, url: String) // TODO relation should be another API call, not an url
}

object Domain {
  abstract class Type extends Expr
  case object Int extends Type
  case object Str extends Type

  abstract class Entity extends Type
  case class Field(name: String, kind: Entity) extends Expr
  case class TypeDef(name: String, fields: Seq[Field]) extends Entity

}

object Path {
  abstract class PathElem(val value: String) extends Expr
  case object Root extends PathElem(value = "/")
  case class Segment(override val value: String) extends PathElem(value)
  case class InPathParameter(override val value: String) extends PathElem(value)

  case class FullPath(value: PathElem*) extends Expr

  implicit def path2path(path: String): FullPath = {
    val segments = path split Root.value map {
      case seg if seg.startsWith("{") && seg.endsWith("}") => InPathParameter(seg.tail.dropRight(1))
      case seg => Segment(seg)
    } toList
    val fullPath = if (path.startsWith(Root.value)) Root :: segments else segments
    FullPath(fullPath:_*)
  }
}

object Query {
  case class QueryParam(name: String, value: String) extends Expr
  case class FullQuery(values: QueryParam*) extends Expr
}


object Application {

  // Play definition
  case class Parameter(name: String, typeName: String,
                       fixed: Option[String], default: Option[String]) extends Expr with Positional // TODO use Domain.Type for typeName

  // Play definition
  case class HandlerCall(packageName: String, controller: String, instantiate: Boolean,
                         method: String, parameters: Option[Seq[Parameter]])

/*
  TODO enrich HandlerCall model
  case class Method(name: String, parameters: Seq[Parameter], result: Domain.Type) extends Expr
  case class Handler(packageName: String, name: String, method: Method, resultType: Domain.Type) extends Expr
*/

  case class ApiCall(
                      verb: Http.Verb,
                      path: Path.FullPath,
                      query: Query.FullQuery,
                      body: Http.Body,
                      handler: HandlerCall,
                      mimeIn: MimeType,
                      mimeOut: MimeType
                      )

  // TODO we could analyse project files for further implementation details and do some kind of autowiring here
  case class Model(calls: Seq[ApiCall])

}


