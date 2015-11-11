package de.zalando.apifirst

import java.awt.Composite

import de.zalando.apifirst.Application.{TypeLookupTable, StrictModel}
import de.zalando.apifirst.Domain._
import de.zalando.apifirst.new_naming.Reference

import scala.annotation.tailrec

/**
  * Flattens types by recursively replacing nested type definitions with references
  *
  * This implementation relies on {@ParameterDereferencer} to extract all non-primitive types from parameter definitions
  *
  * Because of that, the {@ParameterDereferencer} MUST be applyed before using {@TypeFlattener},
  * otherwise results will be inconsistent parameter definitions
  *
  * @author  slasch 
  * @since   11.11.2015.
  */
object TypeFlattener extends TypeAnalyzer {

  def flatten(app: StrictModel): StrictModel = {
    val flatTypeDefs = flatten0(app.typeDefs)
    app.copy(typeDefs = flatTypeDefs)
  }

  private def flatten0(typeDefs: TypeLookupTable): TypeLookupTable = {
    val flatTypeDefs = typeDefs flatMap { case (k, v) => extractComplexType(k, v) }
    if (flatTypeDefs == typeDefs)
      flatTypeDefs
    else
      flatten0(flatTypeDefs)
  }

  private def extractComplexType(ref: Reference, typeDef: Type): Seq[(Reference, Type)] = typeDef match {
    case t: TypeDef if complexFields(t).nonEmpty =>
      val (changedFields, extractedTypes) = t.fields.filter(complexField).map(createTypeFromField).unzip
      val newFields = t.fields.filterNot(complexField) ++ changedFields
      val newTypeDef = t.copy(fields = newFields)
      (ref -> newTypeDef) +: extractedTypes
    case c: Container if isComplexType(c.tpe) =>
      val newRef = ref / "container"
      Seq(ref -> c.withType(TypeReference(newRef)), newRef -> c.tpe)
    case c: Composite => ???
    case _ => Seq(ref -> typeDef)
  }

  private def complexFields(typeDef: TypeDef): Seq[Field] = typeDef.fields filter complexField

  private def complexField: (Field) => Boolean = f => isComplexType(f.tpe)

  private def createTypeFromField: (Field) => (Field, (Reference,Type)) = field => {
    val newReference = TypeReference(field.name)
    val extractedType = field.tpe
    (field.copy(tpe = newReference), newReference.name -> extractedType)
  }

}

object ParameterDereferencer extends TypeAnalyzer {
  /**
    * Converts inline type definitions into type references
    * @param app
    * @return
    */
  @tailrec
  def flatten(app: StrictModel): StrictModel = {
    var result = app
    app.params foreach  { case (name, definition) =>
      definition.typeName match {
        case tpe if isComplexType(tpe) =>
          val tps = app.typeDefs + (tpe.name -> tpe)
          val newReference = TypeReference(tpe.name)
          val newParams = app.params.updated(name, definition.copy(typeName = newReference))
          result = result.copy(typeDefs = tps, params = newParams)
        case _ =>
      }
    }
    if (result == app) result else flatten(result)
  }

}

trait TypeAnalyzer {
  def isComplexType(t: Type): Boolean = t match {
    case tpe @ (_: TypeDef | _: Composite | _: Container) => true
    case _ => false
  }
}