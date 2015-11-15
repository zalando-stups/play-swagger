package de.zalando.apifirst

import java.net.URI

import de.zalando.apifirst.new_naming.dsl.NameDsl

import scala.language.postfixOps

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
    val namespace = if (parts.isEmpty) root else Reference(parts.init)
    override def toString = qualified
    // FIXME helper methods to simplify migration from JsonPointer
    def /(part: String) = new NameDsl(this) / part
    def /(part: Reference) = new NameDsl(this) / part
    def prepend(part: String) = Reference(part :: parts)
    val tokens = parts
    val pointer = this
    val parent = namespace
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