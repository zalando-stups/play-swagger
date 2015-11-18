package de.zalando.apifirst.generators

import de.zalando.apifirst.Application.DiscriminatorLookupTable
import de.zalando.apifirst.Domain._
import de.zalando.apifirst.new_naming.dsl._
import org.scalatest.{FunSpec, MustMatchers}

import scala.language.implicitConversions

/**
  * @author  slasch 
  * @since   18.11.2015.
  */
class ScalaTestDataGeneratorTest extends FunSpec with MustMatchers {

  describe("ScalaTestDataGeneratorTest") {
    it("should generate nothing for empty model") {
      new ScalaTestDataGenerator(Map.empty)("test") mustBe ""
    }

    it("should generate single type alias for an option") {
      val model = Map(
        "definitions" / "Opti" -> Opt(Lng(None), None),
        "definitions" / "Stri" -> Opt(Str(None, None), None)
      )
      new ScalaTestDataGenerator(model)("test") mustBeAs
        """package test
          |import org.scalacheck.Gen
          |import org.scalacheck.Arbitrary._
          |object definitionsGenerator {
          |   def createOptiGenerator = _generate(OptiGenerator)
          |   def createStriGenerator = _generate(StriGenerator)
          |   val OptiGenerator = Gen.option(arbitrary[Long])
          |   val StriGenerator = Gen.option(arbitrary[String])
          |   def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
          |}"""
    }

    // TODO something should be done with these names
    it("should deal with overriden type definitions") {
      val model = Map(
        "definitions" / "Option" -> Opt(Lng(None), None),
        "definitions" / "String" -> Opt(Str(None, None), None)
      )
      new ScalaTestDataGenerator(model)("overloaded") mustBeAs
        """package overloaded
          |import org.scalacheck.Gen
          |import org.scalacheck.Arbitrary._
          |object definitionsGenerator {
          |   def createOptionGenerator = _generate(OptionGenerator)
          |   def createStringGenerator = _generate(StringGenerator)
          |   val OptionGenerator = Gen.option(arbitrary[Long])
          |   val StringGenerator = Gen.option(arbitrary[String])
          |   def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
          |}"""
    }

    it("should generate single type alias for an array") {
      val model = Map(
        "definitions" / "Int" -> Arr(Intgr(None), None),
        "definitions" / "Dbl" -> Arr(Dbl(None), None),
        "definitions" / "Flt" -> Arr(Flt(None), None)
      )
      new ScalaTestDataGenerator(model)("test") mustBeAs
        """package test
          |import org.scalacheck.Gen
          |import org.scalacheck.Arbitrary._
          |object definitionsGenerator {
          |   def createIntGenerator = _generate(IntGenerator)
          |   def createDblGenerator = _generate(DblGenerator)
          |   def createFltGenerator = _generate(FltGenerator)
          |   val IntGenerator = Gen.containerOf[List,Int](arbitrary[Int])
          |   val DblGenerator = Gen.containerOf[List,Double](arbitrary[Double])
          |   val FltGenerator = Gen.containerOf[List,Float](arbitrary[Float])
          |   def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
          |}"""
    }

    it("should generate single type alias for catchAll") {
      val model = Map(
        "parameters" / "all" -> CatchAll(Bool(None), None)
      )
      new ScalaTestDataGenerator(model)("test") mustBeAs
        """package test
          |import org.scalacheck.Gen
          |import org.scalacheck.Arbitrary._
          |object parametersGenerator {
          |   def createAllGenerator = _generate(AllGenerator)
          |   val AllGenerator = Gen.const(Boolean)
          |   def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
          |}"""
    }

    it("should generate single type alias for top-level primitive type") {
      val model = Map(
        "paths" / "/" / "get" / "responses" / "200" -> Null(None)
      )
      new ScalaTestDataGenerator(model)("test") mustBeAs ""
    }

    it("should generate a class file for typeDef") {
      val fields = Seq(
        Field("definitions" / "User" / "name", Str(None, None)),
        Field("definitions" / "User" / "id", Lng(None))
      )
      val model = Map(
        "definitions" / "User" -> TypeDef("definitions" / "User", fields, None)
      )
      new ScalaTestDataGenerator(model)("test") mustBeAs
        """package test
          |import org.scalacheck.Gen
          |import org.scalacheck.Arbitrary._
          |object definitionsGenerator {
          |   def createUserGenerator = _generate(UserGenerator)
          |   val  UserGenerator =
          |     for {
          |       name <- arbitrary[String]
          |       id <- arbitrary[Long]
          |     } yield UserGenerator(name, id)
          |   def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
          |}"""
    }

    it("should generate a type alias for the TypeReference") {
      val model = Map(
        "definitions" / "OptionalData" -> Opt(TypeReference("definitions" / "Passwords"), None),
        "definitions" / "Passwords" -> Arr(Password(None), None, None)
      )
      new ScalaTestDataGenerator(model)("test") mustBeAs
        """package test
          |import org.scalacheck.Gen
          |import org.scalacheck.Arbitrary._
          |object definitionsGenerator {
          |   import definitions.Passwords
          |   def createOptionalDataGenerator = _generate(OptionalDataGenerator)
          |   def createPasswordsGenerator = _generate(PasswordsGenerator)
          |   val OptionalDataGenerator = Gen.option(PasswordsGenerator)
          |   val PasswordsGenerator = Gen.containerOf[List,String](arbitrary[String])
          |   def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
          |}"""
    }

    it("should generate a complex polymorphic hierarchy") {
      val model = Map(
        "definitions" / "Cat" ->
          AllOf("definitions" / "Cat", None,
            Seq(TypeReference("definitions" / "Pet"),
              TypeReference("definitions" / "Cat" / "AllOf1")), Some("definitions" / "Pet")),
        "definitions" / "Dog" ->
          AllOf("definitions" / "Dog", None,
            Seq(TypeReference("definitions" / "Pet"),
              TypeReference("definitions" / "Dog" / "AllOf1")), Some("definitions" / "Pet")),
        "definitions" / "CatNDog" ->
          AllOf("definitions" / "CatNDog", None,
            Seq(TypeReference("definitions" / "Dog"),
              TypeReference("definitions" / "Cat")), Some("definitions" / "Pet")),
        "definitions" / "Pet" ->
          TypeDef("definitions" / "Pet", Seq(
            Field("definitions" / "Pet" / "name", Str(None, None)),
            Field("definitions" / "Pet" / "petType", Str(None, None))), None),
        "definitions" / "Labrador" ->
          AllOf("definitions" / "Labrador", None,
            Seq(TypeReference("definitions" / "Dog"),
              TypeReference("definitions" / "Labrador" / "AllOf1")), Some("definitions" / "Pet")),
        "definitions" / "Cat" / "AllOf1" ->
          TypeDef("definitions" / "Cat", Seq(
            Field("definitions" / "Cat" / "huntingSkill", Str(None, None))), None),
        "definitions" / "Dog" / "AllOf1" ->
          TypeDef("definitions" / "Dog", Seq(
            Field("definitions" / "Dog" / "packSize", Intgr(None))), None),
        "definitions" / "Labrador" / "AllOf1" ->
          TypeDef("definitions" / "Labrador", Seq(
            Field("definitions" / "Labrador" / "cuteness", Intgr(None))), None)
      )
      val discriminators: DiscriminatorLookupTable = Map(
        "definitions" / "Pet" -> "definitions" / "Pet" / "petType"
      )
      val result = new ScalaTestDataGenerator(model)("test")

      result mustBeAs
        s"""package test
          |import org.scalacheck.Gen
          |import org.scalacheck.Arbitrary._
          |
          |object definitionsGenerator {
          |   def createCatGenerator = _generate(CatGenerator)
          |   def createDogGenerator = _generate(DogGenerator)
          |   def createCatNDogGenerator = _generate(CatNDogGenerator)
          |   def createPetGenerator = _generate(PetGenerator)
          |   def createLabradorGenerator = _generate(LabradorGenerator)
          |   val  CatGenerator =
          |     for {
          |       name <- arbitrary[String]
          |       petType <- arbitrary[String]
          |       huntingSkill <- arbitrary[String]
          |     } yield CatGenerator(name, petType, huntingSkill)
          |   val  DogGenerator =
          |     for {
          |       name <- arbitrary[String]
          |       petType <- arbitrary[String]
          |       packSize <- arbitrary[Int]
          |     } yield DogGenerator(name, petType, packSize)
          |   val  CatNDogGenerator =
          |     for {
          |       name <- arbitrary[String]
          |       petType <- arbitrary[String]
          |       packSize <- arbitrary[Int]
          |       huntingSkill <- arbitrary[String]
          |     } yield CatNDogGenerator(name, petType, packSize, huntingSkill)
          |   val  PetGenerator =
          |     for {
          |       name <- arbitrary[String]
          |       petType <- arbitrary[String]
          |     } yield PetGenerator(name, petType)
          |   val  LabradorGenerator =
          |     for {
          |       name <- arbitrary[String]
          |       petType <- arbitrary[String]
          |       packSize <- arbitrary[Int]
          |       cuteness <- arbitrary[Int]
          |     } yield LabradorGenerator(name, petType, packSize, cuteness)
          |   def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
          |}"""
    }

    it("should generate a simple composition") {
      val model = Map(
        "definitions" / "ErrorModel" ->
          TypeDef("definitions" / "ErrorModel", Seq(
            Field("definitions" / "ErrorModel" / "message", Str(None, None)),
            Field("definitions" / "ErrorModel" / "code", Intgr(None))), None),
        "definitions" / "ExtendedErrorModel" ->
          AllOf("definitions" / "ExtendedErrorModel", None, Seq(
            TypeReference("definitions" / "ErrorModel"),
            TypeReference("definitions" / "ExtendedErrorModel" / "AllOf1"))),
        "definitions" / "ExtendedErrorModel" / "AllOf1" ->
          TypeDef("definitions" / "ExtendedErrorModel", Seq(
            Field("definitions" / "ExtendedErrorModel" / "rootCause", Str(None, None))), None)
      )
      new ScalaTestDataGenerator(model)("test") mustBeAs
        """package test
          |import org.scalacheck.Gen
          |import org.scalacheck.Arbitrary._
          |object definitionsGenerator {
          |   def createErrorModelGenerator = _generate(ErrorModelGenerator)
          |   def createExtendedErrorModelGenerator = _generate(ExtendedErrorModelGenerator)
          |   val  ErrorModelGenerator =
          |     for {
          |       message <- arbitrary[String]
          |       code <- arbitrary[Int]
          |     } yield ErrorModelGenerator(message, code)
          |   val  ExtendedErrorModelGenerator =
          |     for {
          |       message <- arbitrary[String]
          |       code <- arbitrary[Int]
          |       rootCause <- arbitrary[String]
          |     } yield ExtendedErrorModelGenerator(message, code, rootCause)
          |   def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
          |}"""
    }

  }

  implicit def any2Comparator(any: Any): StringComparator = new StringComparator(any.toString)

  class StringComparator(s1: String) {

    def mustBeAs(s2: String): Unit = {
      cleanSpaces(s1) mustBe cleanSpaces(s2.stripMargin)
    }

    def cleanSpaces(s: String): String =
      s.split("\n").map(_.trim).filterNot(_.isEmpty).mkString("\n")
  }

}
