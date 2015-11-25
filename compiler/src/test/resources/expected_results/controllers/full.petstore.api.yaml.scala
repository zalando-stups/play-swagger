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
            input: (PetsFindByStatusGetStatus) =>
            val (tags) = input
            ???
        }
        val placeOrder = placeOrderAction {
            input: (StoresOrderPostBody) =>
            val (body) = input
            ???
        }
        val createUser = createUserAction {
            input: (UsersUsernamePutBody) =>
            val (body) = input
            ???
        }
        val createUsersWithListInput = createUsersWithListInputAction {
            input: (UsersCreateWithListPostBody) =>
            val (body) = input
            ???
        }
        val getUserByName = getUserByNameAction {
            input: (String) =>
            val (username) = input
            ???
        }
        val updateUser = updateUserAction {
            input: (String, UsersUsernamePutBody) =>
            val (username, body) = input
            ???
        }
        val deleteUser = deleteUserAction {
            input: (String) =>
            val (username) = input
            ???
        }
        val updatePet = updatePetAction {
            input: (PetsPostBody) =>
            val (body) = input
            ???
        }
        val addPet = addPetAction {
            input: (PetsPostBody) =>
            val (body) = input
            ???
        }
        val createUsersWithArrayInput = createUsersWithArrayInputAction {
            input: (UsersCreateWithListPostBody) =>
            val (body) = input
            ???
        }
        val getOrderById = getOrderByIdAction {
            input: (String) =>
            val (orderId) = input
            ???
        }
        val deleteOrder = deleteOrderAction {
            input: (String) =>
            val (orderId) = input
            ???
        }
        val logoutUser = logoutUserAction {
            ???
        }
        val getPetById = getPetByIdAction {
            input: (Long) =>
            val (petId) = input
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
            input: (PetsFindByStatusGetStatus) =>
            val (status) = input
            ???
        }
        val loginUser = loginUserAction {
            input: (OrderStatus, OrderStatus) =>
            val (username, password) = input
            ???
        }
        }
    }
