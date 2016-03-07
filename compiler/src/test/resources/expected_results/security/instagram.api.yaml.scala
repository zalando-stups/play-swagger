package instagram.api.yaml

import play.api.mvc._
import Security.AuthenticatedBuilder
import de.zalando.play.controllers.PlayBodyParsing


trait InstagramApiYamlSecurity extends SecurityExtractors {
    
    object getmediaByMedia_idLikesSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        }, unauthorizedContent)
    
    object postmediaByMedia_idLikesSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        }, unauthorizedContent)
    
    object deletemediaByMedia_idLikesSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        }, unauthorizedContent)
    
    object getusersByUser_idFollowsSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        }, unauthorizedContent)
    
    object getlocationsByLocation_idSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        }, unauthorizedContent)
    
    object getusersSearchSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        }, unauthorizedContent)
    
    object getusersSelfMediaLikedSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        }, unauthorizedContent)
    
    object gettagsByTag_nameSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        }, unauthorizedContent)
    
    object gettagsSearchSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        }, unauthorizedContent)
    
    object getusersByUser_idFollowed_bySecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        }, unauthorizedContent)
    
    object getmediaByMedia_idCommentsSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        }, unauthorizedContent)
    
    object postmediaByMedia_idCommentsSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        }, unauthorizedContent)
    
    object deletemediaByMedia_idCommentsSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        }, unauthorizedContent)
    
    object gettagsByTag_nameMediaRecentSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        }, unauthorizedContent)
    
    object postusersByUser_idRelationshipSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        }, unauthorizedContent)
    
    object getusersSelfFeedSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        }, unauthorizedContent)
    
    object getusersByUser_idSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(key_Extractor _, oauth_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        }, unauthorizedContent)
    
    object getmediaSearchSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        }, unauthorizedContent)
    
    object getgeographiesByGeo_idMediaRecentSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        }, unauthorizedContent)
    
    object getmediaByShortcodeSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        }, unauthorizedContent)
    
    object getlocationsSearchSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        }, unauthorizedContent)
    
    object getusersSelfRequested_bySecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        }, unauthorizedContent)
    
    object getmediaByMedia_idSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        }, unauthorizedContent)
    
    object getlocationsByLocation_idMediaRecentSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        }, unauthorizedContent)
    
    object getusersByUser_idMediaRecentSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        }, unauthorizedContent)
    
    object getmediaPopularSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        }, unauthorizedContent)
    
}

