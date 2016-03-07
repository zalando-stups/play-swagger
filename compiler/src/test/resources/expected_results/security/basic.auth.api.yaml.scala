package basic.auth.api.yaml

import play.api.mvc._
import Security.AuthenticatedBuilder
import de.zalando.play.controllers.PlayBodyParsing

trait SecurityExtractors {
    def basicAuth_Extractor[User >: Any](header: RequestHeader): Option[User] = ???

}


trait BasicAuthApiYamlSecurity extends SecurityExtractors {
    val unauthorizedContent = ???
    val mimeType: String = ???

    
    object getSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(basicAuth_Extractor _)
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
