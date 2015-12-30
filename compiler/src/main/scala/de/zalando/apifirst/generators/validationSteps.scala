package de.zalando.apifirst.generators

import de.zalando.apifirst.Application.{Parameter, StrictModel, ParameterRef, ApiCall}
import de.zalando.apifirst.Domain._
import de.zalando.apifirst.ScalaName._
import de.zalando.apifirst.generators.DenotationNames._
import de.zalando.apifirst.naming.Reference

/**
  * @author  slasch 
  * @since   28.12.2015.
  */

trait CallValidatorsStep extends EnrichmentStep[ApiCall] with ValidatorsCommon {

  override def steps = callValidators +: super.steps

  /**
    * Puts validation related information into the denotation table
    * @return
    */
  protected val callValidators: SingleStep = call => table =>
    Map("validators" -> callValidatorsProps(call._1, call._2)(table))

  private def callValidatorsProps(ref: Reference, call: ApiCall)(table: DenotationTable) = call match {
    case c if c.handler.parameters.nonEmpty =>
      call.handler.parameters.foreach {
        p => assert(p.isInstanceOf[ParameterRef], "All call parameters expected to be typeRefs, but got: " + p)
      }
      Map("call_validations" -> Seq(callValidations(ref, call)(table)))
    case _ => empty
  }

  /**
    * Creates validations for ApiCall parameters.
    *
    * At this point all api call parameters validations should already be generated and only
    * needed to be sequenced together
    *
    * @return
    */
  private def callValidations(r: Reference, call: ApiCall)(table: DenotationTable): Map[String, Object] =
    Map(
      "validation_name" -> validator(r, table),
      "fields" -> call.handler.parameters.map { p =>
        Map(
          "field_name" -> escape(p.name.simple),
          "type_name" -> typeNameDenotation(table, p.name),
          "validation_name" -> validator(p.name, table)
        )
      }
    )

}

trait ParametersValidatorsStep extends EnrichmentStep[Parameter] with ValidatorsCommon {

  override def steps = callValidators +: super.steps

  /**
    * Puts validation related information into the denotation table
    * @return
    */
  protected val callValidators: SingleStep = parameter => table =>
    parameterValidatorsProps(parameter._1, parameter._2)(table)

  private def parameterValidatorsProps(ref: Reference, parameter: Parameter)(table: DenotationTable): Denotation = {
    val result = parametersValidations(table)(ref, parameter)
    val byType = result.groupBy(_._1).map { case (k,v) => k -> v.map(_._2) }
    Map("validators" -> byType)
  }

  /**
    * Wrapper method for real constraints generator
    * Needed to generate different constraints for different parameter types
    *
    * @return
    */
  private def parametersValidations(table: DenotationTable)(ref: Reference, param: Parameter) =
    constraints0(ref, param.typeName)(table)

  /**
    * Depending upon, what we want to validate, we can have one of following situations:
    * - Primitive types only need to be validated themself
    * - Options does not need any constraints, only primitive contents, recursive
    * - Arrays need to validate {maxItems, minItems, uniqueItems, enum} and elements recursive
    * - CatchAll need to validate {MaxProperties} and {MinProperties}
    * - TypeDefs does not need to constraint anything and fields recursive
    */
  def constraints0(types: (Reference, Type))(implicit table: DenotationTable): Validations = {
    types match {
      case (r: Reference, t: PrimitiveType) =>
        Seq("primitive_validations" -> typeConstraints(r, t))
      case (r: Reference, t: Arr) =>
        val delegate = delegateName(r, t, "Arr")
        val result = ("array_validations" -> optValidations(r, t, delegate)) +: constraints0(delegate -> t.tpe)
        result
      case (r: Reference, t: CatchAll) =>
        val result = ("catch_validations" -> typeConstraints(r, t)) +: constraints0((r / "CatchAll") -> t.tpe)
        result
      case (r: Reference, t: Opt) =>
        val delegate = delegateName(r, t, "Opt")
        val result = ("opt_validations" -> optValidations(r, t, delegate)) +: constraints0(delegate -> t.tpe)
        result
      case (r, t: TypeDef) =>
        val result = ("typedef_validations" -> typeDefValidations(r, t)) +: t.fields.flatMap { f => constraints0(f.name -> f.tpe) }
        result
      case (r, TypeRef(ref)) if ! app.findType(ref).isInstanceOf[TypeRef] =>
        val result = constraints0(ref -> app.findType(ref))
        result
      case (r, TypeRef(ref)) =>
        Nil
      case (r, t: Composite) =>
        Nil // TODO FIXME
    }
  }

  private def delegateName(r: Reference, t: Container, suffix: String): Reference = {
    t.tpe match {
      case p: PrimitiveType => r / suffix
      case _ => t.tpe.name
    }
  }

  private def typeDefValidations(r: Reference, t: TypeDef)(implicit table: DenotationTable) =
    Map(
      "validation_name" -> validator(r, table),
      "type_name" -> typeNameDenotation(table, r),
      "fields" -> t.fields.map { f =>
        Map(
          "field_name" -> escape(f.name.simple),
          "validation_name" -> validator(fieldName(f), table)
        )
      }
    )

  private def optValidations(r: Reference, t: Container, delegateName: Reference)(implicit table: DenotationTable) =
    Map(
      "restrictions" -> t.meta.constraints.filterNot(_.isEmpty).zipWithIndex.map { case (c, i) =>
        Map("name" -> c, "last" -> (i == t.meta.constraints.length - 1))
      },
      "constraint_name" -> constraint(r, table), // restrictions and constraint_name are needed for Arr, not needed for Opt
      "delegate_validation_name" -> validator(delegateName, table),
      "validation_name" -> validator(r, table),
      "type_name" -> typeNameDenotation(table, r)
    )

  private def typeConstraints(r: Reference, t: Type)(implicit table: DenotationTable) =
    Map(
      "restrictions" -> t.meta.constraints.filterNot(_.isEmpty).map { c =>
        Map("name" -> c)
      },
      "constraint_name" -> constraint(r, table),
      "validation_name" -> validator(r, table),
      "type_name" -> (t match {             // TODO this one is bad
        case p: PrimitiveType => p.name.typeAlias()
        case _ => typeNameDenotation(table, r)
      })
    )

  def fieldName(f: Field) = if (f.tpe.isInstanceOf[PrimitiveType]) f.name else f.tpe.name

}

/**
  * @author  slasch
  * @since   28.12.2015.
  */
/*trait TypeValidatorsStep extends EnrichmentStep[Type] with ValidatorsCommon {

  override def steps = typeValidators +: super.steps

  protected val typeValidators: SingleStep = typeDef => table =>
    Map("validators" -> typeValidatorProps(typeDef._1, typeDef._2)(table))

  private def typeValidatorProps(ref: Reference, v: Type)(table: DenotationTable): Map[String, Any] =
    constraints0(ref, v)(table).toMap

}*/

trait ValidatorsCommon {

  def app: StrictModel

  type Validations = Seq[(String, Map[String, Any])]

  val validatorsSuffix = "Validator"
  val constraintsSuffix = "Constraints"

  def validator(ref: Reference, table: DenotationTable): String =
    append(memberNameDenotation(table, ref), validatorsSuffix)

  def constraint(ref: Reference, table: DenotationTable): String =
    append(memberNameDenotation(table, ref), constraintsSuffix)

}