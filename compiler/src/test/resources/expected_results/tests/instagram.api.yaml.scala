package instagram.api.yaml

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
    class InstagramApiYamlSpec extends Specification {
      
      def checkResult(props: Prop) =
        Test.check(Test.Parameters.default, props).status match {
          case Failed(_, labels) => failure(labels.mkString("\\n"))
          case Proved(_) | Exhausted | Passed => success
          case PropException(_, e, labels) =>
            val error = if (labels.isEmpty) e.getLocalizedMessage() else labels.mkString("\\n")
            failure(error)
        }

"GET /v1/users/{user-id}" should {
        def testInvalidInput(`user-id`: Double) = {

            val url = s"""/v1/users/${user-id}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new `UsersUser-idGetValidator`(`user-id`).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(`user-id`: Double) = {

            val url = s"""/v1/users/${user-id}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    `user-id` <- DoubleGenerator

                } yield `user-id`

            val inputs = genInputs suchThat { `user-id`=>
                new `UsersUser-idGetValidator`(`user-id`).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                `user-id` <- DoubleGenerator

            } yield `user-id`

            val inputs = genInputs suchThat { `user-id`=>
                new `UsersUser-idGetValidator`(`user-id`).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
"GET /v1/users/{user-id}/followed-by" should {
        def testInvalidInput(`user-id`: Double) = {

            val url = s"""/v1/users/${user-id}/followed-by"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new `UsersUser-idFollowed-byGetValidator`(`user-id`).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(`user-id`: Double) = {

            val url = s"""/v1/users/${user-id}/followed-by"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    `user-id` <- DoubleGenerator

                } yield `user-id`

            val inputs = genInputs suchThat { `user-id`=>
                new `UsersUser-idFollowed-byGetValidator`(`user-id`).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                `user-id` <- DoubleGenerator

            } yield `user-id`

            val inputs = genInputs suchThat { `user-id`=>
                new `UsersUser-idFollowed-byGetValidator`(`user-id`).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
"GET /v1/media/{media-id}/likes" should {
        def testInvalidInput(`media-id`: Int) = {

            val url = s"""/v1/media/${media-id}/likes"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new `MediaMedia-idLikesGetValidator`(`media-id`).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(`media-id`: Int) = {

            val url = s"""/v1/media/${media-id}/likes"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    `media-id` <- IntGenerator

                } yield `media-id`

            val inputs = genInputs suchThat { `media-id`=>
                new `MediaMedia-idLikesGetValidator`(`media-id`).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                `media-id` <- IntGenerator

            } yield `media-id`

            val inputs = genInputs suchThat { `media-id`=>
                new `MediaMedia-idLikesGetValidator`(`media-id`).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
"GET /v1/locations/search" should {
        def testInvalidInput(input: (MediaCreated_time, MediaCreated_time, MediaCreated_time, LocationLongitude, MediaCreated_time, LocationLongitude)) = {

            val (foursquare_v2_id, facebook_places_id, distance, lat, foursquare_id, lng) = input
            val url = s"""/v1/locations/search?${foursquare_v2_id.map { i => "foursquare_v2_id=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${facebook_places_id.map { i => "facebook_places_id=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${distance.map { i => "distance=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${lat.map { i => "lat=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${foursquare_id.map { i => "foursquare_id=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${lng.map { i => "lng=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new LocationsSearchGetValidator(foursquare_v2_id, facebook_places_id, distance, lat, foursquare_id, lng).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (MediaCreated_time, MediaCreated_time, MediaCreated_time, LocationLongitude, MediaCreated_time, LocationLongitude)) = {

            val (foursquare_v2_id, facebook_places_id, distance, lat, foursquare_id, lng) = input
            val url = s"""/v1/locations/search?${foursquare_v2_id.map { i => "foursquare_v2_id=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${facebook_places_id.map { i => "facebook_places_id=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${distance.map { i => "distance=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${lat.map { i => "lat=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${foursquare_id.map { i => "foursquare_id=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${lng.map { i => "lng=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    foursquare_v2_id <- MediaCreated_timeGenerator
                    facebook_places_id <- MediaCreated_timeGenerator
                    distance <- MediaCreated_timeGenerator
                    lat <- LocationLongitudeGenerator
                    foursquare_id <- MediaCreated_timeGenerator
                    lng <- LocationLongitudeGenerator
                    

                } yield (foursquare_v2_id, facebook_places_id, distance, lat, foursquare_id, lng)

            val inputs = genInputs suchThat { case (foursquare_v2_id, facebook_places_id, distance, lat, foursquare_id, lng)=>
                new LocationsSearchGetValidator(foursquare_v2_id, facebook_places_id, distance, lat, foursquare_id, lng).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                foursquare_v2_id <- MediaCreated_timeGenerator
                facebook_places_id <- MediaCreated_timeGenerator
                distance <- MediaCreated_timeGenerator
                lat <- LocationLongitudeGenerator
                foursquare_id <- MediaCreated_timeGenerator
                lng <- LocationLongitudeGenerator
                

            } yield (foursquare_v2_id, facebook_places_id, distance, lat, foursquare_id, lng)

            val inputs = genInputs suchThat { case (foursquare_v2_id, facebook_places_id, distance, lat, foursquare_id, lng)=>
                new LocationsSearchGetValidator(foursquare_v2_id, facebook_places_id, distance, lat, foursquare_id, lng).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
"DELETE /v1/media/{media-id}/comments" should {
        def testInvalidInput(`media-id`: Int) = {

            val url = s"""/v1/media/${media-id}/comments"""
            val headers = Seq()
            val path = route(FakeRequest(DELETE, url).withHeaders(headers:_*)).get
            val errors = new `MediaMedia-idCommentsDeleteValidator`(`media-id`).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(`media-id`: Int) = {

            val url = s"""/v1/media/${media-id}/comments"""
            val headers = Seq()
            val path = route(FakeRequest(DELETE, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    `media-id` <- IntGenerator

                } yield `media-id`

            val inputs = genInputs suchThat { `media-id`=>
                new `MediaMedia-idCommentsDeleteValidator`(`media-id`).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                `media-id` <- IntGenerator

            } yield `media-id`

            val inputs = genInputs suchThat { `media-id`=>
                new `MediaMedia-idCommentsDeleteValidator`(`media-id`).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
"GET /v1/users/self/media/liked" should {
        def testInvalidInput(input: (MediaCreated_time, MediaCreated_time)) = {

            val (count, max_like_id) = input
            val url = s"""/v1/users/self/media/liked?${count.map { i => "count=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${max_like_id.map { i => "max_like_id=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new UsersSelfMediaLikedGetValidator(count, max_like_id).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (MediaCreated_time, MediaCreated_time)) = {

            val (count, max_like_id) = input
            val url = s"""/v1/users/self/media/liked?${count.map { i => "count=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${max_like_id.map { i => "max_like_id=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    count <- MediaCreated_timeGenerator
                    max_like_id <- MediaCreated_timeGenerator
                    

                } yield (count, max_like_id)

            val inputs = genInputs suchThat { case (count, max_like_id)=>
                new UsersSelfMediaLikedGetValidator(count, max_like_id).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                count <- MediaCreated_timeGenerator
                max_like_id <- MediaCreated_timeGenerator
                

            } yield (count, max_like_id)

            val inputs = genInputs suchThat { case (count, max_like_id)=>
                new UsersSelfMediaLikedGetValidator(count, max_like_id).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
"GET /v1/tags/search" should {
        def testInvalidInput(q: LikeUser_name) = {

            val url = s"""/v1/tags/search?${q.map { i => "q=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new TagsSearchGetValidator(q).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(q: LikeUser_name) = {

            val url = s"""/v1/tags/search?${q.map { i => "q=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    q <- LikeUser_nameGenerator

                } yield q

            val inputs = genInputs suchThat { q=>
                new TagsSearchGetValidator(q).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                q <- LikeUser_nameGenerator

            } yield q

            val inputs = genInputs suchThat { q=>
                new TagsSearchGetValidator(q).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
"GET /v1/media/{media-id}/comments" should {
        def testInvalidInput(`media-id`: Int) = {

            val url = s"""/v1/media/${media-id}/comments"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new `MediaMedia-idCommentsGetValidator`(`media-id`).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(`media-id`: Int) = {

            val url = s"""/v1/media/${media-id}/comments"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    `media-id` <- IntGenerator

                } yield `media-id`

            val inputs = genInputs suchThat { `media-id`=>
                new `MediaMedia-idCommentsGetValidator`(`media-id`).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                `media-id` <- IntGenerator

            } yield `media-id`

            val inputs = genInputs suchThat { `media-id`=>
                new `MediaMedia-idCommentsGetValidator`(`media-id`).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
"DELETE /v1/media/{media-id}/likes" should {
        def testInvalidInput(`media-id`: Int) = {

            val url = s"""/v1/media/${media-id}/likes"""
            val headers = Seq()
            val path = route(FakeRequest(DELETE, url).withHeaders(headers:_*)).get
            val errors = new `MediaMedia-idLikesDeleteValidator`(`media-id`).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(`media-id`: Int) = {

            val url = s"""/v1/media/${media-id}/likes"""
            val headers = Seq()
            val path = route(FakeRequest(DELETE, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    `media-id` <- IntGenerator

                } yield `media-id`

            val inputs = genInputs suchThat { `media-id`=>
                new `MediaMedia-idLikesDeleteValidator`(`media-id`).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                `media-id` <- IntGenerator

            } yield `media-id`

            val inputs = genInputs suchThat { `media-id`=>
                new `MediaMedia-idLikesDeleteValidator`(`media-id`).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
"GET /v1/media/{media-id}" should {
        def testInvalidInput(`media-id`: Int) = {

            val url = s"""/v1/media/${media-id}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new `MediaMedia-idGetValidator`(`media-id`).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(`media-id`: Int) = {

            val url = s"""/v1/media/${media-id}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    `media-id` <- IntGenerator

                } yield `media-id`

            val inputs = genInputs suchThat { `media-id`=>
                new `MediaMedia-idGetValidator`(`media-id`).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                `media-id` <- IntGenerator

            } yield `media-id`

            val inputs = genInputs suchThat { `media-id`=>
                new `MediaMedia-idGetValidator`(`media-id`).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
"GET /v1/media/{shortcode}" should {
        def testInvalidInput(shortcode: String) = {

            val url = s"""/v1/media/${shortcode}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new MediaShortcodeGetValidator(shortcode).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(shortcode: String) = {

            val url = s"""/v1/media/${shortcode}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    shortcode <- StringGenerator

                } yield shortcode

            val inputs = genInputs suchThat { shortcode=>
                new MediaShortcodeGetValidator(shortcode).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                shortcode <- StringGenerator

            } yield shortcode

            val inputs = genInputs suchThat { shortcode=>
                new MediaShortcodeGetValidator(shortcode).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
"GET /v1/users/search" should {
        def testInvalidInput(input: (String, LikeUser_name)) = {

            val (q, count) = input
            val url = s"""/v1/users/search?q=${URLEncoder.encode(q.toString, "UTF-8")}&${count.map { i => "count=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new UsersSearchGetValidator(q, count).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (String, LikeUser_name)) = {

            val (q, count) = input
            val url = s"""/v1/users/search?q=${URLEncoder.encode(q.toString, "UTF-8")}&${count.map { i => "count=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    q <- StringGenerator
                    count <- LikeUser_nameGenerator
                    

                } yield (q, count)

            val inputs = genInputs suchThat { case (q, count)=>
                new UsersSearchGetValidator(q, count).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                q <- StringGenerator
                count <- LikeUser_nameGenerator
                

            } yield (q, count)

            val inputs = genInputs suchThat { case (q, count)=>
                new UsersSearchGetValidator(q, count).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
"POST /v1/media/{media-id}/comments" should {
        def testInvalidInput(input: (Int, LocationLongitude)) = {

            val (`media-id`, tEXT) = input
            val url = s"""/v1/media/${media-id}/comments"""
            val headers = Seq()
            val parsed_TEXT = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(TEXT)
            val path = route(FakeRequest(POST, url).withHeaders(headers:_*).withBody(parsed_TEXT)).get
            val errors = new `MediaMedia-idCommentsPostValidator`(`media-id`, tEXT).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" + "and body [" + parsed_TEXT + "]") |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (Int, LocationLongitude)) = {

            val (`media-id`, tEXT) = input
            val parsed_TEXT = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(TEXT)
            val url = s"""/v1/media/${media-id}/comments"""
            val headers = Seq()
            val path = route(FakeRequest(POST, url).withHeaders(headers:_*).withBody(parsed_TEXT)).get
            ("given an URL: [" + url + "]"+ " and body [" + parsed_TEXT + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    `media-id` <- IntGenerator
                    tEXT <- LocationLongitudeGenerator
                    

                } yield (`media-id`, tEXT)

            val inputs = genInputs suchThat { case (`media-id`, tEXT)=>
                new `MediaMedia-idCommentsPostValidator`(`media-id`, tEXT).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                `media-id` <- IntGenerator
                tEXT <- LocationLongitudeGenerator
                

            } yield (`media-id`, tEXT)

            val inputs = genInputs suchThat { case (`media-id`, tEXT)=>
                new `MediaMedia-idCommentsPostValidator`(`media-id`, tEXT).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
"POST /v1/media/{media-id}/likes" should {
        def testInvalidInput(`media-id`: Int) = {

            val url = s"""/v1/media/${media-id}/likes"""
            val headers = Seq()
            val path = route(FakeRequest(POST, url).withHeaders(headers:_*)).get
            val errors = new `MediaMedia-idLikesPostValidator`(`media-id`).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(`media-id`: Int) = {

            val url = s"""/v1/media/${media-id}/likes"""
            val headers = Seq()
            val path = route(FakeRequest(POST, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    `media-id` <- IntGenerator

                } yield `media-id`

            val inputs = genInputs suchThat { `media-id`=>
                new `MediaMedia-idLikesPostValidator`(`media-id`).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                `media-id` <- IntGenerator

            } yield `media-id`

            val inputs = genInputs suchThat { `media-id`=>
                new `MediaMedia-idLikesPostValidator`(`media-id`).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
"POST /v1/users/{user-id}/relationship" should {
        def testInvalidInput(input: (Double, LikeUser_name)) = {

            val (`user-id`, action) = input
            val url = s"""/v1/users/${user-id}/relationship"""
            val headers = Seq()
            val parsed_action = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(action)
            val path = route(FakeRequest(POST, url).withHeaders(headers:_*).withBody(parsed_action)).get
            val errors = new `UsersUser-idRelationshipPostValidator`(`user-id`, action).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" + "and body [" + parsed_action + "]") |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (Double, LikeUser_name)) = {

            val (`user-id`, action) = input
            val parsed_action = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(action)
            val url = s"""/v1/users/${user-id}/relationship"""
            val headers = Seq()
            val path = route(FakeRequest(POST, url).withHeaders(headers:_*).withBody(parsed_action)).get
            ("given an URL: [" + url + "]"+ " and body [" + parsed_action + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    `user-id` <- DoubleGenerator
                    action <- LikeUser_nameGenerator
                    

                } yield (`user-id`, action)

            val inputs = genInputs suchThat { case (`user-id`, action)=>
                new `UsersUser-idRelationshipPostValidator`(`user-id`, action).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                `user-id` <- DoubleGenerator
                action <- LikeUser_nameGenerator
                

            } yield (`user-id`, action)

            val inputs = genInputs suchThat { case (`user-id`, action)=>
                new `UsersUser-idRelationshipPostValidator`(`user-id`, action).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
"GET /v1/tags/{tag-name}" should {
        def testInvalidInput(`tag-name`: String) = {

            val url = s"""/v1/tags/${tag-name}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new `TagsTag-nameGetValidator`(`tag-name`).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(`tag-name`: String) = {

            val url = s"""/v1/tags/${tag-name}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    `tag-name` <- StringGenerator

                } yield `tag-name`

            val inputs = genInputs suchThat { `tag-name`=>
                new `TagsTag-nameGetValidator`(`tag-name`).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                `tag-name` <- StringGenerator

            } yield `tag-name`

            val inputs = genInputs suchThat { `tag-name`=>
                new `TagsTag-nameGetValidator`(`tag-name`).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
"GET /v1/locations/{location-id}" should {
        def testInvalidInput(`location-id`: Int) = {

            val url = s"""/v1/locations/${location-id}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new `LocationsLocation-idGetValidator`(`location-id`).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(`location-id`: Int) = {

            val url = s"""/v1/locations/${location-id}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    `location-id` <- IntGenerator

                } yield `location-id`

            val inputs = genInputs suchThat { `location-id`=>
                new `LocationsLocation-idGetValidator`(`location-id`).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                `location-id` <- IntGenerator

            } yield `location-id`

            val inputs = genInputs suchThat { `location-id`=>
                new `LocationsLocation-idGetValidator`(`location-id`).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
"GET /v1/locations/{location-id}/media/recent" should {
        def testInvalidInput(input: (Int, MediaCreated_time, MediaCreated_time, LikeUser_name, LikeUser_name)) = {

            val (`location-id`, max_timestamp, min_timestamp, min_id, max_id) = input
            val url = s"""/v1/locations/${location-id}/media/recent?${max_timestamp.map { i => "max_timestamp=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${min_timestamp.map { i => "min_timestamp=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${min_id.map { i => "min_id=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${max_id.map { i => "max_id=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new `LocationsLocation-idMediaRecentGetValidator`(`location-id`, max_timestamp, min_timestamp, min_id, max_id).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (Int, MediaCreated_time, MediaCreated_time, LikeUser_name, LikeUser_name)) = {

            val (`location-id`, max_timestamp, min_timestamp, min_id, max_id) = input
            val url = s"""/v1/locations/${location-id}/media/recent?${max_timestamp.map { i => "max_timestamp=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${min_timestamp.map { i => "min_timestamp=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${min_id.map { i => "min_id=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${max_id.map { i => "max_id=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    `location-id` <- IntGenerator
                    max_timestamp <- MediaCreated_timeGenerator
                    min_timestamp <- MediaCreated_timeGenerator
                    min_id <- LikeUser_nameGenerator
                    max_id <- LikeUser_nameGenerator
                    

                } yield (`location-id`, max_timestamp, min_timestamp, min_id, max_id)

            val inputs = genInputs suchThat { case (`location-id`, max_timestamp, min_timestamp, min_id, max_id)=>
                new `LocationsLocation-idMediaRecentGetValidator`(`location-id`, max_timestamp, min_timestamp, min_id, max_id).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                `location-id` <- IntGenerator
                max_timestamp <- MediaCreated_timeGenerator
                min_timestamp <- MediaCreated_timeGenerator
                min_id <- LikeUser_nameGenerator
                max_id <- LikeUser_nameGenerator
                

            } yield (`location-id`, max_timestamp, min_timestamp, min_id, max_id)

            val inputs = genInputs suchThat { case (`location-id`, max_timestamp, min_timestamp, min_id, max_id)=>
                new `LocationsLocation-idMediaRecentGetValidator`(`location-id`, max_timestamp, min_timestamp, min_id, max_id).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
"GET /v1/media/search" should {
        def testInvalidInput(input: (MediaCreated_time, MediaCreated_time, LocationLongitude, MediaCreated_time, LocationLongitude)) = {

            val (mAX_TIMESTAMP, dISTANCE, lNG, mIN_TIMESTAMP, lAT) = input
            val url = s"""/v1/media/search?${MAX_TIMESTAMP.map { i => "MAX_TIMESTAMP=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${DISTANCE.map { i => "DISTANCE=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${LNG.map { i => "LNG=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${MIN_TIMESTAMP.map { i => "MIN_TIMESTAMP=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${LAT.map { i => "LAT=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new MediaSearchGetValidator(mAX_TIMESTAMP, dISTANCE, lNG, mIN_TIMESTAMP, lAT).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (MediaCreated_time, MediaCreated_time, LocationLongitude, MediaCreated_time, LocationLongitude)) = {

            val (mAX_TIMESTAMP, dISTANCE, lNG, mIN_TIMESTAMP, lAT) = input
            val url = s"""/v1/media/search?${MAX_TIMESTAMP.map { i => "MAX_TIMESTAMP=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${DISTANCE.map { i => "DISTANCE=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${LNG.map { i => "LNG=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${MIN_TIMESTAMP.map { i => "MIN_TIMESTAMP=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${LAT.map { i => "LAT=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    mAX_TIMESTAMP <- MediaCreated_timeGenerator
                    dISTANCE <- MediaCreated_timeGenerator
                    lNG <- LocationLongitudeGenerator
                    mIN_TIMESTAMP <- MediaCreated_timeGenerator
                    lAT <- LocationLongitudeGenerator
                    

                } yield (mAX_TIMESTAMP, dISTANCE, lNG, mIN_TIMESTAMP, lAT)

            val inputs = genInputs suchThat { case (mAX_TIMESTAMP, dISTANCE, lNG, mIN_TIMESTAMP, lAT)=>
                new MediaSearchGetValidator(mAX_TIMESTAMP, dISTANCE, lNG, mIN_TIMESTAMP, lAT).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                mAX_TIMESTAMP <- MediaCreated_timeGenerator
                dISTANCE <- MediaCreated_timeGenerator
                lNG <- LocationLongitudeGenerator
                mIN_TIMESTAMP <- MediaCreated_timeGenerator
                lAT <- LocationLongitudeGenerator
                

            } yield (mAX_TIMESTAMP, dISTANCE, lNG, mIN_TIMESTAMP, lAT)

            val inputs = genInputs suchThat { case (mAX_TIMESTAMP, dISTANCE, lNG, mIN_TIMESTAMP, lAT)=>
                new MediaSearchGetValidator(mAX_TIMESTAMP, dISTANCE, lNG, mIN_TIMESTAMP, lAT).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
"GET /v1/tags/{tag-name}/media/recent" should {
        def testInvalidInput(`tag-name`: String) = {

            val url = s"""/v1/tags/${tag-name}/media/recent"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new `TagsTag-nameMediaRecentGetValidator`(`tag-name`).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(`tag-name`: String) = {

            val url = s"""/v1/tags/${tag-name}/media/recent"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    `tag-name` <- StringGenerator

                } yield `tag-name`

            val inputs = genInputs suchThat { `tag-name`=>
                new `TagsTag-nameMediaRecentGetValidator`(`tag-name`).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                `tag-name` <- StringGenerator

            } yield `tag-name`

            val inputs = genInputs suchThat { `tag-name`=>
                new `TagsTag-nameMediaRecentGetValidator`(`tag-name`).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
"GET /v1/users/{user-id}/follows" should {
        def testInvalidInput(`user-id`: Double) = {

            val url = s"""/v1/users/${user-id}/follows"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new `UsersUser-idFollowsGetValidator`(`user-id`).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(`user-id`: Double) = {

            val url = s"""/v1/users/${user-id}/follows"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    `user-id` <- DoubleGenerator

                } yield `user-id`

            val inputs = genInputs suchThat { `user-id`=>
                new `UsersUser-idFollowsGetValidator`(`user-id`).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                `user-id` <- DoubleGenerator

            } yield `user-id`

            val inputs = genInputs suchThat { `user-id`=>
                new `UsersUser-idFollowsGetValidator`(`user-id`).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
"GET /v1/users/{user-id}/media/recent" should {
        def testInvalidInput(input: (Double, MediaCreated_time, LikeUser_name, MediaCreated_time, LikeUser_name, MediaCreated_time)) = {

            val (`user-id`, max_timestamp, min_id, min_timestamp, max_id, count) = input
            val url = s"""/v1/users/${user-id}/media/recent?${max_timestamp.map { i => "max_timestamp=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${min_id.map { i => "min_id=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${min_timestamp.map { i => "min_timestamp=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${max_id.map { i => "max_id=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${count.map { i => "count=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new `UsersUser-idMediaRecentGetValidator`(`user-id`, max_timestamp, min_id, min_timestamp, max_id, count).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (Double, MediaCreated_time, LikeUser_name, MediaCreated_time, LikeUser_name, MediaCreated_time)) = {

            val (`user-id`, max_timestamp, min_id, min_timestamp, max_id, count) = input
            val url = s"""/v1/users/${user-id}/media/recent?${max_timestamp.map { i => "max_timestamp=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${min_id.map { i => "min_id=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${min_timestamp.map { i => "min_timestamp=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${max_id.map { i => "max_id=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${count.map { i => "count=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    `user-id` <- DoubleGenerator
                    max_timestamp <- MediaCreated_timeGenerator
                    min_id <- LikeUser_nameGenerator
                    min_timestamp <- MediaCreated_timeGenerator
                    max_id <- LikeUser_nameGenerator
                    count <- MediaCreated_timeGenerator
                    

                } yield (`user-id`, max_timestamp, min_id, min_timestamp, max_id, count)

            val inputs = genInputs suchThat { case (`user-id`, max_timestamp, min_id, min_timestamp, max_id, count)=>
                new `UsersUser-idMediaRecentGetValidator`(`user-id`, max_timestamp, min_id, min_timestamp, max_id, count).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                `user-id` <- DoubleGenerator
                max_timestamp <- MediaCreated_timeGenerator
                min_id <- LikeUser_nameGenerator
                min_timestamp <- MediaCreated_timeGenerator
                max_id <- LikeUser_nameGenerator
                count <- MediaCreated_timeGenerator
                

            } yield (`user-id`, max_timestamp, min_id, min_timestamp, max_id, count)

            val inputs = genInputs suchThat { case (`user-id`, max_timestamp, min_id, min_timestamp, max_id, count)=>
                new `UsersUser-idMediaRecentGetValidator`(`user-id`, max_timestamp, min_id, min_timestamp, max_id, count).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
"GET /v1/users/self/feed" should {
        def testInvalidInput(input: (MediaCreated_time, MediaCreated_time, MediaCreated_time)) = {

            val (count, max_id, min_id) = input
            val url = s"""/v1/users/self/feed?${count.map { i => "count=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${max_id.map { i => "max_id=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${min_id.map { i => "min_id=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new UsersSelfFeedGetValidator(count, max_id, min_id).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (MediaCreated_time, MediaCreated_time, MediaCreated_time)) = {

            val (count, max_id, min_id) = input
            val url = s"""/v1/users/self/feed?${count.map { i => "count=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${max_id.map { i => "max_id=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${min_id.map { i => "min_id=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    count <- MediaCreated_timeGenerator
                    max_id <- MediaCreated_timeGenerator
                    min_id <- MediaCreated_timeGenerator
                    

                } yield (count, max_id, min_id)

            val inputs = genInputs suchThat { case (count, max_id, min_id)=>
                new UsersSelfFeedGetValidator(count, max_id, min_id).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                count <- MediaCreated_timeGenerator
                max_id <- MediaCreated_timeGenerator
                min_id <- MediaCreated_timeGenerator
                

            } yield (count, max_id, min_id)

            val inputs = genInputs suchThat { case (count, max_id, min_id)=>
                new UsersSelfFeedGetValidator(count, max_id, min_id).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
"GET /v1/geographies/{geo-id}/media/recent" should {
        def testInvalidInput(input: (Int, MediaCreated_time, MediaCreated_time)) = {

            val (`geo-id`, count, min_id) = input
            val url = s"""/v1/geographies/${geo-id}/media/recent?${count.map { i => "count=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${min_id.map { i => "min_id=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new `GeographiesGeo-idMediaRecentGetValidator`(`geo-id`, count, min_id).errors


            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                status(path) ?= BAD_REQUEST ,
                contentType(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (Int, MediaCreated_time, MediaCreated_time)) = {

            val (`geo-id`, count, min_id) = input
            val url = s"""/v1/geographies/${geo-id}/media/recent?${count.map { i => "count=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}&${min_id.map { i => "min_id=" + URLEncoder.encode(i.toString, "UTF-8")}.getOrElse("")}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            ("given an URL: [" + url + "]") |: (status(path) ?= OK)
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    `geo-id` <- IntGenerator
                    count <- MediaCreated_timeGenerator
                    min_id <- MediaCreated_timeGenerator
                    

                } yield (`geo-id`, count, min_id)

            val inputs = genInputs suchThat { case (`geo-id`, count, min_id)=>
                new `GeographiesGeo-idMediaRecentGetValidator`(`geo-id`, count, min_id).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                `geo-id` <- IntGenerator
                count <- MediaCreated_timeGenerator
                min_id <- MediaCreated_timeGenerator
                

            } yield (`geo-id`, count, min_id)

            val inputs = genInputs suchThat { case (`geo-id`, count, min_id)=>
                new `GeographiesGeo-idMediaRecentGetValidator`(`geo-id`, count, min_id).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
}
