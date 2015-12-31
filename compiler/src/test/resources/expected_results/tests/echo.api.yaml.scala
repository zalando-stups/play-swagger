package echo.api.yaml

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
    class EchoApiYamlSpec extends Specification {
      
      def checkResult(props: Prop) =
        Test.check(Test.Parameters.default, props).status match {
          case Failed(_, labels) => failure(labels.mkString("\\n"))
          case Proved(_) | Exhausted | Passed => success
          case PropException(_, e, labels) =>
            val error = if (labels.isEmpty) e.getLocalizedMessage() else labels.mkString("\\n")
            failure(error)
        }

"GET /echo/test-path/{id}" should {
            def testInvalidInput(id: String) = {

                val url = s"""/echo/test-path/${id}"""
                val headers = Seq()
                val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
                val errors = new `Test-pathIdGetValidator`(id).errors


                lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

                ("given an URL: [" + url + "]" ) |: all(
                    status(path) ?= BAD_REQUEST ,
                    contentType(path) ?= Some("application/json"),
                    errors.nonEmpty ?= true,
                    all(validations:_*)
                )
            }
            def testValidInput(id: String) = {

                val url = s"""/echo/test-path/${id}"""
                val headers = Seq()
                val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
                ("given an URL: [" + url + "]") |: (status(path) ?= OK)
            }
            "discard invalid data" in new WithApplication {
                val genInputs = for {
                        id <- StringGenerator

                    } yield id

                val inputs = genInputs suchThat { id=>
                    new `Test-pathIdGetValidator`(id).errors.nonEmpty
                }
                val props = forAll(inputs) { i => testInvalidInput(i) }
                checkResult(props)
            }
            "do something with valid data" in new WithApplication {
                val genInputs = for {
                    id <- StringGenerator

                } yield id

                val inputs = genInputs suchThat { id=>
                    new `Test-pathIdGetValidator`(id).errors.isEmpty
                }
                val props = forAll(inputs) { i => testValidInput(i) }
                checkResult(props)
            }

        }
    "POST /echo/" should {
            def testInvalidInput(input: (PostName, PostName)) = {

                val (name, year) = input
                val url = s"""/echo/"""
                val headers = Seq()
                val path = route(FakeRequest(POST, url).withHeaders(headers:_*)).get
                val errors = new PostValidator(name, year).errors


                lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

                ("given an URL: [" + url + "]" ) |: all(
                    status(path) ?= BAD_REQUEST ,
                    contentType(path) ?= Some("application/json"),
                    errors.nonEmpty ?= true,
                    all(validations:_*)
                )
            }
            def testValidInput(input: (PostName, PostName)) = {

                val (name, year) = input
                val url = s"""/echo/"""
                val headers = Seq()
                val path = route(FakeRequest(POST, url).withHeaders(headers:_*)).get
                ("given an URL: [" + url + "]") |: (status(path) ?= OK)
            }
            "discard invalid data" in new WithApplication {
                val genInputs = for {
                        name <- PostNameGenerator
                        year <- PostNameGenerator
                        

                    } yield (name, year)

                val inputs = genInputs suchThat { case (name, year)=>
                    new PostValidator(name, year).errors.nonEmpty
                }
                val props = forAll(inputs) { i => testInvalidInput(i) }
                checkResult(props)
            }
            "do something with valid data" in new WithApplication {
                val genInputs = for {
                    name <- PostNameGenerator
                    year <- PostNameGenerator
                    

                } yield (name, year)

                val inputs = genInputs suchThat { case (name, year)=>
                    new PostValidator(name, year).errors.isEmpty
                }
                val props = forAll(inputs) { i => testValidInput(i) }
                checkResult(props)
            }

        }
    }
