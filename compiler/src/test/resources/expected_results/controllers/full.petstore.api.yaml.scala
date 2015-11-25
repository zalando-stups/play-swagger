package full.petstore.api.yaml
import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._

object definitionsAction {
    import definitions._
    import java.util.Date
    }
object pathsAction {
    import definitions._
    import paths._
    import definitionsAction._
    class FullPetstoreApiYaml extends FullPetstoreApiYamlBase {
        val findPetsByTags = findPetsByTagsAction {
            (tags: PetsFindByStatusGetStatus) =>
            ???
        }
        val placeOrder = placeOrderAction {
            (body: StoresOrderPostBody) =>
            ???
        }
        val createUser = createUserAction {
            (body: UsersUsernamePutBody) =>
            ???
        }
        val createUsersWithListInput = createUsersWithListInputAction {
            (body: UsersCreateWithListPostBody) =>
            ???
        }
        val getUserByName = getUserByNameAction {
            (username: String) =>
            ???
        }
        val updateUser = updateUserAction {
            input: (String, UsersUsernamePutBody) =>
            val (username, body) = input
            
            ???
        }
        val deleteUser = deleteUserAction {
            (username: String) =>
            ???
        }
        val updatePet = updatePetAction {
            (body: PetsPostBody) =>
            ???
        }
        val addPet = addPetAction {
            (body: PetsPostBody) =>
            ???
        }
        val createUsersWithArrayInput = createUsersWithArrayInputAction {
            (body: UsersCreateWithListPostBody) =>
            ???
        }
        val getOrderById = getOrderByIdAction {
            (orderId: String) =>
            ???
        }
        val deleteOrder = deleteOrderAction {
            (orderId: String) =>
            ???
        }
        val logoutUser = logoutUserAction {
            
            ???
        }
        val getPetById = getPetByIdAction {
            (petId: Long) =>
            ???
        }
        val updatePetWithForm = updatePetWithFormAction {
            input: (String, String, String) =>
            val (petId, name, status) = input
            
            ???
        }
        val deletePet = deletePetAction {
            input: (String, Long) =>
            val (api_key, petId) = input
            
            ???
        }
        val findPetsByStatus = findPetsByStatusAction {
            (status: PetsFindByStatusGetStatus) =>
            ???
        }
        val loginUser = loginUserAction {
            input: (OrderStatus, OrderStatus) =>
            val (username, password) = input
            
            ???
        }
        }
    }
