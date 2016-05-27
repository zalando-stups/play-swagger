package de.zalando.model

import de.zalando.apifirst.Application._
import de.zalando.apifirst.Domain._
import de.zalando.apifirst.Http._
import de.zalando.apifirst.Hypermedia._
import de.zalando.apifirst.{ParameterPlace, Security}
import de.zalando.apifirst.naming._
//noinspection ScalaStyle
object nested_options_validation_yaml extends WithModel {
 
 val types = Map[Reference, Type](
	Reference("⌿definitions⌿Basic") → 
		TypeDef(Reference("⌿definitions⌿Basic"), 
			Seq(
					Field(Reference("⌿definitions⌿Basic⌿optional"), TypeRef(Reference("⌿definitions⌿Basic⌿optional")))
			), new TypeMeta(None, List())),
	Reference("⌿definitions⌿Basic⌿optional") → 
		Opt(TypeRef(Reference("⌿definitions⌿Basic⌿optional⌿Opt")), new TypeMeta(None, List())),
	Reference("⌿definitions⌿Basic⌿optional⌿Opt") → 
		TypeDef(Reference("⌿definitions⌿Basic⌿optional"), 
			Seq(
					Field(Reference("⌿definitions⌿Basic⌿optional⌿nested_optional"), TypeRef(Reference("⌿definitions⌿Basic⌿optional⌿nested_optional")))
			), new TypeMeta(None, List())),
	Reference("⌿definitions⌿Basic⌿optional⌿nested_optional") → 
		Opt(Str(None, new TypeMeta(None, List("""maxLength(6)""", """minLength(5)"""))), new TypeMeta(None, List())),
	Reference("⌿paths⌿/⌿get⌿responses⌿200") → 
		Null(new TypeMeta(None, List()))
) 
 
 val parameters = Map[ParameterRef, Parameter](
	ParameterRef(	Reference("⌿paths⌿/⌿get⌿basic")) → Parameter("basic", TypeRef(Reference("⌿definitions⌿Basic")), None, None, ".+", encode = false, ParameterPlace.withName("body"))
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
			"nested_options_validation.yaml",
			"Nested_options_validationYaml",
			instantiate = false,
			"get",parameters = 
			Seq(
				ParameterRef(Reference("⌿paths⌿/⌿get⌿basic"))
				)
			), 
		Set(MimeType("application/json")), 
		Set(MimeType("application/json")), 
		Map.empty[String, Seq[Class[Exception]]], 
		TypesResponseInfo(
			Map[Int, ParameterRef](
			200 -> ParameterRef(Reference("⌿paths⌿/⌿get⌿responses⌿200"))
		), None), 
		StateResponseInfo(
				Map[Int, State](
					200 -> Self
			), None), 
		Set.empty[Security.Constraint]))

val packageName: Option[String] = Some("nested_options_validation.yaml")

val model = new StrictModel(calls, types, parameters, discriminators, basePath, packageName, stateTransitions, securityDefinitions)
    
} 