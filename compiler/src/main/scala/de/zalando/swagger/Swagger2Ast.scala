package de.zalando.swagger

import de.zalando.apifirst
import de.zalando.apifirst.Application.{ApiCall, Model}
import de.zalando.apifirst.Path.InPathParameter
import de.zalando.apifirst.{Application, Domain, Http}
import de.zalando.swagger.model._

import scala.language.{implicitConversions, postfixOps}

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
          queryParams = queryParameters(operation)
          allParams = pathParams ++ queryParams
          astPath = apifirst.Path.path2path(path._1, pathParams ++ queryParams)
          handlerText <- operation.vendorExtensions.get(s"$keyPrefix-handler")
          parseResult = parse(handlerText)
          handler <- if (parseResult.successful) Some(parseResult.get.copy(parameters = allParams)) else None
        } yield Some(ApiCall(verb, astPath, handler))
      case _ =>
        None
    }
    call.flatten
  }

  private def pathParameters(operation: model.Operation): Seq[Application.Parameter] =
    Option(operation.parameters).map(_.filter(_.pathParameter) map pathParameter).toSeq.flatten

  private def queryParameters(operation: model.Operation): Seq[Application.Parameter] =
    Option(operation.parameters).map(_.filter(_.queryParameter) map queryParameter).toSeq.flatten

  private def pathParameter(p: model.Parameter): Application.Parameter = {
    val fixed = None
    val default = None
    val typeName = swaggerType2Type(p)
    Application.Parameter(p.name, typeName, fixed, default, InPathParameter.constraint, InPathParameter.encode)
  }

  private def queryParameter(p: model.Parameter): Application.Parameter = {
    val fixed = None // There is no way to define fixed parameters in swagger spec
    val default = if (p.required && p.default != null) Some(p.default) else None
    val typeName = swaggerType2Type(p)
    val fullTypeName = if (p.required) typeName else Domain.Opt(typeName)
    // TODO don't use InPathParameter in the next line
    Application.Parameter(p.name, fullTypeName, fixed, default, InPathParameter.constraint, InPathParameter.encode)
  }

  private def operation(path: (String, Path)) = {
    import scala.reflect.runtime.universe._

    val signatures = typeOf[Path].members.filter(_.typeSignature =:= typeOf[Operation]).iterator.toSeq.reverseIterator
    val ops = path._2.productIterator.zip(signatures.map(_.name.decoded)).filter(_._1 != null).toList
    ops.headOption
  }

  // format: OFF
  import PrimitiveType._

  private def swaggerType2Type(p: model.CommonProperties): Domain.Type = (p.`type`, p.format) match {
    case (INTEGER, "int64")     => Domain.Lng
    case (INTEGER, "int32")     => Domain.Int
    case (INTEGER, _)           => Domain.Int

    case (NUMBER, "float")      => Domain.Flt
    case (NUMBER, "double")     => Domain.Dbl
    case (NUMBER, _)            => Domain.Dbl

    case (BOOLEAN, _)           => Domain.Bool

    case (STRING, "byte")       => Domain.Byt
    case (STRING, "date")       => Domain.Date
    case (STRING, "date-time")  => Domain.DateTime
    case (STRING, "password")   => Domain.Password
    case (STRING, _)            => Domain.Str

    case (NULL, _)              => Domain.Null

    case (OBJECT, _)            => Domain.Object

    case (ARRAY, _)             => Domain.Arr(swaggerType2Type(p.items)) // TODO only for arrays with defined item types for now

//    case (null, _) if p.
    case _                      => Domain.Unknown // TODO custom types ? check specs
  }
  // format: ON
}

