package de.zalando.swagger

import java.net.URL

import de.zalando.apifirst.Application.SecurityDefinitionsTable
import de.zalando.apifirst.{ParameterPlace, Security}
import de.zalando.swagger.strictModel._

/**
 * @author  slasch 
 * @since   05.03.2016
 */
object SecurityConverter {

  def convertDefinitions(swaggerDefinitions: SecurityDefinitions): SecurityDefinitionsTable =
    if (swaggerDefinitions == null) Map.empty
    else swaggerDefinitions.collect {
      case (name, basic: BasicAuthenticationSecurity) =>
        name -> Security.Basic(Option(basic.description))
      case (name, apiKey: ApiKeySecurity) =>
        require(apiKey.name != null && apiKey.name.nonEmpty)
        require(apiKey.in != null && apiKey.in.nonEmpty)
        val place = ParameterPlace.withName(apiKey.in.toLowerCase)
        require(place == ParameterPlace.HEADER || place == ParameterPlace.QUERY)
        name -> Security.ApiKey(Option(apiKey.description), apiKey.name, place)
      case (name, oauth: Oauth2ImplicitSecurity) =>
        val authorizationUrl = new URL(oauth.authorizationUrl)
        name -> Security.OAuth2Implicit(Option(oauth.description), authorizationUrl, oauth.scopes)
      case (name, oauth: Oauth2PasswordSecurity) =>
        val tokenUrl = new URL(oauth.tokenUrl)
        name -> Security.OAuth2Password(Option(oauth.description), tokenUrl, oauth.scopes)
      case (name, oauth: Oauth2ApplicationSecurity) =>
        val tokenUrl = new URL(oauth.tokenUrl)
        name -> Security.OAuth2Application(Option(oauth.description), tokenUrl, oauth.scopes)
      case (name, oauth: Oauth2AccessCodeSecurity) =>
        val authorizationUrl = new URL(oauth.authorizationUrl)
        val tokenUrl = new URL(oauth.tokenUrl)
        name -> Security.OAuth2AccessCode(Option(oauth.description), authorizationUrl, tokenUrl, oauth.scopes)
    }
}




