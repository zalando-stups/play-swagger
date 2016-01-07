package instagram.api
package object yaml {
import de.zalando.play.controllers.PlayPathBindables
type TagsSearchGetResponses200Meta = Option[`UsersSelfRequested-byGetResponses200MetaOpt`]

    type `LocationsLocation-idLocation-id` = Int

    type `MediaMedia-idGetResponses200VideosStandard_resolution` = Option[Image]

    type MediaFilter = Option[String]

    type `MediaMedia-idCommentsDeleteResponses200Meta` = Option[`MediaMedia-idLikesGetResponses200MetaOpt`]

    type UsersSelfFeedGetResponses200Data = Option[UsersSelfFeedGetResponses200DataOpt]

    type MediaTags = Option[MediaTagsOpt]

    type `MediaMedia-idLikesGetResponses200Data` = Option[`MediaMedia-idLikesGetResponses200DataOpt`]

    type MediaId = Option[Int]

    type MediaTagsOpt = Seq[Tag]

    type MediaImages = Option[MediaImagesOpt]

    type MediaLikes = Option[MediaLikesOpt]

    type `MediaMedia-idCommentsGetResponses200DataOpt` = Seq[Comment]

    type MediaUsers_in_photoOpt = Seq[MiniProfile]

    type `MediaMedia-idLikesGetResponses200DataOpt` = Seq[Like]

    type LocationsSearchGetResponses200Data = Option[LocationsSearchGetResponses200DataOpt]

    type `MediaComments:` = Option[`MediaComments:Opt`]

    type MediaSearchGetResponses200DataOpt = Seq[MediaSearchGetResponses200DataOptArrResult]

    type CommentFrom = Option[MiniProfile]

    type LocationsSearchGetResponses200DataOpt = Seq[Location]

    type MediaSearchGetResponses200Data = Option[MediaSearchGetResponses200DataOpt]

    type UsersSelfFeedGetResponses200DataOpt = Seq[Media]

    type `UsersUser-idGetResponses200Data` = Option[User]

    type MediaVideos = Option[MediaVideosOpt]

    type MediaLocation = Option[Location]

    type `GeographiesGeo-idMediaRecentGetResponses200` = Null

    type MediaUsers_in_photo = Option[MediaUsers_in_photoOpt]

    type LocationLatitude = Option[Double]

    type `MediaMedia-idCommentsGetResponses200Data` = Option[`MediaMedia-idCommentsGetResponses200DataOpt`]

    type `User-id-paramUser-id` = Double

    type UserCounts = Option[UserCountsOpt]

    type `Tag-nameTag-name` = String

    case class UsersSelfFeedGetResponses200(data: UsersSelfFeedGetResponses200Data) 

    case class `MediaMedia-idCommentsDeleteResponses200`(meta: `MediaMedia-idCommentsDeleteResponses200Meta`, data: MediaFilter) 

    case class MediaSearchGetResponses200DataOptArrResult(location: MediaLocation, created_time: MediaId, `comments:`: `MediaComments:`, tags: MediaTags, users_in_photo: MediaUsers_in_photo, filter: MediaFilter, likes: MediaLikes, id: MediaId, videos: MediaVideos, `type`: MediaFilter, images: MediaImages, user: CommentFrom, distance: LocationLatitude) 

    case class `UsersUser-idFollowsGetResponses200`(data: MediaUsers_in_photo) 

    case class UserCountsOpt(media: MediaId, follows: MediaId, follwed_by: MediaId) 

    case class User(website: MediaFilter, profile_picture: MediaFilter, username: MediaFilter, full_name: MediaFilter, bio: MediaFilter, id: MediaId, counts: UserCounts) 

    case class `TagsTag-nameMediaRecentGetResponses200`(data: MediaTags) 

    case class Image(width: MediaId, height: MediaId, url: MediaFilter) 

    case class `UsersSelfRequested-byGetResponses200`(meta: TagsSearchGetResponses200Meta, data: MediaUsers_in_photo) 

    case class Tag(media_count: MediaId, name: MediaFilter) 

    case class `UsersSelfRequested-byGetResponses200MetaOpt`(code: MediaId) 

    case class `LocationsLocation-idGetResponses200`(data: MediaLocation) 

    case class Comment(id: MediaFilter, created_time: MediaFilter, text: MediaFilter, from: CommentFrom) 

    case class Media(location: MediaLocation, created_time: MediaId, `comments:`: `MediaComments:`, tags: MediaTags, users_in_photo: MediaUsers_in_photo, filter: MediaFilter, likes: MediaLikes, id: MediaId, videos: MediaVideos, `type`: MediaFilter, images: MediaImages, user: CommentFrom) 

    case class `MediaMedia-idLikesGetResponses200`(meta: `MediaMedia-idCommentsDeleteResponses200Meta`, data: `MediaMedia-idLikesGetResponses200Data`) 

    case class `MediaMedia-idLikesGetResponses200MetaOpt`(code: LocationLatitude) 

    case class MediaSearchGetResponses200(data: MediaSearchGetResponses200Data) 

    case class TagsSearchGetResponses200(meta: TagsSearchGetResponses200Meta, data: MediaTags) 

    case class Like(first_name: MediaFilter, id: MediaFilter, last_name: MediaFilter, `type`: MediaFilter, user_name: MediaFilter) 

    case class `MediaComments:Opt`(count: MediaId, data: `MediaMedia-idCommentsGetResponses200Data`) 

    case class `UsersUser-idGetResponses200`(data: `UsersUser-idGetResponses200Data`) 

    case class `MediaMedia-idCommentsGetResponses200`(meta: `MediaMedia-idCommentsDeleteResponses200Meta`, data: `MediaMedia-idCommentsGetResponses200Data`) 

    case class MediaVideosOpt(low_resolution: `MediaMedia-idGetResponses200VideosStandard_resolution`, standard_resolution: `MediaMedia-idGetResponses200VideosStandard_resolution`) 

    case class Location(id: MediaFilter, name: MediaFilter, latitude: LocationLatitude, longitude: LocationLatitude) 

    case class MiniProfile(user_name: MediaFilter, full_name: MediaFilter, id: MediaId, profile_picture: MediaFilter) 

    case class MediaLikesOpt(count: MediaId, data: MediaUsers_in_photo) 

    case class LocationsSearchGetResponses200(data: LocationsSearchGetResponses200Data) 

    case class MediaImagesOpt(low_resolution: `MediaMedia-idGetResponses200VideosStandard_resolution`, thumbnail: `MediaMedia-idGetResponses200VideosStandard_resolution`, standard_resolution: `MediaMedia-idGetResponses200VideosStandard_resolution`) 

    


    
    
    implicit val bindable_OptionIntQuery = PlayPathBindables.createOptionQueryBindable[Int]
    implicit val bindable_OptionDoubleQuery = PlayPathBindables.createOptionQueryBindable[Double]
    implicit val bindable_OptionStringQuery = PlayPathBindables.createOptionQueryBindable[String]
    }
