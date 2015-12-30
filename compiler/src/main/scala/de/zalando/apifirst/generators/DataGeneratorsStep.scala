package de.zalando.apifirst.generators

import de.zalando.apifirst.Domain._
import de.zalando.apifirst.ScalaName._
import de.zalando.apifirst.naming.Reference
import DenotationNames._
/**
  * @author  slasch 
  * @since   28.12.2015.
  */

trait DataGeneratorsStep extends EnrichmentStep[Type] {

  override def steps = dataGenerators +: super.steps

  val generatorsSuffix = "Generator"

  /**
    * Puts data generators related information into the denotation table
    * @return
    */
  protected val dataGenerators: SingleStep = typeDef => table =>
    generatorProps(typeDef._1, typeDef._2)(table)

  private def generatorProps(ref: Reference, v: Type)(table: DenotationTable): Denotation = v match {
    case t: Container =>
      Map("test_data_aliases" -> containerGenerator(ref, t)(table))
    case t: PrimitiveType =>
      Map("test_data_aliases" -> containerGenerator(ref, t)(table))
    case t: TypeDef if ! ref.simple.startsWith("AllOf") =>
      Map("test_data_classes" -> classGenerator(ref, t)(table))
    case t: Composite  =>
      Map("test_data_classes" -> compositeGenerator(ref, t)(table))

    case _ => empty
  }

  private def containerGenerator(k: Reference, v: Type)(table: DenotationTable): Map[String, Any] = {
    Map(
      GENERATOR_NAME -> generatorNameForType(v, table),
      "creator_method" -> append(prepend("create", typeNameDenotation(table, k)), generatorsSuffix),
      "generator" -> append(typeNameDenotation(table, k), generatorsSuffix)
    )
  }

  private def classGenerator(k: Reference, v: TypeDef)(table: DenotationTable): Map[String, Any] = {
    Map(
      GENERATOR_NAME -> generatorNameForType(v, table),
      "creator_method" -> append(prepend("create", typeNameDenotation(table, k)), generatorsSuffix),
      "generator" -> append(typeNameDenotation(table, k), generatorsSuffix),
      "class_name" -> typeNameDenotation(table, k),
      "fields" -> typeFields(table, k).map { f =>
        Map(
          "name" -> escape(f.name.simple),
          "generator" -> generatorNameForType(f.tpe, table)
        )
      }
    )
  }

  private def compositeGenerator(k: Reference, v: Composite)(table: DenotationTable): Map[String, Any] = {
    Map(
      GENERATOR_NAME -> generatorNameForType(v, table),
      "creator_method" -> append(prepend("create", typeNameDenotation(table, k)), generatorsSuffix),
      "generator" -> append(typeNameDenotation(table, k), generatorsSuffix),
      "class_name" -> typeNameDenotation(table, k),
      "fields" -> typeFields(table, k).map { f =>
        Map(
          "name" -> escape(f.name.simple),
          "generator" -> generatorNameForType(f.tpe, table)
        )
      }
    )
  }
  private val generatorNameForType: (Type, DenotationTable) => String = {
    case (s: PrimitiveType, table) => primitiveType(s, table)
    case (c: Container, table) => containerType(c, table)
    case (TypeRef(r), table) => append(typeNameDenotation(table, r), generatorsSuffix)
    case (d: TypeDef, table) => append(typeNameDenotation(table, d.name), generatorsSuffix)
    case (c: Composite, table) => append(typeNameDenotation(table, c.name), generatorsSuffix)
    case o =>
      throw new Exception("Unexpected reuqest for generator name for: " + o.toString)
  }

  private def containerType(c: Container, t: DenotationTable): String = {
    val innerGenerator = generatorNameForType(c.tpe, t)
    val className = typeNameDenotation(t, c.tpe.name)
    c match {
      case Opt(tpe, _) => s"Gen.option($innerGenerator)"
      case Arr(tpe, _, _) => s"Gen.containerOf[List,$className]($innerGenerator)"
      case c@CatchAll(tpe, _) => s"_genMap[String,$className](arbitrary[String], $innerGenerator)"
    }
  }

  private def primitiveType(tpe: Type, t: DenotationTable) =
    s"arbitrary[${typeNameDenotation(t, tpe.name)}]"

}