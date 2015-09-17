package de.zalando.play.compiler

import java.text.SimpleDateFormat
import java.util.Date

import de.zalando.apifirst.Application.{ApiCall, Model}
import de.zalando.apifirst.Domain._

/**
 * @since 15.09.2015
 */

trait ChainedGenerator extends GeneratorBase {

  def generators: List[GeneratorBase]

  def defaultNamespace = "definitions"

  override val placeHolder = "#IMPORT#"

  def generate(namespace: String)(implicit ast: Model) = {
    val (imports, result) = generateNamespace(defaultNamespace, generators)
    if (result.nonEmpty) {
      val now = new SimpleDateFormat(nowFormat).format(new Date())
      val pkgName = s"package $namespace"

      val prefix = mainTemplate.
        replace(placeHolder, imports.map { i: String => "import " + i }.mkString("\n")).
        replace("#PACKAGE#", pkgName).
        replace("#NOW#", now)
      Seq((imports, prefix + result))
    } else Seq((imports, result))
  }

  def generateNamespace(namespace: String, generators: Seq[GeneratorBase])(implicit ast: Model): (Set[String], String) =
    generateNamespace0(namespace, generators)(ast, ast.definitions)

  def generateNamespace0(namespace: String, generators: Seq[GeneratorBase])(ast: Model, model: ModelDefinition): (Set[String], String) = {

    val thisNamespace = model.flatMap(_.nestedTypes).filterNot(_.isInstanceOf[Reference])

    val template =
      if (thisNamespace.nonEmpty) {
        val nested = thisNamespace groupBy (_.name.namespace) map { case (space, typeDefs) =>
          generateNamespace0(space.nestedNamespace, generators)(ast, typeDefs)
        }
        val nestedStr = nested.values map pad mkString "\n"
        namespaceTemplate.
          replace("#NAMESPACE#", namespace).
          replace("#NESTED#", nestedStr)
      } else
      if (model.exists(!_.isInstanceOf[Reference])) {
        namespaceTemplate.
          replace("#NAMESPACE#", namespace).
          replace("#NESTED#", "")
      }
      else
        ""

    val endResult = generators.foldLeft((Set.empty[String], template)) { case (result, generator) =>
      val info = generator.generate(namespace)(ast.copy(definitions = model))
      val imports = info.map(_._1)
      val code = info map (_._2) map pad mkString "\n"
      (imports.flatten.toSet ++ result._1, result._2.replace(generator.placeHolder, code))
    }
    endResult
  }

  protected val nowFormat = "dd.MM.yyyy HH:mm:ss"

  protected[compiler] val mainTemplate =
    s"""#PACKAGE#
       |
       |#IMPORT#
       |
       |/** @since #NOW# */
       |""".stripMargin


  protected[compiler] val namespaceTemplate =
    s"""
       |object #NAMESPACE# {#NESTED#
       |#TRAITS#
       |#CLASSES#
       |}""".stripMargin

}

/**
 * Dummy implementation to remove given placeHolder from the template
 * @param placeHolder the placeholder to remove
 */
class EmptyGenerator(override val placeHolder: String) extends GeneratorBase {
  override def generate(namespace: String)(implicit model: Model): Iterable[(Set[String], String)] = Nil
}


trait GeneratorBase {

  def pad(s: String) = s split "\n" map { l => if (l.trim.nonEmpty) PAD + l else l } mkString "\n"

  def generate(namespace: String)(implicit model: Model): Iterable[(Set[String], String)]

  def placeHolder: String

  val PAD = "  "

  def comment(meta: TypeMeta, pad: String = PAD) = meta.comment.map {
    _.split("\n").map(c => if (c.nonEmpty) pad + "// " + c else c).mkString("\n")
  } getOrElse ""

}

trait CallsGeneratorBase extends GeneratorBase {
  def groupByFile(ast: Model): Map[String, Map[String, Seq[ApiCall]]] = {
    val groupedByController = ast.calls.groupBy { call =>
      call.handler.packageName
    } map { case ((pkg, calls)) =>
      pkg -> calls.groupBy(_.handler.controller)
    }
    groupedByController
  }
}
