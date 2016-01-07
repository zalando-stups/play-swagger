package error_in_array.yaml

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
    class Error_in_arrayYamlSpec extends Specification {
      
      def checkResult(props: Prop) =
        Test.check(Test.Parameters.default, props).status match {
          case Failed(_, labels) => failure(labels.mkString("\\n"))
          case Proved(_) | Exhausted | Passed => success
          case PropException(_, e, labels) =>
            val error = if (labels.isEmpty) e.getLocalizedMessage() else labels.mkString("\\n")
            failure(error)
        }

"GET /schema/model" should {
            def testInvalidInput(root: ModelSchemaRoot) = {

                val url = s"""/schema/model"""
                val headers = Seq()
                val parsed_root = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(root)
                val path = route(FakeRequest(GET, url).withHeaders(headers:_*).withBody(parsed_root)).get
                val errors = new SchemaModelGetValidator(root).errors


                lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

                ("given an URL: [" + url + "]" + "and body [" + parsed_root + "]") |: all(
                    status(path) ?= BAD_REQUEST ,
                    contentType(path) ?= Some("application/json"),
                    errors.nonEmpty ?= true,
                    all(validations:_*)
                )
            }
            def testValidInput(root: ModelSchemaRoot) = {

                val parsed_root = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(root)
                val url = s"""/schema/model"""
                val headers = Seq()
                val path = route(FakeRequest(GET, url).withHeaders(headers:_*).withBody(parsed_root)).get
                ("given an URL: [" + url + "]"+ " and body [" + parsed_root + "]") |: (status(path) ?= OK)
            }
            "discard invalid data" in new WithApplication {
                val genInputs = for {
                        root <- ModelSchemaRootGenerator

                    } yield root

                val inputs = genInputs suchThat { root=>
                    new SchemaModelGetValidator(root).errors.nonEmpty
                }
                val props = forAll(inputs) { i => testInvalidInput(i) }
                checkResult(props)
            }
            "do something with valid data" in new WithApplication {
                val genInputs = for {
                    root <- ModelSchemaRootGenerator

                } yield root

                val inputs = genInputs suchThat { root=>
                    new SchemaModelGetValidator(root).errors.isEmpty
                }
                val props = forAll(inputs) { i => testValidInput(i) }
                checkResult(props)
            }

        }
    }
