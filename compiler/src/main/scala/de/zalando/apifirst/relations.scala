package de.zalando.apifirst

import de.zalando.apifirst.Domain.TypeDef

/**
 * @author  slasch 
 * @since   03.11.2015.
 */

object relations {

  object InheritanceStrategy extends Enumeration {
    type InheritanceStrategy = Value
    val Concrete, Mixin, Abstract = Value
  }

  import InheritanceStrategy._

  sealed trait ClassRelation

  case class HierarchyRoot(inheritanceStrategy: InheritanceStrategy, tpe: TypeDef) extends ClassRelation

  case class HierarchyDescendant(root: HierarchyRoot, tpe: TypeDef) extends ClassRelation

  case class Composition(parts: Seq[TypeDef]) extends ClassRelation

}
