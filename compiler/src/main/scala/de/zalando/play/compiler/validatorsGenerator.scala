package de.zalando.play.compiler

import de.zalando.apifirst.Application.{Parameter, ApiCall, Model}
import de.zalando.apifirst.Domain.TypeName

/**
 * @since 10.09.2015
 */
object ValidatorsGenerator extends ChainedGenerator  {

  override val mainTemplate = s"""
                   |#PACKAGE#
                   |
                   |import play.api.mvc.{Action, Controller}
                   |import de.zalando.play.controllers.PlayBodyParsing
                   |import PlayBodyParsing._
                   |
                   |#IMPORT#
                   |
                   |/**
                   | * @since #NOW#
                   | */
                   |""".stripMargin

  override val namespaceTemplate = "#VALIDATORS#"

  override def generators: List[GeneratorBase] = new DummyValidatorsGenerator :: Nil
}

class DummyValidatorsGenerator extends GeneratorBase {
  override val placeHolder = "#VALIDATORS#"

  val template =
    """
      | class ValidationFor#CONTROLLER##CALL#(#VALIDATOR_PARAMS#) {
      |   val result = Right((#VALIDATOR_RESULT#))
      |  }
    """.stripMargin

  def validatorParameters(parameters: Seq[Parameter]) =
    if (parameters.isEmpty) ""
    else parameters.map { p =>
      s"${p.name}: ${TypeName.escapeName(p.typeName.name.asSimpleType)}"
    } mkString ", "

  def validatorResult(parameters: Seq[Parameter]) =
    if (parameters.isEmpty) ""
    else parameters.map { _.name } mkString ", "

  override def generate(namespace: String)(implicit ast: Model): Iterable[(Set[String], String)] = {
    val result = for {
      (pkg, pkgFiles) <- ControllersGenerator.groupByFile(ast)
      (controller, calls) <- pkgFiles
    } yield {
      val params = calls.flatMap(_.handler.parameters)
      val imports = params.flatMap {_.typeName.imports}.toSet
      val methods = calls map { call =>
        template.
          replaceAll("#VALIDATOR_PARAMS#", validatorParameters(call.handler.allParameters)).
          replaceAll("#VALIDATOR_RESULT#", validatorResult(call.handler.allParameters)).
          replaceAll("#CALL#", call.handler.method)
      }
      val typeImports = ImportsGenerator.importStatements(ast, "definitions", None)

      val body = s"""
      |#TYPE_IMPORTS#
      |package $pkg {
      |${methods.mkString("\n")}
      |}""".stripMargin.
        replaceAll("#CONTROLLER#", controller).
        replaceAll("#TYPE_IMPORTS#", typeImports)
      (imports, body)
    }
    result.toSeq
  }

}