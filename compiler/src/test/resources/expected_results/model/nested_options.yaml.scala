package nested_options
package object yaml {
import java.util.Date
import java.io.File

type BasicOptional = Option[BasicOptionalOpt]

    type BasicOptionalNested_optional = Option[String]

    case class Basic(optional: BasicOptional) 

    case class BasicOptionalOpt(nested_optional: BasicOptionalNested_optional) 

    

}
