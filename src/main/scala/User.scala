import dispatch.oauth.Token
import scala.xml._
import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat

class User {
  var token: Option[String] = None
  var readable: Option[Boolean] = None
  var writable: Option[Boolean] = None
  var locatedAt: Option[DateTime] = None
}
object User {
/*  def fromXml(xml: Elem): User = new User {
    name = 
  }
*//*  def getLocationHierarchy() {
*//*    XML.loadString(this.fe.h(this.fe.fe / "user" <@ (this.fe.consumer, this.fe.accessToken.get) as_str))*/
/*  }*/
}
