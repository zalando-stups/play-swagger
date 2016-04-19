import bintray.Keys._

val PlayVersion  = "2.4.3"
val ScalaVersion = "2.11.7"

// This is the API project, it gets added to the runtime dependencies of any
// project using play-swagger
lazy val api = (project in file("api"))
  .settings(common: _*)
  .settings(
    name := "play-swagger-api",
    libraryDependencies ++= Seq(
      "com.fasterxml.jackson.core" % "jackson-databind" % "2.4.4",
      "com.fasterxml.jackson.dataformat" % "jackson-dataformat-yaml" % "2.4.4",
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.6.1",
      "com.fasterxml.jackson.dataformat" % "jackson-dataformat-csv" % "2.6.4",
      "joda-time" % "joda-time" % "2.9.1",
      "com.typesafe.play" %% "play" % PlayVersion % Provided,
      "com.typesafe.play" %% "play-java-ws" % PlayVersion,
      "org.scalacheck" %% "scalacheck" % "1.12.4",
      "org.specs2" %% "specs2-scalacheck" % "3.6",
      "me.jeffmay" %% "play-json-tests" % "1.3.0"
    ),
    scalaVersion :=  "2.10.5",
    crossScalaVersions := Seq(scalaVersion.value, ScalaVersion),
    resolvers ++= Seq(
      "jeffmay" at "https://dl.bintray.com/jeffmay/maven"
    )
  )

// This is the compiler, it does compilation of swagger definitions,
// but has no dependency on sbt, so, for example, someone could use it to
// implement gradle support
lazy val compiler = (project in file("compiler"))
  .enablePlugins(SbtTwirl)
  .settings(common: _*)
  .dependsOn(api)
  .settings(
    name := "play-swagger-compiler",
    scalaVersion := "2.10.5",
    libraryDependencies ++= Seq(
      "com.fasterxml.jackson.core" % "jackson-databind" % "2.4.4",
      "com.fasterxml.jackson.dataformat" % "jackson-dataformat-yaml" % "2.4.4",
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.6.1",
      "org.scalatest" %% "scalatest" % "2.2.3" % "test",
      "com.typesafe.play" %% "routes-compiler" % PlayVersion % Provided,
      "com.typesafe.play" %% "play" % PlayVersion % Provided,
      "org.scala-lang" % "scala-compiler" % scalaVersion.value,
      "org.scala-lang" % "scala-library" % scalaVersion.value,
      "org.scala-lang" % "scala-reflect" % scalaVersion.value,
      "org.scalacheck" %% "scalacheck" % "1.12.5",
      "me.andrz.jackson" % "jackson-json-reference-core" % "0.2.1",
      "de.zalando" %% "beard" % "0.0.6",
      "io.argonaut" %% "argonaut" % "6.1"
    ),
    scalacOptions ++= Seq(
      "-Ywarn-value-discard"
    )
  )

// This is the sbt plugin
lazy val plugin = (project in file("plugin"))
  .enablePlugins(BuildInfoPlugin)
  .settings(common: _*)
  .settings(scriptedSettings: _*)
  .settings(
    name := "sbt-play-swagger",
    scalaVersion := "2.10.5",
    sbtPlugin := true,

    addSbtPlugin("com.typesafe.play" % "sbt-plugin" % PlayVersion),

    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    buildInfoPackage := "de.zalando",

    scriptedLaunchOpts := {
      scriptedLaunchOpts.value ++
      Seq(
        "-Dproject.version=" + version.value,
        "-Dscala.version=" + scalaVersion.value,
        "-Xmx512M",
        "-XX:MaxPermSize=256M",
        "-XX:ReservedCodeCacheSize=256M"
      )
    },
    scriptedDependencies := {
      val a = (publishLocal in api).value
      val b = (publishLocal in compiler).value
      val c = publishLocal.value
    },
    scriptedBufferLog := false
  )
  .dependsOn(compiler)

lazy val root = (project in file("."))
  // Use sbt-doge cross building since we have different projects with different scala versions
  .enablePlugins(CrossPerProjectPlugin)
  .settings(common: _*)
  .settings(
    name := "play-swagger-root"
  )
  .aggregate(api, compiler, plugin)

def common: Seq[Setting[_]] = bintrayPublishSettings ++ Seq(
  organization := "de.zalando",
  version      := "0.1.8",
  fork in ( Test, run ) := true,
  autoScalaLibrary := true,
  resolvers ++= Seq(
    "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases",
    "zalando-maven" at "https://dl.bintray.com/zalando/maven"
  ),
  resolvers           += Resolver.bintrayRepo("zalando", "sbt-plugins"),
  licenses            += ("MIT", url("http://opensource.org/licenses/MIT")),
  publishMavenStyle   := false,
  repository in bintray := "sbt-plugins",
  bintrayOrganization in bintray := Some("zalando")
)

ScoverageSbtPlugin.ScoverageKeys.coverageMinimum := 60

ScoverageSbtPlugin.ScoverageKeys.coverageFailOnMinimum := false

ScoverageSbtPlugin.ScoverageKeys.coverageHighlighting := {
  if (scalaBinaryVersion.value == "2.10") false
  else false
}
