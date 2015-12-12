name := "play-scala-swagger"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala, PlaySwagger)

scalaVersion := "%SCALA_VERSION%"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2,
  "org.scalacheck" %% "scalacheck" % "1.12.4",
  "org.specs2" %% "specs2-scalacheck" % "3.6"  
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

resolvers += "zalando-bintray" at "https://dl.bintray.com/zalando/sbt-plugins/de.zalando"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
