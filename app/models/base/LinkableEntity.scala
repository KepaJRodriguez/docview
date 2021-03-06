package models.base

import models._
import models.DocumentaryUnit
import models.Repository

object LinkableEntity {

  final val HAS_LINK_TARGET = "hasLinkTarget"

  def fromEntity(e: Entity): Option[LinkableEntity] = {
    import defines.EntityType

    /**
     * Attempt to instantiate the model representation of this entity.
     * @return
     */
    e.`type` match {
      case EntityType.Concept => Some(Concept(e))
      case EntityType.Repository => Some(Repository(e))
      case EntityType.DocumentaryUnit => Some(DocumentaryUnit(e))
      case EntityType.HistoricalAgent => Some(HistoricalAgent(e))
      case _ => None
    }
  }
}

/**
 * Trait for entities that can be annotated.
 */
trait LinkableEntity extends AccessibleEntity {

  /**
   * Links with no body, i.e. not stemming from an existing access point.
   * @param links
   * @return
   */
  def annotationLinks(links: List[Link]) = links.filter(link => link.bodies.isEmpty)

  def opposingTarget(link: Link): Option[LinkableEntity] = link.targets.filterNot(p => p.id == id).headOption.flatMap(e => LinkableEntity.fromEntity(e.e))
}