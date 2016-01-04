package de.zalando.play.controllers

import java.util.Date

import org.scalatest.{MustMatchers, FunSpec}

import scala.reflect.macros.ParseException

/**
  * @author slasch 
  * @since 04.01.2016.
  */
class Rfc3339UtilTest extends FunSpec with MustMatchers {

  val date = new Date(1451911387284L)

  describe("Rfc3339UtilTest") {

    it("should parse RFC3339 DateTime") {
      Rfc3339Util.parseDateTime("2007-05-01T15:43:26-07:00").toString mustBe "Success(Wed May 02 00:43:26 CEST 2007)"
      Rfc3339Util.parseDateTime("2007-05-01T15:43:26.3-07:00").toString mustBe "Success(Wed May 02 00:43:26 CEST 2007)"
      Rfc3339Util.parseDateTime("2007-05-01T15:43:26.3452-07:00").toString mustBe "Success(Wed May 02 00:43:29 CEST 2007)"
      Rfc3339Util.parseDateTime("2007-05-01T15:43:26.3452Z").toString mustBe "Success(Tue May 01 15:43:29 CEST 2007)"
      Rfc3339Util.parseDateTime("2007-05-01T15:43:26.3Z").toString mustBe "Success(Tue May 01 15:43:26 CEST 2007)"
      Rfc3339Util.parseDateTime("2007-05-01T15:43:26Z").toString mustBe "Success(Tue May 01 15:43:26 CEST 2007)"
    }
    it("should parse RFC3339 Date") {
      Rfc3339Util.parseDate("2007-05-01").toString mustBe "Success(Tue May 01 00:00:00 CEST 2007)"
      Rfc3339Util.parseDate("2008-05-01").toString mustBe "Success(Thu May 01 00:00:00 CEST 2008)"
      Rfc3339Util.parseDate("2007-08-01").toString mustBe "Success(Wed Aug 01 00:00:00 CEST 2007)"
      Rfc3339Util.parseDate("2007-05-08").toString mustBe "Success(Tue May 08 00:00:00 CEST 2007)"
    }
    it("should write DateTime") {
      Rfc3339Util.writeDateTime(date) mustBe "2016-01-04T13:43:07.000284+0100"
    }
    it("should write Date") {
      Rfc3339Util.writeDate(date) mustBe "2016-01-04"
    }
  }
}
