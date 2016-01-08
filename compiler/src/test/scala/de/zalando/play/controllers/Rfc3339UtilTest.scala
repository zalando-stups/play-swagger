package de.zalando.play.controllers

import org.joda.time.{DateTimeZone, DateTime}
import org.scalatest.{MustMatchers, FunSpec}

/**
  * @author slasch 
  * @since 04.01.2016.
  */
class Rfc3339UtilTest extends FunSpec with MustMatchers {

  val date = new DateTime(1451911387284L)

  val dtz = DateTimeZone.forID("GMT")

  describe("Rfc3339UtilTest") {

    it("should parse RFC3339 DateTime") {
      Rfc3339Util.parseDateTime("2007-05-01T15:43:26-07:00").withZoneRetainFields(dtz).toString mustBe "2007-05-02T00:43:26.000Z"
      Rfc3339Util.parseDateTime("2007-05-01T15:43:26.3-07:00").withZoneRetainFields(dtz).toString mustBe "2007-05-02T00:43:26.300Z"
      Rfc3339Util.parseDateTime("2007-05-01T15:43:26.3452-07:00").withZoneRetainFields(dtz).toString mustBe "2007-05-02T00:43:26.345Z"
      Rfc3339Util.parseDateTime("2007-05-01T15:43:26.3452Z").withZoneRetainFields(dtz).toString mustBe "2007-05-01T15:43:26.345Z"
      Rfc3339Util.parseDateTime("2007-05-01T15:43:26.3Z").withZoneRetainFields(dtz).toString mustBe "2007-05-01T15:43:26.300Z"
      Rfc3339Util.parseDateTime("2007-05-01T15:43:26Z").withZoneRetainFields(dtz).toString mustBe "2007-05-01T15:43:26.000Z"
    }
    it("should parse RFC3339 Date") {
      Rfc3339Util.parseDate("2007-05-01").withZoneRetainFields(dtz).toString mustBe "2007-05-01T00:00:00.000Z"
      Rfc3339Util.parseDate("2008-05-01").withZoneRetainFields(dtz).toString mustBe "2008-05-01T00:00:00.000Z"
      Rfc3339Util.parseDate("2007-08-01").withZoneRetainFields(dtz).toString mustBe "2007-08-01T00:00:00.000Z"
      Rfc3339Util.parseDate("2007-05-08").withZoneRetainFields(dtz).toString mustBe "2007-05-08T00:00:00.000Z"
    }
    it("should write DateTime") {
      Rfc3339Util.writeDateTime(date.withZoneRetainFields(dtz)) mustBe "2016-01-04T13:43:07.284000+0000"
    }
    it("should write Date") {
      Rfc3339Util.writeDate(date.toDateMidnight.withZoneRetainFields(dtz)) mustBe "2016-01-04"
    }
  }
}
