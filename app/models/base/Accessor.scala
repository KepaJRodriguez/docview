package models.base

import defines.EntityType
import models.Entity
import models.Group
import models.UserProfile

object Accessor {
  final val BELONGS_REL = "belongsTo"
    
	def apply(e: Entity): Accessor = e.isA match {
	  case EntityType.UserProfile => UserProfile(e)
	  case EntityType.Group => Group(e)
	  case _ => sys.error("Unknow entity type for Accessor: " + e.isA.toString())
	}
}

trait Accessor extends AccessibleEntity with NamedEntity {
  val groups: List[Group] = e.relations(Accessor.BELONGS_REL).map(Group(_))

  lazy val allGroups: List[Group] = getGroups(this)

  def isAdmin = getAccessor(groups, "admin").isDefined

	// Search up the tree(?) if parent groups, looking
	// for one with the desired id.
	def getAccessor(groups: List[Accessor], id: String): Option[Accessor] = {
	  groups match {
	    case lst @ head :: rest => {
	      if (head.id == id) Some(head)	        
	      else getAccessor(head.groups, id) match {
	          case s @ Some(g) => s
	          case None => getAccessor(rest, id)
	      }
	    }
	    case Nil => None
	  }
	}

  private def getGroups(acc: Accessor): List[Group] = {
    acc.groups.foldLeft(acc.groups) { case (all, g) =>
      all ++ getGroups(g)
    }.distinct
  }
}