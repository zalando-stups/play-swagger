package de.zalando.apifirst.generators

import de.zalando.apifirst.Application._
import de.zalando.apifirst.Domain._
import de.zalando.apifirst.ScalaName._
import de.zalando.apifirst.naming.Reference

trait PlayValidatorsGenerator extends PlayValidatorsConstraintsGenerator {

  val validatorsTemplateName = "play_validation.mustache"

  def strictModel: StrictModel

  /**
    * Creates validations for TypeDef
    *
    * At this point all fields validations should already be generated and only
    * needed to be sequenced together
    *
    * @param typeDefs
    * @return
    */
  def complexTypeValidations(typeDefs: TypeLookupTable) = Map.empty // typeDefValidations(typeDefs).flatten.toMap

  /**
    * Creates validations for call parameters taking into the account only calls with non-empty argument list
    *
    * @param calls
    * @return
    */
  def allCallsValidations(calls: Seq[ApiCall]): Seq[Map[String, Object]] =
    calls filterNot {
      _.handler.parameters.isEmpty
    } map {
      callValidations
    }

  /**
    * Creates validations for ApiCall parameters.
    *
    * At this point all api call parameters validations should already be generated and only
    * needed to be sequenced together
    *
    * @param call
    * @return
    */
  def callValidations(call: ApiCall): Map[String, Object] = {
    if (call.handler.parameters.isEmpty) Map.empty
    else {
      call.handler.parameters.foreach {
        p => assert(p.isInstanceOf[ParameterRef], "All call parameters expected to be typeRefs, but got: " + p)
      }
      Map(
        "constraint_name" -> useType(call.asReference, constraintsSuffix, ""),
        "validation_name" -> useType(call.asReference, validatorsSuffix, ""),
        "class_name" -> call.asReference.typeAlias()
      )
    }
  }

  /**
    * Wrapper method for real constraints generator
    * Needed to generate different constraints for different parameter types
    *
    * TODO: Get rid of it
    *
    * @param parameters
    * @return
    */
  def parametersValidations(parameters: Seq[ParameterRef]): Seq[Map[String, Any]] = {
    val neededParams = strictModel.params.filter { case (k, v) => parameters.contains(k) }
    val paramValidations = parameterConstraints(neededParams)
    paramValidations.flatMap(_._2).toSeq
  }


  /**
    * This methods generates validations to wire togehter field validations of the TypeDef

    * @return
    */
/*  private def typeDefValidations(types: Map[Reference, Type]): Validations =
    types.collect {
      case (r, t: TypeDef) => if !r.simple.contains("AllOf") && !r.simple.contains("OneOf")
        Map(
          "validation_name" -> useType(r, validatorsSuffix, ""),
          "class_name" -> typeName(t, r),
          "fields" -> t.fields.zipWithIndex.map { case (f, i) =>
            Map(
              "field_name" -> escape(f.name.simple),
              "validation_name" -> useType(fieldValidation(f), validatorsSuffix, ""),
              "last" -> (i == t.fields.size - 1)
            )
          }
        )
    }.toSeq

  def fieldValidation(f: Field) = if (f.tpe.isInstanceOf[PrimitiveType]) f.name else f.tpe.name*/
}

trait PlayValidatorsConstraintsGenerator extends ImportSupport {

  def strictModel: StrictModel

  val validatorsSuffix = "Validator"
  val constraintsSuffix = "Constraints"

  type Validations = Seq[(String, Map[String, Any])]

  /**
    * Creates validation definitions for complex types
    * @return
  def typeConstraints(typeDefs: TypeLookupTable): Validations = {
    val result = typeDefs.toSeq map constraints0
    result
  }.flatten
*/

  /**
    * Creates validation definitions for parameters
    *
    * @param params
    * @return
    */
  def parameterConstraints(params: ParameterLookupTable): Map[String, Seq[Map[String, Any]]] = {
    val constraintsData = params.toSeq.map { case (ref, param) => (ref.name, param.typeName) }
    val rawResult = constraintsData map constraints0
    val result = rawResult flatMap { resultList: Validations =>
      resultList.groupBy(_._1) map { case (tpe, seqMap) =>
        tpe -> seqMap.map(_._2)
      }
    }
    result.toMap
  }

  /**
    * Depending upon, what we want to validate, we can have one of following situations:
    * - Primitive types only need to be validated themself
    * - Options does not need any constraints, only primitive contents, recursive
    * - Arrays need to validate {maxItems, minItems, uniqueItems, enum} and elements recursive
    * - CatchAll need to validate {MaxProperties} and {MinProperties}
    * - TypeDefs does not need to constraint anything and fields recursive
    */
  def constraints0(types: (Reference, Type)): Validations = Seq.empty /* {
    types match {
      case (r: Reference, t: PrimitiveType) =>
        Seq("primitive_validations" -> typeConstraints(r, t))
      case (r: Reference, t: Arr) =>
        val delegate = delegateName(r, t, "Arr")
        val result = ("array_validations" -> optValidations(r, t, delegate)) +: constraints0(delegate -> t.tpe)
        result
      case (r: Reference, t: CatchAll) =>
        val result = ("catch_validations" -> catchAllConstraints(r, t)) +: constraints0((r / "CatchAll") -> t.tpe)
        result
      case (r: Reference, t: Opt) =>
        val delegate = delegateName(r, t, "Opt")
        val result = ("opt_validations" -> optValidations(r, t, delegate)) +: constraints0(delegate -> t.tpe)
        result
      case (r, t: TypeDef) =>
        val result = ("typedef_validations" -> typeDefValidations(r, t)) +: t.fields.flatMap { f => constraints0(f.name -> f.tpe) }
        result
      case (r, TypeRef(ref)) =>
        val result = constraints0(ref -> strictModel.findType(ref))
        result
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

  private def typeDefValidations(r: Reference, t: TypeDef) =
    Map(
      "validation_name" -> useType(r, validatorsSuffix, ""),
      "type_name" -> typeName(t, r),
      "fields" -> t.fields.zipWithIndex.map { case (f, i) =>
        Map(
          "field_name" -> escape(f.name.simple),
          "validation_name" -> useType(fieldName(f), validatorsSuffix, ""),
          "last" -> (i == t.fields.size - 1)
        )
      }
    )

  private def optValidations(r: Reference, t: Container, delegateName: Reference) =
    Map(
      "restrictions" -> t.meta.constraints.filterNot(_.isEmpty).zipWithIndex.map { case (c, i) =>
        Map("name" -> c, "last" -> (i == t.meta.constraints.length - 1))
      },
      "constraint_name" -> useType(r, constraintsSuffix, ""), // restrictions and constraint_name are needed for Arr, not needed for Opt
      "delegate_validation_name" -> useType(delegateName, validatorsSuffix, ""),
      "validation_name" -> useType(r, validatorsSuffix, ""),
      "type_name" -> typeName(t, r)
    )

  private def typeConstraints(r: Reference, t: Type) =
    Map(
      "restrictions" -> t.meta.constraints.filterNot(_.isEmpty).zipWithIndex.map { case (c, i) =>
        Map("name" -> c, "last" -> (i == t.meta.constraints.length - 1))
      },
      "constraint_name" -> useType(r, constraintsSuffix, ""),
      "validation_name" -> useType(r, validatorsSuffix, ""),
      "type_name" -> typeName(t, r)
    )
  private def catchAllConstraints(r: Reference, t: Type) =
    Map(
      "restrictions" -> t.meta.constraints.filterNot(_.isEmpty).zipWithIndex.map { case (c, i) =>
        Map("name" -> c, "last" -> (i == t.meta.constraints.length - 1))
      },
      "constraint_name" -> useType(r, constraintsSuffix, ""),
      "validation_name" -> useType(r, validatorsSuffix, ""),
      "type_name" -> typeName(t, r)
    )

  def typeName(t: Type, r: Reference, suffix: String = "") = t match {
      case TypeRef(ref) => useType(ref, suffix, "")
      case p: PrimitiveType => useType(t.name, suffix, "")
//       case d: TypeDef => useType(t.name, "", "")
      case _ => useType(r, suffix, "")
    }

  def fieldName(f: Field) = if (f.tpe.isInstanceOf[PrimitiveType]) f.name else f.tpe.name
*/}
