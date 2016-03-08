
package instagram.api.yaml

import scala.concurrent.Future
import play.api.mvc._
import de.zalando.play.controllers.SwaggerSecurityExtractors._

trait SecurityExtractors {
    def oauth_Extractor[User >: Any](header: RequestHeader): Future[Option[User]] =
        oAuth(header) { _ =>
            ???
    }
    def key_Extractor[User >: Any](header: RequestHeader): Future[Option[User]] =
        queryApiKey("access_token")(header) { (apiKey: String) =>
            ???
    }
    implicit val unauthorizedContentWriter = ???
    def unauthorizedContent(req: RequestHeader) = Results.Unauthorized(???)
}
