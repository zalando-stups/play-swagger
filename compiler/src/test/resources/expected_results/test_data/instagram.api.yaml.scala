package instagram.api.yaml

import org.scalacheck.Gen
import org.scalacheck.Arbitrary
import Arbitrary._

object Generators {
    def createTagsSearchGetResponses200MetaGenerator = _generate(TagsSearchGetResponses200MetaGenerator)

    def createIntGenerator = _generate(IntGenerator)

    def `createMediaMedia-idGetResponses200VideosStandard_resolutionGenerator` = _generate(`MediaMedia-idGetResponses200VideosStandard_resolutionGenerator`)

    def createMediaFilterGenerator = _generate(MediaFilterGenerator)

    def `createMediaMedia-idCommentsDeleteResponses200MetaGenerator` = _generate(`MediaMedia-idCommentsDeleteResponses200MetaGenerator`)

    def createUsersSelfFeedGetResponses200DataGenerator = _generate(UsersSelfFeedGetResponses200DataGenerator)

    def createMediaTagsGenerator = _generate(MediaTagsGenerator)

    def `createMediaMedia-idLikesGetResponses200DataGenerator` = _generate(`MediaMedia-idLikesGetResponses200DataGenerator`)

    def createMediaIdGenerator = _generate(MediaIdGenerator)

    def createMediaTagsOptGenerator = _generate(MediaTagsOptGenerator)

    def createMediaImagesGenerator = _generate(MediaImagesGenerator)

    def createMediaLikesGenerator = _generate(MediaLikesGenerator)

    def `createMediaMedia-idCommentsGetResponses200DataOptGenerator` = _generate(`MediaMedia-idCommentsGetResponses200DataOptGenerator`)

    def createMediaUsers_in_photoOptGenerator = _generate(MediaUsers_in_photoOptGenerator)

    def `createMediaMedia-idLikesGetResponses200DataOptGenerator` = _generate(`MediaMedia-idLikesGetResponses200DataOptGenerator`)

    def createLocationsSearchGetResponses200DataGenerator = _generate(LocationsSearchGetResponses200DataGenerator)

    def `createMediaComments:Generator` = _generate(`MediaComments:Generator`)

    def createMediaSearchGetResponses200DataOptGenerator = _generate(MediaSearchGetResponses200DataOptGenerator)

    def createCommentFromGenerator = _generate(CommentFromGenerator)

    def createLocationsSearchGetResponses200DataOptGenerator = _generate(LocationsSearchGetResponses200DataOptGenerator)

    def createMediaSearchGetResponses200DataGenerator = _generate(MediaSearchGetResponses200DataGenerator)

    def createUsersSelfFeedGetResponses200DataOptGenerator = _generate(UsersSelfFeedGetResponses200DataOptGenerator)

    def `createUsersUser-idGetResponses200DataGenerator` = _generate(`UsersUser-idGetResponses200DataGenerator`)

    def createMediaVideosGenerator = _generate(MediaVideosGenerator)

    def createMediaLocationGenerator = _generate(MediaLocationGenerator)

    def createNullGenerator = _generate(NullGenerator)

    def createMediaUsers_in_photoGenerator = _generate(MediaUsers_in_photoGenerator)

    def createLocationLatitudeGenerator = _generate(LocationLatitudeGenerator)

    def `createMediaMedia-idCommentsGetResponses200DataGenerator` = _generate(`MediaMedia-idCommentsGetResponses200DataGenerator`)

    def createDoubleGenerator = _generate(DoubleGenerator)

    def createUserCountsGenerator = _generate(UserCountsGenerator)

    def createStringGenerator = _generate(StringGenerator)

    def TagsSearchGetResponses200MetaGenerator = Gen.option(`UsersSelfRequested-byGetResponses200MetaOptGenerator`)

    def IntGenerator = arbitrary[Int]

    def `MediaMedia-idGetResponses200VideosStandard_resolutionGenerator` = Gen.option(ImageGenerator)

    def MediaFilterGenerator = Gen.option(arbitrary[String])

    def `MediaMedia-idCommentsDeleteResponses200MetaGenerator` = Gen.option(`MediaMedia-idLikesGetResponses200MetaOptGenerator`)

    def UsersSelfFeedGetResponses200DataGenerator = Gen.option(UsersSelfFeedGetResponses200DataOptGenerator)

    def MediaTagsGenerator = Gen.option(MediaTagsOptGenerator)

    def `MediaMedia-idLikesGetResponses200DataGenerator` = Gen.option(`MediaMedia-idLikesGetResponses200DataOptGenerator`)

    def MediaIdGenerator = Gen.option(arbitrary[Int])

    def MediaTagsOptGenerator = Gen.containerOf[List,Tag](TagGenerator)

    def MediaImagesGenerator = Gen.option(MediaImagesOptGenerator)

    def MediaLikesGenerator = Gen.option(MediaLikesOptGenerator)

    def `MediaMedia-idCommentsGetResponses200DataOptGenerator` = Gen.containerOf[List,Comment](CommentGenerator)

    def MediaUsers_in_photoOptGenerator = Gen.containerOf[List,MiniProfile](MiniProfileGenerator)

    def `MediaMedia-idLikesGetResponses200DataOptGenerator` = Gen.containerOf[List,Like](LikeGenerator)

    def LocationsSearchGetResponses200DataGenerator = Gen.option(LocationsSearchGetResponses200DataOptGenerator)

    def `MediaComments:Generator` = Gen.option(`MediaComments:OptGenerator`)

    def MediaSearchGetResponses200DataOptGenerator = Gen.containerOf[List,MediaSearchGetResponses200DataOptArrResult](MediaSearchGetResponses200DataOptArrResultGenerator)

    def CommentFromGenerator = Gen.option(MiniProfileGenerator)

    def LocationsSearchGetResponses200DataOptGenerator = Gen.containerOf[List,Location](LocationGenerator)

    def MediaSearchGetResponses200DataGenerator = Gen.option(MediaSearchGetResponses200DataOptGenerator)

    def UsersSelfFeedGetResponses200DataOptGenerator = Gen.containerOf[List,Media](MediaGenerator)

    def `UsersUser-idGetResponses200DataGenerator` = Gen.option(UserGenerator)

    def MediaVideosGenerator = Gen.option(MediaVideosOptGenerator)

    def MediaLocationGenerator = Gen.option(LocationGenerator)

    def NullGenerator = arbitrary[Null]

    def MediaUsers_in_photoGenerator = Gen.option(MediaUsers_in_photoOptGenerator)

    def LocationLatitudeGenerator = Gen.option(arbitrary[Double])

    def `MediaMedia-idCommentsGetResponses200DataGenerator` = Gen.option(`MediaMedia-idCommentsGetResponses200DataOptGenerator`)

    def DoubleGenerator = arbitrary[Double]

    def UserCountsGenerator = Gen.option(UserCountsOptGenerator)

    def StringGenerator = arbitrary[String]

    def createUsersSelfFeedGetResponses200Generator = _generate(UsersSelfFeedGetResponses200Generator)

    def `createMediaMedia-idCommentsDeleteResponses200Generator` = _generate(`MediaMedia-idCommentsDeleteResponses200Generator`)

    def createMediaSearchGetResponses200DataOptArrResultGenerator = _generate(MediaSearchGetResponses200DataOptArrResultGenerator)

    def `createUsersUser-idFollowsGetResponses200Generator` = _generate(`UsersUser-idFollowsGetResponses200Generator`)

    def createUserCountsOptGenerator = _generate(UserCountsOptGenerator)

    def createUserGenerator = _generate(UserGenerator)

    def `createTagsTag-nameMediaRecentGetResponses200Generator` = _generate(`TagsTag-nameMediaRecentGetResponses200Generator`)

    def createImageGenerator = _generate(ImageGenerator)

    def `createUsersSelfRequested-byGetResponses200Generator` = _generate(`UsersSelfRequested-byGetResponses200Generator`)

    def createTagGenerator = _generate(TagGenerator)

    def `createUsersSelfRequested-byGetResponses200MetaOptGenerator` = _generate(`UsersSelfRequested-byGetResponses200MetaOptGenerator`)

    def `createLocationsLocation-idGetResponses200Generator` = _generate(`LocationsLocation-idGetResponses200Generator`)

    def createCommentGenerator = _generate(CommentGenerator)

    def createMediaGenerator = _generate(MediaGenerator)

    def `createMediaMedia-idLikesGetResponses200Generator` = _generate(`MediaMedia-idLikesGetResponses200Generator`)

    def `createMediaMedia-idLikesGetResponses200MetaOptGenerator` = _generate(`MediaMedia-idLikesGetResponses200MetaOptGenerator`)

    def createMediaSearchGetResponses200Generator = _generate(MediaSearchGetResponses200Generator)

    def createTagsSearchGetResponses200Generator = _generate(TagsSearchGetResponses200Generator)

    def createLikeGenerator = _generate(LikeGenerator)

    def `createMediaComments:OptGenerator` = _generate(`MediaComments:OptGenerator`)

    def `createUsersUser-idGetResponses200Generator` = _generate(`UsersUser-idGetResponses200Generator`)

    def `createMediaMedia-idCommentsGetResponses200Generator` = _generate(`MediaMedia-idCommentsGetResponses200Generator`)

    def createMediaVideosOptGenerator = _generate(MediaVideosOptGenerator)

    def createLocationGenerator = _generate(LocationGenerator)

    def createMiniProfileGenerator = _generate(MiniProfileGenerator)

    def createMediaLikesOptGenerator = _generate(MediaLikesOptGenerator)

    def createLocationsSearchGetResponses200Generator = _generate(LocationsSearchGetResponses200Generator)

    def createMediaImagesOptGenerator = _generate(MediaImagesOptGenerator)

    def UsersSelfFeedGetResponses200Generator = for {
        data <- UsersSelfFeedGetResponses200DataGenerator
        } yield UsersSelfFeedGetResponses200(data)

    def `MediaMedia-idCommentsDeleteResponses200Generator` = for {
        meta <- `MediaMedia-idCommentsDeleteResponses200MetaGenerator`
        data <- MediaFilterGenerator
        } yield `MediaMedia-idCommentsDeleteResponses200`(meta, data)

    def MediaSearchGetResponses200DataOptArrResultGenerator = for {
        location <- MediaLocationGenerator
        created_time <- MediaIdGenerator
        `comments:` <- `MediaComments:Generator`
        tags <- MediaTagsGenerator
        users_in_photo <- MediaUsers_in_photoGenerator
        filter <- MediaFilterGenerator
        likes <- MediaLikesGenerator
        id <- MediaIdGenerator
        videos <- MediaVideosGenerator
        `type` <- MediaFilterGenerator
        images <- MediaImagesGenerator
        user <- CommentFromGenerator
        distance <- LocationLatitudeGenerator
        } yield MediaSearchGetResponses200DataOptArrResult(location, created_time, `comments:`, tags, users_in_photo, filter, likes, id, videos, `type`, images, user, distance)

    def `UsersUser-idFollowsGetResponses200Generator` = for {
        data <- MediaUsers_in_photoGenerator
        } yield `UsersUser-idFollowsGetResponses200`(data)

    def UserCountsOptGenerator = for {
        media <- MediaIdGenerator
        follows <- MediaIdGenerator
        follwed_by <- MediaIdGenerator
        } yield UserCountsOpt(media, follows, follwed_by)

    def UserGenerator = for {
        website <- MediaFilterGenerator
        profile_picture <- MediaFilterGenerator
        username <- MediaFilterGenerator
        full_name <- MediaFilterGenerator
        bio <- MediaFilterGenerator
        id <- MediaIdGenerator
        counts <- UserCountsGenerator
        } yield User(website, profile_picture, username, full_name, bio, id, counts)

    def `TagsTag-nameMediaRecentGetResponses200Generator` = for {
        data <- MediaTagsGenerator
        } yield `TagsTag-nameMediaRecentGetResponses200`(data)

    def ImageGenerator = for {
        width <- MediaIdGenerator
        height <- MediaIdGenerator
        url <- MediaFilterGenerator
        } yield Image(width, height, url)

    def `UsersSelfRequested-byGetResponses200Generator` = for {
        meta <- TagsSearchGetResponses200MetaGenerator
        data <- MediaUsers_in_photoGenerator
        } yield `UsersSelfRequested-byGetResponses200`(meta, data)

    def TagGenerator = for {
        media_count <- MediaIdGenerator
        name <- MediaFilterGenerator
        } yield Tag(media_count, name)

    def `UsersSelfRequested-byGetResponses200MetaOptGenerator` = for {
        code <- MediaIdGenerator
        } yield `UsersSelfRequested-byGetResponses200MetaOpt`(code)

    def `LocationsLocation-idGetResponses200Generator` = for {
        data <- MediaLocationGenerator
        } yield `LocationsLocation-idGetResponses200`(data)

    def CommentGenerator = for {
        id <- MediaFilterGenerator
        created_time <- MediaFilterGenerator
        text <- MediaFilterGenerator
        from <- CommentFromGenerator
        } yield Comment(id, created_time, text, from)

    def MediaGenerator = for {
        location <- MediaLocationGenerator
        created_time <- MediaIdGenerator
        `comments:` <- `MediaComments:Generator`
        tags <- MediaTagsGenerator
        users_in_photo <- MediaUsers_in_photoGenerator
        filter <- MediaFilterGenerator
        likes <- MediaLikesGenerator
        id <- MediaIdGenerator
        videos <- MediaVideosGenerator
        `type` <- MediaFilterGenerator
        images <- MediaImagesGenerator
        user <- CommentFromGenerator
        } yield Media(location, created_time, `comments:`, tags, users_in_photo, filter, likes, id, videos, `type`, images, user)

    def `MediaMedia-idLikesGetResponses200Generator` = for {
        meta <- `MediaMedia-idCommentsDeleteResponses200MetaGenerator`
        data <- `MediaMedia-idLikesGetResponses200DataGenerator`
        } yield `MediaMedia-idLikesGetResponses200`(meta, data)

    def `MediaMedia-idLikesGetResponses200MetaOptGenerator` = for {
        code <- LocationLatitudeGenerator
        } yield `MediaMedia-idLikesGetResponses200MetaOpt`(code)

    def MediaSearchGetResponses200Generator = for {
        data <- MediaSearchGetResponses200DataGenerator
        } yield MediaSearchGetResponses200(data)

    def TagsSearchGetResponses200Generator = for {
        meta <- TagsSearchGetResponses200MetaGenerator
        data <- MediaTagsGenerator
        } yield TagsSearchGetResponses200(meta, data)

    def LikeGenerator = for {
        first_name <- MediaFilterGenerator
        id <- MediaFilterGenerator
        last_name <- MediaFilterGenerator
        `type` <- MediaFilterGenerator
        user_name <- MediaFilterGenerator
        } yield Like(first_name, id, last_name, `type`, user_name)

    def `MediaComments:OptGenerator` = for {
        count <- MediaIdGenerator
        data <- `MediaMedia-idCommentsGetResponses200DataGenerator`
        } yield `MediaComments:Opt`(count, data)

    def `UsersUser-idGetResponses200Generator` = for {
        data <- `UsersUser-idGetResponses200DataGenerator`
        } yield `UsersUser-idGetResponses200`(data)

    def `MediaMedia-idCommentsGetResponses200Generator` = for {
        meta <- `MediaMedia-idCommentsDeleteResponses200MetaGenerator`
        data <- `MediaMedia-idCommentsGetResponses200DataGenerator`
        } yield `MediaMedia-idCommentsGetResponses200`(meta, data)

    def MediaVideosOptGenerator = for {
        low_resolution <- `MediaMedia-idGetResponses200VideosStandard_resolutionGenerator`
        standard_resolution <- `MediaMedia-idGetResponses200VideosStandard_resolutionGenerator`
        } yield MediaVideosOpt(low_resolution, standard_resolution)

    def LocationGenerator = for {
        id <- MediaFilterGenerator
        name <- MediaFilterGenerator
        latitude <- LocationLatitudeGenerator
        longitude <- LocationLatitudeGenerator
        } yield Location(id, name, latitude, longitude)

    def MiniProfileGenerator = for {
        user_name <- MediaFilterGenerator
        full_name <- MediaFilterGenerator
        id <- MediaIdGenerator
        profile_picture <- MediaFilterGenerator
        } yield MiniProfile(user_name, full_name, id, profile_picture)

    def MediaLikesOptGenerator = for {
        count <- MediaIdGenerator
        data <- MediaUsers_in_photoGenerator
        } yield MediaLikesOpt(count, data)

    def LocationsSearchGetResponses200Generator = for {
        data <- LocationsSearchGetResponses200DataGenerator
        } yield LocationsSearchGetResponses200(data)

    def MediaImagesOptGenerator = for {
        low_resolution <- `MediaMedia-idGetResponses200VideosStandard_resolutionGenerator`
        thumbnail <- `MediaMedia-idGetResponses200VideosStandard_resolutionGenerator`
        standard_resolution <- `MediaMedia-idGetResponses200VideosStandard_resolutionGenerator`
        } yield MediaImagesOpt(low_resolution, thumbnail, standard_resolution)

    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
    }