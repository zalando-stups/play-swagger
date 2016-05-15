lazy val root = (project in file(".")).enablePlugins(PlayScala, PlaySwagger)

scalaVersion := sys.props.get("scala.version").getOrElse("2.11.7")

crossPaths := false

routesGenerator := InjectedRoutesGenerator

libraryDependencies ++= Seq(
  specs2 % "test",
  "org.scalacheck" %% "scalacheck" % "1.12.4" % "test",
  "org.specs2" %% "specs2-scalacheck" % "3.6" % "test",
  "me.jeffmay" %% "play-json-tests" % "1.3.0" % "test"
)