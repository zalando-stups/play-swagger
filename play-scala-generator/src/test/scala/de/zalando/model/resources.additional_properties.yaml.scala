package de.zalando.model

import de.zalando.apifirst.Application._
import de.zalando.apifirst.Domain._
import de.zalando.apifirst.Hypermedia._
import de.zalando.apifirst.Security
import de.zalando.apifirst.naming._
//noinspection ScalaStyle
object additional_properties_yaml extends WithModel {
 
 val types = Map[Reference, Type](
	Reference("⌿definitions⌿KeyedArrays") → 
		TypeDef(Reference("⌿definitions⌿KeyedArrays"), 
			Seq(
					Field(Reference("⌿definitions⌿KeyedArrays⌿additionalProperties"), TypeRef(Reference("⌿definitions⌿KeyedArrays⌿additionalProperties")))
			), new TypeMeta(None, List())),
	Reference("⌿definitions⌿KeyedArrays⌿additionalProperties") → 
		CatchAll(TypeRef(Reference("⌿definitions⌿KeyedArrays⌿additionalProperties⌿CatchAll")), new TypeMeta(None, List())),
	Reference("⌿definitions⌿KeyedArrays⌿additionalProperties⌿CatchAll") → 
		Arr(BInt(new TypeMeta(None, List())), new TypeMeta(None, List()), "csv")
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