import dispatch._
import oauth._
import OAuth._
import scala.xml._
import java.net.URI

/**
 * Main FireEagle consumer class. Used to connect to the API and to get back user and location objects.
 * 
 * @author Tom Morris
 */
class FireEagle(val consumer: Consumer) {
  def this(consumerToken: String, consumerSecret: String) = {
    this(Consumer(consumerToken, consumerSecret))
  }
  
  def this(consumer: Consumer, accessToken: Token) = {
    this(consumer)
    this.accessToken = Some[Token](accessToken)
  }
  
  def this(consumerToken: String, consumerSecret: String, accessToken: String, accessSecret: String) {
    this(Consumer(consumerToken, consumerSecret))
    this.accessToken = Some[Token](Token(accessToken, accessSecret))
  }
    
  private val h = new Http()
  private val fe_oauth = (:/("fireeagle.yahooapis.com") / "oauth").secure
  private val fe = (:/("fireeagle.yahooapis.com") / "api" / "0.1").secure
  var callbackUri = "oob"
  
  var generalToken: Option[Token] = None
  var requestToken: Option[Token] = None
  var accessToken: Option[Token] = None

  def getRequestToken() {
    this.requestToken = Some[Token](this.h(this.fe_oauth / "request_token" << Map("oauth_callback" -> this.callbackUri) <@ this.consumer as_token))
  }
    
  def convertToAccessToken(verifier: String) {
    this.accessToken = Some[Token](this.h(this.fe_oauth / "access_token" << Map("oauth_verifier" -> verifier) <@ (this.consumer, this.requestToken.get) as_token))
  }
  
  def getUserLocation(): Option[scala.xml.Elem] = {
    if (this.requestToken.isDefined && this.accessToken.isDefined) {
      Some(XML.loadString(this.h(this.fe / "user" <@ (this.consumer, this.accessToken.get) as_str)))
    } else {
      return None
    }
  }
}