addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.4.0")
addSbtPlugin("com.typesafe.sbt" % "sbt-twirl" % "1.1.1")
addSbtPlugin("com.eed3si9n" % "sbt-doge" % "0.1.5")

libraryDependencies <+= (sbtVersion) { sv =>
  "org.scala-sbt" % "scripted-plugin" % sv
}