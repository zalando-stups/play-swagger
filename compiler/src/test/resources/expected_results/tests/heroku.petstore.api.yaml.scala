package heroku.petstore.api.yaml

import de.zalando.play.controllers.PlayBodyParsing
import org.scalacheck._
import org.scalacheck.Arbitrary._
import org.scalacheck.Prop._
import org.scalacheck.Test._
import org.specs2.mutable._
import play.api.test.Helpers._
import play.api.test._
import play.api.mvc.{QueryStringBindable, PathBindable}
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import java.net.URLEncoder

import Generators._

    @RunWith(classOf[JUnitRunner])
    class HerokuPetstoreApiYamlSpec extends Specification {
        def toPath[T](value: T)(implicit binder: PathBindable[T]): String = binder.unbind("", value)
        def toQuery[T](key: String, value: T)(implicit binder: QueryStringBindable[T]): String = binder.unbind(key, value)
        def toHeader[T](value: T)(implicit binder: PathBindable[T]): String = binder.unbind("", value)


      
      def checkResult(props: Prop) =
        Test.check(Test.Parameters.default, props).status match {
          case Failed(_, labels) => failure(labels.mkString("\\n"))
          case Proved(_) | Exhausted | Passed => success
          case PropException(_, e, labels) =>
            val error = if (labels.isEmpty) e.getLocalizedMessage() else labels.mkString("\\n")
            failure(error)
        }

"PUT /pet/" should {
        def testInvalidInput(pet: Pet) = {

            val url = s"""/pet/"""
            val headers = Seq()
            val parsed_pet = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(pet)
            val path = route(FakeRequest(PUT, url).withHeaders(headers:_*).withBody(parsed_pet)).get
            val errors = new PutValidator(pet).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" + "and body [" + parsed_pet + "]") |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(pet: Pet) = {

            val parsed_pet = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(pet)
            val url = s"""/pet/"""
            val headers = Seq()
            val path = route(FakeRequest(PUT, url).withHeaders(headers:_*).withBody(parsed_pet)).get
            ("given an URL: [" + url + "]"+ " and body [" + parsed_pet + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    pet <- PetGenerator

                } yield pet

            val inputs = genInputs suchThat { pet=>
                new PutValidator(pet).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                pet <- PetGenerator

            } yield pet

            val inputs = genInputs suchThat { pet=>
                new PutValidator(pet).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
"GET /pet/" should {
        def testInvalidInput(limit: PetBirthday) = {

            val url = s"""/pet/?${toQuery("limit", limit)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new GetValidator(limit).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(limit: PetBirthday) = {

            val url = s"""/pet/?${toQuery("limit", limit)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    limit <- PetBirthdayGenerator

                } yield limit

            val inputs = genInputs suchThat { limit=>
                new GetValidator(limit).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                limit <- PetBirthdayGenerator

            } yield limit

            val inputs = genInputs suchThat { limit=>
                new GetValidator(limit).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
"GET /pet/{petId}" should {
        def testInvalidInput(petId: String) = {

            val url = s"""/pet/${toPath(petId)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new PetIdGetValidator(petId).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(petId: String) = {

            val url = s"""/pet/${toPath(petId)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    petId <- StringGenerator

                } yield petId

            val inputs = genInputs suchThat { petId=>
                new PetIdGetValidator(petId).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                petId <- StringGenerator

            } yield petId

            val inputs = genInputs suchThat { petId=>
                new PetIdGetValidator(petId).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
"POST /pet/" should {
        def testInvalidInput(pet: Pet) = {

            val url = s"""/pet/"""
            val headers = Seq()
            val parsed_pet = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(pet)
            val path = route(FakeRequest(POST, url).withHeaders(headers:_*).withBody(parsed_pet)).get
            val errors = new PostValidator(pet).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" + "and body [" + parsed_pet + "]") |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(pet: Pet) = {

            val parsed_pet = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(pet)
            val url = s"""/pet/"""
            val headers = Seq()
            val path = route(FakeRequest(POST, url).withHeaders(headers:_*).withBody(parsed_pet)).get
            ("given an URL: [" + url + "]"+ " and body [" + parsed_pet + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    pet <- PetGenerator

                } yield pet

            val inputs = genInputs suchThat { pet=>
                new PostValidator(pet).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                pet <- PetGenerator

            } yield pet

            val inputs = genInputs suchThat { pet=>
                new PostValidator(pet).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
}
