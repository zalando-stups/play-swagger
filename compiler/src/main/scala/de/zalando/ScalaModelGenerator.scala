package de.zalando

import de.zalando.apifirst.Application.{DiscriminatorLookupTable, TypeLookupTable}
import de.zalando.apifirst.Domain._
import de.zalando.apifirst.ScalaName
import de.zalando.apifirst.new_naming.Reference
import org.fusesource.scalate.TemplateEngine
import ScalaName._

/**
  * @author  slasch 
  * @since   16.11.2015.
  */
class ScalaModelGenerator(allTypes: TypeLookupTable, discriminators: DiscriminatorLookupTable = Map.empty) {

  def apply(fileName: String): String = {
    if (allTypes.isEmpty) "" else nonEmptyTemplate(Map("main_package" -> fileName))
  }

  private def nonEmptyTemplate(map: Map[String, Any]): String = {
    val engine = new TemplateEngine
    val typesByPackage = allTypes.groupBy(_._1.packageName)
    val augmented = map ++ Map(
      "packages" -> typesByPackage.toList.map { case (pckg, typeDefs) =>
        Map(
          "package" -> pckg,
          "aliases" -> aliases(typeDefs),
          "traits" -> traits(typeDefs),
          "classes" -> classes(typeDefs)
        )
      })
    val output = engine.layout("model.mustache", augmented)
    output.replaceAll("\u2B90", "")
  }

  private def aliases(types: Map[Reference, Type]) =
    types collect {
      case (k, v: Container) =>
        Map(
          "name" -> k.className,
          "alias" -> v.imports.head,
          "underlying_type" -> v.nestedTypes.map(_.name.simple).mkString(", ")
        )
    }

  private def classes(types: Map[Reference, Type]) =
    types collect {
      case (k, t: TypeDef) if !k.simple.contains("AllOf") && !k.simple.contains("OneOf") =>
        val traitName = discriminators.get(k).map(_ => Map("name" -> k.className))
        typeDefProps(k, t, t.fields) + ("trait" -> traitName)
      case (k, t: Composite) =>
        val fields = dereferenceFields(t).distinct
        val traitName = lookupTraitName(t, fields)
        typeDefProps(k, t, fields) + ("trait" -> traitName.map(t => Map("name" -> t)))
    }

  private def lookupTraitName(t: Composite, fields: Seq[Field]): Option[String] =
    t.descendants.collect {
      case r: TypeReference if discriminators.contains(r.name) &&
        fields.exists(_.name.simple == discriminators(r.name)) => Some(r.name.simple)
      case r: TypeReference => allTypes.get(r.name) match {
        case Some(c: Composite) => lookupTraitName(c, fields)
        case _ => None
      }
    }.headOption.flatten

  private def traits(types: Map[Reference, Type]) =
    types collect {
      case (k, t: TypeDef) if discriminators.contains(k) => typeDefProps(k, t, t.fields)
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

  private def typeDefProps(k: Reference, t: Type, fields: Seq[Field]): Map[String, Object] = {
    Map(
      "name" -> k.className,
      "fields" -> fields.zipWithIndex.map { case (f, i) =>
        Map(
          "name" -> f.name.simple,
          "typeName" -> f.tpe.name.simple,
          "last" -> (i == fields.size - 1)
        )
      }
    )
  }

}
