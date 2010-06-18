import scala.xml.Node

case class PlaceId(val identifier: String, val bestGuess: Boolean)
object PlaceId {
  def fromXml(xml: Node) = PlaceId(xml.text, xml.attributes("exact-match")(0).text.toBoolean )
}