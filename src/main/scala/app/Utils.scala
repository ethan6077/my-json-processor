package app

import cats.effect.IO

import java.io.File

object Utils {
  // This function will parse the args and return a file path
  def parseArgs(args: List[String]): IO[String] = {
    if (args.length < 1) {
      IO.raiseError(new IllegalArgumentException("Pleas specify the json file path"))
    }
    else if (args.length > 1) {
      IO.raiseError(new IllegalArgumentException("The app only takes one argument"))
    }
    else IO.pure(args.head)
  }

  def buildFile(filePath: String): IO[File] = {
    IO(new File(filePath))
  }
}

