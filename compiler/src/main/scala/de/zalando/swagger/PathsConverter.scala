package de.zalando.swagger

import de.zalando.apifirst.Application.{ApiCall, HandlerCall}
import de.zalando.apifirst.Domain.Type
import de.zalando.apifirst.Http.{MimeType, Verb}
import de.zalando.apifirst.{ParameterPlace, Application, Http, Path}
import de.zalando.swagger.strictModel._

/**
  * @author  slasch 
  * @since   20.10.2015.
  */
class PathsConverter(keyPrefix: String, model: SwaggerModel, typeDefs: ParameterNaming#NamedTypes)
  extends ParameterNaming with StringUtil {

  private val FIXED = None // There is no way to define fixed parameters in swagger spec

  lazy val convert = fromPaths(model.paths, model.basePath)

  private def fromPaths(paths: Paths, basePath: BasePath) = Option(paths).toSeq.flatten flatMap fromPath(basePath)

  private def resultTypes(prefix: String, operation: Operation): Map[Int, Type] =
    operation.responses map { resp =>
      resp._1.toInt -> findType(prefix, resp._1)._2
    }

  private def fromPath(basePath: BasePath)(pathDef: (String, PathItem)) = {
    implicit val (url, path) = pathDef
    for {
      operationName <- path.operationNames
      operation = path.operation(operationName)
      verb <- verbFromOperationName(operationName)
      parameterNamePrefix = append(url, operationName)
      pathParams = Option(path.parameters).toSeq.flatten map fromParametersListItem(parameterNamePrefix)
      operationParams = Option(operation.parameters).toSeq.flatten map fromParametersListItem(parameterNamePrefix)
      params = pathParams ++ operationParams
      astPath = Path.path2path(url, params)
      (mimeIn, mimeOut) = mimeTypes(operation)
      handlerCall <- handler(operation, path, params).toSeq
      results = resultTypes(parameterNamePrefix, operation)
    } yield ApiCall(verb, astPath, handlerCall, mimeIn, mimeOut, errorMappings(path, operation), results)
  }

  private def verbFromOperationName(operationName: String): Seq[Verb] =
    Http.string2verb(operationName).orElse {
      throw new scala.IllegalArgumentException(s"Could not parse HTTP verb $operationName")
    }.toSeq

  private def errorMappings(path: PathItem, operation: Operation) =
    Seq(operation.vendorErrorMappings, path.vendorErrorMappings, model.vendorErrorMappings).
      filter(_ != null).reduce(_ ++ _).toSet.toMap // TODO check that operation > path > model

  private def handler(operation: Operation, path: PathItem, params: Seq[Application.Parameter]): Option[HandlerCall] = for {
    handlerText <- getOrGenerateHandlerLine(operation, path)
    parseResult = HandlerParser.parse(handlerText)
    handler <- if (parseResult.successful) Some(parseResult.get) else None
  } yield handler.copy(parameters = params)

  private def mimeTypes(operation: Operation) = {
    val mimeIn = Option(operation.consumes).orElse(Option(model.consumes)).toSet.flatten.map { new MimeType(_) }
    val mimeOut = Option(operation.produces).orElse(Option(model.produces)).toSet.flatten.map { new MimeType(_) }
    require(mimeIn.nonEmpty)
    require(mimeOut.nonEmpty)
    (mimeIn, mimeOut)
  }

  private def getOrGenerateHandlerLine(operation: Operation, path: PathItem): Option[String] =
    operation.vendorExtensions.get(s"$keyPrefix-handler") orElse
      path.vendorExtensions.get(s"$keyPrefix-handler")
/* TODO add support for package
  orElse
      model.vendorExtensions.get(s"$keyPrefix-package").map { pkg =>
        val method = Option(path.operation.operationId).map(camelize(" ", _)).getOrElse(verb.toLowerCase + capitalize("/",path.string("by/" + _.value)))
        val controller = capitalize("\\.", fileName)
        s"$pkg.$controller.$method"
      }
*/

  @throws[MatchError]
  private def fromParametersListItem(prefix: String)(li: ParametersListItem): Application.Parameter = li match {
    case jr @ JsonReference(ref) => ??? // TODO this probably can only be a parameter reference ?
    case bp: BodyParameter[_] => fromBodyParameter(prefix, bp)
    case nbp: NonBodyParameterCommons[_, _] => fromNonBodyParameter(prefix, nbp)
  }

  private def fromBodyParameter(prefix: String, p: BodyParameter[_]): Application.Parameter = {
    val default = None
    val (name: String, typeDef: Type) = findType(prefix, p.name)
    val (constraint, encode) = Constraints(p.in)
    Application.Parameter(name, typeDef, FIXED, default, constraint, encode, ParameterPlace.BODY)
  }

  private def fromNonBodyParameter(prefix: String, p: NonBodyParameterCommons[_, _]): Application.Parameter = {
    val default = if (p.required) Option(p.default).map(_.toString) else None
    val (name: String, typeDef: Type) = findType(prefix, p.name)
    val (constraint, encode) = Constraints(p.in)
    val place = ParameterPlace.withName(p.in)
    Application.Parameter(name, typeDef, FIXED, default, constraint, encode, place)
  }

  private def findType(prefix: String, bp: String): (String, Type) = {
    val name = append(prefix, bp)
    val typeDef = typeDefs.find(_._1 == name).map(_._2).getOrElse {
      throw new IllegalStateException(s"Could not find type definition with a name $name")
    }
    (name, typeDef)
  }

}

object Constraints {
  private val byType: Map[String, (String, Boolean)] = Map(
    "formData" -> (".+", true),
    "path" -> ("[^/]+", true),
    "header" -> (".+", false),
    "body" -> (".+", false),
    "query" -> (".+", true)
  )
  def apply(in: String): (String, Boolean) = byType(in)
}
