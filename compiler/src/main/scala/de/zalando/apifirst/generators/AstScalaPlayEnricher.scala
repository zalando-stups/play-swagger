package de.zalando.apifirst.generators

import de.zalando.apifirst._
import Domain._
import ScalaName._
import naming.Reference
import Application.StrictModel
import generators.AstScalaPlayEnricher.{Denotation, DenotationTable}
import DenotationNames._

/**
  * @author  slasch 
  * @since   21.12.2015.
  */
object AstScalaPlayEnricher {
  type Denotation = Map[String, Map[String, Any]]
  type DenotationTable = Map[Reference, Denotation]

  val empty = Map.empty[String, Map[String, Any]]
  val emptyTable = Map.empty[Reference, Denotation].withDefaultValue(empty)

  def enrich(app: StrictModel): DenotationTable = {
    val denotationTable = new ScalaModelEnricher(app).apply(emptyTable)
    denotationTable map { case (r, m) => r -> (m map { case (rr, mm) => rr -> LastListElementMarks.set(mm) }) }
  }
}

/**
  * Enriches AST with information needed to generate scala based model
  */
class ScalaModelEnricher(val app: StrictModel) extends DataGeneratorsStep with
  AliasesStep with TraitsStep with ClassesStep with TypesStep {

  private val modelTypes = app.typeDefs.toSeq

  /**
    * Applies all steps to all types to enrich given denotation table
    *
    * @param table  the DenotationTable to enrich
    * @return
    */
  private[apifirst] def apply(implicit table: DenotationTable): DenotationTable = {
    (table /: steps) { (denotation, step) => (denotation /: modelTypes) { (dd, refType) =>
      dd.updated(refType._1, dd(refType._1) ++ step(refType)(dd))
    }
    }
  }

}

trait EnrichmentStep {
  type SingleStep = ((Reference, Type)) => DenotationTable => Denotation

  def app: StrictModel

  def steps: Seq[SingleStep] = Seq.empty

}

trait TypesStep extends EnrichmentStep {

  override def steps = types +: super.steps

  private def avoidClashes(table: DenotationTable, name: String)
                          (names: Iterable[String] = table.values.map(_ (COMMON)(TYPE_NAME).toString)): String =
    if (names.exists(_ == name)) avoidClashes(table, name + "NameClash")(names) else name

  /**
    * Puts type information into the denotation table
    * @return
    */
  protected def types: SingleStep = typeDef => table => typeDef match {
    case (ref, t) =>
      val name = avoidClashes(table, typeName(t, ref))()
      val fields = t match {
        case c: Composite => dereferenceFields(c).distinct
        case c: TypeDef => c.fields
        case _ => Seq.empty[Field]
      }
      Map(COMMON -> Map(TYPE_NAME -> name, FIELDS -> fields))
  }

  private def dereferenceFields(t: Composite): Seq[Field] =
    t.descendants flatMap {
      case td: TypeDef => td.fields
      case r: TypeRef => app.findType(r.name) match {
        case td: TypeDef => td.fields
        case c: Composite => dereferenceFields(c)
        case other =>
          throw new IllegalStateException(s"Unexpected contents of Composite $r: $other")
      }
    }

  private def typeName(t: Type, r: Reference, suffix: String = "") = t match {
    case TypeRef(ref) => useType(ref, suffix, "")
    case p: PrimitiveType => useType(t.name, suffix, "")
    //       case d: TypeDef => useType(t.name, "", "")
    case _ => useType(r, suffix, "")
  }

  private def useType(ref: Reference, suffix: String, prefix: String) = {
    val fullName = ref.qualifiedName(prefix, suffix)
    fullName._2
  }
}

trait ClassesStep extends EnrichmentStep {

  override def steps = classes +: super.steps

  /**
    * Puts class related information into the denotation table
    * @return
    */
  protected def classes: SingleStep = typeDef => table => typeDef match {
    case (ref, t: TypeDef) if !ref.simple.contains("AllOf") && !ref.simple.contains("OneOf") =>
      val traitName = app.discriminators.get(ref).map(_ => Map("name" -> typeNameDenotation(table, ref)))
      Map("classes" -> (typeDefProps(ref, t)(table) + ("trait" -> traitName)))
    case (ref, t: Composite) =>
      Map("classes" -> (typeDefProps(ref, t)(table) + ("trait" -> t.root.map(r => Map("name" -> r.className)))))
    case _ => AstScalaPlayEnricher.empty
  }

  protected def typeDefProps(k: Reference, t: Type)(table: DenotationTable): Map[String, Any] = {
    Map(
      "name" -> typeNameDenotation(table, k),
      //      "creator_method" -> useType(k, "", "create"),
      "fields" -> typeFields(table, k).map { f =>
        Map(
          "name" -> escape(f.name.simple),
          //          "generator" -> generatorNameForType(f.tpe),
          "type_name" -> typeNameDenotation(table, f.tpe.name)
        )
      }
    )
  }


}

trait TraitsStep extends EnrichmentStep {

  override def steps = traits +: super.steps

  /**
    * Puts trait related information into the denotation table
    * @return
    */
  protected def traits: SingleStep = typeDef => table => typeDef match {
    case (ref, t: TypeDef) if app.discriminators.contains(ref) =>
      Map("traits" -> typeDefProps(ref, t)(table))
    case _ => AstScalaPlayEnricher.empty
  }

  protected def typeDefProps(k: Reference, t: Type)(table: DenotationTable): Map[String, Any] // FIXME should be defined only once
}

trait AliasesStep extends EnrichmentStep {

  override def steps = aliases +: super.steps

  /**
    * Puts type related information into the denotation table
    * @return
    */
  protected val aliases: SingleStep = typeDef => table => typeDef match {
    case (ref, t: Container) =>
      Map("aliases" -> aliasProps(ref, t)(table))
    //      case (k, v: PrimitiveType) => mapForAlias(k, v)
    case _ => AstScalaPlayEnricher.empty
  }

  private def aliasProps(k: Reference, v: Container)(table: DenotationTable): Map[String, Any] = {
    Map(
      "name" -> typeNameDenotation(table, k),
      "alias" -> v.alias,
      "underlying_type" -> v.imports.headOption.map { _ => v.nestedTypes.map { t =>
        typeNameDenotation(table, t.name)
      }.mkString(", ")
      }
    )
  }
}

trait DataGeneratorsStep extends EnrichmentStep {

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

    case _ => AstScalaPlayEnricher.empty
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
    case o => s"generator name for $o"
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


object DenotationNames {
  val COMMON = "common"
  val TYPE_NAME = "type_name"
  val FIELDS = "fields"
  val GENERATOR_NAME = "generator_name"

  def typeNameDenotation(table: DenotationTable, r: Reference): String = {
    table.get(r).map(_ (COMMON)(TYPE_NAME).toString).getOrElse(r.simple)
  }

  def typeFields(table: DenotationTable, r: Reference): Seq[Field] = {
    table.get(r).map(_ (COMMON)(FIELDS).asInstanceOf[Seq[Field]]).getOrElse(Seq.empty[Field])
  }

  def prepend(prefix: String, name: String): String =
    if (name.startsWith("`")) "`" + prefix + name.tail else prefix + name

  def append(name: String, suffix: String): String =
    if (name.endsWith("`")) name.init + suffix + "`" else name + suffix
}

object ReShaper {
  def filterByType(tpe: String, table: DenotationTable): Iterable[Map[String, Any]] = table.collect {
    case (r, m) if m.contains(tpe) => m(tpe)
  }
}

object LastListElementMarks {
  def set(d: Map[String, Any]): Map[String, Any] = d map {
    case (ss, tt: Map[String, Any]) => ss -> set(tt)
    case (ss, l: List[_]) if l.isEmpty || l.head.isInstanceOf[Field] =>
      ss -> l
    case (ss, l: List[Map[String, Any]]) =>
      val newList: List[Map[String, Any]] =
        l.zipWithIndex map { case (le: Map[String, Any], i: Int) => le.updated("last", i == l.length - 1) }
      ss -> newList
    case (ss, other) =>
      ss -> other
  }
}