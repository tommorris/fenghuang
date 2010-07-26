import dispatch.oauth.Token
import scala.xml._
import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat

class User {
  var token: Option[String] = None
  var readable: Option[Boolean] = None
  var writable: Option[Boolean] = None
  var locatedAt: Option[DateTime] = None
  var locationHierarchy: Option[List[Location]] = None
}
object User {
  def fromXml(xml: Node): User = new User {
    token = Some((xml \ "user" \ "@token")(0).text)
    readable = Some((xml \ "user" \ "@readable")(0).text match {
      case "true" => true
      case "false" => false })
    writable = Some((xml \ "user" \ "@writable")(0).text match {
      case "true" => true
      case "false" => false })
    locatedAt = Some(ISODateTimeFormat.dateTimeParser().parseDateTime(
                      (xml \ "user" \ "@located-at")(0).text))
    if ((xml \\ "location-hierarchy").size > 0) {
        locationHierarchy = Some((xml \\ "location-hierarchy" \ "location").toList.map((x: Node) => Location.fromXml(x)))
    }
  }
/*  def getLocationHierarchy() {
*//*    XML.loadString(this.fe.h(this.fe.fe / "user" <@ (this.fe.consumer, this.fe.accessToken.get) as_str))*/
/*  }*/
}
