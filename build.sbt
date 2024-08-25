import scala.collection.Seq

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.3"

lazy val root = (project in file("."))
  .settings(
    name := "NewLoader",
    idePackagePrefix := Some("src.main.scala.Loader")
  )

libraryDependencies ++= Seq(
  "com.datastax.spark" % "spark-cassandra-connector_2.11" % "2.0.5"  % "provided",
  "org.apache.cassandra" % "cassandra-all" % "3.11.2",
  "org.apache.spark" % "spark-core_2.11" % "2.2.0" % "provided",
  "org.apache.spark" % "spark-sql_2.11" % "2.2.0" % "provided",
  "org.apache.hadoop" % "hadoop-common" % "3.2.0",
  "org.apache.hadoop" % "hadoop-mapreduce-client-core" % "3.1.0",
  "com.datastax.oss" % "java-driver-core" % "4.13.0",
  "org.slf4j" % "slf4j-api" % "1.7.30",
  "com.datastax.cassandra" % "cassandra-driver-core" % "3.3.0",
  "com.codahale.metrics" % "metrics-core" % "3.0.2",
  "com.datastax.cassandra" % "cassandra-driver-extras" % "3.3.0",
  "com.clearspring.analytics" % "stream" % "2.9.8",
  "net.java.dev.jna" % "jna" % "5.14.0",
  "com.github.jnr" % "jnr-posix" % "3.0.61",
  "com.github.jnr" % "jnr-ffi" % "2.1.15",
  "com.github.jnr" % "jnr-constants" % "0.9.16",
  "com.github.jnr" % "jnr-enxio" % "0.18",
  "com.github.jnr" % "jnr-unixsocket" % "0.25"
)

// Exclusions for all dependencies
excludeDependencies ++= Seq(
  ExclusionRule(organization = "org.slf4j", name = "slf4j-log4j12"),
  ExclusionRule(organization = "log4j")
)
scalacOptions ++= Seq("-deprecation", "-feature")

// JVM options
//javaOptions ++= Seq(
//  "-Dcom.datastax.driver.USE_NATIVE_CLOCK=false",
//  "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"
//)

