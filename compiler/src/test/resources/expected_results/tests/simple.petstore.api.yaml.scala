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

"GET /api/pets" should {
            def testInvalidInput(input: (PetsGetTags, PetsGetLimit)) = {

                val (tags, limit) = input
                val url = s"""/api/pets?tags=${tags.map { i => URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&limit=${limit.map { i => URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}"""

                val path = route(FakeRequest(GET, url)).get
                val errors = new PetsGetValidator(tags, limit).errors


                lazy val validations = errors map { m => contentAsString(path).contains(m) ?= true }

                ("given an URL: [" + url + "]" ) |: all(
                    status(path) ?= 200,
                    contentType(path) ?= Some("application/json"),
                    validations.nonEmpty ?= true,
                    all(validations:_*)
                )
            }
            def testValidInput(input: (PetsGetTags, PetsGetLimit)) = {

                val (tags, limit) = input
                val url = s"""/api/pets?tags=${tags.map { i => URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&limit=${limit.map { i => URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}"""
                val path = route(FakeRequest(GET, url)).get
                ("given an URL: [" + url + "]") |: (status(path) ?= OK)
            }
            "discard invalid data" in new WithApplication {
                val genInputs = for {
                        tags <- pathsGenerator.PetsGetTagsGenerator
                        limit <- pathsGenerator.PetsGetLimitGenerator
                        

                    } yield (tags, limit)

                val inputs = genInputs suchThat { case (tags, limit)=>
                    new PetsGetValidator(tags, limit).errors.nonEmpty
                }
                val props = forAll(inputs) { i => testInvalidInput(i) }
                checkResult(props)
            }
            "do something with valid data" in new WithApplication {
                val genInputs = for {
                    tags <- pathsGenerator.PetsGetTagsGenerator
                    limit <- pathsGenerator.PetsGetLimitGenerator
                    

                } yield (tags, limit)

                val inputs = genInputs suchThat { case (tags, limit)=>
                    new PetsGetValidator(tags, limit).errors.isEmpty
                }
                val props = forAll(inputs) { i => testValidInput(i) }
                checkResult(props)
            }

        }
    "POST /api/pets" should {
            def testInvalidInput(pet: NewPet) = {

                val url = s"""/api/pets"""

                val body = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(pet)
                val path = route(FakeRequest(POST, url).withBody(body)).get
                val errors = new PetsPostValidator(pet).errors


                lazy val validations = errors map { m => contentAsString(path).contains(m) ?= true }

                ("given an URL: [" + url + "]" + "and body [" + body + "]") |: all(
                    status(path) ?= 200,
                    contentType(path) ?= Some("application/json"),
                    validations.nonEmpty ?= true,
                    all(validations:_*)
                )
            }
            def testValidInput(pet: NewPet) = {

                val body = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(pet)
                val url = s"""/api/pets"""
                val path = route(FakeRequest(POST, url).withBody(body)).get
                ("given an URL: [" + url + "]"+ " and body [" + body + "]") |: (status(path) ?= OK)
            }
            "discard invalid data" in new WithApplication {
                val genInputs = for {
                        pet <- definitionsGenerator.NewPetGenerator

                    } yield pet

                val inputs = genInputs suchThat { pet=>
                    new PetsPostValidator(pet).errors.nonEmpty
                }
                val props = forAll(inputs) { i => testInvalidInput(i) }
                checkResult(props)
            }
            "do something with valid data" in new WithApplication {
                val genInputs = for {
                    pet <- definitionsGenerator.NewPetGenerator

                } yield pet

                val inputs = genInputs suchThat { pet=>
                    new PetsPostValidator(pet).errors.isEmpty
                }
                val props = forAll(inputs) { i => testValidInput(i) }
                checkResult(props)
            }

        }
    "GET /api/pets/{id}" should {
            def testInvalidInput(id: Long) = {

                val url = s"""/api/pets/${id}"""

                val path = route(FakeRequest(GET, url)).get
                val errors = new PetsIdGetValidator(id).errors


                lazy val validations = errors map { m => contentAsString(path).contains(m) ?= true }

                ("given an URL: [" + url + "]" ) |: all(
                    status(path) ?= 200,
                    contentType(path) ?= Some("application/json"),
                    validations.nonEmpty ?= true,
                    all(validations:_*)
                )
            }
            def testValidInput(id: Long) = {

                val url = s"""/api/pets/${id}"""
                val path = route(FakeRequest(GET, url)).get
                ("given an URL: [" + url + "]") |: (status(path) ?= OK)
            }
            "discard invalid data" in new WithApplication {
                val genInputs = for {
                        id <- arbitrary[Long]

                    } yield id

                val inputs = genInputs suchThat { id=>
                    new PetsIdGetValidator(id).errors.nonEmpty
                }
                val props = forAll(inputs) { i => testInvalidInput(i) }
                checkResult(props)
            }
            "do something with valid data" in new WithApplication {
                val genInputs = for {
                    id <- arbitrary[Long]

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

                val path = route(FakeRequest(DELETE, url)).get
                val errors = new PetsIdDeleteValidator(id).errors


                lazy val validations = errors map { m => contentAsString(path).contains(m) ?= true }

                ("given an URL: [" + url + "]" ) |: all(
                    status(path) ?= 200,
                    contentType(path) ?= Some("application/json"),
                    validations.nonEmpty ?= true,
                    all(validations:_*)
                )
            }
            def testValidInput(id: Long) = {

                val url = s"""/api/pets/${id}"""
                val path = route(FakeRequest(DELETE, url)).get
                ("given an URL: [" + url + "]") |: (status(path) ?= OK)
            }
            "discard invalid data" in new WithApplication {
                val genInputs = for {
                        id <- arbitrary[Long]

                    } yield id

                val inputs = genInputs suchThat { id=>
                    new PetsIdDeleteValidator(id).errors.nonEmpty
                }
                val props = forAll(inputs) { i => testInvalidInput(i) }
                checkResult(props)
            }
            "do something with valid data" in new WithApplication {
                val genInputs = for {
                    id <- arbitrary[Long]

                } yield id

                val inputs = genInputs suchThat { id=>
                    new PetsIdDeleteValidator(id).errors.isEmpty
                }
                val props = forAll(inputs) { i => testValidInput(i) }
                checkResult(props)
            }

        }
    }
