
package security.api.yaml

import scala.concurrent.Future
import play.api.mvc._
import de.zalando.play.controllers.SwaggerSecurityExtractors._
import de.zalando.play.controllers.ArrayWrapper

trait SecurityExtractors {
    def githubAccessCode_Extractor[User >: Any](header: RequestHeader): Future[Option[User]] =
        oAuth(header) { _ =>
            ???
    }
    def petstoreImplicit_Extractor[User >: Any](header: RequestHeader): Future[Option[User]] =
        oAuth(header) { _ =>
            ???
    }
    def internalApiKey_Extractor[User >: Any](header: RequestHeader): Future[Option[User]] =
        headerApiKey("api_key")(header) { (apiKey: String) =>
            ???
    }
    def justBasicStuff_Extractor[User >: Any](header: RequestHeader): Future[Option[User]] =
        basicAuth(header) { (username: String, password: String) =>
            ???
    }
    implicit val unauthorizedContentWriter = ???
    def unauthorizedContent(req: RequestHeader) = Results.Unauthorized(???)
}
