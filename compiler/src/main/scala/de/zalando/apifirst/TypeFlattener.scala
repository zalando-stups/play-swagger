package de.zalando.apifirst

import de.zalando.apifirst.Application.{TypeLookupTable, StrictModel}
import de.zalando.apifirst.Domain._
import de.zalando.apifirst.new_naming.Reference

import scala.annotation.tailrec

/**
  * Flattens types by recursively replacing nested type definitions with references
  *
  * This implementation relies on {@ParameterDereferencer} to extract all non-primitive types from parameter definitions
  *
  * Because of that, the {@ParameterDereferencer} MUST be applied before using {@TypeFlattener},
  * otherwise results will be inconsistent parameter definitions
  *
  * @author  slasch 
  * @since   11.11.2015.
  */
object TypeFlattener extends TypeAnalyzer {

  def apply(app: StrictModel): StrictModel = {
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
      val newRef = ref / c.getClass.getSimpleName
      Seq(ref -> c.withType(TypeReference(newRef)), newRef -> c.tpe)
    case c: Composite =>
      val (changedTypes, extractedTypes) = c.descendants.filter(isComplexType).
        zipWithIndex.map(flattenType(c.getClass.getSimpleName, ref)).unzip
      val newTypes = c.descendants.filterNot(isComplexType) ++ changedTypes
      val newTypeDef = c.withTypes(newTypes)
      (ref -> newTypeDef) +: extractedTypes
    case _ => Seq(ref -> typeDef)
  }

  private def complexFields(typeDef: TypeDef): Seq[Field] = typeDef.fields filter complexField

  private def complexField: (Field) => Boolean = f => isComplexType(f.tpe)

  private def createTypeFromField: (Field) => (Field, (Reference, Type)) = field => {
    val newReference = TypeReference(field.name)
    val extractedType = field.tpe
    (field.copy(tpe = newReference), newReference.name -> extractedType)
  }

  private def flattenType: (String, Reference) => ((Type, Int)) => (Type, (Reference, Type)) = (name, ref) => pair => {
    val (typeDef, index) = pair
    val newReference = TypeReference(ref / (name + index))
    val extractedType = typeDef // FIXME the name should be changed here as well
    (newReference, newReference.name -> extractedType)
  }


}

object TypeDeduplicator extends TypeAnalyzer {
  /**
    * Removes redundant type definitions changing pointing references
    *
    * @param app
    * @return
    */
  @tailrec
  def apply(app: StrictModel): StrictModel = {
    var result = app
    app.typeDefs foreach { case (name, typeDef) =>
      val duplicates = result.typeDefs.filter { _._2 == typeDef }
      val dupDefs = duplicates.toSeq.map(_._1).sortBy(_.toString.length)
      val bestDef = if (dupDefs.size > 1) dupDefs.headOption else None
      bestDef foreach { bDef =>
        val duplicateRefs = dupDefs.tail
        val newDefs = result.typeDefs flatMap { case (k, v) =>
          if (duplicateRefs.contains(k)) None else Some(k -> v)
        }
        val defsWithCorrectRefs = newDefs map { case (ref, tpe) => tpe match {
          case c: Container if c.tpe.isInstanceOf[TypeReference] =>
            if (duplicateRefs.contains(c.tpe.asInstanceOf[TypeReference].name))
              ref -> c.withType(TypeReference(bDef))
            else
              ref -> c
          case c: Composite =>
            val newDescendants = c.descendants map { d =>
              if (duplicateRefs.contains(d.asInstanceOf[TypeReference].name))
                TypeReference(bDef)
              else
                d
            }
            ref -> c.withTypes(newDescendants)

          case t: Domain.TypeDef =>
            val newFields = t.fields.map { f =>
              f.tpe match {
                case reference: TypeReference if duplicateRefs.contains(reference.name) => f.copy(tpe = TypeReference(bDef))
                case _ => f
              }
            }
            ref -> t.copy(fields = newFields)
          case _ => ref -> tpe
        }
        }
        val newParams = result.params map { case (k, v) =>
          if (duplicateRefs.contains(v.typeName.name))
            k -> v.copy(typeName = TypeReference(bDef))
          else
            k -> v
        }
        result = result.copy(typeDefs = defsWithCorrectRefs, params = newParams)
      }
    }
    if (result == app) result else apply(result)
  }
}

object ParameterDereferencer extends TypeAnalyzer {
  /**
    * Converts inline type definitions into type references
    * @param app
    * @return
    */
  @tailrec
  def apply(app: StrictModel): StrictModel = {
    var result = app
    result.params foreach { case (name, definition) =>
      definition.typeName match {
        case tpe if isComplexType(tpe) =>
          val tps = app.typeDefs + (name.name -> tpe)
          val newReference = TypeReference(name.name)
          val newParams = app.params.updated(name, definition.copy(typeName = newReference))
          result = result.copy(typeDefs = tps, params = newParams)
        case _ =>
      }
    }
    if (result == app) result else apply(result)
  }
}

trait TypeAnalyzer {
  def isComplexType(t: Type): Boolean = t match {
    case tpe@(_: TypeDef | _: Composite | _: Container) => true
    case _ => false
  }
}