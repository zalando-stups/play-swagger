resolvers += Resolver.sonatypeRepo("releases")

addSbtPlugin("com.eed3si9n"     % "sbt-buildinfo" % "0.4.0")
addSbtPlugin("com.typesafe.sbt" % "sbt-twirl"     % "1.1.1")
addSbtPlugin("com.eed3si9n"     % "sbt-doge"      % "0.1.5")
addSbtPlugin("org.scoverage"    % "sbt-scoverage" % "1.1.0")
addSbtPlugin("me.lessis"        % "bintray-sbt"   % "0.2.1")

addSbtPlugin("com.typesafe.sbt" % "sbt-scalariform"         % "1.3.0")
addSbtPlugin("org.scalastyle"   %% "scalastyle-sbt-plugin"  % "0.7.0")
addSbtPlugin("org.brianmckenna" % "sbt-wartremover"         % "0.13")

libraryDependencies <+= (sbtVersion) { sv =>
  "org.scala-sbt" % "scripted-plugin" % sv
}



