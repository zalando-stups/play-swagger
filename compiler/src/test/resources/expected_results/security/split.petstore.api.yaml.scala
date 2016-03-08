package split.petstore.api.yaml

import scala.concurrent.Future
import play.api.mvc._
import de.zalando.play.controllers.{FutureAuthenticatedBuilder,PlayBodyParsing}
import de.zalando.play.controllers.ArrayWrapper
import org.joda.time.DateTime


trait SplitPetstoreApiYamlSecurity extends SecurityExtractors {
    
    object findPetsByTagsSecureAction extends FutureAuthenticatedBuilder(
        req => {
            val secureChecks = Seq(petstore_auth_Extractor _)
            val individualChecks: Future[Seq[Option[_]]] = Future.sequence(secureChecks.map(_.apply(req)))
                individualChecks.map { checks =>
                    checks.find(_.isEmpty).getOrElse(Option(checks.map(_.get)))
                }
        }, unauthorizedContent)
    
    object updatePetSecureAction extends FutureAuthenticatedBuilder(
        req => {
            val secureChecks = Seq(petstore_auth_Extractor _)
            val individualChecks: Future[Seq[Option[_]]] = Future.sequence(secureChecks.map(_.apply(req)))
                individualChecks.map { checks =>
                    checks.find(_.isEmpty).getOrElse(Option(checks.map(_.get)))
                }
        }, unauthorizedContent)
    
    object addPetSecureAction extends FutureAuthenticatedBuilder(
        req => {
            val secureChecks = Seq(petstore_auth_Extractor _)
            val individualChecks: Future[Seq[Option[_]]] = Future.sequence(secureChecks.map(_.apply(req)))
                individualChecks.map { checks =>
                    checks.find(_.isEmpty).getOrElse(Option(checks.map(_.get)))
                }
        }, unauthorizedContent)
    
    object getPetByIdSecureAction extends FutureAuthenticatedBuilder(
        req => {
            val secureChecks = Seq(api_key_Extractor _, petstore_auth_Extractor _)
            val individualChecks: Future[Seq[Option[_]]] = Future.sequence(secureChecks.map(_.apply(req)))
                individualChecks.map { checks =>
                    checks.find(_.isEmpty).getOrElse(Option(checks.map(_.get)))
                }
        }, unauthorizedContent)
    
    object updatePetWithFormSecureAction extends FutureAuthenticatedBuilder(
        req => {
            val secureChecks = Seq(petstore_auth_Extractor _)
            val individualChecks: Future[Seq[Option[_]]] = Future.sequence(secureChecks.map(_.apply(req)))
                individualChecks.map { checks =>
                    checks.find(_.isEmpty).getOrElse(Option(checks.map(_.get)))
                }
        }, unauthorizedContent)
    
    object deletePetSecureAction extends FutureAuthenticatedBuilder(
        req => {
            val secureChecks = Seq(petstore_auth_Extractor _)
            val individualChecks: Future[Seq[Option[_]]] = Future.sequence(secureChecks.map(_.apply(req)))
                individualChecks.map { checks =>
                    checks.find(_.isEmpty).getOrElse(Option(checks.map(_.get)))
                }
        }, unauthorizedContent)
    
    object findPetsByStatusSecureAction extends FutureAuthenticatedBuilder(
        req => {
            val secureChecks = Seq(petstore_auth_Extractor _)
            val individualChecks: Future[Seq[Option[_]]] = Future.sequence(secureChecks.map(_.apply(req)))
                individualChecks.map { checks =>
                    checks.find(_.isEmpty).getOrElse(Option(checks.map(_.get)))
                }
        }, unauthorizedContent)
    
}

