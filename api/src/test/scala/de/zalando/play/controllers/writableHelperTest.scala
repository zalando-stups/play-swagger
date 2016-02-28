package de.zalando.play.controllers

import de.zalando.play.controllers.ResponseWriters.choose
import org.specs2.mutable.Specification
import play.api.http.Writeable
import scala.concurrent.ExecutionContext.Implicits

/**
  * @author slasch 
  * @since 28.02.2016.
  */
class ResponseWritersTest extends Specification {

  import TestEnvironment._

  "ResponseWriters$Test" should {
    "find something" in {
      choose("text/plain")[Any](reg) must_== Some(seqText.w)
    }
    "not find anything for wrong mime type" in {
      choose("application/json")[Any](reg) must_== None
    }
    "find something for correct Writable type" in {
      choose("text/plain")[Seq[Any]](reg) must_== Some(seqText.w)
    }
    "find something for different correct Writable type" in {
      choose("text/plain")[String](reg) must_== Some(stringText.w)
    }
    "not find anything for wrong Writable type" in {
      choose("text/plain")[Numeric[_]](reg) must_== None
    }
    "not find anything by default" in {
      choose("text/plain")[Any]() must_== None
    }

  }
}

object TestEnvironment {
  import Implicits.global
  val transformSeq: Seq[Any] => Array[Byte] = a => a.toString.getBytes("UTF-8")
  val transformStr: String => Array[Byte] = a => a.toString.getBytes("UTF-8")

  val seqText: WriteableWrapper[Seq[Any]] = Writeable(transformSeq, Some("text/plain"))

  val stringText: WriteableWrapper[String] = Writeable(transformStr, Some("text/plain"))

  val reg = Seq(seqText, stringText)
}