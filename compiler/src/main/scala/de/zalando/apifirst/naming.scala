package de.zalando.apifirst

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
    override def asString = JsonPointer.escape(raw)
  }

  final case class Index(i: Long) extends PointerNode {
    assert(i >= 0, "index must be positive")

    override def asString = i.toString
  }

  final case class JsonPointer(values: Seq[PointerNode]) {
    def ++(that: JsonPointer): JsonPointer = {
      val append = if (that.values.headOption.exists(_ == Node(""))) that.values.tail else that.values
      JsonPointer(values ++ append)
    }

    def :+(node: PointerNode): JsonPointer = JsonPointer(values :+ node)

    def :+(nodeStr: String): JsonPointer = JsonPointer(values :+ Node(nodeStr))

    def +:(node: PointerNode): JsonPointer = JsonPointer(node +: values)

    def +:(nodeStr: String): JsonPointer = JsonPointer(Node(nodeStr) +: values)

    override def toString: String = values match {
      case Nil => ""
      case nonempty => nonempty.map(_.asString).mkString("#", "/", "")
    }

    private lazy val parentValues = values match {
      case Nil => Nil
      case _ => values.init
    }
    lazy val parent = JsonPointer(parentValues)
    lazy val simple = values.lastOption.map(_.asString).getOrElse("")
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

    def escape(str: String) = str.replace("~", "~0").replace("/", "~1")

    def unescape(str: String) = str.replace("~1", "/").replace("~0", "~")

    def apply(path: String) = {
      if (path == null || path.trim.isEmpty)
        new JsonPointer(Nil)
      else
       new JsonPointer(path dropWhile { _ == '#' } split "/" map unescape map Node toSeq)
    }

    implicit object JsonPointerOrdering extends Ordering[JsonPointer] {
      override def compare(x: JsonPointer, y: JsonPointer): Int = {
        def recurse(xs: Seq[PointerNode], ys: Seq[PointerNode]): Int = (xs, ys) match {
          case (Nil, Nil) => 0
          case (Nil, _) => -1
          case (_, Nil) => 1
          case (s1, s2) =>
            val c = PointerNodeOrdering.compare(s1.head, s2.head)
            if (c != 0) c else recurse(s1.tail, s2.tail)
        }
        recurse(x.values, y.values)
      }
    }

  }

  implicit def stringToReference: String => Reference = Reference.apply

  trait Reference {
    def pointer: JsonPointer
    lazy val simple = pointer.simple
    lazy val parent = new ReferenceObject(pointer.parent)         // TODO return the right Reference type
    def /(child: String) = new ReferenceObject(pointer :+ child)  // TODO return the right Reference type
    def :/(prefix: String) = new ReferenceObject(prefix +: pointer)  // TODO return the right Reference type
    def :/(suffix: Reference) = new ReferenceObject(pointer ++ suffix.pointer)
  }

  case class ReferenceObject(override val pointer: JsonPointer) extends Reference {
    override def toString = pointer.toString
  }

  case class RelativeSchemaFile(file: String) extends Reference {
    val pointer = JsonPointer(Nil)
    override def toString = file
  }

  case class EmbeddedSchema(file: String, override val pointer: JsonPointer) extends Reference {
    override def toString = file + pointer.toString
  }

  object Reference {
    def apply(url: String): Reference = url.indexOf('#') match {
      case 0 =>
        ReferenceObject(JsonPointer(url.tail))
      case -1 if url.indexOf(':') > 0 =>
        RelativeSchemaFile(url)
      case -1 =>
        ReferenceObject(JsonPointer(url))
      case i if i > 0 =>
        val (filePart, urlPart) = url.splitAt(i)
        EmbeddedSchema(filePart, JsonPointer(urlPart))
    }
  }

}