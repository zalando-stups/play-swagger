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
        // FIXME not supported yet
        Map.empty
      case other    =>
        val tpe = other.toString.capitalize
        Map("binding" -> forType(tpe: String, paramPair._1, paramPair._2.typeName, table))
    }

  val providedBindings = Seq(classOf[Flt], classOf[Intgr], classOf[Lng], classOf[Dbl], classOf[Bool], classOf[Str])

  def forType(tpe: String, k: Reference, typeName: Type, table: DenotationTable): Seq[Map[String, Any]] = typeName match {
    case i if providedBindings.contains(i.getClass) => Nil
    case someTpe if someTpe.nestedTypes.nonEmpty =>
      val alias =  someTpe.name.simple
      val underlyingType = someTpe.nestedTypes.map { t => typeNameDenotation(table, t.name) }.mkString(", ")
      val bindable = s"""implicit val bindable_$alias$underlyingType$tpe = PlayPathBindables.create$alias${tpe}Bindable[$underlyingType]"""
      val format = someTpe match {
        case arr: Arr => "(\"" + arr.format + "\")"
        case _ => ""
      }
      val mainType = Seq(Map(
        "name" -> bindable,
        "binding_imports" -> Set("de.zalando.play.controllers.PlayPathBindables"),
        "format" -> format
      ))
      val nestedTypes = someTpe.nestedTypes.flatMap { nt => forType(tpe, nt.name, nt, table) }
      mainType ++ nestedTypes
    case TypeRef(ref) => forType(tpe, ref, app.findType(ref), table)
    case d: Date =>
      Seq(Map(
        "binding_imports" -> Set(
          "de.zalando.play.controllers.PlayPathBindables",
          s"PlayPathBindables.${tpe.toLowerCase}BindableDateMidnight"
        )
      ))
    case d: DateTime =>
      Seq(Map(
        "binding_imports" -> Set(
          "de.zalando.play.controllers.PlayPathBindables",
          s"PlayPathBindables.${tpe.toLowerCase}BindableDateTime"
        )
      ))
  }

}