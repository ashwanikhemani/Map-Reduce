name := "joda-time"

scalaVersion := "2.10.3"

organization := "joda-time"

version := "2.9.9"

publishMavenStyle := true

crossPaths := false

autoScalaLibrary := false

libraryDependencies ++= Seq("junit" % "junit" % "4.12" % "test",
  "org.joda" % "joda-convert" % "1.2" % "compile")

libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test"

lazy val jacoco = Seq(
  jacocoReportSettings in Test := JacocoReportSettings()
    withFormats(JacocoReportFormats.HTML)
)
lazy val jacoco = Seq(
  jacocoIncludes in Test := jacocoIncludes()
    withFormats(JacocoReportFormats.HTML)
)

mainClass in Compile := Some("org.joda.time.DateTimeZone.Provider")