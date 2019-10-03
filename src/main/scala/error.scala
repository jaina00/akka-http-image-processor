sealed trait Error {
  val msg: String
}

case class ImageFileNotFound(msg: String) extends Error

object Error {
  type ErrorOr[A] = Either[Error, A]
}
