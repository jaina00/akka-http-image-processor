name := "akka-http-image-processor"

version := "0.1"

scalaVersion := "2.12.10"

libraryDependencies ++= {
  val akkaHttpV = "10.1.5"
  val scalaTestV = "3.0.3"
  val akkaV = "2.5.23"

  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "com.typesafe.akka" %% "akka-stream" % akkaV,
    "com.typesafe.akka" %% "akka-http" % akkaHttpV,
    "org.scalatest" %% "scalatest" % scalaTestV % Test,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpV % Test
  )
}