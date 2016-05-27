package de.zalando.model

import de.zalando.apifirst.Application._
import de.zalando.apifirst.Domain._
import de.zalando.apifirst.Http._
import de.zalando.apifirst.Hypermedia._
import de.zalando.apifirst.{ParameterPlace, Security}
import de.zalando.apifirst.naming._
//noinspection ScalaStyle
object expanded_polymorphism_yaml extends WithModel {
 
 val types = Map[Reference, Type](
	Reference("⌿definitions⌿NewPet") → 
		TypeDef(Reference("⌿definitions⌿NewPet"), 
			Seq(
					Field(Reference("⌿definitions⌿NewPet⌿name"), Str(None, new TypeMeta(None, List()))),
					Field(Reference("⌿definitions⌿NewPet⌿tag"), TypeRef(Reference("⌿definitions⌿NewPet⌿tag")))
			), new TypeMeta(None, List())),
	Reference("⌿definitions⌿Pet") → 
					AllOf(Reference("⌿definitions⌿Pet⌿Pet"), new TypeMeta(None, List()),  Seq(
			TypeRef(Reference("⌿definitions⌿NewPet")),
			TypeRef(Reference("⌿definitions⌿Pet⌿AllOf1"))) , None),
	Reference("⌿definitions⌿Error") → 
		TypeDef(Reference("⌿definitions⌿Error"), 
			Seq(
					Field(Reference("⌿definitions⌿Error⌿code"), Intgr(new TypeMeta(None, List()))),
					Field(Reference("⌿definitions⌿Error⌿message"), Str(None, new TypeMeta(None, List())))
			), new TypeMeta(None, List())),
	Reference("⌿definitions⌿Pet⌿AllOf1") → 
		TypeDef(Reference("⌿definitions⌿Pet"), 
			Seq(
					Field(Reference("⌿definitions⌿Pet⌿id"), Lng(new TypeMeta(None, List())))
			), new TypeMeta(None, List())),
	Reference("⌿definitions⌿NewPet⌿tag") → 
		Opt(Str(None, new TypeMeta(None, List())), new TypeMeta(None, List())),
	Reference("⌿paths⌿/pets/{id}⌿delete⌿id") → 
		Lng(new TypeMeta(None, List())),
	Reference("⌿paths⌿/pets⌿get⌿limit") → 
		Opt(Intgr(new TypeMeta(None, List("""max(10.toInt, false)""", """min(1.toInt, false)"""))), new TypeMeta(None, List())),
	Reference("⌿paths⌿/pets⌿get⌿tags") → 
		Opt(TypeRef(Reference("⌿paths⌿/pets⌿get⌿tags⌿Opt")), new TypeMeta(None, List())),
	Reference("⌿paths⌿/pets/{id}⌿delete⌿responses⌿204") → 
		Null(new TypeMeta(None, List())),
	Reference("⌿paths⌿/pets⌿get⌿tags⌿Opt") → 
		Arr(Str(None, new TypeMeta(None, List())), new TypeMeta(None, List()), "csv"),
	Reference("⌿paths⌿/pets⌿get⌿responses⌿200") → 
		ArrResult(TypeRef(Reference("⌿definitions⌿Pet")), new TypeMeta(None, List()))
) 
 
 val parameters = Map[ParameterRef, Parameter](
	ParameterRef(	Reference("⌿paths⌿/pets/{id}⌿get⌿id")) → Parameter("id", Lng(new TypeMeta(None, List())), None, None, "[^/]+", encode = true, ParameterPlace.withName("path")),
	ParameterRef(	Reference("⌿paths⌿/pets/{id}⌿delete⌿id")) → Parameter("id", Lng(new TypeMeta(None, List())), None, None, "[^/]+", encode = true, ParameterPlace.withName("path")),
	ParameterRef(	Reference("⌿paths⌿/pets⌿post⌿pet")) → Parameter("pet", TypeRef(Reference("⌿definitions⌿NewPet")), None, None, ".+", encode = false, ParameterPlace.withName("body")),
	ParameterRef(	Reference("⌿paths⌿/pets⌿get⌿limit")) → Parameter("limit", TypeRef(Reference("⌿paths⌿/pets⌿get⌿limit")), None, None, ".+", encode = true, ParameterPlace.withName("query")),
	ParameterRef(	Reference("⌿paths⌿/pets⌿get⌿tags")) → Parameter("tags", TypeRef(Reference("⌿paths⌿/pets⌿get⌿tags")), None, None, ".+", encode = true, ParameterPlace.withName("query"))
) 
val basePath: String = "/api" 
val discriminators: DiscriminatorLookupTable = Map[Reference, Reference](
	)
val securityDefinitions: SecurityDefinitionsTable = Map[String, Security.Definition](
	
)
val stateTransitions: StateTransitionsTable = Map[State, Map[State, TransitionProperties]]()
val calls: Seq[ApiCall] = Seq(
	ApiCall(GET, Path(Reference("⌿pets")), 
		HandlerCall(
			"expanded",
			"Expanded_polymorphismYaml",
			instantiate = false,
			"findPets",parameters = 
			Seq(
				ParameterRef(Reference("⌿paths⌿/pets⌿get⌿tags")),
				ParameterRef(Reference("⌿paths⌿/pets⌿get⌿limit"))
				)
			), 
		Set(MimeType("application/json")), 
		Set(MimeType("application/json")), 
		Map("404" -> Seq(classOf[java.util.NoSuchElementException])), 
		TypesResponseInfo(
			Map[Int, ParameterRef](
			200 -> ParameterRef(Reference("⌿paths⌿/pets⌿get⌿responses⌿200"))
		), Some(	ParameterRef(Reference("⌿definitions⌿Error")))), 
		StateResponseInfo(
				Map[Int, State](
					200 -> Self
			), Some(Self)), 
		Set.empty[Security.Constraint]), 
	ApiCall(POST, Path(Reference("⌿pets")), 
		HandlerCall(
			"expanded",
			"Expanded_polymorphismYaml",
			instantiate = false,
			"addPet",parameters = 
			Seq(
				ParameterRef(Reference("⌿paths⌿/pets⌿post⌿pet"))
				)
			), 
		Set(MimeType("application/json")), 
		Set(MimeType("application/json")), 
		Map("404" -> Seq(classOf[java.util.NoSuchElementException])), 
		TypesResponseInfo(
			Map[Int, ParameterRef](
			200 -> ParameterRef(Reference("⌿definitions⌿Pet"))
		), Some(	ParameterRef(Reference("⌿definitions⌿Error")))), 
		StateResponseInfo(
				Map[Int, State](
					200 -> Self
			), Some(Self)), 
		Set.empty[Security.Constraint]), 
	ApiCall(DELETE, Path(Reference("⌿pets⌿{id}")), 
		HandlerCall(
			"expanded",
			"Expanded_polymorphismYaml",
			instantiate = false,
			"deletePet",parameters = 
			Seq(
				ParameterRef(Reference("⌿paths⌿/pets/{id}⌿delete⌿id"))
				)
			), 
		Set(MimeType("application/json")), 
		Set(MimeType("application/json")), 
		Map("404" -> Seq(classOf[java.util.NoSuchElementException])), 
		TypesResponseInfo(
			Map[Int, ParameterRef](
			204 -> ParameterRef(Reference("⌿paths⌿/pets/{id}⌿delete⌿responses⌿204"))
		), Some(	ParameterRef(Reference("⌿definitions⌿Error")))), 
		StateResponseInfo(
				Map[Int, State](
					204 -> Self
			), Some(Self)), 
		Set.empty[Security.Constraint]))

val packageName: Option[String] = Some("expanded")

val model = new StrictModel(calls, types, parameters, discriminators, basePath, packageName, stateTransitions, securityDefinitions)
    
} 