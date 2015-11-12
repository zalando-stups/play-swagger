package de.zalando.swagger

import de.zalando.ExpectedResults
import de.zalando.apifirst.Application._
import de.zalando.apifirst.Domain._
import de.zalando.apifirst.{Application, ParameterPlace, TypeDeduplicator}
import de.zalando.apifirst.new_naming.{Reference, TypeName}
import org.scalatest.{FunSpec, MustMatchers}

class TypeDeduplicatorTest extends FunSpec with MustMatchers with ExpectedResults {

  describe("TypeDeduplicator") {
    it("should deduplicate container Opt types") {
      testContainerType(Opt.apply)
    }
    it("should deduplicate container CatchAll types") {
      testContainerType(CatchAll.apply)
    }
    it("should deduplicate composition OneOf types") {
      testCompositionType(OneOf.apply)
    }
    it("should deduplicate composition AllOf types") {
      testCompositionType(OneOf.apply)
    }
    it("should deduplicate TypeReferences") {
      testTypeReferences(TypeReference.apply)
    }
    it("should deduplicate TypeDefs") {
      testTypeDef(TypeDef.apply)
    }
    it("should choose types with discriminators if possible") {
      testDiscriminator(TypeDef.apply)
    }
  }

  private val reference1: Reference = Reference("/paths/users/get/limit")
  private val reference2: Reference = Reference("/definitions/queryLimit")

  def testContainerType[T](constructor: (Type, TypeMeta) => Type): Unit = {
    val duplicates = Map[Reference, Type](
      reference1 -> constructor(Intgr(None), TypeMeta(None)),
      reference2 -> constructor(Intgr(None), TypeMeta(Some("Limit for search queries")))
    )
    checkExpectations(duplicates)
  }

  def testCompositionType[T](constructor: (TypeName, TypeMeta, Seq[Type]) => Type): Unit = {
    val descendants: Seq[Type] = Seq(
      TypeReference(Reference("a")),TypeReference(Reference("b")),TypeReference(Reference("c"))
    )
    val duplicates = Map[Reference, Type](
      reference1 -> constructor(reference1, TypeMeta(None), descendants),
      reference2 -> constructor(Reference("definitions/limit"), TypeMeta(Some("Limit for search queries")), descendants)
    )
    checkExpectations(duplicates)
  }
  def testTypeReferences(constructor: Reference => TypeReference): Unit = {
    val duplicates = Map[Reference, Type](
      reference1 -> constructor(Reference("/wohooo")),
      reference2 -> constructor(Reference("/wohooo"))
    )
    checkExpectations(duplicates)
  }

  def testTypeDef(constructor: (TypeName, Seq[Field], TypeMeta) => TypeDef): Unit = {
    val fields: Seq[Field] = Seq(
      Field(Reference("a"), Lng(TypeMeta(None))),
      Field(Reference("b"), Str(None, TypeMeta(None))),
      Field(Reference("c"), Date(TypeMeta(None)))
    )

    val duplicates = Map[Reference, Type](
      reference1 -> constructor(Reference("/wohooo1"), fields,  TypeMeta(None)),
      reference2 -> constructor(Reference("/wohooo2"), fields, TypeMeta(Some("Limit for search queries")))
    )
    checkExpectations(duplicates)
  }

  def testDiscriminator(constructor: (TypeName, Seq[Field], TypeMeta) => TypeDef): Unit = {
    val longName = "/very/deep/nested/type/with/discriminator"
    val fields: Seq[Field] = Seq(
      Field(Reference("a"), Lng(TypeMeta(None))),
      Field(Reference("b"), Str(None, TypeMeta(None))),
      Field(Reference("c"), Date(TypeMeta(None)))
    )
    val discriminators = Map(
      Reference(longName) -> "root"
    )
    val duplicates = Map[Reference, Type](
      Reference(longName) -> constructor(Reference(longName), fields,  TypeMeta(None)),
      reference1 -> constructor(Reference("/definitions/nothing-special"), fields, TypeMeta(Some("Limit for search queries")))
    )
    checkExpectations(duplicates, discriminators)
  }

  def checkExpectations[T](types: Map[Reference, Type], discriminators: Application.DiscriminatorLookupTable = Map.empty): Unit = {
    val params: ParameterLookupTable = Map(
      ParameterRef(reference1)  -> Parameter("limit1", TypeReference(reference1), None, None, "", encode = false, ParameterPlace.BODY),
      ParameterRef(reference2)  -> Parameter("limit1", TypeReference(reference2), None, None, "", encode = false, ParameterPlace.BODY)
    )

    val expectedTypes = types - reference1
    val model = StrictModel(Nil, types, params, discriminators)
    val result = TypeDeduplicator(model)
    result.typeDefs mustBe expectedTypes

    // for discriminators check, params are different
    if (discriminators.isEmpty)
      result.params.foreach(_._2.typeName.asInstanceOf[TypeReference].name mustBe reference2)
  }

}
