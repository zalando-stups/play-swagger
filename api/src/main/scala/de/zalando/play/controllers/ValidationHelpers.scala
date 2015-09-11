package de.zalando.play.controllers

import play.api.data.validation._

/**
 * @since 03.09.2015
 */
// Parsing error to display to the user of the API
case class ParsingError(messages: Seq[String], args: Seq[Any] = Nil)

trait ValidationBase[T] {

  def constraints: Seq[Constraint[T]]// =

  // apply all constraints defined for a single field
  def applyConstraints(t: T): Either[Seq[ParsingError], T] = {
    Right(t).right.flatMap { v =>
      Option(collectErrors(v)).filterNot(_.isEmpty).toLeft(v)
    }
  }
  // helper to convert failing constraints to errors
  def collectErrors(t: T): Seq[ParsingError] = {
    constraints.map(_(t)).collect {
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
}
