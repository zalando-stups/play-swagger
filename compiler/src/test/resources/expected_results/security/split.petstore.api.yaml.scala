package split.petstore.api.yaml

import play.api.mvc._
import Security.AuthenticatedBuilder
import de.zalando.play.controllers.PlayBodyParsing
import de.zalando.play.controllers.ArrayWrapper
import org.joda.time.DateTime

trait SecurityExtractors {
    def api_key_Extractor[User >: Any](header: RequestHeader): Option[User] = ???
    def petstore_auth_Extractor[User >: Any](header: RequestHeader): Option[User] = ???

}


trait SplitPetstoreApiYamlSecurity extends SecurityExtractors {
    val unauthorizedContent = ???
    val mimeType: String = ???

    
    object findPetsByTagsSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(petstore_auth_Extractor _)
            val individualChecks = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    object updatePetSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(petstore_auth_Extractor _)
            val individualChecks = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    object addPetSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(petstore_auth_Extractor _)
            val individualChecks = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    object getPetByIdSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(api_key_Extractor _, petstore_auth_Extractor _)
            val individualChecks = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    object updatePetWithFormSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(petstore_auth_Extractor _)
            val individualChecks = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    object deletePetSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(petstore_auth_Extractor _)
            val individualChecks = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        },
        onUnauthorized(mimeType, unauthorizedContent)
    )
    
    object findPetsByStatusSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(petstore_auth_Extractor _)
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
