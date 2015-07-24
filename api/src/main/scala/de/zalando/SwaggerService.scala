/*
package de.zalando.brand.swagger

import java.util

import akka.actor.ActorRefFactory
import org.yaml.snakeyaml._
import spray.routing.HttpService

import scala.io.Source

class SwaggerService(implicit val actorRefFactory: ActorRefFactory)
  extends HttpService with MapJsonFormat {

  val route =
    pathPrefix("api") {
      getFromResourceDirectory("swagger/")
    } ~
    path("api" / "api.json") {
      get {
        complete {
          getSpec("api.yaml")
        }
      }
    }

  def getSpec(yamlPath: String) = {
    val yaml  = this.getClass.getClassLoader.getResource(yamlPath)
    val yamlStr = Source.fromURL(yaml).getLines().mkString("\n")
    val javaMap = new Yaml().load(yamlStr).asInstanceOf[util.Map[Any, Any]]
    javaMap
  }
}
*/
