import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._

class ImageRoute(imageProcessor: ImageProcessor) {
  val route = pathPrefix("distort") {
    {
      get {
        complete {
          val response: HttpResponse = imageProcessor.distortImage("c.png")
            .fold(errorHandler, processResponse)
          response
        }
      }
    }
  }

  val errorHandler: Error => HttpResponse = {
    case ImageFileNotFound(msg: String) => HttpResponse(StatusCodes.InternalServerError, entity = msg)
  }

  val processResponse: Array[Byte] => HttpResponse = (bytes: Array[Byte]) => {
    HttpResponse(StatusCodes.OK, entity = HttpEntity(ContentType(MediaTypes.`image/png`), bytes))
  }
}
