
package full.petstore.api.yaml

import play.api.mvc._
import Security.AuthenticatedBuilder
import de.zalando.play.controllers.SwaggerSecurityExtractors._
import de.zalando.play.controllers.ArrayWrapper
import org.joda.time.DateTime

trait SecurityExtractors {
    def api_key_Extractor[User >: Any](header: RequestHeader): Option[User] =
        headerApiKey("api_key")(header) { (apiKey: String) =>
            ???
    }
    def petstore_auth_Extractor[User >: Any](header: RequestHeader): Option[User] =
        oAuth(header) { _ =>
            ???
    }
    implicit val unauthorizedContentWriter = ???
    def unauthorizedContent(req: RequestHeader) = Results.Unauthorized(???)
}
