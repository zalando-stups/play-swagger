package de.zalando.play.compiler

import java.io.File
import java.net.URI

import de.zalando.apifirst.Application.StrictModel
import de.zalando.apifirst.TypeNormaliser
import de.zalando.apifirst.generators.ScalaGenerator
import de.zalando.swagger.strictModel.SwaggerModel
import de.zalando.swagger.{ModelConverter, StrictJsonParser, StrictYamlParser}
import org.apache.commons.io.FileUtils
import play.routes.compiler.RoutesCompiler.RoutesCompilerTask
import play.routes.compiler.RoutesGenerator

import scala.io.Codec

/**
 * The compiler
 */
object SwaggerCompiler {

  type generatorF = (String, String, String) => Seq[String]

  def readSwaggerModel(task: SwaggerCompilationTask): (URI, SwaggerModel) = {
    val parser = if (task.definitionFile.getName.endsWith(".yaml")) StrictYamlParser else StrictJsonParser
    val (uri, model) = parser.parse(task.definitionFile)
    (uri, model)
  }

  def convertModelToAST(definitionFile: File, uri: URI, model: SwaggerModel): StrictModel =
    ModelConverter.fromModel(uri, model, Option(definitionFile))

  def flattenAST(initialAst: StrictModel): StrictModel =
    TypeNormaliser.flatten(initialAst)

  def compileBase(task: SwaggerCompilationTask, outputDir: File, keyPrefix: String, routesImport: Seq[String],
                  flatAst: StrictModel): SwaggerCompilationResult = {
    val places            = Seq("/model/", "/validators/", "/controllers_base/")
    val generator         = new ScalaGenerator(flatAst).generateBase
    val swaggerFiles      = compileSwagger(task, outputDir, places, generator)(flatAst)
    SwaggerCompilationResult(swaggerFiles)
  }

  def compileTests(task: SwaggerCompilationTask, outputDir: File, keyPrefix: String, routesImport: Seq[String],
                   flatAst: StrictModel): SwaggerCompilationResult = {
    val places            = Seq("/generators/", "/tests/")
    val generator         = new ScalaGenerator(flatAst).generateTest
    val swaggerFiles      = compileSwagger(task, outputDir, places, generator)(flatAst)
    SwaggerCompilationResult(swaggerFiles)
  }

  def compileControllers(task: SwaggerCompilationTask, outputDir: File, keyPrefix: String, routesImport: Seq[String],
                         flatAst: StrictModel): SwaggerCompilationResult = {
    val places            = Seq("/generated_controllers/")
    val generator         = new ScalaGenerator(flatAst).generateControllers
    val swaggerFiles      = compileSwagger(task, outputDir, places, generator)(flatAst)
    SwaggerControllerCompilationResult(swaggerFiles.flatten)
  }

  def compileMarshallers(task: SwaggerCompilationTask, outputDir: File, keyPrefix: String, routesImport: Seq[String],
                         flatAst: StrictModel): SwaggerCompilationResult = {
    val places            = Seq("/marshallers/")
    val generator         = new ScalaGenerator(flatAst).generateMarshallers
    val swaggerFiles      = compileSwagger(task, outputDir, places, generator, overwrite = false)(flatAst)
    SwaggerControllerCompilationResult(swaggerFiles.flatten)
  }

  def compileRoutes(task: SwaggerCompilationTask, outputDir: File, keyPrefix: String, routesImport: Seq[String],
                    flatAst: StrictModel): SwaggerCompilationResult = {
    val routesFiles       = compileRoutes(task, outputDir, routesImport)(flatAst)
    SwaggerCompilationResult(routesFiles)
  }

  private def compileSwagger(task: SwaggerCompilationTask, outputDir: File, places: Seq[String], generator: generatorF, overwrite: Boolean = true)
                            (implicit flatAst: StrictModel) = {
    val currentCtrlr  = readFile(outputDir, fullFileName(Option(task.definitionFile), places.last))
    val packageName   = flatAst.packageName.getOrElse(task.packageName)
    val artifacts     = places zip generator(task.definitionFile.getName, packageName, currentCtrlr)
    persistFiles(task.definitionFile, outputDir, overwrite, artifacts)
  }

  private def persistFiles(definitionFile: File, outputDir: File, overwrite: Boolean, artifacts: Seq[(String, String)]): Seq[Seq[File]] = {
    val persister = persist(Option(definitionFile), outputDir, overwrite) _
    val swaggerFiles = artifacts map persister.tupled
    swaggerFiles
  }

  private def compileRoutes(task: SwaggerCompilationTask, outputDir: File, routesImport: Seq[String])(implicit flatAst: StrictModel) = {
    val namespace     = flatAst.packageName.getOrElse(task.packageName)
    val allImports    = ((namespace + "._") +: routesImport).distinct
    val playNameSpace = Some(namespace)
    val playRules     = RuleGenerator.apiCalls2PlayRules(flatAst.calls: _*).toList
    val playTask      = RoutesCompilerTask(task.definitionFile, allImports, forwardsRouter = true, reverseRouter = true, namespaceReverseRouter = false)
    val generated     = task.generator.generate(playTask, playNameSpace, playRules).filter(_._1.endsWith(".scala"))
    val persister     = persist(None, outputDir, overwrite = true) _
    val routesFiles   = generated map persister.tupled
    Seq(routesFiles.flatten)
  }

  def persist(definitionFile: Option[File], outputDir: File, overwrite: Boolean)
              (directory: String, content: String): Seq[File] = {
    val fileName: String = fullFileName(definitionFile, directory)
    val fileContents = readFile(outputDir, fileName)
    val file = new File(outputDir, fileName)
    val canWrite = (overwrite || !file.exists()) && content.trim.nonEmpty
    if (canWrite) {
      if (fileContents != content) writeToFile(file, content)
      Seq(file)
    } else {
      Seq.empty[File]
    }
  }

  def fullFileName(definitionFile: Option[File], directory: String): String =
    directory + definitionFile.map { _.getName + ".scala" }.getOrElse("")

  def writeToFile(file: File, content: String): Unit = {
    val parent = file.getParentFile
    if (parent.exists && parent.isFile) parent.renameTo(new File(parent.getAbsolutePath + ".backup"))
    if (!parent.exists) parent.mkdirs()
    FileUtils.writeStringToFile(file, content, implicitly[Codec].name)
  }

  def readFile(outputDir: File, fileName: String) = {
    val file = new File(outputDir, fileName)
    if (file.exists && file.canRead)
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
 * Results of a compilation
 */
sealed trait SwaggerCompilationResult {
  def allFiles: Set[File]
}
object SwaggerCompilationResult {
  def apply(results: Seq[Seq[File]]) = results match {
    case model :: validators :: controllers :: Nil => SwaggerBaseCompilationResult(model, validators, controllers)
    case testData :: tests :: Nil => SwaggerTestCompilationResult(testData, tests)
    case routes :: Nil => SwaggerRoutesCompilationResult(routes)
    case other => throw new IllegalArgumentException("Not recognized: " + other)
  }
}
case class SwaggerBaseCompilationResult(modelFiles: Seq[File], validatorFiles: Seq[File], controllerBaseFiles: Seq[File]) extends SwaggerCompilationResult {
  def allFiles: Set[File] = (modelFiles ++ validatorFiles ++ controllerBaseFiles).toSet
}
case class SwaggerRoutesCompilationResult(routesFiles: Seq[File]) extends SwaggerCompilationResult {
  def allFiles: Set[File] = routesFiles.toSet
}
case class SwaggerControllerCompilationResult(controllerFiles: Seq[File]) extends SwaggerCompilationResult {
  def allFiles: Set[File] = controllerFiles.toSet
}
case class SwaggerTestCompilationResult(testDataGeneratorFiles: Seq[File], testFiles: Seq[File]) extends SwaggerCompilationResult {
  def allFiles: Set[File] = (testDataGeneratorFiles ++ testFiles).toSet
}