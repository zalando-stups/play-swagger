package de.zalando.apifirst.generators

import de.zalando.apifirst.Application._
import de.zalando.apifirst.Domain._
import de.zalando.apifirst.ScalaName._
import de.zalando.apifirst.generators.DenotationNames.DenotationTable
import de.zalando.apifirst.naming.Reference
import org.fusesource.scalate.{TemplateEngine, TemplateSource}

/**
  * @author slasch
  * @since 16.11.2015.
  */

class ScalaGenerator(val strictModel: StrictModel)
  extends PlayScalaControllerAnalyzer with ImportSupport {

  val denotationTable = AstScalaPlayEnricher(strictModel)

  val StrictModel(modelCalls, modelTypes, modelParameters, discriminators, _) = strictModel

  def model(fileName: String) = generate(fileName, "").head

  def generators(fileName: String) = generate(fileName, "")(1)

  def tests(fileName: String) = generate(fileName, "")(4)

  def controllers(fileName: String, currentVersion: String) =
    generate(fileName, currentVersion)(5)

  val testsTemplateName = "play_scala_test.mustache"
  val validatorsTemplateName = "play_validation.mustache"
  val generatorsTemplateName = "generators.mustache"
  val modelTemplateName = "model.mustache"

  def generate(fileName: String, currentController: String) = {
    Seq(
      generateModel(fileName),
      generateGenerators(fileName),
      playValidators(fileName),
      playScalaControllerBases(fileName),
      playScalaTests(fileName),
      playScalaControllers(fileName, currentController)
    )
  }

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

  def playScalaControllers(fileName: String, currentController: String) = {
    if (modelCalls.isEmpty) ""
    else apply(fileName, controllersTemplateName, currentController)
  }

  def playScalaControllerBases(fileName: String) = {
    if (modelCalls.isEmpty) ""
    else apply(fileName, controllerBaseTemplateName)
  }

  private def apply(fileName: String, templateName: String, currentController: String = ""): String = {
    val packages = Map(
      "main_package" -> fileName.split('.').map(escape).mkString("."),
      "main_package_prefix" -> fileName.split('.').init.mkString("."),
      "main_package_suffix" -> fileName.split('.').last,
      "spec_name" -> escape(capitalize("\\.", fileName) + "Spec")
    )
    nonEmptyTemplate(packages, templateName, currentController)
  }

  private def nonEmptyTemplate(map: Map[String, Any], templateName: String, currentController: String): String = {
    cleanImportTable()
    val engine = new TemplateEngine

    val validations = ReShaper.filterByType("validators", denotationTable)
    val validationsByType = ReShaper.groupByType(validations.toSeq)

    val (unmanagedParts: Map[ApiCall, UnmanagedPart], unmanagedImports: Seq[String]) =
      analyzeController(currentController, denotationTable)

    val rawAllPackages = Map(
      "packages" -> Seq({
        val singlePackage = Map(

          "classes" -> ReShaper.filterByType("classes", denotationTable),
          "aliases" -> ReShaper.filterByType("aliases", denotationTable),
          "traits" -> ReShaper.filterByType("traits", denotationTable),

          "test_data_classes" -> ReShaper.filterByType("test_data_classes", denotationTable),
          "test_data_aliases" -> ReShaper.filterByType("test_data_aliases", denotationTable),

          "tests" -> ReShaper.filterByType("tests", denotationTable),

          "controllers" -> controllers(modelCalls, unmanagedParts)(denotationTable)
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

trait PlayScalaControllerAnalyzer extends PlayScalaControllersGenerator with ControllersCommons {

  def strictModel: StrictModel

  def analyzeController(currentVersion: String, table: DenotationTable): (Map[ApiCall, UnmanagedPart], Seq[String]) = {
    val markers = generateMarkers(strictModel.calls, table)
    val allLines = currentVersion.split("\n").filter(_.trim.nonEmpty).dropWhile(_.trim.isEmpty).toSeq
    val classPositions = allLines.zipWithIndex.filter(_._1.trim.startsWith("class")).map(_._2) :+ (allLines.length + 1)
    val classBlocks = if (classPositions.length > 1) classPositions.sliding(2).toSeq else Seq.empty
    val lines = classBlocks flatMap { b => allLines.slice(b.head, b(1) - 3) }
    val cleanLines = lines map {
      _.trim
    }
    val markerIndexes = markers map { marker =>
      cleanLines.indexOf(marker._2._1)
    }
    val sortedMarkers = markerIndexes.sorted
    val codeParts = markerIndexes map { idx => sortedMarkers.find(_ > idx).getOrElse(lines.length) }

    val parts = markers zip markerIndexes zip codeParts filter {
      case ((((call, (marker, length)), start), end)) =>
        start >= 0
    } map { case ((((call, (marker, length)), start), end)) =>
      val code = lines.slice(start + length, end - 1)
      val relevantCode = code.takeWhile(!_.contains(eof))
      val deadCode = code.dropWhile(!_.contains(eof)).drop(1).filterNot(_.trim.isEmpty)
      val commentedOut = if (deadCode.isEmpty) "" else deadCode.mkString("/*\n", "\n", "\n*/\n")
      call -> UnmanagedPart(call, relevantCode.mkString("\n"), commentedOut)
    }
    val preservedImports = allLines.filter(_.trim.startsWith("import")).map(_.replace("import ", "")).filterNot(controllerImports.contains)
    (parts.toMap, preservedImports)
  }

  def generateMarkers(allCalls: Seq[ApiCall], table: DenotationTable) = {
    val markers = allCalls map { call =>
      val controllerDenotations = table(call.asReference)("controller")
      val signature = controllerDenotations("signature")
      val markerSize = // FIXME this will fail if parameter types will change
        if (controllerDenotations.contains("parameters?")) 3
        else if (controllerDenotations.contains("single_parameter?")) 2
        else 1
      call -> (signature, markerSize)
    }
    markers
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

  val baseControllersSuffix = "Base"

  val controllersTemplateName = "play_scala_controller.mustache"
  val controllerBaseTemplateName = "play_scala_controller_base.mustache"

  def controllers(allCalls: Seq[ApiCall], unmanagedParts: Map[ApiCall, UnmanagedPart])(table: DenotationTable) = {
    allCalls groupBy { c =>
      (c.handler.packageName, c.handler.controller)
    } map { case (controller, calls) =>
      val methods = calls map { call =>
        val method = table(call.asReference)("controller")
        val methodWithCode = method + (
          "dead_code" -> unmanagedParts.get(call).map(_.deadCode).getOrElse(""),
          "implementation" -> unmanagedParts.get(call).map(_.relevantCode).getOrElse("???")
          )
        methodWithCode
      }
      Map(
        "effective_package" -> escape(controller._1), // TODO currently, the package name just ignored
        "controller" -> escape(controller._2),
        "base" -> escape(controller._2 + baseControllersSuffix),
        "methods" -> methods
      )
    }
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
    typeUsages.synchronized {
      typeUsages = Map.empty[String, Set[TypePlacement]]
    }
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
