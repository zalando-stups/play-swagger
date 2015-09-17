package de.zalando.play.compiler

import de.zalando.apifirst.{Domain, Application}
import de.zalando.apifirst.Application.{ApiCall, Model}
import de.zalando.apifirst.Domain.{CatchAll, TypeName}
import de.zalando.apifirst.Path.InPathParameter
import de.zalando.swagger.model.Parameter

/**
 * @since 10.09.2015
 */
class TestsGenerator(basePath: String) extends ChainedGenerator {

  override val mainTemplate = s"""
                                 |#PACKAGE#
                                 |
                                 |import de.zalando.play.controllers.PlayBodyParsing
                                 |
                                 |import org.scalacheck._
                                 |import org.scalacheck.Arbitrary._
                                 |import org.scalacheck.Prop._
                                 |import org.scalacheck.Test._
                                 |
                                 |import org.specs2.mutable._
                                 |import play.api.test.Helpers._
                                 |import play.api.test._
                                 |import org.junit.runner.RunWith
                                 |import org.specs2.runner.JUnitRunner
                                 |import java.net.URLEncoder
                                 |
                                 |import controllers._
                                 |
                                 |/**
                                 |* @since #NOW#
                                 |*/
                                 |""".stripMargin

  override val namespaceTemplate = "#SINGLE_TESTS#"

  override def generators: List[GeneratorBase] = new SingleTestGenerator(basePath) :: Nil
}

// TODO refactor: very similar to the controller generator
class SingleTestGenerator(basePath: String) extends CallsGeneratorBase with TestClassGeneratorBase {

    override def generate(namespace: String)(implicit ast: Model): Iterable[(Set[String], String)] = {
      for {
        (pkg, pkgFiles) <- groupByFile(ast)
        (controller, calls) <- pkgFiles
      } yield {
        val params = calls.flatMap(_.handler.parameters)
        val imports = params.flatMap { _.typeName.imports }.toSet
        val typeImports = ImportsGenerator.importStatements(ast, "definitions", None)
        val methods = calls map toMethod(basePath) map {
          _.trim.split("\n").map(PAD + _).mkString("\n", "\n", "\n")
        } mkString ""

        // package $namespace.$pkg
        val fileContent = s"""
            |${imports.map {i => "import " + i }.mkString("\n")}
            |$typeImports
            |
            |@RunWith(classOf[JUnitRunner])
            |class ${controller}Spec extends Specification {
            |  // TODO use specs2 scalacheck integration instead of doing this manually
            |  def checkResult(props: Prop) =
            |    Test.check(Test.Parameters.default, props).status match {
            |      case Failed(_, labels) => failure(labels.mkString("\\n"))
            |      case Proved(_) | Exhausted | Passed => success
            |      case PropException(_, _, labels) => failure(labels.mkString("\\n"))
            |    }
            |$methods
            |}""".stripMargin

        (Set(pkg + "." + controller), fileContent)
      }
    }


    def toMethod(namespace: String)(call: ApiCall)(implicit ast: Model) =
      s"""
        |"${call.verb.name} ${fullPath(namespace, call.path.toString)}" should {
        |$PAD${testInvalidInput(namespace, call)}
        |}
        |
     """.stripMargin

  // TODO there is a lot of repetition here, especially in namings
  def testInvalidInput(namespace: String, call: ApiCall)(implicit ast: Model) = {
    val method = call.verb.name.toUpperCase
    val expectedRequestType = "\"" + call.mimeIn.name.toLowerCase + "\""
    val expectedCode = "BAD_REQUEST" // default validation error code
    val expectedContentType = "\"" + call.mimeOut.name.toLowerCase + "\""
    val validatorName = s"""ValidationFor${call.handler.controller}${call.handler.method}"""
    val url = fullUrl(namespace, call)
    val (body, withBody, andBody) = call.handler.bodyParameters.headOption.map { p =>
      (s"\n$PAD$PAD${PAD}val body = PlayBodyParsing.jacksonMapper($expectedRequestType).writeValueAsString(${p.name})",
        ".withBody(body)", "+ \"and body [\" + body + \"]\"")
    }.getOrElse(("", "", ""))

    s"""
        |    def testInvalidInput(in: (${parameterTypes(call)})) = {
        |      val (${parameterNames(call)}) = in
        |      val url = $url$body
        |      val path = route(FakeRequest($method, url)$withBody).get
        |      val validation = new $validatorName(${parameterNames(call)}).result
        |      lazy val validations = validation.left.get flatMap {
        |        _.messages map { m => contentAsString(path).contains(m) ?= true }
        |      }
        |      ("given an URL: [" + url + "]"$andBody) |: all(
        |        status(path) ?= $expectedCode,
        |        contentType(path) ?= Some($expectedContentType),
        |        validation.isLeft ?= true,
        |        all(validations:_*)
        |      )
        |    }
        |    def testValidInput(in: (${parameterTypes(call)})) = {
        |      val (${parameterNames(call)}) = in
        |      val url = $url$body
        |      val path = route(FakeRequest($method, url)$withBody).get
        |      ("given an URL: [" + url + "]"$andBody) |: (status(path) ?= OK)
        |    }
        |    ${testMethod("discard invalid data", call, validatorName, "!=", "testInvalidInput")}
        |    ${testMethod("do something with valid data", call, validatorName, "==", "testValidInput")}
        |""".stripMargin
    }

  def testMethod(name: String, call: ApiCall, validatorName: String, op: String, check: String)
      (implicit ast: Model) =
    s""" "$name" in new WithApplication {
      |      val genInputs =
      |        for {
      |           ${inputGenerators(call)}
      |         } yield (${parameterNames(call)})
      |      val inputs = genInputs suchThat { i => new $validatorName(i).result $op Right(i) }
      |      val props = forAll(inputs) { i => $check(i) }
      |      checkResult(props)
      |    }

     """.stripMargin

  // TODO should be generated into the TestData code, not here
    def inputGenerators(call: ApiCall)(implicit ast: Model) = call.handler.allParameters map { param =>
      val relativeType = TypeName("", param.typeName.name.simpleName)
      val generatorName = generatorNameForType(param.typeName, relativeType)("", Set.empty[String])
      s"""${TypeName.escape(param.name)} <- $generatorName"""
    } mkString "\n"

    def parameters(call: ApiCall) = {
      call.handler.allParameters.map { p =>
        s"""${p.name}: ${p.typeName.name.asSimpleType}"""
      }.mkString(", ")
    }

    def parameterNames(call: ApiCall) = {
      if (call.handler.allParameters.isEmpty) ""
      else call.handler.allParameters.map { p => s"${p.name}"}.mkString(", ")
    }

    def parameterTypes(call: ApiCall) = {
      if (call.handler.allParameters.isEmpty) ""
      else call.handler.allParameters.map { p => TypeName.escapeName(p.typeName.name.asSimpleType) } mkString ", "
    }

    def fullUrl(namespace: String, call: ApiCall) = {
      val pathSuffix = call.path.string { p: InPathParameter => "${" + p.value + "}" }
      // TODO this won't work for Containers
      val query = call.handler.queryParameters map { p =>
        singleQueryParam(p.name, p.typeName)
      }
      val fullQuery = if (query.isEmpty) "" else query.mkString("?", "&", "")
      "s\"\"\"" +  fullPath(namespace, pathSuffix) + fullQuery + "\"\"\""
    }

  def containerParam(name: String) = "${" + name + ".map { i => \"" + name + "=\" + URLEncoder.encode(i.toString, \"UTF-8\")}."

  def singleQueryParam(name: String, typeName: Domain.Type): String = typeName match {
    case c: Domain.Opt =>
      containerParam(name) + "getOrElse(\"\")}"
    case c: Domain.Arr =>
      containerParam(name) + "mkString(\"&\")}"
    case d: Domain.CatchAll =>
      "" // TODO no marshalling / unmarshalling yet
    case d: Domain.TypeDef =>
      "" // TODO no marshalling / unmarshalling yet
    case o =>
      name + "=${URLEncoder.encode(" + name + ".toString, \"UTF-8\")}"
  }

  def fullPath(namespace: String, pathSuffix: String) =
    namespace + (if (pathSuffix == "/") "" else pathSuffix)

  override val placeHolder: String = "#SINGLE_TESTS#"

  override def targetNamespace: String = "definitions"

  override def defaultNamespace: String = ModelFactoryGenerator.defaultNamespace
}



