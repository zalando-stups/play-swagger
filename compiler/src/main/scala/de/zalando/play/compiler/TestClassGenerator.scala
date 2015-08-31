package de.zalando.play.compiler

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

class ImportsGenerator(targetNamespace: String, defaultNamespace: String) extends GeneratorBase {
  override def generate(namespace: String)(implicit model: ModelDefinition): Iterable[(Set[String], String)] = {
    val imports = if (namespace == defaultNamespace) targetNamespace else {
      model.find(_.name.namespace.endsWith(namespace)).map { tpe =>
        tpe.name.namespace.tail.replace('/','.')
      } getOrElse namespace
    }
    val code = s"import $imports._"
    Seq((Set.empty[String], code))
  }
  override val placeHolder = "#NESTED_IMPORTS#"
}

class HelpersGenerator extends GeneratorBase {
  override def generate(namespace: String)(implicit model: ModelDefinition): Iterable[(Set[String], String)] =
    Seq((Set.empty[String], "def generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample"))
  override val placeHolder = "#HELPERS#"
}

class MethodsGenerator(val targetNamespace: String, val defaultNamespace: String) extends ClassGenerator with TestClassGeneratorBase {
  override val placeHolder = "#METHODS#"

  override def generateSingleTypeDef(namespace: String, imports: Set[String])(typeDef: Type)
                                    (implicit model: ModelDefinition): Option[(Set[String], String)] = {
    typeDef match {
      case t@TypeDef(typeName, fields, extend, meta) =>
        Some((Set.empty[String], s"""def ${TypeName.escape("gen"+ typeName.asSimpleType)} = generate(${generatorName(t)})"""))
      case _ => None
    }
  }
}

class TestClassGenerator(val targetNamespace: String, val defaultNamespace: String) extends ClassGenerator with TestClassGeneratorBase {

  override val placeHolder = "#GENERATORS#"

  override val defaultImports = Set("org.scalacheck.Gen", "org.scalacheck.Gen._", "org.scalacheck.Arbitrary._")

  override def generateSingleTypeDef(namespace: String, imports: Set[String])(typeDef: Type)
                                    (implicit model: ModelDefinition): Option[(Set[String], String)] = {
    typeDef match {
      case t@TypeDef(typeName, fields, extend, meta) =>
        Some((t.imports ++ imports, typeDefType(namespace, imports)(t)))
      case c: Container =>
        Some((c.imports ++ imports, containerType(namespace, imports)(c)))
      case r: ReferenceObject =>
        Some((imports, generatorName(r)))
      case other: Entity =>
        println("Not generating class for entity " + other) // TODO
        None
      case other =>
        Some((imports, simpleType(other)))
    }
  }

  private def typeDefType(namespace: String, imports: Set[String])(typeDef: TypeDef)(implicit model: ModelDefinition) = {
    s"""
        |// test data generator for ${typeDef.name.fullName}
        |val ${generatorName(typeDef)} =
        |${PAD}for {
        |${classFields(namespace, imports)(typeDef).mkString("\n")}
        |} yield ${classConstructor(typeDef)}
        |""".stripMargin.split("\n").filter(_.trim.nonEmpty).mkString("\n")
  }

  private def classConstructor(typeDef: TypeDef)(implicit model: ModelDefinition) =
    s"""${TypeName.escape(typeDef.name.asSimpleType)}(${typeDef.allFields.map{ n => TypeName.escape(n.name.simpleName)}.mkString(", ")})"""

  private def classFields(namespace: String, imports: Set[String])(typeDef: TypeDef)(implicit model: ModelDefinition) =
    typeDef.allFields map { f =>
      s"""$PAD$PAD${TypeName.escape(f.name.simpleName)} <- ${generateSingleTypeDef(namespace, imports)(f.kind).get._2}""".stripMargin
    }

  private def simpleType(other: Type) = s"arbitrary[${other.name.asSimpleType}]"

  private def containerType(namespace: String, imports: Set[String])(c: Container)(implicit model: ModelDefinition): String = c match {
    case Opt(field, _) =>
      val innerGenerator = generatorNameForType(field.kind, c)(namespace, imports)
      s"Gen.option($innerGenerator)"
    case a@Arr(field, _) =>
      val innerGenerator = generatorNameForType(field.kind, c)(namespace, imports)
      s"Gen.containerOf[List,${field.kind.name.asSimpleType}]($innerGenerator)"
    case ca@CatchAll(_, _) =>
      // TODO generate non-empty map
      s"const(${ca.name.asSimpleType}})".replace("Map[", "Map.empty[")
  }

  private def generatorNameForType(tpe: Type, thisType: Type)(namespace: String, imports: Set[String])(implicit model: ModelDefinition) = tpe match {
    case t @ TypeDef(_, _, _, _) => relativeGeneratorName(t, thisType)
    case r: Reference => relativeGeneratorName(r, thisType)
    case c : Container => containerType(namespace, imports)(c)
    case _ => simpleType(tpe)
  }
}

trait TestClassGeneratorBase {
  def targetNamespace: String
  def defaultNamespace: String
  val GEN = "Generator"

  def generatorName(typeDef: Type): String = TypeName.escapeName(typeDef.name.asSimpleType + GEN)

  def relativeGeneratorName(typeDef: Type, thisType: Type)
    = TypeName.escapeName(typeDef.name.relativeTo(thisType.name).replace(targetNamespace, defaultNamespace) + GEN)
}
