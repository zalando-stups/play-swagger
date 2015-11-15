package de.zalando.swagger

import de.zalando.ExpectedResults
import de.zalando.apifirst.Application._
import de.zalando.apifirst.Domain._
import de.zalando.apifirst._
import de.zalando.apifirst.new_naming.Reference
import org.scalatest.{FunSpec, MustMatchers}
import new_naming.dsl._

/**
  * @author  slasch
  * @since   15.11.2015.
  */
class TypeFlattenerTest extends FunSpec with MustMatchers with ExpectedResults {

  private val noMeta = TypeMeta(None)

  private val reference1: Reference = "paths/users/get/limit"

  private val wohooo2 = TypeDef("wohooo2", Seq(Field("d", Null(noMeta))), noMeta)

  private val fields: Seq[Field] = Seq(
    Field("a", Lng(noMeta)),
    Field("b", Opt(Str(None, noMeta), noMeta)),
    Field("c", wohooo2)
  )
  private val wohooo1 = TypeDef("wohooo1", fields, noMeta)

  describe("TypeFlattener") {
    it("should flatten nested Opt types") {
      val nested = Map[Reference, Type](
        reference1 -> Opt(Opt(Intgr(None), noMeta), noMeta)
      )
      val flat = TypeFlattener(StrictModel(Nil, nested, Map.empty, Map.empty))
      flat.typeDefs.size mustBe 2
      flat.typeDefs mustBe Map(
        reference1 -> Opt(TypeReference(reference1 / "Opt"), noMeta),
        reference1 / "Opt" -> Opt(Intgr(noMeta), noMeta))
    }

    it("should flatten nested Arr types") {
      val nested = Map[Reference, Type](
        reference1 -> Arr(Arr(Arr(Intgr(None), noMeta), noMeta), noMeta)
      )
      val flat = TypeFlattener(StrictModel(Nil, nested, Map.empty, Map.empty))
      flat.typeDefs.size mustBe 3
      flat.typeDefs mustBe Map(
        reference1 -> Arr(TypeReference(reference1 / "Arr"), noMeta),
        reference1 / "Arr" -> Arr(TypeReference(reference1 / "Arr" / "Arr"), noMeta),
        reference1 / "Arr" / "Arr" -> Arr(Intgr(noMeta), noMeta))
    }

    it("should flatten CatchAll types") {
      val nested = Map[Reference, Type](
        reference1 -> CatchAll(Opt(Intgr(None), noMeta), noMeta)
      )
      val flat = TypeFlattener(StrictModel(Nil, nested, Map.empty, Map.empty))
      flat.typeDefs.size mustBe 2
      flat.typeDefs mustBe Map(
        reference1 -> CatchAll(TypeReference(reference1 / "CatchAll"), noMeta),
        reference1 / "CatchAll" -> Opt(Intgr(noMeta), noMeta)
      )
    }

    it("should flatten OneOf types") {
      val nested = Map[Reference, Type](
        reference1 -> OneOf(reference1, noMeta,
          Seq(
            Opt(Intgr(None), noMeta),
            Arr(Str(None, None), noMeta)
          )
        ))
      val flat = TypeFlattener(StrictModel(Nil, nested, Map.empty, Map.empty))
      flat.typeDefs.size mustBe 3
      flat.typeDefs mustBe Map(
        reference1 -> OneOf(reference1, noMeta,
          Seq(
            TypeReference(reference1 / "OneOf0"),
            TypeReference(reference1 / "OneOf1")
          )
        ),
        reference1 / "OneOf0" -> Opt(Intgr(None), noMeta),
        reference1 / "OneOf1" -> Arr(Str(None, None), noMeta)
      )
    }

    it("should flatten AllOf types") {
      val nested = Map[Reference, Type](
        reference1 -> AllOf(reference1, noMeta,
          Seq(wohooo2, TypeDef("wohooo3", fields, noMeta))
        ))
      val flat = TypeFlattener(StrictModel(Nil, nested, Map.empty, Map.empty))
      flat.typeDefs.size mustBe 5
      flat.typeDefs mustBe Map(
        reference1 -> AllOf(reference1, noMeta, List(TypeReference(reference1 / "AllOf0"), TypeReference(reference1 / "AllOf1"))),
        reference1 / "AllOf0" -> wohooo2,
        reference1 / "AllOf1" ->
          TypeDef("wohooo3",
            Seq(
              Field(Reference("a"), Lng(noMeta)),
              Field(Reference("b"), TypeReference("wohooo3" / "b")),
              Field(Reference("c"), TypeReference("wohooo3" / "c"))
            ), noMeta),
        "wohooo3" / "b" -> Opt(Str(None, noMeta), noMeta),
        "wohooo3" / "c" -> wohooo2
      )
    }

    it("should flatten TypeDefs") {

      val nested = Map[Reference, Type](
        reference1 -> wohooo1
      )
      val flat = TypeFlattener(StrictModel(Nil, nested, Map.empty, Map.empty))
      flat.typeDefs.size mustBe 3
      flat.typeDefs mustBe Map(
        reference1 -> TypeDef("wohooo1",
          Seq(
            Field("a", Lng(noMeta)),
            Field("b", TypeReference("wohooo1" / "b")),
            Field("c", TypeReference("wohooo1" / "c"))
          ), noMeta),
        "wohooo1" / "b" -> Opt(Str(None, TypeMeta(None, List())), TypeMeta(None, List())),
        "wohooo1" / "c" -> wohooo2
      )
    }

  }
}
