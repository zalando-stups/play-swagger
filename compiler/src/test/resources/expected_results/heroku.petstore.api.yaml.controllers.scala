package heroku.petstore.api.yaml.controllers

import scala.Option
import heroku.petstore.api.yaml.definitions.Pet

class ApiYaml extends ApiYamlBase {

  // handler for GET /
  def allPets = allPetsAction { (limit: Option[Int]) =>
    ???
  }

  // handler for PUT /
  def updatePet = updatePetAction { (pet: Pet) =>
    ???
  }

  // handler for POST /
  def createPet = createPetAction { (pet: Pet) =>
    ???
  }

  // handler for GET /{petId}
  def getPet = getPetAction { (petId: String) =>
    ???
  }

}