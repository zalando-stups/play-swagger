package de.zalando

import de.zalando.apifirst.Application.TypeLookupTable
import de.zalando.apifirst.Domain.{TypeDef, Type, Container}
import de.zalando.apifirst.ScalaName
import de.zalando.apifirst.new_naming.Reference
import org.fusesource.scalate.TemplateEngine
import ScalaName._

/**
  * @author  slasch 
  * @since   16.11.2015.
  */
class ScalaModelGenerator(types: TypeLookupTable) {

  def apply(fileName: String): String = {
    if (types.isEmpty) "" else nonEmptyTemplate(Map("main_package" -> fileName))
  }

  private def nonEmptyTemplate(map: Map[String, Any]): String = {
    val engine = new TemplateEngine
    val typesByPackage = types.groupBy(_._1.packageName)
    val augmented = map ++ Map(
      "packages" -> typesByPackage.toList.map { case (pckg, typeDefs) =>
        Map(
          "package" -> pckg,
          "aliases" -> aliases(typeDefs),
          "traits"  -> traits(typeDefs),
          "classes" -> classes(typeDefs)
        )
      })
    val output = engine.layout("model.mustache", augmented)
    output
  }

  private def aliases(types: Map[Reference, Type]) =
    types collect {
      case (k, v: Container) => Map(
        "name" -> k.className,
        "alias" -> v.imports.head,
        "underlying_type" -> v.nestedTypes.map(_.name.simple).mkString(", ")
      )
    }

  private def classes(types: Map[Reference, Type]) =
    types collect {
      case (k, t: TypeDef) => Map(
        "name" -> k.className,
        "fields" -> t.fields.zipWithIndex.map { case (f,i) =>
          Map(
            "name" -> f.name.simple,
            "typeName" -> f.tpe.name.simple,
            "last" -> (i == t.fields.size-1)
          )
        }
      )
    }

  private def traits(types: Map[Reference, Type]) = Map.empty

}
