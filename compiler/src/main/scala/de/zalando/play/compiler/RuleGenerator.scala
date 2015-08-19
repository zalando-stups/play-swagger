package de.zalando.play.compiler

import de.zalando.apifirst.Application.ApiCall
import de.zalando.apifirst.Path.{InPathParameter, Segment, Root}
import play.routes.compiler._

/**
 * @since 20.07.2015
 */
object RuleGenerator {

  def apiCalls2PlayRules(calls: ApiCall*): Seq[Rule] = calls map { call =>
    val verb = HttpVerb(call.verb.name)

    val handlerCall = HandlerCall(packageName(call), call.handler.controller, call.handler.instantiate,
      call.handler.method, Some(parameters2parameters(call)))

    val path = PathPattern(path2path(call))
    val comments = List.empty
    Route(verb, path, handlerCall, comments)
  }

  private def packageName(call: ApiCall) = call.handler.packageName

  private def parameters2parameters(call: ApiCall): Seq[Parameter] = {
    call.handler.parameters map { p =>
      Parameter(p.name, p.typeName.name.asSimpleType, p.fixed, p.default)
    }
  }

  private def path2path(call: ApiCall): Seq[PathPart] =
    call.path.value flatMap {
      case Root => None
      case s : Segment => Some(StaticPart(s.value + Root.value))
      case p : InPathParameter => Some(DynamicPart(p.value, p.constraint, p.encode))
    }

}
