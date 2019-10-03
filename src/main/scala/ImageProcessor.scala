import java.awt.Color
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream

import Error.ErrorOr
import javax.imageio.ImageIO

class ImageProcessor() {
  val distortEffect = 3
  private val r = new scala.util.Random

  def distortImage(fileName: String): ErrorOr[Array[Byte]] = for {
    image <- FileProcessor.getImageFromFile(fileName)
  } yield {
    (processImage andThen createByteArray) (image)
  }

  private def distortPixel(rgb: Int): Int = {
    val color = new Color(rgb)
    new Color(getRandom(color.getRed), getRandom(color.getGreen), getRandom(color.getBlue)).getRGB
  }

  private def getRandom(n: Int): Int = {
    (n - distortEffect) + r.nextInt(distortEffect + 1)
  }

  private val processImage = (image: BufferedImage) => {
    val width = image.getWidth
    val height = image.getHeight
    val out = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)

    for (w <- 0 until width) {
      for (h <- 0 until height) {
        val rgb: Int = image.getRGB(w, h)
        if (w < width / 2) {
          out.setRGB(w, h, distortPixel(rgb))
        }
        else out.setRGB(w, h, rgb)
      }
    }
    out
  }

  private val createByteArray = (image: BufferedImage) => {
    val outputStream = new ByteArrayOutputStream
    ImageIO.write(image, "png", outputStream)
    outputStream.toByteArray
  }
}
