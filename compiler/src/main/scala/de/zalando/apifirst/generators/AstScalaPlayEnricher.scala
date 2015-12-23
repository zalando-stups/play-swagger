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
class ScalaModelEnricher(val app: StrictModel) extends TypesStep with ClassesStep with TraitsStep with AliasesStep {

  type SingleStep = ((Reference, Type)) => DenotationTable => Denotation

  private val steps: Seq[SingleStep] = Seq(types _, classes _, traits _, aliases _)
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
  def app: StrictModel
}

trait TypesStep extends EnrichmentStep {

  /**
    * Puts type information into the denotation table
    * @param typeDef  the DenotationTable to enrich
    * @return
    */
  protected def types(typeDef: (Reference, Type))(table: DenotationTable): Denotation = typeDef match {
    case (ref, t) => Map(COMMON -> Map(TYPE_NAME -> typeName(t, ref)))
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
  /**
    * Puts class related information into the denotation table
    * @param typeDef  the DenotationTable to enrich
    * @return
    */
  protected def classes(typeDef: (Reference, Type))(table: DenotationTable): Denotation = typeDef match {
    case (ref, t: TypeDef) if !ref.simple.contains("AllOf") && !ref.simple.contains("OneOf") =>
      val traitName = app.discriminators.get(ref).map(_ => Map("name" -> typeNameDenotation(table, ref)))
      Map("classes" -> (typeDefProps(ref, t, t.fields)(table) + ("trait" -> traitName)))
    case (ref, t: Composite) =>
      val fields = dereferenceFields(t).distinct
      Map("classes" -> (typeDefProps(ref, t, fields)(table) + ("trait" -> t.root.map(r => Map("name" -> r.className)))))
    case _ => AstScalaPlayEnricher.empty
  }

  protected def typeDefProps(k: Reference, t: Type, fields: Seq[Field])(table: DenotationTable): Map[String, Any] = {
    Map(
      "name" -> typeNameDenotation(table, k),
      //      "creator_method" -> useType(k, "", "create"),
      "fields" -> fields.map { f =>
        Map(
          "name" -> escape(f.name.simple),
          //          "generator" -> generatorNameForType(f.tpe),
          "type_name" -> typeNameDenotation(table, f.tpe.name)
        )
      }
    )
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
}

trait TraitsStep extends EnrichmentStep {

  /**
    * Puts trait related information into the denotation table
    * @param typeDef  the DenotationTable to enrich
    * @return
    */
  protected def traits(typeDef: (Reference, Type))(table: DenotationTable): Denotation = typeDef match {
    case (ref, t: TypeDef) if app.discriminators.contains(ref) =>
      Map("traits" -> typeDefProps(ref, t, t.fields)(table))
    case _ => AstScalaPlayEnricher.empty
  }

  protected def typeDefProps(k: Reference, t: Type, fields: Seq[Field])(table: DenotationTable): Map[String, Any] // FIXME should be defined only once
}

trait AliasesStep extends EnrichmentStep {
  /**
    * Puts type related information into the denotation table
    * @param typeDef  the DenotationTable to enrich
    * @return
    */
  protected def aliases(typeDef: (Reference, Type))(table: DenotationTable): Denotation = typeDef match {
    case (ref, t: Container) =>
      Map("aliases" -> aliasProps(ref, t)(table))
    //      case (k, v: PrimitiveType) => mapForAlias(k, v)
    case _ => AstScalaPlayEnricher.empty
  }

  private def aliasProps(k: Reference, v: Container)(table: DenotationTable): Map[String, Any] = {
    Map(
      "name" -> typeNameDenotation(table, k),
      //      "creator_method" -> useType(k, "", "create"),
      "alias" -> v.alias,
      //      "generator" -> typeName(v, k),
      //      "generator_name" -> generatorNameForType(v),
      "underlying_type" -> v.imports.headOption.map { _ => v.nestedTypes.map { t =>
        typeNameDenotation(table, t.name)
      }.mkString(", ")
      }
    )
  }
}

object DenotationNames {
  val COMMON = "common"
  val TYPE_NAME = "type_name"

  def typeNameDenotation(table: DenotationTable, r: Reference): String = {
    table.get(r).map(_ (COMMON)(TYPE_NAME).toString).getOrElse(r.simple)
  }
}

object ReShaper {
  def filterByType(tpe: String, table: DenotationTable): Iterable[Map[String, Any]] = table.collect {
    case (r, m) if m.contains(tpe) => m(tpe)
  }
}

object LastListElementMarks {
  def set(d: Map[String, Any]): Map[String, Any] = d map {
    case (ss, tt: Map[String, Any]) => ss -> set(tt)
    case (ss, l: List[Map[String, Any]]) =>
      val newList: List[Map[String, Any]] =
        l.zipWithIndex map { case (le: Map[String, Any], i: Int) => le.updated("last", i == l.length - 1) }
      ss -> newList
    case (ss, other) =>
      ss -> other
  }
}