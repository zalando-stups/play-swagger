package com.oaganalytics.apib

import de.zalando.apifirst.Application.StrictModel
import de.zalando.swagger.strictModel.SwaggerModel
import de.zalando.swagger.StrictSwaggerParser
import java.net.URI
import java.io.File

import argonaut._, Argonaut._
import scalaz._, Scalaz._
import Decoder._

object ApibParser {
  def noisyParse[T](input: String)(implicit decode: DecodeJson[T]): T = {
    val json = Parse.parse(input).fold({jsonInvalid =>
      throw new IllegalArgumentException(s"Invalid json generated by drafter.\n${jsonInvalid}\n File a bug at https://github.com/apiaryio/snowcrash")
    },
      {x => x})

    decode.decodeJson(json).result.fold({
      decodeFail =>
      val cursor = decodeFail._2.acursor(json.cursor)
      val successfulCursor = Stream.iterate(cursor)(_.up).find(!_.failed)
      val focus = successfulCursor.flatMap(_.focus)
      throw new IllegalArgumentException(s"""Incorrect json. Message:
${decodeFail._1}
Cursor, the document root is at the right, the current location at the left:
${decodeFail._2}
JSON that tripped up the decoder:
${focus}
Cursor to the above JSON:
${successfulCursor.map(_.history)}
""")
    }, {x => x})
  }

  def parse(file: File, packageName: String): StrictModel = {
    val input = scala.io.Source.fromFile(file).getLines().mkString("\n")
    val doc = noisyParse[Document](input)
    Generator.model(doc, packageName, "apib")
  }
}
