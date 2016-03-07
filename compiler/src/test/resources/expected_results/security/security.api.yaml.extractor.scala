
package security.api.yaml

import play.api.mvc._
import Security.AuthenticatedBuilder
import de.zalando.play.controllers.SwaggerSecurityExtractors._
import de.zalando.play.controllers.ArrayWrapper

trait SecurityExtractors {
    def githubAccessCode_Extractor[User >: Any](header: RequestHeader): Option[User] =
        oAuth(header) { _ =>
            ???
    }
    def petstoreImplicit_Extractor[User >: Any](header: RequestHeader): Option[User] =
        oAuth(header) { _ =>
            ???
    }
    def internalApiKey_Extractor[User >: Any](header: RequestHeader): Option[User] =
        headerApiKey("api_key")(header) { (apiKey: String) =>
            ???
    }
    def justBasicStuff_Extractor[User >: Any](header: RequestHeader): Option[User] =
        basicAuth(header) { (username: String, password: String) =>
            ???
    }
    implicit val unauthorizedContentWriter = ???
    def unauthorizedContent(req: RequestHeader) = Results.Unauthorized(???)
}
