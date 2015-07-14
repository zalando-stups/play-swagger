val PlayVersion = "2.4.2"

// This is the API project, it gets added to the runtime dependencies of any
// project using play-swagger
lazy val api = (project in file("api"))
  .settings(common: _*)
  .settings(
    name := "play-swagger-api",
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play" % PlayVersion % Provided
    ),
    scalaVersion := "2.11.7",
    crossScalaVersions := Seq(scalaVersion.value, "2.10.5")
  )

// This is the compiler, it does compilation of swagger definitions,
// but has no dependency on sbt, so, for example, someone could use it to
// implement gradle support
lazy val compiler = (project in file("compiler"))
  .enablePlugins(SbtTwirl)
  .settings(common: _*)
  .settings(
    name := "play-swagger-compiler",
    scalaVersion := "2.10.5",
    libraryDependencies ++= Seq(
      "com.fasterxml.jackson.core" % "jackson-databind" % "2.4.4",
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.5.3",
      "org.scalatest" %% "scalatest" % "2.2.3" % "test"
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
    buildInfoPackage := "com.example.play",

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
  organization := "com.example.play"
)

