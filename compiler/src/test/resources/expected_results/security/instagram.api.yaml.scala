package instagram.api.yaml

import play.api.mvc._
import Security.AuthenticatedBuilder
import de.zalando.play.controllers.PlayBodyParsing

trait SecurityExtractors {
    def oauth_Extractor[User >: Any](header: RequestHeader): Option[User] = ???
    def key_Extractor[User >: Any](header: RequestHeader): Option[User] = ???

}


trait InstagramApiYamlSecurity extends SecurityExtractors {
    val unauthorizedContent = ???
    val mimeType: String = ???

    
    object getmediaByMedia_idLikesSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    object postmediaByMedia_idLikesSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _)
            val individualChecks = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    object deletemediaByMedia_idLikesSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    object getusersByUser_idFollowsSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    object getlocationsByLocation_idSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    object getusersSearchSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    object getusersSelfMediaLikedSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    object gettagsByTag_nameSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    object gettagsSearchSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    object getusersByUser_idFollowed_bySecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    object getmediaByMedia_idCommentsSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    object postmediaByMedia_idCommentsSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _)
            val individualChecks = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    object deletemediaByMedia_idCommentsSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    object gettagsByTag_nameMediaRecentSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    object postusersByUser_idRelationshipSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _)
            val individualChecks = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    object getusersSelfFeedSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    object getusersByUser_idSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(key_Extractor _, oauth_Extractor _)
            val individualChecks = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    object getmediaSearchSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    object getgeographiesByGeo_idMediaRecentSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    object getmediaByShortcodeSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    object getlocationsSearchSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    object getusersSelfRequested_bySecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    object getmediaByMedia_idSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    object getlocationsByLocation_idMediaRecentSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    object getusersByUser_idMediaRecentSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    object getmediaPopularSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    

    private def onUnauthorized[C](mimeType: String, content: C): RequestHeader => Result =_ => {
        implicit val writeable = PlayBodyParsing.anyToWritable[C](mimeType)
        Results.Unauthorized(content)
    }
}
