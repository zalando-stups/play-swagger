package instagram.api.yaml
import org.scalacheck.Gen
import org.scalacheck.Arbitrary._

object parametersGenerator {
    import parameters.`User-id-paramUser-id`
    import parameters.`Tag-nameTag-name`
    def `createUser-id-paramUser-idGenerator` = _generate(`User-id-paramUser-idGenerator`)
    def `createTag-nameTag-nameGenerator` = _generate(`Tag-nameTag-nameGenerator`)
    val `User-id-paramUser-idGenerator` = arbitrary[Double]
    val `Tag-nameTag-nameGenerator` = arbitrary[String]
    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample

    def _genMap[K,V](keyGen: Gen[K], valGen: Gen[V]): Gen[Map[K,V]] = for {
        keys <- Gen.containerOf[List,K](keyGen)
        values <- Gen.containerOfN[List,V](keys.size, valGen)
    } yield keys.zip(values).toMap
}
object definitionsGenerator {
    import definitions._
    def createLikeUser_nameGenerator = _generate(LikeUser_nameGenerator)
    def createMediaUserGenerator = _generate(MediaUserGenerator)
    def createMediaVideosLow_resolutionGenerator = _generate(MediaVideosLow_resolutionGenerator)
    def createLocationLongitudeGenerator = _generate(LocationLongitudeGenerator)
    def createMediaImagesGenerator = _generate(MediaImagesGenerator)
    def createMediaTagsOptGenerator = _generate(MediaTagsOptGenerator)
    def createMediaLikesGenerator = _generate(MediaLikesGenerator)
    def createMediaVideosGenerator = _generate(MediaVideosGenerator)
    def createMediaCreated_timeGenerator = _generate(MediaCreated_timeGenerator)
    def createMediaLocationGenerator = _generate(MediaLocationGenerator)
    def createMediaTagsGenerator = _generate(MediaTagsGenerator)
    def `createMediaComments:DataOptGenerator` = _generate(`MediaComments:DataOptGenerator`)
    def `createMediaComments:Generator` = _generate(`MediaComments:Generator`)
    def createMediaUsers_in_photoGenerator = _generate(MediaUsers_in_photoGenerator)
    def createUserCountsGenerator = _generate(UserCountsGenerator)
    def `createMediaComments:DataGenerator` = _generate(`MediaComments:DataGenerator`)
    val LikeUser_nameGenerator = Gen.option(arbitrary[String])
    val MediaUserGenerator = Gen.option(MiniProfileGenerator)
    val MediaVideosLow_resolutionGenerator = Gen.option(ImageGenerator)
    val LocationLongitudeGenerator = Gen.option(arbitrary[Double])
    val MediaImagesGenerator = Gen.option(MediaImagesOptGenerator)
    val MediaTagsOptGenerator = Gen.containerOf[List,Tag](TagGenerator)
    val MediaLikesGenerator = Gen.option(MediaLikesOptGenerator)
    val MediaVideosGenerator = Gen.option(MediaVideosOptGenerator)
    val MediaCreated_timeGenerator = Gen.option(arbitrary[Int])
    val MediaLocationGenerator = Gen.option(LocationGenerator)
    val MediaTagsGenerator = Gen.option(MediaTagsOptGenerator)
    val `MediaComments:DataOptGenerator` = Gen.containerOf[List,Comment](CommentGenerator)
    val `MediaComments:Generator` = Gen.option(`MediaComments:OptGenerator`)
    val MediaUsers_in_photoGenerator = Gen.option(MediaUserGenerator)
    val UserCountsGenerator = Gen.option(UserCountsOptGenerator)
    val `MediaComments:DataGenerator` = Gen.option(`MediaComments:DataOptGenerator`)
    def createUserGenerator = _generate(UserGenerator)
    def createImageGenerator = _generate(ImageGenerator)
    def createTagGenerator = _generate(TagGenerator)
    def createCommentGenerator = _generate(CommentGenerator)
    def createMediaGenerator = _generate(MediaGenerator)
    def createMediaVideosOptGenerator = _generate(MediaVideosOptGenerator)
    def createLikeGenerator = _generate(LikeGenerator)
    def createMediaLikesOptGenerator = _generate(MediaLikesOptGenerator)
    def createLocationGenerator = _generate(LocationGenerator)
    def createMiniProfileGenerator = _generate(MiniProfileGenerator)
    def createMediaImagesOptGenerator = _generate(MediaImagesOptGenerator)
    def createUserCountsOptGenerator = _generate(UserCountsOptGenerator)
    def `createMediaComments:OptGenerator` = _generate(`MediaComments:OptGenerator`)
    val UserGenerator =
        for {
        website <- LikeUser_nameGenerator
        profile_picture <- LikeUser_nameGenerator
        username <- LikeUser_nameGenerator
        full_name <- LikeUser_nameGenerator
        bio <- LikeUser_nameGenerator
        id <- MediaCreated_timeGenerator
        counts <- UserCountsGenerator
        } yield User(website, profile_picture, username, full_name, bio, id, counts)
    
    val ImageGenerator =
        for {
        width <- MediaCreated_timeGenerator
        height <- MediaCreated_timeGenerator
        url <- LikeUser_nameGenerator
        } yield Image(width, height, url)
    
    val TagGenerator =
        for {
        media_count <- MediaCreated_timeGenerator
        name <- LikeUser_nameGenerator
        } yield Tag(media_count, name)
    
    val CommentGenerator =
        for {
        id <- LikeUser_nameGenerator
        created_time <- LikeUser_nameGenerator
        text <- LikeUser_nameGenerator
        from <- MediaUserGenerator
        } yield Comment(id, created_time, text, from)
    
    val MediaGenerator =
        for {
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
    
    val MediaVideosOptGenerator =
        for {
        low_resolution <- MediaVideosLow_resolutionGenerator
        standard_resolution <- MediaVideosLow_resolutionGenerator
        } yield MediaVideosOpt(low_resolution, standard_resolution)
    
    val LikeGenerator =
        for {
        first_name <- LikeUser_nameGenerator
        id <- LikeUser_nameGenerator
        last_name <- LikeUser_nameGenerator
        `type` <- LikeUser_nameGenerator
        user_name <- LikeUser_nameGenerator
        } yield Like(first_name, id, last_name, `type`, user_name)
    
    val MediaLikesOptGenerator =
        for {
        count <- MediaCreated_timeGenerator
        data <- MediaUsers_in_photoGenerator
        } yield MediaLikesOpt(count, data)
    
    val LocationGenerator =
        for {
        id <- LikeUser_nameGenerator
        name <- LikeUser_nameGenerator
        latitude <- LocationLongitudeGenerator
        longitude <- LocationLongitudeGenerator
        } yield Location(id, name, latitude, longitude)
    
    val MiniProfileGenerator =
        for {
        user_name <- LikeUser_nameGenerator
        full_name <- LikeUser_nameGenerator
        id <- MediaCreated_timeGenerator
        profile_picture <- LikeUser_nameGenerator
        } yield MiniProfile(user_name, full_name, id, profile_picture)
    
    val MediaImagesOptGenerator =
        for {
        low_resolution <- MediaVideosLow_resolutionGenerator
        thumbnail <- MediaVideosLow_resolutionGenerator
        standard_resolution <- MediaVideosLow_resolutionGenerator
        } yield MediaImagesOpt(low_resolution, thumbnail, standard_resolution)
    
    val UserCountsOptGenerator =
        for {
        media <- MediaCreated_timeGenerator
        follows <- MediaCreated_timeGenerator
        follwed_by <- MediaCreated_timeGenerator
        } yield UserCountsOpt(media, follows, follwed_by)
    
    val `MediaComments:OptGenerator` =
        for {
        count <- MediaCreated_timeGenerator
        data <- `MediaComments:DataGenerator`
        } yield `MediaComments:Opt`(count, data)
    
    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample

    def _genMap[K,V](keyGen: Gen[K], valGen: Gen[V]): Gen[Map[K,V]] = for {
        keys <- Gen.containerOf[List,K](keyGen)
        values <- Gen.containerOfN[List,V](keys.size, valGen)
    } yield keys.zip(values).toMap
}
object pathsGenerator {
    import definitions._
    import paths._
    import definitionsGenerator._
    def createTagsSearchGetResponses200MetaGenerator = _generate(TagsSearchGetResponses200MetaGenerator)
    def `createLocationsLocation-idLocation-idGenerator` = _generate(`LocationsLocation-idLocation-idGenerator`)
    def createUsersSelfFeedGetResponses200Generator = _generate(UsersSelfFeedGetResponses200Generator)
    def `createLocationsLocation-idMediaRecentGetResponses200DataGenerator` = _generate(`LocationsLocation-idMediaRecentGetResponses200DataGenerator`)
    def `createMediaMedia-idCommentsDeleteResponses200MetaGenerator` = _generate(`MediaMedia-idCommentsDeleteResponses200MetaGenerator`)
    def `createMediaMedia-idLikesGetResponses200DataGenerator` = _generate(`MediaMedia-idLikesGetResponses200DataGenerator`)
    def `createMediaMedia-idCommentsDeleteResponses200Generator` = _generate(`MediaMedia-idCommentsDeleteResponses200Generator`)
    def `createUsersUser-idFollowsGetResponses200Generator` = _generate(`UsersUser-idFollowsGetResponses200Generator`)
    def `createTagsTag-nameMediaRecentGetResponses200Generator` = _generate(`TagsTag-nameMediaRecentGetResponses200Generator`)
    def `createUsersSelfRequested-byGetResponses200Generator` = _generate(`UsersSelfRequested-byGetResponses200Generator`)
    def `createLocationsLocation-idGetResponses200Generator` = _generate(`LocationsLocation-idGetResponses200Generator`)
    def `createMediaMedia-idLikesGetResponses200DataOptGenerator` = _generate(`MediaMedia-idLikesGetResponses200DataOptGenerator`)
    def createLocationsSearchGetResponses200DataGenerator = _generate(LocationsSearchGetResponses200DataGenerator)
    def createMediaSearchGetResponses200DataOptGenerator = _generate(MediaSearchGetResponses200DataOptGenerator)
    def `createMediaMedia-idLikesGetResponses200Generator` = _generate(`MediaMedia-idLikesGetResponses200Generator`)
    def createMediaSearchGetResponses200Generator = _generate(MediaSearchGetResponses200Generator)
    def createMediaSearchGetResponses200DataGenerator = _generate(MediaSearchGetResponses200DataGenerator)
    def createTagsSearchGetResponses200Generator = _generate(TagsSearchGetResponses200Generator)
    def `createMediaMedia-idGetResponses200Generator` = _generate(`MediaMedia-idGetResponses200Generator`)
    def `createUsersUser-idGetResponses200DataGenerator` = _generate(`UsersUser-idGetResponses200DataGenerator`)
    def `createUsersUser-idGetResponses200Generator` = _generate(`UsersUser-idGetResponses200Generator`)
    def `createMediaMedia-idCommentsGetResponses200Generator` = _generate(`MediaMedia-idCommentsGetResponses200Generator`)
    def `createGeographiesGeo-idMediaRecentGetResponses200Generator` = _generate(`GeographiesGeo-idMediaRecentGetResponses200Generator`)
    def createLocationsSearchGetResponses200Generator = _generate(LocationsSearchGetResponses200Generator)
    val TagsSearchGetResponses200MetaGenerator = Gen.option(`UsersSelfRequested-byGetResponses200MetaOptGenerator`)
    val `LocationsLocation-idLocation-idGenerator` = arbitrary[Int]
    val UsersSelfFeedGetResponses200Generator = Gen.option(`UsersUser-idMediaRecentGetResponses200OptGenerator`)
    val `LocationsLocation-idMediaRecentGetResponses200DataGenerator` = Gen.option(`MediaMedia-idGetResponses200Generator`)
    val `MediaMedia-idCommentsDeleteResponses200MetaGenerator` = Gen.option(`MediaMedia-idCommentsPostResponses200MetaOptGenerator`)
    val `MediaMedia-idLikesGetResponses200DataGenerator` = Gen.option(`MediaMedia-idLikesGetResponses200DataOptGenerator`)
    val `MediaMedia-idCommentsDeleteResponses200Generator` = Gen.option(`MediaMedia-idLikesPostResponses200OptGenerator`)
    val `UsersUser-idFollowsGetResponses200Generator` = Gen.option(`UsersUser-idRelationshipPostResponses200OptGenerator`)
    val `TagsTag-nameMediaRecentGetResponses200Generator` = Gen.option(`TagsTag-nameMediaRecentGetResponses200OptGenerator`)
    val `UsersSelfRequested-byGetResponses200Generator` = Gen.option(`UsersSelfRequested-byGetResponses200OptGenerator`)
    val `LocationsLocation-idGetResponses200Generator` = Gen.option(`LocationsLocation-idGetResponses200OptGenerator`)
    val `MediaMedia-idLikesGetResponses200DataOptGenerator` = Gen.containerOf[List,Like](LikeGenerator)
    val LocationsSearchGetResponses200DataGenerator = Gen.option(MediaLocationGenerator)
    val MediaSearchGetResponses200DataOptGenerator = Gen.containerOf[List,MediaSearchGetResponses200DataOptArr](MediaSearchGetResponses200DataOptArrGenerator)
    val `MediaMedia-idLikesGetResponses200Generator` = Gen.option(`MediaMedia-idLikesGetResponses200OptGenerator`)
    val MediaSearchGetResponses200Generator = Gen.option(MediaSearchGetResponses200OptGenerator)
    val MediaSearchGetResponses200DataGenerator = Gen.option(MediaSearchGetResponses200DataOptGenerator)
    val TagsSearchGetResponses200Generator = Gen.option(TagsSearchGetResponses200OptGenerator)
    val `MediaMedia-idGetResponses200Generator` = Gen.option(MediaGenerator)
    val `UsersUser-idGetResponses200DataGenerator` = Gen.option(UserGenerator)
    val `UsersUser-idGetResponses200Generator` = Gen.option(`UsersUser-idGetResponses200OptGenerator`)
    val `MediaMedia-idCommentsGetResponses200Generator` = Gen.option(`MediaMedia-idCommentsGetResponses200OptGenerator`)
    val `GeographiesGeo-idMediaRecentGetResponses200Generator` = arbitrary[Null]
    val LocationsSearchGetResponses200Generator = Gen.option(LocationsSearchGetResponses200OptGenerator)
    def `createMediaMedia-idLikesGetResponses200OptGenerator` = _generate(`MediaMedia-idLikesGetResponses200OptGenerator`)
    def `createUsersSelfRequested-byGetResponses200OptGenerator` = _generate(`UsersSelfRequested-byGetResponses200OptGenerator`)
    def `createUsersUser-idRelationshipPostResponses200OptGenerator` = _generate(`UsersUser-idRelationshipPostResponses200OptGenerator`)
    def `createUsersUser-idMediaRecentGetResponses200OptGenerator` = _generate(`UsersUser-idMediaRecentGetResponses200OptGenerator`)
    def createTagsSearchGetResponses200OptGenerator = _generate(TagsSearchGetResponses200OptGenerator)
    def `createTagsTag-nameMediaRecentGetResponses200OptGenerator` = _generate(`TagsTag-nameMediaRecentGetResponses200OptGenerator`)
    def createMediaSearchGetResponses200OptGenerator = _generate(MediaSearchGetResponses200OptGenerator)
    def `createMediaMedia-idCommentsPostResponses200MetaOptGenerator` = _generate(`MediaMedia-idCommentsPostResponses200MetaOptGenerator`)
    def `createUsersSelfRequested-byGetResponses200MetaOptGenerator` = _generate(`UsersSelfRequested-byGetResponses200MetaOptGenerator`)
    def createMediaSearchGetResponses200DataOptArrGenerator = _generate(MediaSearchGetResponses200DataOptArrGenerator)
    def `createMediaMedia-idLikesPostResponses200OptGenerator` = _generate(`MediaMedia-idLikesPostResponses200OptGenerator`)
    def createLocationsSearchGetResponses200OptGenerator = _generate(LocationsSearchGetResponses200OptGenerator)
    def `createLocationsLocation-idGetResponses200OptGenerator` = _generate(`LocationsLocation-idGetResponses200OptGenerator`)
    def `createUsersUser-idGetResponses200OptGenerator` = _generate(`UsersUser-idGetResponses200OptGenerator`)
    def `createMediaMedia-idCommentsGetResponses200OptGenerator` = _generate(`MediaMedia-idCommentsGetResponses200OptGenerator`)
    val `MediaMedia-idLikesGetResponses200OptGenerator` =
        for {
        meta <- `MediaMedia-idCommentsDeleteResponses200MetaGenerator`
        data <- `MediaMedia-idLikesGetResponses200DataGenerator`
        } yield `MediaMedia-idLikesGetResponses200Opt`(meta, data)
    
    val `UsersSelfRequested-byGetResponses200OptGenerator` =
        for {
        meta <- TagsSearchGetResponses200MetaGenerator
        data <- MediaUsers_in_photoGenerator
        } yield `UsersSelfRequested-byGetResponses200Opt`(meta, data)
    
    val `UsersUser-idRelationshipPostResponses200OptGenerator` =
        for {
        data <- MediaUsers_in_photoGenerator
        } yield `UsersUser-idRelationshipPostResponses200Opt`(data)
    
    val `UsersUser-idMediaRecentGetResponses200OptGenerator` =
        for {
        data <- `LocationsLocation-idMediaRecentGetResponses200DataGenerator`
        } yield `UsersUser-idMediaRecentGetResponses200Opt`(data)
    
    val TagsSearchGetResponses200OptGenerator =
        for {
        meta <- TagsSearchGetResponses200MetaGenerator
        data <- MediaTagsGenerator
        } yield TagsSearchGetResponses200Opt(meta, data)
    
    val `TagsTag-nameMediaRecentGetResponses200OptGenerator` =
        for {
        data <- MediaTagsGenerator
        } yield `TagsTag-nameMediaRecentGetResponses200Opt`(data)
    
    val MediaSearchGetResponses200OptGenerator =
        for {
        data <- MediaSearchGetResponses200DataGenerator
        } yield MediaSearchGetResponses200Opt(data)
    
    val `MediaMedia-idCommentsPostResponses200MetaOptGenerator` =
        for {
        code <- LocationLongitudeGenerator
        } yield `MediaMedia-idCommentsPostResponses200MetaOpt`(code)
    
    val `UsersSelfRequested-byGetResponses200MetaOptGenerator` =
        for {
        code <- MediaCreated_timeGenerator
        } yield `UsersSelfRequested-byGetResponses200MetaOpt`(code)
    
    val MediaSearchGetResponses200DataOptArrGenerator =
        for {
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
    
    val `MediaMedia-idLikesPostResponses200OptGenerator` =
        for {
        meta <- `MediaMedia-idCommentsDeleteResponses200MetaGenerator`
        data <- LikeUser_nameGenerator
        } yield `MediaMedia-idLikesPostResponses200Opt`(meta, data)
    
    val LocationsSearchGetResponses200OptGenerator =
        for {
        data <- LocationsSearchGetResponses200DataGenerator
        } yield LocationsSearchGetResponses200Opt(data)
    
    val `LocationsLocation-idGetResponses200OptGenerator` =
        for {
        data <- MediaLocationGenerator
        } yield `LocationsLocation-idGetResponses200Opt`(data)
    
    val `UsersUser-idGetResponses200OptGenerator` =
        for {
        data <- `UsersUser-idGetResponses200DataGenerator`
        } yield `UsersUser-idGetResponses200Opt`(data)
    
    val `MediaMedia-idCommentsGetResponses200OptGenerator` =
        for {
        meta <- `MediaMedia-idCommentsDeleteResponses200MetaGenerator`
        data <- `MediaComments:DataGenerator`
        } yield `MediaMedia-idCommentsGetResponses200Opt`(meta, data)
    
    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample

    def _genMap[K,V](keyGen: Gen[K], valGen: Gen[V]): Gen[Map[K,V]] = for {
        keys <- Gen.containerOf[List,K](keyGen)
        values <- Gen.containerOfN[List,V](keys.size, valGen)
    } yield keys.zip(values).toMap
}
