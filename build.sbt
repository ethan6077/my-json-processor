ThisBuild / scalaVersion     := "2.13.11"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "app"

val circeVersion = "0.14.2"
val specs2Version = "4.20.0"

val myDependencies = Seq(
  "org.typelevel"           %% "cats-effect"                  % "3.5.1",
  "io.circe"                %% "circe-core"                   % circeVersion,
  "io.circe"                %% "circe-generic"                % circeVersion,
  "io.circe"                %% "circe-parser"                 % circeVersion,
  "io.circe"                %% "circe-literal"                % circeVersion,
  "org.specs2"              %% "specs2-core"                  % specs2Version             % "test",
  "org.specs2"              %% "specs2-matcher-extra"         % specs2Version             % "test"
)

lazy val root = (project in file("."))
  .settings(
    name := "my-json-processor",
    libraryDependencies ++= myDependencies
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
