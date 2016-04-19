
import play.api.mvc.{Action, Controller}

import play.api.data.validation.Constraint

import de.zalando.play.controllers._

import PlayBodyParsing._

import PlayValidations._

import scala.util._



package heroku.petstore.api.yaml {

    class HerokuPetstoreApiYaml extends HerokuPetstoreApiYamlBase {
        val get = getAction { (limit: Int) =>
            

            // Response: Success((200, Seq[Pet]))
            Failure(???)

            

        } //////// EOF ////////  getAction
        val put = putAction { (pet: PutPet) =>
            

            // Response: Success((200, Null))
            Failure(???)

            

        } //////// EOF ////////  putAction
        val post = postAction { (pet: Pet) =>
            

            // Response: Success((200, Null))
            Failure(???)

            

        } //////// EOF ////////  postAction
        val getbyPetId = getbyPetIdAction { (petId: String) =>
            

            // Response: Success((200, Null))
            Failure(???)

            

        } //////// EOF ////////  getbyPetIdAction
    }
}
