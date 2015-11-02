package de.zalando.swagger

import java.io.File

import com.fasterxml.jackson.core.{JsonFactory, JsonParser => JParser, Version}
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.node.{BaseJsonNode, NullNode}
import com.fasterxml.jackson.databind.{DeserializationContext, DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.snakeyaml.Yaml
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import de.zalando.swagger.model.{Parameter, ParameterOrReference, ParameterReference, SwaggerModel}

import scala.io.Source
import scala.language.postfixOps

trait Parser {
  def parse(file: File): SwaggerModel
}

private[swagger] abstract class SwaggerParser extends Parser {
  def factory: JsonFactory

  def parse(file: File): SwaggerModel = {
    val input = prepareFile(file)
    mapper.readValue(input.getBytes, classOf[SwaggerModel])
  }

  lazy val mapper: ObjectMapper = {
    val mapper = new ObjectMapper(factory)
    mapper.registerModule(DefaultScalaModule)
    mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
    mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true)

    val deserializer = new ParameterOrReferenceDeserializer(
      "$ref" -> classOf[ParameterReference],
      "type" -> classOf[Parameter],
      "name" -> classOf[Parameter]
    )
    val module = new SimpleModule("PolymorphicParameterOrReferenceDeserializerModule", new Version(1, 0, 0, null, "", ""))
    module.addDeserializer(classOf[ParameterOrReference], deserializer)
    mapper.registerModule(module)

    mapper.registerModule(deserializers.securityModule)

    mapper
  }

  def prepareFile(file: File) =
    Source.fromFile(file).getLines().mkString("\n")

}

object YamlParser extends SwaggerParser {
  val factory = new YAMLFactory()
  override def prepareFile(file: File) = {
    val input = super.prepareFile(file)
    val yaml = new Yaml
    val normalized = yaml.load(input)
    mapper.writeValueAsString(normalized)
  }
}

object JsonParser extends SwaggerParser {
  val factory = new JsonFactory()
}

protected class ParameterOrReferenceDeserializer(mappings: (String, Class[_])*)
  extends StdDeserializer[ParameterOrReference](classOf[ParameterOrReference]) {

  import scala.collection.JavaConversions._

  private val registry = mappings.toMap

  @Override
  def deserialize(jp: JParser, ctxt: DeserializationContext): ParameterOrReference = {
    val mapper = jp.getCodec.asInstanceOf[ObjectMapper]
    val root = mapper.readTree(jp).asInstanceOf[BaseJsonNode]

    if (root == null || root == NullNode.instance) null
    else {
      val clazzez = root.fieldNames.toSeq.flatMap { registry.get }
      clazzez.headOption map { definition =>
        mapper.readValue(root.traverse, definition).asInstanceOf[ParameterOrReference]
      } orNull
    }
  }
}