package de.zalando.play.controllers

import play.api.data.validation._

/**
 * @since 03.09.2015
 */
// Parsing error to display to the user of the API
case class ParsingError(messages: Seq[String], args: Seq[Any] = Nil)

trait Validator {
  def errors: Seq[ParsingError]
}
/**
  * This trait allows recursive validation of complex types
  * The idea is like this:
  *   - for primitive types, some {@Constraint}s can be defined
  *   - {@Constraint}s then wrapped into {@ValidationBase}s
  *   - {@ValidationBase}s then combined inside of the {@RecursiveValidator}
  */
trait RecursiveValidator extends Validator {
  def validators: Seq[RecursiveValidator]

  override def errors: Seq[ParsingError] = validators.flatMap(_.errors)

}

/**
  * This is a wrapper for a constraint for primitive type
  * @tparam T
  */
trait ValidationBase[T] extends Validator {
  def instance: T
  def constraints: Seq[Constraint[T]]

  // helper to convert failing constraints to errors
  override def errors: Seq[ParsingError] = {
    constraints.map(_(instance)).collect {
      case Invalid(errors) => errors.toSeq
    }.flatten.map(ve => ParsingError(ve.messages, ve.args))
  }
}

object PlayValidations extends Constraints {

  /**
   * Defines a ‘lowerCase’ constraint for `String` values, i.e. one in which string
   * should be non-empty and lowercase. Has no direct relationship with swagger,
   * for testing purposes
   *
   * '''name'''[constraint.lowerCase]
   * '''error'''[error.lowerCase]
   */
  def lowerCase: Constraint[String] = Constraint[String]("constraint.lowerCase") { o =>
    if (o == null) Invalid(ValidationError("error.required"))
    else if (o.trim.isEmpty) Invalid(ValidationError("error.lowerCase")) else Valid
  }
  /**
   * Defines a ‘multipleOf’ constraint for `Number` values, i.e. one in which a number
   * valid against "multipleOf" if the result of the division of the instance by
   * this keyword's value is an integer
   *
   * '''name'''[constraint.multipleOf]
   * '''error'''[error.multipleOf]
   */
  def multipleOf[T](mOf: T)(implicit integral: scala.math.Integral[T]): Constraint[T] = Constraint[T]("constraint.multipleOf") { o =>
    if (o == null) Invalid(ValidationError("error.required"))
    else if (integral.rem(o, mOf) != 0) Invalid(ValidationError("error.multipleOf")) else Valid
  }

  def enum[T](commaSeparatedList: String): Constraint[T] = Constraint[T]("constraint.enum") { o =>
    if (o == null) Invalid(ValidationError("error.required"))
    else if (commaSeparatedList.isEmpty) Invalid(ValidationError("error.enum.empty"))
    else if (commaSeparatedList.split(",[^,]").map(_.replaceAll(",,",",")).contains(o.toString)) Valid
    else Invalid(ValidationError("error.enum.not.allowed", o))
  }
}
