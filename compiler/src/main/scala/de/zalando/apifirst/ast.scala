package de.zalando.apifirst

import de.zalando.apifirst.Application.Model
import de.zalando.apifirst.Domain.Type
import de.zalando.apifirst.Domain.naming.Name
import de.zalando.apifirst.Http.MimeType
import de.zalando.swagger.ValidationsConverter
import de.zalando.swagger.model._

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

  // TODO flesh out this hierarchy
  class MimeType(val name: String) extends Expr

  case object ApplicationJson extends MimeType("application/json")

  case class Body(value: Option[String]) extends Expr

}

object Hypermedia {

  // TODO relation should be another API call, not an url
  case class Relation(tpe: String, url: String)

}

object Domain {

  object naming {
    object dsl {
      import scala.language.implicitConversions
      implicit def stringToNameOps(s: String): NameDsl = new NameDsl(Name(s))
      implicit def nameToNameOps(name: Name): NameDsl = new NameDsl(name)
      implicit def stringToName(s: String): Name = Name(s)
      class NameDsl(val name: Name) {
        def /(pp: String) = name.copy(parts = name.parts :+ pp)
        def /(other: Name) = merge(other)
        private def merge(other: Name) = name.copy(parts = name.parts ++ other.parts)
      }
    }

    case class Name(parts: List[String]) {
      import Name._
      val qualified = if (parts.isEmpty) delimiter else delimiter + parts.mkString(delimiter)
      val simple = if (parts.isEmpty) delimiter else parts.last
      val namespace = if (parts.isEmpty) root else Name(parts.init)
      override def toString = qualified
    }

    object Name {
      val delimiter = "/"
      val root: Name = Name(List.empty)
      def apply(s: String): Name = parse(s)
      def apply()         : Name = Name.root
      private def parse(s: String) = {
        val normalized = s.dropWhile(_.toString == delimiter).split(delimiter, -1).toList
        val parts = if (normalized.length == 1 && normalized.head.isEmpty) List.empty else normalized
        require(!parts.exists(_.isEmpty), "empty names are not allowed: " + parts.mkString("[", ",", "]"))
        Name(parts)
      }
    }
  }

  object newnaming {
    object dsl {

      import scala.language.implicitConversions

      implicit def pathNameToNameOps(name: PathName):   PathNameDsl   = new PathNameDsl(name)
      implicit def verbNameToNameOps(name: VerbName):   VerbNameDsl   = new VerbNameDsl(name)
      implicit def typeNameToNameOps(name: DomainName): DomainNameDsl = new DomainNameDsl(name)

      class PathNameDsl(val path: PathName) {
        def =|=(name: String): VerbName = VerbName(name, path)
        def =?=(name: String): ParmName = ParmName(name, path)
      }

      class VerbNameDsl(val verb: VerbName) {
        def =?=(name: String) = ParmName(name, verb)
      }

      class DomainNameDsl(val domain: DomainName) {
        def =#=(name: String) = DomainName(name, Some(domain))
      }
    }

    abstract class Named(val parent: Option[Named]) {
      val simple   : String
      val delimiter: String
      lazy val qualified: String = {
        def loop(n: Named, acc: String): String = n.parent match {
          case Some(p) => loop(p, p.simple + n.delimiter + acc)
          case None    => acc
        }
        loop(this, this.simple)
      }
      override def toString: String = this match {
        case n: PathName   => s"PathName(${ n.qualified })"
        case n: VerbName   => s"VerbName(${ n.qualified })"
        case n: ParmName   => s"ParmName(${ n.qualified })"
        case n: DomainName => s"DomainName(${ n.qualified })"
      }
    }

    case class PathName(simple: String) extends Named(None) {
      override val delimiter: String = "/"
    }

    case class VerbName(simple: String, path: PathName) extends Named(Some(path)) {
      override val delimiter: String = "|"
    }

    case class ParmName(simple: String, override val parent: Option[Named]) extends Named(parent) {
      override val delimiter: String = "?"
    }

    object ParmName {
      def apply(simple: String, parent: PathName): ParmName = ParmName(simple, Some(parent))
      def apply(simple: String, parent: VerbName): ParmName = ParmName(simple, Some(parent))
    }

    case class DomainName(simple: String, override val parent: Option[Named]) extends Named(parent) {
      override val delimiter: String = "#"
    }

    object DomainName {
      def apply(simple: String): DomainName = DomainName(simple, None)
      def apply(simple: String, parent: Named): DomainName = DomainName(simple, Some(parent))
    }
  }


  object relations {
    object InheritanceStrategy extends Enumeration {
      type InheritanceStrategy = Value
      val Concrete, Mixin, Abstract = Value
    }

    import InheritanceStrategy._

    sealed trait ClassRelation

    case class HierarchyRoot(inheritanceStrategy: InheritanceStrategy, tpe: TypeDef) extends ClassRelation

    case class HierarchyDescendant(root: HierarchyRoot, tpe: TypeDef) extends ClassRelation

    case class Composition(parts: Seq[TypeDef]) extends ClassRelation
  }

  type ModelDefinition = Iterable[Domain.Type]

  case class TypeName(fullName: String) {
    import TypeName.SL
    val simpleName = fullName.trim.takeRight(fullName.length - fullName.lastIndexOf(SL) - 1)
    val nestedNamespace = "_" + simpleName
    val asSimpleType = camelize(simpleName)
    val namespace = fullName.trim.take(fullName.lastIndexOf(SL)).toLowerCase
    val oneUp = {
      val space = Option(namespace).flatMap(_.split(SL).filter(_.nonEmpty).drop(1).lastOption).getOrElse("")
      (if (space.nonEmpty) "_" + space + "." else "") + asSimpleType
    }

    def nestedIn(t: TypeName) = namespace.nonEmpty && t.fullName.toLowerCase.startsWith(namespace)
    def relativeTo(t: TypeName) = {
      val newSpace = namespace.replace(t.namespace,"").dropWhile(_ == SL).replaceAll(SL.toString, "._")
      val prefix = if (t.namespace.isEmpty) "" else "_"
      if (newSpace.nonEmpty) prefix + newSpace + '.' + asSimpleType else asSimpleType
    }
    def nest(name: String) = TypeName(fullName + SL + name)
    private def camelize(s: String) = if (s.isEmpty) s else s.head.toUpper + s.tail
  }
  object TypeName {
    val SL = '/'
    def apply(namespace: String, simpleName: String):TypeName = TypeName(namespace + SL + simpleName)
    def escapeName(name: String) = {
      val SimpleInnerPattern = """.*(?:Seq|Option)\[([^\]\[]+)\].*""".r
      val ComplexInnerPattern = """.*(?:Map)\[String,([^\]\[]+)\].*""".r
      name match {
        case SimpleInnerPattern(innerName) =>
          name.replace(innerName, escapeComplexName(innerName))
        case ComplexInnerPattern(innerName) =>
          ComplexInnerPattern.replaceFirstIn(name, name.replace(innerName, escapeComplexName(innerName)))
        case _ =>
          escapeComplexName(name)
      }
    }

    def escapeComplexName(innerName: String): String = {
      innerName.split('.').map(escape).toSeq.mkString(".")
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

  case class TypeMeta(comment: Option[String], constraints: Seq[String]) {
    override def toString = s"""TypeMeta(${escapedComment}, ${constraints}"""

    val escapedComment = comment match {
      case None => "None"
      case Some(s) => "Some(\"" + s.replaceAll("\"", "\\\"") + "\")"
    }
  }

  object TypeMeta {
    def apply(comment: Option[String]): TypeMeta = TypeMeta(comment, Seq.empty[String])
  }

  implicit def option2TypeMeta(o: Option[String]): TypeMeta = TypeMeta(o)

  implicit def schema2TypeMeta(s: Schema): TypeMeta = {
    new TypeMeta(Option(s.description), ValidationsConverter.toValidations(s))
  }

  implicit def typeInfo2TypeMeta(s: TypeInfo): TypeMeta = Option(s.format)

  implicit def paramOrRefInfo2TypeMeta(s: ParameterOrReference): TypeMeta = s match {
    case s: Parameter =>
      new TypeMeta(Option(s.description), ValidationsConverter.toValidations(s))
    case s: ParameterReference =>
      Option(s.$ref)
  }

  abstract class Type(val name: TypeName, val meta: TypeMeta) extends Expr {
    def nestedTypes: Seq[Type] = Nil
    def imports: Set[String] = Set.empty
  }

  implicit def string2TypeName(s: String): TypeName = TypeName(s)

  class Nmbr(override val name: TypeName, override val meta: TypeMeta) extends Type(name, meta)

  case class Int(override val meta: TypeMeta) extends Nmbr("Int", meta)

  case class Lng(override val meta: TypeMeta) extends Nmbr("Long", meta)

  case class Flt(override val meta: TypeMeta) extends Nmbr("Float", meta)

  case class Dbl(override val meta: TypeMeta) extends Nmbr("Double", meta)

  case class Byt(override val meta: TypeMeta) extends Nmbr("Byte", meta)

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

  case class Any(override val meta: TypeMeta) extends Type("Any", meta)

  abstract class Container(override val name: TypeName, val tpe: Type, override val meta: TypeMeta, override val imports: Set[String])
    extends Type(name, meta) {
    def allImports: Set[String] = imports ++ tpe.imports
    override def nestedTypes = tpe.nestedTypes :+ tpe
  }

  case class Arr(override val tpe: Type, override val meta: TypeMeta, format: Option[String] = None)
    extends Container(s"Seq[${tpe.name.oneUp}]", tpe, meta, Set("scala.collection.Seq"))

  case class Opt(override val tpe: Type, override val meta: TypeMeta)
    extends Container(s"Option[${tpe.name.oneUp}]", tpe, meta, Set("scala.Option"))

  case class CatchAll(override val tpe: Type, override val meta: TypeMeta)
    extends Container(s"Map[String, ${tpe.name.oneUp}]", tpe, meta, Set("scala.collection.immutable.Map"))

  case class Field(name: TypeName, tpe: Type, meta: TypeMeta){
    override def toString = s"""Field("$name", $tpe, $meta)"""

    def imports = tpe match {
      case c: Container => c.allImports
      case o => o.imports
    }

    def nestedTypes = tpe.nestedTypes :+ tpe
  }

  case class TypeDef(override val name: TypeName,
                     fields: Seq[Field],
                     extend: Seq[Reference] = Nil,
                     override val meta: TypeMeta) extends Type(name, meta) {
    override def toString = s"""\n\tTypeDef("$name", List(${fields.mkString("\n\t\t", ",\n\t\t", "")}), $extend, $meta)\n"""

    def imports(implicit ast: Model): Set[String] = {
      val fromFields = fields.flatMap(_.imports)
      val transient = extend.flatMap(_.resolve(ast).toSeq.flatMap(_.imports))
      (fromFields ++ transient).filter(_.trim.nonEmpty).toSet
    }

    def allFields(implicit ast: Model): Seq[Field] =
      fields ++ extend.flatMap(_.resolve.toSeq.flatMap(_.allFields))

    def allExtends(implicit ast: Model): Seq[Reference] =
      extend ++ extend.flatMap(_.resolve.toSeq.flatMap(_.allExtends))

    override def nestedTypes = fields flatMap (_.nestedTypes) filter { _.name.nestedIn(name) } distinct
  }

  abstract class Reference(override val name: TypeName, override val meta: TypeMeta) extends Type(name, meta) {
    def resolve(implicit ast: Model): Option[TypeDef] = ???
  }

  case class ReferenceObject(override val name: TypeName, override val meta: TypeMeta) extends Reference(name, meta) {
    override def toString = s"""ReferenceObject("$name", $meta)"""
    override def resolve(implicit ast: Model): Option[TypeDef] = ast.definitions.find(_.name == name) match {
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

  case class FullPath(value: Seq[PathElem]) extends Expr {
    def isAbsolute = value match {
      case Root :: segments => true
      case _ => false
    }
    override val toString = string { p: InPathParameter => "{" + p.value + "}" }

    val camelize = string("by/" + _.value).split("/") map { p =>
      if (p.nonEmpty) p.head.toUpper +: p.tail else p
    } mkString ""

    def string(inPath2Str: InPathParameter => String) = {
      value match {
        case Nil => ""
        case Root :: Nil => "/"
        case other => other map {
          case Root => ""
          case Segment(v) => v
          case i: InPathParameter => inPath2Str(i)
        } mkString "/"
      }
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
        case seg if seg.isEmpty =>
          Some(Root)
      }
    }
    val segments = path2segments(path, parameters).toList.flatten
    val elements = if (path.endsWith("/")) segments :+ Root else segments
    FullPath(elements)
  }

}

object Query {

  case class QueryParam(name: String, value: String) extends Expr

  case class FullQuery(values: QueryParam*) extends Expr

}

case object ParameterPlace extends Enumeration {
  type ParameterPlace = Value
  val PATH    = Value("path")
  val BODY    = Value("body")
  val FORM    = Value("formData")
  val QUERY   = Value("query")
  val HEADER  = Value("header")
}

object Application {

  // Play definition
  case class Parameter(
    name:             Name,
    typeName:         Domain.Type,
    fixed:            Option[String],
    default:          Option[String],
    constraint:       String,
    encode:           Boolean,
    place:            ParameterPlace.Value
  ) extends Expr with Positional

  case class HandlerCall(
    packageName:      String,
    controller:       String,
    instantiate:      Boolean,
    method:           String,
    parameters:       Seq[Parameter]
  ) {
    val nonBodyParameters     = parameters.filterNot(_.place == ParameterPlace.BODY)
    val bodyParameters        = parameters.filter(_.place == ParameterPlace.BODY)
    val queryParameters       = parameters.filter(_.place == ParameterPlace.QUERY)
  }

  case class ApiCall(
    verb:             Http.Verb,
    path:             Path.FullPath,
    handler:          HandlerCall,
    mimeIn:           Set[MimeType],  // can be empty for swagger specification
    mimeOut:          Set[MimeType],  // can be empty for swagger specification
    errorMapping:     Map[String, Seq[Class[Exception]]], // can be empty for swagger specification
    resultType:       Map[String, Type]
  )

  case class Model(
    calls:            Seq[ApiCall],
    definitions:      Iterable[Domain.Type]
  )

  case class StrictModel(calls: Seq[ApiCall], definitions: Map[Name, Domain.Type])
//  case class StrictModel(calls: Seq[ApiCall], definitions: Map[String, Domain.Type], relations: Map[Name, ClassRelation])

}

