
package split.petstore.api.yaml

import scala.concurrent.Future
import play.api.mvc._
import de.zalando.play.controllers.SwaggerSecurityExtractors._
import de.zalando.play.controllers.ArrayWrapper
import org.joda.time.DateTime

trait SecurityExtractors {
    def api_key_Extractor[User >: Any](): RequestHeader => Future[Option[User]] =
        header => headerApiKey("api_key")(header) { (apiKey: String) =>
            ???
    }
    def petstore_auth_Extractor[User >: Any](scopes: String*): RequestHeader => Future[Option[User]] =
        header => oAuth(scopes)(header) { _ =>
            ???
    }
    implicit val unauthorizedContentWriter = ???
    def unauthorizedContent(req: RequestHeader) = Results.Unauthorized(???)
}
