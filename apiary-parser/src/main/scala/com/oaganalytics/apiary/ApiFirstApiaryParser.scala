package com.oaganalytics.apiary

import java.io.File

import com.oaganalytics.apib.{ApibModel, ApibParser, Generator}
import de.zalando.BuildInfo
import de.zalando.apifirst.Application.StrictModel
import de.zalando.apifirst.ScalaName
import sbt.Keys._
import sbt.{Defaults, _}

/**
  * @since 01.07.2016.
  */
//noinspection ScalaStyle
object ApiFirstApiaryParser extends AutoPlugin {

  object autoImport {
    lazy val apiaryDefinitions = taskKey[Seq[File]]("The swagger definition files")
  }

  lazy val apiarySpec2Ast = taskKey[Seq[(File,StrictModel)]]("Convert API specifications (swaggerDefinitions) to AST")
  private lazy val drafterVersion = settingKey[String]("Checks presence of the drafter command in the path by querying it's version")

  override def trigger: PluginTrigger = noTrigger

  import autoImport._
  import scala.language.postfixOps

  override def projectSettings = Seq(
    libraryDependencies ++= Seq(
      "de.zalando" %% "play-swagger-api" % BuildInfo.version,
      "io.argonaut" %% "argonaut" % "6.1",
      "org.scalacheck" %% "scalacheck" % "1.12.4" % Test
    )
  ) ++ inConfig(Compile)(apibParserSettings)

  private def apibParserSettings: Seq[Setting[_]] = Seq(

    sourcePositionMappers := Seq(),

    drafterVersion := ("drafter -v" !!),

    apiaryDefinitions := {
      val tmpDir = streams.value.cacheDirectory / "apij"
      tmpDir.mkdirs()
      val cache = apibCache(tmpDir)
      val files = ((resourceDirectory in Compile).value * "*.apib").get.toSet
      cache(files).get
    },

    watchSources in Defaults.ConfigGlobal <++= sources in apiaryDefinitions,

    apiarySpec2Ast in Defaults.ConfigGlobal <<= apiaryDefinitions map { files => files.map { file =>
      import com.oaganalytics.apib.Decoder._
      val model = ApibParser.noisyParse[ApibModel](scala.io.Source.fromFile(file).getLines().mkString("\n"))
      val packageName = ScalaName.scalaPackageName(file.getName)
      val strictModel = Generator.model(model, packageName, "apib")
      file -> strictModel
    }}
  )

  private def apibCache(tmpDir: File) = {
    FileFunction.cached(tmpDir, inStyle = FilesInfo.hash) {(set: Set[File]) =>
      set.map({ apib =>
        val output = tmpDir / s"${apib.base}.apij"
        val exitCode = (apib #> "drafter -f json" #> output).!
        if (exitCode !=  0) {
          throw new MessageOnlyException(s"Apiary parser error.")
        }
        output
      })
    }
  }
}