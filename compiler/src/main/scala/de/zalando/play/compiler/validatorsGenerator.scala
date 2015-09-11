package de.zalando.play.compiler

import de.zalando.apifirst.Application.{Parameter, ApiCall, Model}
import de.zalando.apifirst.Domain._
import de.zalando.swagger.model.CommonProperties

/**
 * @since 10.09.2015
 */
object ValidatorsGenerator extends ChainedGenerator {

  override val mainTemplate = s"""
                                 |#PACKAGE#
                                 |
                                 |import play.api.mvc.{Action, Controller}
                                 |import play.api.data.validation.Constraint
                                 |import de.zalando.play.controllers._
                                 |import PlayBodyParsing._
                                 |import PlayValidations._
                                 |
                                 |#IMPORT#
                                 |
                                 |/**
                                 |* @since #NOW#
                                 |*/
                                 |""".stripMargin

  override val namespaceTemplate = "#INLINE_VALIDATORS##DEFINITION_VALIDATORS#"

  override def generators: List[GeneratorBase] =
    new InlineValidatorsGenerator ::
      new DefinitionValidatorGenerator ::
      Nil
}

class InlineValidatorsGenerator extends GeneratorBase {
  override val placeHolder = "#INLINE_VALIDATORS#"

  val emptyValidations = ".empty[scala.Either[scala.Seq[ParsingError], String]]"

  val template =
    """
      | class ValidationFor#CONTROLLER##CALL#(#VALIDATOR_PARAMS#) {
      |
      |   #CONSTRAINTS#
      |   val normalValidations = Seq#NORMAL_VALIDATIONS#
      |
      |   val containerValidations = Seq#CONTAINER_VALIDATIONS#
      |
      |   val rightResult = Right((#VALIDATOR_RESULT#))
      |
      |   val allValidations = normalValidations ++ containerValidations
      |
      |   val result = {
      |    val errors = allValidations.filter(_.isLeft).flatMap(_.left.get)
      |    if (errors.nonEmpty) Left(errors) else rightResult
      |   }
      |  }
    """.stripMargin

  def validatorParameters(parameters: Seq[Parameter]) =
    if (parameters.isEmpty) ""
    else parameters.map { p =>
      s"${p.name}: ${TypeName.escapeName(p.typeName.name.asSimpleType)}"
    } mkString ", "

  def validatorResult(parameters: Seq[Parameter]) =
    if (parameters.isEmpty) ""
    else parameters.map {_.name} mkString ", "

  def normalValidations(parameters: Seq[Parameter]) = parameters map {
    case p if p.typeName.isInstanceOf[Reference] =>
      s"new ${p.typeName.name.asSimpleType}Validation(${p.name}).result"
    case s => s"${s.name}Constraints.applyConstraints(${s.name})"
  } mkString("(", ",\n", ")")

  def containerValidations(parameters: Seq[Parameter]) = parameters map {
    case p if p.typeName.isInstanceOf[Reference] =>
      s"${p.name} map { new ${p.typeName.name.asSimpleType}Validation(_).result }"
    case s => s"${s.name} map ${s.name}Constraints.applyConstraints"
  } mkString("(", ",\n", ").flatten")

  def constraints(call: ApiCall) = call.handler.allParameters filterNot {
    _.typeName.isInstanceOf[Reference]
  } map constraint mkString "\n"

  def constraint(p: Parameter) = {
    // FIXME this validation should be recursive
    val tpe = p.typeName match {
      case c: Container => c.field.kind.name.asSimpleType
      case _ => p.typeName.name.asSimpleType
    }
    val constraints = p.typeName.meta.constraints.mkString(",\n")
    s"""
      |val ${p.name}Constraints = new ValidationBase[$tpe] {
      |    override def constraints: Seq[Constraint[$tpe]] = Seq(
      |     $constraints
      |    )
      |  }
    """.stripMargin
  }

  override def generate(namespace: String)(implicit ast: Model): Iterable[(Set[String], String)] = {
    val result = for {
      (pkg, pkgFiles) <- ControllersGenerator.groupByFile(ast)
      (controller, calls) <- pkgFiles
    } yield {
        val params = calls.flatMap(_.handler.parameters)
        val imports = params.flatMap {_.typeName.imports}.toSet
        val methods = calls map { call =>
          val (containerParams, normalParams) = call.handler.allParameters.partition {_.typeName.isInstanceOf[Container]}
          val flatValidations = if (normalParams.isEmpty) emptyValidations else normalValidations(normalParams)
          // TODO they need to be recursive for nested containers!
          val nestedValidations = if (containerParams.isEmpty) emptyValidations else containerValidations(containerParams)
          template.
            replaceAll("#VALIDATOR_PARAMS#", validatorParameters(call.handler.allParameters)).
            replaceAll("#VALIDATOR_RESULT#", validatorResult(call.handler.allParameters)).
            replaceAll("#NORMAL_VALIDATIONS#", flatValidations).
            replaceAll("#CONTAINER_VALIDATIONS#", nestedValidations).
            replaceAll("#CONSTRAINTS#", constraints(call)).
            replaceAll("#CALL#", call.handler.method)
        }
        val typeImports = ImportsGenerator.importStatements(ast, "definitions", None)

        val body = s"""
                      |#TYPE_IMPORTS#
                      |package $pkg {
                                     |${methods.mkString("\n")}
            |}""".stripMargin.
          replaceAll("#CONTROLLER#", controller).
          replaceAll("#TYPE_IMPORTS#", typeImports)
        (imports, body)
      }
    result.toSeq
  }

}

// TODO this should actually generate something useful
class DefinitionValidatorGenerator extends InlineValidatorsGenerator {
  override val placeHolder = "#DEFINITION_VALIDATORS#"

  override val template =
    s"""
      |class #TYPE_NAME#Validation(instance: #TYPE_NAME#) {
      |  import de.zalando.play.controllers.PlayValidations._
      |  val allValidations = Seq$emptyValidations
      |  val result = {
      |    val errors = allValidations.filter(_.isLeft).flatMap(_.left.get)
      |    if (errors.nonEmpty) Left(errors) else Right(instance)
      |  }
      |}
    """.stripMargin

  override def generate(namespace: String)(implicit model: Model): Iterable[(Set[String], String)] =
    model.definitions map {
      case d: TypeDef => singleTypeValidator(d)
      case _ => (Set.empty[String], "")
    }

  def singleTypeValidator(d: TypeDef) = {
    (Set.empty[String], template.replaceAll("#TYPE_NAME#", d.name.asSimpleType))
  }

}