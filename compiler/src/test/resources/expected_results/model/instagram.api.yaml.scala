package instagram.api
package object yaml {
import de.zalando.play.controllers.ArrayWrapper
import de.zalando.play.controllers.PlayPathBindables
type TagsSearchGetResponses200Meta = Option[`UsersSelfRequested-byGetResponses200MetaOpt`]

    type `LocationsLocation-idLocation-id` = Int

    type LikeUser_name = Option[String]

    type `MediaMedia-idCommentsDeleteResponses200Meta` = Option[`MediaMedia-idLikesGetResponses200MetaOpt`]

    type UsersSelfFeedGetResponses200Data = Option[UsersSelfFeedGetResponses200DataOpt]

    type `MediaMedia-idLikesGetResponses200Data` = Option[`MediaMedia-idLikesGetResponses200DataOpt`]

    type MediaUser = Option[MiniProfile]

    type MediaVideosLow_resolution = Option[Image]

    type LocationLongitude = Option[Double]

    type MediaUsers_in_photoOpt = ArrayWrapper[MiniProfile]

    type MediaImages = Option[MediaImagesOpt]

    type MediaTagsOpt = ArrayWrapper[Tag]

    type MediaLikes = Option[MediaLikesOpt]

    type MediaVideos = Option[MediaVideosOpt]

    type MediaCreated_time = Option[Int]

    type MediaLocation = Option[Location]

    type `MediaMedia-idLikesGetResponses200DataOpt` = ArrayWrapper[Like]

    type LocationsSearchGetResponses200Data = Option[LocationsSearchGetResponses200DataOpt]

    type MediaSearchGetResponses200DataOpt = ArrayWrapper[MediaSearchGetResponses200DataOptArr]

    type MediaTags = Option[MediaTagsOpt]

    type LocationsSearchGetResponses200DataOpt = ArrayWrapper[Location]

    type MediaSearchGetResponses200Data = Option[MediaSearchGetResponses200DataOpt]

    type UsersSelfFeedGetResponses200DataOpt = ArrayWrapper[Media]

    type `MediaComments:DataOpt` = ArrayWrapper[Comment]

    type `UsersUser-idGetResponses200Data` = Option[User]

    type `MediaComments:` = Option[`MediaComments:Opt`]

    type MediaUsers_in_photo = Option[MediaUsers_in_photoOpt]

    type `GeographiesGeo-idMediaRecentGetResponses200` = Null

    type UserCounts = Option[UserCountsOpt]

    type `MediaComments:Data` = Option[`MediaComments:DataOpt`]

    type `User-id-paramUser-id` = Double

    type `Tag-nameTag-name` = String

    case class UsersSelfFeedGetResponses200(data: UsersSelfFeedGetResponses200Data) 

    case class `MediaMedia-idCommentsDeleteResponses200`(meta: `MediaMedia-idCommentsDeleteResponses200Meta`, data: LikeUser_name) 

    case class `UsersUser-idFollowsGetResponses200`(data: MediaUsers_in_photo) 

    case class User(website: LikeUser_name, profile_picture: LikeUser_name, username: LikeUser_name, full_name: LikeUser_name, bio: LikeUser_name, id: MediaCreated_time, counts: UserCounts) 

    case class `TagsTag-nameMediaRecentGetResponses200`(data: MediaTags) 

    case class Image(width: MediaCreated_time, height: MediaCreated_time, url: LikeUser_name) 

    case class `UsersSelfRequested-byGetResponses200`(meta: TagsSearchGetResponses200Meta, data: MediaUsers_in_photo) 

    case class Tag(media_count: MediaCreated_time, name: LikeUser_name) 

    case class `UsersSelfRequested-byGetResponses200MetaOpt`(code: MediaCreated_time) 

    case class MediaSearchGetResponses200DataOptArr(location: MediaLocation, created_time: MediaCreated_time, `comments:`: `MediaComments:`, tags: MediaTags, users_in_photo: MediaUsers_in_photo, filter: LikeUser_name, likes: MediaLikes, id: MediaCreated_time, videos: MediaVideos, `type`: LikeUser_name, images: MediaImages, user: MediaUser, distance: LocationLongitude) 

    case class `LocationsLocation-idGetResponses200`(data: MediaLocation) 

    case class Comment(id: LikeUser_name, created_time: LikeUser_name, text: LikeUser_name, from: MediaUser) 

    case class Media(location: MediaLocation, created_time: MediaCreated_time, `comments:`: `MediaComments:`, tags: MediaTags, users_in_photo: MediaUsers_in_photo, filter: LikeUser_name, likes: MediaLikes, id: MediaCreated_time, videos: MediaVideos, `type`: LikeUser_name, images: MediaImages, user: MediaUser) 

    case class MediaVideosOpt(low_resolution: MediaVideosLow_resolution, standard_resolution: MediaVideosLow_resolution) 

    case class `MediaMedia-idLikesGetResponses200`(meta: `MediaMedia-idCommentsDeleteResponses200Meta`, data: `MediaMedia-idLikesGetResponses200Data`) 

    case class `MediaMedia-idLikesGetResponses200MetaOpt`(code: LocationLongitude) 

    case class MediaSearchGetResponses200(data: MediaSearchGetResponses200Data) 

    case class TagsSearchGetResponses200(meta: TagsSearchGetResponses200Meta, data: MediaTags) 

    case class Like(first_name: LikeUser_name, id: LikeUser_name, last_name: LikeUser_name, `type`: LikeUser_name, user_name: LikeUser_name) 

    case class MediaLikesOpt(count: MediaCreated_time, data: MediaUsers_in_photo) 

    case class `UsersUser-idGetResponses200`(data: `UsersUser-idGetResponses200Data`) 

    case class `MediaMedia-idCommentsGetResponses200`(meta: `MediaMedia-idCommentsDeleteResponses200Meta`, data: `MediaComments:Data`) 

    case class Location(id: LikeUser_name, name: LikeUser_name, latitude: LocationLongitude, longitude: LocationLongitude) 

    case class MiniProfile(user_name: LikeUser_name, full_name: LikeUser_name, id: MediaCreated_time, profile_picture: LikeUser_name) 

    case class MediaImagesOpt(low_resolution: MediaVideosLow_resolution, thumbnail: MediaVideosLow_resolution, standard_resolution: MediaVideosLow_resolution) 

    case class UserCountsOpt(media: MediaCreated_time, follows: MediaCreated_time, follwed_by: MediaCreated_time) 

    case class `MediaComments:Opt`(count: MediaCreated_time, data: `MediaComments:Data`) 

    case class LocationsSearchGetResponses200(data: LocationsSearchGetResponses200Data) 

    


    
    
    implicit val bindable_OptionIntQuery = PlayPathBindables.createOptionQueryBindable[Int]
    implicit val bindable_OptionDoubleQuery = PlayPathBindables.createOptionQueryBindable[Double]
    implicit val bindable_OptionStringQuery = PlayPathBindables.createOptionQueryBindable[String]
    }
