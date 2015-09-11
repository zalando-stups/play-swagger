package de.zalando.play.compiler

import de.zalando.apifirst.Application.{ApiCall, Model}

/**
 * @since 09.09.2015
 */
object ControllersGenerator extends GeneratorBase  {

  def groupByFile(ast: Model): Map[String, Map[String, Seq[ApiCall]]] = {
    val groupedByController = ast.calls.groupBy { call =>
      call.handler.packageName
    } map { case ((pkg, calls)) =>
      pkg -> calls.groupBy(_.handler.controller)
    }
    groupedByController
  }

  def generate(namespace: String)(implicit ast: Model): Iterable[(Set[String], String)] = {
    for {
      (pkg, pkgFiles) <- groupByFile(ast)
      (controller, calls) <- pkgFiles
    } yield {
      val params = calls.flatMap(_.handler.parameters)
      val imports = params.flatMap { _.typeName.imports }.toSet
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
       |def ${call.handler.method} = ${call.handler.method}Action { (${params(call)}) =>
       |$PAD???
       |}
       |
     """.stripMargin

  def params(call: ApiCall) = {
    (call.handler.allParameters).map { p =>
      s"""${p.name}: ${p.typeName.name.asSimpleType}"""
    }.mkString(", ")
  }

  override def placeHolder: String = ""
}
