package de.zalando.play.swagger.sbt

import de.zalando.apifirst.Application.StrictModel
import de.zalando.play.compiler.SwaggerCompilationTask

/**
  * @since 23.03.2016.
  */
object SwaggerPrettyPrinter {
  def types(task: SwaggerCompilationTask, ast: StrictModel): Seq[String] = {
    val typeDefs  = ast.typeDefs
    val typeMap   = typeDefs map { case (k, v) => k -> ("\n\t\t" + v.toShortString("\t\t\t")) }
    val lines = typeMap.toSeq.sortBy(_._1.parts.size).map(p => formatText(p._1.toString())(magenta,black) + " → " + p._2)
    withFileName("Types:\t", task, lines)
  }

  def parameters(task: SwaggerCompilationTask, ast: StrictModel): Seq[String] = {
    val params      = ast.params
    val lines       = params.toSeq.sortBy(_._1.name.parts.size).map(p => formatText(p._1.name.toString)(dyellow,black) + " → " + p._2)
    withFileName("Parameters:\t", task, lines)
  }

  def withFileName(prefix: String, task: SwaggerCompilationTask, lines: Seq[String]): Seq[String] =
    s"\n$prefix${formatText(task.definitionFile.getName)(blue,black)}\n" +: "\n" +: lines :+ "\n" :+ "\n"

  def textColor(color: Int)      = { s"\033[38;5;${color}m" }
  def backgroundColor(color:Int) = { s"\033[48;5;${color}m" }
  def reset                      = { s"\033[0m" }

  def formatText(str: String)(txtColor: Int, backColor: Int) = {
    s"${textColor(txtColor)}${backgroundColor(backColor)}$str$reset"
  }
  // see http://misc.flogisoft.com/bash/tip_colors_and_formatting, 88/256 Colors for more color codes
  val red     = 1
  val green   = 2
  val yellow  = 11
  val dyellow = 229
  val white   = 15
  val black   = 16
  val orange  = 208
  val blue    = 21
  val gray    = 243
  val lblue   = 117
  val magenta = 225
}
