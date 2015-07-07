lazy val root = (project in file("."))
  .enablePlugins(PlaySwagger, PlayScala)
  .dependsOn(swaggerApi)

// The swagger plugin automatically adds a dependency on this, but we make it an
// explicit project ref, so that when we change it, we get the updates without
// having to publish it again
lazy val swaggerApi = ProjectRef(Path.fileProperty("user.dir").getParentFile, "api")

scalaVersion := "2.11.7"

routesGenerator := InjectedRoutesGenerator