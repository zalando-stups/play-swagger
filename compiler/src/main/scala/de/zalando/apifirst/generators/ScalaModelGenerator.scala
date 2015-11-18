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
class ScalaTestDataGenerator(allTypes: TypeLookupTable)
  extends ScalaGenerator(allTypes, Map.empty) {

  val templateName = "generators.mustache"

  val suffix = "Generator"

  val classAugmenter: Field => Map[String, Any] = f =>
    Map("generator" -> generatorNameForType(f.tpe))

  val aliasAugmenter: Container => Map[String, Any] = t =>
    Map("generator" -> generatorNameForType(t))

  private def generatorNameForType: (Type) => String = {
    case s: PrimitiveType => primitiveType(s)
    case c: Container => containerType(c)
    case r: TypeReference =>
      r.name.typeAlias(suffix)
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
class ScalaModelGenerator(allTypes: TypeLookupTable, discriminators: DiscriminatorLookupTable = Map.empty)
  extends ScalaGenerator(allTypes, discriminators) {

  val suffix = ""

  val templateName = "model.mustache"

  val classAugmenter: Field     => Map[String, Any] = f =>
    Map("typeName" -> f.tpe.name.typeAlias())

  val aliasAugmenter: Container => Map[String, Any] = v => Map(
    "alias" -> v.imports.head,
    "underlying_type" -> v.nestedTypes.map(_.name.typeAlias()).mkString(", ")
  )
}

abstract class ScalaGenerator(allTypes: TypeLookupTable, discriminators: DiscriminatorLookupTable) {

  def templateName: String
  def suffix: String

  def apply(fileName: String): String = {
    if (allTypes.values.forall(_.isInstanceOf[PrimitiveType])) ""
    else nonEmptyTemplate(Map("main_package" -> fileName))
  }

  def classAugmenter: Field => Map[String, Any]
  def aliasAugmenter: Container => Map[String, Any]

  def nonEmptyTemplate(map: Map[String, Any]): String = {
    val engine = new TemplateEngine
    val typesByPackage = allTypes.groupBy(_._1.packageName)
    val augmented = map ++ Map(
      "packages" -> typesByPackage.toList.map { case (pckg, typeDefs) =>
        Map(
          "package" -> (pckg + suffix),
          "imports" -> imports(typeDefs, pckg),
          "aliases" -> aliases(typeDefs, aliasAugmenter),
          "traits"  -> traits(typeDefs),
          "classes" -> classes(typeDefs, classAugmenter)
        )
      })
    val output = engine.layout(templateName, augmented)
    output.replaceAll("\u2B90", "")
  }

  // FIXME if suffix nonempty, the type itself must be imported
  def imports(typeDefs: Map[Reference, Type], pckg: String) = {
    val allImports = typeDefs.values.flatMap(_.nestedTypes).filter(_.name.parts.nonEmpty).toSeq.distinct
    val neededImports = allImports.filterNot(_.name.packageName == pckg + suffix).filterNot(_.name.packageName.isEmpty)
    println(pckg + " - " + neededImports + " | " + allImports)
    val result = neededImports.groupBy(_.name.packageName).flatMap { packageGroup =>
      if (packageGroup._2.size > 2) Seq(packageGroup._1 + "._") // suffix +
      else packageGroup._2.map(_.name.qualifiedName) //  + suffix
    }
    result.toSeq.distinct.map(r => Map("name" -> r))
  }

  private def traits(types: Map[Reference, Type]) =
    types collect {
      case (k, t: TypeDef) if discriminators.contains(k) => typeDefProps(k, t, t.fields, classAugmenter)
    }

  private def aliases(types: Map[Reference, Type], augmenter: Container => Map[String, Any]) =
    types collect {
      case (k, v: Container) =>
        Map(
          "name" -> k.typeAlias(suffix)
        ) ++ augmenter(v)
    }

  def classes(types: Map[Reference, Type], augmenter: Field => Map[String, Any]) =
    types collect {
      case (k, t: TypeDef) if !k.simple.contains("AllOf") && !k.simple.contains("OneOf") =>
        val traitName = discriminators.get(k).map(_ => Map("name" -> k.typeAlias()))
        typeDefProps(k, t, t.fields, augmenter) + ("trait" -> traitName)
      case (k, t: Composite) =>
        val fields = dereferenceFields(t).distinct
        typeDefProps(k, t, fields, augmenter) + ("trait" -> t.root.map(r => Map("name" -> r.className)))
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

  def typeDefProps(k: Reference, t: Type, fields: Seq[Field], augmenter: Field => Map[String, Any]): Map[String, Object] = {
    Map(
      "name" -> k.typeAlias(suffix),
      "class_name" -> k.typeAlias(),
      "fields" -> fields.zipWithIndex.map { case (f, i) =>
        Map(
          "name" -> escape(f.name.simple),
          "last" -> (i == fields.size - 1)
        ) ++ augmenter(f)
      }
    )
  }

}
