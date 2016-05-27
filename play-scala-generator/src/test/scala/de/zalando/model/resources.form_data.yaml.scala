package de.zalando.model

import de.zalando.apifirst.Application._
import de.zalando.apifirst.Domain._
import de.zalando.apifirst.Http._
import de.zalando.apifirst.Hypermedia._
import de.zalando.apifirst.{ParameterPlace, Security}
import de.zalando.apifirst.naming._
//noinspection ScalaStyle
object form_data_yaml extends WithModel {
 
 val types = Map[Reference, Type](
	Reference("⌿paths⌿/multipart⌿post⌿avatar") → 
		Opt(File(new TypeMeta(None, List())), new TypeMeta(None, List())),
	Reference("⌿paths⌿/multipart⌿post⌿name") → 
		Str(None, new TypeMeta(None, List())),
	Reference("⌿paths⌿/both⌿post⌿ringtone") → 
		File(new TypeMeta(None, List())),
	Reference("⌿paths⌿/both⌿post⌿year") → 
		Opt(BInt(new TypeMeta(None, List())), new TypeMeta(None, List())),
	Reference("⌿paths⌿/multipart⌿post⌿responses⌿200") → 
		TypeDef(Reference("⌿paths⌿/multipart⌿post⌿responses⌿200"), 
			Seq(
					Field(Reference("⌿paths⌿/multipart⌿post⌿responses⌿200⌿name"), TypeRef(Reference("⌿paths⌿/both⌿post⌿responses⌿200⌿name"))),
					Field(Reference("⌿paths⌿/multipart⌿post⌿responses⌿200⌿year"), TypeRef(Reference("⌿paths⌿/both⌿post⌿year"))),
					Field(Reference("⌿paths⌿/multipart⌿post⌿responses⌿200⌿fileSize"), TypeRef(Reference("⌿paths⌿/both⌿post⌿year"))),
					Field(Reference("⌿paths⌿/multipart⌿post⌿responses⌿200⌿fileName"), TypeRef(Reference("⌿paths⌿/both⌿post⌿responses⌿200⌿name")))
			), new TypeMeta(None, List())),
	Reference("⌿paths⌿/both⌿post⌿responses⌿200") → 
		TypeDef(Reference("⌿paths⌿/both⌿post⌿responses⌿200"), 
			Seq(
					Field(Reference("⌿paths⌿/both⌿post⌿responses⌿200⌿name"), TypeRef(Reference("⌿paths⌿/both⌿post⌿responses⌿200⌿name"))),
					Field(Reference("⌿paths⌿/both⌿post⌿responses⌿200⌿year"), TypeRef(Reference("⌿paths⌿/both⌿post⌿year"))),
					Field(Reference("⌿paths⌿/both⌿post⌿responses⌿200⌿avatarSize"), TypeRef(Reference("⌿paths⌿/both⌿post⌿year"))),
					Field(Reference("⌿paths⌿/both⌿post⌿responses⌿200⌿ringtoneSize"), TypeRef(Reference("⌿paths⌿/both⌿post⌿year")))
			), new TypeMeta(None, List())),
	Reference("⌿paths⌿/both⌿post⌿responses⌿200⌿name") → 
		Opt(Str(None, new TypeMeta(None, List())), new TypeMeta(None, List()))
) 
 
 val parameters = Map[ParameterRef, Parameter](
	ParameterRef(	Reference("⌿paths⌿/url-encoded⌿post⌿name")) → Parameter("name", Str(None, new TypeMeta(None, List())), None, None, ".+", encode = true, ParameterPlace.withName("formData")),
	ParameterRef(	Reference("⌿paths⌿/both⌿post⌿year")) → Parameter("year", TypeRef(Reference("⌿paths⌿/both⌿post⌿year")), None, None, ".+", encode = true, ParameterPlace.withName("formData")),
	ParameterRef(	Reference("⌿paths⌿/multipart⌿post⌿name")) → Parameter("name", Str(None, new TypeMeta(None, List())), None, None, ".+", encode = true, ParameterPlace.withName("formData")),
	ParameterRef(	Reference("⌿paths⌿/url-encoded⌿post⌿year")) → Parameter("year", TypeRef(Reference("⌿paths⌿/both⌿post⌿year")), None, None, ".+", encode = true, ParameterPlace.withName("formData")),
	ParameterRef(	Reference("⌿paths⌿/url-encoded⌿post⌿avatar")) → Parameter("avatar", File(new TypeMeta(None, List())), None, None, ".+", encode = true, ParameterPlace.withName("formData")),
	ParameterRef(	Reference("⌿paths⌿/both⌿post⌿avatar")) → Parameter("avatar", TypeRef(Reference("⌿paths⌿/multipart⌿post⌿avatar")), None, None, ".+", encode = true, ParameterPlace.withName("formData")),
	ParameterRef(	Reference("⌿paths⌿/both⌿post⌿ringtone")) → Parameter("ringtone", File(new TypeMeta(None, List())), None, None, ".+", encode = true, ParameterPlace.withName("formData")),
	ParameterRef(	Reference("⌿paths⌿/both⌿post⌿name")) → Parameter("name", Str(None, new TypeMeta(None, List())), None, None, ".+", encode = true, ParameterPlace.withName("formData")),
	ParameterRef(	Reference("⌿paths⌿/multipart⌿post⌿avatar")) → Parameter("avatar", TypeRef(Reference("⌿paths⌿/multipart⌿post⌿avatar")), None, None, ".+", encode = true, ParameterPlace.withName("formData")),
	ParameterRef(	Reference("⌿paths⌿/multipart⌿post⌿year")) → Parameter("year", TypeRef(Reference("⌿paths⌿/both⌿post⌿year")), None, None, ".+", encode = true, ParameterPlace.withName("formData"))
) 
val basePath: String = "/form_data" 
val discriminators: DiscriminatorLookupTable = Map[Reference, Reference](
	)
val securityDefinitions: SecurityDefinitionsTable = Map[String, Security.Definition](
	
)
val stateTransitions: StateTransitionsTable = Map[State, Map[State, TransitionProperties]]()
val calls: Seq[ApiCall] = Seq(
	ApiCall(POST, Path(Reference("⌿multipart")), 
		HandlerCall(
			"form_data.yaml",
			"Form_dataYaml",
			instantiate = false,
			"postmultipart",parameters = 
			Seq(
				ParameterRef(Reference("⌿paths⌿/multipart⌿post⌿name")),
				ParameterRef(Reference("⌿paths⌿/multipart⌿post⌿year")),
				ParameterRef(Reference("⌿paths⌿/multipart⌿post⌿avatar"))
				)
			), 
		Set(MimeType("multipart/form-data")), 
		Set(MimeType("application/json")), 
		Map.empty[String, Seq[Class[Exception]]], 
		TypesResponseInfo(
			Map[Int, ParameterRef](
			200 -> ParameterRef(Reference("⌿paths⌿/multipart⌿post⌿responses⌿200"))
		), None), 
		StateResponseInfo(
				Map[Int, State](
					200 -> Self
			), None), 
		Set.empty[Security.Constraint]), 
	ApiCall(POST, Path(Reference("⌿url-encoded")), 
		HandlerCall(
			"form_data.yaml",
			"Form_dataYaml",
			instantiate = false,
			"posturl_encoded",parameters = 
			Seq(
				ParameterRef(Reference("⌿paths⌿/url-encoded⌿post⌿name")),
				ParameterRef(Reference("⌿paths⌿/url-encoded⌿post⌿year")),
				ParameterRef(Reference("⌿paths⌿/url-encoded⌿post⌿avatar"))
				)
			), 
		Set(MimeType("application/x-www-form-urlencoded")), 
		Set(MimeType("application/json")), 
		Map.empty[String, Seq[Class[Exception]]], 
		TypesResponseInfo(
			Map[Int, ParameterRef](
			200 -> ParameterRef(Reference("⌿paths⌿/multipart⌿post⌿responses⌿200"))
		), None), 
		StateResponseInfo(
				Map[Int, State](
					200 -> Self
			), None), 
		Set.empty[Security.Constraint]), 
	ApiCall(POST, Path(Reference("⌿both")), 
		HandlerCall(
			"form_data.yaml",
			"Form_dataYaml",
			instantiate = false,
			"postboth",parameters = 
			Seq(
				ParameterRef(Reference("⌿paths⌿/both⌿post⌿name")),
				ParameterRef(Reference("⌿paths⌿/both⌿post⌿year")),
				ParameterRef(Reference("⌿paths⌿/both⌿post⌿avatar")),
				ParameterRef(Reference("⌿paths⌿/both⌿post⌿ringtone"))
				)
			), 
		Set(MimeType("application/x-www-form-urlencoded"), MimeType("multipart/form-data")), 
		Set(MimeType("application/json")), 
		Map.empty[String, Seq[Class[Exception]]], 
		TypesResponseInfo(
			Map[Int, ParameterRef](
			200 -> ParameterRef(Reference("⌿paths⌿/both⌿post⌿responses⌿200"))
		), None), 
		StateResponseInfo(
				Map[Int, State](
					200 -> Self
			), None), 
		Set.empty[Security.Constraint]))

val packageName: Option[String] = Some("form_data.yaml")

val model = new StrictModel(calls, types, parameters, discriminators, basePath, packageName, stateTransitions, securityDefinitions)
    
} 