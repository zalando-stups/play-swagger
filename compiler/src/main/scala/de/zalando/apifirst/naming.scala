package de.zalando.apifirst

import java.net.URI

import de.zalando.apifirst.new_naming.Reference
import de.zalando.apifirst.new_naming.dsl.NameDsl

import scala.language.{implicitConversions, postfixOps}

/**
 * @since   03.11.2015.
 */
object new_naming {
  // FIXME helper types to simplify migration from JsonPointer
  type TypeName = Reference
  type Pointer = Reference
  object Pointer {
    def unescape(str: String) = str.replace("~1", "/").replace("~0", "~")
    def deref(jstr: String) = Reference(unescape(jstr.reverse.takeWhile(_ != '#').reverse))
  }

  implicit def uriToReference(uri: URI): Reference = {
    Reference(Option(uri.getFragment).map(_.split("/").mkString(Reference.delimiter)).getOrElse(""))
  }
  object dsl {
    import scala.language.implicitConversions
    implicit def nameToNameOps(name: Reference): NameDsl = new NameDsl(name)
    implicit def stringToName(s: String): Reference = Reference(s)
    class NameDsl(val name: Reference) {
      def /(pp: String) = name.copy(parts = name.parts :+ pp)
      def /(other: Reference) = merge(other)
      private def merge(other: Reference) = name.copy(parts = name.parts ++ other.parts)
    }
  }

  case class Reference(parts: List[String]) {
    import Reference._
    val qualified = if (parts.isEmpty) delimiter else delimiter + parts.mkString(delimiter)
    val simple = if (parts.isEmpty) delimiter else parts.last
    val parent = if (parts.isEmpty) root else Reference(parts.init)
    override def toString = qualified
    // FIXME helper methods to simplify migration from JsonPointer
    def /(part: String) = new NameDsl(this) / part
    def /(part: Reference) = new NameDsl(this) / part
    def prepend(part: String) = Reference(part :: parts)
    val tokens = parts
    val pointer = this
  }


  object Reference {
    val delimiter = "⌿"
    val root: Reference = Reference(List.empty)
    def apply(base: String, s: String): Reference = parse(s)
    def apply(base: String, s: Reference): Reference = s
    def apply(s: String): Reference = parse(s)
    def apply()         : Reference = Reference.root
    private def parse(s: String) = {
      val normalized = s.dropWhile(_.toString == delimiter).split(delimiter, -1).toList
      val parts = if (normalized.length == 1 && normalized.head.isEmpty) List.empty else normalized
//      require(!parts.exists(_.isEmpty), "empty names are not allowed: " + parts.mkString("[", ",", "]"))
      Reference(parts)
    }
  }
}

/**
  * Represents scala name converted from the ast pointer
  * This implementation makes heavily use of swagger internal
  * naming model. Probably it should be refactored to support
  * other specification formats
  */
object ScalaName extends StringUtil {
  implicit def reference2ScalaName(r: Reference): ScalaName = ScalaName(r)
  private val scalaNames = Seq(
    "abstract", "case", "do", "for", "import", "lazy", "object", "override", "return",
    "var", "while", "class", "false", "if", "new", "package", "sealed", "trait", "try",
    "private", "super", "this", "true", "type",  "def", "final", "implicit", "null",
    "protected", "throw", "val", "_", "catch", "else", "extends", "finally", "forSome",
    "match", "with", "yield", "case"
  )

  private val scalaPartNames = Seq(",", ";", ":", "=", "=>", "<-", "<:", "<%", ">:", "#", "@", "⇒", "←","+","-","[", ")", "]", "}")

  def escape(name: String) =
    if (scalaNames.contains(name) || scalaPartNames.exists(name.contains))
      "`" + name + "`"
    else
      name

}

trait StringUtil {
  def capitalize(separator: String, str: String) = {
    assert(str != null)
    str.split(separator).map { p => if (p.nonEmpty) p.head.toUpper +: p.tail else p }.mkString("")
  }

  def camelize(separator: String, str: String) = capitalize(separator, str) match {
    case p if p.isEmpty => ""
    case p => p.head.toLower +: p.tail
  }
}

case class ScalaName(ref: Reference) {
  import ScalaName._
  val parts = ref.parts.flatMap(_.split("/").filter(_.nonEmpty)) match {
    case Nil => throw new IllegalArgumentException("At least one part required to construct a name")
    case single :: Nil => "" :: removeVars(single) :: Nil
    case many => many.map(removeVars)
  }
  private def removeVars(s: String) = if (s.startsWith("{") && s.endsWith("}")) s.substring(1,s.length-2) else s
  def packageName = parts.head.toLowerCase.split("/").filter(_.nonEmpty).map(escape).mkString(".")
  def qualifiedName = packageName + "." + className
  def className = escape(capitalize("/", parts.tail.head))
  def typeAlias(suffix: String = "") = {
    val allParts = if (suffix.trim.isEmpty) parts.tail else suffix :: parts.tail
    escape(capitalize("/", allParts.mkString("/")))
  }
  def methodName = escape(camelize("/", parts.last))
  def names = (packageName, className, methodName)

}