
import play.api.mvc.{Action, Controller}

import play.api.data.validation.Constraint

import de.zalando.play.controllers._

import PlayBodyParsing._

import PlayValidations._





package instagram.api.yaml {

    class InstagramApiYaml extends InstagramApiYamlBase {
        val getusersSearch = getusersSearchAction { input: (String, MediaFilter) =>
            val (q, count) = input
            

            ???

            

        } //////// EOF ////////  getusersSearchAction
        val getusersSelfMediaLiked = getusersSelfMediaLikedAction { input: (MediaId, MediaId) =>
            val (count, max_like_id) = input
            

            ???

            

        } //////// EOF ////////  getusersSelfMediaLikedAction
        val gettagsSearch = gettagsSearchAction { (q: MediaFilter) =>

            ???

            

        } //////// EOF ////////  gettagsSearchAction
        val getusersSelfFeed = getusersSelfFeedAction { input: (MediaId, MediaId, MediaId) =>
            val (count, max_id, min_id) = input
            

            ???

            

        } //////// EOF ////////  getusersSelfFeedAction
        val getmediaSearch = getmediaSearchAction { input: (MediaId, MediaId, LocationLatitude, MediaId, LocationLatitude) =>
            val (mAX_TIMESTAMP, dISTANCE, lNG, mIN_TIMESTAMP, lAT) = input
            

            ???

            

        } //////// EOF ////////  getmediaSearchAction
        val getmediaByShortcode = getmediaByShortcodeAction { (shortcode: String) =>

            ???

            

        } //////// EOF ////////  getmediaByShortcodeAction
        val getlocationsSearch = getlocationsSearchAction { input: (MediaId, MediaId, MediaId, LocationLatitude, MediaId, LocationLatitude) =>
            val (foursquare_v2_id, facebook_places_id, distance, lat, foursquare_id, lng) = input
            

            ???

            

        } //////// EOF ////////  getlocationsSearchAction
        val getusersSelfRequested_by = getusersSelfRequested_byAction {

            ???

            

        } //////// EOF ////////  getusersSelfRequested_byAction
        val getmediaPopular = getmediaPopularAction {

            ???

            

        } //////// EOF ////////  getmediaPopularAction
    }
}

