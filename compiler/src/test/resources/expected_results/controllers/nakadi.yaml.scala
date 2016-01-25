
import play.api.mvc.{Action, Controller}

import play.api.data.validation.Constraint

import de.zalando.play.controllers._

import PlayBodyParsing._

import PlayValidations._
import scala.util._




package nakadi.yaml {

    class hack extends hackBase {
        val get_metrics = get_metricsAction { _ =>

            Failure(???)

            

        } //////// EOF ////////  get_metricsAction
        val get_events_from_single_partition = get_events_from_single_partitionAction { input: (String, String, TopicsTopicEventsGetStream_timeout, String, TopicsTopicEventsGetStream_timeout, TopicsTopicEventsGetStream_timeout, TopicsTopicEventsGetStream_timeout, TopicsTopicEventsGetStream_timeout) =>
            val (start_from, partition, stream_limit, topic, batch_limit, batch_flush_timeout, stream_timeout, batch_keep_alive_limit) = input
            

            Failure(???)

            

        } //////// EOF ////////  get_events_from_single_partitionAction
        val get_partition = get_partitionAction { input: (String, String) =>
            val (topic, partition) = input
            

            Failure(???)

            

        } //////// EOF ////////  get_partitionAction
        val get_topics = get_topicsAction { _ =>

            Failure(???)

            

        } //////// EOF ////////  get_topicsAction
        val get_events_from_multiple_partitions = get_events_from_multiple_partitionsAction { input: (TopicsTopicEventsGetStream_timeout, TopicsTopicEventsGetStream_timeout, TopicsTopicEventsGetStream_timeout, String, TopicsTopicEventsGetStream_timeout, TopicsTopicEventsGetStream_timeout, String) =>
            val (stream_timeout, stream_limit, batch_flush_timeout, x_nakadi_cursors, batch_limit, batch_keep_alive_limit, topic) = input
            

            Failure(???)

            

        } //////// EOF ////////  get_events_from_multiple_partitionsAction
        val post_event = post_eventAction { input: (String, TopicsTopicEventsBatchPostEvent) =>
            val (topic, event) = input
            

            Failure(???)

            

        } //////// EOF ////////  post_eventAction
        val get_partitions = get_partitionsAction { (topic: String) =>

            Failure(???)

            

        } //////// EOF ////////  get_partitionsAction
        val post_events = post_eventsAction { input: (String, TopicsTopicEventsBatchPostEvent) =>
            val (topic, event) = input
            

            Failure(???)

            

        } //////// EOF ////////  post_eventsAction
    }
}

