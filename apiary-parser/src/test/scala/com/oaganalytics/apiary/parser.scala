package com.oaganalytics.apib

import org.scalatest.{FunSpec, MustMatchers}
import org.scalatest.Assertions._
import argonaut._, Argonaut._
import Decoder._
import Utils._
import Data._

class ParserTest extends FunSpec with MustMatchers {

  describe("parsing members") {
    it("should parse a key/value member") {
      json[Member](shortMember, { member =>
        member.keyString mustBe Some("Content-Type")
        member.value match {
          case string: StringElement => string.content mustBe(Some("application/json"))
          case _ => assert(false, s"${member.value} should be a StringElement")
        }
      })
    }
    it("should parse a key/value member with array and attributes") {
      json[Member](longMember, { member =>
        member.key.content mustBe(Some("versions"))
        member.value match {
          case ArrayElement(content, attributes) => content match {
            case StringElement(string, attributes) => string mustBe(Some("[unique-id]"))
            case _ => assert(false)
          }
          case _ => assert(false, s"${member.value} should be an ArrayElement")
        }
      })
    }
  }

  it("should parse http headers") {
    json[HttpHeaders](headers, { header =>
      header.keyStrings(0) mustBe(Some("Content-Type"))
    })
  }

  it("should parse an asset") {
    json[Asset](asset, { asset =>
      asset.attributes.get.contentType mustBe("application/json")
      asset.content mustBe("some-stuff")
    })
  }

  it("should parse a httpResponse")  {
    json[HttpResponse](response, { response =>
      response.attributes.statusCode mustBe(200)
    })
  }

  it("should parse a httpRequest") {
    json[HttpRequest](request, { request =>
      request.method mustBe("PUT")
    })
  }

  it("should parse a short httpRequest") {
    json[HttpRequest](shortRequest, {request =>
      request.method mustBe("GET")
    })
  }

  it("should parse a httpTransaction") {
    json[HttpTransaction](transaction, { transaction =>
      transaction.request.method mustBe("GET")
    })
  }

  it("should decode a meta object") {
    json[Meta](meta, { meta =>
      meta.description mustBe(Some("Unique ID"))
    })
  }

  it("should parse an object") {
    json[StructObject](structObject, { obj =>
      obj.content(0).meta.flatMap(_.description) mustBe(Some("Unique ID"))
    })
  }

  it("should read an element with a reference") {
    json[Member](memberRef, { member =>
      member.value match {
        case Reference(ref) => ref mustBe("Config Selection")
        case _ => assert(false, s"${member.value} should be a Reference")
      }
    })
  }

  it("should read a datastructure") {
    json[DataStructure](dataStructure, { struct =>
      struct.content.asInstanceOf[StructObject].content(0).keyString mustBe(Some("configSelection"))
      struct.content.asInstanceOf[StructObject].content(0).value.asInstanceOf[StructObject].content(0).keyString mustBe(Some("activeNotificationEmail"))
    })
  }

  it("should read another member") {
    json[Member](anotherMember, { member =>
      member.keyString mustBe(Some("plays"))
    })
  }

  it("should decode a mixed response") {
    json[HttpResponse](mixedResponse, { response =>
    })
  }

  it("should decode a resource") {
    json[Resource](resource, { resource =>
      resource.href mustBe("/plays")
      resource.meta.title mustBe("Play")
      resource.content.head.asInstanceOf[DataStructure].content.asInstanceOf[StructObject].meta.get.id.get mustBe("Play")
    })
  }

  it("should decode a another resource") {
    json[Resource](anotherResource, { resource =>
      resource.href mustBe("/Pictures")
      resource.content(0).asInstanceOf[Copy].content mustBe("Pictures")
      val typecheck = resource.content(1).asInstanceOf[Transition]
    })
  }

  it("should parse an enum") {
    json[KeyValue](enum, { enum =>
      enum.value.asInstanceOf[EnumElement].attributes.get.default.get(0).content mustBe(Some("70%"))
    })
  }

  it("should parse a category") {
    json[Category](category, { category =>
      category.title mustBe("Regions")
    })
  }

  it("should parse the complex stuff") {
    json[Resource](complex, { complex =>
    })
  }

  it("should parse a literal object") {
    json[LiteralObject](literalObject, { check =>
    })
  }

  it("should parse a DataStructure that is a single object") {
    json[DataStructure](objectDS, { check =>
    })
  }

  it("should parse a single object into an object element") {
    json[Element](objectElement, { check =>
      val typecheck = check.asInstanceOf[DummyObject]
    })
  }

  it("should parse a literal array object") {
    json[Element](arrayObjectElement, { check =>
      val typecheck = check.asInstanceOf[DummyArrayObject]
    })
  }

}
