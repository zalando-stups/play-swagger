package de.zalando.model

import de.zalando.apifirst.Application._
import de.zalando.apifirst.Domain._
import de.zalando.apifirst.Http._
import de.zalando.apifirst.Hypermedia._
import de.zalando.apifirst.{ParameterPlace, Security}
import de.zalando.apifirst.naming._
//noinspection ScalaStyle
object string_formats_yaml extends WithModel {
 
 val types = Map[Reference, Type](
	Reference("⌿paths⌿/⌿get⌿base64") → 
		Opt(Base64String(new TypeMeta(None, List())), new TypeMeta(None, List())),
	Reference("⌿paths⌿/⌿get⌿petId") → 
		BinaryString(new TypeMeta(None, List())),
	Reference("⌿paths⌿/⌿get⌿date_time") → 
		Opt(DateTime(new TypeMeta(None, List())), new TypeMeta(None, List())),
	Reference("⌿paths⌿/⌿get⌿date") → 
		Opt(Date(new TypeMeta(None, List())), new TypeMeta(None, List())),
	Reference("⌿paths⌿/⌿get⌿responses⌿200") → 
		Null(new TypeMeta(None, List()))
) 
 
 val parameters = Map[ParameterRef, Parameter](
	ParameterRef(	Reference("⌿paths⌿/⌿get⌿petId")) → Parameter("petId", BinaryString(new TypeMeta(None, List())), None, None, ".+", encode = false, ParameterPlace.withName("body")),
	ParameterRef(	Reference("⌿paths⌿/⌿get⌿base64")) → Parameter("base64", TypeRef(Reference("⌿paths⌿/⌿get⌿base64")), None, None, ".+", encode = true, ParameterPlace.withName("query")),
	ParameterRef(	Reference("⌿paths⌿/⌿get⌿date")) → Parameter("date", TypeRef(Reference("⌿paths⌿/⌿get⌿date")), None, None, ".+", encode = true, ParameterPlace.withName("query")),
	ParameterRef(	Reference("⌿paths⌿/⌿get⌿date_time")) → Parameter("date_time", TypeRef(Reference("⌿paths⌿/⌿get⌿date_time")), None, None, ".+", encode = true, ParameterPlace.withName("query"))
) 
val basePath: String =null
val discriminators: DiscriminatorLookupTable = Map[Reference, Reference](
	)
val securityDefinitions: SecurityDefinitionsTable = Map[String, Security.Definition](
	
)
val stateTransitions: StateTransitionsTable = Map[State, Map[State, TransitionProperties]]()
val calls: Seq[ApiCall] = Seq(
	ApiCall(GET, Path(Reference("⌿")), 
		HandlerCall(
			"string_formats.yaml",
			"String_formatsYaml",
			instantiate = false,
			"get",parameters = 
			Seq(
				ParameterRef(Reference("⌿paths⌿/⌿get⌿petId")),
				ParameterRef(Reference("⌿paths⌿/⌿get⌿base64")),
				ParameterRef(Reference("⌿paths⌿/⌿get⌿date")),
				ParameterRef(Reference("⌿paths⌿/⌿get⌿date_time"))
				)
			), 
		Set.empty[MimeType], 
		Set(MimeType("application/json"), MimeType("application/yaml")), 
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

val packageName: Option[String] = Some("string_formats.yaml")

val model = new StrictModel(calls, types, parameters, discriminators, basePath, packageName, stateTransitions, securityDefinitions)
    
} 