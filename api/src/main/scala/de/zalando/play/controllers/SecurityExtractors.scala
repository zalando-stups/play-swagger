package de.zalando.play.controllers

import play.api.mvc.RequestHeader
import sun.misc.BASE64Decoder

/**
  * @author slasch 
  * @since 07.03.2016.
  */
object SwaggerSecurityExtractors extends BasicAuthSecurityExtractor {

  def basicAuth[User >: Any](header: RequestHeader)(convertToUser: (String, String) => User): Option[User] =
    header.headers.get("authorization").map { basicAuth =>
      decodeBasicAuth(basicAuth).map(p => convertToUser(p._1, p._2))
    }

  def headerApiKey[User >: Any](name: String)(header: RequestHeader)(convertToUser: String => User): Option[User] =
    header.headers.get(name) map convertToUser

  def queryApiKey[User >: Any](name: String)(header: RequestHeader)(convertToUser: Seq[String] => User): Option[User] =
    header.queryString.get(name) map convertToUser

  def oAuth[User >: Any](header: RequestHeader)(convertToUser: Seq[String] => User): Option[User] =
    ???
}

trait BasicAuthSecurityExtractor {
  private val basicSt = "basic "

  protected def decodeBasicAuth(auth: String): Option[(String, String)] = {
    lazy val basicReqSt = auth.substring(0, basicSt.length())
    lazy val basicAuthSt = auth.replaceFirst(basicReqSt, "")
    lazy val decoder = new BASE64Decoder() //BESE64Decoder is not thread safe
    lazy val decodedAuthSt = new String(decoder.decodeBuffer(basicAuthSt), "UTF-8")
    lazy val usernamePassword = decodedAuthSt.split(":")

    if (auth.length() < basicSt.length()) None
    else if (basicReqSt.toLowerCase() != basicSt) None
    else if (usernamePassword.length != 2) None
    else Some(usernamePassword.head, usernamePassword.last)
  }
}
