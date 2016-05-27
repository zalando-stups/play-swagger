package de.zalando.apifirst.util

import java.io.File

import de.zalando.apifirst.Application.StrictModel

/**
  * @since 25.05.2016.
  */
object ScalaPrinter {
  private val imports = Seq(
    "de.zalando.apifirst.Application._",
    "de.zalando.apifirst.Domain._",
    "de.zalando.apifirst.ParameterPlace",
    "de.zalando.apifirst.naming._",
    "de.zalando.apifirst.Hypermedia._",
    "de.zalando.apifirst.Http._",
    "de.zalando.apifirst.Security",
    "java.net.URL",
    "Security._"
  ).map("import " + _).mkString("\n")

  def asScala(file:File, ast: StrictModel): String = {
    imports + s"\n//noinspection ScalaStyle\nobject ${file.getName.replace('.', '_')} extends WithModel {\n" +
      types(file, ast) +
      "\n" +
      parameters(file, ast) +
      "\n" +
      basePath(ast) +
      "\n" +
      discriminators(ast) +
      "\n" +
      security(ast) +
      "\n" +
      transitions(ast) +
    s"""
      |val calls: Seq[ApiCall] = ${ast.calls.map(_.toScalaString).mkString("Seq(", ", ", ")")}
      |
      |val packageName: Option[String] = ${ast.packageName.map("\"" + _ + "\"")}
      |
      |val model = new StrictModel(calls, types, parameters, discriminators, basePath, packageName, stateTransitions, securityDefinitions)
    """.stripMargin +
    "\n} "
  }

  def transitions(ast: StrictModel): String = {
    val transStr = ast.stateTransitions.map {
      case (k, v) => "\"" + k + "\" -> " + v.map { case (kk, vv) =>
        "\"" + kk + "\" -> TransitionProperties(" + vv.condition.map("\"" + _ + "\"") + ")"
      }.mkString("Map[State, TransitionProperties](", ", ", ")")
    }.mkString(", ")
    s"""val stateTransitions: StateTransitionsTable = Map[State, Map[State, TransitionProperties]]($transStr)"""
  }

  def security(ast: StrictModel): String = {
    val securityStr = ast.securityDefinitionsTable.map {
      case (k, v) => "\"" + k + "\" -> " + v.toScalaString
    }.mkString("\n\t", ",\n\t", "\n")
    s"""val securityDefinitions: SecurityDefinitionsTable = Map[String, Security.Definition]($securityStr)"""
  }

  def discriminators(ast: StrictModel): String = {
    val discStr = s"""${ast.discriminators.map{case (k, v) => k.toScalaString("\t") -> v.toScalaString("")}.mkString(",\n")}"""
    s"val discriminators: DiscriminatorLookupTable = Map[Reference, Reference](\n\t$discStr)"
  }
  def basePath(ast: StrictModel): String = {
    val path = if (ast.basePath == null) "null" else s""" "${ast.basePath}" """
    s"val basePath: String =$path"
  }

  def types(file: File, ast: StrictModel): String = {
    val typeDefs = ast.typeDefs
    val typeMap = typeDefs map { case (k, v) => k -> ("\n\t\t" + v.toScalaString("\t\t\t")) }
    val lines = typeMap.toSeq.sortBy(_._1.parts.size).map(p => p._1.toScalaString("\t") + " → " + p._2)
    s" \n val types = Map[Reference, Type](\n${lines.mkString(",\n")}\n) "
  }
  def parameters(file: File, ast: StrictModel): String = {
    val params      = ast.params
    val lines       = params.toSeq.sortBy(_._1.name.parts.size).map(p => "\tParameterRef(" + p._1.name.toScalaString("\t") + ") → " + p._2.toScalaString)
    s" \n val parameters = Map[ParameterRef, Parameter](\n${lines.mkString(",\n")}\n) "
  }

}

