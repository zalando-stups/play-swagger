package de.zalando.swagger

import de.zalando.apifirst.Application.{ParameterLookupTable, ParameterRef}
import de.zalando.apifirst.Domain.Type
import de.zalando.apifirst._
import de.zalando.apifirst.new_naming.{JsonPointer, Reference}
import de.zalando.swagger.strictModel._

/**
  * @author  slasch 
  * @since   03.11.2015.
  */
class ParametersConverter(val keyPrefix: String, val model: SwaggerModel, typeDefs: ParameterNaming#NamedTypes,
                     val definitionFileName: Option[String] = None)
  extends ParameterNaming with HandlerGenerator {

  private val FIXED = None // There is no way to define fixed parameters in swagger spec

  lazy val parameters: ParameterLookupTable = fromPaths(model.paths, model.basePath).flatten.toMap

  private def fromPaths(paths: Paths, basePath: BasePath) =
    Option(paths).toSeq.flatten flatMap fromPath(basePath)

  import new_naming.stringToReference

  private def fromPath(basePath: BasePath)(pathDef: (String, PathItem)) = {
    implicit val (url, path) = pathDef
    val escapedUrl = JsonPointer.escape(url)
    for {
      operationName     <- path.operationNames
      operation         = path.operation(operationName)
      namePrefix        = escapedUrl / operationName
    } yield parameters(path, operation, namePrefix)
  }

  private def parameters(path: PathItem, operation: Operation, namePrefix: Reference) = {
    val pathParams        = fromParameterList(path.parameters, namePrefix)
    val operationParams   = fromParameterList(operation.parameters, namePrefix)
    pathParams ++ operationParams
  }

  private def fromParameterList(parameters: ParametersList, parameterNamePrefix: Reference): ParameterLookupTable = {
    Option(parameters).toSeq.flatten map fromParametersListItem(parameterNamePrefix) toMap
  }

  // for each parameter defined inline, create definition in "inlineParameters"
  // then, create a parameter reference pointing to this parameter
  // convert existing references as well
  // TODO namings here must be replaced with something more generic
  @throws[MatchError]
  private def fromParametersListItem(prefix: Reference)(li: ParametersListItem): (Application.ParameterRef, Application.Parameter) = li match {
    case jr @ JsonReference(ref) =>
      val paramRef = ParameterRef(prefix / JsonPointer(ref).simple)
      paramRef -> fromExplicitParameter(prefix, ref)
    case bp: BodyParameter[_] =>
      val (name, parameter) = fromBodyParameter(prefix, bp)
      val ref = ParameterRef(name)
      ref -> parameter
    case nbp: NonBodyParameterCommons[_, _] =>
      val (name, parameter) = fromNonBodyParameter(prefix, nbp)
      val ref = ParameterRef(name)
      ref -> parameter
  }

  private def fromExplicitParameter(prefix: Reference, ref: String): Application.Parameter = {
    val default = None
    val parameter = model.parameters.find { case (_, p) =>
      p.name == ref.simple
    } map {
      _._2
    } getOrElse {
      throw new IllegalStateException(s"Could not find parameter definition for reference $ref")
    }
    val (_, typeDef) = findType(prefix, parameter.name)
    val (constraint, encode) = Constraints(parameter.in)
    Application.Parameter(parameter.name, typeDef, FIXED, default, constraint, encode, ParameterPlace.BODY)
  }

  private def fromBodyParameter(prefix: Reference, p: BodyParameter[_]): (Reference, Application.Parameter) = {
    val default = None
    val (name, typeDef) = findType(prefix, p.name)
    val (constraint, encode) = Constraints(p.in)
    name -> Application.Parameter(p.name, typeDef, FIXED, default, constraint, encode, ParameterPlace.BODY)
  }

  private def fromNonBodyParameter(prefix: Reference, p: NonBodyParameterCommons[_, _]): (Reference, Application.Parameter) = {
    val default = if (p.required) Option(p.default).map(_.toString) else None
    val (name, typeDef) = findType(prefix, p.name)
    val (constraint, encode) = Constraints(p.in)
    val place = ParameterPlace.withName(p.in)
    name -> Application.Parameter(p.name, typeDef, FIXED, default, constraint, encode, place)
  }

  private def findType(prefix: Reference, paramName: String): NamedType = {
    val name = prefix / paramName
    val typeDef = typeDefByName(name) orElse findTypeByPath(prefix, paramName) getOrElse {
      println(typeDefs.mkString("\n"))
      throw new IllegalStateException(s"Could not find type definition with a name $name")
    }
    (name, typeDef)
  }

  private def findTypeByPath(fullPath: Reference, name: String): Option[Type] =
    typeDefByName(fullPath.parent / "" / name)

  private def typeDefByName(name: Reference): Option[Type] =
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
