
import play.api.mvc.{Action, Controller}

import play.api.data.validation.Constraint

import de.zalando.play.controllers._

import PlayBodyParsing._

import PlayValidations._

import scala.util._



package full.petstore.api.yaml {

    class FullPetstoreApiYaml extends FullPetstoreApiYamlBase {
        val findPetsByTags = findPetsByTagsAction { (tags: PetsFindByStatusGetStatus) =>
            
            // Response: Success((400, Null, ))
            // Response: Success((200, Seq[Pet]))
            

            Failure(???)

            

        } //////// EOF ////////  findPetsByTagsAction
        val placeOrder = placeOrderAction { (body: StoresOrderPostBody) =>
            
            // Response: Success((400, Null, ))
            // Response: Success((200, Order))
            

            Failure(???)

            

        } //////// EOF ////////  placeOrderAction
        val createUser = createUserAction { (body: UsersUsernamePutBody) =>
            
            

            Failure(???)

            

        } //////// EOF ////////  createUserAction
        val createUsersWithListInput = createUsersWithListInputAction { (body: UsersCreateWithListPostBody) =>
            
            

            Failure(???)

            

        } //////// EOF ////////  createUsersWithListInputAction
        val getUserByName = getUserByNameAction { (username: String) =>
            
            // Response: Success((200, User, ))
            // Response: Success((404, Null, ))
            // Response: Success((400, Null))
            

            Failure(???)

            

        } //////// EOF ////////  getUserByNameAction
        val updateUser = updateUserAction { input: (String, UsersUsernamePutBody) =>
            val (username, body) = input
            
            // Response: Success((404, Null, ))
            // Response: Success((400, Null))
            

            Failure(???)

            

        } //////// EOF ////////  updateUserAction
        val deleteUser = deleteUserAction { (username: String) =>
            
            // Response: Success((404, Null, ))
            // Response: Success((400, Null))
            

            Failure(???)

            

        } //////// EOF ////////  deleteUserAction
        val updatePet = updatePetAction { (body: PetsPostBody) =>
            
            // Response: Success((405, Null, ))
            // Response: Success((404, Null, ))
            // Response: Success((400, Null))
            

            Failure(???)

            

        } //////// EOF ////////  updatePetAction
        val addPet = addPetAction { (body: PetsPostBody) =>
            
            // Response: Success((405, Null))
            

            Failure(???)

            

        } //////// EOF ////////  addPetAction
        val createUsersWithArrayInput = createUsersWithArrayInputAction { (body: UsersCreateWithListPostBody) =>
            
            

            Failure(???)

            

        } //////// EOF ////////  createUsersWithArrayInputAction
        val getOrderById = getOrderByIdAction { (orderId: String) =>
            
            // Response: Success((404, Null, ))
            // Response: Success((400, Null, ))
            // Response: Success((200, Order))
            

            Failure(???)

            

        } //////// EOF ////////  getOrderByIdAction
        val deleteOrder = deleteOrderAction { (orderId: String) =>
            
            // Response: Success((404, Null, ))
            // Response: Success((400, Null))
            

            Failure(???)

            

        } //////// EOF ////////  deleteOrderAction
        val logoutUser = logoutUserAction { _ =>
            
            

            Failure(???)

            

        } //////// EOF ////////  logoutUserAction
        val getPetById = getPetByIdAction { (petId: Long) =>
            
            // Response: Success((404, Null, ))
            // Response: Success((400, Null, ))
            // Response: Success((200, Pet))
            

            Failure(???)

            

        } //////// EOF ////////  getPetByIdAction
        val updatePetWithForm = updatePetWithFormAction { input: (String, String, String) =>
            val (petId, name, status) = input
            
            // Response: Success((405, Null))
            

            Failure(???)

            

        } //////// EOF ////////  updatePetWithFormAction
        val deletePet = deletePetAction { input: (String, Long) =>
            val (api_key, petId) = input
            
            // Response: Success((400, Null))
            

            Failure(???)

            

        } //////// EOF ////////  deletePetAction
        val findPetsByStatus = findPetsByStatusAction { (status: PetsFindByStatusGetStatus) =>
            
            // Response: Success((200, Seq[Pet], ))
            // Response: Success((400, Null))
            

            Failure(???)

            

        } //////// EOF ////////  findPetsByStatusAction
        val loginUser = loginUserAction { input: (OrderStatus, OrderStatus) =>
            val (username, password) = input
            
            // Response: Success((200, String, ))
            // Response: Success((400, Null))
            

            Failure(???)

            

        } //////// EOF ////////  loginUserAction
    }
}
