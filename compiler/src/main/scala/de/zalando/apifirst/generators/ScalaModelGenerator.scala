package de.zalando.apifirst.generators

import de.zalando.apifirst.Application.{ParameterRef, ApiCall, DiscriminatorLookupTable, StrictModel}
import de.zalando.apifirst.{ParameterPlace, Domain}
import de.zalando.apifirst.Domain._
import de.zalando.apifirst.ScalaName._
import de.zalando.apifirst.new_naming.Reference
import org.fusesource.scalate.TemplateEngine

/**
  * @author  slasch 
  * @since   16.11.2015.
  */

class ScalaGenerator(val strictModel: StrictModel)
  extends ScalaModelGenerator with ScalaTestDataGenerator with PlayValidatorsGenerator with PlayScalaControllersGenerator with ImportSupport {

  def this(tps: Map[Reference, Domain.Type], discriminators: DiscriminatorLookupTable) =
    this(StrictModel(Nil, tps, Map.empty, discriminators))

  def this(tps: Map[Reference, Domain.Type]) = this(tps, Map.empty)

  val StrictModel(modelCalls, modelTypes, params, discriminators) = strictModel

  def model(fileName: String) = generate(fileName)._1
  def generators(fileName: String) = generate(fileName)._2

  // TODO the order here is importante because of the typeDefinitions lookup table
  def generate(fileName: String) =
    (
      generateModel(fileName),
      generateGenerators(fileName),
      playValidators(fileName),
      playScalaControllerBases(fileName),
      playScalaControllers(fileName)
    )

  private def generateModel(fileName: String) = {
    if (modelTypes.values.forall(_.isInstanceOf[PrimitiveType])) ""
    else apply(fileName, modelTemplateName, modelSuffix)
  }

  private def generateGenerators(fileName: String) = {
    if (modelTypes.isEmpty) ""
    else apply(fileName, generatorsTemplateName, generatorsSuffix)
  }

  def playValidators(fileName: String) = {
    if (modelCalls.map(_.handler.parameters.size).sum == 0) ""
    else apply(fileName, validatorsTemplateName, validatorsSuffix)
  }

  def playScalaControllers(fileName: String) = {
    if (modelCalls.isEmpty) ""
    else apply(fileName, controllersTemplateName, controllersSuffix)
  }

  def playScalaControllerBases(fileName: String) = {
    if (modelCalls.isEmpty) ""
    else apply(fileName, controllerBaseTemplateName, baseControllersSuffix)
  }

  private def apply(fileName: String, templateName: String, suffix: String): String =
    nonEmptyTemplate(Map("main_package" -> fileName), templateName, suffix)

  private def nonEmptyTemplate(map: Map[String, Any], templateName: String, suffix: String): String = {
    cleanImportTable()
    val engine = new TemplateEngine
    val typesByPackage = modelTypes.groupBy(_._1.packageName)

    val callsPrimitiveParameters = for {
      call <- modelCalls if call.handler.parameters.nonEmpty
      parameter <- call.handler.parameters
      tpe = strictModel.findParameter(parameter).typeName if tpe.isInstanceOf[PrimitiveType]
    } yield parameter.name -> tpe

    val allPackages = Map(
      "packages" -> typesByPackage.toList.map { case (pckg, typeDefs) =>
        implicit val pckgName = pckg + suffix
        val singlePackage = Map(
          "classes" -> classes(typeDefs, suffix),
          "aliases" -> aliases(typeDefs, suffix),
          "traits" -> traits(typeDefs, suffix),
          "constraints" -> constraints(typeDefs ++ callsPrimitiveParameters),
          "validations" -> validations(typeDefs)
        )
        val callValidations = if (pckg == "paths") Some("call_validations" -> validations(modelCalls)) else None
        val callControllers = if (pckg == "paths") Some("controllers" -> controllers(modelCalls)) else None
        val fullPackage = singlePackage ++ callValidations.toSeq.toMap ++ callControllers.toSeq.toMap
        fullPackage + ("package" -> pckgName) + ("imports" -> imports(pckgName))
      })

    val output = engine.layout(templateName, map ++ allPackages)
    output.replaceAll("\u2B90", "")
  }

  private def traits(types: Map[Reference, Type], suffix: String)(implicit pckg: String) =
    types collect {
      case (k, t: TypeDef) if discriminators.contains(k) => typeDefProps(k, t, t.fields, suffix)
    }

  private def aliases(types: Map[Reference, Type], suffix: String)(implicit pckg: String) =
    types collect {
      case (k, v: Container) => mapForAlias(suffix, k, v)
      case (k, v: PrimitiveType) => mapForAlias(suffix, k, v)
    }

  private def mapForAlias(suffix: String, k: Reference, v: Type)(implicit pckg: String): Map[String, Object] = {
    Map(
      "name" -> useType(k, suffix, ""),
      "typeName" -> useType(k, "", "")(pckg.replace(suffix, "")),
      "creator_method" -> useType(k, suffix, "create"),
      "alias" -> v.imports.headOption.getOrElse(v.name.simple),
      "generator" -> useType(k, suffix, ""),
      "generator_name" -> generatorNameForType(pckg)(v),
      "underlying_type" -> v.imports.headOption.map { _ => v.nestedTypes.map { t => useType(t.name, "", "")}.mkString(", ") }
    )
  }

  private def classes(types: Map[Reference, Type], suffix: String)(implicit pckg: String) =
    types collect {
      case (k, t: TypeDef) if !k.simple.contains("AllOf") && !k.simple.contains("OneOf") =>
        val traitName = discriminators.get(k).map(_ => Map("name" -> useType(k, "", "")))
        typeDefProps(k, t, t.fields, suffix) + ("trait" -> traitName)
      case (k, t: Composite) =>
        val fields = dereferenceFields(t).distinct
        typeDefProps(k, t, fields, suffix) + ("trait" -> t.root.map(r => Map("name" -> r.className)))
    }

  private def dereferenceFields(t: Composite): Seq[Field] =
    t.descendants flatMap {
      case td: TypeDef => td.fields
      case r: TypeRef => modelTypes.get(r.name) match {
        case Some(td: TypeDef) => td.fields
        case Some(c: Composite) => dereferenceFields(c)
        case other => throw new IllegalStateException(s"Unexpected contents of Composite $r: $other")
      }
    }

  private def typeDefProps(k: Reference, t: Type, fields: Seq[Field], suffix: String)(implicit pckg: String): Map[String, Object] = {
    Map(
      "name" -> useType(k, suffix, ""),
      "class_name" -> useType(k, "", ""),
      "creator_method" -> useType(k, suffix, "create"),
      "fields" -> fields.zipWithIndex.map { case (f, i) =>
        Map(
          "name" -> escape(f.name.simple),
          "generator" -> generatorNameForType(pckg)(f.tpe),
          "typeName" -> useType(f.tpe.name, "", ""),
          "last" -> (i == fields.size - 1)
        )
      }
    )
  }

}

trait PlayScalaControllersGenerator extends ImportSupport {

  def strictModel: StrictModel

  def validatorsSuffix: String

  def callValidations(call: ApiCall)(implicit pckg: String): Map[String, Object]

  def parametersValidations(parameters: Seq[ParameterRef])(implicit pckg: String): Seq[Map[String, Any]]

  val controllersSuffix = "Action"
  val baseControllersSuffix = "Base"

  val controllersTemplateName = "play_scala_controller.mustache"

  val controllerBaseTemplateName = "play_scala_controller_base.mustache"

  // TODO this part is where it's getting ugly.
  def controllers(allCalls: Seq[ApiCall])(implicit pckg: String) =
    allCalls groupBy {
      _.handler.controller
    } map { case (controller, calls) =>
      val methods = calls.zipWithIndex.map { case (call, i) =>
        lazy val parameters = Map(
          "parameters" -> call.handler.parameters.zipWithIndex.map { case (param, ii) =>
            val typeName = strictModel.findParameter(param).typeName.name
            Map(
              "name" -> escape(camelize("\\.", param.simple)),
              "type" -> useType(typeName, "", ""),
              "last" -> (ii == call.handler.parameters.size - 1)
            )
          },
          "last" -> (i == calls.size - 1)
        )
        lazy val parameter = {
          val param = call.handler.parameters.head
          val typeName = strictModel.findParameter(param).typeName.name
          Map("name" -> escape(camelize("\\.", param.simple)), "type" -> useType(typeName, "", ""))
        }
        val nameParamPair =
          if (call.handler.parameters.isEmpty) "" -> None
          else if (call.handler.parameters.length == 1) "single_parameter?" -> Some(parameter)
          else "parameters?" -> Some(parameters)

        val bodyParam = parametersValidations(call.handler.parameters.filter(strictModel.findParameter(_).place == ParameterPlace.BODY))
        val nonBodyParams = parametersValidations(call.handler.parameters.filterNot(strictModel.findParameter(_).place == ParameterPlace.BODY))
        val validations = callValidations(call)

        val actionResultType = call.resultTypes.headOption.map(t => useType(strictModel.findType(t.name).name, "", ""))
        Map(
          "response_mime_type_value" -> call.mimeOut.headOption.map(_.name).getOrElse("application/json"), // TODO implement content negotiation
          "action_result_type_value" -> actionResultType, // TODO implement content negotiation
          "action_success_status_value" -> 200, // FIXME

          "method" -> escape(call.handler.method),
          "action" -> escape(call.handler.method + controllersSuffix),
          "parser_name" -> escape(call.handler.method + "Parser"),
          "writable_json" -> escape(call.handler.method + "WritableJson"),
          "action_type" -> escape(call.handler.method + "ActionType"),
          "action_result_type" -> escape(call.handler.method + "ActionResultType"),
          "action_success_status_name" -> escape(call.handler.method + "ActionSuccessStatus"),
          "action_request_type" -> escape(call.handler.method + "ActionRequestType"),
          "process_valid_request" -> escape("processValid" + call.handler.method + "Request"),
          "response_mime_type_name" -> escape(call.handler.method + "ResponseMimeType"),
          "error_to_status" -> escape("errorToStatus" + call.handler.method),
          "error_mappings" -> call.errorMapping.flatMap { case (k, v) => v.map { _.getCanonicalName -> k }},
          "validations" -> validations,
          "validations?" -> validations.nonEmpty,
          "body_param" -> bodyParam,
          "non_body_params" -> nonBodyParams,
          "non_body_params?" -> nonBodyParams.nonEmpty,
          "body_param?" -> bodyParam.nonEmpty,
          nameParamPair
        )
      }
      Map(
        "controller" -> escape(controller),
        "base" -> escape(controller + baseControllersSuffix),
        "methods" -> methods
      )
    }
  }

  trait ScalaTestDataGenerator extends ImportSupport {

    val generatorsTemplateName = "generators.mustache"

    val generatorsSuffix = "Generator"

    def generatorNameForType(implicit pckg: String): (Type) => String = {
      case s: PrimitiveType => primitiveType(s)
      case c: Container => containerType(c)
      case r: TypeRef => useType(r.name, generatorsSuffix, "")
      case o => s"generator name for $o"
    }

    private def containerType(c: Container)(implicit pckg: String): String = {
      val innerGenerator = generatorNameForType(pckg)(c.tpe)
      val className = useType(c.tpe.name, "", "")
      c match {
        case Opt(tpe, _) => s"Gen.option($innerGenerator)"
        case Arr(tpe, _, _) => s"Gen.containerOf[List,$className]($innerGenerator)"
        case c@CatchAll(tpe, _) => s"_genMap[String,$className](arbitrary[String], $innerGenerator)"
      }
    }

    private def primitiveType(tpe: Type)(implicit pckg: String) = s"arbitrary[${useType(tpe.name, "", "")}]" // tpe.name.className

  }

  trait ScalaModelGenerator {

    val modelSuffix = ""

    val modelTemplateName = "model.mustache"
  }

  trait PlayValidatorsGenerator extends ImportSupport {

    val validatorsSuffix = "Validator"
    val constraintsSuffix = "Constraints"
    val validatorsTemplateName = "play_validation.mustache"

    type Validations = Iterable[Map[String, Any]]

    def strictModel: StrictModel

    def validations(typeDefs: Map[Reference, Type])(implicit pckg: String) = validations0(typeDefs.toSeq).flatten

    def validations(calls: Seq[ApiCall])(implicit pckg: String) = calls filterNot {
      _.handler.parameters.isEmpty
    } map { callValidations }

    def callValidations(call: ApiCall)(implicit pckg: String): Map[String, Object] = {
      val fields = parametersValidations(call.handler.parameters)
      if (fields.isEmpty)
        Map.empty
      else
        Map(
          "constraint_name" -> useType(call.asReference, constraintsSuffix, ""),
          "validation_name" -> useType(call.asReference, validatorsSuffix, ""),
          "class_name" -> call.asReference.typeAlias(), //useType(call.asReference, "", ""),
          "fields" -> fields
        )
      }

    def parametersValidations(parameters: Seq[ParameterRef])(implicit pckg: String): Seq[Map[String, Any]] =
      parameters.zipWithIndex.map { case (p, i) =>
        val tpe = strictModel.findParameter(p).typeName
        val validation = if (tpe.isInstanceOf[PrimitiveType]) p.name else tpe.name
        Map(
          "field_name" -> escape(p.name.simple),
          "field_type" -> tpe.name.typeAlias(),
          "field_raw_type" -> tpe.name,
          "validation_name" -> useType(validation, validatorsSuffix, ""),
          "last" -> (i == parameters.size - 1)
        )
      }

    private def validations0(types: Iterable[(Reference, Type)])(implicit pckg: String): Iterable[Validations] =
      types collect {
        case (r, t: TypeDef) if !r.simple.contains("AllOf") && !r.simple.contains("OneOf") => // FIXME copy-pasted and fragile, what if this in actual name of the parameter
          Seq(Map(
            "constraint_name" -> useType(r, constraintsSuffix, ""),
            "validation_name" -> useType(r, validatorsSuffix, ""),
            "class_name" -> useType(r, "", ""),
            "fields" -> t.fields.zipWithIndex.map { case (f, i) =>
              val validation = if (f.tpe.isInstanceOf[PrimitiveType]) f.name else f.tpe.name
              Map(
                "field_name" -> escape(f.name.simple),
                "validation_name" -> useType(validation, validatorsSuffix, ""),
                "last" -> (i == t.fields.size - 1)
              )
            }))
        case (r, t: Composite) =>
          validatorFromReference(r)
        case (r, t: Container) =>
          validatorFromReference(r)
        case (r, t: TypeRef) =>
          validatorFromReference(r)
      }

    def validatorFromReference(r: Reference)(implicit pckg: String): Seq[Map[String, String]] = {
      Seq(Map(
        "constraint_name" -> useType(r, constraintsSuffix, ""),
        "validation_name" -> useType(r, validatorsSuffix, ""),
        "field_name" -> r.simple,
        "class_name" -> useType(r, "", "")
      ))
    }

    def constraints(typeDefs: Map[Reference, Type])(implicit pckg: String) = constraints0(typeDefs.toSeq).flatten

    private def constraints0(types: Iterable[(Reference, Type)])(implicit pckg: String): Iterable[Validations] =
      types collect {
        case (r: Reference, t: PrimitiveType) =>
          Seq(Map(
            "restrictions" -> t.meta.constraints.filterNot(_.isEmpty).zipWithIndex.map { case (c, i) =>
              Map(
                "name" -> c,
                "last" -> (i == t.meta.constraints.length - 1)
              )
            },
            "constraint_name" -> useType(r, constraintsSuffix, ""),
            "validation_name" -> useType(r, validatorsSuffix, ""),
            "type_name" -> t.name.typeAlias(),
            "class_name" -> r.typeAlias()
          ))
        case (r, t: TypeDef) =>
          constraints0(t.fields.map { f => f.name -> f.tpe }).flatten
      }

  }


trait ImportSupport {

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

  def useType(ref: Reference, suffix: String, prefix: String)(implicit pckg: String) = {
    val fullName = ref.qualifiedName(prefix, suffix)
    val importDef = TypePlacement.fromParts(fullName)
    if (pckg.endsWith(suffix)) importDef.packageName.foreach { _ =>
      typeUsages.synchronized {
        val toUpdate = typeUsages.getOrElse(pckg, Set.empty[TypePlacement])
        val updated = toUpdate + importDef
        typeUsages = typeUsages.updated(pckg, updated)
      }
    }
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
