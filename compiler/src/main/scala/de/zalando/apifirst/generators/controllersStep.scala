package de.zalando.apifirst.generators

import de.zalando.apifirst.Application.{ApiCall, Parameter, ParameterRef}
import de.zalando.apifirst.Domain._
import de.zalando.apifirst.ParameterPlace
import de.zalando.apifirst.ScalaName._
import de.zalando.apifirst.generators.DenotationNames._
import de.zalando.apifirst.naming.Reference

/**
  * @author  slasch
  * @since   31.12.2015.
  */
trait CallControllersStep extends EnrichmentStep[ApiCall] with ControllersCommons {

  override def steps = controllers +: super.steps

  val nameMapping = Map(
    "action"                      -> controllersSuffix,
    "parser_name"                 -> "Parser",
    "writable_json"               -> "WritableJson",
    "action_type"                 -> "ActionType",
    "action_result_type"          -> "ActionResultType",
    "action_success_status_name"  -> "ActionSuccessStatus",
    "action_request_type"         -> "ActionRequestType",
    "response_mime_type_name"     -> "ResponseMimeType",
    "request"                     -> "Request",
    "method"                      -> ""
  )

  /**
    * Puts controllers related information into the denotation table
    *
    * @return
    */
  protected val controllers: SingleStep = call => table =>
    Map("controller" -> controllerProps(call._1, call._2)(table))

  private def controllerProps(ref: Reference, call: ApiCall)(implicit table: DenotationTable) = {
    val bodyParam           = validationsByType(call, p => p.place == ParameterPlace.BODY)
    val headerParams        = validationsByType(call, p => p.place == ParameterPlace.HEADER)
    val nonBodyParams       = validationsByType(call, p => p.place != ParameterPlace.BODY && p.place != ParameterPlace.HEADER)
    val allValidations      = callValidations(ref).asInstanceOf[Seq[_]]
    val actionResultType    = resultType(call)(table)
    val actionErrorMappings = errorMappings(call)
    val nameParamPair       = singleOrMultipleParameters(call)(table)

    val nameMappings        = nameMapping map { case (k, v) => k -> nameWithSuffix(call, v) }

    val method              = nameMappings("method")
    val action              = nameMappings("action")

    Map(
      "response_mime_type_value"      -> call.mimeOut.headOption.map(_.name).getOrElse("application/json"), // TODO implement content negotiation
      "request_mime_type_value"       -> call.mimeIn.headOption.map(_.name).getOrElse("application/json"), // TODO implement content negotiation
      "action_result_type_value"      -> actionResultType, // TODO implement content negotiation
      "action_success_status_value"   -> 200, // FIXME
      "method"                        -> method,
      "signature"                     -> signature(action, method),
      "comment"                       -> comment(action),

      "process_valid_request"         -> prepend("processValid", nameMappings("request")),
      "error_to_status"               -> prepend("errorToStatus", nameMappings("method")),

      "error_mappings"                -> actionErrorMappings,
      "validations"                   -> allValidations,
      "body_param"                    -> bodyParam,
      "non_body_params"               -> nonBodyParams,
      "header_params"                 -> headerParams,

      "validations?"                  -> allValidations.nonEmpty,
      "non_body_params?"              -> nonBodyParams.nonEmpty,
      "body_param?"                   -> bodyParam.nonEmpty,
      "request_needed?"               -> (bodyParam.nonEmpty || headerParams.nonEmpty)
    ) ++ nameMappings + nameParamPair
  }

  private def nameWithSuffix(call: ApiCall, suffix: String): String =
    escape(call.handler.method + suffix)

  private def resultType(call: ApiCall)(table: DenotationTable): Option[String] = {
    call.resultTypes.toSeq.sortBy(_.simple).headOption.map { t =>
      val tpe = app.findType(t.name)
      tpe match {
        case c: Container =>
          // TODO this should be readable from model
          c.name.simple + c.nestedTypes.map{ t => typeNameDenotation(table, t.name)}.mkString("[", ", ", "]")
        case p: ProvidedType => typeNameDenotation(table, p.name)
        case o => o.name.className
      }
    }
  }

  private def errorMappings(call: ApiCall): Iterable[Map[String, String]] =
    call.errorMapping.flatMap { case (k, v) => v.map { ex =>
      Map("exception_name" -> ex.getCanonicalName, "exception_code" -> k)
    }}

  private def validationsByType(call: ApiCall, filter: Parameter => Boolean)(implicit table: DenotationTable) =
    parametersValidations(table)(parametersByPlace(call, filter))

  private def parametersByPlace(call: ApiCall, filter: Parameter => Boolean): Seq[ParameterRef] =
    call.handler.parameters.filter { p => filter(app.findParameter(p)) }

  private def singleOrMultipleParameters(call: ApiCall)(table: DenotationTable): (String, Option[Map[String, Any]]) = {
    lazy val parameters = Map("parameters" -> call.handler.parameters.map { parameterMap(table) })
    lazy val parameter = parameterMap(table)(call.handler.parameters.head)
    val nameParamPair =
      if (call.handler.parameters.isEmpty) "" -> None
      else if (call.handler.parameters.length == 1) "single_parameter?" -> Some(parameter)
      else "parameters?" -> Some(parameters)
    nameParamPair
  }

  private def parameterMap(table: DenotationTable): ParameterRef => Map[String, String] = param => {
    val typeName = app.findParameter(param).typeName
    Map(
      "field_name" -> escape(camelize("\\.", param.simple)), // should be taken from the validation
      "header_method" -> "get",
      "type_name" -> typeNameDenotation(table, typeName.name)
    )
  }

  def comment(action: String) = s"$eof $action"

  def parametersValidations(table: DenotationTable)(parameters: Seq[ParameterRef]) =
    parameters map parameterMap(table)

  // TODO made these names constants
  def callValidations(ref: Reference)(implicit table: DenotationTable) =
    table(ref)("validators").getOrElse("call_validations", Nil)

  def signature(action: String, method: String): String = s"val $method = $action {"

}

trait ControllersCommons {

  val controllersSuffix = "Action"

  val eof = "//////// EOF //////// "

}