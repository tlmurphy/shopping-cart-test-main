ThisBuild / organization := "com.siriusxm"
ThisBuild / scalaVersion := "3.2.2"

val http4sVersion = "0.23.18"

lazy val root = (project in file(".")).settings(
  name := "shopping-cart-test-main",
  libraryDependencies ++= Seq(
    "org.http4s" %% "http4s-ember-client" % http4sVersion,
    "org.http4s" %% "http4s-dsl" % http4sVersion,
    // JSON Helpers
    "org.http4s" %% "http4s-circe" % http4sVersion,
    "io.circe" %% "circe-generic" % "0.14.5",
    "org.typelevel" %% "munit-cats-effect" % "2.0.0-M3" % Test
  )
)
