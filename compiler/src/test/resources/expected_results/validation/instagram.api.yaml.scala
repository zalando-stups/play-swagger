package instagram.api.yaml
import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._
// ----- constraints and wrapper validations -----
class MediaIdOptConstraints(override val instance: Int) extends ValidationBase[Int] {
    override def constraints: Seq[Constraint[Int]] =
        Seq()
}
class MediaIdOptValidator(instance: Int) extends RecursiveValidator {
    override val validators = Seq(new MediaIdOptConstraints(instance))

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
class LocationLatitudeOptConstraints(override val instance: Double) extends ValidationBase[Double] {
    override def constraints: Seq[Constraint[Double]] =
        Seq()
}
class LocationLatitudeOptValidator(instance: Double) extends RecursiveValidator {
    override val validators = Seq(new LocationLatitudeOptConstraints(instance))

}
class `MediaMedia-idCommentsDeleteMedia-idConstraints`(override val instance: Int) extends ValidationBase[Int] {
    override def constraints: Seq[Constraint[Int]] =
        Seq()
}
class `MediaMedia-idCommentsDeleteMedia-idValidator`(instance: Int) extends RecursiveValidator {
    override val validators = Seq(new `MediaMedia-idCommentsDeleteMedia-idConstraints`(instance))

}
class MediaFilterOptConstraints(override val instance: String) extends ValidationBase[String] {
    override def constraints: Seq[Constraint[String]] =
        Seq()
}
class MediaFilterOptValidator(instance: String) extends RecursiveValidator {
    override val validators = Seq(new MediaFilterOptConstraints(instance))

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
class MediaIdValidator(instance: MediaId) extends RecursiveValidator {
    override val validators = instance.toSeq.map { new MediaIdOptValidator(_) }
}
class LocationLatitudeValidator(instance: LocationLatitude) extends RecursiveValidator {
    override val validators = instance.toSeq.map { new LocationLatitudeOptValidator(_) }
}
class MediaFilterValidator(instance: MediaFilter) extends RecursiveValidator {
    override val validators = instance.toSeq.map { new MediaFilterOptValidator(_) }
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
class LocationsSearchGetValidator(foursquare_v2_id: MediaId, facebook_places_id: MediaId, distance: MediaId, lat: LocationLatitude, foursquare_id: MediaId, lng: LocationLatitude) extends RecursiveValidator {
    override val validators = Seq(
        new MediaIdValidator(foursquare_v2_id), new MediaIdValidator(facebook_places_id), new MediaIdValidator(distance), new LocationLatitudeValidator(lat), new MediaIdValidator(foursquare_id), new LocationLatitudeValidator(lng))
}
class `MediaMedia-idCommentsDeleteValidator`(`media-id`: Int) extends RecursiveValidator {
    override val validators = Seq(
        new `MediaMedia-idCommentsDeleteMedia-idValidator`(`media-id`))
}
class UsersSelfMediaLikedGetValidator(count: MediaId, max_like_id: MediaId) extends RecursiveValidator {
    override val validators = Seq(
        new MediaIdValidator(count), new MediaIdValidator(max_like_id))
}
class TagsSearchGetValidator(q: MediaFilter) extends RecursiveValidator {
    override val validators = Seq(
        new MediaFilterValidator(q))
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
class UsersSearchGetValidator(q: String, count: MediaFilter) extends RecursiveValidator {
    override val validators = Seq(
        new UsersSearchGetQValidator(q), new MediaFilterValidator(count))
}
class `MediaMedia-idCommentsPostValidator`(`media-id`: Int, tEXT: LocationLatitude) extends RecursiveValidator {
    override val validators = Seq(
        new `MediaMedia-idCommentsPostMedia-idValidator`(`media-id`), new LocationLatitudeValidator(tEXT))
}
class `MediaMedia-idLikesPostValidator`(`media-id`: Int) extends RecursiveValidator {
    override val validators = Seq(
        new `MediaMedia-idLikesPostMedia-idValidator`(`media-id`))
}
class `UsersUser-idRelationshipPostValidator`(`user-id`: Double, action: MediaFilter) extends RecursiveValidator {
    override val validators = Seq(
        new `UsersUser-idRelationshipPostUser-idValidator`(`user-id`), new MediaFilterValidator(action))
}
class `TagsTag-nameGetValidator`(`tag-name`: String) extends RecursiveValidator {
    override val validators = Seq(
        new `TagsTag-nameGetTag-nameValidator`(`tag-name`))
}
class `LocationsLocation-idGetValidator`(`location-id`: Int) extends RecursiveValidator {
    override val validators = Seq(
        new `LocationsLocation-idGetLocation-idValidator`(`location-id`))
}
class `LocationsLocation-idMediaRecentGetValidator`(`location-id`: Int, max_timestamp: MediaId, min_timestamp: MediaId, min_id: MediaFilter, max_id: MediaFilter) extends RecursiveValidator {
    override val validators = Seq(
        new `LocationsLocation-idMediaRecentGetLocation-idValidator`(`location-id`), new MediaIdValidator(max_timestamp), new MediaIdValidator(min_timestamp), new MediaFilterValidator(min_id), new MediaFilterValidator(max_id))
}
class MediaSearchGetValidator(mAX_TIMESTAMP: MediaId, dISTANCE: MediaId, lNG: LocationLatitude, mIN_TIMESTAMP: MediaId, lAT: LocationLatitude) extends RecursiveValidator {
    override val validators = Seq(
        new MediaIdValidator(mAX_TIMESTAMP), new MediaIdValidator(dISTANCE), new LocationLatitudeValidator(lNG), new MediaIdValidator(mIN_TIMESTAMP), new LocationLatitudeValidator(lAT))
}
class `TagsTag-nameMediaRecentGetValidator`(`tag-name`: String) extends RecursiveValidator {
    override val validators = Seq(
        new `TagsTag-nameMediaRecentGetTag-nameValidator`(`tag-name`))
}
class `UsersUser-idFollowsGetValidator`(`user-id`: Double) extends RecursiveValidator {
    override val validators = Seq(
        new `UsersUser-idFollowsGetUser-idValidator`(`user-id`))
}
class `UsersUser-idMediaRecentGetValidator`(`user-id`: Double, max_timestamp: MediaId, min_id: MediaFilter, min_timestamp: MediaId, max_id: MediaFilter, count: MediaId) extends RecursiveValidator {
    override val validators = Seq(
        new `UsersUser-idMediaRecentGetUser-idValidator`(`user-id`), new MediaIdValidator(max_timestamp), new MediaFilterValidator(min_id), new MediaIdValidator(min_timestamp), new MediaFilterValidator(max_id), new MediaIdValidator(count))
}
class UsersSelfFeedGetValidator(count: MediaId, max_id: MediaId, min_id: MediaId) extends RecursiveValidator {
    override val validators = Seq(
        new MediaIdValidator(count), new MediaIdValidator(max_id), new MediaIdValidator(min_id))
}
class `GeographiesGeo-idMediaRecentGetValidator`(`geo-id`: Int, count: MediaId, min_id: MediaId) extends RecursiveValidator {
    override val validators = Seq(
        new `GeographiesGeo-idMediaRecentGetGeo-idValidator`(`geo-id`), new MediaIdValidator(count), new MediaIdValidator(min_id))
}
