package de.zalando.apifirst.generators

import de.zalando.apifirst.Domain._
import de.zalando.apifirst.generators.DenotationNames._
import de.zalando.apifirst.naming.Reference

/**
  * @author  slasch 
  * @since   28.12.2015.
  */

trait TestsStep extends EnrichmentStep[Type] {

  override def steps = tests +: super.steps

  val testsSuffix = "Spec"

  /**
    * Puts data generators related information into the denotation table
    * @return
    */
  protected val tests: SingleStep = typeDef => table =>
    testsProps(typeDef._1, typeDef._2)(table)

  private def testsProps(ref: Reference, v: Type)(table: DenotationTable): Denotation = v match {
/*
    case t: Container =>
      Map("test_data_aliases" -> containerGenerator(ref, t)(table))
    case t: PrimitiveType =>
      Map("test_data_aliases" -> containerGenerator(ref, t)(table))
    case t: TypeDef if ! ref.simple.startsWith("AllOf") =>
      Map("test_data_classes" -> classGenerator(ref, t)(table))
    case t: Composite  =>
      Map("test_data_classes" -> compositeGenerator(ref, t)(table))
*/

    case _ => empty
  }
/*

  def tests(calls: Seq[ApiCall]) =
    calls filterNot { _.handler.parameters.isEmpty } map callTest

  def callTest(call: ApiCall): Map[String, Object] = {
    val namespace = if (strictModel.basePath == "/") "" else strictModel.basePath
    Map(
      "verb_name" -> call.verb.name,
      "full_path" -> call.path.prepend(namespace).asSwagger,
      "full_url" -> fullUrl(namespace, call),
      "validation_name" -> useType(call.asReference, validatorsSuffix, ""),
      "body?" -> bodyParameter(call),
      "expected_code" -> "200", // FIXME
      expectedResultType(call),
      parameters(call),
      "headers" -> headers(call).toMap
    )
  }

  def expectedResultType(call: ApiCall) = "expected_result_type" -> call.mimeOut.headOption.map(_.name.toLowerCase).getOrElse("application/json")

  def bodyParameter(call: ApiCall) = {
    call.handler.parameters.map {
      strictModel.findParameter
    }.find {
      _.place == ParameterPlace.BODY
    }.map { p =>
      Map("body_parameter_name" -> p.name, expectedResultType(call))
    }
  }

  def headers(call: ApiCall) = {
    call.handler.parameters.filter { p =>
      strictModel.findParameter(p).place == ParameterPlace.HEADER
    }.map {
      "name" -> _.name.simple
    }
  }

  def parameters(call: ApiCall) = {
    lazy val parameters = Map(
      "parameters" -> call.handler.parameters.zipWithIndex.map { case (param, ii) =>
        val paramName = strictModel.findParameter(param)
        val typeName = paramName.typeName
        val genName = generatorNameForType(typeName)
        val genPckg = if (genName.indexOf(']')>0) "" else typeName.name.qualifiedName("", generatorsSuffix)._1 + "."
        Map(
          "name" -> escape(camelize("\\.", param.simple)),
          "type" -> useType(typeName.name, "", ""),
          "generator" -> (genPckg + genName),
          "last" -> (ii == call.handler.parameters.size - 1)
        )
      }
    )
    lazy val parameter = {
      val param = call.handler.parameters.head
      val paramName = strictModel.findParameter(param)
      val typeName = paramName.typeName
      val genName = generatorNameForType(typeName)
      val genPckg = if (genName.indexOf(']')>0) "" else typeName.name.qualifiedName("", generatorsSuffix)._1 + "."
      Map(
        "name" -> escape(camelize("\\.", param.simple)),
        "type" -> useType(typeName.name, "", ""),
        "generator" -> (genPckg + genName)
      )
    }
    val nameParamPair =
      if (call.handler.parameters.isEmpty) "" -> None
      else if (call.handler.parameters.length == 1) "single_parameter?" -> Some(parameter)
      else "parameters?" -> Some(parameters)
    nameParamPair
  }


  private def fullUrl(namespace: String, call: ApiCall) = {
    val query = call.handler.parameters.filter { p =>
      strictModel.findParameter(p).place == ParameterPlace.QUERY
    } map { p =>
      val param = strictModel.findParameter(p)
      singleQueryParam(param.name, param.typeName)
    }
    val fullQuery = if (query.isEmpty) "" else query.mkString("?", "&", "")
    val url = call.path.prepend(namespace).interpolated
    "s\"\"\"" + url + fullQuery + "\"\"\""
  }

  private def singleQueryParam(name: String, typeName: Type): String = typeName match {
    case r@ TypeRef(ref) =>
      singleQueryParam(name, strictModel.findType(ref))
    case c: Domain.Opt =>
      containerParam(name) + "getOrElse(\"\")}"
    case c: Domain.Arr =>
      containerParam(name) + "mkString(\"&\")}"
    case d: Domain.CatchAll =>
      "" // TODO no marshalling / unmarshalling yet
    case d: Domain.TypeDef =>
      "" // TODO no marshalling / unmarshalling yet
    case o =>
      name + "=${URLEncoder.encode(" + name + ".toString, \"UTF-8\")}"
  }
  private def containerParam(name: String) =
    "${" + name + ".map { i => \"" + name + "=\" + URLEncoder.encode(i.toString, \"UTF-8\")}."
*/

}