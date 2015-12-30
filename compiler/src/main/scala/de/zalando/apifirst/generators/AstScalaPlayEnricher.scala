package de.zalando.apifirst.generators

import de.zalando.apifirst.Application.{ApiCall, Parameter, StrictModel}
import de.zalando.apifirst.Domain._
import de.zalando.apifirst.ScalaName._
import de.zalando.apifirst.generators.DenotationNames._
import de.zalando.apifirst.naming.Reference

import scala.annotation.tailrec

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
  /*TypeValidatorsStep with*/ DataGeneratorsStep with
  AliasesStep with TraitsStep with ClassesStep with CommonDataStep {

  override val data = app.typeDefs.toSeq

}

/**
  * Enriches AST with information related to ApiCalls
  */
class ScalaPlayCallEnricher(val app: StrictModel) extends Transformation[ApiCall]
  with CallValidatorsStep with CommonCallDataStep {

  override def data = app.calls map { c => c.asReference -> c }

}

/**
  * Enriches AST with information related to Parameters
  */
class ScalaPlayParameterEnricher(val app: StrictModel) extends Transformation[Parameter]
  /*with ParameterAliasesStep */with ParametersValidatorsStep with CommonParamDataStep {

  override def data = app.params.toSeq.map { case (r, p) => r.name -> p }

}

trait EnrichmentStep[InputType] {

  type SingleStep = ((Reference, InputType)) => DenotationTable => Denotation

  def app: StrictModel

  def steps: Seq[SingleStep] = Seq.empty

}

trait CommonParamDataStep extends EnrichmentStep[Parameter] with CommonData {

  override def steps = types +: super.steps

  /**
    * Puts type information into the denotation table
    * @return
    */
  protected def types: SingleStep = parameter => table => singleParameter(parameter)

  private def singleParameter(paramPair: (Reference, Parameter)) = {
    val (ref, param) = paramPair
    val t = param.typeName
    val name = typeName(t, ref)
    Map(COMMON -> Map(TYPE_NAME -> name, FIELDS -> fieldsForType(t), MEMBER_NAME -> memberName(t, ref)))
  }

}
trait CommonCallDataStep extends EnrichmentStep[ApiCall] with CommonData {

  override def steps = types +: super.steps

  /**
    * Puts type information into the denotation table
    * @return
    */
  protected def types: SingleStep = parameter => table => singleParameter(parameter)

  private def singleParameter(paramPair: (Reference, ApiCall)) = {
    val ref = paramPair._2.asReference
    val t = null
    val name = typeName(t, ref)
    Map(COMMON -> Map(TYPE_NAME -> name, FIELDS -> fieldsForType(t), MEMBER_NAME -> memberName(t, ref)))
  }
}

trait CommonDataStep extends EnrichmentStep[Type] with CommonData {

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
      Map(COMMON -> Map(TYPE_NAME -> name, FIELDS -> fieldsForType(t), MEMBER_NAME -> memberName(t, ref)))
  }

}

trait CommonData {

  def app: StrictModel

  protected def typeName(t: Type, r: Reference, suffix: String = "") = t match {
    case TypeRef(ref) =>
      app.findType(ref) match {
        case p: PrimitiveType => useType(p.name, suffix, "")
        case _ => useType(ref, suffix, "")
      }
    case p: PrimitiveType => useType(t.name, suffix, "")
    case _ => useType(r, suffix, "")
  }

  @tailrec
  protected final def memberName(t: Type, r: Reference, suffix: String = ""): String = t match {
    case TypeRef(ref) if ! app.findType(ref).isInstanceOf[TypeRef] => memberName(app.findType(ref), ref, suffix)
    case TypeRef(ref) => useType(ref, suffix, "")
    case p: PrimitiveType => useType(r, suffix, "")
    case _ => useType(r, suffix, "")
  }

  protected def dereferenceFields(t: Composite): Seq[Field] =
    t.descendants flatMap {
      case td: TypeDef => td.fields
      case r: TypeRef => app.findType(r.name) match {
        case td: TypeDef => td.fields
        case c: Composite => dereferenceFields(c)
        case other =>
          throw new IllegalStateException(s"Unexpected contents of Composite $r: $other")
      }
    }

  protected def fieldsForType(t: Type): Seq[Field] = {
    t match {
      case c: Composite => dereferenceFields(c).distinct
      case c: TypeDef => c.fields
      case _ => Seq.empty[Field]
    }
  }

  private def useType(ref: Reference, suffix: String, prefix: String) = {
    val fullName = ref.qualifiedName(prefix, suffix)
    fullName._2
  }

}
trait ClassesStep extends EnrichmentStep[Type] {

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
    case _ => empty
  }

  protected def typeDefProps(k: Reference, t: Type)(table: DenotationTable): Map[String, Any] = {
    Map(
      "name" -> typeNameDenotation(table, k),
      "fields" -> typeFields(table, k).map { f =>
        Map(
          "name" -> escape(f.name.simple),
          "type_name" -> typeNameDenotation(table, f.tpe.name)
        )
      }
    )
  }


}

trait TraitsStep extends EnrichmentStep[Type] {

  override def steps = traits +: super.steps

  /**
    * Puts trait related information into the denotation table
    * @return
    */
  protected def traits: SingleStep = typeDef => table => typeDef match {
    case (ref, t: TypeDef) if app.discriminators.contains(ref) =>
      Map("traits" -> typeDefProps(ref, t)(table))
    case _ => empty
  }

  protected def typeDefProps(k: Reference, t: Type)(table: DenotationTable): Map[String, Any] // FIXME should be defined only once
}

trait AliasesStep extends EnrichmentStep[Type] {

  override def steps = aliases +: super.steps

  /**
    * Puts type related information into the denotation table
    * @return
    */
  protected val aliases: SingleStep = typeDef => table => typeDef match {
    case (ref, t: Container) =>
      Map("aliases" -> aliasProps(ref, t)(table))
    case (k, v: PrimitiveType) =>
      Map("aliases" -> mapForAlias(k, v)(table))
    case (k, v: TypeRef) =>
      Map("aliases" -> mapForAlias(v.name, v)(table))
    case _ => empty
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

  private def mapForAlias(k: Reference, v: Type)(table: DenotationTable): Map[String, Object] = {
    Map(
      "name" -> memberNameDenotation(table, k),
      "alias" -> typeNameDenotation(table, v.name),
      "underlying_type" -> None
    )
  }
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