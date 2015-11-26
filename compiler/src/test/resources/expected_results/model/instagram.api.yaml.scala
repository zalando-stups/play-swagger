package instagram.api.yaml
object parameters {
    type `User-id-paramUser-id` = Double
    type `Tag-nameTag-name` = String
    }
object definitions {
    type LikeUser_name = Option[String]
    type MediaUser = Option[MiniProfile]
    type MediaVideosLow_resolution = Option[Image]
    type LocationLongitude = Option[Double]
    type MediaUsers_in_photoOpt = scala.collection.Seq[MiniProfile]
    type MediaImages = Option[MediaImagesOpt]
    type MediaTagsOpt = scala.collection.Seq[Tag]
    type MediaLikes = Option[MediaLikesOpt]
    type MediaVideos = Option[MediaVideosOpt]
    type MediaCreated_time = Option[Int]
    type MediaLocation = Option[Location]
    type MediaTags = Option[MediaTagsOpt]
    type `MediaComments:DataOpt` = scala.collection.Seq[Comment]
    type `MediaComments:` = Option[`MediaComments:Opt`]
    type MediaUsers_in_photo = Option[MediaUsers_in_photoOpt]
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
    type `LocationsLocation-idLocation-id` = Int
    type UsersSelfFeedGetResponses200 = Option[`UsersUser-idMediaRecentGetResponses200Opt`]
    type `LocationsLocation-idMediaRecentGetResponses200Data` = Option[`LocationsLocation-idMediaRecentGetResponses200DataOpt`]
    type `MediaMedia-idCommentsDeleteResponses200Meta` = Option[`MediaMedia-idCommentsPostResponses200MetaOpt`]
    type `LocationsLocation-idMediaRecentGetResponses200DataOpt` = scala.collection.Seq[Media]
    type `MediaMedia-idLikesGetResponses200Data` = Option[`MediaMedia-idLikesGetResponses200DataOpt`]
    type `MediaMedia-idCommentsDeleteResponses200` = Option[`MediaMedia-idLikesPostResponses200Opt`]
    type `UsersUser-idFollowsGetResponses200` = Option[`UsersUser-idRelationshipPostResponses200Opt`]
    type `TagsTag-nameMediaRecentGetResponses200` = Option[`TagsTag-nameMediaRecentGetResponses200Opt`]
    type `UsersSelfRequested-byGetResponses200` = Option[`UsersSelfRequested-byGetResponses200Opt`]
    type `LocationsLocation-idGetResponses200` = Option[`LocationsLocation-idGetResponses200Opt`]
    type `MediaMedia-idLikesGetResponses200DataOpt` = scala.collection.Seq[Like]
    type LocationsSearchGetResponses200Data = Option[LocationsSearchGetResponses200DataOpt]
    type `TagsTag-nameGetResponses200` = Option[Tag]
    type MediaSearchGetResponses200DataOpt = scala.collection.Seq[MediaSearchGetResponses200DataOptArr]
    type `MediaMedia-idLikesGetResponses200` = Option[`MediaMedia-idLikesGetResponses200Opt`]
    type LocationsSearchGetResponses200DataOpt = scala.collection.Seq[Location]
    type MediaSearchGetResponses200 = Option[MediaSearchGetResponses200Opt]
    type MediaSearchGetResponses200Data = Option[MediaSearchGetResponses200DataOpt]
    type TagsSearchGetResponses200 = Option[TagsSearchGetResponses200Opt]
    type `MediaMedia-idGetResponses200` = Option[Media]
    type `UsersUser-idGetResponses200Data` = Option[User]
    type `UsersUser-idGetResponses200` = Option[`UsersUser-idGetResponses200Opt`]
    type `MediaMedia-idCommentsGetResponses200` = Option[`MediaMedia-idCommentsGetResponses200Opt`]
    type `GeographiesGeo-idMediaRecentGetResponses200` = Null
    type LocationsSearchGetResponses200 = Option[LocationsSearchGetResponses200Opt]
    case class `MediaMedia-idLikesGetResponses200Opt`(meta: `MediaMedia-idCommentsDeleteResponses200Meta`, data: `MediaMedia-idLikesGetResponses200Data`) 
    case class `UsersSelfRequested-byGetResponses200Opt`(meta: TagsSearchGetResponses200Meta, data: MediaUsers_in_photo) 
    case class `UsersUser-idRelationshipPostResponses200Opt`(data: MediaUsers_in_photo) 
    case class `UsersUser-idMediaRecentGetResponses200Opt`(data: `LocationsLocation-idMediaRecentGetResponses200Data`) 
    case class TagsSearchGetResponses200Opt(meta: TagsSearchGetResponses200Meta, data: MediaTags) 
    case class `TagsTag-nameMediaRecentGetResponses200Opt`(data: MediaTags) 
    case class MediaSearchGetResponses200Opt(data: MediaSearchGetResponses200Data) 
    case class `MediaMedia-idCommentsPostResponses200MetaOpt`(code: LocationLongitude) 
    case class `UsersSelfRequested-byGetResponses200MetaOpt`(code: MediaCreated_time) 
    case class MediaSearchGetResponses200DataOptArr(location: MediaLocation, created_time: MediaCreated_time, `comments:`: `MediaComments:`, tags: MediaTags, users_in_photo: MediaUsers_in_photo, filter: LikeUser_name, likes: MediaLikes, id: MediaCreated_time, videos: MediaVideos, `type`: LikeUser_name, images: MediaImages, user: MediaUser, distance: LocationLongitude) 
    case class `MediaMedia-idLikesPostResponses200Opt`(meta: `MediaMedia-idCommentsDeleteResponses200Meta`, data: LikeUser_name) 
    case class LocationsSearchGetResponses200Opt(data: LocationsSearchGetResponses200Data) 
    case class `LocationsLocation-idGetResponses200Opt`(data: MediaLocation) 
    case class `UsersUser-idGetResponses200Opt`(data: `UsersUser-idGetResponses200Data`) 
    case class `MediaMedia-idCommentsGetResponses200Opt`(meta: `MediaMedia-idCommentsDeleteResponses200Meta`, data: `MediaComments:Data`) 
    }
