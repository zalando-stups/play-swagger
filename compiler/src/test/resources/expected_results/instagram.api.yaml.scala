package instagram.api.yaml
import scala.Option
import scala.collection.Seq
object definitions {
  object user {
    case class Counts(
      media: Option[Int],
      follows: Option[Int],
      follwed_by: Option[Int]
    )
  }
  object media {
    case class `Comments:`(
      count: Option[Int],
      data: Option[Seq[Comment]]
    )
    case class Likes(
      count: Option[Int],
      data: Option[Seq[MiniProfile]]
    )
    case class Videos(
      low_resolution: Option[Image],
      standard_resolution: Option[Image]
    )
    case class Images(
      low_resolution: Option[Image],
      thumbnail: Option[Image],
      standard_resolution: Option[Image]
    )
  }
  case class Media(
    location: Option[Location],
    // Epoc time (ms)
    created_time: Option[Int],
    `comments:`: Option[media.`Comments:`],
    tags: Option[Seq[Tag]],
    users_in_photo: Option[Seq[MiniProfile]],
    filter: Option[String],
    likes: Option[media.Likes],
    id: Option[Int],
    videos: Option[media.Videos],
    `type`: Option[String],
    images: Option[media.Images],
    user: Option[MiniProfile]
  )
  case class Comment(
    id: Option[String],
    created_time: Option[String],
    text: Option[String],
    from: Option[MiniProfile]
  )
  case class Like(
    first_name: Option[String],
    id: Option[String],
    last_name: Option[String],
    `type`: Option[String],
    user_name: Option[String]
  )
  case class User(
    website: Option[String],
    profile_picture: Option[String],
    username: Option[String],
    full_name: Option[String],
    bio: Option[String],
    id: Option[Int],
    counts: Option[user.Counts]
  )
  case class Location(
    id: Option[String],
    name: Option[String],
    latitude: Option[Double],
    longitude: Option[Double]
  )
  case class Image(
    width: Option[Int],
    height: Option[Int],
    url: Option[String]
  )
  // A shorter version of User for likes array
  case class MiniProfile(
    user_name: Option[String],
    full_name: Option[String],
    id: Option[Int],
    profile_picture: Option[String]
  )
  case class Tag(
    media_count: Option[Int],
    name: Option[String]
  )
}