package de.zalando.swagger

import de.zalando.swagger.model.{PrimitiveType, CommonProperties}
import de.zalando.swagger.strictModel._

/**
  * @author  slasch 
  * @since   16.10.2015.
  */

object ValidationsConverter {

  def toValidations(s: Schema[_]): Seq[String] = validationsPF(s)

  def toValidations[T, CF](bp: BodyParameter[T]): Seq[String] = validationsPF(bp)

  def toValidations[T, CF](bp: NonBodyParameter[T]): Seq[String] = validationsPF(bp)

  def toValidations(nb: ParametersListItem with NonBodyParameterCommons[_, _]): Seq[String] = validationsPF(nb)

  @throws[MatchError]
  private def validationsPF[CF, T](bp: ValidationBase): Seq[String] = {
    bp match {
      case v: ArrayValidation[_] => toArrayValidations(v)
      case v: StringValidation => toStringValidations(v)
      case n: NumberValidation => toNumberValidations(n)
      case o: ObjectValidation => toObjectValidations(o)
    }
  }

  def toArrayValidations[T](a: ArrayValidation[T]): Seq[String] =
    Seq(
      ifDefined(a.maxItems, s"maxItems(${a.maxItems})" ),
      ifDefined(a.minItems, s"minItems(${a.minItems})" ),
      ifDefined(a.uniqueItems, s"uniqueItems(${a.uniqueItems})"),
      ifDefined(a.enum, s"enum(${a.enum.mkString(",")})" ) // TODO what if parameters contain commas ?
    ).flatten

  private def toStringValidations(p: StringValidation): Seq[String] = {
    val format = p match {
      case c: NonBodyParameterCommons[_, _] => Some(c.format)
      case _ => None
    }
    val emailConstraint: Option[String] = if (format.exists(_ == "email")) Some("emailAddress") else None
    Seq(
      ifDefined(p.maxLength, s"maxLength(${p.maxLength})"),
      ifDefined(p.minLength, s"minLength(${p.minLength})"),
      Option(p.pattern) map { p => s"""pattern("$p".r)""" },
      emailConstraint
    ).flatten
  }

  private def toNumberValidations(p: NumberValidation): Seq[String] = {
    val strictMax = Option(p.exclusiveMaximum).getOrElse(false)
    val strictMin = Option(p.exclusiveMinimum).getOrElse(false)
    Seq(
      ifDefined(p.maximum, s"max(${p.maximum.get.toInt}, $strictMax)"),
      ifDefined(p.minimum, s"min(${p.minimum.get.toInt}, $strictMin)"),
      ifDefined(p.multipleOf, s"multipleOf(${p.multipleOf.get})")
    ).flatten
  }

  private def toObjectValidations(p: ObjectValidation): Seq[String] =
    Seq(
      ifDefined(p.maxProperties, s"maxProperties(${p.maxProperties.get})"),
      ifDefined(p.minProperties, s"minProperties(${p.minProperties.get})")
    ).flatten

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

  private def ifDefined[T](validation: Option[T], result: => String) = validation map { _ => result }
}
