package security.api.yaml

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
    class SecurityApiYamlSpec extends Specification {
      
      def checkResult(props: Prop) =
        Test.check(Test.Parameters.default, props).status match {
          case Failed(_, labels) => failure(labels.mkString("\\n"))
          case Proved(_) | Exhausted | Passed => success
          case PropException(_, e, labels) =>
            val error = if (labels.isEmpty) e.getLocalizedMessage() else labels.mkString("\\n")
            failure(error)
        }

"GET /v1/pets/{id}" should {
            def testInvalidInput(id: PetsIdGetId) = {

                val url = s"""/v1/pets/${id}"""
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
            def testValidInput(id: PetsIdGetId) = {

                val url = s"""/v1/pets/${id}"""
                val headers = Seq()
                val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
                ("given an URL: [" + url + "]") |: (status(path) ?= OK)
            }
            "discard invalid data" in new WithApplication {
                val genInputs = for {
                        id <- PetsIdGetIdGenerator

                    } yield id

                val inputs = genInputs suchThat { id=>
                    new PetsIdGetValidator(id).errors.nonEmpty
                }
                val props = forAll(inputs) { i => testInvalidInput(i) }
                checkResult(props)
            }
            "do something with valid data" in new WithApplication {
                val genInputs = for {
                    id <- PetsIdGetIdGenerator

                } yield id

                val inputs = genInputs suchThat { id=>
                    new PetsIdGetValidator(id).errors.isEmpty
                }
                val props = forAll(inputs) { i => testValidInput(i) }
                checkResult(props)
            }

        }
    }
