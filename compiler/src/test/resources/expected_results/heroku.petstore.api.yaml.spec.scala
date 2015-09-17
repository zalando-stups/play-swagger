package heroku.petstore.api.yaml
import de.zalando.play.controllers.PlayBodyParsing
import org.scalacheck._
import org.scalacheck.Arbitrary._
import org.scalacheck.Prop._
import org.scalacheck.Test._
import org.specs2.mutable._
import play.api.test.Helpers._
import play.api.test._
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import java.net.URLEncoder
import controllers._
/**
*/
  import scala.Option
  import definitions.Pet
  @RunWith(classOf[JUnitRunner])
  class ApiYamlSpec extends Specification {
    // TODO use specs2 scalacheck integration instead of doing this manually
    def checkResult(props: Prop) =
      Test.check(Test.Parameters.default, props).status match {
        case Failed(_, labels) => failure(labels.mkString("\n"))
        case Proved(_) | Exhausted | Passed => success
        case PropException(_, _, labels) => failure(labels.mkString("\n"))
      }
    "GET /api" should {
        def testInvalidInput(in: (Option[Int])) = {
          val (limit) = in
          val url = s"""/api?${limit.map { i => "limit=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}"""
          val path = route(FakeRequest(GET, url)).get
          val validation = new ValidationForApiYamlallPets(limit).result
          lazy val validations = validation.left.get flatMap {
            _.messages map { m => contentAsString(path).contains(m) ?= true }
          }
          ("given an URL: [" + url + "]") |: all(
            status(path) ?= BAD_REQUEST,
            contentType(path) ?= Some("application/json"),
            validation.isLeft ?= true,
            all(validations:_*)
          )
        }
        def testValidInput(in: (Option[Int])) = {
          val (limit) = in
          val url = s"""/api?${limit.map { i => "limit=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}"""
          val path = route(FakeRequest(GET, url)).get
          ("given an URL: [" + url + "]") |: (status(path) ?= OK)
        }
         "discard invalid data" in new WithApplication {
          val genInputs =
            for {
               limit <- Gen.option(arbitrary[Int])
             } yield (limit)
          val inputs = genInputs suchThat { i => new ValidationForApiYamlallPets(i).result != Right(i) }
          val props = forAll(inputs) { i => testInvalidInput(i) }
          checkResult(props)
        }
         "do something with valid data" in new WithApplication {
          val genInputs =
            for {
               limit <- Gen.option(arbitrary[Int])
             } yield (limit)
          val inputs = genInputs suchThat { i => new ValidationForApiYamlallPets(i).result == Right(i) }
          val props = forAll(inputs) { i => testValidInput(i) }
          checkResult(props)
        }
    }
    "PUT /api" should {
        def testInvalidInput(in: (Pet)) = {
          val (pet) = in
          val url = s"""/api"""
          val body = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(pet)
          val path = route(FakeRequest(PUT, url).withBody(body)).get
          val validation = new ValidationForApiYamlupdatePet(pet).result
          lazy val validations = validation.left.get flatMap {
            _.messages map { m => contentAsString(path).contains(m) ?= true }
          }
          ("given an URL: [" + url + "]"+ "and body [" + body + "]") |: all(
            status(path) ?= BAD_REQUEST,
            contentType(path) ?= Some("application/json"),
            validation.isLeft ?= true,
            all(validations:_*)
          )
        }
        def testValidInput(in: (Pet)) = {
          val (pet) = in
          val url = s"""/api"""
          val body = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(pet)
          val path = route(FakeRequest(PUT, url).withBody(body)).get
          ("given an URL: [" + url + "]"+ "and body [" + body + "]") |: (status(path) ?= OK)
        }
         "discard invalid data" in new WithApplication {
          val genInputs =
            for {
               pet <- generatorDefinitions.PetGenerator
             } yield (pet)
          val inputs = genInputs suchThat { i => new ValidationForApiYamlupdatePet(i).result != Right(i) }
          val props = forAll(inputs) { i => testInvalidInput(i) }
          checkResult(props)
        }
         "do something with valid data" in new WithApplication {
          val genInputs =
            for {
               pet <- generatorDefinitions.PetGenerator
             } yield (pet)
          val inputs = genInputs suchThat { i => new ValidationForApiYamlupdatePet(i).result == Right(i) }
          val props = forAll(inputs) { i => testValidInput(i) }
          checkResult(props)
        }
    }
    "POST /api" should {
        def testInvalidInput(in: (Pet)) = {
          val (pet) = in
          val url = s"""/api"""
          val body = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(pet)
          val path = route(FakeRequest(POST, url).withBody(body)).get
          val validation = new ValidationForApiYamlcreatePet(pet).result
          lazy val validations = validation.left.get flatMap {
            _.messages map { m => contentAsString(path).contains(m) ?= true }
          }
          ("given an URL: [" + url + "]"+ "and body [" + body + "]") |: all(
            status(path) ?= BAD_REQUEST,
            contentType(path) ?= Some("application/json"),
            validation.isLeft ?= true,
            all(validations:_*)
          )
        }
        def testValidInput(in: (Pet)) = {
          val (pet) = in
          val url = s"""/api"""
          val body = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(pet)
          val path = route(FakeRequest(POST, url).withBody(body)).get
          ("given an URL: [" + url + "]"+ "and body [" + body + "]") |: (status(path) ?= OK)
        }
         "discard invalid data" in new WithApplication {
          val genInputs =
            for {
               pet <- generatorDefinitions.PetGenerator
             } yield (pet)
          val inputs = genInputs suchThat { i => new ValidationForApiYamlcreatePet(i).result != Right(i) }
          val props = forAll(inputs) { i => testInvalidInput(i) }
          checkResult(props)
        }
         "do something with valid data" in new WithApplication {
          val genInputs =
            for {
               pet <- generatorDefinitions.PetGenerator
             } yield (pet)
          val inputs = genInputs suchThat { i => new ValidationForApiYamlcreatePet(i).result == Right(i) }
          val props = forAll(inputs) { i => testValidInput(i) }
          checkResult(props)
        }
    }
    "GET /api/{petId}" should {
        def testInvalidInput(in: (String)) = {
          val (petId) = in
          val url = s"""/api/${petId}"""
          val path = route(FakeRequest(GET, url)).get
          val validation = new ValidationForApiYamlgetPet(petId).result
          lazy val validations = validation.left.get flatMap {
            _.messages map { m => contentAsString(path).contains(m) ?= true }
          }
          ("given an URL: [" + url + "]") |: all(
            status(path) ?= BAD_REQUEST,
            contentType(path) ?= Some("application/json"),
            validation.isLeft ?= true,
            all(validations:_*)
          )
        }
        def testValidInput(in: (String)) = {
          val (petId) = in
          val url = s"""/api/${petId}"""
          val path = route(FakeRequest(GET, url)).get
          ("given an URL: [" + url + "]") |: (status(path) ?= OK)
        }
         "discard invalid data" in new WithApplication {
          val genInputs =
            for {
               petId <- arbitrary[String]
             } yield (petId)
          val inputs = genInputs suchThat { i => new ValidationForApiYamlgetPet(i).result != Right(i) }
          val props = forAll(inputs) { i => testInvalidInput(i) }
          checkResult(props)
        }
         "do something with valid data" in new WithApplication {
          val genInputs =
            for {
               petId <- arbitrary[String]
             } yield (petId)
          val inputs = genInputs suchThat { i => new ValidationForApiYamlgetPet(i).result == Right(i) }
          val props = forAll(inputs) { i => testValidInput(i) }
          checkResult(props)
        }
    }
  }