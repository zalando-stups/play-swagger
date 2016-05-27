package de.zalando.model

import de.zalando.apifirst.Application._
import de.zalando.apifirst.Domain._
import de.zalando.apifirst.Hypermedia._
import de.zalando.apifirst.Security
import de.zalando.apifirst.naming._
//noinspection ScalaStyle
object nested_options_yaml extends WithModel {
 
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
		Opt(Str(None, new TypeMeta(None, List())), new TypeMeta(None, List()))
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