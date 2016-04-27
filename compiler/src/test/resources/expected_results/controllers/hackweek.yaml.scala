
import play.api.mvc.{Action, Controller}

import play.api.data.validation.Constraint

import de.zalando.play.controllers._

import PlayBodyParsing._

import PlayValidations._

import scala.util._



package hackweek.yaml {

    class HackweekYaml extends HackweekYamlBase {
        val getschemaModel = getschemaModelAction { (root: ModelSchemaRoot) =>
            

            // Response: Success((200, ModelSchemaRoot))
            Failure(???)

            

        } //////// EOF ////////  getschemaModelAction
    }
}
