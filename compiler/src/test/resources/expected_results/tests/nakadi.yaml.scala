package nakadi.yaml

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
    class NakadiYamlSpec extends Specification {
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


    "GET /topics/{topic}/partitions/{partition}" should {
        def testInvalidInput(input: (String, String)) = {

            val (topic, partition) = input

            val url = s"""/topics/${toPath(topic)}/partitions/${toPath(partition)}"""
            val headers = Seq("Accept" -> toHeader("application/json"))

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new TopicsTopicPartitionsPartitionGetValidator(topic, partition).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some(""),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (String, String)) = {
            val (topic, partition) = input
            
            val url = s"""/topics/${toPath(topic)}/partitions/${toPath(partition)}"""
            val headers = Seq("Accept" -> toHeader("application/json"))
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new TopicsTopicPartitionsPartitionGetValidator(topic, partition).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]

            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some(""),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                        topic <- StringGenerator
                        partition <- StringGenerator
                    
                } yield (topic, partition)
            val inputs = genInputs suchThat { case (topic, partition) =>
                new TopicsTopicPartitionsPartitionGetValidator(topic, partition).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    topic <- StringGenerator
                    partition <- StringGenerator
                
            } yield (topic, partition)
            val inputs = genInputs suchThat { case (topic, partition) =>
                new TopicsTopicPartitionsPartitionGetValidator(topic, partition).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "GET /topics/{topic}/events" should {
        def testInvalidInput(input: (TopicsTopicEventsGetStream_timeout, TopicsTopicEventsGetStream_timeout, TopicsTopicEventsGetStream_timeout, String, Int, TopicsTopicEventsGetStream_timeout, String)) = {

            val (stream_timeout, stream_limit, batch_flush_timeout, x_nakadi_cursors, batch_limit, batch_keep_alive_limit, topic) = input

            val url = s"""/topics/${toPath(topic)}/events?${toQuery("stream_timeout", stream_timeout)}&${toQuery("stream_limit", stream_limit)}&${toQuery("batch_flush_timeout", batch_flush_timeout)}&${toQuery("batch_limit", batch_limit)}&${toQuery("batch_keep_alive_limit", batch_keep_alive_limit)}"""
            val headers = Seq("x_nakadi_cursors" -> toHeader(x_nakadi_cursors), "Accept" -> toHeader("application/json"))

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new TopicsTopicEventsGetValidator(stream_timeout, stream_limit, batch_flush_timeout, x_nakadi_cursors, batch_limit, batch_keep_alive_limit, topic).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some(""),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (TopicsTopicEventsGetStream_timeout, TopicsTopicEventsGetStream_timeout, TopicsTopicEventsGetStream_timeout, String, Int, TopicsTopicEventsGetStream_timeout, String)) = {
            val (stream_timeout, stream_limit, batch_flush_timeout, x_nakadi_cursors, batch_limit, batch_keep_alive_limit, topic) = input
            
            val url = s"""/topics/${toPath(topic)}/events?${toQuery("stream_timeout", stream_timeout)}&${toQuery("stream_limit", stream_limit)}&${toQuery("batch_flush_timeout", batch_flush_timeout)}&${toQuery("batch_limit", batch_limit)}&${toQuery("batch_keep_alive_limit", batch_keep_alive_limit)}"""
            val headers = Seq("x_nakadi_cursors" -> toHeader(x_nakadi_cursors), "Accept" -> toHeader("application/json"))
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new TopicsTopicEventsGetValidator(stream_timeout, stream_limit, batch_flush_timeout, x_nakadi_cursors, batch_limit, batch_keep_alive_limit, topic).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]

            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some(""),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                        stream_timeout <- TopicsTopicEventsGetStream_timeoutGenerator
                        stream_limit <- TopicsTopicEventsGetStream_timeoutGenerator
                        batch_flush_timeout <- TopicsTopicEventsGetStream_timeoutGenerator
                        x_nakadi_cursors <- StringGenerator
                        batch_limit <- IntGenerator
                        batch_keep_alive_limit <- TopicsTopicEventsGetStream_timeoutGenerator
                        topic <- StringGenerator
                    
                } yield (stream_timeout, stream_limit, batch_flush_timeout, x_nakadi_cursors, batch_limit, batch_keep_alive_limit, topic)
            val inputs = genInputs suchThat { case (stream_timeout, stream_limit, batch_flush_timeout, x_nakadi_cursors, batch_limit, batch_keep_alive_limit, topic) =>
                new TopicsTopicEventsGetValidator(stream_timeout, stream_limit, batch_flush_timeout, x_nakadi_cursors, batch_limit, batch_keep_alive_limit, topic).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    stream_timeout <- TopicsTopicEventsGetStream_timeoutGenerator
                    stream_limit <- TopicsTopicEventsGetStream_timeoutGenerator
                    batch_flush_timeout <- TopicsTopicEventsGetStream_timeoutGenerator
                    x_nakadi_cursors <- StringGenerator
                    batch_limit <- IntGenerator
                    batch_keep_alive_limit <- TopicsTopicEventsGetStream_timeoutGenerator
                    topic <- StringGenerator
                
            } yield (stream_timeout, stream_limit, batch_flush_timeout, x_nakadi_cursors, batch_limit, batch_keep_alive_limit, topic)
            val inputs = genInputs suchThat { case (stream_timeout, stream_limit, batch_flush_timeout, x_nakadi_cursors, batch_limit, batch_keep_alive_limit, topic) =>
                new TopicsTopicEventsGetValidator(stream_timeout, stream_limit, batch_flush_timeout, x_nakadi_cursors, batch_limit, batch_keep_alive_limit, topic).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "GET /topics/{topic}/partitions/{partition}/events" should {
        def testInvalidInput(input: (String, String, TopicsTopicEventsGetStream_timeout, String, Int, TopicsTopicEventsGetStream_timeout, TopicsTopicEventsGetStream_timeout, TopicsTopicEventsGetStream_timeout)) = {

            val (start_from, partition, stream_limit, topic, batch_limit, batch_flush_timeout, stream_timeout, batch_keep_alive_limit) = input

            val url = s"""/topics/${toPath(topic)}/partitions/${toPath(partition)}/events?${toQuery("start_from", start_from)}&${toQuery("stream_limit", stream_limit)}&${toQuery("batch_limit", batch_limit)}&${toQuery("batch_flush_timeout", batch_flush_timeout)}&${toQuery("stream_timeout", stream_timeout)}&${toQuery("batch_keep_alive_limit", batch_keep_alive_limit)}"""
            val headers = Seq("Accept" -> toHeader("application/json"))

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new TopicsTopicPartitionsPartitionEventsGetValidator(start_from, partition, stream_limit, topic, batch_limit, batch_flush_timeout, stream_timeout, batch_keep_alive_limit).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some(""),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (String, String, TopicsTopicEventsGetStream_timeout, String, Int, TopicsTopicEventsGetStream_timeout, TopicsTopicEventsGetStream_timeout, TopicsTopicEventsGetStream_timeout)) = {
            val (start_from, partition, stream_limit, topic, batch_limit, batch_flush_timeout, stream_timeout, batch_keep_alive_limit) = input
            
            val url = s"""/topics/${toPath(topic)}/partitions/${toPath(partition)}/events?${toQuery("start_from", start_from)}&${toQuery("stream_limit", stream_limit)}&${toQuery("batch_limit", batch_limit)}&${toQuery("batch_flush_timeout", batch_flush_timeout)}&${toQuery("stream_timeout", stream_timeout)}&${toQuery("batch_keep_alive_limit", batch_keep_alive_limit)}"""
            val headers = Seq("Accept" -> toHeader("application/json"))
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new TopicsTopicPartitionsPartitionEventsGetValidator(start_from, partition, stream_limit, topic, batch_limit, batch_flush_timeout, stream_timeout, batch_keep_alive_limit).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]

            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some(""),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                        start_from <- StringGenerator
                        partition <- StringGenerator
                        stream_limit <- TopicsTopicEventsGetStream_timeoutGenerator
                        topic <- StringGenerator
                        batch_limit <- IntGenerator
                        batch_flush_timeout <- TopicsTopicEventsGetStream_timeoutGenerator
                        stream_timeout <- TopicsTopicEventsGetStream_timeoutGenerator
                        batch_keep_alive_limit <- TopicsTopicEventsGetStream_timeoutGenerator
                    
                } yield (start_from, partition, stream_limit, topic, batch_limit, batch_flush_timeout, stream_timeout, batch_keep_alive_limit)
            val inputs = genInputs suchThat { case (start_from, partition, stream_limit, topic, batch_limit, batch_flush_timeout, stream_timeout, batch_keep_alive_limit) =>
                new TopicsTopicPartitionsPartitionEventsGetValidator(start_from, partition, stream_limit, topic, batch_limit, batch_flush_timeout, stream_timeout, batch_keep_alive_limit).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    start_from <- StringGenerator
                    partition <- StringGenerator
                    stream_limit <- TopicsTopicEventsGetStream_timeoutGenerator
                    topic <- StringGenerator
                    batch_limit <- IntGenerator
                    batch_flush_timeout <- TopicsTopicEventsGetStream_timeoutGenerator
                    stream_timeout <- TopicsTopicEventsGetStream_timeoutGenerator
                    batch_keep_alive_limit <- TopicsTopicEventsGetStream_timeoutGenerator
                
            } yield (start_from, partition, stream_limit, topic, batch_limit, batch_flush_timeout, stream_timeout, batch_keep_alive_limit)
            val inputs = genInputs suchThat { case (start_from, partition, stream_limit, topic, batch_limit, batch_flush_timeout, stream_timeout, batch_keep_alive_limit) =>
                new TopicsTopicPartitionsPartitionEventsGetValidator(start_from, partition, stream_limit, topic, batch_limit, batch_flush_timeout, stream_timeout, batch_keep_alive_limit).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "POST /topics/{topic}/events" should {
        def testInvalidInput(input: (String, TopicsTopicEventsBatchPostEvent)) = {

            val (topic, event) = input

            val url = s"""/topics/${toPath(topic)}/events"""
            val headers = Seq("Accept" -> toHeader("application/json"))
                val parsed_event = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(event)

            val path = route(FakeRequest(POST, url).withHeaders(headers:_*).withBody(parsed_event)).get
            val errors = new TopicsTopicEventsPostValidator(topic, event).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" + "and body [" + parsed_event + "]") |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some(""),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (String, TopicsTopicEventsBatchPostEvent)) = {
            val (topic, event) = input
            
            val parsed_event = parserConstructor("application/json").writeValueAsString(event)
            
            val url = s"""/topics/${toPath(topic)}/events"""
            val headers = Seq("Accept" -> toHeader("application/json"))
            val path = route(FakeRequest(POST, url).withHeaders(headers:_*).withBody(parsed_event)).get
            val errors = new TopicsTopicEventsPostValidator(topic, event).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]

            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" + "and body [" + parsed_event + "]") |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some(""),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                        topic <- StringGenerator
                        event <- TopicsTopicEventsBatchPostEventGenerator
                    
                } yield (topic, event)
            val inputs = genInputs suchThat { case (topic, event) =>
                new TopicsTopicEventsPostValidator(topic, event).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    topic <- StringGenerator
                    event <- TopicsTopicEventsBatchPostEventGenerator
                
            } yield (topic, event)
            val inputs = genInputs suchThat { case (topic, event) =>
                new TopicsTopicEventsPostValidator(topic, event).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "GET /topics/{topic}/partitions" should {
        def testInvalidInput(topic: String) = {


            val url = s"""/topics/${toPath(topic)}/partitions"""
            val headers = Seq("Accept" -> toHeader("application/json"))

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new TopicsTopicPartitionsGetValidator(topic).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some(""),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(topic: String) = {
            
            val url = s"""/topics/${toPath(topic)}/partitions"""
            val headers = Seq("Accept" -> toHeader("application/json"))
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new TopicsTopicPartitionsGetValidator(topic).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]

            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some(""),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    topic <- StringGenerator
                } yield topic
            val inputs = genInputs suchThat { topic =>
                new TopicsTopicPartitionsGetValidator(topic).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                topic <- StringGenerator
            } yield topic
            val inputs = genInputs suchThat { topic =>
                new TopicsTopicPartitionsGetValidator(topic).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

    "POST /topics/{topic}/events/batch" should {
        def testInvalidInput(input: (String, TopicsTopicEventsBatchPostEvent)) = {

            val (topic, event) = input

            val url = s"""/topics/${toPath(topic)}/events/batch"""
            val headers = Seq("Accept" -> toHeader("application/json"))
                val parsed_event = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(event)

            val path = route(FakeRequest(POST, url).withHeaders(headers:_*).withBody(parsed_event)).get
            val errors = new TopicsTopicEventsBatchPostValidator(topic, event).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" + "and body [" + parsed_event + "]") |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some(""),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (String, TopicsTopicEventsBatchPostEvent)) = {
            val (topic, event) = input
            
            val parsed_event = parserConstructor("application/json").writeValueAsString(event)
            
            val url = s"""/topics/${toPath(topic)}/events/batch"""
            val headers = Seq("Accept" -> toHeader("application/json"))
            val path = route(FakeRequest(POST, url).withHeaders(headers:_*).withBody(parsed_event)).get
            val errors = new TopicsTopicEventsBatchPostValidator(topic, event).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]

            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" + "and body [" + parsed_event + "]") |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some(""),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                        topic <- StringGenerator
                        event <- TopicsTopicEventsBatchPostEventGenerator
                    
                } yield (topic, event)
            val inputs = genInputs suchThat { case (topic, event) =>
                new TopicsTopicEventsBatchPostValidator(topic, event).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    topic <- StringGenerator
                    event <- TopicsTopicEventsBatchPostEventGenerator
                
            } yield (topic, event)
            val inputs = genInputs suchThat { case (topic, event) =>
                new TopicsTopicEventsBatchPostValidator(topic, event).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }
}
