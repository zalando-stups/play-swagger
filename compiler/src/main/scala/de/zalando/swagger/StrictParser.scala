package de.zalando.swagger

/**
 * @author  slasch 
 * @since   12.10.2015.
 */

import java.io.File
import java.net.URI

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.snakeyaml.Yaml
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import de.zalando.swagger.strictModel._

import scala.io.Source
import scala.language.postfixOps

trait StrictParser {
  def parse(file: File): (URI, SwaggerModel)
}

private[swagger] abstract class StrictSwaggerParser extends StrictParser {
  def factory: JsonFactory

  def parse(file: File): (URI, SwaggerModel) = {
    val input = prepareFile(file)
    (file.toURI, mapper.readValue(input.getBytes, classOf[SwaggerModel]))
  }

  lazy val mapper: ObjectMapper = {
    val mapper = new ObjectMapper(factory)
    mapper.registerModule(DefaultScalaModule)
    mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
    mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true)

    mapper.registerModule(deserializers.securityModule)
    mapper.registerModule(deserializers.parametersListItemModule)

    mapper
  }

  def prepareFile(file: File) =
    Source.fromFile(file).getLines().mkString("\n")

}

object StrictYamlParser extends StrictSwaggerParser {
  val factory = new YAMLFactory()
  override def prepareFile(file: File) = {
    val input = super.prepareFile(file)
    val yaml = new Yaml
    val normalized = yaml.load(input)
    mapper.writeValueAsString(normalized)
  }
}

object StrictJsonParser extends StrictSwaggerParser {
  val factory = new JsonFactory()
}