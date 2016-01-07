package instagram.api.yaml

import org.scalacheck.Gen
import org.scalacheck.Arbitrary
import Arbitrary._

import de.zalando.play.controllers.ArrayWrapper
object Generators {
    def createTagsSearchGetResponses200MetaGenerator = _generate(TagsSearchGetResponses200MetaGenerator)

    def createIntGenerator = _generate(IntGenerator)

    def createLikeUser_nameGenerator = _generate(LikeUser_nameGenerator)

    def `createMediaMedia-idCommentsDeleteResponses200MetaGenerator` = _generate(`MediaMedia-idCommentsDeleteResponses200MetaGenerator`)

    def createUsersSelfFeedGetResponses200DataGenerator = _generate(UsersSelfFeedGetResponses200DataGenerator)

    def `createMediaMedia-idLikesGetResponses200DataGenerator` = _generate(`MediaMedia-idLikesGetResponses200DataGenerator`)

    def createMediaUserGenerator = _generate(MediaUserGenerator)

    def createMediaVideosLow_resolutionGenerator = _generate(MediaVideosLow_resolutionGenerator)

    def createLocationLongitudeGenerator = _generate(LocationLongitudeGenerator)

    def createMediaUsers_in_photoOptGenerator = _generate(MediaUsers_in_photoOptGenerator)

    def createMediaImagesGenerator = _generate(MediaImagesGenerator)

    def createMediaTagsOptGenerator = _generate(MediaTagsOptGenerator)

    def createMediaLikesGenerator = _generate(MediaLikesGenerator)

    def createMediaVideosGenerator = _generate(MediaVideosGenerator)

    def createMediaCreated_timeGenerator = _generate(MediaCreated_timeGenerator)

    def createMediaLocationGenerator = _generate(MediaLocationGenerator)

    def `createMediaMedia-idLikesGetResponses200DataOptGenerator` = _generate(`MediaMedia-idLikesGetResponses200DataOptGenerator`)

    def createLocationsSearchGetResponses200DataGenerator = _generate(LocationsSearchGetResponses200DataGenerator)

    def createMediaSearchGetResponses200DataOptGenerator = _generate(MediaSearchGetResponses200DataOptGenerator)

    def createMediaTagsGenerator = _generate(MediaTagsGenerator)

    def createLocationsSearchGetResponses200DataOptGenerator = _generate(LocationsSearchGetResponses200DataOptGenerator)

    def createMediaSearchGetResponses200DataGenerator = _generate(MediaSearchGetResponses200DataGenerator)

    def createUsersSelfFeedGetResponses200DataOptGenerator = _generate(UsersSelfFeedGetResponses200DataOptGenerator)

    def `createMediaComments:DataOptGenerator` = _generate(`MediaComments:DataOptGenerator`)

    def `createUsersUser-idGetResponses200DataGenerator` = _generate(`UsersUser-idGetResponses200DataGenerator`)

    def `createMediaComments:Generator` = _generate(`MediaComments:Generator`)

    def createMediaUsers_in_photoGenerator = _generate(MediaUsers_in_photoGenerator)

    def createNullGenerator = _generate(NullGenerator)

    def createUserCountsGenerator = _generate(UserCountsGenerator)

    def `createMediaComments:DataGenerator` = _generate(`MediaComments:DataGenerator`)

    def createDoubleGenerator = _generate(DoubleGenerator)

    def createStringGenerator = _generate(StringGenerator)

    def TagsSearchGetResponses200MetaGenerator = Gen.option(`UsersSelfRequested-byGetResponses200MetaOptGenerator`)

    def IntGenerator = arbitrary[Int]

    def LikeUser_nameGenerator = Gen.option(arbitrary[String])

    def `MediaMedia-idCommentsDeleteResponses200MetaGenerator` = Gen.option(`MediaMedia-idLikesGetResponses200MetaOptGenerator`)

    def UsersSelfFeedGetResponses200DataGenerator = Gen.option(UsersSelfFeedGetResponses200DataOptGenerator)

    def `MediaMedia-idLikesGetResponses200DataGenerator` = Gen.option(`MediaMedia-idLikesGetResponses200DataOptGenerator`)

    def MediaUserGenerator = Gen.option(MiniProfileGenerator)

    def MediaVideosLow_resolutionGenerator = Gen.option(ImageGenerator)

    def LocationLongitudeGenerator = Gen.option(arbitrary[Double])

    def MediaUsers_in_photoOptGenerator = _genList(MiniProfileGenerator, "csv")

    def MediaImagesGenerator = Gen.option(MediaImagesOptGenerator)

    def MediaTagsOptGenerator = _genList(TagGenerator, "csv")

    def MediaLikesGenerator = Gen.option(MediaLikesOptGenerator)

    def MediaVideosGenerator = Gen.option(MediaVideosOptGenerator)

    def MediaCreated_timeGenerator = Gen.option(arbitrary[Int])

    def MediaLocationGenerator = Gen.option(LocationGenerator)

    def `MediaMedia-idLikesGetResponses200DataOptGenerator` = _genList(LikeGenerator, "csv")

    def LocationsSearchGetResponses200DataGenerator = Gen.option(LocationsSearchGetResponses200DataOptGenerator)

    def MediaSearchGetResponses200DataOptGenerator = _genList(MediaSearchGetResponses200DataOptArrGenerator, "csv")

    def MediaTagsGenerator = Gen.option(MediaTagsOptGenerator)

    def LocationsSearchGetResponses200DataOptGenerator = _genList(LocationGenerator, "csv")

    def MediaSearchGetResponses200DataGenerator = Gen.option(MediaSearchGetResponses200DataOptGenerator)

    def UsersSelfFeedGetResponses200DataOptGenerator = _genList(MediaGenerator, "csv")

    def `MediaComments:DataOptGenerator` = _genList(CommentGenerator, "csv")

    def `UsersUser-idGetResponses200DataGenerator` = Gen.option(UserGenerator)

    def `MediaComments:Generator` = Gen.option(`MediaComments:OptGenerator`)

    def MediaUsers_in_photoGenerator = Gen.option(MediaUsers_in_photoOptGenerator)

    def NullGenerator = arbitrary[Null]

    def UserCountsGenerator = Gen.option(UserCountsOptGenerator)

    def `MediaComments:DataGenerator` = Gen.option(`MediaComments:DataOptGenerator`)

    def DoubleGenerator = arbitrary[Double]

    def StringGenerator = arbitrary[String]

    def createUsersSelfFeedGetResponses200Generator = _generate(UsersSelfFeedGetResponses200Generator)

    def `createMediaMedia-idCommentsDeleteResponses200Generator` = _generate(`MediaMedia-idCommentsDeleteResponses200Generator`)

    def `createUsersUser-idFollowsGetResponses200Generator` = _generate(`UsersUser-idFollowsGetResponses200Generator`)

    def createUserGenerator = _generate(UserGenerator)

    def `createTagsTag-nameMediaRecentGetResponses200Generator` = _generate(`TagsTag-nameMediaRecentGetResponses200Generator`)

    def createImageGenerator = _generate(ImageGenerator)

    def `createUsersSelfRequested-byGetResponses200Generator` = _generate(`UsersSelfRequested-byGetResponses200Generator`)

    def createTagGenerator = _generate(TagGenerator)

    def `createUsersSelfRequested-byGetResponses200MetaOptGenerator` = _generate(`UsersSelfRequested-byGetResponses200MetaOptGenerator`)

    def createMediaSearchGetResponses200DataOptArrGenerator = _generate(MediaSearchGetResponses200DataOptArrGenerator)

    def `createLocationsLocation-idGetResponses200Generator` = _generate(`LocationsLocation-idGetResponses200Generator`)

    def createCommentGenerator = _generate(CommentGenerator)

    def createMediaGenerator = _generate(MediaGenerator)

    def createMediaVideosOptGenerator = _generate(MediaVideosOptGenerator)

    def `createMediaMedia-idLikesGetResponses200Generator` = _generate(`MediaMedia-idLikesGetResponses200Generator`)

    def `createMediaMedia-idLikesGetResponses200MetaOptGenerator` = _generate(`MediaMedia-idLikesGetResponses200MetaOptGenerator`)

    def createMediaSearchGetResponses200Generator = _generate(MediaSearchGetResponses200Generator)

    def createTagsSearchGetResponses200Generator = _generate(TagsSearchGetResponses200Generator)

    def createLikeGenerator = _generate(LikeGenerator)

    def createMediaLikesOptGenerator = _generate(MediaLikesOptGenerator)

    def `createUsersUser-idGetResponses200Generator` = _generate(`UsersUser-idGetResponses200Generator`)

    def `createMediaMedia-idCommentsGetResponses200Generator` = _generate(`MediaMedia-idCommentsGetResponses200Generator`)

    def createLocationGenerator = _generate(LocationGenerator)

    def createMiniProfileGenerator = _generate(MiniProfileGenerator)

    def createMediaImagesOptGenerator = _generate(MediaImagesOptGenerator)

    def createUserCountsOptGenerator = _generate(UserCountsOptGenerator)

    def `createMediaComments:OptGenerator` = _generate(`MediaComments:OptGenerator`)

    def createLocationsSearchGetResponses200Generator = _generate(LocationsSearchGetResponses200Generator)

    def UsersSelfFeedGetResponses200Generator = for {
        data <- UsersSelfFeedGetResponses200DataGenerator
        } yield UsersSelfFeedGetResponses200(data)

    def `MediaMedia-idCommentsDeleteResponses200Generator` = for {
        meta <- `MediaMedia-idCommentsDeleteResponses200MetaGenerator`
        data <- LikeUser_nameGenerator
        } yield `MediaMedia-idCommentsDeleteResponses200`(meta, data)

    def `UsersUser-idFollowsGetResponses200Generator` = for {
        data <- MediaUsers_in_photoGenerator
        } yield `UsersUser-idFollowsGetResponses200`(data)

    def UserGenerator = for {
        website <- LikeUser_nameGenerator
        profile_picture <- LikeUser_nameGenerator
        username <- LikeUser_nameGenerator
        full_name <- LikeUser_nameGenerator
        bio <- LikeUser_nameGenerator
        id <- MediaCreated_timeGenerator
        counts <- UserCountsGenerator
        } yield User(website, profile_picture, username, full_name, bio, id, counts)

    def `TagsTag-nameMediaRecentGetResponses200Generator` = for {
        data <- MediaTagsGenerator
        } yield `TagsTag-nameMediaRecentGetResponses200`(data)

    def ImageGenerator = for {
        width <- MediaCreated_timeGenerator
        height <- MediaCreated_timeGenerator
        url <- LikeUser_nameGenerator
        } yield Image(width, height, url)

    def `UsersSelfRequested-byGetResponses200Generator` = for {
        meta <- TagsSearchGetResponses200MetaGenerator
        data <- MediaUsers_in_photoGenerator
        } yield `UsersSelfRequested-byGetResponses200`(meta, data)

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

    def `LocationsLocation-idGetResponses200Generator` = for {
        data <- MediaLocationGenerator
        } yield `LocationsLocation-idGetResponses200`(data)

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

    def `MediaMedia-idLikesGetResponses200Generator` = for {
        meta <- `MediaMedia-idCommentsDeleteResponses200MetaGenerator`
        data <- `MediaMedia-idLikesGetResponses200DataGenerator`
        } yield `MediaMedia-idLikesGetResponses200`(meta, data)

    def `MediaMedia-idLikesGetResponses200MetaOptGenerator` = for {
        code <- LocationLongitudeGenerator
        } yield `MediaMedia-idLikesGetResponses200MetaOpt`(code)

    def MediaSearchGetResponses200Generator = for {
        data <- MediaSearchGetResponses200DataGenerator
        } yield MediaSearchGetResponses200(data)

    def TagsSearchGetResponses200Generator = for {
        meta <- TagsSearchGetResponses200MetaGenerator
        data <- MediaTagsGenerator
        } yield TagsSearchGetResponses200(meta, data)

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

    def `UsersUser-idGetResponses200Generator` = for {
        data <- `UsersUser-idGetResponses200DataGenerator`
        } yield `UsersUser-idGetResponses200`(data)

    def `MediaMedia-idCommentsGetResponses200Generator` = for {
        meta <- `MediaMedia-idCommentsDeleteResponses200MetaGenerator`
        data <- `MediaComments:DataGenerator`
        } yield `MediaMedia-idCommentsGetResponses200`(meta, data)

    def LocationGenerator = for {
        id <- LikeUser_nameGenerator
        name <- LikeUser_nameGenerator
        latitude <- LocationLongitudeGenerator
        longitude <- LocationLongitudeGenerator
        } yield Location(id, name, latitude, longitude)

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

    def UserCountsOptGenerator = for {
        media <- MediaCreated_timeGenerator
        follows <- MediaCreated_timeGenerator
        follwed_by <- MediaCreated_timeGenerator
        } yield UserCountsOpt(media, follows, follwed_by)

    def `MediaComments:OptGenerator` = for {
        count <- MediaCreated_timeGenerator
        data <- `MediaComments:DataGenerator`
        } yield `MediaComments:Opt`(count, data)

    def LocationsSearchGetResponses200Generator = for {
        data <- LocationsSearchGetResponses200DataGenerator
        } yield LocationsSearchGetResponses200(data)

    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
    def _genList[T](gen: Gen[T], format: String): Gen[ArrayWrapper[T]] = for {
        items <- Gen.containerOf[List,T](gen)
    } yield ArrayWrapper(format)(items)
    }