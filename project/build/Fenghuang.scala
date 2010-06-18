import sbt._
import growl._

class Blogtext(info: ProjectInfo) extends DefaultProject(info) with growl.GrowlingTests {
  override def parallelExecution = true

  val scalaToolsRepository = "Scala Tools Repository" at "http://scala-tools.org/repo-releases/"
  val mockitoCoreRepository = "Mockito Core Repository" at "http://repo2.maven.org/maven2/org/mockito/mockito-core"
  val mockitoAllRepository = "Mockito All Repository" at "http://repo2.maven.org/maven2/org/mockito/mockito-all"
  
  override def libraryDependencies = Set(
    "org.scala-tools.testing" % "specs" % "1.6.2.1" % "test",
    "net.databinder" %% "dispatch" % "0.7.4",
    "org.mockito" % "mockito-core" % "1.8.5",
    "org.mockito" % "mockito-all" % "1.8.5",
    "joda-time" % "joda-time" % "1.6"
  ) ++ super.libraryDependencies
  
  override val growlTestImages = GrowlTestImages(
    Some("/Users/tom/code/sbt-growltest/images/scalalogo-green.png"), 
    Some("/Users/tom/code/sbt-growltest/images/scalalogo-red.png"), 
    Some("/Users/tom/code/sbt-growltest/images/scalalogo-red.png")
  )
}