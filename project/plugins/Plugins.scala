import sbt._
class Plugins(info: ProjectInfo) extends PluginDefinition(info) {
  val lessRepo = "lessis repo" at "http://repo.lessis.me"
  val growl = "me.lessis" % "sbt-growl-plugin" % "0.0.5"
}

// vim: set ts=4 sw=4 et: