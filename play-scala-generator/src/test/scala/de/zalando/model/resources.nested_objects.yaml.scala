package de.zalando.model

import de.zalando.apifirst.Application._
import de.zalando.apifirst.Domain._
import de.zalando.apifirst.Hypermedia._
import de.zalando.apifirst.Security
import de.zalando.apifirst.naming._
//noinspection ScalaStyle
object nested_objects_yaml extends WithModel {
 
 val types = Map[Reference, Type](
	Reference("⌿definitions⌿NestedObjects") → 
		TypeDef(Reference("⌿definitions⌿NestedObjects"), 
			Seq(
					Field(Reference("⌿definitions⌿NestedObjects⌿plain"), TypeRef(Reference("⌿definitions⌿NestedObjects⌿plain"))),
					Field(Reference("⌿definitions⌿NestedObjects⌿nested"), TypeRef(Reference("⌿definitions⌿NestedObjects⌿nested")))
			), new TypeMeta(None, List())),
	Reference("⌿definitions⌿NestedObjects⌿nested") → 
		Opt(TypeRef(Reference("⌿definitions⌿NestedObjects⌿nested⌿Opt")), new TypeMeta(None, List())),
	Reference("⌿definitions⌿NestedObjects⌿plain") → 
		Opt(TypeRef(Reference("⌿definitions⌿NestedObjects⌿plain⌿Opt")), new TypeMeta(None, List())),
	Reference("⌿definitions⌿NestedObjects⌿nested⌿Opt") → 
		TypeDef(Reference("⌿definitions⌿NestedObjects⌿nested"), 
			Seq(
					Field(Reference("⌿definitions⌿NestedObjects⌿nested⌿nested2"), TypeRef(Reference("⌿definitions⌿NestedObjects⌿nested⌿nested2")))
			), new TypeMeta(None, List())),
	Reference("⌿definitions⌿NestedObjects⌿plain⌿Opt") → 
		TypeDef(Reference("⌿definitions⌿NestedObjects⌿plain"), 
			Seq(
					Field(Reference("⌿definitions⌿NestedObjects⌿plain⌿simple"), Str(None, new TypeMeta(None, List("""pattern("""+"""""""""+"""the pattern"""+"""""""""+""".r)"""))))
			), new TypeMeta(None, List())),
	Reference("⌿definitions⌿NestedObjects⌿nested⌿nested2") → 
		TypeDef(Reference("⌿definitions⌿NestedObjects⌿nested⌿nested2"), 
			Seq(
					Field(Reference("⌿definitions⌿NestedObjects⌿nested⌿nested2⌿nested3"), TypeRef(Reference("⌿definitions⌿NestedObjects⌿nested⌿nested2⌿nested3")))
			), new TypeMeta(None, List())),
	Reference("⌿definitions⌿NestedObjects⌿nested⌿nested2⌿nested3") → 
		Opt(TypeRef(Reference("⌿definitions⌿NestedObjects⌿nested⌿nested2⌿nested3⌿Opt")), new TypeMeta(None, List())),
	Reference("⌿definitions⌿NestedObjects⌿nested⌿nested2⌿nested3⌿bottom") → 
		Opt(Str(None, new TypeMeta(None, List("""maxLength(30)""", """minLength(3)"""))), new TypeMeta(None, List())),
	Reference("⌿definitions⌿NestedObjects⌿nested⌿nested2⌿nested3⌿Opt") → 
		TypeDef(Reference("⌿definitions⌿NestedObjects⌿nested⌿nested2⌿nested3"), 
			Seq(
					Field(Reference("⌿definitions⌿NestedObjects⌿nested⌿nested2⌿nested3⌿bottom"), TypeRef(Reference("⌿definitions⌿NestedObjects⌿nested⌿nested2⌿nested3⌿bottom")))
			), new TypeMeta(None, List()))
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