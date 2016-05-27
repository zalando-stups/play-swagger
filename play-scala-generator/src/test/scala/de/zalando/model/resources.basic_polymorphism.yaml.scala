package de.zalando.model

import de.zalando.apifirst.Application._
import de.zalando.apifirst.Domain._
import de.zalando.apifirst.Hypermedia._
import de.zalando.apifirst.Security
import de.zalando.apifirst.naming._
//noinspection ScalaStyle
object basic_polymorphism_yaml extends WithModel {
 
 val types = Map[Reference, Type](
	Reference("⌿definitions⌿Cat") → 
					AllOf(Reference("⌿definitions⌿Cat⌿Cat"), new TypeMeta(None, List()),  Seq(
			TypeRef(Reference("⌿definitions⌿Pet")),
			TypeRef(Reference("⌿definitions⌿Cat⌿AllOf1"))) , Some(Reference("⌿definitions⌿Pet⌿petType"))),
	Reference("⌿definitions⌿Dog") → 
					AllOf(Reference("⌿definitions⌿Dog⌿Dog"), new TypeMeta(None, List()),  Seq(
			TypeRef(Reference("⌿definitions⌿Pet")),
			TypeRef(Reference("⌿definitions⌿Dog⌿AllOf1"))) , Some(Reference("⌿definitions⌿Pet⌿petType"))),
	Reference("⌿definitions⌿CatNDog") → 
					AllOf(Reference("⌿definitions⌿CatNDog⌿CatNDog"), new TypeMeta(None, List()),  Seq(
			TypeRef(Reference("⌿definitions⌿Dog")),
			TypeRef(Reference("⌿definitions⌿Cat"))) , Some(Reference("⌿definitions⌿Pet⌿petType"))),
	Reference("⌿definitions⌿Pet") → 
		TypeDef(Reference("⌿definitions⌿Pet"), 
			Seq(
					Field(Reference("⌿definitions⌿Pet⌿name"), Str(None, new TypeMeta(None, List()))),
					Field(Reference("⌿definitions⌿Pet⌿petType"), Str(None, new TypeMeta(None, List())))
			), new TypeMeta(None, List())),
	Reference("⌿definitions⌿Labrador") → 
					AllOf(Reference("⌿definitions⌿Labrador⌿Labrador"), new TypeMeta(None, List()),  Seq(
			TypeRef(Reference("⌿definitions⌿Dog")),
			TypeRef(Reference("⌿definitions⌿Labrador⌿AllOf1"))) , Some(Reference("⌿definitions⌿Pet⌿petType"))),
	Reference("⌿definitions⌿Cat⌿AllOf1") → 
		TypeDef(Reference("⌿definitions⌿Cat"), 
			Seq(
					Field(Reference("⌿definitions⌿Cat⌿huntingSkill"), TypeRef(Reference("⌿definitions⌿Cat⌿huntingSkill")))
			), new TypeMeta(None, List())),
	Reference("⌿definitions⌿Dog⌿AllOf1") → 
		TypeDef(Reference("⌿definitions⌿Dog"), 
			Seq(
					Field(Reference("⌿definitions⌿Dog⌿packSize"), Intgr(new TypeMeta(None, List("""min(0.toInt, false)"""))))
			), new TypeMeta(None, List())),
	Reference("⌿definitions⌿Cat⌿huntingSkill") → 
					EnumTrait(Str(None, new TypeMeta(None, List("""enum(\"clueless,lazy,adventurous,aggressive\")"""))), new TypeMeta(None, List()), 
				Set(
					EnumObject(Str(None, new TypeMeta(None, List("""enum(\"clueless,lazy,adventurous,aggressive\")"""))), "clueless", new TypeMeta(None, List())),
					EnumObject(Str(None, new TypeMeta(None, List("""enum(\"clueless,lazy,adventurous,aggressive\")"""))), "lazy", new TypeMeta(None, List())),
					EnumObject(Str(None, new TypeMeta(None, List("""enum(\"clueless,lazy,adventurous,aggressive\")"""))), "adventurous", new TypeMeta(None, List())),
					EnumObject(Str(None, new TypeMeta(None, List("""enum(\"clueless,lazy,adventurous,aggressive\")"""))), "aggressive", new TypeMeta(None, List()))

				)),
	Reference("⌿definitions⌿Labrador⌿AllOf1") → 
		TypeDef(Reference("⌿definitions⌿Labrador"), 
			Seq(
					Field(Reference("⌿definitions⌿Labrador⌿cuteness"), Intgr(new TypeMeta(None, List("""min(0.toInt, false)"""))))
			), new TypeMeta(None, List())),
	Reference("⌿definitions⌿Cat⌿huntingSkill⌿lazy") → 
					EnumObject(Str(None, new TypeMeta(None, List("""enum(\"clueless,lazy,adventurous,aggressive\")"""))), "lazy", new TypeMeta(None, List())),
	Reference("⌿definitions⌿Cat⌿huntingSkill⌿clueless") → 
					EnumObject(Str(None, new TypeMeta(None, List("""enum(\"clueless,lazy,adventurous,aggressive\")"""))), "clueless", new TypeMeta(None, List())),
	Reference("⌿definitions⌿CatNDog⌿huntingSkill⌿aggressive") → 
					EnumObject(Str(None, new TypeMeta(None, List("""enum(\"clueless,lazy,adventurous,aggressive\")"""))), "aggressive", new TypeMeta(None, List())),
	Reference("⌿definitions⌿Cat⌿huntingSkill⌿adventurous") → 
					EnumObject(Str(None, new TypeMeta(None, List("""enum(\"clueless,lazy,adventurous,aggressive\")"""))), "adventurous", new TypeMeta(None, List()))
) 
 
 val parameters = Map[ParameterRef, Parameter](

) 
val basePath: String =null
val discriminators: DiscriminatorLookupTable = Map[Reference, Reference](
		Reference("⌿definitions⌿CatNDog") -> Reference("⌿definitions⌿Pet⌿petType"),
	Reference("⌿definitions⌿Dog") -> Reference("⌿definitions⌿Pet⌿petType"),
	Reference("⌿definitions⌿Cat") -> Reference("⌿definitions⌿Pet⌿petType"),
	Reference("⌿definitions⌿Labrador") -> Reference("⌿definitions⌿Pet⌿petType"),
	Reference("⌿definitions⌿Pet") -> Reference("⌿definitions⌿Pet⌿petType"))
val securityDefinitions: SecurityDefinitionsTable = Map[String, Security.Definition](
	
)
val stateTransitions: StateTransitionsTable = Map[State, Map[State, TransitionProperties]]()
val calls: Seq[ApiCall] = Seq()

val packageName: Option[String] = None

val model = new StrictModel(calls, types, parameters, discriminators, basePath, packageName, stateTransitions, securityDefinitions)
    
} 