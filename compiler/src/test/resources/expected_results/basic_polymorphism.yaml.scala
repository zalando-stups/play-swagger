package basic_polymorphism.yaml
import scala.Option
object definitions {
  trait PetDef {
    def name: String
    def petType: String
  }
  // A representation of a dog
  trait DogDef extends PetDef {
    // the size of the pack the dog is from
    def packSize: Option[Int]
  }
  case class Pet(
    name: String,
    petType: String
  ) extends PetDef
  // A representation of a cat
  case class Cat(
    // The measured skill for hunting
    huntingSkill: Option[String],
    name: String,
    petType: String
  ) extends PetDef
  // A representation of a dog
  case class Dog(
    // the size of the pack the dog is from
    packSize: Option[Int],
    name: String,
    petType: String
  ) extends PetDef with DogDef
  // A concrete implementation of a dog
  case class Labrador(
    // the cuteness of the animal in percent
    cuteness: Option[Int],
    // the size of the pack the dog is from
    packSize: Option[Int],
    name: String,
    petType: String
  ) extends DogDef
}