package com.oaganalytics.apib
import argonaut._, Argonaut._
import scalaz._, Scalaz._

object Utils {
  def json[T: DecodeJson](json: String, check: T => Unit) =
    Parse.decodeEither[T](json) match {
      case -\/(error: String) => assert(false, error)
      case \/-(result: T) => {
        check(result)
      }
    }
}
