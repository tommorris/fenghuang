import scala.xml.Node

case class WoeId(val identifier: Int, val bestGuess: Boolean)
object WoeId {
  def fromXml(xml: Node) = WoeId(xml.text.toInt, xml.attributes("exact-match")(0).text.toBoolean )
}