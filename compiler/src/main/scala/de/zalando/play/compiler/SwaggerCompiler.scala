package de.zalando.play.compiler

import java.io.File
import de.zalando.swagger.{Swagger2Ast, JsonParser, YamlParser}
import org.apache.commons.io.FileUtils
import play.routes.compiler.RoutesCompiler.RoutesCompilerTask
import play.routes.compiler.RoutesGenerator

import scala.io.Codec

/**
 * The compiler
 */
object SwaggerCompiler {

  def compile(task: SwaggerCompilationTask, outputDir: File,
              reverseRouter: Boolean, namespaceReverseRouter: Boolean, routesImport: Seq[String], keyPrefix: String): SwaggerCompilationResult = {

    outputDir.mkdirs()

    val parser = if (task.definitionFile.getName.endsWith(".yaml")) YamlParser else JsonParser

    val swaggerModel = parser.parse(task.definitionFile)

    val ast = Swagger2Ast.convert(keyPrefix)(swaggerModel)

    val playRules = RuleGenerator.apiCalls2PlayRules(ast.calls:_*).toList

    val playTask = RoutesCompilerTask(task.definitionFile, routesImport,
      forwardsRouter = true, reverseRouter, namespaceReverseRouter = true)

    val namespace = Some(task.definitionFile.getName.takeWhile(_ != '.'))

    val generated = task.generator.generate(playTask, namespace, playRules)

    val routesFiles = generated map writeToFile(outputDir).tupled

    implicit val definitions = ast.definitions

    val model = ModelGenerator.generate(namespace)

    val modelFileName = "model/" + task.definitionFile.getName + ".scala"

    val modelFile = writeToFile(outputDir)(modelFileName, model)

    SwaggerCompilationResult(routesFiles, Seq(modelFile))
  }

  def writeToFile(outputDir: File) = (filename: String, content: String) => {
    val file = new File(outputDir, filename)
    FileUtils.writeStringToFile(file, content, implicitly[Codec].name)
    file
  }
}

/**
 * This should capture everything about the compilation of a single file.
 *
 * Incremental compilation will hash the toString of this file and use it as part of its algorithm to determine if
 * anything has changed - this is why, for example, the version is included, so that when you upgrade play swagger, it
 * will trigger a recompile.
 */
case class SwaggerCompilationTask(definitionFile: File, packageName: String, generator: RoutesGenerator, playSwaggerVersion: String)

/**
 * The result of a compilation
 */
case class SwaggerCompilationResult(routesFiles: Seq[File], modelFiles: Seq[File])