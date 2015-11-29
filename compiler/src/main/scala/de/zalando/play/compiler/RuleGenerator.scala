package de.zalando.play.compiler

import de.zalando.apifirst.Application.{StrictModel, ApiCall}
import de.zalando.apifirst.ParameterPlace
import de.zalando.apifirst.Path.{InPathParameter, Segment, Root}
import play.routes.compiler._
import de.zalando.apifirst.ScalaName._

/**
  * @author  slasch 
  * @since   27.11.2015.
  *
  * TODO this generator is a copy of the prototypical implementation
  * It must be refactored after FullPath implementation is removed
  */
object RuleGenerator {

  def apiCalls2PlayRules(calls: ApiCall*)(implicit model: StrictModel): Seq[Rule] = calls map { call =>
    val verb = HttpVerb(call.verb.name)

    val handlerCall = HandlerCall(packageName(call), call.handler.controller, call.handler.instantiate,
      call.handler.method, Some(parameters2parameters(call)))

    val path = PathPattern(path2path(call))
    val comments = List.empty
    Route(verb, path, handlerCall, comments)
  }

  def packageName(call: ApiCall) = call.handler.packageName

  private def parameters2parameters(call: ApiCall)(implicit model: StrictModel): Seq[Parameter] = {
    val params = call.handler.parameters flatMap { param =>
      val p = model.findParameter(param)
      if (p.place != ParameterPlace.BODY && p.place != ParameterPlace.HEADER)
        Some(Parameter(p.name, p.typeName.name.typeAlias(), p.fixed, p.default))
      else
        None
    }
    params
  }

  def path2path(call: ApiCall)(implicit model: StrictModel): Seq[PathPart] = {
    val result = call.path.value.flatMap {
      case Root => Some(StaticPart(Root.value))
      case s : Segment => Some(StaticPart(s.value + Root.value))
      case p : InPathParameter =>
        val pRef = call.handler.parameters.find(_.simple == p.simple).get
        val param = model.findParameter(pRef)
        Some(DynamicPart(p.value, param.constraint, param.encode))
    }.dropWhile(_ == StaticPart(Root.value))
    // oh my
    result match {
      case head :: StaticPart(name) :: StaticPart(Root.value) :: Nil  =>
        head :: StaticPart(name) :: Nil
      case head :: StaticPart(name) :: Nil if name.nonEmpty =>
        head :: StaticPart(name.init) :: Nil
      case StaticPart(name) :: StaticPart(Root.value) :: Nil  =>
        StaticPart(name) :: Nil
      case StaticPart(name) :: Nil if name.nonEmpty =>
        StaticPart(name.init) :: Nil
      case _ => result
    }
  }

}