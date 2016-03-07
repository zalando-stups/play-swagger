
package instagram.api.yaml

import play.api.mvc._
import Security.AuthenticatedBuilder
import de.zalando.play.controllers.SwaggerSecurityExtractors._

trait SecurityExtractors {
    def oauth_Extractor[User >: Any](header: RequestHeader): Option[User] =
        oAuth(header) { _ =>
            ???
    }
    def key_Extractor[User >: Any](header: RequestHeader): Option[User] =
        queryApiKey("access_token")(header) { (apiKey: String) =>
            ???
    }
    implicit val unauthorizedContentWriter = ???
    def unauthorizedContent(req: RequestHeader) = Results.Unauthorized(???)
}
