package de.zalando.apifirst.generators

import de.zalando.apifirst.Application.{ApiCall, Parameter, StrictModel}
import de.zalando.apifirst.Domain._
import de.zalando.apifirst.ScalaName._
import de.zalando.apifirst.generators.DenotationNames._
import de.zalando.apifirst.naming.Reference

/**
  * @author  slasch 
  * @since   21.12.2015.
  */
object AstScalaPlayEnricher {

  val emptyTable = Map.empty[Reference, Denotation].withDefaultValue(empty)

  def apply(app: StrictModel): DenotationTable = {
    val transformations = Seq(
      new ScalaPlayTypeEnricher(app),
      new ScalaPlayParameterEnricher(app),
      new ScalaPlayCallEnricher(app)
    )
    val denotationTable = (emptyTable /: transformations) { (table, transformation) =>
      transformation enrich table
    }
    denotationTable
  }
}

/**
  * Enriches AST with information related to Types
  */
trait Transformation[T] extends EnrichmentStep[T] {

  def data: Seq[(Reference, T)]
  /**
    * Applies all steps to all types to enrich given denotation table
    *
    * @param table  the DenotationTable to enrich
    * @return
    */
  private[apifirst] def apply(table: DenotationTable): DenotationTable = {
    (table /: steps) { (denotation, step) => (denotation /: data) { (dd, refType) =>
      val stepResult = step(refType)(dd)
      if (stepResult.isEmpty) dd
      else dd.updated(refType._1, dd(refType._1) ++ stepResult)
    }}
  }

  private[apifirst] def enrich(table: DenotationTable): DenotationTable = apply(table)

}

/**
  * Enriches AST with information related to Types
  */
class ScalaPlayTypeEnricher(val app: StrictModel) extends Transformation[Type] with
  DataGeneratorsStep with
  AliasesStep with TraitsStep with ClassesStep with CommonDataStep {

  override val data = app.typeDefs.toSeq

}

/**
  * Enriches AST with information related to ApiCalls
  */
class ScalaPlayCallEnricher(val app: StrictModel) extends Transformation[ApiCall]
  with CallTestsStep with CallValidatorsStep with CommonCallDataStep {

  override def data = app.calls map { c => c.asReference -> c }

}

/**
  * Enriches AST with information related to Parameters
  */
class ScalaPlayParameterEnricher(val app: StrictModel) extends Transformation[Parameter]
  with ParametersValidatorsStep with CommonParamDataStep {

  override def data = app.params.toSeq.map { case (r, p) => r.name -> p }

}

trait EnrichmentStep[InputType] {

  type SingleStep = ((Reference, InputType)) => DenotationTable => Denotation

  def app: StrictModel

  def steps: Seq[SingleStep] = Seq.empty

}

object DenotationNames {

  type Denotation = Map[String, Map[String, Any]]
  type DenotationTable = Map[Reference, Denotation]

  val empty = Map.empty[String, Map[String, Any]]

  val COMMON = "common"
  val TYPE_NAME = "type_name"
  val MEMBER_NAME = "member_name"
  val FIELDS = "fields"
  val GENERATOR_NAME = "generator_name"

  def typeNameDenotation(table: DenotationTable, r: Reference): String = {
    table.get(r).map(_ (COMMON)(TYPE_NAME).toString).getOrElse { r.simple }
  }

  def memberNameDenotation(table: DenotationTable, r: Reference): String = {
    table.get(r).map(_ (COMMON)(MEMBER_NAME).toString).getOrElse {
      r.typeAlias() // FIXME
    }
  }

  def typeFields(table: DenotationTable, r: Reference): Seq[Field] = {
    table.get(r).map(_ (COMMON)(FIELDS).asInstanceOf[Seq[Field]]).getOrElse(Seq.empty[Field])
  }

  private val generatorsSuffix = "Generator"

  def generator(r: Reference, table: DenotationTable): String =
    append(typeNameDenotation(table, r), generatorsSuffix)

  private val validatorsSuffix = "Validator"

  def validator(ref: Reference, table: DenotationTable): String =
    append(memberNameDenotation(table, ref), validatorsSuffix)


  def prepend(prefix: String, name: String): String =
    if (name.startsWith("`")) "`" + prefix + name.tail else prefix + name

  def append(name: String, suffix: String): String =
    if (name.endsWith("`")) name.init + suffix + "`" else name + suffix
}

object ReShaper {
  def groupByType(toGroup: Seq[Map[String, Any]]): Seq[(String, Seq[Any])] = {
    val groupped = toGroup.foldLeft (Map.empty[String, Seq[Any]].withDefaultValue(Nil)) { (input, m) =>
      val result = m map { case (key, value) =>
          key -> input.get(key).map { v => value match {
            case vv: Seq[Any] => (v ++ vv).distinct
            case vv: Any => Nil
          }}.getOrElse { value match {
            case v: Seq[Any] => v.distinct
            case _ => Seq(value)
          }}
      }
      input ++ result
    }
    groupped.toSeq
  }

  def filterByType(tpe: String, table: DenotationTable): Iterable[Map[String, Any]] = table.collect {
    case (r, m) if m.contains(tpe) => m(tpe)
  }
}

object LastListElementMarks {
  def set(d: Map[String, Any]): Map[String, Any] = d map {
    case (ss, tt: Map[String, Any]) =>
      ss -> set(tt)
    case (ss, l: List[_]) if l.isEmpty || !l.head.isInstanceOf[Map[_,_]] =>
      ss -> l
    case (ss, l: List[Map[String, Any]]) =>
      val newList: List[Map[String, Any]] =
        l.zipWithIndex map { case (le, i: Int) => set(le.updated("last", i == l.length - 1)) }
      ss -> newList
    case (ss, other) =>
      ss -> other
  }
}