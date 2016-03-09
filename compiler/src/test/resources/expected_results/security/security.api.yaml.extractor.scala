
package security.api.yaml

import scala.concurrent.Future
import play.api.mvc._
import de.zalando.play.controllers.SwaggerSecurityExtractors._
import de.zalando.play.controllers.ArrayWrapper

trait SecurityExtractors {
    def petstoreImplicit_Extractor[User >: Any](scopes: String*): RequestHeader => Future[Option[User]] =
        header => oAuth(scopes)(header) { _ =>
            ???
    }
    def githubAccessCode_Extractor[User >: Any](scopes: String*): RequestHeader => Future[Option[User]] =
        header => oAuth(scopes)(header) { _ =>
            ???
    }
    def petstorePassword_Extractor[User >: Any](scopes: String*): RequestHeader => Future[Option[User]] =
        header => oAuthPassword(scopes)("http://petstore.swagger.wordnik.com/oauth/dialog")(header) { (token: play.api.libs.json.JsValue) =>
            ???
    }
    def justBasicStuff_Extractor[User >: Any](): RequestHeader => Future[Option[User]] =
        header => basicAuth(header) { (username: String, password: String) =>
            ???
    }
    def petstoreApplication_Extractor[User >: Any](scopes: String*): RequestHeader => Future[Option[User]] =
        header => oAuthPassword(scopes)("http://petstore.swagger.wordnik.com/oauth/token")(header) { (token: play.api.libs.json.JsValue) =>
            ???
    }
    def internalApiKey_Extractor[User >: Any](): RequestHeader => Future[Option[User]] =
        header => headerApiKey("api_key")(header) { (apiKey: String) =>
            ???
    }
    implicit val unauthorizedContentWriter = ???
    def unauthorizedContent(req: RequestHeader) = Results.Unauthorized(???)
}
