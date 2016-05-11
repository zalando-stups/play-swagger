package basic_extension

package object yaml {

    import scala.math.BigInt





    case class ErrorModel(message: String, code: BigInt) 
    case class ExtendedErrorModel(message: String, code: BigInt, rootCause: String) 


}
