package de.zalando.play.apifirst.sbt

import java.io.File

import de.zalando.apifirst.Application.StrictModel
import de.zalando.apifirst.Hypermedia
import de.zalando.apifirst.generators.AstScalaPlayEnricher

/**
  * @since 23.03.2016.
  */
object ApiFirstPrettyPrinter {
  def types(file: File, ast: StrictModel): Seq[String] = {
    val typeDefs  = ast.typeDefs
    val typeMap   = typeDefs map { case (k, v) => k -> ("\n\t\t" + v.toShortString("\t\t\t")) }
    val lines = typeMap.toSeq.sortBy(_._1.parts.size).map(p => formatText(p._1.toString())(magenta,black) + " → " + p._2)
    withFileName("Types:\t", file, lines)
  }

  def parameters(file: File, ast: StrictModel): Seq[String] = {
    val params      = ast.params
    val lines       = params.toSeq.sortBy(_._1.name.parts.size).map(p => formatText(p._1.name.toString)(dyellow,black) + " → " + p._2)
    withFileName("Parameters:\t", file, lines)
  }

  def states(file: File, ast: StrictModel): Seq[String] = {
    val lines       = Hypermedia.State.toDot(ast.stateTransitions)
    withFileName("State Diagram:\t", file, lines)
  }

  def denotations(file: File, ast: StrictModel): Seq[String] = {
    val play = AstScalaPlayEnricher(ast)
    val lines = play.toSeq.map({case (ref, den) => formatText(ref.toString)(dyellow, black) + " → " + den })
    withFileName("Parameters:\t", file, lines)
  }

  def withFileName(prefix: String, definitionFile: File, lines: Seq[String]): Seq[String] =
    s"\n$prefix${formatText(definitionFile.getName)(blue,black)}\n" +: "\n" +: lines :+ "\n" :+ "\n"

  def textColor(color: Int): String = { s"\033[38;5;${color}m" }
  def backgroundColor(color:Int): String = { s"\033[48;5;${color}m" }
  def reset: String = { s"\033[0m" }

  def formatText(str: String)(txtColor: Int, backColor: Int): String = {
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
