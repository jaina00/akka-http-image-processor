import akka.http.scaladsl.Http
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContext

object Application extends App {
  implicit val system = ActorSystem()
  implicit val mat = ActorMaterializer()
  implicit val executionContext: ExecutionContext = system.dispatcher

  val imageRoute = new ImageRoute(new ImageProcessor())
  Http().bindAndHandle(imageRoute.route, "localhost", 8080)
}
