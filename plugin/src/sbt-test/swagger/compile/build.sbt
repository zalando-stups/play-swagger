lazy val root = (project in file("."))
  .enablePlugins(PlayScala, PlaySwagger)

scalaVersion := "2.11.7"

routesGenerator := InjectedRoutesGenerator

libraryDependencies ++= Seq(
  specs2,
  "org.scalacheck" %% "scalacheck" % "1.12.4",
  "org.specs2" %% "specs2-scalacheck" % "3.6"
)