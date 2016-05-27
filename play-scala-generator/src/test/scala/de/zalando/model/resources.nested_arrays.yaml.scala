package de.zalando.model

import de.zalando.apifirst.Application._
import de.zalando.apifirst.Domain._
import de.zalando.apifirst.Hypermedia._
import de.zalando.apifirst.Security
import de.zalando.apifirst.naming._
//noinspection ScalaStyle
object nested_arrays_yaml extends WithModel {
 
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
		Opt(Str(None, new TypeMeta(None, List())), new TypeMeta(None, List())),
	Reference("⌿definitions⌿Example⌿nestedArrays⌿Opt") → 
		Arr(TypeRef(Reference("⌿definitions⌿Example⌿nestedArrays⌿Opt⌿Arr")), new TypeMeta(None, List()), "csv"),
	Reference("⌿definitions⌿Example⌿messages⌿Opt") → 
		Arr(TypeRef(Reference("⌿definitions⌿Example⌿messages⌿Opt⌿Arr")), new TypeMeta(None, List()), "csv"),
	Reference("⌿definitions⌿Example⌿nestedArrays⌿Opt⌿Arr") → 
		Arr(TypeRef(Reference("⌿definitions⌿Example⌿nestedArrays⌿Opt⌿Arr⌿Arr")), new TypeMeta(None, List()), "csv"),
	Reference("⌿definitions⌿Example⌿messages⌿Opt⌿Arr") → 
		Arr(TypeRef(Reference("⌿definitions⌿Activity")), new TypeMeta(None, List()), "csv"),
	Reference("⌿definitions⌿Example⌿nestedArrays⌿Opt⌿Arr⌿Arr") → 
		Arr(TypeRef(Reference("⌿definitions⌿Example⌿nestedArrays⌿Opt⌿Arr⌿Arr⌿Arr")), new TypeMeta(None, List()), "csv"),
	Reference("⌿definitions⌿Example⌿nestedArrays⌿Opt⌿Arr⌿Arr⌿Arr") → 
		Arr(Str(Some("nested arrays"), new TypeMeta(None, List())), new TypeMeta(None, List()), "csv")
) 
 
 val parameters = Map[ParameterRef, Parameter](

) 
val basePath: String = "/api" 
val discriminators: DiscriminatorLookupTable = Map[Reference, Reference](
	)
val securityDefinitions: SecurityDefinitionsTable = Map[String, Security.Definition](
	
)
val stateTransitions: StateTransitionsTable = Map[State, Map[State, TransitionProperties]]()
val calls: Seq[ApiCall] = Seq()

val packageName: Option[String] = None

val model = new StrictModel(calls, types, parameters, discriminators, basePath, packageName, stateTransitions, securityDefinitions)
    
} 