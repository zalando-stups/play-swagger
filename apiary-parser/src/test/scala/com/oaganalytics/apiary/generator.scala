package com.oaganalytics.apib

import org.scalatest.{FunSpec, MustMatchers}
import org.scalatest.Assertions._
import org.scalatest.Matchers._
import argonaut._, Argonaut._

import de.zalando.apifirst._
import Domain._
import Decoder._
import Utils._
import Data._

class GeneratorTest extends FunSpec with MustMatchers {
  it("should generate a string type from a StringElement") {
    json[DataStructure](dataStructure, {ds =>
      val se = ds.content.asInstanceOf[StructObject].content(0).content.value.asInstanceOf[StructObject].content(0).value.asInstanceOf[StringElement]
      Generator.transform(se, Generator.emptyMeta, naming.Reference("definitions")) mustBe(Str(None, TypeMeta(None)))
    })
  }
  it("should generate a datastructure") {
    json[DataStructure](dataStructure, {ds =>
      val res = Generator.transform(ds, naming.Reference("definitions")).asInstanceOf[TypeDef]
      res.name.parts mustBe(List("definitions"))
      res.fields.head.name.parts mustBe(List("definitions", "configSelection"))
      val typedef = res.fields.head.tpe.asInstanceOf[TypeDef]
      typedef.fields.head.tpe mustBe(Opt(Str(None, Generator.emptyMeta), TypeMeta(Some("Unique ID"))))
      typedef.fields(1).name mustBe(naming.Reference(List("definitions", "configSelection", "_singlequote_email_singlequote_")))
    })

  }
  it("should generate the Type from a resource") {
    json[Resource](resource, {resource =>
      val ds = Generator.transform(resource.content(0).asInstanceOf[DataStructure], naming.Reference("definitions"))
      ds.name.parts mustBe(List("definitions", "Play"))
    })
  }
  it("should create an ApiCall from a resource") {
    json[Resource](resource, {resource =>
      // Generator.transform(resource)
    })
  }
  describe("should parse a few hrefs") {
    List(
      "/foo" -> Href(naming.Reference(List("foo")), Set()),
      "/foo/bar" -> Href(naming.Reference(List("foo", "bar")), Set()),
      "/foo/{bar}" -> Href(naming.Reference(List("foo", "{bar}")), Set()),
      "/foo{?bar}" -> Href(naming.Reference(List("foo")), Set("bar")),
      "/" -> Href(naming.Reference(List("/")), Set())
    ).foreach({case (exp, result) =>
      it(s"should translate $exp to $result") {
        Href(exp) mustBe(result)
      }
    })
    it("should return /validate as path from /validate{?name}") {
      Href("/validate{?name}").path mustBe(naming.Reference(List("validate")))
    }
  }
  it("should generate the correct structures from a simple resource") {
    json[Resource](resource, {resource =>
      val res = Generator.transform(resource, "test")
      res.types(naming.Reference("⌿definitions⌿Play")).asInstanceOf[TypeDef].fields.head.name.parts mustBe(List("definitions", "Play", "id"))
    })
  }
  it("should generate the correct query string paths") {
    json[Transition](queryParameters, { transition =>
      Generator.transform(transition, naming.Reference("test"), "test", "testController").parameters.keys.head.name.parts mustBe(List("paths", "versions", "validate", "getbody", "name"))
    })
  }
  it("should generate a correct StrictModel") {
    json[ApibModel](document, { doc =>
      val model = Generator.model(doc, "testPackage", "testBase")
      model.typeDefs.keys should contain (naming.Reference(List("definitions", "CompanyCompany")))
      model.typeDefs(naming.Reference(List("paths", "companies", "{company}", "getbody", "200", "responseBody"))).asInstanceOf[TypeDef].fields.head.asInstanceOf[Field].tpe.asInstanceOf[TypeRef].name mustBe(naming.Reference(List("definitions", "CompanyCompany")))
    })
  }
  it("should add the post body from TransitionAttributes") {
    json[Transition](postTransition, { transition =>
      Generator.transform(transition, naming.Reference(List("test")), "test", "testController").parameters.keys.head.name.parts mustBe(List("paths", "configs", "postbody", "requestBody"))
    })
  }
}
