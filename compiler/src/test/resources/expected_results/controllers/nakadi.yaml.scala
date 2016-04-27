
import play.api.mvc.{Action, Controller}

import play.api.data.validation.Constraint

import de.zalando.play.controllers._

import PlayBodyParsing._

import PlayValidations._

import scala.util._



package nakadi.yaml {

    class NakadiYaml extends NakadiYamlBase {
        val nakadiHackGet_metrics = nakadiHackGet_metricsAction { _ =>
            

            // Response: Success((401, Problem, ))
            // Response: Success((503, Problem, ))
            // Response: Success((200, Metrics))
            Failure(???)

            

        } //////// EOF ////////  nakadiHackGet_metricsAction
        val nakadiHackGet_events_from_single_partition = nakadiHackGet_events_from_single_partitionAction { input: (String, String, TopicsTopicEventsGetStream_timeout, String, Int, TopicsTopicEventsGetStream_timeout, TopicsTopicEventsGetStream_timeout, TopicsTopicEventsGetStream_timeout) =>
            val (start_from, partition, stream_limit, topic, batch_limit, batch_flush_timeout, stream_timeout, batch_keep_alive_limit) = input
            

            // Response: Success((500, Problem, ))
            // Response: Success((404, Problem, ))
            // Response: Success((401, Problem, ))
            // Response: Success((400, Problem, ))
            // Response: Success((200, SimpleStreamEvent))
            Failure(???)

            

        } //////// EOF ////////  nakadiHackGet_events_from_single_partitionAction
        val nakadiHackGet_partition = nakadiHackGet_partitionAction { input: (String, String) =>
            val (topic, partition) = input
            

            // Response: Success((200, TopicPartition))
            Failure(???)

            

        } //////// EOF ////////  nakadiHackGet_partitionAction
        val nakadiHackGet_topics = nakadiHackGet_topicsAction { _ =>
            

            // Response: Success((200, Seq[Topic], ))
            // Response: Success((401, Problem, ))
            // Response: Success((503, Problem))
            Failure(???)

            

        } //////// EOF ////////  nakadiHackGet_topicsAction
        val nakadiHackGet_events_from_multiple_partitions = nakadiHackGet_events_from_multiple_partitionsAction { input: (TopicsTopicEventsGetStream_timeout, TopicsTopicEventsGetStream_timeout, TopicsTopicEventsGetStream_timeout, String, Int, TopicsTopicEventsGetStream_timeout, String) =>
            val (stream_timeout, stream_limit, batch_flush_timeout, x_nakadi_cursors, batch_limit, batch_keep_alive_limit, topic) = input
            

            // Response: Success((500, Problem, ))
            // Response: Success((404, Problem, ))
            // Response: Success((401, Problem, ))
            // Response: Success((400, Problem, ))
            // Response: Success((200, SimpleStreamEvent))
            Failure(???)

            

        } //////// EOF ////////  nakadiHackGet_events_from_multiple_partitionsAction
        val nakadiHackPost_event = nakadiHackPost_eventAction { input: (String, TopicsTopicEventsBatchPostEvent) =>
            val (topic, event) = input
            

            // Response: Success((201, Null, ))
            // Response: Success((403, Problem, ))
            // Response: Success((503, Problem, ))
            // Response: Success((401, Problem, ))
            // Response: Success((422, Problem))
            Failure(???)

            

        } //////// EOF ////////  nakadiHackPost_eventAction
        val nakadiHackGet_partitions = nakadiHackGet_partitionsAction { (topic: String) =>
            

            // Response: Success((200, Seq[TopicPartition]))
            Failure(???)

            

        } //////// EOF ////////  nakadiHackGet_partitionsAction
        val nakadiHackPost_events = nakadiHackPost_eventsAction { input: (String, TopicsTopicEventsBatchPostEvent) =>
            val (topic, event) = input
            

            // Response: Success((201, Null, ))
            // Response: Success((403, Problem, ))
            // Response: Success((503, Problem, ))
            // Response: Success((401, Problem, ))
            // Response: Success((422, Problem))
            Failure(???)

            

        } //////// EOF ////////  nakadiHackPost_eventsAction
    }
}
