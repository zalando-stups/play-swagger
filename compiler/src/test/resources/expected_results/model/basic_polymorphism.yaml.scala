package basic_polymorphism
package object yaml {
import java.util.Date
import java.io.File
import de.zalando.play.controllers.ArrayWrapper

trait IPet {

    def name: String

    def petType: String

    }

    case class Cat(name: String, petType: String, huntingSkill: String) extends IPet 

    case class Dog(name: String, petType: String, packSize: Int) extends IPet 

    case class CatNDog(name: String, petType: String, packSize: Int, huntingSkill: String) extends IPet 

    case class Pet(name: String, petType: String) extends IPet 

    case class Labrador(name: String, petType: String, packSize: Int, cuteness: Int) extends IPet 

    

}
