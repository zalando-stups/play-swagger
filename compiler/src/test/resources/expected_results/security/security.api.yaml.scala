package security.api.yaml

import play.api.mvc._
import Security.AuthenticatedBuilder
import de.zalando.play.controllers.PlayBodyParsing
import de.zalando.play.controllers.ArrayWrapper

trait SecurityExtractors {
    def githubAccessCode_Extractor[User >: Any](header: RequestHeader): Option[User] = ???
    def petstoreImplicit_Extractor[User >: Any](header: RequestHeader): Option[User] = ???
    def internalApiKey_Extractor[User >: Any](header: RequestHeader): Option[User] = ???
    def justBasicStuff_Extractor[User >: Any](header: RequestHeader): Option[User] = ???

}


trait SecurityApiYamlSecurity extends SecurityExtractors {
    val unauthorizedContent = ???
    val mimeType: String = ???

    
    object getPetsByIdSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(githubAccessCode_Extractor _, internalApiKey_Extractor _)
            val individualChecks = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    

    private def onUnauthorized[C](mimeType: String, content: C): RequestHeader => Result =_ => {
        implicit val writeable = PlayBodyParsing.anyToWritable[C](mimeType)
        Results.Unauthorized(content)
    }
}
