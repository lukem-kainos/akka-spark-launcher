name := "akka-fault-POC"

version := "1.0"

scalaVersion := "2.11.1"

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs@_*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

mainClass in assembly := Some("Main")

assemblyJarName in assembly := "Launcher-Args.jar"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.3.11",
  "org.apache.hadoop" % "hadoop-client" % "2.6.0",
  "org.apache.spark" %% "spark-launcher" % "1.6.0"

)

scalacOptions += "-target:jvm-1.7"