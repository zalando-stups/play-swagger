



import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._
package full.petstore.api.yaml {





    class FullPetstoreApiYaml extends FullPetstoreApiYamlBase {
    val findPetsByTags = findPetsByTagsAction {
            (tags: PetsFindByStatusGetStatus) =>
???
            
        } //////// EOF ////////  findPetsByTagsAction
    val placeOrder = placeOrderAction {
            (body: StoresOrderPostBody) =>
???
            
        } //////// EOF ////////  placeOrderAction
    val createUser = createUserAction {
            (body: UsersUsernamePutBody) =>
???
            
        } //////// EOF ////////  createUserAction
    val createUsersWithListInput = createUsersWithListInputAction {
            (body: UsersCreateWithListPostBody) =>
???
            
        } //////// EOF ////////  createUsersWithListInputAction
    val getUserByName = getUserByNameAction {
            (username: String) =>
???
            
        } //////// EOF ////////  getUserByNameAction
    val updateUser = updateUserAction {
            input: (String, UsersUsernamePutBody) =>
            val (username, body) = input

            ???
            
        } //////// EOF ////////  updateUserAction
    val deleteUser = deleteUserAction {
            (username: String) =>
???
            
        } //////// EOF ////////  deleteUserAction
    val updatePet = updatePetAction {
            (body: PetsPostBody) =>
???
            
        } //////// EOF ////////  updatePetAction
    val addPet = addPetAction {
            (body: PetsPostBody) =>
???
            
        } //////// EOF ////////  addPetAction
    val createUsersWithArrayInput = createUsersWithArrayInputAction {
            (body: UsersCreateWithListPostBody) =>
???
            
        } //////// EOF ////////  createUsersWithArrayInputAction
    val getOrderById = getOrderByIdAction {
            (orderId: String) =>
???
            
        } //////// EOF ////////  getOrderByIdAction
    val deleteOrder = deleteOrderAction {
            (orderId: String) =>
???
            
        } //////// EOF ////////  deleteOrderAction
    val logoutUser = logoutUserAction {
            ???
            
        } //////// EOF ////////  logoutUserAction
    val getPetById = getPetByIdAction {
            (petId: Long) =>
???
            
        } //////// EOF ////////  getPetByIdAction
    val updatePetWithForm = updatePetWithFormAction {
            input: (String, String, String) =>
            val (petId, name, status) = input

            ???
            
        } //////// EOF ////////  updatePetWithFormAction
    val deletePet = deletePetAction {
            input: (String, Long) =>
            val (api_key, petId) = input

            ???
            
        } //////// EOF ////////  deletePetAction
    val findPetsByStatus = findPetsByStatusAction {
            (status: PetsFindByStatusGetStatus) =>
???
            
        } //////// EOF ////////  findPetsByStatusAction
    val loginUser = loginUserAction {
            input: (OrderStatus, OrderStatus) =>
            val (username, password) = input

            ???
            
        } //////// EOF ////////  loginUserAction
    }
}

