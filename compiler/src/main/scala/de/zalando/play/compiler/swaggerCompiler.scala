package de.zalando.play.compiler

import java.io.File
import de.zalando.apifirst.Application.Model
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

    implicit val ast = Swagger2Ast.convert(keyPrefix)(swaggerModel)

    val playRules = RuleGenerator.apiCalls2PlayRules(ast.calls: _*).toList

    val playTask = RoutesCompilerTask(task.definitionFile, routesImport,
      forwardsRouter = true, reverseRouter, namespaceReverseRouter = true)

    val namespace = task.definitionFile.getName.takeWhile(_ != '.')

    val generated = task.generator.generate(playTask, Some(namespace), playRules)

    val routesFiles = generated map writeToFile(outputDir).tupled

    val generateFiles = generate(namespace, task, outputDir) _

    val model = generateFiles(ModelGenerator, "model/")

    val generators = generateFiles(ModelFactoryGenerator, "generators/")

    val controllerFiles = generateFiles(ControllersGenerator, "controllers/")

    // FIXME generate others as well
    SwaggerCompilationResult(routesFiles, model, generators, Nil, controllerFiles, Nil)
  }


  def generate(namespace: String, task: SwaggerCompilationTask, outputDir: File)
      (generator: GeneratorBase, directory: String)(implicit ast: Model) = {
    val generatedContent = generator.generate(namespace)
    val modelFileName = directory + task.definitionFile.getName + ".scala"
    val result = writeToFile(outputDir)(modelFileName, generatedContent.head._2)
    Seq(result)
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
case class SwaggerCompilationTask(
  definitionFile: File,
  packageName: String,
  generator: RoutesGenerator,
  playSwaggerVersion: String)

/**
 * The result of a compilation
 */
case class SwaggerCompilationResult(
  routesFiles: Seq[File],
  modelFiles: Seq[File],
  testDataGeneratorFiles: Seq[File],
  controllerBaseFiles: Seq[File],
  controllerFiles: Seq[File],
  testFiles: Seq[File]
) {
  def allFiles: Set[File] = (
    routesFiles ++ modelFiles ++
      testDataGeneratorFiles ++ testFiles ++
      controllerFiles ++ controllerBaseFiles
    ).toSet
}