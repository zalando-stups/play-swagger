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
  def parse(file: File, packageName: String): StrictModel = {
    val input = scala.io.Source.fromFile(file).getLines().mkString("\n")
    val doc = Parse.decodeValidation[Document](input).fold({
      message => throw new IllegalArgumentException(s"Incorrect json. The document root is at the right, the current cursor at the left:\n$message")
    }, {x => x})
    Generator.model(doc, packageName, "apib")
  }
}
