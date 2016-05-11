package string_formats.yaml

import de.zalando.play.controllers._
import org.scalacheck._
import org.scalacheck.Arbitrary._
import org.scalacheck.Prop._
import org.scalacheck.Test._
import org.specs2.mutable._
import play.api.test.Helpers._
import play.api.test._
import play.api.mvc.MultipartFormData.FilePart
import play.api.mvc._

import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import java.net.URLEncoder
import com.fasterxml.jackson.databind.ObjectMapper

import play.api.http.Writeable
import play.api.libs.Files.TemporaryFile
import play.api.test.Helpers.{status => requestStatusCode_}
import play.api.test.Helpers.{contentAsString => requestContentAsString_}
import play.api.test.Helpers.{contentType => requestContentType_}

import de.zalando.play.controllers.Base64String
import Base64String._
import de.zalando.play.controllers.BinaryString
import BinaryString._
import org.joda.time.DateTime
import org.joda.time.LocalDate

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

      def parseResponseContent[T](mapper: ObjectMapper, content: String, mimeType: Option[String], expectedType: Class[T]) =
        mapper.readValue(content, expectedType)


    "GET /" should {
        def testInvalidInput(input: (BinaryString, GetBase64, GetDate, GetDate_time)) = {

            val (petId, base64, date, date_time) = input

            val url = s"""/?${toQuery("base64", base64)}&${toQuery("date", date)}&${toQuery("date_time", date_time)}"""
            val acceptHeaders: Seq[String] = Seq(
               "application/json", 
            
               "application/yaml"
            )
            val propertyList = acceptHeaders.map { acceptHeader =>
                val headers =
                    Seq() :+ ("Accept" -> acceptHeader)

                    val parsed_petId = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(petId)

                val request = FakeRequest(GET, url).withHeaders(headers:_*).withBody(parsed_petId)
                val path =
                    if (acceptHeader == "multipart/form-data") {
                        import de.zalando.play.controllers.WriteableWrapper.anyContentAsMultipartFormWritable

                        val files: Seq[FilePart[TemporaryFile]] = Nil
                        val data = Map.empty[String, Seq[String]] 
                        val form = new MultipartFormData(data, files, Nil, Nil)

                        route(request.withMultipartFormDataBody(form)).get
                    } else if (acceptHeader == "application/x-www-form-urlencoded") {
                        val form =  Nil
                        route(request.withFormUrlEncodedBody(form:_*)).get
                    } else route(request).get

                val errors = new GetValidator(petId, base64, date, date_time).errors

                lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

                ("given 'Accept' header '" + acceptHeader + "' and URL: [" + url + "]" + "and body [" + parsed_petId + "]") |: all(
                    requestStatusCode_(path) ?= BAD_REQUEST ,
                    requestContentType_(path) ?= Some(acceptHeader),
                    errors.nonEmpty ?= true,
                    all(validations:_*)
                )
            }
            if (propertyList.isEmpty) throw new IllegalStateException(s"No 'produces' defined for the $url")
            propertyList.reduce(_ && _)
        }
        def testValidInput(input: (BinaryString, GetBase64, GetDate, GetDate_time)) = {
            val (petId, base64, date, date_time) = input
            
            val parsed_petId = parserConstructor("application/json").writeValueAsString(petId)
            
            val url = s"""/?${toQuery("base64", base64)}&${toQuery("date", date)}&${toQuery("date_time", date_time)}"""
            val acceptHeaders: Seq[String] = Seq(
               "application/json", 
            
               "application/yaml"
            )
            val propertyList = acceptHeaders.map { acceptHeader =>
                val headers =
                   Seq() :+ ("Accept" -> acceptHeader)

                val request = FakeRequest(GET, url).withHeaders(headers:_*).withBody(parsed_petId)
                val path =
                    if (acceptHeader == "multipart/form-data") {
                        import de.zalando.play.controllers.WriteableWrapper.anyContentAsMultipartFormWritable

                        val files: Seq[FilePart[TemporaryFile]] = Nil
                        val data = Map.empty[String, Seq[String]] 
                        val form = new MultipartFormData(data, files, Nil, Nil)

                        route(request.withMultipartFormDataBody(form)).get
                    } else if (acceptHeader == "application/x-www-form-urlencoded") {
                        val form =  Nil
                        route(request.withFormUrlEncodedBody(form:_*)).get
                    } else route(request).get

                val errors = new GetValidator(petId, base64, date, date_time).errors
                val possibleResponseTypes: Map[Int,Class[_ <: Any]] = Map(
                    200 -> classOf[Null]
                )

                val expectedCode = requestStatusCode_(path)
                val mimeType = requestContentType_(path)
                val mapper = parserConstructor(mimeType.getOrElse("application/json"))

                val parsedApiResponse = scala.util.Try {
                    parseResponseContent(mapper, requestContentAsString_(path), mimeType, possibleResponseTypes(expectedCode))
                }

                ("given response code " + expectedCode + " and 'Accept' header '" + acceptHeader + "' and URL: [" + url + "]" + "and body [" + parsed_petId + "]") |: all(
                    possibleResponseTypes.contains(expectedCode) ?= true,
                    parsedApiResponse.isSuccess ?= true,
                    requestContentType_(path) ?= Some(acceptHeader),
                    errors.isEmpty ?= true
                )
            }
            if (propertyList.isEmpty) throw new IllegalStateException(s"No 'produces' defined for the $url")
            propertyList.reduce(_ && _)
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
