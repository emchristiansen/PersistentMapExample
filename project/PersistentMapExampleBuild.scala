import sbt._
import Keys._

object PersistentMapExampleBuild extends Build {
  def extraResolvers = Seq(
    resolvers ++= Seq(
      "Sonatype OSS Releases" at "http://oss.sonatype.org/content/repositories/releases/",
      "Sonatype OSS Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"
    )
  )

  val projectName = "PersistentMapExample"

  val scalaVersionString = "2.10.3-RC3"

  def extraLibraryDependencies = Seq(
    libraryDependencies ++= Seq(
      "st.sparse" %% "persistent-map" % "0.1-SNAPSHOT",
      "com.typesafe.slick" %% "slick" % "1.0.1",
      "org.scala-lang" %% "scala-pickling" % "0.8.0-SNAPSHOT",
      "org.xerial" % "sqlite-jdbc" % "3.7.2",
      "org.slf4j" % "slf4j-nop" % "1.6.4"
    )
  )

  def updateOnDependencyChange = Seq(
    watchSources <++= (managedClasspath in Test) map { cp => cp.files })

  def scalaSettings = Seq(
    scalaVersion := scalaVersionString,
    scalacOptions ++= Seq(
      "-optimize",
      "-unchecked",
      "-deprecation",
      "-feature"
    )
  )

  def moreSettings =
    Project.defaultSettings ++
    extraResolvers ++
    extraLibraryDependencies ++
    scalaSettings ++
    updateOnDependencyChange

  lazy val root = {
    val settings = moreSettings ++ Seq(name := projectName, fork := true)
    Project(id = projectName, base = file("."), settings = settings)
  }
}
