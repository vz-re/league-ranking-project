ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.12"

libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.4.0",
  "org.scalatest" %% "scalatest" % "3.2.11" % Test
)

lazy val root = (project in file("."))
  .settings(
    name := "league-ranking"
  )
