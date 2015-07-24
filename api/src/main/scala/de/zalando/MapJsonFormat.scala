/*
package de.zalando.brand.swagger

import spray.httpx.SprayJsonSupport
import spray.json._

trait MapJsonFormat extends DefaultJsonProtocol with SprayJsonSupport {

  import scala.collection.convert.wrapAsScala._

  implicit object MapJsonFormat extends RootJsonFormat[java.util.Map[Any, Any]] {
    def write(m: java.util.Map[Any, Any]): JsObject = {
      JsObject(mapAsScalaMap(m).toMap.map { case (k, v) => k.toString -> mapValue(v) })
    }

    def writeList(m: java.util.List[Any]) = {
      val svector = iterableAsScalaIterable(m).toVector
      JsArray(svector map mapValue)
    }

    def mapValue: PartialFunction[Any, JsValue] = {
      case v: String => JsString(v)
      case v: Int => JsNumber(v)
      case v: Boolean => JsBoolean(v)
      case v: Integer => JsNumber(v)
      case v: java.util.Map[_, _] => write(v.asInstanceOf[java.util.Map[Any, Any]])
      case v: java.util.List[_] => writeList(v.asInstanceOf[java.util.List[Any]])
      case null => JsNull
      case v: Any =>
        import org.slf4j.LoggerFactory
        val logger = LoggerFactory.getLogger(this.getClass.getCanonicalName)
        logger.warn("Unknown type, serializing as String: " + v.getClass.getCanonicalName)
        JsString(v.toString)
    }

    def read(value: JsValue) = ??? // never mind
  }
}
*/
