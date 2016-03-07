package basic.auth.api.yaml

import play.api.mvc._
import Security.AuthenticatedBuilder
import de.zalando.play.controllers.PlayBodyParsing


trait BasicAuthApiYamlSecurity {
    val unauthorizedContent = ???
    val mimeType: String = ???
    
    def basicAuth_Extractor[User >: Any](header: RequestHeader): Option[User] = ???

    val basicAuth_Checks = Seq(basicAuth_Extractor _)

    object basicAuth_Action extends AuthenticatedBuilder(
        req => {
            val individualChecks = basicAuth_Checks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    

    private def onUnauthorized[C](mimeType: String, content: C): RequestHeader => Result =_ => {
        implicit val writeable = PlayBodyParsing.anyToWritable[C](mimeType)
        Results.Unauthorized(content)
    }
}
