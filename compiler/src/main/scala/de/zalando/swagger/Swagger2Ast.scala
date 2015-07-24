package de.zalando.swagger

import de.zalando.apifirst
import apifirst.Http
import apifirst.Application.{ApiCall, Model}
import de.zalando.swagger.model.{Path, Operation, SwaggerModel}

import scala.language.implicitConversions
import scala.reflect.runtime.universe._

/**
 * @since 17.07.2015
 */
object Swagger2Ast extends HandlerParser {
  implicit def convert(model: SwaggerModel): Model = Model(model.paths flatMap toCall toList)

  import Http.string2verb

  def toCall(path: (String, model.Path)): Option[ApiCall] = {
    val call = operation(path) flatMap { case (operation: Operation, signature: Symbol) =>
        for {
          verb <- string2verb(signature.name.decoded)
          defaults = pathData(path)
          astPath = apifirst.Path.path2path(path._1, defaults)
          handlerText <- operation.vendorExtensions.get("x-api-first-handler")
          parseResult = parse(handlerText)
          handler <- if (parseResult.successful) Some(parseResult.get) else None
        } yield Some(ApiCall(verb, astPath, null, null, handler, null, null)) // TODO convert query, body, handler, mimeIn, mimeOut as well
      case _ =>
        None
    }
    call.flatten
  }

  private def pathData(path: (String, model.Path)): Map[String, (String, Boolean)] = Map.empty[String, (String, Boolean)]

  private def operation(path: (String, Path)) = {
    val signatures = typeOf[Path].members.filter(_.typeSignature =:= typeOf[Operation]).iterator.toSeq.reverseIterator
    val ops = path._2.productIterator.zip(signatures).filter(_._1 != null).toList
    ops.headOption
  }
}

