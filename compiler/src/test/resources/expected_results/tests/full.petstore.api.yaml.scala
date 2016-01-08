package full.petstore.api.yaml

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
    class FullPetstoreApiYamlSpec extends Specification {
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

"POST /v2/users" should {
        def testInvalidInput(body: UsersUsernamePutBody) = {

            val url = s"""/v2/users"""
            val headers = Seq()
            val parsed_body = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(body)
            val path = route(FakeRequest(POST, url).withHeaders(headers:_*).withBody(parsed_body)).get
            val errors = new UsersPostValidator(body).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" + "and body [" + parsed_body + "]") |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(body: UsersUsernamePutBody) = {

            val parsed_body = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(body)
            val url = s"""/v2/users"""
            val headers = Seq()
            val path = route(FakeRequest(POST, url).withHeaders(headers:_*).withBody(parsed_body)).get
            ("given an URL: [" + url + "]"+ " and body [" + parsed_body + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    body <- UsersUsernamePutBodyGenerator

                } yield body

            val inputs = genInputs suchThat { body=>
                new UsersPostValidator(body).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                body <- UsersUsernamePutBodyGenerator

            } yield body

            val inputs = genInputs suchThat { body=>
                new UsersPostValidator(body).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
"POST /v2/pets" should {
        def testInvalidInput(body: PetsPostBody) = {

            val url = s"""/v2/pets"""
            val headers = Seq()
            val parsed_body = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(body)
            val path = route(FakeRequest(POST, url).withHeaders(headers:_*).withBody(parsed_body)).get
            val errors = new PetsPostValidator(body).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" + "and body [" + parsed_body + "]") |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(body: PetsPostBody) = {

            val parsed_body = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(body)
            val url = s"""/v2/pets"""
            val headers = Seq()
            val path = route(FakeRequest(POST, url).withHeaders(headers:_*).withBody(parsed_body)).get
            ("given an URL: [" + url + "]"+ " and body [" + parsed_body + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    body <- PetsPostBodyGenerator

                } yield body

            val inputs = genInputs suchThat { body=>
                new PetsPostValidator(body).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                body <- PetsPostBodyGenerator

            } yield body

            val inputs = genInputs suchThat { body=>
                new PetsPostValidator(body).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
"PUT /v2/pets" should {
        def testInvalidInput(body: PetsPostBody) = {

            val url = s"""/v2/pets"""
            val headers = Seq()
            val parsed_body = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(body)
            val path = route(FakeRequest(PUT, url).withHeaders(headers:_*).withBody(parsed_body)).get
            val errors = new PetsPutValidator(body).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" + "and body [" + parsed_body + "]") |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(body: PetsPostBody) = {

            val parsed_body = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(body)
            val url = s"""/v2/pets"""
            val headers = Seq()
            val path = route(FakeRequest(PUT, url).withHeaders(headers:_*).withBody(parsed_body)).get
            ("given an URL: [" + url + "]"+ " and body [" + parsed_body + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    body <- PetsPostBodyGenerator

                } yield body

            val inputs = genInputs suchThat { body=>
                new PetsPutValidator(body).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                body <- PetsPostBodyGenerator

            } yield body

            val inputs = genInputs suchThat { body=>
                new PetsPutValidator(body).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
"GET /v2/pets/findByStatus" should {
        def testInvalidInput(status: PetsFindByStatusGetStatus) = {

            val url = s"""/v2/pets/findByStatus?${toQuery("status", status)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new PetsFindByStatusGetValidator(status).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(status: PetsFindByStatusGetStatus) = {

            val url = s"""/v2/pets/findByStatus?${toQuery("status", status)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    status <- PetsFindByStatusGetStatusGenerator

                } yield status

            val inputs = genInputs suchThat { status=>
                new PetsFindByStatusGetValidator(status).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                status <- PetsFindByStatusGetStatusGenerator

            } yield status

            val inputs = genInputs suchThat { status=>
                new PetsFindByStatusGetValidator(status).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
"POST /v2/stores/order" should {
        def testInvalidInput(body: StoresOrderPostBody) = {

            val url = s"""/v2/stores/order"""
            val headers = Seq()
            val parsed_body = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(body)
            val path = route(FakeRequest(POST, url).withHeaders(headers:_*).withBody(parsed_body)).get
            val errors = new StoresOrderPostValidator(body).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" + "and body [" + parsed_body + "]") |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(body: StoresOrderPostBody) = {

            val parsed_body = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(body)
            val url = s"""/v2/stores/order"""
            val headers = Seq()
            val path = route(FakeRequest(POST, url).withHeaders(headers:_*).withBody(parsed_body)).get
            ("given an URL: [" + url + "]"+ " and body [" + parsed_body + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    body <- StoresOrderPostBodyGenerator

                } yield body

            val inputs = genInputs suchThat { body=>
                new StoresOrderPostValidator(body).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                body <- StoresOrderPostBodyGenerator

            } yield body

            val inputs = genInputs suchThat { body=>
                new StoresOrderPostValidator(body).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
"POST /v2/users/createWithArray" should {
        def testInvalidInput(body: UsersCreateWithListPostBody) = {

            val url = s"""/v2/users/createWithArray"""
            val headers = Seq()
            val parsed_body = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(body)
            val path = route(FakeRequest(POST, url).withHeaders(headers:_*).withBody(parsed_body)).get
            val errors = new UsersCreateWithArrayPostValidator(body).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" + "and body [" + parsed_body + "]") |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(body: UsersCreateWithListPostBody) = {

            val parsed_body = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(body)
            val url = s"""/v2/users/createWithArray"""
            val headers = Seq()
            val path = route(FakeRequest(POST, url).withHeaders(headers:_*).withBody(parsed_body)).get
            ("given an URL: [" + url + "]"+ " and body [" + parsed_body + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    body <- UsersCreateWithListPostBodyGenerator

                } yield body

            val inputs = genInputs suchThat { body=>
                new UsersCreateWithArrayPostValidator(body).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                body <- UsersCreateWithListPostBodyGenerator

            } yield body

            val inputs = genInputs suchThat { body=>
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
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new UsersLoginGetValidator(username, password).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (OrderStatus, OrderStatus)) = {

            val (username, password) = input
            val url = s"""/v2/users/login?${toQuery("username", username)}&${toQuery("password", password)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    username <- OrderStatusGenerator
                    password <- OrderStatusGenerator
                    

                } yield (username, password)

            val inputs = genInputs suchThat { case (username, password)=>
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

            val inputs = genInputs suchThat { case (username, password)=>
                new UsersLoginGetValidator(username, password).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
"GET /v2/stores/order/{orderId}" should {
        def testInvalidInput(orderId: String) = {

            val url = s"""/v2/stores/order/${toPath(orderId)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new StoresOrderOrderIdGetValidator(orderId).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(orderId: String) = {

            val url = s"""/v2/stores/order/${toPath(orderId)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    orderId <- StringGenerator

                } yield orderId

            val inputs = genInputs suchThat { orderId=>
                new StoresOrderOrderIdGetValidator(orderId).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                orderId <- StringGenerator

            } yield orderId

            val inputs = genInputs suchThat { orderId=>
                new StoresOrderOrderIdGetValidator(orderId).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
"GET /v2/pets/{petId}" should {
        def testInvalidInput(petId: Long) = {

            val url = s"""/v2/pets/${toPath(petId)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new PetsPetIdGetValidator(petId).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(petId: Long) = {

            val url = s"""/v2/pets/${toPath(petId)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    petId <- LongGenerator

                } yield petId

            val inputs = genInputs suchThat { petId=>
                new PetsPetIdGetValidator(petId).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                petId <- LongGenerator

            } yield petId

            val inputs = genInputs suchThat { petId=>
                new PetsPetIdGetValidator(petId).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
"GET /v2/users/{username}" should {
        def testInvalidInput(username: String) = {

            val url = s"""/v2/users/${toPath(username)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new UsersUsernameGetValidator(username).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(username: String) = {

            val url = s"""/v2/users/${toPath(username)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    username <- StringGenerator

                } yield username

            val inputs = genInputs suchThat { username=>
                new UsersUsernameGetValidator(username).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                username <- StringGenerator

            } yield username

            val inputs = genInputs suchThat { username=>
                new UsersUsernameGetValidator(username).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
"POST /v2/users/createWithList" should {
        def testInvalidInput(body: UsersCreateWithListPostBody) = {

            val url = s"""/v2/users/createWithList"""
            val headers = Seq()
            val parsed_body = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(body)
            val path = route(FakeRequest(POST, url).withHeaders(headers:_*).withBody(parsed_body)).get
            val errors = new UsersCreateWithListPostValidator(body).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" + "and body [" + parsed_body + "]") |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(body: UsersCreateWithListPostBody) = {

            val parsed_body = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(body)
            val url = s"""/v2/users/createWithList"""
            val headers = Seq()
            val path = route(FakeRequest(POST, url).withHeaders(headers:_*).withBody(parsed_body)).get
            ("given an URL: [" + url + "]"+ " and body [" + parsed_body + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    body <- UsersCreateWithListPostBodyGenerator

                } yield body

            val inputs = genInputs suchThat { body=>
                new UsersCreateWithListPostValidator(body).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                body <- UsersCreateWithListPostBodyGenerator

            } yield body

            val inputs = genInputs suchThat { body=>
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
            val headers = Seq()
            val path = route(FakeRequest(POST, url).withHeaders(headers:_*)).get
            val errors = new PetsPetIdPostValidator(petId, name, status).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (String, String, String)) = {

            val (petId, name, status) = input
            val url = s"""/v2/pets/${toPath(petId)}"""
            val headers = Seq()
            val path = route(FakeRequest(POST, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    petId <- StringGenerator
                    name <- StringGenerator
                    status <- StringGenerator
                    

                } yield (petId, name, status)

            val inputs = genInputs suchThat { case (petId, name, status)=>
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

            val inputs = genInputs suchThat { case (petId, name, status)=>
                new PetsPetIdPostValidator(petId, name, status).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
"DELETE /v2/users/{username}" should {
        def testInvalidInput(username: String) = {

            val url = s"""/v2/users/${toPath(username)}"""
            val headers = Seq()
            val path = route(FakeRequest(DELETE, url).withHeaders(headers:_*)).get
            val errors = new UsersUsernameDeleteValidator(username).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(username: String) = {

            val url = s"""/v2/users/${toPath(username)}"""
            val headers = Seq()
            val path = route(FakeRequest(DELETE, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    username <- StringGenerator

                } yield username

            val inputs = genInputs suchThat { username=>
                new UsersUsernameDeleteValidator(username).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                username <- StringGenerator

            } yield username

            val inputs = genInputs suchThat { username=>
                new UsersUsernameDeleteValidator(username).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
"DELETE /v2/stores/order/{orderId}" should {
        def testInvalidInput(orderId: String) = {

            val url = s"""/v2/stores/order/${toPath(orderId)}"""
            val headers = Seq()
            val path = route(FakeRequest(DELETE, url).withHeaders(headers:_*)).get
            val errors = new StoresOrderOrderIdDeleteValidator(orderId).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(orderId: String) = {

            val url = s"""/v2/stores/order/${toPath(orderId)}"""
            val headers = Seq()
            val path = route(FakeRequest(DELETE, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    orderId <- StringGenerator

                } yield orderId

            val inputs = genInputs suchThat { orderId=>
                new StoresOrderOrderIdDeleteValidator(orderId).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                orderId <- StringGenerator

            } yield orderId

            val inputs = genInputs suchThat { orderId=>
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
            val headers = Seq("api_key" -> toHeader(api_key))
            val path = route(FakeRequest(DELETE, url).withHeaders(headers:_*)).get
            val errors = new PetsPetIdDeleteValidator(api_key, petId).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (String, Long)) = {

            val (api_key, petId) = input
            val url = s"""/v2/pets/${toPath(petId)}"""
            val headers = Seq("api_key" -> api_key)
            val path = route(FakeRequest(DELETE, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    api_key <- StringGenerator
                    petId <- LongGenerator
                    

                } yield (api_key, petId)

            val inputs = genInputs suchThat { case (api_key, petId)=>
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

            val inputs = genInputs suchThat { case (api_key, petId)=>
                new PetsPetIdDeleteValidator(api_key, petId).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
"GET /v2/pets/findByTags" should {
        def testInvalidInput(tags: PetsFindByStatusGetStatus) = {

            val url = s"""/v2/pets/findByTags?${toQuery("tags", tags)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new PetsFindByTagsGetValidator(tags).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(tags: PetsFindByStatusGetStatus) = {

            val url = s"""/v2/pets/findByTags?${toQuery("tags", tags)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    tags <- PetsFindByStatusGetStatusGenerator

                } yield tags

            val inputs = genInputs suchThat { tags=>
                new PetsFindByTagsGetValidator(tags).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                tags <- PetsFindByStatusGetStatusGenerator

            } yield tags

            val inputs = genInputs suchThat { tags=>
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
            val headers = Seq()
            val parsed_body = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(body)
            val path = route(FakeRequest(PUT, url).withHeaders(headers:_*).withBody(parsed_body)).get
            val errors = new UsersUsernamePutValidator(username, body).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" + "and body [" + parsed_body + "]") |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (String, UsersUsernamePutBody)) = {

            val (username, body) = input
            val parsed_body = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(body)
            val url = s"""/v2/users/${toPath(username)}"""
            val headers = Seq()
            val path = route(FakeRequest(PUT, url).withHeaders(headers:_*).withBody(parsed_body)).get
            ("given an URL: [" + url + "]"+ " and body [" + parsed_body + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    username <- StringGenerator
                    body <- UsersUsernamePutBodyGenerator
                    

                } yield (username, body)

            val inputs = genInputs suchThat { case (username, body)=>
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

            val inputs = genInputs suchThat { case (username, body)=>
                new UsersUsernamePutValidator(username, body).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
}
