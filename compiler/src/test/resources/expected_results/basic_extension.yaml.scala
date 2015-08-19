package basic_extension.yaml
import scala.Option
object definitions {
  // Basic error model
  trait ErrorModelDef {
    // The text of the error message
    def message: String
    // The error code
    def code: Int
  }
  // Basic error model
  case class ErrorModel(
    // The text of the error message
    message: String,
    // The error code
    code: Int
  ) extends ErrorModelDef
  // Extended error model
  case class ExtendedErrorModel(
    rootCause: Option[String],
    // The text of the error message
    message: String,
    // The error code
    code: Int
  ) extends ErrorModelDef
}