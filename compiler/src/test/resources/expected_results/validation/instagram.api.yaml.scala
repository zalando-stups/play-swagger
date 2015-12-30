package instagram.api.yaml
import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._
import java.util.Date
import java.io.File

// ----- constraints and wrapper validations -----
class MediaCreated_timeOptConstraints(override val instance: Int) extends ValidationBase[Int] {
    override def constraints: Seq[Constraint[Int]] =
        Seq()
}
class MediaCreated_timeOptValidator(instance: Int) extends RecursiveValidator {
    override val validators = Seq(new MediaCreated_timeOptConstraints(instance))

}
class `UsersUser-idFollowsGetUser-idConstraints`(override val instance: Double) extends ValidationBase[Double] {
    override def constraints: Seq[Constraint[Double]] =
        Seq()
}
class `UsersUser-idFollowsGetUser-idValidator`(instance: Double) extends RecursiveValidator {
    override val validators = Seq(new `UsersUser-idFollowsGetUser-idConstraints`(instance))

}
class `TagsTag-nameMediaRecentGetTag-nameConstraints`(override val instance: String) extends ValidationBase[String] {
    override def constraints: Seq[Constraint[String]] =
        Seq()
}
class `TagsTag-nameMediaRecentGetTag-nameValidator`(instance: String) extends RecursiveValidator {
    override val validators = Seq(new `TagsTag-nameMediaRecentGetTag-nameConstraints`(instance))

}
class `LocationsLocation-idGetLocation-idConstraints`(override val instance: Int) extends ValidationBase[Int] {
    override def constraints: Seq[Constraint[Int]] =
        Seq()
}
class `LocationsLocation-idGetLocation-idValidator`(instance: Int) extends RecursiveValidator {
    override val validators = Seq(new `LocationsLocation-idGetLocation-idConstraints`(instance))

}
class LocationLongitudeOptConstraints(override val instance: Double) extends ValidationBase[Double] {
    override def constraints: Seq[Constraint[Double]] =
        Seq()
}
class LocationLongitudeOptValidator(instance: Double) extends RecursiveValidator {
    override val validators = Seq(new LocationLongitudeOptConstraints(instance))

}
class `MediaMedia-idCommentsDeleteMedia-idConstraints`(override val instance: Int) extends ValidationBase[Int] {
    override def constraints: Seq[Constraint[Int]] =
        Seq()
}
class `MediaMedia-idCommentsDeleteMedia-idValidator`(instance: Int) extends RecursiveValidator {
    override val validators = Seq(new `MediaMedia-idCommentsDeleteMedia-idConstraints`(instance))

}
class LikeUser_nameOptConstraints(override val instance: String) extends ValidationBase[String] {
    override def constraints: Seq[Constraint[String]] =
        Seq()
}
class LikeUser_nameOptValidator(instance: String) extends RecursiveValidator {
    override val validators = Seq(new LikeUser_nameOptConstraints(instance))

}
class `MediaMedia-idLikesGetMedia-idConstraints`(override val instance: Int) extends ValidationBase[Int] {
    override def constraints: Seq[Constraint[Int]] =
        Seq()
}
class `MediaMedia-idLikesGetMedia-idValidator`(instance: Int) extends RecursiveValidator {
    override val validators = Seq(new `MediaMedia-idLikesGetMedia-idConstraints`(instance))

}
class `TagsTag-nameGetTag-nameConstraints`(override val instance: String) extends ValidationBase[String] {
    override def constraints: Seq[Constraint[String]] =
        Seq()
}
class `TagsTag-nameGetTag-nameValidator`(instance: String) extends RecursiveValidator {
    override val validators = Seq(new `TagsTag-nameGetTag-nameConstraints`(instance))

}
class `MediaMedia-idLikesDeleteMedia-idConstraints`(override val instance: Int) extends ValidationBase[Int] {
    override def constraints: Seq[Constraint[Int]] =
        Seq()
}
class `MediaMedia-idLikesDeleteMedia-idValidator`(instance: Int) extends RecursiveValidator {
    override val validators = Seq(new `MediaMedia-idLikesDeleteMedia-idConstraints`(instance))

}
class `MediaMedia-idCommentsGetMedia-idConstraints`(override val instance: Int) extends ValidationBase[Int] {
    override def constraints: Seq[Constraint[Int]] =
        Seq()
}
class `MediaMedia-idCommentsGetMedia-idValidator`(instance: Int) extends RecursiveValidator {
    override val validators = Seq(new `MediaMedia-idCommentsGetMedia-idConstraints`(instance))

}
class `LocationsLocation-idMediaRecentGetLocation-idConstraints`(override val instance: Int) extends ValidationBase[Int] {
    override def constraints: Seq[Constraint[Int]] =
        Seq()
}
class `LocationsLocation-idMediaRecentGetLocation-idValidator`(instance: Int) extends RecursiveValidator {
    override val validators = Seq(new `LocationsLocation-idMediaRecentGetLocation-idConstraints`(instance))

}
class `UsersUser-idFollowed-byGetUser-idConstraints`(override val instance: Double) extends ValidationBase[Double] {
    override def constraints: Seq[Constraint[Double]] =
        Seq()
}
class `UsersUser-idFollowed-byGetUser-idValidator`(instance: Double) extends RecursiveValidator {
    override val validators = Seq(new `UsersUser-idFollowed-byGetUser-idConstraints`(instance))

}
class `MediaMedia-idGetMedia-idConstraints`(override val instance: Int) extends ValidationBase[Int] {
    override def constraints: Seq[Constraint[Int]] =
        Seq()
}
class `MediaMedia-idGetMedia-idValidator`(instance: Int) extends RecursiveValidator {
    override val validators = Seq(new `MediaMedia-idGetMedia-idConstraints`(instance))

}
class `MediaMedia-idCommentsPostMedia-idConstraints`(override val instance: Int) extends ValidationBase[Int] {
    override def constraints: Seq[Constraint[Int]] =
        Seq()
}
class `MediaMedia-idCommentsPostMedia-idValidator`(instance: Int) extends RecursiveValidator {
    override val validators = Seq(new `MediaMedia-idCommentsPostMedia-idConstraints`(instance))

}
class MediaShortcodeGetShortcodeConstraints(override val instance: String) extends ValidationBase[String] {
    override def constraints: Seq[Constraint[String]] =
        Seq()
}
class MediaShortcodeGetShortcodeValidator(instance: String) extends RecursiveValidator {
    override val validators = Seq(new MediaShortcodeGetShortcodeConstraints(instance))

}
class `UsersUser-idGetUser-idConstraints`(override val instance: Double) extends ValidationBase[Double] {
    override def constraints: Seq[Constraint[Double]] =
        Seq()
}
class `UsersUser-idGetUser-idValidator`(instance: Double) extends RecursiveValidator {
    override val validators = Seq(new `UsersUser-idGetUser-idConstraints`(instance))

}
class `GeographiesGeo-idMediaRecentGetGeo-idConstraints`(override val instance: Int) extends ValidationBase[Int] {
    override def constraints: Seq[Constraint[Int]] =
        Seq()
}
class `GeographiesGeo-idMediaRecentGetGeo-idValidator`(instance: Int) extends RecursiveValidator {
    override val validators = Seq(new `GeographiesGeo-idMediaRecentGetGeo-idConstraints`(instance))

}
class `MediaMedia-idLikesPostMedia-idConstraints`(override val instance: Int) extends ValidationBase[Int] {
    override def constraints: Seq[Constraint[Int]] =
        Seq()
}
class `MediaMedia-idLikesPostMedia-idValidator`(instance: Int) extends RecursiveValidator {
    override val validators = Seq(new `MediaMedia-idLikesPostMedia-idConstraints`(instance))

}
class UsersSearchGetQConstraints(override val instance: String) extends ValidationBase[String] {
    override def constraints: Seq[Constraint[String]] =
        Seq()
}
class UsersSearchGetQValidator(instance: String) extends RecursiveValidator {
    override val validators = Seq(new UsersSearchGetQConstraints(instance))

}
class `UsersUser-idRelationshipPostUser-idConstraints`(override val instance: Double) extends ValidationBase[Double] {
    override def constraints: Seq[Constraint[Double]] =
        Seq()
}
class `UsersUser-idRelationshipPostUser-idValidator`(instance: Double) extends RecursiveValidator {
    override val validators = Seq(new `UsersUser-idRelationshipPostUser-idConstraints`(instance))

}
class `UsersUser-idMediaRecentGetUser-idConstraints`(override val instance: Double) extends ValidationBase[Double] {
    override def constraints: Seq[Constraint[Double]] =
        Seq()
}
class `UsersUser-idMediaRecentGetUser-idValidator`(instance: Double) extends RecursiveValidator {
    override val validators = Seq(new `UsersUser-idMediaRecentGetUser-idConstraints`(instance))

}
// ----- complex type validators -----
// ----- option delegating validators -----
class MediaCreated_timeValidator(instance: MediaCreated_time) extends RecursiveValidator {
    override val validators = instance.toSeq.map { new MediaCreated_timeOptValidator(_) }
}
class LocationLongitudeValidator(instance: LocationLongitude) extends RecursiveValidator {
    override val validators = instance.toSeq.map { new LocationLongitudeOptValidator(_) }
}
class LikeUser_nameValidator(instance: LikeUser_name) extends RecursiveValidator {
    override val validators = instance.toSeq.map { new LikeUser_nameOptValidator(_) }
}
// ----- array delegating validators -----
// ----- catch all simple validators -----
// ----- call validations -----
class `UsersUser-idGetValidator`(`user-id`: Double) extends RecursiveValidator {
    override val validators = Seq(
        new `UsersUser-idGetUser-idValidator`(`user-id`))
}
class `UsersUser-idFollowed-byGetValidator`(`user-id`: Double) extends RecursiveValidator {
    override val validators = Seq(
        new `UsersUser-idFollowed-byGetUser-idValidator`(`user-id`))
}
class `MediaMedia-idLikesGetValidator`(`media-id`: Int) extends RecursiveValidator {
    override val validators = Seq(
        new `MediaMedia-idLikesGetMedia-idValidator`(`media-id`))
}
class LocationsSearchGetValidator(foursquare_v2_id: MediaCreated_time, facebook_places_id: MediaCreated_time, distance: MediaCreated_time, lat: LocationLongitude, foursquare_id: MediaCreated_time, lng: LocationLongitude) extends RecursiveValidator {
    override val validators = Seq(
        new MediaCreated_timeValidator(foursquare_v2_id), new MediaCreated_timeValidator(facebook_places_id), new MediaCreated_timeValidator(distance), new LocationLongitudeValidator(lat), new MediaCreated_timeValidator(foursquare_id), new LocationLongitudeValidator(lng))
}
class `MediaMedia-idCommentsDeleteValidator`(`media-id`: Int) extends RecursiveValidator {
    override val validators = Seq(
        new `MediaMedia-idCommentsDeleteMedia-idValidator`(`media-id`))
}
class UsersSelfMediaLikedGetValidator(count: MediaCreated_time, max_like_id: MediaCreated_time) extends RecursiveValidator {
    override val validators = Seq(
        new MediaCreated_timeValidator(count), new MediaCreated_timeValidator(max_like_id))
}
class TagsSearchGetValidator(q: LikeUser_name) extends RecursiveValidator {
    override val validators = Seq(
        new LikeUser_nameValidator(q))
}
class `MediaMedia-idCommentsGetValidator`(`media-id`: Int) extends RecursiveValidator {
    override val validators = Seq(
        new `MediaMedia-idCommentsGetMedia-idValidator`(`media-id`))
}
class `MediaMedia-idLikesDeleteValidator`(`media-id`: Int) extends RecursiveValidator {
    override val validators = Seq(
        new `MediaMedia-idLikesDeleteMedia-idValidator`(`media-id`))
}
class `MediaMedia-idGetValidator`(`media-id`: Int) extends RecursiveValidator {
    override val validators = Seq(
        new `MediaMedia-idGetMedia-idValidator`(`media-id`))
}
class MediaShortcodeGetValidator(shortcode: String) extends RecursiveValidator {
    override val validators = Seq(
        new MediaShortcodeGetShortcodeValidator(shortcode))
}
class UsersSearchGetValidator(q: String, count: LikeUser_name) extends RecursiveValidator {
    override val validators = Seq(
        new UsersSearchGetQValidator(q), new LikeUser_nameValidator(count))
}
class `MediaMedia-idCommentsPostValidator`(`media-id`: Int, TEXT: LocationLongitude) extends RecursiveValidator {
    override val validators = Seq(
        new `MediaMedia-idCommentsPostMedia-idValidator`(`media-id`), new LocationLongitudeValidator(TEXT))
}
class `MediaMedia-idLikesPostValidator`(`media-id`: Int) extends RecursiveValidator {
    override val validators = Seq(
        new `MediaMedia-idLikesPostMedia-idValidator`(`media-id`))
}
class `UsersUser-idRelationshipPostValidator`(`user-id`: Double, action: LikeUser_name) extends RecursiveValidator {
    override val validators = Seq(
        new `UsersUser-idRelationshipPostUser-idValidator`(`user-id`), new LikeUser_nameValidator(action))
}
class `TagsTag-nameGetValidator`(`tag-name`: String) extends RecursiveValidator {
    override val validators = Seq(
        new `TagsTag-nameGetTag-nameValidator`(`tag-name`))
}
class `LocationsLocation-idGetValidator`(`location-id`: Int) extends RecursiveValidator {
    override val validators = Seq(
        new `LocationsLocation-idGetLocation-idValidator`(`location-id`))
}
class `LocationsLocation-idMediaRecentGetValidator`(`location-id`: Int, max_timestamp: MediaCreated_time, min_timestamp: MediaCreated_time, min_id: LikeUser_name, max_id: LikeUser_name) extends RecursiveValidator {
    override val validators = Seq(
        new `LocationsLocation-idMediaRecentGetLocation-idValidator`(`location-id`), new MediaCreated_timeValidator(max_timestamp), new MediaCreated_timeValidator(min_timestamp), new LikeUser_nameValidator(min_id), new LikeUser_nameValidator(max_id))
}
class MediaSearchGetValidator(MAX_TIMESTAMP: MediaCreated_time, DISTANCE: MediaCreated_time, LNG: LocationLongitude, MIN_TIMESTAMP: MediaCreated_time, LAT: LocationLongitude) extends RecursiveValidator {
    override val validators = Seq(
        new MediaCreated_timeValidator(MAX_TIMESTAMP), new MediaCreated_timeValidator(DISTANCE), new LocationLongitudeValidator(LNG), new MediaCreated_timeValidator(MIN_TIMESTAMP), new LocationLongitudeValidator(LAT))
}
class `TagsTag-nameMediaRecentGetValidator`(`tag-name`: String) extends RecursiveValidator {
    override val validators = Seq(
        new `TagsTag-nameMediaRecentGetTag-nameValidator`(`tag-name`))
}
class `UsersUser-idFollowsGetValidator`(`user-id`: Double) extends RecursiveValidator {
    override val validators = Seq(
        new `UsersUser-idFollowsGetUser-idValidator`(`user-id`))
}
class `UsersUser-idMediaRecentGetValidator`(`user-id`: Double, max_timestamp: MediaCreated_time, min_id: LikeUser_name, min_timestamp: MediaCreated_time, max_id: LikeUser_name, count: MediaCreated_time) extends RecursiveValidator {
    override val validators = Seq(
        new `UsersUser-idMediaRecentGetUser-idValidator`(`user-id`), new MediaCreated_timeValidator(max_timestamp), new LikeUser_nameValidator(min_id), new MediaCreated_timeValidator(min_timestamp), new LikeUser_nameValidator(max_id), new MediaCreated_timeValidator(count))
}
class UsersSelfFeedGetValidator(count: MediaCreated_time, max_id: MediaCreated_time, min_id: MediaCreated_time) extends RecursiveValidator {
    override val validators = Seq(
        new MediaCreated_timeValidator(count), new MediaCreated_timeValidator(max_id), new MediaCreated_timeValidator(min_id))
}
class `GeographiesGeo-idMediaRecentGetValidator`(`geo-id`: Int, count: MediaCreated_time, min_id: MediaCreated_time) extends RecursiveValidator {
    override val validators = Seq(
        new `GeographiesGeo-idMediaRecentGetGeo-idValidator`(`geo-id`), new MediaCreated_timeValidator(count), new MediaCreated_timeValidator(min_id))
}
