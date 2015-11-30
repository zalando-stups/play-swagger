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
package simple.petstore.api.yaml

}

@RunWith(classOf[JUnitRunner])
    class PetsGetSpec extends Specification {
      
      def checkResult(props: Prop) =
        Test.check(Test.Parameters.default, props).status match {
          case Failed(_, labels) => failure(labels.mkString("\\n"))
          case Proved(_) | Exhausted | Passed => success
          case PropException(_, _, labels) => failure(labels.mkString("\\n"))
        }


        "GET /pets" should {
            def testInvalidInput(
                input: (PetsGetTags, PetsGetLimit))

                {

                val () = input
                val url = s"""/pets?tags=${URLEncoder.encode(tags.toString, "UTF-8")}&limit=${URLEncoder.encode(limit.toString, "UTF-8")}"""
                val path = route(FakeRequest(GET, url)).get
                val validation = new PetsGetValidator(tags, limit).result


                lazy val validations = validation.left.get flatMap {
                    _.messages map { m => contentAsString(path).contains(m) ?= true }
                }

                ("given an URL: [" + url + "]" ) |: all(
                    status(path) ?= 200,
                    contentType(path) ?= Some(application/json),
                    validation.isLeft ?= true,
                    all(validations:_*)
                )
            }
            def testValidInput(
                input: (PetsGetTags, PetsGetLimit)) = {

                val (tags, limit) = input
                ("given an URL: [" + url + "]") |: (status(path) ?= OK)
            }
            "discard invalid data" in new WithApplication {
                val genInputs =
                    for {
                        tags <- 
                            limit <- 
                            

                    } yield
                        (tags, limit)
val inputs = genInputs suchThat { i => new PetsGetValidator(i).result != Right(i) }
                val props = forAll(inputs) { i => testInvalidInput(i) }
                checkResult(props)
            }
            "do something with valid data" in new WithApplication {
                val genInputs =
                for {
                    tags <- pathsGenerator.PetsGetTagsGenerator
                        limit <- pathsGenerator.PetsGetLimitGenerator
                        

                } yield
                    (tags, limit)
val inputs = genInputs suchThat { i => new PetsGetValidator(i).result == Right(i) }
                val props = forAll(inputs) { i => testValidInput(i) }
                checkResult(props)
            }
        }
    @RunWith(classOf[JUnitRunner])
    class PetsPostSpec extends Specification {
      
      def checkResult(props: Prop) =
        Test.check(Test.Parameters.default, props).status match {
          case Failed(_, labels) => failure(labels.mkString("\\n"))
          case Proved(_) | Exhausted | Passed => success
          case PropException(_, _, labels) => failure(labels.mkString("\\n"))
        }


        "POST /pets" should {
            val body = PlayBodyParsing.jacksonMapper(application/json).writeValueAsString(pet)
            def testInvalidInput(
                pet: NewPet){

                val () = input
                val url = s"""/pets"""
                val path = route(FakeRequest(POST, url).withBody(body)).get
                val validation = new PetsPostValidator(pet)).result


                lazy val validations = validation.left.get flatMap {
                    _.messages map { m => contentAsString(path).contains(m) ?= true }
                }

                ("given an URL: [" + url + "]" + "and body [" + body + "]") |: all(
                    status(path) ?= 200,
                    contentType(path) ?= Some(application/json),
                    validation.isLeft ?= true,
                    all(validations:_*)
                )
            }
            def testValidInput(
                pet: NewPet)) = {

                ("given an URL: [" + url + "]"+ "and body [" + body + "]") |: (status(path) ?= OK)
            }
            "discard invalid data" in new WithApplication {
                val genInputs =
                    for {
                        pet <- pathsGenerator.PetsPostPetGenerator

                    } yield
                        pet
val inputs = genInputs suchThat { i => new PetsPostValidator(i).result != Right(i) }
                val props = forAll(inputs) { i => testInvalidInput(i) }
                checkResult(props)
            }
            "do something with valid data" in new WithApplication {
                val genInputs =
                for {
                    pet <- pathsGenerator.PetsPostPetGenerator

                } yield
                    pet
val inputs = genInputs suchThat { i => new PetsPostValidator(i).result == Right(i) }
                val props = forAll(inputs) { i => testValidInput(i) }
                checkResult(props)
            }
        }
    @RunWith(classOf[JUnitRunner])
    class PetsIdGetSpec extends Specification {
      
      def checkResult(props: Prop) =
        Test.check(Test.Parameters.default, props).status match {
          case Failed(_, labels) => failure(labels.mkString("\\n"))
          case Proved(_) | Exhausted | Passed => success
          case PropException(_, _, labels) => failure(labels.mkString("\\n"))
        }


        "GET /pets/{id}" should {
            def testInvalidInput(
                id: Long){

                val () = input
                val url = s"""/pets/${id}"""
                val path = route(FakeRequest(GET, url)).get
                val validation = new PetsIdGetValidator(id)).result


                lazy val validations = validation.left.get flatMap {
                    _.messages map { m => contentAsString(path).contains(m) ?= true }
                }

                ("given an URL: [" + url + "]" ) |: all(
                    status(path) ?= 200,
                    contentType(path) ?= Some(application/json),
                    validation.isLeft ?= true,
                    all(validations:_*)
                )
            }
            def testValidInput(
                id: Long)) = {

                ("given an URL: [" + url + "]") |: (status(path) ?= OK)
            }
            "discard invalid data" in new WithApplication {
                val genInputs =
                    for {
                        id <- pathsGenerator.PetsIdGetIdGenerator

                    } yield
                        id
val inputs = genInputs suchThat { i => new PetsIdGetValidator(i).result != Right(i) }
                val props = forAll(inputs) { i => testInvalidInput(i) }
                checkResult(props)
            }
            "do something with valid data" in new WithApplication {
                val genInputs =
                for {
                    id <- pathsGenerator.PetsIdGetIdGenerator

                } yield
                    id
val inputs = genInputs suchThat { i => new PetsIdGetValidator(i).result == Right(i) }
                val props = forAll(inputs) { i => testValidInput(i) }
                checkResult(props)
            }
        }
    @RunWith(classOf[JUnitRunner])
    class PetsIdDeleteSpec extends Specification {
      
      def checkResult(props: Prop) =
        Test.check(Test.Parameters.default, props).status match {
          case Failed(_, labels) => failure(labels.mkString("\\n"))
          case Proved(_) | Exhausted | Passed => success
          case PropException(_, _, labels) => failure(labels.mkString("\\n"))
        }


        "DELETE /pets/{id}" should {
            def testInvalidInput(
                id: Long){

                val () = input
                val url = s"""/pets/${id}"""
                val path = route(FakeRequest(DELETE, url)).get
                val validation = new PetsIdDeleteValidator(id)).result


                lazy val validations = validation.left.get flatMap {
                    _.messages map { m => contentAsString(path).contains(m) ?= true }
                }

                ("given an URL: [" + url + "]" ) |: all(
                    status(path) ?= 200,
                    contentType(path) ?= Some(application/json),
                    validation.isLeft ?= true,
                    all(validations:_*)
                )
            }
            def testValidInput(
                id: Long)) = {

                ("given an URL: [" + url + "]") |: (status(path) ?= OK)
            }
            "discard invalid data" in new WithApplication {
                val genInputs =
                    for {
                        id <- pathsGenerator.PetsIdDeleteIdGenerator

                    } yield
                        id
val inputs = genInputs suchThat { i => new PetsIdDeleteValidator(i).result != Right(i) }
                val props = forAll(inputs) { i => testInvalidInput(i) }
                checkResult(props)
            }
            "do something with valid data" in new WithApplication {
                val genInputs =
                for {
                    id <- pathsGenerator.PetsIdDeleteIdGenerator

                } yield
                    id
val inputs = genInputs suchThat { i => new PetsIdDeleteValidator(i).result == Right(i) }
                val props = forAll(inputs) { i => testValidInput(i) }
                checkResult(props)
            }
        }
    }

