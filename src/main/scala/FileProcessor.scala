import java.awt.image.BufferedImage
import java.io.InputStream

import Error.ErrorOr
import javax.imageio.ImageIO

object FileProcessor {
  def getImageFromFile(fileName: String): ErrorOr[BufferedImage] = {
    val stream: InputStream = getClass.getResourceAsStream(fileName)
    if (stream == null) {
      Left(ImageFileNotFound(s"$fileName does not exists"))
    } else {
      Right(ImageIO.read(stream))
    }
  }
}
