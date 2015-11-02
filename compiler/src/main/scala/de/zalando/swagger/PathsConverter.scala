package de.zalando.swagger

import de.zalando.apifirst.Application.{ParameterRef, ApiCall, HandlerCall}
import de.zalando.apifirst.Domain.{Type, newnaming}
import newnaming._
import de.zalando.apifirst.Http.{MimeType, Verb}
import de.zalando.apifirst._
import Path.FullPath
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
    import newnaming.dsl._
    implicit val (url, path) = pathDef
    for {
      operationName     <- path.operationNames
      verb              <- verbFromOperationName(operationName)
      operation         = path.operation(operationName)
      namePrefix        = url / operationName
      refsAndParamDefs  = parameters(path, operation, namePrefix)
      refs              = refsAndParamDefs.map(_._1)
      astPath           = Path.path2path(url, refs)
      handlerCall       <- handler(operation, path, refs, operationName, astPath).toSeq
      results           = resultTypes(namePrefix, operation)
      (mimeIn, mimeOut) = mimeTypes(operation)
      errMappings       = errorMappings(path, operation)
      parametersWithRef = refsAndParamDefs.filter(_._2.isDefined) map { pair => pair._1 -> pair._2.get }
    } yield (ApiCall(verb, astPath, handlerCall, mimeIn, mimeOut, errMappings, results), parametersWithRef)
  }

  private def fromPaths(paths: Paths, basePath: BasePath) = Option(paths).toSeq.flatten flatMap fromPath(basePath)

  private def resultTypes(prefix: Named, operation: Operation): Map[String, Type] =
    operation.responses map {
      case (code, definition) if code.forall(_.isDigit) => code -> findType(prefix, code)._2
      case ("default", definition)  => "default" -> findType(prefix, "default")._2
      case (other, _)               => throw new IllegalArgumentException(s"Expected numeric error code or 'default', but was $other")
    }

  private def parameters(path: PathItem, operation: Operation, namePrefix: Named) = {
    val pathParams        = fromParameterList(path.parameters, namePrefix)
    val operationParams   = fromParameterList(operation.parameters, namePrefix)
    pathParams ++ operationParams
  }

  private def fromParameterList(parameters: ParametersList, parameterNamePrefix: Named): Seq[(Application.ParameterRef, Option[Application.Parameter])] = {
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
    (mimeIn, mimeOut)
  }

  def orderedMediaTypeList(hiPriority: MediaTypeList, lowPriority: MediaTypeList): Set[MimeType] = {
    Option(hiPriority).orElse(Option(lowPriority)).toSet.flatten.map {
      new MimeType(_)
    }
  }


  // for each parameter defined inline, create definition in "inlineParameters"
  // then, create a parameter reference pointing to this parameter
  // convert existing references as well
  // TODO namings here must be replaced with something more generic
  @throws[MatchError]
  private def fromParametersListItem(prefix: Named)(li: ParametersListItem): (Application.ParameterRef, Option[Application.Parameter]) = li match {
    case jr @ JsonReference(ref) =>
      ParameterRef(ParmName(ref, PathName(""))) -> None
    case bp: BodyParameter[_] =>
      val (name, parameter) = fromBodyParameter(prefix, bp)
      val ref = ParameterRef(ParmName(name.simple, PathName("inlineParameters")))
      ref -> Some(parameter)
    case nbp: NonBodyParameterCommons[_, _] =>
      val (name, parameter) = fromNonBodyParameter(prefix, nbp)
      val ref = ParameterRef(ParmName(name.simple, PathName("inlineParameters")))
      ref -> Some(parameter)
  }

  private def fromBodyParameter(prefix: Named, p: BodyParameter[_]): (Named, Application.Parameter) = {
    val default = None
    val (name, typeDef) = findType(prefix, p.name)
    val (constraint, encode) = Constraints(p.in)
    name -> Application.Parameter(p.name, typeDef, FIXED, default, constraint, encode, ParameterPlace.BODY)
  }

  private def fromNonBodyParameter(prefix: Named, p: NonBodyParameterCommons[_, _]): (Named, Application.Parameter) = {
    val default = if (p.required) Option(p.default).map(_.toString) else None
    val (name, typeDef) = findType(prefix, p.name)
    val (constraint, encode) = Constraints(p.in)
    val place = ParameterPlace.withName(p.in)
    name -> Application.Parameter(p.name, typeDef, FIXED, default, constraint, encode, place)
  }

  private def findType(prefix: Named, paramName: String): NamedType = {
    val name = prefix =?= paramName
    val typeDef = typeDefByName(name) orElse findTypeByPath(prefix, paramName) getOrElse {
      println(typeDefs.mkString("\n"))
      throw new IllegalStateException(s"Could not find type definition with a name $name")
    }
    (name, typeDef)
  }

  private def findTypeByPath(fullPath: Named, name: String): Option[Type] = {
    val (pathOption, operation) = (fullPath.parent, fullPath.simple)
    val result = for {
      path <- pathOption
      verb <- Http.string2verb(operation)
    } yield typeDefByName(path =?= name)
    result.flatten
  }

  private def typeDefByName(name: Named): Option[Type] =
    typeDefs.find(_._1 == name).map(_._2)

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