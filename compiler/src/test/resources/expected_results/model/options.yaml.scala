package options
package object yaml {
import java.util.Date
import java.io.File


    type BasicRequired = scala.collection.Seq[String]

    type BasicOptional = Option[BasicRequired]

    case class Basic(id: Long, 
required: BasicRequired, 
optional: BasicOptional
) 

    

}
