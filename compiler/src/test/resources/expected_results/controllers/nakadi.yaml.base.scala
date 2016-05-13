package nakadi.yaml

import play.api.mvc.{Action, Controller, Results}
import play.api.http._
import Results.Status
import de.zalando.play.controllers.{PlayBodyParsing, ParsingError, ResponseWriters}
import PlayBodyParsing._
import scala.util._
import de.zalando.play.controllers.ArrayWrapper

import de.zalando.play.controllers.PlayPathBindables




trait NakadiYamlBase extends Controller with PlayBodyParsing {
    private type nakadiHackGet_metricsActionRequestType       = (Unit)
    private type nakadiHackGet_metricsActionType              = nakadiHackGet_metricsActionRequestType => Try[(Int, Any)]

    private val errorToStatusnakadiHackGet_metrics: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val nakadiHackGet_metricsActionConstructor  = Action
    def nakadiHackGet_metricsAction = (f: nakadiHackGet_metricsActionType) => nakadiHackGet_metricsActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { nakadiHackGet_metricsResponseMimeType =>
                val possibleWriters = Map(
                    401 -> anyToWritable[Problem], 
                    503 -> anyToWritable[Problem], 
                    200 -> anyToWritable[Metrics]
            )
            
            

                val result = processValidnakadiHackGet_metricsRequest(f)()(possibleWriters, nakadiHackGet_metricsResponseMimeType)
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidnakadiHackGet_metricsRequest[T <: Any](f: nakadiHackGet_metricsActionType)(request: nakadiHackGet_metricsActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusnakadiHackGet_metrics orElse defaultErrorMapping)(error)(error.getMessage)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        case Success(other) =>
            implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
            Status(500)(new IllegalStateException(s"Expected pair (responseCode, response) from the controller, but was: other"))
        }
        status
    }
    private type nakadiHackGet_events_from_single_partitionActionRequestType       = (String, String, TopicsTopicEventsGetStream_timeout, String, Int, TopicsTopicEventsGetStream_timeout, TopicsTopicEventsGetStream_timeout, TopicsTopicEventsGetStream_timeout)
    private type nakadiHackGet_events_from_single_partitionActionType              = nakadiHackGet_events_from_single_partitionActionRequestType => Try[(Int, Any)]

    private val errorToStatusnakadiHackGet_events_from_single_partition: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val nakadiHackGet_events_from_single_partitionActionConstructor  = Action
    def nakadiHackGet_events_from_single_partitionAction = (f: nakadiHackGet_events_from_single_partitionActionType) => (start_from: String, partition: String, stream_limit: TopicsTopicEventsGetStream_timeout, topic: String, batch_limit: Int, batch_flush_timeout: TopicsTopicEventsGetStream_timeout, stream_timeout: TopicsTopicEventsGetStream_timeout, batch_keep_alive_limit: TopicsTopicEventsGetStream_timeout) => nakadiHackGet_events_from_single_partitionActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { nakadiHackGet_events_from_single_partitionResponseMimeType =>
                val possibleWriters = Map(
                    500 -> anyToWritable[Problem], 
                    404 -> anyToWritable[Problem], 
                    401 -> anyToWritable[Problem], 
                    400 -> anyToWritable[Problem], 
                    200 -> anyToWritable[SimpleStreamEvent]
            )
            
            

                val result =
                        new TopicsTopicPartitionsPartitionEventsGetValidator(start_from, partition, stream_limit, topic, batch_limit, batch_flush_timeout, stream_timeout, batch_keep_alive_limit).errors match {
                            case e if e.isEmpty => processValidnakadiHackGet_events_from_single_partitionRequest(f)((start_from, partition, stream_limit, topic, batch_limit, batch_flush_timeout, stream_timeout, batch_keep_alive_limit))(possibleWriters, nakadiHackGet_events_from_single_partitionResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(nakadiHackGet_events_from_single_partitionResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidnakadiHackGet_events_from_single_partitionRequest[T <: Any](f: nakadiHackGet_events_from_single_partitionActionType)(request: nakadiHackGet_events_from_single_partitionActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusnakadiHackGet_events_from_single_partition orElse defaultErrorMapping)(error)(error.getMessage)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        case Success(other) =>
            implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
            Status(500)(new IllegalStateException(s"Expected pair (responseCode, response) from the controller, but was: other"))
        }
        status
    }
    private type nakadiHackGet_partitionActionRequestType       = (String, String)
    private type nakadiHackGet_partitionActionType              = nakadiHackGet_partitionActionRequestType => Try[(Int, Any)]

    private val errorToStatusnakadiHackGet_partition: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val nakadiHackGet_partitionActionConstructor  = Action
    def nakadiHackGet_partitionAction = (f: nakadiHackGet_partitionActionType) => (topic: String, partition: String) => nakadiHackGet_partitionActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { nakadiHackGet_partitionResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[TopicPartition]
            )
            
            

                val result =
                        new TopicsTopicPartitionsPartitionGetValidator(topic, partition).errors match {
                            case e if e.isEmpty => processValidnakadiHackGet_partitionRequest(f)((topic, partition))(possibleWriters, nakadiHackGet_partitionResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(nakadiHackGet_partitionResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidnakadiHackGet_partitionRequest[T <: Any](f: nakadiHackGet_partitionActionType)(request: nakadiHackGet_partitionActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusnakadiHackGet_partition orElse defaultErrorMapping)(error)(error.getMessage)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        case Success(other) =>
            implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
            Status(500)(new IllegalStateException(s"Expected pair (responseCode, response) from the controller, but was: other"))
        }
        status
    }
    private type nakadiHackGet_topicsActionRequestType       = (Unit)
    private type nakadiHackGet_topicsActionType              = nakadiHackGet_topicsActionRequestType => Try[(Int, Any)]

    private val errorToStatusnakadiHackGet_topics: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val nakadiHackGet_topicsActionConstructor  = Action
    def nakadiHackGet_topicsAction = (f: nakadiHackGet_topicsActionType) => nakadiHackGet_topicsActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { nakadiHackGet_topicsResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[Seq[Topic]], 
                    401 -> anyToWritable[Problem], 
                    503 -> anyToWritable[Problem]
            )
            
            

                val result = processValidnakadiHackGet_topicsRequest(f)()(possibleWriters, nakadiHackGet_topicsResponseMimeType)
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidnakadiHackGet_topicsRequest[T <: Any](f: nakadiHackGet_topicsActionType)(request: nakadiHackGet_topicsActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusnakadiHackGet_topics orElse defaultErrorMapping)(error)(error.getMessage)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        case Success(other) =>
            implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
            Status(500)(new IllegalStateException(s"Expected pair (responseCode, response) from the controller, but was: other"))
        }
        status
    }
    private type nakadiHackGet_events_from_multiple_partitionsActionRequestType       = (TopicsTopicEventsGetStream_timeout, TopicsTopicEventsGetStream_timeout, TopicsTopicEventsGetStream_timeout, String, Int, TopicsTopicEventsGetStream_timeout, String)
    private type nakadiHackGet_events_from_multiple_partitionsActionType              = nakadiHackGet_events_from_multiple_partitionsActionRequestType => Try[(Int, Any)]

    private val errorToStatusnakadiHackGet_events_from_multiple_partitions: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val nakadiHackGet_events_from_multiple_partitionsActionConstructor  = Action
    def nakadiHackGet_events_from_multiple_partitionsAction = (f: nakadiHackGet_events_from_multiple_partitionsActionType) => (stream_timeout: TopicsTopicEventsGetStream_timeout, stream_limit: TopicsTopicEventsGetStream_timeout, batch_flush_timeout: TopicsTopicEventsGetStream_timeout, batch_limit: Int, batch_keep_alive_limit: TopicsTopicEventsGetStream_timeout, topic: String) => nakadiHackGet_events_from_multiple_partitionsActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { nakadiHackGet_events_from_multiple_partitionsResponseMimeType =>
                val possibleWriters = Map(
                    500 -> anyToWritable[Problem], 
                    404 -> anyToWritable[Problem], 
                    401 -> anyToWritable[Problem], 
                    400 -> anyToWritable[Problem], 
                    200 -> anyToWritable[SimpleStreamEvent]
            )
            
            val x_nakadi_cursors: Either[String,String] =
                fromParameters[String]("header")("x_nakadi_cursors", request.headers.toMap)
            
            
                (x_nakadi_cursors) match {
                    case (Right(x_nakadi_cursors)) =>
            

                val result =
                        new TopicsTopicEventsGetValidator(stream_timeout, stream_limit, batch_flush_timeout, x_nakadi_cursors, batch_limit, batch_keep_alive_limit, topic).errors match {
                            case e if e.isEmpty => processValidnakadiHackGet_events_from_multiple_partitionsRequest(f)((stream_timeout, stream_limit, batch_flush_timeout, x_nakadi_cursors, batch_limit, batch_keep_alive_limit, topic))(possibleWriters, nakadiHackGet_events_from_multiple_partitionsResponseMimeType)
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

    private def processValidnakadiHackGet_events_from_multiple_partitionsRequest[T <: Any](f: nakadiHackGet_events_from_multiple_partitionsActionType)(request: nakadiHackGet_events_from_multiple_partitionsActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusnakadiHackGet_events_from_multiple_partitions orElse defaultErrorMapping)(error)(error.getMessage)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        case Success(other) =>
            implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
            Status(500)(new IllegalStateException(s"Expected pair (responseCode, response) from the controller, but was: other"))
        }
        status
    }
    private type nakadiHackPost_eventActionRequestType       = (String, TopicsTopicEventsBatchPostEvent)
    private type nakadiHackPost_eventActionType              = nakadiHackPost_eventActionRequestType => Try[(Int, Any)]

    private val errorToStatusnakadiHackPost_event: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

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
    def nakadiHackPost_eventAction = (f: nakadiHackPost_eventActionType) => (topic: String) => nakadiHackPost_eventActionConstructor(nakadiHackPost_eventParser(Seq[String]("application/json"))) { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { nakadiHackPost_eventResponseMimeType =>
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
                            case e if e.isEmpty => processValidnakadiHackPost_eventRequest(f)((topic, event))(possibleWriters, nakadiHackPost_eventResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(nakadiHackPost_eventResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidnakadiHackPost_eventRequest[T <: Any](f: nakadiHackPost_eventActionType)(request: nakadiHackPost_eventActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusnakadiHackPost_event orElse defaultErrorMapping)(error)(error.getMessage)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        case Success(other) =>
            implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
            Status(500)(new IllegalStateException(s"Expected pair (responseCode, response) from the controller, but was: other"))
        }
        status
    }
    private type nakadiHackGet_partitionsActionRequestType       = (String)
    private type nakadiHackGet_partitionsActionType              = nakadiHackGet_partitionsActionRequestType => Try[(Int, Any)]

    private val errorToStatusnakadiHackGet_partitions: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]


    val nakadiHackGet_partitionsActionConstructor  = Action
    def nakadiHackGet_partitionsAction = (f: nakadiHackGet_partitionsActionType) => (topic: String) => nakadiHackGet_partitionsActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { nakadiHackGet_partitionsResponseMimeType =>
                val possibleWriters = Map(
                    200 -> anyToWritable[Seq[TopicPartition]]
            )
            
            

                val result =
                        new TopicsTopicPartitionsGetValidator(topic).errors match {
                            case e if e.isEmpty => processValidnakadiHackGet_partitionsRequest(f)((topic))(possibleWriters, nakadiHackGet_partitionsResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(nakadiHackGet_partitionsResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidnakadiHackGet_partitionsRequest[T <: Any](f: nakadiHackGet_partitionsActionType)(request: nakadiHackGet_partitionsActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusnakadiHackGet_partitions orElse defaultErrorMapping)(error)(error.getMessage)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        case Success(other) =>
            implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
            Status(500)(new IllegalStateException(s"Expected pair (responseCode, response) from the controller, but was: other"))
        }
        status
    }
    private type nakadiHackPost_eventsActionRequestType       = (String, TopicsTopicEventsBatchPostEvent)
    private type nakadiHackPost_eventsActionType              = nakadiHackPost_eventsActionRequestType => Try[(Int, Any)]

    private val errorToStatusnakadiHackPost_events: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

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
    def nakadiHackPost_eventsAction = (f: nakadiHackPost_eventsActionType) => (topic: String) => nakadiHackPost_eventsActionConstructor(nakadiHackPost_eventsParser(Seq[String]("application/json"))) { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { nakadiHackPost_eventsResponseMimeType =>
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
                            case e if e.isEmpty => processValidnakadiHackPost_eventsRequest(f)((topic, event))(possibleWriters, nakadiHackPost_eventsResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(nakadiHackPost_eventsResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(415)("The server doesn't support any of the requested mime types"))
    }

    private def processValidnakadiHackPost_eventsRequest[T <: Any](f: nakadiHackPost_eventsActionType)(request: nakadiHackPost_eventsActionRequestType)
                             (writers: Map[Int, String => Writeable[T]], mimeType: String)(implicit m: Manifest[T]) = {
        import de.zalando.play.controllers.ResponseWriters
        
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusnakadiHackPost_events orElse defaultErrorMapping)(error)(error.getMessage)
            case Success((code: Int, result: T @ unchecked)) =>
                val writerOpt = ResponseWriters.choose(mimeType)[T]().orElse(writers.get(code).map(_.apply(mimeType)))
                writerOpt.map { implicit writer =>
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        case Success(other) =>
            implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
            Status(500)(new IllegalStateException(s"Expected pair (responseCode, response) from the controller, but was: other"))
        }
        status
    }
}
