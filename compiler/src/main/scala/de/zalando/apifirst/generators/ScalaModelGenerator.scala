package de.zalando.apifirst.generators

import de.zalando.apifirst.Application.{ApiCall, DiscriminatorLookupTable, StrictModel}
import de.zalando.apifirst.Domain
import de.zalando.apifirst.Domain._
import de.zalando.apifirst.ScalaName._
import de.zalando.apifirst.new_naming.Reference
import org.fusesource.scalate.TemplateEngine

/**
  * @author  slasch 
  * @since   16.11.2015.
  */

class ScalaGenerator(val model: StrictModel)
  extends ScalaModelGenerator with ScalaTestDataGenerator with PlayValidatorsGenerator {

  def this(tps: Map[Reference, Domain.Type], discriminators: DiscriminatorLookupTable) =
    this(StrictModel(Nil, tps, Map.empty, discriminators))

  def this(tps: Map[Reference, Domain.Type]) = this(tps, Map.empty)

  val StrictModel(calls, allTypes, params, discriminators) = model

  def model(fileName: String) = apply(fileName, modelTemplateName, modelSuffix)

  def generators(fileName: String) = apply(fileName, generatorsTemplateName, generatorsSuffix)

  def playValidators(fileName: String) = apply(fileName, validatorsTemplateName, validatorsSuffix)

  private def apply(fileName: String, templateName: String, suffix: String): String = {
    if (allTypes.values.forall(_.isInstanceOf[PrimitiveType])) ""
    else nonEmptyTemplate(Map("main_package" -> fileName), templateName, suffix)
  }

  private def nonEmptyTemplate(map: Map[String, Any], templateName: String, suffix: String): String = {
    val engine = new TemplateEngine
    val typesByPackage = allTypes.groupBy(_._1.packageName)

    val callsPrimitiveParameters = for {
      call <- calls if call.handler.parameters.nonEmpty
      parameter <- call.handler.parameters
      tpe = model.findParameter(parameter).typeName if tpe.isInstanceOf[PrimitiveType]
    } yield parameter.name -> tpe

    val allPackages = Map(
      "packages" -> typesByPackage.toList.map { case (pckg, typeDefs) =>
        val singlePackage = Map(
          "aliases" -> aliases(typeDefs, suffix),
          "traits" -> traits(typeDefs, suffix),
          "classes" -> classes(typeDefs, suffix),
          "constraints" -> constraints(typeDefs ++ callsPrimitiveParameters),
          "validations" -> validations(typeDefs)
        )
        val callValidations = if (pckg == "paths") Some("call_validations" -> validations(calls)) else None
        val fullPackage = singlePackage ++ callValidations.toSeq.toMap
        fullPackage + ("package" -> (pckg + suffix)) + ("imports" -> imports(fullPackage, pckg, suffix))
      })

    val output = engine.layout(templateName, map ++ allPackages)
    output.replaceAll("\u2B90", "")
  }

  // TODO for validators, tests and controllers we'll need a chain of dependencies
  // FIXME very fragile implementation
  private def imports(packageInfo: Map[String, Iterable[Map[String, Any]]], pckg: String, suffix: String) = {
    val allImports = packageInfo.values.flatMap(_.filter(_.get("dependencies").nonEmpty)).
      flatMap(_.apply("dependencies").asInstanceOf[Iterable[Reference]]).toSeq.distinct
    val neededImports = allImports.filterNot(_.packageName == pckg + suffix).filterNot(_.packageName.isEmpty)
    val result = neededImports.groupBy(_.packageName).flatMap { packageGroup =>
      if (packageGroup._2.size > 2) Seq(packageGroup._1 + "._")
      else packageGroup._2.map(_.qualifiedName)
    }
    val objectDependencies =
      if (suffix.nonEmpty) result.filterNot{pkg => pkg.startsWith("scala") || pkg.startsWith("java")}.map(addSuffix(suffix))
      else Nil
    val correctedResult = result ++ objectDependencies
    correctedResult.toSeq.distinct.filterNot(_.startsWith(pckg + suffix)).map(r => Map("name" -> r))
  }

  // FIXME very fragile implementation
  private def addSuffix(suffix: String)(importStr: String) =
    if (importStr.endsWith("._")) importStr.substring(0, importStr.length - 2) + suffix + "._"
    else importStr.substring(0, importStr.lastIndexOf(".")) + suffix + "." +
      importStr.substring(importStr.lastIndexOf(".") + 1, importStr.length) + suffix

  private def traits(types: Map[Reference, Type], suffix: String) =
    types collect {
      case (k, t: TypeDef) if discriminators.contains(k) => typeDefProps(k, t, t.fields, suffix)
    }

  private def aliases(types: Map[Reference, Type], suffix: String) =
    types collect {
      case (k, v: Container) => mapForAlias(suffix, k, v)
      case (k, v: PrimitiveType) => mapForAlias(suffix, k, v)
    }

  def mapForAlias(suffix: String, k: Reference, v: Type): Map[String, Object] = {
    Map(
      "name" -> k.typeAlias(suffix = suffix),
      "typeName" -> k.typeAlias(),
      "creator_method" -> k.typeAlias(prefix = "create", suffix = suffix),
      "alias" -> v.imports.headOption.getOrElse(v.name.simple),
      "generator" -> k.typeAlias(suffix = suffix),
      "generator_name" -> generatorNameForType(v),
      "underlying_type" -> v.imports.headOption.map { _ => v.nestedTypes.map(_.name.typeAlias()).mkString(", ")},
      "dependencies" -> (k +: v.nestedTypes.map(_.name))
    )
  }

  private def classes(types: Map[Reference, Type], suffix: String) =
    types collect {
      case (k, t: TypeDef) if !k.simple.contains("AllOf") && !k.simple.contains("OneOf") =>
        val traitName = discriminators.get(k).map(_ => Map("name" -> k.typeAlias()))
        typeDefProps(k, t, t.fields, suffix) + ("trait" -> traitName)
      case (k, t: Composite) =>
        val fields = dereferenceFields(t).distinct
        typeDefProps(k, t, fields, suffix) + ("trait" -> t.root.map(r => Map("name" -> r.className)))
    }

  private def dereferenceFields(t: Composite): Seq[Field] =
    t.descendants flatMap {
      case td: TypeDef => td.fields
      case r: TypeRef => allTypes.get(r.name) match {
        case Some(td: TypeDef) => td.fields
        case Some(c: Composite) => dereferenceFields(c)
        case other => throw new IllegalStateException(s"Unexpected contents of Composite $r: $other")
      }
    }

  private def typeDefProps(k: Reference, t: Type, fields: Seq[Field], suffix: String): Map[String, Object] = {
    Map(
      "name" -> k.typeAlias(suffix = suffix),
      "class_name" -> k.typeAlias(),
      "creator_method" -> k.typeAlias(prefix = "create", suffix = suffix),
      "fields" -> fields.zipWithIndex.map { case (f, i) =>
        Map(
          "name" -> escape(f.name.simple),
          "generator" -> generatorNameForType(f.tpe),
          "typeName" -> f.tpe.name.typeAlias(),
          "last" -> (i == fields.size - 1)
        )
      },
      "dependencies" -> (k +: fields.map(_.tpe.name))
    )
  }

}


trait ScalaTestDataGenerator {

  val generatorsTemplateName = "generators.mustache"

  val generatorsSuffix = "Generator"

  def generatorNameForType: (Type) => String = {
    case s: PrimitiveType => primitiveType(s)
    case c: Container => containerType(c)
    case r: TypeRef => r.name.typeAlias(suffix = generatorsSuffix)
    case o => s"generator name for $o" // relativeGeneratorName(tpe, thisType)
  }

  private def containerType(c: Container): String = {
    val innerGenerator = generatorNameForType(c.tpe)
    val className = c.tpe.name.typeAlias()
    c match {
      case Opt(tpe, _) => s"Gen.option($innerGenerator)"
      case Arr(tpe, _, _) => s"Gen.containerOf[List,$className]($innerGenerator)"
      case c@CatchAll(tpe, _) => s"_genMap[String,$className](arbitrary[String], $innerGenerator)"
    }
  }

  private def primitiveType(tpe: Type) = s"arbitrary[${tpe.name.className}]"

}

trait ScalaModelGenerator {

  val modelSuffix = ""

  val modelTemplateName = "model.mustache"
}

trait PlayValidatorsGenerator {

  val validatorsSuffix = "Validator"
  val constraintsSuffix = "Constraints"
  val validatorsTemplateName = "play_validation.mustache"

  type Validations = Iterable[Map[String, Any]]
  def model: StrictModel

  def validations(typeDefs: Map[Reference, Type]) = validations0(typeDefs.toSeq).flatten

  def validations(calls: Seq[ApiCall]) = calls filterNot { _.handler.parameters.isEmpty } map { call =>
    val fields = call.handler.parameters.zipWithIndex.map { case (p, i) =>
      val tpe = model.findParameter(p).typeName
      Map(
        "field_name" -> escape(p.name.simple),
        "field_type" -> tpe.name.typeAlias(),
        "field_raw_type" -> tpe.name,
        "validation_name" -> (if (tpe.isInstanceOf[PrimitiveType]) p.name else tpe.name).typeAlias(suffix = validatorsSuffix),
        "last" -> (i == call.handler.parameters.size - 1)
      )
    }
    Map(
      "constraint_name" -> call.asReference.typeAlias(suffix = constraintsSuffix),
      "validation_name" -> call.asReference.typeAlias(suffix = validatorsSuffix),
      "class_name" -> call.asReference.typeAlias(),
      "fields" -> fields,
      "dependencies" -> (call.asReference +: fields.map(_.apply("field_raw_type")))
    )
  }

  private def validations0(types: Iterable[(Reference, Type)]): Iterable[Validations] =
    types collect {
      case (r, t: TypeDef) if !r.simple.contains("AllOf") && !r.simple.contains("OneOf") => // FIXME copy-pasted and fragile, what if this in actual name of the parameter
        Seq(Map(
          "constraint_name" -> r.typeAlias(suffix = constraintsSuffix),
          "validation_name" -> r.typeAlias(suffix = validatorsSuffix),
          "class_name" -> r.typeAlias(),
          "fields" -> t.fields.zipWithIndex.map { case (f, i) =>
            Map(
              "field_name" -> escape(f.name.simple),
              "validation_name" -> (if (f.tpe.isInstanceOf[PrimitiveType]) f.name else f.tpe.name).typeAlias(suffix = validatorsSuffix),
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

  def validatorFromReference(r: Reference): Seq[Map[String, String]] = {
    Seq(Map(
      "constraint_name" -> r.typeAlias(suffix = constraintsSuffix),
      "validation_name" -> r.typeAlias(suffix = validatorsSuffix),
      "field_name" -> r.simple,
      "class_name" -> r.typeAlias()
    ))
  }

  def constraints(typeDefs: Map[Reference, Type]) = constraints0(typeDefs.toSeq).flatten

  private def constraints0(types: Iterable[(Reference, Type)]): Iterable[Validations] =
    types collect {
      case (r: Reference, t: PrimitiveType) =>
        Seq(Map(
          "restrictions" -> t.meta.constraints.filterNot(_.isEmpty).zipWithIndex.map { case (c, i) =>
            Map(
              "name" -> c,
              "last" -> (i == t.meta.constraints.length - 1)
            )
          },
          "constraint_name" -> r.typeAlias(suffix = constraintsSuffix),
          "validation_name" -> r.typeAlias(suffix = validatorsSuffix),
          "type_name" -> t.name.typeAlias(),
          "class_name" -> r.typeAlias()
        ))
      case (r, t: TypeDef) =>
        constraints0(t.fields.map { f => f.name -> f.tpe }).flatten
    }

}

