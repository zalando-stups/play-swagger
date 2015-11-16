val PlayVersion = "2.4.2"
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
      "com.typesafe.play" %% "play" % PlayVersion % Provided,
      "org.scalacheck" %% "scalacheck" % "1.12.4",
      "org.specs2" %% "specs2-scalacheck" % "3.6"
    ),
    scalaVersion :=  "2.10.5",
    crossScalaVersions := Seq(scalaVersion.value, ScalaVersion)
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
      "org.scalacheck" %% "scalacheck" % "1.12.4",
      "me.andrz.jackson" % "jackson-json-reference" % "0.1.2",
      "org.scalatra.scalate" %% "scalate-core" % "1.7.0"
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

    scriptedLaunchOpts := { scriptedLaunchOpts.value ++
      Seq("-Dproject.version=" + version.value)
    },
    scriptedDependencies := {
      // Ensure everything is published before scripted runs
      val a = (publishLocal in api).value
      val b = (publishLocal in compiler).value
      val c = publishLocal.value
    }
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

def common: Seq[Setting[_]] = Seq(
  organization := "de.zalando",
  fork in ( Test, run ) := true,
  autoScalaLibrary := true,
  resolvers ++= Seq(
    "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases",
    Resolver.bintrayRepo("slavaschmidt","maven")
  )
)

