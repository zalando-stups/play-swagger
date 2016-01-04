package de.zalando.play.controllers

import java.text.{ParseException, SimpleDateFormat}
import java.util._

import scala.util.Try

/**
  * An utility class for parsing date and date-time inputs as required by RFC3339
  * Based on work done by Chad Okere
  *
  * @author slasch 
  * @since 04.01.2016.
  */
object Rfc3339Util {

  private val fullDate = new SimpleDateFormat("yyyy-MM-dd")
  private val shortDateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
  private val shortDTWithTicks = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
  private val fullDTWithTicks = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")
  private val dateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ")
  fullDTWithTicks.setLenient(true)
  dateTime.setLenient(true)

  def parseDateTime(datestring: String): Try[Date] = Try {
    if(datestring.endsWith("Z")) parseFull(datestring)
    else parseParts(datestring)
  }

  def parseDate(datestring: String): Try[Date] = Try {
    fullDate.parse(datestring)
  }

  def writeDate(date: Date): String = fullDate.format(date)

  def writeDateTime(date: Date): String = dateTime.format(date)

  private def parseParts(datestring: String): Date = {
    //step one, split off the timezone.
    val firstpart = datestring.substring(0, datestring.lastIndexOf('-'))
    val secondpart = datestring.substring(datestring.lastIndexOf('-'))
    //step two, remove the colon from the timezone offset
    val thirdpart = secondpart.substring(0, secondpart.indexOf(':')) + secondpart.substring(secondpart.indexOf(':') + 1)
    val dstring = firstpart + thirdpart
    try {
      shortDateTime.parse(dstring)
    } catch {
      case pe: ParseException =>  dateTime.parse(dstring)
    }
  }

  private def parseFull(datestring: String): Date = {
    try {
      shortDTWithTicks.parse(datestring)
    } catch {
      case p: ParseException => fullDTWithTicks.parse(datestring)
    }
  }

}