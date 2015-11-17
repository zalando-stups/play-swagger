package nested_options.yaml
object definitions {
    type BasicOptional = Option[BasicOptionalOpt]
    type BasicOptionalNested_optional = Option[String]
    case class Basic(optional: BasicOptional) 
    case class BasicOptionalOpt(nested_optional: BasicOptionalNested_optional) 
    }
