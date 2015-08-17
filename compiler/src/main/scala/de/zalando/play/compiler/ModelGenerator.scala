package de.zalando.play.compiler

import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Date

import de.zalando.apifirst.Domain._

import scala.language.postfixOps

/**
 * @since 16.08.2015
 */
object ModelGenerator {

  def generate(packageName: Option[String],
               namespace: String = "definitions")(implicit model: ModelDefinition) = {
    val traitInfo = generateTraits(namespace)
    val traitImports = traitInfo.map(_._1)
    val traits = traitInfo.map(_._2)
    val classes = generateClasses(model)
    val now = new Date()
    val result = template.
      replace("#NAMESPACE#", namespace).
      replace("#IMPORT#", traitImports.mkString("\n")). // TODO FIXME
      replace("#PACKAGE#", packageName.fold("") { p => s"package $p" }).
      replace("#NOW#", new SimpleDateFormat(nowFormat).format(now)).
      replace("#TRAITS#", traits.mkString("\n")).
      replace("#CLASSES#", classes)
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

  protected def generateTraits(namespace: String)(implicit model: ModelDefinition): Iterable[(String, String)] = {
    val allTraits = model map { case (name, typeDef) =>
      typeDef match {
        case TypeDef(_, _, extend, meta) if extend.nonEmpty =>
          extend flatMap { reference =>
            reference.resolve map { typeDef =>
              val code = traitCode(typeDef.name, typeDef)
              (typeDef.imports.mkString("\n"), code)
            }
          }
        case _ => Seq.empty[(String, String)]
      }
    }
    allTraits.flatten.toSet
  }

  // TODO escape reserved words
  def traitCode(name: String, typeDef: TypeDef) =
    s"""  ${comment(typeDef.meta)}
      |  trait $name {
      |${typeFields(typeDef).mkString("\n")}
      |  }""".stripMargin.split("\n").filter(_.trim.nonEmpty).mkString("\n")

  private def typeFields(typeDef: TypeDef) = typeDef.fields map { f =>
    s"""    ${comment(f.meta)}
       |    def ${f.name}: ${f.kind.name}""".stripMargin
  }

  private def comment(meta: TypeMeta) = meta.comment.map {
    _.split("\n").map(c => "// " + c).mkString("\n")
  } getOrElse ""

  def errorMessage(ref: ReferenceObject, typeName: String, nameSpace: String) = {
    val expected = removeNamespace(ref, nameSpace)
    Left(s"Field <${ref.name}> of type <$typeName> is defined being of type <$expected> but <$expected> could not be found")
  }

  def removeNamespace(ref: ReferenceObject, nameSpace: String): String = {
    ref.url.replace(nameSpace, "").dropWhile(_ == '/')
  }

  protected def generateClasses(model: ModelDefinition) = ""

  protected val nowFormat = "dd.MM.yyyy HH:mm:ss"

  protected[compiler] val template =
    s"""
       |#PACKAGE#
       |#IMPORT#
       |@since #NOW#
       |
       |object #NAMESPACE# {
       |
       |#TRAITS#
       |#CLASSES#
       |}""".stripMargin

}
