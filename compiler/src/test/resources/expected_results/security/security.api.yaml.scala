package security.api.yaml

import play.api.mvc._
import Security.AuthenticatedBuilder
import de.zalando.play.controllers.PlayBodyParsing
import de.zalando.play.controllers.ArrayWrapper


trait SecurityApiYamlSecurity extends SecurityExtractors {
    
    object getPetsByIdSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(githubAccessCode_Extractor _, internalApiKey_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        }, unauthorizedContent)
    
}

