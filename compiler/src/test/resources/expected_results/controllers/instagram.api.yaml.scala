
import play.api.mvc.{Action, Controller}

import play.api.data.validation.Constraint

import de.zalando.play.controllers._

import PlayBodyParsing._

import PlayValidations._

import scala.util._

import scala.math.BigInt

import scala.math.BigInt

import scala.math.BigDecimal


/**
 * This controller is re-generated after each change in the specification.
 * Please only place your hand-written code between appropriate comments in the body of the controller.
 */

package instagram.api.yaml {

    class InstagramApiYaml extends InstagramApiYamlBase {
        val getmediaByMedia_idLikes = getmediaByMedia_idLikesAction { (media_id: BigInt) =>  
            // ----- Start of unmanaged code area for action  InstagramApiYaml.getmediaByMedia_idLikes
            Failure(???)
            // ----- End of unmanaged code area for action  InstagramApiYaml.getmediaByMedia_idLikes
        }
        val postmediaByMedia_idLikes = postmediaByMedia_idLikesAction { (media_id: BigInt) =>  
            // ----- Start of unmanaged code area for action  InstagramApiYaml.postmediaByMedia_idLikes
            Failure(???)
            // ----- End of unmanaged code area for action  InstagramApiYaml.postmediaByMedia_idLikes
        }
        val deletemediaByMedia_idLikes = deletemediaByMedia_idLikesAction { (media_id: BigInt) =>  
            // ----- Start of unmanaged code area for action  InstagramApiYaml.deletemediaByMedia_idLikes
            Failure(???)
            // ----- End of unmanaged code area for action  InstagramApiYaml.deletemediaByMedia_idLikes
        }
        val getusersByUser_idFollows = getusersByUser_idFollowsAction { (user_id: BigDecimal) =>  
            // ----- Start of unmanaged code area for action  InstagramApiYaml.getusersByUser_idFollows
            Failure(???)
            // ----- End of unmanaged code area for action  InstagramApiYaml.getusersByUser_idFollows
        }
        val getlocationsByLocation_id = getlocationsByLocation_idAction { (location_id: BigInt) =>  
            // ----- Start of unmanaged code area for action  InstagramApiYaml.getlocationsByLocation_id
            Failure(???)
            // ----- End of unmanaged code area for action  InstagramApiYaml.getlocationsByLocation_id
        }
        val getusersSearch = getusersSearchAction { input: (String, MediaFilter) =>
            val (q, count) = input
            // ----- Start of unmanaged code area for action  InstagramApiYaml.getusersSearch
            Failure(???)
            // ----- End of unmanaged code area for action  InstagramApiYaml.getusersSearch
        }
        val getusersSelfMediaLiked = getusersSelfMediaLikedAction { input: (MediaId, MediaId) =>
            val (count, max_like_id) = input
            // ----- Start of unmanaged code area for action  InstagramApiYaml.getusersSelfMediaLiked
            Failure(???)
            // ----- End of unmanaged code area for action  InstagramApiYaml.getusersSelfMediaLiked
        }
        val gettagsByTag_name = gettagsByTag_nameAction { (tag_name: String) =>  
            // ----- Start of unmanaged code area for action  InstagramApiYaml.gettagsByTag_name
            Failure(???)
            // ----- End of unmanaged code area for action  InstagramApiYaml.gettagsByTag_name
        }
        val gettagsSearch = gettagsSearchAction { (q: MediaFilter) =>  
            // ----- Start of unmanaged code area for action  InstagramApiYaml.gettagsSearch
            Failure(???)
            // ----- End of unmanaged code area for action  InstagramApiYaml.gettagsSearch
        }
        val getusersByUser_idFollowed_by = getusersByUser_idFollowed_byAction { (user_id: BigDecimal) =>  
            // ----- Start of unmanaged code area for action  InstagramApiYaml.getusersByUser_idFollowed_by
            Failure(???)
            // ----- End of unmanaged code area for action  InstagramApiYaml.getusersByUser_idFollowed_by
        }
        val getmediaByMedia_idComments = getmediaByMedia_idCommentsAction { (media_id: BigInt) =>  
            // ----- Start of unmanaged code area for action  InstagramApiYaml.getmediaByMedia_idComments
            Failure(???)
            // ----- End of unmanaged code area for action  InstagramApiYaml.getmediaByMedia_idComments
        }
        val postmediaByMedia_idComments = postmediaByMedia_idCommentsAction { input: (BigInt, LocationLatitude) =>
            val (media_id, tEXT) = input
            // ----- Start of unmanaged code area for action  InstagramApiYaml.postmediaByMedia_idComments
            Failure(???)
            // ----- End of unmanaged code area for action  InstagramApiYaml.postmediaByMedia_idComments
        }
        val deletemediaByMedia_idComments = deletemediaByMedia_idCommentsAction { (media_id: BigInt) =>  
            // ----- Start of unmanaged code area for action  InstagramApiYaml.deletemediaByMedia_idComments
            Failure(???)
            // ----- End of unmanaged code area for action  InstagramApiYaml.deletemediaByMedia_idComments
        }
        val gettagsByTag_nameMediaRecent = gettagsByTag_nameMediaRecentAction { (tag_name: String) =>  
            // ----- Start of unmanaged code area for action  InstagramApiYaml.gettagsByTag_nameMediaRecent
            Failure(???)
            // ----- End of unmanaged code area for action  InstagramApiYaml.gettagsByTag_nameMediaRecent
        }
        val postusersByUser_idRelationship = postusersByUser_idRelationshipAction { input: (BigDecimal, UsersUser_idRelationshipPostAction) =>
            val (user_id, action) = input
            // ----- Start of unmanaged code area for action  InstagramApiYaml.postusersByUser_idRelationship
            Failure(???)
            // ----- End of unmanaged code area for action  InstagramApiYaml.postusersByUser_idRelationship
        }
        val getusersSelfFeed = getusersSelfFeedAction { input: (MediaId, MediaId, MediaId) =>
            val (count, max_id, min_id) = input
            // ----- Start of unmanaged code area for action  InstagramApiYaml.getusersSelfFeed
            Failure(???)
            // ----- End of unmanaged code area for action  InstagramApiYaml.getusersSelfFeed
        }
        val getusersByUser_id = getusersByUser_idAction { (user_id: BigDecimal) =>  
            // ----- Start of unmanaged code area for action  InstagramApiYaml.getusersByUser_id
            Failure(???)
            // ----- End of unmanaged code area for action  InstagramApiYaml.getusersByUser_id
        }
        val getmediaSearch = getmediaSearchAction { input: (MediaId, BigInt, LocationLatitude, MediaId, LocationLatitude) =>
            val (mAX_TIMESTAMP, dISTANCE, lNG, mIN_TIMESTAMP, lAT) = input
            // ----- Start of unmanaged code area for action  InstagramApiYaml.getmediaSearch
            Failure(???)
            // ----- End of unmanaged code area for action  InstagramApiYaml.getmediaSearch
        }
        val getgeographiesByGeo_idMediaRecent = getgeographiesByGeo_idMediaRecentAction { input: (BigInt, MediaId, MediaId) =>
            val (geo_id, count, min_id) = input
            // ----- Start of unmanaged code area for action  InstagramApiYaml.getgeographiesByGeo_idMediaRecent
            Failure(???)
            // ----- End of unmanaged code area for action  InstagramApiYaml.getgeographiesByGeo_idMediaRecent
        }
        val getmediaByShortcode = getmediaByShortcodeAction { (shortcode: String) =>  
            // ----- Start of unmanaged code area for action  InstagramApiYaml.getmediaByShortcode
            Failure(???)
            // ----- End of unmanaged code area for action  InstagramApiYaml.getmediaByShortcode
        }
        val getlocationsSearch = getlocationsSearchAction { input: (MediaId, MediaId, MediaId, LocationLatitude, MediaId, LocationLatitude) =>
            val (foursquare_v2_id, facebook_places_id, distance, lat, foursquare_id, lng) = input
            // ----- Start of unmanaged code area for action  InstagramApiYaml.getlocationsSearch
            Failure(???)
            // ----- End of unmanaged code area for action  InstagramApiYaml.getlocationsSearch
        }
        val getusersSelfRequested_by = getusersSelfRequested_byAction {  _ =>  
            // ----- Start of unmanaged code area for action  InstagramApiYaml.getusersSelfRequested_by
            Failure(???)
            // ----- End of unmanaged code area for action  InstagramApiYaml.getusersSelfRequested_by
        }
        val getmediaByMedia_id = getmediaByMedia_idAction { (media_id: BigInt) =>  
            // ----- Start of unmanaged code area for action  InstagramApiYaml.getmediaByMedia_id
            Failure(???)
            // ----- End of unmanaged code area for action  InstagramApiYaml.getmediaByMedia_id
        }
        val getlocationsByLocation_idMediaRecent = getlocationsByLocation_idMediaRecentAction { input: (BigInt, MediaId, MediaId, MediaFilter, MediaFilter) =>
            val (location_id, max_timestamp, min_timestamp, min_id, max_id) = input
            // ----- Start of unmanaged code area for action  InstagramApiYaml.getlocationsByLocation_idMediaRecent
            Failure(???)
            // ----- End of unmanaged code area for action  InstagramApiYaml.getlocationsByLocation_idMediaRecent
        }
        val getusersByUser_idMediaRecent = getusersByUser_idMediaRecentAction { input: (BigDecimal, MediaId, MediaFilter, MediaId, MediaFilter, MediaId) =>
            val (user_id, max_timestamp, min_id, min_timestamp, max_id, count) = input
            // ----- Start of unmanaged code area for action  InstagramApiYaml.getusersByUser_idMediaRecent
            Failure(???)
            // ----- End of unmanaged code area for action  InstagramApiYaml.getusersByUser_idMediaRecent
        }
        val getmediaPopular = getmediaPopularAction {  _ =>  
            // ----- Start of unmanaged code area for action  InstagramApiYaml.getmediaPopular
            Failure(???)
            // ----- End of unmanaged code area for action  InstagramApiYaml.getmediaPopular
        }
    
    }
}
