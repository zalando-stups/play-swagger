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

    def createMediaTagsGenerator = _generate(MediaTagsGenerator)

    def `createMediaMedia-idLikesGetResponses200DataGenerator` = _generate(`MediaMedia-idLikesGetResponses200DataGenerator`)

    def createMediaUserGenerator = _generate(MediaUserGenerator)

    def createMediaVideosLow_resolutionGenerator = _generate(MediaVideosLow_resolutionGenerator)

    def createLocationLongitudeGenerator = _generate(LocationLongitudeGenerator)

    def createMediaTagsOptGenerator = _generate(MediaTagsOptGenerator)

    def createMediaUsers_in_photoOptGenerator = _generate(MediaUsers_in_photoOptGenerator)

    def createMediaImagesGenerator = _generate(MediaImagesGenerator)

    def createMediaLikesGenerator = _generate(MediaLikesGenerator)

    def createMediaTagsOptNameClashGenerator = _generate(MediaTagsOptNameClashGenerator)

    def createMediaLikesNameClashGenerator = _generate(MediaLikesNameClashGenerator)

    def createMediaVideosGenerator = _generate(MediaVideosGenerator)

    def `createMediaMedia-idCommentsGetResponses200DataOptGenerator` = _generate(`MediaMedia-idCommentsGetResponses200DataOptGenerator`)

    def createMediaUsers_in_photoOptNameClashGenerator = _generate(MediaUsers_in_photoOptNameClashGenerator)

    def createMediaCreated_timeGenerator = _generate(MediaCreated_timeGenerator)

    def createMediaLocationGenerator = _generate(MediaLocationGenerator)

    def `createMediaMedia-idLikesGetResponses200DataOptGenerator` = _generate(`MediaMedia-idLikesGetResponses200DataOptGenerator`)

    def createLocationsSearchGetResponses200DataGenerator = _generate(LocationsSearchGetResponses200DataGenerator)

    def `createMediaComments:Generator` = _generate(`MediaComments:Generator`)

    def createMediaSearchGetResponses200DataOptGenerator = _generate(MediaSearchGetResponses200DataOptGenerator)

    def createMediaTagsNameClashGenerator = _generate(MediaTagsNameClashGenerator)

    def createLocationsSearchGetResponses200DataOptGenerator = _generate(LocationsSearchGetResponses200DataOptGenerator)

    def createMediaSearchGetResponses200DataGenerator = _generate(MediaSearchGetResponses200DataGenerator)

    def createUsersSelfFeedGetResponses200DataOptGenerator = _generate(UsersSelfFeedGetResponses200DataOptGenerator)

    def `createMediaComments:DataOptGenerator` = _generate(`MediaComments:DataOptGenerator`)

    def `createUsersUser-idGetResponses200DataGenerator` = _generate(`UsersUser-idGetResponses200DataGenerator`)

    def `createMediaComments:`NameClashGenerator = _generate(`MediaComments:`NameClashGenerator)

    def createMediaUsers_in_photoGenerator = _generate(MediaUsers_in_photoGenerator)

    def createNullGenerator = _generate(NullGenerator)

    def createUserCountsGenerator = _generate(UserCountsGenerator)

    def `createMediaComments:DataGenerator` = _generate(`MediaComments:DataGenerator`)

    def createMediaUsers_in_photoNameClashGenerator = _generate(MediaUsers_in_photoNameClashGenerator)

    def `createMediaMedia-idCommentsGetResponses200DataGenerator` = _generate(`MediaMedia-idCommentsGetResponses200DataGenerator`)

    def createDoubleGenerator = _generate(DoubleGenerator)

    def createStringGenerator = _generate(StringGenerator)

    def TagsSearchGetResponses200MetaGenerator = Gen.option(`UsersSelfRequested-byGetResponses200MetaOptGenerator`)

    def IntGenerator = arbitrary[Int]

    def LikeUser_nameGenerator = Gen.option(arbitrary[String])

    def `MediaMedia-idCommentsDeleteResponses200MetaGenerator` = Gen.option(`MediaMedia-idLikesGetResponses200MetaOptGenerator`)

    def UsersSelfFeedGetResponses200DataGenerator = Gen.option(UsersSelfFeedGetResponses200DataOptGenerator)

    def MediaTagsGenerator = Gen.option(MediaTagsOptGenerator)

    def `MediaMedia-idLikesGetResponses200DataGenerator` = Gen.option(`MediaMedia-idLikesGetResponses200DataOptGenerator`)

    def MediaUserGenerator = Gen.option(MiniProfileGenerator)

    def MediaVideosLow_resolutionGenerator = Gen.option(ImageGenerator)

    def LocationLongitudeGenerator = Gen.option(arbitrary[Double])

    def MediaTagsOptGenerator = _genList(TagGenerator, "csv")

    def MediaUsers_in_photoOptGenerator = Gen.containerOf[List,MiniProfile](MiniProfileGenerator)

    def MediaImagesGenerator = Gen.option(MediaImagesOptGenerator)

    def MediaLikesGenerator = Gen.option(MediaLikesOptNameClashGenerator)

    def MediaTagsOptNameClashGenerator = Gen.containerOf[List,Tag](TagGenerator)

    def MediaLikesNameClashGenerator = Gen.option(MediaLikesOptGenerator)

    def MediaVideosGenerator = Gen.option(MediaVideosOptGenerator)

    def `MediaMedia-idCommentsGetResponses200DataOptGenerator` = Gen.containerOf[List,Comment](CommentGenerator)

    def MediaUsers_in_photoOptNameClashGenerator = _genList(MiniProfileGenerator, "csv")

    def MediaCreated_timeGenerator = Gen.option(arbitrary[Int])

    def MediaLocationGenerator = Gen.option(LocationGenerator)

    def `MediaMedia-idLikesGetResponses200DataOptGenerator` = Gen.containerOf[List,Like](LikeGenerator)

    def LocationsSearchGetResponses200DataGenerator = Gen.option(LocationsSearchGetResponses200DataOptGenerator)

    def `MediaComments:Generator` = Gen.option(`MediaComments:OptGenerator`)

    def MediaSearchGetResponses200DataOptGenerator = Gen.containerOf[List,MediaSearchGetResponses200DataOptArrResult](MediaSearchGetResponses200DataOptArrResultGenerator)

    def MediaTagsNameClashGenerator = Gen.option(MediaTagsOptNameClashGenerator)

    def LocationsSearchGetResponses200DataOptGenerator = Gen.containerOf[List,Location](LocationGenerator)

    def MediaSearchGetResponses200DataGenerator = Gen.option(MediaSearchGetResponses200DataOptGenerator)

    def UsersSelfFeedGetResponses200DataOptGenerator = Gen.containerOf[List,`MediaMedia-idGetResponses200`](`MediaMedia-idGetResponses200Generator`)

    def `MediaComments:DataOptGenerator` = _genList(CommentGenerator, "csv")

    def `UsersUser-idGetResponses200DataGenerator` = Gen.option(UserGenerator)

    def `MediaComments:`NameClashGenerator = Gen.option(`MediaComments:Opt`NameClashGenerator)

    def MediaUsers_in_photoGenerator = Gen.option(MediaUsers_in_photoOptGenerator)

    def NullGenerator = arbitrary[Null]

    def UserCountsGenerator = Gen.option(UserCountsOptGenerator)

    def `MediaComments:DataGenerator` = Gen.option(`MediaComments:DataOptGenerator`)

    def MediaUsers_in_photoNameClashGenerator = Gen.option(MediaUsers_in_photoOptNameClashGenerator)

    def `MediaMedia-idCommentsGetResponses200DataGenerator` = Gen.option(`MediaMedia-idCommentsGetResponses200DataOptGenerator`)

    def DoubleGenerator = arbitrary[Double]

    def StringGenerator = arbitrary[String]

    def createUsersSelfFeedGetResponses200Generator = _generate(UsersSelfFeedGetResponses200Generator)

    def `createMediaMedia-idCommentsDeleteResponses200Generator` = _generate(`MediaMedia-idCommentsDeleteResponses200Generator`)

    def createMediaSearchGetResponses200DataOptArrResultGenerator = _generate(MediaSearchGetResponses200DataOptArrResultGenerator)

    def `createUsersUser-idFollowsGetResponses200Generator` = _generate(`UsersUser-idFollowsGetResponses200Generator`)

    def createUserGenerator = _generate(UserGenerator)

    def `createTagsTag-nameMediaRecentGetResponses200Generator` = _generate(`TagsTag-nameMediaRecentGetResponses200Generator`)

    def createImageGenerator = _generate(ImageGenerator)

    def `createUsersSelfRequested-byGetResponses200Generator` = _generate(`UsersSelfRequested-byGetResponses200Generator`)

    def createTagGenerator = _generate(TagGenerator)

    def `createUsersSelfRequested-byGetResponses200MetaOptGenerator` = _generate(`UsersSelfRequested-byGetResponses200MetaOptGenerator`)

    def `createLocationsLocation-idGetResponses200Generator` = _generate(`LocationsLocation-idGetResponses200Generator`)

    def createCommentGenerator = _generate(CommentGenerator)

    def createMediaGenerator = _generate(MediaGenerator)

    def createMediaVideosOptGenerator = _generate(MediaVideosOptGenerator)

    def `createMediaMedia-idLikesGetResponses200Generator` = _generate(`MediaMedia-idLikesGetResponses200Generator`)

    def `createMediaMedia-idLikesGetResponses200MetaOptGenerator` = _generate(`MediaMedia-idLikesGetResponses200MetaOptGenerator`)

    def createMediaSearchGetResponses200Generator = _generate(MediaSearchGetResponses200Generator)

    def createTagsSearchGetResponses200Generator = _generate(TagsSearchGetResponses200Generator)

    def createLikeGenerator = _generate(LikeGenerator)

    def `createMediaMedia-idGetResponses200Generator` = _generate(`MediaMedia-idGetResponses200Generator`)

    def `createMediaComments:OptGenerator` = _generate(`MediaComments:OptGenerator`)

    def createMediaLikesOptGenerator = _generate(MediaLikesOptGenerator)

    def `createUsersUser-idGetResponses200Generator` = _generate(`UsersUser-idGetResponses200Generator`)

    def `createMediaMedia-idCommentsGetResponses200Generator` = _generate(`MediaMedia-idCommentsGetResponses200Generator`)

    def createLocationGenerator = _generate(LocationGenerator)

    def createMiniProfileGenerator = _generate(MiniProfileGenerator)

    def createMediaImagesOptGenerator = _generate(MediaImagesOptGenerator)

    def createMediaLikesOptNameClashGenerator = _generate(MediaLikesOptNameClashGenerator)

    def createUserCountsOptGenerator = _generate(UserCountsOptGenerator)

    def `createMediaComments:Opt`NameClashGenerator = _generate(`MediaComments:Opt`NameClashGenerator)

    def createLocationsSearchGetResponses200Generator = _generate(LocationsSearchGetResponses200Generator)

    def UsersSelfFeedGetResponses200Generator = for {
        data <- UsersSelfFeedGetResponses200DataGenerator
        } yield UsersSelfFeedGetResponses200(data)

    def `MediaMedia-idCommentsDeleteResponses200Generator` = for {
        meta <- `MediaMedia-idCommentsDeleteResponses200MetaGenerator`
        data <- LikeUser_nameGenerator
        } yield `MediaMedia-idCommentsDeleteResponses200`(meta, data)

    def MediaSearchGetResponses200DataOptArrResultGenerator = for {
        location <- MediaLocationGenerator
        created_time <- MediaCreated_timeGenerator
        `comments:` <- `MediaComments:`NameClashGenerator
        tags <- MediaTagsNameClashGenerator
        users_in_photo <- MediaUsers_in_photoGenerator
        filter <- LikeUser_nameGenerator
        likes <- MediaLikesNameClashGenerator
        id <- MediaCreated_timeGenerator
        videos <- MediaVideosGenerator
        `type` <- LikeUser_nameGenerator
        images <- MediaImagesGenerator
        user <- MediaUserGenerator
        distance <- LocationLongitudeGenerator
        } yield MediaSearchGetResponses200DataOptArrResult(location, created_time, `comments:`, tags, users_in_photo, filter, likes, id, videos, `type`, images, user, distance)

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
        data <- MediaTagsNameClashGenerator
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
        users_in_photo <- MediaUsers_in_photoNameClashGenerator
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
        data <- MediaTagsNameClashGenerator
        } yield TagsSearchGetResponses200(meta, data)

    def LikeGenerator = for {
        first_name <- LikeUser_nameGenerator
        id <- LikeUser_nameGenerator
        last_name <- LikeUser_nameGenerator
        `type` <- LikeUser_nameGenerator
        user_name <- LikeUser_nameGenerator
        } yield Like(first_name, id, last_name, `type`, user_name)

    def `MediaMedia-idGetResponses200Generator` = for {
        location <- MediaLocationGenerator
        created_time <- MediaCreated_timeGenerator
        `comments:` <- `MediaComments:`NameClashGenerator
        tags <- MediaTagsNameClashGenerator
        users_in_photo <- MediaUsers_in_photoGenerator
        filter <- LikeUser_nameGenerator
        likes <- MediaLikesNameClashGenerator
        id <- MediaCreated_timeGenerator
        videos <- MediaVideosGenerator
        `type` <- LikeUser_nameGenerator
        images <- MediaImagesGenerator
        user <- MediaUserGenerator
        } yield `MediaMedia-idGetResponses200`(location, created_time, `comments:`, tags, users_in_photo, filter, likes, id, videos, `type`, images, user)

    def `MediaComments:OptGenerator` = for {
        count <- MediaCreated_timeGenerator
        data <- `MediaComments:DataGenerator`
        } yield `MediaComments:Opt`(count, data)

    def MediaLikesOptGenerator = for {
        count <- MediaCreated_timeGenerator
        data <- MediaUsers_in_photoGenerator
        } yield MediaLikesOpt(count, data)

    def `UsersUser-idGetResponses200Generator` = for {
        data <- `UsersUser-idGetResponses200DataGenerator`
        } yield `UsersUser-idGetResponses200`(data)

    def `MediaMedia-idCommentsGetResponses200Generator` = for {
        meta <- `MediaMedia-idCommentsDeleteResponses200MetaGenerator`
        data <- `MediaMedia-idCommentsGetResponses200DataGenerator`
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

    def MediaLikesOptNameClashGenerator = for {
        count <- MediaCreated_timeGenerator
        data <- MediaUsers_in_photoNameClashGenerator
        } yield MediaLikesOptNameClash(count, data)

    def UserCountsOptGenerator = for {
        media <- MediaCreated_timeGenerator
        follows <- MediaCreated_timeGenerator
        follwed_by <- MediaCreated_timeGenerator
        } yield UserCountsOpt(media, follows, follwed_by)

    def `MediaComments:Opt`NameClashGenerator = for {
        count <- MediaCreated_timeGenerator
        data <- `MediaMedia-idCommentsGetResponses200DataGenerator`
        } yield `MediaComments:Opt`NameClash(count, data)

    def LocationsSearchGetResponses200Generator = for {
        data <- LocationsSearchGetResponses200DataGenerator
        } yield LocationsSearchGetResponses200(data)

    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
    def _genList[T](gen: Gen[T], format: String): Gen[ArrayWrapper[T]] = for {
        items <- Gen.containerOf[List,T](gen)
    } yield ArrayWrapper(format)(items)
    }