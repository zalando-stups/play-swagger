package de.zalando.play.swagger.sbt

import _root_.sbt.Keys._
import _root_.sbt._
import com.typesafe.sbt.web.incremental._
import de.zalando.BuildInfo
import de.zalando.play.compiler.{SwaggerCompilationTask, SwaggerCompiler}
import play.routes.compiler.RoutesGenerator
import play.sbt.routes.RoutesCompiler

import scala.util.{Failure, Success, Try}

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
    val swagger = taskKey[Seq[File]]("Compile swagger definitions")

    // Options for swagger compilation
    val swaggerPackageName = settingKey[String]("The package to which the swagger definition will be compiled")
    val playGenerator = settingKey[RoutesGenerator]("Play's generator to be used for play routes generation")
    val keyPrefix = settingKey[String]("The key prefix is a name for swagger vendor extension")
    val swaggerDefinitions = taskKey[Seq[File]]("The swagger definition files")
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
    swaggerPackageName := "swagger",
    libraryDependencies ++= Seq(
      "de.zalando" %% "play-swagger-api" % BuildInfo.version,
      "com.fasterxml.jackson.dataformat" % "jackson-dataformat-yaml" % "2.4.4",
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.6.1",
      "org.scalacheck" %% "scalacheck" % "1.12.4" % Test,
      "com.typesafe.play" %% "play-test" % play.core.PlayVersion.current % Test
    )
  ) ++ inConfig(Compile)(unscopedSwaggerSettings)

  /**
   * We define these unscoped, and then scope later using inConfig, this means we could define different definitions
   * to be compiled in compile and test, for example.
   */
  def unscopedSwaggerSettings: Seq[Setting[_]] = Seq(

    swaggerDefinitions := (resourceDirectory.value * "*.yaml").get,

    sources in swagger := swaggerDefinitions.value,

    // Now from the list of sources, build the compilation tasks
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

      SwaggerCompilationTask(source, source.getName.takeWhile(_ != '.'), playGenerator.value, version)
    },

    // Target directory
    target in swagger := crossTarget.value / "routes" / Defaults.nameForSrc(configuration.value.name),

    // And the swagger compiler definition
    swagger := swaggerCompile.value,

    watchSources in Defaults.ConfigGlobal <++= sources in swagger,

    managedSources ++= swagger.value.filter(_.getName.endsWith(".scala")),

    managedSourceDirectories <+= target in swagger,

    playGenerator :=  RoutesCompiler.autoImport.routesGenerator.value,

    keyPrefix :=  "x-api-first"
  )

  def swaggerCompile = Def.task {
    val tasks = swaggerCompilationTasks.value
    val cacheDirectory = streams.value.cacheDirectory
    val outputDirectory = (target in swagger).value

    // Read the detailed scaladoc for syncIncremental to see how it works
    val (products, errors) = syncIncremental(cacheDirectory, tasks) { tasksToRun: Seq[SwaggerCompilationTask] =>

      val genRevRoutes = RoutesCompiler.autoImport.generateReverseRouter.value

      val namespaceRevRoutes = RoutesCompiler.autoImport.namespaceReverseRouter.value

      val routesImport = RoutesCompiler.autoImport.routesImport.value

      val results = tasksToRun.map { task =>
        task -> Try {
          SwaggerCompiler.compile(task, outputDirectory, genRevRoutes, namespaceRevRoutes, routesImport, keyPrefix.value)
        }
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

    if (errors.nonEmpty) {
      throw errors.head
    }

    // Return all the files that were or have in the past been compiled
    products.to[Seq]
  }

}
