package de.zalando.apifirst

import java.net.URL

import de.zalando.apifirst.Domain.Type
import de.zalando.apifirst.Http.MimeType
import de.zalando.apifirst.Hypermedia.{State, StateTransitionsTable}
import de.zalando.apifirst.ParameterPlace.ParameterPlace
import de.zalando.apifirst.naming.{Path, Reference, TypeName}

import scala.language.{implicitConversions, postfixOps}
import scala.util.parsing.input.Positional

sealed trait Expr

object Http {

  abstract class Verb(val name: String) extends Expr

  case object GET     extends Verb("GET")
  case object POST    extends Verb("POST")
  case object PUT     extends Verb("PUT")
  case object DELETE  extends Verb("DELETE")
  case object HEAD    extends Verb("HEAD")
  case object OPTIONS extends Verb("OPTIONS")
  case object TRACE   extends Verb("TRACE")
  case object PATCH   extends Verb("PATCH")

  private val verbs = GET :: POST :: PUT :: DELETE :: HEAD :: OPTIONS :: TRACE :: PATCH :: Nil

  def string2verb(name: String): Option[Verb] = verbs find { _.name == name.trim.toUpperCase }

  class MimeType(val name: String) extends Expr {
    override def toString: String = s"""MimeType("$name")"""
  }

  case object ApplicationJson extends MimeType("application/json")

  case object HalJson extends MimeType("application/hal+json")

  case object SirenJson extends MimeType("application/vnd.siren+json")

  case object CollectionJson extends MimeType("application/vnd.collection+json")

  case object LdJson extends MimeType("application/ld+json")

  case object ApplicationFormUrlEncoded extends MimeType("application/x-www-form-urlencoded")

  case object MultipartFormData extends MimeType("multipart/form-data")

  case class Body(value: Option[String]) extends Expr

  object MimeType {
    def apply(name: String): MimeType = name.toLowerCase match {
      case ApplicationFormUrlEncoded.name => ApplicationFormUrlEncoded
      case MultipartFormData.name => MultipartFormData
      case ApplicationJson.name => ApplicationJson
      case other => new MimeType(other)
    }
  }
}

object Hypermedia {

  trait State extends Expr {
    def name: String
    def toScalaString(pad: String): String = s"""${pad}NamedState("$name")"""
  }

  type Condition                = Option[String]
  case class TransitionProperties(condition: Condition)
  type StateTransitionsTable    = Map[State, Map[State, TransitionProperties]]

  object State {
    def apply(name: String): State = if (Self.name.equalsIgnoreCase(name)) Self else NamedState(name)
    def toDot(table: StateTransitionsTable): Seq[String] = {
      val transitions = for {
        (from, destinations) <- table
        (to, props) <- destinations
        toNode = if (to == Self) from else to
        label = props.condition.getOrElse("")
        transition = s""" "${from.name}" -> "${toNode.name}" [label="$label"];"""
      } yield transition
      val acceptingNodes = table collect {
        case (from, destinations) if destinations.keySet == Set(Self) => from.name
      } map { name =>
        s""" "$name" [color="red",style="bold"] """
      }
      val allDestinations = table.values.flatMap(_.keySet).toSet
      val startingNodes = table collect {
        case (from, destinations) if ! allDestinations.contains(from) => from.name
      } map { name =>
        s""" "$name" [color="green",style="bold"] """
      }
      val lines = transitions ++ acceptingNodes ++ startingNodes
      "digraph {" +: lines.toSeq :+ "}"
    }
  }

  case class NamedState(name: String) extends State

  case object Self extends State {
    override val name = "Self"
    override def toScalaString(pad: String): String = s"""${pad}Self"""
  }

  case class Relation(tpe: String, from: State, to: State)

}

object Domain {

  type ModelDefinition = Iterable[Domain.Type]

  case class TypeMeta(comment: Option[String], constraints: Seq[String]) {
    override def toString: String = s"""TypeMeta($escapedComment, $constrStr)"""
    private val replacement = Seq('"','"','"','+','"','"','"','"','"','"','"','"','"','+','"','"','"').mkString
    private def escape(s: String) =
      if (s.contains('"')) "\"\"\"" + s.replaceAllLiterally("\"\"\"", replacement) + "\"\"\""
      else "\"" + s + "\""
    lazy val constrStr = constraints.map(escape)
    val escapedComment = comment.map(escape)
  }

  object TypeMeta {
    def apply(comment: Option[String]): TypeMeta = TypeMeta(comment, Seq.empty[String])
  }

  implicit def option2TypeMeta(o: Option[String]): TypeMeta = TypeMeta(o)

  trait PrimitiveType {
    def name: TypeName
  }

  abstract class Type(val name: TypeName, val meta: TypeMeta) extends Expr {
    def nestedTypes: Seq[Type] = Nil
    def imports: Set[String] = Set.empty
    def toShortString(pad: String): String = getClass.getSimpleName
    def toScalaString(pad: String): String = getClass.getSimpleName + "(" + name + ", " + meta + ")"
  }
  
  case class TypeRef(override val name: Reference) extends Type(name, TypeMeta(None)) {
    override def toShortString(pad: String): String = s"${super.toShortString(pad)}($name)"
    override def toScalaString(pad: String): String = s"${getClass.getSimpleName}(${name.toScalaString("")})"
  }

  abstract class ProvidedType(name: String, override val meta: TypeMeta)
    extends Type(Reference(name), meta) {
    override def toScalaString(pad: String): String = toString
  }

  class Nmbr(name: String, override val meta: TypeMeta) extends ProvidedType(name, meta) with PrimitiveType

  case class Intgr(override val meta: TypeMeta) extends Nmbr("Int", meta)

  case class Lng(override val meta: TypeMeta) extends Nmbr("Long", meta)

  case class Flt(override val meta: TypeMeta) extends Nmbr("Float", meta)

  case class Dbl(override val meta: TypeMeta) extends Nmbr("Double", meta)

  case class BDcml(override val meta: TypeMeta) extends Nmbr("BigDecimal", meta) {
    override val imports = Set("scala.math.BigDecimal")
  }

  case class BInt(override val meta: TypeMeta) extends Nmbr("BigInt", meta) {
    override val imports = Set("scala.math.BigInt")
  }

  case class Str(format: Option[String] = None, override val meta: TypeMeta) extends ProvidedType("String", meta) with PrimitiveType {
    override def toScalaString(pad: String): String = s"""Str(${format.map("\"" + _ + "\"")}, $meta)"""
  }

  case class Bool(override val meta: TypeMeta) extends ProvidedType("Boolean", meta) with PrimitiveType

  case class Date(override val meta: TypeMeta) extends ProvidedType("LocalDate", meta) with PrimitiveType {
    override val imports = Set("org.joda.time.LocalDate")
  }

  case class DateTime(override val meta: TypeMeta) extends ProvidedType("DateTime", meta)  with PrimitiveType {
    override val imports = Set("org.joda.time.DateTime")
  }

  case class File(override val meta: TypeMeta) extends ProvidedType("File", meta)  with PrimitiveType {
    override val imports = Set("java.io.File")
  }

  case class BinaryString(override val meta: TypeMeta) extends ProvidedType("BinaryString", meta) with PrimitiveType {
    override val imports = Set("de.zalando.play.controllers.BinaryString", "BinaryString._")
  }

  case class Base64String(override val meta: TypeMeta) extends ProvidedType("Base64String", meta)  with PrimitiveType {
    override val imports = Set("de.zalando.play.controllers.Base64String", "Base64String._")
  }

  case class Password(override val meta: TypeMeta) extends ProvidedType("String", meta) with PrimitiveType

  case class Null(override val meta: TypeMeta) extends ProvidedType("Null", meta) with PrimitiveType

  // case class Any(override val meta: TypeMeta) extends ProvidedType("Any", meta)

  /**
   * Composite classes describe some class composition
   *
   * It is different from the Container because it
   * has more than one underlying Type
   *
   */
  abstract class Composite(override val name: TypeName,
                           override val meta: TypeMeta,
                           val descendants: Seq[Type],
                           val root: Option[Reference])
    extends Type(name, meta) {
    override def toShortString(pad: String): String =
      s"${getClass.getSimpleName}(${descendants.map(_.toShortString(pad + "\t")).mkString(s"\n$pad",s"\n$pad","")})"
    override def nestedTypes: Seq[Type] = descendants flatMap ( _.nestedTypes )
    override def imports: Set[String] = descendants.flatMap(_.imports).toSet
    def withTypes(t: Seq[Type]): Composite
    override def toScalaString(pad: String): String = {
      // val impStr = if (imports.isEmpty) "Set.empty[String]" else imports.mkString("Set(\"", "\", \"","\")")
      val descStr = s""" Seq(${descendants.map(_.toScalaString(pad + "\t")).mkString(s"\n$pad",s",\n$pad","")}) """
      s"$pad${getClass.getSimpleName}(${name.toScalaString("")}, $meta, $descStr, ${root.map(_.toScalaString(""))})"
    }
  }

  case class AllOf(override val name: TypeName,
                   override val meta: TypeMeta,
                   override val descendants: Seq[Type],
                   override val root: Option[Reference] = None)
    extends Composite(name, meta, descendants, root) {
    def withTypes(t: Seq[Type]): AllOf = this.copy(descendants = t)
  }

  case class OneOf(override val name: TypeName,
                   override val meta: TypeMeta,
                   override val descendants: Seq[Type])
    extends Composite(name, meta, descendants, None) {
    def withTypes(t: Seq[Type]): OneOf = this.copy(descendants = t)
  }

  /**
   * Container is just a wrapper for another single type with some unique properties
   */
  abstract class Container(name: TypeName, val tpe: Type, override val meta: TypeMeta, override val imports: Set[String])
    extends Type(name, meta) {
    def allImports: Set[String] = imports ++ tpe.imports
    override def nestedTypes: Seq[Type] = Seq(tpe)
    override def toShortString(pad: String): String = s"${getClass.getSimpleName}(${tpe.toShortString(pad)})"
    override def toScalaString(pad: String): String = {
      // val impStr = if (imports.isEmpty) "Set.empty[String]" else imports.mkString("Set(\"", "\", \"","\")")
      s"${getClass.getSimpleName}(${tpe.toScalaString(pad)}, $meta)"
    }
    def withType(t: Type): Container
  }

  case class Arr(override val tpe: Type, override val meta: TypeMeta, format: String)
    extends Container(tpe.name / "ArrayWrapper", tpe, meta, Set("de.zalando.play.controllers.ArrayWrapper")) {
    def withType(t: Type): Arr = this.copy(tpe = t)
    override def toScalaString(pad: String): String =
      s"""${getClass.getSimpleName}(${tpe.toScalaString(pad)}, $meta, "$format")"""
  }

  case class ArrResult(override val tpe: Type, override val meta: TypeMeta)
    extends Container(tpe.name / "Seq", tpe, meta, Set.empty[String]) {
    def withType(t: Type): ArrResult = this.copy(tpe = t)
    override def toScalaString(pad: String): String =
      s"""${getClass.getSimpleName}(${tpe.toScalaString(pad)}, $meta)"""
  }

  case class Opt(override val tpe: Type, override val meta: TypeMeta)
    extends Container(tpe.name / "Option", tpe, meta, Set.empty[String]) {
    def withType(t: Type): Opt = this.copy(tpe = t)
  }

  case class CatchAll(override val tpe: Type, override val meta: TypeMeta)
    extends Container(tpe.name / "Map", tpe, meta, Set("scala.collection.immutable.Map")) {
    def withType(t: Type): CatchAll = this.copy(tpe = t)
    override def nestedTypes: Seq[Type] = Str(None, None) +: super.nestedTypes
  }

  case class Field(name: TypeName, tpe: Type) {
    def toString(pad: String): String = s"""\n${pad}Field($name, ${tpe.toShortString(pad + "\t")})"""
    def imports: Set[String] = tpe match {
      case c: Container => c.allImports
      case o => o.imports
    }

    def nestedTypes: Seq[Type] = tpe.nestedTypes :+ tpe
    def toScalaString(pad: String): String = s"""${pad}Field(${name.toScalaString("")}, ${tpe.toScalaString(pad + "\t")})"""
  }

  case class TypeDef(override val name: TypeName,
                     fields: Seq[Field],
                     override val meta: TypeMeta) extends Type(name, meta) {
    override def toString: String = s"""\n\tTypeDef($name, \n\t\tSeq(${fields.mkString("\n\t\t", ",\n\t\t", "")}\n\t\t), $meta)\n"""
    override def toShortString(pad: String): String = s"""TypeDef($name, Seq(${fields.map(_.toString(pad)).mkString(", ")}))"""
    override def nestedTypes: Seq[Type] = fields flatMap (_.nestedTypes) filter { _.name.parent == name  } distinct
    override def imports: Set[String] = (fields flatMap { _.imports }).toSet
    override def toScalaString(pad: String): String =
      s"""TypeDef(${name.toScalaString("")}, \n\t\t\tSeq(${fields.map(_.toScalaString(pad)).mkString("\n\t\t", ",\n\t\t", "")}\n\t\t\t), $meta)"""
  }

  abstract class EnumType(override val name: Reference, override val tpe: Type, override val meta: TypeMeta)
    extends Container(tpe.name / "Enum", tpe, meta, Set.empty[String])

  case class EnumTrait(override val tpe: Type, override val meta: TypeMeta, leaves: Set[EnumObject])
    extends EnumType(tpe.name / "Enum", tpe, meta) {
      override def nestedTypes: Seq[Type] = tpe +: leaves.toSeq
      override def withType(t: Type): EnumTrait = this.copy(tpe = t)
    override def toScalaString(pad: String): String = {
      val leavesStr = leaves.map(_.toScalaString(pad + "\t\t")).mkString("\n", ",\n","\n")
      s"""$pad${getClass.getSimpleName}(${tpe.toScalaString("")}, $meta, \n${pad}\tSet($leavesStr\n${pad}\t))"""
    }

  }

  case class EnumObject(override val tpe: Type, fieldValue: String, override val meta: TypeMeta)
    extends EnumType(tpe.name / fieldValue, tpe, meta) {
    override def withType(t: Type): EnumObject = this.copy(tpe = t)
    override def toScalaString(pad: String): String =
      s"""$pad${getClass.getSimpleName}(${tpe.toScalaString("")}, "$fieldValue", $meta)"""

  }
}

case object ParameterPlace extends Enumeration {
  type ParameterPlace = Value
  val PATH = Value("path")
  val BODY = Value("body")
  val FORM = Value("formData")
  val QUERY = Value("query")
  val HEADER = Value("header")
}

object Security {
  type OAuth2Scopes = Map[String, String]
  sealed trait Definition {
    def description: Option[String]
    def toScalaString: String = s"""${getClass.getSimpleName}(${description.map("\"" + _ + "\"")})"""
  }
  case class Basic(description: Option[String]) extends Definition
  case class ApiKey(description: Option[String], name: String, in: ParameterPlace) extends Definition {
    require(in == ParameterPlace.QUERY || in == ParameterPlace.HEADER)
    require(name.nonEmpty)
    override def toScalaString: String =
      s"""${getClass.getSimpleName}(${description.map("\"" + _ + "\"")}, "$name", ParameterPlace.withName("$in"))"""
  }
  case class OAuth2Definition(description: Option[String], validationURL: Option[URL],
      scopes: OAuth2Scopes) extends Definition {
    require(validationURL != null)
    override def toScalaString: String = {
      val scopesStr = scopes.map{case (k,v) => s""" "$k" -> "${v.replace('\n', ' ')}" """}.mkString(", ")
      s"""OAuth2Definition(${description.map("\"" + _ + "\"")}, ${validationURL.map("new URL(\"" + _ + "\")")}, Map[String, String]($scopesStr))""".stripMargin
    }
  }

  sealed trait Constraint {
    def name: String
    def definition: Definition
    def toScalaString: String = s"""${getClass.getSimpleName}("$name", ${definition.toScalaString})"""
  }
  case class BasicConstraint(name: String, definition: Definition) extends Constraint
  case class ApiKeyConstraint(name: String, definition: Definition) extends Constraint
  case class OAuth2Constraint(name: String, definition: OAuth2Definition, scopes: Set[String]) extends Constraint {
    require(scopes.forall(definition.scopes.keySet.contains))
    override def toScalaString: String = s"""${getClass.getSimpleName}("$name", ${definition.toScalaString}, Set(${scopes.map("\"" + _ + "\"").mkString(", ")}))"""
  }
  object Constraint {
    def fromDefinition(name: String, definition: Definition, scopes: Set[String]): Constraint = definition match {
      case b: Basic => BasicConstraint(name, b)
      case a: ApiKey => ApiKeyConstraint(name, a)
      case o: OAuth2Definition => OAuth2Constraint(name, o, scopes)
    }
  }
}

object Application {

  case class ParameterRef(name: Reference) {
    val simple = name.simple
    def toScalaString(pad: String): String = s"""${pad}ParameterRef(${name.toScalaString("")})"""
  }

  case class Parameter(
    name: String,
    typeName: Domain.Type,
    fixed: Option[String],
    default: Option[String],
    constraint: String,
    encode: Boolean,
    place: ParameterPlace.Value) extends Expr with Positional {
    def toScalaString: String =
      s"""Parameter("$name", ${typeName.toScalaString("")}, ${fixed.map("\"" + _ + "\"")}, ${default.map("\"" + _ + "\"")}, "$constraint", encode = $encode, ParameterPlace.withName("${place}"))"""
  }

  case class HandlerCall(
    packageName: String,
    controller: String,
    instantiate: Boolean,
    method: String,
    parameters: Seq[ParameterRef]) {
    def toScalaString(pad:String): String =
      s"""HandlerCall(\n$pad\t"$packageName",\n$pad\t"$controller",\n$pad\tinstantiate = $instantiate,\n$pad\t"$method",parameters = ${parameters.map(_.toScalaString(pad + "\t\t")).mkString(s"\n$pad\tSeq(\n", s",\n", s"\n$pad\t\t)\n")}$pad\t)"""
  }

  abstract class ResponseInfo[T] {
    def results: Map[Int, T]
    def default: Option[T]
  }

  case class TypesResponseInfo(results: Map[Int, ParameterRef], default: Option[ParameterRef]) extends ResponseInfo[ParameterRef] {
    def toScalaString(pad: String): String = {
      val resStr =
        if (results.isEmpty) "Map.empty[Int, ParameterRef]"
        else s"\n$pad\tMap[Int, ParameterRef](" + results.map{case(k,v) => s"\n$pad\t$k -> " + v.toScalaString("")}.mkString(",") + s"\n$pad)"
      s"""${pad}TypesResponseInfo($resStr, ${default.map(_.toScalaString("\t"))})"""
    }
  }
  case class StateResponseInfo(results: Map[Int, State], default: Option[State]) extends ResponseInfo[State] {
    def toScalaString(pad: String): String = {
      val resStr =
        if (results.isEmpty) "Map.empty[Int, State]"
        else s"\n$pad\tMap[Int, State](" + results.map{case(k,v) => s"\n$pad\t\t$k -> " + v.toScalaString("")}.mkString(",") + s"\n$pad)"
      s"""StateResponseInfo($resStr, ${default.map(_.toScalaString(""))})"""
    }
  }

  case class ApiCall(
    verb:             Http.Verb,
    path:             Path,
    handler:          HandlerCall,
    mimeIn:           Set[MimeType],  // can be empty for swagger specification
    mimeOut:          Set[MimeType],  // can be empty for swagger specification
    errorMapping:     Map[String, Seq[Class[_ <: Exception]]], // can be empty for swagger specification
    resultTypes:      TypesResponseInfo,
    targetStates:     StateResponseInfo,
    security: Set[Security.Constraint] = Set.empty) {
    def asReference: Reference = (path.prepend("paths") / verb.toString.toLowerCase).ref
    def toScalaString: String = {
      val errorStr = if (errorMapping.isEmpty)
        "Map.empty[String, Seq[Class[Exception]]]"
      else  errorMapping.map { case (k, v) =>
        "\"" + k + "\" -> Seq(" + v.map(_.getCanonicalName).map("classOf[" + _ + "]").mkString(", ") + ")"
      }.mkString("Map(", ", ", ")")
      val mimeInStr = if (mimeIn.isEmpty) "Set.empty[MimeType]" else mimeIn.mkString("Set(", ", ", ")")
      val mimeOutStr = if (mimeOut.isEmpty) "Set.empty[MimeType]" else mimeOut.mkString("Set(", ", ", ")")
      val securityStr =
        if (security.isEmpty) "Set.empty[Security.Constraint]"
        else security.map(_.toScalaString).mkString("Set(\n\t\t\t", ",\n\t\t\t", "\n\t\t)")
      s"""\n\tApiCall($verb, ${path.asScala}, \n\t\t${handler.toScalaString("\t\t")}, \n\t\t$mimeInStr, \n\t\t$mimeOutStr, \n\t\t$errorStr, \n${resultTypes.toScalaString("\t\t")}, \n\t\t${targetStates.toScalaString("\t\t\t")}, \n\t\t$securityStr)"""
    }
  }

  type ParameterLookupTable = Map[ParameterRef, Parameter]
  type TypeLookupTable = Map[Reference, Domain.Type]
  type DiscriminatorLookupTable = Map[Reference, Reference]

  type SecurityDefinitionsTable = Map[String, Security.Definition]

  case class StrictModel(calls: Seq[ApiCall],
       typeDefs: TypeLookupTable,
       params: ParameterLookupTable,
       discriminators: DiscriminatorLookupTable,
       basePath: String,
       packageName: Option[String],
       stateTransitions: StateTransitionsTable,
       securityDefinitionsTable: SecurityDefinitionsTable) {
    def findParameter(ref: ParameterRef): Parameter = params(ref)
    def findParameter(name: Reference): Option[Parameter] = params.find(_._1.name == name).map(_._2)
    def findType(ref: Reference): Type = typeDefs(ref)
  }

}

