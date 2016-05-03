
import play.api.mvc.{Action, Controller}

import play.api.data.validation.Constraint

import de.zalando.play.controllers._

import PlayBodyParsing._

import PlayValidations._

import scala.util._

import java.io.File



package form_data.yaml {

    class Form_dataYaml extends Form_dataYamlBase {
        val postmultipart = postmultipartAction { input: (String, BothPostYear, MultipartPostAvatar) =>
            val (name, year, avatar) = input
            

            Failure(???)

            

        } //////// EOF ////////  postmultipartAction
        val posturl_encoded = posturl_encodedAction { input: (String, BothPostYear, File) =>
            val (name, year, avatar) = input
            

            Failure(???)

            

        } //////// EOF ////////  posturl_encodedAction
        val postboth = postbothAction { input: (String, BothPostYear, MultipartPostAvatar, File) =>
            val (name, year, avatar, ringtone) = input
            

            Failure(???)

            

        } //////// EOF ////////  postbothAction
    }
}
