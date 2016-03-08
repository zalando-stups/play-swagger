package security.api.yaml

import scala.concurrent.Future
import play.api.mvc._
import de.zalando.play.controllers.{FutureAuthenticatedBuilder,PlayBodyParsing}
import de.zalando.play.controllers.ArrayWrapper


trait SecurityApiYamlSecurity extends SecurityExtractors {
    
    object getPetsByIdSecureAction extends FutureAuthenticatedBuilder(
        req => {
            val secureChecks = Seq(githubAccessCode_Extractor _, internalApiKey_Extractor _)
            val individualChecks: Future[Seq[Option[_]]] = Future.sequence(secureChecks.map(_.apply(req)))
                individualChecks.map { checks =>
                    checks.find(_.isEmpty).getOrElse(Option(checks.map(_.get)))
                }
        }, unauthorizedContent)
    
}

