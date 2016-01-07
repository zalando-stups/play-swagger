package de.zalando.apifirst.generators

import de.zalando.apifirst.Application.{ApiCall, ParameterRef}
import de.zalando.apifirst.Domain._
import de.zalando.apifirst.generators.DenotationNames._
import de.zalando.apifirst.{ScalaName, Domain, ParameterPlace}

/**
  * @author  slasch
  * @since   30.12.2015.
  */
trait CallTestsStep extends EnrichmentStep[ApiCall] {

  override def steps = tests +: super.steps

  val testsSuffix = "Spec"

  /**
    * Puts tests related information into the denotation table
    *
    * @return
    */
  protected val tests: SingleStep = apiCall => table =>
    if (apiCall._2.handler.parameters.isEmpty) empty
    else Map("tests" -> callTest(apiCall._2)(table))

  def callTest(call: ApiCall)(table: DenotationTable) = {
    val namespace = if (app.basePath == "/") "" else app.basePath
    Map(
      "verb_name" -> call.verb.name,
      "full_path" -> call.path.prepend(namespace).asSwagger,
      "full_url" -> fullUrl(namespace, call),
      "validation_name" -> validator(call.asReference, table),
      "body?" -> bodyParameter(call),
      expectedSuccessCode(call),
      expectedResultType(call),
      parameters(call)(table),
      "headers" -> headers(call).toMap
    )
  }

  // FIXME extend ApiCall to hold needed information
  def expectedSuccessCode(call: ApiCall) =
    "expected_code" -> "200"

  // TODO should be capable of handling any of expected results
  def expectedResultType(call: ApiCall) =
    "expected_result_type" -> call.mimeOut.headOption.map(_.name.toLowerCase).getOrElse("application/json")

  def bodyParameter(call: ApiCall) = {
    call.handler.parameters.map {
      app.findParameter
    }.find {
      _.place == ParameterPlace.BODY
    }.map { p =>
      Map("body_parameter_name" -> p.name, expectedResultType(call))
    }
  }

  def headers(call: ApiCall) = {
    call.handler.parameters.filter { p =>
      app.findParameter(p).place == ParameterPlace.HEADER
    }.map {
      "name" -> _.name.simple
    }
  }

  def singleParameter(table: DenotationTable)(param: ParameterRef) = {
    val paramName = app.findParameter(param)
    val typeName = paramName.typeName
    val genName = generator(typeName.name, table)
    Map(
      "name" -> ScalaName.escape(ScalaName.camelize("\\.", param.simple)),
      "type" -> typeNameDenotation(table, param.name),
      "generator" -> genName
    )
  }

  private def parameters(call: ApiCall)(table: DenotationTable) = {
    lazy val parameters = Map("parameters" -> call.handler.parameters.map { singleParameter(table) })
    lazy val parameter = singleParameter(table)(call.handler.parameters.head)
    val nameParamPair =
      if (call.handler.parameters.isEmpty) "" -> None
      else if (call.handler.parameters.length == 1) "single_parameter?" -> Some(parameter)
      else "parameters?" -> Some(parameters)
    nameParamPair
  }

  private def fullUrl(namespace: String, call: ApiCall) = {
    val query = call.handler.parameters.filter { p =>
      app.findParameter(p).place == ParameterPlace.QUERY
    } map { p =>
      val param = app.findParameter(p)
      singleQueryParam(param.name, param.typeName)
    }
    val fullQuery = if (query.isEmpty) "" else query.mkString("?", "&", "")
    val url = call.path.prepend(namespace).interpolated
    "s\"\"\"" + url + fullQuery + "\"\"\""
  }

  private def singleQueryParam(name: String, typeName: Type): String = typeName match {
    case r@ TypeRef(ref) =>
      singleQueryParam(name, app.findType(ref))
    case c: Domain.Opt =>
      containerParam(name) + "getOrElse(\"\")}"
    case c: Domain.Arr =>
      if (c.format == "multi") containerParam(name) + "mkString(\"&\")}"
      else containerParam(name) + "mkString(\"&\")}" // FIXME provide correct formatting here
    case d: Domain.CatchAll =>
      "" // TODO no marshalling / unmarshalling yet
    case d: Domain.TypeDef =>
      "" // TODO no marshalling / unmarshalling yet
    case o =>
      name + "=${URLEncoder.encode(" + name + ".toString, \"UTF-8\")}"
  }

  private def containerParam(name: String) =
    "${" + name + ".map { i => \"" + name + "=\" + URLEncoder.encode(i.toString, \"UTF-8\")}."

}