import bintray.Keys._
import sbt.{Resolver, UpdateLogging}

val PlayVersion = "2.5.4"
val Scala10 = "2.10.5"
val Scala11 = "2.11.8"

val deps = new Dependencies(PlayVersion)

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

lazy val common = (project in file("common"))
  .enablePlugins(BuildInfoPlugin)
  .settings(commonSettings: _*)
  .settings(
//    scalaVersion := Scala11,
//    crossScalaVersions := Seq(Scala10, Scala11),
    name := "play-swagger-common",
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    libraryDependencies ++= deps.jacksons
  )

// This is the API project, it gets added to the runtime dependencies of any
// project using play-swagger
lazy val api = (project in file("api"))
  .enablePlugins(BuildInfoPlugin)
  .settings(commonSettings: _*)
  .settings(
    //    scalaVersion := Scala11,
    //crossScalaVersions := Seq(Scala11),
    name := "play-swagger-api",
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    libraryDependencies ++= deps.api ++ deps.test
  )
  .dependsOn(common)

lazy val swaggerModel = (project in file("swagger-model"))
  .enablePlugins(BuildInfoPlugin)
  .settings(commonSettings: _*)
  .settings(
    name := "swagger-model",
    //scalaVersion := Scala11,
    //crossScalaVersions := Seq(Scala10, Scala11),
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    libraryDependencies ++= deps.swaggerModel ++ deps.test
  )

lazy val apiFirstCore = (project in file("api-first-core"))
  .enablePlugins(BuildInfoPlugin)
  .settings(commonSettings: _*)
  .settings(
    //scalaVersion := Scala11,
    //crossScalaVersions := Seq(Scala11),
    name := "api-first-core",
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    libraryDependencies ++= deps.scala ++ deps.test
  )


lazy val swaggerParser = (project in file("swagger-parser"))
  .enablePlugins(BuildInfoPlugin)
  .settings(commonSettings: _*)
  .settings(
    //scalaVersion := Scala11,
    //crossScalaVersions := Seq(Scala11),
    name := "swagger-parser",
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    libraryDependencies ++= deps.swaggerParser(scalaVersion.value) ++ deps.test
  )
  .dependsOn(swaggerModel, apiFirstCore)

lazy val playScalaGenerator = (project in file("play-scala-generator"))
  .enablePlugins(BuildInfoPlugin)
  .settings(commonSettings: _*)
  .settings(
    //scalaVersion := Scala11,
    //crossScalaVersions := Seq(Scala11),
    name := "play-scala-generator",
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    libraryDependencies ++= deps.playScalaGenerator ++ deps.test
  ).dependsOn(apiFirstCore)

// This is the sbt plugin
lazy val plugin = (project in file("plugin"))
  .enablePlugins(BuildInfoPlugin)
  .settings(commonSettings: _*)
  .settings(scriptedSettings: _*)
  .settings(
    name := "sbt-play-swagger",
    sbtPlugin := true,

    scalaVersion := Scala10,

    addSbtPlugin("com.typesafe.play" % "sbt-plugin" % PlayVersion),

    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),

    scriptedLaunchOpts := {
      scriptedLaunchOpts.value ++
        Seq(
          "-Dproject.version=" + version.value,
          "-Dscala.version=" + scalaVersion.value,
          "-Xmx768M",
          "-XX:ReservedCodeCacheSize=256M"
        )
    },
    scriptedDependencies := {
      val a = (publishLocal in swaggerModel).value
      val b = (publishLocal in api).value
      val c = (publishLocal in apiFirstCore).value
      val d = (publishLocal in swaggerParser).value
      val e = (publishLocal in playScalaGenerator).value
      val f = publishLocal.value
    },
    scriptedBufferLog := false
  )
  .dependsOn(apiFirstCore, playScalaGenerator, swaggerParser, common)

lazy val root = (project in file("."))
  // Use sbt-doge cross building since we have different projects with different scala versions
// .enablePlugins(CrossPerProjectPlugin)
  .settings(commonSettings: _*)
  .settings(
    name := "play-swagger-root"
  )
  .aggregate(common, swaggerModel, api, swaggerParser, apiFirstCore, playScalaGenerator, plugin)

def commonSettings: Seq[Setting[_]] = bintrayPublishSettings ++ Seq(
  ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) },
  ivyLoggingLevel := UpdateLogging.DownloadOnly,
  version := "0.1.11",
  scalaVersion := Scala11,
  sbtPlugin := false,
  buildInfoPackage := "de.zalando",
  organization := "de.zalando",
  fork in(Test, run) := true,
  autoScalaLibrary := true,
  resolvers ++= Seq(
    Resolver.typesafeRepo("releases"),
    Resolver.typesafeIvyRepo("releases"),
    Resolver.bintrayRepo("zalando", "sbt-plugins"),
    "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases",
    "zalando-maven" at "https://dl.bintray.com/zalando/maven",
    "jeffmay" at "https://dl.bintray.com/jeffmay/maven"
  ),
  licenses +=("MIT", url("http://opensource.org/licenses/MIT")),
  publishMavenStyle := false,
  repository in bintray := "sbt-plugins",
  bintrayOrganization in bintray := Some("zalando"),
  scalacOptions ++= Seq(
    "-deprecation",
    "-encoding", "UTF-8", // yes, this is 2 args
    "-feature",
    "-unchecked",
    "-Ywarn-numeric-widen",
    "-Ywarn-value-discard",
    //    "-Xfatal-warnings",
    //    "-Xlint",
    "-Yno-adapted-args",
    "-Xfuture"
  ),
  scalastyleFailOnError := false
) ++ Lint.all


// Apply default Scalariform formatting.
// Reformat at every compile.
// c.f. https://github.com/sbt/sbt-scalariform#advanced-configuration for more options.
// ++ scalariformSettings

// coverageEnabled := false

coverageMinimum := 80

coverageFailOnMinimum := false

coverageHighlighting := {
  if (scalaBinaryVersion.value == "2.10") false
  else false
}

