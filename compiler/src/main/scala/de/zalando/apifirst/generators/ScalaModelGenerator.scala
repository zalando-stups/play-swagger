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
  extends ScalaModelGenerator with ScalaTestDataGenerator {

  def model(fileName: String) = apply(fileName, modelTemplateName, modelSuffix)
  def generators(fileName: String) = apply(fileName, generatorsTemplateName, generatorsSuffix)

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
          "classes" -> classes(typeDefs, suffix)
        )
      })
    val output = engine.layout(templateName, augmented)
    output.replaceAll("\u2B90", "")
  }

  // TODO for validators, tests and controllers we'll need a chain of dependencies
  private def imports(typeDefs: Map[Reference, Type], pckg: String, suffix: String) = {
    val allImports = typeDefs.values.flatMap { v => v +: v.nestedTypes }.filter(_.name.parts.nonEmpty).toSeq.distinct
    val neededImports = allImports.filterNot(_.name.packageName == pckg + suffix).filterNot(_.name.packageName.isEmpty)
    val result = neededImports.groupBy(_.name.packageName).flatMap { packageGroup =>
      if (packageGroup._2.size > 2) Seq(packageGroup._1 + "._")
      else packageGroup._2.map(_.name.qualifiedName)
    }
    val correctedResult = if (suffix.nonEmpty) result ++ result.map(addSuffix(suffix)) else result
    correctedResult.toSeq.distinct.map(r => Map("name" -> r))
  }

  // FIXME
  private def addSuffix(suffix: String)(importStr: String) =
    if (importStr.endsWith("._")) importStr.substring(importStr.length-2) + suffix + "._"
    else importStr.substring(0,importStr.lastIndexOf(".")) + suffix + "." + importStr.substring(importStr.lastIndexOf(".")+1,importStr.size) + suffix

  private def traits(types: Map[Reference, Type], suffix: String) =
    types collect {
      case (k, t: TypeDef) if discriminators.contains(k) => typeDefProps(k, t, t.fields, suffix)
    }

  private def aliases(types: Map[Reference, Type], suffix: String) =
    types collect {
      case (k, v: Container) =>
        Map(
          "name" -> k.typeAlias(suffix = suffix),
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
      case r: TypeReference => allTypes.get(r.name) match {
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
    case r: TypeReference => r.name.typeAlias(suffix = generatorsSuffix)
    case o => s"generator name for $o"// relativeGeneratorName(tpe, thisType)
  }

  private def containerType(c: Container): String = c match {
    case Opt(tpe, _) =>
      val innerGenerator = generatorNameForType(tpe)
      s"Gen.option($innerGenerator)"
    case Arr(tpe, _, _) =>
      val innerGenerator = generatorNameForType(tpe)
      s"Gen.containerOf[List,${tpe.name.className}]($innerGenerator)"
    case c @ CatchAll(tpe, _) =>
      // TODO generate non-empty map
      // FIXME this is not working yet
      s"Gen.const(${tpe.name.className})".replace("Map[", "Map.empty[")
  }

  private def primitiveType(tpe: Type) = s"arbitrary[${tpe.name.className}]"

  /*
  def generatorName(typeDef: Type): String = escape(typeDef.name.className + GEN)

  def relativeGeneratorName(typeDef: Type, thisType: TypeName)
    = escape(typeDef.name.relativeTo(thisType).replace(targetNamespace, defaultNamespace) + GEN)
  */

}
trait ScalaModelGenerator {

  val modelSuffix = ""

  val modelTemplateName = "model.mustache"
}


