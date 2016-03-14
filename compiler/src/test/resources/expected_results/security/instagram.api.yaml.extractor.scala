
package instagram.api.yaml

import scala.concurrent.Future
import play.api.mvc._
import de.zalando.play.controllers.SwaggerSecurityExtractors._

trait SecurityExtractors {
    def oauth_Extractor[User >: Any](scopes: String*): RequestHeader => Future[Option[User]] =
        header => oAuth(scopes)(header) { _ =>
            ???
    }
    def key_Extractor[User >: Any](): RequestHeader => Future[Option[User]] =
        header => queryApiKey("access_token")(header) { (apiKey: String) =>
            ???
    }
    implicit val unauthorizedContentWriter = ???
    def unauthorizedContent(req: RequestHeader) = Results.Unauthorized(???)
}
