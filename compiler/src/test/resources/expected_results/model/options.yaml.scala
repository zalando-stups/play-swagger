package options
package object yaml {
import java.util.Date
import java.io.File
import de.zalando.play.controllers.ArrayWrapper

    type BasicRequired = ArrayWrapper[String]

    type BasicOptional = Option[BasicRequired]

    case class Basic(id: Long, required: BasicRequired, optional: BasicOptional) 

    

}
