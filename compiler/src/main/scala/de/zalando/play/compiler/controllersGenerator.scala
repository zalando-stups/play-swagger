package de.zalando.play.compiler

import de.zalando.apifirst.Application.{ApiCall, Model}
import de.zalando.apifirst.Domain.TypeName

/**
 * @since 09.09.2015
 */
object ControllersGenerator extends ControllersGenerator

trait ControllersGenerator extends CallsGeneratorBase {

  def generate(namespace: String)(implicit ast: Model): Iterable[(Set[String], String)] = {
    for {
      (pkg, pkgFiles) <- groupByFile(ast)
      (controller, calls) <- pkgFiles
    } yield {
      val params = calls.flatMap(_.handler.parameters)
      val imports = params.flatMap { p => p.typeName.imports ++ p.typeName.nestedTypes.flatMap(_.imports) }.toSet
      val typeImports = ImportsGenerator.importStatements(ast, "definitions", Some(namespace))
      val methods = calls map toMethod map {
        _.trim.split("\n").map(PAD + _).mkString("\n", "\n", "\n")
      } mkString ""

      val fileContent = s"""package $namespace.$pkg
         |
         |${imports.map{i => "import " + i }.mkString("\n")}
         |$typeImports
         |
         |class $controller extends ${controller}Base {
         |$methods
         |}""".stripMargin

      (Set(pkg + "." + controller), fileContent)
    }
  }

  def toMethod(call: ApiCall) =
    s"""
       |// handler for ${call.verb.name} ${call.path}
       |def ${call.handler.method} = ${call.handler.method}Action { in : (${parameterTypes(call)}) =>
       | val (${parameterNames(call)}) = in
       |$PAD???
       |}
       |
     """.stripMargin

  def parameterNames(call: ApiCall) = {
    if (call.handler.parameters.isEmpty) ""
    else call.handler.parameters.map { p => s"${p.name}"}.mkString(", ")
  }

  def parameterTypes(call: ApiCall) = {
    if (call.handler.parameters.isEmpty) ""
    else call.handler.parameters.map { p => TypeName.escapeName(p.typeName.name.asSimpleType) } mkString ", "
  }

  override def placeHolder: String = ""
}
