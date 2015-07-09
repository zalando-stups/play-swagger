package com.example.play.swagger.compiler

sealed trait Expr

// HTTP

abstract class Verb(name: String) extends Expr
case object GET extends Verb("GET")
case object POST extends Verb("POST")
case object PUT extends Verb("PUT")
case object DELETE extends Verb("DELETE")
case object HEAD extends Verb("HEAD")
case object OPTIONS extends Verb("OPTIONS")
case object TRACE extends Verb("TRACE")

abstract class PathElem(value: String) extends Expr
case object Root extends PathElem(value = "/")
case class Segment(value: String) extends PathElem(value)
case class Path(value: Seq[PathElem]) extends Expr

case class QueryParm(name: String, value: String) extends Expr
case class Query(value: Seq[QueryParm]) extends Expr

// MIME

abstract class MimeType(name: String) extends Expr
case object ApplicationJson extends MimeType(name = "application/json")

// BODY

case class Body(value: String) extends Expr

// APPLICATION

abstract class Type extends Expr
case object Int extends Type
case object Str extends Type

abstract class Parm(name: String, value: Type)
abstract class Method(name: String, params: Seq[Parm], result: Type)
abstract class Handler(name: String, methods: Seq[Method])


