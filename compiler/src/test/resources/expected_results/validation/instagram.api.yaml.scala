package instagram.api.yaml
import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._

object parametersValidator {
    import parameters.`User-id-paramUser-id`
    import parameters.`Tag-nameTag-name`
    class MediaShortcodeGetShortcodeConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class MediaShortcodeGetShortcodeValidator(instance: String) extends RecursiveValidator {
      override val validators = Seq(new MediaShortcodeGetShortcodeConstraints(instance))
    }
    class `User-id-paramUser-idConstraints`(override val instance: Double) extends ValidationBase[Double] {
        override def constraints: Seq[Constraint[Double]] =
        Seq()
    }
    class `User-id-paramUser-idValidator`(instance: Double) extends RecursiveValidator {
      override val validators = Seq(new `User-id-paramUser-idConstraints`(instance))
    }
    class UsersSearchGetQConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class UsersSearchGetQValidator(instance: String) extends RecursiveValidator {
      override val validators = Seq(new UsersSearchGetQConstraints(instance))
    }
    class `Tag-nameTag-nameConstraints`(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class `Tag-nameTag-nameValidator`(instance: String) extends RecursiveValidator {
      override val validators = Seq(new `Tag-nameTag-nameConstraints`(instance))
    }
    }
object definitionsValidator {
    import definitions._
    class MediaShortcodeGetShortcodeConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class MediaShortcodeGetShortcodeValidator(instance: String) extends RecursiveValidator {
      override val validators = Seq(new MediaShortcodeGetShortcodeConstraints(instance))
    }
    class UsersSearchGetQConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class UsersSearchGetQValidator(instance: String) extends RecursiveValidator {
      override val validators = Seq(new UsersSearchGetQConstraints(instance))
    }
    class LikeUser_nameValidator(instance: LikeUser_name) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class MediaUserValidator(instance: MediaUser) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class MediaVideosLow_resolutionValidator(instance: MediaVideosLow_resolution) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class LocationLongitudeValidator(instance: LocationLongitude) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class MediaImagesValidator(instance: MediaImages) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class UserValidator(instance: User) extends RecursiveValidator {
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
    class ImageValidator(instance: Image) extends RecursiveValidator {
        override val validators = Seq(
            new MediaCreated_timeValidator(instance.width), 
            new MediaCreated_timeValidator(instance.height), 
            new LikeUser_nameValidator(instance.url)
            )
        }
    class TagValidator(instance: Tag) extends RecursiveValidator {
        override val validators = Seq(
            new MediaCreated_timeValidator(instance.media_count), 
            new LikeUser_nameValidator(instance.name)
            )
        }
    class MediaTagsOptValidator(instance: MediaTagsOpt) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class MediaLikesValidator(instance: MediaLikes) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class MediaVideosValidator(instance: MediaVideos) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class CommentValidator(instance: Comment) extends RecursiveValidator {
        override val validators = Seq(
            new LikeUser_nameValidator(instance.id), 
            new LikeUser_nameValidator(instance.created_time), 
            new LikeUser_nameValidator(instance.text), 
            new MediaUserValidator(instance.from)
            )
        }
    class MediaValidator(instance: Media) extends RecursiveValidator {
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
    class MediaCreated_timeValidator(instance: MediaCreated_time) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class MediaLocationValidator(instance: MediaLocation) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class MediaVideosOptValidator(instance: MediaVideosOpt) extends RecursiveValidator {
        override val validators = Seq(
            new MediaVideosLow_resolutionValidator(instance.low_resolution), 
            new MediaVideosLow_resolutionValidator(instance.standard_resolution)
            )
        }
    class MediaTagsValidator(instance: MediaTags) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class LikeValidator(instance: Like) extends RecursiveValidator {
        override val validators = Seq(
            new LikeUser_nameValidator(instance.first_name), 
            new LikeUser_nameValidator(instance.id), 
            new LikeUser_nameValidator(instance.last_name), 
            new LikeUser_nameValidator(instance.`type`), 
            new LikeUser_nameValidator(instance.user_name)
            )
        }
    class `MediaComments:DataOptValidator`(instance: `MediaComments:DataOpt`) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class `MediaComments:Validator`(instance: `MediaComments:`) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class MediaLikesOptValidator(instance: MediaLikesOpt) extends RecursiveValidator {
        override val validators = Seq(
            new MediaCreated_timeValidator(instance.count), 
            new MediaUsers_in_photoValidator(instance.data)
            )
        }
    class MediaUsers_in_photoValidator(instance: MediaUsers_in_photo) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class LocationValidator(instance: Location) extends RecursiveValidator {
        override val validators = Seq(
            new LikeUser_nameValidator(instance.id), 
            new LikeUser_nameValidator(instance.name), 
            new LocationLongitudeValidator(instance.latitude), 
            new LocationLongitudeValidator(instance.longitude)
            )
        }
    class MiniProfileValidator(instance: MiniProfile) extends RecursiveValidator {
        override val validators = Seq(
            new LikeUser_nameValidator(instance.user_name), 
            new LikeUser_nameValidator(instance.full_name), 
            new MediaCreated_timeValidator(instance.id), 
            new LikeUser_nameValidator(instance.profile_picture)
            )
        }
    class MediaImagesOptValidator(instance: MediaImagesOpt) extends RecursiveValidator {
        override val validators = Seq(
            new MediaVideosLow_resolutionValidator(instance.low_resolution), 
            new MediaVideosLow_resolutionValidator(instance.thumbnail), 
            new MediaVideosLow_resolutionValidator(instance.standard_resolution)
            )
        }
    class UserCountsValidator(instance: UserCounts) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class `MediaComments:DataValidator`(instance: `MediaComments:Data`) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class UserCountsOptValidator(instance: UserCountsOpt) extends RecursiveValidator {
        override val validators = Seq(
            new MediaCreated_timeValidator(instance.media), 
            new MediaCreated_timeValidator(instance.follows), 
            new MediaCreated_timeValidator(instance.follwed_by)
            )
        }
    class `MediaComments:OptValidator`(instance: `MediaComments:Opt`) extends RecursiveValidator {
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
    class `LocationsLocation-idLocation-idConstraints`(override val instance: Int) extends ValidationBase[Int] {
        override def constraints: Seq[Constraint[Int]] =
        Seq()
    }
    class `LocationsLocation-idLocation-idValidator`(instance: Int) extends RecursiveValidator {
      override val validators = Seq(new `LocationsLocation-idLocation-idConstraints`(instance))
    }
    class `GeographiesGeo-idMediaRecentGetResponses200Constraints`(override val instance: Null) extends ValidationBase[Null] {
        override def constraints: Seq[Constraint[Null]] =
        Seq()
    }
    class `GeographiesGeo-idMediaRecentGetResponses200Validator`(instance: Null) extends RecursiveValidator {
      override val validators = Seq(new `GeographiesGeo-idMediaRecentGetResponses200Constraints`(instance))
    }
    class MediaShortcodeGetShortcodeConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class MediaShortcodeGetShortcodeValidator(instance: String) extends RecursiveValidator {
      override val validators = Seq(new MediaShortcodeGetShortcodeConstraints(instance))
    }
    class UsersSearchGetQConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class UsersSearchGetQValidator(instance: String) extends RecursiveValidator {
      override val validators = Seq(new UsersSearchGetQConstraints(instance))
    }
    class TagsSearchGetResponses200MetaValidator(instance: TagsSearchGetResponses200Meta) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class `MediaMedia-idLikesGetResponses200OptValidator`(instance: `MediaMedia-idLikesGetResponses200Opt`) extends RecursiveValidator {
        override val validators = Seq(
            new `MediaMedia-idCommentsDeleteResponses200MetaValidator`(instance.meta), 
            new `MediaMedia-idLikesGetResponses200DataValidator`(instance.data)
            )
        }
    class `UsersSelfRequested-byGetResponses200OptValidator`(instance: `UsersSelfRequested-byGetResponses200Opt`) extends RecursiveValidator {
        override val validators = Seq(
            new TagsSearchGetResponses200MetaValidator(instance.meta), 
            new MediaUsers_in_photoValidator(instance.data)
            )
        }
    class UsersSelfFeedGetResponses200Validator(instance: UsersSelfFeedGetResponses200) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class `LocationsLocation-idMediaRecentGetResponses200DataValidator`(instance: `LocationsLocation-idMediaRecentGetResponses200Data`) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class `UsersUser-idRelationshipPostResponses200OptValidator`(instance: `UsersUser-idRelationshipPostResponses200Opt`) extends RecursiveValidator {
        override val validators = Seq(
            new MediaUsers_in_photoValidator(instance.data)
            )
        }
    class `UsersUser-idMediaRecentGetResponses200OptValidator`(instance: `UsersUser-idMediaRecentGetResponses200Opt`) extends RecursiveValidator {
        override val validators = Seq(
            new `LocationsLocation-idMediaRecentGetResponses200DataValidator`(instance.data)
            )
        }
    class `MediaMedia-idCommentsDeleteResponses200MetaValidator`(instance: `MediaMedia-idCommentsDeleteResponses200Meta`) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class TagsSearchGetResponses200OptValidator(instance: TagsSearchGetResponses200Opt) extends RecursiveValidator {
        override val validators = Seq(
            new TagsSearchGetResponses200MetaValidator(instance.meta), 
            new MediaTagsValidator(instance.data)
            )
        }
    class `MediaMedia-idLikesGetResponses200DataValidator`(instance: `MediaMedia-idLikesGetResponses200Data`) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class `MediaMedia-idCommentsDeleteResponses200Validator`(instance: `MediaMedia-idCommentsDeleteResponses200`) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class `TagsTag-nameMediaRecentGetResponses200OptValidator`(instance: `TagsTag-nameMediaRecentGetResponses200Opt`) extends RecursiveValidator {
        override val validators = Seq(
            new MediaTagsValidator(instance.data)
            )
        }
    class `UsersUser-idFollowsGetResponses200Validator`(instance: `UsersUser-idFollowsGetResponses200`) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class MediaSearchGetResponses200OptValidator(instance: MediaSearchGetResponses200Opt) extends RecursiveValidator {
        override val validators = Seq(
            new MediaSearchGetResponses200DataValidator(instance.data)
            )
        }
    class `TagsTag-nameMediaRecentGetResponses200Validator`(instance: `TagsTag-nameMediaRecentGetResponses200`) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class `MediaMedia-idCommentsPostResponses200MetaOptValidator`(instance: `MediaMedia-idCommentsPostResponses200MetaOpt`) extends RecursiveValidator {
        override val validators = Seq(
            new LocationLongitudeValidator(instance.code)
            )
        }
    class `UsersSelfRequested-byGetResponses200Validator`(instance: `UsersSelfRequested-byGetResponses200`) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class `UsersSelfRequested-byGetResponses200MetaOptValidator`(instance: `UsersSelfRequested-byGetResponses200MetaOpt`) extends RecursiveValidator {
        override val validators = Seq(
            new MediaCreated_timeValidator(instance.code)
            )
        }
    class MediaSearchGetResponses200DataOptArrValidator(instance: MediaSearchGetResponses200DataOptArr) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class `LocationsLocation-idGetResponses200Validator`(instance: `LocationsLocation-idGetResponses200`) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class `MediaMedia-idLikesGetResponses200DataOptValidator`(instance: `MediaMedia-idLikesGetResponses200DataOpt`) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class LocationsSearchGetResponses200DataValidator(instance: LocationsSearchGetResponses200Data) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class MediaSearchGetResponses200DataOptValidator(instance: MediaSearchGetResponses200DataOpt) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class `MediaMedia-idLikesPostResponses200OptValidator`(instance: `MediaMedia-idLikesPostResponses200Opt`) extends RecursiveValidator {
        override val validators = Seq(
            new `MediaMedia-idCommentsDeleteResponses200MetaValidator`(instance.meta), 
            new LikeUser_nameValidator(instance.data)
            )
        }
    class `MediaMedia-idLikesGetResponses200Validator`(instance: `MediaMedia-idLikesGetResponses200`) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class MediaSearchGetResponses200Validator(instance: MediaSearchGetResponses200) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class MediaSearchGetResponses200DataValidator(instance: MediaSearchGetResponses200Data) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class TagsSearchGetResponses200Validator(instance: TagsSearchGetResponses200) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class `MediaMedia-idGetResponses200Validator`(instance: `MediaMedia-idGetResponses200`) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class `UsersUser-idGetResponses200DataValidator`(instance: `UsersUser-idGetResponses200Data`) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class `UsersUser-idGetResponses200Validator`(instance: `UsersUser-idGetResponses200`) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class `MediaMedia-idCommentsGetResponses200Validator`(instance: `MediaMedia-idCommentsGetResponses200`) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class LocationsSearchGetResponses200OptValidator(instance: LocationsSearchGetResponses200Opt) extends RecursiveValidator {
        override val validators = Seq(
            new LocationsSearchGetResponses200DataValidator(instance.data)
            )
        }
    class `LocationsLocation-idGetResponses200OptValidator`(instance: `LocationsLocation-idGetResponses200Opt`) extends RecursiveValidator {
        override val validators = Seq(
            new MediaLocationValidator(instance.data)
            )
        }
    class `UsersUser-idGetResponses200OptValidator`(instance: `UsersUser-idGetResponses200Opt`) extends RecursiveValidator {
        override val validators = Seq(
            new `UsersUser-idGetResponses200DataValidator`(instance.data)
            )
        }
    class LocationsSearchGetResponses200Validator(instance: LocationsSearchGetResponses200) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class `MediaMedia-idCommentsGetResponses200OptValidator`(instance: `MediaMedia-idCommentsGetResponses200Opt`) extends RecursiveValidator {
        override val validators = Seq(
            new `MediaMedia-idCommentsDeleteResponses200MetaValidator`(instance.meta), 
            new `MediaComments:DataValidator`(instance.data)
            )
        }
    class UsersSearchGetValidator(q: String, count: LikeUser_name) extends RecursiveValidator {
    override val validators = Seq(
    new UsersSearchGetQValidator(q), 
    new LikeUser_nameValidator(count)
    )
    }
    class UsersSelfMediaLikedGetValidator(count: MediaCreated_time, max_like_id: MediaCreated_time) extends RecursiveValidator {
    override val validators = Seq(
    new MediaCreated_timeValidator(count), 
    new MediaCreated_timeValidator(max_like_id)
    )
    }
    class TagsSearchGetValidator(q: LikeUser_name) extends RecursiveValidator {
    override val validators = Seq(
    new LikeUser_nameValidator(q)
    )
    }
    class UsersSelfFeedGetValidator(count: MediaCreated_time, max_id: MediaCreated_time, min_id: MediaCreated_time) extends RecursiveValidator {
    override val validators = Seq(
    new MediaCreated_timeValidator(count), 
    new MediaCreated_timeValidator(max_id), 
    new MediaCreated_timeValidator(min_id)
    )
    }
    class MediaSearchGetValidator(MAX_TIMESTAMP: MediaCreated_time, DISTANCE: MediaCreated_time, LNG: LocationLongitude, MIN_TIMESTAMP: MediaCreated_time, LAT: LocationLongitude) extends RecursiveValidator {
    override val validators = Seq(
    new MediaCreated_timeValidator(MAX_TIMESTAMP), 
    new MediaCreated_timeValidator(DISTANCE), 
    new LocationLongitudeValidator(LNG), 
    new MediaCreated_timeValidator(MIN_TIMESTAMP), 
    new LocationLongitudeValidator(LAT)
    )
    }
    class MediaShortcodeGetValidator(shortcode: String) extends RecursiveValidator {
    override val validators = Seq(
    new MediaShortcodeGetShortcodeValidator(shortcode)
    )
    }
    class LocationsSearchGetValidator(foursquare_v2_id: MediaCreated_time, facebook_places_id: MediaCreated_time, distance: MediaCreated_time, lat: LocationLongitude, foursquare_id: MediaCreated_time, lng: LocationLongitude) extends RecursiveValidator {
    override val validators = Seq(
    new MediaCreated_timeValidator(foursquare_v2_id), 
    new MediaCreated_timeValidator(facebook_places_id), 
    new MediaCreated_timeValidator(distance), 
    new LocationLongitudeValidator(lat), 
    new MediaCreated_timeValidator(foursquare_id), 
    new LocationLongitudeValidator(lng)
    )
    }
    }
