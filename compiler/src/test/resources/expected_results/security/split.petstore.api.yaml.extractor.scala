
package split.petstore.api.yaml

import scala.concurrent.Future
import play.api.mvc._
import de.zalando.play.controllers.SwaggerSecurityExtractors._
import de.zalando.play.controllers.ArrayWrapper
import org.joda.time.DateTime

trait SecurityExtractors {
    def api_key_Extractor[User >: Any](header: RequestHeader): Future[Option[User]] =
        headerApiKey("api_key")(header) { (apiKey: String) =>
            ???
    }
    def petstore_auth_Extractor[User >: Any](header: RequestHeader): Future[Option[User]] =
        oAuth(header) { _ =>
            ???
    }
    implicit val unauthorizedContentWriter = ???
    def unauthorizedContent(req: RequestHeader) = Results.Unauthorized(???)
}
