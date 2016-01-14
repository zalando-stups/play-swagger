



import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._
package instagram.api.yaml { 

  class InstagramApiYaml extends InstagramApiYamlBase {
    val getmediaByMedia_idLikes = getmediaByMedia_idLikesAction { (`media-id`: Int) =>
      ???
    } //////// EOF ////////  getmediaByMedia_idLikesAction
    val postmediaByMedia_idLikes = postmediaByMedia_idLikesAction { (`media-id`: Int) =>
      ???
    } //////// EOF ////////  postmediaByMedia_idLikesAction
    val deletemediaByMedia_idLikes = deletemediaByMedia_idLikesAction { (`media-id`: Int) =>
      ???
    } //////// EOF ////////  deletemediaByMedia_idLikesAction
    val getusersByUser_idFollows = getusersByUser_idFollowsAction { (`user-id`: Double) =>
      ???
    } //////// EOF ////////  getusersByUser_idFollowsAction
    val getlocationsByLocation_id = getlocationsByLocation_idAction { (`location-id`: Int) =>
      ???
    } //////// EOF ////////  getlocationsByLocation_idAction
    val getusersSearch = getusersSearchAction { input: (String, MediaFilter) =>
      val (q, count) = input
      ???
    } //////// EOF ////////  getusersSearchAction
    val getusersSelfMediaLiked = getusersSelfMediaLikedAction { input: (MediaId, MediaId) =>
      val (count, max_like_id) = input
      ???
    } //////// EOF ////////  getusersSelfMediaLikedAction
    val gettagsByTag_name = gettagsByTag_nameAction { (`tag-name`: String) =>
      ???
    } //////// EOF ////////  gettagsByTag_nameAction
    val gettagsSearch = gettagsSearchAction { (q: MediaFilter) =>
      ???
    } //////// EOF ////////  gettagsSearchAction
    val getusersByUser_idFollowed_by = getusersByUser_idFollowed_byAction { (`user-id`: Double) =>
      ???
    } //////// EOF ////////  getusersByUser_idFollowed_byAction
    val getmediaByMedia_idComments = getmediaByMedia_idCommentsAction { (`media-id`: Int) =>
      ???
    } //////// EOF ////////  getmediaByMedia_idCommentsAction
    val postmediaByMedia_idComments = postmediaByMedia_idCommentsAction { input: (Int, LocationLatitude) =>
      val (`media-id`, tEXT) = input
      ???
    } //////// EOF ////////  postmediaByMedia_idCommentsAction
    val deletemediaByMedia_idComments = deletemediaByMedia_idCommentsAction { (`media-id`: Int) =>
      ???
    } //////// EOF ////////  deletemediaByMedia_idCommentsAction
    val gettagsByTag_nameMediaRecent = gettagsByTag_nameMediaRecentAction { (`tag-name`: String) =>
      ???
    } //////// EOF ////////  gettagsByTag_nameMediaRecentAction
    val postusersByUser_idRelationship = postusersByUser_idRelationshipAction { input: (Double, MediaFilter) =>
      val (`user-id`, action) = input
      ???
    } //////// EOF ////////  postusersByUser_idRelationshipAction
    val getusersSelfFeed = getusersSelfFeedAction { input: (MediaId, MediaId, MediaId) =>
      val (count, max_id, min_id) = input
      ???
    } //////// EOF ////////  getusersSelfFeedAction
    val getusersByUser_id = getusersByUser_idAction { (`user-id`: Double) =>
      ???
    } //////// EOF ////////  getusersByUser_idAction
    val getmediaSearch = getmediaSearchAction { input: (MediaId, MediaId, LocationLatitude, MediaId, LocationLatitude) =>
      val (mAX_TIMESTAMP, dISTANCE, lNG, mIN_TIMESTAMP, lAT) = input
      ???
    } //////// EOF ////////  getmediaSearchAction
    val getgeographiesByGeo_idMediaRecent = getgeographiesByGeo_idMediaRecentAction { input: (Int, MediaId, MediaId) =>
      val (`geo-id`, count, min_id) = input
      ???
    } //////// EOF ////////  getgeographiesByGeo_idMediaRecentAction
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
    val getmediaByMedia_id = getmediaByMedia_idAction { (`media-id`: Int) =>
      ???
    } //////// EOF ////////  getmediaByMedia_idAction
    val getlocationsByLocation_idMediaRecent = getlocationsByLocation_idMediaRecentAction { input: (Int, MediaId, MediaId, MediaFilter, MediaFilter) =>
      val (`location-id`, max_timestamp, min_timestamp, min_id, max_id) = input
      ???
    } //////// EOF ////////  getlocationsByLocation_idMediaRecentAction
    val getusersByUser_idMediaRecent = getusersByUser_idMediaRecentAction { input: (Double, MediaId, MediaFilter, MediaId, MediaFilter, MediaId) =>
      val (`user-id`, max_timestamp, min_id, min_timestamp, max_id, count) = input
      ???
    } //////// EOF ////////  getusersByUser_idMediaRecentAction
    val getmediaPopular = getmediaPopularAction {
      ???
    } //////// EOF ////////  getmediaPopularAction
  }
}
