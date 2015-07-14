package de.zalando.swagger

import com.fasterxml.jackson.databind.{DeserializationFeature, DeserializationConfig, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import de.zalando.swagger.model.SwaggerModel

/**
 * @since 14.07.2015
 */
object parser {

  def readJson(json: String): SwaggerModel = {
    val mapper = new ObjectMapper()
    mapper.registerModule(DefaultScalaModule)
    mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
    mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true)
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    mapper.readValue(json, classOf[SwaggerModel])
  }

}
