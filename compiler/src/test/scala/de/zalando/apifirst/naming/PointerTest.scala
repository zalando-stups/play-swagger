package de.zalando.apifirst.naming

import de.zalando.apifirst.new_naming.{Pointer, Index, Node, Pointer$}
import org.scalatest.{MustMatchers, FunSpec}

class PointerTest extends FunSpec with MustMatchers {
  describe("JsonPointer") {

    it("must be created from a sequence of zero or more reference tokens, each prefixed  by a '/'") {
      Pointer("").tokens         mustBe Nil
      Pointer("/").tokens        mustBe Seq(Node(""))
      Pointer("//").tokens       mustBe Seq(Node(""), Node(""))
      Pointer("/foo").tokens     mustBe Seq(Node("foo"))
      Pointer("/{foo}").tokens   mustBe Seq(Node("{foo}"))
      Pointer("/foo/bar").tokens mustBe Seq(Node("foo"), Node("bar"))
      Pointer("/foo/0").tokens   mustBe Seq(Node("foo"), Index(0))
      Pointer("/0/foo").tokens   mustBe Seq(Index(0), Node("foo"))
    }

    it("must a string representation of a sequence of zero or more reference tokens, each prefixed  by a '/'") {
      Pointer(Nil).toString                           mustBe ""
      Pointer(Seq(Node(""))).toString                 mustBe "/"
      Pointer(Seq(Node(""), Node(""))).toString       mustBe "//"
      Pointer(Seq(Node("foo"))).toString              mustBe "/foo"
      Pointer(Seq(Node("foo"), Node("bar"))).toString mustBe "/foo/bar"
      Pointer(Seq(Node("foo"), Index(0))).toString    mustBe "/foo/0"
      Pointer(Seq(Index(0), Node("foo"))).toString    mustBe "/0/foo"
    }

    it("must properly unescape json string values upon creation") {
      Pointer("""/""").tokens     mustBe Seq(Node(""""""))
      Pointer("""//""").tokens    mustBe Seq(Node(""""""), Node(""""""))
      Pointer("""/a~1b""").tokens mustBe Seq(Node("""a/b"""))
      Pointer("""/c%d""").tokens  mustBe Seq(Node("""c%d"""))
      Pointer("""/e^f""").tokens  mustBe Seq(Node("""e^f"""))
      Pointer("""/g|h""").tokens  mustBe Seq(Node("""g|h"""))
      Pointer("""/i\\j""").tokens mustBe Seq(Node("""i\\j"""))
      Pointer("""/k\"l""").tokens mustBe Seq(Node("""k\"l"""))
      Pointer("""/ """).tokens    mustBe Seq(Node(""" """))
      Pointer("""/m~0n""").tokens mustBe Seq(Node("""m~n"""))
    }

    it("must return a pointer of the parent token sequence or a pointer to the whole json document if no parent exist") {
      Pointer("/foo/bar").parent mustBe Pointer("/foo")
      Pointer("/foo").parent     mustBe Pointer("")
      Pointer("//foo").parent    mustBe Pointer("/")
      Pointer("").parent         mustBe Pointer("")
    }
  }
}
