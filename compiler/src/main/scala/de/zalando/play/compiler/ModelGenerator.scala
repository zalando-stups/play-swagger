package de.zalando.play.compiler

import java.text.SimpleDateFormat
import java.util.Date

import de.zalando.apifirst.Domain._

import scala.language.postfixOps

/**
 * @since 16.08.2015
 */
object ModelGenerator extends ChainedGenerator {
  override def generators = new TraitGenerator :: new ClassGenerator :: Nil
}

trait ChainedGenerator extends GeneratorBase {

  def generators: List[GeneratorBase]

  def defaultNamespace = "definitions"

  override val placeHolder = "#IMPORT#"

  def generate(namespace: String)(implicit model: ModelDefinition) = {
    val (imports, result) = generateNamespace(defaultNamespace, generators)
    if (result.nonEmpty) {
      val now = new SimpleDateFormat(nowFormat).format(new Date())
      val pkgName = s"package $namespace"

      val prefix = mainTemplate.
        replace(placeHolder, imports.map { i: String => "import " + i }.mkString("\n")).
        replace("#PACKAGE#", pkgName).
        replace("#NOW#", now)
      Seq((imports, prefix + result))
    } else Seq((imports, result))
  }

  def generateNamespace(namespace: String, generators: Seq[GeneratorBase])(implicit model: ModelDefinition): (Set[String], String) = {

    val thisNamespace = model.flatMap(_.nestedTypes).filterNot(_.isInstanceOf[Reference])

    val template =
      if (thisNamespace.nonEmpty) {
        val nested = thisNamespace groupBy (_.name.namespace) map { case (space, typeDefs) =>
          generateNamespace(space.nestedNamespace, generators)(typeDefs)
        }
        val nestedStr = nested.values map pad mkString "\n"
        namespaceTemplate.
          replace("#NAMESPACE#", namespace).
          replace("#NESTED#", nestedStr)
      } else
        if (model.exists(!_.isInstanceOf[Reference])) {
          namespaceTemplate.
            replace("#NAMESPACE#", namespace).
            replace("#NESTED#", "")
        }
      else
        ""

    val endResult = generators.foldLeft((Set.empty[String], template)) { case (result, generator) =>
      val info = generator.generate(namespace)
      val imports = info.map(_._1)
      val code = info map (_._2) map pad mkString "\n"
      (imports.flatten.toSet ++ result._1, result._2.replace(generator.placeHolder, code))
    }
    endResult
  }

  protected val nowFormat = "dd.MM.yyyy HH:mm:ss"

  protected[compiler] val mainTemplate =
    s"""#PACKAGE#
       |
       |#IMPORT#
       |
       |/** @since #NOW# */
       |""".stripMargin


  protected[compiler] val namespaceTemplate =
    s"""
       |object #NAMESPACE# {#NESTED#
       |#TRAITS#
       |#CLASSES#
       |}""".stripMargin

}

/**
 * Dummy implementation to remove given placeHolder from the template
 * @param placeHolder the placeholder to remove
 */
class EmptyGenerator(override val placeHolder: String) extends GeneratorBase {
  override def generate(namespace: String)(implicit model: ModelDefinition): Iterable[(Set[String], String)] = Nil
}

class ClassGenerator extends TraitGenerator {

  override val placeHolder = "#CLASSES#"

  val defaultImports = Set.empty[String]

  override def generate(namespace: String)(implicit model: ModelDefinition): Iterable[(Set[String], String)] = {
    val namedClasses = model map generateSingleTypeDef(namespace, defaultImports)
    namedClasses.flatten.toSet
  }

  protected def generateSingleTypeDef(namespace: String, imports: Set[String])(typeDef: Type)
                                     (implicit model: ModelDefinition): Option[(Set[String], String)] = {
    typeDef match {
      case t@TypeDef(typeName, fields, extend, meta) =>
        Some((t.imports ++ imports, classCode(typeName, t)))
      // TODO add support for anonymous Objects (example instagram)
      // TODO add support for enums
      // TODO add support for catch-all property
      case c: Container =>
        generateSingleTypeDef(namespace, c.imports ++ imports)(c.field.kind)
      case r: ReferenceObject =>
        // some validation could be added here
        None
      case other: Entity =>
        println("Not generating class for entity " + other) // TODO
        None
      case _ =>
        // probably just simple type
        None
    }
  }

  private def classCode(name: TypeName, typeDef: TypeDef)(implicit model: ModelDefinition) = {
    s"""${comment(typeDef.meta, "")}
        |case class ${TypeName.escape(name.asSimpleType)}(
        |${classFields(typeDef).mkString(",\n")}
        |)${extendClause(typeDef, selfExtend = true)}
        | """.stripMargin.split("\n").filter(_.trim.nonEmpty).mkString("\n")
  }

  private def classFields(typeDef: TypeDef)(implicit model: ModelDefinition) =
    typeDef.allFields map { f =>
      s"""${comment(f.meta)}
          |$PAD${TypeName.escape(f.name.simpleName)}: ${TypeName.escapeName(f.kind.name.relativeTo(typeDef.name))}""".stripMargin
    }
}

class TraitGenerator extends GeneratorBase {

  override val placeHolder = "#TRAITS#"

  override def generate(namespace: String)(implicit model: ModelDefinition): Iterable[(Set[String], String)] =
    traitsToGenerate flatMap { reference =>
      reference.resolve map { typeDef =>
        val code = traitCode(typeDef)
        (typeDef.imports, code)
      }
    }

  def extendClause(typeDef: TypeDef, selfExtend: Boolean)(implicit model: ModelDefinition): String = {
    val selfExtending = traitsToGenerate find (typeDef.name == _.name && selfExtend)
    val extend = typeDef.extend ++ selfExtending.toSeq
    val extendClause = extend map {
      _.name
    } map traitName mkString " with "
    (if (extendClause.nonEmpty) " extends " else "") + extendClause
  }

  private def traitCode(typeDef: TypeDef)(implicit model: ModelDefinition) = {
    s"""|${comment(typeDef.meta, "")}
        |trait ${traitName(typeDef.name).asSimpleType}${extendClause(typeDef, selfExtend = false)} {
        |${traitFields(typeDef).mkString("\n")}
        |}""".stripMargin.split("\n").filter(_.trim.nonEmpty).mkString("\n")
  }

  private def traitFields(typeDef: TypeDef) = typeDef.fields map { f =>
    s"""${comment(f.meta)}
        |${PAD}def ${TypeName.escape(f.name.simpleName)}: ${f.kind.name.relativeTo(f.name)}""".stripMargin
  }

  private def traitsToGenerate(implicit model: ModelDefinition): Set[Reference] =
    model flatMap {
      case TypeDef(_, _, extend, meta) => extend
      case _ => Nil
    } toSet

  private def traitName(name: TypeName) = TypeName.escape {
    TypeName(name.namespace, name.asSimpleType + "Def").simpleName
  }
}

trait GeneratorBase {

  def pad(s: String) = s split "\n" map { l => if (l.trim.nonEmpty) PAD + l else l } mkString "\n"

  def generate(namespace: String)(implicit model: ModelDefinition): Iterable[(Set[String], String)]

  def placeHolder: String

  val PAD = "  "

  def comment(meta: TypeMeta, pad: String = PAD) = meta.comment.map {
    _.split("\n").map(c => if (c.nonEmpty) pad + "// " + c else c).mkString("\n")
  } getOrElse ""

}
