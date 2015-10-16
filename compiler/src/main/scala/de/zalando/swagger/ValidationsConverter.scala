package de.zalando.swagger

import de.zalando.swagger.model.{PrimitiveType, CommonProperties}
import de.zalando.swagger.strictModel.{ArrayValidation, NonBodyParameterCommons, ParametersListItem, BodyParameter}

/**
  * @author  slasch 
  * @since   16.10.2015.
  */

// TODO probably additional parameters will be needed for definitions and inline objects
object ValidationConverter {
  def toValidations(bp: BodyParameter[_]): Seq[String] = Nil // TODO

  def toValidations(nb: (ParametersListItem with NonBodyParameterCommons[_, _])): Seq[String] = Nil // TODO

  def toValidations[T](a: ArrayValidation[T]): Seq[String] = {
    Seq(
      a.maxItems map { p => s"maxItems($p)" },
      a.minItems map { p => s"minItems($p)" },
      a.uniqueItems map { p => s"uniqueItems($p)" },
      a.enum map { p => s"enum(${p.mkString(",")})" } // TODO what if parameters contain commas ?
    ).flatten
  }
  def toValidations(p: CommonProperties):Seq[String] = p.`type` match {
    case PrimitiveType.STRING =>
      val emailConstraint: Option[String] = if ("email" == p.format) Some("emailAddress") else None
      val stringConstraints: Seq[String] = Seq(
        ifNot0(p.maxLength, s"maxLength(${p.maxLength})"),
        ifNot0(p.minLength, s"minLength(${p.minLength})"),
        Option(p.pattern) map { p => s"""pattern("$p".r)""" },
        emailConstraint
      ).flatten
      stringConstraints
    case PrimitiveType.NUMBER | PrimitiveType.INTEGER =>
      val strictMax = Option(p.exclusiveMaximum).getOrElse(false)
      val strictMin = Option(p.exclusiveMinimum).getOrElse(false)
      val numberConstraints = Seq(
        ifNot0(p.maximum.toInt, s"max(${p.maximum.toInt}, $strictMax)"),
        ifNot0(p.minimum.toInt, s"min(${p.minimum.toInt}, $strictMin)"),
        ifNot0(p.multipleOf, s"multipleOf(${p.multipleOf})")
      ).flatten
      numberConstraints
    case PrimitiveType.ARRAY =>
      // TODO these are not implemented in PlayValidations yet
      val arrayConstraints = Seq(
        Option(p.maxItems).map { p => s"maxItems($p)" },
        Option(p.minItems).map { p => s"minItems($p)" },
        Option(p.uniqueItems)map { p => s"uniqueItems($p)" }
      ).flatten
      Seq.empty[String]
    // TODO implement objects and other types
    case _ => Seq.empty[String]
  }

  def ifNot0(check:Int, result: String): Option[String] = if (check != 0) Some(result) else None
}
