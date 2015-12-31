



import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._
package heroku.petstore.api.yaml {





    class HerokuPetstoreApiYaml extends HerokuPetstoreApiYamlBase {
    val get = getAction {
            (limit: PetBirthday) =>
???
            
        } //////// EOF ////////  getAction
    val put = putAction {
            (pet: Pet) =>
???
            
        } //////// EOF ////////  putAction
    val post = postAction {
            (pet: Pet) =>
???
            
        } //////// EOF ////////  postAction
    val getbyPetId = getbyPetIdAction {
            (petId: String) =>
???
            
        } //////// EOF ////////  getbyPetIdAction
    }
}

