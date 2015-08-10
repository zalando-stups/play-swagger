package de.zalando.swagger

import java.io.{FileWriter, BufferedWriter, File}

import de.zalando.apifirst.Domain
import de.zalando.apifirst.Domain.{Field, TypeDef}
import de.zalando.swagger.model.PrimitiveType
import org.scalatest.{FunSpec, MustMatchers}

import scala.language.implicitConversions

class SchemaConverterTest extends FunSpec with MustMatchers {

  describe("Schema Converter") {
    it("should convert primitive schema") {
      val schema = new model.Schema("email", null, 0, 0, false, 0, false, 0, 0, null, 0, 0, false, null, null, PrimitiveType.STRING, Nil, null, null, null, null, null, null, null, false, null, null, null, 0, 0)
      val result = SchemaConverter.schema2Type(schema, "/definitions/type_name")
      result mustBe Domain.Str(Some("email"))
    }
    it("should convert simple object") {
      val props = Map("name" -> model.Property(PrimitiveType.STRING, null, null, null, null, null, null))
      val schema = new model.Schema(null, null, 0, 0, false, 0, false, 0, 0, null, 0, 0, false, null, null, PrimitiveType.OBJECT, List("name"), null, null, null, null, props, null, null, false, null, null, null, 0, 0)
      val result = SchemaConverter.schema2Type(schema, "/definitions/type_name")
      result mustBe TypeDef("/definitions/type_name", Seq(Field("name", Domain.Str())))
    }

    it("should convert a complex object") {
      val props = Map("name" -> model.Property(PrimitiveType.STRING, null, null, null, null, null, null))
      val mainprops = Map("properties" -> model.Property(PrimitiveType.OBJECT, null, null, null, null, null, props))
      val schema = new model.Schema(null, null, 0, 0, false, 0, false, 0, 0, null, 0, 0, false, null, null, PrimitiveType.OBJECT, List("mainprops"), null, null, null, null, mainprops, null, null, false, null, null, null, 0, 0)
      val result = SchemaConverter.schema2Type(schema, "/definitions/type_name")
      result mustBe TypeDef("/definitions/type_name", Seq(Field("properties", Domain.TypeDef("properties", Seq(Field("name", Domain.Str()))))))
    }
  }

  implicit def fileFromText(text: String): File = {
    val temp = File.createTempFile("asdf", "test")
    temp.deleteOnExit()
    val out = new BufferedWriter(new FileWriter(temp))
    val txt = text.split("\n").map("  " + _).mkString("\n")
    out.write("definitions:\n" + text)
    out.close()
    temp
  }
}
