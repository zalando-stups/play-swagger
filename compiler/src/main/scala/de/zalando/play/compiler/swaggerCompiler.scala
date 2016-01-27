package de.zalando.play.compiler

import java.io.File

import de.zalando.apifirst.Application.StrictModel
import de.zalando.apifirst.TypeNormaliser
import de.zalando.apifirst.generators.ScalaGenerator
import de.zalando.swagger.{ModelConverter, StrictJsonParser, StrictYamlParser}
import org.apache.commons.io.FileUtils
import play.routes.compiler.RoutesCompiler.RoutesCompilerTask
import play.routes.compiler.RoutesGenerator

import scala.io.Codec

/**
 * The compiler
 */
object SwaggerCompiler {

  val controllerDir = "app/controllers/"

  def compileSpec(task: SwaggerCompilationTask, outputDir: File, routesImport: Seq[String], keyPrefix: String): SwaggerCompilationResult = {
    implicit val flatAst          = readFlatAst(task)
    val swaggerFiles              = compileSwagger(task, outputDir)
    SwaggerCompilationResult.fromSwagger(swaggerFiles)
  }

  def compileConf(task: SwaggerCompilationTask, outputDir: File, routesImport: Seq[String], keyPrefix: String): SwaggerCompilationResult = {
    implicit val flatAst  = readFlatAst(task)
    val routesFiles       = compileRoutes(task, outputDir, routesImport)
    SwaggerCompilationResult.fromRoutes(routesFiles)
  }

  private def compileSwagger(task: SwaggerCompilationTask, outputDir: File)(implicit flatAst: StrictModel) = {
    val places        = Seq("model/", "generators/", "validators/", "controllers_base/", "../../../../test/", "../../../../" + controllerDir)
    val generator     = new ScalaGenerator(flatAst)
    val currentCtrlr  = readFile(outputDir, fullFileName(task, places.last))
    val packageName   = flatAst.packageName.getOrElse(task.packageName)
    val artifacts     = generator.generate(task.definitionFile.getName, packageName, currentCtrlr) zip places
    val persister     = persist(task, outputDir) _
    val swaggerFiles  = artifacts map { persister.tupled } map { Seq(_) }
    swaggerFiles
  }

  private def compileRoutes(task: SwaggerCompilationTask, outputDir: File, routesImport: Seq[String])(implicit flatAst: StrictModel) = {
    val namespace     = flatAst.packageName.getOrElse(task.packageName)
    val allImports    = ((namespace + "._") +: routesImport).distinct
    val playNameSpace = Some(namespace)
    val playRules     = RuleGenerator.apiCalls2PlayRules(flatAst.calls: _*).toList
    val playTask      = RoutesCompilerTask(task.definitionFile, allImports, forwardsRouter = true, reverseRouter = true, namespaceReverseRouter = false)
    val generated     = task.generator.generate(playTask, playNameSpace, playRules)
    val routesFiles   = generated map writeToFile(outputDir).tupled
    routesFiles
  }


  def readFlatAst(task: SwaggerCompilationTask): StrictModel = {
    val parser = if (task.definitionFile.getName.endsWith(".yaml")) StrictYamlParser else StrictJsonParser
    val (uri, model) = parser.parse(task.definitionFile)
    val initialAst = ModelConverter.fromModel(uri, model, Option(task.definitionFile))
    implicit val flatAst = TypeNormaliser.flatten(initialAst)
    flatAst
  }

  def persist(task: SwaggerCompilationTask, outputDir: File)
              (content: String, directory: String)(implicit ast: StrictModel) = {
    val fileName: String = fullFileName(task, directory)
    writeToFile(outputDir)(fileName, content)
  }

  def fullFileName(task: SwaggerCompilationTask, directory: String): String =
    directory + task.definitionFile.getName + ".scala"

  def writeToFile(outputDir: File) = (filename: String, content: String) => {
    val file = new File(outputDir, filename)
    val parent = file.getParentFile
    if (parent.exists && parent.isFile) parent.renameTo(new File(parent.getAbsolutePath + ".backup"))
    if (!parent.exists) parent.mkdirs()
    FileUtils.writeStringToFile(file, content, implicitly[Codec].name)
    file
  }

  def readFile(outputDir: File, fileName: String) = {
    val file = new File(outputDir, fileName)
    if (file.exists() && file.canRead)
      FileUtils.readFileToString(file)
    else
      ""
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
  validatorFiles: Seq[File],
  controllerBaseFiles: Seq[File],
  testFiles: Seq[File],
  controllerFiles: Seq[File]
) {

  def allFiles: Set[File] = (
    routesFiles ++ modelFiles ++
      testDataGeneratorFiles ++ testFiles ++
      validatorFiles ++ controllerBaseFiles
      // ++ controllerFiles
    ).toSet
}
object SwaggerCompilationResult {
  def fromRoutes(routes: Seq[File]) =
    new SwaggerCompilationResult(routes, Nil, Nil, Nil, Nil, Nil, Nil)

  def fromSwagger(files: Seq[Seq[File]]) =
    new SwaggerCompilationResult(Nil, files.head, files(1), files(2), files(3), files(4), files(5))

  def apply(files: Seq[File]*) =
    new SwaggerCompilationResult(files.head, files(1), files(2), files(3), files(4), files(5), files(6))
}