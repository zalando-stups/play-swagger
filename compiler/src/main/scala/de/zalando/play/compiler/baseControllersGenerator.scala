package de.zalando.play.compiler

import de.zalando.apifirst.Application.{Parameter, ApiCall, Model}
import de.zalando.apifirst.Domain.TypeName

/**
 * @since 10.09.2015
 */
object BaseControllersGenerator extends ChainedGenerator  {

  override val mainTemplate = s"""
                   |#PACKAGE#
                   |
                   |import play.api.mvc.{Action, Controller}
                   |import de.zalando.play.controllers.PlayBodyParsing
                   |import PlayBodyParsing._
                   |
                   |#IMPORT#
                   |
                   |/**
                   | * @since #NOW#
                   | */
                   |""".stripMargin

  override val namespaceTemplate = "#CONTROLLERS#"

  override def generators: List[GeneratorBase] = new SingleBaseControllerGenerator :: Nil
}

class SingleBaseControllerGenerator extends GeneratorBase {
  override val placeHolder = "#CONTROLLERS#"

  val template =
    """
      |  private val #CALL#ResponseMimeType = "#RESPONSE_MIME_TYPE#"
      |  private val #CALL#ActionSuccessStatus = Status(#SUCCESS_STATUS#)
      |
      |  #TYPE_DEFINITIONS#
      |  #ERROR_MAPPING#
      |  #PARSER_DEF#
      |
      |  def #CALL#Action = (f: #CALL#ActionType) => #PARAMETER_LIST#Action#PARSER#
      |    val result = new ValidationFor#CONTROLLER##CALL#(#VALIDATOR_PARAMS#).result.right.map {
      |      processValid#CALL#Request(f)
      |    }
      |    implicit val marshaller = parsingErrors2Writable(#CALL#ResponseMimeType)
      |    val response = result.left.map { BadRequest(_) }
      |    response.fold(♀ => ♀, ♂ => ♂)
      |  }
      |
      |  private def processValid#CALL#Request(f: #CALL#ActionType)(request: #CALL#ActionRequestType) = {
      |    val callerResult = f(request)
      |    val status = callerResult match {
      |      case Left(error) => (errorToStatus#CALL# orElse defaultErrorMapping)(error)
      |      case Right(result) => #CALL#ActionSuccessStatus
      |    }
      |    implicit val #CALL#WritableJson = anyToWritable[#CALL#ActionResultType](#CALL#ResponseMimeType)
      |    status(callerResult)
      |  }
    """.stripMargin

  def errorMapping(call: ApiCall) = {
    val lines = call.errorMapping map { case (code, exception) =>
      exception map { e =>
        s"""case _: ${e.getCanonicalName} => Status($code)"""
      } mkString "\n"
    }
    val pf = if (lines.isEmpty) "PartialFunction.empty[Throwable, Status]" else s"{\n${lines.mkString("\n")}\n}"

    s"""private def errorToStatus#CALL#: PartialFunction[Throwable, Status] = $pf"""
  }

  def parameterList(parameters: Seq[Parameter]) =
    if (parameters.isEmpty) ""
    else parameters.map { p =>
      s"${p.name}: ${TypeName.escapeName(p.typeName.name.asSimpleType)}"
    }.mkString("(", ", ", ")") + " => "

  def validatorParameters(parameters: Seq[Parameter]) =
    if (parameters.isEmpty) ""
    else parameters.map { _.name} mkString ", "

  // TODO bodyParameters - is it Seq or Opt ?
  def parser(call: ApiCall) =
    if (call.handler.bodyParameters.nonEmpty)
      s"""(#CALL#Parser()) { request =>
        |${PAD}val ${TypeName.escapeComplexName(call.handler.bodyParameters.head.name.simple)} = request.body
      """.stripMargin
    else " {"

  def typeDefinitions(call: ApiCall) = {
    val requestType = call.handler.parameters.map { _.typeName.name.asSimpleType }.mkString("(", ", ", ")") // FIXME
    val resultType = call.resultType.head._2.name.asSimpleType
    val definitions =
      s"""private type #CALL#ActionRequestType = $requestType
        |  private type #CALL#ActionResultType = $resultType
        |  private type #CALL#ActionType = #CALL#ActionRequestType => Either[Throwable, #CALL#ActionResultType]
        |""".stripMargin
    (definitions, requestType, resultType)
  }
  def parserDefinition(parser: String, requestType: String) =
    if (parser.contains("#CALL#Parser"))
      s"""private def #CALL#Parser(maxLength: Int = parse.DefaultMaxTextLength) = anyParser[#CALL#ActionRequestType]("$requestType", "Invalid $requestType", maxLength)""".stripMargin
    else
      ""

  override def generate(namespace: String)(implicit ast: Model): Iterable[(Set[String], String)] = {
    val result = for {
      (pkg, pkgFiles) <- ControllersGenerator.groupByFile(ast)
      (controller, calls) <- pkgFiles
    } yield {
      val params = calls.flatMap(_.handler.parameters)
      val imports = params.flatMap {_.typeName.imports}.toSet
      val methods = calls map { call =>
        val callName = call.handler.method
        val (definitions, requestType, resultType) = typeDefinitions(call)
        val parserCode = parser(call)
        template.
          replaceAll("#RESPONSE_MIME_TYPE#", call.mimeOut.head.name).
          replaceAll("#ERROR_MAPPING#", errorMapping(call)).
          replaceAll("#TYPE_DEFINITIONS#", definitions).
          replaceAll("#SUCCESS_STATUS#", call.resultType.head._1.toString).
          replaceAll("#PARSER#", parserCode).
          replaceAll("#PARSER_DEF#", parserDefinition(parserCode, requestType)).
          replaceAll("#PARAMETER_LIST#", parameterList(call.handler.parameters)).
          replaceAll("#VALIDATOR_PARAMS#", validatorParameters(call.handler.parameters)).
          replaceAll("#CALL#", callName) // should be the last replacement
      }
      val typeImports = ImportsGenerator.importStatements(ast, "definitions", None)

      val body = s"""
      |import definitions._
      |#TYPE_IMPORTS#
      |package $pkg {
      |trait #CONTROLLER#Base extends Controller with PlayBodyParsing {
      |${methods.mkString("\n")}
      |}
      |}""".stripMargin.
        replaceAll("#CONTROLLER#", controller).
        replaceAll("#TYPE_IMPORTS#", typeImports)
      (imports, body)
    }
    result.toSeq
  }

}