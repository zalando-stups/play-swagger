package instagram.api.yaml

import scala.concurrent.Future
import play.api.mvc._
import de.zalando.play.controllers.{FutureAuthenticatedBuilder,PlayBodyParsing}


trait InstagramApiYamlSecurity extends SecurityExtractors {
    
    object getmediaByMedia_idLikesSecureAction extends FutureAuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Future[Seq[Option[_]]] = Future.sequence(secureChecks.map(_.apply(req)))
                individualChecks.map { checks =>
                    checks.find(_.isEmpty).getOrElse(Option(checks.map(_.get)))
                }
        }, unauthorizedContent)
    
    object postmediaByMedia_idLikesSecureAction extends FutureAuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _)
            val individualChecks: Future[Seq[Option[_]]] = Future.sequence(secureChecks.map(_.apply(req)))
                individualChecks.map { checks =>
                    checks.find(_.isEmpty).getOrElse(Option(checks.map(_.get)))
                }
        }, unauthorizedContent)
    
    object deletemediaByMedia_idLikesSecureAction extends FutureAuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Future[Seq[Option[_]]] = Future.sequence(secureChecks.map(_.apply(req)))
                individualChecks.map { checks =>
                    checks.find(_.isEmpty).getOrElse(Option(checks.map(_.get)))
                }
        }, unauthorizedContent)
    
    object getusersByUser_idFollowsSecureAction extends FutureAuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Future[Seq[Option[_]]] = Future.sequence(secureChecks.map(_.apply(req)))
                individualChecks.map { checks =>
                    checks.find(_.isEmpty).getOrElse(Option(checks.map(_.get)))
                }
        }, unauthorizedContent)
    
    object getlocationsByLocation_idSecureAction extends FutureAuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Future[Seq[Option[_]]] = Future.sequence(secureChecks.map(_.apply(req)))
                individualChecks.map { checks =>
                    checks.find(_.isEmpty).getOrElse(Option(checks.map(_.get)))
                }
        }, unauthorizedContent)
    
    object getusersSearchSecureAction extends FutureAuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Future[Seq[Option[_]]] = Future.sequence(secureChecks.map(_.apply(req)))
                individualChecks.map { checks =>
                    checks.find(_.isEmpty).getOrElse(Option(checks.map(_.get)))
                }
        }, unauthorizedContent)
    
    object getusersSelfMediaLikedSecureAction extends FutureAuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Future[Seq[Option[_]]] = Future.sequence(secureChecks.map(_.apply(req)))
                individualChecks.map { checks =>
                    checks.find(_.isEmpty).getOrElse(Option(checks.map(_.get)))
                }
        }, unauthorizedContent)
    
    object gettagsByTag_nameSecureAction extends FutureAuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Future[Seq[Option[_]]] = Future.sequence(secureChecks.map(_.apply(req)))
                individualChecks.map { checks =>
                    checks.find(_.isEmpty).getOrElse(Option(checks.map(_.get)))
                }
        }, unauthorizedContent)
    
    object gettagsSearchSecureAction extends FutureAuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Future[Seq[Option[_]]] = Future.sequence(secureChecks.map(_.apply(req)))
                individualChecks.map { checks =>
                    checks.find(_.isEmpty).getOrElse(Option(checks.map(_.get)))
                }
        }, unauthorizedContent)
    
    object getusersByUser_idFollowed_bySecureAction extends FutureAuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Future[Seq[Option[_]]] = Future.sequence(secureChecks.map(_.apply(req)))
                individualChecks.map { checks =>
                    checks.find(_.isEmpty).getOrElse(Option(checks.map(_.get)))
                }
        }, unauthorizedContent)
    
    object getmediaByMedia_idCommentsSecureAction extends FutureAuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Future[Seq[Option[_]]] = Future.sequence(secureChecks.map(_.apply(req)))
                individualChecks.map { checks =>
                    checks.find(_.isEmpty).getOrElse(Option(checks.map(_.get)))
                }
        }, unauthorizedContent)
    
    object postmediaByMedia_idCommentsSecureAction extends FutureAuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _)
            val individualChecks: Future[Seq[Option[_]]] = Future.sequence(secureChecks.map(_.apply(req)))
                individualChecks.map { checks =>
                    checks.find(_.isEmpty).getOrElse(Option(checks.map(_.get)))
                }
        }, unauthorizedContent)
    
    object deletemediaByMedia_idCommentsSecureAction extends FutureAuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Future[Seq[Option[_]]] = Future.sequence(secureChecks.map(_.apply(req)))
                individualChecks.map { checks =>
                    checks.find(_.isEmpty).getOrElse(Option(checks.map(_.get)))
                }
        }, unauthorizedContent)
    
    object gettagsByTag_nameMediaRecentSecureAction extends FutureAuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Future[Seq[Option[_]]] = Future.sequence(secureChecks.map(_.apply(req)))
                individualChecks.map { checks =>
                    checks.find(_.isEmpty).getOrElse(Option(checks.map(_.get)))
                }
        }, unauthorizedContent)
    
    object postusersByUser_idRelationshipSecureAction extends FutureAuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _)
            val individualChecks: Future[Seq[Option[_]]] = Future.sequence(secureChecks.map(_.apply(req)))
                individualChecks.map { checks =>
                    checks.find(_.isEmpty).getOrElse(Option(checks.map(_.get)))
                }
        }, unauthorizedContent)
    
    object getusersSelfFeedSecureAction extends FutureAuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Future[Seq[Option[_]]] = Future.sequence(secureChecks.map(_.apply(req)))
                individualChecks.map { checks =>
                    checks.find(_.isEmpty).getOrElse(Option(checks.map(_.get)))
                }
        }, unauthorizedContent)
    
    object getusersByUser_idSecureAction extends FutureAuthenticatedBuilder(
        req => {
            val secureChecks = Seq(key_Extractor _, oauth_Extractor _)
            val individualChecks: Future[Seq[Option[_]]] = Future.sequence(secureChecks.map(_.apply(req)))
                individualChecks.map { checks =>
                    checks.find(_.isEmpty).getOrElse(Option(checks.map(_.get)))
                }
        }, unauthorizedContent)
    
    object getmediaSearchSecureAction extends FutureAuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Future[Seq[Option[_]]] = Future.sequence(secureChecks.map(_.apply(req)))
                individualChecks.map { checks =>
                    checks.find(_.isEmpty).getOrElse(Option(checks.map(_.get)))
                }
        }, unauthorizedContent)
    
    object getgeographiesByGeo_idMediaRecentSecureAction extends FutureAuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Future[Seq[Option[_]]] = Future.sequence(secureChecks.map(_.apply(req)))
                individualChecks.map { checks =>
                    checks.find(_.isEmpty).getOrElse(Option(checks.map(_.get)))
                }
        }, unauthorizedContent)
    
    object getmediaByShortcodeSecureAction extends FutureAuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Future[Seq[Option[_]]] = Future.sequence(secureChecks.map(_.apply(req)))
                individualChecks.map { checks =>
                    checks.find(_.isEmpty).getOrElse(Option(checks.map(_.get)))
                }
        }, unauthorizedContent)
    
    object getlocationsSearchSecureAction extends FutureAuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Future[Seq[Option[_]]] = Future.sequence(secureChecks.map(_.apply(req)))
                individualChecks.map { checks =>
                    checks.find(_.isEmpty).getOrElse(Option(checks.map(_.get)))
                }
        }, unauthorizedContent)
    
    object getusersSelfRequested_bySecureAction extends FutureAuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Future[Seq[Option[_]]] = Future.sequence(secureChecks.map(_.apply(req)))
                individualChecks.map { checks =>
                    checks.find(_.isEmpty).getOrElse(Option(checks.map(_.get)))
                }
        }, unauthorizedContent)
    
    object getmediaByMedia_idSecureAction extends FutureAuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Future[Seq[Option[_]]] = Future.sequence(secureChecks.map(_.apply(req)))
                individualChecks.map { checks =>
                    checks.find(_.isEmpty).getOrElse(Option(checks.map(_.get)))
                }
        }, unauthorizedContent)
    
    object getlocationsByLocation_idMediaRecentSecureAction extends FutureAuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Future[Seq[Option[_]]] = Future.sequence(secureChecks.map(_.apply(req)))
                individualChecks.map { checks =>
                    checks.find(_.isEmpty).getOrElse(Option(checks.map(_.get)))
                }
        }, unauthorizedContent)
    
    object getusersByUser_idMediaRecentSecureAction extends FutureAuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Future[Seq[Option[_]]] = Future.sequence(secureChecks.map(_.apply(req)))
                individualChecks.map { checks =>
                    checks.find(_.isEmpty).getOrElse(Option(checks.map(_.get)))
                }
        }, unauthorizedContent)
    
    object getmediaPopularSecureAction extends FutureAuthenticatedBuilder(
        req => {
            val secureChecks = Seq(oauth_Extractor _, key_Extractor _)
            val individualChecks: Future[Seq[Option[_]]] = Future.sequence(secureChecks.map(_.apply(req)))
                individualChecks.map { checks =>
                    checks.find(_.isEmpty).getOrElse(Option(checks.map(_.get)))
                }
        }, unauthorizedContent)
    
}

