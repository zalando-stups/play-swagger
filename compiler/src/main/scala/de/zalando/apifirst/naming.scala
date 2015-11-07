package de.zalando.apifirst

import java.net.URI

import scala.language.postfixOps

/**
 * @author  slasch 
 * @since   03.11.2015.
 */
object new_naming {

  type TypeName = Reference

  trait PointerNode {
    def asJsonString: String
  }

  final case class Node(private val token: String) extends PointerNode {
    override def asJsonString: String = Pointer.escape(token)
    override def toString: String = token
  }

  final case class Index(i: Long) extends PointerNode {
    require(i >= 0, "index must be positive")
    override def asJsonString: String = i.toString
    override def toString: String = i.toString
  }

  final case class Pointer(tokens: Seq[PointerNode]) {
    def ++(that: Pointer): Pointer     = Pointer(tokens ++ that.tokens)
    def :+(node: PointerNode): Pointer = Pointer(tokens :+ node)
    def :+(node: String): Pointer      = Pointer(tokens :+ Node(node))
    def :+(node: Long): Pointer        = Pointer(tokens :+ Index(node))
    def +:(node: PointerNode): Pointer = Pointer(node +: tokens)
    def +:(node: String): Pointer      = Pointer(Node(node) +: tokens)
    def +:(node: Long): Pointer        = Pointer(Index(node) +: tokens)

    def asJsonString() = if (tokens.isEmpty) "" else tokens.map(_.asJsonString).mkString("/", "/", "")

    override def toString = if (tokens.isEmpty) "" else tokens.map(_.toString).mkString("/", "/", "")

    lazy val parent: Pointer = tokens match {
      case Nil => Pointer(Nil)
      case _   => Pointer(tokens.init)
    }

    lazy val simple = tokens.lastOption.map(_.toString).getOrElse("")
  }

  object Pointer {
    def escape(str: String) = str.replace("~", "~0").replace("/", "~1")
    def unescape(str: String) = str.replace("~1", "/").replace("~0", "~")

    def apply(path: String): Pointer = {
      require(path != null)
/*
      require(path != null && (path.isEmpty || path.startsWith("/")),
        s"must be created from zero or more reference tokens prefixed by a '/', but found: $path")
*/

      // Your own parser, seriously?  I know.
      // Just got fed up trying to split('/')
      //
      // "/"      ->  Seq(Node()),
      // "//"     ->  Seq(Node(), Node()),
      // "/foo"   ->  Seq(Node(foo))
      // "/foo/"  ->  Seq(Node(foo), Node())
      //
      def parse(s: String): Seq[String] = {
        def recurse(s: String, acc: (Option[String], Seq[String])): Seq[String] = {
          s.headOption match {
            case Some('/') => acc._1 match {
              case Some(token) => recurse(s.tail, (Some(""), acc._2 :+ token))
              case None        => recurse(s.tail, (Some(""), acc._2))
            }
            case Some(c)   => acc._1 match {
              case Some(token) => recurse(s.tail, (Some(token :+ c), acc._2))
              case None        => recurse(s.tail, (Some(c.toString), acc._2))
            }
            case None      => acc._1 match {
              case Some(token) => acc._2 :+ token
              case None        => acc._2
            }
          }
        }
        recurse(s.dropWhile(_ == '#'), (None,Seq()))
      }

      def isAllDigits(token: String) = (token.length > 0) && (token forall Character.isDigit)

      Pointer(parse(path) map unescape map { token =>
        if (isAllDigits(token)) Index(token.toLong) else Node(token)
      })
    }

    def deref(jstr: String): Pointer = {
      val fragment = unescape(jstr.reverse.takeWhile(_ != '#').reverse)
      Pointer(fragment)
    }

    implicit object JsonPointerOrdering extends Ordering[Pointer] {
      override def compare(x: Pointer, y: Pointer): Int = {
        def recurse(xs: Seq[PointerNode], ys: Seq[PointerNode]): Int = (xs, ys) match {
          case (Nil, Nil) => 0
          case (Nil, _) => -1
          case (_, Nil) => 1
          case (s1, s2) =>
            val c = PointerNodeOrdering.compare(s1.head, s2.head)
            if (c != 0) c else recurse(s1.tail, s2.tail)
        }
        recurse(x.tokens, y.tokens)
      }
    }

    implicit object PointerNodeOrdering extends Ordering[PointerNode] {
      override def compare(x: PointerNode, y: PointerNode): Int = (x, y) match {
        case (Index(_), Node(_)) => -1
        case (Node(_), Index(_)) => 1
        case (Index(a), Index(b)) => a compare b
        case (Node(a), Node(b)) => a compare b
      }
    }
  }

  implicit def uriToReference: URI => Reference = Reference.apply

  case class Reference(base: String, pointer: Pointer = Pointer(Nil)) {
    require(new URI(base).isAbsolute, s"must be absolute, found: $base")
    val simple = pointer.simple
    lazy val parent = new Reference(base, pointer.parent)
    def prepend(token: String): Reference = new Reference(base, Pointer(token) ++ pointer)
    def /(token: String): Reference = if (token.isEmpty) /(Pointer(Seq(Node(token)))) else /(Pointer(token))
    def /(p: Pointer): Reference = new Reference(base, pointer ++ p)
    def /: = prepend _
    override def toString = base + Reference.percentDecodeFragmentChars(pointer.toString())
  }

  object Reference {
    def apply(uri: URI): Reference = Reference(uri.toString)
    def apply(str: String): Reference = {
      if (str.contains("#")) {
        val (base, frag) = str.splitAt(str.indexOf("#")+1)
        new Reference(base, Pointer(percentEncodeFragmentChars(frag)))
      }
      else {
        new Reference(str + '#')
      }
    }

    private[new_naming] def percentEncodeFragmentChars(fragment: String): String = fragment
    .replace("{", "%7B")
    .replace("}", "%7D")

    private[new_naming] def percentDecodeFragmentChars(fragment: String): String = fragment
    .replace("%7B", "{")
    .replace("%7D", "}")
  }
}