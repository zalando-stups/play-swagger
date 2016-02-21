package string_formats.yaml

import de.zalando.play.controllers._
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
import play.api.http.Writeable

import play.api.test.Helpers.{status => requestStatusCode_}
import play.api.test.Helpers.{contentAsString => requestContentAsString_}
import play.api.test.Helpers.{contentType => requestContentType_}

import Generators._

    @RunWith(classOf[JUnitRunner])
    class String_formatsYamlSpec extends Specification {
        def toPath[T](value: T)(implicit binder: PathBindable[T]): String = Option(binder.unbind("", value)).getOrElse("")
        def toQuery[T](key: String, value: T)(implicit binder: QueryStringBindable[T]): String = Option(binder.unbind(key, value)).getOrElse("")
        def toHeader[T](value: T)(implicit binder: PathBindable[T]): String = Option(binder.unbind("", value)).getOrElse("")

      def checkResult(props: Prop) =
        Test.check(Test.Parameters.default, props).status match {
          case Failed(_, labels) => failure(labels.mkString("\n"))
          case Proved(_) | Exhausted | Passed => success
          case PropException(_, e, labels) =>
            val error = if (labels.isEmpty) e.getLocalizedMessage() else labels.mkString("\n")
            failure(error)
        }

      private def parserConstructor(mimeType: String) = PlayBodyParsing.jacksonMapper(mimeType)

      def parseResponseContent[T](content: String, mimeType: Option[String], expectedType: Class[T]) =
        parserConstructor(mimeType.getOrElse("application/json")).readValue(content, expectedType)


    "GET /" should {
        def testInvalidInput(input: (BinaryString, GetBase64, GetDate, GetDate_time)) = {

            val (petId, base64, date, date_time) = input

            val url = s"""/?${toQuery("base64", base64)}&${toQuery("date", date)}&${toQuery("date_time", date_time)}"""
            val headers = Seq()
                val parsed_petId = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(petId)

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*).withBody(parsed_petId)).get
            val errors = new GetValidator(petId, base64, date, date_time).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" + "and body [" + parsed_petId + "]") |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (BinaryString, GetBase64, GetDate, GetDate_time)) = {
            val (petId, base64, date, date_time) = input
            
            val parsed_petId = parserConstructor("application/json").writeValueAsString(petId)
            
            val url = s"""/?${toQuery("base64", base64)}&${toQuery("date", date)}&${toQuery("date_time", date_time)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*).withBody(parsed_petId)).get
            val errors = new GetValidator(petId, base64, date, date_time).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]

            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" + "and body [" + parsed_petId + "]") |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                        petId <- BinaryStringGenerator
                        base64 <- GetBase64Generator
                        date <- GetDateGenerator
                        date_time <- GetDate_timeGenerator
                    
                } yield (petId, base64, date, date_time)
            val inputs = genInputs suchThat { case (petId, base64, date, date_time) =>
                new GetValidator(petId, base64, date, date_time).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    petId <- BinaryStringGenerator
                    base64 <- GetBase64Generator
                    date <- GetDateGenerator
                    date_time <- GetDate_timeGenerator
                
            } yield (petId, base64, date, date_time)
            val inputs = genInputs suchThat { case (petId, base64, date, date_time) =>
                new GetValidator(petId, base64, date, date_time).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
}
