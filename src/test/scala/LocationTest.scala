import org.specs._
import org.specs.util._
import scala.xml._

class LocationTest extends Specification {
  "Location class" should {
    "parse from XML" in {
      val data = XML.loadFile(System.getProperty("user.dir") + "/src/test/resources/validresponse.xml")
      val loc = Location.fromXml((data \\ "location-hierarchy" \ "location")(0))
      loc.name.isDefined mustBe true
      loc.name.get must_== "Sumner Street, London, SE1 9"
      loc.latitude.isDefined mustBe true
    }
  }
}