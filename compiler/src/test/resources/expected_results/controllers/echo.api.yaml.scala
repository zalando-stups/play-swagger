
import play.api.mvc.{Action, Controller}

import play.api.data.validation.Constraint

import de.zalando.play.controllers._

import PlayBodyParsing._

import PlayValidations._

import scala.util._



package echo {

    class EchoHandler extends EchoHandlerBase {
        val method = methodAction { _ =>
            
            // Response: Success((200, Null))
            

            Failure(???)

            

        } //////// EOF ////////  methodAction
    }
}
package echo {

    class EchoApiYaml extends EchoApiYamlBase {
        val post = postAction { input: (PostName, PostName) =>
            val (name, year) = input
            
            // Response: Success((200, PostResponses200))
            

            Failure(???)

            

        } //////// EOF ////////  postAction
        val gettest_pathById = gettest_pathByIdAction { (id: String) =>
            
            // Response: Success((200, Null))
            

            Failure(???)

            

        } //////// EOF ////////  gettest_pathByIdAction
    }
}
