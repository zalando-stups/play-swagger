package nakadi.yaml

import play.api.mvc.{Action, Controller, Results}
import play.api.http.Writeable
import Results.Status
import de.zalando.play.controllers.{PlayBodyParsing, ParsingError}
import PlayBodyParsing._
import scala.util._
import de.zalando.play.controllers.ArrayWrapper
import de.zalando.play.controllers.PlayPathBindables




trait hackBase extends Controller with PlayBodyParsing {
    private type get_metricsActionRequestType       = (Unit)
    private type get_metricsActionType              = get_metricsActionRequestType => Try[Any]

    private val errorToStatusget_metrics: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def get_metricsAction = (f: get_metricsActionType) => Action {        val get_metricsResponseMimeType    = "application/json"

        val possibleWriters = Map(
                401 -> anyToWritable[Problem], 
                503 -> anyToWritable[Problem], 
                200 -> anyToWritable[Metrics]
        )        
            val result = processValidget_metricsRequest(f)()                
            result
    }

    private def processValidget_metricsRequest[T <: Any](f: get_metricsActionType)(request: get_metricsActionRequestType, writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusget_metrics orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val get_metricsWritableJson = writer(mimeType)
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        }
        status
    }
    private type get_events_from_single_partitionActionRequestType       = (String, String, TopicsTopicEventsGetStream_timeout, String, Int, TopicsTopicEventsGetStream_timeout, TopicsTopicEventsGetStream_timeout, TopicsTopicEventsGetStream_timeout)
    private type get_events_from_single_partitionActionType              = get_events_from_single_partitionActionRequestType => Try[Any]

    private val errorToStatusget_events_from_single_partition: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def get_events_from_single_partitionAction = (f: get_events_from_single_partitionActionType) => (start_from: String, partition: String, stream_limit: TopicsTopicEventsGetStream_timeout, topic: String, batch_limit: Int, batch_flush_timeout: TopicsTopicEventsGetStream_timeout, stream_timeout: TopicsTopicEventsGetStream_timeout, batch_keep_alive_limit: TopicsTopicEventsGetStream_timeout) => Action {        val get_events_from_single_partitionResponseMimeType    = "application/json"

        val possibleWriters = Map(
                500 -> anyToWritable[Problem], 
                404 -> anyToWritable[Problem], 
                401 -> anyToWritable[Problem], 
                400 -> anyToWritable[Problem], 
                200 -> anyToWritable[SimpleStreamEvent]
        )        
            val result =                
                    new TopicsTopicPartitionsPartitionEventsGetValidator(start_from, partition, stream_limit, topic, batch_limit, batch_flush_timeout, stream_timeout, batch_keep_alive_limit).errors match {
                        case e if e.isEmpty => processValidget_events_from_single_partitionRequest(f)((start_from, partition, stream_limit, topic, batch_limit, batch_flush_timeout, stream_timeout, batch_keep_alive_limit), possibleWriters, get_events_from_single_partitionResponseMimeType)
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(get_events_from_single_partitionResponseMimeType)
                            BadRequest(l)
                    }
                
            result
    }

    private def processValidget_events_from_single_partitionRequest[T <: Any](f: get_events_from_single_partitionActionType)(request: get_events_from_single_partitionActionRequestType, writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusget_events_from_single_partition orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val get_events_from_single_partitionWritableJson = writer(mimeType)
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        }
        status
    }
    private type get_partitionActionRequestType       = (String, String)
    private type get_partitionActionType              = get_partitionActionRequestType => Try[Any]

    private val errorToStatusget_partition: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def get_partitionAction = (f: get_partitionActionType) => (topic: String, partition: String) => Action {        val get_partitionResponseMimeType    = "application/json"

        val possibleWriters = Map(
                200 -> anyToWritable[TopicPartition]
        )        
            val result =                
                    new TopicsTopicPartitionsPartitionGetValidator(topic, partition).errors match {
                        case e if e.isEmpty => processValidget_partitionRequest(f)((topic, partition), possibleWriters, get_partitionResponseMimeType)
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(get_partitionResponseMimeType)
                            BadRequest(l)
                    }
                
            result
    }

    private def processValidget_partitionRequest[T <: Any](f: get_partitionActionType)(request: get_partitionActionRequestType, writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusget_partition orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val get_partitionWritableJson = writer(mimeType)
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        }
        status
    }
    private type get_topicsActionRequestType       = (Unit)
    private type get_topicsActionType              = get_topicsActionRequestType => Try[Any]

    private val errorToStatusget_topics: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def get_topicsAction = (f: get_topicsActionType) => Action {        val get_topicsResponseMimeType    = "application/json"

        val possibleWriters = Map(
                200 -> anyToWritable[Seq[Topic]], 
                401 -> anyToWritable[Problem], 
                503 -> anyToWritable[Problem]
        )        
            val result = processValidget_topicsRequest(f)()                
            result
    }

    private def processValidget_topicsRequest[T <: Any](f: get_topicsActionType)(request: get_topicsActionRequestType, writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusget_topics orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val get_topicsWritableJson = writer(mimeType)
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        }
        status
    }
    private type get_events_from_multiple_partitionsActionRequestType       = (TopicsTopicEventsGetStream_timeout, TopicsTopicEventsGetStream_timeout, TopicsTopicEventsGetStream_timeout, String, Int, TopicsTopicEventsGetStream_timeout, String)
    private type get_events_from_multiple_partitionsActionType              = get_events_from_multiple_partitionsActionRequestType => Try[Any]

    private val errorToStatusget_events_from_multiple_partitions: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def get_events_from_multiple_partitionsAction = (f: get_events_from_multiple_partitionsActionType) => (stream_timeout: TopicsTopicEventsGetStream_timeout, stream_limit: TopicsTopicEventsGetStream_timeout, batch_flush_timeout: TopicsTopicEventsGetStream_timeout, batch_limit: Int, batch_keep_alive_limit: TopicsTopicEventsGetStream_timeout, topic: String) => Action { request =>        val get_events_from_multiple_partitionsResponseMimeType    = "application/json"

        val possibleWriters = Map(
                500 -> anyToWritable[Problem], 
                404 -> anyToWritable[Problem], 
                401 -> anyToWritable[Problem], 
                400 -> anyToWritable[Problem], 
                200 -> anyToWritable[SimpleStreamEvent]
        )        
        val x_nakadi_cursors_either =
            fromHeaders[String]("x_nakadi_cursors", request.headers.toMap)
            (x_nakadi_cursors_either) match {
                case (Right(x_nakadi_cursors)) =>
        
            val result =                
                    new TopicsTopicEventsGetValidator(stream_timeout, stream_limit, batch_flush_timeout, x_nakadi_cursors, batch_limit, batch_keep_alive_limit, topic).errors match {
                        case e if e.isEmpty => processValidget_events_from_multiple_partitionsRequest(f)((stream_timeout, stream_limit, batch_flush_timeout, x_nakadi_cursors, batch_limit, batch_keep_alive_limit, topic), possibleWriters, get_events_from_multiple_partitionsResponseMimeType)
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(get_events_from_multiple_partitionsResponseMimeType)
                            BadRequest(l)
                    }
                
            result
            case (_) =>
                val msg = Seq(x_nakadi_cursors_either).filter{_.isLeft}.map(_.left.get).mkString("\n")
                BadRequest(msg)
            }
        
    }

    private def processValidget_events_from_multiple_partitionsRequest[T <: Any](f: get_events_from_multiple_partitionsActionType)(request: get_events_from_multiple_partitionsActionRequestType, writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusget_events_from_multiple_partitions orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val get_events_from_multiple_partitionsWritableJson = writer(mimeType)
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        }
        status
    }
    private type post_eventActionRequestType       = (String, TopicsTopicEventsBatchPostEvent)
    private type post_eventActionType              = post_eventActionRequestType => Try[Any]

    private val errorToStatuspost_event: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]
        private def post_eventParser(maxLength: Int = parse.DefaultMaxTextLength) = optionParser[Event]("application/json", "Invalid TopicsTopicEventsBatchPostEvent", maxLength)

    def post_eventAction = (f: post_eventActionType) => (topic: String) => Action(post_eventParser()) { request =>        val post_eventResponseMimeType    = "application/json"

        val possibleWriters = Map(
                201 -> anyToWritable[Null], 
                403 -> anyToWritable[Problem], 
                503 -> anyToWritable[Problem], 
                401 -> anyToWritable[Problem], 
                422 -> anyToWritable[Problem]
        )        
        val event = request.body
        
            val result =                
                    new TopicsTopicEventsPostValidator(topic, event).errors match {
                        case e if e.isEmpty => processValidpost_eventRequest(f)((topic, event), possibleWriters, post_eventResponseMimeType)
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(post_eventResponseMimeType)
                            BadRequest(l)
                    }
                
            result
    }

    private def processValidpost_eventRequest[T <: Any](f: post_eventActionType)(request: post_eventActionRequestType, writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatuspost_event orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val post_eventWritableJson = writer(mimeType)
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        }
        status
    }
    private type get_partitionsActionRequestType       = (String)
    private type get_partitionsActionType              = get_partitionsActionRequestType => Try[Any]

    private val errorToStatusget_partitions: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def get_partitionsAction = (f: get_partitionsActionType) => (topic: String) => Action {        val get_partitionsResponseMimeType    = "application/json"

        val possibleWriters = Map(
                200 -> anyToWritable[Seq[TopicPartition]]
        )        
            val result =                
                    new TopicsTopicPartitionsGetValidator(topic).errors match {
                        case e if e.isEmpty => processValidget_partitionsRequest(f)((topic), possibleWriters, get_partitionsResponseMimeType)
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(get_partitionsResponseMimeType)
                            BadRequest(l)
                    }
                
            result
    }

    private def processValidget_partitionsRequest[T <: Any](f: get_partitionsActionType)(request: get_partitionsActionRequestType, writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusget_partitions orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val get_partitionsWritableJson = writer(mimeType)
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        }
        status
    }
    private type post_eventsActionRequestType       = (String, TopicsTopicEventsBatchPostEvent)
    private type post_eventsActionType              = post_eventsActionRequestType => Try[Any]

    private val errorToStatuspost_events: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]
        private def post_eventsParser(maxLength: Int = parse.DefaultMaxTextLength) = optionParser[Event]("application/json", "Invalid TopicsTopicEventsBatchPostEvent", maxLength)

    def post_eventsAction = (f: post_eventsActionType) => (topic: String) => Action(post_eventsParser()) { request =>        val post_eventsResponseMimeType    = "application/json"

        val possibleWriters = Map(
                201 -> anyToWritable[Null], 
                403 -> anyToWritable[Problem], 
                503 -> anyToWritable[Problem], 
                401 -> anyToWritable[Problem], 
                422 -> anyToWritable[Problem]
        )        
        val event = request.body
        
            val result =                
                    new TopicsTopicEventsBatchPostValidator(topic, event).errors match {
                        case e if e.isEmpty => processValidpost_eventsRequest(f)((topic, event), possibleWriters, post_eventsResponseMimeType)
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(post_eventsResponseMimeType)
                            BadRequest(l)
                    }
                
            result
    }

    private def processValidpost_eventsRequest[T <: Any](f: post_eventsActionType)(request: post_eventsActionRequestType, writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatuspost_events orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val post_eventsWritableJson = writer(mimeType)
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        }
        status
    }
}
