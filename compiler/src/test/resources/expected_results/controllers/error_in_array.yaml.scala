
import play.api.mvc.{Action, Controller}

import play.api.data.validation.Constraint

import de.zalando.play.controllers._

import PlayBodyParsing._

import PlayValidations._

import scala.util._



package error_in_array.yaml {

    class Error_in_arrayYaml extends Error_in_arrayYamlBase {
        val getschemaModel = getschemaModelAction { (root: ModelSchemaRoot) =>
            

            // Response: Success((200, ModelSchemaRoot))
            Failure(???)

            

        } //////// EOF ////////  getschemaModelAction
    }
}
