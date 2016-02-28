package de.zalando.apifirst.generators

import de.zalando.apifirst.Application.{ApiCall, Parameter, ParameterRef}
import de.zalando.apifirst.Domain._
import de.zalando.apifirst.ParameterPlace
import de.zalando.apifirst.ScalaName._
import de.zalando.apifirst.generators.DenotationNames._
import de.zalando.apifirst.naming.Reference
import de.zalando.play.controllers.WriterFactories

/**
  * @author  slasch
  * @since   31.12.2015.
  */
trait CallControllersStep extends EnrichmentStep[ApiCall] with ControllersCommons {

  override def steps = controllers +: super.steps

  val nameMapping = Map(
    "action"                      -> controllersSuffix,
    "parser_name"                 -> "Parser",
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

    val actionErrorMappings = errorMappings(call)
    val nameParamPair       = singleOrMultipleParameters(call)(table)

    val customWriters       = call.mimeOut.map(_.name).diff(WriterFactories.factories.keySet).nonEmpty

    val nameMappings        = nameMapping map { case (k, v) => k -> nameWithSuffix(call, v) }

    val method              = nameMappings("method")
    val action              = nameMappings("action")

    val (allActionResults, defaultResultType) = actionResults(call)(table)

    Map(
      "request_mime_type_value"       -> call.mimeIn.headOption.map(_.name).getOrElse("application/json"), // TODO implement content negotiation
      "result_types"                  -> allActionResults,
      "default_result_type"           -> defaultResultType,
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

      "produces"                      -> call.mimeOut.map(_.name).map("\"" + _ + "\"").mkString("Seq[String](",", ",")"),
      "needs_custom_writers"          -> customWriters,
      "has_no_validations"            -> allValidations.isEmpty,
      "has_no_error_mappings"         -> actionErrorMappings.isEmpty
    ) ++ nameMappings ++ nameParamPair
  }

  private def nameWithSuffix(call: ApiCall, suffix: String): String =
    escape(call.handler.method + suffix)

  private def actionResults(call: ApiCall)(table: DenotationTable): (Seq[Map[String, Any]], Option[String]) = {
    val resultTypes = call.resultTypes.toSeq map { case(code, ref) =>
        Map("code" -> code, "type" -> singleResultType(table)(ref))
    }
    val default = call.defaultResult.map(singleResultType(table))
    if (default.isEmpty && resultTypes.isEmpty)
      println("Could not found any response code definition. It's not possible to define any marshallers. This will lead to the error at runtime.")
    (resultTypes, default)
  }

  private def singleResultType(table: DenotationTable)(ref: ParameterRef): String = {
    val tpe = app.findType(ref.name)
    tpe match {
      case c: Container =>
        // TODO this should be readable from model
        c.name.simple + c.nestedTypes.map{ t => typeNameDenotation(table, t.name)}.mkString("[", ", ", "]")
      case p: ProvidedType => typeNameDenotation(table, p.name)
      case p: TypeDef => typeNameDenotation(table, p.name)
      case o => o.name.className
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

  private def singleOrMultipleParameters(call: ApiCall)(table: DenotationTable) = {
    val parameters = call.handler.parameters map parameterMap(table)
    val parameter = parameters.headOption
    Map(
      "single_parameter" -> (if (call.handler.parameters.length == 1) parameter else None),
      "multiple_parameters" -> (if (call.handler.parameters.length > 1) parameters else Nil)
    )
  }

  private def parameterMap(table: DenotationTable): ParameterRef => Map[String, String] = param => {
    val parameter = app.findParameter(param)
    val typeName = parameter.typeName
    val commonTypeName = typeNameDenotation(table, typeName.name)
    val (parser, parserType) = typeName match {
      case TypeRef(ref) =>
        app.findType(ref) match {
          case Opt(underlyingType, _) =>
            val tpeName = typeNameDenotation(table, underlyingType.name)
            ("optionParser", tpeName)
          case _ => ("anyParser", commonTypeName)
        }
      case _ => ("anyParser", commonTypeName)
    }
    Map(
      "field_name" -> escape(camelize("\\.", param.simple)), // should be taken from the validation
      "type_name" -> commonTypeName,
      "parser_type" -> parserType,
      "body_parser"  -> parser,
      "real_name" -> param.simple
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