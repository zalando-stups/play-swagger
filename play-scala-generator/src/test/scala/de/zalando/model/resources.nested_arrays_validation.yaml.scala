package de.zalando.model

import de.zalando.apifirst.Application._
import de.zalando.apifirst.Domain._
import de.zalando.apifirst.Http._
import de.zalando.apifirst.Hypermedia._
import de.zalando.apifirst.{ParameterPlace, Security}
import de.zalando.apifirst.naming._
//noinspection ScalaStyle
object nested_arrays_validation_yaml extends WithModel {
 
 val types = Map[Reference, Type](
	Reference("⌿definitions⌿Activity") → 
		TypeDef(Reference("⌿definitions⌿Activity"), 
			Seq(
					Field(Reference("⌿definitions⌿Activity⌿actions"), TypeRef(Reference("⌿definitions⌿Activity⌿actions")))
			), new TypeMeta(None, List())),
	Reference("⌿definitions⌿Example") → 
		TypeDef(Reference("⌿definitions⌿Example"), 
			Seq(
					Field(Reference("⌿definitions⌿Example⌿messages"), TypeRef(Reference("⌿definitions⌿Example⌿messages"))),
					Field(Reference("⌿definitions⌿Example⌿nestedArrays"), TypeRef(Reference("⌿definitions⌿Example⌿nestedArrays")))
			), new TypeMeta(None, List())),
	Reference("⌿definitions⌿Example⌿messages") → 
		Opt(TypeRef(Reference("⌿definitions⌿Example⌿messages⌿Opt")), new TypeMeta(None, List())),
	Reference("⌿definitions⌿Example⌿nestedArrays") → 
		Opt(TypeRef(Reference("⌿definitions⌿Example⌿nestedArrays⌿Opt")), new TypeMeta(None, List())),
	Reference("⌿definitions⌿Activity⌿actions") → 
		Opt(Str(None, new TypeMeta(None, List("""pattern("""+"""""""""+"""the pattern to validate"""+"""""""""+""".r)"""))), new TypeMeta(None, List())),
	Reference("⌿definitions⌿Example⌿nestedArrays⌿Opt") → 
		Arr(TypeRef(Reference("⌿definitions⌿Example⌿nestedArrays⌿Opt⌿Arr")), new TypeMeta(None, List("""maxItems(6)""", """minItems(5)""")), "csv"),
	Reference("⌿definitions⌿Example⌿messages⌿Opt") → 
		Arr(TypeRef(Reference("⌿definitions⌿Example⌿messages⌿Opt⌿Arr")), new TypeMeta(None, List("""maxItems(6)""", """minItems(5)""")), "csv"),
	Reference("⌿paths⌿/another⌿post⌿example") → 
		Opt(TypeRef(Reference("⌿definitions⌿Example")), new TypeMeta(None, List())),
	Reference("⌿definitions⌿Example⌿nestedArrays⌿Opt⌿Arr") → 
		Arr(TypeRef(Reference("⌿definitions⌿Example⌿nestedArrays⌿Opt⌿Arr⌿Arr")), new TypeMeta(None, List("""maxItems(16)""", """minItems(15)""")), "csv"),
	Reference("⌿definitions⌿Example⌿messages⌿Opt⌿Arr") → 
		Arr(TypeRef(Reference("⌿definitions⌿Activity")), new TypeMeta(None, List("""maxItems(6)""", """minItems(5)""")), "csv"),
	Reference("⌿paths⌿/another⌿post⌿responses⌿200") → 
		Null(new TypeMeta(None, List())),
	Reference("⌿definitions⌿Example⌿nestedArrays⌿Opt⌿Arr⌿Arr") → 
		Arr(TypeRef(Reference("⌿definitions⌿Example⌿nestedArrays⌿Opt⌿Arr⌿Arr⌿Arr")), new TypeMeta(None, List("""maxItems(26)""", """minItems(25)""")), "csv"),
	Reference("⌿definitions⌿Example⌿nestedArrays⌿Opt⌿Arr⌿Arr⌿Arr") → 
		Arr(Str(Some("nested arrays"), new TypeMeta(None, List("""maxLength(6)""", """minLength(5)"""))), new TypeMeta(None, List("""maxItems(36)""", """minItems(35)""")), "csv")
) 
 
 val parameters = Map[ParameterRef, Parameter](
	ParameterRef(	Reference("⌿paths⌿/⌿get⌿activity")) → Parameter("activity", TypeRef(Reference("⌿definitions⌿Activity")), None, None, ".+", encode = false, ParameterPlace.withName("body")),
	ParameterRef(	Reference("⌿paths⌿/another⌿post⌿example")) → Parameter("example", TypeRef(Reference("⌿paths⌿/another⌿post⌿example")), None, None, ".+", encode = false, ParameterPlace.withName("body"))
) 
val basePath: String = "/api" 
val discriminators: DiscriminatorLookupTable = Map[Reference, Reference](
	)
val securityDefinitions: SecurityDefinitionsTable = Map[String, Security.Definition](
	
)
val stateTransitions: StateTransitionsTable = Map[State, Map[State, TransitionProperties]]()
val calls: Seq[ApiCall] = Seq(
	ApiCall(GET, Path(Reference("⌿")), 
		HandlerCall(
			"nested_arrays_validation.yaml",
			"Nested_arrays_validationYaml",
			instantiate = false,
			"get",parameters = 
			Seq(
				ParameterRef(Reference("⌿paths⌿/⌿get⌿activity"))
				)
			), 
		Set(MimeType("application/json")), 
		Set(MimeType("application/json")), 
		Map.empty[String, Seq[Class[Exception]]], 
		TypesResponseInfo(
			Map[Int, ParameterRef](
			200 -> ParameterRef(Reference("⌿paths⌿/another⌿post⌿responses⌿200"))
		), None), 
		StateResponseInfo(
				Map[Int, State](
					200 -> Self
			), None), 
		Set.empty[Security.Constraint]), 
	ApiCall(POST, Path(Reference("⌿another")), 
		HandlerCall(
			"nested_arrays_validation.yaml",
			"Nested_arrays_validationYaml",
			instantiate = false,
			"postanother",parameters = 
			Seq(
				ParameterRef(Reference("⌿paths⌿/another⌿post⌿example"))
				)
			), 
		Set(MimeType("application/json")), 
		Set(MimeType("application/json")), 
		Map.empty[String, Seq[Class[Exception]]], 
		TypesResponseInfo(
			Map[Int, ParameterRef](
			200 -> ParameterRef(Reference("⌿paths⌿/another⌿post⌿responses⌿200"))
		), None), 
		StateResponseInfo(
				Map[Int, State](
					200 -> Self
			), None), 
		Set.empty[Security.Constraint]))

val packageName: Option[String] = Some("nested_arrays_validation.yaml")

val model = new StrictModel(calls, types, parameters, discriminators, basePath, packageName, stateTransitions, securityDefinitions)
    
} 