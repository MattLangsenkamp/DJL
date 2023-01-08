ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.1.3"

lazy val root = (project in file("."))
  .settings(
    name := "DJL",
    libraryDependencies ++= Seq(
      "ai.djl" % "api" % "0.20.0",
      "ai.djl.mxnet" % "mxnet-engine" % "0.20.0" % "runtime",
      "org.slf4j" % "slf4j-api" % "2.0.5",
      "org.slf4j" % "slf4j-simple" % "2.0.5",
      "tech.tablesaw" % "tablesaw-jsplot" % "0.38.1",
      "ai.djl.mxnet" % "mxnet-native-cu112mkl" % "1.9.1",
      "ai.djl.mxnet" % "mxnet-native-mkl" % "1.9.1" % "runtime"
    )
  )
