import java.awt.Color
import java.io.ByteArrayInputStream

import javax.imageio.ImageIO
import org.scalatest.{Matchers, WordSpec}

class ImageProcessorTest extends WordSpec with Matchers {

  val imageProcessor = new ImageProcessor()

  "ImageProcessor" should {

    "return image with left half pixels distorted" in {
      //Given there is a 2x2 pixel image
      val originalImage = FileProcessor.getImageFromFile("2x2pixel.png").right.get

      //Then the image is distorted
      val bytes = imageProcessor.distortImage("2x2pixel.png").right.get
      val distortedImage = ImageIO.read(new ByteArrayInputStream(bytes))
      val leftColumn = 0
      for (height <- 0 until 2) {
        val originalImageColor = new Color(originalImage.getRGB(leftColumn, height))
        val newImageColor = new Color(distortedImage.getRGB(leftColumn, height))
        //Left half pixels of the new image should be distorted by an effect of up to 3
        assert(originalImageColor.getRed - newImageColor.getRed <= imageProcessor.distortEffect)
        assert(originalImageColor.getGreen - newImageColor.getGreen <= imageProcessor.distortEffect)
        assert(originalImageColor.getBlue - newImageColor.getBlue <= imageProcessor.distortEffect)
      }
    }

    "return image with right half pixels remains unchanged" in {
      //Given there is a 2x2 pixel image
      val originalImage = FileProcessor.getImageFromFile("2x2pixel.png").right.get

      //Then the image is distorted
      val bytes = imageProcessor.distortImage("2x2pixel.png").right.get
      val distortedImage = ImageIO.read(new ByteArrayInputStream(bytes))

      val rightColumn = 1
      for (height <- 0 until 2) {
        val originalImageColor = new Color(originalImage.getRGB(rightColumn, height))
        val newImageColor = new Color(distortedImage.getRGB(rightColumn, height))
        //Right half pixels of the new image should not be distorted at all
        assert(originalImageColor.getRed == newImageColor.getRed)
        assert(originalImageColor.getGreen == newImageColor.getGreen)
        assert(originalImageColor.getBlue == newImageColor.getBlue)
      }
    }
  }
}