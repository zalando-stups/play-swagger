package de.zalando.play.compiler

import de.zalando.apifirst.Domain._

import scala.language.postfixOps

/**
 * @since 31.08.2015
 */
object ModelFactoryGenerator extends ChainedGenerator {
  override def generators = new TestClassGenerator :: new MethodsGenerator :: new HelpersGenerator :: Nil
}

class HelpersGenerator extends GeneratorBase {
  override def generate(namespace: String)(implicit model: ModelDefinition): Iterable[(Set[String], String)] =
    Seq((Set.empty[String], "def generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample"))
  override val placeHolder = "#HELPERS#"
}

class MethodsGenerator extends ClassGenerator with TestClassGeneratorBase {
  override val placeHolder = "#TRAITS#"

  override def generateSingleTypeDef(namespace: String, imports: Set[String])(typeDef: Type)
                                    (implicit model: ModelDefinition): Option[(Set[String], String)] = {
    typeDef match {
      case t@TypeDef(typeName, fields, extend, meta) =>
        Some((Set.empty[String], s"""def gen${typeName.asSimpleType} = generate(${generatorName(t)})"""))
      case _ => None
    }
  }
}

class TestClassGenerator extends ClassGenerator with TestClassGeneratorBase {

  override val placeHolder = "#CLASSES#"

  override val defaultImports = Set("org.scalacheck._", "Gen._", "Arbitrary._")

  private def unknown(imports: Set[String]) = (imports, "const(null)")

  override def generateSingleTypeDef(namespace: String, imports: Set[String])(typeDef: Type)
                                    (implicit model: ModelDefinition): Option[(Set[String], String)] = {
    typeDef match {
      case t@TypeDef(typeName, fields, extend, meta) =>
        Some((t.imports ++ imports, typeDefType(namespace, imports)(t)))
      case c: Container =>
        Some((c.imports ++ imports, containerType(c)))
      case r: ReferenceObject =>
        r.resolve flatMap { tpe =>
          generateSingleTypeDef(namespace, imports)(tpe)
        }
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
        |${classFields(namespace, imports)(typeDef).mkString(",\n")}
        |} yield ${classConstructor(typeDef)}
        |""".stripMargin.split("\n").filter(_.trim.nonEmpty).mkString("\n")
  }

  private def classConstructor(typeDef: TypeDef) =
    s"""${typeDef.name.asSimpleType}(${typeDef.fields.map(_.name.simpleName).mkString(", ")})"""

  private def classFields(namespace: String, imports: Set[String])(typeDef: TypeDef)(implicit model: ModelDefinition) =
    typeDef.allFields map { f =>
      s"""$PAD$PAD${TypeName.escape(f.name.simpleName)} <- ${generateSingleTypeDef(namespace, imports)(f.kind).get._2}""".stripMargin
    }

  private def referenceType(r: Reference)(implicit model: ModelDefinition) = {
    r.resolve map generatorName getOrElse "const(null)"
  }

  private def simpleType(other: Type) = s"arbitrary[${other.name.asSimpleType}]"

  private def containerType(c: Container) = c match {
    case a@Arr(_, _) => s"Gen.containerOf[List,${a.name.asSimpleType}]"
    case o@Opt(_, _) => s"Gen.fromOption[${o.name.asSimpleType}]"
    case ca@CatchAll(_, _) => s"const(${ca.name.asSimpleType}})".replace("Map[", "Map.empty[") // TODO generate non-empty map
  }
}

trait TestClassGeneratorBase {
  val GEN = "Generator"

  def generatorName(typeDef: TypeDef): String = TypeName.escapeName(typeDef.name.simpleName + GEN)
}
