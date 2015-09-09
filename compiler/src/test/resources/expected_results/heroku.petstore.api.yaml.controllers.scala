package heroku.petstore.api.yaml

import scala.Option
import definitions.Pet

object controllers {
  class ApiYaml extends ApplicationBase {

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
}