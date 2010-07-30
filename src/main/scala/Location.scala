import scala.xml.Node
import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat

/* 
<location best-guess="true">
  ! <georss:point>51.5083 -0.125969</georss:point>
  ! <label></label>
  <level>0</level>
  <level-name>exact</level-name>
  ! <located-at>2010-06-17T11:37:13-07:00</located-at>
  ! <name>Strand, London, WC2N 5</name>
  ! <normal-name>Strand</normal-name>
  ! <place-id exact-match="false">IR1c0qSbB5tPh9kZoQ</place-id>
  ! <woeid exact-match="false">26788881</woeid>
  ! <query>q=Strand%2C%20London%2C%20England</query>
</location>
*/

/* tests todo:
  - empty label */

class Location {
  var name: Option[String]        = None
  var normalName: Option[String]  = None
  var label: Option[String]       = None
  var dateTime: Option[DateTime]  = None
  var query: Option[String]       = None
  var woeId: Option[WoeId]        = None
  var placeId: Option[PlaceId]    = None
  var level: Option[Level]        = None
  
  def parseMetadata(xml: Node) {
    this.name        = Some[String]((xml \ "name").text)
    this.normalName  = Some[String]((xml \ "normal-name").text)
    this.label       = if ((xml \ "label")(0).text.isEmpty) {
                      Some[String]((xml \ "label")(0).text)
                      } else None
    this.dateTime    = Some(ISODateTimeFormat.dateTimeParser().parseDateTime(
                      (xml \ "located-at").text))
    this.query       = Some[String]((xml \ "query").text)
    this.woeId       = Some[WoeId](WoeId.fromXml((xml \ "woeid")(0)))
    this.placeId     = Some[PlaceId](PlaceId.fromXml((xml \ "place-id")(0)))
    this.level       = (xml \ "level").text.toInt match {
                      case 0 => Some[Level](Exact)
                      case 1 => Some[Level](Postal)
                      case 2 => Some[Level](Neighbourhood)
                      case 3 => Some[Level](City)
                      case 4 => Some[Level](Region)
                      case 5 => Some[Level](State)
                      case 6 => Some[Level](Country)
                    }
  }
}
class LocationBox extends Location {
  var xx: Option[String] = None
  var xy: Option[String] = None
  var yx: Option[String] = None
  var yy: Option[String] = None
}
class LocationPoint extends Location {
  var latitude: Option[String]     = None
  var longitude: Option[String]    = None
}

object Location {
  def fromXml(xml: Node): Location = {
    var loc: Option[Location] = None
    if (xml.exists((x: Node) => (x \\ "point").size > 0)) {
      loc = Some(new LocationPoint {
          latitude = Some((xml \ "georss:point").text.split(" ")(0))
          longitude = Some((xml \ "georss:point").text.split(" ")(1))
          parseMetadata(xml)
      })
    }
    if (xml.exists((x: Node) => (x \\ "box").size > 0)) {
      loc = Some(new LocationBox {
        xx = Some((xml \ "georss:box").text.split(" ")(0))
        xy = Some((xml \ "georss:box").text.split(" ")(1))
        yx = Some((xml \ "georss:box").text.split(" ")(2))
        yy = Some((xml \ "georss:box").text.split(" ")(3))
        parseMetadata(xml)
      })
    }
    return loc.get
  }
}