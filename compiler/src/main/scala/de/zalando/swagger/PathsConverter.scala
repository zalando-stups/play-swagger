package de.zalando.swagger

import java.net.URI

import de.zalando.apifirst.Application.{ParameterLookupTable, ParameterRef, ApiCall, HandlerCall}
import de.zalando.apifirst.Http.{MimeType, Verb}
import de.zalando.apifirst._
import Path.FullPath
import de.zalando.swagger.strictModel._

/**
  * @author  slasch 
  * @since   20.10.2015.
  */
class PathsConverter(val base: URI, val model: SwaggerModel, val keyPrefix: String, params: ParameterLookupTable,
                     val definitionFileName: Option[String] = None)
  extends ParameterNaming with HandlerGenerator with ParameterReferenceGenerator {

  lazy val convert = fromPaths(model.paths, model.basePath)

  import new_naming._

  private def fromPath(basePath: BasePath)(pathDef: (String, PathItem)) = {
    implicit val (url, path) = pathDef
    for {
      operationName     <- path.operationNames
      verb              <- verbFromOperationName(operationName)
      operation         = path.operation(operationName)
      namePrefix        = base / url / operationName
      params            = parameters(path, operation, namePrefix)
      astPath           = Path.path2path(url, params)
      handlerCall       <- handler(operation, path, params, operationName, astPath).toSeq
      results           = resultTypes(namePrefix, operation)
      (mimeIn, mimeOut) = mimeTypes(operation)
      errMappings       = errorMappings(path, operation)
    } yield ApiCall(verb, astPath, handlerCall, mimeIn, mimeOut, errMappings, results)
  }

  private def fromPaths(paths: Paths, basePath: BasePath) = Option(paths).toSeq.flatten flatMap fromPath(basePath)

  private def resultTypes(prefix: Reference, operation: Operation): Iterable[ParameterRef] = {
    operation.responses map {
      case (code, definition) if code.forall(_.isDigit) => {
        ParameterRef(prefix.prepend("resultTypes") / code)
      }
      case ("default", definition)  =>
        ParameterRef(prefix.prepend("resultTypes") / "default")
      case (other, _) =>
        throw new IllegalArgumentException(s"Expected numeric error code or 'default', but was $other")
    }
  }

  private def parameters(path: PathItem, operation: Operation, namePrefix: Reference) = {
    val pathParams        = fromParameterList(path.parameters, namePrefix)
    val operationParams   = fromParameterList(operation.parameters, namePrefix)
    val simpleNames       = operationParams map (_.simple)
    pathParams.filterNot { p => simpleNames.contains(p.simple) } ++ operationParams.toSet
  }

  private def fromParameterList(parameters: ParametersList, parameterNamePrefix: Reference): Seq[Application.ParameterRef] = {
    Option(parameters).toSeq.flatten map refFromParametersListItem(parameterNamePrefix)
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
    (mimeIn, mimeOut)
  }

  def orderedMediaTypeList(hiPriority: MediaTypeList, lowPriority: MediaTypeList): Set[MimeType] = {
    Option(hiPriority).orElse(Option(lowPriority)).toSet.flatten.map {
      new MimeType(_)
    }
  }

}

// TODO use ScalaName here
trait HandlerGenerator extends StringUtil {
  def keyPrefix: String
  def model: SwaggerModel
  def definitionFileName: Option[String]
  def handler(operation: Operation, path: PathItem, params: Seq[Application.ParameterRef], verb: String, callPath: FullPath): Option[HandlerCall] = for {
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
      val controller = definitionFileName map { ScalaName.capitalize("\\.", _) } getOrElse {
        throw new IllegalStateException(s"The definition file name must be defined in order to use '$keyPrefix-package' directive")
      }
      val method = Option(operation.operationId).map(ScalaName.camelize(" ", _)) getOrElse {
        verb.toLowerCase + ScalaName.capitalize("/", path.string("by/" + _.value))
      }
      s"$pkg.$controller.$method"
    }
  }
}