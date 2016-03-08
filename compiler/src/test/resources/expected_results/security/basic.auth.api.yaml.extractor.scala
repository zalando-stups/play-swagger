
package basic.auth.api.yaml

import scala.concurrent.Future
import play.api.mvc._
import de.zalando.play.controllers.SwaggerSecurityExtractors._

trait SecurityExtractors {
    def basicAuth_Extractor[User >: Any](header: RequestHeader): Future[Option[User]] =
        basicAuth(header) { (username: String, password: String) =>
            ???
    }
    implicit val unauthorizedContentWriter = ???
    def unauthorizedContent(req: RequestHeader) = Results.Unauthorized(???)
}
