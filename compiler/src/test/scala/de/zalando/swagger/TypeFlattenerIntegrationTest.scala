package de.zalando.swagger

import java.io.File

import de.zalando.ExpectedResults
import de.zalando.apifirst.{TypeDeduplicator, TypeFlattener, ParameterDereferencer}
import de.zalando.swagger.strictModel.SwaggerModel
import org.scalatest.{FunSpec, MustMatchers}

class TypeFlattenerIntegrationTest extends FunSpec with MustMatchers with ExpectedResults {

  override val expectationsFolder = "/expected_results/flat_types/"

  val modelFixtures = new File("compiler/src/test/resources/model").listFiles

  val exampleFixtures = new File("compiler/src/test/resources/examples").listFiles

  describe("Strict Swagger Parser model") {
    modelFixtures.filter(_.getName.endsWith(".yaml")).foreach { file =>
      testTypeFlattener(file)
    }
  }

  describe("Strict Swagger Parser examples") {
    exampleFixtures.filter(_.getName.endsWith(".yaml")).foreach { file =>
      testTypeFlattener(file)
    }
  }

  def testTypeFlattener(file: File): Unit = {
    it(s"should parse the yaml swagger file ${file.getName} as specification") {
      val (base, model) = StrictYamlParser.parse(file)
      model mustBe a[SwaggerModel]
      val ast       = ModelConverter.fromModel(base, model)
      val flatAst   = (ParameterDereferencer.apply _ andThen TypeFlattener.apply andThen TypeDeduplicator.apply) (ast)
      val typeDefs  = flatAst.typeDefs
      val typeMap   = typeDefs map { case (k, v) => k -> ("\n\t" + v.toShortString("\t\t")) }
      val typesStr  = typeMap.toSeq.sortBy(_._1.parts.size).map(p => p._1 + " -> " + p._2).mkString("\n").replace(base.toString, "")
      val params    = flatAst.params
      val paramsStr = params.toSeq.sortBy(_._1.name.parts.size).map(p => p._1.name.toString + " -> " + p._2).mkString("\n").replace(base.toString, "")
      val expected  = asInFile(file, "types")
      val fullResult = typesStr + "\n-- params --\n\n" + paramsStr
      if (expected.isEmpty) dump(fullResult, file, "types")
      clean(fullResult) mustBe clean(expected)
    }
  }

  def clean(str: String) = str.split("\n").map(_.trim).mkString("\n")
}
