package simple.petstore.api.yaml

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
import Generators._

    @RunWith(classOf[JUnitRunner])
    class SimplePetstoreApiYamlSpec extends Specification {
      
      def checkResult(props: Prop) =
        Test.check(Test.Parameters.default, props).status match {
          case Failed(_, labels) => failure(labels.mkString("\\n"))
          case Proved(_) | Exhausted | Passed => success
          case PropException(_, e, labels) =>
            val error = if (labels.isEmpty) e.getLocalizedMessage() else labels.mkString("\\n")
            failure(error)
        }

"POST /api/pets" should {
            def testInvalidInput(pet: NewPet) = {

                val url = s"""/api/pets"""
                val headers = Seq()
                val parsed_pet = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(pet)
                val path = route(FakeRequest(POST, url).withHeaders(headers:_*).withBody(parsed_pet)).get
                val errors = new PetsPostValidator(pet).errors


                lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

                ("given an URL: [" + url + "]" + "and body [" + parsed_pet + "]") |: all(
                    status(path) ?= BAD_REQUEST ,
                    contentType(path) ?= Some("application/json"),
                    errors.nonEmpty ?= true,
                    all(validations:_*)
                )
            }
            def testValidInput(pet: NewPet) = {

                val parsed_pet = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(pet)
                val url = s"""/api/pets"""
                val headers = Seq()
                val path = route(FakeRequest(POST, url).withHeaders(headers:_*).withBody(parsed_pet)).get
                ("given an URL: [" + url + "]"+ " and body [" + parsed_pet + "]") |: (status(path) ?= OK)
            }
            "discard invalid data" in new WithApplication {
                val genInputs = for {
                        pet <- NewPetGenerator

                    } yield pet

                val inputs = genInputs suchThat { pet=>
                    new PetsPostValidator(pet).errors.nonEmpty
                }
                val props = forAll(inputs) { i => testInvalidInput(i) }
                checkResult(props)
            }
            "do something with valid data" in new WithApplication {
                val genInputs = for {
                    pet <- NewPetGenerator

                } yield pet

                val inputs = genInputs suchThat { pet=>
                    new PetsPostValidator(pet).errors.isEmpty
                }
                val props = forAll(inputs) { i => testValidInput(i) }
                checkResult(props)
            }

        }
    "GET /api/pets" should {
            def testInvalidInput(input: (PetsGetTags, PetsGetLimit)) = {

                val (tags, limit) = input
                val url = s"""/api/pets?${tags.map { i => "tags=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${limit.map { i => "limit=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}"""
                val headers = Seq()
                val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
                val errors = new PetsGetValidator(tags, limit).errors


                lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

                ("given an URL: [" + url + "]" ) |: all(
                    status(path) ?= BAD_REQUEST ,
                    contentType(path) ?= Some("application/json"),
                    errors.nonEmpty ?= true,
                    all(validations:_*)
                )
            }
            def testValidInput(input: (PetsGetTags, PetsGetLimit)) = {

                val (tags, limit) = input
                val url = s"""/api/pets?${tags.map { i => "tags=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${limit.map { i => "limit=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}"""
                val headers = Seq()
                val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
                ("given an URL: [" + url + "]") |: (status(path) ?= OK)
            }
            "discard invalid data" in new WithApplication {
                val genInputs = for {
                        tags <- PetsGetTagsGenerator
                        limit <- PetsGetLimitGenerator
                        

                    } yield (tags, limit)

                val inputs = genInputs suchThat { case (tags, limit)=>
                    new PetsGetValidator(tags, limit).errors.nonEmpty
                }
                val props = forAll(inputs) { i => testInvalidInput(i) }
                checkResult(props)
            }
            "do something with valid data" in new WithApplication {
                val genInputs = for {
                    tags <- PetsGetTagsGenerator
                    limit <- PetsGetLimitGenerator
                    

                } yield (tags, limit)

                val inputs = genInputs suchThat { case (tags, limit)=>
                    new PetsGetValidator(tags, limit).errors.isEmpty
                }
                val props = forAll(inputs) { i => testValidInput(i) }
                checkResult(props)
            }

        }
    "GET /api/pets/{id}" should {
            def testInvalidInput(id: Long) = {

                val url = s"""/api/pets/${id}"""
                val headers = Seq()
                val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
                val errors = new PetsIdGetValidator(id).errors


                lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

                ("given an URL: [" + url + "]" ) |: all(
                    status(path) ?= BAD_REQUEST ,
                    contentType(path) ?= Some("application/json"),
                    errors.nonEmpty ?= true,
                    all(validations:_*)
                )
            }
            def testValidInput(id: Long) = {

                val url = s"""/api/pets/${id}"""
                val headers = Seq()
                val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
                ("given an URL: [" + url + "]") |: (status(path) ?= OK)
            }
            "discard invalid data" in new WithApplication {
                val genInputs = for {
                        id <- LongGenerator

                    } yield id

                val inputs = genInputs suchThat { id=>
                    new PetsIdGetValidator(id).errors.nonEmpty
                }
                val props = forAll(inputs) { i => testInvalidInput(i) }
                checkResult(props)
            }
            "do something with valid data" in new WithApplication {
                val genInputs = for {
                    id <- LongGenerator

                } yield id

                val inputs = genInputs suchThat { id=>
                    new PetsIdGetValidator(id).errors.isEmpty
                }
                val props = forAll(inputs) { i => testValidInput(i) }
                checkResult(props)
            }

        }
    "DELETE /api/pets/{id}" should {
            def testInvalidInput(id: Long) = {

                val url = s"""/api/pets/${id}"""
                val headers = Seq()
                val path = route(FakeRequest(DELETE, url).withHeaders(headers:_*)).get
                val errors = new PetsIdDeleteValidator(id).errors


                lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

                ("given an URL: [" + url + "]" ) |: all(
                    status(path) ?= BAD_REQUEST ,
                    contentType(path) ?= Some("application/json"),
                    errors.nonEmpty ?= true,
                    all(validations:_*)
                )
            }
            def testValidInput(id: Long) = {

                val url = s"""/api/pets/${id}"""
                val headers = Seq()
                val path = route(FakeRequest(DELETE, url).withHeaders(headers:_*)).get
                ("given an URL: [" + url + "]") |: (status(path) ?= OK)
            }
            "discard invalid data" in new WithApplication {
                val genInputs = for {
                        id <- LongGenerator

                    } yield id

                val inputs = genInputs suchThat { id=>
                    new PetsIdDeleteValidator(id).errors.nonEmpty
                }
                val props = forAll(inputs) { i => testInvalidInput(i) }
                checkResult(props)
            }
            "do something with valid data" in new WithApplication {
                val genInputs = for {
                    id <- LongGenerator

                } yield id

                val inputs = genInputs suchThat { id=>
                    new PetsIdDeleteValidator(id).errors.isEmpty
                }
                val props = forAll(inputs) { i => testValidInput(i) }
                checkResult(props)
            }

        }
    }
