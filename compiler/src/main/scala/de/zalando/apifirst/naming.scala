package de.zalando.apifirst

import java.net.URI

import scala.language.postfixOps

/**
 * @author  slasch 
 * @since   03.11.2015.
 */
object new_naming {

  type TypeName = Reference

  abstract class PointerNode {
    def asString: String
  }

  final case class Node(private val raw: String) extends PointerNode {
    override def asString = Pointer.escape(raw)
  }

  final case class Index(i: Long) extends PointerNode {
    require(i >= 0, "index must be positive")
    override def asString = i.toString
  }

  final case class Pointer(tokens: Seq[PointerNode]) {
    def ++(that: Pointer): Pointer = Pointer(tokens ++ that.tokens)
    def :+(node: PointerNode): Pointer = Pointer(tokens :+ node)
    def :+(node: String): Pointer      = Pointer(tokens :+ Node(node))
    def :+(node: Long): Pointer        = Pointer(tokens :+ Index(node))
    def +:(node: PointerNode): Pointer = Pointer(node +: tokens)
    def +:(node: String): Pointer      = Pointer(Node(node) +: tokens)
    def +:(node: Long): Pointer        = Pointer(Index(node) +: tokens)

    override def toString: String = tokens match {
      case Nil => ""
      case _   => tokens.map(_.asString).mkString("/", "/", "")
    }

    lazy val parent: Option[Pointer] = tokens match {
      case Nil => None
      case _   => Some(Pointer(tokens.init))
    }

    lazy val simple = tokens.lastOption.map(_.asString).getOrElse("")
  }

  object Pointer {
    def escape(str: String) = str.replace("~", "~0").replace("/", "~1")
    def unescape(str: String) = str.replace("~1", "/").replace("~0", "~")

    def apply(path: String): Pointer = {
      require(path == null || path.isEmpty || path.startsWith("/"),
        s"must be created from zero or more reference tokens prefixed by a '/', but found: $path")

      val tokens: Seq[PointerNode] = if (path.isEmpty)
        Nil
      else {
        def isAllDigits(token: String) = (token.length > 0) && (token forall Character.isDigit)
        path drop(1) split '/' map unescape map { token =>
          if (isAllDigits(token)) Index(token.toLong) else Node(token)
        }
      }
      Pointer(tokens)
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

  implicit def stringToReference: String => Reference = Reference.apply

  trait Reference {
    def pointer: Pointer
    lazy val simple = pointer.simple
    lazy val parent = new ReferenceObject(pointer.parent.get)         // TODO return the right Reference type
    def /(child: String) = new ReferenceObject(pointer :+ child)  // TODO return the right Reference type
    def :/(prefix: String) = new ReferenceObject(prefix +: pointer)  // TODO return the right Reference type
    def :/(suffix: Reference) = new ReferenceObject(pointer ++ suffix.pointer)
  }

  case class ReferenceObject(override val pointer: Pointer) extends Reference {
    override def toString = pointer.toString
  }

  object Reference {
    def apply(url: String): Reference = url.indexOf('#') match {
      case 0 =>
        ReferenceObject(Pointer(url.tail))
      case -1 =>
        ReferenceObject(Pointer(url))
    }
  }

}