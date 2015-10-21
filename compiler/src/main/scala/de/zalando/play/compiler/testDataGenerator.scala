package de.zalando.play.compiler

import de.zalando.apifirst.Application.Model
import de.zalando.apifirst.Domain._

import scala.language.postfixOps

/**
 * @since 31.08.2015
 */
object ModelFactoryGenerator extends ChainedGenerator {
  override def generators = new ImportsGenerator(super.defaultNamespace, defaultNamespace) ::
    new TestClassGenerator(super.defaultNamespace, defaultNamespace) ::
    new MethodsGenerator(super.defaultNamespace, defaultNamespace) :: new HelpersGenerator :: Nil
  override val defaultNamespace = "generatorDefinitions"
  override protected[compiler] val namespaceTemplate =
    s"""
       |object #NAMESPACE# {
       |#NESTED_IMPORTS#
       |#NESTED#
       |#METHODS#
       |#GENERATORS#
       |#HELPERS#
       |}""".stripMargin

}

/**
 * These imports are needed to handle nested scopes. Each generator scope needs to
 * import type definitions from the corresponding  {{{ definitions }}} object
 *
 * @param targetNamespace the root namespace of the generators
 * @param defaultNamespace the root namespace of the model
 */
class ImportsGenerator(targetNamespace: String, defaultNamespace: String) extends GeneratorBase {
  override def generate(namespace: String)(implicit ast: Model): Iterable[(Set[String], String)] = {
    val lookupName = if (namespace == defaultNamespace) targetNamespace else namespace
    val code: String = ImportsGenerator.importStatements(ast, lookupName)
    Seq((Set.empty[String], code))
  }

  override val placeHolder = "#NESTED_IMPORTS#"
}

object ImportsGenerator {
  def importStatements(ast: Model, lookupName: String, namespace: Option[String] = None): String = {
    val imports =
      ast.definitions.filter(_.name.namespace.endsWith(lookupName.replace("_", ""))).map { tpe =>
        namespace.map(_ + ".").getOrElse("") + tpe.name.relativeTo(TypeName(""))
      }
    val code = imports map { i => s"import ${TypeName.escapeName(i)}" } mkString "\n"
    code
  }
}
/**
 * Generates simple helper method for every namespace
 */
class HelpersGenerator extends GeneratorBase {
  override def generate(namespace: String)(implicit ast: Model): Iterable[(Set[String], String)] =
    Seq((Set.empty[String], "def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample"))
  override val placeHolder = "#HELPERS#"
}

/**
 * Creates helper methods for actual generation of the test data, like that:
 *
 * {{{ def genModelName = generate(ModelNameGenerator) }}}
 *
 * which can later be used like that to generate for example 100 instances:
 *
 * {{{ generatorDefinitions.genModelName(100) }}}
 *
 * @param targetNamespace the root namespace of the generators
 * @param defaultNamespace the root namespace of the model
 */
class MethodsGenerator(val targetNamespace: String, val defaultNamespace: String) extends ClassGenerator with TestClassGeneratorBase {
  override val placeHolder = "#METHODS#"

  override def generateSingleTypeDef(namespace: String, imports: Set[String])(typeDef: Type)
                                    (implicit ast: Model): Option[(Set[String], String)] = {
    typeDef match {
      case t@TypeDef(typeName, fields, extend, meta, _) =>
        Some((Set.empty[String], s"""def ${TypeName.escape("create"+ typeName.asSimpleType)} = _generate(${generatorName(t)})"""))
      case _ => None
    }
  }
}

/**
 * Creates generators for every model type definition
 *
 * @param targetNamespace the root namespace of the generators
 * @param defaultNamespace the root namespace of the model
 */
class TestClassGenerator(val targetNamespace: String, val defaultNamespace: String) extends ClassGenerator with TestClassGeneratorBase {

  override val placeHolder = "#GENERATORS#"

  override val defaultImports = Set("org.scalacheck.Gen", "org.scalacheck.Arbitrary._")

  override def generateSingleTypeDef(namespace: String, imports: Set[String])(typeDef: Type)
                                    (implicit ast: Model): Option[(Set[String], String)] = {
    typeDef match {
      case t@TypeDef(typeName, fields, extend, meta, _) =>
        Some((t.imports ++ imports, typeDefType(namespace, imports)(t)))
      case c: Container =>
        Some((c.imports ++ imports, containerType(namespace, imports)(c)))
      case r: ReferenceObject =>
        Some((imports, generatorName(r)))
      case other =>
        Some((imports, simpleType(other)))
    }
  }

  def typeDefType(namespace: String, imports: Set[String])(typeDef: TypeDef)(implicit ast: Model) = {
    s"""
        |// test data generator for ${typeDef.name.fullName}
        |val ${generatorName(typeDef)} =
        |${PAD}for {
        |${classFields(namespace, imports)(typeDef).mkString("\n")}
        |$PAD} yield ${classConstructor(typeDef)}
        |""".stripMargin.split("\n").filter(_.trim.nonEmpty).mkString("\n")
  }

  def classConstructor(typeDef: TypeDef)(implicit ast: Model) =
    s"""${TypeName.escape(typeDef.name.asSimpleType)}(${typeDef.allFields.map{ n => TypeName.escape(n.name.simpleName)}.mkString(", ")})"""

}

/**
 * Common stuff for all test data generators
 */
trait TestClassGeneratorBase extends GeneratorBase {
  def targetNamespace: String
  def defaultNamespace: String
  val GEN = "Generator"

  def classFields(namespace: String, imports: Set[String])(typeDef: TypeDef)(implicit ast: Model) =
    typeDef.allFields map { f =>
      s"""$PAD$PAD${TypeName.escape(f.name.simpleName)} <- ${generatorNameForType(f.tpe, typeDef.name)(namespace, imports)}""".stripMargin
    }

  def generatorNameForType(tpe: Type, thisType: TypeName)(namespace: String, imports: Set[String])(implicit ast: Model) = tpe match {
    case t @ TypeDef(_, _, _, _, _) => relativeGeneratorName(t, thisType)
    case r: Reference => relativeGeneratorName(r, thisType)
    case c : Container => containerType(namespace, imports)(c)
    case _ => simpleType(tpe)
  }

  def containerType(namespace: String, imports: Set[String])(c: Container)(implicit ast: Model): String = c match {
    case Opt(field, _) =>
      val innerGenerator = generatorNameForType(field, c.name)(namespace, imports)
      s"Gen.option($innerGenerator)"
    case a@Arr(field, _, _) =>
      val innerGenerator = generatorNameForType(field, c.name)(namespace, imports)
      s"Gen.containerOf[List,${field.name.asSimpleType}]($innerGenerator)"
    case ca@CatchAll(_, _) =>
      // TODO generate non-empty map
      s"Gen.const(${ca.name.asSimpleType})".replace("Map[", "Map.empty[")
  }

  def simpleType(other: Type) = s"arbitrary[${other.name.asSimpleType}]"

  def generatorName(typeDef: Type): String = TypeName.escapeName(typeDef.name.asSimpleType + GEN)

  def relativeGeneratorName(typeDef: Type, thisType: TypeName)
    = TypeName.escapeName(typeDef.name.relativeTo(thisType).replace(targetNamespace, defaultNamespace) + GEN)
}
