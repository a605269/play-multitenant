name := """play-multitenant"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.0-RC1" % Test,
  "com.h2database" % "h2" % "1.4.190",
  "io.getquill" %% "quill-jdbc" % "0.10.0",
  "com.typesafe.play" % "play-jdbc-evolutions_2.11" % "2.5.7",
  "com.pauldijou" %% "jwt-play-json" % "0.9.2"
)

resolvers ++= Seq(
  "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
)

routesGenerator := InjectedRoutesGenerator