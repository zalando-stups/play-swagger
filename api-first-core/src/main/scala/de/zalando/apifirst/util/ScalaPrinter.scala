package de.zalando.apifirst.util

import de.zalando.apifirst.Application.StrictModel

/**
  * @since 25.05.2016.
  */
object ScalaPrinter {

  def nameFromModel(ast: AnyRef): String =
    ast.getClass.getName.split("\\.").last.replaceAllLiterally("_yaml$", "") + "_yaml"

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

  def asScala(fileName:String, ast: StrictModel): String = {
    "package de.zalando.model\n" +
    imports +
      s" \n//noinspection ScalaStyle\nobject ${fileName.replace('.', '_')} extends WithModel {\n" +
      types(ast) +
      "\n" +
      parameters(ast) +
      "\n" +
      basePath(ast) +
      "\n" +
      discriminators(ast) +
      "\n" +
      security(ast) +
      "\n" +
      transitions(ast) +
    s"""
      |def calls: Seq[ApiCall] = ${ast.calls.map(_.toScalaString).mkString("Seq(", ", ", ")")}
      |
      |def packageName: Option[String] = ${ast.packageName.map("\"" + _ + "\"")}
      |
      |def model = new StrictModel(calls, types, parameters, discriminators, basePath, packageName, stateTransitions, securityDefinitions)
    """.stripMargin +
    "\n} "
  }

  def transitions(ast: StrictModel): String = {
    val transStr = ast.stateTransitions.map {
      case (k, v) => "\"" + k + "\" -> " + v.map { case (kk, vv) =>
        "\"" + kk + "\" -> TransitionProperties(" + vv.condition.map("\"" + _ + "\"") + ")"
      }.mkString("Map[State, TransitionProperties](", ", ", ")")
    }.mkString(", ")
    s"""def stateTransitions: StateTransitionsTable = Map[State, Map[State, TransitionProperties]]($transStr)"""
  }

  def security(ast: StrictModel): String = {
    val securityStr = ast.securityDefinitionsTable.map {
      case (k, v) => "\"" + k + "\" -> " + v.toScalaString
    }.mkString("\n\t", ",\n\t", "\n")
    s""" def securityDefinitions: SecurityDefinitionsTable = Map[String, Security.Definition]($securityStr)"""
  }

  def discriminators(ast: StrictModel): String = {
    val discStr = s"""${ast.discriminators.map{case (k, v) => k.toScalaString("\t") -> v.toScalaString("")}.mkString(",\n")}"""
    s" def discriminators: DiscriminatorLookupTable = Map[Reference, Reference](\n\t$discStr)"
  }
  def basePath(ast: StrictModel): String = {
    val path = if (ast.basePath == null) "null" else s""" "${ast.basePath}" """
    s" def basePath: String =$path"
  }

  def types(ast: StrictModel): String = {
    val typeDefs = ast.typeDefs
    val typeMap = typeDefs map { case (k, v) => k -> ("\n\t\t" + v.toScalaString("\t\t\t")) }
    val lines = typeMap.toSeq.sortBy(_._1.parts.size).map(p => p._1.toScalaString("\t") + " → " + p._2)
    s" \n def types = Map[Reference, Type](\n${lines.mkString(",\n")}\n) "
  }
  def parameters(ast: StrictModel): String = {
    val params      = ast.params
    val lines       = params.toSeq.sortBy(_._1.name.parts.size).map(p => "\tParameterRef(" + p._1.name.toScalaString("\t") + ") → " + p._2.toScalaString)
    s" \n def parameters = Map[ParameterRef, Parameter](\n${lines.mkString(",\n")}\n) "
  }

}

