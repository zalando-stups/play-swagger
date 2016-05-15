package full.petstore.api.yaml

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

import de.zalando.play.controllers.ArrayWrapper
import org.joda.time.DateTime

import Generators._

    @RunWith(classOf[JUnitRunner])
    class FullPetstoreApiYamlSpec extends Specification {
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


    "POST /v2/users" should {
        def testInvalidInput(body: UsersUsernamePutBody) = {


            val url = s"""/v2/users"""
            val acceptHeaders: Seq[String] = Seq(
               "application/json", 
            
               "application/xml"
            )
            val propertyList = acceptHeaders.map { acceptHeader =>
                val headers =
                    Seq() :+ ("Accept" -> acceptHeader)

                    val parsed_body = PlayBodyParsing.jacksonMapper("Null").writeValueAsString(body)

                val request = FakeRequest(POST, url).withHeaders(headers:_*).withBody(parsed_body)
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

                val errors = new UsersPostValidator(body).errors

                lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

                ("given 'Accept' header '" + acceptHeader + "' and URL: [" + url + "]" + "and body [" + parsed_body + "]") |: all(
                    requestStatusCode_(path) ?= BAD_REQUEST ,
                    requestContentType_(path) ?= Some(acceptHeader),
                    errors.nonEmpty ?= true,
                    all(validations:_*)
                )
            }
            if (propertyList.isEmpty) throw new IllegalStateException(s"No 'produces' defined for the $url")
            propertyList.reduce(_ && _)
        }
        def testValidInput(body: UsersUsernamePutBody) = {
            
            val parsed_body = parserConstructor("Null").writeValueAsString(body)
            
            val url = s"""/v2/users"""
            val acceptHeaders: Seq[String] = Seq(
               "application/json", 
            
               "application/xml"
            )
            val propertyList = acceptHeaders.map { acceptHeader =>
                val headers =
                   Seq() :+ ("Accept" -> acceptHeader)

                val request = FakeRequest(POST, url).withHeaders(headers:_*).withBody(parsed_body)
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

                val errors = new UsersPostValidator(body).errors
                val possibleResponseTypes: Map[Int,Class[_ <: Any]] = Map.empty[Int,Class[Any]]

                val expectedCode = requestStatusCode_(path)
                val mimeType = requestContentType_(path)
                val mapper = parserConstructor(mimeType.getOrElse("application/json"))

                val parsedApiResponse = scala.util.Try {
                    parseResponseContent(mapper, requestContentAsString_(path), mimeType, possibleResponseTypes(expectedCode))
                }

                ("given response code " + expectedCode + " and 'Accept' header '" + acceptHeader + "' and URL: [" + url + "]" + "and body [" + parsed_body + "]") |: all(
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
                    body <- UsersUsernamePutBodyGenerator
                } yield body
            val inputs = genInputs suchThat { body =>
                new UsersPostValidator(body).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                body <- UsersUsernamePutBodyGenerator
            } yield body
            val inputs = genInputs suchThat { body =>
                new UsersPostValidator(body).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "POST /v2/pets" should {
        def testInvalidInput(body: PetsPostBody) = {


            val url = s"""/v2/pets"""
            val acceptHeaders: Seq[String] = Seq(
               "application/json", 
            
               "application/xml"
            )
            val propertyList = acceptHeaders.map { acceptHeader =>
                val headers =
                    Seq() :+ ("Accept" -> acceptHeader)

                    val parsed_body = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(body)

                val request = FakeRequest(POST, url).withHeaders(headers:_*).withBody(parsed_body)
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

                val errors = new PetsPostValidator(body).errors

                lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

                ("given 'Accept' header '" + acceptHeader + "' and URL: [" + url + "]" + "and body [" + parsed_body + "]") |: all(
                    requestStatusCode_(path) ?= BAD_REQUEST ,
                    requestContentType_(path) ?= Some(acceptHeader),
                    errors.nonEmpty ?= true,
                    all(validations:_*)
                )
            }
            if (propertyList.isEmpty) throw new IllegalStateException(s"No 'produces' defined for the $url")
            propertyList.reduce(_ && _)
        }
        def testValidInput(body: PetsPostBody) = {
            
            val parsed_body = parserConstructor("application/json").writeValueAsString(body)
            
            val url = s"""/v2/pets"""
            val acceptHeaders: Seq[String] = Seq(
               "application/json", 
            
               "application/xml"
            )
            val propertyList = acceptHeaders.map { acceptHeader =>
                val headers =
                   Seq() :+ ("Accept" -> acceptHeader)

                val request = FakeRequest(POST, url).withHeaders(headers:_*).withBody(parsed_body)
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

                val errors = new PetsPostValidator(body).errors
                val possibleResponseTypes: Map[Int,Class[_ <: Any]] = Map(
                    405 -> classOf[Null]
                )

                val expectedCode = requestStatusCode_(path)
                val mimeType = requestContentType_(path)
                val mapper = parserConstructor(mimeType.getOrElse("application/json"))

                val parsedApiResponse = scala.util.Try {
                    parseResponseContent(mapper, requestContentAsString_(path), mimeType, possibleResponseTypes(expectedCode))
                }

                ("given response code " + expectedCode + " and 'Accept' header '" + acceptHeader + "' and URL: [" + url + "]" + "and body [" + parsed_body + "]") |: all(
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
                    body <- PetsPostBodyGenerator
                } yield body
            val inputs = genInputs suchThat { body =>
                new PetsPostValidator(body).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                body <- PetsPostBodyGenerator
            } yield body
            val inputs = genInputs suchThat { body =>
                new PetsPostValidator(body).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "PUT /v2/pets" should {
        def testInvalidInput(body: PetsPostBody) = {


            val url = s"""/v2/pets"""
            val acceptHeaders: Seq[String] = Seq(
               "application/json", 
            
               "application/xml"
            )
            val propertyList = acceptHeaders.map { acceptHeader =>
                val headers =
                    Seq() :+ ("Accept" -> acceptHeader)

                    val parsed_body = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(body)

                val request = FakeRequest(PUT, url).withHeaders(headers:_*).withBody(parsed_body)
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

                val errors = new PetsPutValidator(body).errors

                lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

                ("given 'Accept' header '" + acceptHeader + "' and URL: [" + url + "]" + "and body [" + parsed_body + "]") |: all(
                    requestStatusCode_(path) ?= BAD_REQUEST ,
                    requestContentType_(path) ?= Some(acceptHeader),
                    errors.nonEmpty ?= true,
                    all(validations:_*)
                )
            }
            if (propertyList.isEmpty) throw new IllegalStateException(s"No 'produces' defined for the $url")
            propertyList.reduce(_ && _)
        }
        def testValidInput(body: PetsPostBody) = {
            
            val parsed_body = parserConstructor("application/json").writeValueAsString(body)
            
            val url = s"""/v2/pets"""
            val acceptHeaders: Seq[String] = Seq(
               "application/json", 
            
               "application/xml"
            )
            val propertyList = acceptHeaders.map { acceptHeader =>
                val headers =
                   Seq() :+ ("Accept" -> acceptHeader)

                val request = FakeRequest(PUT, url).withHeaders(headers:_*).withBody(parsed_body)
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

                val errors = new PetsPutValidator(body).errors
                val possibleResponseTypes: Map[Int,Class[_ <: Any]] = Map(
                    405 -> classOf[Null], 
                    404 -> classOf[Null], 
                    400 -> classOf[Null]
                )

                val expectedCode = requestStatusCode_(path)
                val mimeType = requestContentType_(path)
                val mapper = parserConstructor(mimeType.getOrElse("application/json"))

                val parsedApiResponse = scala.util.Try {
                    parseResponseContent(mapper, requestContentAsString_(path), mimeType, possibleResponseTypes(expectedCode))
                }

                ("given response code " + expectedCode + " and 'Accept' header '" + acceptHeader + "' and URL: [" + url + "]" + "and body [" + parsed_body + "]") |: all(
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
                    body <- PetsPostBodyGenerator
                } yield body
            val inputs = genInputs suchThat { body =>
                new PetsPutValidator(body).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                body <- PetsPostBodyGenerator
            } yield body
            val inputs = genInputs suchThat { body =>
                new PetsPutValidator(body).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "GET /v2/pets/findByStatus" should {
        def testInvalidInput(status: PetsFindByStatusGetStatus) = {


            val url = s"""/v2/pets/findByStatus?${toQuery("status", status)}"""
            val acceptHeaders: Seq[String] = Seq(
               "application/json", 
            
               "application/xml"
            )
            val propertyList = acceptHeaders.map { acceptHeader =>
                val headers =
                    Seq() :+ ("Accept" -> acceptHeader)


                val request = FakeRequest(GET, url).withHeaders(headers:_*)
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

                val errors = new PetsFindByStatusGetValidator(status).errors

                lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

                ("given 'Accept' header '" + acceptHeader + "' and URL: [" + url + "]" ) |: all(
                    requestStatusCode_(path) ?= BAD_REQUEST ,
                    requestContentType_(path) ?= Some(acceptHeader),
                    errors.nonEmpty ?= true,
                    all(validations:_*)
                )
            }
            if (propertyList.isEmpty) throw new IllegalStateException(s"No 'produces' defined for the $url")
            propertyList.reduce(_ && _)
        }
        def testValidInput(status: PetsFindByStatusGetStatus) = {
            
            val url = s"""/v2/pets/findByStatus?${toQuery("status", status)}"""
            val acceptHeaders: Seq[String] = Seq(
               "application/json", 
            
               "application/xml"
            )
            val propertyList = acceptHeaders.map { acceptHeader =>
                val headers =
                   Seq() :+ ("Accept" -> acceptHeader)

                val request = FakeRequest(GET, url).withHeaders(headers:_*)
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

                val errors = new PetsFindByStatusGetValidator(status).errors
                val possibleResponseTypes: Map[Int,Class[_ <: Any]] = Map(
                    200 -> classOf[Seq[Pet]], 
                    400 -> classOf[Null]
                )

                val expectedCode = requestStatusCode_(path)
                val mimeType = requestContentType_(path)
                val mapper = parserConstructor(mimeType.getOrElse("application/json"))

                val parsedApiResponse = scala.util.Try {
                    parseResponseContent(mapper, requestContentAsString_(path), mimeType, possibleResponseTypes(expectedCode))
                }

                ("given response code " + expectedCode + " and 'Accept' header '" + acceptHeader + "' and URL: [" + url + "]" ) |: all(
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
                    status <- PetsFindByStatusGetStatusGenerator
                } yield status
            val inputs = genInputs suchThat { status =>
                new PetsFindByStatusGetValidator(status).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                status <- PetsFindByStatusGetStatusGenerator
            } yield status
            val inputs = genInputs suchThat { status =>
                new PetsFindByStatusGetValidator(status).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "POST /v2/stores/order" should {
        def testInvalidInput(body: StoresOrderPostBody) = {


            val url = s"""/v2/stores/order"""
            val acceptHeaders: Seq[String] = Seq(
               "application/json", 
            
               "application/xml"
            )
            val propertyList = acceptHeaders.map { acceptHeader =>
                val headers =
                    Seq() :+ ("Accept" -> acceptHeader)

                    val parsed_body = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(body)

                val request = FakeRequest(POST, url).withHeaders(headers:_*).withBody(parsed_body)
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

                val errors = new StoresOrderPostValidator(body).errors

                lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

                ("given 'Accept' header '" + acceptHeader + "' and URL: [" + url + "]" + "and body [" + parsed_body + "]") |: all(
                    requestStatusCode_(path) ?= BAD_REQUEST ,
                    requestContentType_(path) ?= Some(acceptHeader),
                    errors.nonEmpty ?= true,
                    all(validations:_*)
                )
            }
            if (propertyList.isEmpty) throw new IllegalStateException(s"No 'produces' defined for the $url")
            propertyList.reduce(_ && _)
        }
        def testValidInput(body: StoresOrderPostBody) = {
            
            val parsed_body = parserConstructor("application/json").writeValueAsString(body)
            
            val url = s"""/v2/stores/order"""
            val acceptHeaders: Seq[String] = Seq(
               "application/json", 
            
               "application/xml"
            )
            val propertyList = acceptHeaders.map { acceptHeader =>
                val headers =
                   Seq() :+ ("Accept" -> acceptHeader)

                val request = FakeRequest(POST, url).withHeaders(headers:_*).withBody(parsed_body)
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

                val errors = new StoresOrderPostValidator(body).errors
                val possibleResponseTypes: Map[Int,Class[_ <: Any]] = Map(
                    400 -> classOf[Null], 
                    200 -> classOf[Order]
                )

                val expectedCode = requestStatusCode_(path)
                val mimeType = requestContentType_(path)
                val mapper = parserConstructor(mimeType.getOrElse("application/json"))

                val parsedApiResponse = scala.util.Try {
                    parseResponseContent(mapper, requestContentAsString_(path), mimeType, possibleResponseTypes(expectedCode))
                }

                ("given response code " + expectedCode + " and 'Accept' header '" + acceptHeader + "' and URL: [" + url + "]" + "and body [" + parsed_body + "]") |: all(
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
                    body <- StoresOrderPostBodyGenerator
                } yield body
            val inputs = genInputs suchThat { body =>
                new StoresOrderPostValidator(body).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                body <- StoresOrderPostBodyGenerator
            } yield body
            val inputs = genInputs suchThat { body =>
                new StoresOrderPostValidator(body).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "POST /v2/users/createWithArray" should {
        def testInvalidInput(body: UsersCreateWithListPostBody) = {


            val url = s"""/v2/users/createWithArray"""
            val acceptHeaders: Seq[String] = Seq(
               "application/json", 
            
               "application/xml"
            )
            val propertyList = acceptHeaders.map { acceptHeader =>
                val headers =
                    Seq() :+ ("Accept" -> acceptHeader)

                    val parsed_body = PlayBodyParsing.jacksonMapper("Null").writeValueAsString(body)

                val request = FakeRequest(POST, url).withHeaders(headers:_*).withBody(parsed_body)
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

                val errors = new UsersCreateWithArrayPostValidator(body).errors

                lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

                ("given 'Accept' header '" + acceptHeader + "' and URL: [" + url + "]" + "and body [" + parsed_body + "]") |: all(
                    requestStatusCode_(path) ?= BAD_REQUEST ,
                    requestContentType_(path) ?= Some(acceptHeader),
                    errors.nonEmpty ?= true,
                    all(validations:_*)
                )
            }
            if (propertyList.isEmpty) throw new IllegalStateException(s"No 'produces' defined for the $url")
            propertyList.reduce(_ && _)
        }
        def testValidInput(body: UsersCreateWithListPostBody) = {
            
            val parsed_body = parserConstructor("Null").writeValueAsString(body)
            
            val url = s"""/v2/users/createWithArray"""
            val acceptHeaders: Seq[String] = Seq(
               "application/json", 
            
               "application/xml"
            )
            val propertyList = acceptHeaders.map { acceptHeader =>
                val headers =
                   Seq() :+ ("Accept" -> acceptHeader)

                val request = FakeRequest(POST, url).withHeaders(headers:_*).withBody(parsed_body)
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

                val errors = new UsersCreateWithArrayPostValidator(body).errors
                val possibleResponseTypes: Map[Int,Class[_ <: Any]] = Map.empty[Int,Class[Any]]

                val expectedCode = requestStatusCode_(path)
                val mimeType = requestContentType_(path)
                val mapper = parserConstructor(mimeType.getOrElse("application/json"))

                val parsedApiResponse = scala.util.Try {
                    parseResponseContent(mapper, requestContentAsString_(path), mimeType, possibleResponseTypes(expectedCode))
                }

                ("given response code " + expectedCode + " and 'Accept' header '" + acceptHeader + "' and URL: [" + url + "]" + "and body [" + parsed_body + "]") |: all(
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
                    body <- UsersCreateWithListPostBodyGenerator
                } yield body
            val inputs = genInputs suchThat { body =>
                new UsersCreateWithArrayPostValidator(body).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                body <- UsersCreateWithListPostBodyGenerator
            } yield body
            val inputs = genInputs suchThat { body =>
                new UsersCreateWithArrayPostValidator(body).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "GET /v2/users/login" should {
        def testInvalidInput(input: (OrderStatus, OrderStatus)) = {

            val (username, password) = input

            val url = s"""/v2/users/login?${toQuery("username", username)}&${toQuery("password", password)}"""
            val acceptHeaders: Seq[String] = Seq(
               "application/json", 
            
               "application/xml"
            )
            val propertyList = acceptHeaders.map { acceptHeader =>
                val headers =
                    Seq() :+ ("Accept" -> acceptHeader)


                val request = FakeRequest(GET, url).withHeaders(headers:_*)
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

                val errors = new UsersLoginGetValidator(username, password).errors

                lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

                ("given 'Accept' header '" + acceptHeader + "' and URL: [" + url + "]" ) |: all(
                    requestStatusCode_(path) ?= BAD_REQUEST ,
                    requestContentType_(path) ?= Some(acceptHeader),
                    errors.nonEmpty ?= true,
                    all(validations:_*)
                )
            }
            if (propertyList.isEmpty) throw new IllegalStateException(s"No 'produces' defined for the $url")
            propertyList.reduce(_ && _)
        }
        def testValidInput(input: (OrderStatus, OrderStatus)) = {
            val (username, password) = input
            
            val url = s"""/v2/users/login?${toQuery("username", username)}&${toQuery("password", password)}"""
            val acceptHeaders: Seq[String] = Seq(
               "application/json", 
            
               "application/xml"
            )
            val propertyList = acceptHeaders.map { acceptHeader =>
                val headers =
                   Seq() :+ ("Accept" -> acceptHeader)

                val request = FakeRequest(GET, url).withHeaders(headers:_*)
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

                val errors = new UsersLoginGetValidator(username, password).errors
                val possibleResponseTypes: Map[Int,Class[_ <: Any]] = Map(
                    200 -> classOf[String], 
                    400 -> classOf[Null]
                )

                val expectedCode = requestStatusCode_(path)
                val mimeType = requestContentType_(path)
                val mapper = parserConstructor(mimeType.getOrElse("application/json"))

                val parsedApiResponse = scala.util.Try {
                    parseResponseContent(mapper, requestContentAsString_(path), mimeType, possibleResponseTypes(expectedCode))
                }

                ("given response code " + expectedCode + " and 'Accept' header '" + acceptHeader + "' and URL: [" + url + "]" ) |: all(
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
                        username <- OrderStatusGenerator
                        password <- OrderStatusGenerator
                    
                } yield (username, password)
            val inputs = genInputs suchThat { case (username, password) =>
                new UsersLoginGetValidator(username, password).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    username <- OrderStatusGenerator
                    password <- OrderStatusGenerator
                
            } yield (username, password)
            val inputs = genInputs suchThat { case (username, password) =>
                new UsersLoginGetValidator(username, password).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "GET /v2/stores/order/{orderId}" should {
        def testInvalidInput(orderId: String) = {


            val url = s"""/v2/stores/order/${toPath(orderId)}"""
            val acceptHeaders: Seq[String] = Seq(
               "application/json", 
            
               "application/xml"
            )
            val propertyList = acceptHeaders.map { acceptHeader =>
                val headers =
                    Seq() :+ ("Accept" -> acceptHeader)


                val request = FakeRequest(GET, url).withHeaders(headers:_*)
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

                val errors = new StoresOrderOrderIdGetValidator(orderId).errors

                lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

                ("given 'Accept' header '" + acceptHeader + "' and URL: [" + url + "]" ) |: all(
                    requestStatusCode_(path) ?= BAD_REQUEST ,
                    requestContentType_(path) ?= Some(acceptHeader),
                    errors.nonEmpty ?= true,
                    all(validations:_*)
                )
            }
            if (propertyList.isEmpty) throw new IllegalStateException(s"No 'produces' defined for the $url")
            propertyList.reduce(_ && _)
        }
        def testValidInput(orderId: String) = {
            
            val url = s"""/v2/stores/order/${toPath(orderId)}"""
            val acceptHeaders: Seq[String] = Seq(
               "application/json", 
            
               "application/xml"
            )
            val propertyList = acceptHeaders.map { acceptHeader =>
                val headers =
                   Seq() :+ ("Accept" -> acceptHeader)

                val request = FakeRequest(GET, url).withHeaders(headers:_*)
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

                val errors = new StoresOrderOrderIdGetValidator(orderId).errors
                val possibleResponseTypes: Map[Int,Class[_ <: Any]] = Map(
                    404 -> classOf[Null], 
                    400 -> classOf[Null], 
                    200 -> classOf[Order]
                )

                val expectedCode = requestStatusCode_(path)
                val mimeType = requestContentType_(path)
                val mapper = parserConstructor(mimeType.getOrElse("application/json"))

                val parsedApiResponse = scala.util.Try {
                    parseResponseContent(mapper, requestContentAsString_(path), mimeType, possibleResponseTypes(expectedCode))
                }

                ("given response code " + expectedCode + " and 'Accept' header '" + acceptHeader + "' and URL: [" + url + "]" ) |: all(
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
                    orderId <- StringGenerator
                } yield orderId
            val inputs = genInputs suchThat { orderId =>
                new StoresOrderOrderIdGetValidator(orderId).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                orderId <- StringGenerator
            } yield orderId
            val inputs = genInputs suchThat { orderId =>
                new StoresOrderOrderIdGetValidator(orderId).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "GET /v2/pets/{petId}" should {
        def testInvalidInput(petId: Long) = {


            val url = s"""/v2/pets/${toPath(petId)}"""
            val acceptHeaders: Seq[String] = Seq(
               "application/json", 
            
               "application/xml"
            )
            val propertyList = acceptHeaders.map { acceptHeader =>
                val headers =
                    Seq() :+ ("Accept" -> acceptHeader)


                val request = FakeRequest(GET, url).withHeaders(headers:_*)
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

                val errors = new PetsPetIdGetValidator(petId).errors

                lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

                ("given 'Accept' header '" + acceptHeader + "' and URL: [" + url + "]" ) |: all(
                    requestStatusCode_(path) ?= BAD_REQUEST ,
                    requestContentType_(path) ?= Some(acceptHeader),
                    errors.nonEmpty ?= true,
                    all(validations:_*)
                )
            }
            if (propertyList.isEmpty) throw new IllegalStateException(s"No 'produces' defined for the $url")
            propertyList.reduce(_ && _)
        }
        def testValidInput(petId: Long) = {
            
            val url = s"""/v2/pets/${toPath(petId)}"""
            val acceptHeaders: Seq[String] = Seq(
               "application/json", 
            
               "application/xml"
            )
            val propertyList = acceptHeaders.map { acceptHeader =>
                val headers =
                   Seq() :+ ("Accept" -> acceptHeader)

                val request = FakeRequest(GET, url).withHeaders(headers:_*)
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

                val errors = new PetsPetIdGetValidator(petId).errors
                val possibleResponseTypes: Map[Int,Class[_ <: Any]] = Map(
                    404 -> classOf[Null], 
                    400 -> classOf[Null], 
                    200 -> classOf[Pet]
                )

                val expectedCode = requestStatusCode_(path)
                val mimeType = requestContentType_(path)
                val mapper = parserConstructor(mimeType.getOrElse("application/json"))

                val parsedApiResponse = scala.util.Try {
                    parseResponseContent(mapper, requestContentAsString_(path), mimeType, possibleResponseTypes(expectedCode))
                }

                ("given response code " + expectedCode + " and 'Accept' header '" + acceptHeader + "' and URL: [" + url + "]" ) |: all(
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
                    petId <- LongGenerator
                } yield petId
            val inputs = genInputs suchThat { petId =>
                new PetsPetIdGetValidator(petId).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                petId <- LongGenerator
            } yield petId
            val inputs = genInputs suchThat { petId =>
                new PetsPetIdGetValidator(petId).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "GET /v2/users/{username}" should {
        def testInvalidInput(username: String) = {


            val url = s"""/v2/users/${toPath(username)}"""
            val acceptHeaders: Seq[String] = Seq(
               "application/json", 
            
               "application/xml"
            )
            val propertyList = acceptHeaders.map { acceptHeader =>
                val headers =
                    Seq() :+ ("Accept" -> acceptHeader)


                val request = FakeRequest(GET, url).withHeaders(headers:_*)
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

                val errors = new UsersUsernameGetValidator(username).errors

                lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

                ("given 'Accept' header '" + acceptHeader + "' and URL: [" + url + "]" ) |: all(
                    requestStatusCode_(path) ?= BAD_REQUEST ,
                    requestContentType_(path) ?= Some(acceptHeader),
                    errors.nonEmpty ?= true,
                    all(validations:_*)
                )
            }
            if (propertyList.isEmpty) throw new IllegalStateException(s"No 'produces' defined for the $url")
            propertyList.reduce(_ && _)
        }
        def testValidInput(username: String) = {
            
            val url = s"""/v2/users/${toPath(username)}"""
            val acceptHeaders: Seq[String] = Seq(
               "application/json", 
            
               "application/xml"
            )
            val propertyList = acceptHeaders.map { acceptHeader =>
                val headers =
                   Seq() :+ ("Accept" -> acceptHeader)

                val request = FakeRequest(GET, url).withHeaders(headers:_*)
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

                val errors = new UsersUsernameGetValidator(username).errors
                val possibleResponseTypes: Map[Int,Class[_ <: Any]] = Map(
                    200 -> classOf[User], 
                    404 -> classOf[Null], 
                    400 -> classOf[Null]
                )

                val expectedCode = requestStatusCode_(path)
                val mimeType = requestContentType_(path)
                val mapper = parserConstructor(mimeType.getOrElse("application/json"))

                val parsedApiResponse = scala.util.Try {
                    parseResponseContent(mapper, requestContentAsString_(path), mimeType, possibleResponseTypes(expectedCode))
                }

                ("given response code " + expectedCode + " and 'Accept' header '" + acceptHeader + "' and URL: [" + url + "]" ) |: all(
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
                    username <- StringGenerator
                } yield username
            val inputs = genInputs suchThat { username =>
                new UsersUsernameGetValidator(username).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                username <- StringGenerator
            } yield username
            val inputs = genInputs suchThat { username =>
                new UsersUsernameGetValidator(username).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "POST /v2/users/createWithList" should {
        def testInvalidInput(body: UsersCreateWithListPostBody) = {


            val url = s"""/v2/users/createWithList"""
            val acceptHeaders: Seq[String] = Seq(
               "application/json", 
            
               "application/xml"
            )
            val propertyList = acceptHeaders.map { acceptHeader =>
                val headers =
                    Seq() :+ ("Accept" -> acceptHeader)

                    val parsed_body = PlayBodyParsing.jacksonMapper("Null").writeValueAsString(body)

                val request = FakeRequest(POST, url).withHeaders(headers:_*).withBody(parsed_body)
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

                val errors = new UsersCreateWithListPostValidator(body).errors

                lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

                ("given 'Accept' header '" + acceptHeader + "' and URL: [" + url + "]" + "and body [" + parsed_body + "]") |: all(
                    requestStatusCode_(path) ?= BAD_REQUEST ,
                    requestContentType_(path) ?= Some(acceptHeader),
                    errors.nonEmpty ?= true,
                    all(validations:_*)
                )
            }
            if (propertyList.isEmpty) throw new IllegalStateException(s"No 'produces' defined for the $url")
            propertyList.reduce(_ && _)
        }
        def testValidInput(body: UsersCreateWithListPostBody) = {
            
            val parsed_body = parserConstructor("Null").writeValueAsString(body)
            
            val url = s"""/v2/users/createWithList"""
            val acceptHeaders: Seq[String] = Seq(
               "application/json", 
            
               "application/xml"
            )
            val propertyList = acceptHeaders.map { acceptHeader =>
                val headers =
                   Seq() :+ ("Accept" -> acceptHeader)

                val request = FakeRequest(POST, url).withHeaders(headers:_*).withBody(parsed_body)
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

                val errors = new UsersCreateWithListPostValidator(body).errors
                val possibleResponseTypes: Map[Int,Class[_ <: Any]] = Map.empty[Int,Class[Any]]

                val expectedCode = requestStatusCode_(path)
                val mimeType = requestContentType_(path)
                val mapper = parserConstructor(mimeType.getOrElse("application/json"))

                val parsedApiResponse = scala.util.Try {
                    parseResponseContent(mapper, requestContentAsString_(path), mimeType, possibleResponseTypes(expectedCode))
                }

                ("given response code " + expectedCode + " and 'Accept' header '" + acceptHeader + "' and URL: [" + url + "]" + "and body [" + parsed_body + "]") |: all(
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
                    body <- UsersCreateWithListPostBodyGenerator
                } yield body
            val inputs = genInputs suchThat { body =>
                new UsersCreateWithListPostValidator(body).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                body <- UsersCreateWithListPostBodyGenerator
            } yield body
            val inputs = genInputs suchThat { body =>
                new UsersCreateWithListPostValidator(body).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "POST /v2/pets/{petId}" should {
        def testInvalidInput(input: (String, String, String)) = {

            val (petId, name, status) = input

            val url = s"""/v2/pets/${toPath(petId)}"""
            val acceptHeaders: Seq[String] = Seq(
               "application/json", 
            
               "application/xml"
            )
            val propertyList = acceptHeaders.map { acceptHeader =>
                val headers =
                    Seq() :+ ("Accept" -> acceptHeader)


                val request = FakeRequest(POST, url).withHeaders(headers:_*)
                val path =
                    if (acceptHeader == "multipart/form-data") {
                        import de.zalando.play.controllers.WriteableWrapper.anyContentAsMultipartFormWritable

                        val files: Seq[FilePart[TemporaryFile]] = Nil
                        val data = Map.empty[String, Seq[String]]   ++ Seq("name" -> Seq(name.toString))     ++ Seq("status" -> Seq(status.toString))   
                        val form = new MultipartFormData(data, files, Nil, Nil)

                        route(request.withMultipartFormDataBody(form)).get
                    } else if (acceptHeader == "application/x-www-form-urlencoded") {
                        val form =   ("name" -> name.toString) ::     ("status" -> status.toString) ::    Nil
                        route(request.withFormUrlEncodedBody(form:_*)).get
                    } else route(request).get

                val errors = new PetsPetIdPostValidator(petId, name, status).errors

                lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

                ("given 'Accept' header '" + acceptHeader + "' and URL: [" + url + "]" ) |: all(
                    requestStatusCode_(path) ?= BAD_REQUEST ,
                    requestContentType_(path) ?= Some(acceptHeader),
                    errors.nonEmpty ?= true,
                    all(validations:_*)
                )
            }
            if (propertyList.isEmpty) throw new IllegalStateException(s"No 'produces' defined for the $url")
            propertyList.reduce(_ && _)
        }
        def testValidInput(input: (String, String, String)) = {
            val (petId, name, status) = input
            
            val url = s"""/v2/pets/${toPath(petId)}"""
            val acceptHeaders: Seq[String] = Seq(
               "application/json", 
            
               "application/xml"
            )
            val propertyList = acceptHeaders.map { acceptHeader =>
                val headers =
                   Seq() :+ ("Accept" -> acceptHeader)

                val request = FakeRequest(POST, url).withHeaders(headers:_*)
                val path =
                    if (acceptHeader == "multipart/form-data") {
                        import de.zalando.play.controllers.WriteableWrapper.anyContentAsMultipartFormWritable

                        val files: Seq[FilePart[TemporaryFile]] = Nil
                        val data = Map.empty[String, Seq[String]]   ++ Seq("name" -> Seq(name.toString))     ++ Seq("status" -> Seq(status.toString))   
                        val form = new MultipartFormData(data, files, Nil, Nil)

                        route(request.withMultipartFormDataBody(form)).get
                    } else if (acceptHeader == "application/x-www-form-urlencoded") {
                        val form =   ("name" -> name.toString) ::     ("status" -> status.toString) ::    Nil
                        route(request.withFormUrlEncodedBody(form:_*)).get
                    } else route(request).get

                val errors = new PetsPetIdPostValidator(petId, name, status).errors
                val possibleResponseTypes: Map[Int,Class[_ <: Any]] = Map(
                    405 -> classOf[Null]
                )

                val expectedCode = requestStatusCode_(path)
                val mimeType = requestContentType_(path)
                val mapper = parserConstructor(mimeType.getOrElse("application/json"))

                val parsedApiResponse = scala.util.Try {
                    parseResponseContent(mapper, requestContentAsString_(path), mimeType, possibleResponseTypes(expectedCode))
                }

                ("given response code " + expectedCode + " and 'Accept' header '" + acceptHeader + "' and URL: [" + url + "]" ) |: all(
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
                        petId <- StringGenerator
                        name <- StringGenerator
                        status <- StringGenerator
                    
                } yield (petId, name, status)
            val inputs = genInputs suchThat { case (petId, name, status) =>
                new PetsPetIdPostValidator(petId, name, status).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    petId <- StringGenerator
                    name <- StringGenerator
                    status <- StringGenerator
                
            } yield (petId, name, status)
            val inputs = genInputs suchThat { case (petId, name, status) =>
                new PetsPetIdPostValidator(petId, name, status).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "DELETE /v2/users/{username}" should {
        def testInvalidInput(username: String) = {


            val url = s"""/v2/users/${toPath(username)}"""
            val acceptHeaders: Seq[String] = Seq(
               "application/json", 
            
               "application/xml"
            )
            val propertyList = acceptHeaders.map { acceptHeader =>
                val headers =
                    Seq() :+ ("Accept" -> acceptHeader)


                val request = FakeRequest(DELETE, url).withHeaders(headers:_*)
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

                val errors = new UsersUsernameDeleteValidator(username).errors

                lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

                ("given 'Accept' header '" + acceptHeader + "' and URL: [" + url + "]" ) |: all(
                    requestStatusCode_(path) ?= BAD_REQUEST ,
                    requestContentType_(path) ?= Some(acceptHeader),
                    errors.nonEmpty ?= true,
                    all(validations:_*)
                )
            }
            if (propertyList.isEmpty) throw new IllegalStateException(s"No 'produces' defined for the $url")
            propertyList.reduce(_ && _)
        }
        def testValidInput(username: String) = {
            
            val url = s"""/v2/users/${toPath(username)}"""
            val acceptHeaders: Seq[String] = Seq(
               "application/json", 
            
               "application/xml"
            )
            val propertyList = acceptHeaders.map { acceptHeader =>
                val headers =
                   Seq() :+ ("Accept" -> acceptHeader)

                val request = FakeRequest(DELETE, url).withHeaders(headers:_*)
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

                val errors = new UsersUsernameDeleteValidator(username).errors
                val possibleResponseTypes: Map[Int,Class[_ <: Any]] = Map(
                    404 -> classOf[Null], 
                    400 -> classOf[Null]
                )

                val expectedCode = requestStatusCode_(path)
                val mimeType = requestContentType_(path)
                val mapper = parserConstructor(mimeType.getOrElse("application/json"))

                val parsedApiResponse = scala.util.Try {
                    parseResponseContent(mapper, requestContentAsString_(path), mimeType, possibleResponseTypes(expectedCode))
                }

                ("given response code " + expectedCode + " and 'Accept' header '" + acceptHeader + "' and URL: [" + url + "]" ) |: all(
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
                    username <- StringGenerator
                } yield username
            val inputs = genInputs suchThat { username =>
                new UsersUsernameDeleteValidator(username).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                username <- StringGenerator
            } yield username
            val inputs = genInputs suchThat { username =>
                new UsersUsernameDeleteValidator(username).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "DELETE /v2/stores/order/{orderId}" should {
        def testInvalidInput(orderId: String) = {


            val url = s"""/v2/stores/order/${toPath(orderId)}"""
            val acceptHeaders: Seq[String] = Seq(
               "application/json", 
            
               "application/xml"
            )
            val propertyList = acceptHeaders.map { acceptHeader =>
                val headers =
                    Seq() :+ ("Accept" -> acceptHeader)


                val request = FakeRequest(DELETE, url).withHeaders(headers:_*)
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

                val errors = new StoresOrderOrderIdDeleteValidator(orderId).errors

                lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

                ("given 'Accept' header '" + acceptHeader + "' and URL: [" + url + "]" ) |: all(
                    requestStatusCode_(path) ?= BAD_REQUEST ,
                    requestContentType_(path) ?= Some(acceptHeader),
                    errors.nonEmpty ?= true,
                    all(validations:_*)
                )
            }
            if (propertyList.isEmpty) throw new IllegalStateException(s"No 'produces' defined for the $url")
            propertyList.reduce(_ && _)
        }
        def testValidInput(orderId: String) = {
            
            val url = s"""/v2/stores/order/${toPath(orderId)}"""
            val acceptHeaders: Seq[String] = Seq(
               "application/json", 
            
               "application/xml"
            )
            val propertyList = acceptHeaders.map { acceptHeader =>
                val headers =
                   Seq() :+ ("Accept" -> acceptHeader)

                val request = FakeRequest(DELETE, url).withHeaders(headers:_*)
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

                val errors = new StoresOrderOrderIdDeleteValidator(orderId).errors
                val possibleResponseTypes: Map[Int,Class[_ <: Any]] = Map(
                    404 -> classOf[Null], 
                    400 -> classOf[Null]
                )

                val expectedCode = requestStatusCode_(path)
                val mimeType = requestContentType_(path)
                val mapper = parserConstructor(mimeType.getOrElse("application/json"))

                val parsedApiResponse = scala.util.Try {
                    parseResponseContent(mapper, requestContentAsString_(path), mimeType, possibleResponseTypes(expectedCode))
                }

                ("given response code " + expectedCode + " and 'Accept' header '" + acceptHeader + "' and URL: [" + url + "]" ) |: all(
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
                    orderId <- StringGenerator
                } yield orderId
            val inputs = genInputs suchThat { orderId =>
                new StoresOrderOrderIdDeleteValidator(orderId).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                orderId <- StringGenerator
            } yield orderId
            val inputs = genInputs suchThat { orderId =>
                new StoresOrderOrderIdDeleteValidator(orderId).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "DELETE /v2/pets/{petId}" should {
        def testInvalidInput(input: (String, Long)) = {

            val (api_key, petId) = input

            val url = s"""/v2/pets/${toPath(petId)}"""
            val acceptHeaders: Seq[String] = Seq(
               "application/json", 
            
               "application/xml"
            )
            val propertyList = acceptHeaders.map { acceptHeader =>
                val headers =
                    Seq(
                        "api_key" -> toHeader(api_key)
                        ) :+ ("Accept" -> acceptHeader)


                val request = FakeRequest(DELETE, url).withHeaders(headers:_*)
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

                val errors = new PetsPetIdDeleteValidator(api_key, petId).errors

                lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

                ("given 'Accept' header '" + acceptHeader + "' and URL: [" + url + "]" ) |: all(
                    requestStatusCode_(path) ?= BAD_REQUEST ,
                    requestContentType_(path) ?= Some(acceptHeader),
                    errors.nonEmpty ?= true,
                    all(validations:_*)
                )
            }
            if (propertyList.isEmpty) throw new IllegalStateException(s"No 'produces' defined for the $url")
            propertyList.reduce(_ && _)
        }
        def testValidInput(input: (String, Long)) = {
            val (api_key, petId) = input
            
            val url = s"""/v2/pets/${toPath(petId)}"""
            val acceptHeaders: Seq[String] = Seq(
               "application/json", 
            
               "application/xml"
            )
            val propertyList = acceptHeaders.map { acceptHeader =>
                val headers =
                   Seq(
                        "api_key" -> toHeader(api_key)
                    ) :+ ("Accept" -> acceptHeader)

                val request = FakeRequest(DELETE, url).withHeaders(headers:_*)
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

                val errors = new PetsPetIdDeleteValidator(api_key, petId).errors
                val possibleResponseTypes: Map[Int,Class[_ <: Any]] = Map(
                    400 -> classOf[Null]
                )

                val expectedCode = requestStatusCode_(path)
                val mimeType = requestContentType_(path)
                val mapper = parserConstructor(mimeType.getOrElse("application/json"))

                val parsedApiResponse = scala.util.Try {
                    parseResponseContent(mapper, requestContentAsString_(path), mimeType, possibleResponseTypes(expectedCode))
                }

                ("given response code " + expectedCode + " and 'Accept' header '" + acceptHeader + "' and URL: [" + url + "]" ) |: all(
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
                        api_key <- StringGenerator
                        petId <- LongGenerator
                    
                } yield (api_key, petId)
            val inputs = genInputs suchThat { case (api_key, petId) =>
                new PetsPetIdDeleteValidator(api_key, petId).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    api_key <- StringGenerator
                    petId <- LongGenerator
                
            } yield (api_key, petId)
            val inputs = genInputs suchThat { case (api_key, petId) =>
                new PetsPetIdDeleteValidator(api_key, petId).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "GET /v2/pets/findByTags" should {
        def testInvalidInput(tags: PetsFindByStatusGetStatus) = {


            val url = s"""/v2/pets/findByTags?${toQuery("tags", tags)}"""
            val acceptHeaders: Seq[String] = Seq(
               "application/json", 
            
               "application/xml"
            )
            val propertyList = acceptHeaders.map { acceptHeader =>
                val headers =
                    Seq() :+ ("Accept" -> acceptHeader)


                val request = FakeRequest(GET, url).withHeaders(headers:_*)
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

                val errors = new PetsFindByTagsGetValidator(tags).errors

                lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

                ("given 'Accept' header '" + acceptHeader + "' and URL: [" + url + "]" ) |: all(
                    requestStatusCode_(path) ?= BAD_REQUEST ,
                    requestContentType_(path) ?= Some(acceptHeader),
                    errors.nonEmpty ?= true,
                    all(validations:_*)
                )
            }
            if (propertyList.isEmpty) throw new IllegalStateException(s"No 'produces' defined for the $url")
            propertyList.reduce(_ && _)
        }
        def testValidInput(tags: PetsFindByStatusGetStatus) = {
            
            val url = s"""/v2/pets/findByTags?${toQuery("tags", tags)}"""
            val acceptHeaders: Seq[String] = Seq(
               "application/json", 
            
               "application/xml"
            )
            val propertyList = acceptHeaders.map { acceptHeader =>
                val headers =
                   Seq() :+ ("Accept" -> acceptHeader)

                val request = FakeRequest(GET, url).withHeaders(headers:_*)
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

                val errors = new PetsFindByTagsGetValidator(tags).errors
                val possibleResponseTypes: Map[Int,Class[_ <: Any]] = Map(
                    400 -> classOf[Null], 
                    200 -> classOf[Seq[Pet]]
                )

                val expectedCode = requestStatusCode_(path)
                val mimeType = requestContentType_(path)
                val mapper = parserConstructor(mimeType.getOrElse("application/json"))

                val parsedApiResponse = scala.util.Try {
                    parseResponseContent(mapper, requestContentAsString_(path), mimeType, possibleResponseTypes(expectedCode))
                }

                ("given response code " + expectedCode + " and 'Accept' header '" + acceptHeader + "' and URL: [" + url + "]" ) |: all(
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
                    tags <- PetsFindByStatusGetStatusGenerator
                } yield tags
            val inputs = genInputs suchThat { tags =>
                new PetsFindByTagsGetValidator(tags).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                tags <- PetsFindByStatusGetStatusGenerator
            } yield tags
            val inputs = genInputs suchThat { tags =>
                new PetsFindByTagsGetValidator(tags).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "PUT /v2/users/{username}" should {
        def testInvalidInput(input: (String, UsersUsernamePutBody)) = {

            val (username, body) = input

            val url = s"""/v2/users/${toPath(username)}"""
            val acceptHeaders: Seq[String] = Seq(
               "application/json", 
            
               "application/xml"
            )
            val propertyList = acceptHeaders.map { acceptHeader =>
                val headers =
                    Seq() :+ ("Accept" -> acceptHeader)

                    val parsed_body = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(body)

                val request = FakeRequest(PUT, url).withHeaders(headers:_*).withBody(parsed_body)
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

                val errors = new UsersUsernamePutValidator(username, body).errors

                lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

                ("given 'Accept' header '" + acceptHeader + "' and URL: [" + url + "]" + "and body [" + parsed_body + "]") |: all(
                    requestStatusCode_(path) ?= BAD_REQUEST ,
                    requestContentType_(path) ?= Some(acceptHeader),
                    errors.nonEmpty ?= true,
                    all(validations:_*)
                )
            }
            if (propertyList.isEmpty) throw new IllegalStateException(s"No 'produces' defined for the $url")
            propertyList.reduce(_ && _)
        }
        def testValidInput(input: (String, UsersUsernamePutBody)) = {
            val (username, body) = input
            
            val parsed_body = parserConstructor("application/json").writeValueAsString(body)
            
            val url = s"""/v2/users/${toPath(username)}"""
            val acceptHeaders: Seq[String] = Seq(
               "application/json", 
            
               "application/xml"
            )
            val propertyList = acceptHeaders.map { acceptHeader =>
                val headers =
                   Seq() :+ ("Accept" -> acceptHeader)

                val request = FakeRequest(PUT, url).withHeaders(headers:_*).withBody(parsed_body)
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

                val errors = new UsersUsernamePutValidator(username, body).errors
                val possibleResponseTypes: Map[Int,Class[_ <: Any]] = Map(
                    404 -> classOf[Null], 
                    400 -> classOf[Null]
                )

                val expectedCode = requestStatusCode_(path)
                val mimeType = requestContentType_(path)
                val mapper = parserConstructor(mimeType.getOrElse("application/json"))

                val parsedApiResponse = scala.util.Try {
                    parseResponseContent(mapper, requestContentAsString_(path), mimeType, possibleResponseTypes(expectedCode))
                }

                ("given response code " + expectedCode + " and 'Accept' header '" + acceptHeader + "' and URL: [" + url + "]" + "and body [" + parsed_body + "]") |: all(
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
                        username <- StringGenerator
                        body <- UsersUsernamePutBodyGenerator
                    
                } yield (username, body)
            val inputs = genInputs suchThat { case (username, body) =>
                new UsersUsernamePutValidator(username, body).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    username <- StringGenerator
                    body <- UsersUsernamePutBodyGenerator
                
            } yield (username, body)
            val inputs = genInputs suchThat { case (username, body) =>
                new UsersUsernamePutValidator(username, body).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
}
