package nakadi.yaml

import play.api.mvc.{Action, Controller, Results}
import play.api.http._
import Results.Status
import de.zalando.play.controllers.{PlayBodyParsing, ParsingError, ResultWrapper}
import PlayBodyParsing._
import scala.util._
// import PickAvailableWriteable._ // if you want play to pick the first available writable
import de.zalando.play.controllers.ArrayWrapper

import de.zalando.play.controllers.PlayPathBindables



trait NakadiYamlBase extends Controller with PlayBodyParsing {
    sealed trait nakadiHackGet_metricsType[Result] extends ResultWrapper[Result]
    case class nakadiHackGet_metrics401(result: Problem)(implicit val writers: List[Writeable[Problem]]) extends nakadiHackGet_metricsType[Problem] { val statusCode = 401 }
    case class nakadiHackGet_metrics503(result: Problem)(implicit val writers: List[Writeable[Problem]]) extends nakadiHackGet_metricsType[Problem] { val statusCode = 503 }
    case class nakadiHackGet_metrics200(result: Metrics)(implicit val writers: List[Writeable[Metrics]]) extends nakadiHackGet_metricsType[Metrics] { val statusCode = 200 }
    

    private type nakadiHackGet_metricsActionRequestType       = (Unit)
    private type nakadiHackGet_metricsActionType[T]            = nakadiHackGet_metricsActionRequestType => nakadiHackGet_metricsType[T]


    val nakadiHackGet_metricsActionConstructor  = Action
    def nakadiHackGet_metricsAction[T] = (f: nakadiHackGet_metricsActionType[T]) => nakadiHackGet_metricsActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { nakadiHackGet_metricsResponseMimeType =>

            

                val result = processValidnakadiHackGet_metricsRequest(f)()(nakadiHackGet_metricsResponseMimeType)
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidnakadiHackGet_metricsRequest[T](f: nakadiHackGet_metricsActionType[T])(request: nakadiHackGet_metricsActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait nakadiHackGet_events_from_single_partitionType[Result] extends ResultWrapper[Result]
    case class nakadiHackGet_events_from_single_partition500(result: Problem)(implicit val writers: List[Writeable[Problem]]) extends nakadiHackGet_events_from_single_partitionType[Problem] { val statusCode = 500 }
    case class nakadiHackGet_events_from_single_partition404(result: Problem)(implicit val writers: List[Writeable[Problem]]) extends nakadiHackGet_events_from_single_partitionType[Problem] { val statusCode = 404 }
    case class nakadiHackGet_events_from_single_partition401(result: Problem)(implicit val writers: List[Writeable[Problem]]) extends nakadiHackGet_events_from_single_partitionType[Problem] { val statusCode = 401 }
    case class nakadiHackGet_events_from_single_partition400(result: Problem)(implicit val writers: List[Writeable[Problem]]) extends nakadiHackGet_events_from_single_partitionType[Problem] { val statusCode = 400 }
    case class nakadiHackGet_events_from_single_partition200(result: SimpleStreamEvent)(implicit val writers: List[Writeable[SimpleStreamEvent]]) extends nakadiHackGet_events_from_single_partitionType[SimpleStreamEvent] { val statusCode = 200 }
    

    private type nakadiHackGet_events_from_single_partitionActionRequestType       = (String, String, TopicsTopicEventsGetStream_timeout, String, Int, TopicsTopicEventsGetStream_timeout, TopicsTopicEventsGetStream_timeout, TopicsTopicEventsGetStream_timeout)
    private type nakadiHackGet_events_from_single_partitionActionType[T]            = nakadiHackGet_events_from_single_partitionActionRequestType => nakadiHackGet_events_from_single_partitionType[T]


    val nakadiHackGet_events_from_single_partitionActionConstructor  = Action
    def nakadiHackGet_events_from_single_partitionAction[T] = (f: nakadiHackGet_events_from_single_partitionActionType[T]) => (start_from: String, partition: String, stream_limit: TopicsTopicEventsGetStream_timeout, topic: String, batch_limit: Int, batch_flush_timeout: TopicsTopicEventsGetStream_timeout, stream_timeout: TopicsTopicEventsGetStream_timeout, batch_keep_alive_limit: TopicsTopicEventsGetStream_timeout) => nakadiHackGet_events_from_single_partitionActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { nakadiHackGet_events_from_single_partitionResponseMimeType =>

            

                val result =
                        new TopicsTopicPartitionsPartitionEventsGetValidator(start_from, partition, stream_limit, topic, batch_limit, batch_flush_timeout, stream_timeout, batch_keep_alive_limit).errors match {
                            case e if e.isEmpty => processValidnakadiHackGet_events_from_single_partitionRequest(f)((start_from, partition, stream_limit, topic, batch_limit, batch_flush_timeout, stream_timeout, batch_keep_alive_limit))(nakadiHackGet_events_from_single_partitionResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(nakadiHackGet_events_from_single_partitionResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidnakadiHackGet_events_from_single_partitionRequest[T](f: nakadiHackGet_events_from_single_partitionActionType[T])(request: nakadiHackGet_events_from_single_partitionActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait nakadiHackGet_partitionType[Result] extends ResultWrapper[Result]
    case class nakadiHackGet_partition200(result: TopicPartition)(implicit val writers: List[Writeable[TopicPartition]]) extends nakadiHackGet_partitionType[TopicPartition] { val statusCode = 200 }
    

    private type nakadiHackGet_partitionActionRequestType       = (String, String)
    private type nakadiHackGet_partitionActionType[T]            = nakadiHackGet_partitionActionRequestType => nakadiHackGet_partitionType[T]


    val nakadiHackGet_partitionActionConstructor  = Action
    def nakadiHackGet_partitionAction[T] = (f: nakadiHackGet_partitionActionType[T]) => (topic: String, partition: String) => nakadiHackGet_partitionActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { nakadiHackGet_partitionResponseMimeType =>

            

                val result =
                        new TopicsTopicPartitionsPartitionGetValidator(topic, partition).errors match {
                            case e if e.isEmpty => processValidnakadiHackGet_partitionRequest(f)((topic, partition))(nakadiHackGet_partitionResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(nakadiHackGet_partitionResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidnakadiHackGet_partitionRequest[T](f: nakadiHackGet_partitionActionType[T])(request: nakadiHackGet_partitionActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait nakadiHackGet_topicsType[Result] extends ResultWrapper[Result]
    case class nakadiHackGet_topics200(result: Seq[Topic])(implicit val writers: List[Writeable[Seq[Topic]]]) extends nakadiHackGet_topicsType[Seq[Topic]] { val statusCode = 200 }
    case class nakadiHackGet_topics401(result: Problem)(implicit val writers: List[Writeable[Problem]]) extends nakadiHackGet_topicsType[Problem] { val statusCode = 401 }
    case class nakadiHackGet_topics503(result: Problem)(implicit val writers: List[Writeable[Problem]]) extends nakadiHackGet_topicsType[Problem] { val statusCode = 503 }
    

    private type nakadiHackGet_topicsActionRequestType       = (Unit)
    private type nakadiHackGet_topicsActionType[T]            = nakadiHackGet_topicsActionRequestType => nakadiHackGet_topicsType[T]


    val nakadiHackGet_topicsActionConstructor  = Action
    def nakadiHackGet_topicsAction[T] = (f: nakadiHackGet_topicsActionType[T]) => nakadiHackGet_topicsActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { nakadiHackGet_topicsResponseMimeType =>

            

                val result = processValidnakadiHackGet_topicsRequest(f)()(nakadiHackGet_topicsResponseMimeType)
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidnakadiHackGet_topicsRequest[T](f: nakadiHackGet_topicsActionType[T])(request: nakadiHackGet_topicsActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait nakadiHackGet_events_from_multiple_partitionsType[Result] extends ResultWrapper[Result]
    case class nakadiHackGet_events_from_multiple_partitions500(result: Problem)(implicit val writers: List[Writeable[Problem]]) extends nakadiHackGet_events_from_multiple_partitionsType[Problem] { val statusCode = 500 }
    case class nakadiHackGet_events_from_multiple_partitions404(result: Problem)(implicit val writers: List[Writeable[Problem]]) extends nakadiHackGet_events_from_multiple_partitionsType[Problem] { val statusCode = 404 }
    case class nakadiHackGet_events_from_multiple_partitions401(result: Problem)(implicit val writers: List[Writeable[Problem]]) extends nakadiHackGet_events_from_multiple_partitionsType[Problem] { val statusCode = 401 }
    case class nakadiHackGet_events_from_multiple_partitions400(result: Problem)(implicit val writers: List[Writeable[Problem]]) extends nakadiHackGet_events_from_multiple_partitionsType[Problem] { val statusCode = 400 }
    case class nakadiHackGet_events_from_multiple_partitions200(result: SimpleStreamEvent)(implicit val writers: List[Writeable[SimpleStreamEvent]]) extends nakadiHackGet_events_from_multiple_partitionsType[SimpleStreamEvent] { val statusCode = 200 }
    

    private type nakadiHackGet_events_from_multiple_partitionsActionRequestType       = (TopicsTopicEventsGetStream_timeout, TopicsTopicEventsGetStream_timeout, TopicsTopicEventsGetStream_timeout, String, Int, TopicsTopicEventsGetStream_timeout, String)
    private type nakadiHackGet_events_from_multiple_partitionsActionType[T]            = nakadiHackGet_events_from_multiple_partitionsActionRequestType => nakadiHackGet_events_from_multiple_partitionsType[T]


    val nakadiHackGet_events_from_multiple_partitionsActionConstructor  = Action
    def nakadiHackGet_events_from_multiple_partitionsAction[T] = (f: nakadiHackGet_events_from_multiple_partitionsActionType[T]) => (stream_timeout: TopicsTopicEventsGetStream_timeout, stream_limit: TopicsTopicEventsGetStream_timeout, batch_flush_timeout: TopicsTopicEventsGetStream_timeout, batch_limit: Int, batch_keep_alive_limit: TopicsTopicEventsGetStream_timeout, topic: String) => nakadiHackGet_events_from_multiple_partitionsActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { nakadiHackGet_events_from_multiple_partitionsResponseMimeType =>

            
            val x_nakadi_cursors: Either[String,String] =
                fromHeaders[String]("x_nakadi_cursors", request.headers.toMap)
            
                (x_nakadi_cursors) match {
                    case (Right(x_nakadi_cursors)) =>

                val result =
                        new TopicsTopicEventsGetValidator(stream_timeout, stream_limit, batch_flush_timeout, x_nakadi_cursors, batch_limit, batch_keep_alive_limit, topic).errors match {
                            case e if e.isEmpty => processValidnakadiHackGet_events_from_multiple_partitionsRequest(f)((stream_timeout, stream_limit, batch_flush_timeout, x_nakadi_cursors, batch_limit, batch_keep_alive_limit, topic))(nakadiHackGet_events_from_multiple_partitionsResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(nakadiHackGet_events_from_multiple_partitionsResponseMimeType)
                                BadRequest(l)
                        }
                result
                case (_) =>
                    val problem: Seq[String] = Seq(x_nakadi_cursors).filter{_.isLeft}.map(_.left.get)
                    val msg = problem.mkString("\n")
                    BadRequest(msg)
                }
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidnakadiHackGet_events_from_multiple_partitionsRequest[T](f: nakadiHackGet_events_from_multiple_partitionsActionType[T])(request: nakadiHackGet_events_from_multiple_partitionsActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait nakadiHackPost_eventType[Result] extends ResultWrapper[Result]
    case class nakadiHackPost_event201(result: Null)(implicit val writers: List[Writeable[Null]]) extends nakadiHackPost_eventType[Null] { val statusCode = 201 }
    case class nakadiHackPost_event403(result: Problem)(implicit val writers: List[Writeable[Problem]]) extends nakadiHackPost_eventType[Problem] { val statusCode = 403 }
    case class nakadiHackPost_event503(result: Problem)(implicit val writers: List[Writeable[Problem]]) extends nakadiHackPost_eventType[Problem] { val statusCode = 503 }
    case class nakadiHackPost_event401(result: Problem)(implicit val writers: List[Writeable[Problem]]) extends nakadiHackPost_eventType[Problem] { val statusCode = 401 }
    case class nakadiHackPost_event422(result: Problem)(implicit val writers: List[Writeable[Problem]]) extends nakadiHackPost_eventType[Problem] { val statusCode = 422 }
    

    private type nakadiHackPost_eventActionRequestType       = (String, TopicsTopicEventsBatchPostEvent)
    private type nakadiHackPost_eventActionType[T]            = nakadiHackPost_eventActionRequestType => nakadiHackPost_eventType[T]

        private def nakadiHackPost_eventParser(acceptedTypes: Seq[String], maxLength: Int = parse.DefaultMaxTextLength) = {
            def bodyMimeType: Option[MediaType] => String = mediaType => {
                val requestType = mediaType.toSeq.map {
                    case m: MediaRange => m
                    case MediaType(a,b,c) => new MediaRange(a,b,c,None,Nil)
                }
                negotiateContent(requestType, acceptedTypes).orElse(acceptedTypes.headOption).getOrElse("application/json")
            }
            
            import de.zalando.play.controllers.WrappedBodyParsers
            
            val customParsers = WrappedBodyParsers.optionParser[Event]
            optionParser[Event](bodyMimeType, customParsers, "Invalid TopicsTopicEventsBatchPostEvent", maxLength)
        }

    val nakadiHackPost_eventActionConstructor  = Action
    def nakadiHackPost_eventAction[T] = (f: nakadiHackPost_eventActionType[T]) => (topic: String) => nakadiHackPost_eventActionConstructor(nakadiHackPost_eventParser(Seq[String]("application/json"))) { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { nakadiHackPost_eventResponseMimeType =>

            val event = request.body
            

                val result =
                        new TopicsTopicEventsPostValidator(topic, event).errors match {
                            case e if e.isEmpty => processValidnakadiHackPost_eventRequest(f)((topic, event))(nakadiHackPost_eventResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(nakadiHackPost_eventResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidnakadiHackPost_eventRequest[T](f: nakadiHackPost_eventActionType[T])(request: nakadiHackPost_eventActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait nakadiHackGet_partitionsType[Result] extends ResultWrapper[Result]
    case class nakadiHackGet_partitions200(result: Seq[TopicPartition])(implicit val writers: List[Writeable[Seq[TopicPartition]]]) extends nakadiHackGet_partitionsType[Seq[TopicPartition]] { val statusCode = 200 }
    

    private type nakadiHackGet_partitionsActionRequestType       = (String)
    private type nakadiHackGet_partitionsActionType[T]            = nakadiHackGet_partitionsActionRequestType => nakadiHackGet_partitionsType[T]


    val nakadiHackGet_partitionsActionConstructor  = Action
    def nakadiHackGet_partitionsAction[T] = (f: nakadiHackGet_partitionsActionType[T]) => (topic: String) => nakadiHackGet_partitionsActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { nakadiHackGet_partitionsResponseMimeType =>

            

                val result =
                        new TopicsTopicPartitionsGetValidator(topic).errors match {
                            case e if e.isEmpty => processValidnakadiHackGet_partitionsRequest(f)((topic))(nakadiHackGet_partitionsResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(nakadiHackGet_partitionsResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidnakadiHackGet_partitionsRequest[T](f: nakadiHackGet_partitionsActionType[T])(request: nakadiHackGet_partitionsActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait nakadiHackPost_eventsType[Result] extends ResultWrapper[Result]
    case class nakadiHackPost_events201(result: Null)(implicit val writers: List[Writeable[Null]]) extends nakadiHackPost_eventsType[Null] { val statusCode = 201 }
    case class nakadiHackPost_events403(result: Problem)(implicit val writers: List[Writeable[Problem]]) extends nakadiHackPost_eventsType[Problem] { val statusCode = 403 }
    case class nakadiHackPost_events503(result: Problem)(implicit val writers: List[Writeable[Problem]]) extends nakadiHackPost_eventsType[Problem] { val statusCode = 503 }
    case class nakadiHackPost_events401(result: Problem)(implicit val writers: List[Writeable[Problem]]) extends nakadiHackPost_eventsType[Problem] { val statusCode = 401 }
    case class nakadiHackPost_events422(result: Problem)(implicit val writers: List[Writeable[Problem]]) extends nakadiHackPost_eventsType[Problem] { val statusCode = 422 }
    

    private type nakadiHackPost_eventsActionRequestType       = (String, TopicsTopicEventsBatchPostEvent)
    private type nakadiHackPost_eventsActionType[T]            = nakadiHackPost_eventsActionRequestType => nakadiHackPost_eventsType[T]

        private def nakadiHackPost_eventsParser(acceptedTypes: Seq[String], maxLength: Int = parse.DefaultMaxTextLength) = {
            def bodyMimeType: Option[MediaType] => String = mediaType => {
                val requestType = mediaType.toSeq.map {
                    case m: MediaRange => m
                    case MediaType(a,b,c) => new MediaRange(a,b,c,None,Nil)
                }
                negotiateContent(requestType, acceptedTypes).orElse(acceptedTypes.headOption).getOrElse("application/json")
            }
            
            import de.zalando.play.controllers.WrappedBodyParsers
            
            val customParsers = WrappedBodyParsers.optionParser[Event]
            optionParser[Event](bodyMimeType, customParsers, "Invalid TopicsTopicEventsBatchPostEvent", maxLength)
        }

    val nakadiHackPost_eventsActionConstructor  = Action
    def nakadiHackPost_eventsAction[T] = (f: nakadiHackPost_eventsActionType[T]) => (topic: String) => nakadiHackPost_eventsActionConstructor(nakadiHackPost_eventsParser(Seq[String]("application/json"))) { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { nakadiHackPost_eventsResponseMimeType =>

            val event = request.body
            

                val result =
                        new TopicsTopicEventsBatchPostValidator(topic, event).errors match {
                            case e if e.isEmpty => processValidnakadiHackPost_eventsRequest(f)((topic, event))(nakadiHackPost_eventsResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(nakadiHackPost_eventsResponseMimeType)
                                BadRequest(l)
                        }
                result
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidnakadiHackPost_eventsRequest[T](f: nakadiHackPost_eventsActionType[T])(request: nakadiHackPost_eventsActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    case object EmptyReturn extends ResultWrapper[Results.EmptyContent]                          { val statusCode = 204; val result = Results.EmptyContent(); val writers = List(new DefaultWriteables{}.writeableOf_EmptyContent); override def toResult(mimeType: String): Option[play.api.mvc.Result] = Some(Results.NoContent) }
    case object NotImplementedYet extends ResultWrapper[Results.EmptyContent]  with nakadiHackGet_metricsType[Results.EmptyContent] with nakadiHackGet_events_from_single_partitionType[Results.EmptyContent] with nakadiHackGet_partitionType[Results.EmptyContent] with nakadiHackGet_topicsType[Results.EmptyContent] with nakadiHackGet_events_from_multiple_partitionsType[Results.EmptyContent] with nakadiHackPost_eventType[Results.EmptyContent] with nakadiHackGet_partitionsType[Results.EmptyContent] with nakadiHackPost_eventsType[Results.EmptyContent] { val statusCode = 501; val result = Results.EmptyContent(); val writers = List(new DefaultWriteables{}.writeableOf_EmptyContent); override def toResult(mimeType: String): Option[play.api.mvc.Result] = Some(Results.NotImplemented) }
}
