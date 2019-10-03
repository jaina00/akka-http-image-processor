import org.scalatest.{FunSuite, Matchers, WordSpec}

class FileProcessorTest extends WordSpec with Matchers {
  "FileProcessor" should {
    "return Buffer Image if the given file exists" in {
      val res = FileProcessor.getImageFromFile("c.png")
      assert(res.isRight)
    }

    "return Error if the given file does not exists" in {
      val res = FileProcessor.getImageFromFile("unknown.png")
      res shouldBe Left(ImageFileNotFound("unknown.png does not exists"))
    }
  }
}
