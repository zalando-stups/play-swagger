package de.zalando.swagger

import de.zalando.apifirst
import de.zalando.apifirst.Path.InPathParameter
import de.zalando.apifirst.{Domain, Application, Http}
import apifirst.Application.{ApiCall, Model}
import de.zalando.swagger.model.{Items, Path, Operation, SwaggerModel}

import scala.language.{postfixOps, implicitConversions}

/**
 * @since 17.07.2015
 */
object Swagger2Ast extends HandlerParser {
  implicit def convert(keyPrefix: String)(model: SwaggerModel): Model = Model(model.paths flatMap toCall(keyPrefix) toList)

  import Http.string2verb

  def toCall(keyPrefix: String)(path: (String, model.Path)): Option[ApiCall] = {
    val call = operation(path) flatMap { case (operation: Operation, signature: String) =>
        for {
          verb <- string2verb(signature)
          pathParams = pathParameters(operation)
          astPath = apifirst.Path.path2path(path._1, pathParams)
          handlerText <- operation.vendorExtensions.get(s"$keyPrefix-handler")
          parseResult = parse(handlerText)
          handler <- if (parseResult.successful) Some(parseResult.get.copy(parameters = pathParams)) else None
        } yield Some(ApiCall(verb, astPath, handler))
      case _ =>
        None
    }
    call.flatten
  }

  private def pathParameters(operation: model.Operation): Seq[Application.Parameter] =
    Option(operation.parameters).map(_.filter(_.pathParameter) map pathParameter).toSeq.flatten

  private def pathParameter(p: model.Parameter): Application.Parameter = {
    val fixed = None // TODO check difference between fixed and default
    val default = Option(p.default)
    val typeName = swaggerType2Type(p.kind, p.items)
    Application.Parameter(p.name, typeName, fixed, default, InPathParameter.constraint, InPathParameter.encode)
  }

  private def operation(path: (String, Path)) = {
    import scala.reflect.runtime.universe._

    val signatures = typeOf[Path].members.filter(_.typeSignature =:= typeOf[Operation]).iterator.toSeq.reverseIterator
    val ops = path._2.productIterator.zip(signatures.map(_.name.decoded)).filter(_._1 != null).toList
    ops.headOption
  }

  private def swaggerType2Type(name: String, items: Items): Domain.Type = name.toLowerCase match {
    case "string" => Domain.Str
    case "integer" => Domain.Int
    case "boolean" => Domain.Bool
    case "array" => Domain.Arr(swaggerType2Type(items.kind, items.items)) // TODO only for arrays with defined item types first
    case _ => Domain.Str // TODO custom types ? check specification
  }
}

