package type_deduplication.yaml

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
    class Type_deduplicationYamlSpec extends Specification {
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


    "DELETE /api/users/{user_id}" should {
        def testInvalidInput(input: (String, User)) = {

            val (user_id, user) = input

            val url = s"""/api/users/${toPath(user_id)}"""
            val headers = Seq()
                val parsed_user = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(user)

            val path = route(FakeRequest(DELETE, url).withHeaders(headers:_*).withBody(parsed_user)).get
            val errors = new UsersUser_idDeleteValidator(user_id, user).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" + "and body [" + parsed_user + "]") |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (String, User)) = {
            val (user_id, user) = input
            
            val parsed_user = parserConstructor("application/json").writeValueAsString(user)
            
            val url = s"""/api/users/${toPath(user_id)}"""
            val headers = Seq()
            val path = route(FakeRequest(DELETE, url).withHeaders(headers:_*).withBody(parsed_user)).get
            val errors = new UsersUser_idDeleteValidator(user_id, user).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]

            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" + "and body [" + parsed_user + "]") |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                        user_id <- StringGenerator
                        user <- UserGenerator
                    
                } yield (user_id, user)
            val inputs = genInputs suchThat { case (user_id, user) =>
                new UsersUser_idDeleteValidator(user_id, user).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    user_id <- StringGenerator
                    user <- UserGenerator
                
            } yield (user_id, user)
            val inputs = genInputs suchThat { case (user_id, user) =>
                new UsersUser_idDeleteValidator(user_id, user).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "POST /api/users" should {
        def testInvalidInput(signin_data: SigninData) = {


            val url = s"""/api/users"""
            val headers = Seq()
                val parsed_signin_data = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(signin_data)

            val path = route(FakeRequest(POST, url).withHeaders(headers:_*).withBody(parsed_signin_data)).get
            val errors = new UsersPostValidator(signin_data).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" + "and body [" + parsed_signin_data + "]") |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(signin_data: SigninData) = {
            
            val parsed_signin_data = parserConstructor("application/json").writeValueAsString(signin_data)
            
            val url = s"""/api/users"""
            val headers = Seq()
            val path = route(FakeRequest(POST, url).withHeaders(headers:_*).withBody(parsed_signin_data)).get
            val errors = new UsersPostValidator(signin_data).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]

            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" + "and body [" + parsed_signin_data + "]") |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    signin_data <- SigninDataGenerator
                } yield signin_data
            val inputs = genInputs suchThat { signin_data =>
                new UsersPostValidator(signin_data).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                signin_data <- SigninDataGenerator
            } yield signin_data
            val inputs = genInputs suchThat { signin_data =>
                new UsersPostValidator(signin_data).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "GET /api/plants/{plant_id}/water_needs" should {
        def testInvalidInput(plant_id: String) = {


            val url = s"""/api/plants/${toPath(plant_id)}/water_needs"""
            val headers = Seq()

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new PlantsPlant_idWater_needsGetValidator(plant_id).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(plant_id: String) = {
            
            val url = s"""/api/plants/${toPath(plant_id)}/water_needs"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new PlantsPlant_idWater_needsGetValidator(plant_id).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]

            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    plant_id <- StringGenerator
                } yield plant_id
            val inputs = genInputs suchThat { plant_id =>
                new PlantsPlant_idWater_needsGetValidator(plant_id).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                plant_id <- StringGenerator
            } yield plant_id
            val inputs = genInputs suchThat { plant_id =>
                new PlantsPlant_idWater_needsGetValidator(plant_id).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "PUT /api/plants/{plant_id}/sunlight_needs" should {
        def testInvalidInput(input: (String, SunlightNeeds)) = {

            val (plant_id, sunlight_needs) = input

            val url = s"""/api/plants/${toPath(plant_id)}/sunlight_needs"""
            val headers = Seq()
                val parsed_sunlight_needs = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(sunlight_needs)

            val path = route(FakeRequest(PUT, url).withHeaders(headers:_*).withBody(parsed_sunlight_needs)).get
            val errors = new PlantsPlant_idSunlight_needsPutValidator(plant_id, sunlight_needs).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" + "and body [" + parsed_sunlight_needs + "]") |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (String, SunlightNeeds)) = {
            val (plant_id, sunlight_needs) = input
            
            val parsed_sunlight_needs = parserConstructor("application/json").writeValueAsString(sunlight_needs)
            
            val url = s"""/api/plants/${toPath(plant_id)}/sunlight_needs"""
            val headers = Seq()
            val path = route(FakeRequest(PUT, url).withHeaders(headers:_*).withBody(parsed_sunlight_needs)).get
            val errors = new PlantsPlant_idSunlight_needsPutValidator(plant_id, sunlight_needs).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]

            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" + "and body [" + parsed_sunlight_needs + "]") |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                        plant_id <- StringGenerator
                        sunlight_needs <- SunlightNeedsGenerator
                    
                } yield (plant_id, sunlight_needs)
            val inputs = genInputs suchThat { case (plant_id, sunlight_needs) =>
                new PlantsPlant_idSunlight_needsPutValidator(plant_id, sunlight_needs).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    plant_id <- StringGenerator
                    sunlight_needs <- SunlightNeedsGenerator
                
            } yield (plant_id, sunlight_needs)
            val inputs = genInputs suchThat { case (plant_id, sunlight_needs) =>
                new PlantsPlant_idSunlight_needsPutValidator(plant_id, sunlight_needs).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "GET /api/plants/{plant_id}" should {
        def testInvalidInput(plant_id: String) = {


            val url = s"""/api/plants/${toPath(plant_id)}"""
            val headers = Seq()

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new PlantsPlant_idGetValidator(plant_id).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(plant_id: String) = {
            
            val url = s"""/api/plants/${toPath(plant_id)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new PlantsPlant_idGetValidator(plant_id).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]

            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    plant_id <- StringGenerator
                } yield plant_id
            val inputs = genInputs suchThat { plant_id =>
                new PlantsPlant_idGetValidator(plant_id).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                plant_id <- StringGenerator
            } yield plant_id
            val inputs = genInputs suchThat { plant_id =>
                new PlantsPlant_idGetValidator(plant_id).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "GET /api/plants/{plant_id}/waterings/{watering_id}" should {
        def testInvalidInput(input: (String, String)) = {

            val (plant_id, watering_id) = input

            val url = s"""/api/plants/${toPath(plant_id)}/waterings/${toPath(watering_id)}"""
            val headers = Seq()

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new PlantsPlant_idWateringsWatering_idGetValidator(plant_id, watering_id).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (String, String)) = {
            val (plant_id, watering_id) = input
            
            val url = s"""/api/plants/${toPath(plant_id)}/waterings/${toPath(watering_id)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new PlantsPlant_idWateringsWatering_idGetValidator(plant_id, watering_id).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]

            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                        plant_id <- StringGenerator
                        watering_id <- StringGenerator
                    
                } yield (plant_id, watering_id)
            val inputs = genInputs suchThat { case (plant_id, watering_id) =>
                new PlantsPlant_idWateringsWatering_idGetValidator(plant_id, watering_id).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    plant_id <- StringGenerator
                    watering_id <- StringGenerator
                
            } yield (plant_id, watering_id)
            val inputs = genInputs suchThat { case (plant_id, watering_id) =>
                new PlantsPlant_idWateringsWatering_idGetValidator(plant_id, watering_id).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "PUT /api/plants/{plant_id}/water_needs" should {
        def testInvalidInput(input: (String, WaterNeeds)) = {

            val (plant_id, water_needs) = input

            val url = s"""/api/plants/${toPath(plant_id)}/water_needs"""
            val headers = Seq()
                val parsed_water_needs = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(water_needs)

            val path = route(FakeRequest(PUT, url).withHeaders(headers:_*).withBody(parsed_water_needs)).get
            val errors = new PlantsPlant_idWater_needsPutValidator(plant_id, water_needs).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" + "and body [" + parsed_water_needs + "]") |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (String, WaterNeeds)) = {
            val (plant_id, water_needs) = input
            
            val parsed_water_needs = parserConstructor("application/json").writeValueAsString(water_needs)
            
            val url = s"""/api/plants/${toPath(plant_id)}/water_needs"""
            val headers = Seq()
            val path = route(FakeRequest(PUT, url).withHeaders(headers:_*).withBody(parsed_water_needs)).get
            val errors = new PlantsPlant_idWater_needsPutValidator(plant_id, water_needs).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]

            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" + "and body [" + parsed_water_needs + "]") |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                        plant_id <- StringGenerator
                        water_needs <- WaterNeedsGenerator
                    
                } yield (plant_id, water_needs)
            val inputs = genInputs suchThat { case (plant_id, water_needs) =>
                new PlantsPlant_idWater_needsPutValidator(plant_id, water_needs).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    plant_id <- StringGenerator
                    water_needs <- WaterNeedsGenerator
                
            } yield (plant_id, water_needs)
            val inputs = genInputs suchThat { case (plant_id, water_needs) =>
                new PlantsPlant_idWater_needsPutValidator(plant_id, water_needs).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "GET /api/plants/{plant_id}/location" should {
        def testInvalidInput(plant_id: String) = {


            val url = s"""/api/plants/${toPath(plant_id)}/location"""
            val headers = Seq()

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new PlantsPlant_idLocationGetValidator(plant_id).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(plant_id: String) = {
            
            val url = s"""/api/plants/${toPath(plant_id)}/location"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new PlantsPlant_idLocationGetValidator(plant_id).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]

            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    plant_id <- StringGenerator
                } yield plant_id
            val inputs = genInputs suchThat { plant_id =>
                new PlantsPlant_idLocationGetValidator(plant_id).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                plant_id <- StringGenerator
            } yield plant_id
            val inputs = genInputs suchThat { plant_id =>
                new PlantsPlant_idLocationGetValidator(plant_id).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "GET /api/user/{user_id}/plants" should {
        def testInvalidInput(input: (String, ErrorCode, ErrorCode)) = {

            val (user_id, limit, offset) = input

            val url = s"""/api/user/${toPath(user_id)}/plants?${toQuery("limit", limit)}&${toQuery("offset", offset)}"""
            val headers = Seq()

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new UserUser_idPlantsGetValidator(user_id, limit, offset).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (String, ErrorCode, ErrorCode)) = {
            val (user_id, limit, offset) = input
            
            val url = s"""/api/user/${toPath(user_id)}/plants?${toQuery("limit", limit)}&${toQuery("offset", offset)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new UserUser_idPlantsGetValidator(user_id, limit, offset).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]

            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                        user_id <- StringGenerator
                        limit <- ErrorCodeGenerator
                        offset <- ErrorCodeGenerator
                    
                } yield (user_id, limit, offset)
            val inputs = genInputs suchThat { case (user_id, limit, offset) =>
                new UserUser_idPlantsGetValidator(user_id, limit, offset).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    user_id <- StringGenerator
                    limit <- ErrorCodeGenerator
                    offset <- ErrorCodeGenerator
                
            } yield (user_id, limit, offset)
            val inputs = genInputs suchThat { case (user_id, limit, offset) =>
                new UserUser_idPlantsGetValidator(user_id, limit, offset).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "GET /api/users/{user_id}" should {
        def testInvalidInput(user_id: String) = {


            val url = s"""/api/users/${toPath(user_id)}"""
            val headers = Seq()

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new UsersUser_idGetValidator(user_id).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(user_id: String) = {
            
            val url = s"""/api/users/${toPath(user_id)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new UsersUser_idGetValidator(user_id).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]

            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    user_id <- StringGenerator
                } yield user_id
            val inputs = genInputs suchThat { user_id =>
                new UsersUser_idGetValidator(user_id).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                user_id <- StringGenerator
            } yield user_id
            val inputs = genInputs suchThat { user_id =>
                new UsersUser_idGetValidator(user_id).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "PUT /api/plants/{plant_id}/location" should {
        def testInvalidInput(input: (String, Location)) = {

            val (plant_id, location) = input

            val url = s"""/api/plants/${toPath(plant_id)}/location"""
            val headers = Seq()
                val parsed_location = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(location)

            val path = route(FakeRequest(PUT, url).withHeaders(headers:_*).withBody(parsed_location)).get
            val errors = new PlantsPlant_idLocationPutValidator(plant_id, location).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" + "and body [" + parsed_location + "]") |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (String, Location)) = {
            val (plant_id, location) = input
            
            val parsed_location = parserConstructor("application/json").writeValueAsString(location)
            
            val url = s"""/api/plants/${toPath(plant_id)}/location"""
            val headers = Seq()
            val path = route(FakeRequest(PUT, url).withHeaders(headers:_*).withBody(parsed_location)).get
            val errors = new PlantsPlant_idLocationPutValidator(plant_id, location).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]

            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" + "and body [" + parsed_location + "]") |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                        plant_id <- StringGenerator
                        location <- LocationGenerator
                    
                } yield (plant_id, location)
            val inputs = genInputs suchThat { case (plant_id, location) =>
                new PlantsPlant_idLocationPutValidator(plant_id, location).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    plant_id <- StringGenerator
                    location <- LocationGenerator
                
            } yield (plant_id, location)
            val inputs = genInputs suchThat { case (plant_id, location) =>
                new PlantsPlant_idLocationPutValidator(plant_id, location).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "GET /api/plants/{plant_id}/waterings" should {
        def testInvalidInput(input: (String, ErrorCode, ErrorCode)) = {

            val (plant_id, limit, offset) = input

            val url = s"""/api/plants/${toPath(plant_id)}/waterings?${toQuery("limit", limit)}&${toQuery("offset", offset)}"""
            val headers = Seq()

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new PlantsPlant_idWateringsGetValidator(plant_id, limit, offset).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (String, ErrorCode, ErrorCode)) = {
            val (plant_id, limit, offset) = input
            
            val url = s"""/api/plants/${toPath(plant_id)}/waterings?${toQuery("limit", limit)}&${toQuery("offset", offset)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new PlantsPlant_idWateringsGetValidator(plant_id, limit, offset).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]

            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                        plant_id <- StringGenerator
                        limit <- ErrorCodeGenerator
                        offset <- ErrorCodeGenerator
                    
                } yield (plant_id, limit, offset)
            val inputs = genInputs suchThat { case (plant_id, limit, offset) =>
                new PlantsPlant_idWateringsGetValidator(plant_id, limit, offset).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    plant_id <- StringGenerator
                    limit <- ErrorCodeGenerator
                    offset <- ErrorCodeGenerator
                
            } yield (plant_id, limit, offset)
            val inputs = genInputs suchThat { case (plant_id, limit, offset) =>
                new PlantsPlant_idWateringsGetValidator(plant_id, limit, offset).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "PUT /api/plants/{plant_id}/waterings/{watering_id}" should {
        def testInvalidInput(input: (String, String)) = {

            val (plant_id, watering_id) = input

            val url = s"""/api/plants/${toPath(plant_id)}/waterings/${toPath(watering_id)}"""
            val headers = Seq()

            val path = route(FakeRequest(PUT, url).withHeaders(headers:_*)).get
            val errors = new PlantsPlant_idWateringsWatering_idPutValidator(plant_id, watering_id).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (String, String)) = {
            val (plant_id, watering_id) = input
            
            val url = s"""/api/plants/${toPath(plant_id)}/waterings/${toPath(watering_id)}"""
            val headers = Seq()
            val path = route(FakeRequest(PUT, url).withHeaders(headers:_*)).get
            val errors = new PlantsPlant_idWateringsWatering_idPutValidator(plant_id, watering_id).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]

            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                        plant_id <- StringGenerator
                        watering_id <- StringGenerator
                    
                } yield (plant_id, watering_id)
            val inputs = genInputs suchThat { case (plant_id, watering_id) =>
                new PlantsPlant_idWateringsWatering_idPutValidator(plant_id, watering_id).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    plant_id <- StringGenerator
                    watering_id <- StringGenerator
                
            } yield (plant_id, watering_id)
            val inputs = genInputs suchThat { case (plant_id, watering_id) =>
                new PlantsPlant_idWateringsWatering_idPutValidator(plant_id, watering_id).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "GET /api/users" should {
        def testInvalidInput(input: (ErrorCode, ErrorCode)) = {

            val (limit, offset) = input

            val url = s"""/api/users?${toQuery("limit", limit)}&${toQuery("offset", offset)}"""
            val headers = Seq()

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new UsersGetValidator(limit, offset).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (ErrorCode, ErrorCode)) = {
            val (limit, offset) = input
            
            val url = s"""/api/users?${toQuery("limit", limit)}&${toQuery("offset", offset)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new UsersGetValidator(limit, offset).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]

            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                        limit <- ErrorCodeGenerator
                        offset <- ErrorCodeGenerator
                    
                } yield (limit, offset)
            val inputs = genInputs suchThat { case (limit, offset) =>
                new UsersGetValidator(limit, offset).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    limit <- ErrorCodeGenerator
                    offset <- ErrorCodeGenerator
                
            } yield (limit, offset)
            val inputs = genInputs suchThat { case (limit, offset) =>
                new UsersGetValidator(limit, offset).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "GET /api/users/{user_id}/picture" should {
        def testInvalidInput(user_id: String) = {


            val url = s"""/api/users/${toPath(user_id)}/picture"""
            val headers = Seq()

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new UsersUser_idPictureGetValidator(user_id).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(user_id: String) = {
            
            val url = s"""/api/users/${toPath(user_id)}/picture"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new UsersUser_idPictureGetValidator(user_id).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]

            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    user_id <- StringGenerator
                } yield user_id
            val inputs = genInputs suchThat { user_id =>
                new UsersUser_idPictureGetValidator(user_id).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                user_id <- StringGenerator
            } yield user_id
            val inputs = genInputs suchThat { user_id =>
                new UsersUser_idPictureGetValidator(user_id).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "PUT /api/users/{user_id}" should {
        def testInvalidInput(input: (String, User)) = {

            val (user_id, user) = input

            val url = s"""/api/users/${toPath(user_id)}"""
            val headers = Seq()
                val parsed_user = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(user)

            val path = route(FakeRequest(PUT, url).withHeaders(headers:_*).withBody(parsed_user)).get
            val errors = new UsersUser_idPutValidator(user_id, user).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" + "and body [" + parsed_user + "]") |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (String, User)) = {
            val (user_id, user) = input
            
            val parsed_user = parserConstructor("application/json").writeValueAsString(user)
            
            val url = s"""/api/users/${toPath(user_id)}"""
            val headers = Seq()
            val path = route(FakeRequest(PUT, url).withHeaders(headers:_*).withBody(parsed_user)).get
            val errors = new UsersUser_idPutValidator(user_id, user).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]

            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" + "and body [" + parsed_user + "]") |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                        user_id <- StringGenerator
                        user <- UserGenerator
                    
                } yield (user_id, user)
            val inputs = genInputs suchThat { case (user_id, user) =>
                new UsersUser_idPutValidator(user_id, user).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    user_id <- StringGenerator
                    user <- UserGenerator
                
            } yield (user_id, user)
            val inputs = genInputs suchThat { case (user_id, user) =>
                new UsersUser_idPutValidator(user_id, user).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "DELETE /api/plants/{plant_id}/pictures/{picture_id}" should {
        def testInvalidInput(input: (String, String)) = {

            val (plant_id, picture_id) = input

            val url = s"""/api/plants/${toPath(plant_id)}/pictures/${toPath(picture_id)}"""
            val headers = Seq()

            val path = route(FakeRequest(DELETE, url).withHeaders(headers:_*)).get
            val errors = new PlantsPlant_idPicturesPicture_idDeleteValidator(plant_id, picture_id).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (String, String)) = {
            val (plant_id, picture_id) = input
            
            val url = s"""/api/plants/${toPath(plant_id)}/pictures/${toPath(picture_id)}"""
            val headers = Seq()
            val path = route(FakeRequest(DELETE, url).withHeaders(headers:_*)).get
            val errors = new PlantsPlant_idPicturesPicture_idDeleteValidator(plant_id, picture_id).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]

            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                        plant_id <- StringGenerator
                        picture_id <- StringGenerator
                    
                } yield (plant_id, picture_id)
            val inputs = genInputs suchThat { case (plant_id, picture_id) =>
                new PlantsPlant_idPicturesPicture_idDeleteValidator(plant_id, picture_id).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    plant_id <- StringGenerator
                    picture_id <- StringGenerator
                
            } yield (plant_id, picture_id)
            val inputs = genInputs suchThat { case (plant_id, picture_id) =>
                new PlantsPlant_idPicturesPicture_idDeleteValidator(plant_id, picture_id).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "PUT /api/areas/{area_id}" should {
        def testInvalidInput(area_id: String) = {


            val url = s"""/api/areas/${toPath(area_id)}"""
            val headers = Seq()

            val path = route(FakeRequest(PUT, url).withHeaders(headers:_*)).get
            val errors = new AreasArea_idPutValidator(area_id).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(area_id: String) = {
            
            val url = s"""/api/areas/${toPath(area_id)}"""
            val headers = Seq()
            val path = route(FakeRequest(PUT, url).withHeaders(headers:_*)).get
            val errors = new AreasArea_idPutValidator(area_id).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]

            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    area_id <- StringGenerator
                } yield area_id
            val inputs = genInputs suchThat { area_id =>
                new AreasArea_idPutValidator(area_id).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                area_id <- StringGenerator
            } yield area_id
            val inputs = genInputs suchThat { area_id =>
                new AreasArea_idPutValidator(area_id).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "GET /api/plants" should {
        def testInvalidInput(input: (PlantsGetLimit, PlantsGetOffset)) = {

            val (limit, offset) = input

            val url = s"""/api/plants?${toQuery("limit", limit)}&${toQuery("offset", offset)}"""
            val headers = Seq()

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new PlantsGetValidator(limit, offset).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (PlantsGetLimit, PlantsGetOffset)) = {
            val (limit, offset) = input
            
            val url = s"""/api/plants?${toQuery("limit", limit)}&${toQuery("offset", offset)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new PlantsGetValidator(limit, offset).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]

            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                        limit <- PlantsGetLimitGenerator
                        offset <- PlantsGetOffsetGenerator
                    
                } yield (limit, offset)
            val inputs = genInputs suchThat { case (limit, offset) =>
                new PlantsGetValidator(limit, offset).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    limit <- PlantsGetLimitGenerator
                    offset <- PlantsGetOffsetGenerator
                
            } yield (limit, offset)
            val inputs = genInputs suchThat { case (limit, offset) =>
                new PlantsGetValidator(limit, offset).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "PUT /api/users/{user_id}/picture" should {
        def testInvalidInput(user_id: String) = {


            val url = s"""/api/users/${toPath(user_id)}/picture"""
            val headers = Seq()

            val path = route(FakeRequest(PUT, url).withHeaders(headers:_*)).get
            val errors = new UsersUser_idPicturePutValidator(user_id).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(user_id: String) = {
            
            val url = s"""/api/users/${toPath(user_id)}/picture"""
            val headers = Seq()
            val path = route(FakeRequest(PUT, url).withHeaders(headers:_*)).get
            val errors = new UsersUser_idPicturePutValidator(user_id).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]

            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    user_id <- StringGenerator
                } yield user_id
            val inputs = genInputs suchThat { user_id =>
                new UsersUser_idPicturePutValidator(user_id).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                user_id <- StringGenerator
            } yield user_id
            val inputs = genInputs suchThat { user_id =>
                new UsersUser_idPicturePutValidator(user_id).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "DELETE /api/plants/{plant_id}/location" should {
        def testInvalidInput(plant_id: String) = {


            val url = s"""/api/plants/${toPath(plant_id)}/location"""
            val headers = Seq()

            val path = route(FakeRequest(DELETE, url).withHeaders(headers:_*)).get
            val errors = new PlantsPlant_idLocationDeleteValidator(plant_id).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(plant_id: String) = {
            
            val url = s"""/api/plants/${toPath(plant_id)}/location"""
            val headers = Seq()
            val path = route(FakeRequest(DELETE, url).withHeaders(headers:_*)).get
            val errors = new PlantsPlant_idLocationDeleteValidator(plant_id).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]

            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    plant_id <- StringGenerator
                } yield plant_id
            val inputs = genInputs suchThat { plant_id =>
                new PlantsPlant_idLocationDeleteValidator(plant_id).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                plant_id <- StringGenerator
            } yield plant_id
            val inputs = genInputs suchThat { plant_id =>
                new PlantsPlant_idLocationDeleteValidator(plant_id).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "DELETE /api/plants/{plant_id}" should {
        def testInvalidInput(plant_id: String) = {


            val url = s"""/api/plants/${toPath(plant_id)}"""
            val headers = Seq()

            val path = route(FakeRequest(DELETE, url).withHeaders(headers:_*)).get
            val errors = new PlantsPlant_idDeleteValidator(plant_id).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(plant_id: String) = {
            
            val url = s"""/api/plants/${toPath(plant_id)}"""
            val headers = Seq()
            val path = route(FakeRequest(DELETE, url).withHeaders(headers:_*)).get
            val errors = new PlantsPlant_idDeleteValidator(plant_id).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]

            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    plant_id <- StringGenerator
                } yield plant_id
            val inputs = genInputs suchThat { plant_id =>
                new PlantsPlant_idDeleteValidator(plant_id).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                plant_id <- StringGenerator
            } yield plant_id
            val inputs = genInputs suchThat { plant_id =>
                new PlantsPlant_idDeleteValidator(plant_id).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "PUT /api/plants/{plant_id}" should {
        def testInvalidInput(input: (String, Plant)) = {

            val (plant_id, plant) = input

            val url = s"""/api/plants/${toPath(plant_id)}"""
            val headers = Seq()
                val parsed_plant = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(plant)

            val path = route(FakeRequest(PUT, url).withHeaders(headers:_*).withBody(parsed_plant)).get
            val errors = new PlantsPlant_idPutValidator(plant_id, plant).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" + "and body [" + parsed_plant + "]") |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (String, Plant)) = {
            val (plant_id, plant) = input
            
            val parsed_plant = parserConstructor("application/json").writeValueAsString(plant)
            
            val url = s"""/api/plants/${toPath(plant_id)}"""
            val headers = Seq()
            val path = route(FakeRequest(PUT, url).withHeaders(headers:_*).withBody(parsed_plant)).get
            val errors = new PlantsPlant_idPutValidator(plant_id, plant).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]

            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" + "and body [" + parsed_plant + "]") |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                        plant_id <- StringGenerator
                        plant <- PlantGenerator
                    
                } yield (plant_id, plant)
            val inputs = genInputs suchThat { case (plant_id, plant) =>
                new PlantsPlant_idPutValidator(plant_id, plant).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    plant_id <- StringGenerator
                    plant <- PlantGenerator
                
            } yield (plant_id, plant)
            val inputs = genInputs suchThat { case (plant_id, plant) =>
                new PlantsPlant_idPutValidator(plant_id, plant).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "GET /api/areas" should {
        def testInvalidInput(input: (ErrorCode, ErrorCode)) = {

            val (limit, offset) = input

            val url = s"""/api/areas?${toQuery("limit", limit)}&${toQuery("offset", offset)}"""
            val headers = Seq()

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new AreasGetValidator(limit, offset).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (ErrorCode, ErrorCode)) = {
            val (limit, offset) = input
            
            val url = s"""/api/areas?${toQuery("limit", limit)}&${toQuery("offset", offset)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new AreasGetValidator(limit, offset).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]

            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                        limit <- ErrorCodeGenerator
                        offset <- ErrorCodeGenerator
                    
                } yield (limit, offset)
            val inputs = genInputs suchThat { case (limit, offset) =>
                new AreasGetValidator(limit, offset).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    limit <- ErrorCodeGenerator
                    offset <- ErrorCodeGenerator
                
            } yield (limit, offset)
            val inputs = genInputs suchThat { case (limit, offset) =>
                new AreasGetValidator(limit, offset).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "PUT /api/plants/{plant_id}/pictures/{picture_id}" should {
        def testInvalidInput(input: (String, String)) = {

            val (plant_id, picture_id) = input

            val url = s"""/api/plants/${toPath(plant_id)}/pictures/${toPath(picture_id)}"""
            val headers = Seq()

            val path = route(FakeRequest(PUT, url).withHeaders(headers:_*)).get
            val errors = new PlantsPlant_idPicturesPicture_idPutValidator(plant_id, picture_id).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (String, String)) = {
            val (plant_id, picture_id) = input
            
            val url = s"""/api/plants/${toPath(plant_id)}/pictures/${toPath(picture_id)}"""
            val headers = Seq()
            val path = route(FakeRequest(PUT, url).withHeaders(headers:_*)).get
            val errors = new PlantsPlant_idPicturesPicture_idPutValidator(plant_id, picture_id).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]

            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                        plant_id <- StringGenerator
                        picture_id <- StringGenerator
                    
                } yield (plant_id, picture_id)
            val inputs = genInputs suchThat { case (plant_id, picture_id) =>
                new PlantsPlant_idPicturesPicture_idPutValidator(plant_id, picture_id).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    plant_id <- StringGenerator
                    picture_id <- StringGenerator
                
            } yield (plant_id, picture_id)
            val inputs = genInputs suchThat { case (plant_id, picture_id) =>
                new PlantsPlant_idPicturesPicture_idPutValidator(plant_id, picture_id).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "GET /api/plants/{plant_id}/sunlight_needs" should {
        def testInvalidInput(plant_id: String) = {


            val url = s"""/api/plants/${toPath(plant_id)}/sunlight_needs"""
            val headers = Seq()

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new PlantsPlant_idSunlight_needsGetValidator(plant_id).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(plant_id: String) = {
            
            val url = s"""/api/plants/${toPath(plant_id)}/sunlight_needs"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new PlantsPlant_idSunlight_needsGetValidator(plant_id).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]

            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    plant_id <- StringGenerator
                } yield plant_id
            val inputs = genInputs suchThat { plant_id =>
                new PlantsPlant_idSunlight_needsGetValidator(plant_id).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                plant_id <- StringGenerator
            } yield plant_id
            val inputs = genInputs suchThat { plant_id =>
                new PlantsPlant_idSunlight_needsGetValidator(plant_id).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "DELETE /api/areas/{area_id}" should {
        def testInvalidInput(area_id: String) = {


            val url = s"""/api/areas/${toPath(area_id)}"""
            val headers = Seq()

            val path = route(FakeRequest(DELETE, url).withHeaders(headers:_*)).get
            val errors = new AreasArea_idDeleteValidator(area_id).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(area_id: String) = {
            
            val url = s"""/api/areas/${toPath(area_id)}"""
            val headers = Seq()
            val path = route(FakeRequest(DELETE, url).withHeaders(headers:_*)).get
            val errors = new AreasArea_idDeleteValidator(area_id).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]

            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    area_id <- StringGenerator
                } yield area_id
            val inputs = genInputs suchThat { area_id =>
                new AreasArea_idDeleteValidator(area_id).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                area_id <- StringGenerator
            } yield area_id
            val inputs = genInputs suchThat { area_id =>
                new AreasArea_idDeleteValidator(area_id).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "DELETE /api/users/{user_id}/picture" should {
        def testInvalidInput(user_id: String) = {


            val url = s"""/api/users/${toPath(user_id)}/picture"""
            val headers = Seq()

            val path = route(FakeRequest(DELETE, url).withHeaders(headers:_*)).get
            val errors = new UsersUser_idPictureDeleteValidator(user_id).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(user_id: String) = {
            
            val url = s"""/api/users/${toPath(user_id)}/picture"""
            val headers = Seq()
            val path = route(FakeRequest(DELETE, url).withHeaders(headers:_*)).get
            val errors = new UsersUser_idPictureDeleteValidator(user_id).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]

            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    user_id <- StringGenerator
                } yield user_id
            val inputs = genInputs suchThat { user_id =>
                new UsersUser_idPictureDeleteValidator(user_id).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                user_id <- StringGenerator
            } yield user_id
            val inputs = genInputs suchThat { user_id =>
                new UsersUser_idPictureDeleteValidator(user_id).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "GET /api/plants/{plant_id}/pictures/{picture_id}" should {
        def testInvalidInput(input: (String, String)) = {

            val (plant_id, picture_id) = input

            val url = s"""/api/plants/${toPath(plant_id)}/pictures/${toPath(picture_id)}"""
            val headers = Seq()

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new PlantsPlant_idPicturesPicture_idGetValidator(plant_id, picture_id).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (String, String)) = {
            val (plant_id, picture_id) = input
            
            val url = s"""/api/plants/${toPath(plant_id)}/pictures/${toPath(picture_id)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new PlantsPlant_idPicturesPicture_idGetValidator(plant_id, picture_id).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]

            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                        plant_id <- StringGenerator
                        picture_id <- StringGenerator
                    
                } yield (plant_id, picture_id)
            val inputs = genInputs suchThat { case (plant_id, picture_id) =>
                new PlantsPlant_idPicturesPicture_idGetValidator(plant_id, picture_id).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    plant_id <- StringGenerator
                    picture_id <- StringGenerator
                
            } yield (plant_id, picture_id)
            val inputs = genInputs suchThat { case (plant_id, picture_id) =>
                new PlantsPlant_idPicturesPicture_idGetValidator(plant_id, picture_id).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "GET /api/areas/{area_id}" should {
        def testInvalidInput(area_id: String) = {


            val url = s"""/api/areas/${toPath(area_id)}"""
            val headers = Seq()

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new AreasArea_idGetValidator(area_id).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(area_id: String) = {
            
            val url = s"""/api/areas/${toPath(area_id)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new AreasArea_idGetValidator(area_id).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]

            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    area_id <- StringGenerator
                } yield area_id
            val inputs = genInputs suchThat { area_id =>
                new AreasArea_idGetValidator(area_id).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                area_id <- StringGenerator
            } yield area_id
            val inputs = genInputs suchThat { area_id =>
                new AreasArea_idGetValidator(area_id).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "GET /api/plants/{plant_id}/pictures" should {
        def testInvalidInput(input: (String, ErrorCode, ErrorCode)) = {

            val (plant_id, limit, offset) = input

            val url = s"""/api/plants/${toPath(plant_id)}/pictures?${toQuery("limit", limit)}&${toQuery("offset", offset)}"""
            val headers = Seq()

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new PlantsPlant_idPicturesGetValidator(plant_id, limit, offset).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (String, ErrorCode, ErrorCode)) = {
            val (plant_id, limit, offset) = input
            
            val url = s"""/api/plants/${toPath(plant_id)}/pictures?${toQuery("limit", limit)}&${toQuery("offset", offset)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new PlantsPlant_idPicturesGetValidator(plant_id, limit, offset).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]

            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                        plant_id <- StringGenerator
                        limit <- ErrorCodeGenerator
                        offset <- ErrorCodeGenerator
                    
                } yield (plant_id, limit, offset)
            val inputs = genInputs suchThat { case (plant_id, limit, offset) =>
                new PlantsPlant_idPicturesGetValidator(plant_id, limit, offset).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    plant_id <- StringGenerator
                    limit <- ErrorCodeGenerator
                    offset <- ErrorCodeGenerator
                
            } yield (plant_id, limit, offset)
            val inputs = genInputs suchThat { case (plant_id, limit, offset) =>
                new PlantsPlant_idPicturesGetValidator(plant_id, limit, offset).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
}
