package instagram.api.yaml

import org.scalacheck.Gen
import org.scalacheck.Arbitrary._

import de.zalando.play.controllers.ArrayWrapper
object Generators {
def createTagsSearchGetResponses200MetaGenerator = _generate(TagsSearchGetResponses200MetaGenerator)

    def createIntGenerator = _generate(IntGenerator)

    def createUsersSelfFeedGetResponses200Generator = _generate(UsersSelfFeedGetResponses200Generator)

    def createLikeUser_nameGenerator = _generate(LikeUser_nameGenerator)

    def `createMediaMedia-idCommentsDeleteResponses200MetaGenerator` = _generate(`MediaMedia-idCommentsDeleteResponses200MetaGenerator`)

    def createUsersSelfFeedGetResponses200DataGenerator = _generate(UsersSelfFeedGetResponses200DataGenerator)

    def `createMediaMedia-idLikesGetResponses200DataGenerator` = _generate(`MediaMedia-idLikesGetResponses200DataGenerator`)

    def createMediaUserGenerator = _generate(MediaUserGenerator)

    def createMediaVideosLow_resolutionGenerator = _generate(MediaVideosLow_resolutionGenerator)

    def createLocationLongitudeGenerator = _generate(LocationLongitudeGenerator)

    def `createMediaMedia-idCommentsDeleteResponses200Generator` = _generate(`MediaMedia-idCommentsDeleteResponses200Generator`)

    def createMediaUsers_in_photoOptGenerator = _generate(MediaUsers_in_photoOptGenerator)

    def createMediaImagesGenerator = _generate(MediaImagesGenerator)

    def `createUsersUser-idFollowsGetResponses200Generator` = _generate(`UsersUser-idFollowsGetResponses200Generator`)

    def `createTagsTag-nameMediaRecentGetResponses200Generator` = _generate(`TagsTag-nameMediaRecentGetResponses200Generator`)

    def `createUsersSelfRequested-byGetResponses200Generator` = _generate(`UsersSelfRequested-byGetResponses200Generator`)

    def createMediaTagsOptGenerator = _generate(MediaTagsOptGenerator)

    def createMediaLikesGenerator = _generate(MediaLikesGenerator)

    def createMediaVideosGenerator = _generate(MediaVideosGenerator)

    def `createLocationsLocation-idGetResponses200Generator` = _generate(`LocationsLocation-idGetResponses200Generator`)

    def createMediaCreated_timeGenerator = _generate(MediaCreated_timeGenerator)

    def createMediaLocationGenerator = _generate(MediaLocationGenerator)

    def `createMediaMedia-idLikesGetResponses200DataOptGenerator` = _generate(`MediaMedia-idLikesGetResponses200DataOptGenerator`)

    def createLocationsSearchGetResponses200DataGenerator = _generate(LocationsSearchGetResponses200DataGenerator)

    def `createTagsTag-nameGetResponses200Generator` = _generate(`TagsTag-nameGetResponses200Generator`)

    def createMediaSearchGetResponses200DataOptGenerator = _generate(MediaSearchGetResponses200DataOptGenerator)

    def `createMediaMedia-idLikesGetResponses200Generator` = _generate(`MediaMedia-idLikesGetResponses200Generator`)

    def createMediaTagsGenerator = _generate(MediaTagsGenerator)

    def createLocationsSearchGetResponses200DataOptGenerator = _generate(LocationsSearchGetResponses200DataOptGenerator)

    def createMediaSearchGetResponses200Generator = _generate(MediaSearchGetResponses200Generator)

    def createMediaSearchGetResponses200DataGenerator = _generate(MediaSearchGetResponses200DataGenerator)

    def createUsersSelfFeedGetResponses200DataOptGenerator = _generate(UsersSelfFeedGetResponses200DataOptGenerator)

    def createTagsSearchGetResponses200Generator = _generate(TagsSearchGetResponses200Generator)

    def `createMediaMedia-idGetResponses200Generator` = _generate(`MediaMedia-idGetResponses200Generator`)

    def `createMediaComments:DataOptGenerator` = _generate(`MediaComments:DataOptGenerator`)

    def `createUsersUser-idGetResponses200DataGenerator` = _generate(`UsersUser-idGetResponses200DataGenerator`)

    def `createMediaComments:Generator` = _generate(`MediaComments:Generator`)

    def `createUsersUser-idGetResponses200Generator` = _generate(`UsersUser-idGetResponses200Generator`)

    def `createMediaMedia-idCommentsGetResponses200Generator` = _generate(`MediaMedia-idCommentsGetResponses200Generator`)

    def createMediaUsers_in_photoGenerator = _generate(MediaUsers_in_photoGenerator)

    def createNullGenerator = _generate(NullGenerator)

    def createUserCountsGenerator = _generate(UserCountsGenerator)

    def `createMediaComments:DataGenerator` = _generate(`MediaComments:DataGenerator`)

    def createDoubleGenerator = _generate(DoubleGenerator)

    def createStringGenerator = _generate(StringGenerator)

    def createLocationsSearchGetResponses200Generator = _generate(LocationsSearchGetResponses200Generator)

    def TagsSearchGetResponses200MetaGenerator = Gen.option(`UsersSelfRequested-byGetResponses200MetaOptGenerator`)

    def IntGenerator = arbitrary[Int]

    def UsersSelfFeedGetResponses200Generator = Gen.option(UsersSelfFeedGetResponses200OptGenerator)

    def LikeUser_nameGenerator = Gen.option(arbitrary[String])

    def `MediaMedia-idCommentsDeleteResponses200MetaGenerator` = Gen.option(`MediaMedia-idLikesGetResponses200MetaOptGenerator`)

    def UsersSelfFeedGetResponses200DataGenerator = Gen.option(UsersSelfFeedGetResponses200DataOptGenerator)

    def `MediaMedia-idLikesGetResponses200DataGenerator` = Gen.option(`MediaMedia-idLikesGetResponses200DataOptGenerator`)

    def MediaUserGenerator = Gen.option(MiniProfileGenerator)

    def MediaVideosLow_resolutionGenerator = Gen.option(ImageGenerator)

    def LocationLongitudeGenerator = Gen.option(arbitrary[Double])

    def `MediaMedia-idCommentsDeleteResponses200Generator` = Gen.option(`MediaMedia-idCommentsDeleteResponses200OptGenerator`)

    def MediaUsers_in_photoOptGenerator = _genList(MiniProfileGenerator, "csv")

    def MediaImagesGenerator = Gen.option(MediaImagesOptGenerator)

    def `UsersUser-idFollowsGetResponses200Generator` = Gen.option(`UsersUser-idFollowsGetResponses200OptGenerator`)

    def `TagsTag-nameMediaRecentGetResponses200Generator` = Gen.option(`TagsTag-nameMediaRecentGetResponses200OptGenerator`)

    def `UsersSelfRequested-byGetResponses200Generator` = Gen.option(`UsersSelfRequested-byGetResponses200OptGenerator`)

    def MediaTagsOptGenerator = _genList(TagGenerator, "csv")

    def MediaLikesGenerator = Gen.option(MediaLikesOptGenerator)

    def MediaVideosGenerator = Gen.option(MediaVideosOptGenerator)

    def `LocationsLocation-idGetResponses200Generator` = Gen.option(`LocationsLocation-idGetResponses200OptGenerator`)

    def MediaCreated_timeGenerator = Gen.option(arbitrary[Int])

    def MediaLocationGenerator = Gen.option(LocationGenerator)

    def `MediaMedia-idLikesGetResponses200DataOptGenerator` = _genList(LikeGenerator, "csv")

    def LocationsSearchGetResponses200DataGenerator = Gen.option(LocationsSearchGetResponses200DataOptGenerator)

    def `TagsTag-nameGetResponses200Generator` = Gen.option(TagGenerator)

    def MediaSearchGetResponses200DataOptGenerator = _genList(MediaSearchGetResponses200DataOptArrGenerator, "csv")

    def `MediaMedia-idLikesGetResponses200Generator` = Gen.option(`MediaMedia-idLikesGetResponses200OptGenerator`)

    def MediaTagsGenerator = Gen.option(MediaTagsOptGenerator)

    def LocationsSearchGetResponses200DataOptGenerator = _genList(LocationGenerator, "csv")

    def MediaSearchGetResponses200Generator = Gen.option(MediaSearchGetResponses200OptGenerator)

    def MediaSearchGetResponses200DataGenerator = Gen.option(MediaSearchGetResponses200DataOptGenerator)

    def UsersSelfFeedGetResponses200DataOptGenerator = _genList(MediaGenerator, "csv")

    def TagsSearchGetResponses200Generator = Gen.option(TagsSearchGetResponses200OptGenerator)

    def `MediaMedia-idGetResponses200Generator` = Gen.option(MediaGenerator)

    def `MediaComments:DataOptGenerator` = _genList(CommentGenerator, "csv")

    def `UsersUser-idGetResponses200DataGenerator` = Gen.option(UserGenerator)

    def `MediaComments:Generator` = Gen.option(`MediaComments:OptGenerator`)

    def `UsersUser-idGetResponses200Generator` = Gen.option(`UsersUser-idGetResponses200OptGenerator`)

    def `MediaMedia-idCommentsGetResponses200Generator` = Gen.option(`MediaMedia-idCommentsGetResponses200OptGenerator`)

    def MediaUsers_in_photoGenerator = Gen.option(MediaUsers_in_photoOptGenerator)

    def NullGenerator = arbitrary[Null]

    def UserCountsGenerator = Gen.option(UserCountsOptGenerator)

    def `MediaComments:DataGenerator` = Gen.option(`MediaComments:DataOptGenerator`)

    def DoubleGenerator = arbitrary[Double]

    def StringGenerator = arbitrary[String]

    def LocationsSearchGetResponses200Generator = Gen.option(LocationsSearchGetResponses200OptGenerator)

    def `createMediaMedia-idLikesGetResponses200OptGenerator` = _generate(`MediaMedia-idLikesGetResponses200OptGenerator`)

    def `createUsersSelfRequested-byGetResponses200OptGenerator` = _generate(`UsersSelfRequested-byGetResponses200OptGenerator`)

    def createTagsSearchGetResponses200OptGenerator = _generate(TagsSearchGetResponses200OptGenerator)

    def `createTagsTag-nameMediaRecentGetResponses200OptGenerator` = _generate(`TagsTag-nameMediaRecentGetResponses200OptGenerator`)

    def createMediaSearchGetResponses200OptGenerator = _generate(MediaSearchGetResponses200OptGenerator)

    def createUserGenerator = _generate(UserGenerator)

    def createImageGenerator = _generate(ImageGenerator)

    def createTagGenerator = _generate(TagGenerator)

    def `createUsersSelfRequested-byGetResponses200MetaOptGenerator` = _generate(`UsersSelfRequested-byGetResponses200MetaOptGenerator`)

    def createMediaSearchGetResponses200DataOptArrGenerator = _generate(MediaSearchGetResponses200DataOptArrGenerator)

    def createCommentGenerator = _generate(CommentGenerator)

    def createMediaGenerator = _generate(MediaGenerator)

    def createMediaVideosOptGenerator = _generate(MediaVideosOptGenerator)

    def `createMediaMedia-idLikesGetResponses200MetaOptGenerator` = _generate(`MediaMedia-idLikesGetResponses200MetaOptGenerator`)

    def createLikeGenerator = _generate(LikeGenerator)

    def createMediaLikesOptGenerator = _generate(MediaLikesOptGenerator)

    def `createMediaMedia-idCommentsDeleteResponses200OptGenerator` = _generate(`MediaMedia-idCommentsDeleteResponses200OptGenerator`)

    def createUsersSelfFeedGetResponses200OptGenerator = _generate(UsersSelfFeedGetResponses200OptGenerator)

    def createLocationGenerator = _generate(LocationGenerator)

    def createLocationsSearchGetResponses200OptGenerator = _generate(LocationsSearchGetResponses200OptGenerator)

    def createMiniProfileGenerator = _generate(MiniProfileGenerator)

    def createMediaImagesOptGenerator = _generate(MediaImagesOptGenerator)

    def `createLocationsLocation-idGetResponses200OptGenerator` = _generate(`LocationsLocation-idGetResponses200OptGenerator`)

    def `createUsersUser-idGetResponses200OptGenerator` = _generate(`UsersUser-idGetResponses200OptGenerator`)

    def createUserCountsOptGenerator = _generate(UserCountsOptGenerator)

    def `createUsersUser-idFollowsGetResponses200OptGenerator` = _generate(`UsersUser-idFollowsGetResponses200OptGenerator`)

    def `createMediaComments:OptGenerator` = _generate(`MediaComments:OptGenerator`)

    def `createMediaMedia-idCommentsGetResponses200OptGenerator` = _generate(`MediaMedia-idCommentsGetResponses200OptGenerator`)

    def `MediaMedia-idLikesGetResponses200OptGenerator` = for {
        meta <- `MediaMedia-idCommentsDeleteResponses200MetaGenerator`
        data <- `MediaMedia-idLikesGetResponses200DataGenerator`
        } yield `MediaMedia-idLikesGetResponses200Opt`(meta, data)

    def `UsersSelfRequested-byGetResponses200OptGenerator` = for {
        meta <- TagsSearchGetResponses200MetaGenerator
        data <- MediaUsers_in_photoGenerator
        } yield `UsersSelfRequested-byGetResponses200Opt`(meta, data)

    def TagsSearchGetResponses200OptGenerator = for {
        meta <- TagsSearchGetResponses200MetaGenerator
        data <- MediaTagsGenerator
        } yield TagsSearchGetResponses200Opt(meta, data)

    def `TagsTag-nameMediaRecentGetResponses200OptGenerator` = for {
        data <- MediaTagsGenerator
        } yield `TagsTag-nameMediaRecentGetResponses200Opt`(data)

    def MediaSearchGetResponses200OptGenerator = for {
        data <- MediaSearchGetResponses200DataGenerator
        } yield MediaSearchGetResponses200Opt(data)

    def UserGenerator = for {
        website <- LikeUser_nameGenerator
        profile_picture <- LikeUser_nameGenerator
        username <- LikeUser_nameGenerator
        full_name <- LikeUser_nameGenerator
        bio <- LikeUser_nameGenerator
        id <- MediaCreated_timeGenerator
        counts <- UserCountsGenerator
        } yield User(website, profile_picture, username, full_name, bio, id, counts)

    def ImageGenerator = for {
        width <- MediaCreated_timeGenerator
        height <- MediaCreated_timeGenerator
        url <- LikeUser_nameGenerator
        } yield Image(width, height, url)

    def TagGenerator = for {
        media_count <- MediaCreated_timeGenerator
        name <- LikeUser_nameGenerator
        } yield Tag(media_count, name)

    def `UsersSelfRequested-byGetResponses200MetaOptGenerator` = for {
        code <- MediaCreated_timeGenerator
        } yield `UsersSelfRequested-byGetResponses200MetaOpt`(code)

    def MediaSearchGetResponses200DataOptArrGenerator = for {
        location <- MediaLocationGenerator
        created_time <- MediaCreated_timeGenerator
        `comments:` <- `MediaComments:Generator`
        tags <- MediaTagsGenerator
        users_in_photo <- MediaUsers_in_photoGenerator
        filter <- LikeUser_nameGenerator
        likes <- MediaLikesGenerator
        id <- MediaCreated_timeGenerator
        videos <- MediaVideosGenerator
        `type` <- LikeUser_nameGenerator
        images <- MediaImagesGenerator
        user <- MediaUserGenerator
        distance <- LocationLongitudeGenerator
        } yield MediaSearchGetResponses200DataOptArr(location, created_time, `comments:`, tags, users_in_photo, filter, likes, id, videos, `type`, images, user, distance)

    def CommentGenerator = for {
        id <- LikeUser_nameGenerator
        created_time <- LikeUser_nameGenerator
        text <- LikeUser_nameGenerator
        from <- MediaUserGenerator
        } yield Comment(id, created_time, text, from)

    def MediaGenerator = for {
        location <- MediaLocationGenerator
        created_time <- MediaCreated_timeGenerator
        `comments:` <- `MediaComments:Generator`
        tags <- MediaTagsGenerator
        users_in_photo <- MediaUsers_in_photoGenerator
        filter <- LikeUser_nameGenerator
        likes <- MediaLikesGenerator
        id <- MediaCreated_timeGenerator
        videos <- MediaVideosGenerator
        `type` <- LikeUser_nameGenerator
        images <- MediaImagesGenerator
        user <- MediaUserGenerator
        } yield Media(location, created_time, `comments:`, tags, users_in_photo, filter, likes, id, videos, `type`, images, user)

    def MediaVideosOptGenerator = for {
        low_resolution <- MediaVideosLow_resolutionGenerator
        standard_resolution <- MediaVideosLow_resolutionGenerator
        } yield MediaVideosOpt(low_resolution, standard_resolution)

    def `MediaMedia-idLikesGetResponses200MetaOptGenerator` = for {
        code <- LocationLongitudeGenerator
        } yield `MediaMedia-idLikesGetResponses200MetaOpt`(code)

    def LikeGenerator = for {
        first_name <- LikeUser_nameGenerator
        id <- LikeUser_nameGenerator
        last_name <- LikeUser_nameGenerator
        `type` <- LikeUser_nameGenerator
        user_name <- LikeUser_nameGenerator
        } yield Like(first_name, id, last_name, `type`, user_name)

    def MediaLikesOptGenerator = for {
        count <- MediaCreated_timeGenerator
        data <- MediaUsers_in_photoGenerator
        } yield MediaLikesOpt(count, data)

    def `MediaMedia-idCommentsDeleteResponses200OptGenerator` = for {
        meta <- `MediaMedia-idCommentsDeleteResponses200MetaGenerator`
        data <- LikeUser_nameGenerator
        } yield `MediaMedia-idCommentsDeleteResponses200Opt`(meta, data)

    def UsersSelfFeedGetResponses200OptGenerator = for {
        data <- UsersSelfFeedGetResponses200DataGenerator
        } yield UsersSelfFeedGetResponses200Opt(data)

    def LocationGenerator = for {
        id <- LikeUser_nameGenerator
        name <- LikeUser_nameGenerator
        latitude <- LocationLongitudeGenerator
        longitude <- LocationLongitudeGenerator
        } yield Location(id, name, latitude, longitude)

    def LocationsSearchGetResponses200OptGenerator = for {
        data <- LocationsSearchGetResponses200DataGenerator
        } yield LocationsSearchGetResponses200Opt(data)

    def MiniProfileGenerator = for {
        user_name <- LikeUser_nameGenerator
        full_name <- LikeUser_nameGenerator
        id <- MediaCreated_timeGenerator
        profile_picture <- LikeUser_nameGenerator
        } yield MiniProfile(user_name, full_name, id, profile_picture)

    def MediaImagesOptGenerator = for {
        low_resolution <- MediaVideosLow_resolutionGenerator
        thumbnail <- MediaVideosLow_resolutionGenerator
        standard_resolution <- MediaVideosLow_resolutionGenerator
        } yield MediaImagesOpt(low_resolution, thumbnail, standard_resolution)

    def `LocationsLocation-idGetResponses200OptGenerator` = for {
        data <- MediaLocationGenerator
        } yield `LocationsLocation-idGetResponses200Opt`(data)

    def `UsersUser-idGetResponses200OptGenerator` = for {
        data <- `UsersUser-idGetResponses200DataGenerator`
        } yield `UsersUser-idGetResponses200Opt`(data)

    def UserCountsOptGenerator = for {
        media <- MediaCreated_timeGenerator
        follows <- MediaCreated_timeGenerator
        follwed_by <- MediaCreated_timeGenerator
        } yield UserCountsOpt(media, follows, follwed_by)

    def `UsersUser-idFollowsGetResponses200OptGenerator` = for {
        data <- MediaUsers_in_photoGenerator
        } yield `UsersUser-idFollowsGetResponses200Opt`(data)

    def `MediaComments:OptGenerator` = for {
        count <- MediaCreated_timeGenerator
        data <- `MediaComments:DataGenerator`
        } yield `MediaComments:Opt`(count, data)

    def `MediaMedia-idCommentsGetResponses200OptGenerator` = for {
        meta <- `MediaMedia-idCommentsDeleteResponses200MetaGenerator`
        data <- `MediaComments:DataGenerator`
        } yield `MediaMedia-idCommentsGetResponses200Opt`(meta, data)

    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
    def _genList[T](gen: Gen[T], format: String): Gen[ArrayWrapper[T]] = for {
        items <- Gen.containerOf[List,T](gen)
    } yield ArrayWrapper(format)(items)
}
