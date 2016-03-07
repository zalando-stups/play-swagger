package security.api.yaml

import play.api.mvc._
import Security.AuthenticatedBuilder
import de.zalando.play.controllers.{PlayBodyParsing, SwaggerSecurityExtractors}
import SwaggerSecurityExtractors._
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


trait SecurityApiYamlSecurity extends SecurityExtractors {
    
    object getPetsByIdSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(githubAccessCode_Extractor _, internalApiKey_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        }, unauthorizedContent)
    
}

