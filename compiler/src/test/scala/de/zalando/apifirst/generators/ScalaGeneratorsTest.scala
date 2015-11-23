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
class ScalaGeneratorsTest extends FunSpec with MustMatchers {

  describe("ScalaGeneratorTest") {
    it("should generate nothing for empty model") {
      new ScalaGenerator(Map.empty).generators("test") mustBe ""
    }

    it("should generate single type alias for an option") {
      val model = Map(
        "definitions" / "Opti" -> Opt(Lng(None), None),
        "definitions" / "Stri" -> Opt(Str(None, None), None)
      )
      new ScalaGenerator(model).generators("test") mustBeAs
        """package test
          |import org.scalacheck.Gen
          |import org.scalacheck.Arbitrary._
          |object definitionsGenerator {
          |   import definitions.Opti
          |   import definitions.Stri
          |   def createOptiGenerator = _generate(OptiGenerator)
          |   def createStriGenerator = _generate(StriGenerator)
          |   val OptiGenerator = Gen.option(arbitrary[Long])
          |   val StriGenerator = Gen.option(arbitrary[String])
          |   def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
          |   def _genMap[K,V](keyGen: Gen[K], valGen: Gen[V]): Gen[Map[K,V]] = for {
          |     keys <- Gen.containerOf[List,K](keyGen)
          |     values <- Gen.containerOfN[List,V](keys.size, valGen)
          |   } yield keys.zip(values).toMap
          |}"""
    }

    // TODO something should be done with these names
    it("should deal with overriden type definitions") {
      val model = Map(
        "definitions" / "Option" -> Opt(Lng(None), None),
        "definitions" / "String" -> Opt(Str(None, None), None)
      )
      new ScalaGenerator(model).generators("overloaded") mustBeAs
        """package overloaded
          |import org.scalacheck.Gen
          |import org.scalacheck.Arbitrary._
          |object definitionsGenerator {
          |   import definitions.Option
          |   import definitions.String
          |   def createOptionGenerator = _generate(OptionGenerator)
          |   def createStringGenerator = _generate(StringGenerator)
          |   val OptionGenerator = Gen.option(arbitrary[Long])
          |   val StringGenerator = Gen.option(arbitrary[String])
          |   def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
          |   def _genMap[K,V](keyGen: Gen[K], valGen: Gen[V]): Gen[Map[K,V]] = for {
          |     keys <- Gen.containerOf[List,K](keyGen)
          |     values <- Gen.containerOfN[List,V](keys.size, valGen)
          |   } yield keys.zip(values).toMap
          |}"""
    }

    it("should generate single type alias for an array") {
      val model = Map(
        "definitions" / "Int" -> Arr(Intgr(None), None),
        "definitions" / "Dbl" -> Arr(Dbl(None), None),
        "definitions" / "Flt" -> Arr(Flt(None), None)
      )
      new ScalaGenerator(model).generators("test") mustBeAs
        """package test
          |import org.scalacheck.Gen
          |import org.scalacheck.Arbitrary._
          |object definitionsGenerator {
          |   import definitions._
          |   def createIntGenerator = _generate(IntGenerator)
          |   def createDblGenerator = _generate(DblGenerator)
          |   def createFltGenerator = _generate(FltGenerator)
          |   val IntGenerator = Gen.containerOf[List,Int](arbitrary[Int])
          |   val DblGenerator = Gen.containerOf[List,Double](arbitrary[Double])
          |   val FltGenerator = Gen.containerOf[List,Float](arbitrary[Float])
          |   def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
          |   def _genMap[K,V](keyGen: Gen[K], valGen: Gen[V]): Gen[Map[K,V]] = for {
          |     keys <- Gen.containerOf[List,K](keyGen)
          |     values <- Gen.containerOfN[List,V](keys.size, valGen)
          |   } yield keys.zip(values).toMap
          |}"""
    }

    it("should generate single type alias for catchAll") {
      val model = Map(
        "parameters" / "all" -> CatchAll(Bool(None), None)
      )
      new ScalaGenerator(model).generators("test") mustBeAs
        """package test
          |import org.scalacheck.Gen
          |import org.scalacheck.Arbitrary._
          |object parametersGenerator {
          |   import parameters.All
          |   def createAllGenerator = _generate(AllGenerator)
          |   val AllGenerator = _genMap[String,Boolean](arbitrary[String], arbitrary[Boolean])
          |   def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
          |   def _genMap[K,V](keyGen: Gen[K], valGen: Gen[V]): Gen[Map[K,V]] = for {
          |     keys <- Gen.containerOf[List,K](keyGen)
          |     values <- Gen.containerOfN[List,V](keys.size, valGen)
          |   } yield keys.zip(values).toMap
          |}"""
    }

    it("should generate single type alias for top-level primitive type") {
      val model = Map(
        "paths" / "/" / "get" / "responses" / "200" -> Null(None)
      )
      new ScalaGenerator(model).generators("test") mustBeAs ""
    }

    it("should generate a class file for typeDef") {
      val fields = Seq(
        Field("definitions" / "User" / "name", Str(None, None)),
        Field("definitions" / "User" / "id", Lng(None))
      )
      val model = Map(
        "definitions" / "User" -> TypeDef("definitions" / "User", fields, None)
      )
      new ScalaGenerator(model).generators("test") mustBeAs
        """package test
          |import org.scalacheck.Gen
          |import org.scalacheck.Arbitrary._
          |object definitionsGenerator {
          |   import definitions.User
          |   def createUserGenerator = _generate(UserGenerator)
          |   val UserGenerator =
          |     for {
          |       name <- arbitrary[String]
          |       id <- arbitrary[Long]
          |     } yield User(name, id)
          |   def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
          |   def _genMap[K,V](keyGen: Gen[K], valGen: Gen[V]): Gen[Map[K,V]] = for {
          |     keys <- Gen.containerOf[List,K](keyGen)
          |     values <- Gen.containerOfN[List,V](keys.size, valGen)
          |   } yield keys.zip(values).toMap
          |}"""
    }

    it("should generate a type alias for the TypeReference") {
      val model = Map(
        "definitions" / "OptionalData" -> Opt(TypeRef("definitions" / "Passwords"), None),
        "definitions" / "Passwords" -> Arr(Password(None), None, None)
      )
      new ScalaGenerator(model).generators("test") mustBeAs
        """package test
          |import org.scalacheck.Gen
          |import org.scalacheck.Arbitrary._
          |object definitionsGenerator {
          |   import definitions.OptionalData
          |   import definitions.Passwords
          |   def createOptionalDataGenerator = _generate(OptionalDataGenerator)
          |   def createPasswordsGenerator = _generate(PasswordsGenerator)
          |   val OptionalDataGenerator = Gen.option(PasswordsGenerator)
          |   val PasswordsGenerator = Gen.containerOf[List,String](arbitrary[String])
          |   def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
          |   def _genMap[K,V](keyGen: Gen[K], valGen: Gen[V]): Gen[Map[K,V]] = for {
          |     keys <- Gen.containerOf[List,K](keyGen)
          |     values <- Gen.containerOfN[List,V](keys.size, valGen)
          |   } yield keys.zip(values).toMap
          |}"""
    }

    it("should generate a complex polymorphic hierarchy") {
      val model = Map(
        "definitions" / "Cat" ->
          AllOf("definitions" / "Cat", None,
            Seq(TypeRef("definitions" / "Pet"),
              TypeRef("definitions" / "Cat" / "AllOf1")), Some("definitions" / "Pet")),
        "definitions" / "Dog" ->
          AllOf("definitions" / "Dog", None,
            Seq(TypeRef("definitions" / "Pet"),
              TypeRef("definitions" / "Dog" / "AllOf1")), Some("definitions" / "Pet")),
        "definitions" / "CatNDog" ->
          AllOf("definitions" / "CatNDog", None,
            Seq(TypeRef("definitions" / "Dog"),
              TypeRef("definitions" / "Cat")), Some("definitions" / "Pet")),
        "definitions" / "Pet" ->
          TypeDef("definitions" / "Pet", Seq(
            Field("definitions" / "Pet" / "name", Str(None, None)),
            Field("definitions" / "Pet" / "petType", Str(None, None))), None),
        "definitions" / "Labrador" ->
          AllOf("definitions" / "Labrador", None,
            Seq(TypeRef("definitions" / "Dog"),
              TypeRef("definitions" / "Labrador" / "AllOf1")), Some("definitions" / "Pet")),
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
      val result = new ScalaGenerator(model).generators("test")

      result mustBeAs
        s"""package test
          |import org.scalacheck.Gen
          |import org.scalacheck.Arbitrary._
          |
          |object definitionsGenerator {
          |   import definitions._
          |   def createCatGenerator = _generate(CatGenerator)
          |   def createDogGenerator = _generate(DogGenerator)
          |   def createCatNDogGenerator = _generate(CatNDogGenerator)
          |   def createPetGenerator = _generate(PetGenerator)
          |   def createLabradorGenerator = _generate(LabradorGenerator)
          |   val CatGenerator =
          |     for {
          |       name <- arbitrary[String]
          |       petType <- arbitrary[String]
          |       huntingSkill <- arbitrary[String]
          |     } yield Cat(name, petType, huntingSkill)
          |   val DogGenerator =
          |     for {
          |       name <- arbitrary[String]
          |       petType <- arbitrary[String]
          |       packSize <- arbitrary[Int]
          |     } yield Dog(name, petType, packSize)
          |   val CatNDogGenerator =
          |     for {
          |       name <- arbitrary[String]
          |       petType <- arbitrary[String]
          |       packSize <- arbitrary[Int]
          |       huntingSkill <- arbitrary[String]
          |     } yield CatNDog(name, petType, packSize, huntingSkill)
          |   val PetGenerator =
          |     for {
          |       name <- arbitrary[String]
          |       petType <- arbitrary[String]
          |     } yield Pet(name, petType)
          |   val LabradorGenerator =
          |     for {
          |       name <- arbitrary[String]
          |       petType <- arbitrary[String]
          |       packSize <- arbitrary[Int]
          |       cuteness <- arbitrary[Int]
          |     } yield Labrador(name, petType, packSize, cuteness)
          |   def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
          |   def _genMap[K,V](keyGen: Gen[K], valGen: Gen[V]): Gen[Map[K,V]] = for {
          |     keys <- Gen.containerOf[List,K](keyGen)
          |     values <- Gen.containerOfN[List,V](keys.size, valGen)
          |   } yield keys.zip(values).toMap
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
            TypeRef("definitions" / "ErrorModel"),
            TypeRef("definitions" / "ExtendedErrorModel" / "AllOf1"))),
        "definitions" / "ExtendedErrorModel" / "AllOf1" ->
          TypeDef("definitions" / "ExtendedErrorModel", Seq(
            Field("definitions" / "ExtendedErrorModel" / "rootCause", Str(None, None))), None)
      )
      new ScalaGenerator(model).generators("test") mustBeAs
        """package test
          |import org.scalacheck.Gen
          |import org.scalacheck.Arbitrary._
          |object definitionsGenerator {
          |   import definitions.ErrorModel
          |   import definitions.ExtendedErrorModel
          |   def createErrorModelGenerator = _generate(ErrorModelGenerator)
          |   def createExtendedErrorModelGenerator = _generate(ExtendedErrorModelGenerator)
          |   val ErrorModelGenerator =
          |     for {
          |       message <- arbitrary[String]
          |       code <- arbitrary[Int]
          |     } yield ErrorModel(message, code)
          |   val ExtendedErrorModelGenerator =
          |     for {
          |       message <- arbitrary[String]
          |       code <- arbitrary[Int]
          |       rootCause <- arbitrary[String]
          |     } yield ExtendedErrorModel(message, code, rootCause)
          |   def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
          |   def _genMap[K,V](keyGen: Gen[K], valGen: Gen[V]): Gen[Map[K,V]] = for {
          |     keys <- Gen.containerOf[List,K](keyGen)
          |     values <- Gen.containerOfN[List,V](keys.size, valGen)
          |   } yield keys.zip(values).toMap
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
