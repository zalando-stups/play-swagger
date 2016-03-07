package instagram.api.yaml

import play.api.mvc._
import Security.AuthenticatedBuilder
import de.zalando.play.controllers.PlayBodyParsing


trait InstagramApiYamlSecurity {
    
    def oauth_Extractor[User >: Any](header: RequestHeader): Option[User] = ???
    def key_Extractor[User >: Any](header: RequestHeader): Option[User] = ???

    val oauth_key_Checks = Seq(oauth_Extractor _, key_Extractor _)

    class oauth_key_Action(mimeType: String, unauthorizedContent: Any) extends AuthenticatedBuilder(
        req => {
            val individualChecks = oauth_key_Checks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    
    def oauth_Extractor[User >: Any](header: RequestHeader): Option[User] = ???

    val oauth_Checks = Seq(oauth_Extractor _)

    class oauth_Action(mimeType: String, unauthorizedContent: Any) extends AuthenticatedBuilder(
        req => {
            val individualChecks = oauth_Checks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    
    def oauth_Extractor[User >: Any](header: RequestHeader): Option[User] = ???
    def key_Extractor[User >: Any](header: RequestHeader): Option[User] = ???

    val oauth_key_Checks = Seq(oauth_Extractor _, key_Extractor _)

    class oauth_key_Action(mimeType: String, unauthorizedContent: Any) extends AuthenticatedBuilder(
        req => {
            val individualChecks = oauth_key_Checks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    
    def oauth_Extractor[User >: Any](header: RequestHeader): Option[User] = ???
    def key_Extractor[User >: Any](header: RequestHeader): Option[User] = ???

    val oauth_key_Checks = Seq(oauth_Extractor _, key_Extractor _)

    class oauth_key_Action(mimeType: String, unauthorizedContent: Any) extends AuthenticatedBuilder(
        req => {
            val individualChecks = oauth_key_Checks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    
    def oauth_Extractor[User >: Any](header: RequestHeader): Option[User] = ???
    def key_Extractor[User >: Any](header: RequestHeader): Option[User] = ???

    val oauth_key_Checks = Seq(oauth_Extractor _, key_Extractor _)

    class oauth_key_Action(mimeType: String, unauthorizedContent: Any) extends AuthenticatedBuilder(
        req => {
            val individualChecks = oauth_key_Checks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    
    def oauth_Extractor[User >: Any](header: RequestHeader): Option[User] = ???
    def key_Extractor[User >: Any](header: RequestHeader): Option[User] = ???

    val oauth_key_Checks = Seq(oauth_Extractor _, key_Extractor _)

    class oauth_key_Action(mimeType: String, unauthorizedContent: Any) extends AuthenticatedBuilder(
        req => {
            val individualChecks = oauth_key_Checks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    
    def oauth_Extractor[User >: Any](header: RequestHeader): Option[User] = ???
    def key_Extractor[User >: Any](header: RequestHeader): Option[User] = ???

    val oauth_key_Checks = Seq(oauth_Extractor _, key_Extractor _)

    class oauth_key_Action(mimeType: String, unauthorizedContent: Any) extends AuthenticatedBuilder(
        req => {
            val individualChecks = oauth_key_Checks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    
    def oauth_Extractor[User >: Any](header: RequestHeader): Option[User] = ???
    def key_Extractor[User >: Any](header: RequestHeader): Option[User] = ???

    val oauth_key_Checks = Seq(oauth_Extractor _, key_Extractor _)

    class oauth_key_Action(mimeType: String, unauthorizedContent: Any) extends AuthenticatedBuilder(
        req => {
            val individualChecks = oauth_key_Checks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    
    def oauth_Extractor[User >: Any](header: RequestHeader): Option[User] = ???
    def key_Extractor[User >: Any](header: RequestHeader): Option[User] = ???

    val oauth_key_Checks = Seq(oauth_Extractor _, key_Extractor _)

    class oauth_key_Action(mimeType: String, unauthorizedContent: Any) extends AuthenticatedBuilder(
        req => {
            val individualChecks = oauth_key_Checks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    
    def oauth_Extractor[User >: Any](header: RequestHeader): Option[User] = ???
    def key_Extractor[User >: Any](header: RequestHeader): Option[User] = ???

    val oauth_key_Checks = Seq(oauth_Extractor _, key_Extractor _)

    class oauth_key_Action(mimeType: String, unauthorizedContent: Any) extends AuthenticatedBuilder(
        req => {
            val individualChecks = oauth_key_Checks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    
    def oauth_Extractor[User >: Any](header: RequestHeader): Option[User] = ???
    def key_Extractor[User >: Any](header: RequestHeader): Option[User] = ???

    val oauth_key_Checks = Seq(oauth_Extractor _, key_Extractor _)

    class oauth_key_Action(mimeType: String, unauthorizedContent: Any) extends AuthenticatedBuilder(
        req => {
            val individualChecks = oauth_key_Checks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    
    def oauth_Extractor[User >: Any](header: RequestHeader): Option[User] = ???

    val oauth_Checks = Seq(oauth_Extractor _)

    class oauth_Action(mimeType: String, unauthorizedContent: Any) extends AuthenticatedBuilder(
        req => {
            val individualChecks = oauth_Checks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    
    def oauth_Extractor[User >: Any](header: RequestHeader): Option[User] = ???
    def key_Extractor[User >: Any](header: RequestHeader): Option[User] = ???

    val oauth_key_Checks = Seq(oauth_Extractor _, key_Extractor _)

    class oauth_key_Action(mimeType: String, unauthorizedContent: Any) extends AuthenticatedBuilder(
        req => {
            val individualChecks = oauth_key_Checks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    
    def oauth_Extractor[User >: Any](header: RequestHeader): Option[User] = ???
    def key_Extractor[User >: Any](header: RequestHeader): Option[User] = ???

    val oauth_key_Checks = Seq(oauth_Extractor _, key_Extractor _)

    class oauth_key_Action(mimeType: String, unauthorizedContent: Any) extends AuthenticatedBuilder(
        req => {
            val individualChecks = oauth_key_Checks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    
    def oauth_Extractor[User >: Any](header: RequestHeader): Option[User] = ???

    val oauth_Checks = Seq(oauth_Extractor _)

    class oauth_Action(mimeType: String, unauthorizedContent: Any) extends AuthenticatedBuilder(
        req => {
            val individualChecks = oauth_Checks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    
    def oauth_Extractor[User >: Any](header: RequestHeader): Option[User] = ???
    def key_Extractor[User >: Any](header: RequestHeader): Option[User] = ???

    val oauth_key_Checks = Seq(oauth_Extractor _, key_Extractor _)

    class oauth_key_Action(mimeType: String, unauthorizedContent: Any) extends AuthenticatedBuilder(
        req => {
            val individualChecks = oauth_key_Checks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    
    def key_Extractor[User >: Any](header: RequestHeader): Option[User] = ???
    def oauth_Extractor[User >: Any](header: RequestHeader): Option[User] = ???

    val key_oauth_Checks = Seq(key_Extractor _, oauth_Extractor _)

    class key_oauth_Action(mimeType: String, unauthorizedContent: Any) extends AuthenticatedBuilder(
        req => {
            val individualChecks = key_oauth_Checks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    
    def oauth_Extractor[User >: Any](header: RequestHeader): Option[User] = ???
    def key_Extractor[User >: Any](header: RequestHeader): Option[User] = ???

    val oauth_key_Checks = Seq(oauth_Extractor _, key_Extractor _)

    class oauth_key_Action(mimeType: String, unauthorizedContent: Any) extends AuthenticatedBuilder(
        req => {
            val individualChecks = oauth_key_Checks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    
    def oauth_Extractor[User >: Any](header: RequestHeader): Option[User] = ???
    def key_Extractor[User >: Any](header: RequestHeader): Option[User] = ???

    val oauth_key_Checks = Seq(oauth_Extractor _, key_Extractor _)

    class oauth_key_Action(mimeType: String, unauthorizedContent: Any) extends AuthenticatedBuilder(
        req => {
            val individualChecks = oauth_key_Checks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    
    def oauth_Extractor[User >: Any](header: RequestHeader): Option[User] = ???
    def key_Extractor[User >: Any](header: RequestHeader): Option[User] = ???

    val oauth_key_Checks = Seq(oauth_Extractor _, key_Extractor _)

    class oauth_key_Action(mimeType: String, unauthorizedContent: Any) extends AuthenticatedBuilder(
        req => {
            val individualChecks = oauth_key_Checks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    
    def oauth_Extractor[User >: Any](header: RequestHeader): Option[User] = ???
    def key_Extractor[User >: Any](header: RequestHeader): Option[User] = ???

    val oauth_key_Checks = Seq(oauth_Extractor _, key_Extractor _)

    class oauth_key_Action(mimeType: String, unauthorizedContent: Any) extends AuthenticatedBuilder(
        req => {
            val individualChecks = oauth_key_Checks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    
    def oauth_Extractor[User >: Any](header: RequestHeader): Option[User] = ???
    def key_Extractor[User >: Any](header: RequestHeader): Option[User] = ???

    val oauth_key_Checks = Seq(oauth_Extractor _, key_Extractor _)

    class oauth_key_Action(mimeType: String, unauthorizedContent: Any) extends AuthenticatedBuilder(
        req => {
            val individualChecks = oauth_key_Checks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    
    def oauth_Extractor[User >: Any](header: RequestHeader): Option[User] = ???
    def key_Extractor[User >: Any](header: RequestHeader): Option[User] = ???

    val oauth_key_Checks = Seq(oauth_Extractor _, key_Extractor _)

    class oauth_key_Action(mimeType: String, unauthorizedContent: Any) extends AuthenticatedBuilder(
        req => {
            val individualChecks = oauth_key_Checks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    
    def oauth_Extractor[User >: Any](header: RequestHeader): Option[User] = ???
    def key_Extractor[User >: Any](header: RequestHeader): Option[User] = ???

    val oauth_key_Checks = Seq(oauth_Extractor _, key_Extractor _)

    class oauth_key_Action(mimeType: String, unauthorizedContent: Any) extends AuthenticatedBuilder(
        req => {
            val individualChecks = oauth_key_Checks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    
    def oauth_Extractor[User >: Any](header: RequestHeader): Option[User] = ???
    def key_Extractor[User >: Any](header: RequestHeader): Option[User] = ???

    val oauth_key_Checks = Seq(oauth_Extractor _, key_Extractor _)

    class oauth_key_Action(mimeType: String, unauthorizedContent: Any) extends AuthenticatedBuilder(
        req => {
            val individualChecks = oauth_key_Checks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    
    def oauth_Extractor[User >: Any](header: RequestHeader): Option[User] = ???
    def key_Extractor[User >: Any](header: RequestHeader): Option[User] = ???

    val oauth_key_Checks = Seq(oauth_Extractor _, key_Extractor _)

    class oauth_key_Action(mimeType: String, unauthorizedContent: Any) extends AuthenticatedBuilder(
        req => {
            val individualChecks = oauth_key_Checks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    

    private def onUnauthorized[C](mimeType: String, content: C): RequestHeader => Result =_ => {
        implicit val writeable = PlayBodyParsing.anyToWritable[C](mimeType)
        Results.Unauthorized(content)
    }
}
