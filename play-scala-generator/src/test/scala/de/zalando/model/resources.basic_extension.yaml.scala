package de.zalando.model

import de.zalando.apifirst.Application._
import de.zalando.apifirst.Domain._
import de.zalando.apifirst.Hypermedia._
import de.zalando.apifirst.Security
import de.zalando.apifirst.naming._
//noinspection ScalaStyle
object basic_extension_yaml extends WithModel {
 
 val types = Map[Reference, Type](
	Reference("⌿definitions⌿ErrorModel") → 
		TypeDef(Reference("⌿definitions⌿ErrorModel"), 
			Seq(
					Field(Reference("⌿definitions⌿ErrorModel⌿message"), Str(None, new TypeMeta(None, List()))),
					Field(Reference("⌿definitions⌿ErrorModel⌿code"), BInt(new TypeMeta(None, List("""max(BigInt("600"), false)""", """min(BigInt("100"), false)"""))))
			), new TypeMeta(None, List())),
	Reference("⌿definitions⌿ExtendedErrorModel") → 
					AllOf(Reference("⌿definitions⌿ExtendedErrorModel⌿ExtendedErrorModel"), new TypeMeta(None, List()),  Seq(
			TypeRef(Reference("⌿definitions⌿ErrorModel")),
			TypeRef(Reference("⌿definitions⌿ExtendedErrorModel⌿AllOf1"))) , None),
	Reference("⌿definitions⌿ExtendedErrorModel⌿AllOf1") → 
		TypeDef(Reference("⌿definitions⌿ExtendedErrorModel"), 
			Seq(
					Field(Reference("⌿definitions⌿ExtendedErrorModel⌿rootCause"), Str(None, new TypeMeta(None, List())))
			), new TypeMeta(None, List()))
) 
 
 val parameters = Map[ParameterRef, Parameter](

) 
val basePath: String =null
val discriminators: DiscriminatorLookupTable = Map[Reference, Reference](
	)
val securityDefinitions: SecurityDefinitionsTable = Map[String, Security.Definition](
	
)
val stateTransitions: StateTransitionsTable = Map[State, Map[State, TransitionProperties]]()
val calls: Seq[ApiCall] = Seq()

val packageName: Option[String] = None

val model = new StrictModel(calls, types, parameters, discriminators, basePath, packageName, stateTransitions, securityDefinitions)
    
} 