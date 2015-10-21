package de.zalando.swagger

import de.zalando.apifirst.Application.{ApiCall, HandlerCall}
import de.zalando.apifirst.Domain.Type
import de.zalando.apifirst.Http.{MimeType, Verb}
import de.zalando.apifirst.Path.FullPath
import de.zalando.apifirst._
import de.zalando.swagger.strictModel._

/**
  * @author  slasch 
  * @since   20.10.2015.
  */
class PathsConverter(val keyPrefix: String, val model: SwaggerModel, typeDefs: ParameterNaming#NamedTypes,
                     val definitionFileName: Option[String] = None)
  extends ParameterNaming with HandlerGenerator {

  private val FIXED = None // There is no way to define fixed parameters in swagger spec

  lazy val convert = fromPaths(model.paths, model.basePath)

  private def fromPath(basePath: BasePath)(pathDef: (String, PathItem)) = {
    implicit val (url, path) = pathDef
    for {
      operationName     <- path.operationNames
      verb              <- verbFromOperationName(operationName)
      operation         = path.operation(operationName)
      namePrefix        = append(url, operationName)
      params            = parameters(path, operation, namePrefix)
      astPath           = Path.path2path(url, params)
      handlerCall       <- handler(operation, path, params, operationName, astPath).toSeq
      results           = resultTypes(namePrefix, operation)
      (mimeIn, mimeOut) = mimeTypes(operation)
      errMappings       = errorMappings(path, operation)
    } yield ApiCall(verb, astPath, handlerCall, mimeIn, mimeOut, errMappings, results)
  }

  private def fromPaths(paths: Paths, basePath: BasePath) = Option(paths).toSeq.flatten flatMap fromPath(basePath)

  private def resultTypes(prefix: String, operation: Operation): Map[Int, Type] =
    operation.responses map { resp =>
      resp._1.toInt -> findType(prefix, resp._1)._2
    }

  private def parameters(path: PathItem, operation: Operation, namePrefix: String) = {
    val pathParams        = fromParameterList(path.parameters, namePrefix)
    val operationParams   = fromParameterList(operation.parameters, namePrefix)
    pathParams ++ operationParams
  }

  private def fromParameterList(parameters: ParametersList, parameterNamePrefix: SimpleTag): Seq[Application.Parameter] = {
    Option(parameters).toSeq.flatten map fromParametersListItem(parameterNamePrefix)
  }

  private def verbFromOperationName(operationName: String): Seq[Verb] =
    Http.string2verb(operationName).orElse {
      throw new scala.IllegalArgumentException(s"Could not parse HTTP verb $operationName")
    }.toSeq

  private def errorMappings(path: PathItem, operation: Operation) =
    Seq(operation.vendorErrorMappings, path.vendorErrorMappings, model.vendorErrorMappings).
      filter(_ != null).reduce(_ ++ _).toSet.toMap // TODO check that operation > path > model

  private def mimeTypes(operation: Operation) = {
    val mimeIn = orderedMediaTypeList(operation.consumes, model.consumes)
    val mimeOut = orderedMediaTypeList(operation.produces, model.produces)
    require(mimeIn.nonEmpty)
    require(mimeOut.nonEmpty)
    (mimeIn, mimeOut)
  }

  def orderedMediaTypeList(hiPriority: MediaTypeList, lowPriority: MediaTypeList): Set[MimeType] = {
    Option(hiPriority).orElse(Option(lowPriority)).toSet.flatten.map {
      new MimeType(_)
    }
  }

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

trait HandlerGenerator extends StringUtil {
  def keyPrefix: String
  def model: SwaggerModel
  def definitionFileName: Option[String]
  def handler(operation: Operation, path: PathItem, params: Seq[Application.Parameter], verb: String, callPath: FullPath): Option[HandlerCall] = for {
    handlerText <- getOrGenerateHandlerLine(operation, path, verb, callPath)
    parseResult = HandlerParser.parse(handlerText)
    handler <- if (parseResult.successful) Some(parseResult.get) else None
  } yield handler.copy(parameters = params)

  private def getOrGenerateHandlerLine(operation: Operation, path: PathItem, verb: String, callPath: FullPath): Option[String] =
    operation.vendorExtensions.get(s"$keyPrefix-handler") orElse
      path.vendorExtensions.get(s"$keyPrefix-handler") orElse
      generateHandlerLine(operation, callPath, verb)

  private def generateHandlerLine(operation: Operation, path: FullPath, verb: String): Option[String] = {
    model.vendorExtensions.get(s"$keyPrefix-package") map { pkg =>
      val controller = definitionFileName map { capitalize("\\.", _) } getOrElse {
        throw new IllegalStateException(s"The definition file name must be defined in order to use '$keyPrefix-package' directive")
      }
      val method = Option(operation.operationId).map(camelize(" ", _)) getOrElse {
        verb.toLowerCase + capitalize("/", path.string("by/" + _.value))
      }
      s"$pkg.$controller.$method"
    }
  }
}