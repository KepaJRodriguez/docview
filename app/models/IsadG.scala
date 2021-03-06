package models

case object IsadG {

  object LevelOfDescription extends Enumeration {
    type Type = Value
    val Collection = Value("collection")
    val Fonds = Value("fonds")
    val Subfonds = Value("subfonds")
    val Series = Value("series")
    val Subseries = Value("subseries")
    val File = Value("file")
    val Item = Value("item")
  }


  val FIELD_PREFIX = "isadg"

  val ACCESS_POINTS = "accessPoints"
  /* ISAD(G)-based field set */
  val TITLE = "name"
  val DATES = "dates"
  val LEVEL_OF_DESCRIPTION = "levelOfDescription"
  val EXTENT_MEDIUM = "extentAndMedium"
  val PUB_STATUS = "publicationStatus"
  val LANG_CODE = "languageCode"

  val IDENTITY_AREA = "identityArea"
  val DESCRIPTIONS_AREA = "descriptionsArea"
  val ADMINISTRATION_AREA = "administrationArea"

  val CONTEXT_AREA = "contextArea"
  val ADMIN_BIOG = "adminBiogHistory"
  val ARCH_HIST = "archivalHistory"
  val ACQUISITION = "acquisition"

  val CONTENT_AREA = "contentArea"
  val SCOPE_CONTENT = "scopeAndContent"
  val APPRAISAL = "appraisal"
  val ACCRUALS = "accruals"
  val SYS_ARR = "systemOfArrangement"

  val CONDITIONS_AREA = "conditionsArea"
  val ACCESS_COND = "conditionsOfAccess"
  val REPROD_COND = "conditionsOfReproduction"
  val LANG_MATERIALS = "languageOfMaterial"
  val SCRIPT_MATERIALS = "scriptOfMaterial"
  val PHYSICAL_CHARS = "physicalCharacteristics"
  val FINDING_AIDS = "findingAids"

  val MATERIALS_AREA = "materialsArea"
  val LOCATION_ORIGINALS = "locationOfOriginals"
  val LOCATION_COPIES = "locationOfCopies"
  val RELATED_UNITS = "relatedUnitsOfDescription"
  val PUBLICATION_NOTE = "publicationNote"

  val NOTES_AREA = "notesArea"
  val NOTES = "notes"

  val CONTROL_AREA = "controlArea"
  val ARCHIVIST_NOTE = "archivistNote"
  val RULES_CONVENTIONS = "rulesAndConventions"
  val DATES_DESCRIPTIONS = "datesOfDescriptions"
}
