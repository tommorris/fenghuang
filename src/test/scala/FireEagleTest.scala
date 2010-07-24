import org.specs._
import org.specs.util._
import dispatch._
import oauth._
import scala.xml._

trait FireEagleMocker extends FireEagle {
  import java.io.File.{separator => /}
  
  override def getRequestToken() {
    this.requestToken = Some[Token](Token("0000000000","0000000000000000000000000000000"))
  }
  
  override def convertToAccessToken(verifier: String) {
    this.accessToken = Some[Token](Token("1111111111","1111111111111111111111111111111"))
  }
  
  override def getUserLocation(): Option[User] = {
    val resp = this.impl_getUserLocationXml()
    if (resp.isDefined)
      return Some(User.fromXml(resp))
    else
      return None
  }
  return this.impl_getUserLocationXml()
  
  private def impl_getUserLocationXml(): Option[Elem] = {
    if (this.requestToken.isDefined == true && this.accessToken.isDefined == true)
      Some[Elem](XML.loadFile(System.getProperty("user.dir") + "src/test/resources/validresponse.xml"))
    else
      None    
  }
}

object FireEagleTest extends Specification {
  "FireEagle" should {
    "get request token" in {
      val fe = new FireEagle("000000000000", "00000000000000000000000000000000") with FireEagleMocker
      fe.requestToken.isDefined mustBe false
      fe.getRequestToken()
      fe.requestToken.isDefined mustBe true
      fe.requestToken.get.value mustBe "0000000000"
      fe.requestToken.get.secret mustBe "0000000000000000000000000000000"
    }
    
    "convert request token to access token" in {
      val fe = new FireEagle("000000000000", "00000000000000000000000000000000") with FireEagleMocker
      fe.getRequestToken()
      fe.convertToAccessToken("abcdef")
      fe.accessToken.get.value mustBe "1111111111"
      fe.accessToken.get.secret mustBe "1111111111111111111111111111111"
    }
    
    "load XML" in {
      val fe = new FireEagle("000000000000", "00000000000000000000000000000000") with FireEagleMocker
      fe.getUserLocation().isDefined mustBe false
      fe.getRequestToken()
      fe.convertToAccessToken("abcdef")
      
      val data = fe.getUserLocation()
      data.isDefined mustBe true
    }
  }
}