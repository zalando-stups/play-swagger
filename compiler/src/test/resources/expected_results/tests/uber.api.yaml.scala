package uber.api.yaml

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
    class UberApiYamlSpec extends Specification {
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

"GET /v1/history" should {
        def testInvalidInput(input: (ErrorCode, ErrorCode)) = {

            val (offset, limit) = input
            val url = s"""/v1/history?${toQuery("offset", offset)}&${toQuery("limit", limit)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new HistoryGetValidator(offset, limit).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (ErrorCode, ErrorCode)) = {

            val (offset, limit) = input
            val url = s"""/v1/history?${toQuery("offset", offset)}&${toQuery("limit", limit)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    offset <- ErrorCodeGenerator
                    limit <- ErrorCodeGenerator
                    

                } yield (offset, limit)

            val inputs = genInputs suchThat { case (offset, limit)=>
                new HistoryGetValidator(offset, limit).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                offset <- ErrorCodeGenerator
                limit <- ErrorCodeGenerator
                

            } yield (offset, limit)

            val inputs = genInputs suchThat { case (offset, limit)=>
                new HistoryGetValidator(offset, limit).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
"GET /v1/estimates/time" should {
        def testInvalidInput(input: (Double, Double, ProfilePicture, ProfilePicture)) = {

            val (start_latitude, start_longitude, customer_uuid, product_id) = input
            val url = s"""/v1/estimates/time?${toQuery("start_latitude", start_latitude)}&${toQuery("start_longitude", start_longitude)}&${toQuery("customer_uuid", customer_uuid)}&${toQuery("product_id", product_id)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new EstimatesTimeGetValidator(start_latitude, start_longitude, customer_uuid, product_id).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (Double, Double, ProfilePicture, ProfilePicture)) = {

            val (start_latitude, start_longitude, customer_uuid, product_id) = input
            val url = s"""/v1/estimates/time?${toQuery("start_latitude", start_latitude)}&${toQuery("start_longitude", start_longitude)}&${toQuery("customer_uuid", customer_uuid)}&${toQuery("product_id", product_id)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    start_latitude <- DoubleGenerator
                    start_longitude <- DoubleGenerator
                    customer_uuid <- ProfilePictureGenerator
                    product_id <- ProfilePictureGenerator
                    

                } yield (start_latitude, start_longitude, customer_uuid, product_id)

            val inputs = genInputs suchThat { case (start_latitude, start_longitude, customer_uuid, product_id)=>
                new EstimatesTimeGetValidator(start_latitude, start_longitude, customer_uuid, product_id).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                start_latitude <- DoubleGenerator
                start_longitude <- DoubleGenerator
                customer_uuid <- ProfilePictureGenerator
                product_id <- ProfilePictureGenerator
                

            } yield (start_latitude, start_longitude, customer_uuid, product_id)

            val inputs = genInputs suchThat { case (start_latitude, start_longitude, customer_uuid, product_id)=>
                new EstimatesTimeGetValidator(start_latitude, start_longitude, customer_uuid, product_id).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
"GET /v1/products" should {
        def testInvalidInput(input: (Double, Double)) = {

            val (latitude, longitude) = input
            val url = s"""/v1/products?${toQuery("latitude", latitude)}&${toQuery("longitude", longitude)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new ProductsGetValidator(latitude, longitude).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (Double, Double)) = {

            val (latitude, longitude) = input
            val url = s"""/v1/products?${toQuery("latitude", latitude)}&${toQuery("longitude", longitude)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    latitude <- DoubleGenerator
                    longitude <- DoubleGenerator
                    

                } yield (latitude, longitude)

            val inputs = genInputs suchThat { case (latitude, longitude)=>
                new ProductsGetValidator(latitude, longitude).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                latitude <- DoubleGenerator
                longitude <- DoubleGenerator
                

            } yield (latitude, longitude)

            val inputs = genInputs suchThat { case (latitude, longitude)=>
                new ProductsGetValidator(latitude, longitude).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
"GET /v1/estimates/price" should {
        def testInvalidInput(input: (Double, Double, Double, Double)) = {

            val (start_latitude, start_longitude, end_latitude, end_longitude) = input
            val url = s"""/v1/estimates/price?${toQuery("start_latitude", start_latitude)}&${toQuery("start_longitude", start_longitude)}&${toQuery("end_latitude", end_latitude)}&${toQuery("end_longitude", end_longitude)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new EstimatesPriceGetValidator(start_latitude, start_longitude, end_latitude, end_longitude).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (Double, Double, Double, Double)) = {

            val (start_latitude, start_longitude, end_latitude, end_longitude) = input
            val url = s"""/v1/estimates/price?${toQuery("start_latitude", start_latitude)}&${toQuery("start_longitude", start_longitude)}&${toQuery("end_latitude", end_latitude)}&${toQuery("end_longitude", end_longitude)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    start_latitude <- DoubleGenerator
                    start_longitude <- DoubleGenerator
                    end_latitude <- DoubleGenerator
                    end_longitude <- DoubleGenerator
                    

                } yield (start_latitude, start_longitude, end_latitude, end_longitude)

            val inputs = genInputs suchThat { case (start_latitude, start_longitude, end_latitude, end_longitude)=>
                new EstimatesPriceGetValidator(start_latitude, start_longitude, end_latitude, end_longitude).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                start_latitude <- DoubleGenerator
                start_longitude <- DoubleGenerator
                end_latitude <- DoubleGenerator
                end_longitude <- DoubleGenerator
                

            } yield (start_latitude, start_longitude, end_latitude, end_longitude)

            val inputs = genInputs suchThat { case (start_latitude, start_longitude, end_latitude, end_longitude)=>
                new EstimatesPriceGetValidator(start_latitude, start_longitude, end_latitude, end_longitude).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
}
