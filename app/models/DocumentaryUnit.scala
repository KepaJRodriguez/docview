package models

import defines._
import models.base.HierarchicalEntity
import models.base.AccessibleEntity
import models.base.NamedEntity
import models.base.Description
import models.base.DescribedEntity
import models.base.Formable
import models.base.TemporalEntity

import forms.{DocumentaryUnitF,DocumentaryUnitDescriptionF}


case class DocumentaryUnit(val e: Entity) extends NamedEntity
  with AccessibleEntity
  with HierarchicalEntity
  with DescribedEntity
  with TemporalEntity
  with Formable[DocumentaryUnitF] {

  import DocumentaryUnitF._
  import DescribedEntity._
  import forms.IsadG._

  val holder: Option[Agent] = e.relations(HELD_REL).headOption.map(Agent(_))
  val parent: Option[DocumentaryUnit] = e.relations(CHILD_REL).headOption.map(DocumentaryUnit(_))
  val publicationStatus = e.property(PUB_STATUS).flatMap(enum(PublicationStatus).reads(_).asOpt)
  override def descriptions: List[DocumentaryUnitDescription] = e.relations(DESCRIBES_REL).map(DocumentaryUnitDescription(_))

  def to: DocumentaryUnitF = new DocumentaryUnitF(
    id = Some(e.id),
    identifier = identifier,
    name = name,
    publicationStatus = publicationStatus,
    descriptions = descriptions.map(_.to)
  )
}

case class DocumentaryUnitDescription(val e: Entity)
  extends Description
  with Formable[DocumentaryUnitDescriptionF] {
  import models.forms.IsadG._
  import DocumentaryUnitDescriptionF._
  def to = new DocumentaryUnitDescriptionF(
    id = Some(e.id),
    languageCode = languageCode,
    title = e.property(TITLE).flatMap(_.asOpt[String]),
    context = Context(
      adminBiogHist = stringProperty(ADMIN_BIOG),
      archivalHistory = stringProperty(ARCH_HIST),
      acquisition = stringProperty(ACQUISITION)
    ),
    content = Content(
      scopeAndContent = stringProperty(SCOPE_CONTENT),
      appraisal = stringProperty(APPRAISAL),
      accruals = stringProperty(ACCRUALS),
      systemOfArrangement = stringProperty(SYS_ARR)
    ),
    conditions = Conditions(
      conditionsOfAccess = stringProperty(ACCESS_COND),
      conditionsOfReproduction = stringProperty(REPROD_COND),
      physicalCharacteristics = stringProperty(PHYSICAL_CHARS),
      findingAids = stringProperty(FINDING_AIDS)
    ),
    materials = Materials(
      locationOfOriginals = stringProperty(LOCATION_ORIGINALS),
      locationOfCopies = stringProperty(LOCATION_COPIES),
      relatedUnitsOfDescription = stringProperty(RELATED_UNITS),
      publicationNote = stringProperty(PUBLICATION_NOTE)
    )
  )
}
