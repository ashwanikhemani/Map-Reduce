name := "junit-test-coverage"

organization := "junit-test-coverage"

version := "1.0"

publishMavenStyle := true

crossPaths := false

autoScalaLibrary := false

libraryDependencies ++= Seq("junit" % "junit" % "4.12" % "test",
  "org.joda" % "joda-convert" % "1.2" % "compile",
  "org.apache.hadoop" % "hadoop-mapreduce-client-core" % "2.8.1" % "compile",
  "org.apache.hadoop" % "hadoop-core" % "0.20.2" % "compile"
  )

libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test"




