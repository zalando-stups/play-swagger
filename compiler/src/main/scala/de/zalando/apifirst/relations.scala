package de.zalando.apifirst

import de.zalando.apifirst.Domain.TypeDef

/**
 * @author  slasch 
 * @since   03.11.2015.
 */

object relations {

  sealed trait ClassRelation

  case class HierarchyRoot(tpe: TypeDef) extends ClassRelation

  case class HierarchyDescendant(root: HierarchyRoot, tpe: TypeDef) extends ClassRelation

  case class Composition(parts: Seq[TypeDef]) extends ClassRelation

}
