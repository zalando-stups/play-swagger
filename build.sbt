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
      "org.scalacheck" %% "scalacheck" % "1.12.4" % "test",
      "org.specs2" %% "specs2-scalacheck" % "3.6" % "test",
      "me.jeffmay" %% "play-json-tests" % "1.3.0" % "test"
    ),
    scalaVersion :=  "2.10.5",
    crossScalaVersions := Seq(scalaVersion.value, ScalaVersion),
    resolvers ++= Seq(
      "jeffmay" at "https://dl.bintray.com/jeffmay/maven"
    )
  )

lazy val swaggerModel = (project in file("swagger-model"))
  .enablePlugins(BuildInfoPlugin)
  .settings(common: _*)
  .settings(
    name := "swagger-model",
    scalaVersion := "2.10.5",
    sbtPlugin := false,

    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    buildInfoPackage := "de.zalando",
    libraryDependencies ++= Seq(
      "com.fasterxml.jackson.core" % "jackson-databind" % "2.4.4",
      "com.fasterxml.jackson.dataformat" % "jackson-dataformat-yaml" % "2.4.4",
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.6.1",
      "me.andrz.jackson" % "jackson-json-reference-core" % "0.2.1",
      "org.scalatest" %% "scalatest" % "2.2.6" % "test"
    )
  )

lazy val apiFirstCore = (project in file("api-first-core"))
  .enablePlugins(BuildInfoPlugin)
  .settings(common: _*)
  .settings(
    name := "api-first-core",
    scalaVersion := "2.10.5",
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-compiler" % scalaVersion.value,
      "org.scala-lang" % "scala-library" % scalaVersion.value,
      "org.scala-lang" % "scala-reflect" % scalaVersion.value,
      "org.scalatest" %% "scalatest" % "2.2.3" % "test",
      "org.scalacheck" %% "scalacheck" % "1.12.5" % "test"
    )
  )
  .dependsOn(api)


lazy val swaggerParser = (project in file("swagger-parser"))
  .enablePlugins(BuildInfoPlugin)
  .settings(common: _*)
  .settings(
    name := "swagger-parser",
    scalaVersion := "2.10.5",
    sbtPlugin := false,

    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    buildInfoPackage := "de.zalando",
    libraryDependencies ++= Seq(
      "com.fasterxml.jackson.core" % "jackson-databind" % "2.4.4",
      "com.fasterxml.jackson.dataformat" % "jackson-dataformat-yaml" % "2.4.4",
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.6.1",
      "me.andrz.jackson" % "jackson-json-reference-core" % "0.2.1",
      "org.scalatest" %% "scalatest" % "2.2.6" % "test"
    )
  )
  .dependsOn(swaggerModel, apiFirstCore)


lazy val playScalaGenerator = (project in file("play-scala-generator"))
  .enablePlugins(BuildInfoPlugin)
  .settings(common: _*)
  .settings(
    name := "play-scala-generator",
    scalaVersion := "2.10.5",
    sbtPlugin := false,

    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    buildInfoPackage := "de.zalando",
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "routes-compiler" % PlayVersion % Provided,
      "com.typesafe.play" %% "play" % PlayVersion % Provided,
      "org.scala-lang" % "scala-compiler" % scalaVersion.value,
      "org.scala-lang" % "scala-library" % scalaVersion.value,
      "org.scala-lang" % "scala-reflect" % scalaVersion.value,
      "org.scalatest" %% "scalatest" % "2.2.6" % "test",
      "org.scalacheck" %% "scalacheck" % "1.12.5" % "test",
      "de.zalando" %% "beard" % "0.0.6"
    )
  ).dependsOn(apiFirstCore)

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
  .dependsOn(swaggerModel, swaggerParser, apiFirstCore, playScalaGenerator)

lazy val root = (project in file("."))
  // Use sbt-doge cross building since we have different projects with different scala versions
  .enablePlugins(CrossPerProjectPlugin)
  .settings(common: _*)
  .settings(
    name := "play-swagger-root"
  )
  .aggregate(swaggerModel, api, swaggerParser, apiFirstCore, playScalaGenerator, plugin)

def common: Seq[Setting[_]] = bintrayPublishSettings ++ Seq(
  organization := "de.zalando",
  version      := "0.1.10",
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
  bintrayOrganization in bintray := Some("zalando"),
  scalacOptions ++= Seq(
    "-deprecation",
    "-encoding", "UTF-8", // yes, this is 2 args
    "-feature",
    "-unchecked",
    "-Xfatal-warnings",
    "-Xlint",
    "-Yno-adapted-args",
    "-Ywarn-numeric-widen",
    "-Ywarn-value-discard",
    "-Xfuture"
  ),
  scalastyleFailOnError := true
) ++ addMainSourcesToLintTarget ++ addSlowScalacSwitchesToLintTarget ++ addWartRemoverToLintTarget ++
  removeWartRemoverFromCompileTarget ++ addFoursquareLinterToLintTarget ++ removeFoursquareLinterFromCompileTarget
// ++ scalariformSettings

// coverageEnabled := false

coverageMinimum := 80

coverageFailOnMinimum := false

coverageHighlighting := {
  if (scalaBinaryVersion.value == "2.10") false
  else false
}


// Apply default Scalariform formatting.
// Reformat at every compile.
// c.f. https://github.com/sbt/sbt-scalariform#advanced-configuration for more options.

val LintTarget = config("lint").extend(Compile)

def addMainSourcesToLintTarget: Seq[_root_.sbt.Def.Setting[_]] = {
  inConfig(LintTarget) {
    // I posted http://stackoverflow.com/questions/27575140/ and got back the bit below as the magic necessary
    // to create a separate lint target which we can run slow static analysis on.
    Defaults.compileSettings ++ Seq(
      sources in LintTarget := {
        val lintSources = (sources in LintTarget).value
        lintSources ++ (sources in Compile).value
      }
    )
  }
}

def addSlowScalacSwitchesToLintTarget: Seq[_root_.sbt.Def.Setting[_]] = {
  inConfig(LintTarget) {
    // In addition to everything we normally do when we compile, we can add additional scalac switches which are
    // normally too time consuming to run.
    scalacOptions in LintTarget ++= Seq(
      // As it says on the tin, detects unused imports. This is too slow to always include in the build.
      // "-Ywarn-unused-import",
      //This produces errors you don't want in development, but is useful.
      "-Ywarn-dead-code"
    )
  }
}

def addWartRemoverToLintTarget: Seq[_root_.sbt.Def.Setting[_]] = {
  import wartremover._
  import Wart._
  // I didn't simply include WartRemove in the build all the time because it roughly tripled compile time.
  inConfig(LintTarget) {
    wartremoverErrors ++= Seq(
      // Ban inferring Any, Serializable, and Product because such inference usually indicates a code error.
      Wart.Any,
      Wart.Serializable,
      Wart.Product,
      // Ban calling partial methods because they behave surprisingly
      Wart.ListOps,
      Wart.OptionPartial,
      Wart.EitherProjectionPartial,
      // Ban applying Scala's implicit any2String because it usually indicates a code error.
      Wart.Any2StringAdd
    )
  }
}

def removeWartRemoverFromCompileTarget: _root_.sbt.Def.Setting[_root_.sbt.Task[Seq[String]]] = {
  // WartRemover's sbt plugin calls addCompilerPlugin which always adds directly to the Compile configuration.
  // The bit below removes all switches that could be passed to scalac about WartRemover during a non-lint compile.
  scalacOptions in Compile := (scalacOptions in Compile).value filterNot { switch =>
    switch.startsWith("-P:wartremover:") ||
      "^-Xplugin:.*/org[.]brianmckenna/.*wartremover.*[.]jar$".r.pattern.matcher(switch).find
  }
}

def addFoursquareLinterToLintTarget: Seq[_root_.sbt.Def.Setting[_ >: _root_.sbt.Task[Seq[String]] with Seq[_root_.sbt.ModuleID] <: Equals]] = {
  Seq(
    addCompilerPlugin("org.psywerx.hairyfotr" %% "linter" % "0.1.12"),
    // See https://github.com/HairyFotr/linter#list-of-implemented-checks for a list of checks that foursquare linter
    // implements
    // By default linter enables all checks.
    // I don't mind using match on boolean variables.
    scalacOptions in LintTarget += "-P:linter:disable:PreferIfToBooleanMatch"
  )
}

def removeFoursquareLinterFromCompileTarget: _root_.sbt.Def.Setting[_root_.sbt.Task[Seq[String]]] = {
  // We call addCompilerPlugin in project/plugins.sbt to add a depenency on the foursquare linter so that sbt magically
  // manages the JAR for us.  Unfortunately, addCompilerPlugin also adds a switch to scalacOptions in the Compile config
  // to load the plugin.
  // The bit below removes all switches that could be passed to scalac about Foursquare Linter during a non-lint compile.
  scalacOptions in Compile := (scalacOptions in Compile).value filterNot { switch =>
    switch.startsWith("-P:linter:") ||
      "^-Xplugin:.*/org[.]psywerx[.]hairyfotr/.*linter.*[.]jar$".r.pattern.matcher(switch).find
  }
}
