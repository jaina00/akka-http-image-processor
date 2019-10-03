import akka.http.scaladsl.model.{ContentType, MediaTypes}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{Matchers, WordSpec}
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Route
import akka.stream.scaladsl.Sink
import akka.util.ByteString
import org.scalatest.concurrent.ScalaFutures

import scala.concurrent.Future

class ImageRouteTest extends WordSpec with Matchers with ScalatestRouteTest with ScalaFutures{

  "GET /distort" should {

    "return successful response when image is distorted" in {
      val imageProcessor = new ImageProcessor{
        override def distortImage(fileName: String) = Right(Array(115, 111, 109, 101, 116, 104, 105, 110, 103))
      }

      Get("/distort") ~> Route.seal(new ImageRoute(imageProcessor).route) ~> check {
        status shouldEqual OK
        contentType shouldBe ContentType(MediaTypes.`image/png`)
        val res: Future[ByteString] = response.entity.dataBytes.runWith(Sink.fold(ByteString.empty)(_ ++ _))
        whenReady(res){ _ shouldBe ByteString(115, 111, 109, 101, 116, 104, 105, 110, 103)}
      }
    }

    "return error response when image to be distorted is not found" in {
      val imageProcessor = new ImageProcessor{
        override def distortImage(fileName: String) = Left(ImageFileNotFound("image not found"))
      }
      Get("/distort") ~> Route.seal(new ImageRoute(imageProcessor).route) ~> check {
        status shouldEqual InternalServerError
        entityAs[String] shouldBe "image not found"
      }
    }

  }

}
