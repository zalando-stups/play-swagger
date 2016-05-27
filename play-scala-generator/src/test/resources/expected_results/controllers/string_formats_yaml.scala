
import play.api.mvc.{Action, Controller}

import play.api.data.validation.Constraint

import de.zalando.play.controllers._

import PlayBodyParsing._

import PlayValidations._

import scala.util._

import de.zalando.play.controllers.BinaryString

import BinaryString._


/**
 * This controller is re-generated after each change in the specification.
 * Please only place your hand-written code between appropriate comments in the body of the controller.
 */

package string_formats.yaml {

    class String_formatsYaml extends String_formatsYamlBase {
        val get = getAction { input: (BinaryString, GetBase64, GetDate, GetDate_time) =>
            val (petId, base64, date, date_time) = input
            // ----- Start of unmanaged code area for action  String_formatsYaml.get
            NotImplementedYet
            // ----- End of unmanaged code area for action  String_formatsYaml.get
        }
    
    }
}
