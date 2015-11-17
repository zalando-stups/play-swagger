package de.zalando.swagger

/**
 * @author  slasch 
 * @since   12.10.2015.
 */

import java.io.File
import java.net.URI

import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.snakeyaml.Yaml
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import de.zalando.swagger.strictModel._
import me.andrz.jackson.{ObjectMapperFactory, JsonContext, JsonReferenceProcessor}

import scala.io.Source
import scala.language.postfixOps

trait StrictParser {
  def parse(file: File): (URI, SwaggerModel)
}

/**
  * This context is needed to override default JsonContext behaviour to read contents of provided URL of File
  * We cannot do that because we need to use our 'prepared' content.
  * And we need to prepare the content because of Jackson bug in Yaml Parsing
  * @param file
  * @param contents
  * @param factory
  */
class TransientJsonContext(file: File, contents: String, factory: ObjectMapperFactory) extends JsonContext(file) {
  setUrl(file.toURI.toURL)
  private val rootNode = factory.create().readTree(contents)
  setNode(rootNode)
}

class JsonObjectMapperFactory extends ObjectMapperFactory {
  override def create: ObjectMapper = {
    val mapper = new ObjectMapper()
    configure(mapper)
  }

  def configure(mapper:ObjectMapper): ObjectMapper = {
    mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
    mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true)
  }
}

class YamlObjectMapperFactory extends JsonObjectMapperFactory {
  override def create: ObjectMapper = {
    val mapper = new ObjectMapper(new YAMLFactory())
    configure(mapper)
  }
}

private[swagger] abstract class StrictSwaggerParser extends StrictParser {

  def mapperFactory: ObjectMapperFactory

  def parse(file: File): (URI, SwaggerModel) = {
    val input = prepareFile(file)
    val node = processor.process(new TransientJsonContext(file, input, mapperFactory))
    (file.toURI, mapper.treeToValue(node, classOf[SwaggerModel]))
  }

  def mapper: ObjectMapper = {
    val mapper = mapperFactory.create

    mapper.registerModule(DefaultScalaModule)
    mapper.registerModule(deserializers.securityModule)
    mapper.registerModule(deserializers.parametersListItemModule)

    mapper
  }

  lazy val processor = {
    val p = new JsonReferenceProcessor()
    p.setMapperFactory(mapperFactory)
    p.setStopOnCircular(false)
    p.setMaxDepth(200)
    p.setPreserveRefs(true)
    p
  }

  def prepareFile(file: File) =
    Source.fromFile(file).getLines().mkString("\n")

}

object StrictYamlParser extends StrictSwaggerParser {

  lazy val mapperFactory = new YamlObjectMapperFactory

  /**
    * The workaround for jackson not able to parse extended yaml syntax
    * @param file
    * @return
    */
  override def prepareFile(file: File) = {
    val input = super.prepareFile(file)
    val yaml = new Yaml
    val normalized = yaml.load(input)
    mapper.writeValueAsString(normalized)
  }
}

object StrictJsonParser extends StrictSwaggerParser {
  lazy val mapperFactory = new JsonObjectMapperFactory
}

