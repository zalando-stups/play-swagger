package basic.auth.api.yaml

import play.api.mvc._
import Security.AuthenticatedBuilder
import de.zalando.play.controllers.PlayBodyParsing


trait BasicAuthApiYamlSecurity extends SecurityExtractors {
    
    object getSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(basicAuth_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        }, unauthorizedContent)
    
}

