package de.zalando.apifirst.naming

import de.zalando.apifirst.new_naming.{Pointer, Index, Node, Pointer$}
import org.scalatest.{MustMatchers, FunSpec}

class PointerTest extends FunSpec with MustMatchers {
  describe("JsonPointer") {

    it("must be created from a sequence of zero or more reference tokens, each prefixed  by a '/'") {
      Pointer("").tokens         mustBe Nil
      Pointer("/").tokens        mustBe Seq(Node(""))
      Pointer("/foo").tokens     mustBe Seq(Node("foo"))
      Pointer("/foo/bar").tokens mustBe Seq(Node("foo"), Node("bar"))
      Pointer("/foo/0").tokens   mustBe Seq(Node("foo"), Index(0))
      Pointer("/0/foo").tokens   mustBe Seq(Index(0), Node("foo"))
    }

    it("must a string representation of a sequence of zero or more reference tokens, each prefixed  by a '/'") {
      Pointer("").toString         mustBe ""
      Pointer("/").toString        mustBe "/"
      Pointer("/foo").toString     mustBe "/foo"
      Pointer("/foo/bar").toString mustBe "/foo/bar"
      Pointer("/foo/0").toString   mustBe "/foo/0"
      Pointer("/0/foo").toString   mustBe "/0/foo"
    }

    it("must properly unescape json string values upon creation") {
      Pointer("""/""").tokens     mustBe Seq(Node(""""""))
      Pointer("""/a~1b""").tokens mustBe Seq(Node("""a/b"""))
      Pointer("""/c%d""").tokens  mustBe Seq(Node("""c%d"""))
      Pointer("""/e^f""").tokens  mustBe Seq(Node("""e^f"""))
      Pointer("""/g|h""").tokens  mustBe Seq(Node("""g|h"""))
      Pointer("""/i\\j""").tokens mustBe Seq(Node("""i\\j"""))
      Pointer("""/k\"l""").tokens mustBe Seq(Node("""k\"l"""))
      Pointer("""/ """).tokens    mustBe Seq(Node(""" """))
      Pointer("""/m~0n""").tokens mustBe Seq(Node("""m~n"""))
    }
  }
}
