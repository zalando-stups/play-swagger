package instagram.api.yaml
object parameters {
    }
object definitions {
    import paths._
    type LikeUser_name = Option[String]
    type MediaUser = Option[MiniProfile]
    type MediaVideosLow_resolution = Option[Image]
    type LocationLongitude = Option[Double]
    type MediaImages = Option[MediaImagesOpt]
    type MediaTagsOpt = scala.collection.Seq[Tag]
    type MediaLikes = Option[MediaLikesOpt]
    type MediaVideos = Option[MediaVideosOpt]
    type MediaCreated_time = Option[Int]
    type MediaLocation = Option[Location]
    type MediaTags = Option[MediaTagsOpt]
    type `MediaComments:DataOpt` = scala.collection.Seq[Comment]
    type `MediaComments:` = Option[`MediaComments:Opt`]
    type MediaUsers_in_photo = Option[MediaUser]
    type UserCounts = Option[UserCountsOpt]
    type `MediaComments:Data` = Option[`MediaComments:DataOpt`]
    case class User(website: LikeUser_name, profile_picture: LikeUser_name, username: LikeUser_name, full_name: LikeUser_name, bio: LikeUser_name, id: MediaCreated_time, counts: UserCounts) 
    case class Image(width: MediaCreated_time, height: MediaCreated_time, url: LikeUser_name) 
    case class Tag(media_count: MediaCreated_time, name: LikeUser_name) 
    case class Comment(id: LikeUser_name, created_time: LikeUser_name, text: LikeUser_name, from: MediaUser) 
    case class Media(location: MediaLocation, created_time: MediaCreated_time, `comments:`: `MediaComments:`, tags: MediaTags, users_in_photo: MediaUsers_in_photo, filter: LikeUser_name, likes: MediaLikes, id: MediaCreated_time, videos: MediaVideos, `type`: LikeUser_name, images: MediaImages, user: MediaUser) 
    case class MediaVideosOpt(low_resolution: MediaVideosLow_resolution, standard_resolution: MediaVideosLow_resolution) 
    case class Like(first_name: LikeUser_name, id: LikeUser_name, last_name: LikeUser_name, `type`: LikeUser_name, user_name: LikeUser_name) 
    case class MediaLikesOpt(count: MediaCreated_time, data: MediaUsers_in_photo) 
    case class Location(id: LikeUser_name, name: LikeUser_name, latitude: LocationLongitude, longitude: LocationLongitude) 
    case class MiniProfile(user_name: LikeUser_name, full_name: LikeUser_name, id: MediaCreated_time, profile_picture: LikeUser_name) 
    case class MediaImagesOpt(low_resolution: MediaVideosLow_resolution, thumbnail: MediaVideosLow_resolution, standard_resolution: MediaVideosLow_resolution) 
    case class UserCountsOpt(media: MediaCreated_time, follows: MediaCreated_time, follwed_by: MediaCreated_time) 
    case class `MediaComments:Opt`(count: MediaCreated_time, data: `MediaComments:Data`) 
    }
object paths {
    import definitions._
    type TagsSearchGetResponses200Meta = Option[`UsersSelfRequested-byGetResponses200MetaOpt`]
    type UsersSelfFeedGetResponses200 = Option[`UsersUser-iMediaRecentGetResponses200Opt`]
    type `LocationsLocation-iMediaRecentGetResponses200Data` = Option[`MediaMedia-iGetResponses200`]
    type `MediaMedia-iCommentsDeleteResponses200Meta` = Option[`MediaMedia-iCommentsPostResponses200MetaOpt`]
    type `MediaMedia-iLikesGetResponses200Data` = Option[`MediaMedia-iLikesGetResponses200DataOpt`]
    type `MediaMedia-iCommentsDeleteResponses200` = Option[`MediaMedia-iLikesPostResponses200Opt`]
    type `UsersUser-iFollowsGetResponses200` = Option[`UsersUser-iRelationshipPostResponses200Opt`]
    type `TagsTag-namMediaRecentGetResponses200` = Option[`TagsTag-namMediaRecentGetResponses200Opt`]
    type `UsersSelfRequested-byGetResponses200` = Option[`UsersSelfRequested-byGetResponses200Opt`]
    type `LocationsLocation-iGetResponses200` = Option[`LocationsLocation-iGetResponses200Opt`]
    type `MediaMedia-iLikesGetResponses200DataOpt` = scala.collection.Seq[Like]
    type LocationsSearchGetResponses200Data = Option[MediaLocation]
    type MediaSearchGetResponses200DataOpt = scala.collection.Seq[MediaSearchGetResponses200DataOptArr]
    type `MediaMedia-iLikesGetResponses200` = Option[`MediaMedia-iLikesGetResponses200Opt`]
    type MediaSearchGetResponses200 = Option[MediaSearchGetResponses200Opt]
    type MediaSearchGetResponses200Data = Option[MediaSearchGetResponses200DataOpt]
    type TagsSearchGetResponses200 = Option[TagsSearchGetResponses200Opt]
    type `MediaMedia-iGetResponses200` = Option[Media]
    type `UsersUser-iGetResponses200Data` = Option[User]
    type `UsersUser-iGetResponses200` = Option[`UsersUser-iGetResponses200Opt`]
    type `MediaMedia-iCommentsGetResponses200` = Option[`MediaMedia-iCommentsGetResponses200Opt`]
    type LocationsSearchGetResponses200 = Option[LocationsSearchGetResponses200Opt]
    case class `MediaMedia-iLikesGetResponses200Opt`(meta: `MediaMedia-iCommentsDeleteResponses200Meta`, data: `MediaMedia-iLikesGetResponses200Data`) 
    case class `UsersSelfRequested-byGetResponses200Opt`(meta: TagsSearchGetResponses200Meta, data: MediaUsers_in_photo) 
    case class `UsersUser-iRelationshipPostResponses200Opt`(data: MediaUsers_in_photo) 
    case class `UsersUser-iMediaRecentGetResponses200Opt`(data: `LocationsLocation-iMediaRecentGetResponses200Data`) 
    case class TagsSearchGetResponses200Opt(meta: TagsSearchGetResponses200Meta, data: MediaTags) 
    case class `TagsTag-namMediaRecentGetResponses200Opt`(data: MediaTags) 
    case class MediaSearchGetResponses200Opt(data: MediaSearchGetResponses200Data) 
    case class `MediaMedia-iCommentsPostResponses200MetaOpt`(code: LocationLongitude) 
    case class `UsersSelfRequested-byGetResponses200MetaOpt`(code: MediaCreated_time) 
    case class MediaSearchGetResponses200DataOptArr(location: MediaLocation, created_time: MediaCreated_time, `comments:`: `MediaComments:`, tags: MediaTags, users_in_photo: MediaUsers_in_photo, filter: LikeUser_name, likes: MediaLikes, id: MediaCreated_time, videos: MediaVideos, `type`: LikeUser_name, images: MediaImages, user: MediaUser, distance: LocationLongitude) 
    case class `MediaMedia-iLikesPostResponses200Opt`(meta: `MediaMedia-iCommentsDeleteResponses200Meta`, data: LikeUser_name) 
    case class LocationsSearchGetResponses200Opt(data: LocationsSearchGetResponses200Data) 
    case class `LocationsLocation-iGetResponses200Opt`(data: MediaLocation) 
    case class `UsersUser-iGetResponses200Opt`(data: `UsersUser-iGetResponses200Data`) 
    case class `MediaMedia-iCommentsGetResponses200Opt`(meta: `MediaMedia-iCommentsDeleteResponses200Meta`, data: `MediaComments:Data`) 
    }
