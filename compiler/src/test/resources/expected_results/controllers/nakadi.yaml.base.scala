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
    private val get_metricsResponseMimeType    = "application/json"
    private val get_metricsActionSuccessStatus = Status(200)

    private type get_metricsActionRequestType       = (Unit)
    private type get_metricsActionResultType        = Metrics
    private type get_metricsActionType              = get_metricsActionRequestType => Try[get_metricsActionResultType]

    private val errorToStatusget_metrics: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def get_metricsAction = (f: get_metricsActionType) => Action {        
            val result = processValidget_metricsRequest(f)()                
            result
    }

    private def processValidget_metricsRequest(f: get_metricsActionType)(request: get_metricsActionRequestType) = {
        implicit val get_metricsWritableJson = anyToWritable[get_metricsActionResultType](get_metricsResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusget_metrics orElse defaultErrorMapping)(error)
            case Success(result) => get_metricsActionSuccessStatus(result)
        }
        status
    }
    private val get_events_from_single_partitionResponseMimeType    = "application/json"
    private val get_events_from_single_partitionActionSuccessStatus = Status(200)

    private type get_events_from_single_partitionActionRequestType       = (String, String, TopicsTopicEventsGetStream_timeout, String, Int, TopicsTopicEventsGetStream_timeout, TopicsTopicEventsGetStream_timeout, TopicsTopicEventsGetStream_timeout)
    private type get_events_from_single_partitionActionResultType        = Problem
    private type get_events_from_single_partitionActionType              = get_events_from_single_partitionActionRequestType => Try[get_events_from_single_partitionActionResultType]

    private val errorToStatusget_events_from_single_partition: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def get_events_from_single_partitionAction = (f: get_events_from_single_partitionActionType) => (start_from: String, partition: String, stream_limit: TopicsTopicEventsGetStream_timeout, topic: String, batch_limit: Int, batch_flush_timeout: TopicsTopicEventsGetStream_timeout, stream_timeout: TopicsTopicEventsGetStream_timeout, batch_keep_alive_limit: TopicsTopicEventsGetStream_timeout) => Action {        
            val result =                
                    new TopicsTopicPartitionsPartitionEventsGetValidator(start_from, partition, stream_limit, topic, batch_limit, batch_flush_timeout, stream_timeout, batch_keep_alive_limit).errors match {
                        case e if e.isEmpty => processValidget_events_from_single_partitionRequest(f)((start_from, partition, stream_limit, topic, batch_limit, batch_flush_timeout, stream_timeout, batch_keep_alive_limit))
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(get_events_from_single_partitionResponseMimeType)
                            BadRequest(l)
                    }
                
            result
    }

    private def processValidget_events_from_single_partitionRequest(f: get_events_from_single_partitionActionType)(request: get_events_from_single_partitionActionRequestType) = {
        implicit val get_events_from_single_partitionWritableJson = anyToWritable[get_events_from_single_partitionActionResultType](get_events_from_single_partitionResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusget_events_from_single_partition orElse defaultErrorMapping)(error)
            case Success(result) => get_events_from_single_partitionActionSuccessStatus(result)
        }
        status
    }
    private val get_partitionResponseMimeType    = "application/json"
    private val get_partitionActionSuccessStatus = Status(200)

    private type get_partitionActionRequestType       = (String, String)
    private type get_partitionActionResultType        = TopicPartition
    private type get_partitionActionType              = get_partitionActionRequestType => Try[get_partitionActionResultType]

    private val errorToStatusget_partition: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def get_partitionAction = (f: get_partitionActionType) => (topic: String, partition: String) => Action {        
            val result =                
                    new TopicsTopicPartitionsPartitionGetValidator(topic, partition).errors match {
                        case e if e.isEmpty => processValidget_partitionRequest(f)((topic, partition))
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(get_partitionResponseMimeType)
                            BadRequest(l)
                    }
                
            result
    }

    private def processValidget_partitionRequest(f: get_partitionActionType)(request: get_partitionActionRequestType) = {
        implicit val get_partitionWritableJson = anyToWritable[get_partitionActionResultType](get_partitionResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusget_partition orElse defaultErrorMapping)(error)
            case Success(result) => get_partitionActionSuccessStatus(result)
        }
        status
    }
    private val get_topicsResponseMimeType    = "application/json"
    private val get_topicsActionSuccessStatus = Status(200)

    private type get_topicsActionRequestType       = (Unit)
    private type get_topicsActionResultType        = Seq[Topic]
    private type get_topicsActionType              = get_topicsActionRequestType => Try[get_topicsActionResultType]

    private val errorToStatusget_topics: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def get_topicsAction = (f: get_topicsActionType) => Action {        
            val result = processValidget_topicsRequest(f)()                
            result
    }

    private def processValidget_topicsRequest(f: get_topicsActionType)(request: get_topicsActionRequestType) = {
        implicit val get_topicsWritableJson = anyToWritable[get_topicsActionResultType](get_topicsResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusget_topics orElse defaultErrorMapping)(error)
            case Success(result) => get_topicsActionSuccessStatus(result)
        }
        status
    }
    private val get_events_from_multiple_partitionsResponseMimeType    = "application/json"
    private val get_events_from_multiple_partitionsActionSuccessStatus = Status(200)

    private type get_events_from_multiple_partitionsActionRequestType       = (TopicsTopicEventsGetStream_timeout, TopicsTopicEventsGetStream_timeout, TopicsTopicEventsGetStream_timeout, String, Int, TopicsTopicEventsGetStream_timeout, String)
    private type get_events_from_multiple_partitionsActionResultType        = Problem
    private type get_events_from_multiple_partitionsActionType              = get_events_from_multiple_partitionsActionRequestType => Try[get_events_from_multiple_partitionsActionResultType]

    private val errorToStatusget_events_from_multiple_partitions: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def get_events_from_multiple_partitionsAction = (f: get_events_from_multiple_partitionsActionType) => (stream_timeout: TopicsTopicEventsGetStream_timeout, stream_limit: TopicsTopicEventsGetStream_timeout, batch_flush_timeout: TopicsTopicEventsGetStream_timeout, batch_limit: Int, batch_keep_alive_limit: TopicsTopicEventsGetStream_timeout, topic: String) => Action { request =>        
        val x_nakadi_cursors_either =
            fromHeaders[String]("x_nakadi_cursors", request.headers.toMap)
            (x_nakadi_cursors_either) match {
                case (Right(x_nakadi_cursors)) =>
        
            val result =                
                    new TopicsTopicEventsGetValidator(stream_timeout, stream_limit, batch_flush_timeout, x_nakadi_cursors, batch_limit, batch_keep_alive_limit, topic).errors match {
                        case e if e.isEmpty => processValidget_events_from_multiple_partitionsRequest(f)((stream_timeout, stream_limit, batch_flush_timeout, x_nakadi_cursors, batch_limit, batch_keep_alive_limit, topic))
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

    private def processValidget_events_from_multiple_partitionsRequest(f: get_events_from_multiple_partitionsActionType)(request: get_events_from_multiple_partitionsActionRequestType) = {
        implicit val get_events_from_multiple_partitionsWritableJson = anyToWritable[get_events_from_multiple_partitionsActionResultType](get_events_from_multiple_partitionsResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusget_events_from_multiple_partitions orElse defaultErrorMapping)(error)
            case Success(result) => get_events_from_multiple_partitionsActionSuccessStatus(result)
        }
        status
    }
    private val post_eventResponseMimeType    = "application/json"
    private val post_eventActionSuccessStatus = Status(200)

    private type post_eventActionRequestType       = (String, TopicsTopicEventsBatchPostEvent)
    private type post_eventActionResultType        = Null
    private type post_eventActionType              = post_eventActionRequestType => Try[post_eventActionResultType]

    private val errorToStatuspost_event: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]
        private def post_eventParser(maxLength: Int = parse.DefaultMaxTextLength) = optionParser[Event]("application/json", "Invalid TopicsTopicEventsBatchPostEvent", maxLength)

    def post_eventAction = (f: post_eventActionType) => (topic: String) => Action(post_eventParser()) { request =>        
        val event = request.body
        
            val result =                
                    new TopicsTopicEventsPostValidator(topic, event).errors match {
                        case e if e.isEmpty => processValidpost_eventRequest(f)((topic, event))
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(post_eventResponseMimeType)
                            BadRequest(l)
                    }
                
            result
    }

    private def processValidpost_eventRequest(f: post_eventActionType)(request: post_eventActionRequestType) = {
        implicit val post_eventWritableJson = anyToWritable[post_eventActionResultType](post_eventResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatuspost_event orElse defaultErrorMapping)(error)
            case Success(result) => post_eventActionSuccessStatus(result)
        }
        status
    }
    private val get_partitionsResponseMimeType    = "application/json"
    private val get_partitionsActionSuccessStatus = Status(200)

    private type get_partitionsActionRequestType       = (String)
    private type get_partitionsActionResultType        = Seq[TopicPartition]
    private type get_partitionsActionType              = get_partitionsActionRequestType => Try[get_partitionsActionResultType]

    private val errorToStatusget_partitions: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def get_partitionsAction = (f: get_partitionsActionType) => (topic: String) => Action {        
            val result =                
                    new TopicsTopicPartitionsGetValidator(topic).errors match {
                        case e if e.isEmpty => processValidget_partitionsRequest(f)((topic))
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(get_partitionsResponseMimeType)
                            BadRequest(l)
                    }
                
            result
    }

    private def processValidget_partitionsRequest(f: get_partitionsActionType)(request: get_partitionsActionRequestType) = {
        implicit val get_partitionsWritableJson = anyToWritable[get_partitionsActionResultType](get_partitionsResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusget_partitions orElse defaultErrorMapping)(error)
            case Success(result) => get_partitionsActionSuccessStatus(result)
        }
        status
    }
    private val post_eventsResponseMimeType    = "application/json"
    private val post_eventsActionSuccessStatus = Status(200)

    private type post_eventsActionRequestType       = (String, TopicsTopicEventsBatchPostEvent)
    private type post_eventsActionResultType        = Null
    private type post_eventsActionType              = post_eventsActionRequestType => Try[post_eventsActionResultType]

    private val errorToStatuspost_events: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]
        private def post_eventsParser(maxLength: Int = parse.DefaultMaxTextLength) = optionParser[Event]("application/json", "Invalid TopicsTopicEventsBatchPostEvent", maxLength)

    def post_eventsAction = (f: post_eventsActionType) => (topic: String) => Action(post_eventsParser()) { request =>        
        val event = request.body
        
            val result =                
                    new TopicsTopicEventsBatchPostValidator(topic, event).errors match {
                        case e if e.isEmpty => processValidpost_eventsRequest(f)((topic, event))
                        case l =>
                            implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(post_eventsResponseMimeType)
                            BadRequest(l)
                    }
                
            result
    }

    private def processValidpost_eventsRequest(f: post_eventsActionType)(request: post_eventsActionRequestType) = {
        implicit val post_eventsWritableJson = anyToWritable[post_eventsActionResultType](post_eventsResponseMimeType)
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatuspost_events orElse defaultErrorMapping)(error)
            case Success(result) => post_eventsActionSuccessStatus(result)
        }
        status
    }
}
