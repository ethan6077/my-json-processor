package app

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import org.specs2.mutable.Specification

class UtilsSpec extends Specification {

  "parseArgs" should {
    "return a valid file path" in {
      val validFilePath = "csv/xxx.csv"
      val validArgs: List[String] = List(validFilePath)
      val io: IO[String] = Utils.parseArgs(validArgs)

      io.unsafeRunSync() must beEqualTo(validFilePath)
    }

    "raise error given 0 args" in {
      val emptyArgs: List[String] = List()
      val io: IO[String] = Utils.parseArgs(emptyArgs)
      val expectedError: IllegalArgumentException = new IllegalArgumentException("Pleas specify the json file path")

      io.unsafeRunSync() must throwA(expectedError)
    }

    "raise error given 2 args" in {
      val filePath1 = "csv/xxx1.csv"
      val filePath2 = "csv/xxx2.csv"
      val twoArgs: List[String] = List(filePath1, filePath2)
      val io: IO[String] = Utils.parseArgs(twoArgs)
      val expectedError: IllegalArgumentException = new IllegalArgumentException("The app only takes one argument")

      io.unsafeRunSync() must throwA(expectedError)
    }
  }
}
