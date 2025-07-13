ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.7.0"

Compile / scalacOptions ++= Seq(
  "-Wconf:msg=Implicit parameters should be provided with a `using` clause:s"
)

lazy val root = (project in file("."))
  .settings(
    name := "Client",
    libraryDependencies += "org.scalafx" %% "scalafx" % "24.0.0-R35"
  )
