
import play.api.mvc.{Action, Controller}

import play.api.data.validation.Constraint

import de.zalando.play.controllers._

import PlayBodyParsing._

import PlayValidations._

import scala.util._


/**
 * This controller is re-generated after each change in the specification.
 * Please only place your hand-written code between appropriate comments in the body of the controller.
 */

package split.petstore.api.yaml {

    class SplitPetstoreApiYaml extends SplitPetstoreApiYamlBase {
        val findPetsByTags = findPetsByTagsAction { (tags: PetsFindByStatusGetStatus) =>  
            // ----- Start of unmanaged code area for action  SplitPetstoreApiYaml.findPetsByTags
            Failure(???)
            // ----- End of unmanaged code area for action  SplitPetstoreApiYaml.findPetsByTags
        }
        val placeOrder = placeOrderAction { (body: StoresOrderPostBody) =>  
            // ----- Start of unmanaged code area for action  SplitPetstoreApiYaml.placeOrder
            Failure(???)
            // ----- End of unmanaged code area for action  SplitPetstoreApiYaml.placeOrder
        }
        val createUser = createUserAction { (body: UsersUsernamePutBody) =>  
            // ----- Start of unmanaged code area for action  SplitPetstoreApiYaml.createUser
            Failure(???)
            // ----- End of unmanaged code area for action  SplitPetstoreApiYaml.createUser
        }
        val createUsersWithListInput = createUsersWithListInputAction { (body: UsersCreateWithListPostBody) =>  
            // ----- Start of unmanaged code area for action  SplitPetstoreApiYaml.createUsersWithListInput
            Failure(???)
            // ----- End of unmanaged code area for action  SplitPetstoreApiYaml.createUsersWithListInput
        }
        val getUserByName = getUserByNameAction { (username: String) =>  
            // ----- Start of unmanaged code area for action  SplitPetstoreApiYaml.getUserByName
            Failure(???)
            // ----- End of unmanaged code area for action  SplitPetstoreApiYaml.getUserByName
        }
        val updateUser = updateUserAction { input: (String, UsersUsernamePutBody) =>
            val (username, body) = input
            // ----- Start of unmanaged code area for action  SplitPetstoreApiYaml.updateUser
            Failure(???)
            // ----- End of unmanaged code area for action  SplitPetstoreApiYaml.updateUser
        }
        val deleteUser = deleteUserAction { (username: String) =>  
            // ----- Start of unmanaged code area for action  SplitPetstoreApiYaml.deleteUser
            Failure(???)
            // ----- End of unmanaged code area for action  SplitPetstoreApiYaml.deleteUser
        }
        val updatePet = updatePetAction { (body: PetsPostBody) =>  
            // ----- Start of unmanaged code area for action  SplitPetstoreApiYaml.updatePet
            Failure(???)
            // ----- End of unmanaged code area for action  SplitPetstoreApiYaml.updatePet
        }
        val addPet = addPetAction { (body: PetsPostBody) =>  
            // ----- Start of unmanaged code area for action  SplitPetstoreApiYaml.addPet
            Failure(???)
            // ----- End of unmanaged code area for action  SplitPetstoreApiYaml.addPet
        }
        val createUsersWithArrayInput = createUsersWithArrayInputAction { (body: UsersCreateWithListPostBody) =>  
            // ----- Start of unmanaged code area for action  SplitPetstoreApiYaml.createUsersWithArrayInput
            Failure(???)
            // ----- End of unmanaged code area for action  SplitPetstoreApiYaml.createUsersWithArrayInput
        }
        val getOrderById = getOrderByIdAction { (orderId: String) =>  
            // ----- Start of unmanaged code area for action  SplitPetstoreApiYaml.getOrderById
            Failure(???)
            // ----- End of unmanaged code area for action  SplitPetstoreApiYaml.getOrderById
        }
        val deleteOrder = deleteOrderAction { (orderId: String) =>  
            // ----- Start of unmanaged code area for action  SplitPetstoreApiYaml.deleteOrder
            Failure(???)
            // ----- End of unmanaged code area for action  SplitPetstoreApiYaml.deleteOrder
        }
        val logoutUser = logoutUserAction {  _ =>  
            // ----- Start of unmanaged code area for action  SplitPetstoreApiYaml.logoutUser
            Failure(???)
            // ----- End of unmanaged code area for action  SplitPetstoreApiYaml.logoutUser
        }
        val getPetById = getPetByIdAction { (petId: Long) =>  
            // ----- Start of unmanaged code area for action  SplitPetstoreApiYaml.getPetById
            Failure(???)
            // ----- End of unmanaged code area for action  SplitPetstoreApiYaml.getPetById
        }
        val updatePetWithForm = updatePetWithFormAction { input: (String, String, String) =>
            val (petId, name, status) = input
            // ----- Start of unmanaged code area for action  SplitPetstoreApiYaml.updatePetWithForm
            Failure(???)
            // ----- End of unmanaged code area for action  SplitPetstoreApiYaml.updatePetWithForm
        }
        val deletePet = deletePetAction { input: (String, Long) =>
            val (api_key, petId) = input
            // ----- Start of unmanaged code area for action  SplitPetstoreApiYaml.deletePet
            Failure(???)
            // ----- End of unmanaged code area for action  SplitPetstoreApiYaml.deletePet
        }
        val findPetsByStatus = findPetsByStatusAction { (status: PetsFindByStatusGetStatus) =>  
            // ----- Start of unmanaged code area for action  SplitPetstoreApiYaml.findPetsByStatus
            Failure(???)
            // ----- End of unmanaged code area for action  SplitPetstoreApiYaml.findPetsByStatus
        }
        val loginUser = loginUserAction { input: (OrderStatus, OrderStatus) =>
            val (username, password) = input
            // ----- Start of unmanaged code area for action  SplitPetstoreApiYaml.loginUser
            Failure(???)
            // ----- End of unmanaged code area for action  SplitPetstoreApiYaml.loginUser
        }
    
    }
}
