package de.zalando.model

import de.zalando.apifirst.Application._
import de.zalando.apifirst.Domain._
import de.zalando.apifirst.Hypermedia._
import de.zalando.apifirst.Security
import de.zalando.apifirst.naming._
//noinspection ScalaStyle
object options_yaml extends WithModel {
 
 val types = Map[Reference, Type](
	Reference("⌿definitions⌿Basic") → 
		TypeDef(Reference("⌿definitions⌿Basic"), 
			Seq(
					Field(Reference("⌿definitions⌿Basic⌿id"), Lng(new TypeMeta(None, List()))),
					Field(Reference("⌿definitions⌿Basic⌿required"), TypeRef(Reference("⌿definitions⌿Basic⌿required"))),
					Field(Reference("⌿definitions⌿Basic⌿optional"), TypeRef(Reference("⌿definitions⌿Basic⌿optional")))
			), new TypeMeta(None, List())),
	Reference("⌿definitions⌿Basic⌿required") → 
		Arr(Str(None, new TypeMeta(None, List())), new TypeMeta(None, List()), "csv"),
	Reference("⌿definitions⌿Basic⌿optional") → 
		Opt(TypeRef(Reference("⌿definitions⌿Basic⌿required")), new TypeMeta(None, List()))
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