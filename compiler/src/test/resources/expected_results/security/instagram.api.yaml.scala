package instagram.api.yaml

import play.api.mvc._
import Security.AuthenticatedBuilder
import de.zalando.play.controllers.PlayBodyParsing


trait InstagramApiYamlSecurity extends SecurityExtractors {
    
    object getmediaByMedia_idLikesSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.map(_.get)))
        }, unauthorizedContent)
    
    object postmediaByMedia_idLikesSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.map(_.get)))
        }, unauthorizedContent)
    
    object deletemediaByMedia_idLikesSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.map(_.get)))
        }, unauthorizedContent)
    
    object getusersByUser_idFollowsSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.map(_.get)))
        }, unauthorizedContent)
    
    object getlocationsByLocation_idSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.map(_.get)))
        }, unauthorizedContent)
    
    object getusersSearchSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.map(_.get)))
        }, unauthorizedContent)
    
    object getusersSelfMediaLikedSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.map(_.get)))
        }, unauthorizedContent)
    
    object gettagsByTag_nameSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.map(_.get)))
        }, unauthorizedContent)
    
    object gettagsSearchSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.map(_.get)))
        }, unauthorizedContent)
    
    object getusersByUser_idFollowed_bySecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.map(_.get)))
        }, unauthorizedContent)
    
    object getmediaByMedia_idCommentsSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.map(_.get)))
        }, unauthorizedContent)
    
    object postmediaByMedia_idCommentsSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.map(_.get)))
        }, unauthorizedContent)
    
    object deletemediaByMedia_idCommentsSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.map(_.get)))
        }, unauthorizedContent)
    
    object gettagsByTag_nameMediaRecentSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.map(_.get)))
        }, unauthorizedContent)
    
    object postusersByUser_idRelationshipSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.map(_.get)))
        }, unauthorizedContent)
    
    object getusersSelfFeedSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.map(_.get)))
        }, unauthorizedContent)
    
    object getusersByUser_idSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(key_Extractor _, oauth_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.map(_.get)))
        }, unauthorizedContent)
    
    object getmediaSearchSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.map(_.get)))
        }, unauthorizedContent)
    
    object getgeographiesByGeo_idMediaRecentSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.map(_.get)))
        }, unauthorizedContent)
    
    object getmediaByShortcodeSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.map(_.get)))
        }, unauthorizedContent)
    
    object getlocationsSearchSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.map(_.get)))
        }, unauthorizedContent)
    
    object getusersSelfRequested_bySecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.map(_.get)))
        }, unauthorizedContent)
    
    object getmediaByMedia_idSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.map(_.get)))
        }, unauthorizedContent)
    
    object getlocationsByLocation_idMediaRecentSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.map(_.get)))
        }, unauthorizedContent)
    
    object getusersByUser_idMediaRecentSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.map(_.get)))
        }, unauthorizedContent)
    
    object getmediaPopularSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.map(_.get)))
        }, unauthorizedContent)
    
}

