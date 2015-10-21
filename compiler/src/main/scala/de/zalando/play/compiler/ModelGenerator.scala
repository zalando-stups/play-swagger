package de.zalando.play.compiler

import de.zalando.apifirst.Application.Model
import de.zalando.apifirst.Domain._

import scala.language.postfixOps

/**
 * @since 16.08.2015
 */
object ModelGenerator extends ChainedGenerator {
  override def generators = new TraitGenerator :: new ClassGenerator :: Nil
}

class ClassGenerator extends TraitGenerator {

  override val placeHolder = "#CLASSES#"

  val defaultImports = Set.empty[String]

  override def generate(namespace: String)(implicit ast: Model): Iterable[(Set[String], String)] = {
    val namedClasses = ast.definitions map generateSingleTypeDef(namespace, defaultImports)
    namedClasses.flatten.toSet
  }

  protected def generateSingleTypeDef(namespace: String, imports: Set[String])(typeDef: Type)
      (implicit ast: Model): Option[(Set[String], String)] = {
    typeDef match {
      case t@TypeDef(typeName, fields, extend, meta) =>
        Some((t.imports ++ imports, classCode(typeName, t)))
      // TODO add support for anonymous Objects (example instagram)
      // TODO add support for enums
      case c: Container =>
        Some((c.imports ++ imports, aliasCode(c.tpe.name, c)))
        // generateSingleTypeDef(namespace, c.imports ++ imports)(c.field.kind)
      case r: ReferenceObject =>
        // some validation could be added here
        None
      case _ =>
        // probably just simple type
        None
    }
  }

  private def classCode(name: TypeName, typeDef: TypeDef)(implicit ast: Model) = {
    s"""${comment(typeDef.meta, "")}
        |case class ${TypeName.escape(name.asSimpleType)}(
        |${classFields(typeDef).mkString(",\n")}
        |)${extendClause(typeDef, selfExtend = true)}
        | """.stripMargin.split("\n").filter(_.trim.nonEmpty).mkString("\n")
  }

  private def classFields(typeDef: TypeDef)(implicit ast: Model) =
    typeDef.allFields map { f =>
      s"""${comment(f.meta)}
          |$PAD${TypeName.escape(f.name.simpleName)}: ${TypeName.escapeName(f.tpe.name.relativeTo(typeDef.name))}""".stripMargin
    }

  private def aliasCode(name: TypeName, typeDef: Type)(implicit ast: Model) = {
    s"""${comment(typeDef.meta, "")}
       |type ${TypeName.escape(name.asSimpleType)} = ${TypeName.escapeName(typeDef.name.relativeTo(typeDef.name))}""".stripMargin
  }

}

class TraitGenerator extends GeneratorBase {

  override val placeHolder = "#TRAITS#"

  override def generate(namespace: String)(implicit ast: Model): Iterable[(Set[String], String)] =
    traitsToGenerate flatMap { reference =>
      reference.resolve map { typeDef =>
        val code = traitCode(typeDef)
        (typeDef.imports, code)
      }
    }

  def extendClause(typeDef: TypeDef, selfExtend: Boolean)(implicit ast: Model): String = {
    val selfExtending = traitsToGenerate find (typeDef.name == _.name && selfExtend)
    val extend = typeDef.extend ++ selfExtending.toSeq
    val extendClause = extend map {
      _.name
    } map traitName mkString " with "
    (if (extendClause.nonEmpty) " extends " else "") + extendClause
  }

  private def traitCode(typeDef: TypeDef)(implicit ast: Model) = {
    s"""|${comment(typeDef.meta, "")}
        |trait ${traitName(typeDef.name).asSimpleType}${extendClause(typeDef, selfExtend = false)} {
          |${traitFields(typeDef).mkString("\n")}
        |}""".stripMargin.split("\n").filter(_.trim.nonEmpty).mkString("\n")
  }

  private def traitFields(typeDef: TypeDef) = typeDef.fields map { f =>
    s"""${comment(f.meta)}
        |${PAD}def ${TypeName.escape(f.name.simpleName)}: ${f.tpe.name.relativeTo(f.name)}""".stripMargin
  }

  private def traitsToGenerate(implicit ast: Model): Set[Reference] =
    ast.definitions flatMap {
      case TypeDef(_, _, extend, meta) => extend
      case _ => Nil
    } toSet

  private def traitName(name: TypeName) = TypeName.escape {
    TypeName(name.namespace, name.asSimpleType + "Def").simpleName
  }
}
