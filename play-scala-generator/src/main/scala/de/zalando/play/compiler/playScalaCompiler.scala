package de.zalando.play.compiler

import java.io.File

import de.zalando.apifirst.Application.StrictModel
import de.zalando.apifirst.Domain.PrimitiveType
import de.zalando.apifirst.ScalaName
import de.zalando.apifirst.generators.ScalaGenerator
import org.apache.commons.io.FileUtils
import play.routes.compiler.RoutesCompiler.RoutesCompilerTask
import play.routes.compiler.RoutesGenerator

import scala.collection.immutable.::
import scala.io.Codec

case class PlayScalaCompilationTask(definitionFile: File, generator: RoutesGenerator)
/**
 * The compiler
 */
object PlayScalaCompiler {

  type generatorF = (String, String, String) => Seq[String]

  def compileBase(task: PlayScalaCompilationTask, outputDir: File, routesImport: Seq[String],
                  flatAst: StrictModel, customTemplates: Option[String]): CompilationResult = {
    val places = Seq("/model/", "/validators/", "/security/", "/controllers_base/", "/marshallers/")
    val generator = new ScalaGenerator(flatAst, customTemplates).generateBase
    val swaggerFiles = compileSwagger(task, outputDir, places, generator)(flatAst)
    CompilationResult(swaggerFiles)
  }

  def compileTests(task: PlayScalaCompilationTask, outputDir: File, routesImport: Seq[String],
                   flatAst: StrictModel, customTemplates: Option[String]): CompilationResult = {
    val places = Seq("/generators/", "/tests/")
    val generator = new ScalaGenerator(flatAst, customTemplates).generateTest
    val swaggerFiles = compileSwagger(task, outputDir, places, generator)(flatAst)
    CompilationResult(swaggerFiles)
  }

  def compileControllers(task: PlayScalaCompilationTask, outputDir: File, routesImport: Seq[String],
                         flatAst: StrictModel, customTemplates: Option[String]): CompilationResult = {
    val places = Seq("/generated_controllers/")
    val generator = new ScalaGenerator(flatAst, customTemplates).generateControllers
    val swaggerFiles = compileSwagger(task, outputDir, places, generator)(flatAst)
    ControllerCompilationResult(swaggerFiles.flatten)
  }

  def compileMarshallers(task: PlayScalaCompilationTask, outputDir: File, routesImport: Seq[String],
                         flatAst: StrictModel, customTemplates: Option[String]): CompilationResult = {
    val places = Seq("/marshallers/")
    val generator = new ScalaGenerator(flatAst, customTemplates).generateMarshallers
    val swaggerFiles = compileSwagger(task, outputDir, places, generator, overwrite = false)(flatAst)
    ControllerCompilationResult(swaggerFiles.flatten)
  }

  def compileExtractors(task: PlayScalaCompilationTask, outputDir: File, routesImport: Seq[String],
                        flatAst: StrictModel, customTemplates: Option[String]): CompilationResult = {
    val places = Seq("/security/")
    val generator = new ScalaGenerator(flatAst, customTemplates).generateExtractors
    val swaggerFiles = compileSwagger(task, outputDir, places, generator, overwrite = false)(flatAst)
    ControllerCompilationResult(swaggerFiles.flatten)
  }

  def compileRoutes(task: PlayScalaCompilationTask, outputDir: File, routesImport: Seq[String],
                    flatAst: StrictModel, customTemplates: Option[String]): CompilationResult = {
    val routesFiles = compileRoutes(task, outputDir, routesImport)(flatAst)
    CompilationResult(routesFiles)
  }

  private def compileSwagger(task: PlayScalaCompilationTask, outputDir: File, places: Seq[String],
                             generator: generatorF, overwrite: Boolean = true)(implicit flatAst: StrictModel) = {
    val currentCtrlr = readFile(outputDir, fullFileName(Option(task.definitionFile), places.last))
    val packageName = flatAst.packageName.getOrElse(taskPackageName(task))
    val artifacts = places zip generator(task.definitionFile.getName, packageName, currentCtrlr)
    persistFiles(task.definitionFile, outputDir, overwrite, artifacts)
  }

  private def taskPackageName(task: PlayScalaCompilationTask): String =
    ScalaName.scalaPackageName(task.definitionFile.getName)

  private def persistFiles(definitionFile: File, outputDir: File, overwrite: Boolean, artifacts: Seq[(String, String)]): Seq[Seq[File]] = {
    val persister = persist(Option(definitionFile), outputDir, overwrite) _
    val swaggerFiles = artifacts map persister.tupled
    swaggerFiles
  }

  def additionalImports(implicit flatAst: StrictModel): Seq[String] =
    flatAst.typeDefs.values.collect {
      case t: PrimitiveType if t.imports.nonEmpty => t.imports.filterNot(_.contains("_"))
    }.flatten.toSeq

  private def compileRoutes(task: PlayScalaCompilationTask, outputDir: File, routesImport: Seq[String])(implicit flatAst: StrictModel) = {
    val namespace = flatAst.packageName.getOrElse(taskPackageName(task))
    val allImports = ((namespace + "._") +: (additionalImports(flatAst) ++ routesImport)).distinct
    val playNameSpace = Some(namespace)
    val playRules = RuleGenerator.apiCalls2PlayRules(flatAst.calls: _*).toList
    val playTask = RoutesCompilerTask(task.definitionFile, allImports, forwardsRouter = true, reverseRouter = true, namespaceReverseRouter = false)
    val generated = task.generator.generate(playTask, playNameSpace, playRules).filter(_._1.endsWith(".scala"))
    val persister = persist(None, outputDir, overwrite = true) _
    val routesFiles = generated map persister.tupled
    Seq(routesFiles.flatten)
  }

  def persist(definitionFile: Option[File], outputDir: File, overwrite: Boolean)(directory: String, content: String): Seq[File] = {
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

  def readFile(outputDir: File, fileName: String): String = {
    val file = new File(outputDir, fileName)
    if (file.exists && file.canRead)
      FileUtils.readFileToString(file)
    else
      ""
  }
}

/**
 * Results of a compilation
 */
sealed trait CompilationResult {
  def allFiles: Set[File]
}
object CompilationResult {
  def apply(results: Seq[Seq[File]]): CompilationResult with Product with Serializable = results match {
    case model :: validators :: security :: controllers :: formParsers :: Nil =>
      BaseCompilationResult(model, validators, security, controllers, formParsers)
    case testData :: tests :: Nil => TestCompilationResult(testData, tests)
    case routes :: Nil => RoutesCompilationResult(routes)
    case other => throw new IllegalArgumentException("Not recognized: " + other)
  }
}
case class BaseCompilationResult(modelFiles: Seq[File], validatorFiles: Seq[File],
    securityFiles: Seq[File], controllerBaseFiles: Seq[File], formParserFiles: Seq[File]) extends CompilationResult {
  def allFiles: Set[File] = (modelFiles ++ validatorFiles ++ securityFiles ++ controllerBaseFiles ++ formParserFiles).toSet
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