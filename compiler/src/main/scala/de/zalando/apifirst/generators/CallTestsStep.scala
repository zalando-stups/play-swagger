package de.zalando.apifirst.generators

import de.zalando.apifirst.Application.{ApiCall, ParameterRef}
import de.zalando.apifirst.Domain._
import de.zalando.apifirst.generators.DenotationNames._
import de.zalando.apifirst.{ParameterPlace, ScalaName}

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
      "body_param" -> bodyParameter(call),
      expectedSuccessCode(call),
      expectedResultType(call),
      "headers" -> headers(call)
    ) ++ parameters(call)(table)
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
    }.map { p =>
      Map("name" -> p.name.simple)
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
    lazy val parameters = call.handler.parameters.map { singleParameter(table) }
    lazy val parameter = call.handler.parameters.headOption map singleParameter(table)
    Map("multiple_parameters" -> (if (call.handler.parameters.length > 1) parameters else Nil),
       "single_parameter" -> (if (call.handler.parameters.length == 1) parameter else None))
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

  private def singleQueryParam(name: String, typeName: Type): String = "$" + s"""{toQuery("${ScalaName.escape(name)}", $name)}"""
}