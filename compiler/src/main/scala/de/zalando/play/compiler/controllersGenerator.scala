package de.zalando.play.compiler

/**
 * @since 09.09.2015
 */
object ControllersGenerator extends ChainedGenerator {

  override def generators = Nil
  override val defaultNamespace = "controllerDefinitions"
  override protected[compiler] val namespaceTemplate =
    s"""
       |package #PACKAGE#
       |#IMPORTS#
       |/** @since #NOW# */
       |class #CLASS_NAME# extends ApplicationBase {
       |#METHODS#
       |}""".stripMargin


  val method = """  def helloPet = helloPetAction { pet: Pet =>
                 |    ???
                 |  }"""

  val imports = """import api.definitions.Pet"""

  val pgk = """api.controllers"""

  val className = """ApiYaml"""
}

