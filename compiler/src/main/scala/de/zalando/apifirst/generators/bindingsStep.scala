package de.zalando.apifirst.generators

import de.zalando.apifirst.Application.Parameter
import de.zalando.apifirst.Domain._
import de.zalando.apifirst.ParameterPlace
import de.zalando.apifirst.generators.DenotationNames._
import de.zalando.apifirst.naming.Reference

/**
  * @author slasch 
  * @since 06.01.2016.
  */
/**
  * @author slasch
  * @since 30.12.2015.
  */
trait ParamBindingsStep extends EnrichmentStep[Parameter] {

  override def steps = bindings +: super.steps

  /**
    * Puts type information into the denotation table
    *
    * @return
    */
  protected def bindings: SingleStep = parameter => table =>
    Map("bindings" -> singleParameter(parameter, table))

  private def singleParameter(paramPair: (Reference, Parameter), table: DenotationTable): Map[String, Any] =
    paramPair._2.place match {
      case ParameterPlace.BODY    =>
        // body parameters do not need bindables
        Map.empty
      case ParameterPlace.FORM    =>
        // TODO not supported yet
        Map.empty
      case other    =>
        val key = other.toString + "_param"
        Map(key -> forType(paramPair._1, paramPair._2.typeName, table))
    }

  val providedBindings = Seq(classOf[Flt], classOf[Intgr], classOf[Lng], classOf[Dbl], classOf[Bool], classOf[Str])

  def forType(k: Reference, typeName: Type, table: DenotationTable): Seq[Map[String, Any]] = typeName match {
    case i if providedBindings.contains(i.getClass) => Nil
    case tpe if tpe.nestedTypes.nonEmpty =>
      val mainType = Seq(Map(
        "alias" -> tpe.name.simple,
        "underlying_type" -> tpe.nestedTypes.map { t => typeNameDenotation(table, t.name) }.mkString(", "),
        "binding_imports" -> Set("de.zalando.play.controllers.PlayPathBindables")
      ))
      val nestedTypes = tpe.nestedTypes.flatMap { nt => forType(nt.name, nt, table) }
      mainType ++ nestedTypes
    case TypeRef(ref) => forType(ref, app.findType(ref), table)
  }

}