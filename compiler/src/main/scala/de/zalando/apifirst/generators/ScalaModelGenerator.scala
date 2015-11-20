package de.zalando.apifirst.generators

import de.zalando.apifirst.Application.{DiscriminatorLookupTable, TypeLookupTable}
import de.zalando.apifirst.Domain._
import de.zalando.apifirst.ScalaName._
import de.zalando.apifirst.new_naming.Reference
import org.fusesource.scalate.TemplateEngine

/**
  * @author  slasch 
  * @since   16.11.2015.
  */

class ScalaGenerator(allTypes: TypeLookupTable, discriminators: DiscriminatorLookupTable = Map.empty)
  extends ScalaModelGenerator with ScalaTestDataGenerator with PlayValidatorsGenerator {

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
    val augmented = map ++ Map(
      "packages" -> typesByPackage.toList.map { case (pckg, typeDefs) =>
        Map(
          "package" -> (pckg + suffix),
          "imports" -> imports(typeDefs, pckg, suffix),
          "aliases" -> aliases(typeDefs, suffix),
          "traits"  -> traits(typeDefs, suffix),
          "classes" -> classes(typeDefs, suffix),
          "constraints" -> constraints(typeDefs)
        )
      })
    val output = engine.layout(templateName, augmented)
    output.replaceAll("\u2B90", "")
  }

  // TODO for validators, tests and controllers we'll need a chain of dependencies
  // FIXME very fragile implementation
  private def imports(typeDefs: Map[Reference, Type], pckg: String, suffix: String) = {
    val allImports = typeDefs.values.flatMap { v => v +: v.nestedTypes }.filter(_.name.parts.nonEmpty).toSeq.distinct
    val neededImports = allImports.filterNot(_.name.packageName == pckg + suffix).filterNot(_.name.packageName.isEmpty)
    val result = neededImports.groupBy(_.name.packageName).flatMap { packageGroup =>
      if (packageGroup._2.size > 2) Seq(packageGroup._1 + "._")
      else packageGroup._2.map(_.name.qualifiedName)
    }
    val correctedResult = if (suffix.nonEmpty) result ++ result.map(addSuffix(suffix)) else result
    correctedResult.toSeq.distinct.filterNot(_.startsWith(pckg+suffix)).map(r => Map("name" -> r))
  }

  // FIXME very fragile implementation
  private def addSuffix(suffix: String)(importStr: String) =
    if (importStr.endsWith("._")) importStr.substring(0,importStr.length-2) + suffix + "._"
    else importStr.substring(0,importStr.lastIndexOf(".")) + suffix + "." +
      importStr.substring(importStr.lastIndexOf(".")+1,importStr.length) + suffix

  private def traits(types: Map[Reference, Type], suffix: String) =
    types collect {
      case (k, t: TypeDef) if discriminators.contains(k) => typeDefProps(k, t, t.fields, suffix)
    }

  private def aliases(types: Map[Reference, Type], suffix: String) =
    types collect {
      case (k, v: Container) =>
        Map(
          "name" -> k.typeAlias(suffix = suffix),
          "typeName" -> k.typeAlias(),
          "creator_method" -> k.typeAlias(prefix = "create", suffix = suffix),
          "alias" -> v.imports.head,
          "generator" -> k.typeAlias(suffix = suffix),
          "generator_name" -> generatorNameForType(v),
          "underlying_type" -> v.nestedTypes.map(_.name.typeAlias()).mkString(", ")
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
          "last" -> (i == fields.size - 1),
          "top_validations" -> validations(f.name -> f.tpe),
          "bottom_validations" -> bottomValidations(f.name -> f.tpe)
        )
      }
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
    case o => s"generator name for $o"// relativeGeneratorName(tpe, thisType)
  }

  private def containerType(c: Container): String = {
    val innerGenerator = generatorNameForType(c.tpe)
    val className = c.tpe.name.typeAlias()
    c match {
      case Opt(tpe, _)          => s"Gen.option($innerGenerator)"
      case Arr(tpe, _, _)       => s"Gen.containerOf[List,$className]($innerGenerator)"
      case c @ CatchAll(tpe, _) => s"_genMap[String,$className](arbitrary[String], $innerGenerator)"
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

  def validations(typeDefs: (Reference, Type)) =
    validations0(typeDefs).flatten

  private def validations0(types: (Reference,Type)): Iterable[Iterable[Map[String, Any]]] =
    Seq(types) collect {
      case (k, t: TypeDef) =>
        Seq.empty
      case (_, t: TypeRef) =>
        Seq(Map(
          "constraint_name" -> t.name.typeAlias(suffix = constraintsSuffix),
          "field_name" -> t.name.simple
        ))
      case (k, t: Composite) =>
        Seq.empty
      case (k, t: Container) =>
        Seq.empty
    }

  def bottomValidations(typeDefs: (Reference, Type)) =
    bottomValidations0(typeDefs).flatten

  private def bottomValidations0(types: (Reference,Type)): Iterable[Iterable[Map[String, Any]]] =
    Seq(types) collect {
      case (k, t: PrimitiveType) =>
        Seq(Map(
          "constraint_name" -> t.name.typeAlias(suffix = constraintsSuffix),
          "field_name" -> t.name.simple
        ))
    }

  def constraints(typeDefs: Map[Reference, Type]) =
    constraints0(typeDefs.toSeq).flatten

  private def constraints0(types: Iterable[(Reference,Type)]): Iterable[Iterable[Map[String, Any]]] =
    types collect {
      case (r: Reference, t: PrimitiveType) if t.meta.constraints.nonEmpty =>
        Seq(Map(
          "restrictions" -> t.meta.constraints.filterNot(_.isEmpty).zipWithIndex.map { case (c, i) =>
            Map(
              "name" -> c,
              "last" -> (i == t.meta.constraints.length - 1)
            )
          },
          "constraint_name" -> r.typeAlias(suffix = constraintsSuffix),
          "type_name" -> t.name.typeAlias()
        ))
      case (r, t: TypeDef) =>
        constraints0(t.fields.map{f => (f.name, f.tpe)}).flatten
      case (r, t: Composite) =>
        constraints0(t.descendants.map{d => (d.name, d)}).flatten
      case (r, t: Container) =>
        constraints0(Seq(r -> t.tpe)).flatten
    }

  def flatValidations(types: Seq[Type]) = types collect {
    case t: TypeDef =>
      val constraintName = t.name.typeAlias(suffix = constraintsSuffix)
       s"$constraintName.applyConstraints(instance.${t.name.simple})"
    case t: PrimitiveType =>
      val constraintName = t.name.typeAlias(suffix = constraintsSuffix)
      s"$constraintName.applyConstraints(instance.${t.name.simple})"
    case t: TypeRef =>
      val constraintName = t.name.typeAlias(suffix = constraintsSuffix)
      s"$constraintName.applyConstraints(instance.${t.name.simple})"
  }

  def nestedValidations(types: Seq[Type]) = types collect {
    case t: Container =>
      val constraintName = t.name.typeAlias(suffix = constraintsSuffix)
      s"instance.${t.name.simple} map $constraintName.applyConstraints"
    case t: Composite =>
      val validatorName = t.name.typeAlias(suffix = validatorsSuffix)
      s"instance.${t.name.simple} map { new $validatorName(_) }.result"
  }

}

