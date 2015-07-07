lazy val root = (project in file("."))
  .enablePlugins(PlaySwagger, PlayScala)

scalaVersion := "2.11.7"

routesGenerator := InjectedRoutesGenerator