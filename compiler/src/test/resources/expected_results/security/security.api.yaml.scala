package security.api.yaml

import play.api.mvc._
import Security.AuthenticatedBuilder
import de.zalando.play.controllers.PlayBodyParsing
import de.zalando.play.controllers.ArrayWrapper


trait SecurityApiYamlSecurity {
    val unauthorizedContent = ???
    val mimeType: String = ???
    
    def githubAccessCode_Extractor[User >: Any](header: RequestHeader): Option[User] = ???
    def internalApiKey_Extractor[User >: Any](header: RequestHeader): Option[User] = ???

    val githubAccessCode_internalApiKey_Checks = Seq(githubAccessCode_Extractor _, internalApiKey_Extractor _)

    object githubAccessCode_internalApiKey_Action extends AuthenticatedBuilder(
        req => {
            val individualChecks = githubAccessCode_internalApiKey_Checks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    

    private def onUnauthorized[C](mimeType: String, content: C): RequestHeader => Result =_ => {
        implicit val writeable = PlayBodyParsing.anyToWritable[C](mimeType)
        Results.Unauthorized(content)
    }
}
