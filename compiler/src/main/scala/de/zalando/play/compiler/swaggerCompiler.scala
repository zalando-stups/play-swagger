package de.zalando.play.compiler

import java.io.File
import java.net.URI

import de.zalando.apifirst.Application.StrictModel
import de.zalando.apifirst.TypeNormaliser
import de.zalando.apifirst.generators.ScalaGenerator
import de.zalando.swagger.strictModel.SwaggerModel
import de.zalando.swagger.{ModelConverter, StrictJsonParser, StrictYamlParser}
import com.oaganalytics.apib.{ApibModel, ApibParser, Generator}
import com.oaganalytics.apib.Decoder._
import org.apache.commons.io.FileUtils
import play.routes.compiler.RoutesCompiler.RoutesCompilerTask
import play.routes.compiler.RoutesGenerator

import scala.collection.immutable.::
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

  def compileBase(task: CompilationTask, outputDir: File, keyPrefix: String, routesImport: Seq[String],
                  flatAst: StrictModel): CompilationResult = {
    val places            = Seq("/model/", "/validators/", "/security/", "/controllers_base/")
    val generator         = new ScalaGenerator(flatAst).generateBase
    val swaggerFiles      = compileSwagger(task, outputDir, places, generator)(flatAst)
    CompilationResult(swaggerFiles)
  }

  def compileTests(task: CompilationTask, outputDir: File, keyPrefix: String, routesImport: Seq[String],
                   flatAst: StrictModel): CompilationResult = {
    val places            = Seq("/generators/", "/tests/")
    val generator         = new ScalaGenerator(flatAst).generateTest
    val swaggerFiles      = compileSwagger(task, outputDir, places, generator)(flatAst)
    CompilationResult(swaggerFiles)
  }

  def compileControllers(task: CompilationTask, outputDir: File, keyPrefix: String, routesImport: Seq[String],
                         flatAst: StrictModel): CompilationResult = {
    val places            = Seq("/generated_controllers/")
    val generator         = new ScalaGenerator(flatAst).generateControllers
    val swaggerFiles      = compileSwagger(task, outputDir, places, generator)(flatAst)
    ControllerCompilationResult(swaggerFiles.flatten)
  }

  def compileMarshallers(task: CompilationTask, outputDir: File, keyPrefix: String, routesImport: Seq[String],
                         flatAst: StrictModel): CompilationResult = {
    val places            = Seq("/marshallers/")
    val generator         = new ScalaGenerator(flatAst).generateMarshallers
    val swaggerFiles      = compileSwagger(task, outputDir, places, generator, overwrite = false)(flatAst)
    ControllerCompilationResult(swaggerFiles.flatten)
  }

  def compileExtractors(task: CompilationTask, outputDir: File, keyPrefix: String, routesImport: Seq[String],
                        flatAst: StrictModel): CompilationResult = {
    val places            = Seq("/security/")
    val generator         = new ScalaGenerator(flatAst).generateExtractors
    val swaggerFiles      = compileSwagger(task, outputDir, places, generator, overwrite = false)(flatAst)
    ControllerCompilationResult(swaggerFiles.flatten)
  }

  def compileRoutes(task: CompilationTask, outputDir: File, keyPrefix: String, routesImport: Seq[String],
                    flatAst: StrictModel): CompilationResult = {
    val routesFiles       = compileRoutes(task, outputDir, routesImport)(flatAst)
    CompilationResult(routesFiles)
  }

  private def compileSwagger(task: CompilationTask, outputDir: File, places: Seq[String], generator: generatorF, overwrite: Boolean = true)
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

  private def compileRoutes(task: CompilationTask, outputDir: File, routesImport: Seq[String])(implicit flatAst: StrictModel) = {
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

sealed trait SpecType
case object Swagger extends SpecType
case object Apib extends SpecType

/**
 * This should capture everything about the compilation of a single file.
 *
 * Incremental compilation will hash the toString of this file and use it as part of its algorithm to determine if
 * anything has changed - this is why, for example, the version is included, so that when you upgrade play swagger, it
 * will trigger a recompile.
 */
trait CompilationTask {
  type Model
  def definitionFile: File
  def packageName: String
  def generator: RoutesGenerator
  def playSwaggerVersion: String
  def specType: SpecType
  def model: Model
  def strictModel: StrictModel
}

object CompilationTask {
  def apply(
    definitionFile: File,
    packageName: String,
    generator: RoutesGenerator,
    playSwaggerVersion: String,
    specType: SpecType
  ): CompilationTask = {
    specType match {
      case Swagger => SwaggerCompilationTask(definitionFile, packageName, generator, playSwaggerVersion)
      case Apib => ApibCompilationTask(definitionFile, packageName, generator, playSwaggerVersion)
    }
  }
}

case class SwaggerCompilationTask(
  definitionFile: File,
  packageName: String,
  generator: RoutesGenerator,
  playSwaggerVersion: String
) extends CompilationTask {
  type Model = (URI, SwaggerModel)
  val specType = Swagger

  lazy val model: (URI, SwaggerModel) = {
    val parser = if (definitionFile.getName.endsWith(".yaml")) StrictYamlParser else StrictJsonParser
    parser.parse(definitionFile)
  }

  lazy val strictModel: StrictModel =
    ModelConverter.fromModel(model._1, model._2, Option(definitionFile))
}

case class ApibCompilationTask(
  definitionFile: File,
  packageName: String,
  generator: RoutesGenerator,
  playSwaggerVersion: String
) extends CompilationTask {
  type Model = ApibModel
  val specType = Apib

  lazy val model =
    ApibParser.noisyParse[ApibModel](scala.io.Source.fromFile(definitionFile).getLines().mkString("\n"))

  lazy val strictModel = Generator.model(model, packageName, "apib")
}

/**
 * Results of a compilation
 */
sealed trait CompilationResult {
  def allFiles: Set[File]
}
object CompilationResult {
  def apply(results: Seq[Seq[File]]) = results match {
    case model :: validators :: security :: controllers :: Nil =>
      BaseCompilationResult(model, validators, security, controllers)
    case testData :: tests :: Nil => TestCompilationResult(testData, tests)
    case routes :: Nil => RoutesCompilationResult(routes)
    case other => throw new IllegalArgumentException("Not recognized: " + other)
  }
}
case class BaseCompilationResult(modelFiles: Seq[File], validatorFiles: Seq[File],
  securityFiles: Seq[File], controllerBaseFiles: Seq[File]) extends CompilationResult {
  def allFiles: Set[File] = (modelFiles ++ validatorFiles ++ securityFiles ++ controllerBaseFiles).toSet
}
case class RoutesCompilationResult(routesFiles: Seq[File]) extends CompilationResult {
  def allFiles: Set[File] = routesFiles.toSet
}
case class ControllerCompilationResult(controllerFiles: Seq[File]) extends CompilationResult {
  def allFiles: Set[File] = controllerFiles.toSet
}
case class TestCompilationResult(testDataGeneratorFiles: Seq[File],
                                        testFiles: Seq[File]) extends CompilationResult {
  def allFiles: Set[File] = (testDataGeneratorFiles ++ testFiles).toSet
}
