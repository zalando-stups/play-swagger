package com.oaganalytics.apib
import argonaut._, Argonaut._
import scalaz._, Scalaz._

object Utils {
  def json[T: DecodeJson](json: String, check: T => Unit) =
    check(ApibParser.noisyParse[T](json))
}
