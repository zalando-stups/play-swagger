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
    def `createLocationsLocation-iLocation-idGenerator` = _generate(`LocationsLocation-iLocation-idGenerator`)
    def createUsersSelfFeedGetResponses200Generator = _generate(UsersSelfFeedGetResponses200Generator)
    def `createLocationsLocation-iMediaRecentGetResponses200DataGenerator` = _generate(`LocationsLocation-iMediaRecentGetResponses200DataGenerator`)
    def `createMediaMedia-iCommentsDeleteResponses200MetaGenerator` = _generate(`MediaMedia-iCommentsDeleteResponses200MetaGenerator`)
    def `createMediaMedia-iLikesGetResponses200DataGenerator` = _generate(`MediaMedia-iLikesGetResponses200DataGenerator`)
    def `createMediaMedia-iCommentsDeleteResponses200Generator` = _generate(`MediaMedia-iCommentsDeleteResponses200Generator`)
    def `createUsersUser-iFollowsGetResponses200Generator` = _generate(`UsersUser-iFollowsGetResponses200Generator`)
    def `createTagsTag-namMediaRecentGetResponses200Generator` = _generate(`TagsTag-namMediaRecentGetResponses200Generator`)
    def `createUsersSelfRequested-byGetResponses200Generator` = _generate(`UsersSelfRequested-byGetResponses200Generator`)
    def `createLocationsLocation-iGetResponses200Generator` = _generate(`LocationsLocation-iGetResponses200Generator`)
    def `createMediaMedia-iLikesGetResponses200DataOptGenerator` = _generate(`MediaMedia-iLikesGetResponses200DataOptGenerator`)
    def createLocationsSearchGetResponses200DataGenerator = _generate(LocationsSearchGetResponses200DataGenerator)
    def createMediaSearchGetResponses200DataOptGenerator = _generate(MediaSearchGetResponses200DataOptGenerator)
    def `createMediaMedia-iLikesGetResponses200Generator` = _generate(`MediaMedia-iLikesGetResponses200Generator`)
    def createMediaSearchGetResponses200Generator = _generate(MediaSearchGetResponses200Generator)
    def createMediaSearchGetResponses200DataGenerator = _generate(MediaSearchGetResponses200DataGenerator)
    def createTagsSearchGetResponses200Generator = _generate(TagsSearchGetResponses200Generator)
    def `createMediaMedia-iGetResponses200Generator` = _generate(`MediaMedia-iGetResponses200Generator`)
    def `createUsersUser-iGetResponses200DataGenerator` = _generate(`UsersUser-iGetResponses200DataGenerator`)
    def `createUsersUser-iGetResponses200Generator` = _generate(`UsersUser-iGetResponses200Generator`)
    def `createMediaMedia-iCommentsGetResponses200Generator` = _generate(`MediaMedia-iCommentsGetResponses200Generator`)
    def `createGeographiesGeo-iMediaRecentGetResponses200Generator` = _generate(`GeographiesGeo-iMediaRecentGetResponses200Generator`)
    def createLocationsSearchGetResponses200Generator = _generate(LocationsSearchGetResponses200Generator)
    val TagsSearchGetResponses200MetaGenerator = Gen.option(`UsersSelfRequested-byGetResponses200MetaOptGenerator`)
    val `LocationsLocation-iLocation-idGenerator` = arbitrary[Int]
    val UsersSelfFeedGetResponses200Generator = Gen.option(`UsersUser-iMediaRecentGetResponses200OptGenerator`)
    val `LocationsLocation-iMediaRecentGetResponses200DataGenerator` = Gen.option(`MediaMedia-iGetResponses200Generator`)
    val `MediaMedia-iCommentsDeleteResponses200MetaGenerator` = Gen.option(`MediaMedia-iCommentsPostResponses200MetaOptGenerator`)
    val `MediaMedia-iLikesGetResponses200DataGenerator` = Gen.option(`MediaMedia-iLikesGetResponses200DataOptGenerator`)
    val `MediaMedia-iCommentsDeleteResponses200Generator` = Gen.option(`MediaMedia-iLikesPostResponses200OptGenerator`)
    val `UsersUser-iFollowsGetResponses200Generator` = Gen.option(`UsersUser-iRelationshipPostResponses200OptGenerator`)
    val `TagsTag-namMediaRecentGetResponses200Generator` = Gen.option(`TagsTag-namMediaRecentGetResponses200OptGenerator`)
    val `UsersSelfRequested-byGetResponses200Generator` = Gen.option(`UsersSelfRequested-byGetResponses200OptGenerator`)
    val `LocationsLocation-iGetResponses200Generator` = Gen.option(`LocationsLocation-iGetResponses200OptGenerator`)
    val `MediaMedia-iLikesGetResponses200DataOptGenerator` = Gen.containerOf[List,Like](LikeGenerator)
    val LocationsSearchGetResponses200DataGenerator = Gen.option(MediaLocationGenerator)
    val MediaSearchGetResponses200DataOptGenerator = Gen.containerOf[List,MediaSearchGetResponses200DataOptArr](MediaSearchGetResponses200DataOptArrGenerator)
    val `MediaMedia-iLikesGetResponses200Generator` = Gen.option(`MediaMedia-iLikesGetResponses200OptGenerator`)
    val MediaSearchGetResponses200Generator = Gen.option(MediaSearchGetResponses200OptGenerator)
    val MediaSearchGetResponses200DataGenerator = Gen.option(MediaSearchGetResponses200DataOptGenerator)
    val TagsSearchGetResponses200Generator = Gen.option(TagsSearchGetResponses200OptGenerator)
    val `MediaMedia-iGetResponses200Generator` = Gen.option(MediaGenerator)
    val `UsersUser-iGetResponses200DataGenerator` = Gen.option(UserGenerator)
    val `UsersUser-iGetResponses200Generator` = Gen.option(`UsersUser-iGetResponses200OptGenerator`)
    val `MediaMedia-iCommentsGetResponses200Generator` = Gen.option(`MediaMedia-iCommentsGetResponses200OptGenerator`)
    val `GeographiesGeo-iMediaRecentGetResponses200Generator` = arbitrary[Null]
    val LocationsSearchGetResponses200Generator = Gen.option(LocationsSearchGetResponses200OptGenerator)
    def `createMediaMedia-iLikesGetResponses200OptGenerator` = _generate(`MediaMedia-iLikesGetResponses200OptGenerator`)
    def `createUsersSelfRequested-byGetResponses200OptGenerator` = _generate(`UsersSelfRequested-byGetResponses200OptGenerator`)
    def `createUsersUser-iRelationshipPostResponses200OptGenerator` = _generate(`UsersUser-iRelationshipPostResponses200OptGenerator`)
    def `createUsersUser-iMediaRecentGetResponses200OptGenerator` = _generate(`UsersUser-iMediaRecentGetResponses200OptGenerator`)
    def createTagsSearchGetResponses200OptGenerator = _generate(TagsSearchGetResponses200OptGenerator)
    def `createTagsTag-namMediaRecentGetResponses200OptGenerator` = _generate(`TagsTag-namMediaRecentGetResponses200OptGenerator`)
    def createMediaSearchGetResponses200OptGenerator = _generate(MediaSearchGetResponses200OptGenerator)
    def `createMediaMedia-iCommentsPostResponses200MetaOptGenerator` = _generate(`MediaMedia-iCommentsPostResponses200MetaOptGenerator`)
    def `createUsersSelfRequested-byGetResponses200MetaOptGenerator` = _generate(`UsersSelfRequested-byGetResponses200MetaOptGenerator`)
    def createMediaSearchGetResponses200DataOptArrGenerator = _generate(MediaSearchGetResponses200DataOptArrGenerator)
    def `createMediaMedia-iLikesPostResponses200OptGenerator` = _generate(`MediaMedia-iLikesPostResponses200OptGenerator`)
    def createLocationsSearchGetResponses200OptGenerator = _generate(LocationsSearchGetResponses200OptGenerator)
    def `createLocationsLocation-iGetResponses200OptGenerator` = _generate(`LocationsLocation-iGetResponses200OptGenerator`)
    def `createUsersUser-iGetResponses200OptGenerator` = _generate(`UsersUser-iGetResponses200OptGenerator`)
    def `createMediaMedia-iCommentsGetResponses200OptGenerator` = _generate(`MediaMedia-iCommentsGetResponses200OptGenerator`)
    val `MediaMedia-iLikesGetResponses200OptGenerator` =
        for {
        meta <- `MediaMedia-iCommentsDeleteResponses200MetaGenerator`
        data <- `MediaMedia-iLikesGetResponses200DataGenerator`
        } yield `MediaMedia-iLikesGetResponses200Opt`(meta, data)
    
    val `UsersSelfRequested-byGetResponses200OptGenerator` =
        for {
        meta <- TagsSearchGetResponses200MetaGenerator
        data <- MediaUsers_in_photoGenerator
        } yield `UsersSelfRequested-byGetResponses200Opt`(meta, data)
    
    val `UsersUser-iRelationshipPostResponses200OptGenerator` =
        for {
        data <- MediaUsers_in_photoGenerator
        } yield `UsersUser-iRelationshipPostResponses200Opt`(data)
    
    val `UsersUser-iMediaRecentGetResponses200OptGenerator` =
        for {
        data <- `LocationsLocation-iMediaRecentGetResponses200DataGenerator`
        } yield `UsersUser-iMediaRecentGetResponses200Opt`(data)
    
    val TagsSearchGetResponses200OptGenerator =
        for {
        meta <- TagsSearchGetResponses200MetaGenerator
        data <- MediaTagsGenerator
        } yield TagsSearchGetResponses200Opt(meta, data)
    
    val `TagsTag-namMediaRecentGetResponses200OptGenerator` =
        for {
        data <- MediaTagsGenerator
        } yield `TagsTag-namMediaRecentGetResponses200Opt`(data)
    
    val MediaSearchGetResponses200OptGenerator =
        for {
        data <- MediaSearchGetResponses200DataGenerator
        } yield MediaSearchGetResponses200Opt(data)
    
    val `MediaMedia-iCommentsPostResponses200MetaOptGenerator` =
        for {
        code <- LocationLongitudeGenerator
        } yield `MediaMedia-iCommentsPostResponses200MetaOpt`(code)
    
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
    
    val `MediaMedia-iLikesPostResponses200OptGenerator` =
        for {
        meta <- `MediaMedia-iCommentsDeleteResponses200MetaGenerator`
        data <- LikeUser_nameGenerator
        } yield `MediaMedia-iLikesPostResponses200Opt`(meta, data)
    
    val LocationsSearchGetResponses200OptGenerator =
        for {
        data <- LocationsSearchGetResponses200DataGenerator
        } yield LocationsSearchGetResponses200Opt(data)
    
    val `LocationsLocation-iGetResponses200OptGenerator` =
        for {
        data <- MediaLocationGenerator
        } yield `LocationsLocation-iGetResponses200Opt`(data)
    
    val `UsersUser-iGetResponses200OptGenerator` =
        for {
        data <- `UsersUser-iGetResponses200DataGenerator`
        } yield `UsersUser-iGetResponses200Opt`(data)
    
    val `MediaMedia-iCommentsGetResponses200OptGenerator` =
        for {
        meta <- `MediaMedia-iCommentsDeleteResponses200MetaGenerator`
        data <- `MediaComments:DataGenerator`
        } yield `MediaMedia-iCommentsGetResponses200Opt`(meta, data)
    
    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample

    def _genMap[K,V](keyGen: Gen[K], valGen: Gen[V]): Gen[Map[K,V]] = for {
        keys <- Gen.containerOf[List,K](keyGen)
        values <- Gen.containerOfN[List,V](keys.size, valGen)
    } yield keys.zip(values).toMap
}
