package models.json

import play.api.libs.json._
import models._
import play.api.libs.functional.syntax._
import defines.EntityType
import defines.EnumUtils._
import models.base.{TemporalEntity, Description}


object IsaarFormat {
  import Entity._
  import HistoricalAgentF._
  import AccessPointFormat._
  import Isaar._

  implicit val HistoricalAgentTypeReads = defines.EnumUtils.enumReads(HistoricalAgentType)

  implicit val isaarWrites = new Writes[HistoricalAgentDescriptionF] {
    def writes(d: HistoricalAgentDescriptionF): JsValue = {
      Json.obj(
        ID -> d.id,
        TYPE -> d.isA,
        DATA -> Json.obj(
          AUTHORIZED_FORM_OF_NAME -> d.name,
          ENTITY_TYPE -> d.entityType,
          LANG_CODE -> d.languageCode,
          OTHER_FORMS_OF_NAME -> d.otherFormsOfName,
          PARALLEL_FORMS_OF_NAME -> d.parallelFormsOfName,
          DATES_OF_EXISTENCE -> d.details.datesOfExistence,
          HISTORY -> d.details.history,
          PLACES -> d.details.places,
          LEGAL_STATUS -> d.details.legalStatus,
          FUNCTIONS -> d.details.functions,
          MANDATES -> d.details.mandates,
          INTERNAL_STRUCTURE -> d.details.internalStructure,
          GENERAL_CONTEXT -> d.details.generalContext,
          DESCRIPTION_IDENTIFIER -> d.control.descriptionIdentifier,
          INSTITUTION_IDENTIFIER -> d.control.institutionIdentifier,
          RULES_CONVENTIONS -> d.control.rulesAndConventions,
          STATUS -> d.control.status,
          LEVEL_OF_DETAIL -> d.control.levelOfDetail,
          DATES_CVD -> d.control.datesCDR,
          LANGUAGES_USED -> d.control.languages,
          SCRIPTS_USED -> d.control.scripts,
          SOURCES -> d.control.sources,
          MAINTENANCE_NOTES -> d.control.maintenanceNotes
        ),
        RELATIONSHIPS -> Json.obj(
          Description.ACCESS_REL -> Json.toJson(d.accessPoints.map(Json.toJson(_)).toSeq)
        )
      )
    }
  }

  import HistoricalAgentDescriptionF._
  implicit val isaarReads: Reads[HistoricalAgentDescriptionF] = (
    (__ \ TYPE).read[EntityType.Value](equalsReads(EntityType.HistoricalAgentDescription)) andKeep
    (__ \ ID).readNullable[String] and
      (__ \ DATA \ LANG_CODE).read[String] and
      ((__ \ DATA \ ENTITY_TYPE).read[HistoricalAgentType.Value]
          orElse Reads.pure(HistoricalAgentType.CorporateBody)) and
      ((__ \ DATA \ AUTHORIZED_FORM_OF_NAME).read[String]
          orElse Reads.pure(UNNAMED_PLACEHOLDER)) and
      ((__ \ DATA \ OTHER_FORMS_OF_NAME).readNullable[List[String]] orElse
        (__ \ DATA \ OTHER_FORMS_OF_NAME).readNullable[String].map(os => os.map(List(_))) ) and
      ((__ \ DATA \ PARALLEL_FORMS_OF_NAME).readNullable[List[String]] orElse
        (__ \ DATA \ PARALLEL_FORMS_OF_NAME).readNullable[String].map(os => os.map(List(_))) ) and
      ((__ \ RELATIONSHIPS \ TemporalEntity.DATE_REL).lazyRead[List[DatePeriodF]](
        Reads.list[DatePeriodF]) orElse Reads.pure(Nil)) and
      (__ \ DATA).read[Details]((
        (__ \ DATES_OF_EXISTENCE).readNullable[String] and
        (__ \ HISTORY).readNullable[String] and
        (__ \ PLACES).readNullable[String] and
        (__ \ LEGAL_STATUS).readNullable[String] and
        (__ \ FUNCTIONS).readNullable[String] and
        (__ \ MANDATES).readNullable[String] and
        (__ \ INTERNAL_STRUCTURE).readNullable[String] and
        (__ \ GENERAL_CONTEXT).readNullable[String]
      )(Details.apply _)) and
      (__ \ DATA).read[Control]((
        (__ \ DESCRIPTION_IDENTIFIER).readNullable[String] and
        (__ \ INSTITUTION_IDENTIFIER).readNullable[String] and
        (__ \ RULES_CONVENTIONS).readNullable[String] and
        (__ \ STATUS).readNullable[String] and
        (__ \ LEVEL_OF_DETAIL).readNullable[String] and
        (__ \ DATES_CVD).readNullable[String] and
        (__ \ LANGUAGES_USED).readNullable[List[String]] and
        (__ \ SCRIPTS_USED).readNullable[List[String]] and
        ((__ \ SOURCES).readNullable[List[String]] orElse
          (__ \ SOURCES).readNullable[String].map(os => os.map(List(_))) ) and
        (__ \ MAINTENANCE_NOTES).readNullable[String]
      )(Control.apply _)) and
      ((__ \ RELATIONSHIPS \ Description.ACCESS_REL).lazyRead[List[AccessPointF]](
          Reads.list[AccessPointF]) orElse Reads.pure(Nil))
  )(HistoricalAgentDescriptionF.apply _)

  implicit val isaarFormat: Format[HistoricalAgentDescriptionF] = Format(isaarReads,isaarWrites)
}