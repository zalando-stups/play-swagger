package de.zalando.play.swagger.sbt

import sbt.Keys._
import sbt._
import com.typesafe.sbt.web.incremental._
import de.zalando.BuildInfo
import de.zalando.apifirst.Application.StrictModel
import de.zalando.apifirst.ScalaName
import de.zalando.play.compiler.{SwaggerCompilationResult, SwaggerCompilationTask, SwaggerCompiler}
import de.zalando.swagger.strictModel.SwaggerModel
import play.routes.compiler.RoutesGenerator
import play.sbt.routes.RoutesCompiler

import scala.util._

/**
 * @since 28.07.2015
 */
object PlaySwagger extends AutoPlugin {

  // Anything in this object will automatically be available in all *.sbt files
  object autoImport {

    // sbt convention is that all tasks/settings use camel case, and should be prefixed with a name that's unique
    // to the plugin.

    // taskKey is actually a macro, it will ensure that the name of the task is the same as the name of the field,
    // ie, the name will be "swagger" because the field is "swagger".  This is sbt convention, and means that the
    // same name will be used in .sbt files as will be used on the command line.
    lazy val swagger                   = taskKey[Seq[File]]("Compile swagger definitions")

    // Options for swagger compilation
    lazy val swaggerPlayGenerator      = settingKey[RoutesGenerator]("Play's generator to be used for play routes generation")
    lazy val swaggerKeyPrefix          = settingKey[String]("The key prefix is a name for swagger vendor extension")
    lazy val swaggerTarget             = settingKey[File]("Target folder to save generated files")

    lazy val swaggerAutogenerateControllers = settingKey[Boolean]("Auto - generate swagger controllers")

    lazy val swaggerDefinitions        = taskKey[Seq[File]]("The swagger definition files")

    lazy val swaggerBase               = taskKey[Seq[File]]("Generate model, validators and controller bases from swagger definitions")
    lazy val swaggerRoutes             = taskKey[Seq[File]]("Generate play routes from swagger definitions")
    lazy val swaggerTests              = taskKey[Seq[File]]("Generate test data generators and tests from swagger definitions")
    lazy val swaggerControllers        = taskKey[Seq[File]]("Generate controllers from swagger definitions")
    lazy val swaggerMarshallers        = taskKey[Seq[File]]("Generate marshallers from swagger definitions")

    // By default, end users won't define this themselves, they'll just use the options above for one single
    // swagger definition, however, if they want more control over settings, or what to compile multiple files,
    // they can ignore the above options, and just define this directly.
    lazy val swaggerCompilationTasks    = taskKey[Seq[SwaggerCompilationTask]]("The compilation tasks")

    lazy val swaggerParseSpec           = taskKey[Seq[(java.net.URI, SwaggerModel)]]("Parse API specifications (swaggerDefinitions)")
    lazy val swaggerSpec2Ast            = taskKey[Seq[StrictModel]]("Convert API specifications (swaggerDefinitions) to AST")
    lazy val swaggerFlattenAst          = taskKey[Seq[StrictModel]]("Prepares AST by removing duplicate types and flattening it")

    lazy val swaggerRawData             = taskKey[Seq[(SwaggerCompilationTask, StrictModel)]]("Pairs compilation tasks with raw model to be used for debugging purposes")
    lazy val swaggerPreparedData        = taskKey[Seq[(SwaggerCompilationTask, StrictModel)]]("Pairs compilation tasks with models to prepare them for code generation")

    lazy val swaggerPrintRawAstTypes        = taskKey[Seq[Unit]]("Prints AST type information before type optimisation")
    lazy val swaggerPrintRawAstParameters   = taskKey[Seq[Unit]]("Prints AST parameter information before type optimisation")

    lazy val swaggerPrintFlatAstTypes       = taskKey[Seq[Unit]]("Prints AST type information before after optimisation")
    lazy val swaggerPrintFlatAstParameters  = taskKey[Seq[Unit]]("Prints AST parameter information after type optimisation")

  }

  // Users have to explicitly enable it
  override def trigger = noTrigger

  override def requires = RoutesCompiler

  import autoImport._

  override def projectSettings = Seq(
    libraryDependencies ++= Seq(
      "de.zalando" %% "play-swagger-api" % BuildInfo.version,
      "com.fasterxml.jackson.dataformat" % "jackson-dataformat-yaml" % "2.4.4",
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.6.1",
      "org.scalacheck" %% "scalacheck" % "1.12.4" % Test,
      "com.typesafe.play" %% "play-test" % play.core.PlayVersion.current % Test
    )
  ) ++ Seq(
    managedSourceDirectories in Compile += crossTarget.value / "routes" / Defaults.nameForSrc(Compile.name),
    managedSourceDirectories in Test += crossTarget.value / "routes" / Defaults.nameForSrc(Test.name)
  ) ++ inConfig(Compile)(rawSwaggerSettings) ++
    inConfig(Test)(rawSwaggerSettings) ++
    inConfig(Compile)(swaggerBaseSettings) ++
    inConfig(Compile)(swaggerMarshallerSettings) ++
    inConfig(Compile)(swaggerControllerSettings) ++
    inConfig(Compile)(swaggerRoutesSettings) ++
    inConfig(Test)(swaggerTestSettings)

  /**
   * We define these unscoped, and then scope later using inConfig, this means we could define different definitions
   * to be compiled in compile and test, for example.
   */
  def rawSwaggerSettings: Seq[Setting[_]] = Seq(

    swaggerAutogenerateControllers := true,

    swaggerPlayGenerator  :=  RoutesCompiler.autoImport.routesGenerator.value,

    swaggerKeyPrefix      :=  "x-api-first",

    swaggerDefinitions    := ((resourceDirectory in Compile).value * "*.yaml").get,

    sources in swagger    := swaggerDefinitions.value,

    swaggerCompilationTasks := (sources in swagger).value.map { source =>
      val version = if (BuildInfo.version.endsWith("-SNAPSHOT")) {
        // This essentially disables incremental compilation if we're using a SNAPSHOT version of the swagger plugin.
        // You may want to get rid of this eventually, but during development, it's going to make things a lot nicer,
        // since otherwise you'll need to do a clean every time you update the plugin if you want to regenerate the
        // sources
        BuildInfo.version + System.currentTimeMillis()
      } else {
        BuildInfo.version
      }
      SwaggerCompilationTask(source, ScalaName.scalaPackageName(source.getName), swaggerPlayGenerator.value, version)
    },

    watchSources in Defaults.ConfigGlobal <++= sources in swagger,

    swagger := swaggerRoutes.value,

    swaggerParseSpec  <<= swaggerCompilationTasks map { t => t.map(SwaggerCompiler.readSwaggerModel) },

    swaggerSpec2Ast   <<= (swaggerCompilationTasks, swaggerParseSpec) map { (t, s) =>
      s.zip(t) map { case ((uri, model), task) => SwaggerCompiler.convertModelToAST(task.definitionFile, uri, model) }
    },

    swaggerFlattenAst <<= swaggerSpec2Ast map { t => t.map(SwaggerCompiler.flattenAST) },

    swaggerRawData <<= (swaggerCompilationTasks, swaggerSpec2Ast) map { _ zip _ },

    swaggerPreparedData <<= (swaggerCompilationTasks, swaggerFlattenAst) map { _ zip _ },

    swaggerPrintRawAstTypes <<= (swaggerRawData, streams) map prettyPrint(SwaggerPrettyPrinter.types),
    swaggerPrintRawAstParameters <<= (swaggerRawData, streams) map prettyPrint(SwaggerPrettyPrinter.parameters),

    swaggerPrintFlatAstTypes <<= (swaggerPreparedData, streams) map prettyPrint(SwaggerPrettyPrinter.types),
    swaggerPrintFlatAstParameters <<= (swaggerPreparedData, streams) map prettyPrint(SwaggerPrettyPrinter.parameters)

  )

  def prettyPrint(printer: (SwaggerCompilationTask, StrictModel) => Seq[String]):
    (Types.Id[Seq[(SwaggerCompilationTask, StrictModel)]], Types.Id[TaskStreams]) => Seq[Unit] = {
    case (r, s) => r map { case (a,b) => printer(a, b) } flatMap { _ map { m => s.log.info(m) } }
  }

  def swaggerBaseSettings: Seq[Setting[_]] = Seq(
    target in swaggerBase := crossTarget.value / "routes" / Defaults.nameForSrc(Compile.name),
    swaggerBase := swaggerGenerateBase.value,
    managedSources ++= swaggerBase.value
  )
  def swaggerTestSettings: Seq[Setting[_]] = Seq(
    target in swaggerTests := crossTarget.value / "routes" / Defaults.nameForSrc(Test.name),
    swaggerTests := {
      val rout = swaggerRoutes.value
      swaggerGenerateTests.value
    },
    managedSources ++= swaggerTests.value
  )
  def swaggerControllerSettings: Seq[Setting[_]] = Seq(
    target in swaggerControllers := scalaSource.value,
    swaggerControllers := {
      val comp = swaggerBase.value
      swaggerGenerateControllers.value
    }
    // managedSources in Compile in swaggerControllers ++= swaggerControllers.value
  )
  def swaggerMarshallerSettings: Seq[Setting[_]] = Seq(
    target in swaggerMarshallers := scalaSource.value,
    swaggerMarshallers := {
      val comp = swaggerBase.value
      swaggerGenerateMarshallers.value
    }
  )
  def swaggerRoutesSettings: Seq[Setting[_]] = Seq(
    target in swaggerRoutes := crossTarget.value / "routes" / Defaults.nameForSrc(Compile.name),
    swaggerRoutes := {
      if (swaggerAutogenerateControllers.value) {
        val cont = swaggerControllers.value
      }
      swaggerGenerateRoutes.value
    },
    managedSources ++= swaggerRoutes.value
  )

  val swaggerGenerateBase         = commonSwaggerCompile(SwaggerCompiler.compileBase, swaggerBase, swaggerPreparedData)
  val swaggerGenerateRoutes       = commonSwaggerCompile(SwaggerCompiler.compileRoutes, swaggerRoutes, swaggerPreparedData)
  val swaggerGenerateTests        = commonSwaggerCompile(SwaggerCompiler.compileTests, swaggerTests, swaggerPreparedData)
  val swaggerGenerateControllers  = commonSwaggerCompile(SwaggerCompiler.compileControllers, swaggerControllers, swaggerPreparedData)
  val swaggerGenerateMarshallers  = commonSwaggerCompile(SwaggerCompiler.compileMarshallers, swaggerMarshallers, swaggerPreparedData)

  private def commonSwaggerCompile: (
    (
      SwaggerCompilationTask, File, String, Seq[String], StrictModel) => SwaggerCompilationResult,
      TaskKey[scala.Seq[sbt.File]],
      TaskKey[scala.Seq[(SwaggerCompilationTask, StrictModel)]]
    ) =>
    Def.Initialize[Task[Seq[File]]] =
    (compiler, config, tmPairs) => Def.task {
      val routesImport      = RoutesCompiler.autoImport.routesImport.value
      val cacheDirectory    = streams.value.cacheDirectory
      val taskModelPairs    = tmPairs.value
      val outputDirectory   = (target in config).value

      // Read the detailed scaladoc for syncIncremental to see how it works
      val (products, errors) = syncIncremental(cacheDirectory, taskModelPairs) { taskAndModel: Seq[(SwaggerCompilationTask, StrictModel)] =>
        val results = taskAndModel map { case (task, model) =>
          (task, model) -> Try { compiler(task, outputDirectory, swaggerKeyPrefix.value, routesImport, model) }
        }
        // Collect the results into a map of task to OpResult for syncIncremental
        val taskResults: Map[(SwaggerCompilationTask, StrictModel), OpResult] = results.map {
          case ((task, model), Success(result)) =>
            (task, model) -> OpSuccess(Set(task.definitionFile), result.allFiles)
          case (op, Failure(_)) => op -> OpFailure
        }.toMap

        // Collect the errors for our own error reporting
        val errors = results.collect {
          case (_, Failure(e)) => e
        }
        (taskResults, errors)
      }
      if (errors.nonEmpty)
        throw errors.head
      else
        products.to[Seq]
    }
}
