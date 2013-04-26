package models.base

import models.SystemEvent
import play.api.i18n.Lang

object AccessibleEntity {
  val IDENTIFIER = "identifier"
  val NAME = "name"

  final val EVENT_REL = "lifecycleEvent"
  final val ACCESS_REL = "access"
}

trait AccessibleEntity extends WrappedEntity {
  import AccessibleEntity._

  val nameProperty = NAME
  
  def accessors = e.relations(ACCESS_REL).map(Accessor(_))
  def latestEvent: Option[SystemEvent] = e.relations(EVENT_REL).headOption.map(SystemEvent(_))

  override def toString = e.stringProperty(nameProperty).getOrElse(e.id)


  // Language-aware toString
  def toStringLang(implicit lang: Lang) = {
    if (isInstanceOf[DescribedEntity[_]]) {
      val descriptions = asInstanceOf[DescribedEntity[_]].descriptions

      descriptions.find(_.asInstanceOf[Description].languageCode==lang.code).orElse(descriptions.headOption)
        .map(_.toString).getOrElse(id)
    } else {
      toString
    }
  }

}