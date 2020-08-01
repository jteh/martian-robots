organization := "com.jteh"
name := "martian-robots"
version := "0.1"
scalaVersion := "2.12.4"
crossPaths := false

resolvers ++= Seq(
  "typesafe" at "http://repo.typesafe.com/typesafe/releases",
  Resolver.bintrayRepo("jarlakxen", "maven"),
  Resolver.mavenLocal
)

libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-classic" % "1.1.7",
  "com.typesafe.akka" %% "akka-actor" % "2.6.4",
  "com.typesafe.akka" %% "akka-http" % "10.1.11",
  "com.typesafe.akka" %% "akka-stream" % "2.6.4",
  "com.typesafe.scala-logging" % "scala-logging_2.12" % "3.7.1",
  "org.json4s" %% "json4s-ext" % "3.6.2",
  "org.json4s" %% "json4s-native" % "3.6.2",

  // test
  "com.github.pureconfig" %% "pureconfig" % "0.8.0" % Test,
  "com.github.tomakehurst" % "wiremock-jre8" % "2.26.0" % Test,
  "com.typesafe.akka" %% "akka-http-testkit" % "10.1.11" % Test,
  "com.typesafe.akka" %% "akka-testkit" % "2.6.4" % Test,
  "org.mockito" % "mockito-all" % "1.10.19" % Test,
  "org.scalamock" %% "scalamock" % "4.2.0" % Test,
  "org.scalatest" %% "scalatest" % "3.0.6" % Test,
)

fork in run := true

enablePlugins(UniversalPlugin)
enablePlugins(JavaAppPackaging)

version := (version in ThisBuild).value
