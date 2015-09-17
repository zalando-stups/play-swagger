package heroku.petstore.api.yaml.controllers
import scala.Option
import heroku.petstore.api.yaml.definitions.Pet
class ApiYaml extends ApiYamlBase {
  // handler for GET /
  def allPets = allPetsAction { in : (Option[Int]) =>
   val (limit) = in
    ???
  }
  // handler for PUT /
  def updatePet = updatePetAction { in : (Pet) =>
   val (pet) = in
    ???
  }
  // handler for POST /
  def createPet = createPetAction { in : (Pet) =>
   val (pet) = in
    ???
  }
  // handler for GET /{petId}
  def getPet = getPetAction { in : (String) =>
   val (petId) = in
    ???
  }
}