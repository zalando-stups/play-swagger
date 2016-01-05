package basic_extension
package object yaml {
    case class ErrorModel(message: String, code: Int)

    case class ExtendedErrorModel(message: String, code: Int, rootCause: String) 

    

}
