package split.petstore.api.yaml

import play.api.mvc._
import Security.AuthenticatedBuilder
import de.zalando.play.controllers.PlayBodyParsing
import de.zalando.play.controllers.ArrayWrapper
import org.joda.time.DateTime


trait SplitPetstoreApiYamlSecurity {
    val unauthorizedContent = ???
    val mimeType: String = ???
    
    def petstore_auth_Extractor[User >: Any](header: RequestHeader): Option[User] = ???

    val petstore_auth_Checks = Seq(petstore_auth_Extractor _)

    object petstore_auth_Action extends AuthenticatedBuilder(
        req => {
            val individualChecks = petstore_auth_Checks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    
    
    
    
    
    
    
    def petstore_auth_Extractor[User >: Any](header: RequestHeader): Option[User] = ???

    val petstore_auth_Checks = Seq(petstore_auth_Extractor _)

    object petstore_auth_Action extends AuthenticatedBuilder(
        req => {
            val individualChecks = petstore_auth_Checks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    
    def petstore_auth_Extractor[User >: Any](header: RequestHeader): Option[User] = ???

    val petstore_auth_Checks = Seq(petstore_auth_Extractor _)

    object petstore_auth_Action extends AuthenticatedBuilder(
        req => {
            val individualChecks = petstore_auth_Checks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    
    
    
    
    
    def api_key_Extractor[User >: Any](header: RequestHeader): Option[User] = ???
    def petstore_auth_Extractor[User >: Any](header: RequestHeader): Option[User] = ???

    val api_key_petstore_auth_Checks = Seq(api_key_Extractor _, petstore_auth_Extractor _)

    object api_key_petstore_auth_Action extends AuthenticatedBuilder(
        req => {
            val individualChecks = api_key_petstore_auth_Checks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    
    def petstore_auth_Extractor[User >: Any](header: RequestHeader): Option[User] = ???

    val petstore_auth_Checks = Seq(petstore_auth_Extractor _)

    object petstore_auth_Action extends AuthenticatedBuilder(
        req => {
            val individualChecks = petstore_auth_Checks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    
    def petstore_auth_Extractor[User >: Any](header: RequestHeader): Option[User] = ???

    val petstore_auth_Checks = Seq(petstore_auth_Extractor _)

    object petstore_auth_Action extends AuthenticatedBuilder(
        req => {
            val individualChecks = petstore_auth_Checks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    
    def petstore_auth_Extractor[User >: Any](header: RequestHeader): Option[User] = ???

    val petstore_auth_Checks = Seq(petstore_auth_Extractor _)

    object petstore_auth_Action extends AuthenticatedBuilder(
        req => {
            val individualChecks = petstore_auth_Checks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    

    private def onUnauthorized[C](mimeType: String, content: C): RequestHeader => Result =_ => {
        implicit val writeable = PlayBodyParsing.anyToWritable[C](mimeType)
        Results.Unauthorized(content)
    }
}
