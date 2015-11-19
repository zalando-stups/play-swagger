package basic_extension.yaml
object definitions {
    case class ErrorModel(message: String, code: Int) 
    case class ExtendedErrorModel(message: String, code: Int, rootCause: String) 
    }
