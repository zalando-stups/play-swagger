package de.zalando.swagger

import de.zalando.swagger.strictModel._

/**
  * @author  slasch 
  * @since   16.10.2015.
  */

object ValidationsConverter {

  def toValidations(s: Schema[_]): Seq[String] = validationsPF(s)

  def toValidations[T, CF](bp: BodyParameter[T]): Seq[String] = validationsPF(bp)

  def toValidations[T, CF](bp: NonBodyParameter[T]): Seq[String] = validationsPF(bp)

  def toValidations(nb: NonBodyParameterCommons[_, _]): Seq[String] = validationsPF(nb)

  def toValidations[T, CF](bp: PrimitivesItems[T]): Seq[String] = validationsPF(bp)

  private val allValidations = Seq[PartialFunction[ValidationBase, Seq[String]]](
    { case v: ArrayValidation[_] => toArrayValidations(v) },
    { case v: StringValidation => toStringValidations(v) },
    { case n: NumberValidation[_] => toNumberValidations(n) },
    { case o: ObjectValidation => toObjectValidations(o) }
  )

  private def validationsPF[CF, T](b: ValidationBase): Seq[String] = {
    val default: ValidationBase => Seq[String] = _ => Nil
    allValidations flatMap { v => v.applyOrElse(b, default) }
  }

  def toArrayValidations[T](a: ArrayValidation[T]): Seq[String] =
    Seq(
      ifDefined(a.maxItems, s"maxItems(${a.maxItems.get})" ),
      ifDefined(a.minItems, s"minItems(${a.minItems.get})" ),
      ifDefined(a.uniqueItems, s"uniqueItems(${a.uniqueItems.get})"),
      ifDefined(a.enum, "enum(\"" + a.enum.get.map(a => a.toString.replaceAll(",",",,")).mkString(",") + "\")" )
    ).flatten

  private def toStringValidations(p: StringValidation): Seq[String] = {
    val format = p match {
      case c: NonBodyParameterCommons[_, _] => Some(c.format)
      case _ => None
    }
    val emailConstraint: Option[String] = if (format.exists(_ == "email")) Some("emailAddress") else None
    val result = Seq(
      ifDefined(p.maxLength, s"maxLength(${p.maxLength.get})"),
      ifDefined(p.minLength, s"minLength(${p.minLength.get})"),
      p.pattern map { p => "pattern(\"\"\"" + p + "\"\"\".r)"},
      emailConstraint
    ).flatten
    if (result.nonEmpty && format.exists(s => s == "date-time" || s == "date")) {
      println(s"WARN: Ignoring nonsense validations for ${format.get}: ${result.mkString(", ")}")
      Seq.empty[String]
    } else {
      result
    }
  }

  private def toNumberValidations(p: NumberValidation[_]): Seq[String] = {
    val strictMax = p.exclusiveMaximum.getOrElse(false)
    val strictMin = p.exclusiveMinimum.getOrElse(false)
    val typeCoerce = p.format match {
      case "int32" => "Int"
      case "int64" => "Long"
      case "float" => "Float"
      case "double" => "Double"
      case "byte" => "Byte"
      case _ => ""
    }
    val converter = if (typeCoerce.nonEmpty) ".to" + typeCoerce else ""
    Seq(
      ifDefined(p.maximum, s"max(${p.maximum.get}$converter, $strictMax)"),
      ifDefined(p.minimum, s"min(${p.minimum.get}$converter, $strictMin)"),
      ifDefined(p.multipleOf, s"multipleOf(${p.multipleOf.get}$converter)")
    ).flatten
  }

  private def toObjectValidations(p: ObjectValidation): Seq[String] =
    Seq(
      ifDefined(p.maxProperties, s"maxProperties(${p.maxProperties.get})"),
      ifDefined(p.minProperties, s"minProperties(${p.minProperties.get})")
    ).flatten

  def ifNot0(check:Int, result: String): Option[String] = if (check != 0) Some(result) else None

  private def ifDefined[T](validation: Option[T], result: => String) = validation map { _ => result }
}
