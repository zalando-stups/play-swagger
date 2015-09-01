package instagram.api.yaml
import org.scalacheck.Arbitrary._
import org.scalacheck.Gen
import scala.Option
import scala.collection.Seq
object generatorDefinitions {
  import definitions.Like
  import definitions.Location
  import definitions.Media
  import definitions.MiniProfile
  import definitions.Tag
  import definitions.User
  import definitions.Image
  import definitions.Comment
  object _user {
    import definitions._user.Counts
    def genCounts = _generate(CountsGenerator)
    // test data generator for /definitions/User/counts
    val CountsGenerator =
      for {
        media <- Gen.option(arbitrary[Int])
        follows <- Gen.option(arbitrary[Int])
        follwed_by <- Gen.option(arbitrary[Int])
      } yield Counts(media, follows, follwed_by)
    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
  }
  object _media {
    import definitions._media.`Comments:`
    import definitions._media.Likes
    import definitions._media.Videos
    import definitions._media.Images
    def `genComments:` = _generate(`Comments:Generator`)
    def genLikes = _generate(LikesGenerator)
    def genVideos = _generate(VideosGenerator)
    def genImages = _generate(ImagesGenerator)
    // test data generator for /definitions/Media/comments:
    val `Comments:Generator` =
      for {
        count <- Gen.option(arbitrary[Int])
        data <- Gen.option(Gen.containerOf[List,Comment](generatorDefinitions.CommentGenerator))
      } yield `Comments:`(count, data)
    // test data generator for /definitions/Media/likes
    val LikesGenerator =
      for {
        count <- Gen.option(arbitrary[Int])
        data <- Gen.option(Gen.containerOf[List,MiniProfile](generatorDefinitions.MiniProfileGenerator))
      } yield Likes(count, data)
    // test data generator for /definitions/Media/videos
    val VideosGenerator =
      for {
        low_resolution <- Gen.option(generatorDefinitions.ImageGenerator)
        standard_resolution <- Gen.option(generatorDefinitions.ImageGenerator)
      } yield Videos(low_resolution, standard_resolution)
    // test data generator for /definitions/Media/images
    val ImagesGenerator =
      for {
        low_resolution <- Gen.option(generatorDefinitions.ImageGenerator)
        thumbnail <- Gen.option(generatorDefinitions.ImageGenerator)
        standard_resolution <- Gen.option(generatorDefinitions.ImageGenerator)
      } yield Images(low_resolution, thumbnail, standard_resolution)
    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
  }
  def genComment = _generate(CommentGenerator)
  def genMedia = _generate(MediaGenerator)
  def genLocation = _generate(LocationGenerator)
  def genLike = _generate(LikeGenerator)
  def genImage = _generate(ImageGenerator)
  def genMiniProfile = _generate(MiniProfileGenerator)
  def genUser = _generate(UserGenerator)
  def genTag = _generate(TagGenerator)
  // test data generator for /definitions/User
  val UserGenerator =
    for {
      website <- Gen.option(arbitrary[String])
      profile_picture <- Gen.option(arbitrary[String])
      username <- Gen.option(arbitrary[String])
      full_name <- Gen.option(arbitrary[String])
      bio <- Gen.option(arbitrary[String])
      id <- Gen.option(arbitrary[Int])
      counts <- Gen.option(generatorDefinitions._user.CountsGenerator)
    } yield User(website, profile_picture, username, full_name, bio, id, counts)
  // test data generator for /definitions/Comment
  val CommentGenerator =
    for {
      id <- Gen.option(arbitrary[String])
      created_time <- Gen.option(arbitrary[String])
      text <- Gen.option(arbitrary[String])
      from <- Gen.option(generatorDefinitions.MiniProfileGenerator)
    } yield Comment(id, created_time, text, from)
  // test data generator for /definitions/Tag
  val TagGenerator =
    for {
      media_count <- Gen.option(arbitrary[Int])
      name <- Gen.option(arbitrary[String])
    } yield Tag(media_count, name)
  // test data generator for /definitions/Media
  val MediaGenerator =
    for {
      location <- Gen.option(generatorDefinitions.LocationGenerator)
      created_time <- Gen.option(arbitrary[Int])
      `comments:` <- Gen.option(generatorDefinitions._media.`Comments:Generator`)
      tags <- Gen.option(Gen.containerOf[List,Tag](generatorDefinitions.TagGenerator))
      users_in_photo <- Gen.option(Gen.containerOf[List,MiniProfile](generatorDefinitions.MiniProfileGenerator))
      filter <- Gen.option(arbitrary[String])
      likes <- Gen.option(generatorDefinitions._media.LikesGenerator)
      id <- Gen.option(arbitrary[Int])
      videos <- Gen.option(generatorDefinitions._media.VideosGenerator)
      `type` <- Gen.option(arbitrary[String])
      images <- Gen.option(generatorDefinitions._media.ImagesGenerator)
      user <- Gen.option(generatorDefinitions.MiniProfileGenerator)
    } yield Media(location, created_time, `comments:`, tags, users_in_photo, filter, likes, id, videos, `type`, images, user)
  // test data generator for /definitions/Image
  val ImageGenerator =
    for {
      width <- Gen.option(arbitrary[Int])
      height <- Gen.option(arbitrary[Int])
      url <- Gen.option(arbitrary[String])
    } yield Image(width, height, url)
  // test data generator for /definitions/MiniProfile
  val MiniProfileGenerator =
    for {
      user_name <- Gen.option(arbitrary[String])
      full_name <- Gen.option(arbitrary[String])
      id <- Gen.option(arbitrary[Int])
      profile_picture <- Gen.option(arbitrary[String])
    } yield MiniProfile(user_name, full_name, id, profile_picture)
  // test data generator for /definitions/Location
  val LocationGenerator =
    for {
      id <- Gen.option(arbitrary[String])
      name <- Gen.option(arbitrary[String])
      latitude <- Gen.option(arbitrary[Double])
      longitude <- Gen.option(arbitrary[Double])
    } yield Location(id, name, latitude, longitude)
  // test data generator for /definitions/Like
  val LikeGenerator =
    for {
      first_name <- Gen.option(arbitrary[String])
      id <- Gen.option(arbitrary[String])
      last_name <- Gen.option(arbitrary[String])
      `type` <- Gen.option(arbitrary[String])
      user_name <- Gen.option(arbitrary[String])
    } yield Like(first_name, id, last_name, `type`, user_name)
  def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
}