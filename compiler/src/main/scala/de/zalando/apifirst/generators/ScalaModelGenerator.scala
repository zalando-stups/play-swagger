package de.zalando.apifirst.generators

import de.zalando.apifirst.Application._
import de.zalando.apifirst.Domain._
import de.zalando.apifirst.ParameterPlace
import de.zalando.apifirst.ScalaName._
import de.zalando.apifirst.naming.Reference
import org.fusesource.scalate.{TemplateEngine, TemplateSource}

/**
  * @author  slasch 
  * @since   16.11.2015.
  */

class ScalaGenerator(val strictModel: StrictModel)
  extends PlayScalaControllersGenerator with ImportSupport {

  val denotationTable = AstScalaPlayEnricher(strictModel)

  val StrictModel(modelCalls, modelTypes, modelParameters, discriminators, _) = strictModel

  def model(fileName: String) = generate(fileName, Map.empty, Nil).head
  def generators(fileName: String) = generate(fileName, Map.empty, Nil)(1)
  def tests(fileName: String) = generate(fileName, Map.empty, Nil)(4)

  def controllers(fileName: String, currentVersion: String) = {
    val (unmanagedParts, unmanagedImports) = analyzeController(currentVersion)
    generate(fileName, unmanagedParts, unmanagedImports)(5)
  }

  val testsTemplateName = "play_scala_test.mustache"
  val validatorsTemplateName = "play_validation.mustache"
  val generatorsTemplateName = "generators.mustache"
  val modelTemplateName = "model.mustache"

  // TODO the order here is important because of the typeDefinitions lookup table
  def generate(fileName: String, unmanagedParts: Map[ApiCall, UnmanagedPart], unmanagedImports: Seq[String]) = Seq(
      generateModel(fileName),
      generateGenerators(fileName),
      playValidators(fileName),
      playScalaControllerBases(fileName),
      playScalaTests(fileName),
      playScalaControllers(fileName, unmanagedParts, unmanagedImports)
    )

  private def generateModel(fileName: String) = {
    if (modelTypes.values.forall(_.isInstanceOf[PrimitiveType])) ""
    else apply(fileName, modelTemplateName)
  }

  private def generateGenerators(fileName: String) = {
    if (modelTypes.isEmpty) ""
    else apply(fileName, generatorsTemplateName)
  }

  def playValidators(fileName: String) = {
    if (modelCalls.map(_.handler.parameters.size).sum == 0) ""
    else apply(fileName, validatorsTemplateName)
  }

  def playScalaTests(fileName: String) = {
    if (modelCalls.map(_.handler.parameters.size).sum == 0) ""
    else apply(fileName, testsTemplateName)
  }

  def playScalaControllers(fileName: String, unmanagedParts: Map[ApiCall, UnmanagedPart], unmanagedImports: Seq[String]) = {
    if (modelCalls.isEmpty) ""
    else apply(fileName, controllersTemplateName, unmanagedParts, unmanagedImports)
  }

  def playScalaControllerBases(fileName: String) = {
    if (modelCalls.isEmpty) ""
    else apply(fileName, controllerBaseTemplateName)
  }

  private def apply(fileName: String, templateName: String,
                    unmanagedParts: Map[ApiCall, UnmanagedPart] = Map.empty, unmanagedImports: Seq[String] = Seq.empty): String = {
    val packages = Map(
      "main_package" -> fileName.split('.').map(escape).mkString("."),
      "main_package_prefix" -> fileName.split('.').init.mkString("."),
      "main_package_suffix" -> fileName.split('.').last,
      "spec_name" -> escape(capitalize("\\.", fileName) + "Spec")
    )
    nonEmptyTemplate(packages, templateName, unmanagedParts, unmanagedImports)
  }

  private def nonEmptyTemplate(map: Map[String, Any], templateName: String,
                               unmanagedParts: Map[ApiCall, UnmanagedPart], unmanagedImports: Seq[String]): String = {
    cleanImportTable()
    val engine = new TemplateEngine

    val validations = ReShaper.filterByType("validators", denotationTable)
    val validationsByType = ReShaper.groupByType(validations.toSeq)

    val rawAllPackages = Map(
      "packages" -> Seq({
        val singlePackage = Map(

          "classes" -> ReShaper.filterByType("classes", denotationTable),
          "aliases" -> ReShaper.filterByType("aliases", denotationTable),
          "traits" -> ReShaper.filterByType("traits", denotationTable),

          "test_data_classes" -> ReShaper.filterByType("test_data_classes", denotationTable),
          "test_data_aliases" -> ReShaper.filterByType("test_data_aliases", denotationTable),

          "tests" -> ReShaper.filterByType("tests", denotationTable),

          "controllers" -> controllers(modelCalls, unmanagedParts)
        )
        singlePackage ++ validationsByType.toMap // + ("imports" -> imports(suffix))
      }),
      "controller_imports" -> controllerImports.map(i => Map("name" -> i)),
      "unmanaged_imports" -> unmanagedImports.map(i => Map("name" -> i))
    )
    val allPackages = LastListElementMarks.set(rawAllPackages)
    val template = getClass.getClassLoader.getResource(templateName)
    val templateSource = TemplateSource.fromURL(template)
    val output = engine.layout(templateSource, map ++ allPackages)
    output.replaceAll("\u2B90", "\n")
  }

}

trait PlayScalaControllersGenerator extends ImportSupport {

  val controllerImports = Seq(
    "play.api.mvc.{Action, Controller}",
    "play.api.data.validation.Constraint",
    "de.zalando.play.controllers._",
    "PlayBodyParsing._",
    "PlayValidations._"
  )

  case class UnmanagedPart(marker: ApiCall, relevantCode: String, deadCode: String)

  def strictModel: StrictModel

  def callValidations(call: ApiCall): Map[String, Any] = Map.empty // REMOVE ME

  def parametersValidations(parameters: Seq[ParameterRef]): Seq[Map[String, Any]] = Nil // REMOVE ME

  val controllersSuffix = "Action"
  val baseControllersSuffix = "Base"

  val controllersTemplateName = "play_scala_controller.mustache"
  val controllerBaseTemplateName = "play_scala_controller_base.mustache"

  val eof = "//////// EOF //////// "

  def analyzeController(currentVersion: String): (Map[ApiCall, UnmanagedPart], Seq[String]) = {
    val markers = generateMarkers(strictModel.calls)
    val allLines = currentVersion.split("\n").filter(_.trim.nonEmpty).dropWhile(_.trim.isEmpty).toSeq
    val classPositions = allLines.zipWithIndex.filter(_._1.trim.startsWith("class")).map(_._2) :+ (allLines.length +1)
    val classBlocks = if (classPositions.length > 1) classPositions.sliding(2).toSeq else Seq.empty
    val lines = classBlocks flatMap { b => allLines.slice(b.head, b(1)-3)}
    val cleanLines = lines map { _.trim }
    val markerIndexes = markers map { marker =>
      cleanLines.indexOf(marker._2._1)
    }
    val sortedMarkers = markerIndexes.sorted
    val codeParts = markerIndexes map { idx => sortedMarkers.find(_ > idx).getOrElse(lines.length)}

    val parts = markers zip markerIndexes zip codeParts filter {
      case ((((call,(marker,length)), start), end)) =>
        start >= 0
      } map { case ((((call,(marker,length)), start), end)) =>
        val code = lines.slice(start + length, end - 1)
        val relevantCode = code.takeWhile(!_.contains(eof))
        val deadCode = code.dropWhile(!_.contains(eof)).drop(1).filterNot(_.trim.isEmpty)
        val commentedOut = if (deadCode.isEmpty) "" else deadCode.mkString("/*\n", "\n", "\n*/\n")
        call -> UnmanagedPart(call, relevantCode.mkString("\n"), commentedOut)
      }
    val preservedImports = allLines.filter(_.trim.startsWith("import")).map(_.replace("import ", "")).filterNot(controllerImports.contains)
    (parts.toMap, preservedImports)
  }

  def generateMarkers(allCalls: Seq[ApiCall]) = {
    val markers = allCalls map { call =>
      val action = escape(call.handler.method + controllersSuffix)
      val method = escape(call.handler.method)
      val nameParamPair = singleOrMultipleParameters(allCalls, call, 0)
      val markerSize = if (nameParamPair._1 == "parameters?") 3 else if (nameParamPair._1 == "single_parameter?") 2 else 1
      call -> (signature(action, method), markerSize)
    }
    markers
  }

  def comment(action: String) = s"$eof $action"

  def signature(action: String, method: String): String = s"val $method = $action {"

  // TODO this part is where it's getting ugly.
  def controllers(allCalls: Seq[ApiCall], unmanagedParts: Map[ApiCall, UnmanagedPart]) = {
    allCalls groupBy { c =>
      (c.handler.packageName, c.handler.controller)
    } map { case (controller, calls) =>
      val methods = calls.zipWithIndex.map { case (call, i) =>
        val nameParamPair: (String, Option[Map[String, Any]]) = singleOrMultipleParameters(calls, call, i)

        val bodyParam = parametersValidations(call.handler.parameters.filter(strictModel.findParameter(_).place == ParameterPlace.BODY))
        val headerParams = parametersValidations(call.handler.parameters.filter(strictModel.findParameter(_).place == ParameterPlace.HEADER))
        val nonBodyParams = parametersValidations(call.handler.parameters.filterNot { p =>
          val place = strictModel.findParameter(p).place
          place == ParameterPlace.BODY || place == ParameterPlace.HEADER
        })
        val validations = callValidations(call)

        // FIXME should be already implemented in AST
        val actionResultType = call.resultTypes.headOption.map { t =>
          val tpe = strictModel.findType(t.name)
          tpe match {
            case c: Container => c.imports.head + "[" + c.tpe.name.typeAlias() + "]" // FIXME won't work with MAP
            case p: ProvidedType => p.name.typeAlias()
            case o => o.name.className
          }
        }
        val action = escape(call.handler.method + controllersSuffix)
        val method = escape(call.handler.method)
        Map(
          "response_mime_type_value" -> call.mimeOut.headOption.map(_.name).getOrElse("application/json"), // TODO implement content negotiation
          "action_result_type_value" -> actionResultType, // TODO implement content negotiation
          "action_success_status_value" -> 200, // FIXME
          "method" -> method,
          "action" -> action,
          "signature" -> signature(action, method),
          "comment" -> comment(action),
          "dead_code" -> unmanagedParts.get(call).map(_.deadCode).getOrElse(""),
          "implementation" -> unmanagedParts.get(call).map(_.relevantCode).getOrElse("???"),
          "parser_name" -> escape(call.handler.method + "Parser"),
          "writable_json" -> escape(call.handler.method + "WritableJson"),
          "action_type" -> escape(call.handler.method + "ActionType"),
          "action_result_type" -> escape(call.handler.method + "ActionResultType"),
          "action_success_status_name" -> escape(call.handler.method + "ActionSuccessStatus"),
          "action_request_type" -> escape(call.handler.method + "ActionRequestType"),
          "process_valid_request" -> escape("processValid" + call.handler.method + "Request"),
          "response_mime_type_name" -> escape(call.handler.method + "ResponseMimeType"),
          "error_to_status" -> escape("errorToStatus" + call.handler.method),
          "error_mappings" -> call.errorMapping.flatMap { case (k, v) => v.map { ex =>
            Map("exception_name" -> ex.getCanonicalName, "exception_code" -> k)
          } },
          "validations" -> validations,
          "validations?" -> validations.nonEmpty,
          "body_param" -> bodyParam,
          "non_body_params" -> nonBodyParams,
          "non_body_params?" -> nonBodyParams.nonEmpty,
          "header_params" -> headerParams,
          "body_param?" -> bodyParam.nonEmpty,
          "request_needed?" -> (bodyParam.nonEmpty || headerParams.nonEmpty),
          nameParamPair
        )
      }
      Map(
        "effective_package" -> escape(controller._1), // TODO currently, the package name just ignored
        "controller" -> escape(controller._2),
        "base" -> escape(controller._2 + baseControllersSuffix),
        "methods" -> methods
      )
    }
  }

  def singleOrMultipleParameters(calls: Seq[ApiCall], call: ApiCall, i: Int): (String, Option[Map[String, Any]]) = {
    lazy val parameters = Map(
      "parameters" -> call.handler.parameters.zipWithIndex.map { case (param, ii) =>
        val typeName = strictModel.findParameter(param).typeName.name
        Map(
          "name" -> escape(camelize("\\.", param.simple)),
          "type" -> typeName.typeAlias("", ""),
          "last" -> (ii == call.handler.parameters.size - 1)
        )
      },
      "last" -> (i == calls.size - 1)
    )
    lazy val parameter = {
      val param = call.handler.parameters.head
      val typeName = strictModel.findParameter(param).typeName.name
      Map("name" -> escape(camelize("\\.", param.simple)), "type" -> typeName.typeAlias("", ""))
    }
    val nameParamPair =
      if (call.handler.parameters.isEmpty) "" -> None
      else if (call.handler.parameters.length == 1) "single_parameter?" -> Some(parameter)
      else "parameters?" -> Some(parameters)
    nameParamPair
  }
}


trait ImportSupport {

  val dependencies = Map(
    "Base" -> Set("Validator"),
    "Action" -> Set("Validator", "Base"),
    "Test" -> Seq("Validator", "Base")
  )

  protected case class TypePlacement(packageName: Option[String], typeName: String) {
    val qualifiedName = packageName.map(_ + "." + typeName).getOrElse(typeName)
  }

  private var typeUsages = Map.empty[String, Set[TypePlacement]]

  def cleanImportTable(): Unit = {
    typeUsages.synchronized { typeUsages = Map.empty[String, Set[TypePlacement]] }
  }

  def useType(ref: Reference, suffix: String, prefix: String) = {
    val fullName = ref.qualifiedName(prefix, suffix)
    fullName._2
  }

  def imports(pckg: String) = {
    val neededTypes = typeUsages.get(pckg).toSeq.flatten.distinct.filterNot(_.packageName == Some(pckg))

    val result = neededTypes.groupBy(_.packageName).flatMap { packageGroup =>
      if (packageGroup._2.size > 3) Seq(packageGroup._1.get + "._")
      else if (packageGroup._2.size > 1) Seq(packageGroup._1.get + "." + packageGroup._2.map(_.typeName).sorted.mkString("{", ", ", "}"))
      else packageGroup._2.map(_.qualifiedName)
    }
    val endResult = result.map(r => Map("name" -> r))
    endResult
  }

}
