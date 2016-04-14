
import play.api.mvc.{Action, Controller}

import play.api.data.validation.Constraint

import de.zalando.play.controllers._

import PlayBodyParsing._

import PlayValidations._

import scala.util._



package security.api.yaml {

    class SecurityApiYaml extends SecurityApiYamlBase {
        val getPetsById = getPetsByIdAction { (id: PetsIdGetId) =>
            
            // Response: Success((200, Seq[Pet]))
            

            Failure(???)

            

        } //////// EOF ////////  getPetsByIdAction
    }
}
