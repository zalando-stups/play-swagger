package de.zalando.play.compiler

import java.text.SimpleDateFormat
import java.util.Date

import de.zalando.apifirst.Domain._

import scala.language.postfixOps

/**
 * @since 16.08.2015
 */
object ModelGenerator {

  import de.zalando.swagger.SchemaConverter.{removeNamespace, escape}

  def generate(packageName: Option[String],
               namespace: String = "definitions")(implicit model: ModelDefinition) = {
    val traitInfo = generateTraits(namespace)
    val traitImports = traitInfo.map(_._1)
    val traits = traitInfo.map("\n" + _._2)
    val classInfo = generateClasses(namespace)
    val classImports = classInfo.map(_._1)
    val classes = classInfo.map("\n" + _._2)
    val imports = (traitImports ++ classImports).flatten.map { i: String => "import " + i }.mkString("\n") // TODO what with transient imports?
    val now = new SimpleDateFormat(nowFormat).format(new Date())
    val result = template.
      replace("#NAMESPACE#", namespace).
      replace("#IMPORT#", imports).
      replace("#PACKAGE#", packageName.fold("") { p => s"package $p" }).
      replace("#NOW#", now).
      replace("#TRAITS#", traits.mkString("\n")).
      replace("#CLASSES#", classes.mkString("\n"))
    result
  }

  /*  protected def validate(model: ModelDefinition, namespace: String): Iterable[String] = {
      val defined = model.keys.toSet
      val fields = for {
        (name, typedef) <- model
        field <- typedef match {
          case c if c.isInstanceOf[Container] => Seq(c.asInstanceOf[Container].field)
          case e if e.isInstanceOf[TypeDef] => e.asInstanceOf[TypeDef].fields
          case _ => Seq.empty[Field]
        }
      } yield (typedef, field)
      val result = fields map {
        case (typedef, f@Field(fName, ref: ReferenceObject, _)) =>
          if (!defined.contains(ref.url)) {
            val expected = removeNamespace(ref, namespace)
            Seq(
              s"Field <$fName> of type <${typedef.name}> is defined being of type <$expected> but <$expected> could not be found")
          } else Seq.empty[String]
        case (typedef, f@Field(fName, ref: Reference, _)) =>
          Seq(s"Unsupported Reference type for field <$fName> of type <${typedef.name}>")
        case (typedef, f@Field(fName, container: Container, _)) =>
          val typeName = fName + "." + container.name
          validate(Map(typeName -> container), namespace)
        case (typedef, f@Field(fName, typeDef: TypeDef, _)) =>
          val extend = typeDef.extend.map(f => f.name -> f)
          validate((extend :+ typeDef.name -> typeDef).toMap, namespace)
        case (typeDef: TypeDef, _) =>
          val extend = typeDef.extend.map(f => f.name -> f)
          validate(extend.toMap, namespace)

        case (a, b) =>
          println("Nothing found for " + a + " and " + b)
          Seq.empty[String]
      }
      result.flatten
    }*/

  private def traitsToGenerate(implicit model: ModelDefinition): Set[Reference] =
    model flatMap {
      _._2 match {
        case TypeDef(_, _, extend, meta) => extend
        case _ => Nil
      }
    } toSet

  protected def generateTraits(namespace: String)(implicit model: ModelDefinition): Iterable[(Set[String], String)] =
    traitsToGenerate flatMap { reference =>
      reference.resolve map { typeDef =>
        val code = traitCode(typeDef.name, namespace, typeDef)
        (typeDef.imports, code)
      }
    }

  private def traitCode(name: String, namespace: String, typeDef: TypeDef)(implicit model: ModelDefinition) = {
    s"""  ${comment(typeDef.meta)}
        |  trait ${traitName(name)}${extendClause(typeDef, namespace, selfExtend = false)} {
        |${traitFields(typeDef, namespace).mkString("\n")}
        |  }""".stripMargin.split("\n").filter(_.trim.nonEmpty).mkString("\n")
  }

  private def traitFields(typeDef: TypeDef, namespace: String) = typeDef.fields map { f =>
    s"""    ${comment(f.meta)}
        |    def ${escape(f.name)}: ${removeNamespace(namespace)(f.kind.name)}""".stripMargin
  }

  private def comment(meta: TypeMeta) = meta.comment.map {
    _.split("\n").map(c => "// " + c).mkString("\n")
  } getOrElse ""

  private def traitName(name: String) = name + "Def"

  private def removeNamespaceFromRef(namespace: String)(ref: Reference) =
    removeNamespace(namespace)(ref.name)

  protected def generateClasses(namespace: String)(implicit model: ModelDefinition): Iterable[(Set[String], String)] = {
    val namedClasses = model map { case (name, typeDef) =>
      generateSingleTypeDef(namespace, typeDef, Set.empty)
    }
    namedClasses.flatten.toSet
  }

  def generateSingleTypeDef(namespace: String, typeDef: Type, imports: Set[String])(implicit model: ModelDefinition): Option[(Set[String], String)] = {
    typeDef match {
      case t@TypeDef(typeName, fields, extend, meta) =>
        Some((t.imports ++ imports, classCode(typeName, t, namespace)))
      // TODO add support for anonymous Objects (example instagram)
      // TODO add support for enums
      // TODO add support for nested objects or options (provide an example)
      // TODO add support for catch-all property
      case c: Container =>
        generateSingleTypeDef(namespace, c.field.kind, c.imports ++ imports)
      case other =>
        println("Not generating class for " + other) // TODO
        None
    }
  }

  private def classCode(name: String, typeDef: TypeDef, namespace: String)(implicit model: ModelDefinition) = {
    s"""
        |  ${comment(typeDef.meta)}
        |  case class $name(
        |${classFields(typeDef, namespace).mkString(",\n")}
        |  )${extendClause(typeDef, namespace, selfExtend = true)}
        | """.stripMargin.split("\n").filter(_.trim.nonEmpty).mkString("\n")
  }

  def extendClause(typeDef: TypeDef, namespace: String, selfExtend: Boolean)(implicit model: ModelDefinition): String = {
    val selfExtending = traitsToGenerate find (removeNamespaceFromRef(namespace)(_) == typeDef.name && selfExtend)
    val extend = (typeDef.extend ++ selfExtending.toSeq) map {
      removeNamespaceFromRef(namespace)
    } map traitName mkString " with "
    (if (extend.nonEmpty) " extends " else "") + extend
  }

  private def classFields(typeDef: TypeDef, namespace: String)(implicit model: ModelDefinition) =
    typeDef.allFields map { f =>
      s"""    ${comment(f.meta)}
          |    ${escape(f.name)}: ${removeNamespace(namespace)(f.kind.name)}""".stripMargin
    }

  protected val nowFormat = "dd.MM.yyyy HH:mm:ss"

  protected[compiler] val template =
    s"""
       |#PACKAGE#
       |
       |#IMPORT#
       |
       |/** @since #NOW# */
       |
       |object #NAMESPACE# {
       |#TRAITS#
       |#CLASSES#
       |}""".stripMargin

}
