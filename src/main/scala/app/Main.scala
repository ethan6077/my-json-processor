package app

import app.models.{AppError, KeyValuePair}
import cats.effect.{ExitCode, IO, IOApp}
import io.circe.Decoder.Result
import io.circe.literal.JsonStringContext
import io.circe.parser._
import io.circe.{Json, ParsingFailure}

object Hello extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    for {
      _ <- IO(println("IOApp started ..."))
      rawJson <- IO.pure(buildRawJson)
      parsedJson <- readJsonFromString(rawJson.noSpaces)
      _ <- processJson(parsedJson)
      _ <- IO(println(s"Processed JSON file successfully!"))
    } yield ExitCode.Success
  }

  private def buildRawJson: Json = {
    json"""{
            "aaa": "lala",
            "bbb": "dodo",
            "ccc": "baba"
          }"""
  }

  private def readJsonFromString(jsonInString: String): IO[Json] = {
    println("reading json from a string ...")
    println(s"jsonInString: $jsonInString")

    val result: Either[ParsingFailure, Json] = parse(jsonInString)

    result match {
      case Right(json) => IO.pure(json)
      case Left(parsingFailure: ParsingFailure) =>
        IO.raiseError(
          AppError(s"parsing json string error caused by: ${parsingFailure.message}")
        )
    }
  }

  private def processJson(json: Json): IO[Unit] = {
//    val printableKeys: String = getPrintableKeys(getKeys(json))
//     IO(println(s"keys: $printableKeys"))

    val values: List[KeyValuePair] = getValues(json)
    IO(println(s"values ===> ${getPrintableValues(values)}"))
  }

  private def getKeys(json: Json): Option[List[String]] = {
    json.hcursor.keys.map(_.toList)
  }

  private def getPrintableKeys(keys: Option[List[String]]): String = {
    keys.map(_.mkString(", ")).getOrElse("")
  }

  private def getValueByKey(json: Json, key: String): String = {
    val decodeRes: Result[String] = json.hcursor.downField(key).as[String]

    decodeRes match {
      case Right(value) => value
      case Left(_) => ""
    }
  }

  private def getValues(json: Json): List[KeyValuePair] = {
    val keys: Option[List[String]] = getKeys(json)
    keys match {
      case Some(keyList) => keyList.map {
        k: String => KeyValuePair(k, getValueByKey(json, k))
      }
      case None => List()
    }
  }

  private def getPrintableValues(values: List[KeyValuePair]): String = {
    values.map(_.toString).mkString(", ")
  }

}
