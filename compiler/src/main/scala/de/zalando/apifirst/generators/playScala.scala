package de.zalando.apifirst.generators

import de.zalando.apifirst.Application._
import de.zalando.apifirst.Domain._
import de.zalando.apifirst.ScalaName._
import de.zalando.apifirst.generators.DenotationNames.DenotationTable

/**
  * @author slasch
  * @since 16.11.2015.
  */

class ScalaGenerator(val strictModel: StrictModel) extends PlayScalaControllerAnalyzer {

  val denotationTable = AstScalaPlayEnricher(strictModel)

  val StrictModel(modelCalls, modelTypes, modelParameters, discriminators, _, overridenPackageName, securityDefinitions) = strictModel

  val testsTemplateName               = "play_scala_test"
  val validatorsTemplateName          = "play_validation"
  val generatorsTemplateName          = "generators"
  val modelTemplateName               = "model"
  val controllersTemplateName         = "play_scala_controller"
  val controllerBaseTemplateName      = "play_scala_controller_base"
  val marschallersTemplateName        = "play_scala_response_writers"
  val securityTemplateName            = "play_scala_controller_security"
  val securityExtractorsTemplateName  = "play_scala_security_extractors"

  def generateBase: (String, String, String) => Seq[String] = (fileName, packageName, currentController) => Seq(
    generateModel(fileName, packageName),
    playValidators(fileName, packageName),
    playScalaSecurity(fileName, packageName),
    playScalaControllerBases(fileName, packageName)
  )

  def generateTest: (String, String, String) => Seq[String] = (fileName, packageName, currentController) => Seq(
    generateGenerators(fileName, packageName),
    playScalaTests(fileName, packageName)
  )

  def generateMarshallers: (String, String, String) => Seq[String] = (fileName, packageName, currentController) => Seq(
    playScalaMarshallers(fileName, packageName)
  )

  def generateExtractors: (String, String, String) => Seq[String] = (fileName, packageName, currentController) => Seq(
    playScalaSecurityExtractors(fileName, packageName)
  )

  def generateControllers: (String, String, String) => Seq[String] = (fileName, packageName, currentController) => Seq(
    playScalaControllers(fileName, packageName, currentController)
  )

  def generateModel(fileName: String, packageName: String) =
    if (modelTypes.values.forall(_.isInstanceOf[PrimitiveType])) ""
    else apply(fileName, packageName, modelTemplateName)

  def generateGenerators(fileName: String, packageName: String) =
    if (modelTypes.isEmpty) ""
    else apply(fileName, packageName, generatorsTemplateName)

  def playValidators(fileName: String, packageName: String) =
    if (modelCalls.map(_.handler.parameters.size).sum == 0) ""
    else apply(fileName, packageName, validatorsTemplateName)

  def playScalaTests(fileName: String, packageName: String) =
    if (modelCalls.map(_.handler.parameters.size).sum == 0) ""
    else apply(fileName, packageName, testsTemplateName)

  def playScalaControllers(fileName: String, packageName: String, currentController: String) =
    if (modelCalls.isEmpty) ""
    else apply(fileName, packageName, controllersTemplateName, currentController)

  def playScalaControllerBases(fileName: String, packageName: String) =
    if (modelCalls.isEmpty) ""
    else apply(fileName, packageName, controllerBaseTemplateName)

  def playScalaMarshallers(fileName: String, packageName: String) =
    if (modelCalls.isEmpty) ""
    else apply(fileName, packageName, marschallersTemplateName)

  def playScalaSecurityExtractors(fileName: String, packageName: String) =
    if (modelCalls.isEmpty) ""
    else apply(fileName, packageName, securityExtractorsTemplateName)

  def playScalaSecurity(fileName: String, packageName: String) =
    if (securityDefinitions.isEmpty) ""
    else apply(fileName, packageName, securityTemplateName)

  private def apply(fileName: String, packageName: String, templateName: String, currentController: String = ""): String = {
    nonEmptyTemplate(fileName, packageName, templateName, currentController)
  }

  private def nonEmptyTemplate(fileName: String, packageName: String, templateName: String, currentController: String): String = {

    assert(packageName.contains('-') == packageName.contains('`'), packageName)

    val validations         = ReShaper.filterByType("validators", denotationTable)
    val validationsByType   = ReShaper.groupByType(validations.toSeq).toMap

    val bindings            = ReShaper.filterByType("bindings", denotationTable)
    val grouppedBindings    = ReShaper.groupByType(bindings.toSeq)
    val sortedBindings      = grouppedBindings.map { case (x,y: Seq[Map[String, Any] @unchecked]) =>
      val sorted = y.sortWith { (a, b) => (a.get("dependencies"), b.get("dependencies")) match {
        case (Some(aa: Int), Some(bb: Int)) => aa < bb
        case _ => false
      }
      }
      x -> sorted
    }

    val bindingsByType      = sortedBindings.toMap

    val marshallers         = ReShaper.filterByType("marshallers", denotationTable)
    val grouppedMarshallers = ReShaper.groupByType(marshallers.toSeq).toMap

    val unmarshallers         = ReShaper.filterByType("unmarshallers", denotationTable)
    val grouppedunMarshallers = ReShaper.groupByType(unmarshallers.toSeq).toMap

    val securityExtractors    = ReShaper.filterByType("security_extractors", denotationTable)
    val extractors            = ReShaper.groupByType(securityExtractors.toSeq).toMap

    val (unmanagedParts: Map[ApiCall, UnmanagedPart], unmanagedImports: Seq[String]) =
      analyzeController(currentController, denotationTable)

    val pckg = overridenPackageName.getOrElse(packageName)

    val packages = Map(
      "main_package" -> pckg,
      "main_package_prefix" -> pckg.split('.').init.mkString("."),
      "main_package_suffix" -> pckg.split('.').last,
      "spec_name" -> escape(capitalize("\\.", fileName) + "Spec")
    )

    val controllersList = controllers(modelCalls, unmanagedParts, pckg)(denotationTable)

    val controllersMap = Map(
      "controllers"         -> controllersList,
      "controller_imports"  -> controllerImports.map(i => Map("name" -> i)),
      "unmanaged_imports"   -> unmanagedImports.map(i => Map("name" -> i))
    )

    val securityChecks = ReShaper.groupByType(controllersList.toSeq).toMap

    val singlePackage = Map(
      "classes"             -> ReShaper.filterByType("classes", denotationTable),
      "aliases"             -> ReShaper.filterByType("aliases", denotationTable),
      "traits"              -> ReShaper.filterByType("traits", denotationTable),
      "test_data_classes"   -> ReShaper.filterByType("test_data_classes", denotationTable),
      "test_data_aliases"   -> ReShaper.filterByType("test_data_aliases", denotationTable),
      "tests"               -> ReShaper.filterByType("tests", denotationTable),
      "marshallers"         -> grouppedMarshallers,
      "unmarshallers"       -> grouppedunMarshallers,
      "security_extractors" -> extractors,
      "bindings"            -> bindingsByType
    )

    val rawAllPackages      = singlePackage ++ validationsByType ++ controllersMap
    val allPackages         = enrichWithStructuralInfo(rawAllPackages)


    renderTemplate(packages, templateName, allPackages)

  }

  def renderTemplate(map: Map[String, Any], templateName: String, allPackages: Map[String, Any]): String = {
    import de.zalando.beard.renderer._
    val loader = new ClasspathTemplateLoader(templatePrefix = "/", templateSuffix = ".mustache")
    val templateCompiler = new CustomizableTemplateCompiler(loader)
    val template = templateCompiler.compile(TemplateName(templateName)).get
    val renderer = new BeardTemplateRenderer(templateCompiler)

    renderer.render(template,
      StringWriterRenderResult(),
      map ++ allPackages,
      None).toString
  }

  def enrichWithStructuralInfo(rawAllPackages: Map[String, Iterable[Any]]): Map[String, Any] = {
    val imports = KeyCollector.collect("imports")(rawAllPackages)
    val importMaps = imports.distinct map { i => Map("name" -> i) }

    val bind_imports = KeyCollector.collect("binding_imports")(rawAllPackages)
    val bind_importMaps = bind_imports.distinct map { i => Map("name" -> i) }

    val allPackages = LastListElementMarks.set(rawAllPackages) ++
      neededParts(imports) + ("imports" -> importMaps) + ("binding_imports" -> bind_importMaps)
    allPackages
  }

  private val partsMapping = Map(
    "lists_part"          -> "ArrayWrapper",
    "maps_part"           -> "Map",
    "date_part"           -> "DateMidnight",
    "date_time_part"      -> "DateTime",
    "binary_string_part"  -> "BinaryString",
    "base64_string_part"  -> "Base64String"
  )
  private def neededParts(imports: Seq[String]): Map[String, Boolean] = partsMapping map {
    case (k,v) => k -> imports.exists(_.contains(v))
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
      cleanLines indexWhere { _.startsWith(marker._2._1) }
    }
    val sortedMarkers = markerIndexes.sorted
    val codeParts = markerIndexes map { idx => sortedMarkers.find(_ > idx).getOrElse(lines.length) }

    val parts = markers zip markerIndexes zip codeParts filter {
      case ((((call, (marker, length)), start), end)) => start >= 0
    } map {
      case ((((call, (marker, length)), start), end)) =>
        val code = lines.slice(start + length, end - 1)
        val relevantCode = code.takeWhile(!_.contains(eof))
        val deadCode = code.dropWhile(!_.contains(eof)).drop(1).filterNot(_.trim.isEmpty)
        val commentedOut = if (deadCode.isEmpty) "" else deadCode.mkString("/*\n", "\n", "\n*/\n")
        call -> UnmanagedPart(call, relevantCode.mkString("\n"), commentedOut)
    }
    val preservedImports =
      allLines.filter(_.trim.startsWith("import")).map(_.replace("import ", "")).filterNot(controllerImports.contains)
    (parts.toMap, preservedImports)
  }

  def generateMarkers(allCalls: Seq[ApiCall], table: DenotationTable) = {
    val markers = allCalls map { call =>
      val controllerDenotations = table(call.asReference)("controller")
      val signature = controllerDenotations("signature").toString
      val markerSize = // FIXME this will fail if parameter types will change
        if (controllerDenotations("multiple_parameters") != Nil) 2 // FIXME this is very error-prone
        else if (controllerDenotations("single_parameter") != None) 1  // FIXME this is very error-prone
        else 1
      call -> (signature, markerSize)
    }
    markers
  }
}

trait PlayScalaControllersGenerator {

  val controllerImports = Seq(
    "play.api.mvc.{Action, Controller}",
    "play.api.data.validation.Constraint",
    "de.zalando.play.controllers._",
    "PlayBodyParsing._",
    "PlayValidations._",
    "scala.util._"
  )

  case class UnmanagedPart(marker: ApiCall, relevantCode: String, deadCode: String)

  val baseControllersSuffix = "Base"
  val securityTraitSuffix = "Security"

  def controllers(allCalls: Seq[ApiCall], unmanagedParts: Map[ApiCall, UnmanagedPart], packageName: String)(table: DenotationTable) = {
    allCalls groupBy { c =>
      (c.handler.packageName, c.handler.controller)
    } map { case (controller, calls) =>
      val methods = calls map { singleMethod(unmanagedParts, table) }
      if (packageName != controller._1) {
        println(s"WARN: Ignoring package part of the handler name '${controller._1}', using '$packageName' instead. \n\t" +
          "Current plugin version only supports single package definition per specification.\n\t" +
          "Play's route files will fail to compile.")
      }
      val securityTrait = calls.find(_.security.nonEmpty).map(_ => escape(controller._2 + securityTraitSuffix))
      Map(
        "effective_package"   -> packageName,
        "controller"          -> escape(controller._2),
        "base"                -> escape(controller._2 + baseControllersSuffix),
        "methods"             -> methods,
        "security_trait"      -> securityTrait
      )
    }
  }

  def singleMethod(unmanagedParts: Map[ApiCall, UnmanagedPart], table: DenotationTable): ApiCall => Map[String, Any] =
    call => {
      val method = table(call.asReference)("controller")
      val methodWithCode = method + (
        "dead_code"       -> unmanagedParts.get(call).map(_.deadCode).getOrElse(""),
        "implementation"  -> unmanagedParts.get(call).map(_.relevantCode).getOrElse("Failure(???)")
        )
      methodWithCode
    }
}
