package instagram.api.yaml

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

import play.api.test.Helpers.{status => requestStatusCode_}

import Generators._

    @RunWith(classOf[JUnitRunner])
    class InstagramApiYamlSpec extends Specification {
        def toPath[T](value: T)(implicit binder: PathBindable[T]): String = Option(binder.unbind("", value)).getOrElse("")
        def toQuery[T](key: String, value: T)(implicit binder: QueryStringBindable[T]): String = Option(binder.unbind(key, value)).getOrElse("")
        def toHeader[T](value: T)(implicit binder: PathBindable[T]): String = Option(binder.unbind("", value)).getOrElse("")

      def checkResult(props: Prop) =
        Test.check(Test.Parameters.default, props).status match {
          case Failed(_, labels) => failure(labels.mkString("\\n"))
          case Proved(_) | Exhausted | Passed => success
          case PropException(_, e, labels) =>
            val error = if (labels.isEmpty) e.getLocalizedMessage() else labels.mkString("\\n")
            failure(error)
        }



    "GET /v1/locations/search" should {
        def testInvalidInput(input: (MediaId, MediaId, MediaId, LocationLatitude, MediaId, LocationLatitude)) = {


                val (foursquare_v2_id, facebook_places_id, distance, lat, foursquare_id, lng) = input
            
            val url = s"""/v1/locations/search?${toQuery("foursquare_v2_id", foursquare_v2_id)}&${toQuery("facebook_places_id", facebook_places_id)}&${toQuery("distance", distance)}&${toQuery("lat", lat)}&${toQuery("foursquare_id", foursquare_id)}&${toQuery("lng", lng)}"""
            val headers = Seq()

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new LocationsSearchGetValidator(foursquare_v2_id, facebook_places_id, distance, lat, foursquare_id, lng).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (MediaId, MediaId, MediaId, LocationLatitude, MediaId, LocationLatitude)) = {


                val (foursquare_v2_id, facebook_places_id, distance, lat, foursquare_id, lng) = input
            

            val url = s"""/v1/locations/search?${toQuery("foursquare_v2_id", foursquare_v2_id)}&${toQuery("facebook_places_id", facebook_places_id)}&${toQuery("distance", distance)}&${toQuery("lat", lat)}&${toQuery("foursquare_id", foursquare_id)}&${toQuery("lng", lng)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (requestStatusCode_(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                        foursquare_v2_id <- MediaIdGenerator
                        facebook_places_id <- MediaIdGenerator
                        distance <- MediaIdGenerator
                        lat <- LocationLatitudeGenerator
                        foursquare_id <- MediaIdGenerator
                        lng <- LocationLatitudeGenerator
                    
                } yield (foursquare_v2_id, facebook_places_id, distance, lat, foursquare_id, lng)
            val inputs = genInputs suchThat { case (foursquare_v2_id, facebook_places_id, distance, lat, foursquare_id, lng) =>
                new LocationsSearchGetValidator(foursquare_v2_id, facebook_places_id, distance, lat, foursquare_id, lng).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    foursquare_v2_id <- MediaIdGenerator
                    facebook_places_id <- MediaIdGenerator
                    distance <- MediaIdGenerator
                    lat <- LocationLatitudeGenerator
                    foursquare_id <- MediaIdGenerator
                    lng <- LocationLatitudeGenerator
                
            } yield (foursquare_v2_id, facebook_places_id, distance, lat, foursquare_id, lng)
            val inputs = genInputs suchThat { case (foursquare_v2_id, facebook_places_id, distance, lat, foursquare_id, lng) =>
                new LocationsSearchGetValidator(foursquare_v2_id, facebook_places_id, distance, lat, foursquare_id, lng).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }


    "GET /v1/users/self/media/liked" should {
        def testInvalidInput(input: (MediaId, MediaId)) = {


                val (count, max_like_id) = input
            
            val url = s"""/v1/users/self/media/liked?${toQuery("count", count)}&${toQuery("max_like_id", max_like_id)}"""
            val headers = Seq()

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new UsersSelfMediaLikedGetValidator(count, max_like_id).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (MediaId, MediaId)) = {


                val (count, max_like_id) = input
            

            val url = s"""/v1/users/self/media/liked?${toQuery("count", count)}&${toQuery("max_like_id", max_like_id)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (requestStatusCode_(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                        count <- MediaIdGenerator
                        max_like_id <- MediaIdGenerator
                    
                } yield (count, max_like_id)
            val inputs = genInputs suchThat { case (count, max_like_id) =>
                new UsersSelfMediaLikedGetValidator(count, max_like_id).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    count <- MediaIdGenerator
                    max_like_id <- MediaIdGenerator
                
            } yield (count, max_like_id)
            val inputs = genInputs suchThat { case (count, max_like_id) =>
                new UsersSelfMediaLikedGetValidator(count, max_like_id).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }


    "GET /v1/tags/search" should {
        def testInvalidInput(q: MediaFilter) = {


            val url = s"""/v1/tags/search?${toQuery("q", q)}"""
            val headers = Seq()

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new TagsSearchGetValidator(q).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(q: MediaFilter) = {



            val url = s"""/v1/tags/search?${toQuery("q", q)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (requestStatusCode_(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    q <- MediaFilterGenerator
                } yield q
            val inputs = genInputs suchThat { q =>
                new TagsSearchGetValidator(q).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                q <- MediaFilterGenerator
            } yield q
            val inputs = genInputs suchThat { q =>
                new TagsSearchGetValidator(q).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }


    "GET /v1/media/{shortcode}" should {
        def testInvalidInput(shortcode: String) = {


            val url = s"""/v1/media/${toPath(shortcode)}"""
            val headers = Seq()

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new MediaShortcodeGetValidator(shortcode).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(shortcode: String) = {



            val url = s"""/v1/media/${toPath(shortcode)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (requestStatusCode_(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    shortcode <- StringGenerator
                } yield shortcode
            val inputs = genInputs suchThat { shortcode =>
                new MediaShortcodeGetValidator(shortcode).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                shortcode <- StringGenerator
            } yield shortcode
            val inputs = genInputs suchThat { shortcode =>
                new MediaShortcodeGetValidator(shortcode).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }


    "GET /v1/users/search" should {
        def testInvalidInput(input: (String, MediaFilter)) = {


                val (q, count) = input
            
            val url = s"""/v1/users/search?${toQuery("q", q)}&${toQuery("count", count)}"""
            val headers = Seq()

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new UsersSearchGetValidator(q, count).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (String, MediaFilter)) = {


                val (q, count) = input
            

            val url = s"""/v1/users/search?${toQuery("q", q)}&${toQuery("count", count)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (requestStatusCode_(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                        q <- StringGenerator
                        count <- MediaFilterGenerator
                    
                } yield (q, count)
            val inputs = genInputs suchThat { case (q, count) =>
                new UsersSearchGetValidator(q, count).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    q <- StringGenerator
                    count <- MediaFilterGenerator
                
            } yield (q, count)
            val inputs = genInputs suchThat { case (q, count) =>
                new UsersSearchGetValidator(q, count).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }


    "GET /v1/media/search" should {
        def testInvalidInput(input: (MediaId, MediaId, LocationLatitude, MediaId, LocationLatitude)) = {


                val (mAX_TIMESTAMP, dISTANCE, lNG, mIN_TIMESTAMP, lAT) = input
            
            val url = s"""/v1/media/search?${toQuery("MAX_TIMESTAMP", MAX_TIMESTAMP)}&${toQuery("DISTANCE", DISTANCE)}&${toQuery("LNG", LNG)}&${toQuery("MIN_TIMESTAMP", MIN_TIMESTAMP)}&${toQuery("LAT", LAT)}"""
            val headers = Seq()

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new MediaSearchGetValidator(mAX_TIMESTAMP, dISTANCE, lNG, mIN_TIMESTAMP, lAT).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (MediaId, MediaId, LocationLatitude, MediaId, LocationLatitude)) = {


                val (mAX_TIMESTAMP, dISTANCE, lNG, mIN_TIMESTAMP, lAT) = input
            

            val url = s"""/v1/media/search?${toQuery("MAX_TIMESTAMP", MAX_TIMESTAMP)}&${toQuery("DISTANCE", DISTANCE)}&${toQuery("LNG", LNG)}&${toQuery("MIN_TIMESTAMP", MIN_TIMESTAMP)}&${toQuery("LAT", LAT)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (requestStatusCode_(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                        mAX_TIMESTAMP <- MediaIdGenerator
                        dISTANCE <- MediaIdGenerator
                        lNG <- LocationLatitudeGenerator
                        mIN_TIMESTAMP <- MediaIdGenerator
                        lAT <- LocationLatitudeGenerator
                    
                } yield (mAX_TIMESTAMP, dISTANCE, lNG, mIN_TIMESTAMP, lAT)
            val inputs = genInputs suchThat { case (mAX_TIMESTAMP, dISTANCE, lNG, mIN_TIMESTAMP, lAT) =>
                new MediaSearchGetValidator(mAX_TIMESTAMP, dISTANCE, lNG, mIN_TIMESTAMP, lAT).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    mAX_TIMESTAMP <- MediaIdGenerator
                    dISTANCE <- MediaIdGenerator
                    lNG <- LocationLatitudeGenerator
                    mIN_TIMESTAMP <- MediaIdGenerator
                    lAT <- LocationLatitudeGenerator
                
            } yield (mAX_TIMESTAMP, dISTANCE, lNG, mIN_TIMESTAMP, lAT)
            val inputs = genInputs suchThat { case (mAX_TIMESTAMP, dISTANCE, lNG, mIN_TIMESTAMP, lAT) =>
                new MediaSearchGetValidator(mAX_TIMESTAMP, dISTANCE, lNG, mIN_TIMESTAMP, lAT).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }


    "GET /v1/users/self/feed" should {
        def testInvalidInput(input: (MediaId, MediaId, MediaId)) = {


                val (count, max_id, min_id) = input
            
            val url = s"""/v1/users/self/feed?${toQuery("count", count)}&${toQuery("max_id", max_id)}&${toQuery("min_id", min_id)}"""
            val headers = Seq()

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new UsersSelfFeedGetValidator(count, max_id, min_id).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (MediaId, MediaId, MediaId)) = {


                val (count, max_id, min_id) = input
            

            val url = s"""/v1/users/self/feed?${toQuery("count", count)}&${toQuery("max_id", max_id)}&${toQuery("min_id", min_id)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (requestStatusCode_(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                        count <- MediaIdGenerator
                        max_id <- MediaIdGenerator
                        min_id <- MediaIdGenerator
                    
                } yield (count, max_id, min_id)
            val inputs = genInputs suchThat { case (count, max_id, min_id) =>
                new UsersSelfFeedGetValidator(count, max_id, min_id).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    count <- MediaIdGenerator
                    max_id <- MediaIdGenerator
                    min_id <- MediaIdGenerator
                
            } yield (count, max_id, min_id)
            val inputs = genInputs suchThat { case (count, max_id, min_id) =>
                new UsersSelfFeedGetValidator(count, max_id, min_id).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

}
