package basic_extension
package object yaml {
import java.util.Date
import java.io.File
import de.zalando.play.controllers.ArrayWrapper

case class ErrorModel(message: String, code: Int) 

    case class ExtendedErrorModel(message: String, code: Int, rootCause: String) 

    

}
