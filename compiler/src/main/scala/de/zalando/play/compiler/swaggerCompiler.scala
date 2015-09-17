package de.zalando.play.compiler

import java.io.File
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TestGenerator
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

    val basePath = Option(swaggerModel.basePath).getOrElse("/")

    val namespace = task.definitionFile.getName.takeWhile(_ != '.')

    val generateFiles = generate(namespace, task, outputDir) _

    // controllers has to be generated first
    // because it's outside of the syncIncremental cycle of the plugin
    val controllerFiles = generateFiles(ControllersGenerator, "../../../../app/controllers/", false)

    val playRules = RuleGenerator.apiCalls2PlayRules(ast.calls: _*).toList

    val playTask = RoutesCompilerTask(task.definitionFile, routesImport,
      forwardsRouter = true, reverseRouter, namespaceReverseRouter = true)

    val generated = task.generator.generate(playTask, Some(namespace), playRules)

    val routesFiles = generated map writeToFile(outputDir, true).tupled

    val model = generateFiles(ModelGenerator, "model/", true)

    val generators = generateFiles(ModelFactoryGenerator, "generators/", true)

    val baseControllers = generateFiles(BaseControllersGenerator, "controllers/", true)

    val validators = generateFiles(ValidatorsGenerator, "validators/", true)

    val tests = generateFiles(new TestsGenerator(basePath), "../../../..//test/", true)

    SwaggerCompilationResult(routesFiles, model, generators, baseControllers, controllerFiles, validators, tests)
  }


  def generate(namespace: String, task: SwaggerCompilationTask, outputDir: File)
      (generator: GeneratorBase, directory: String, writeOver: Boolean)(implicit ast: Model) = {
    val generatedContent = generator.generate(namespace)
    val modelFileName = directory + task.definitionFile.getName + ".scala"
    generatedContent.headOption.map(_._2).map { content =>
      writeToFile(outputDir, writeOver)(modelFileName, content)
    }.toSeq
  }

  def writeToFile(outputDir: File, writeOver: Boolean) = (filename: String, content: String) => {
    val file = new File(outputDir, filename)
    if (writeOver || !file.exists())
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
  validatorFiles: Seq[File],
  testFiles: Seq[File]
) {
  // for incremental sync we do not include controller files
  // because they should be generated only once
  def allFiles: Set[File] = (
    routesFiles ++ modelFiles ++
      testDataGeneratorFiles ++ testFiles ++
      validatorFiles ++ controllerBaseFiles
    ).toSet
}