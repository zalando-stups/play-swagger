package de.zalando.play.compiler

import java.text.SimpleDateFormat
import java.util.Date

import de.zalando.apifirst.Domain._

import scala.language.postfixOps

/**
 * @since 16.08.2015
 */
object ModelGenerator {

  val PAD = "  "

  def generate(packageName: Option[String],
               namespace: String = "definitions")(implicit model: ModelDefinition) = {
    val (imports, result) = generateNamespace(namespace)
    if (result.nonEmpty) {
      val now = new SimpleDateFormat(nowFormat).format(new Date())
      val pkgName = packageName.fold("") { p => s"package $p" }

      val prefix = mainTemplate.
        replace("#IMPORT#", imports).
        replace("#PACKAGE#", pkgName).
        replace("#NOW#", now)
      prefix + result
    } else result
  }

  def generateNamespace(namespace: String)(implicit model: ModelDefinition): (String, String) = {

    def pad(s: String) = s split "\n" map { l => PAD + l } mkString "\n"

    val thisNamespace = model.flatMap(_.nestedTypes)

    val nested = thisNamespace groupBy (_.name.namespace) map { case (space, typeDefs) =>
      generateNamespace(space.simpleName)(typeDefs)
    } values

    val traitInfo = generateTraits(namespace)
    val traitImports = traitInfo.map(_._1)
    val traits = traitInfo.map(_._2)
    val classInfo = generateClasses(namespace)
    val classImports = classInfo.map(_._1)
    val classes = classInfo.map(_._2)
    val imports = (traitImports ++ classImports).flatten.map { i: String => "import " + i }.mkString("\n")
    lazy val result = namespaceTemplate.
      replace("#NAMESPACE#", namespace).
      replace("#NESTED#", nested map pad mkString "\n").
      replace("#TRAITS#", traits map pad mkString "\n").
      replace("#CLASSES#", classes map pad mkString "\n")
    val endResult = if (classes.isEmpty && traits.isEmpty) "" else result
    (imports, endResult)
  }

  private def traitsToGenerate(implicit model: ModelDefinition): Set[Reference] =
    model flatMap {
      case TypeDef(_, _, extend, meta) => extend
      case _ => Nil
    } toSet

  protected def generateTraits(namespace: String)(implicit model: ModelDefinition): Iterable[(Set[String], String)] =
    traitsToGenerate flatMap { reference =>
      reference.resolve map { typeDef =>
        val code = traitCode(typeDef)
        (typeDef.imports, code)
      }
    }

  private def traitCode(typeDef: TypeDef)(implicit model: ModelDefinition) = {
    s"""|${comment(typeDef.meta)}
        |trait ${traitName(typeDef.name).asSimpleType}${extendClause(typeDef, selfExtend = false)} {
        |${traitFields(typeDef).mkString("\n")}
        |}""".stripMargin.split("\n").filter(_.trim.nonEmpty).mkString("\n")
  }

  private def traitFields(typeDef: TypeDef) = typeDef.fields map { f =>
    s"""$PAD${comment(f.meta)}
        |${PAD}def ${TypeName.escape(f.name.simpleName)}: ${f.kind.name.relativeTo(f.name)}""".stripMargin
  }

  private def comment(meta: TypeMeta) = meta.comment.map {
    _.split("\n").map(c => "// " + c).mkString("\n")
  } getOrElse ""

  private def traitName(name: TypeName) = TypeName.escape {
    TypeName(name.namespace, name.asSimpleType + "Def").simpleName
  }

  protected def generateClasses(namespace: String)(implicit model: ModelDefinition): Iterable[(Set[String], String)] = {
    val namedClasses = model map generateSingleTypeDef(namespace, Set.empty)
    namedClasses.flatten.toSet
  }

  def generateSingleTypeDef(namespace: String, imports: Set[String])(typeDef: Type)(implicit model: ModelDefinition): Option[(Set[String], String)] = {
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
    s"""${comment(typeDef.meta)}
        |case class ${TypeName.escape(name.asSimpleType)}(
        |${classFields(typeDef).mkString(",\n")}
        |)${extendClause(typeDef, selfExtend = true)}
        | """.stripMargin.split("\n").filter(_.trim.nonEmpty).mkString("\n")
  }

  def extendClause(typeDef: TypeDef, selfExtend: Boolean)(implicit model: ModelDefinition): String = {
    val selfExtending = traitsToGenerate find (typeDef.name == _.name && selfExtend)
    val extend = typeDef.extend ++ selfExtending.toSeq
    val extendClause = extend map {
      _.name
    } map traitName mkString " with "
    (if (extendClause.nonEmpty) " extends " else "") + extendClause
  }

  private def classFields(typeDef: TypeDef)(implicit model: ModelDefinition) =
    typeDef.allFields map { f =>
      s"""$PAD${comment(f.meta)}
          |$PAD${TypeName.escape(f.name.simpleName)}: ${TypeName.escapeName(f.kind.name.relativeTo(typeDef.name))}""".stripMargin
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
