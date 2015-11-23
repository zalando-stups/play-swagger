package instagram.api.yaml
import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._

object parametersValidator {
    import parameters.`User-id-paramUser-id`
    import parameters.`Tag-nameTag-name`
    class `User-id-paramUser-idConstraints`(override val instance: Double) extends ValidationBase[Double] {
        override def constraints: Seq[Constraint[Double]] =
        Seq()
    }
    class `User-id-paramUser-idValidator`(override val instance: Double) extends RecursiveValidator[Double] {
      override val validators = Seq(new `User-id-paramUser-idConstraints`(instance))
    }
    class `Tag-nameTag-nameConstraints`(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class `Tag-nameTag-nameValidator`(override val instance: String) extends RecursiveValidator[String] {
      override val validators = Seq(new `Tag-nameTag-nameConstraints`(instance))
    }
    }
object definitionsValidator {
    import definitions._
    class LikeUser_nameValidator(override val instance: LikeUser_name) extends RecursiveValidator[LikeUser_name] {
        override val validators = Seq(
            )
        }
    class MediaUserValidator(override val instance: MediaUser) extends RecursiveValidator[MediaUser] {
        override val validators = Seq(
            )
        }
    class MediaVideosLow_resolutionValidator(override val instance: MediaVideosLow_resolution) extends RecursiveValidator[MediaVideosLow_resolution] {
        override val validators = Seq(
            )
        }
    class LocationLongitudeValidator(override val instance: LocationLongitude) extends RecursiveValidator[LocationLongitude] {
        override val validators = Seq(
            )
        }
    class MediaImagesValidator(override val instance: MediaImages) extends RecursiveValidator[MediaImages] {
        override val validators = Seq(
            )
        }
    class UserValidator(override val instance: User) extends RecursiveValidator[User] {
        override val validators = Seq(
            new LikeUser_nameValidator(instance.website), 
            new LikeUser_nameValidator(instance.profile_picture), 
            new LikeUser_nameValidator(instance.username), 
            new LikeUser_nameValidator(instance.full_name), 
            new LikeUser_nameValidator(instance.bio), 
            new MediaCreated_timeValidator(instance.id), 
            new UserCountsValidator(instance.counts)
            )
        }
    class ImageValidator(override val instance: Image) extends RecursiveValidator[Image] {
        override val validators = Seq(
            new MediaCreated_timeValidator(instance.width), 
            new MediaCreated_timeValidator(instance.height), 
            new LikeUser_nameValidator(instance.url)
            )
        }
    class TagValidator(override val instance: Tag) extends RecursiveValidator[Tag] {
        override val validators = Seq(
            new MediaCreated_timeValidator(instance.media_count), 
            new LikeUser_nameValidator(instance.name)
            )
        }
    class MediaTagsOptValidator(override val instance: MediaTagsOpt) extends RecursiveValidator[MediaTagsOpt] {
        override val validators = Seq(
            )
        }
    class MediaLikesValidator(override val instance: MediaLikes) extends RecursiveValidator[MediaLikes] {
        override val validators = Seq(
            )
        }
    class MediaVideosValidator(override val instance: MediaVideos) extends RecursiveValidator[MediaVideos] {
        override val validators = Seq(
            )
        }
    class CommentValidator(override val instance: Comment) extends RecursiveValidator[Comment] {
        override val validators = Seq(
            new LikeUser_nameValidator(instance.id), 
            new LikeUser_nameValidator(instance.created_time), 
            new LikeUser_nameValidator(instance.text), 
            new MediaUserValidator(instance.from)
            )
        }
    class MediaValidator(override val instance: Media) extends RecursiveValidator[Media] {
        override val validators = Seq(
            new MediaLocationValidator(instance.location), 
            new MediaCreated_timeValidator(instance.created_time), 
            new `MediaComments:Validator`(instance.`comments:`), 
            new MediaTagsValidator(instance.tags), 
            new MediaUsers_in_photoValidator(instance.users_in_photo), 
            new LikeUser_nameValidator(instance.filter), 
            new MediaLikesValidator(instance.likes), 
            new MediaCreated_timeValidator(instance.id), 
            new MediaVideosValidator(instance.videos), 
            new LikeUser_nameValidator(instance.`type`), 
            new MediaImagesValidator(instance.images), 
            new MediaUserValidator(instance.user)
            )
        }
    class MediaCreated_timeValidator(override val instance: MediaCreated_time) extends RecursiveValidator[MediaCreated_time] {
        override val validators = Seq(
            )
        }
    class MediaLocationValidator(override val instance: MediaLocation) extends RecursiveValidator[MediaLocation] {
        override val validators = Seq(
            )
        }
    class MediaVideosOptValidator(override val instance: MediaVideosOpt) extends RecursiveValidator[MediaVideosOpt] {
        override val validators = Seq(
            new MediaVideosLow_resolutionValidator(instance.low_resolution), 
            new MediaVideosLow_resolutionValidator(instance.standard_resolution)
            )
        }
    class MediaTagsValidator(override val instance: MediaTags) extends RecursiveValidator[MediaTags] {
        override val validators = Seq(
            )
        }
    class LikeValidator(override val instance: Like) extends RecursiveValidator[Like] {
        override val validators = Seq(
            new LikeUser_nameValidator(instance.first_name), 
            new LikeUser_nameValidator(instance.id), 
            new LikeUser_nameValidator(instance.last_name), 
            new LikeUser_nameValidator(instance.`type`), 
            new LikeUser_nameValidator(instance.user_name)
            )
        }
    class `MediaComments:DataOptValidator`(override val instance: `MediaComments:DataOpt`) extends RecursiveValidator[`MediaComments:DataOpt`] {
        override val validators = Seq(
            )
        }
    class `MediaComments:Validator`(override val instance: `MediaComments:`) extends RecursiveValidator[`MediaComments:`] {
        override val validators = Seq(
            )
        }
    class MediaLikesOptValidator(override val instance: MediaLikesOpt) extends RecursiveValidator[MediaLikesOpt] {
        override val validators = Seq(
            new MediaCreated_timeValidator(instance.count), 
            new MediaUsers_in_photoValidator(instance.data)
            )
        }
    class MediaUsers_in_photoValidator(override val instance: MediaUsers_in_photo) extends RecursiveValidator[MediaUsers_in_photo] {
        override val validators = Seq(
            )
        }
    class LocationValidator(override val instance: Location) extends RecursiveValidator[Location] {
        override val validators = Seq(
            new LikeUser_nameValidator(instance.id), 
            new LikeUser_nameValidator(instance.name), 
            new LocationLongitudeValidator(instance.latitude), 
            new LocationLongitudeValidator(instance.longitude)
            )
        }
    class MiniProfileValidator(override val instance: MiniProfile) extends RecursiveValidator[MiniProfile] {
        override val validators = Seq(
            new LikeUser_nameValidator(instance.user_name), 
            new LikeUser_nameValidator(instance.full_name), 
            new MediaCreated_timeValidator(instance.id), 
            new LikeUser_nameValidator(instance.profile_picture)
            )
        }
    class MediaImagesOptValidator(override val instance: MediaImagesOpt) extends RecursiveValidator[MediaImagesOpt] {
        override val validators = Seq(
            new MediaVideosLow_resolutionValidator(instance.low_resolution), 
            new MediaVideosLow_resolutionValidator(instance.thumbnail), 
            new MediaVideosLow_resolutionValidator(instance.standard_resolution)
            )
        }
    class UserCountsValidator(override val instance: UserCounts) extends RecursiveValidator[UserCounts] {
        override val validators = Seq(
            )
        }
    class `MediaComments:DataValidator`(override val instance: `MediaComments:Data`) extends RecursiveValidator[`MediaComments:Data`] {
        override val validators = Seq(
            )
        }
    class UserCountsOptValidator(override val instance: UserCountsOpt) extends RecursiveValidator[UserCountsOpt] {
        override val validators = Seq(
            new MediaCreated_timeValidator(instance.media), 
            new MediaCreated_timeValidator(instance.follows), 
            new MediaCreated_timeValidator(instance.follwed_by)
            )
        }
    class `MediaComments:OptValidator`(override val instance: `MediaComments:Opt`) extends RecursiveValidator[`MediaComments:Opt`] {
        override val validators = Seq(
            new MediaCreated_timeValidator(instance.count), 
            new `MediaComments:DataValidator`(instance.data)
            )
        }
    }
object pathsValidator {
    import definitions._
    import paths._
    import definitionsValidator._
    class `LocationsLocation-iLocation-idConstraints`(override val instance: Int) extends ValidationBase[Int] {
        override def constraints: Seq[Constraint[Int]] =
        Seq()
    }
    class `LocationsLocation-iLocation-idValidator`(override val instance: Int) extends RecursiveValidator[Int] {
      override val validators = Seq(new `LocationsLocation-iLocation-idConstraints`(instance))
    }
    class `GeographiesGeo-iMediaRecentGetResponses200Constraints`(override val instance: Null) extends ValidationBase[Null] {
        override def constraints: Seq[Constraint[Null]] =
        Seq()
    }
    class `GeographiesGeo-iMediaRecentGetResponses200Validator`(override val instance: Null) extends RecursiveValidator[Null] {
      override val validators = Seq(new `GeographiesGeo-iMediaRecentGetResponses200Constraints`(instance))
    }
    class TagsSearchGetResponses200MetaValidator(override val instance: TagsSearchGetResponses200Meta) extends RecursiveValidator[TagsSearchGetResponses200Meta] {
        override val validators = Seq(
            )
        }
    class `MediaMedia-iLikesGetResponses200OptValidator`(override val instance: `MediaMedia-iLikesGetResponses200Opt`) extends RecursiveValidator[`MediaMedia-iLikesGetResponses200Opt`] {
        override val validators = Seq(
            new `MediaMedia-iCommentsDeleteResponses200MetaValidator`(instance.meta), 
            new `MediaMedia-iLikesGetResponses200DataValidator`(instance.data)
            )
        }
    class `UsersSelfRequested-byGetResponses200OptValidator`(override val instance: `UsersSelfRequested-byGetResponses200Opt`) extends RecursiveValidator[`UsersSelfRequested-byGetResponses200Opt`] {
        override val validators = Seq(
            new TagsSearchGetResponses200MetaValidator(instance.meta), 
            new MediaUsers_in_photoValidator(instance.data)
            )
        }
    class UsersSelfFeedGetResponses200Validator(override val instance: UsersSelfFeedGetResponses200) extends RecursiveValidator[UsersSelfFeedGetResponses200] {
        override val validators = Seq(
            )
        }
    class `LocationsLocation-iMediaRecentGetResponses200DataValidator`(override val instance: `LocationsLocation-iMediaRecentGetResponses200Data`) extends RecursiveValidator[`LocationsLocation-iMediaRecentGetResponses200Data`] {
        override val validators = Seq(
            )
        }
    class `UsersUser-iRelationshipPostResponses200OptValidator`(override val instance: `UsersUser-iRelationshipPostResponses200Opt`) extends RecursiveValidator[`UsersUser-iRelationshipPostResponses200Opt`] {
        override val validators = Seq(
            new MediaUsers_in_photoValidator(instance.data)
            )
        }
    class `UsersUser-iMediaRecentGetResponses200OptValidator`(override val instance: `UsersUser-iMediaRecentGetResponses200Opt`) extends RecursiveValidator[`UsersUser-iMediaRecentGetResponses200Opt`] {
        override val validators = Seq(
            new `LocationsLocation-iMediaRecentGetResponses200DataValidator`(instance.data)
            )
        }
    class `MediaMedia-iCommentsDeleteResponses200MetaValidator`(override val instance: `MediaMedia-iCommentsDeleteResponses200Meta`) extends RecursiveValidator[`MediaMedia-iCommentsDeleteResponses200Meta`] {
        override val validators = Seq(
            )
        }
    class TagsSearchGetResponses200OptValidator(override val instance: TagsSearchGetResponses200Opt) extends RecursiveValidator[TagsSearchGetResponses200Opt] {
        override val validators = Seq(
            new TagsSearchGetResponses200MetaValidator(instance.meta), 
            new MediaTagsValidator(instance.data)
            )
        }
    class `MediaMedia-iLikesGetResponses200DataValidator`(override val instance: `MediaMedia-iLikesGetResponses200Data`) extends RecursiveValidator[`MediaMedia-iLikesGetResponses200Data`] {
        override val validators = Seq(
            )
        }
    class `MediaMedia-iCommentsDeleteResponses200Validator`(override val instance: `MediaMedia-iCommentsDeleteResponses200`) extends RecursiveValidator[`MediaMedia-iCommentsDeleteResponses200`] {
        override val validators = Seq(
            )
        }
    class `TagsTag-namMediaRecentGetResponses200OptValidator`(override val instance: `TagsTag-namMediaRecentGetResponses200Opt`) extends RecursiveValidator[`TagsTag-namMediaRecentGetResponses200Opt`] {
        override val validators = Seq(
            new MediaTagsValidator(instance.data)
            )
        }
    class `UsersUser-iFollowsGetResponses200Validator`(override val instance: `UsersUser-iFollowsGetResponses200`) extends RecursiveValidator[`UsersUser-iFollowsGetResponses200`] {
        override val validators = Seq(
            )
        }
    class MediaSearchGetResponses200OptValidator(override val instance: MediaSearchGetResponses200Opt) extends RecursiveValidator[MediaSearchGetResponses200Opt] {
        override val validators = Seq(
            new MediaSearchGetResponses200DataValidator(instance.data)
            )
        }
    class `TagsTag-namMediaRecentGetResponses200Validator`(override val instance: `TagsTag-namMediaRecentGetResponses200`) extends RecursiveValidator[`TagsTag-namMediaRecentGetResponses200`] {
        override val validators = Seq(
            )
        }
    class `MediaMedia-iCommentsPostResponses200MetaOptValidator`(override val instance: `MediaMedia-iCommentsPostResponses200MetaOpt`) extends RecursiveValidator[`MediaMedia-iCommentsPostResponses200MetaOpt`] {
        override val validators = Seq(
            new LocationLongitudeValidator(instance.code)
            )
        }
    class `UsersSelfRequested-byGetResponses200Validator`(override val instance: `UsersSelfRequested-byGetResponses200`) extends RecursiveValidator[`UsersSelfRequested-byGetResponses200`] {
        override val validators = Seq(
            )
        }
    class `UsersSelfRequested-byGetResponses200MetaOptValidator`(override val instance: `UsersSelfRequested-byGetResponses200MetaOpt`) extends RecursiveValidator[`UsersSelfRequested-byGetResponses200MetaOpt`] {
        override val validators = Seq(
            new MediaCreated_timeValidator(instance.code)
            )
        }
    class MediaSearchGetResponses200DataOptArrValidator(override val instance: MediaSearchGetResponses200DataOptArr) extends RecursiveValidator[MediaSearchGetResponses200DataOptArr] {
        override val validators = Seq(
            )
        }
    class `LocationsLocation-iGetResponses200Validator`(override val instance: `LocationsLocation-iGetResponses200`) extends RecursiveValidator[`LocationsLocation-iGetResponses200`] {
        override val validators = Seq(
            )
        }
    class `MediaMedia-iLikesGetResponses200DataOptValidator`(override val instance: `MediaMedia-iLikesGetResponses200DataOpt`) extends RecursiveValidator[`MediaMedia-iLikesGetResponses200DataOpt`] {
        override val validators = Seq(
            )
        }
    class LocationsSearchGetResponses200DataValidator(override val instance: LocationsSearchGetResponses200Data) extends RecursiveValidator[LocationsSearchGetResponses200Data] {
        override val validators = Seq(
            )
        }
    class MediaSearchGetResponses200DataOptValidator(override val instance: MediaSearchGetResponses200DataOpt) extends RecursiveValidator[MediaSearchGetResponses200DataOpt] {
        override val validators = Seq(
            )
        }
    class `MediaMedia-iLikesPostResponses200OptValidator`(override val instance: `MediaMedia-iLikesPostResponses200Opt`) extends RecursiveValidator[`MediaMedia-iLikesPostResponses200Opt`] {
        override val validators = Seq(
            new `MediaMedia-iCommentsDeleteResponses200MetaValidator`(instance.meta), 
            new LikeUser_nameValidator(instance.data)
            )
        }
    class `MediaMedia-iLikesGetResponses200Validator`(override val instance: `MediaMedia-iLikesGetResponses200`) extends RecursiveValidator[`MediaMedia-iLikesGetResponses200`] {
        override val validators = Seq(
            )
        }
    class MediaSearchGetResponses200Validator(override val instance: MediaSearchGetResponses200) extends RecursiveValidator[MediaSearchGetResponses200] {
        override val validators = Seq(
            )
        }
    class MediaSearchGetResponses200DataValidator(override val instance: MediaSearchGetResponses200Data) extends RecursiveValidator[MediaSearchGetResponses200Data] {
        override val validators = Seq(
            )
        }
    class TagsSearchGetResponses200Validator(override val instance: TagsSearchGetResponses200) extends RecursiveValidator[TagsSearchGetResponses200] {
        override val validators = Seq(
            )
        }
    class `MediaMedia-iGetResponses200Validator`(override val instance: `MediaMedia-iGetResponses200`) extends RecursiveValidator[`MediaMedia-iGetResponses200`] {
        override val validators = Seq(
            )
        }
    class `UsersUser-iGetResponses200DataValidator`(override val instance: `UsersUser-iGetResponses200Data`) extends RecursiveValidator[`UsersUser-iGetResponses200Data`] {
        override val validators = Seq(
            )
        }
    class `UsersUser-iGetResponses200Validator`(override val instance: `UsersUser-iGetResponses200`) extends RecursiveValidator[`UsersUser-iGetResponses200`] {
        override val validators = Seq(
            )
        }
    class `MediaMedia-iCommentsGetResponses200Validator`(override val instance: `MediaMedia-iCommentsGetResponses200`) extends RecursiveValidator[`MediaMedia-iCommentsGetResponses200`] {
        override val validators = Seq(
            )
        }
    class LocationsSearchGetResponses200OptValidator(override val instance: LocationsSearchGetResponses200Opt) extends RecursiveValidator[LocationsSearchGetResponses200Opt] {
        override val validators = Seq(
            new LocationsSearchGetResponses200DataValidator(instance.data)
            )
        }
    class `LocationsLocation-iGetResponses200OptValidator`(override val instance: `LocationsLocation-iGetResponses200Opt`) extends RecursiveValidator[`LocationsLocation-iGetResponses200Opt`] {
        override val validators = Seq(
            new MediaLocationValidator(instance.data)
            )
        }
    class `UsersUser-iGetResponses200OptValidator`(override val instance: `UsersUser-iGetResponses200Opt`) extends RecursiveValidator[`UsersUser-iGetResponses200Opt`] {
        override val validators = Seq(
            new `UsersUser-iGetResponses200DataValidator`(instance.data)
            )
        }
    class LocationsSearchGetResponses200Validator(override val instance: LocationsSearchGetResponses200) extends RecursiveValidator[LocationsSearchGetResponses200] {
        override val validators = Seq(
            )
        }
    class `MediaMedia-iCommentsGetResponses200OptValidator`(override val instance: `MediaMedia-iCommentsGetResponses200Opt`) extends RecursiveValidator[`MediaMedia-iCommentsGetResponses200Opt`] {
        override val validators = Seq(
            new `MediaMedia-iCommentsDeleteResponses200MetaValidator`(instance.meta), 
            new `MediaComments:DataValidator`(instance.data)
            )
        }
    }
