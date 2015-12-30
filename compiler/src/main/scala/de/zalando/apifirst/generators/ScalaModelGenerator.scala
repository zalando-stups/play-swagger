package de.zalando.apifirst.generators

import de.zalando.apifirst.Application._
import de.zalando.apifirst.Domain._
import de.zalando.apifirst.ScalaName._
import de.zalando.apifirst.naming.Reference
import de.zalando.apifirst.{Domain, ParameterPlace}
import org.fusesource.scalate.{TemplateEngine, TemplateSource}

/**
  * @author  slasch 
  * @since   16.11.2015.
  */

class ScalaGenerator(val strictModel: StrictModel)
  extends ScalaModelGenerator with ScalaTestDataGenerator with PlayValidatorsGenerator
    with PlayScalaControllersGenerator with ImportSupport with PlayScalaTestsGenerator {

  val denotationTable = AstScalaPlayEnricher(strictModel)

  val StrictModel(modelCalls, modelTypes, modelParameters, discriminators, _) = strictModel

  def model(fileName: String) = generate(fileName, Map.empty, Nil).head
  def generators(fileName: String) = generate(fileName, Map.empty, Nil)(1)
  def tests(fileName: String) = generate(fileName, Map.empty, Nil)(4)

  def controllers(fileName: String, currentVersion: String) = {
    val (unmanagedParts, unmanagedImports) = analyzeController(currentVersion)
    generate(fileName, unmanagedParts, unmanagedImports)(5)
  }

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
    else apply(fileName, modelTemplateName, "")
  }

  private def generateGenerators(fileName: String) = {
    if (modelTypes.isEmpty) ""
    else apply(fileName, generatorsTemplateName, generatorsSuffix)
  }

  def playValidators(fileName: String) = {
    if (modelCalls.map(_.handler.parameters.size).sum == 0) ""
    else apply(fileName, validatorsTemplateName, validatorsSuffix)
  }

  def playScalaTests(fileName: String) = {
    if (modelCalls.map(_.handler.parameters.size).sum == 0) ""
    else apply(fileName, testsTemplateName, testsSuffix)
  }


  def playScalaControllers(fileName: String, unmanagedParts: Map[ApiCall, UnmanagedPart], unmanagedImports: Seq[String]) = {
    if (modelCalls.isEmpty) ""
    else apply(fileName, controllersTemplateName, controllersSuffix, unmanagedParts, unmanagedImports)
  }

  def playScalaControllerBases(fileName: String) = {
    if (modelCalls.isEmpty) ""
    else apply(fileName, controllerBaseTemplateName, baseControllersSuffix)
  }

  private def apply(fileName: String, templateName: String, suffix: String,
                    unmanagedParts: Map[ApiCall, UnmanagedPart] = Map.empty, unmanagedImports: Seq[String] = Seq.empty): String = {
    val packages = Map(
      "main_package" -> fileName.split('.').map(escape).mkString("."),
      "main_package_prefix" -> fileName.split('.').init.mkString("."),
      "main_package_suffix" -> fileName.split('.').last,
      "spec_name" -> escape(capitalize("\\.", fileName) + testsSuffix)
    )
    nonEmptyTemplate(packages, templateName, suffix, unmanagedParts, unmanagedImports)
  }

  private def nonEmptyTemplate(map: Map[String, Any], templateName: String, suffix: String,
                               unmanagedParts: Map[ApiCall, UnmanagedPart], unmanagedImports: Seq[String]): String = {
    cleanImportTable()
    val engine = new TemplateEngine

    val validations = ReShaper.filterByType("validators", denotationTable)
    val validationsByType = ReShaper.groupByType(validations.toSeq)

    val rawAllPackages = Map(
      "packages" -> Seq({
        // val tConstraints = typeConstraints(modelTypes)
        //val pConstraints = parameterConstraints(modelParameters)
        val singlePackage = Map(

          "classes" -> ReShaper.filterByType("classes", denotationTable),
          "aliases" -> ReShaper.filterByType("aliases", denotationTable),
          "traits" -> ReShaper.filterByType("traits", denotationTable),

          "test_data_classes" -> ReShaper.filterByType("test_data_classes", denotationTable),
          "test_data_aliases" -> ReShaper.filterByType("test_data_aliases", denotationTable),


        /*
        "constraints" -> (tConstraints ++ pConstraints),
        "validations" -> complexTypeValidations(modelTypes),
        "call_validations" -> (modelCalls map callValidations),
*/
          "tests" -> ReShaper.filterByType("tests", denotationTable),

          "controllers" -> controllers(modelCalls, unmanagedParts)
        )
        singlePackage + ("package" -> suffix) + ("imports" -> imports(suffix)) ++ validationsByType.toMap
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

/*  private def traits(types: Map[Reference, Type]) =
    types collect {
      case (k, t: TypeDef) if discriminators.contains(k) => typeDefProps(k, t, t.fields)
    }

  private def aliases(types: Map[Reference, Type]) =
    types collect {
      case (k, v: Container) => mapForAlias(k, v)
//      case (k, v: PrimitiveType) => mapForAlias(k, v)
    }*/

//  private def mapForAlias(k: Reference, v: Container): Map[String, Object] = {
//    Map(
//      "name" -> useType(k,"",""),
//      "creator_method" -> useType(k, "", "create"),
//      "alias" -> v.alias,
//      "generator" -> typeName(v, k),
//      "generator_name" -> generatorNameForType(v),
//      "underlying_type" -> v.imports.headOption.map { _ => v.nestedTypes.map { t => useType(t.name, "", "")}.mkString(", ") }
///*        case t: PrimitiveType => typeName(t, null)
//        case TypeRef(ref) => typeName(strictModel.findType(ref), ref)
//      }}*/
//    )
//  }
//


/*  private def classes(types: Map[Reference, Type]) =
    types collect {
      case (k, t: TypeDef) if !k.simple.contains("AllOf") && !k.simple.contains("OneOf") =>
        val traitName = discriminators.get(k).map(_ => Map("name" -> useType(k, "", "")))
        typeDefProps(k, t, t.fields) + ("trait" -> traitName)
      case (k, t: Composite) =>
        val fields = dereferenceFields(t).distinct
        typeDefProps(k, t, fields) + ("trait" -> t.root.map(r => Map("name" -> r.className)))
    }*/

/*  private def dereferenceFields(t: Composite): Seq[Field] =
    t.descendants flatMap {
      case td: TypeDef => td.fields
      case r: TypeRef => modelTypes.get(r.name) match {
        case Some(td: TypeDef) => td.fields
        case Some(c: Composite) => dereferenceFields(c)
        case Some(other) =>
          throw new IllegalStateException(s"Unexpected contents of Composite $r: $other")
        case None =>
          throw new IllegalStateException(s"Could not find type definition for reference $r")

      }
    }*/

  /*private def typeDefProps(k: Reference, t: Type, fields: Seq[Field]): Map[String, Object] = {
    Map(
      "name" -> typeName(t, k),
      "creator_method" -> useType(k, "", "create"),
      "fields" -> fields.zipWithIndex.map { case (f, i) =>
        Map(
          "name" -> escape(f.name.simple),
          "generator" -> generatorNameForType(f.tpe),
          "type_name" -> typeName(f.tpe, f.tpe.name),
          "last" -> (i == fields.size - 1)
        )
      }
    )
  }*/

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

  def validatorsSuffix: String

  def callValidations(call: ApiCall): Map[String, Any]

  def parametersValidations(parameters: Seq[ParameterRef]): Seq[Map[String, Any]]

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

  trait ScalaTestDataGenerator extends ImportSupport {

    val generatorsTemplateName = "generators.mustache"
/*
    val generatorsSuffix = "Generator"

    def generatorNameForType: (Type) => String = {
      case s: PrimitiveType => primitiveType(s)
      case c: Container => containerType(c)
      case r: TypeRef => useType(r.name, generatorsSuffix, "")
      case o => s"generator name for $o"
    }

    private def containerType(c: Container): String = {
      val innerGenerator = generatorNameForType(c.tpe)
      val className = useType(c.tpe.name, "", "")
      c match {
        case Opt(tpe, _) => s"Gen.option($innerGenerator)"
        case Arr(tpe, _, _) => s"Gen.containerOf[List,$className]($innerGenerator)"
        case c@CatchAll(tpe, _) => s"_genMap[String,$className](arbitrary[String], $innerGenerator)"
      }
    }

    private def primitiveType(tpe: Type) = s"arbitrary[${useType(tpe.name, "", "")}]" // tpe.name.className
  */
  }

  trait ScalaModelGenerator {
    val modelTemplateName = "model.mustache"
  }

  trait PlayScalaTestsGenerator extends ImportSupport {
    def strictModel: StrictModel
    val testsTemplateName = "play_scala_test.mustache"
    val testsSuffix = "Spec"
//    def validatorsSuffix: String = "" // WRONG, MUST BE REMOVED
    def generatorsSuffix: String = "" // WRONG, MUST BE REMOVED
//    def generatorNameForType: (Type) => String = _ => "" // WRONG, MUST BE REMOVED

    /*def tests(calls: Seq[ApiCall]) =
      calls filterNot { _.handler.parameters.isEmpty } map callTest

    def callTest(call: ApiCall): Map[String, Object] = {
        val namespace = if (strictModel.basePath == "/") "" else strictModel.basePath
        Map(
          "verb_name" -> call.verb.name,
          "full_path" -> call.path.prepend(namespace).asSwagger,
          "full_url" -> fullUrl(namespace, call),
          "validation_name" -> useType(call.asReference, validatorsSuffix, ""),
          "body?" -> bodyParameter(call),
          "expected_code" -> "200", // FIXME
          expectedResultType(call),
          parameters(call),
          "headers" -> headers(call).toMap
        )
    }

    def expectedResultType(call: ApiCall) = "expected_result_type" -> call.mimeOut.headOption.map(_.name.toLowerCase).getOrElse("application/json")

    def bodyParameter(call: ApiCall) = {
      call.handler.parameters.map {
        strictModel.findParameter
      }.find {
        _.place == ParameterPlace.BODY
      }.map { p =>
        Map("body_parameter_name" -> p.name, expectedResultType(call))
      }
    }

    def headers(call: ApiCall) = {
      call.handler.parameters.filter { p =>
        strictModel.findParameter(p).place == ParameterPlace.HEADER
      }.map {
        "name" -> _.name.simple
      }
    }

    def parameters(call: ApiCall) = {
      lazy val parameters = Map(
        "parameters" -> call.handler.parameters.zipWithIndex.map { case (param, ii) =>
          val paramName = strictModel.findParameter(param)
          val typeName = paramName.typeName
          val genName = generatorNameForType(typeName)
          val genPckg = if (genName.indexOf(']')>0) "" else typeName.name.qualifiedName("", generatorsSuffix)._1 + "."
            Map(
            "name" -> escape(camelize("\\.", param.simple)),
            "type" -> useType(typeName.name, "", ""),
            "generator" -> (genPckg + genName),
            "last" -> (ii == call.handler.parameters.size - 1)
          )
        }
      )
      lazy val parameter = {
        val param = call.handler.parameters.head
        val paramName = strictModel.findParameter(param)
        val typeName = paramName.typeName
        val genName = generatorNameForType(typeName)
        val genPckg = if (genName.indexOf(']')>0) "" else typeName.name.qualifiedName("", generatorsSuffix)._1 + "."
          Map(
          "name" -> escape(camelize("\\.", param.simple)),
          "type" -> useType(typeName.name, "", ""),
          "generator" -> (genPckg + genName)
        )
      }
      val nameParamPair =
        if (call.handler.parameters.isEmpty) "" -> None
        else if (call.handler.parameters.length == 1) "single_parameter?" -> Some(parameter)
        else "parameters?" -> Some(parameters)
      nameParamPair
    }


    private def fullUrl(namespace: String, call: ApiCall) = {
      val query = call.handler.parameters.filter { p =>
        strictModel.findParameter(p).place == ParameterPlace.QUERY
      } map { p =>
        val param = strictModel.findParameter(p)
        singleQueryParam(param.name, param.typeName)
      }
      val fullQuery = if (query.isEmpty) "" else query.mkString("?", "&", "")
      val url = call.path.prepend(namespace).interpolated
      "s\"\"\"" + url + fullQuery + "\"\"\""
    }

    private def singleQueryParam(name: String, typeName: Type): String = typeName match {
      case r@ TypeRef(ref) =>
        singleQueryParam(name, strictModel.findType(ref))
      case c: Domain.Opt =>
        containerParam(name) + "getOrElse(\"\")}"
      case c: Domain.Arr =>
        containerParam(name) + "mkString(\"&\")}"
      case d: Domain.CatchAll =>
        "" // TODO no marshalling / unmarshalling yet
      case d: Domain.TypeDef =>
        "" // TODO no marshalling / unmarshalling yet
      case o =>
        name + "=${URLEncoder.encode(" + name + ".toString, \"UTF-8\")}"
    }
    private def containerParam(name: String) =
      "${" + name + ".map { i => \"" + name + "=\" + URLEncoder.encode(i.toString, \"UTF-8\")}."*/
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

  object TypePlacement {
    def apply(qualified: String) =  {
      val border = qualified.lastIndexOf('⌿')
      if (border < 0) new TypePlacement(None, qualified)
      else {
        val parts = qualified.splitAt(border)
        fromParts(parts._1, parts._2.tail)
      }
    }
    def fromParts(parts: (String, String)) = {
      val pack = if (parts._1 != null && parts._1.trim.nonEmpty) Some(parts._1) else None
      new TypePlacement(pack, parts._2)
    }
  }
  private var typeUsages = Map.empty[String, Set[TypePlacement]]

  def cleanImportTable(): Unit = {
    typeUsages.synchronized { typeUsages = Map.empty[String, Set[TypePlacement]] }
  }

  def useType(ref: Reference, suffix: String, prefix: String) = {
    val fullName = ref.qualifiedName(prefix, suffix)
    /*
        val importDef = TypePlacement.fromParts(fullName)
        val key = pckg.replace(ref.packageName, "")
        val moduleDependent = dependencies.get(key).exists(_.exists(_ == suffix))
        if (suffix.nonEmpty && (pckg.endsWith(suffix) || moduleDependent)) importDef.packageName.foreach { _ =>
          typeUsages.synchronized {
            val toUpdate = typeUsages.getOrElse(pckg, Set.empty[TypePlacement])
            val updated = toUpdate + importDef
            typeUsages = typeUsages.updated(pckg, updated)
          }
        }
    */
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
