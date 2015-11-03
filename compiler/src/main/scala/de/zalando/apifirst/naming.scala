package de.zalando.apifirst

import de.zalando.apifirst.Application.StrictModel
import de.zalando.apifirst.Domain._
import de.zalando.apifirst.naming.newnaming.Named

import scala.language.postfixOps

/**
 * @author  slasch 
 * @since   03.11.2015.
 */
object naming {

  object _naming_ {

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

      def apply(): Name = Name.root

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

      implicit def pathNameToNameOps(name: PathName): PathNameDsl = new PathNameDsl(name)

      implicit def verbNameToNameOps(name: VerbName): VerbNameDsl = new VerbNameDsl(name)

      implicit def parmNameToNameOps(name: ParmName): ParmNameDsl = new ParmNameDsl(name)

      implicit def typeNameToNameOps(name: DomainName): DomainNameDsl = new DomainNameDsl(name)

      implicit def stringToPathName(name: String): PathNameDsl = new PathNameDsl(PathName(name))

      class PathNameDsl(val path: PathName) {
        def =|=(name: String): VerbName = VerbName(name, path)

        def =?=(name: String): ParmName = ParmName(name, path)

        def /(name: String) = this.=|=(name)
      }

      class VerbNameDsl(val verb: VerbName) {
        def =?=(name: String) = ParmName(name, verb)

        def /(name: String) = this.=?=(name)
      }

      class ParmNameDsl(nestInto: ParmName) {
        def /(name: String) = DomainName(name, nestInto)
      }

      class DomainNameDsl(val domain: DomainName) {
        def =#=(name: String) = new DomainName(name, Some(domain))
      }

    }

    abstract class Named(val parent: Option[Named]) {
      val simple: String
      val delimiter: String

      def /(name: String): Named

      lazy val qualified: String = {
        def loop(n: Named, acc: String): String = n.parent match {
          case Some(p) => loop(p, p.simple + n.delimiter + acc)
          case None => acc
        }
        loop(this, this.simple)
      }

      override def toString: String = this match {
        case n: PathName => s"PathName(${n.qualified})"
        case n: VerbName => s"VerbName(${n.qualified})"
        case n: ParmName => s"ParmName(${n.qualified})"
        case n: DomainName => s"DomainName(${n.qualified})"
      }

      def =?=(name: String): Named = this match {
        case p: PathName => new ParmName(name, p)
        case v: VerbName => new ParmName(name, v)
        case v: DomainName => new DomainName(name, Some(v))
      }
    }

    case class PathName(simple: String) extends Named(None) {
      override val delimiter: String = "/"

      def /(name: String) = VerbName(name, this)
    }

    case class VerbName(simple: String, path: PathName) extends Named(Some(path)) {
      override val delimiter: String = "|"

      def /(name: String) = ParmName(name, this)
    }

    case class ParmName(simple: String, path: Named) extends Named(Some(path)) {
      override val delimiter: String = "?"

      def /(name: String) = DomainName(name, this)
    }

    object ParmName {
      def apply(simple: String, parent: PathName): ParmName = new ParmName(simple, parent)

      def apply(simple: String, parent: VerbName): ParmName = new ParmName(simple, parent)

      def apply(simple: String, parent: ParmName): DomainName = new DomainName(simple, Some(parent))
    }

    case class DomainName(simple: String, override val parent: Option[Named]) extends Named(parent) {
      override val delimiter: String = "#"

      override def /(name: String) = DomainName(name, this)
    }

    object DomainName {
      def apply(simple: String): DomainName = new DomainName(simple, None)

      def apply(simple: String, parent: Named): DomainName = new DomainName(simple, Some(parent))
    }

  }

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
      val newSpace = namespace.replace(t.namespace, "").dropWhile(_ == SL).replaceAll(SL.toString, "._")
      val prefix = if (t.namespace.isEmpty) "" else "_"
      if (newSpace.nonEmpty) prefix + newSpace + '.' + asSimpleType else asSimpleType
    }

    def nest(name: String) = TypeName(fullName + SL + name)

    private def camelize(s: String) = if (s.isEmpty) s else s.head.toUpper + s.tail
  }

  object TypeName {
    val SL = '/'

    def apply(namespace: String, simpleName: String): TypeName = TypeName(namespace + SL + simpleName)

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

    def escapeComplexName(innerName: String): String =
      innerName.split('.').map(escape).toSeq.mkString(".")

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

}

object new_naming {

  abstract class PointerNode {
    def asString: String
  }

  final case class Node(private val raw: String) extends PointerNode {
    override def asString = raw.replace("~", "~0").replace("/", "~1")
  }

  final case class Index(i: Long) extends PointerNode {
    assert(i >= 0, "index must be positive")

    override def asString = i.toString
  }

  final case class JsonPointer(values: Seq[PointerNode]) {
    def ++(that: JsonPointer): JsonPointer = JsonPointer(values ++ that.values)

    def :+(node: PointerNode): JsonPointer = JsonPointer(values :+ node)

    def +:(node: PointerNode): JsonPointer = JsonPointer(node +: values)

    override def toString: String = values.map(_.asString).mkString("#", "/", "")
  }

  implicit object PointerNodeOrdering extends Ordering[PointerNode] {
    override def compare(x: PointerNode, y: PointerNode): Int = (x, y) match {
      case (Index(_), Node(_)) => -1
      case (Node(_), Index(_)) => 1
      case (Index(a), Index(b)) => a compare b
      case (Node(a), Node(b)) => a compare b
    }
  }

  object JsonPointer {

    def apply(path: String) = {
      if (path == null || path.trim.isEmpty)
        new JsonPointer(Nil)
      else
        new JsonPointer(path dropWhile { _ == '#' } split "/" map Node toSeq)
    }

    implicit object JsonPointerOrdering extends Ordering[JsonPointer] {
      override def compare(x: JsonPointer, y: JsonPointer): Int = {
        def recurse(xs: Seq[PointerNode], ys: Seq[PointerNode]): Int = (xs, ys) match {
          case (Nil, Nil) => 0
          case (Nil, _) => -1
          case (_, Nil) => 1
          case (h1 :: t1, h2 :: t2) => {
            val c = PointerNodeOrdering.compare(h1, h2)
            if (c != 0) c else recurse(t1, t2)
          }
        }
        recurse(x.values, y.values)
      }
    }

  }

  implicit def stringToJsonPointer: String => JsonPointer = JsonPointer.apply


  abstract class Reference(val pointer: JsonPointer) {
    def resolve(implicit ast: StrictModel): Option[TypeDef] = ???
  }

  case class ReferenceObject(override val pointer: JsonPointer) extends Reference(pointer) {
    override def toString = pointer.toString

/*
    override def resolve(implicit ast: StrictModel): Option[Type] = ast.typeDefs.find(_._2.name == name) match {
      case Some((n: Named, t: TypeDef)) => Some(t)
      case _ => None
    }
*/
  }

  case class RelativeSchemaFile(file: String) extends Reference("")

  case class EmbeddedSchema(file: String, override val pointer: JsonPointer) extends Reference(pointer)

  object Reference {
    def apply(url: String): Reference = url.indexOf('#') match {
      case 0 =>
        ReferenceObject(url.tail)
      case i if i < 0 =>
        RelativeSchemaFile(url)
      case i if i > 0 =>
        val (filePart, urlPart) = url.splitAt(i)
        EmbeddedSchema(filePart, urlPart)
    }
  }

}