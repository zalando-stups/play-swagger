
package basic.auth.api.yaml

import play.api.mvc._
import Security.AuthenticatedBuilder
import de.zalando.play.controllers.SwaggerSecurityExtractors._

trait SecurityExtractors {
    def basicAuth_Extractor[User >: Any](header: RequestHeader): Option[User] =
        basicAuth(header) { (username: String, password: String) =>
            ???
    }
    implicit val unauthorizedContentWriter = ???
    def unauthorizedContent(req: RequestHeader) = Results.Unauthorized(???)
}
