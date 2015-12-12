package controllers

import java.util

import de.zalando.play.controllers.PlayBodyParsing._
import org.yaml.snakeyaml.Yaml
import play.api.mvc._

import scala.io.Source

class Swagger extends Controller {

  def swaggerSpec(name: String) = Action {
    implicit val mapMarshaller = anyToWritable[java.util.Map[_,_]]("application/json")
    Ok(getSpec(name))
  }

  private def getSpec(yamlPath: String) = {
    val yaml  = this.getClass.getClassLoader.getResource(yamlPath)
    val yamlStr = Source.fromURL(yaml).getLines().mkString("\n")
    val javaMap = new Yaml().load(yamlStr).asInstanceOf[util.Map[Any, Any]]
    javaMap
  }
}
