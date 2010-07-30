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
  var latitude: Option[Float]     = None
  var longitude: Option[Float]    = None
  
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
trait LocationBox {
  var lowerLatitude: Option[Float] = None
  var lowerLongitude: Option[Float] = None
  var upperLatitude: Option[Float] = None
  var upperLongitude: Option[Float] = None
}

object Location {
  def fromXml(xml: Node): Location = {
    var loc: Option[Location] = None
    if (xml.exists((x: Node) => (x \\ "point").size > 0)) {
      loc = Some(new Location {
          latitude = Some((xml \ "point").text.split(" ")(0).toFloat)
          longitude = Some((xml \ "point").text.split(" ")(1).toFloat)
          parseMetadata(xml)
      })
    }
    if (xml.exists((x: Node) => (x \\ "box").size > 0)) {
      loc = Some(new Location with LocationBox {
        lowerLatitude = Some((xml \ "box").text.split(" ")(0).toFloat)
        lowerLongitude = Some((xml \ "box").text.split(" ")(1).toFloat)
        upperLatitude = Some((xml \ "box").text.split(" ")(2).toFloat)
        upperLongitude = Some((xml \ "box").text.split(" ")(3).toFloat)
        latitude = Some((lowerLatitude.get + upperLatitude.get) / 2)
        longitude = Some((lowerLongitude.get + upperLongitude.get) / 2)
        parseMetadata(xml)
      })
    }
    return loc.get
  }
}