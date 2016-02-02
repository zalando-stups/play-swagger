package de.zalando.play.swagger.sbt

import sbt.Keys._
import sbt._
import com.typesafe.sbt.web.incremental._
import de.zalando.BuildInfo
import de.zalando.apifirst.ScalaName
import de.zalando.play.compiler.{SwaggerCompilationResult, SwaggerCompilationTask, SwaggerCompiler}
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
    val swagger                   = taskKey[Seq[File]]("Compile swagger definitions")

    // Options for swagger compilation
    val swaggerPlayGenerator      = settingKey[RoutesGenerator]("Play's generator to be used for play routes generation")
    val swaggerKeyPrefix          = settingKey[String]("The key prefix is a name for swagger vendor extension")
    val swaggerTarget             = settingKey[File]("Target folder to save generated files")

    val swaggerDefinitions        = taskKey[Seq[File]]("The swagger definition files")

    val swaggerCompileAll         = taskKey[Seq[File]]("Combines swaggerCompile, swaggerCompileTests and swaggerCompileRoutes")

    val swaggerCompileBase        = taskKey[Seq[File]]("Compile model, validators and controller bases from swagger definitions")
    val swaggerCompileTests       = taskKey[Seq[File]]("Compile test data generators and tests from swagger definitions")
    val swaggerCompileRoutes      = taskKey[Seq[File]]("Compile play routes from swagger definitions")
    val swaggerCompileControllers = taskKey[Seq[File]]("Compile controllers from swagger definitions")


    // By default, end users won't define this themselves, they'll just use the options above for one single
    // swagger definition, however, if they want more control over settings, or what to compile multiple files,
    // they can ignore the above options, and just define this directly.
    val swaggerCompilationTasks = taskKey[Seq[SwaggerCompilationTask]]("The compilation tasks")

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
  ) ++ unscopedSwaggerSettings ++
    baseSwaggerSettings ++
    controllerSwaggerSettings ++
    routesSwaggerSettings ++
    testsSwaggerSettings


  /**
   * We define these unscoped, and then scope later using inConfig, this means we could define different definitions
   * to be compiled in compile and test, for example.
   */
  def unscopedSwaggerSettings: Seq[Setting[_]] = Seq(
    swaggerDefinitions := ((resourceDirectory in Compile).value * "*.yaml").get,
    sources in swagger := swaggerDefinitions.value,
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

    swaggerKeyPrefix :=  "x-api-first",

    swagger := swaggerGenerateAll.value,

    watchSources in Defaults.ConfigGlobal <++= sources in swagger,

    swaggerTarget in Compile := crossTarget.value / "routes" / Defaults.nameForSrc(Compile.name),

    swaggerTarget in Test := crossTarget.value / "routes" / Defaults.nameForSrc(Test.name),

    managedSourceDirectories in Compile += (swaggerTarget in Compile).value,

    managedSourceDirectories in Test += (swaggerTarget in Test).value,

    swaggerPlayGenerator :=  RoutesCompiler.autoImport.routesGenerator.value
  )

  def baseSwaggerSettings: Seq[Setting[_]] = Seq(
    target in swaggerCompileBase := (swaggerTarget in Compile).value,
    swaggerCompileBase := swaggerGenerateBase.value,
    managedSources in Compile ++= swaggerCompileBase.value
  )
  def testsSwaggerSettings: Seq[Setting[_]] = Seq(
    target in swaggerCompileTests := (swaggerTarget in Test).value,
    swaggerCompileTests := swaggerGenerateTests.value,
    managedSources in Test ++= swaggerCompileTests.value
  )
  def routesSwaggerSettings: Seq[Setting[_]] = Seq(
    target in swaggerCompileRoutes := (swaggerTarget in Compile).value,
    swaggerCompileRoutes := swaggerGenerateRoutes.value,
    managedSources in Compile ++= swaggerCompileRoutes.value
  )

  def controllerSwaggerSettings: Seq[Setting[_]] = Seq(
    target in swaggerCompileControllers := (scalaSource in Compile).value,
    swaggerCompileControllers := swaggerGenerateControllers.value
  )

  def swaggerGenerateBase        = commonSwaggerCompile(SwaggerCompiler.compileBase,         swaggerCompileBase)
  def swaggerGenerateTests       = commonSwaggerCompile(SwaggerCompiler.compileTests,        swaggerCompileTests)
  def swaggerGenerateRoutes      = commonSwaggerCompile(SwaggerCompiler.compileRoutes,       swaggerCompileRoutes)
  def swaggerGenerateControllers = commonSwaggerCompile(SwaggerCompiler.compileControllers,  swaggerCompileControllers)

  def swaggerGenerateAll = Def.task {
    swaggerGenerateBase.value ++ swaggerGenerateTests.value ++ swaggerGenerateControllers.value ++ swaggerGenerateRoutes.value
  }

  private def commonSwaggerCompile: ((SwaggerCompilationTask, File, String, Seq[String]) => SwaggerCompilationResult, TaskKey[scala.Seq[sbt.File]]) =>
    Def.Initialize[Task[Seq[File]]] =
    (compiler, config) => Def.task {
      val routesImport      = RoutesCompiler.autoImport.routesImport.value
      val cacheDirectory    = streams.value.cacheDirectory
      val tasks             = swaggerCompilationTasks.value
      val outputDirectory   = (target in config).value

      // Read the detailed scaladoc for syncIncremental to see how it works
      val (products, errors) = syncIncremental(cacheDirectory, tasks) { tasksToRun: Seq[SwaggerCompilationTask] =>
        val results = tasksToRun.map { task =>
          task -> Try { compiler(task, outputDirectory, swaggerKeyPrefix.value, routesImport) }
        }

        // Collect the results into a map of task to OpResult for syncIncremental
        val taskResults: Map[SwaggerCompilationTask, OpResult] = results.map {
          case (task, Success(result)) =>
            task -> OpSuccess(Set(task.definitionFile), result.allFiles)
          case (op, Failure(_)) => op -> OpFailure
        }.toMap

        // Collect the errors for our own error reporting
        val errors = results.collect {
          case (_, Failure(e)) => e
        }
        (taskResults, errors)
      }

      if (errors.nonEmpty) throw errors.head

      // Return all the files that were or have in the past been compiled
      products.to[Seq]
    }

}
